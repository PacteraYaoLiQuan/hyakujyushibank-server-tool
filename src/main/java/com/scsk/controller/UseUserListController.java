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
import com.scsk.request.vo.AccountAppListCsvButtonReqVO;
import com.scsk.request.vo.FileListDeleteReqVO;
import com.scsk.request.vo.PushMessageListDeleteReqVO;
import com.scsk.request.vo.UseUserListCsvButtonReqVO;
import com.scsk.response.vo.AccountAppListCsvButtonResVO;
import com.scsk.response.vo.AccountYamaGataAppListCsvButtonResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.response.vo.PushMessageInitResVO;
import com.scsk.response.vo.UseUserListCsvButtonResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.FileListDelService;
import com.scsk.service.PushMessageListDelService;
import com.scsk.service.UseUserListCsvService;
import com.scsk.service.UseUserListInitService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.UseUserListInitVO;
import com.scsk.response.vo.UseUserListInitResVO;

/**
 * 利用ユーザー一覧画面。<br>
 * <br>
 * メニュー画面から利用ユーザー一覧表示＆登録／CSV出力を実装すること。<br>
 */

@Controller
@RequestMapping("/protected")
public class UseUserListController {

	@Autowired
	private UseUserListInitService useUserListInitService;
	@Autowired
	private UseUserListCsvService useUserListCsvService;
	@Autowired
	private ReportBLogic reportBLogic;

