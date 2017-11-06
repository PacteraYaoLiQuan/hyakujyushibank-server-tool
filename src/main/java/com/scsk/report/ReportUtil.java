package com.scsk.report;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.util.ResultMessages;

/**
 * 帳票出力用の共通クラス ・JasperReportオブジェクト生成 ・PDFファイルをサーバに作成 ・CSVファイルをサーバに作成 ・ZIPファイルを作成
 */
public class ReportUtil {

	/**
	 * JasperReportオブジェクト生成
	 * 
	 * @param jasperFileName
	 *            帳票テンプレート名
	 * @param parameters
	 *            帳票表示用のパラメータ
	 * @param jrDataSource
	 *            帳票表示用のデータソース
	 */
	public static JasperPrint jasperCreate(String jasperFileName,
			Map<String, Object> parameters, JRDataSource jrDataSource) {
		// 帳票ファイルを定義
		File reportFile = new File(jasperFileName);
		JasperPrint jasperPrint = null;
		JasperReport jasperReport = null;
		try {
			// 帳票がデータソースに紐付く
			jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parameters, jrDataSource);
		} catch (JRException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		}
		return jasperPrint;
	}

	/**
	 * PDFファイルをサーバに作成
	 * 
	 * @param fileName
	 *            PDF名
	 * @param jasperPrint
	 *            帳票オブジェクト
	 */
	public static void pdfCreate(String fileName, JasperPrint jasperPrint) {
		FileOutputStream out = null;
		try {
			// PDFファイル生成
			out = new FileOutputStream(fileName);
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			out.close();
		} catch (JRException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		} catch (IOException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		}

	}

	/**
	 * CSVファイルをサーバに作成
	 * 
	 * @param fileName
	 *            CSV名
	 * @param jasperPrint
	 *            帳票オブジェクト
	 */
	public static void csvCreate(String fileName, JasperPrint jasperPrint) {
		FileOutputStream out = null;
		try {
			// CSVファイル生成
			out = new FileOutputStream(fileName);
			JRCsvExporter exporter = new JRCsvExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			//exporter.setExporterOutput(new SimpleWriterExporterOutput(out,
			//		"SJIS"));
			//　Excel文字化け対応
	        exporter.setExporterOutput(new SimpleWriterExporterOutput(out,"x-IBM943C"));
			exporter.exportReport();
			out.close();
		} catch (JRException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		} catch (IOException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		}

	}

	/**
	 * ZIPファイルを作成
	 * 
	 * @param　zipfile　ZIPファイル名
	 */
	public static void zipFiles(String datePath, File zipfile) {

		try {
			// 圧縮フォルダー存在しない場合
			File datePathDirectory = new File(datePath);
			if (!datePathDirectory.exists()) {
				// 帳票出力失敗
				ResultMessages messages = ResultMessages.error().add(
						MessageKeys.E_ACCOUNT001_1002);
				throw new BusinessException(messages);
			}
			// ZIPファイル生成
			FileOutputStream fileOutputStream = new FileOutputStream(zipfile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
					new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos,Charset.forName("Shift_JIS"));
			String basedir = "";
			compressByType(datePathDirectory, out, basedir);
			out.close();
			// フォルダーを削除
			deleteDir(datePathDirectory);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		} catch (Exception e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		}

	}

	/**
	 * ファイルか、フォルダーかチェックするメソッド
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private static void compressByType(File file, ZipOutputStream out,
			String basedir) {
		// フォルダーの場合
		if (file.isDirectory()) {
			// フォルダー圧縮
			compressDirectory(file, out, basedir);
		} else {
			// ファイルの場合
			// ファイル圧縮
			compressFile(file, out, basedir);
		}
	}

	/**
	 * フォルダーを圧縮
	 * 
	 * @param dir
	 * @param out
	 * @param basedir
	 */
	private static void compressDirectory(File dir, ZipOutputStream out,
			String basedir) {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 再帰呼び出し
			compressByType(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/**
	 * ファイル圧縮
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private static void compressFile(File file, ZipOutputStream out,
			String basedir) {
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[8192];
			while ((count = bis.read(data, 0, 8192)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		}
	}

	/**
	 * フォルダー及びサブフォルダーを削除
	 * 
	 * @param dir
	 *            削除するフォルダー
	 * @return boolean
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 再帰削除フォルダー
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 削除
		return dir.delete();
	}

	/**
	 * ファイルダウンロード
	 * 
	 * @param response
	 *            　Response
	 * @param serverPath
	 *            帳票出力フォルダー
	 * @param　zipName　ZIPファイル名
	 */
	public static void downFile(HttpServletResponse response,
			String serverPath, String zipName) {
	    
	    InputStream ins = null;
	    BufferedInputStream bins = null;
	    OutputStream outs = null;
	    BufferedOutputStream bouts = null;

		try {
			String path = serverPath + zipName;
			File file = new File(path);
			// ZIPファイル存在する場合
			if (file.exists()) {
				ins = new FileInputStream(path);
				bins = new BufferedInputStream(ins);
				// ファイルダウンロードを準備
				outs = response.getOutputStream();
				bouts = new BufferedOutputStream(outs);
				response.setContentType("application/x-download");
				response.setHeader(
						"Content-disposition",
						"attachment;filename="
								+ URLEncoder.encode(zipName, "UTF-8"));
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();
//				bouts.close();
//				outs.close();
//				bins.close();
//				ins.close();
			} else {
				response.sendRedirect("/view/master#/welcome");
			}
		} catch (IOException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		} finally {
            if (bins != null) {
                try {
                    bins.close();
                } catch (Exception e) {
                }
            }

            if (ins != null) {
                try {
                    ins.close();
                } catch (Exception e) {
                }
            }
        }
	}
	/**
	 * PDFをウェブブラウザーに開ける
	 * 
	 * @param response
	 *            　Response
	 * @param serverPath
	 *            帳票出力フォルダー
	 * @param　fileName　PDFファイル名
	 */
	public static void openFile(HttpServletResponse response,
			String path,String fileName) {
	    InputStream ins = null;
	    BufferedInputStream bins = null;
	    // PDF開けるを準備
	    OutputStream outs = null;
	    BufferedOutputStream bouts = null;
		try {
			File file = new File(path);
			// PDF存在する場合
			if (file.exists()) {
				ins = new FileInputStream(path + Constants.MARK4 + fileName+Constants.PDF);
				bins = new BufferedInputStream(ins);
				// PDF開けるを準備
				outs = response.getOutputStream();
				bouts = new BufferedOutputStream(outs);
				response.setContentType("application/pdf");
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();
//				bouts.close();
//				outs.close();
//				bins.close();
//				ins.close();
			} else {
				response.sendRedirect("protected/welcomePage.jsp");
			}
		} catch (IOException e) {
			e.printStackTrace();
			// 帳票出力失敗
			ResultMessages messages = ResultMessages.error().add(
					MessageKeys.E_ACCOUNT001_1002);
			throw new BusinessException(messages);
		} finally {
            if (bins != null) {
                try {
                    bins.close();
                } catch (Exception e) {
                }
            }
            
            if (ins != null) {
                try {
                    ins.close();
                } catch (Exception e) {
                }
            }
        }
	}
}
