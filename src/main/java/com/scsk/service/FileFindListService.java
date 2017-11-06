package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.UpLoadFileDoc;
import com.scsk.model.UserDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.FileUploadReqVo;
import com.scsk.response.vo.FileUploadResVo;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;

@Service
public class FileFindListService extends AbstractBLogic<FileUploadReqVo, FileUploadResVo> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;
    @Override
    protected void preExecute(FileUploadReqVo input) throws Exception {

    }

    @Override
    protected FileUploadResVo doExecute(CloudantClient client, FileUploadReqVo input) throws Exception {
        
        Database db = client.database(Constants.DB_NAME, false);
        List<UpLoadFileDoc> upLoadFileDocList = new ArrayList<>();
        upLoadFileDocList = repositoryUtil.getView(db,ApplicationKeys.INSIGHTVIEW_UPLOADFILE_UPLOADFILE,UpLoadFileDoc.class);
        // 申込一覧初期データを取得
        FileUploadResVo fileUploadResVo = new FileUploadResVo();
        List<FileUploadReqVo> fileUploadReqVoList = new ArrayList<>();
        for (UpLoadFileDoc upLoadFileDoc : upLoadFileDocList) {
            if (upLoadFileDoc.getIosORandroid().equals(input.getIosORandroid())
                    && upLoadFileDoc.getFileNameJP().equals(input.getFileNameJP())) {
                FileUploadReqVo fileUploadReqVo = new FileUploadReqVo();
                fileUploadReqVo.set_id(upLoadFileDoc.get_id());
                if ("0".equals(upLoadFileDoc.getIosORandroid())) {
                    fileUploadReqVo.setIosORandroid("iOS");
                } else {
                    fileUploadReqVo.setIosORandroid("Android");
                }
                // アップロード日時
                fileUploadReqVo.setUploadDatetime(upLoadFileDoc.getUploadDatetime());
                // 希望利用日
                fileUploadReqVo.setHopingUseDate(upLoadFileDoc.getHopingUseDate());
                // 利用中フラグ
                fileUploadReqVo.setUseFlag(upLoadFileDoc.getUseFlag());
                if (upLoadFileDoc.getBatExecuteDatetime() != null && !"".equals(upLoadFileDoc.getBatExecuteDatetime() )) {
                    fileUploadReqVo.setBatExecuteDatetime(upLoadFileDoc.getBatExecuteDatetime());
                } else {
                    fileUploadReqVo.setBatExecuteDatetime("―");
                }
                fileUploadReqVo.setFileNameJP(upLoadFileDoc.getFileNameJP());
                fileUploadReqVo.setFileNameEN(upLoadFileDoc.getFileNameEN());
                fileUploadReqVo.setPath(upLoadFileDoc.getPath());
                List<UserDoc> userList = new ArrayList<>();
                if ("admin".equalsIgnoreCase(upLoadFileDoc.getCreatedBy())) {
                    fileUploadReqVo.setCreatedBy("Admin");
                } else {
                    String query = "userID:\""
                            + encryptorUtil.encrypt(upLoadFileDoc.getCreatedBy()) + "\"";
                    userList = repositoryUtil.getIndex(db,
                            ApplicationKeys.INSIGHTINDEX_USER_SEARCHBYUSERID_USERINFO,
                            query, UserDoc.class);
                    if (userList != null && userList.size() > 0) {
                        fileUploadReqVo.setCreatedBy(encryptorUtil.decrypt(userList.get(0).getUserName()));
                    }
                }
                fileUploadReqVoList.add(fileUploadReqVo);
            }
        }
        fileUploadResVo.setFileFindListReqVo(fileUploadReqVoList);
        actionLog.saveActionLog(Constants.ACTIONLOG_HTML_PDF_FILE_2, db);
        return fileUploadResVo;
    }
}