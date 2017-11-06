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
import com.scsk.model.PushrecordDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.request.vo.PushRecordAppListSendReqVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PushNotifications;
import com.scsk.util.ResultMessages;
import com.scsk.vo.AccountAppPushListVO;

@Service
public class PushRecordAppRefuseService extends AbstractBLogic<PushRecordAppListSendReqVO, PushRecordAppListSendReqVO> {

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
        if ("0169".equals(bank_cd)) {
            for (AccountAppPushListVO accountAppPushListVO : ReqVO.getSendList()) {
                if (accountAppPushListVO.getSelect() == null) {
                    continue;
                }
                flag = "0";
                selectCount++;
                PushrecordDoc pushrecordDoc = new PushrecordDoc();
                String statusStr = "";
                try {
                    // push通知履歴
                    if (accountAppPushListVO.getSelect() == true) {
                        pushrecordDoc = (PushrecordDoc) repositoryUtil.find(db, accountAppPushListVO.get_id(),
                                PushrecordDoc.class);
                        if ("3".equals(pushrecordDoc.getStatus())) {
                            statusStr = "完了";
                        } else if ("4".equals(pushrecordDoc.getStatus())) {
                            statusStr = "却下（総合的判断）";
                        } else if ("5".equals(pushrecordDoc.getStatus())) {
                            statusStr = "却下（本人確認連絡不可）";
                        } else if ("6".equals(pushrecordDoc.getStatus())) {
                            statusStr = "却下（郵便受取不可）";
                        } else if ("7".equals(pushrecordDoc.getStatus())) {
                            statusStr = "却下（内容不備）";
                        } else if ("8".equals(pushrecordDoc.getStatus())) {
                            statusStr = "却下（本人申出）";
                        }
                    }
                } catch (BusinessException e) {
                    count++;
                    flag = "1";
                }
                if (!"3".equals(pushrecordDoc.getPushStatus()) && !"4".equals(pushrecordDoc.getPushStatus())) {
                    pushrecordDoc.setPushStatus("5");
                    pushrecordDoc.setPushDate("");
                    pushrecordDoc.setSortTime("");
                } else if ("4".equals(pushrecordDoc.getPushStatus())) {
                    pushrecordDoc.setPushStatus("6");
                    pushrecordDoc.setPushDate("");
                    pushrecordDoc.setSortTime("");
                } else if ("3".equals(pushrecordDoc.getPushStatus())) {
                    pushrecordDoc.setPushStatus("7");
                    pushrecordDoc.setPushDate("");
                    pushrecordDoc.setSortTime("");
                }
                try {
                    // push通知履歴DBを更新
                    repositoryUtil.update(db, pushrecordDoc);
                } catch (BusinessException e) {
                    if (!"1".equals(flag)) {
                        count++;
                    }
                }
                String pushStatus = "";
                if ("5".equals(pushrecordDoc.getPushStatus())) {
                    pushStatus = "承認取下げ";
                } else if ("6".equals(pushrecordDoc.getPushStatus())) {
                    pushStatus = "承認取下げ（端末IDなし、配信不可）";
                } else if ("7".equals(pushrecordDoc.getPushStatus())) {
                    pushStatus = "承認取下げ（配信エラー）";
                }
                pushRecordAppRefuseLog = pushRecordAppRefuseLog + "【受付番号：" + pushrecordDoc.getAccountAppSeq()
                        + "/ステータス：" + statusStr + "/取下げ理由：" + pushStatus + "】";
                if (count == selectCount) {
                    // 一括承認取り下げ失敗しました。
                    ReqVO.setErrFlag("0");
                } else if (count == 0) {
                    // 一括承認取り下げ成功しました。
                    ReqVO.setErrFlag("1");
                } else {
                    // 一括承認取り下げ失敗しました。（承認取り下げ失敗あり）
                    ReqVO.setErrFlag("2");
                }
            }
        } else if ("0122".equals(bank_cd)) {
            yamagataRefuse(db, ReqVO);
        } else if ("0173".equals(bank_cd)) {
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
    public void yamagataRefuse(Database db, PushRecordAppListSendReqVO ReqVO) {
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
