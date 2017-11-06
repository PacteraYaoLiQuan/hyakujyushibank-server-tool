package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.DesignDocument.MapReduce;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.model.UserActionLogDoc;
import com.scsk.request.vo.UserListLogOutputReqVO;
import com.scsk.response.vo.UserListLogOutputResVO;
import com.scsk.util.Utils;
import com.scsk.vo.UserInitVO;

/**
 * ログ出力サービス。<br>
 * <br>
 * ログ出力を実装するロジック。<br>
 */
@Service
public class UserListLogOutputService extends
		AbstractBLogic<UserListLogOutputReqVO, UserListLogOutputResVO> {

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param UserListLogOutputReqVO
	 *            ユーザー一覧情報
	 */
	@Override
	protected void preExecute(UserListLogOutputReqVO userListLogOutputReqVO)
			throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param UserListLogOutputReqVO
	 *            ユーザー一覧情報
	 * @return UserListLogOutputResVO ログ出力情報
	 */
	@Override
	protected UserListLogOutputResVO doExecute(CloudantClient client,
			UserListLogOutputReqVO userListLogOutputReqVO) throws Exception {
		UserListLogOutputResVO resVo = new UserListLogOutputResVO();
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		String queryKey = "";
		MapReduce mapReduce = new MapReduce();
		List<UserActionLogDoc> userActionList = new ArrayList<>();
		for (UserInitVO initVo : userListLogOutputReqVO.getLogOutputList()) {
			if (initVo.getSelect() == null) {
				continue;
			}
			if (initVo.getSelect() == true) {

				if (Utils.isNotNullAndEmpty(queryKey)) {
					queryKey = queryKey + " || doc.userID === \""
							+ initVo.getUserID() + "\"";
				} else {
					queryKey = "doc.userID === \"" + initVo.getUserID() + "\"";
				}

			}
		}
		mapReduce
				.setMap("function (doc) {if(doc.docType && doc.docType === \"USERACTIONLOG\" && doc.delFlg && doc.delFlg===\"0\" && ("
						+ queryKey + ")) {emit(doc.userID, 1);}}");
		userActionList = repositoryUtil.queryByDynamicView(db, mapReduce,
				UserActionLogDoc.class);
		resVo.setUserActionList(userActionList);
		return resVo;
	}

}
