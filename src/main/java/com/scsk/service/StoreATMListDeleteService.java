package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.IYoStoreATMInfoDoc;
import com.scsk.model.Store114ATMInfoDoc;
import com.scsk.model.StoreATMInfoDoc;
import com.scsk.request.vo.StoreATMListDeleteButtonReqVO;
import com.scsk.util.ActionLog;
import com.scsk.util.ResultMessages;

/**
 * 一括削除サービス。<br>
 * <br>
 * 一括削除を実装するロジック。<br>
 */
@Service
public class StoreATMListDeleteService
        extends AbstractBLogic<StoreATMListDeleteButtonReqVO, StoreATMListDeleteButtonReqVO> {
    
    @Value("${bank_cd}")
    private String bank_cd;
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param storeATMListDeleteButtonReqVO
     *            店舗ATM一覧情報
     */
    @Override
    protected void preExecute(StoreATMListDeleteButtonReqVO storeATMListDeleteButtonReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param storeATMListDeleteButtonReqVO
     *            店舗ATM一覧情報
     * @return storeATMListDeleteButtonReqVO 一括削除情報
     */
    @Override
    protected StoreATMListDeleteButtonReqVO doExecute(CloudantClient client,
            StoreATMListDeleteButtonReqVO storeATMListDeleteButtonReqVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        String storeDeleteLog = "(店舗ATM名：";
        for (int i = 0; i < storeATMListDeleteButtonReqVO.getDeleteList().size(); i++) {
            if (storeATMListDeleteButtonReqVO.getDeleteList().get(i).getSelect() == null) {
                continue;
            }
            if (storeATMListDeleteButtonReqVO.getDeleteList().get(i).getSelect() == true) {
                storeDeleteLog = storeDeleteLog + storeATMListDeleteButtonReqVO.getDeleteList().get(i).getStoreATMName()
                        + "/";
                StoreATMInfoDoc storeATMInfoDoc = new StoreATMInfoDoc();
                IYoStoreATMInfoDoc iYoStoreATMInfoDoc=new IYoStoreATMInfoDoc();
                Store114ATMInfoDoc store114atmInfoDoc=new Store114ATMInfoDoc();
                if("0169".equals(bank_cd)){
                    try {
                        // 削除前検索
                        storeATMInfoDoc = (StoreATMInfoDoc) repositoryUtil.find(db,
                                storeATMListDeleteButtonReqVO.getDeleteList().get(i).get_id(), StoreATMInfoDoc.class);
                    } catch (BusinessException e) {
                        // エラーメッセージを出力、処理終了。
                        ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMLIST_1001);
                        throw new BusinessException(messages);
                    }
                    // 店舗情報DBを削除
                    repositoryUtil.removeByDocId(db, storeATMInfoDoc.get_id());
                }else if("0174".equals(bank_cd)){
                    try {
                        // 削除前検索
                        iYoStoreATMInfoDoc = (IYoStoreATMInfoDoc) repositoryUtil.find(db,
                                storeATMListDeleteButtonReqVO.getDeleteList().get(i).get_id(), IYoStoreATMInfoDoc.class);
                    } catch (BusinessException e) {
                        // エラーメッセージを出力、処理終了。
                        ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMLIST_1001);
                        throw new BusinessException(messages);
                    }
                    // 店舗情報DBを削除
                    repositoryUtil.removeByDocId(db, iYoStoreATMInfoDoc.get_id());
                }else if("0173".equals(bank_cd)){
                    try {
                        // 削除前検索
                        store114atmInfoDoc = (Store114ATMInfoDoc) repositoryUtil.find(db,
                                storeATMListDeleteButtonReqVO.getDeleteList().get(i).get_id(), Store114ATMInfoDoc.class);
                    } catch (BusinessException e) {
                        // エラーメッセージを出力、処理終了。
                        ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMLIST_1001);
                        throw new BusinessException(messages);
                    }
                    // 店舗情報DBを削除
                    repositoryUtil.removeByDocId(db, store114atmInfoDoc.get_id());
                }
            }
        }
        storeDeleteLog = storeDeleteLog.substring(0, storeDeleteLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_2 + storeDeleteLog + ")", db);
        return storeATMListDeleteButtonReqVO;
    }

}
