package com.scsk.service;

import java.util.ArrayList;
import java.util.Collections;
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
import com.scsk.model.IYoUserInfoDoc;
import com.scsk.response.vo.UseUserListInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.UseUserListInitVO;

/**
 * ファイル一覧初期化検索サービス。<br>
 * <br>
 * ファイル一覧初期化検索を実装するロジック。<br>
 */
@Service
public class UseUserDownloadListInitService extends AbstractBLogic<UseUserListInitResVO, UseUserListInitResVO> {

	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private ActionLog actionLog;

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param なし
	 *            検索条件
	 */
	@Override
	protected void preExecute(UseUserListInitResVO ResVO) throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param なし
	 *            検索条件
	 */
	@Override
	protected UseUserListInitResVO doExecute(CloudantClient client, UseUserListInitResVO ResVO) throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);

		// ファイル一覧初期データを取得
		List<IYoUserInfoDoc> useUserInfoDocList = new ArrayList<>();
		List<UseUserListInitVO> useUserList = new ArrayList<>();
		UseUserListInitResVO useUserListInitResVO = new UseUserListInitResVO();

		try {
			useUserInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_IYOUSERINFOLIST_USERINFOLIST,
					IYoUserInfoDoc.class);
			if (useUserInfoDocList != null && useUserInfoDocList.size() != 0) {
				for (IYoUserInfoDoc doc : useUserInfoDocList) {
					UseUserListInitVO useUserListInitVO = new UseUserListInitVO();
					// ファイル一覧初期化用データを戻る
					useUserListInitVO.set_id(doc.get_id());
					useUserListInitVO.set_rev(doc.get_rev());
					if ("0".equals(doc.getUserType())) {
						useUserListInitVO.setUserKey("0");
					} else if ("1".equals(doc.getUserType())) {
						useUserListInitVO.setUserKey("1");
					} else if ("2".equals(doc.getUserType())) {
						useUserListInitVO.setUserKey("2");
					} else if ("4".equals(doc.getUserType())) {
						useUserListInitVO.setUserKey("4");
					} else if ("3".equals(doc.getUserType())) {
						useUserListInitVO.setUserKey("3");
					}
					// メールアドレス
					if ("0".equals(doc.getUserType())) {
						useUserListInitVO.setEmail("");
					} else if ("1".equals(doc.getUserType())) {
						useUserListInitVO.setEmail(encryptorUtil.decrypt(doc.getEmail()));
					} else if ("2".equals(doc.getUserType())) {
						useUserListInitVO.setEmail(encryptorUtil.decrypt(doc.getFacebookId()));
					} else if ("4".equals(doc.getUserType())) {
						useUserListInitVO.setEmail(encryptorUtil.decrypt(doc.getGoogleId()));
					} else if ("3".equals(doc.getUserType())) {
						useUserListInitVO.setEmail(encryptorUtil.decrypt(doc.getYahooId()));
					}
					useUserListInitVO.setLastReqTime(doc.getLastReqTime());

					if (doc.getCardNoList().size() != 0 && doc.getCardNoList() != null) {
						String accountNumber = "";
						for (int i = 0; i < doc.getCardNoList().size(); i++) {
							accountNumber = accountNumber + encryptorUtil.decrypt(doc.getCardNoList().get(i).getStoreName()) + "/" +
									encryptorUtil.decrypt(doc.getCardNoList().get(i).getKamokuName()) + "/" +
									encryptorUtil.decrypt(doc.getCardNoList().get(i).getAccountNumber()) + "/" +
									encryptorUtil.decrypt(doc.getCardNoList().get(i).getAccountName()) + "\r\n";
						}
//						String storeName = "";
//						for (int i = 0; i < doc.getCardNoList().size(); i++) {
//							storeName = storeName + encryptorUtil.decrypt(doc.getCardNoList().get(i).getStoreName())
//									+ "\r\n";
//						}
//						useUserListInitVO.setStoreName(storeName);
//						String kamokuName = "";
//						for (int i = 0; i < doc.getCardNoList().size(); i++) {
//							kamokuName = kamokuName + encryptorUtil.decrypt(doc.getCardNoList().get(i).getKamokuName())
//									+ "\r\n";
//						}
//						useUserListInitVO.setKamokuName(kamokuName);
//						String accountNumber = "";
//						for (int i = 0; i < doc.getCardNoList().size(); i++) {
//							accountNumber = accountNumber
//									+ encryptorUtil.decrypt(doc.getCardNoList().get(i).getAccountNumber()) + "\r\n";
//						}
						useUserListInitVO.setAccountNumber(accountNumber);

					}
					useUserList.add(useUserListInitVO);
				}
			}
		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSHMESSAGE_1004);
			throw new BusinessException(messages);
		}

		// ファイル一覧初期データを取得
		useUserListInitResVO.setUseUserList(useUserList);
		actionLog.saveActionLog(Constants.ACTIONLOG_DOWNLOAD_1);
		return useUserListInitResVO;
	}
}
