package com.scsk.batch.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.exception.BusinessException;
import com.scsk.model.IYoStoreATMInfoDoc;
import com.scsk.model.MasterDataDoc;
import com.scsk.model.Store114ATMInfoDoc;
import com.scsk.model.StoreATMInfoDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;
import com.scsk.util.LogInfoUtil_Batch;

public class BatStoreATMStartController implements Job {
    private RepositoryUtil repositoryUtil = new RepositoryUtil();
    private int deleteSuccess = 0, deleteFail = 0, listSize = 0;
    private String num = "", insertNum = "", fileName = "", errorMessage = "";
    private int updateSuccess = 0, updateFail = 0, updateListSize = 0;
    private ObjectMapper mapper = new ObjectMapper();
    private String bankCode = "";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LogInfoUtil_Batch.LogInfo("==========  店舗ATMマスタデータ自動更新バッチ実施開始  ==========");
        CloudantDBService cloudantDBService = (CloudantDBService) context.getJobDetail().getJobDataMap()
                .get("cloudantDBService");
        bankCode = (String) context.getJobDetail().getJobDataMap().get("bankCode");
        boolean CLOUDANTLOCAL = (boolean) context.getJobDetail().getJobDataMap().get("CLOUDANTLOCAL");
        try {
            cloudantDBService.cloudantOpen();
            CloudantClient cloudantClient = cloudantDBService.getCloudantClient();
            Database db = cloudantClient.database(Constants.DB_NAME, false);
            File directory = new File("");
            Date newDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_APP_DATE);
            fileName = sdf.format(newDate);
            String path = "", masterDataPath = "";
            String base64String = "";
            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
            String now = sdf.format(today);
            String batExecuteDatetime = simpleDateFormat.format(today);
            String name = "";
            if (CLOUDANTLOCAL) {
                masterDataPath = directory.getAbsolutePath().toString().replaceAll("\\\\", "/")
                        + "/src/main/webapp/WEB-INF/StoreAtm_MasterData";
            } else {
                masterDataPath = directory.getAbsolutePath().toString().replaceAll("\\\\", "/")
                        + "/apps/myapp.war/WEB-INF/StoreAtm_MasterData";
            }
            if ("0169".equals(bankCode)) {
                List<MasterDataDoc> countList = new ArrayList<>();
                List<MasterDataDoc> masterDataFileList = repositoryUtil.getView(db,
                        ApplicationKeys.INSIGHTVIEW_MASTERDATA_MASTERDATA, MasterDataDoc.class);
                if (masterDataFileList != null && masterDataFileList.size() != 0) {
                    for (MasterDataDoc storeATMDataDoc : masterDataFileList) {
                        if (storeATMDataDoc.getHopingUseDate().replaceAll("\\/", "").equals(now)
                                && storeATMDataDoc.getBatExecuteDatetime().equals("")
                                && storeATMDataDoc.getUseFlag().equals("0")) {
                            countList.add(storeATMDataDoc);
                            base64String = storeATMDataDoc.getFileStream();
                            name = storeATMDataDoc.getHopingUseDate().replaceAll("\\/", "") + ".txt";
                            storeATMDataDoc.setUseFlag("1");
                            storeATMDataDoc.setBatExecuteDatetime(batExecuteDatetime);
                            repositoryUtil.update(db, storeATMDataDoc);
                            updateSuccess += 1;
                            base64StringToPDF(base64String, masterDataPath, name);
                            if (errorMessage.equals("")) {
                                LogInfoUtil_Batch.LogInfo("更新ファイル：" + masterDataPath);
                                LogInfoUtil_Batch.LogInfo("更新状態：成功");
                            } else {
                                LogInfoUtil_Batch.LogInfo("更新ファイル：" + masterDataPath);
                                LogInfoUtil_Batch.LogInfo("更新状態：失敗:" + "(" + errorMessage + ")");
                            }
                        }
                    }
                }
                updateListSize = countList.size();
                LogInfoUtil_Batch.LogInfo("更新ファイル数：　" + String.valueOf(updateListSize) + "件");
                LogInfoUtil_Batch.LogInfo("更新成功ファイル数：　" + String.valueOf(updateSuccess) + "件");
                LogInfoUtil_Batch.LogInfo("更新失敗ファイル数：　" + String.valueOf(updateFail) + "件");
            }
            if (CLOUDANTLOCAL) {
                // ローカルテスト用
                path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/")
                        + "/src/main/webapp/WEB-INF/StoreAtm_MasterData/" + fileName + ".txt";
            } else {
                // BlueMixサーバ実施用
                path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/")
                        + "/apps/myapp.war/WEB-INF/StoreAtm_MasterData/" + fileName + ".txt";
            }

