package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.scsk.request.vo.PasswordUpdReqVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.SessionUser;

/**
 * 店舗ATMデータ更新メソッド。
 * 
 * @return ResponseEntity　戻るデータオブジェクト
 */
@Service
public class PasswordUpdService extends
		AbstractBLogic<PasswordUpdReqVO, PasswordUpdReqVO> {
	@Autowired
	private RepositoryUtil repositoryUtil;
	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private ActionLog actionLog;
	@Override
	protected void preExecute(PasswordUpdReqVO passwordUpdReqVO)
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
	protected PasswordUpdReqVO doExecute(CloudantClient client,
			PasswordUpdReqVO passwordUpdReqVO) throws Exception {

		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		// 店舗ATM詳細情報取得
		// 検索キーを整理する
		String queryKey = "userID:\""
				+ encryptorUtil.encrypt(SessionUser.userId()) + "\"";
		List<UserDoc> userList = repositoryUtil.getIndex(db,
				ApplicationKeys.INSIGHTINDEX_LOGIN_LOGIN_GETUSERINFO, queryKey,
				UserDoc.class);

		if (!userList.isEmpty() && userList != null) {
			UserDoc userDoc = new UserDoc();
			// 更新前検索
			userDoc = (UserDoc) repositoryUtil.find(db, userList.get(0)
					.get_id(), UserDoc.class);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (passwordEncoder.matches(passwordUpdReqVO.getPassword(),
					userDoc.getPassword()) == false) {
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_LOGIN001_1013);
				throw new BusinessException(messages);
			}

			if (userDoc.getChangePasswordFlg()!= 0) {
				// パスワード変更履歴を取得
				List<PwdHistoryDoc> pwsHistoryList = new ArrayList<>();
				MapReduce view = new MapReduce();
				view.setMap("function (doc) {if(doc.docType && doc.docType === \"PWDHISTORY\" && doc.delFlg && doc.delFlg===\"0\" && doc.userID ===\""
						+ encryptorUtil.encrypt(SessionUser.userId())
						+ "\") {emit(doc.passwordChangeDateTime, 1);}}");
				pwsHistoryList = repositoryUtil.queryByDynamicView(db, view,
						PwdHistoryDoc.class);

				if (pwsHistoryList != null && pwsHistoryList.size() != 0) {
					int index = pwsHistoryList.size() - 1;
					SimpleDateFormat sdf = new SimpleDateFormat(
							Constants.DATE_FORMAT_APP_DATE2);
					String date = sdf.format(new Date());
					String lastChgTime = pwsHistoryList.get(index)
							.getPasswordChangeDateTime().substring(0, 10);
					// １回変更したら１日は変更できないこと
					if (lastChgTime.equals(date.replace("-", "/"))) {
						ResultMessages messages = ResultMessages.error().add(
								MessageKeys.E_LOGIN001_1010);
						actionLog.saveActionLog(Constants.ACTIONLOG_PASSWORD_7, db);
						throw new BusinessException(messages);
					} else {
						for (PwdHistoryDoc doc : pwsHistoryList) {
							BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
							// パスワード再利用は、過去５世代前まで認めないこと
							if (passwordEncoder2.matches(
									passwordUpdReqVO.getNewPassword(),
									doc.getPassword())) {
								ResultMessages messages = ResultMessages
										.error().add(
												MessageKeys.E_LOGIN001_1011);
								actionLog.saveActionLog(Constants.ACTIONLOG_PASSWORD_8, db);
								throw new BusinessException(messages);
							}
						}
					}
				}
				if (pwsHistoryList.size() == 5) {
					repositoryUtil.removeByDocId(db, pwsHistoryList.get(0)
							.get_id());
				}
			}	
			// パスワード
			BCryptPasswordEncoder passwordEncoder3 = new BCryptPasswordEncoder();
			userDoc.setPassword(passwordEncoder3.encode(passwordUpdReqVO
					.getNewPassword()));
			userDoc.setChangePasswordFlg(1);
			SimpleDateFormat sdf = new SimpleDateFormat(
					Constants.DATE_FORMAT_DB);
			String date = sdf.format(new Date());
			userDoc.setEndPasswordChangeDateTime(date);
			// UserDocを更新する
			repositoryUtil.update(db, userDoc);

			// パスワード変更履歴Docを更新する
			PwdHistoryDoc pwsHistoryDoc = new PwdHistoryDoc();
			// ユーザーID
			pwsHistoryDoc
					.setUserID(encryptorUtil.encrypt(SessionUser.userId()));
			// パスワード
			BCryptPasswordEncoder passwordEncoder4 = new BCryptPasswordEncoder();
			pwsHistoryDoc.setPassword(passwordEncoder4.encode(passwordUpdReqVO
					.getNewPassword()));
			// パスワード変更日時
			pwsHistoryDoc.setPasswordChangeDateTime(date);
			// DBに登録
			repositoryUtil.save(db, pwsHistoryDoc);
		} else {
			// メッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_LOGIN001_1001);
			throw new BusinessException(messages);
		}
		actionLog.saveActionLog(Constants.ACTIONLOG_PASSWORD_6, db);
		return passwordUpdReqVO;
	}
}