package com.scsk.batch.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.UpLoadFileDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;
import com.scsk.util.LogInfoUtil_Batch;

public class BatUpdateHtmlPdfController implements Job {
    private RepositoryUtil repositoryUtil = new RepositoryUtil();
    private int updateSuccess = 0, updateFail = 0, listSize = 0;
    private String errorMessage = "";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LogInfoUtil_Batch.LogInfo("==========  PDFとHTMLファイル更新バッチ　実行開始  ==========");
        CloudantDBService cloudantDBService = (CloudantDBService) context.getJobDetail().getJobDataMap()
                .get("cloudantDBService");
        boolean CLOUDANTLOCAL = (boolean) context.getJobDetail().getJobDataMap().get("CLOUDANTLOCAL");
        Database db = null;
        try {
            cloudantDBService.cloudantOpen();
            CloudantClient cloudantClient = cloudantDBService.getCloudantClient();
            db = cloudantClient.database(Constants.DB_NAME, false);
            File directory = new File("");
            String path = "";
            String base64String = "";
            String fileName = "";
            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
            String batExecuteDatetime = simpleDateFormat.format(today);
            List<UpLoadFileDoc> countList = new ArrayList<>();
            List<UpLoadFileDoc> upLoadFileList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_UPLOADFILE_UPLOADFILE, UpLoadFileDoc.class);
            if (upLoadFileList != null && upLoadFileList.size() != 0) {
                for (UpLoadFileDoc upLoadFileDoc : upLoadFileList) {
                    if (upLoadFileDoc.getHopingUseDate().compareTo(batExecuteDatetime.substring(0,16))<=0
                            && upLoadFileDoc.getUseFlag().equals("0")
                            && upLoadFileDoc.getBatExecuteDatetime().equals("")) {
                        countList.add(upLoadFileDoc);
                        List<UpLoadFileDoc> upLoadList = repositoryUtil.getView(db,
                                ApplicationKeys.INSIGHTVIEW_UPLOADFILE_UPLOADFILE, UpLoadFileDoc.class);
                        for (int i = 0; i < upLoadList.size(); i++) {
                            if (upLoadList.get(i).getFileNameJP().equals(upLoadFileDoc.getFileNameJP())
                                    && upLoadList.get(i).getUseFlag().equals("1")
                                    && upLoadList.get(i).getIosORandroid().equals(upLoadFileDoc.getIosORandroid())
                                    && upLoadList.get(i).get_id() != upLoadFileDoc.get_id()) {
                                upLoadList.get(i).setUseFlag("2");
                                repositoryUtil.update(db, upLoadList.get(i));
                            }
                        }
                        base64String = upLoadFileDoc.getFileStream();
                        fileName = upLoadFileDoc.getFileNameEN();
                        upLoadFileDoc.setUseFlag("1");
                        upLoadFileDoc.setBatExecuteDatetime(batExecuteDatetime);
                        repositoryUtil.update(db, upLoadFileDoc);
                        updateSuccess += 1;
                        if (CLOUDANTLOCAL) {
                            path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/") + "/src/main/webapp/"
                                    + upLoadFileDoc.getPath().substring(8, upLoadFileDoc.getPath().length()-1)
                                            .replaceAll("\\\\", "/");
                        } else {
                            path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/") + "/apps/myapp.war/"
                                    + upLoadFileDoc.getPath().substring(8, upLoadFileDoc.getPath().length()-1)
                                            .replaceAll("\\\\", "/");
                        }
                        base64StringToPDF(base64String, path, fileName);
                        if (errorMessage.equals("")) {
                            LogInfoUtil_Batch.LogInfo("更新ファイル：" + path+"/"+fileName);
                            LogInfoUtil_Batch.LogInfo("更新状態：成功");
                        } else {
                            LogInfoUtil_Batch.LogInfo("更新ファイル：" + path+"/"+fileName);
                            LogInfoUtil_Batch.LogInfo("更新状態：失敗" + "(" + errorMessage + ")");
                        }
                    }
                }
                listSize = countList.size();
                LogInfoUtil_Batch.LogInfo("更新ファイル数：　" + String.valueOf(listSize) + "件");
                LogInfoUtil_Batch.LogInfo("更新成功ファイル数：　" + String.valueOf(updateSuccess) + "件");
                LogInfoUtil_Batch.LogInfo("更新失敗ファイル数：　" + String.valueOf(updateFail) + "件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogInfoUtil_Batch.LogInfo("==========  PDFとHTMLファイル更新バッチ　実行完了  ==========");
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
}
