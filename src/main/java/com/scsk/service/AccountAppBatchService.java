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
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountAppImageDoc;
import com.scsk.model.AccountSumDoc;
import com.scsk.model.UserInfoDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppListButtonReqVO;
import com.scsk.response.vo.AccountAppListButtonResVO;

/**
 * 口座開設データ、自動で一括削除サービス。<br>
 * <br>
 * 設定期間を経過したステータスが完了の申込データについて自動で一括削除するロジック。<br>
 * 
 * @param <I>
 */
@Service
public class AccountAppBatchService extends
		AbstractBLogic<AccountAppListButtonReqVO, AccountAppListButtonResVO> {
	@Autowired
	private RepositoryUtil repositoryUtil;

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param accountAppListButtonReqVO
	 *            申込一覧情報
	 */
	@Override
	protected void preExecute(
			AccountAppListButtonReqVO accountAppListButtonReqVO)
			throws Exception {
	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param delDate
	 *            削除比較用日付
	 * @return なし
	 */
	@Override
	protected AccountAppListButtonResVO doExecute(CloudantClient client,
			AccountAppListButtonReqVO accountAppListButtonReqVO)
			throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);

		AccountAppListButtonResVO accountAppListButtonResVO = new AccountAppListButtonResVO();
		List<AccountAppDoc> accountAppDocList = new ArrayList<>();

		// AccountAppDoc更新前検索
		MapReduce view = new MapReduce();
		view.setMap("function (doc) {if(doc.docType && doc.docType === \"ACCOUNTAPPLICATION\" && doc.delFlg && doc.delFlg===\"0\" && doc.status===\"3\") {emit(doc._id, 1);}}");
		accountAppDocList = repositoryUtil.queryByDynamicView(db, view,
				AccountAppDoc.class);

		// 当日日付を取得する（日付フォーマット）
		SimpleDateFormat sdf1 = new SimpleDateFormat(
				Constants.DATE_FORMAT_APP_DATE);
		String date1 = sdf1.format(new Date());

		if (accountAppDocList != null && accountAppDocList.size() != 0) {
			for (AccountAppDoc doc : accountAppDocList) {
				String delDate = "";
				delDate = doc.getUpdatedDate().substring(0, 4)
						+ doc.getUpdatedDate().substring(5, 7)
						+ doc.getUpdatedDate().substring(8, 10);
				if (date1.compareTo(delDate) > 0) {
					if (doc.getUserType() == 1) {
						// ユーザー情報DOCを更新(口座申込数を加算)
						List<UserInfoDoc> userInfoDoc = new ArrayList<>();
						// UserInfoDoc更新前検索
						MapReduce view2 = new MapReduce();
						view2.setMap("function (doc) {if(doc.docType && doc.docType === \"USERINFO\" && doc.delFlg && doc.delFlg===\"0\" && doc.userId===\""
								+ doc.getUserId() + "\") {emit(doc._id, 1);}}");
						userInfoDoc = repositoryUtil.queryByDynamicView(db,
								view2, UserInfoDoc.class);
						// UserInfoDoc更新
						if (userInfoDoc != null && userInfoDoc.size() != 0) {
							for (UserInfoDoc doc2 : userInfoDoc) {
								doc2.setAccountAppCount(doc2
										.getAccountAppCount() + 1);
								repositoryUtil.update(db, doc2);
							}
						}
					} else {
						// 匿名ユーザー口座集計DOCを更新(口座申込数を加算)
						List<AccountSumDoc> accountSum = new ArrayList<>();
						accountSum = repositoryUtil
								.getView(
										db,
										ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPBATCH_ACCOUNTSUM,
										AccountSumDoc.class);
						if (accountSum != null && accountSum.size() != 0) {
							for (AccountSumDoc doc2 : accountSum) {
								doc2.setAccountAppCount(doc2
										.getAccountAppCount() + 1);
								repositoryUtil.update(db, doc2);
							}
						} else {
							// 匿名ユーザー口座集計DOCを作成
							AccountSumDoc accountSumDoc = new AccountSumDoc();
							accountSumDoc.setDocType(Constants.DOCTYPE_2);
							accountSumDoc.setAccountAppCount(1);
							repositoryUtil.save(db, accountSumDoc);
						}
					}
					// 削除口座開設DOC処理
					repositoryUtil.removeByDocId(db, doc.get_id());

					// 申込画像削除前検索
					AccountAppImageDoc accountAppImageDoc = new AccountAppImageDoc();
					accountAppImageDoc = repositoryUtil.find(db,
							doc.getIdentificationImage(),
							AccountAppImageDoc.class);
					// 削除申込画像DOC処理
					repositoryUtil.removeByDocId(db,
							accountAppImageDoc.get_id());
				}
			}
		}

		return accountAppListButtonResVO;
	}
}
