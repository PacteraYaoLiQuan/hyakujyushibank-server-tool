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
import com.scsk.model.AuthorityDoc;
import com.scsk.model.FunctionDoc;
import com.scsk.request.vo.AuthorityDetailReqVO;
import com.scsk.response.vo.AuthorityDetailResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.SortList;
import com.scsk.vo.FunctionVO;

/**
 * 権限詳細検索サービス。<br>
 * <br>
 * 権限詳細検索を実装するロジック。<br>
 */
@Service
public class AuthorityDetailSelService extends
		AbstractBLogic<AuthorityDetailReqVO, AuthorityDetailResVO> {
    
    @Autowired
    private ActionLog actionLog;
	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param reqVo
	 *            入力情報
	 */
	@Override
	protected void preExecute(AuthorityDetailReqVO detailReqVO)
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
	protected AuthorityDetailResVO doExecute(CloudantClient client,
			AuthorityDetailReqVO detailReqVO) throws Exception {

		AuthorityDetailResVO authorityDetailResVO = new AuthorityDetailResVO();
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		// 権限詳細情報取得
		AuthorityDoc authorityDoc = new AuthorityDoc();
		String actionLogStr ="";
		if (!detailReqVO.get_id().isEmpty()) {
			try {
				authorityDoc = (AuthorityDoc) repositoryUtil.find(db,
						detailReqVO.get_id(), AuthorityDoc.class);
			} catch (BusinessException e) {
				// e.printStackTrace();
				LogInfoUtil.LogDebug(e.getMessage());
				// エラーメッセージを出力、処理終了。
				ResultMessages messages = ResultMessages.warning().add(
						MessageKeys.E_AUTHORITYLIST_1006);
				throw new BusinessException(messages);
			}
			// 権限名
			authorityDetailResVO.setAuthorityName(authorityDoc
					.getAuthorityName());
			actionLogStr = authorityDoc .getAuthorityName();
		} else {
			authorityDetailResVO.setAuthorityName("");
		}

		// 戻り値を設定(機能一覧を取得)
		List<String> functionIDList = new ArrayList<String>();
		List<String> functionValueList = new ArrayList<String>();
		functionIDList = authorityDoc.getFunctionIDList();
		functionValueList = authorityDoc.getFunctionValueList();
		List<FunctionVO> functionList = new ArrayList<>();
		List<FunctionDoc> functionDoc = new ArrayList<>();
		int count2 = 1;
		functionDoc = repositoryUtil.getView(db,
				ApplicationKeys.INSIGHTVIEW_FUNCTIONLIST_FUNCTIONLIST,
				FunctionDoc.class);
		for (int count = 0; count < functionDoc.size(); count++) {
			String radioName = "radioName" + String.valueOf(count2);
			count2 = count2 + 1;
			FunctionVO functionVO = new FunctionVO();
			functionVO
					.setFunctionName(functionDoc.get(count).getFunctionName());
			functionVO.setFunctionID(functionDoc.get(count).getFunctionID());
			functionVO.setRadioName(radioName);
			functionVO.setOrderBy(functionDoc.get(count).getOrderBy());
			if (functionIDList.contains(functionDoc.get(count).getFunctionID())) {
				int index = functionIDList.indexOf(functionDoc.get(count)
						.getFunctionID());
				functionVO.setFunctionValue(functionValueList.get(index));
			} else {
				functionVO.setFunctionValue("0");
			}
			functionList.add(count, functionVO);
		}

		authorityDetailResVO.setFunctionList(functionList);
		if (!detailReqVO.get_id().isEmpty()) {
		    actionLog.saveActionLog(Constants.ACTIONLOG_AUTHORITY_5 + "（"
	                + "権限名：" +actionLogStr + "）" , db);
		} else {
		    actionLog.saveActionLog(Constants.ACTIONLOG_AUTHORITY_3,db);
	          
		}
        
		return authorityDetailResVO;

	}
}
