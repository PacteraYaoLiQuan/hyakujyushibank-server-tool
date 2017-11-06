package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.MasterDataDoc;
import com.scsk.request.vo.FileUploadReqVo;
import com.scsk.request.vo.StoreATMDataReqVO;
import com.scsk.response.vo.FileUploadResVo;
import com.scsk.response.vo.StoreATMDataResVO;
import com.scsk.util.ActionLog;
@Service
public class MasterDataUploadService  extends AbstractBLogic<StoreATMDataReqVO, StoreATMDataResVO>{
    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(StoreATMDataReqVO input) throws Exception {
        
    }

    @Override
    protected StoreATMDataResVO doExecute(CloudantClient client, StoreATMDataReqVO input) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String uploadDatetime = simpleDateFormat.format(today);
/*        List<MasterDataDoc> upLoadFileList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_MASTERDATA_MASTERDATA, MasterDataDoc.class);*/
        MasterDataDoc storeATMDataDoc = new MasterDataDoc();
        storeATMDataDoc.setDocType(Constants.DOCTYPE_5);
        storeATMDataDoc.setFileStream(input.getFileStream());
        storeATMDataDoc.setFileName(input.getFileName());
        storeATMDataDoc.setUseFlag("0");
        storeATMDataDoc.setHopingUseDate(input.getHopingUseDate());
        storeATMDataDoc.setUploadDatetime(uploadDatetime);
/*        boolean isUploaded=false;
        if (upLoadFileList != null && upLoadFileList.size() != 0) {
            for (int i = 0; i < upLoadFileList.size(); i++) {
                if (upLoadFileList.get(i).getFileName().equals(input.getFileName())) {
                    upLoadFileList.get(i).setFileStream(input.getFileStream());
                    upLoadFileList.get(i).setUseFlag("0");
                    upLoadFileList.get(i).setHopingUseDate(input.getHopingUseDate());
                    upLoadFileList.get(i).setUploadDatetime(uploadDatetime);
                    isUploaded=true;
                    repositoryUtil.update(db, upLoadFileList.get(i));
                } 
            }
            if(!isUploaded){
                try {
                    repositoryUtil.save(db, storeATMDataDoc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {*/
            try {
                repositoryUtil.save(db, storeATMDataDoc);
                actionLog.saveActionLog(Constants.ACTIONLOG_STORE_ATM_DATA_3, db);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
        return null;
    }

}
