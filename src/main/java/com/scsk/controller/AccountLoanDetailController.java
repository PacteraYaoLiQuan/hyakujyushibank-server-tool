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
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.AccountLoanDetailSelService;
import com.scsk.service.AccountLoanDetailUpdService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * 申込詳細コントロール。<br>
 * <br>
 * 申込詳細データを検索すること。<br>
 * 申込詳細データを更新すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class AccountLoanDetailController {

    @Autowired
    AccountLoanDetailSelService accountLoanDetailSelService;
    @Autowired
    AccountLoanDetailUpdService accountLoanDetailUpdService;

    /**
     * 申込詳細データ検索メソッド。
     * 
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/accountLoanDetail", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> detailShow(@RequestParam("_id") String id) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
        AccountAppDetailReqVO input = new AccountAppDetailReqVO();
        input.set_id(id);
        BaseResVO baseResVO = new BaseResVO();
        baseResVO = input;

        try {
            // 申込詳細情報取得
            BaseResVO ba = accountLoanDetailSelService.execute(baseResVO);
            // ヘッダ設定（処理成功の場合）
            entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
            entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定
            entityVO.setResultData(ba);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            // 予想エラー以外の場合
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(entityVO, HttpStatus.OK);

    }

    /**
     * 申込詳細データ更新メソッド。
     * 
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/accountLoanStatusUpd", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>> statusUpd(
            @RequestBody AccountAppDetailStatusReqVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppDetailStatusResVO> entityVO = new ResponseEntityVO<AccountAppDetailStatusResVO>();
        try {
            // ステータスを更新
            AccountAppDetailStatusResVO output = accountLoanDetailUpdService.execute(input);
            // ヘッダ設定（処理成功の場合）
            entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
            entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定
            entityVO.setResultData(output);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            // 予想エラー以外の場合
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }

        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>>(entityVO, HttpStatus.OK);

    }
}
