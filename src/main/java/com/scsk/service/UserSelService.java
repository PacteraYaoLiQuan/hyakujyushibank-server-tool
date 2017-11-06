package com.scsk.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.Key;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.model.UserDoc;

/**
 * ユーザー一覧検索サービス。<br>
 * <br>
 * ユーザー一覧検索を実装するロジック。<br>
 */
@Service
public class UserSelService extends AbstractBLogic<UserDoc,List<UserDoc>> {
	
	private static final Logger logger = LoggerFactory.getLogger(UserSelService.class); 
	
    /**
     *  主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * @param userBean ユーザー情報
     */	
	@Override
	protected void preExecute(UserDoc userBean) {

	}

    /**
     *  主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * @param client クラウドDBに接続オブジェクト
     * @param userBean ユーザー情報
     * @return userBean ユーザー情報
     */	
	@Override
	protected List<UserDoc> doExecute(CloudantClient client,UserDoc userBean) {
		logger.debug("UserSelService----doExecute----実行開始");
		logger.info("UserSelService----doExecute----実行開始");
		//データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        
    	//追加するユーザーがDBに存在するかチェックする
		List<UserDoc> userList = new ArrayList<UserDoc>();
		try {
			userList = db.getViewRequestBuilder("insightView","account001_getList")
					.newRequest(Key.Type.STRING, Object.class)
					.reduce(false)
//		 			.startKey("start-key")
//		 			.endKey("end-key")
//		 			.limit(10)
				 	.includeDocs(true)
				 	.build()
				 	.getResponse()
				 	.getDocsAs(UserDoc.class);
		} catch (IOException e) {
			return userList;
		}
		logger.debug("UserSelService----doExecute----実行終了");
		logger.info("UserSelService----doExecute----実行終了");
		return userList;
	}

}


