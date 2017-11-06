package com.scsk.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.AccountAppDoc;
import com.scsk.response.vo.AccountAppInitResVO;
import com.scsk.vo.AccountAppInitVO;

/**
 * 申込一覧検索サービス。<br>
 * <br>
 * 申込一覧検索を実装するロジック。<br>
 */
@Service
public class WebListInitSampleService extends
		AbstractBLogic<AccountAppInitResVO, AccountAppInitResVO> {

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param resVo
	 *            一覧情報
	 */
	@Override
	protected void preExecute(AccountAppInitResVO resVo) {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param resVo
	 *            一覧情報
	 * @return resVo 一覧情報
	 * @throws IOException
	 * @throws Exception
	 */
	@Override
	protected AccountAppInitResVO doExecute(CloudantClient client,
			AccountAppInitResVO resVo) {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		AccountAppInitResVO resultVo = new AccountAppInitResVO();
		List<AccountAppInitVO> voList = new ArrayList<AccountAppInitVO>();
		List<AccountAppDoc> docList = new ArrayList<AccountAppDoc>();
		// 一覧情報を検索する
		docList = repositoryUtil.getView(db,
				ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST,
				AccountAppDoc.class);
		// 検索結果が存在する場合
		if (docList != null && docList.size() != 0) {
			for (AccountAppDoc doc : docList) {
				AccountAppInitVO vo = new AccountAppInitVO();
				BeanUtils.copyProperties(doc, vo);
				voList.add(vo);
			}
			resultVo.setAccountAppList(voList);
		}
		return resultVo;
	}

}
