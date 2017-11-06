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
import com.scsk.request.vo.ContentsTypeUpdateReqVO;
import com.scsk.request.vo.ContentsUpdateReqVO;
import com.scsk.response.vo.ContentsTypeCDListResVO;
import com.scsk.response.vo.ContentsUpdateResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.ContentsIDService;
import com.scsk.service.ContentsSelService;
import com.scsk.service.ContentsTypeCDListService;
import com.scsk.service.ContentsUpdService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Controller
@RequestMapping("/protected")
public class ContentsDetailController {
	@Autowired
	private ContentsTypeCDListService contentsTypeCDListService;
	@Autowired
	private ContentsUpdService contentsUpdService;
	@Autowired
	private ContentsSelService contentsSelService;
	@Autowired
	private ContentsIDService contentsIDService;
	
	@RequestMapping(value = "/contents/autoContentsID", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<ContentsUpdateResVO>> contentsID() {
	    LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<ContentsUpdateResVO> entityVO = new ResponseEntityVO<ContentsUpdateResVO>();
        ContentsUpdateReqVO input = new ContentsUpdateReqVO();
        try {
            // 店舗ATM詳細情報取得
            ContentsUpdateResVO output = (ContentsUpdateResVO) contentsIDService
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
        return new ResponseEntity<ResponseEntityVO<ContentsUpdateResVO>>(
                entityVO, HttpStatus.OK);
	}
	@RequestMapping(value = "/contents/contentsTypeCDList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsTypeCDListResVO>> dataUpd(
			@RequestBody ContentsTypeUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsTypeCDListResVO> entityVO = new ResponseEntityVO<ContentsTypeCDListResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=input;
		try {
			// ステータスを更新
			ContentsTypeCDListResVO output = (ContentsTypeCDListResVO) contentsTypeCDListService
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
		return new ResponseEntity<ResponseEntityVO<ContentsTypeCDListResVO>>(
				entityVO, HttpStatus.OK);

	}
	@RequestMapping(value = "/contents/contentsUpd", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsUpdateResVO>> dataUpd(
			@RequestBody ContentsUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsUpdateResVO> entityVO = new ResponseEntityVO<ContentsUpdateResVO>();
		BaseResVO baseResVO=new BaseResVO();
		baseResVO=input;
		try {
			// ステータスを更新
			ContentsUpdateResVO output = (ContentsUpdateResVO) contentsUpdService
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
		return new ResponseEntity<ResponseEntityVO<ContentsUpdateResVO>>(
				entityVO, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/contents/contentsDetail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<ContentsUpdateResVO>> detailShow(
			@RequestParam("_id") String id) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<ContentsUpdateResVO> entityVO = new ResponseEntityVO<ContentsUpdateResVO>();
		ContentsUpdateReqVO input = new ContentsUpdateReqVO();
		input.set_id(id);

		try {
			// 店舗ATM詳細情報取得
			ContentsUpdateResVO output = (ContentsUpdateResVO) contentsSelService
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
		return new ResponseEntity<ResponseEntityVO<ContentsUpdateResVO>>(
				entityVO, HttpStatus.OK);

	}
}
