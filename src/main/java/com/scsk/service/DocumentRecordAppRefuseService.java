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
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.request.vo.PushRecordAppListSendReqVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.AccountAppPushListVO;
@Service
public class DocumentRecordAppRefuseService extends AbstractBLogic<PushRecordAppListSendReqVO, PushRecordAppListSendReqVO>{


    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(PushRecordAppListSendReqVO pushRecordAppListSendReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param reqVo
     *            入力情報
     * @return resVo 詳細情報
     * @throws Exception
     */
    @Override
    protected PushRecordAppListSendReqVO doExecute(CloudantClient client, PushRecordAppListSendReqVO ReqVO)
            throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        String pushRecordAppRefuseLog = "";
        int count = 0;
        int selectCount = 0;
        String flag = "";
        if ("0173".equals(bank_cd)) {
            refuse114(db, ReqVO);
        }

        actionLog.saveActionLog(Constants.ACTIONLOG_PUSHPRECORD_5 + pushRecordAppRefuseLog);
        return ReqVO;
    }

    /**
     * PUSH通知を送信する。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param statusList
     *            ステータス
     * @return void
     * @throws Exception
     */
    public void refuse114(Database db, PushRecordAppListSendReqVO ReqVO) {
        for (AccountAppPushListVO accountAppPushListVO : ReqVO.getSendList()) {
            if (accountAppPushListVO.getSelect() == null) {
                continue;
            }
            if (accountAppPushListVO.getSelect() == true) {
                YamagataStatusModifyDoc yamagataStatusModifyDoc = new YamagataStatusModifyDoc();
                try {
                    yamagataStatusModifyDoc = repositoryUtil.find(db, accountAppPushListVO.get_id(),
                            YamagataStatusModifyDoc.class);
                } catch (Exception e) {
                }
                yamagataStatusModifyDoc.setSendStatus("5");
                try {
                    repositoryUtil.update(db, yamagataStatusModifyDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
                    throw new BusinessException(messages);
                }
            }
        }
    }

}
