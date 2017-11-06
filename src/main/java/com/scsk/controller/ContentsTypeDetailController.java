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
import com.scsk.request.vo.ContentsOrderIDReqVO;
import com.scsk.request.vo.ContentsTypeUpdateReqVO;
import com.scsk.request.vo.ContentsUpdateReqVO;
import com.scsk.response.vo.ContentsInitResVO;
import com.scsk.response.vo.ContentsTypeUpdateResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.ContentsTypeOrderIDCheckService;
import com.scsk.service.ContentsTypeOrderIDService;
import com.scsk.service.ContentsTypeSelService;
import com.scsk.service.ContentsTypeUpdService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Controller
@RequestMapping("/protected")
public class ContentsTypeDetailController {
	@Autowired
	private ContentsTypeUpdService contentsTypeUpdService;
	@Autowired
	private ContentsTypeSelService contentsTypeSelService;
	@Autowired
	private ContentsTypeOrderIDService contentsTypeOrderIDService;
	@Autowired
	private ContentsTypeOrderIDCheckService contentsTypeOrderIDCheckService;
	@RequestMapping(value = "/contentsType/contentsTypeUpd", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsTypeUpdateResVO>> dataUpd(
			@RequestBody ContentsTypeUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsTypeUpdateResVO> entityVO = new ResponseEntityVO<ContentsTypeUpdateResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=input;
		try {
			// ステータスを更新
			ContentsTypeUpdateResVO output = (ContentsTypeUpdateResVO) contentsTypeUpdService
					.execute(baseResVO);
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
		return new ResponseEntity<ResponseEntityVO<ContentsTypeUpdateResVO>>(
				entityVO, HttpStatus.OK);

	}
	@RequestMapping(value = "/contentsType/contentsTypeDetail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsTypeUpdateResVO>> detailShow(
			@RequestParam("_id") String id) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsTypeUpdateResVO> entityVO = new ResponseEntityVO<ContentsTypeUpdateResVO>();
		ContentsTypeUpdateReqVO input = new ContentsTypeUpdateReqVO();
		input.set_id(id);
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=input;
		try {
			// 店舗ATM詳細情報取得
			ContentsTypeUpdateResVO output = (ContentsTypeUpdateResVO) contentsTypeSelService
					.execute(baseResVO);
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
		return new ResponseEntity<ResponseEntityVO<ContentsTypeUpdateResVO>>(
				entityVO, HttpStatus.OK);

	}
	@RequestMapping(value = "/contentsType/contentsTypeOrderID", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsInitResVO>> orderID(
			@RequestBody ContentsUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsInitResVO> entityVO = new ResponseEntityVO<ContentsInitResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=input;
		try {
			// ステータスを更新
			ContentsInitResVO output = (ContentsInitResVO) contentsTypeOrderIDService
					.execute(baseResVO);
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
		return new ResponseEntity<ResponseEntityVO<ContentsInitResVO>>(
				entityVO, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/contentsType/contentsTypeOrderIDCheck", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsInitResVO>> orderIDCheck(
			@RequestBody ContentsOrderIDReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsInitResVO> entityVO = new ResponseEntityVO<ContentsInitResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=input;
		try {
			// ステータスを更新
			ContentsInitResVO output = (ContentsInitResVO) contentsTypeOrderIDCheckService
					.execute(baseResVO);
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
		return new ResponseEntity<ResponseEntityVO<ContentsInitResVO>>(
				entityVO, HttpStatus.OK);

	}
}
