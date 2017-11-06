package com.scsk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.PushRecordLoanDetailSelService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

@Controller
@RequestMapping("/protected")
public class PushRecordLoanDetailController {
    
    @Autowired
    PushRecordLoanDetailSelService pushRecordLoanDetailSelService;
    
    /**
     * 申込詳細データ検索メソッド。
     * 
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/push/pushRecordLoanDetail", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> detailShow(
            @RequestParam("_id") String id) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
        AccountAppDetailReqVO input = new AccountAppDetailReqVO();
        input.set_id(id);
        BaseResVO baseResVO = new BaseResVO(); 
        baseResVO = input;

        try {
            // 申込詳細情報取得
        	BaseResVO ba = pushRecordLoanDetailSelService
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
    
    
    

}
