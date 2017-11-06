package com.scsk.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.model.AccountAppDoc;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;

/**
 * 申込詳細更新サービス。<br>
 * <br>
 * 申込詳細を更新するロジック。<br>
 */
@Service
public class WebDetailUpdSampleService extends AbstractBLogic<AccountAppDetailStatusReqVO,AccountAppDetailStatusResVO> {
	

    /**
     *  主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * @param reqVo 入力情報
     */	
	@Override
	protected void preExecute(AccountAppDetailStatusReqVO reqVo) {

	}

    /**
     *  主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * @param client クラウドDBに接続オブジェクト
     * @param reqVo 入力情報
     * @return resVo 詳細情報
     * @throws Exception 
     */	 
	@Override
	protected AccountAppDetailStatusResVO doExecute(CloudantClient client,AccountAppDetailStatusReqVO reqVo){
		//データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        
        AccountAppDetailStatusResVO resVo = new AccountAppDetailStatusResVO();
		AccountAppDoc doc = new AccountAppDoc();
	    //詳細情報を更新する
		doc =  (AccountAppDoc) repositoryUtil.find(db, reqVo.get_id(),AccountAppDoc.class);
		repositoryUtil.update(db, doc);
		BeanUtils.copyProperties(doc, resVo);
		
		return resVo;
	}

}


