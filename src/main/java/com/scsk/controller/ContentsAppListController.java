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
import com.scsk.response.vo.ContentsAppInitResVO;
import com.scsk.response.vo.ContentsVersionCodeResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.ContentsAppDeleteService;
import com.scsk.service.ContentsAppListService;
import com.scsk.service.ContentsVersionCodeService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Controller
@RequestMapping("/protected")
public class ContentsAppListController {
	@Autowired
	private ContentsAppListService contentsAppListService;
	@Autowired
	private ContentsAppDeleteService contentsAppDeleteService;
	@Autowired
	private ContentsVersionCodeService contentsVersionCodeService;
	@RequestMapping(value = "/contentsApp/contentsAppList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsAppInitResVO>> listInit(
			HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ContentsAppInitResVO contentsAppInitResVO = new ContentsAppInitResVO();
		ResponseEntityVO<ContentsAppInitResVO> resEntityBody = new ResponseEntityVO<ContentsAppInitResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=contentsAppInitResVO;
		try {
			// 店舗ATM一覧データを検索する
			contentsAppInitResVO = (ContentsAppInitResVO) contentsAppListService
					.execute(baseResVO);
			// 初期化データない場合、Messageをセットする
			if (contentsAppInitResVO.getContentsAppInitList().isEmpty()) {
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_CONTENTSAPP_1002);
				throw new BusinessException(messages);
			}
			// ヘッダ設定（処理成功の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定（処理成功の場合）
			resEntityBody.setResultData(contentsAppInitResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(e.getResultMessages());
			resEntityBody.setResultData(contentsAppInitResVO);
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			e.printStackTrace();
			// 予想エラー以外の場合
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(ResultMessages.error().add(
					MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<ContentsAppInitResVO>>(
				resEntityBody, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contentsApp/contentsVersion", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsVersionCodeResVO>> getVersion(
			HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ContentsVersionCodeResVO contentsAppInitResVO = new ContentsVersionCodeResVO();
		ResponseEntityVO<ContentsVersionCodeResVO> resEntityBody = new ResponseEntityVO<ContentsVersionCodeResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=contentsAppInitResVO;
		try {
			contentsAppInitResVO = (ContentsVersionCodeResVO) contentsVersionCodeService
					.execute(baseResVO);
			// ヘッダ設定（処理成功の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定（処理成功の場合）
			resEntityBody.setResultData(contentsAppInitResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(e.getResultMessages());
			resEntityBody.setResultData(contentsAppInitResVO);
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			e.printStackTrace();
			// 予想エラー以外の場合
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(ResultMessages.error().add(
					MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<ContentsVersionCodeResVO>>(
				resEntityBody, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contentsApp/deleteButton", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsAppInitResVO>> deleteButton(
			HttpServletRequest req,
			@RequestBody ContentsAppInitResVO contentsAppInitResVO) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsAppInitResVO> resEntityBody = new ResponseEntityVO<ContentsAppInitResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=contentsAppInitResVO;
		try {
			// 選択したデータを削除する
			contentsAppDeleteService.execute(baseResVO);
			// ヘッダ設定（処理成功の場合）
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
			resEntityBody.setMessages(ResultMessages.error().add(
					MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<ContentsAppInitResVO>>(
				resEntityBody, HttpStatus.OK);
	}
}
