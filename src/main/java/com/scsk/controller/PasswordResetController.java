package com.scsk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.scsk.request.vo.PasswordResetReqVO;
import com.scsk.response.vo.PasswordResetResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.PasswordResetService;
import com.scsk.service.UserDetailSelService;
import com.scsk.util.GlobalRequest;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * パスワードリセット
 * 
 * 
 */
@Controller
@RequestMapping("/public")
public class PasswordResetController {
    
    @Autowired
    UserDetailSelService userDetailSelService;
    
    @Autowired
    PasswordResetService passwordResetService;

    /**
     * パスワードをリセットメソッド。
     * 
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/passwordReset", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<PasswordResetResVO>> passwordReset(
            @RequestBody PasswordResetReqVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<PasswordResetResVO> entityVO = new ResponseEntityVO<PasswordResetResVO>();
        try {
           
            HttpServletRequest request = GlobalRequest.request();
            HttpSession session = request.getSession();
            input.setUserID((String) session.getAttribute("userID")); 
            input.setUserName((String) session.getAttribute("userName")); 
            input.setEmail((String) session.getAttribute("email")); 
            // ステータスを更新
            PasswordResetResVO output = passwordResetService.execute(input);
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
        return new ResponseEntity<ResponseEntityVO<PasswordResetResVO>>(entityVO, HttpStatus.OK);
    }
}
