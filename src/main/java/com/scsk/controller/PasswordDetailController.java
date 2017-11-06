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
import com.scsk.request.vo.PasswordUpdReqVO;
import com.scsk.request.vo.StoreATMDetailReqVO;
import com.scsk.request.vo.StoreATMDetailUpdateReqVO;
import com.scsk.response.vo.StoreATMDetailResVO;
import com.scsk.response.vo.StoreATMDetailUpdateResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.PasswordUpdService;
import com.scsk.service.StoreATMDetailSelService;
import com.scsk.service.StoreATMDetailUpdService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * パスワード変更コントロール。<br>
 * <br>
 * パスワード変更すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class PasswordDetailController {

	@Autowired
	PasswordUpdService passwordUpdService;

	/**
	 * パスワード変更メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/password/passwordUpd", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<PasswordUpdReqVO>> dataUpd(
			@RequestParam("password") String password,@RequestParam("newPassword") String newPassword) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<PasswordUpdReqVO> entityVO = new ResponseEntityVO<PasswordUpdReqVO>();
		PasswordUpdReqVO passwordUpdReqVO = new PasswordUpdReqVO();
		passwordUpdReqVO.setPassword(password);
		passwordUpdReqVO.setNewPassword(newPassword);

		try {
			// パスワードを変更
			passwordUpdService.execute(passwordUpdReqVO);
			// ヘッダ設定（処理成功の場合）
			entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
			entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定
			//entityVO.setResultData(input);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(e.getResultMessages());
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<PasswordUpdReqVO>>(
				entityVO, HttpStatus.OK);

	}

}
