package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.UserDoc;
import com.scsk.request.vo.UserListLogOutputReqVO;
import com.scsk.response.vo.UserListCsvOutputResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.UserInitVO;

/**
 * CSV一覧検索サービス。<br>
 * <br>
 * CSV一覧検索を実装するロジック。<br>
 */
@Service
public class UserListCsvService extends AbstractBLogic<UserListLogOutputReqVO, UserListCsvOutputResVO> {

    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param userListLogOutputReqVO
     * 
     */
    @Override
    protected void preExecute(UserListLogOutputReqVO userListLogOutputReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param userListLogOutputReqVO
     * 
     * @return userListLogOutputReqVO CSV出力用情報
     */
    @Override
    protected UserListCsvOutputResVO doExecute(CloudantClient client, UserListLogOutputReqVO userListLogOutputReqVO)
            throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);

        // 出力用データを取得
        UserListCsvOutputResVO userListCsvOutputResVO = new UserListCsvOutputResVO();
        List<UserInitVO> userListCsvOutput = new ArrayList<>();
        String userListOutputLog = "（ユーザーID：";
        for (int i = 0; i < userListLogOutputReqVO.getLogOutputList().size(); i++) {
            if (userListLogOutputReqVO.getLogOutputList().get(i).getSelect() == null) {
                continue;
            }
            // 一覧選択したデータ
            if (userListLogOutputReqVO.getLogOutputList().get(i).getSelect() == true) {
                    userListOutputLog = userListOutputLog + userListLogOutputReqVO.getLogOutputList().get(i).getUserID()
                            + "/";
                UserDoc userDoc = new UserDoc();
                try {
                    // ユーザーデータを取得
                    userDoc = (UserDoc) repositoryUtil.find(db,
                            userListLogOutputReqVO.getLogOutputList().get(i).get_id(), UserDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
                    throw new BusinessException(messages);
                }
                if (userDoc != null) {
                    UserInitVO userInitVO = new UserInitVO();
                    // CSV出力用データを戻る
                    userInitVO.set_id(userDoc.get_id());
                    userInitVO.set_rev(userDoc.get_rev());

                    // userID
                    userInitVO.setUserID(encryptorUtil.decrypt(userDoc.getUserID()));
                    // ユーザー名
                    userInitVO.setUserName(encryptorUtil.decrypt(userDoc.getUserName()));
                    // 権限
                    userInitVO.setAuthority(userDoc.getAuthority());
                    // メールアドレス
                    userInitVO.setEmail(encryptorUtil.decrypt(userDoc.getEmail()));
                    // ロック状態
                    userInitVO.setLockStatus(userDoc.isLockStatus());
                    // ログイン状態
                    userInitVO.setLoginStatus(userDoc.getLoginStatus());
                    // 最終ログイン日時
                    userInitVO.setEndLoginDateTime(userDoc.getEndLoginDateTime());
                    userListCsvOutput.add(userInitVO);
                }
            }
        }
        userListOutputLog=userListOutputLog.substring(0, userListOutputLog.length()-1);
        userListCsvOutputResVO.setUserListCsvOutput(userListCsvOutput);
        actionLog.saveActionLog(Constants.ACTIONLOG_USERLIST_3 + userListOutputLog + ")", db);
        return userListCsvOutputResVO;
    }
}
