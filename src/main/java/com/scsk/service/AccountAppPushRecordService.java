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
import com.scsk.model.PushrecordDoc;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.response.vo.AccountAppDetailResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.Utils;
import com.scsk.vo.AccountAppPushListVO;

/**
 * Push通知確認画面検索サービス。<br>
 * <br>
 * Push通知確認画面を実装するロジック。<br>
 */
@Service
public class AccountAppPushRecordService extends
		AbstractBLogic<AccountAppDetailReqVO, AccountAppDetailResVO> {

	@Autowired
	private EncryptorUtil encryptorUtil;

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param reqVo
	 *            入力情報
	 */
	@Override
	protected void preExecute(AccountAppDetailReqVO detailReqVO)
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
	protected AccountAppDetailResVO doExecute(CloudantClient client,
			AccountAppDetailReqVO detailReqVO) throws Exception {

		AccountAppDetailResVO applicationDetailResVO = new AccountAppDetailResVO();
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);

		// push通知履歴一覧を取得
		List<PushrecordDoc> pushrecordDocList = new ArrayList<>();

		// push通知履歴
		pushrecordDocList = repositoryUtil.getView(db,
				ApplicationKeys.INSIGHTVIEW_PUSHRECORDLIST_PUSHRECORDLIST,
				PushrecordDoc.class);

		List<AccountAppPushListVO> accountAppDetailPushList = new ArrayList<AccountAppPushListVO>();
		for (PushrecordDoc doc : pushrecordDocList) {
			AccountAppPushListVO pushVo = new AccountAppPushListVO();
			// Push開封状況
			pushVo.setPushStatus(doc.getPushStatus());
			// 配信日付
			pushVo.setPushDate(dateFormat(doc.getPushDate()));
			// 開封日付
			pushVo.setOpenDate(dateFormat(doc.getOpenDate()));
			// 申込処理ステータス
			pushVo.setStatus(doc.getStatus());
			// 申込受付日付
			pushVo.setReceiptDate(doc.getReceiptDate());
			// 氏名
			pushVo.setName(doc.getLastName() + doc.getFirstName());
			// 受付番号
			pushVo.setAccountAppSeq(doc.getAccountAppSeq());
			accountAppDetailPushList.add(pushVo);
		}

		applicationDetailResVO
				.setAccountAppDetailPushList(accountAppDetailPushList);
		return applicationDetailResVO;

	}

	/**
	 * date formartメソッド。
	 * 
	 * @param date
	 *            　処理前日付
	 * @return date 処理後日付
	 * @throws Exception
	 */
	public String dateFormat(String dateInput) {
		String dateOutput = "";
		if (Utils.isNotNullAndEmpty(dateInput) && dateInput.length() > 7) {
			dateOutput = dateInput.substring(0, 4) + "/"
					+ dateInput.substring(4, 6) + "/"
					+ dateInput.substring(6, 8);
		}

		return dateOutput;
	}
}
