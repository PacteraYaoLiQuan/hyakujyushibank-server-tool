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
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.FunctionDoc;
import com.scsk.model.UserDoc;
import com.scsk.request.vo.AuthorityListDeleteButtonReqVO;
import com.scsk.util.ActionLog;
import com.scsk.util.ResultMessages;

/**
 * 一括削除サービス。<br>
 * <br>
 * 一括削除を実装するロジック。<br>
 */
@Service
public class AuthorityListDeleteService
		extends
		AbstractBLogic<AuthorityListDeleteButtonReqVO, AuthorityListDeleteButtonReqVO> {
    
    @Autowired
    private ActionLog actionLog;
	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param authorityListDeleteButtonReqVO
	 *            権限一覧情報
	 */
	@Override
	protected void preExecute(
			AuthorityListDeleteButtonReqVO authorityListDeleteButtonReqVO)
			throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param authorityListDeleteButtonReqVO
	 *            権限一覧情報
	 * @return authorityListDeleteButtonReqVO 一括削除情報
	 */
	@Override
	protected AuthorityListDeleteButtonReqVO doExecute(CloudantClient client,
			AuthorityListDeleteButtonReqVO authorityListDeleteButtonReqVO)
			throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		String actionLogStr = "";
		String flag = "0";
		for (int i = 0; i < authorityListDeleteButtonReqVO.getDeleteList()
				.size(); i++) {
			if (authorityListDeleteButtonReqVO.getDeleteList().get(i)
					.getSelect() == null) {
				continue;
			}
			if (authorityListDeleteButtonReqVO.getDeleteList().get(i)
					.getSelect() == true) {
			        actionLogStr = actionLogStr + 
                            authorityListDeleteButtonReqVO.getDeleteList().get(i).getAuthorityName() + "/";
				List<UserDoc> userDoc = new ArrayList<>();
				// 検索キーを整理する
				String queryKey = "authority:\""
						+ authorityListDeleteButtonReqVO.getDeleteList().get(i)
								.getAuthorityName() + "\"";
				userDoc = repositoryUtil.getIndex(db,
						ApplicationKeys.INSIGHTINDEX_USER_USER_SEARCHINFO,
						queryKey, UserDoc.class);
				// この権限はユーザーを使用しない場合、削除できる
				if (userDoc == null || userDoc.size() == 0) {

					FunctionDoc functionDoc = new FunctionDoc();
					try {
						// 削除前検索
						functionDoc = (FunctionDoc) repositoryUtil.find(db,
								authorityListDeleteButtonReqVO.getDeleteList()
										.get(i).get_id(), FunctionDoc.class);
					} catch (BusinessException e) {
						// エラーメッセージを出力、処理終了。
					    flag = "1";
					}
					// 権限情報DBを削除
					if (!"1".equals(flag)) {
					    repositoryUtil.removeByDocId(db, functionDoc.get_id());
					    
					}
				} else {
					// この権限はユーザーを使用中場合、削除不可
					// エラーメッセージを出力、処理終了。
				    flag = "2";
//					ResultMessages messages = ResultMessages.error().add(
//							MessageKeys.E_AUTHORITYLIST_1001);
//					throw new BusinessException(messages);
				}
			}
		}
		actionLogStr=actionLogStr.substring(0, actionLogStr.length()-1);
		actionLog.saveActionLog(Constants.ACTIONLOG_AUTHORITY_2 + "（"
		        + "権限名：" +actionLogStr + "）" , db);
	      if ("2".equals(flag)) {
	            ResultMessages messages = ResultMessages.warning().add(
	                    MessageKeys.E_AUTHORITYLIST_1001);
	            throw new BusinessException(messages);
	        } else if ("1".equals(flag)) {
	            ResultMessages messages = ResultMessages.warning().add(
                        MessageKeys.E_AUTHORITYLIST_1002);
                throw new BusinessException(messages);
	        }
		return authorityListDeleteButtonReqVO;
	}

}
