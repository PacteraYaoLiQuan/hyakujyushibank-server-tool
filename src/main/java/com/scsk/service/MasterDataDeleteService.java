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
import com.scsk.util.ResultMessages;
@Service
public class MasterDataDeleteService extends AbstractBLogic<StoreATMDataResVO, StoreATMDataResVO> {
    @Autowired
    private ActionLog actionLog;
    @Override
    protected void preExecute(StoreATMDataResVO storeATMDataResVO) throws Exception {

    }

    @Override
    protected StoreATMDataResVO doExecute(CloudantClient client, StoreATMDataResVO storeATMDataResVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        for (int i = 0; i < storeATMDataResVO.getFileFindListReqVo().size(); i++) {
            if (storeATMDataResVO.getFileFindListReqVo().get(i).getSelect() == null) {
                continue;
            }
            if (storeATMDataResVO.getFileFindListReqVo().get(i).getSelect() == true) {
                MasterDataDoc storeATMDataDoc = new MasterDataDoc();
                try {
                    // 削除前検索
                    storeATMDataDoc = (MasterDataDoc) repositoryUtil.find(db,
                            storeATMDataResVO.getFileFindListReqVo().get(i).get_id(), MasterDataDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_USERDETAIL_1001);
                    throw new BusinessException(messages);
                }
                try {
                    // ユーザー情報DBを削除
                    repositoryUtil.removeByDocId(db, storeATMDataDoc.get_id());
                    actionLog.saveActionLog(Constants.ACTIONLOG_STORE_ATM_DATA_5, db);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_USER001_1004);
                    throw new BusinessException(messages);
                }

            }
        }
        return storeATMDataResVO;
    }
}
