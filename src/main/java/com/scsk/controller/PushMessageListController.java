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
import com.scsk.request.vo.FileListDeleteReqVO;
import com.scsk.request.vo.PushMessageListDeleteReqVO;
import com.scsk.response.vo.PushMessageInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.FileListDelService;
import com.scsk.service.PushMessageListDelService;
import com.scsk.service.PushMessageListInitService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * 任意配信履歴一覧画面。<br>
 * <br>
 * メニュー画面から任意配信履歴一覧表示＆登録／削除を実装すること。<br>
 */

@Controller
@RequestMapping("/protected")
public class PushMessageListController {

	@Autowired
	private PushMessageListInitService pushMessageListInitService;
	@Autowired
	private PushMessageListDelService pushMessageListDelService;

	/**
	 * 任意配信履歴一覧初期化表示メソッド。
	 * 
	 * @param なし
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/message/pushMessageList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<PushMessageInitResVO>> listInit(HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		PushMessageInitResVO pushMessageInitResVO = new PushMessageInitResVO();
		ResponseEntityVO<PushMessageInitResVO> resEntityBody = new ResponseEntityVO<PushMessageInitResVO>();
		try {
			// ファイル一覧データを検索する
			pushMessageInitResVO = (PushMessageInitResVO) pushMessageListInitService.execute(pushMessageInitResVO);
			if (pushMessageInitResVO.getMessageList() == null || pushMessageInitResVO.getMessageList().size() == 0) {
				// エラーメッセージを出力、処理終了。
				ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGELIST_1001);
				throw new BusinessException(messages);
			}
			// ヘッダ設定（処理成功の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定（処理成功の場合）
			resEntityBody.setResultData(pushMessageInitResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(e.getResultMessages());
			resEntityBody.setResultData(pushMessageInitResVO);
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			e.printStackTrace();
			// 予想エラー以外の場合
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<PushMessageInitResVO>>(resEntityBody, HttpStatus.OK);
	}

	/**
	 * 一括削除メソッド。
	 * 
	 * @param @RequestBody
	 *            PushMessageListDeleteReqVO 一覧データ
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/message/deleteButton", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<PushMessageListDeleteReqVO>> deleteButton(HttpServletRequest req,
			@RequestBody PushMessageListDeleteReqVO pushMessageListDeleteReqVO) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<PushMessageListDeleteReqVO> resEntityBody = new ResponseEntityVO<PushMessageListDeleteReqVO>();

		try {
			// 選択したデータを削除する
			pushMessageListDelService.execute(pushMessageListDeleteReqVO);
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
			resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<PushMessageListDeleteReqVO>>(resEntityBody, HttpStatus.OK);
	}
}
