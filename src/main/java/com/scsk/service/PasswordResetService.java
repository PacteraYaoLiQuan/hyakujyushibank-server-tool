package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.DesignDocument.MapReduce;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.PwdHistoryDoc;
import com.scsk.model.UserDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.PasswordResetReqVO;
import com.scsk.response.vo.PasswordResetResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.SendMail;

/**
 * パスワードをリセットメソッド。
 * 
 * @return ResponseEntity 戻るデータオブジェクト
 */
@Service
public class PasswordResetService extends 
    AbstractBLogic<PasswordResetReqVO, PasswordResetResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private SendMail sendMail;
    @Autowired
    private ActionLog actionLog;
    @Override
    protected void preExecute(PasswordResetReqVO passwordResetReqVO)
            throws Exception {
        
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
    protected PasswordResetResVO doExecute(CloudantClient client, 
            PasswordResetReqVO passwordResetReqVO) throws Exception {

        PasswordResetResVO output = new PasswordResetResVO();
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);

        List<UserDoc> userList = new ArrayList<>();
        UserDoc userDoc = new UserDoc();
        String passwordResetLog="(メールアドレス：";
        try {
            // 検索キーを整理する
            String queryKey = "userID:\"" + encryptorUtil.encrypt(passwordResetReqVO.getUserID()) + "\"";
            // ユーザー詳細情報取得
            userList = repositoryUtil.getIndex(db,ApplicationKeys.INSIGHTINDEX_LOGIN_LOGIN_GETUSERINFO,queryKey, UserDoc.class);
            
            if(userList != null && userList.size() > 0){
                userDoc = (UserDoc) repositoryUtil.find(db, userList.get(0).get_id(), UserDoc.class);
                output.setEmail(encryptorUtil.decrypt(userDoc.getEmail()));
                output.setUserID(passwordResetReqVO.getUserID());
                passwordResetLog=passwordResetLog +encryptorUtil.decrypt(userDoc.getEmail());
            } else {
                return output;
            }
            // ユーザー詳細情報取得
        } catch (BusinessException e) {
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(
                    MessageKeys.E_USERDETAIL_1001);
            throw new BusinessException(messages);
        }

        // ユーザーID
        userDoc.setUserID(encryptorUtil.encrypt(passwordResetReqVO
                .getUserID()));
        // ユーザー名
        userDoc.setUserName(encryptorUtil.encrypt(passwordResetReqVO
                .getUserName()));
        // パスワード
        String sPWD = RandomStringUtils.randomAlphanumeric(10);
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        userDoc.setPassword(bcrypt.encode(sPWD));
        // パスワード種別
        userDoc.setChangePasswordFlg(0);
        // メールアドレス
        userDoc.setEmail(encryptorUtil.encrypt(passwordResetReqVO
                .getEmail()));
        // DBに更新
        try {
            repositoryUtil.update(db, userDoc);
        } catch (BusinessException e) {
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(
                    MessageKeys.E_USERDETAIL_1005);
            throw new BusinessException(messages);
        }
        // パスワード変更履歴を取得
        List<PwdHistoryDoc> pwsHistoryList = new ArrayList<>();
        MapReduce view = new MapReduce();
        view.setMap("function (doc) {if(doc.docType && doc.docType === \"PWDHISTORY\" && doc.delFlg && doc.delFlg===\"0\" && doc.userID===\""
                + passwordResetReqVO.getUserID()
                + "\") {emit(doc.passwordChangeDateTime, 1);}}");
        pwsHistoryList = repositoryUtil.queryByDynamicView(db, view,
                PwdHistoryDoc.class);
        if (pwsHistoryList != null && pwsHistoryList.size() != 0) {
            for (PwdHistoryDoc doc : pwsHistoryList) {
                // パスワード変更履歴Docを削除する
                repositoryUtil.removeByDocId(db, doc.getUserID());
            }
        }
        actionLog.saveActionLog(Constants.ACTIONLOG_PASSWORD_5+passwordResetLog+")", passwordResetReqVO.getUserID());
        // リセットメールアドレスに初期化パスワードを通知
        sendMail.sendMailUserResetPassword(
                passwordResetReqVO.getUserName(),
                passwordResetReqVO.getEmail(), sPWD,passwordResetReqVO.getUserID());
        return output;
    }
}

