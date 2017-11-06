package com.scsk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scsk.constants.Constants;
import com.scsk.exception.BusinessException;
import com.scsk.response.vo.AccountAppInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.WebListInitSampleService;

/**
 * 申込一覧コントロール。<br>
 * <br>
 * 申込一覧データを検索すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class WebListSampleController {

    @Autowired
    WebListInitSampleService webListSampleService;

    /**
     *  申込一覧データ検索メソッド。
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/webListInit", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<AccountAppInitResVO>> webListInit(HttpServletRequest req , Model model) {
        // TODO 通信ルールは実装されていません
    	
    	
        ResponseEntityVO<AccountAppInitResVO> resEntity = new ResponseEntityVO<AccountAppInitResVO>();
        try {
        	AccountAppInitResVO reqVo = new AccountAppInitResVO();
        	//申込一覧データを検索する
        	AccountAppInitResVO output = (AccountAppInitResVO) webListSampleService.execute(reqVo);
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
        return new ResponseEntity<ResponseEntityVO<AccountAppInitResVO>>(resEntity, HttpStatus.OK);
    }
}
