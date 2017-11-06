package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.DesignDocument.MapReduce;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.PwdHistoryDoc;
import com.scsk.model.UserDoc;
import com.scsk.request.vo.UserListDeleteReqVO;
import com.scsk.util.ActionLog;
import com.scsk.util.ResultMessages;

/**
 * ユーザー一括削除サービス。<br>
 * <br>
 * ユーザー 一括削除を実装するロジック。<br>
 */
@Service
public class UserListDeleteService extends AbstractBLogic<UserListDeleteReqVO, UserListDeleteReqVO> {
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param UserListDeleteReqVO
     *            ユーザー一覧情報
     */
    @Override
    protected void preExecute(UserListDeleteReqVO UserListDeleteReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param UserListDeleteReqVO
     *            ユーザー一覧情報
     * @return UserListDeleteReqVO 一括削除情報
     */
    @Override
    protected UserListDeleteReqVO doExecute(CloudantClient client, UserListDeleteReqVO userListDeleteReqVO)
            throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        String userListDeleteLog = "（ユーザーID：";
        for (int i = 0; i < userListDeleteReqVO.getDeleteList().size(); i++) {
            if (userListDeleteReqVO.getDeleteList().get(i).getSelect() == null) {
                continue;
            }
            if (userListDeleteReqVO.getDeleteList().get(i).getSelect() == true) {
                userListDeleteLog = userListDeleteLog + userListDeleteReqVO.getDeleteList().get(i).getUserID() + "/";
                UserDoc userDoc = new UserDoc();
                try {
                    // 削除前検索
                    userDoc = (UserDoc) repositoryUtil.find(db, userListDeleteReqVO.getDeleteList().get(i).get_id(),
                            UserDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_USERDETAIL_1001);
                    throw new BusinessException(messages);
                }
                try {
                    // ユーザー情報DBを削除
                    repositoryUtil.removeByDocId(db, userDoc.get_id());
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_USER001_1004);
                    throw new BusinessException(messages);
                }
                // パスワード変更履歴を取得
                List<PwdHistoryDoc> pwsHistoryList = new ArrayList<>();
                MapReduce view = new MapReduce();
                view.setMap(
                        "function (doc) {if(doc.docType && doc.docType === \"PWDHISTORY\" && doc.delFlg && doc.delFlg===\"0\" && doc.userID===\""
                                + userDoc.getUserID() + "\") {emit(doc.passwordChangeDateTime, 1);}}");
                pwsHistoryList = repositoryUtil.queryByDynamicView(db, view, PwdHistoryDoc.class);
                if (pwsHistoryList != null && pwsHistoryList.size() != 0) {

                    for (PwdHistoryDoc doc : pwsHistoryList) {
                        // パスワード変更履歴Docを削除する
                        try {
                            repositoryUtil.removeByDocId(db, doc.get_id());
                        } catch (BusinessException e) {
                            // エラーメッセージを出力、処理終了。
                            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_USERDETAIL_1006);
                            throw new BusinessException(messages);
                        }
                    }

                }
            }
        }
        userListDeleteLog = userListDeleteLog.substring(0, userListDeleteLog.length()-1);
        actionLog.saveActionLog(Constants.ACTIONLOG_USERLIST_2 + userListDeleteLog + ")", db);
        return userListDeleteReqVO;
    }

}
