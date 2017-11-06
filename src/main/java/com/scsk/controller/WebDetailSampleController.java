package com.scsk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scsk.constants.Constants;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.response.vo.AccountAppDetailResVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.WebDetailInitSampleService;
import com.scsk.service.WebDetailUpdSampleService;

/**
 * 申込詳細コントロール。<br>
 * <br>
 * 申込詳細データを検索すること。<br>
 * 申込詳細データを更新すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class WebDetailSampleController {

    @Autowired
    WebDetailInitSampleService webDetailInitSampleService;
    @Autowired
    WebDetailUpdSampleService webDetailUpdSampleService;
    
    
    /**
     *  申込詳細データ検索メソッド。
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/webDetailInit", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<AccountAppDetailResVO>> webDetailInit(@RequestParam("_id") String id) {
        // TODO 通信ルールは実装されていません
    	
    	//画面入力データを取得する
    	AccountAppDetailReqVO reqVo = new AccountAppDetailReqVO();
    	reqVo.set_id(id);
    	//画面戻り値を宣言する
        ResponseEntityVO<AccountAppDetailResVO> resEntity = new ResponseEntityVO<AccountAppDetailResVO>();
        try {
        	//申込詳細データを検索する
        	AccountAppDetailResVO output = (AccountAppDetailResVO) webDetailInitSampleService.execute(reqVo);
            // ヘッダ設定（処理成功の場合）
            resEntity.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntity.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            resEntity.setResultData(output);
        }catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntity.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntity.setResultCode(e.getErrorCode());
            resEntity.setMessages(e.getResultMessages());
        } catch (Exception e) {
        	 //予想エラー以外の場合
        	resEntity.setResultStatus(Constants.RESULT_STATUS_NG);
        	resEntity.setResultCode(Constants.SYSTEMERROR_CODE);
        }
        return new ResponseEntity<ResponseEntityVO<AccountAppDetailResVO>>(resEntity, HttpStatus.OK);
    }
    
    /**
     *  申込詳細データ更新メソッド。
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/webDetailUpd", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>> webDetailUpd(@RequestBody AccountAppDetailStatusReqVO reqVo) {
        // TODO 通信ルールは実装されていません
    	
    	//画面戻り値を宣言する
        ResponseEntityVO<AccountAppDetailStatusResVO> resEntity = new ResponseEntityVO<AccountAppDetailStatusResVO>();
        try {
        	//申込詳細データを更新する
        	AccountAppDetailStatusResVO output = webDetailUpdSampleService.execute(reqVo);
            // ヘッダ設定（処理成功の場合）
            resEntity.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntity.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            resEntity.setResultData(output);
        }catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntity.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntity.setResultCode(e.getErrorCode());
            resEntity.setMessages(e.getResultMessages());
        }catch (Exception e) {
        	 //予想エラー以外の場合
        	resEntity.setResultStatus(Constants.RESULT_STATUS_NG);
        	resEntity.setResultCode(Constants.SYSTEMERROR_CODE);
        }
        return new ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>>(resEntity, HttpStatus.OK);
    }
}
