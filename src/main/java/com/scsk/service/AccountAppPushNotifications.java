package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.DesignDocument.MapReduce;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.AccountAppDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.model.UserInfoDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppPushNotificationsReqVO;
import com.scsk.response.vo.AccountAppPushNotificationsResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PushNotifications;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;

/**
 * 申込詳細データ更新メソッド。
 * 
 * @return ResponseEntity　戻るデータオブジェクト
 */
@Service
public class AccountAppPushNotifications
		extends
		AbstractBLogic<AccountAppPushNotificationsReqVO, AccountAppPushNotificationsResVO> {
	@Autowired
	private RepositoryUtil repositoryUtil;
	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private PushNotifications pushNotifications;

	@Override
	protected void preExecute(
			AccountAppPushNotificationsReqVO accountAppPushNotificationsReqVO)
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
	protected AccountAppPushNotificationsResVO doExecute(CloudantClient client,
			AccountAppPushNotificationsReqVO accountAppPushNotificationsReqVO)
			throws Exception {

		AccountAppPushNotificationsResVO output = new AccountAppPushNotificationsResVO();
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		AccountAppDoc applicationDoc = new AccountAppDoc();
		// 申込詳細情報取得
		try {
			applicationDoc = (AccountAppDoc) repositoryUtil.find(db,
					accountAppPushNotificationsReqVO.get_id(), AccountAppDoc.class);
		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNTAPPDETAIL_1001);
			throw new BusinessException(messages);
		}

		PushrecordDoc pushrecordDoc = pushNotifications(client, applicationDoc,
				applicationDoc.getStatus());
		// 戻る値を設定
		output.set_id(applicationDoc.get_id());
		output.setPushStatus(pushrecordDoc.getPushStatus());

		return output;
	}

	/**
	 * PUSH通知を送信する。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param AccountAppDoc
	 *            口座開設取得結果
	 * @param status
	 *            　ステータス
	 * 
	 * @return void
	 */
	public PushrecordDoc pushNotifications(CloudantClient client,
			AccountAppDoc doc, String status) throws Exception {
		Database db = client.database(Constants.DB_NAME, false);

		PushrecordDoc pushrecordDoc = new PushrecordDoc();
		// 配信履歴DBに追加
		// システム日時を取得
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String date = sdf.format(new Date());
		SimpleDateFormat dateFormart = new SimpleDateFormat(
				Constants.DATE_FORMAT_ASS);
		String pushDate = dateFormart.format(new Date());
		pushrecordDoc.setDocType(Constants.DOCTYPE_1);

		List<String> deviceTokenIdList = new ArrayList<String>();

		int sum = 1;
		// 配信履歴Doc件数検索
		List<PushrecordDoc> pushrecordDocSUM = new ArrayList<>();
		MapReduce viewSUM = new MapReduce();

		viewSUM.setMap("function (doc) {if(doc.docType && doc.docType === \"PUSHRECORD\" && doc.delFlg && doc.delFlg===\"0\" && doc.pushStatus ===\"1\" && doc.userId===\""
				+ doc.getUserId() + "\") {emit(doc._id, 1);}}");

		pushrecordDocSUM = repositoryUtil.queryByDynamicView(db, viewSUM,
				PushrecordDoc.class);

		if (pushrecordDocSUM != null && pushrecordDocSUM.size() != 0) {
			sum = pushrecordDocSUM.size() + 1;
		}
		String receiptDate = dateFormatJP(doc.getReceiptDate());
		String message = "";
		String applyInformation = Constants.RECEIPT_DATE + receiptDate
				+ Constants.ACCOUNT_SEQ + doc.getAccountAppSeq()
				+ Constants.APPLY_KIND;
		switch (status) {
		case "1":
			message = Constants.PUSH_MESSAGE_STATUS_1 + applyInformation
					+ Constants.PUSH_MESSAGE_ABOUT;
			break;
		case "2":
			message = Constants.PUSH_MESSAGE_STATUS_2 + applyInformation
					+ Constants.PUSH_MESSAGE_ABOUT;
			break;
		case "3":
			message = Constants.PUSH_MESSAGE_STATUS_3
					+ Constants.PUSH_MESSAGE_ABOUT;
			break;
		case "4":
			message = Constants.PUSH_MESSAGE_STATUS_4 + applyInformation
					+ Constants.PUSH_MESSAGE_ABOUT;
			break;
		case "5":
			message = Constants.PUSH_MESSAGE_STATUS_5 + applyInformation
					+ Constants.PUSH_MESSAGE_ABOUT;
			break;
		case "6":
			message = Constants.PUSH_MESSAGE_STATUS_6 + applyInformation
					+ Constants.PUSH_MESSAGE_ABOUT;
			break;
		default:
			break;
		}

		// 臨時ユーザの場合、単一アプリを送信する
		if (doc.getUserType() == 0) {
			// PUSH通知を送信する
			List<String> list = new ArrayList<String>();

			list.add(encryptorUtil.decrypt(doc.getDeviceTokenId()));
			String messageCode = pushNotifications.sendMessage(message, sum,
					list);
			pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
			pushrecordDoc.setUserId(doc.getUserId());
			List<String> tokenId=new ArrayList<String>();
			tokenId.add(doc.getDeviceTokenId());
			pushrecordDoc.setDeviceTokenId(tokenId);
			pushrecordDoc.setPushDate(pushDate);
			pushrecordDoc.setOpenDate("");
			pushrecordDoc.setSaveDate(date);
			pushrecordDoc.setPushContent(message);
			pushrecordDoc.setStatus(status);
			if (messageCode.equals(Constants.RETURN_OK)) {
				// 未開封
				pushrecordDoc.setPushStatus("1");
			} else {
				// 配信失敗
				pushrecordDoc.setPushStatus("3");
			}
			repositoryUtil.save(db, pushrecordDoc);

		} else {
			// 正式ユーザの場合、複数アプリを送信する
			List<UserInfoDoc> userInfoDoc = new ArrayList<>();
			// 検索キーを整理する
			String queryKey = "userId:\"" + doc.getUserId() + "\"";

			userInfoDoc = repositoryUtil
					.getIndex(
							db,
							ApplicationKeys.INSIGHTINDEX_ACCOUNTMNT_SEARCHBYUSERID_USERINFO,
							queryKey, UserInfoDoc.class);
			if (userInfoDoc != null && userInfoDoc.size() != 0) {
				deviceTokenIdList = userInfoDoc.get(0).getDeviceTokenIdList();
			}

			if (deviceTokenIdList.size() == 0 || deviceTokenIdList.isEmpty()) {
				pushrecordDoc.setDocType(Constants.DOCTYPE_1);
				pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
				pushrecordDoc.setUserId(doc.getUserId());
				pushrecordDoc.setSaveDate(date);
				pushrecordDoc.setPushContent("");
				pushrecordDoc.setPushStatus("4");
				pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
				pushrecordDoc.setStatus(status);
				repositoryUtil.save(db, pushrecordDoc);
			} else {

				// PUSH通知を送信する
				List<String> list = new ArrayList<String>();
				for (int count = 0; count < deviceTokenIdList.size(); count++) {
					list.add(encryptorUtil.decrypt(deviceTokenIdList.get(count)));
				}

				String messageCode = pushNotifications.sendMessage(message,
						sum, list);

				pushrecordDoc.setDocType(Constants.DOCTYPE_1);
				pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
				pushrecordDoc.setUserId(doc.getUserId());
				pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
				pushrecordDoc.setPushDate(pushDate);
				pushrecordDoc.setOpenDate("");
				pushrecordDoc.setSaveDate(date);
				pushrecordDoc.setPushContent(message);
				pushrecordDoc.setStatus(status);
				if (messageCode.equals(Constants.RETURN_OK)) {
					// 未開封
					pushrecordDoc.setPushStatus("1");
				} else {
					// 配信失敗
					pushrecordDoc.setPushStatus("3");
				}
				repositoryUtil.save(db, pushrecordDoc);

			}
		}
		return pushrecordDoc;
	}

	/**
	 * date formartメソッド。
	 * 
	 * @param date
	 *            　処理前日付
	 * @return date 処理後日付
	 * @throws Exception
	 */
	public String dateFormatJP(String dateInput) {
		String dateOutput = "";
		if (Utils.isNotNullAndEmpty(dateInput) && dateInput.length() > 7) {
			dateOutput = dateInput.substring(0, 4) + Constants.YEAR
					+ dateInput.substring(5, 7) + Constants.MONTH_JP
					+ dateInput.substring(8, 10) + Constants.DAY + " "
					+ dateInput.substring(11,16);
		}

		return dateOutput;
	}
}
