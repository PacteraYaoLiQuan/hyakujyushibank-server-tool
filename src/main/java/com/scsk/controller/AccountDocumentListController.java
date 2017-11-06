package com.scsk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.scsk.response.vo.Account114DocumentDetailResVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.response.vo.AccountDocumentInitResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.AccountDocumentDetailUpdService;
import com.scsk.service.AccountDocumentListInitService;
import com.scsk.service.AccountDocumentSelService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

@Controller
@RequestMapping("/protected")
public class AccountDocumentListController {
    @Autowired
    private AccountDocumentListInitService accountDocumentListInitService;
    @Autowired
    private AccountDocumentSelService accountDocumentSelService;
    @Autowired
    private AccountDocumentDetailUpdService accountDocumentDetailUpdService;
    @Value("${bank_cd}")
    private String bank_cd;
    
    @RequestMapping(value = "/account/documentPushDetail", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> pushDetail(
            @RequestParam("_id") String id) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
        AccountAppDetailReqVO input = new AccountAppDetailReqVO();
        input.set_id(id);
        BaseResVO baseResVO = new BaseResVO();
        baseResVO = input;

        try {
            // 申込詳細情報取得
            BaseResVO ba = accountDocumentSelService
                    .execute(baseResVO);
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
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
                    .getCode());
        } catch (Exception e) {
            // 予想エラー以外の場合
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(ResultMessages.error()
                    .add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(
                entityVO, HttpStatus.OK);

    }
    @RequestMapping(value = "/account/documentStatusUpd", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>> statusUpd(
            @RequestBody AccountAppDetailStatusReqVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppDetailStatusResVO> entityVO = new ResponseEntityVO<AccountAppDetailStatusResVO>();

        try {
            // ステータスを更新
            AccountAppDetailStatusResVO output = accountDocumentDetailUpdService
                    .execute(input);
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
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
                    .getCode());
        } catch (Exception e) {
            // 予想エラー以外の場合
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(ResultMessages.error()
                    .add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }

        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>>(
                entityVO, HttpStatus.OK);

    }
    @RequestMapping(value = "/account/accountDocumentSel", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> detailShow(
            @RequestParam("_id") String id) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());

        AccountAppDetailReqVO input = new AccountAppDetailReqVO();
        input.set_id(id);
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();
        baseResVO=input;
        try {
            // 申込一覧データを検索する

            BaseResVO ba = (Account114DocumentDetailResVO) accountDocumentSelService.execute(baseResVO);

            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）

            resEntityBody.setResultData(ba);

            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
                    .getCode());
        } catch (Exception e) {
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error()
                    .add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }
    @RequestMapping(value = "/account/accountDocumentList", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> accountAppListInit(HttpServletRequest req) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        AccountDocumentInitResVO AccountDocumentInitResVO = new AccountDocumentInitResVO();

        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();

        try {
            // 申込一覧データを検索する

            BaseResVO ba = (AccountDocumentInitResVO) accountDocumentListInitService.execute(baseResVO);
            AccountDocumentInitResVO = (AccountDocumentInitResVO) ba;
            // 初期化データない場合、Messageをセットする

            if (AccountDocumentInitResVO.getAccountDocumentList().isEmpty()) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1002);
                throw new BusinessException(messages);
            }

            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）

            resEntityBody.setResultData(AccountDocumentInitResVO);

            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());

            resEntityBody.setResultData(AccountDocumentInitResVO);

            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }
}
