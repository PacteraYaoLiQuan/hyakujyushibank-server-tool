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
import org.springframework.web.servlet.ModelAndView;

import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.PasswordResetReqVO;
import com.scsk.response.vo.PasswordResetResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.PasswordResetCheckService;
import com.scsk.util.GlobalRequest;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

@Controller
@RequestMapping("/public")
public class PasswordResetCheckController {

    @RequestMapping(value = "/passwordReset", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT })
    public ModelAndView passwordReset() {
        LogInfoUtil.LogInfoPage("passwordReset");
        return new ModelAndView("passwordReset");
     }

    @Autowired
    PasswordResetCheckService passwordResetCheckService;

    @RequestMapping(value = "/passwordResetData", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<PasswordResetResVO>> dataShow(
            @RequestBody PasswordResetReqVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<PasswordResetResVO> entityVO = new ResponseEntityVO<PasswordResetResVO>();

        try {
            // ユーザー詳細情報取得
            PasswordResetResVO output = passwordResetCheckService.execute(input);
            // ヘッダ設定（処理成功の場合）
            entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
            entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定
            entityVO.setResultData(output);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
            HttpServletRequest request = GlobalRequest.request();
            HttpSession session = request.getSession();
            session.setAttribute("userName", entityVO.getResultData().getUserName());
            session.setAttribute("userID", entityVO.getResultData().getUserID());
            session.setAttribute("email", entityVO.getResultData().getEmail());
            session.setAttribute("showUserID", entityVO.getResultData().getShowUserID());
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
