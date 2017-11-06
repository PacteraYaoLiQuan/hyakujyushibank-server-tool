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
import com.cloudant.client.api.model.DesignDocument.MapReduce;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.PwdHistoryDoc;
import com.scsk.model.UserDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.UserPasswordResetReqVO;
import com.scsk.response.vo.UserPasswordResetResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.SendMail;

/**
 * ユーザーパスワードをリセットメソッド。
 * 
 * @return ResponseEntity　戻るデータオブジェクト
 */
@Service
public class UserPasswordResetService extends
		AbstractBLogic<UserPasswordResetReqVO, UserPasswordResetResVO> {
	@Autowired
	private RepositoryUtil repositoryUtil;
	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private SendMail sendMail;
	@Autowired
	private ActionLog actionLog;

	@Override
	protected void preExecute(UserPasswordResetReqVO userPasswordResetReqVO)
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
	protected UserPasswordResetResVO doExecute(CloudantClient client,
			UserPasswordResetReqVO userPasswordResetReqVO) throws Exception {

		UserPasswordResetResVO output = new UserPasswordResetResVO();
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		String passwordResetLog="（ユーザーID：";
		UserDoc userDoc = new UserDoc();

		try {
			// ユーザー詳細情報取得
			userDoc = (UserDoc) repositoryUtil.find(db,
					userPasswordResetReqVO.get_id(), UserDoc.class);
			passwordResetLog=passwordResetLog+encryptorUtil.decrypt(userDoc.getUserID());
		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.warning().add(
					MessageKeys.E_USERDETAIL_1001);
			throw new BusinessException(messages);
		}

		// ユーザーID
		userDoc.setUserID(encryptorUtil.encrypt(userPasswordResetReqVO
				.getUserID()));
		// ユーザー名
		userDoc.setUserName(encryptorUtil.encrypt(userPasswordResetReqVO
				.getUserName()));
		// パスワード
		String sPWD = RandomStringUtils.randomAlphanumeric(10);
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		userDoc.setPassword(bcrypt.encode(sPWD));
		// パスワード種別
		userDoc.setChangePasswordFlg(0);
		// メールアドレス
		userDoc.setEmail(encryptorUtil.encrypt(userPasswordResetReqVO
				.getEmail()));
		// 所属権限名
		userDoc.setAuthority(userPasswordResetReqVO.getAuthority());
		// ログイン状態
		userDoc.setLoginStatus(0);
		// ロック状態
		userDoc.setLockStatus(false);
		// システム日時を取得
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
		String date = sdf.format(new Date());
		userDoc.setEndPasswordChangeDateTime(date);
		// DBに更新
		try {
			repositoryUtil.update(db, userDoc);
		} catch (BusinessException e) {
			// e.printStackTrace();
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
				+ userPasswordResetReqVO.getUserID()
				+ "\") {emit(doc.passwordChangeDateTime, 1);}}");
		pwsHistoryList = repositoryUtil.queryByDynamicView(db, view,
				PwdHistoryDoc.class);
		if (pwsHistoryList != null && pwsHistoryList.size() != 0) {

			for (PwdHistoryDoc doc : pwsHistoryList) {
				// パスワード変更履歴Docを削除する
				repositoryUtil.removeByDocId(db, doc.get_id());
			}

		}
		actionLog.saveActionLog(Constants.ACTIONLOG_USERLIST_9+passwordResetLog+")", db);
		// 登録メールアドレスに初期化パスワードを通知
		sendMail.sendMailUserResetPassword(
				userPasswordResetReqVO.getUserName(),
				userPasswordResetReqVO.getEmail(), sPWD,userPasswordResetReqVO.getUserID());
		return output;
	}

}
