package com.scsk.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.model.AccountAppDoc;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.response.vo.AccountAppDetailResVO;

/**
 * 申込詳細検索サービス。<br>
 * <br>
 * 申込詳細検索を実装するロジック。<br>
 * 申込詳細を更新するロジック。<br>
 */
@Service
public class WebDetailInitSampleService extends AbstractBLogic<AccountAppDetailReqVO,AccountAppDetailResVO> {
	
    /**
     *  主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * @param reqVo 入力情報
     */	
	@Override
	protected void preExecute(AccountAppDetailReqVO reqVo) {

	}

    /**
     *  主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * @param client クラウドDBに接続オブジェクト
     * @param reqVo 入力情報
     * @return resVo 詳細情報
     * @throws Exception 
     */	 
	@Override
	protected AccountAppDetailResVO doExecute(CloudantClient client,AccountAppDetailReqVO reqVo){
		//データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        
        AccountAppDetailResVO resVo = new AccountAppDetailResVO();
		AccountAppDoc doc = new AccountAppDoc();
		
	    //詳細情報を検索する
		doc = (AccountAppDoc) repositoryUtil.find(db, reqVo.get_id() , AccountAppDoc.class);
		BeanUtils.copyProperties(doc, resVo);
       
		return resVo;
	}

}