            File file = new File(path);
            if (file.exists()) {
                LogInfoUtil_Batch.LogInfo("==========  店舗ATMデータ削除開始 ==========  ");
                deleteStoreATMList(db);
                LogInfoUtil_Batch.LogInfo("削除処理件数：　" + String.valueOf(listSize) + "件");
                LogInfoUtil_Batch.LogInfo("削除成功件数：　" + String.valueOf(deleteSuccess) + "件");
                LogInfoUtil_Batch.LogInfo("削除失敗件数：　" + String.valueOf(deleteFail) + "件");
                if (deleteFail != 0) {
                    LogInfoUtil_Batch.LogInfo("削除失敗店番：【" + num + "】");
                }
                LogInfoUtil_Batch.LogInfo("==========  店舗ATMデータ削除完了  ==========");
                try {
                    readFileByLines(path, db);
                } catch (Exception e) {
                    LogInfoUtil_Batch.LogInfo("==========  マスタデータファイル「" + name + "」内容不正、店舗ATMマスタデータ更新失敗  ==========");
                }
            } else {
                LogInfoUtil_Batch.LogInfo("==========  当日店舗ATMマスタ更新データなし  ==========");
            }
        } catch (Exception e) {
            LogInfoUtil_Batch.logWarn(e.getMessage(), e);
        } finally {
            LogInfoUtil_Batch.LogInfo("==========  店舗ATMマスタデータ自動更新バッチ実施完了  ==========");
            cloudantDBService.cloudantClose();
        }
    }

    private void base64StringToPDF(String base64String, String path, String fileName) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            byte[] bytes = Base64.decodeBase64(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            bin = new BufferedInputStream(bais);
            File file = new File(path + "/" + fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fout = new FileOutputStream(file);
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            bout.flush();

        } catch (IOException e) {
            updateFail += 1;
            errorMessage = e.getMessage();
        } finally {
            try {
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteStoreATMList(Database db) {

        if (bankCode.equals("0169")) {
            List<StoreATMInfoDoc> storeATMList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_STOREATMDATALIST_STOREATMDATALIST, false, StoreATMInfoDoc.class, 500);
            if (storeATMList.size() != 0 && storeATMList != null) {
                listSize = listSize + storeATMList.size();
                for (int i = 0; i < storeATMList.size(); i++) {
                    String docId = storeATMList.get(i).get_id();
                    try {
                        repositoryUtil.removeByDocId(db, docId);
                        deleteSuccess += 1;
                    } catch (BusinessException e) {
                        LogInfoUtil_Batch.LogError(e.getMessage());
                        num = num + storeATMList.get(i).getStoreNumber() + storeATMList.get(i).getSubStoreNumber()
                                + storeATMList.get(i).getAtmStoreNumber() + "/";
                        deleteFail += 1;
                    }
                }
                deleteStoreATMList(db);
            }
        } else if (bankCode.equals("0174")) {
            List<IYoStoreATMInfoDoc> iYoStoreATMList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_STOREATMDATALIST_STOREATMDATALIST, false, IYoStoreATMInfoDoc.class,
                    500);
            if (iYoStoreATMList.size() != 0 && iYoStoreATMList != null) {
                listSize = listSize + iYoStoreATMList.size();
                for (int i = 0; i < iYoStoreATMList.size(); i++) {
                    String docId = iYoStoreATMList.get(i).get_id();
                    try {
                        repositoryUtil.removeByDocId(db, docId);
                        deleteSuccess += 1;
                    } catch (BusinessException e) {
                        num = num + iYoStoreATMList.get(i).getStoreNumber() + "/";
                        LogInfoUtil_Batch.LogError(e.getMessage() + num);
                        deleteFail += 1;
                    }
                }
                deleteStoreATMList(db);
            }
        } else if ("0173".equals(bankCode)) {
            List<Store114ATMInfoDoc> storeATMList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_STOREATMDATALIST_STOREATMDATALIST, false, Store114ATMInfoDoc.class,
                    500);
            if (storeATMList != null && storeATMList.size() != 0) {
                listSize = listSize + storeATMList.size();
                for (int i = 0; i < storeATMList.size(); i++) {
                    String docId = storeATMList.get(i).get_id();
                    try {
                        repositoryUtil.removeByDocId(db, docId);
                        deleteSuccess += 1;
                    } catch (BusinessException e) {
                        num = num + storeATMList.get(i).getStoreNumber() + "/";
                        LogInfoUtil_Batch.LogError(e.getMessage() + num);
                        deleteFail += 1;
                    }
                }
                deleteStoreATMList(db);
            }
        }
    }

    public void readFileByLines(String path, Database db) {
        LogInfoUtil_Batch.LogInfo("==========  店舗ATMデータ投入開始  ==========");
        LogInfoUtil_Batch.LogInfo("==========  投入データ：" + fileName + ".txt" + "  ==========");
        File file = new File(path);
        BufferedReader reader = null;
        String encoding = "shift-jis";
        int saveSuccess = 0, saveFail = 0, line = 0;
        String error = "";
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
            reader = new BufferedReader(read);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                if (bankCode.equals("0169")) {
                    StoreATMInfoDoc storeATMInfoDoc = null;
                    try {
                        storeATMInfoDoc = mapper.readValue(tempString, StoreATMInfoDoc.class);
                        repositoryUtil.save(db, storeATMInfoDoc);
                        saveSuccess++;
                    } catch (Exception e) {
                        if (storeATMInfoDoc != null) {
                            insertNum = insertNum + storeATMInfoDoc.getStoreNumber()
                                    + storeATMInfoDoc.getSubStoreNumber() + storeATMInfoDoc.getAtmStoreNumber() + "/";
                        } else {
                            error = e.toString();
                        }
                        saveFail++;
                    }
                    line += 1;
                } else if (bankCode.equals("0174")) {
                    IYoStoreATMInfoDoc storeATMInfoDoc = null;
                    try {
                        storeATMInfoDoc = mapper.readValue(tempString, IYoStoreATMInfoDoc.class);
                        repositoryUtil.save(db, storeATMInfoDoc);
                        saveSuccess++;
                    } catch (Exception e) {
                        if (storeATMInfoDoc != null) {
                            insertNum = insertNum + storeATMInfoDoc.getStoreNumber() + "/";
                        } else {
                            error = e.toString() + "/" + line++;
                        }
                        saveFail++;
                    }
                    line += 1;
                }else if("0173".equals(bankCode)){
                    Store114ATMInfoDoc storeATMInfoDoc = null;
                    try {
                        storeATMInfoDoc = mapper.readValue(tempString, Store114ATMInfoDoc.class);
                        repositoryUtil.save(db, storeATMInfoDoc);
                        saveSuccess++;
                    } catch (Exception e) {
                        if (storeATMInfoDoc != null) {
                            insertNum = insertNum + storeATMInfoDoc.getStoreNumber() + "/";
                        } else {
                            error = e.toString() + "/" + line++;
                        }
                        saveFail++;
                    }
                    line += 1;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        LogInfoUtil_Batch.LogInfo("投入処理件数：　" + String.valueOf(line) + "件");
        LogInfoUtil_Batch.LogInfo("投入成功件数：　" + String.valueOf(saveSuccess) + "件");
        LogInfoUtil_Batch.LogInfo("投入失敗件数：　" + String.valueOf(saveFail) + "件");
        if (!insertNum.equals("")) {
            LogInfoUtil_Batch.LogInfo("投入失敗店番：【" + insertNum.substring(0, insertNum.length() - 1) + "】");
        }
        if (!error.equals("")) {
            LogInfoUtil_Batch.LogInfo(error);
        }
        LogInfoUtil_Batch.LogInfo("==========  店舗ATMデータ投入完了  ==========");
    }
}
