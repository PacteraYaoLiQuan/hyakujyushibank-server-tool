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
import com.scsk.model.UserDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.LoginStatusUpdReqVO;
import com.scsk.response.vo.LoginStatusUpdResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.ResultMessages;

@Service
public class LoginStatusUpdService extends
		AbstractBLogic<LoginStatusUpdReqVO, LoginStatusUpdResVO> {

	@Autowired
	private RepositoryUtil repositoryUtil;
	@Autowired
	private EncryptorUtil encryptorUtil;

	@Override
	protected void preExecute(LoginStatusUpdReqVO loginStatusUpdReqVO)
			throws Exception {

	}

	/**
	 *  Session失効、LogOut、画面閉じるの場合、ログイン状態更新。
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param reqVo
	 *            入力情報
	 * @return resVo 詳細情報
	 * @throws Exception
	 */
	@Override
	protected LoginStatusUpdResVO doExecute(CloudantClient client,
			LoginStatusUpdReqVO loginStatusUpdReqVO) throws Exception {

		LoginStatusUpdResVO output = new LoginStatusUpdResVO();
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);

		List<UserDoc> userList = new ArrayList<>();
		String query = "userID:\""
				+ encryptorUtil.encrypt(loginStatusUpdReqVO.getUserID()) + "\"";
		userList = repositoryUtil.getIndex(db,
				ApplicationKeys.INSIGHTINDEX_USER_SEARCHBYUSERID_USERINFO,
				query, UserDoc.class);
		if (userList.size() == 0) {
			ResultMessages messages = ResultMessages.warning().add(
					MessageKeys.E_USERDETAIL_1001);
			throw new BusinessException(messages);
		} else {
			UserDoc userDoc = userList.get(0);
			userDoc.setLoginStatus(0);
			repositoryUtil.update(db, userDoc);
		}
		
		return output;

	}
}
