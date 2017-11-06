package com.scsk.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.vo.AccountApp114InitVO;
import com.scsk.vo.AccountAppYamaGataInitVO;

@Service
public class AccountAppYamaGataExcelOutPutService {
	/**
	 * 帳票出力ボタンEXCELファイル生成メソッド。
	 * 
	 * @param tacsflag
	 *            TACSフラグ
	 * @return date TACSフラグ値
	 * @throws Exception
	 */
	public void ExcelOutput(HttpServletRequest req, AccountAppYamaGataInitVO accountAppYamaGataInitVO,
			ZipOutputStream zipOutputStream) {
		Workbook workBook = null;
		// 帳票テンプレートフォルダーを取得
		String excelPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_EXCEL_PATH);
		// 当日日付を取得する（日付フォーマット）
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String createDate = sdf.format(new Date());
		// 作成フォルダーを取得
		String filePath = req.getServletContext()
				.getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH + ApplicationKeys.REPORT_TEMP_PATH);
		try {
			// 受付番号
			String accountAppSeq = accountAppYamaGataInitVO.getAccountAppSeq();
			FileInputStream fis = new FileInputStream(excelPath + "/" + ApplicationKeys.ACCOUNTAPPLIST108_REPORT);
			workBook = new XSSFWorkbook(fis);
			String strfileName = accountAppSeq + "_" + createDate + ".xlsx";
			OutputStream out = new FileOutputStream(filePath + "/" + strfileName);
			Sheet sheet = workBook.getSheetAt(0);

			// おなまえ（漢字）
			String strLastName = accountAppYamaGataInitVO.getLastName();
			String strFirstName = accountAppYamaGataInitVO.getFirstName();
			// （表1）
			sheet.getRow(107).getCell(22).setCellValue(strLastName + " " + strFirstName);
			// （表2）
			sheet.getRow(614).getCell(22).setCellValue(strLastName + " " + strFirstName);
			// （表3）
			sheet.getRow(1119).getCell(22).setCellValue(strLastName + " " + strFirstName);
			// （表4）
			sheet.getRow(1637).getCell(22).setCellValue(strLastName + " " + strFirstName);
			// （表6）
			sheet.getRow(2156).getCell(22).setCellValue(strLastName + " " + strFirstName);

			// お名前（フリガナ）
			String strKanaLastName = accountAppYamaGataInitVO.getKanaLastName();
			String strKanaFirstName = accountAppYamaGataInitVO.getKanaFirstName();
			// （表1）
			sheet.getRow(98).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);
			// （表2）
			sheet.getRow(605).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);
			// （表3）
			sheet.getRow(1110).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);
			// （表4）
			sheet.getRow(1628).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);
			// （表6）
			sheet.getRow(2147).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);

			// 和暦年号
			String strOrdinaryDepositEraKbn = accountAppYamaGataInitVO.getOrdinaryDepositEraKbn();
			if ("1".equals(strOrdinaryDepositEraKbn)) {
				// （表1）
				sheet.getRow(146).getCell(26).setCellValue("✔");
				// （表2）
				sheet.getRow(653).getCell(26).setCellValue("✔");
				// （表3）
				sheet.getRow(1158).getCell(26).setCellValue("✔");
				// （表4）
				sheet.getRow(1676).getCell(26).setCellValue("✔");
				// （表6）
				sheet.getRow(2195).getCell(26).setCellValue("✔");
			} else if ("2".equals(strOrdinaryDepositEraKbn)) {
				// （表1）
				sheet.getRow(146).getCell(48).setCellValue("✔");
				// （表2）
				sheet.getRow(653).getCell(48).setCellValue("✔");
				// （表3）
				sheet.getRow(1158).getCell(48).setCellValue("✔");
				// （表4）
				sheet.getRow(1676).getCell(48).setCellValue("✔");
				// （表6）
				sheet.getRow(2195).getCell(48).setCellValue("✔");
			} else if ("3".equals(strOrdinaryDepositEraKbn)) {
				// （表1）
				sheet.getRow(146).getCell(70).setCellValue("✔");
				// （表2）
				sheet.getRow(653).getCell(70).setCellValue("✔");
				// （表3）
				sheet.getRow(1158).getCell(70).setCellValue("✔");
				// （表4）
				sheet.getRow(1676).getCell(70).setCellValue("✔");
				// （表6）
				sheet.getRow(2195).getCell(70).setCellValue("✔");
			} else if ("4".equals(strOrdinaryDepositEraKbn)) {
				// （表1）
				sheet.getRow(146).getCell(92).setCellValue("✔");
				// （表2）
				sheet.getRow(653).getCell(92).setCellValue("✔");
				// （表3）
				sheet.getRow(1158).getCell(92).setCellValue("✔");
				// （表4）
				sheet.getRow(1676).getCell(92).setCellValue("✔");
				// （表6）
				sheet.getRow(2195).getCell(92).setCellValue("✔");
			}

			// 和暦生年月日
			String strEraBirthday = accountAppYamaGataInitVO.getEraBirthday();
			// （表1）
			sheet.getRow(145).getCell(119).setCellValue(strEraBirthday.substring(0, 1));
			sheet.getRow(145).getCell(129).setCellValue(strEraBirthday.substring(1, 2));
			sheet.getRow(145).getCell(146).setCellValue(strEraBirthday.substring(3, 4));
			sheet.getRow(145).getCell(156).setCellValue(strEraBirthday.substring(4, 5));
			sheet.getRow(145).getCell(173).setCellValue(strEraBirthday.substring(6, 7));
			sheet.getRow(145).getCell(183).setCellValue(strEraBirthday.substring(7, 8));
			// （表2）
			sheet.getRow(652).getCell(119).setCellValue(strEraBirthday.substring(0, 1));
			sheet.getRow(652).getCell(129).setCellValue(strEraBirthday.substring(1, 2));
			sheet.getRow(652).getCell(146).setCellValue(strEraBirthday.substring(3, 4));
			sheet.getRow(652).getCell(156).setCellValue(strEraBirthday.substring(4, 5));
			sheet.getRow(652).getCell(173).setCellValue(strEraBirthday.substring(6, 7));
			sheet.getRow(652).getCell(183).setCellValue(strEraBirthday.substring(7, 8));
			// （表3）
			sheet.getRow(1157).getCell(119).setCellValue(strEraBirthday.substring(0, 1));
			sheet.getRow(1157).getCell(129).setCellValue(strEraBirthday.substring(1, 2));
			sheet.getRow(1157).getCell(146).setCellValue(strEraBirthday.substring(3, 4));
			sheet.getRow(1157).getCell(156).setCellValue(strEraBirthday.substring(4, 5));
			sheet.getRow(1157).getCell(173).setCellValue(strEraBirthday.substring(6, 7));
			sheet.getRow(1157).getCell(183).setCellValue(strEraBirthday.substring(7, 8));
			// （表4）
			sheet.getRow(1675).getCell(119).setCellValue(strEraBirthday.substring(0, 1));
			sheet.getRow(1675).getCell(129).setCellValue(strEraBirthday.substring(1, 2));
			sheet.getRow(1675).getCell(146).setCellValue(strEraBirthday.substring(3, 4));
			sheet.getRow(1675).getCell(156).setCellValue(strEraBirthday.substring(4, 5));
			sheet.getRow(1675).getCell(173).setCellValue(strEraBirthday.substring(6, 7));
			sheet.getRow(1675).getCell(183).setCellValue(strEraBirthday.substring(7, 8));
			// （表6）
			sheet.getRow(2194).getCell(119).setCellValue(strEraBirthday.substring(0, 1));
			sheet.getRow(2194).getCell(129).setCellValue(strEraBirthday.substring(1, 2));
			sheet.getRow(2194).getCell(146).setCellValue(strEraBirthday.substring(3, 4));
			sheet.getRow(2194).getCell(156).setCellValue(strEraBirthday.substring(4, 5));
			sheet.getRow(2194).getCell(173).setCellValue(strEraBirthday.substring(6, 7));
			sheet.getRow(2194).getCell(183).setCellValue(strEraBirthday.substring(7, 8));

			// 普通預金の種類
			String strAccountType = accountAppYamaGataInitVO.getAccountType();
			// （表1）
			sheet.getRow(301).getCell(64).setCellValue(strAccountType.substring(0, 1));
			sheet.getRow(301).getCell(71).setCellValue(strAccountType.substring(1, 2));

			// 性別
			String strSex = accountAppYamaGataInitVO.getSexKbn();
			if ("1".equals(strSex)) {
				// （表1）
				sheet.getRow(161).getCell(157).setCellValue("✔");
				// （表2）
				sheet.getRow(668).getCell(157).setCellValue("✔");
				// （表3）
				sheet.getRow(1173).getCell(157).setCellValue("✔");
			} else if ("2".equals(strSex)) {
				// （表1）
				sheet.getRow(161).getCell(182).setCellValue("✔");
				// （表2）
				sheet.getRow(668).getCell(182).setCellValue("✔");
				// （表3）
				sheet.getRow(1173).getCell(182).setCellValue("✔");
			}

			// 郵便番号
			String strPostCode = accountAppYamaGataInitVO.getPostCode();
			// （表1）
			sheet.getRow(69).getCell(29).setCellValue(strPostCode.substring(0, 1));
			sheet.getRow(69).getCell(39).setCellValue(strPostCode.substring(1, 2));
			sheet.getRow(69).getCell(49).setCellValue(strPostCode.substring(2, 3));
			sheet.getRow(69).getCell(63).setCellValue(strPostCode.substring(4, 5));
			sheet.getRow(69).getCell(73).setCellValue(strPostCode.substring(5, 6));
			sheet.getRow(69).getCell(83).setCellValue(strPostCode.substring(6, 7));
			sheet.getRow(69).getCell(93).setCellValue(strPostCode.substring(7, 8));
			// （表2）
			sheet.getRow(576).getCell(29).setCellValue(strPostCode.substring(0, 1));
			sheet.getRow(576).getCell(39).setCellValue(strPostCode.substring(1, 2));
			sheet.getRow(576).getCell(49).setCellValue(strPostCode.substring(2, 3));
			sheet.getRow(576).getCell(63).setCellValue(strPostCode.substring(4, 5));
			sheet.getRow(576).getCell(73).setCellValue(strPostCode.substring(5, 6));
			sheet.getRow(576).getCell(83).setCellValue(strPostCode.substring(6, 7));
			sheet.getRow(576).getCell(93).setCellValue(strPostCode.substring(7, 8));
			// （表3）
			sheet.getRow(1081).getCell(29).setCellValue(strPostCode.substring(0, 1));
			sheet.getRow(1081).getCell(39).setCellValue(strPostCode.substring(1, 2));
			sheet.getRow(1081).getCell(49).setCellValue(strPostCode.substring(2, 3));
			sheet.getRow(1081).getCell(63).setCellValue(strPostCode.substring(4, 5));
			sheet.getRow(1081).getCell(73).setCellValue(strPostCode.substring(5, 6));
			sheet.getRow(1081).getCell(83).setCellValue(strPostCode.substring(6, 7));
			sheet.getRow(1081).getCell(93).setCellValue(strPostCode.substring(7, 8));
			// （表4）
			sheet.getRow(1599).getCell(29).setCellValue(strPostCode.substring(0, 1));
			sheet.getRow(1599).getCell(39).setCellValue(strPostCode.substring(1, 2));
			sheet.getRow(1599).getCell(49).setCellValue(strPostCode.substring(2, 3));
			sheet.getRow(1599).getCell(63).setCellValue(strPostCode.substring(4, 5));
			sheet.getRow(1599).getCell(73).setCellValue(strPostCode.substring(5, 6));
			sheet.getRow(1599).getCell(83).setCellValue(strPostCode.substring(6, 7));
			sheet.getRow(1599).getCell(93).setCellValue(strPostCode.substring(7, 8));
			// （表6）
			sheet.getRow(2118).getCell(29).setCellValue(strPostCode.substring(0, 1));
			sheet.getRow(2118).getCell(39).setCellValue(strPostCode.substring(1, 2));
			sheet.getRow(2118).getCell(49).setCellValue(strPostCode.substring(2, 3));
			sheet.getRow(2118).getCell(63).setCellValue(strPostCode.substring(4, 5));
			sheet.getRow(2118).getCell(73).setCellValue(strPostCode.substring(5, 6));
			sheet.getRow(2118).getCell(83).setCellValue(strPostCode.substring(6, 7));
			sheet.getRow(2118).getCell(93).setCellValue(strPostCode.substring(7, 8));

			// おところ
			String strAddress = accountAppYamaGataInitVO.getAddress();
			// 都道府県
			switch (accountAppYamaGataInitVO.getPrefecturesCode()) {
			case "1":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
				break;
			case "2":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
				break;
			case "3":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
				break;
			case "4":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
				break;
			case "5":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
				break;
			case "6":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
				break;
			case "7":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
				break;
			case "8":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
				break;
			case "9":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
				break;
			case "10":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
				break;
			case "11":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
				break;
			case "12":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
				break;
			case "13":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
				break;
			case "14":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
				break;
			case "15":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
				break;
			case "16":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
				break;
			case "17":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
				break;
			case "18":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
				break;
			case "19":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
				break;
			case "20":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
				break;
			case "21":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
				break;
			case "22":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
				break;
			case "23":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
				break;
			case "24":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
				break;
			case "25":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
				break;
			case "26":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
				break;
			case "27":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
				break;
			case "28":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
				break;
			case "29":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
				break;
			case "30":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
				break;
			case "31":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
				break;
			case "32":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
				break;
			case "33":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
				break;
			case "34":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
				break;
			case "35":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
				break;
			case "36":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
				break;
			case "37":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
				break;
			case "38":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
				break;
			case "39":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
				break;
			case "40":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
				break;
			case "41":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
				break;
			case "42":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
				break;
			case "43":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
				break;
			case "44":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
				break;
			case "45":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
				break;
			case "46":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
				break;
			case "47":
				// （表1）
				sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
				// （表2）
				sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
				// （表3）
				sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
				// （表4）
				sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
				// （表6）
				sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
			}

			// 自宅電話
			String strTelIndex = "-";
			String strTeleNumber = accountAppYamaGataInitVO.getTeleNumber();
			if (!strTeleNumber.isEmpty()) {
				if (strTeleNumber.contains(strTelIndex)) {
					int temp = strTeleNumber.indexOf(strTelIndex);
					int tempLast = strTeleNumber.lastIndexOf(strTelIndex);

					if (temp == tempLast) {
						strTeleNumber = strTeleNumber.replace("-", "");
						// （表1）
						sheet.getRow(67).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
						sheet.getRow(67).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
						sheet.getRow(67).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
						// （表2）
						sheet.getRow(574).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
						sheet.getRow(574).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
						sheet.getRow(574).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
						// （表3）
						sheet.getRow(1079).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
						sheet.getRow(1079).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
						sheet.getRow(1079).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
						// （表4）
						sheet.getRow(1597).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
						sheet.getRow(1597).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
						sheet.getRow(1597).getCell(180)
								.setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
						// （表6）
						sheet.getRow(2116).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
						sheet.getRow(2116).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
						sheet.getRow(2116).getCell(180)
								.setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
					} else {
						// （表1）
						sheet.getRow(67).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
						sheet.getRow(67).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(67).getCell(180)
								.setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
						// （表2）
						sheet.getRow(574).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
						sheet.getRow(574).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(574).getCell(180)
								.setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
						// （表3）
						sheet.getRow(1079).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
						sheet.getRow(1079).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(1079).getCell(180)
								.setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
						// （表4）
						sheet.getRow(1597).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
						sheet.getRow(1597).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(1597).getCell(180)
								.setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
						// （表6）
						sheet.getRow(2116).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
						sheet.getRow(2116).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(2116).getCell(180)
								.setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
					}
				} else {
					// （表1）
					sheet.getRow(67).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
					sheet.getRow(67).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
					sheet.getRow(67).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
					// （表2）
					sheet.getRow(574).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
					sheet.getRow(574).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
					sheet.getRow(574).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
					// （表3）
					sheet.getRow(1079).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
					sheet.getRow(1079).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
					sheet.getRow(1079).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
					// （表4）
					sheet.getRow(1597).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
					sheet.getRow(1597).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
					sheet.getRow(1597).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
					// （表6）
					sheet.getRow(2116).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
					sheet.getRow(2116).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
					sheet.getRow(2116).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
				}
			}

			// 携帯電話
			String strPhIndex = "-";
			String strPhoneNumber = accountAppYamaGataInitVO.getPhoneNumber();
			if (!strPhoneNumber.isEmpty()) {
				if (strPhoneNumber.contains(strPhIndex)) {
					int temp = strPhoneNumber.indexOf(strPhIndex);
					int tempLast = strPhoneNumber.lastIndexOf(strPhIndex);

					if (temp == tempLast) {
						strPhoneNumber = strPhoneNumber.replace("-", "");
						// （表1）
						sheet.getRow(75).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
						sheet.getRow(75).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
						sheet.getRow(75).getCell(180)
								.setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
						// （表2）
						sheet.getRow(582).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
						sheet.getRow(582).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
						sheet.getRow(582).getCell(180)
								.setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
						// （表3）
						sheet.getRow(1087).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
						sheet.getRow(1087).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
						sheet.getRow(1087).getCell(180)
								.setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
						// （表4）
						sheet.getRow(1605).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
						sheet.getRow(1605).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
						sheet.getRow(1605).getCell(180)
								.setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
						// （表6）
						sheet.getRow(2124).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
						sheet.getRow(2124).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
						sheet.getRow(2124).getCell(180)
								.setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
					} else {
						// （表1）
						sheet.getRow(75).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
						sheet.getRow(75).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
						sheet.getRow(75).getCell(180)
								.setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
						// （表2）
						sheet.getRow(582).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
						sheet.getRow(582).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
						sheet.getRow(582).getCell(180)
								.setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
						// （表3）
						sheet.getRow(1087).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
						sheet.getRow(1087).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
						sheet.getRow(1087).getCell(180)
								.setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
						// （表4）
						sheet.getRow(1605).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
						sheet.getRow(1605).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
						sheet.getRow(1605).getCell(180)
								.setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
						// （表6）
						sheet.getRow(2124).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
						sheet.getRow(2124).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
						sheet.getRow(2124).getCell(180)
								.setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
					}
				} else {
					// （表1）
					sheet.getRow(75).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
					sheet.getRow(75).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
					sheet.getRow(75).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
					// （表2）
					sheet.getRow(582).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
					sheet.getRow(582).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
					sheet.getRow(582).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
					// （表3）
					sheet.getRow(1087).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
					sheet.getRow(1087).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
					sheet.getRow(1087).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
					// （表4）
					sheet.getRow(1605).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
					sheet.getRow(1605).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
					sheet.getRow(1605).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
					// （表6）
					sheet.getRow(2124).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
					sheet.getRow(2124).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
					sheet.getRow(2124).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
				}
			}

			// ご職業
			List<String> jobKbn = accountAppYamaGataInitVO.getJobKbn();
			Collections.sort(jobKbn);
			String jobKbnOther = "";
			for (int count = 0; count < jobKbn.size(); count++) {
				String strCount = "";
				strCount = jobKbn.get(count);
				switch (strCount) {
				case "01":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_01 + ",";
					sheet.getRow(1345).getCell(151).setCellValue("○");
					break;
				case "02":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_02 + ",";
					sheet.getRow(1357).getCell(151).setCellValue("○");
					break;
				case "03":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_03 + ",";
					sheet.getRow(1369).getCell(151).setCellValue("○");
					break;
				case "04":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_04 + ",";
					sheet.getRow(1381).getCell(151).setCellValue("○");
					break;
				case "05":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_05 + ",";
					sheet.getRow(1393).getCell(151).setCellValue("○");
					break;
				case "06":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_06 + ",";
					sheet.getRow(1345).getCell(199).setCellValue("○");
					break;
				case "07":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_07 + ",";
					sheet.getRow(1357).getCell(199).setCellValue("○");
					break;
				case "08":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_08 + ",";
					sheet.getRow(1369).getCell(199).setCellValue("○");
					break;
				case "09":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_09 + ",";
					sheet.getRow(1381).getCell(199).setCellValue("○");
					break;
				case "10":
					jobKbnOther = jobKbnOther + Constants.JOBKBN_10 + ",";
					sheet.getRow(1393).getCell(199).setCellValue("○");
					break;
				case "49":
					jobKbnOther = jobKbnOther + "その他（" + accountAppYamaGataInitVO.getJobKbnOther() + "）,";
					sheet.getRow(1345).getCell(244).setCellValue("○");
					sheet.getRow(1356).getCell(246).setCellValue(accountAppYamaGataInitVO.getJobKbnOther());
				}
			}

			// 取引目的
			List<String> accountPurpose = accountAppYamaGataInitVO.getAccountPurpose();
			Collections.sort(accountPurpose);
			String accountPurposeOther = "";
			for (int count = 0; count < accountPurpose.size(); count++) {
				switch (accountPurpose.get(count)) {
				case "01":
					accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_01 + ",";
					sheet.getRow(1337).getCell(14).setCellValue("○");
					break;
				case "02":
					accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_02 + ",";
					sheet.getRow(1345).getCell(14).setCellValue("○");
					break;
				case "03":
					accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_03 + ",";
					sheet.getRow(1353).getCell(14).setCellValue("○");
					break;
				case "04":
					accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_04 + ",";
					sheet.getRow(1361).getCell(14).setCellValue("○");
					break;
				case "05":
					accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_05 + ",";
					sheet.getRow(1369).getCell(14).setCellValue("○");
					break;
				case "06":
					accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_06 + ",";
					sheet.getRow(1378).getCell(14).setCellValue("○");
					break;
				case "07":
					accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_07 + ",";
					sheet.getRow(1386).getCell(14).setCellValue("○");
					break;
				case "08":
					accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_08 + ",";
					sheet.getRow(1394).getCell(14).setCellValue("○");
					break;
				case "09":
					accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_09 + ",";
					sheet.getRow(1337).getCell(70).setCellValue("○");
					break;
				case "99":
					accountPurposeOther = accountPurposeOther + "その他（"
							+ accountAppYamaGataInitVO.getAccountPurposeOther() + "）,";
					sheet.getRow(1345).getCell(70).setCellValue("○");
					sheet.getRow(1358).getCell(80).setCellValue(accountAppYamaGataInitVO.getAccountPurposeOther());
				}
			}

			// おつとめ先
			String strWorkName = accountAppYamaGataInitVO.getWorkName();
			if (!strWorkName.isEmpty()) {
				// （表1）
				sheet.getRow(165).getCell(22).setCellValue(strWorkName);
				// （表2）
				sheet.getRow(672).getCell(22).setCellValue(strWorkName);
				// （表3）
				sheet.getRow(1177).getCell(22).setCellValue(strWorkName);
				// （表4）
				sheet.getRow(1695).getCell(22).setCellValue(strWorkName);
				// （表6）
				sheet.getRow(2214).getCell(22).setCellValue(strWorkName);
			}
			// 勤務先電話番号
			String strWorkIndex = "-";
			String strWorkTeleNumber = accountAppYamaGataInitVO.getWorkTeleNumber();
			if (!strWorkTeleNumber.isEmpty()) {
				if (strWorkTeleNumber.contains(strWorkIndex)) {
					int temp = strWorkTeleNumber.indexOf(strWorkIndex);
					int tempLast = strWorkTeleNumber.lastIndexOf(strWorkIndex);

					if (temp == tempLast) {
						strWorkTeleNumber = strWorkTeleNumber.replace("-", "");
						// （表1）
						sheet.getRow(164).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
						sheet.getRow(164).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
						sheet.getRow(164).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
						// （表2）
						sheet.getRow(671).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
						sheet.getRow(671).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
						sheet.getRow(671).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
						// （表3）
						sheet.getRow(1176).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
						sheet.getRow(1176).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
						sheet.getRow(1176).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
						// （表4）
						sheet.getRow(1694).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
						sheet.getRow(1694).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
						sheet.getRow(1694).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
						// （表6）
						sheet.getRow(2213).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
						sheet.getRow(2213).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
						sheet.getRow(2213).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
						
					} else {
						// （表1）
						sheet.getRow(164).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
						sheet.getRow(164).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(164).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
						// （表2）
						sheet.getRow(671).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
						sheet.getRow(671).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(671).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
						// （表3）
						sheet.getRow(1176).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
						sheet.getRow(1176).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(1176).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
						// （表4）
						sheet.getRow(1694).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
						sheet.getRow(1694).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(1694).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
						// （表6）
						sheet.getRow(2213).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
						sheet.getRow(2213).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
						sheet.getRow(2213).getCell(119)
								.setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
					}

				} else {
					// （表1）
					sheet.getRow(164).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
					sheet.getRow(164).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
					sheet.getRow(164).getCell(119)
							.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
					// （表2）
					sheet.getRow(671).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
					sheet.getRow(671).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
					sheet.getRow(671).getCell(119)
							.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
					// （表3）
					sheet.getRow(1176).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
					sheet.getRow(1176).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
					sheet.getRow(1176).getCell(119)
							.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
					// （表4）
					sheet.getRow(1694).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
					sheet.getRow(1694).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
					sheet.getRow(1694).getCell(119)
							.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
					// （表6）
					sheet.getRow(2213).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
					sheet.getRow(2213).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
					sheet.getRow(2213).getCell(119)
							.setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
				}
			}

			// カードデザイン
			// （表1）
			String strCardDesingKbn = accountAppYamaGataInitVO.getCardDesingKbn();
			if ("1".equals(strCardDesingKbn) || "2".equals(strCardDesingKbn)) {
				sheet.getRow(328).getCell(27).setCellValue(1);
				sheet.getRow(328).getCell(34).setCellValue(5);
			} else if ("3".equals(strCardDesingKbn) || "4".equals(strCardDesingKbn)) {
				sheet.getRow(328).getCell(27).setCellValue(1);
				sheet.getRow(328).getCell(34).setCellValue(0);
			} else {
				sheet.getRow(328).getCell(27).setCellValue(1);
				sheet.getRow(328).getCell(34).setCellValue(5);
			}

			// キャッシュカード暗証番号
			// （表1）
			String strSecurityPassword = accountAppYamaGataInitVO.getSecurityPassword();
			if (!strSecurityPassword.isEmpty() && strSecurityPassword.length() == 4) {
				if (Integer.parseInt(strSecurityPassword.substring(1, 2)) > 3) {
					String boutSecurityPassword = String
							.valueOf(Integer.parseInt(strSecurityPassword.substring(1, 2)) - 3);
					if ("10".equals(boutSecurityPassword)) {
						boutSecurityPassword = "0";
					}
					sheet.getRow(442).getCell(240).setCellValue(boutSecurityPassword);
				} else {
					String boutSecurityPassword = String
							.valueOf(Integer.parseInt(strSecurityPassword.substring(1, 2)) + 7);
					if ("10".equals(boutSecurityPassword)) {
						boutSecurityPassword = "0";
					}
					sheet.getRow(442).getCell(240).setCellValue(boutSecurityPassword);
				}

				if (Integer.parseInt(strSecurityPassword.substring(2, 3)) > 5) {
					String coutSecurityPassword = String
							.valueOf(Integer.parseInt(strSecurityPassword.substring(2, 3)) - 5);
					if ("10".equals(coutSecurityPassword)) {
						coutSecurityPassword = "0";
					}
					sheet.getRow(442).getCell(251).setCellValue(coutSecurityPassword);
				} else {
					String coutSecurityPassword = String
							.valueOf(Integer.parseInt(strSecurityPassword.substring(2, 3)) + 5);
					if ("10".equals(coutSecurityPassword)) {
						coutSecurityPassword = "0";
					}
					sheet.getRow(442).getCell(251).setCellValue(coutSecurityPassword);
				}

				if (Integer.parseInt(strSecurityPassword.substring(3)) > 7) {
					String doutSecurityPassword = String
							.valueOf(Integer.parseInt(strSecurityPassword.substring(3)) - 7);
					if ("10".equals(doutSecurityPassword)) {
						doutSecurityPassword = "0";
					}
					sheet.getRow(442).getCell(262).setCellValue(doutSecurityPassword);
				} else {
					String doutSecurityPassword = String
							.valueOf(Integer.parseInt(strSecurityPassword.substring(3)) + 3);
					if ("10".equals(doutSecurityPassword)) {
						doutSecurityPassword = "0";
					}
					sheet.getRow(442).getCell(262).setCellValue(doutSecurityPassword);
				}

				if (Integer.parseInt(strSecurityPassword.substring(0, 1)) > 2) {
					String aoutSecurityPassword = String
							.valueOf(Integer.parseInt(strSecurityPassword.substring(0, 1)) - 2);
					if ("10".equals(aoutSecurityPassword)) {
						aoutSecurityPassword = "0";
					}
					sheet.getRow(442).getCell(273).setCellValue(aoutSecurityPassword);
				} else {
					String aoutSecurityPassword = String
							.valueOf(Integer.parseInt(strSecurityPassword.substring(0, 1)) + 8);
					if ("10".equals(aoutSecurityPassword)) {
						aoutSecurityPassword = "0";
					}
					sheet.getRow(442).getCell(273).setCellValue(aoutSecurityPassword);
				}
			}

			 // １日あたりの振込・払込限度額
			 int creditlimit = accountAppYamaGataInitVO.getCreditlimit();
			 String strCreditlimit = "";
			 strCreditlimit = String.valueOf(creditlimit);
			 // （表4）
			 if (creditlimit < 10) {
			 sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(0,
			 1));
			 } else if (creditlimit < 100) {
			 sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(0,
			 1));
			 sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(1,
			 2));
			 } else if (creditlimit < 1000) {
			 sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(0,
			 1));
			 sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(1,
			 2));
			 sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(2,
			 3));
			 } else if (creditlimit < 10000) {
			 sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(0,
			 1));
			 sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(1,
			 2));
			 sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(2,
			 3));
			 sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(3,
			 4));
			 } else if (creditlimit < 100000) {
			 sheet.getRow(1724).getCell(31).setCellValue(strCreditlimit.substring(0,
			 1));
			 sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(1,
			 2));
			 sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(2,
			 3));
			 sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(3,
			 4));
			 sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(4,
			 5));
			 } else if (creditlimit < 1000000) {
			 sheet.getRow(1724).getCell(21).setCellValue(strCreditlimit.substring(0,
			 1));
			 sheet.getRow(1724).getCell(31).setCellValue(strCreditlimit.substring(1,
			 2));
			 sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(2,
			 3));
			 sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(3,
			 4));
			 sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(4,
			 5));
			 sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(5,
			 6));
			 } else if (creditlimit < 10000000) {
			 sheet.getRow(1724).getCell(11).setCellValue(strCreditlimit.substring(0,
			 1));
			 sheet.getRow(1724).getCell(21).setCellValue(strCreditlimit.substring(1,
			 2));
			 sheet.getRow(1724).getCell(31).setCellValue(strCreditlimit.substring(2,
			 3));
			 sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(3,
			 4));
			 sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(4,
			 5));
			 sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(5,
			 6));
			 sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(6,
			 7));
			} else {
				sheet.getRow(1724).getCell(1).setCellValue(strCreditlimit.substring(0, 1));
				sheet.getRow(1724).getCell(11).setCellValue(strCreditlimit.substring(1, 2));
				sheet.getRow(1724).getCell(21).setCellValue(strCreditlimit.substring(2, 3));
				sheet.getRow(1724).getCell(31).setCellValue(strCreditlimit.substring(3, 4));
				sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(4, 5));
				sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(5, 6));
				sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(6, 7));
				sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(7, 8));
			}

			// オンライン暗証番号
			String strOnlinePassword = accountAppYamaGataInitVO.getOnlinePassword();
			sheet.getRow(2130).getCell(253).setCellValue(strOnlinePassword.substring(0, 1));
			sheet.getRow(2130).getCell(263).setCellValue(strOnlinePassword.substring(1, 2));
			sheet.getRow(2130).getCell(273).setCellValue(strOnlinePassword.substring(2, 3));
			sheet.getRow(2130).getCell(283).setCellValue(strOnlinePassword.substring(3, 4));
			sheet.getRow(2130).getCell(293).setCellValue(strOnlinePassword.substring(4, 5));
			sheet.getRow(2130).getCell(303).setCellValue(strOnlinePassword.substring(5, 6));

			// 申込日(日付)
			String strDate = accountAppYamaGataInitVO.getApplicationDate();
			// （表1）
			// 申込日(日付)の年
			sheet.getRow(83).getCell(202).setCellValue(strDate.substring(2, 3));
			sheet.getRow(83).getCell(210).setCellValue(strDate.substring(3, 4));
			// 申込日(日付)の月
			sheet.getRow(83).getCell(218).setCellValue(strDate.substring(5, 6));
			sheet.getRow(83).getCell(226).setCellValue(strDate.substring(6, 7));
			// 申込日(日付)の日
			sheet.getRow(83).getCell(234).setCellValue(strDate.substring(8, 9));
			sheet.getRow(83).getCell(242).setCellValue(strDate.substring(9, 10));
			// （表3）
			// 申込日(日付)の年
			sheet.getRow(1095).getCell(202).setCellValue(strDate.substring(2, 3));
			sheet.getRow(1095).getCell(210).setCellValue(strDate.substring(3, 4));
			// 申込日(日付)の月
			sheet.getRow(1095).getCell(218).setCellValue(strDate.substring(5, 6));
			sheet.getRow(1095).getCell(226).setCellValue(strDate.substring(6, 7));
			// 申込日(日付)の日
			sheet.getRow(1095).getCell(234).setCellValue(strDate.substring(8, 9));
			sheet.getRow(1095).getCell(242).setCellValue(strDate.substring(9, 10));
			// （表4）
			// 申込日(日付)の年
			sheet.getRow(1613).getCell(202).setCellValue(strDate.substring(2, 3));
			sheet.getRow(1613).getCell(210).setCellValue(strDate.substring(3, 4));
			// 申込日(日付)の月
			sheet.getRow(1613).getCell(218).setCellValue(strDate.substring(5, 6));
			sheet.getRow(1613).getCell(226).setCellValue(strDate.substring(6, 7));
			// 申込日(日付)の日
			sheet.getRow(1613).getCell(234).setCellValue(strDate.substring(8, 9));
			sheet.getRow(1613).getCell(242).setCellValue(strDate.substring(9, 10));
			// （表6）
			// 申込日(日付)の年
			sheet.getRow(2132).getCell(202).setCellValue(strDate.substring(2, 3));
			sheet.getRow(2132).getCell(210).setCellValue(strDate.substring(3, 4));
			// 申込日(日付)の月
			sheet.getRow(2132).getCell(218).setCellValue(strDate.substring(5, 6));
			sheet.getRow(2132).getCell(226).setCellValue(strDate.substring(6, 7));
			// 申込日(日付)の日
			sheet.getRow(2132).getCell(234).setCellValue(strDate.substring(8, 9));
			sheet.getRow(2132).getCell(242).setCellValue(strDate.substring(9, 10));

			// 端末機種
			String strModelName = accountAppYamaGataInitVO.getModelName();
			sheet.getRow(1514).getCell(90).setCellValue(strModelName);

			// OSバージョン
			String strOsVersion = accountAppYamaGataInitVO.getOsVersion();
			sheet.getRow(1521).getCell(90).setCellValue(strOsVersion);

			// アプリバージョン
			String strAppVersion = accountAppYamaGataInitVO.getAppVersion();
			sheet.getRow(1514).getCell(177).setCellValue(strAppVersion);

			// 口座開設申込同意日時
			SimpleDateFormat agreeDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
			String strAgreedOperationDate = accountAppYamaGataInitVO.getAgreedOperationDate();
			strAgreedOperationDate = agreeDateFormat.format(new Date());
			sheet.getRow(1521).getCell(177).setCellValue(strAgreedOperationDate);

			workBook.write(out);
			fis.close();
			out.flush();
			out.close();
			zipOutputStream.putNextEntry(new ZipEntry(strfileName));
			File file = new File(filePath + "/" + strfileName);
			FileInputStream fisFile = new FileInputStream(file);
			int len;
			byte[] buffer = new byte[1024];
			while ((len = fisFile.read(buffer)) > 0) {

				zipOutputStream.write(buffer, 0, len);

			}
			zipOutputStream.closeEntry();

			fisFile.close();
		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void HyakujyushiExcelOutput(HttpServletRequest req, AccountApp114InitVO accountAppYamaGataInitVO,
            ZipOutputStream zipOutputStream) {

        Workbook workBook = null;
        // 帳票テンプレートフォルダーを取得
        String excelPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_EXCEL_PATH);
        // 当日日付を取得する（日付フォーマット）
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String createDate = sdf.format(new Date());
        // 作成フォルダーを取得
        String filePath = req.getServletContext()
                .getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH + ApplicationKeys.REPORT_TEMP_PATH);
        try {
            // 受付番号
            String accountAppSeq = accountAppYamaGataInitVO.getAccountAppSeq();
            FileInputStream fis = new FileInputStream(excelPath + "/" + ApplicationKeys.ACCOUNTAPPLIST108_REPORT);
            workBook = new XSSFWorkbook(fis);
            String strfileName = accountAppSeq + "_" + createDate + ".xlsx";
            OutputStream out = new FileOutputStream(filePath + "/" + strfileName);
            Sheet sheet = workBook.getSheetAt(0);

            // おなまえ（漢字）
            String strLastName = accountAppYamaGataInitVO.getLastName();
            String strFirstName = accountAppYamaGataInitVO.getFirstName();
            // （表1）
            sheet.getRow(107).getCell(22).setCellValue(strLastName + " " + strFirstName);
            // （表2）
            sheet.getRow(614).getCell(22).setCellValue(strLastName + " " + strFirstName);
            // （表3）
            sheet.getRow(1119).getCell(22).setCellValue(strLastName + " " + strFirstName);
            // （表4）
            sheet.getRow(1637).getCell(22).setCellValue(strLastName + " " + strFirstName);
            // （表6）
            sheet.getRow(2156).getCell(22).setCellValue(strLastName + " " + strFirstName);

            // お名前（フリガナ）
            String strKanaLastName = accountAppYamaGataInitVO.getKanaLastName();
            String strKanaFirstName = accountAppYamaGataInitVO.getKanaFirstName();
            // （表1）
            sheet.getRow(98).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);
            // （表2）
            sheet.getRow(605).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);
            // （表3）
            sheet.getRow(1110).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);
            // （表4）
            sheet.getRow(1628).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);
            // （表6）
            sheet.getRow(2147).getCell(22).setCellValue(strKanaLastName + " " + strKanaFirstName);

            
            // 普通預金の種類
            String strAccountType = accountAppYamaGataInitVO.getAccountType();
            // （表1）
            sheet.getRow(301).getCell(64).setCellValue(strAccountType.substring(0, 1));
            sheet.getRow(301).getCell(71).setCellValue(strAccountType.substring(1, 2));

            // 性別
            String strSex = accountAppYamaGataInitVO.getSexKbn();
            if ("1".equals(strSex)) {
                // （表1）
                sheet.getRow(161).getCell(157).setCellValue("✔");
                // （表2）
                sheet.getRow(668).getCell(157).setCellValue("✔");
                // （表3）
                sheet.getRow(1173).getCell(157).setCellValue("✔");
            } else if ("2".equals(strSex)) {
                // （表1）
                sheet.getRow(161).getCell(182).setCellValue("✔");
                // （表2）
                sheet.getRow(668).getCell(182).setCellValue("✔");
                // （表3）
                sheet.getRow(1173).getCell(182).setCellValue("✔");
            }

            // 郵便番号
            String strPostCode = accountAppYamaGataInitVO.getPostCode();
            // （表1）
            sheet.getRow(69).getCell(29).setCellValue(strPostCode.substring(0, 1));
            sheet.getRow(69).getCell(39).setCellValue(strPostCode.substring(1, 2));
            sheet.getRow(69).getCell(49).setCellValue(strPostCode.substring(2, 3));
            sheet.getRow(69).getCell(63).setCellValue(strPostCode.substring(4, 5));
            sheet.getRow(69).getCell(73).setCellValue(strPostCode.substring(5, 6));
            sheet.getRow(69).getCell(83).setCellValue(strPostCode.substring(6, 7));
            sheet.getRow(69).getCell(93).setCellValue(strPostCode.substring(7, 8));
            // （表2）
            sheet.getRow(576).getCell(29).setCellValue(strPostCode.substring(0, 1));
            sheet.getRow(576).getCell(39).setCellValue(strPostCode.substring(1, 2));
            sheet.getRow(576).getCell(49).setCellValue(strPostCode.substring(2, 3));
            sheet.getRow(576).getCell(63).setCellValue(strPostCode.substring(4, 5));
            sheet.getRow(576).getCell(73).setCellValue(strPostCode.substring(5, 6));
            sheet.getRow(576).getCell(83).setCellValue(strPostCode.substring(6, 7));
            sheet.getRow(576).getCell(93).setCellValue(strPostCode.substring(7, 8));
            // （表3）
            sheet.getRow(1081).getCell(29).setCellValue(strPostCode.substring(0, 1));
            sheet.getRow(1081).getCell(39).setCellValue(strPostCode.substring(1, 2));
            sheet.getRow(1081).getCell(49).setCellValue(strPostCode.substring(2, 3));
            sheet.getRow(1081).getCell(63).setCellValue(strPostCode.substring(4, 5));
            sheet.getRow(1081).getCell(73).setCellValue(strPostCode.substring(5, 6));
            sheet.getRow(1081).getCell(83).setCellValue(strPostCode.substring(6, 7));
            sheet.getRow(1081).getCell(93).setCellValue(strPostCode.substring(7, 8));
            // （表4）
            sheet.getRow(1599).getCell(29).setCellValue(strPostCode.substring(0, 1));
            sheet.getRow(1599).getCell(39).setCellValue(strPostCode.substring(1, 2));
            sheet.getRow(1599).getCell(49).setCellValue(strPostCode.substring(2, 3));
            sheet.getRow(1599).getCell(63).setCellValue(strPostCode.substring(4, 5));
            sheet.getRow(1599).getCell(73).setCellValue(strPostCode.substring(5, 6));
            sheet.getRow(1599).getCell(83).setCellValue(strPostCode.substring(6, 7));
            sheet.getRow(1599).getCell(93).setCellValue(strPostCode.substring(7, 8));
            // （表6）
            sheet.getRow(2118).getCell(29).setCellValue(strPostCode.substring(0, 1));
            sheet.getRow(2118).getCell(39).setCellValue(strPostCode.substring(1, 2));
            sheet.getRow(2118).getCell(49).setCellValue(strPostCode.substring(2, 3));
            sheet.getRow(2118).getCell(63).setCellValue(strPostCode.substring(4, 5));
            sheet.getRow(2118).getCell(73).setCellValue(strPostCode.substring(5, 6));
            sheet.getRow(2118).getCell(83).setCellValue(strPostCode.substring(6, 7));
            sheet.getRow(2118).getCell(93).setCellValue(strPostCode.substring(7, 8));

            // おところ
            String strAddress = accountAppYamaGataInitVO.getAddress();
            // 都道府県
            switch (accountAppYamaGataInitVO.getPrefecturesCode()) {
            case "1":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_1 + strAddress);
                break;
            case "2":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_2 + strAddress);
                break;
            case "3":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_3 + strAddress);
                break;
            case "4":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_4 + strAddress);
                break;
            case "5":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_5 + strAddress);
                break;
            case "6":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_6 + strAddress);
                break;
            case "7":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_7 + strAddress);
                break;
            case "8":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_8 + strAddress);
                break;
            case "9":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_9 + strAddress);
                break;
            case "10":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_10 + strAddress);
                break;
            case "11":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_11 + strAddress);
                break;
            case "12":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_12 + strAddress);
                break;
            case "13":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_13 + strAddress);
                break;
            case "14":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_14 + strAddress);
                break;
            case "15":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_15 + strAddress);
                break;
            case "16":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_16 + strAddress);
                break;
            case "17":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_17 + strAddress);
                break;
            case "18":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_18 + strAddress);
                break;
            case "19":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_19 + strAddress);
                break;
            case "20":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_20 + strAddress);
                break;
            case "21":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_21 + strAddress);
                break;
            case "22":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_22 + strAddress);
                break;
            case "23":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_23 + strAddress);
                break;
            case "24":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_24 + strAddress);
                break;
            case "25":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_25 + strAddress);
                break;
            case "26":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_26 + strAddress);
                break;
            case "27":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_27 + strAddress);
                break;
            case "28":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_28 + strAddress);
                break;
            case "29":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_29 + strAddress);
                break;
            case "30":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_30 + strAddress);
                break;
            case "31":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_31 + strAddress);
                break;
            case "32":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_32 + strAddress);
                break;
            case "33":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_33 + strAddress);
                break;
            case "34":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_34 + strAddress);
                break;
            case "35":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_35 + strAddress);
                break;
            case "36":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_36 + strAddress);
                break;
            case "37":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_37 + strAddress);
                break;
            case "38":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_38 + strAddress);
                break;
            case "39":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_39 + strAddress);
                break;
            case "40":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_40 + strAddress);
                break;
            case "41":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_41 + strAddress);
                break;
            case "42":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_42 + strAddress);
                break;
            case "43":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_43 + strAddress);
                break;
            case "44":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_44 + strAddress);
                break;
            case "45":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_45 + strAddress);
                break;
            case "46":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_46 + strAddress);
                break;
            case "47":
                // （表1）
                sheet.getRow(84).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
                // （表2）
                sheet.getRow(590).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
                // （表3）
                sheet.getRow(1095).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
                // （表4）
                sheet.getRow(1613).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
                // （表6）
                sheet.getRow(2132).getCell(22).setCellValue(Constants.PREFETURESCODE_47 + strAddress);
            }

            // 自宅電話
            String strTelIndex = "-";
            String strTeleNumber = accountAppYamaGataInitVO.getTeleNumber();
            if (!strTeleNumber.isEmpty()) {
                if (strTeleNumber.contains(strTelIndex)) {
                    int temp = strTeleNumber.indexOf(strTelIndex);
                    int tempLast = strTeleNumber.lastIndexOf(strTelIndex);

                    if (temp == tempLast) {
                        strTeleNumber = strTeleNumber.replace("-", "");
                        // （表1）
                        sheet.getRow(67).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                        sheet.getRow(67).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                        sheet.getRow(67).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                        // （表2）
                        sheet.getRow(574).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                        sheet.getRow(574).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                        sheet.getRow(574).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                        // （表3）
                        sheet.getRow(1079).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                        sheet.getRow(1079).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                        sheet.getRow(1079).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                        // （表4）
                        sheet.getRow(1597).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                        sheet.getRow(1597).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                        sheet.getRow(1597).getCell(180)
                                .setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                        // （表6）
                        sheet.getRow(2116).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                        sheet.getRow(2116).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                        sheet.getRow(2116).getCell(180)
                                .setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                    } else {
                        // （表1）
                        sheet.getRow(67).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
                        sheet.getRow(67).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(67).getCell(180)
                                .setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
                        // （表2）
                        sheet.getRow(574).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
                        sheet.getRow(574).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(574).getCell(180)
                                .setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
                        // （表3）
                        sheet.getRow(1079).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
                        sheet.getRow(1079).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(1079).getCell(180)
                                .setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
                        // （表4）
                        sheet.getRow(1597).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
                        sheet.getRow(1597).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(1597).getCell(180)
                                .setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
                        // （表6）
                        sheet.getRow(2116).getCell(128).setCellValue(strTeleNumber.substring(0, temp));
                        sheet.getRow(2116).getCell(154).setCellValue(strTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(2116).getCell(180)
                                .setCellValue(strTeleNumber.substring(tempLast + 1, strTeleNumber.length()));
                    }
                } else {
                    // （表1）
                    sheet.getRow(67).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                    sheet.getRow(67).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                    sheet.getRow(67).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                    // （表2）
                    sheet.getRow(574).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                    sheet.getRow(574).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                    sheet.getRow(574).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                    // （表3）
                    sheet.getRow(1079).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                    sheet.getRow(1079).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                    sheet.getRow(1079).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                    // （表4）
                    sheet.getRow(1597).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                    sheet.getRow(1597).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                    sheet.getRow(1597).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                    // （表6）
                    sheet.getRow(2116).getCell(128).setCellValue(strTeleNumber.substring(0, 3));
                    sheet.getRow(2116).getCell(154).setCellValue(strTeleNumber.substring(3, 7));
                    sheet.getRow(2116).getCell(180).setCellValue(strTeleNumber.substring(7, strTeleNumber.length()));
                }
            }

            // 携帯電話
            String strPhIndex = "-";
            String strPhoneNumber = accountAppYamaGataInitVO.getPhoneNumber();
            if (!strPhoneNumber.isEmpty()) {
                if (strPhoneNumber.contains(strPhIndex)) {
                    int temp = strPhoneNumber.indexOf(strPhIndex);
                    int tempLast = strPhoneNumber.lastIndexOf(strPhIndex);

                    if (temp == tempLast) {
                        strPhoneNumber = strPhoneNumber.replace("-", "");
                        // （表1）
                        sheet.getRow(75).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                        sheet.getRow(75).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                        sheet.getRow(75).getCell(180)
                                .setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                        // （表2）
                        sheet.getRow(582).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                        sheet.getRow(582).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                        sheet.getRow(582).getCell(180)
                                .setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                        // （表3）
                        sheet.getRow(1087).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                        sheet.getRow(1087).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                        sheet.getRow(1087).getCell(180)
                                .setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                        // （表4）
                        sheet.getRow(1605).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                        sheet.getRow(1605).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                        sheet.getRow(1605).getCell(180)
                                .setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                        // （表6）
                        sheet.getRow(2124).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                        sheet.getRow(2124).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                        sheet.getRow(2124).getCell(180)
                                .setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                    } else {
                        // （表1）
                        sheet.getRow(75).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
                        sheet.getRow(75).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
                        sheet.getRow(75).getCell(180)
                                .setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
                        // （表2）
                        sheet.getRow(582).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
                        sheet.getRow(582).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
                        sheet.getRow(582).getCell(180)
                                .setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
                        // （表3）
                        sheet.getRow(1087).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
                        sheet.getRow(1087).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
                        sheet.getRow(1087).getCell(180)
                                .setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
                        // （表4）
                        sheet.getRow(1605).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
                        sheet.getRow(1605).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
                        sheet.getRow(1605).getCell(180)
                                .setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
                        // （表6）
                        sheet.getRow(2124).getCell(128).setCellValue(strPhoneNumber.substring(0, temp));
                        sheet.getRow(2124).getCell(154).setCellValue(strPhoneNumber.substring(temp + 1, tempLast));
                        sheet.getRow(2124).getCell(180)
                                .setCellValue(strPhoneNumber.substring(tempLast + 1, strPhoneNumber.length()));
                    }
                } else {
                    // （表1）
                    sheet.getRow(75).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                    sheet.getRow(75).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                    sheet.getRow(75).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                    // （表2）
                    sheet.getRow(582).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                    sheet.getRow(582).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                    sheet.getRow(582).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                    // （表3）
                    sheet.getRow(1087).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                    sheet.getRow(1087).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                    sheet.getRow(1087).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                    // （表4）
                    sheet.getRow(1605).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                    sheet.getRow(1605).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                    sheet.getRow(1605).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                    // （表6）
                    sheet.getRow(2124).getCell(128).setCellValue(strPhoneNumber.substring(0, 3));
                    sheet.getRow(2124).getCell(154).setCellValue(strPhoneNumber.substring(3, 7));
                    sheet.getRow(2124).getCell(180).setCellValue(strPhoneNumber.substring(7, strPhoneNumber.length()));
                }
            }

            // ご職業
            List<String> jobKbn = accountAppYamaGataInitVO.getJobKbn();
            Collections.sort(jobKbn);
            String jobKbnOther = "";
            for (int count = 0; count < jobKbn.size(); count++) {
                String strCount = "";
                strCount = jobKbn.get(count);
                switch (strCount) {
                case "01":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_01 + ",";
                    sheet.getRow(1345).getCell(151).setCellValue("○");
                    break;
                case "02":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_02 + ",";
                    sheet.getRow(1357).getCell(151).setCellValue("○");
                    break;
                case "03":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_03 + ",";
                    sheet.getRow(1369).getCell(151).setCellValue("○");
                    break;
                case "04":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_04 + ",";
                    sheet.getRow(1381).getCell(151).setCellValue("○");
                    break;
                case "05":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_05 + ",";
                    sheet.getRow(1393).getCell(151).setCellValue("○");
                    break;
                case "06":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_06 + ",";
                    sheet.getRow(1345).getCell(199).setCellValue("○");
                    break;
                case "07":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_07 + ",";
                    sheet.getRow(1357).getCell(199).setCellValue("○");
                    break;
                case "08":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_08 + ",";
                    sheet.getRow(1369).getCell(199).setCellValue("○");
                    break;
                case "09":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_09 + ",";
                    sheet.getRow(1381).getCell(199).setCellValue("○");
                    break;
                case "10":
                    jobKbnOther = jobKbnOther + Constants.JOBKBN_10 + ",";
                    sheet.getRow(1393).getCell(199).setCellValue("○");
                    break;
                case "49":
                    jobKbnOther = jobKbnOther + "その他（" + accountAppYamaGataInitVO.getJobKbnOther() + "）,";
                    sheet.getRow(1345).getCell(244).setCellValue("○");
                    sheet.getRow(1356).getCell(246).setCellValue(accountAppYamaGataInitVO.getJobKbnOther());
                }
            }

            // 取引目的
            List<String> accountPurpose = accountAppYamaGataInitVO.getAccountPurpose();
            Collections.sort(accountPurpose);
            String accountPurposeOther = "";
            for (int count = 0; count < accountPurpose.size(); count++) {
                switch (accountPurpose.get(count)) {
                case "01":
                    accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_01 + ",";
                    sheet.getRow(1337).getCell(14).setCellValue("○");
                    break;
                case "02":
                    accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_02 + ",";
                    sheet.getRow(1345).getCell(14).setCellValue("○");
                    break;
                case "03":
                    accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_03 + ",";
                    sheet.getRow(1353).getCell(14).setCellValue("○");
                    break;
                case "04":
                    accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_04 + ",";
                    sheet.getRow(1361).getCell(14).setCellValue("○");
                    break;
                case "05":
                    accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_05 + ",";
                    sheet.getRow(1369).getCell(14).setCellValue("○");
                    break;
                case "06":
                    accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_06 + ",";
                    sheet.getRow(1378).getCell(14).setCellValue("○");
                    break;
                case "07":
                    accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_07 + ",";
                    sheet.getRow(1386).getCell(14).setCellValue("○");
                    break;
                case "08":
                    accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_08 + ",";
                    sheet.getRow(1394).getCell(14).setCellValue("○");
                    break;
                case "09":
                    accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_09 + ",";
                    sheet.getRow(1337).getCell(70).setCellValue("○");
                    break;
                case "99":
                    accountPurposeOther = accountPurposeOther + "その他（"
                            + accountAppYamaGataInitVO.getAccountPurposeOther() + "）,";
                    sheet.getRow(1345).getCell(70).setCellValue("○");
                    sheet.getRow(1358).getCell(80).setCellValue(accountAppYamaGataInitVO.getAccountPurposeOther());
                }
            }

            // おつとめ先
            String strWorkName = accountAppYamaGataInitVO.getWorkName();
            if (!strWorkName.isEmpty()) {
                // （表1）
                sheet.getRow(165).getCell(22).setCellValue(strWorkName);
                // （表2）
                sheet.getRow(672).getCell(22).setCellValue(strWorkName);
                // （表3）
                sheet.getRow(1177).getCell(22).setCellValue(strWorkName);
                // （表4）
                sheet.getRow(1695).getCell(22).setCellValue(strWorkName);
                // （表6）
                sheet.getRow(2214).getCell(22).setCellValue(strWorkName);
            }
            // 勤務先電話番号
            String strWorkIndex = "-";
            String strWorkTeleNumber = accountAppYamaGataInitVO.getWorkTeleNumber();
            if (!strWorkTeleNumber.isEmpty()) {
                if (strWorkTeleNumber.contains(strWorkIndex)) {
                    int temp = strWorkTeleNumber.indexOf(strWorkIndex);
                    int tempLast = strWorkTeleNumber.lastIndexOf(strWorkIndex);

                    if (temp == tempLast) {
                        strWorkTeleNumber = strWorkTeleNumber.replace("-", "");
                        // （表1）
                        sheet.getRow(164).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                        sheet.getRow(164).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                        sheet.getRow(164).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                        // （表2）
                        sheet.getRow(671).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                        sheet.getRow(671).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                        sheet.getRow(671).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                        // （表3）
                        sheet.getRow(1176).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                        sheet.getRow(1176).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                        sheet.getRow(1176).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                        // （表4）
                        sheet.getRow(1694).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                        sheet.getRow(1694).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                        sheet.getRow(1694).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                        // （表6）
                        sheet.getRow(2213).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                        sheet.getRow(2213).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                        sheet.getRow(2213).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                        
                    } else {
                        // （表1）
                        sheet.getRow(164).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
                        sheet.getRow(164).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(164).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
                        // （表2）
                        sheet.getRow(671).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
                        sheet.getRow(671).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(671).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
                        // （表3）
                        sheet.getRow(1176).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
                        sheet.getRow(1176).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(1176).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
                        // （表4）
                        sheet.getRow(1694).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
                        sheet.getRow(1694).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(1694).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
                        // （表6）
                        sheet.getRow(2213).getCell(77).setCellValue(strWorkTeleNumber.substring(0, temp));
                        sheet.getRow(2213).getCell(98).setCellValue(strWorkTeleNumber.substring(temp + 1, tempLast));
                        sheet.getRow(2213).getCell(119)
                                .setCellValue(strWorkTeleNumber.substring(tempLast + 1, strWorkTeleNumber.length()));
                    }

                } else {
                    // （表1）
                    sheet.getRow(164).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                    sheet.getRow(164).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                    sheet.getRow(164).getCell(119)
                            .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                    // （表2）
                    sheet.getRow(671).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                    sheet.getRow(671).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                    sheet.getRow(671).getCell(119)
                            .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                    // （表3）
                    sheet.getRow(1176).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                    sheet.getRow(1176).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                    sheet.getRow(1176).getCell(119)
                            .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                    // （表4）
                    sheet.getRow(1694).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                    sheet.getRow(1694).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                    sheet.getRow(1694).getCell(119)
                            .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                    // （表6）
                    sheet.getRow(2213).getCell(77).setCellValue(strWorkTeleNumber.substring(0, 3));
                    sheet.getRow(2213).getCell(98).setCellValue(strWorkTeleNumber.substring(3, 7));
                    sheet.getRow(2213).getCell(119)
                            .setCellValue(strWorkTeleNumber.substring(7, strWorkTeleNumber.length()));
                }
            }

            // カードデザイン
            // （表1）
            String strCardDesingKbn = accountAppYamaGataInitVO.getCardType();
            if ("1".equals(strCardDesingKbn) || "2".equals(strCardDesingKbn)) {
                sheet.getRow(328).getCell(27).setCellValue(1);
                sheet.getRow(328).getCell(34).setCellValue(5);
            } else if ("3".equals(strCardDesingKbn) || "4".equals(strCardDesingKbn)) {
                sheet.getRow(328).getCell(27).setCellValue(1);
                sheet.getRow(328).getCell(34).setCellValue(0);
            } else {
                sheet.getRow(328).getCell(27).setCellValue(1);
                sheet.getRow(328).getCell(34).setCellValue(5);
            }

            // キャッシュカード暗証番号
            // （表1）
            String strSecurityPassword = accountAppYamaGataInitVO.getSecurityPassword();
            if (!strSecurityPassword.isEmpty() && strSecurityPassword.length() == 4) {
                if (Integer.parseInt(strSecurityPassword.substring(1, 2)) > 3) {
                    String boutSecurityPassword = String
                            .valueOf(Integer.parseInt(strSecurityPassword.substring(1, 2)) - 3);
                    if ("10".equals(boutSecurityPassword)) {
                        boutSecurityPassword = "0";
                    }
                    sheet.getRow(442).getCell(240).setCellValue(boutSecurityPassword);
                } else {
                    String boutSecurityPassword = String
                            .valueOf(Integer.parseInt(strSecurityPassword.substring(1, 2)) + 7);
                    if ("10".equals(boutSecurityPassword)) {
                        boutSecurityPassword = "0";
                    }
                    sheet.getRow(442).getCell(240).setCellValue(boutSecurityPassword);
                }

                if (Integer.parseInt(strSecurityPassword.substring(2, 3)) > 5) {
                    String coutSecurityPassword = String
                            .valueOf(Integer.parseInt(strSecurityPassword.substring(2, 3)) - 5);
                    if ("10".equals(coutSecurityPassword)) {
                        coutSecurityPassword = "0";
                    }
                    sheet.getRow(442).getCell(251).setCellValue(coutSecurityPassword);
                } else {
                    String coutSecurityPassword = String
                            .valueOf(Integer.parseInt(strSecurityPassword.substring(2, 3)) + 5);
                    if ("10".equals(coutSecurityPassword)) {
                        coutSecurityPassword = "0";
                    }
                    sheet.getRow(442).getCell(251).setCellValue(coutSecurityPassword);
                }

                if (Integer.parseInt(strSecurityPassword.substring(3)) > 7) {
                    String doutSecurityPassword = String
                            .valueOf(Integer.parseInt(strSecurityPassword.substring(3)) - 7);
                    if ("10".equals(doutSecurityPassword)) {
                        doutSecurityPassword = "0";
                    }
                    sheet.getRow(442).getCell(262).setCellValue(doutSecurityPassword);
                } else {
                    String doutSecurityPassword = String
                            .valueOf(Integer.parseInt(strSecurityPassword.substring(3)) + 3);
                    if ("10".equals(doutSecurityPassword)) {
                        doutSecurityPassword = "0";
                    }
                    sheet.getRow(442).getCell(262).setCellValue(doutSecurityPassword);
                }

                if (Integer.parseInt(strSecurityPassword.substring(0, 1)) > 2) {
                    String aoutSecurityPassword = String
                            .valueOf(Integer.parseInt(strSecurityPassword.substring(0, 1)) - 2);
                    if ("10".equals(aoutSecurityPassword)) {
                        aoutSecurityPassword = "0";
                    }
                    sheet.getRow(442).getCell(273).setCellValue(aoutSecurityPassword);
                } else {
                    String aoutSecurityPassword = String
                            .valueOf(Integer.parseInt(strSecurityPassword.substring(0, 1)) + 8);
                    if ("10".equals(aoutSecurityPassword)) {
                        aoutSecurityPassword = "0";
                    }
                    sheet.getRow(442).getCell(273).setCellValue(aoutSecurityPassword);
                }
            }

             // １日あたりの振込・払込限度額
             String limit = accountAppYamaGataInitVO.getCreditlimit();
             int creditlimit=Integer.parseInt(limit);
             String strCreditlimit = "";
             strCreditlimit = String.valueOf(creditlimit);
             // （表4）
             if (creditlimit < 10) {
             sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(0,
             1));
             } else if (creditlimit < 100) {
             sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(0,
             1));
             sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(1,
             2));
             } else if (creditlimit < 1000) {
             sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(0,
             1));
             sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(1,
             2));
             sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(2,
             3));
             } else if (creditlimit < 10000) {
             sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(0,
             1));
             sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(1,
             2));
             sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(2,
             3));
             sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(3,
             4));
             } else if (creditlimit < 100000) {
             sheet.getRow(1724).getCell(31).setCellValue(strCreditlimit.substring(0,
             1));
             sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(1,
             2));
             sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(2,
             3));
             sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(3,
             4));
             sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(4,
             5));
             } else if (creditlimit < 1000000) {
             sheet.getRow(1724).getCell(21).setCellValue(strCreditlimit.substring(0,
             1));
             sheet.getRow(1724).getCell(31).setCellValue(strCreditlimit.substring(1,
             2));
             sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(2,
             3));
             sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(3,
             4));
             sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(4,
             5));
             sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(5,
             6));
             } else if (creditlimit < 10000000) {
             sheet.getRow(1724).getCell(11).setCellValue(strCreditlimit.substring(0,
             1));
             sheet.getRow(1724).getCell(21).setCellValue(strCreditlimit.substring(1,
             2));
             sheet.getRow(1724).getCell(31).setCellValue(strCreditlimit.substring(2,
             3));
             sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(3,
             4));
             sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(4,
             5));
             sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(5,
             6));
             sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(6,
             7));
            } else {
                sheet.getRow(1724).getCell(1).setCellValue(strCreditlimit.substring(0, 1));
                sheet.getRow(1724).getCell(11).setCellValue(strCreditlimit.substring(1, 2));
                sheet.getRow(1724).getCell(21).setCellValue(strCreditlimit.substring(2, 3));
                sheet.getRow(1724).getCell(31).setCellValue(strCreditlimit.substring(3, 4));
                sheet.getRow(1724).getCell(41).setCellValue(strCreditlimit.substring(4, 5));
                sheet.getRow(1724).getCell(51).setCellValue(strCreditlimit.substring(5, 6));
                sheet.getRow(1724).getCell(61).setCellValue(strCreditlimit.substring(6, 7));
                sheet.getRow(1724).getCell(71).setCellValue(strCreditlimit.substring(7, 8));
            }

            // オンライン暗証番号
            String strOnlinePassword = accountAppYamaGataInitVO.getOnlinePassword();
            sheet.getRow(2130).getCell(253).setCellValue(strOnlinePassword.substring(0, 1));
            sheet.getRow(2130).getCell(263).setCellValue(strOnlinePassword.substring(1, 2));
            sheet.getRow(2130).getCell(273).setCellValue(strOnlinePassword.substring(2, 3));
            sheet.getRow(2130).getCell(283).setCellValue(strOnlinePassword.substring(3, 4));
            sheet.getRow(2130).getCell(293).setCellValue(strOnlinePassword.substring(4, 5));
            sheet.getRow(2130).getCell(303).setCellValue(strOnlinePassword.substring(5, 6));

            // 申込日(日付)
            String strDate = accountAppYamaGataInitVO.getApplicationDate();
            // （表1）
            // 申込日(日付)の年
            sheet.getRow(83).getCell(202).setCellValue(strDate.substring(2, 3));
            sheet.getRow(83).getCell(210).setCellValue(strDate.substring(3, 4));
            // 申込日(日付)の月
            sheet.getRow(83).getCell(218).setCellValue(strDate.substring(5, 6));
            sheet.getRow(83).getCell(226).setCellValue(strDate.substring(6, 7));
            // 申込日(日付)の日
            sheet.getRow(83).getCell(234).setCellValue(strDate.substring(8, 9));
            sheet.getRow(83).getCell(242).setCellValue(strDate.substring(9, 10));
            // （表3）
            // 申込日(日付)の年
            sheet.getRow(1095).getCell(202).setCellValue(strDate.substring(2, 3));
            sheet.getRow(1095).getCell(210).setCellValue(strDate.substring(3, 4));
            // 申込日(日付)の月
            sheet.getRow(1095).getCell(218).setCellValue(strDate.substring(5, 6));
            sheet.getRow(1095).getCell(226).setCellValue(strDate.substring(6, 7));
            // 申込日(日付)の日
            sheet.getRow(1095).getCell(234).setCellValue(strDate.substring(8, 9));
            sheet.getRow(1095).getCell(242).setCellValue(strDate.substring(9, 10));
            // （表4）
            // 申込日(日付)の年
            sheet.getRow(1613).getCell(202).setCellValue(strDate.substring(2, 3));
            sheet.getRow(1613).getCell(210).setCellValue(strDate.substring(3, 4));
            // 申込日(日付)の月
            sheet.getRow(1613).getCell(218).setCellValue(strDate.substring(5, 6));
            sheet.getRow(1613).getCell(226).setCellValue(strDate.substring(6, 7));
            // 申込日(日付)の日
            sheet.getRow(1613).getCell(234).setCellValue(strDate.substring(8, 9));
            sheet.getRow(1613).getCell(242).setCellValue(strDate.substring(9, 10));
            // （表6）
            // 申込日(日付)の年
            sheet.getRow(2132).getCell(202).setCellValue(strDate.substring(2, 3));
            sheet.getRow(2132).getCell(210).setCellValue(strDate.substring(3, 4));
            // 申込日(日付)の月
            sheet.getRow(2132).getCell(218).setCellValue(strDate.substring(5, 6));
            sheet.getRow(2132).getCell(226).setCellValue(strDate.substring(6, 7));
            // 申込日(日付)の日
            sheet.getRow(2132).getCell(234).setCellValue(strDate.substring(8, 9));
            sheet.getRow(2132).getCell(242).setCellValue(strDate.substring(9, 10));

            // 口座開設申込同意日時
            SimpleDateFormat agreeDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
            String strAgreedOperationDate = accountAppYamaGataInitVO.getReceiptDate();
            strAgreedOperationDate = agreeDateFormat.format(new Date());
            sheet.getRow(1521).getCell(177).setCellValue(strAgreedOperationDate);

            workBook.write(out);
            fis.close();
            out.flush();
            out.close();
            zipOutputStream.putNextEntry(new ZipEntry(strfileName));
            File file = new File(filePath + "/" + strfileName);
            FileInputStream fisFile = new FileInputStream(file);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fisFile.read(buffer)) > 0) {

                zipOutputStream.write(buffer, 0, len);

            }
            zipOutputStream.closeEntry();

            fisFile.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    
	}
}
