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
import com.scsk.model.AuthorityDoc;
import com.scsk.model.FunctionDoc;
import com.scsk.response.vo.AuthorityInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.Utils;
import com.scsk.vo.AuthorityInitVO;

/**
 * 権限一覧初期化検索サービス。<br>
 * <br>
 * 権限一覧初期化検索を実装するロジック。<br>
 */
@Service
public class AuthorityListInitService extends
		AbstractBLogic<AuthorityInitResVO, AuthorityInitResVO> {

    @Autowired
    private ActionLog actionLog;
	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param なし
	 *            検索条件
	 */
	@Override
	protected void preExecute(AuthorityInitResVO ResVO) throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param なし
	 *            検索条件
	 * @return authorityInitResVO 権限一覧情報
	 */
	@Override
	protected AuthorityInitResVO doExecute(CloudantClient client,
			AuthorityInitResVO ResVO) throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);

		// 権限一覧初期データを取得
		List<AuthorityDoc> authorityDocList = new ArrayList<>();
		List<AuthorityInitVO> authorityList = new ArrayList<>();
		AuthorityInitResVO authorityInitResVO = new AuthorityInitResVO();
		List<FunctionDoc> functionDoc = new ArrayList<>();

		authorityDocList = repositoryUtil.getView(db,
				ApplicationKeys.INSIGHTVIEW_AUTHORITYLIST_AUTHORITYLIST,
				AuthorityDoc.class);
		functionDoc = repositoryUtil.getView(db,
				ApplicationKeys.INSIGHTVIEW_FUNCTIONLIST_FUNCTIONLIST,
				FunctionDoc.class);

		if (authorityDocList != null && authorityDocList.size() != 0) {
			for (AuthorityDoc doc : authorityDocList) {
				AuthorityInitVO authorityInitVO = new AuthorityInitVO();
				// 権限初期化用データを戻る
				authorityInitVO.set_id(doc.get_id());
				authorityInitVO.set_rev(doc.get_rev());
				authorityInitVO.setAuthorityName(doc.getAuthorityName());
				String reference = "";
				String management = "";
				for (int count = 0; count < doc.getFunctionValueList().size(); count++) {
					if (doc.getFunctionValueList().get(count).equals("1")) {
						for (int count2 = 0; count2 < functionDoc.size(); count2++) {
							if (functionDoc.get(count2).getFunctionID()
									.equals(doc.getFunctionIDList().get(count))) {
								reference = reference
										+ functionDoc.get(count2)
												.getFunctionName() + "\r\n";
							}
						}

					} else if (doc.getFunctionValueList().get(count)
							.equals("2")) {
						for (int count3 = 0; count3 < functionDoc.size(); count3++) {
							if (functionDoc.get(count3).getFunctionID()
									.equals(doc.getFunctionIDList().get(count))) {
								management = management
										+ functionDoc.get(count3)
												.getFunctionName() + "\r\n";
							}
						}
					}
				}

				int length1 = reference.length();
				int length2 = management.length();
				if (Utils.isNotNullAndEmpty(reference)) {
					authorityInitVO.setReference(reference.substring(0,
							length1 - 1));
				}
				if (Utils.isNotNullAndEmpty(management)) {
					authorityInitVO.setManagement(management.substring(0,
							length2 - 1));
				}

				authorityList.add(authorityInitVO);
			}
		}

		authorityInitResVO.setAuthorityList(authorityList);
		actionLog.saveActionLog(Constants.ACTIONLOG_AUTHORITY_1, db);
		return authorityInitResVO;
	}

}
