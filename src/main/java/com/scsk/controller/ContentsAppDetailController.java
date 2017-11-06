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
import com.scsk.request.vo.ContentsAppUpdateReqVO;
import com.scsk.response.vo.ContentsAppUpdateResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.ContentsAppSelService;
import com.scsk.service.ContentsAppUpdService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

@Controller
@RequestMapping("/protected")
public class ContentsAppDetailController {
	@Autowired
	private ContentsAppUpdService contentsAppUpdService;
	@Autowired
	private ContentsAppSelService contentsAppSelService;

	@RequestMapping(value = "/contentsApp/contentsAppUpd", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsAppUpdateResVO>> dataUpd(
			@RequestBody ContentsAppUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsAppUpdateResVO> entityVO = new ResponseEntityVO<ContentsAppUpdateResVO>();

		try {
			// ステータスを更新
			ContentsAppUpdateResVO output = (ContentsAppUpdateResVO) contentsAppUpdService
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
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<ContentsAppUpdateResVO>>(
				entityVO, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/contentsApp/contentsAppDetail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsAppUpdateResVO>> detailShow(
			@RequestParam("_id") String id) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsAppUpdateResVO> entityVO = new ResponseEntityVO<ContentsAppUpdateResVO>();
		ContentsAppUpdateReqVO input = new ContentsAppUpdateReqVO();
		input.set_id(id);

		try {
			// 店舗ATM詳細情報取得
			ContentsAppUpdateResVO output = (ContentsAppUpdateResVO) contentsAppSelService
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
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<ContentsAppUpdateResVO>>(
				entityVO, HttpStatus.OK);

	}
}
