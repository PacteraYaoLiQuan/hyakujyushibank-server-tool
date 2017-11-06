package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.MasterDataDoc;
import com.scsk.response.vo.StoreATMDataResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

@Service
public class MasterDataDownloadService extends AbstractBLogic<StoreATMDataResVO, StoreATMDataResVO> {
    @Autowired
    private ActionLog actionLog;
    @Override
    protected void preExecute(StoreATMDataResVO input) throws Exception {

    }

    @Override
    protected StoreATMDataResVO doExecute(CloudantClient client, StoreATMDataResVO input) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        // 出力用データを取得
        StoreATMDataResVO storeATMDataResVO = new StoreATMDataResVO();
        MasterDataDoc storeATMDataDoc = new MasterDataDoc();
        try {
            storeATMDataDoc = (MasterDataDoc) repositoryUtil.find(db, input.get_id(), MasterDataDoc.class);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.W_MASTERDATA_1001);
            throw new BusinessException(messages);
        }
        storeATMDataResVO.setFileStream(storeATMDataDoc.getFileStream());
        storeATMDataResVO.setFileName(storeATMDataDoc.getFileName());
        actionLog.saveActionLog(Constants.ACTIONLOG_STORE_ATM_DATA_4, db);
        return storeATMDataResVO;

    }

}
