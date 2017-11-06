package com.scsk.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.report.ReportBLogic;
import com.scsk.report.ReportUtil;
import com.scsk.request.vo.PushMessageDetailUpdateReqVO;
import com.scsk.request.vo.PushMessagePushTypeReqVO;
import com.scsk.request.vo.UseUserListCsvButtonReqVO;
import com.scsk.response.vo.PushMessageDetailUpdateResVO;
import com.scsk.response.vo.PushMessagePushTypeResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.PushMessageAllDetailUpdService;
import com.scsk.service.PushMessageCsvDetailUpdService;
import com.scsk.service.PushMessageDetailSelService;
import com.scsk.service.PushMessageDetailUpdService;
import com.scsk.service.PushMessagePushTypeService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * メッセージ新規／編集コントロール。<br>
 * <br>
 * メッセージ情報データを登録すること。<br>
 * メッセージ情報データを更新すること。<br>
 */

@Controller
@RequestMapping("/protected")
public class PushMessageDetailController {

	@Autowired
	PushMessageDetailSelService pushMessageDetailSelService;

	@Autowired
	PushMessageDetailUpdService pushMessageDetailUpdService;
	
	@Autowired
	PushMessageAllDetailUpdService pushMessageAllDetailUpdService;
	
	@Autowired
	PushMessageCsvDetailUpdService pushMessageCsvDetailUpdService;
	
    @Autowired
    PushMessagePushTypeService pushMessagePushTypeService;
	@Autowired
	private ReportBLogic reportBLogic;

