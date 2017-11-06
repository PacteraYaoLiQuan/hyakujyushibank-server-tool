package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.scsk.request.vo.PasswordResetReqVO;
import com.scsk.response.vo.PasswordResetResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * パスワードをリセットメソッド。
 * 
 * @return ResponseEntity 戻るデータオブジェクト
 */
@Service
public class PasswordResetCheckService extends 
    AbstractBLogic<PasswordResetReqVO, PasswordResetResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
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
        List<UserDoc> userInfoDocList = new ArrayList<>();
        UserDoc userDoc = new UserDoc();
        try {
          
            userInfoDocList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_USERLIST_USERLIST, UserDoc.class);
            for (UserDoc userDoc1 :userInfoDocList) {
                if (passwordResetReqVO.getUserID().equalsIgnoreCase(encryptorUtil.decrypt(userDoc1.getUserID()))) {
                    userList.add(userDoc1);
                    break;
                }
            }
//            // 検索キーを整理する
//            String queryKey = "userID:\"" + encryptorUtil.encrypt(passwordResetReqVO.getUserID()) + "\"";
//            // ユーザー詳細情報取得
//            userList = repositoryUtil.getIndex(db,ApplicationKeys.INSIGHTINDEX_LOGIN_LOGIN_GETUSERINFO,queryKey, UserDoc.class);
            if  (userList != null && userList.size() > 0) {
                userDoc = (UserDoc) repositoryUtil.find(db, userList.get(0).get_id(), UserDoc.class);
                output.setEmail(encryptorUtil.decrypt(userDoc.getEmail()));
                output.setUserName(encryptorUtil.decrypt(userDoc.getUserName()));
                output.setUserID(encryptorUtil.decrypt(userDoc.getUserID()));
                output.setShowUserID(passwordResetReqVO.getUserID());
            }
            // ユーザー詳細情報取得
        } catch (BusinessException e) {
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(
                    MessageKeys.E_USERDETAIL_1001);
            throw new BusinessException(messages);
        }
        actionLog.saveActionLog(Constants.ACTIONLOG_PASSWORD_4, passwordResetReqVO.getUserID());
        return output;
    }
}

