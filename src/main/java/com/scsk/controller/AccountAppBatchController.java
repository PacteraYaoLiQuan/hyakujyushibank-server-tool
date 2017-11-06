package com.scsk.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.AccountAppListButtonReqVO;
import com.scsk.response.vo.AccountAppInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.AccountAppBatchService;
import com.scsk.service.CloudantDBService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * 口座開設データ、自動で一括削除バッチ。<br>
 * <br>
 * 設定期間を経過したステータスが完了の申込データについて自動で一括削除する。<br>
 */
@Controller
@RequestMapping("/batch")
public class AccountAppBatchController {

	@Autowired
	private CloudantDBService cloudantDBService;
	@Autowired
	private AccountAppBatchService accountAppBatchService;

	/**
	 * 口座開設データ、自動で一括削除メソッド。
	 * 
	 * @param なし
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/account/accountAppBatch", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<AccountAppInitResVO>> accountAppListInit(
			HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<AccountAppInitResVO> resEntityBody = new ResponseEntityVO<AccountAppInitResVO>();

		try {
			// 申込一覧データを検索する
			AccountAppListButtonReqVO accountAppListButtonReqVO = new AccountAppListButtonReqVO();
			accountAppBatchService.execute(accountAppListButtonReqVO);

			// ZIPファイルを削除
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_APP_DATE);
			String date = sdf.format(new Date());
			date = date + "0000";
			String outputPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用のフォルダーを取得
			String printPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;
			File folder = new File(printPath);
			File[] files = folder.listFiles();
			for (File file : files) {
				String compareDate = file.getName();
				compareDate = compareDate.substring(0, 14);
				
				if (date.compareTo(compareDate) > 0) {
					file.delete();
				}
			}
			// ヘッダ設定（処理成功の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(e.getResultMessages());
		} catch (Exception e) {
			e.printStackTrace();
			// 予想エラー以外の場合
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(ResultMessages.error().add(
					MessageKeys.ERR_500));
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<AccountAppInitResVO>>(
				resEntityBody, HttpStatus.OK);
	}
}