	/**
	 * ファイル詳細データ検索/登録メソッド。
	 * 
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/message/messageDetail", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<PushMessageDetailUpdateResVO>> detailShow(HttpServletRequest req,
			@RequestBody PushMessageDetailUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<PushMessageDetailUpdateResVO> entityVO = new ResponseEntityVO<PushMessageDetailUpdateResVO>();
		String outPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
		input.setOutPath(outPath);

		try {

			// ユーザー詳細情報取得
			PushMessageDetailUpdateResVO output = pushMessageDetailSelService.execute(input);
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
			entityVO.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<PushMessageDetailUpdateResVO>>(entityVO, HttpStatus.OK);
	}

	/**
	 * ファイル詳細データ更新メソッド。
	 * 
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/message/messageUpdate", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<PushMessageDetailUpdateResVO>> messageUpdate(HttpServletRequest req,
			@RequestBody PushMessageDetailUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<PushMessageDetailUpdateResVO> entityVO = new ResponseEntityVO<PushMessageDetailUpdateResVO>();

		try {
			// ステータスを更新
			PushMessageDetailUpdateResVO output = pushMessageDetailUpdService.execute(input);
			// 当日日付を取得する（日付フォーマット）
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			String date = sdf.format(new Date());
			// 出力する日付フォルダーを作成
			reportBLogic.datePathCreate(req, date);

			// CSV出力用のparameterを準備
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("year", date.substring(0, 4));
			parameters.put("month", date.substring(4, 6));
			parameters.put("day", date.substring(6));
			// プロジェクトのパスを取得
			this.getClass().getClassLoader();
			// String jasperName = ApplicationKeys.ACCOUNTAPPLIST003_REPORT;
			String outputFileName = ApplicationKeys.USEUSERLIST102_REPORT + "_" + date;
			// 帳票/CSV区分設定
			if (input.getArrNoList() != null && input.getArrNoList().size() != 0) {
				output.setDate(date);
				csvCreate1("1", date, input.getArrNoList(), outputFileName, req);
			}

			// CSV出力
			// reportBLogic.reportCreate(date, jasperName,
			// (Map<String, Object>) parameters, jrDataSource, flg,
			// outputFileName, req, res);
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
			entityVO.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<PushMessageDetailUpdateResVO>>(entityVO, HttpStatus.OK);
	}
	
	/**
	 * allPush
	 * 
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/message/allMessageUpdate", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<PushMessageDetailUpdateResVO>> allMessageUpdate(HttpServletRequest req,
			@RequestBody PushMessageDetailUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<PushMessageDetailUpdateResVO> entityVO = new ResponseEntityVO<PushMessageDetailUpdateResVO>();

		try {
			// ステータスを更新
			PushMessageDetailUpdateResVO output = pushMessageAllDetailUpdService.execute(input);
			// 当日日付を取得する（日付フォーマット）
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			String date = sdf.format(new Date());
			// 出力する日付フォルダーを作成
			reportBLogic.datePathCreate(req, date);

			// CSV出力用のparameterを準備
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("year", date.substring(0, 4));
			parameters.put("month", date.substring(4, 6));
			parameters.put("day", date.substring(6));
			// プロジェクトのパスを取得
			this.getClass().getClassLoader();
			// String jasperName = ApplicationKeys.ACCOUNTAPPLIST003_REPORT;
			String outputFileName = ApplicationKeys.USEUSERLIST102_REPORT + "_" + date;
			// 帳票/CSV区分設定
			if (input.getArrNoList() != null && input.getArrNoList().size() != 0) {
				output.setDate(date);
				csvCreate1("1", date, input.getArrNoList(), outputFileName, req);
			}

			// CSV出力
			// reportBLogic.reportCreate(date, jasperName,
			// (Map<String, Object>) parameters, jrDataSource, flg,
			// outputFileName, req, res);
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
			entityVO.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<PushMessageDetailUpdateResVO>>(entityVO, HttpStatus.OK);
	}
	
	/**
	 * csvPush
	 * 
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/message/csvMessageUpdate", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<PushMessageDetailUpdateResVO>> csvMessageUpdate(HttpServletRequest req,
			@RequestBody PushMessageDetailUpdateReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<PushMessageDetailUpdateResVO> entityVO = new ResponseEntityVO<PushMessageDetailUpdateResVO>();

		try {
			// ステータスを更新
			PushMessageDetailUpdateResVO output = pushMessageCsvDetailUpdService.execute(input);
			// 当日日付を取得する（日付フォーマット）
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			String date = sdf.format(new Date());
			// 出力する日付フォルダーを作成
			reportBLogic.datePathCreate(req, date);

			// CSV出力用のparameterを準備
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("year", date.substring(0, 4));
			parameters.put("month", date.substring(4, 6));
			parameters.put("day", date.substring(6));
			// プロジェクトのパスを取得
			this.getClass().getClassLoader();
			// String jasperName = ApplicationKeys.ACCOUNTAPPLIST003_REPORT;
			String outputFileName = ApplicationKeys.USEUSERLIST102_REPORT + "_" + date;
			// 帳票/CSV区分設定
			if (input.getArrNoList() != null && input.getArrNoList().size() != 0) {
				output.setDate(date);
				csvCreate1("1", date, input.getArrNoList(), outputFileName, req);
			}

			// CSV出力
			// reportBLogic.reportCreate(date, jasperName,
			// (Map<String, Object>) parameters, jrDataSource, flg,
			// outputFileName, req, res);
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
			entityVO.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<PushMessageDetailUpdateResVO>>(entityVO, HttpStatus.OK);
	}

	/**
	 * CSVボタンダウン‐ロードメソッド。
	 * 
	 * @param @RequestParam
	 *            date 当日日付
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/message/accountNumberCsvButtonDownLoad", method = RequestMethod.GET)
	public void csvButtonDownLoad(HttpServletRequest req, HttpServletResponse res, @RequestParam("date") String date) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<UseUserListCsvButtonReqVO> resEntityBody = new ResponseEntityVO<UseUserListCsvButtonReqVO>();

		try {
			// CSV出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の詳細フォルダーを取得
			String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";
			// ZIPファイルダウンロード
			String zipName = ApplicationKeys.USEUSERLIST102_REPORT + "_" + date + Constants.CSV;
			ReportUtil.downFile(res, tempPath, zipName);

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
	}
	/**
     * ポップアップで配信対象リストメソッド。
     * 
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/message/pushTypeList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<PushMessagePushTypeResVO>> pushTypeList(HttpServletRequest req,
            @RequestBody PushMessagePushTypeReqVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<PushMessagePushTypeResVO> entityVO = new ResponseEntityVO<PushMessagePushTypeResVO>();
        try {
            // 配信対象リスト情報取得
            PushMessagePushTypeResVO output = pushMessagePushTypeService.execute(input);
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
            entityVO.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<PushMessagePushTypeResVO>>(entityVO, HttpStatus.OK);
    }
	/**
	 * 利用ユーザーCSV出力ボタンCSVファイル生成メソッド。
	 * 
	 * @param tacsflag
	 *            TACSフラグ
	 * @return date TACSフラグ値
	 * @throws Exception
	 */
	public void csvCreate1(String flg, String date, List<String> arrNoList, String outputFileName,
			HttpServletRequest req) {
		FileOutputStream outSTr = null;

		BufferedOutputStream Buff = null;

		try {
			// 帳票出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の日付フォルダーを取得
			String datePath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";
			// Keyフォルダーを作成
			String printPath = datePath;
			File file = new File(printPath);
			// フォルダー存在しない場合
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			// PDF拡張名を設定
			String[] fileName = outputFileName.split("\\.");
			// CSV拡張名を設定
			String csvName = fileName[0] + Constants.CSV;
			// 出力するCSVファイル名を設定
			String csvFileName = printPath + csvName;

			outSTr = new FileOutputStream(new File(csvFileName));

			Buff = new BufferedOutputStream(outSTr);
			String s = "";
//			s = "\"口座番号情報\"" + "\n";
//			Buff.write(s.getBytes("SHIFT-JIS"));
//			s = "\"" + "口座番号" + "\"" + "\n";
//			Buff.write(s.getBytes("MS932"));

			for (String arr : arrNoList) {
				if (arr != null && arr != "") {
					String s2 = "";
					String s1 = arr;
					if (flg.equals("1")) {
						s2 = s1;
					} else {
						s2 = s1 + arr + "\n";
					}
					Buff.write(s2.getBytes("MS932"));
				}
			}
			Buff.flush();
			Buff.close();
		} catch (Exception e) {
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		}

		finally {
			try {
				Buff.close();
			} catch (Exception e) {
				ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1002);
				throw new BusinessException(messages);
			}
		}
	}

}
