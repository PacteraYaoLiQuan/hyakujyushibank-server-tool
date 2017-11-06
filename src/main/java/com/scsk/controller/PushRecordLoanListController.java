package com.scsk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.PushRecordAppListSendReqVO;
import com.scsk.response.vo.PushRecordAppListInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.PushRecordLoanListService;
import com.scsk.service.PushRecordLoanRefuseService;
import com.scsk.service.PushRecordLoanSendService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * PUSH通知承認一覧画面。<br>
 * <br>
 * メニュー画面からPUSH通知承認一覧表示＆PUSH通知承認を実装すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class PushRecordLoanListController {

    @Autowired
    private PushRecordLoanListService pushRecordLoanListService;
    @Autowired
    private PushRecordLoanSendService pushRecordLoanSendService;
    @Autowired
    private PushRecordLoanRefuseService pushRecordLoanRefuseService;

    /**
     * PUSH通知承認一覧初期化表示メソッド。
     * 
     * @param なし
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/push/pushRecordLoanList", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<PushRecordAppListInitResVO>> accountAppListInit(HttpServletRequest req) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        PushRecordAppListInitResVO pushRecordAppListInitResVO = new PushRecordAppListInitResVO();
        ResponseEntityVO<PushRecordAppListInitResVO> resEntityBody = new ResponseEntityVO<PushRecordAppListInitResVO>();

        try {
            // PUSH通知一覧データを検索する
            pushRecordAppListInitResVO = (PushRecordAppListInitResVO) pushRecordLoanListService
                    .execute(pushRecordAppListInitResVO);
            // 初期化データない場合、Messageをセットする
            if (pushRecordAppListInitResVO.getAccountAppPushList().isEmpty()) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1001);
                throw new BusinessException(messages);
            }
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            resEntityBody.setResultData(pushRecordAppListInitResVO);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            resEntityBody.setResultData(pushRecordAppListInitResVO);
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<PushRecordAppListInitResVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * PUSH通知一括承認メソッド。
     * 
     * @param @RequestBody
     *            PushRecordAppListSendReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/push/sendLoanButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<PushRecordAppListSendReqVO>> sendButton(HttpServletRequest req,
            @RequestBody PushRecordAppListSendReqVO pushRecordAppListSendReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<PushRecordAppListSendReqVO> resEntityBody = new ResponseEntityVO<PushRecordAppListSendReqVO>();
        PushRecordAppListSendReqVO sendReqVO = new PushRecordAppListSendReqVO();
        try {
            // 選択したデータを承認する
            sendReqVO = pushRecordLoanSendService.execute(pushRecordAppListSendReqVO);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.I_PUSH001_1002);
            sendReqVO.setErrFlag("1");
            resEntityBody.setMessages(messages);
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultData(sendReqVO);
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<PushRecordAppListSendReqVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * 承認取り下げメソッド。
     * 
     * @param @RequestBody
     *            UserListDeleteReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/push/deleteLoanButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<PushRecordAppListSendReqVO>> deleteButton(HttpServletRequest req,
            @RequestBody PushRecordAppListSendReqVO pushRecordAppListSendReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<PushRecordAppListSendReqVO> resEntityBody = new ResponseEntityVO<PushRecordAppListSendReqVO>();
        PushRecordAppListSendReqVO sendReqVO = new PushRecordAppListSendReqVO();
        try {
            // 選択したデータを承認する
            sendReqVO = pushRecordLoanRefuseService.execute(pushRecordAppListSendReqVO);

            ResultMessages messages = ResultMessages.error().add(MessageKeys.I_PUSH001_1006);
            sendReqVO.setErrFlag("1");
            resEntityBody.setMessages(messages);

            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultData(sendReqVO);
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<PushRecordAppListSendReqVO>>(resEntityBody, HttpStatus.OK);
    }
}
