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
import com.scsk.request.vo.UseUserDownLoadCsvButtonReqVO;
import com.scsk.request.vo.UseUserListCsvButtonReqVO;
import com.scsk.response.vo.UseUserDownLoadCsvButtonResVO;
import com.scsk.response.vo.UseUserListCsvButtonResVO;
import com.scsk.response.vo.UseUserListInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.UseUserDownLoadClipCsvService;
import com.scsk.service.UseUserDownLoadCsvService;
import com.scsk.service.UseUserDownloadListInitService;
import com.scsk.service.UseUserListInitService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.UseUserDownLoadInitVO;
import com.scsk.vo.UseUserListInitVO;

/**
 * 利用ユーザー一覧画面。<br>
 * <br>
 * メニュー画面から利用ユーザー一覧表示＆登録／CSV出力を実装すること。<br>
 */

@Controller
@RequestMapping("/protected")
public class UseUserDownLoadController {

	@Autowired
	private UseUserDownloadListInitService useUserDownloadListInitService;
	@Autowired
	private UseUserDownLoadCsvService useUserDownLoadCsvService;
	@Autowired
	private UseUserDownLoadClipCsvService useUserDownLoadClipCsvService;
	@Autowired
	private ReportBLogic reportBLogic;

	/**
	 * 利用ユーザー一覧初期化表示メソッド。
	 * 
	 * @param なし
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/useUser/useUserDownloadList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<UseUserListInitResVO>> listInit(HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		UseUserListInitResVO useUserListInitResVO = new UseUserListInitResVO();
		ResponseEntityVO<UseUserListInitResVO> resEntityBody = new ResponseEntityVO<UseUserListInitResVO>();
		try {
			// ファイル一覧データを検索する
			useUserListInitResVO = (UseUserListInitResVO) useUserDownloadListInitService.execute(useUserListInitResVO);
			if (useUserListInitResVO.getUseUserList() == null || useUserListInitResVO.getUseUserList().size() == 0) {
				// エラーメッセージを出力、処理終了。
				ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_USEUSERLIST_1001);
				throw new BusinessException(messages);
			}
			// ヘッダ設定（処理成功の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定（処理成功の場合）
			resEntityBody.setResultData(useUserListInitResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(e.getResultMessages());
			resEntityBody.setResultData(useUserListInitResVO);
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			e.printStackTrace();
			// 予想エラー以外の場合
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<UseUserListInitResVO>>(resEntityBody, HttpStatus.OK);
	}

	/**
	 * テーマダウンロードCSV出力メソッド。
	 * 
	 * @param @RequestBody
	 *            useUserDownLoadCsvButtonReqVO 一覧データ
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/useUser/timeCsvButton", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<UseUserDownLoadCsvButtonResVO>> timeCsvButton(HttpServletRequest req,
			HttpServletResponse res, @RequestBody UseUserDownLoadCsvButtonReqVO useUserDownLoadCsvButtonReqVO) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		UseUserDownLoadCsvButtonResVO useUserDownLoadCsvButtonResVO = new UseUserDownLoadCsvButtonResVO();
		ResponseEntityVO<UseUserDownLoadCsvButtonResVO> resEntityBody = new ResponseEntityVO<UseUserDownLoadCsvButtonResVO>();

		try {
			// CSV出力用データを抽出
			useUserDownLoadCsvButtonResVO = useUserDownLoadCsvService.execute(useUserDownLoadCsvButtonReqVO);
			// CSV出力用の datasourceを準備
			// JRDataSource jrDataSource = new
			// JRBeanCollectionDataSource(accountAppListCsvButtonResVO.getAccountAppList());

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
			String outputFileName = ApplicationKeys.USEUSERDOWNLOADLIST101_REPORT + "_" + date;
			// 帳票/CSV区分設定
			csvCreate("1", date, useUserDownLoadCsvButtonResVO.getUseUserDownLoadList(), outputFileName, req);
			// CSV出力
			// reportBLogic.reportCreate(date, jasperName,
			// (Map<String, Object>) parameters, jrDataSource, flg,
			// outputFileName, req, res);

			// ヘッダ設定（処理成功の場合）
			useUserDownLoadCsvButtonResVO.setDate(date);
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定
			resEntityBody.setResultData(useUserDownLoadCsvButtonResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			if (e.getResultMessages().getList().get(0).getCode().equals(MessageKeys.E_ACCOUNT001_1002)) {
				resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1008));
			} else {
				resEntityBody.setMessages(e.getResultMessages());
			}
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			e.printStackTrace();
			// 予想エラー以外の場合
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<UseUserDownLoadCsvButtonResVO>>(resEntityBody, HttpStatus.OK);
	}

	/**
	 * クリップダウンロードCSV出力メソッド。
	 * 
	 * @param @RequestBody
	 *            useUserDownLoadCsvButtonReqVO 一覧データ
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/useUser/clipCsvButton", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<UseUserDownLoadCsvButtonResVO>> clipCsvButton(HttpServletRequest req,
			HttpServletResponse res, @RequestBody UseUserDownLoadCsvButtonReqVO useUserDownLoadCsvButtonReqVO) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		UseUserDownLoadCsvButtonResVO useUserDownLoadCsvButtonResVO = new UseUserDownLoadCsvButtonResVO();
		ResponseEntityVO<UseUserDownLoadCsvButtonResVO> resEntityBody = new ResponseEntityVO<UseUserDownLoadCsvButtonResVO>();

		try {
			// CSV出力用データを抽出
			useUserDownLoadCsvButtonResVO = useUserDownLoadClipCsvService.execute(useUserDownLoadCsvButtonReqVO);
			// CSV出力用の datasourceを準備
			// JRDataSource jrDataSource = new
			// JRBeanCollectionDataSource(accountAppListCsvButtonResVO.getAccountAppList());

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
			String outputFileName = ApplicationKeys.USEUSERDOWNLOADLIST102_REPORT + "_" + date;
			// 帳票/CSV区分設定
			csvCreate2("1", date, useUserDownLoadCsvButtonResVO.getUseUserDownLoadList(), outputFileName, req);
			// CSV出力
			// reportBLogic.reportCreate(date, jasperName,
			// (Map<String, Object>) parameters, jrDataSource, flg,
			// outputFileName, req, res);

			// ヘッダ設定（処理成功の場合）
			useUserDownLoadCsvButtonResVO.setDate(date);
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定
			resEntityBody.setResultData(useUserDownLoadCsvButtonResVO);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			if (e.getResultMessages().getList().get(0).getCode().equals(MessageKeys.E_ACCOUNT001_1002)) {
				resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1008));
			} else {
				resEntityBody.setMessages(e.getResultMessages());
			}
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			e.printStackTrace();
			// 予想エラー以外の場合
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
			resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<UseUserDownLoadCsvButtonResVO>>(resEntityBody, HttpStatus.OK);
	}

	/**
	 * timeCSVボタンダウン‐ロードメソッド。
	 * 
	 * @param @RequestParam
	 *            date 当日日付
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/useUser/timeCsvButtonDownLoad", method = RequestMethod.GET)
	public void csvButtonDownLoad(HttpServletRequest req, HttpServletResponse res, @RequestParam("date") String date) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<UseUserDownLoadCsvButtonReqVO> resEntityBody = new ResponseEntityVO<UseUserDownLoadCsvButtonReqVO>();

		try {
			// CSV出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の詳細フォルダーを取得
			String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";

			// ZIPファイルダウンロード
			String zipName = ApplicationKeys.USEUSERDOWNLOADLIST101_REPORT + "_" + date + Constants.CSV;
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
	 * clipCSVボタンダウン‐ロードメソッド。
	 * 
	 * @param @RequestParam
	 *            date 当日日付
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/useUser/clipCsvButtonDownLoad", method = RequestMethod.GET)
	public void clipCsvButtonDownLoad(HttpServletRequest req, HttpServletResponse res,
			@RequestParam("date") String date) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<UseUserDownLoadCsvButtonReqVO> resEntityBody = new ResponseEntityVO<UseUserDownLoadCsvButtonReqVO>();

		try {
			// CSV出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の詳細フォルダーを取得
			String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";

			// ZIPファイルダウンロード
			String zipName = ApplicationKeys.USEUSERDOWNLOADLIST102_REPORT + "_" + date + Constants.CSV;
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
	 * テーマダウロードCSV出力ボタンCSVファイル生成メソッド。
	 * 
	 * @param tacsflag
	 *            TACSフラグ
	 * @return date TACSフラグ値
	 * @throws Exception
	 */
	public void csvCreate(String flg, String date, List<UseUserDownLoadInitVO> dateSourceList, String outputFileName,
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
			s = "\"テーマ情報\"" + "\n";
			Buff.write(s.getBytes("SHIFT-JIS"));
			s = "\"" + "ユーザー名" + "\"" + "," + "\"" + "トピック名" + "\"" + "\n";
			Buff.write(s.getBytes("MS932"));

			for (int i = 0; i < dateSourceList.size(); i++) {
				String s2 = "";
				String s1 = "\"" + dateSourceList.get(i).getUserId() + "\"" + "," + "\""
						+ dateSourceList.get(i).getTopicTitle() + "\"";
				if (flg.equals("1")) {
					s2 = s1 + "\n";
				} else {
					s2 = s1 + "," + "\"" + dateSourceList.get(i).getErrMessage() + "\"" + "\n";
				}
				Buff.write(s2.getBytes("MS932"));
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

	/**
	 * クリップダウンロードCSV出力ボタンCSVファイル生成メソッド。
	 * 
	 * @param tacsflag
	 *            TACSフラグ
	 * @return date TACSフラグ値
	 * @throws Exception
	 */
	public void csvCreate2(String flg, String date, List<UseUserDownLoadInitVO> dateSourceList, String outputFileName,
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
			s = "\"クリップ情報\"" + "\n";
			Buff.write(s.getBytes("SHIFT-JIS"));
			s = "\"" + "ユーザー名" + "\"" + "," + "\"" + "記事タイトル" + "\"" + "," + "\"" + "記事公開日時" + "\"" + "," + "\""
					+ "ソース名" + "\"" + "\n";
			Buff.write(s.getBytes("MS932"));

			for (int i = 0; i < dateSourceList.size(); i++) {
				String s2 = "";
				String s1 = "\"" + dateSourceList.get(i).getUserId() + "\"" + "," + "\""
						+ dateSourceList.get(i).getTitle() + "\"" + "," + "\"" + dateSourceList.get(i).getPublishedAt()
						+ "\"" + "," + "\"" + dateSourceList.get(i).getSourceName() + "\"";
				if (flg.equals("1")) {
					s2 = s1 + "\n";
				} else {
					s2 = s1 + "," + "\"" + dateSourceList.get(i).getErrMessage() + "\"" + "\n";
				}
				Buff.write(s2.getBytes("MS932"));
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
