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
import com.scsk.request.vo.UserDetailReqVO;
import com.scsk.request.vo.UserDetailUpdateReqVO;
import com.scsk.request.vo.UserPasswordResetReqVO;
import com.scsk.response.vo.UserDetailResVO;
import com.scsk.response.vo.UserDetailUpdateResVO;
import com.scsk.response.vo.UserPasswordResetResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.UserDetailSelService;
import com.scsk.service.UserDetailUpdService;
import com.scsk.service.UserPasswordResetService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * ユーザー新規／編集コントロール。<br>
 * <br>
 * ユーザー情報データを検索すること。<br>
 * ユーザー情報データを登録すること。<br>
 * ユーザー情報データを更新すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class UserDetailController {

	@Autowired
	UserDetailSelService userDetailSelService;

	@Autowired
	UserDetailUpdService userDetailUpdService;

	@Autowired
	UserPasswordResetService userPasswordResetService;
	/**
	 * ユーザー詳細データ検索メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/user/userDetail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<UserDetailResVO>> detailShow(
			@RequestParam("_id") String id) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<UserDetailResVO> entityVO = new ResponseEntityVO<UserDetailResVO>();
		UserDetailReqVO input = new UserDetailReqVO();
		input.set_id(id);

		try {
			// ユーザー詳細情報取得
			UserDetailResVO output = userDetailSelService.execute(input);
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
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<UserDetailResVO>>(entityVO,
				HttpStatus.OK);

	}

	/**
	 * ユーザー詳細データ更新メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/user/userUpdate", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<UserDetailUpdateResVO>> dataUpd(
			@RequestBody UserDetailUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<UserDetailUpdateResVO> entityVO = new ResponseEntityVO<UserDetailUpdateResVO>();

		try {
			// ステータスを更新
			UserDetailUpdateResVO output = userDetailUpdService.execute(input);
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
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<UserDetailUpdateResVO>>(
				entityVO, HttpStatus.OK);

	}
	/**
	 * パスワードをリセットメソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/user/userPasswordReset", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<UserPasswordResetResVO>> passwordReset(
			@RequestBody UserPasswordResetReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<UserPasswordResetResVO> entityVO = new ResponseEntityVO<UserPasswordResetResVO>();

		try {
			// ステータスを更新
			UserPasswordResetResVO output = userPasswordResetService.execute(input);
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
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<UserPasswordResetResVO>>(
				entityVO, HttpStatus.OK);

	}
}
