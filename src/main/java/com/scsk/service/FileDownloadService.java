package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.UpLoadFileDoc;
import com.scsk.request.vo.FileUploadReqVo;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
@Service
public class FileDownloadService extends  AbstractBLogic<FileUploadReqVo, FileUploadReqVo>{
    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(FileUploadReqVo input) throws Exception {
        
    }

    @Override
    protected FileUploadReqVo doExecute(CloudantClient client, FileUploadReqVo input) throws Exception {

        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        // 出力用データを取得
        FileUploadReqVo fileUploadReqVo = new FileUploadReqVo();
        UpLoadFileDoc upLoadFileDoc = new UpLoadFileDoc();
        try {
            upLoadFileDoc = (UpLoadFileDoc) repositoryUtil.find(db, input.get_id(), UpLoadFileDoc.class);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.W_MASTERDATA_1001);
            throw new BusinessException(messages);
        }
        fileUploadReqVo.setFileStream(upLoadFileDoc.getFileStream());
        fileUploadReqVo.setFileName(upLoadFileDoc.getFileName());
        fileUploadReqVo.setIosORandroid(upLoadFileDoc.getIosORandroid());
        actionLog.saveActionLog(Constants.ACTIONLOG_HTML_PDF_FILE_4, db);
        return fileUploadReqVo;

    }

}
