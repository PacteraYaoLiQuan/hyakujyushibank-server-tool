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
import com.scsk.response.vo.ContentsInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.ContentsDeleteService;
import com.scsk.service.ContentsListService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Controller
@RequestMapping("/protected")
public class ContentsListController {
	@Autowired
	private ContentsListService contentsListService;
	@Autowired
	private ContentsDeleteService contentsDeleteService;
	@RequestMapping(value = "/contents/contentsList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsInitResVO>> listInit(
			HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ContentsInitResVO contentsInitResVO = new ContentsInitResVO();
		ResponseEntityVO<ContentsInitResVO> resEntityBody = new ResponseEntityVO<ContentsInitResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=contentsInitResVO;
		try {
			// 店舗ATM一覧データを検索する
			contentsInitResVO = (ContentsInitResVO) contentsListService
					.execute(baseResVO);
			// 初期化データない場合、Messageをセットする
			if (contentsInitResVO.getContentsInitList().isEmpty()) {
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_CONTENTS_1005);
				throw new BusinessException(messages);
			}
			// ヘッダ設定（処理成功の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定（処理成功の場合）
			resEntityBody.setResultData(contentsInitResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(e.getResultMessages());
			resEntityBody.setResultData(contentsInitResVO);
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
		return new ResponseEntity<ResponseEntityVO<ContentsInitResVO>>(
				resEntityBody, HttpStatus.OK);
	}
	@RequestMapping(value = "/contents/deleteButton", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsInitResVO>> deleteButton(
			HttpServletRequest req,
			@RequestBody ContentsInitResVO contentsInitResVO) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsInitResVO> resEntityBody = new ResponseEntityVO<ContentsInitResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=contentsInitResVO;
		try {
			// 選択したデータを削除する
			contentsDeleteService.execute(baseResVO);
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
		return new ResponseEntity<ResponseEntityVO<ContentsInitResVO>>(
				resEntityBody, HttpStatus.OK);
	}
}
