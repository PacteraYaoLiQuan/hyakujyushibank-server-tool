package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.MasterDataDoc;
import com.scsk.model.UserDoc;
import com.scsk.request.vo.StoreATMDataReqVO;
import com.scsk.response.vo.StoreATMDataResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;

@Service
public class MasterDataListService extends AbstractBLogic<StoreATMDataReqVO, StoreATMDataResVO> {
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(StoreATMDataReqVO input) throws Exception {

    }

    @Override
    protected StoreATMDataResVO doExecute(CloudantClient client, StoreATMDataReqVO input) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);
        List<MasterDataDoc> upLoadFileDocList = new ArrayList<>();
        upLoadFileDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_MASTERDATA_MASTERDATA,
                MasterDataDoc.class);
        // 申込一覧初期データを取得
        StoreATMDataResVO fileUploadResVo = new StoreATMDataResVO();
        List<StoreATMDataReqVO> fileUploadReqVoList = new ArrayList<>();
        if (input.getBatExecuteDatetime().equals("") && input.getUploadDatetime().equals("")) {
            for (MasterDataDoc storeATMDataDoc : upLoadFileDocList) {
                StoreATMDataReqVO storeATMDataReqVO = new StoreATMDataReqVO();
                storeATMDataReqVO.set_id(storeATMDataDoc.get_id());
                // アップロード日時
                storeATMDataReqVO.setUploadDatetime(storeATMDataDoc.getUploadDatetime());
                // 希望利用日
                storeATMDataReqVO.setHopingUseDate(storeATMDataDoc.getHopingUseDate());
                // 利用中フラグ
                storeATMDataReqVO.setUseFlag(storeATMDataDoc.getUseFlag());
                if (storeATMDataDoc.getBatExecuteDatetime() != null
                        && !"".equals(storeATMDataDoc.getBatExecuteDatetime())) {
                    storeATMDataReqVO.setBatExecuteDatetime(storeATMDataDoc.getBatExecuteDatetime());
                } else {
                    storeATMDataReqVO.setBatExecuteDatetime("―");
                }
                storeATMDataReqVO.setFileName(storeATMDataDoc.getFileName());
                storeATMDataReqVO.setPath(storeATMDataDoc.getPath());
                List<UserDoc> userList = new ArrayList<>();
                if (storeATMDataDoc.getCreatedBy().equalsIgnoreCase("admin")) {
                    storeATMDataReqVO.setCreatedBy("Admin");
                } else {
                    String query = "userID:\"" + encryptorUtil.encrypt(storeATMDataDoc.getCreatedBy()) + "\"";
                    userList = repositoryUtil.getIndex(db, ApplicationKeys.INSIGHTINDEX_USER_SEARCHBYUSERID_USERINFO,
                            query, UserDoc.class);
                    if (userList != null && userList.size() > 0) {
                        storeATMDataReqVO.setCreatedBy(encryptorUtil.decrypt(userList.get(0).getUserName()));
                    }
                }
                fileUploadReqVoList.add(storeATMDataReqVO);
            }
        }
        if (!input.getBatExecuteDatetime().equals("") && !input.getUploadDatetime().equals("")) {
            for (MasterDataDoc storeATMDataDoc : upLoadFileDocList) {
                if (!storeATMDataDoc.getBatExecuteDatetime().equals("")) {
                    if (storeATMDataDoc.getBatExecuteDatetime().substring(0, 10)
                            .compareTo(input.getBatExecuteDatetime()) <= 0
                            && storeATMDataDoc.getUploadDatetime().substring(0, 10)
                                    .compareTo(input.getUploadDatetime()) <= 0) {
                        StoreATMDataReqVO storeATMDataReqVO = new StoreATMDataReqVO();
                        storeATMDataReqVO.set_id(storeATMDataDoc.get_id());
                        // アップロード日時
                        storeATMDataReqVO.setUploadDatetime(storeATMDataDoc.getUploadDatetime());
                        // 希望利用日
                        storeATMDataReqVO.setHopingUseDate(storeATMDataDoc.getHopingUseDate());
                        // 利用中フラグ
                        storeATMDataReqVO.setUseFlag(storeATMDataDoc.getUseFlag());
                        if (storeATMDataDoc.getBatExecuteDatetime() != null
                                && !"".equals(storeATMDataDoc.getBatExecuteDatetime())) {
                            storeATMDataReqVO.setBatExecuteDatetime(storeATMDataDoc.getBatExecuteDatetime());
                        } else {
                            storeATMDataReqVO.setBatExecuteDatetime("―");
                        }
                        storeATMDataReqVO.setFileName(storeATMDataDoc.getFileName());
                        storeATMDataReqVO.setPath(storeATMDataDoc.getPath());
                        List<UserDoc> userList = new ArrayList<>();
                        if (storeATMDataDoc.getCreatedBy().equalsIgnoreCase("admin")) {
                            storeATMDataReqVO.setCreatedBy("Admin");
                        } else {
                            String query = "userID:\"" + encryptorUtil.encrypt(storeATMDataDoc.getCreatedBy()) + "\"";
                            userList = repositoryUtil.getIndex(db,
                                    ApplicationKeys.INSIGHTINDEX_USER_SEARCHBYUSERID_USERINFO, query, UserDoc.class);
                            if (userList != null && userList.size() > 0) {
                                storeATMDataReqVO.setCreatedBy(encryptorUtil.decrypt(userList.get(0).getUserName()));
                            }
                        }
                        fileUploadReqVoList.add(storeATMDataReqVO);
                    }
                }
            }
        }
        if (!input.getBatExecuteDatetime().equals("") && input.getUploadDatetime().equals("")) {
            for (MasterDataDoc storeATMDataDoc : upLoadFileDocList) {
                if (!storeATMDataDoc.getBatExecuteDatetime().equals("")) {
                    if (storeATMDataDoc.getBatExecuteDatetime().substring(0, 10)
                            .compareTo(input.getBatExecuteDatetime()) <= 0) {
                        StoreATMDataReqVO storeATMDataReqVO = new StoreATMDataReqVO();
                        storeATMDataReqVO.set_id(storeATMDataDoc.get_id());
                        // アップロード日時
                        storeATMDataReqVO.setUploadDatetime(storeATMDataDoc.getUploadDatetime());
                        // 希望利用日
                        storeATMDataReqVO.setHopingUseDate(storeATMDataDoc.getHopingUseDate());
                        // 利用中フラグ
                        storeATMDataReqVO.setUseFlag(storeATMDataDoc.getUseFlag());
                        if (storeATMDataDoc.getBatExecuteDatetime() != null
                                && !"".equals(storeATMDataDoc.getBatExecuteDatetime())) {
                            storeATMDataReqVO.setBatExecuteDatetime(storeATMDataDoc.getBatExecuteDatetime());
                        } else {
                            storeATMDataReqVO.setBatExecuteDatetime("―");
                        }
                        storeATMDataReqVO.setFileName(storeATMDataDoc.getFileName());
                        storeATMDataReqVO.setPath(storeATMDataDoc.getPath());
                        List<UserDoc> userList = new ArrayList<>();
                        if (storeATMDataDoc.getCreatedBy().equalsIgnoreCase("admin")) {
                            storeATMDataReqVO.setCreatedBy("Admin");
                        } else {
                            String query = "userID:\"" + encryptorUtil.encrypt(storeATMDataDoc.getCreatedBy()) + "\"";
                            userList = repositoryUtil.getIndex(db,
                                    ApplicationKeys.INSIGHTINDEX_USER_SEARCHBYUSERID_USERINFO, query, UserDoc.class);
                            if (userList != null && userList.size() > 0) {
                                storeATMDataReqVO.setCreatedBy(encryptorUtil.decrypt(userList.get(0).getUserName()));
                            }
                        }
                        fileUploadReqVoList.add(storeATMDataReqVO);
                    }
                }
            }
        }
        if (input.getBatExecuteDatetime().equals("") && !input.getUploadDatetime().equals("")) {
            for (MasterDataDoc storeATMDataDoc : upLoadFileDocList) {
                if (storeATMDataDoc.getUploadDatetime().substring(0, 10).compareTo(input.getUploadDatetime()) <= 0) {
                    StoreATMDataReqVO storeATMDataReqVO = new StoreATMDataReqVO();
                    storeATMDataReqVO.set_id(storeATMDataDoc.get_id());
                    // アップロード日時
                    storeATMDataReqVO.setUploadDatetime(storeATMDataDoc.getUploadDatetime());
                    // 希望利用日
                    storeATMDataReqVO.setHopingUseDate(storeATMDataDoc.getHopingUseDate());
                    // 利用中フラグ
                    storeATMDataReqVO.setUseFlag(storeATMDataDoc.getUseFlag());
                    if (storeATMDataDoc.getBatExecuteDatetime() != null
                            && !"".equals(storeATMDataDoc.getBatExecuteDatetime())) {
                        storeATMDataReqVO.setBatExecuteDatetime(storeATMDataDoc.getBatExecuteDatetime());
                    } else {
                        storeATMDataReqVO.setBatExecuteDatetime("―");
                    }
                    storeATMDataReqVO.setFileName(storeATMDataDoc.getFileName());
                    storeATMDataReqVO.setPath(storeATMDataDoc.getPath());
                    List<UserDoc> userList = new ArrayList<>();
                    if (storeATMDataDoc.getCreatedBy().equalsIgnoreCase("admin")) {
                        storeATMDataReqVO.setCreatedBy("Admin");
                    } else {
                        String query = "userID:\"" + encryptorUtil.encrypt(storeATMDataDoc.getCreatedBy()) + "\"";
                        userList = repositoryUtil.getIndex(db,
                                ApplicationKeys.INSIGHTINDEX_USER_SEARCHBYUSERID_USERINFO, query, UserDoc.class);
                        if (userList != null && userList.size() > 0) {
                            storeATMDataReqVO.setCreatedBy(encryptorUtil.decrypt(userList.get(0).getUserName()));
                        }
                    }
                    fileUploadReqVoList.add(storeATMDataReqVO);
                }
            }
        }
        fileUploadResVo.setFileFindListReqVo(fileUploadReqVoList);
        actionLog.saveActionLog(Constants.ACTIONLOG_STORE_ATM_DATA_2, db);
        return fileUploadResVo;
    }

}
