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
import com.scsk.request.vo.AuthorityListDeleteButtonReqVO;
import com.scsk.response.vo.AuthorityInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.AuthorityListDeleteService;
import com.scsk.service.AuthorityListInitService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * 権限一覧画面。<br>
 * <br>
 * メニュー画面から権限一覧表示＆登録／詳細／削除を実装すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class AuthorityListController {

	@Autowired
	private AuthorityListDeleteService authorityListDeleteService;
	@Autowired
	private AuthorityListInitService authorityListInitService;

	/**
	 * 権限一覧初期化表示メソッド。
	 * 
	 * @param なし
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/authority/authorityList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<AuthorityInitResVO>> listInit(
			HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		AuthorityInitResVO authorityInitResVO = new AuthorityInitResVO();
		ResponseEntityVO<AuthorityInitResVO> resEntityBody = new ResponseEntityVO<AuthorityInitResVO>();

		try {
			// 権限一覧データを検索する
			authorityInitResVO = (AuthorityInitResVO) authorityListInitService
					.execute(authorityInitResVO);
			// 初期化データない場合、Messageをセットする
			if (authorityInitResVO.getAuthorityList().isEmpty()) {
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_AUTHORITYLIST_1003);
				throw new BusinessException(messages);
			}
			// ヘッダ設定（処理成功の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定（処理成功の場合）
			resEntityBody.setResultData(authorityInitResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(e.getResultMessages());
			resEntityBody.setResultData(authorityInitResVO);
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
		return new ResponseEntity<ResponseEntityVO<AuthorityInitResVO>>(
				resEntityBody, HttpStatus.OK);
	}

	/**
	 * 一括削除メソッド。
	 * 
	 * @param @RequestBody authorityListDeleteButtonReqVO 一覧データ
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/authority/deleteButton", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<AuthorityListDeleteButtonReqVO>> deleteButton(
			HttpServletRequest req,
			@RequestBody AuthorityListDeleteButtonReqVO authorityListDeleteButtonReqVO) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<AuthorityListDeleteButtonReqVO> resEntityBody = new ResponseEntityVO<AuthorityListDeleteButtonReqVO>();

		try {
			// 選択したデータを削除する
			authorityListDeleteService.execute(authorityListDeleteButtonReqVO);
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
		return new ResponseEntity<ResponseEntityVO<AuthorityListDeleteButtonReqVO>>(
				resEntityBody, HttpStatus.OK);
	}
}
