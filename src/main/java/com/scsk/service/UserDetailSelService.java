package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.UserDoc;
import com.scsk.request.vo.UserDetailReqVO;
import com.scsk.response.vo.UserDetailResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * ユーザー詳細検索サービス。<br>
 * <br>
 * ユーザー詳細検索を実装するロジック。<br>
 */
@Service
public class UserDetailSelService extends
		AbstractBLogic<UserDetailReqVO, UserDetailResVO> {
	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private ActionLog actionLog;
	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param reqVo
	 *            入力情報
	 */
	@Override
	protected void preExecute(UserDetailReqVO detailReqVO) throws Exception {

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
	protected UserDetailResVO doExecute(CloudantClient client,
			UserDetailReqVO detailReqVO) throws Exception {
	    String userDetailLog="（ユーザーID：";
		UserDetailResVO userDetailResVO = new UserDetailResVO();
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		// ユーザー詳細情報取得
		UserDoc userDoc = new UserDoc();
		try {
			userDoc = (UserDoc) repositoryUtil.find(db, detailReqVO.get_id(),
					UserDoc.class);
			userDetailLog=userDetailLog+encryptorUtil.decrypt(userDoc.getUserID());
		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.warning().add(
					MessageKeys.E_USERDETAIL_1001);
			throw new BusinessException(messages);
		}

		// 戻り値を設定
		 userDetailResVO.setUserID(encryptorUtil.decrypt(userDoc.getUserID()));
		// ユーザー名
		 userDetailResVO
		 .setUserName(encryptorUtil.decrypt(userDoc.getUserName()));
		// パスワード種別
		userDetailResVO.setPasswordType(userDoc.getChangePasswordFlg());
		// メールアドレス
		userDetailResVO.setEmail(encryptorUtil.decrypt(userDoc.getEmail()));
		// 所属権限名
		userDetailResVO.setAuthority(userDoc.getAuthority());
		// ログイン状態
		userDetailResVO.setLoginStatus(userDoc.getLoginStatus());
		// 最終ログイン日時
		userDetailResVO.setEndLoginDateTime(userDoc.getEndLoginDateTime());
		// 最終パスワード変更日時
		userDetailResVO.setEndPasswordChangeDateTime(userDoc
				.getEndPasswordChangeDateTime());
		// ロック状態
		userDetailResVO.setLockStatus(userDoc.isLockStatus());
		actionLog.saveActionLog(Constants.ACTIONLOG_USERLIST_7+userDetailLog+")", db);
		return userDetailResVO;

	}
}
