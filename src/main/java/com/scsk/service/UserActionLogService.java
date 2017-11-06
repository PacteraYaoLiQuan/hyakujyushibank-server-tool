package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.UserActionLogDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.UserActionLogReqVO;
import com.scsk.response.vo.UserActionLogResVO;
import com.scsk.util.ResultMessages;
import com.scsk.util.SessionUser;

@Service
public class UserActionLogService extends
		AbstractBLogic<UserActionLogReqVO, UserActionLogResVO> {
	@Autowired
	private RepositoryUtil repositoryUtil;

	@Override
	protected void preExecute(UserActionLogReqVO userActionLogReqVO)
			throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。 行動ログを登録処理。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param reqVo
	 *            入力情報
	 * @return resVo 詳細情報
	 * @throws Exception
	 */
	@Override
	protected UserActionLogResVO doExecute(CloudantClient client,
			UserActionLogReqVO userActionLogReqVO) throws Exception {

		UserActionLogResVO output = new UserActionLogResVO();
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		// UserActionLogDoc
		UserActionLogDoc userActionLogDoc = new UserActionLogDoc();
		// システム日時を取得
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
		String date = sdf.format(new Date());
		userActionLogDoc.setCreatedDate(date);
		userActionLogDoc.setUserID(SessionUser.userId());
		userActionLogDoc.setUserName(SessionUser.userName());
		// アクセス画面名
		userActionLogDoc.setAccessScreen(userActionLogReqVO.getAccessScreen());
		// ボタン名
		userActionLogDoc.setButtonName(userActionLogReqVO.getButtonName());
		try {
			// 登録処理
			db.save(userActionLogDoc);
		} catch (DocumentConflictException e) {
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1005);
			throw new BusinessException(messages);
		}
		return output;

	}

}