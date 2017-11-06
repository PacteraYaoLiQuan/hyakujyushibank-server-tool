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
import com.scsk.response.vo.ContentsAppCDListResVO;
import com.scsk.response.vo.ContentsTypeInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.ContentsAppCDListService;
import com.scsk.service.ContentsTypeDeleteService;
import com.scsk.service.ContentsTypeListService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;
@Controller
@RequestMapping("/protected")
public class ContentsTypeListController {

	@Autowired
	private ContentsTypeListService contentsTypeListService;
	@Autowired
	private ContentsTypeDeleteService contentsTypeDeleteService;
	@Autowired
	private ContentsAppCDListService contentsAppCDListService;
	
	@RequestMapping(value = "/contentsType/contentsTypeList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsTypeInitResVO>> listInit(
			HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ContentsTypeInitResVO contentsTypeInitResVO = new ContentsTypeInitResVO();
		ResponseEntityVO<ContentsTypeInitResVO> resEntityBody = new ResponseEntityVO<ContentsTypeInitResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=contentsTypeInitResVO;
		try {
			// 店舗ATM一覧データを検索する
			contentsTypeInitResVO = (ContentsTypeInitResVO) contentsTypeListService
					.execute(baseResVO);
			// 初期化データない場合、Messageをセットする
			if (contentsTypeInitResVO.getContentsTypeInitList().isEmpty()) {
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_CONTENTSTYPE_1001);
				throw new BusinessException(messages);
			}
			// ヘッダ設定（処理成功の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定（処理成功の場合）
			resEntityBody.setResultData(contentsTypeInitResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(e.getResultMessages());
			resEntityBody.setResultData(contentsTypeInitResVO);
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
		return new ResponseEntity<ResponseEntityVO<ContentsTypeInitResVO>>(
				resEntityBody, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contentsType/contentsAppCDList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsAppCDListResVO>> getAppCDList(
			HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ContentsAppCDListResVO contentsAppCDListResVO = new ContentsAppCDListResVO();
		ResponseEntityVO<ContentsAppCDListResVO> resEntityBody = new ResponseEntityVO<ContentsAppCDListResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=contentsAppCDListResVO;
		try {
			contentsAppCDListResVO = (ContentsAppCDListResVO) contentsAppCDListService
					.execute(baseResVO);
			// ヘッダ設定（処理成功の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定（処理成功の場合）
			resEntityBody.setResultData(contentsAppCDListResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(e.getResultMessages());
			resEntityBody.setResultData(contentsAppCDListResVO);
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
		return new ResponseEntity<ResponseEntityVO<ContentsAppCDListResVO>>(
				resEntityBody, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contentsType/deleteButton", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsTypeInitResVO>> deleteButton(
			HttpServletRequest req,
			@RequestBody ContentsTypeInitResVO contentsAppInitResVO) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsTypeInitResVO> resEntityBody = new ResponseEntityVO<ContentsTypeInitResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=contentsAppInitResVO;
		try {
			// 選択したデータを削除する
			contentsTypeDeleteService.execute(baseResVO);
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
		return new ResponseEntity<ResponseEntityVO<ContentsTypeInitResVO>>(
				resEntityBody, HttpStatus.OK);
	}

}
