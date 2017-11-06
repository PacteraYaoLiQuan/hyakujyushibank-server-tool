package com.scsk.report;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.response.vo.AccountApp114DetailResVO;
import com.scsk.response.vo.AccountAppDetailResVO;
import com.scsk.response.vo.AccountAppYamaGataDetailResVO;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.ImageVO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import sun.misc.BASE64Decoder;

/**
 * 帳票出力サービス。<br>
 * <br>
 * 帳票出力ロジックを実装すること。<br>
 */
@Service
public class ReportBLogic {

	/**
	 * 帳票出力フォルダー作成メソッド。
	 * 
	 * @param req
	 *            request
	 * @param date
	 *            当日日付
	 */
	public void datePathCreate(HttpServletRequest req, String date) {
		// 帳票出力用のフォルダー
		String outputPath = req.getServletContext().getRealPath(
				ApplicationKeys.REPORT_PROTECTED_PATH);
		// 帳票出力用の詳細フォルダーを取得
		String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;

		// 帳票出力用の主フォルダーを作成
		String datePath = tempPath + date + "/";
		File file = new File(datePath);
		// 　フォルダー存在しない場合
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		} else {
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		}
	}

	/**
	 * 帳票出力メソッド(単一pdf/csvを出力)。
	 * 
	 * @param date
	 *            当日日付
	 * @param jasperFileName
	 *            帳票テンプレート名
	 * @param parameters
	 *            帳票表示用のパラメータ
	 * @param jrDataSource
	 *            帳票表示用のデータソース
	 * @param flg
	 *            帳票/CSV区分（1：帳票　2：CSV）
	 * @param req
	 *            request
	 * @param res
	 *            response
	 */
	public void reportCreate(String date, String jasperName,
			Map<String, Object> parameters, JRDataSource jrDataSource,
			String flg, String outputFileName, HttpServletRequest req,
			HttpServletResponse res) {

		try {
			// resourcesフォルダーを取得
			String resourcesPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_RESOURCES_PATH);
			// 帳票テンプレートフォルダーを取得
			String jasperPath = resourcesPath
					+ ApplicationKeys.REPORT_JASPER_PATH;
			// 帳票出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用のフォルダーを取得
			String printPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH
					+ date + "/";
			File file = new File(printPath);
			// フォルダー存在しない場合
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			// 帳票名を取得
			String jasperFileName = jasperPath + jasperName;
			// JasperReportオブジェクト生成
			JasperPrint jasperPrint = null;
			jasperPrint = ReportUtil.jasperCreate(jasperFileName, parameters,
					jrDataSource);
			// PDF拡張名を設定
			String[] fileName = jasperName.split("\\.");
			String pdfName = fileName[0] + Constants.PDF;
			// CSV拡張名を設定
			String csvName = fileName[0] + Constants.CSV;
			// 出力するPDFファイル名を設定
			String pdfFileName = printPath + pdfName;
			// 出力するCSVファイル名を設定
			String csvFileName = printPath + csvName;

			if (flg.equals("1")) {
				// PDFファイルをサーバに作成
				ReportUtil.pdfCreate(pdfFileName, jasperPrint);
				File pdfFile = new File(pdfFileName);
				if (pdfFile.exists()) {
					pdfFile.renameTo(new File(printPath + outputFileName
							+ Constants.PDF));
				}
			} else {
				// CSVファイルをサーバに作成
				ReportUtil.csvCreate(csvFileName, jasperPrint);
				File csvFile = new File(csvFileName);
				if (csvFile.exists()) {
					csvFile.renameTo(new File(printPath + outputFileName
							+ Constants.CSV));
				}
			}

		} catch (BusinessException be) {
			// 帳票出力失敗
			throw new BusinessException(be.getResultMessages());
		}
	}

	/**
	 * 帳票出力フォルダー作成メソッド。
	 * 
	 * @param req
	 *            request
	 * @param date
	 *            当日日付
	 */
	public void dateZipCreate(HttpServletRequest req, HttpServletResponse res,
			String date) {
		// 帳票出力用のフォルダー
		String outputPath = req.getServletContext().getRealPath(
				ApplicationKeys.REPORT_PROTECTED_PATH);
		// 帳票出力用の詳細フォルダーを取得
		String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;
		// 帳票出力用の主フォルダーを作成
		String datePath = tempPath + date + "/";

		// ZIPファイル作成
		File zipfile = new File(tempPath + date + Constants.ZIP);
		ReportUtil.zipFiles(datePath, zipfile);
	}

	/**
	 * 帳票/CSV出力メソッド。
	 * 
	 * @param date
	 *            当日日付
	 * @param key
	 *            出力ファイルのKeyファイル名
	 * @param jasperFileName
	 *            帳票/CSVテンプレート名
	 * @param parameters
	 *            帳票/CSV表示用のパラメータ
	 * @param jrDataSource
	 *            帳票/CSV表示用のデータソース
	 * @param flg
	 *            帳票/CSV区分（1：帳票　2：CSV）
	 * @param req
	 *            request
	 * @param res
	 *            response
	 */
	public void outputCreate(String date, String key, String jasperName,
			Map<String, Object> parameters, JRDataSource jrDataSource,
			String flg, String outputFileName, HttpServletRequest req,
			HttpServletResponse res) {

		try {
			// resourcesフォルダーを取得
			String resourcesPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_RESOURCES_PATH);
			// 帳票テンプレートフォルダーを取得
			String jasperPath = resourcesPath
					+ ApplicationKeys.REPORT_JASPER_PATH;
			// 帳票出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の日付フォルダーを取得
			String datePath = outputPath + ApplicationKeys.REPORT_TEMP_PATH
					+ date + "/";
			// Keyフォルダーを作成
			String printPath = datePath + key + "/";
			File file = new File(printPath);
			// フォルダー存在しない場合
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			parameters.put("url", jasperPath);
			// 帳票名を取得
			String jasperFileName = jasperPath + jasperName;
			// JasperReportオブジェクト生成
			JasperPrint jasperPrint = null;
			jasperPrint = ReportUtil.jasperCreate(jasperFileName, parameters,
					jrDataSource);
			// PDF拡張名を設定
			String[] fileName = jasperName.split("\\.");
			String pdfName = fileName[0] + Constants.PDF;
			// CSV拡張名を設定
			String csvName = fileName[0] + Constants.CSV;
			// 出力するPDFファイル名を設定
			String pdfFileName = printPath + pdfName;
			// 出力するCSVファイル名を設定
			String csvFileName = printPath + csvName;

			if (flg.equals("1")) {
				// PDFファイルをサーバに作成
				ReportUtil.pdfCreate(pdfFileName, jasperPrint);
				File pdfFile = new File(pdfFileName);
				if (pdfFile.exists()) {
					pdfFile.renameTo(new File(printPath + outputFileName
							+ Constants.PDF));
				}
			} else {
				// CSVファイルをサーバに作成
				ReportUtil.csvCreate(csvFileName, jasperPrint);
				File csvFile = new File(csvFileName);
				if (csvFile.exists()) {
					csvFile.renameTo(new File(printPath + outputFileName
							+ Constants.CSV));
				}
			}

		} catch (BusinessException be) {
			// 帳票出力失敗
			throw new BusinessException(be.getResultMessages());
		}
	}

	/**
	 * 画像帳票出力作成メソッド。
	 * 
	 * @param req
	 *            request
	 * @param date
	 *            当日日付
	 */

	public ImageVO ImageOutputSave(String date, AccountAppDetailResVO vo,
			HttpServletRequest req) {
		try {
			ImageVO imageVO = new ImageVO();
			
			// 帳票出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の日付フォルダーを取得
			String datePath = outputPath + ApplicationKeys.REPORT_TEMP_PATH
					+ date + "/";
			// Keyフォルダーを作成
			String pngPath = datePath + "png"
					+ "/";
			File file = new File(pngPath);
			// フォルダー存在しない場合
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}

			// 本人確認書類画像保存
			if (Utils.isNotNullAndEmpty(vo.getIdentificationImageBack())) {
				if (base64StringToImage(vo.getIdentificationImageBack(), pngPath,
						(vo.getAccountAppSeq() + "2")) == false){
					ResultMessages messages = ResultMessages.error().add(
							MessageKeys.E_CSV_1001);
					throw new BusinessException(messages);
				}else {
					imageVO.setUrl2(pngPath + vo.getAccountAppSeq() + "2"+ ".jpg");
				}
			} else {
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_CSV_1001);
				throw new BusinessException(messages);
			}
			if (Utils.isNotNullAndEmpty(vo.getIdentificationImage())) {
				if (base64StringToImage(vo.getIdentificationImage(), pngPath,
						(vo.getAccountAppSeq() + "1"))== false){
					ResultMessages messages = ResultMessages.error().add(
							MessageKeys.E_CSV_1001);
					throw new BusinessException(messages);
				}else {
					imageVO.setUrl1(pngPath + vo.getAccountAppSeq() + "1"+ ".jpg");
				}
			} else {
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_CSV_1001);
				throw new BusinessException(messages);
			}
			
			imageVO.setAccountAppSeq(vo.getAccountAppSeq());
			
			return imageVO;
		} catch (BusinessException be) {
			// 帳票出力失敗
			throw new BusinessException(be.getResultMessages());
		}
	}
	
	/**
	 * 画像帳票出力作成メソッド。
	 * 
	 * @param req
	 *            request
	 * @param date
	 *            当日日付
	 */

	public ImageVO ImageOutputSave2(String date, AccountAppYamaGataDetailResVO vo,
			HttpServletRequest req) {
		try {
			ImageVO imageVO = new ImageVO();
			
			// 帳票出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の日付フォルダーを取得
			String datePath = outputPath + ApplicationKeys.REPORT_TEMP_PATH
					+ date + "/";
			// Keyフォルダーを作成
			String pngPath = datePath + "png"
					+ "/";
			File file = new File(pngPath);
			// フォルダー存在しない場合
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}

			// 本人確認書類画像保存
			if (Utils.isNotNullAndEmpty(vo.getIdentificationImageBack())) {
				if (base64StringToImage(vo.getIdentificationImageBack(), pngPath,
						(vo.getAccountAppSeq() + "2")) == false){
					ResultMessages messages = ResultMessages.error().add(
							MessageKeys.E_CSV_1001);
					throw new BusinessException(messages);
				}else {
					imageVO.setUrl2(pngPath + vo.getAccountAppSeq() + "2"+ ".jpg");
				}
			} else {
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_CSV_1001);
				throw new BusinessException(messages);
			}
			if (Utils.isNotNullAndEmpty(vo.getIdentificationImage())) {
				if (base64StringToImage(vo.getIdentificationImage(), pngPath,
						(vo.getAccountAppSeq() + "1"))== false){
					ResultMessages messages = ResultMessages.error().add(
							MessageKeys.E_CSV_1001);
					throw new BusinessException(messages);
				}else {
					imageVO.setUrl1(pngPath + vo.getAccountAppSeq() + "1"+ ".jpg");
				}
			} else {
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_CSV_1001);
				throw new BusinessException(messages);
			}
			
			imageVO.setAccountAppSeq(vo.getAccountAppSeq());
			
			return imageVO;
		} catch (BusinessException be) {
			// 帳票出力失敗
			throw new BusinessException(be.getResultMessages());
		}
	}

	/**
     * 画像帳票出力作成メソッド。
     * 
     * @param req
     *            request
     * @param date
     *            当日日付
     */

    public ImageVO ImageOutputSave3(String date, AccountApp114DetailResVO vo,
            HttpServletRequest req) {
        try {
            ImageVO imageVO = new ImageVO();
            
            // 帳票出力用のフォルダー
            String outputPath = req.getServletContext().getRealPath(
                    ApplicationKeys.REPORT_PROTECTED_PATH);
            // 帳票出力用の日付フォルダーを取得
            String datePath = outputPath + ApplicationKeys.REPORT_TEMP_PATH
                    + date + "/";
            // Keyフォルダーを作成
            String pngPath = datePath + "png"
                    + "/";
            File file = new File(pngPath);
            // フォルダー存在しない場合
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }

            // 本人確認書類画像保存
            if (Utils.isNotNullAndEmpty(vo.getIdentificationImageBack())) {
                if (base64StringToImage(vo.getIdentificationImageBack(), pngPath,
                        (vo.getAccountAppSeq() + "2")) == false){
                    ResultMessages messages = ResultMessages.error().add(
                            MessageKeys.E_CSV_1001);
                    throw new BusinessException(messages);
                }else {
                    imageVO.setUrl2(pngPath + vo.getAccountAppSeq() + "2"+ ".jpg");
                }
            } else {
                ResultMessages messages = ResultMessages.error().add(
                        MessageKeys.E_CSV_1001);
                throw new BusinessException(messages);
            }
            if (Utils.isNotNullAndEmpty(vo.getIdentificationImage())) {
                if (base64StringToImage(vo.getIdentificationImage(), pngPath,
                        (vo.getAccountAppSeq() + "1"))== false){
                    ResultMessages messages = ResultMessages.error().add(
                            MessageKeys.E_CSV_1001);
                    throw new BusinessException(messages);
                }else {
                    imageVO.setUrl1(pngPath + vo.getAccountAppSeq() + "1"+ ".jpg");
                }
            } else {
                ResultMessages messages = ResultMessages.error().add(
                        MessageKeys.E_CSV_1001);
                throw new BusinessException(messages);
            }
            
            imageVO.setAccountAppSeq(vo.getAccountAppSeq());
            
            return imageVO;
        } catch (BusinessException be) {
            // 帳票出力失敗
            throw new BusinessException(be.getResultMessages());
        }
    }
	
	/**
	 * 画像帳票出力作成メソッド。
	 * 
	 * @param req
	 *            request
	 * @param date
	 *            当日日付
	 */

	public void ImageOutputCreate(String date,JRDataSource jrDataSource, String jasperName,
			String outputFileName, HttpServletRequest req) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			// resourcesフォルダーを取得
			String resourcesPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_RESOURCES_PATH);
			// 帳票テンプレートフォルダーを取得
			String jasperPath = resourcesPath
					+ ApplicationKeys.REPORT_JASPER_PATH;
			// 帳票出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の日付フォルダーを取得
			String datePath = outputPath + ApplicationKeys.REPORT_TEMP_PATH
					+ date + "/";
			// 帳票出力用の日付フォルダーを取得
			String datePath2 = outputPath + ApplicationKeys.REPORT_TEMP_PATH
					+ date + "-2/";
			// Keyフォルダーを作成
			String pngPath = datePath + "png"
					+ "/";
			File file = new File(pngPath);
			File file2 = new File(datePath2);
			// フォルダー存在しない場合
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			if (!file2.exists() && !file2.isDirectory()) {
				file2.mkdir();
			}

			// 帳票名を取得
			String jasperFileName = jasperPath + jasperName;
			
			// JasperReportオブジェクト生成
			JasperPrint jasperPrint = null;
			jasperPrint = ReportUtil.jasperCreate(jasperFileName, parameters,
					jrDataSource);
			// PDF拡張名を設定
			String[] fileName = jasperName.split("\\.");
			String pdfName = fileName[0] + Constants.PDF;

			// 出力するPDFファイル名を設定
			String printPath = datePath2;
			String pdfFileName = printPath + pdfName;

			// PDFファイルをサーバに作成
			ReportUtil.pdfCreate(pdfFileName, jasperPrint);
			File pdfFile = new File(pdfFileName);
			if (pdfFile.exists()) {
				pdfFile.renameTo(new File(printPath + outputFileName
						+ Constants.PDF));
			}
			ReportUtil.deleteDir(file);
		} catch (BusinessException be) {
			// 帳票出力失敗
			throw new BusinessException(be.getResultMessages());
		}
	}

	/**
	 * base64Stringは画像に保存するメソッド。
	 * 
	 * @param base64String
	 *            base64String
	 * @param path
	 *            保存パス
	 * @param name
	 *            画像名前
	 */
	public boolean base64StringToImage(String base64String, String path,
			String name) {
		boolean rtn = true;
		
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes1 = decoder.decodeBuffer(base64String);

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			BufferedImage bi1 = ImageIO.read(bais);
			File w2 = new File(path + "/" + name + ".jpg");
			ImageIO.write(bi1, "jpg", w2);
		} catch (Exception e) {
			rtn = false;
			e.printStackTrace();
		}
		
		return rtn;
	}

	/**
	 * ZIPファイルダウンロードメソッド。
	 * 
	 * @param req
	 *            request
	 * @param res
	 *            response
	 * @param date
	 *            当日日付
	 */
	public void zipDownLoad(HttpServletRequest req, HttpServletResponse res,
			String date) {
		// 帳票出力用のフォルダー
		String outputPath = req.getServletContext().getRealPath(
				ApplicationKeys.REPORT_PROTECTED_PATH);
		// 帳票出力用の詳細フォルダーを取得
		String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;

		// ZIPファイルダウンロード
		String zipName = date + Constants.ZIP;
		ReportUtil.downFile(res, tempPath, zipName);
	}
	
	/**
	 * CSVファイルダウンロードメソッド。
	 * 
	 * @param req
	 *            request
	 * @param res
	 *            response
	 * @param date
	 *            当日日付
	 */
	public void csvDownLoad(HttpServletRequest req, HttpServletResponse res,
			String date,String outputFileName) {
		// 帳票出力用のフォルダー
		String outputPath = req.getServletContext().getRealPath(
				ApplicationKeys.REPORT_PROTECTED_PATH);
		// 帳票出力用の詳細フォルダーを取得
		String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";

		// ZIPファイルダウンロード
		String csvName = outputFileName + Constants.CSV;
		ReportUtil.downFile(res, tempPath, csvName);
	}
	
	/**
	 * CSVファイルダウンロードメソッド。
	 * 
	 * @param req
	 *            request
	 * @param res
	 *            response
	 * @param date
	 *            当日日付
	 */
	public void excelDownLoad(HttpServletRequest req, HttpServletResponse res,
			String date) {
		// 帳票出力用のフォルダー
		String outputPath = req.getServletContext().getRealPath(
				ApplicationKeys.REPORT_PROTECTED_PATH);
		// 帳票出力用の詳細フォルダーを取得
		String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH  + "/";

		// ファイルダウンロード
		String excelName = date + Constants.XLSX;
		ReportUtil.downFile(res, tempPath, excelName);
	}
	

	/**
	 * PDFファイルを開けるメソッド。
	 * 
	 * @param req
	 *            request
	 * @param res
	 *            response
	 * @param pdfName
	 *            PDFファイル名
	 */
	public void pdfOpen(HttpServletRequest req, HttpServletResponse res,
			String date,String pdfName) {
		// 帳票出力用のフォルダー
		String outputPath = req.getServletContext().getRealPath(
				ApplicationKeys.REPORT_PROTECTED_PATH);
		// 帳票出力用の詳細フォルダーを取得
		String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;
		String path=tempPath+date;
 
		// PDFファイルを開ける
		ReportUtil.openFile(res, path, pdfName);
	}
	
	/**
	 * PDFファイルを開けるメソッド。
	 * 
	 * @param req
	 *            request
	 * @param res
	 *            response
	 * @param pdfName
	 *            PDFファイル名
	 */
	public void pdfOpen2(HttpServletRequest req, HttpServletResponse res,
			String date,String pdfName) {
		// 帳票出力用のフォルダー
		String outputPath = req.getServletContext().getRealPath(
				ApplicationKeys.REPORT_PROTECTED_PATH);
		// 帳票出力用の詳細フォルダーを取得
		String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;
		String path=tempPath+date + "-2/";
 
		// PDFファイルを開ける
		ReportUtil.openFile(res, path, pdfName);
	}
	
	/**
     * PDFファイルを開けるメソッド。
     * 
     * @param req
     *            request
     * @param res
     *            response
     * @param pdfName
     *            PDFファイル名
     */
    public void pdfOpen4(HttpServletRequest req, HttpServletResponse res,
            String date,String pdfName) {
        // 帳票出力用のフォルダー
        String outputPath = req.getServletContext().getRealPath(
                ApplicationKeys.REPORT_PROTECTED_PATH);
        // 帳票出力用の詳細フォルダーを取得
        String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;
        String path=tempPath+date;
 
        // PDFファイルを開ける
        ReportUtil.openFile(res, path, pdfName);
    }
    
    
    /**
     * PDFファイルを開けるメソッド。
     * 
     * @param req
     *            request
     * @param res
     *            response
     * @param pdfName
     *            PDFファイル名
     */
    public void pdfOpen5(HttpServletRequest req, HttpServletResponse res,
            String date,String pdfName) {
        // 帳票出力用のフォルダー
        String outputPath = req.getServletContext().getRealPath(
                ApplicationKeys.REPORT_PROTECTED_PATH);
        // 帳票出力用の詳細フォルダーを取得
        String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;
        String path=tempPath+date;
 
        // PDFファイルを開ける
        ReportUtil.openFile(res, path, pdfName);
    }
	
	/**
	 * 帳票出力ボタンCSV出力メソッド。
	 * 
	 * @param date
	 *            当日日付
	 * @param jasperFileName
	 *            帳票/CSVテンプレート名
	 * @param parameters
	 *            帳票/CSV表示用のパラメータ
	 * @param jrDataSource
	 *            帳票/CSV表示用のデータソース
	 * @param req
	 *            request
	 * @param res
	 *            response
	 */
	public void outputCsvCreate(String date, String jasperName,
			Map<String, Object> parameters, JRDataSource jrDataSource,
			String outputFileName, HttpServletRequest req,
			HttpServletResponse res) {

		try {
			// resourcesフォルダーを取得
			String resourcesPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_RESOURCES_PATH);
			// 帳票テンプレートフォルダーを取得
			String jasperPath = resourcesPath
					+ ApplicationKeys.REPORT_JASPER_PATH;
			// 帳票出力用のフォルダー
			String outputPath = req.getServletContext().getRealPath(
					ApplicationKeys.REPORT_PROTECTED_PATH);
			// 帳票出力用の日付フォルダーを取得
			String datePath = outputPath + ApplicationKeys.REPORT_TEMP_PATH
					+ date + "/";
			// Keyフォルダーを作成
			String printPath = datePath;
			File file = new File(printPath);
			// フォルダー存在しない場合
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			parameters.put("url", jasperPath);
			// 帳票名を取得
			String jasperFileName = jasperPath + jasperName;
			// JasperReportオブジェクト生成
			JasperPrint jasperPrint = null;
			jasperPrint = ReportUtil.jasperCreate(jasperFileName, parameters,
					jrDataSource);
			// PDF拡張名を設定
			String[] fileName = jasperName.split("\\.");
			// CSV拡張名を設定
			String csvName = fileName[0] + Constants.CSV;
			// 出力するCSVファイル名を設定
			String csvFileName = printPath + csvName;


				// CSVファイルをサーバに作成
				ReportUtil.csvCreate(csvFileName, jasperPrint);
				File csvFile = new File(csvFileName);
				if (csvFile.exists()) {
					csvFile.renameTo(new File(printPath + outputFileName
							+ Constants.CSV));
				}

		} catch (BusinessException be) {
			// 帳票出力失敗
			throw new BusinessException(be.getResultMessages());
		}
	}
}