	/**
	 * 利用ユーザー一覧初期化表示メソッド。
	 * 
	 * @param なし
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/useUser/useUserList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<UseUserListInitResVO>> listInit(HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		UseUserListInitResVO useUserListInitResVO = new UseUserListInitResVO();
		ResponseEntityVO<UseUserListInitResVO> resEntityBody = new ResponseEntityVO<UseUserListInitResVO>();
		try {
			// ファイル一覧データを検索する
			useUserListInitResVO = (UseUserListInitResVO) useUserListInitService.execute(useUserListInitResVO);
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
	 * CSV出力メソッド。
	 * 
	 * @param @RequestBody
	 *            accountAppListCsvButtonReqVO 一覧データ
	 * @return ResponseEntity 戻るデータオブジェクト
	 */
	@RequestMapping(value = "/useUser/csvButton", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<UseUserListCsvButtonResVO>> csvButton(HttpServletRequest req,
			HttpServletResponse res, @RequestBody UseUserListCsvButtonReqVO useUserListCsvButtonReqVO) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		UseUserListCsvButtonResVO useUserListCsvButtonResVO = new UseUserListCsvButtonResVO();
		ResponseEntityVO<UseUserListCsvButtonResVO> resEntityBody = new ResponseEntityVO<UseUserListCsvButtonResVO>();

		try {
			// CSV出力用データを抽出
			useUserListCsvButtonResVO = useUserListCsvService.execute(useUserListCsvButtonReqVO);
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
			String outputFileName = ApplicationKeys.USEUSERLIST101_REPORT + "_" + date;
			// 帳票/CSV区分設定
			csvCreate1("1", date, useUserListCsvButtonResVO.getUseUserList(), outputFileName, req);
			// CSV出力
			// reportBLogic.reportCreate(date, jasperName,
			// (Map<String, Object>) parameters, jrDataSource, flg,
			// outputFileName, req, res);

			// ヘッダ設定（処理成功の場合）
			useUserListCsvButtonResVO.setDate(date);
			resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
			resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定
			resEntityBody.setResultData(useUserListCsvButtonResVO);
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
		return new ResponseEntity<ResponseEntityVO<UseUserListCsvButtonResVO>>(resEntityBody, HttpStatus.OK);
	}
	
	/**
	 * CSVボタンダウン‐ロードメソッド。
	 * 
	 * @param @RequestParam date 当日日付
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/useUser/csvButtonDownLoad", method = RequestMethod.GET)
	public void csvButtonDownLoad(
			HttpServletRequest req, HttpServletResponse res,@RequestParam("date") String date) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<UseUserListCsvButtonReqVO> resEntityBody = new ResponseEntityVO<UseUserListCsvButtonReqVO>();

		try {
			// CSV出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の詳細フォルダーを取得
			String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";
			// ZIPファイルダウンロード
			String zipName = ApplicationKeys.USEUSERLIST101_REPORT + "_" + date+ Constants.CSV;
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
     * 利用ユーザーCSV出力ボタンCSVファイル生成メソッド。
     * 
     * @param tacsflag
     *            　 TACSフラグ
     * @return date TACSフラグ値
     * @throws Exception
     */
    public void csvCreate1(String flg,String date, List<UseUserListInitVO> dateSourceList,String outputFileName,HttpServletRequest req) {
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
            s = "\"個人情報管理表\""+ "\n";
            Buff.write(s.getBytes("SHIFT-JIS"));
            s= "\"" + "ユーザータイプ" + "\"" + ","
                + "\"" + "氏名" + "\"" + ","
                + "\"" + "氏名（カナ）" + "\"" + ","
                + "\"" + "年齢" + "\"" + ","
                + "\"" + "生年月日" + "\"" + ","
                + "\"" + "性別" + "\"" + ","
                + "\"" + "職業" + "\"" + ","
                + "\"" + "携帯電話番号" + "\"" + ","
                + "\"" + "自宅電話番号" + "\"" + ","
                + "\"" + "勤務先名" + "\"" + ","
                + "\"" + "勤務先電話番号" + "\"" + ","
                + "\"" + "郵便番号" + "\"" + ","
                + "\"" + "住所" + "\"" + ","
                + "\"" + "市区町村以下（カナ）" + "\"" + ","
                + "\"" + "E-mail" + "\"" + ","
                + "\"" + "店番" + "\"" + ","
                + "\"" + "科目" + "\"" + ","
                + "\"" + "口座番号" + "\"" + ","
                + "\"" + "口座名称" + "\"" + ","
                + "\"" + "利用規約同意日時" + "\"" + ","
                + "\"" + "最終リクエスト日時" + "\""+ "\n";
            Buff.write(s.getBytes("MS932"));
            
            for (int i = 0; i < dateSourceList.size(); i++) {
                // 都道府
                String address = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getAddress())) {
                    address = dateSourceList.get(i).getAddress();
                    address = address.replace("\"", "\"\"");
                }
                // 職業
                String otherOccupations = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getOtherOccupations())) {
                    otherOccupations = dateSourceList.get(i).getOtherOccupations();
                    otherOccupations = otherOccupations.replace("\"", "\"\"");
                }
                // 勤務先名
                String workName = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getWorkName())) {
                    workName = dateSourceList.get(i).getWorkName();
                    workName = workName.replace("\"", "\"\"");
                }
                String s2 = "";
                String s1 = "\"" + dateSourceList.get(i).getUserType() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getName() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getKanaName() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getAge() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getBirthday() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getSex() + "\"" + ","
                        + "\"" + otherOccupations + "\"" + ","
                        + "\"" + dateSourceList.get(i).getPhoneNumber() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getTeleNumber() + "\"" + ","
                        + "\"" + workName + "\"" + ","
                        + "\"" + dateSourceList.get(i).getWorkTeleNumber() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getPostCode() + "\"" + ","
                        + "\"" + address + "\"" + ","
                        + "\"" + dateSourceList.get(i).getKanaAddress() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getEmail() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getStoreName() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getKamokuName() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getAccountNumber() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getAccountName() + "\"" + ","
                        + "\"" + dateSourceList.get(i).getAgreeDate() + "\"" + ","
                         + "\"" + dateSourceList.get(i).getLastReqTime() + "\"";
                if(flg.equals("1")){
                    s2 = s1 + "\n";
                }else{
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
