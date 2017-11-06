package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.UserDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.UserDetailUpdateReqVO;
import com.scsk.response.vo.UserDetailUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.SendMail;

/**
 * ユーザーデータ更新メソッド。
 * 
 * @return ResponseEntity 戻るデータオブジェクト
 */
@Service
public class UserDetailUpdService extends AbstractBLogic<UserDetailUpdateReqVO, UserDetailUpdateResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private SendMail sendMail;
    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(UserDetailUpdateReqVO userDetailUpdateReqVO) throws Exception {

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
    protected UserDetailUpdateResVO doExecute(CloudantClient client, UserDetailUpdateReqVO userDetailUpdateReqVO)
            throws Exception {

        UserDetailUpdateResVO output = new UserDetailUpdateResVO();
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        List<UserDoc> userList = new ArrayList<>();
        List<UserDoc> userInfoDocList = new ArrayList<>();
        UserDoc userDoc = new UserDoc();
        // userID単一チェック
        if (userDetailUpdateReqVO.getModeType().equals("1")) {
            userInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_USERLIST_USERLIST, UserDoc.class);
            for (UserDoc userDoc1 : userInfoDocList) {
                if (userDetailUpdateReqVO.getUserID().equalsIgnoreCase(encryptorUtil.decrypt(userDoc1.getUserID()))) {
                    userList.add(userDoc1);
                    break;
                }
            }

            if (userList != null && userList.size() != 0) {
                // メッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_USERDETAIL_1004);
                throw new BusinessException(messages);
            }
        } else if(userDetailUpdateReqVO.getModeType().equals("2")){

            // // パスワード変更履歴を取得
            // List<PwdHistoryDoc> pwsHistoryList = new ArrayList<>();
            // MapReduce view = new MapReduce();
            // view.setMap("function (doc) {if(doc.docType && doc.docType ===
            // \"PWDHISTORY\" && doc.delFlg && doc.delFlg===\"0\" &&
            // doc.userName===\""
            // + userDetailUpdateReqVO.getUserName()
            // + "\") {emit(doc.passwordChangeDateTime, 1);}}");
            // pwsHistoryList = repositoryUtil.queryByDynamicView(db, view,
            // PwdHistoryDoc.class);
            //
            // if (pwsHistoryList != null && pwsHistoryList.size() != 0) {
            // int index = pwsHistoryList.size() - 1;
            // SimpleDateFormat sdf = new SimpleDateFormat(
            // Constants.DATE_FORMAT_YMD);
            // String date = sdf.format(new Date());
            // String lastChgTime = pwsHistoryList.get(index)
            // .getPasswordChangeDateTime().substring(0, 10);
            // // １回変更したら１日は変更できないこと
            // if (lastChgTime.equals(date)) {
            // ResultMessages messages = ResultMessages.error().add(
            // MessageKeys.I_USER_1002);
            // throw new BusinessException(messages);
            // } else {
            // for (PwdHistoryDoc doc : pwsHistoryList) {
            // // パスワード再利用は、過去５世代前まで認めないこと
            // if (userDetailUpdateReqVO.getPassword().equals(
            // doc.getPassword())) {
            // ResultMessages messages = ResultMessages.error()
            // .add(MessageKeys.I_USER_1003);
            // throw new BusinessException(messages);
            // }
            // }
            // }
            //
            // }
            // if (pwsHistoryList.size() == 5) {
            // repositoryUtil
            // .removeByDocId(db, pwsHistoryList.get(0).get_id());
            // }
            try {
                // ユーザー詳細情報取得
                userDoc = (UserDoc) repositoryUtil.find(db, userDetailUpdateReqVO.get_id(), UserDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_USERDETAIL_1001);
                throw new BusinessException(messages);
            }
        }
        // ユーザーID
        userDoc.setUserID(encryptorUtil.encrypt(userDetailUpdateReqVO.getUserID()));
        // ユーザー名
        userDoc.setUserName(encryptorUtil.encrypt(userDetailUpdateReqVO.getUserName()));

        // メールアドレス
        userDoc.setEmail(encryptorUtil.encrypt(userDetailUpdateReqVO.getEmail()));
        // 所属権限名
        userDoc.setAuthority(userDetailUpdateReqVO.getAuthority());

        // ロック状態
        userDoc.setLockStatus(userDetailUpdateReqVO.isLockStatus());
        String sPWD = RandomStringUtils.randomAlphanumeric(10);
        if (userDetailUpdateReqVO.getModeType().equals("1")) {
            // パスワード
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            userDoc.setPassword(bcrypt.encode(sPWD));
            // パスワード種別
            userDoc.setChangePasswordFlg(0);// システム自動初期化パスワード
            // ログイン状態
            userDoc.setLoginStatus(0);
        }

        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        userDoc.setEndPasswordChangeDateTime(date);
        // DBに更新
        if (userDetailUpdateReqVO.getModeType().equals("2")) {
            try {
                String userDetailUpdLog = "（ユーザーID：";
                userDetailUpdLog = userDetailUpdLog + encryptorUtil.decrypt(userDoc.getUserID());
                repositoryUtil.update(db, userDoc);
                actionLog.saveActionLog(Constants.ACTIONLOG_USERLIST_8 + userDetailUpdLog + ")", db);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_USERDETAIL_1003);
                throw new BusinessException(messages);
            }

        } else {
            try {
                String userDetailSaveLog = "（ユーザーID：";
                userDetailSaveLog = userDetailSaveLog + encryptorUtil.decrypt(userDoc.getUserID());
                repositoryUtil.save(db, userDoc);
                actionLog.saveActionLog(Constants.ACTIONLOG_USERLIST_6 + userDetailSaveLog + ")", db);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_USERDETAIL_1002);
                throw new BusinessException(messages);
            }

        }
        // パスワード変更履歴Docを更新する
        // PwdHistoryDoc pwsHistoryDoc = new PwdHistoryDoc();
        // // ユーザーID
        // pwsHistoryDoc.setUserID(encryptorUtil.encrypt(userDetailUpdateReqVO.getUserID()));
        // // ユーザー名
        // pwsHistoryDoc.setUserName(encryptorUtil.encrypt(userDetailUpdateReqVO
        // .getUserName()));
        // // パスワード
        // pwsHistoryDoc.setPassword(PasswordUtils
        // .passwordEncode(userDetailUpdateReqVO.getPassword()));
        // // パスワード変更日時
        // pwsHistoryDoc.setPasswordChangeDateTime(date);
        // // DBに登録
        // repositoryUtil.save(db, pwsHistoryDoc);
        if (userDetailUpdateReqVO.getModeType().equals("1")) {
            // 登録メールアドレスに初期化パスワードを通知
            sendMail.sendMailForNewPassword(userDetailUpdateReqVO.getUserName(), userDetailUpdateReqVO.getEmail(), sPWD,
                    userDetailUpdateReqVO.getUserID());
        }
        return output;
    }

}
