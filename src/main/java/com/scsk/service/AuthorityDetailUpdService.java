package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.AuthorityDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AuthorityDetailStatusReqVO;
import com.scsk.response.vo.AuthorityDetailUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.SortList;
import com.scsk.vo.FunctionVO;

/**
 * 申込詳細データ更新メソッド。
 * 
 * @return ResponseEntity　戻るデータオブジェクト
 */
@Service
public class AuthorityDetailUpdService extends
		AbstractBLogic<AuthorityDetailStatusReqVO, AuthorityDetailUpdateResVO> {
	@Autowired
	private RepositoryUtil repositoryUtil;
    @Autowired
    private ActionLog actionLog;
	@Override
	protected void preExecute(
			AuthorityDetailStatusReqVO authorityDetailStatusReqVO)
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
	protected AuthorityDetailUpdateResVO doExecute(CloudantClient client,
			AuthorityDetailStatusReqVO authorityDetailStatusReqVO)
			throws Exception {

		AuthorityDetailUpdateResVO output = new AuthorityDetailUpdateResVO();
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		AuthorityDoc authorityDoc = new AuthorityDoc();
		String actionLogStr = "";
		if (authorityDetailStatusReqVO.getModeType().equals("2")) {
			// 権限詳細情報取得
			try {
				authorityDoc = (AuthorityDoc) repositoryUtil
						.find(db, authorityDetailStatusReqVO.get_id(),
								AuthorityDoc.class);
			} catch (BusinessException e) {
				// e.printStackTrace();
				LogInfoUtil.LogDebug(e.getMessage());
				// エラーメッセージを出力、処理終了。
				ResultMessages messages = ResultMessages.warning().add(
						MessageKeys.E_AUTHORITYLIST_1006);
				throw new BusinessException(messages);
			}
		}
		
		// 権限名
		authorityDoc.setAuthorityName(authorityDetailStatusReqVO.getAuthorityName());
		List<FunctionVO> functionList = authorityDetailStatusReqVO.getFunctionList();
		SortList<FunctionVO> sortList = new SortList<FunctionVO>();
        sortList.Sort(functionList, "getOrderBy", null);
		List<String> functionIDList = new ArrayList<String>();
		List<String> functionValueList = new ArrayList<String>();
		for (int count = 0; count < functionList.size(); count++) {
		// アクセス機能ID
		functionIDList.add(count,functionList.get(count).getFunctionID());
		// アクセス機能権限
		functionValueList.add(count,functionList.get(count).getFunctionValue());
		}
		// アクセス機能ID
		authorityDoc.setFunctionIDList(functionIDList);
		// アクセス機能権限
		authorityDoc.setFunctionValueList(functionValueList);
		
		// DBに更新
		if (authorityDetailStatusReqVO.getModeType().equals("2")) {
			try{
				repositoryUtil.update(db, authorityDoc);
			} catch (BusinessException e) {
				// e.printStackTrace();
				LogInfoUtil.LogDebug(e.getMessage());
				// エラーメッセージを出力、処理終了。
				ResultMessages messages = ResultMessages.error().add(MessageKeys.E_AUTHORITYLIST_1005);
				throw new BusinessException(messages);
			}
		} else {
			authorityDoc.setDocType(Constants.DOCTYPE_3);
			try{
				repositoryUtil.save(db, authorityDoc);
			} catch (BusinessException e) {
				// e.printStackTrace();
				LogInfoUtil.LogDebug(e.getMessage());
				// エラーメッセージを出力、処理終了。
				ResultMessages messages = ResultMessages.error().add(MessageKeys.E_AUTHORITYLIST_1004);
				throw new BusinessException(messages);
			}
		}
		actionLogStr = authorityDoc.getAuthorityName();
        if (authorityDetailStatusReqVO.getModeType().equals("2")) {
            actionLog.saveActionLog(Constants.ACTIONLOG_AUTHORITY_6 + "（"
                    + "権限名：" +actionLogStr + "）" , db);
        } else {
            actionLog.saveActionLog(Constants.ACTIONLOG_AUTHORITY_4 + "（"
                    + "権限名：" +actionLogStr + "）" , db);
        }
		output.setAuthorityName(authorityDoc.getAuthorityName());
		output.setFunctionIDList(authorityDoc.getFunctionIDList());
		output.setFunctionValueList(authorityDoc.getFunctionValueList());
		return output;
	}

}
