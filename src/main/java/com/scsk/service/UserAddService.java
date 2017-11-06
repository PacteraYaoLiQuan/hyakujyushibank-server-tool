package com.scsk.service;

import java.util.List;

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
import com.scsk.util.ResultMessages;

/**
 * ユーザ追加サービス。<br>
 * <br>
 * ユーザ登録画面からユーザ追加を実装するロジック。<br>
 */
@Service
public class UserAddService extends AbstractBLogic<UserDoc,UserDoc> {
	
    @Autowired
    private RepositoryUtil repositoryUtil;
	
    /**
     *  主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * @param userBean ユーザ情報
     */	
	@Override
	protected void preExecute(UserDoc userBean) {
		//入力するパスワードを取得
        String paraPass = userBean.getPassword();
        //入力するパスワードをMD5で暗号化に変換
//        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
//        String strPass= encoder.encodePassword(paraPass, null).toLowerCase();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(paraPass);
        //メール設定
        userBean.setEmail(userBean.getUserName()+Constants.EMAILEND);
        //暗号化したパスワードを設定
        userBean.setPassword(hashedPassword);
        //利用可否フラグを設定
        userBean.isLockStatus();
        //権限を設定
		if ("1".equals(userBean.getAuthority()))
		{
			userBean.setAuthority(Constants.ROLE_ADMIN);
		}else
		{
			userBean.setAuthority(Constants.ROLE_USER);
		}
	}

    /**
     *  主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * @param client クラウドDBに接続オブジェクト
     * @param userBean ユーザ情報
     * @return userBean ユーザ情報
     */	
	@Override
	protected UserDoc doExecute(CloudantClient client,UserDoc userBean) {
		
		//データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        
    	//追加するユーザがDBに存在するかチェックする
		List<UserDoc> userList = null;
		userList = db.search(ApplicationKeys.INSIGHTINDEX_LOGIN_LOGIN_GETUSERINFO)
		.includeDocs(true)
		.query("email:" + userBean.getEmail(), UserDoc.class);
		//該当ユーザが存在する場合
		if(userList != null && userList.size() != 0){
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1001);
			throw new BusinessException(messages);
		}else{
			//DBに追加
			repositoryUtil.save(db, userBean);
		}

		return userBean;
	}


}


