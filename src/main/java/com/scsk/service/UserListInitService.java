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
import com.scsk.model.UserDoc;
import com.scsk.response.vo.UserInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.SessionUser;
import com.scsk.vo.UserInitVO;

/**
 * ユーザー一覧初期化検索サービス。<br>
 * <br>
 * ユーザー一覧初期化検索を実装するロジック。<br>
 */
@Service
public class UserListInitService extends
		AbstractBLogic<UserInitResVO, UserInitResVO> {

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
	protected void preExecute(UserInitResVO ResVO) throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param なし
	 *            検索条件
	 * @return storeATMInitResVO ユーザー一覧情報
	 */
	@Override
	protected UserInitResVO doExecute(CloudantClient client, UserInitResVO ResVO)
			throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);

		// ユーザー一覧初期データを取得
		List<UserDoc> userInfoDocList = new ArrayList<>();
		List<UserInitVO> userList = new ArrayList<>();
		UserInitResVO userInitResVO = new UserInitResVO();
		userInfoDocList = repositoryUtil.getView(db,
				ApplicationKeys.INSIGHTVIEW_USERLIST_USERLIST, UserDoc.class);
		if (userInfoDocList != null && userInfoDocList.size() != 0) {
			for (UserDoc doc : userInfoDocList) {
				UserInitVO userInitVO = new UserInitVO();
				// ユーザー一覧初期化用データを戻る
				userInitVO.set_id(doc.get_id());
				userInitVO.set_rev(doc.get_rev());
				userInitVO.setUserID(encryptorUtil.decrypt(doc.getUserID()));
				userInitVO
						.setUserName(encryptorUtil.decrypt(doc.getUserName()));
				userInitVO.setEmail(encryptorUtil.decrypt(doc.getEmail()));
				userInitVO.setAuthority(doc.getAuthority());
				userInitVO.setLoginStatus(doc.getLoginStatus());
				userInitVO.setEndLoginDateTime(doc.getEndLoginDateTime());
				userInitVO.setEndPasswordChangeDateTime(doc
						.getEndPasswordChangeDateTime());
				userInitVO.setLockStatus(doc.isLockStatus());
				userList.add(userInitVO);
			}
		} 
		// ユーザー一覧初期データを取得
		List<AuthorityDoc> authorityDocList = new ArrayList<>();
		authorityDocList = repositoryUtil.getView(db,
				ApplicationKeys.INSIGHTVIEW_AUTHORITYLIST_AUTHORITYLIST,
				AuthorityDoc.class);
		List<String> authorityList = new ArrayList<String>();
	      authorityList.add("未指定");
		if (authorityDocList != null && authorityDocList.size() != 0) {
			for (AuthorityDoc doc : authorityDocList) {
				authorityList.add(doc.getAuthorityName());
			}
		}

		userInitResVO.setUserList(userList);
		userInitResVO.setAuthorityList(authorityList);
		userInitResVO.setSessionUserID(SessionUser.userId());
		actionLog.saveActionLog(Constants.ACTIONLOG_USERLIST_1, db);
		return userInitResVO;
	}
}
