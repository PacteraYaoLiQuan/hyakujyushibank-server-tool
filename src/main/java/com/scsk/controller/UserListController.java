package com.scsk.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.scsk.model.AuthorityDoc;
import com.scsk.report.ReportBLogic;
import com.scsk.report.ReportUtil;
import com.scsk.request.vo.UserListDeleteReqVO;
import com.scsk.request.vo.UserListLogCsvOutputReqVO;
import com.scsk.request.vo.UserListLogOutputReqVO;
import com.scsk.response.vo.UserInitResVO;
import com.scsk.response.vo.UserListCsvOutputResVO;
import com.scsk.response.vo.UserListLogCsvOutputResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.UserListCsvService;
import com.scsk.service.UserListDeleteService;
import com.scsk.service.UserListInitService;
import com.scsk.service.UserListLogCsvService;
import com.scsk.service.UserListLogOutputService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.UserInitVO;
import com.scsk.vo.UserListLogCsvInitVO;

/**
 * ユーザー一覧画面。<br>
 * <br>
 * メニュー画面からユーザー一覧表示＆登録／詳細／削除を実装すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class UserListController {

    @Autowired
    private UserListCsvService userListCsvService;
    @Autowired
    private UserListLogCsvService userListLogCsvService;
    @Autowired
    private UserListDeleteService userListDeleteService;
    @Autowired
    private UserListInitService userListInitService;
    @Autowired
    private UserListLogOutputService userListLogOutputService;
    @Autowired
    private ReportBLogic reportBLogic;

    /**
     * ユーザー一覧初期化表示メソッド。
     * 
     * @param なし
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/user/userList", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<UserInitResVO>> listInit(
            HttpServletRequest req) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        UserInitResVO userInitResVO = new UserInitResVO();
        ResponseEntityVO<UserInitResVO> resEntityBody = new ResponseEntityVO<UserInitResVO>();
        try {
            // ユーザー一覧データを検索する
            userInitResVO = (UserInitResVO) userListInitService
                    .execute(userInitResVO);
            if (userInitResVO.getUserList() == null || userInitResVO.getUserList() .size() == 0) {
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(
                        MessageKeys.E_USER001_1001);
                throw new BusinessException(messages);
            }
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            resEntityBody.setResultData(userInitResVO);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            resEntityBody.setResultData(userInitResVO);
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
        return new ResponseEntity<ResponseEntityVO<UserInitResVO>>(
                resEntityBody, HttpStatus.OK);
    }

    /**
     * 一括削除メソッド。
     * 
     * @param @RequestBody UserListDeleteReqVO 一覧データ
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/user/deleteButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<UserListDeleteReqVO>> deleteButton(
            HttpServletRequest req,
            @RequestBody UserListDeleteReqVO userListDeleteReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<UserListDeleteReqVO> resEntityBody = new ResponseEntityVO<UserListDeleteReqVO>();

        try {
            // 選択したデータを削除する
            userListDeleteService.execute(userListDeleteReqVO);
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
        return new ResponseEntity<ResponseEntityVO<UserListDeleteReqVO>>(
                resEntityBody, HttpStatus.OK);
    }
    
    
    
    /**
     * ユーザー一覧CSV出力メソッド。
     * 
     * @param @RequestBody accountAppListCsvButtonReqVO　一覧データ
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/user/outPut", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<UserListCsvOutputResVO>> csvButton(
            HttpServletRequest req,HttpServletResponse res,@RequestBody UserListLogOutputReqVO userListLogOutputReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        UserListCsvOutputResVO userListCsvOutputResVO = new UserListCsvOutputResVO();
        ResponseEntityVO<UserListCsvOutputResVO> resEntityBody = new ResponseEntityVO<UserListCsvOutputResVO>();

        try {
            // CSV出力用データを抽出
            userListCsvOutputResVO = userListCsvService.execute(userListLogOutputReqVO);

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
            String outputFileName = ApplicationKeys.USERLIST001_REPORT;
            // 帳票/CSV区分設定
            csvCreate1("1", date, userListCsvOutputResVO.getUserListCsvOutput(), outputFileName, req);

            // ヘッダ設定（処理成功の場合）
            userListCsvOutputResVO.setDate(date);
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定
            resEntityBody.setResultData(userListCsvOutputResVO);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            if (e.getResultMessages().getList().get(0).getCode().equals(MessageKeys.E_ACCOUNT001_1008)) {
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
        return new ResponseEntity<ResponseEntityVO<UserListCsvOutputResVO>>(resEntityBody, HttpStatus.OK);
    }
    
    /**
     * CSVボタンダウン‐ロードメソッド。
     * 
     * @param @RequestParam date 当日日付
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/user/csvButtonDownLoad", method = RequestMethod.GET)
    public void csvButtonDownLoad(
            HttpServletRequest req, HttpServletResponse res,@RequestParam("date") String date) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<UserListLogOutputReqVO> resEntityBody = new ResponseEntityVO<UserListLogOutputReqVO>();

        try {
            // CSV出力用のフォルダー
            String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
            // 帳票出力用の詳細フォルダーを取得
            String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";

            // ZIPファイルダウンロード
            String zipName = ApplicationKeys.USERLIST001_REPORT + Constants.CSV;
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
     * ユーザー一覧出力ボタンCSVファイル生成メソッド。
     * 
     * @param tacsflag
     *            　 TACSフラグ
     * @return date TACSフラグ値
     * @throws Exception
     */
    public void csvCreate1(String flg,String date, List<UserInitVO> dateSourceList,String outputFileName,HttpServletRequest req) {
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
            s = "\"ユーザー一覧表\""+ "\n";
            Buff.write(s.getBytes("SHIFT-JIS"));
            s= "\"" + "userID" + "\"" + ","
                + "\"" + "ユーザー名" + "\"" + ","
                + "\"" + "権限" + "\"" + ","
                + "\"" + "メールアドレス" + "\"" + ","
                + "\"" + "ロック状態" + "\"" + ","
                + "\"" + "ログイン状態" + "\"" + ","
                + "\"" + "最終ログイン日時" + "\"" + "\n";
            Buff.write(s.getBytes("MS932"));
            
            for (int i = 0; i < dateSourceList.size(); i++) {
                String loginStatus = "";
                String lockStatus = "";
                if (dateSourceList.get(i).getLoginStatus() != 0) {
                    loginStatus = "ログイン中";
                } else {
                    loginStatus = "未ログイン";
                }
                if(dateSourceList.get(i).isLockStatus() != true) {
                    lockStatus = "未ロック";
                } else{
                    lockStatus = "ロック中";
                }
            String s2 = "";
            String s1 = "\"" + dateSourceList.get(i).getUserID() + "\"" + ","
                    + "\"" + dateSourceList.get(i).getUserName() + "\"" + ","
                    + "\"" + dateSourceList.get(i).getAuthority() + "\"" + ","
                    + "\"" + dateSourceList.get(i).getEmail() + "\"" + ","
                    + "\"" + lockStatus + "\"" + ","
                    + "\"" + loginStatus + "\"" + ","
                    + "\"" + dateSourceList.get(i).getEndLoginDateTime() + "\"" ;
            if(flg.equals("1")){
                s2 = s1 + "\n";
            }
            Buff.write(s2.getBytes("MS932"));
            }
            Buff.flush();
            Buff.close();
            }catch (Exception e) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1008);
                throw new BusinessException(messages);
            }

            finally {
                try {
                    Buff.close();
                } catch (Exception e) {
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1008);
                    throw new BusinessException(messages);
                }
            }
        }
            
            
            
            /**
             * 操作/行動ログCSV出力メソッド。
             * 
             * @param @RequestBody accountAppListCsvButtonReqVO　一覧データ
             * @return ResponseEntity　戻るデータオブジェクト
             */
            @RequestMapping(value = "/user/logOutPut", method = RequestMethod.POST, produces = "application/json")
            public ResponseEntity<ResponseEntityVO<UserListLogCsvOutputResVO>> logCsvButton(
                    HttpServletRequest req,HttpServletResponse res,@RequestBody UserListLogOutputReqVO userListLogOutputReqVO) {
                LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
                UserListLogCsvOutputResVO userListLogCsvOutputResVO = new UserListLogCsvOutputResVO();
                ResponseEntityVO<UserListLogCsvOutputResVO> resEntityBody = new ResponseEntityVO<UserListLogCsvOutputResVO>();

                try {
                    // CSV出力用データを抽出
                    userListLogCsvOutputResVO = userListLogCsvService.execute(userListLogOutputReqVO);
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
                    String outputFileName = ApplicationKeys.USERLIST002_REPORT;
                    // CSV区分設定
                    csvCreate2("1", date, userListLogCsvOutputResVO.getUserListLogCsvOutput(), outputFileName, req);
                    // ヘッダ設定（処理成功の場合）
                    userListLogCsvOutputResVO.setDate(date);
                    resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
                    resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
                    // ボーディ設定
                    resEntityBody.setResultData(userListLogCsvOutputResVO);
                    LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
                } catch (BusinessException e) {
                    // ヘッダ設定（処理失敗の場合）
                    resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
                    if (e.getResultMessages().getList().get(0).getCode().equals(MessageKeys.E_ACCOUNT001_1008)) {
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
                return new ResponseEntity<ResponseEntityVO<UserListLogCsvOutputResVO>>(resEntityBody, HttpStatus.OK);
            }
            
            /**
             * CSVボタンダウン‐ロードメソッド。
             * 
             * @param @RequestParam date 当日日付
             * @return ResponseEntity　戻るデータオブジェクト
             */
            @RequestMapping(value = "/user/logCsvButtonDownLoad", method = RequestMethod.GET)
            public void logCsvButtonDownLoad(
                    HttpServletRequest req, HttpServletResponse res,@RequestParam("date") String date) {
                LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
                ResponseEntityVO<UserListLogCsvOutputReqVO> resEntityBody = new ResponseEntityVO<UserListLogCsvOutputReqVO>();

                try {
                    // CSV出力用のフォルダー
                    String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
                    // 出力用の詳細フォルダーを取得
                    String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";

                    // ZIPファイルダウンロード
                    String zipName = ApplicationKeys.USERLIST002_REPORT + Constants.CSV;
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
             * 操作／行動ログ出力ボタンCSVファイル生成メソッド。
             * 
             * @param tacsflag
             *            　 TACSフラグ
             * @return date TACSフラグ値
             * @throws Exception
             */
            public void csvCreate2(String flg,String date, List<UserListLogCsvInitVO> dateSourceList,String outputFileName,HttpServletRequest req) {
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
                    s = "\"操作／行動ログ表\""+ "\n";
                    Buff.write(s.getBytes("SHIFT-JIS"));
                    s= "\"" + "操作日時" + "\"" + ","
                        + "\"" + "userID" + "\"" + ","
                        + "\"" + "ユーザー名" + "\"" + ","
                        + "\"" + "操作／行動ログ" + "\"" + "\n";
                    Buff.write(s.getBytes("MS932"));
                    
                    for (int i = 0; i < dateSourceList.size(); i++) {
                    String s2 = "";
                    String s1 = "\"" + dateSourceList.get(i).getAccessDatetime() + "\"" + ","
                            + "\"" + dateSourceList.get(i).getUserID() + "\"" + ","
                            + "\"" + dateSourceList.get(i).getUserName() + "\"" + ","
                            + "\"" + dateSourceList.get(i).getActionLog() + "\"" ;
                    if(flg.equals("1")){
                        s2 = s1 + "\n";
                    }
                    Buff.write(s2.getBytes("MS932"));
                    }
                    Buff.flush();
                    Buff.close();
        } catch (Exception e) {
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1008);
            throw new BusinessException(messages);
        }

        finally {
            try {
                Buff.close();
            } catch (Exception e) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1008);
                throw new BusinessException(messages);
            }
        }
    }
    
    /**
     * ログ出力メソッド。
     * 
     * @param @RequestBody UserListLogOutputReqVO 一覧データ
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/user/logOutputButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<UserListLogOutputReqVO>> logOutputButton(
            HttpServletRequest req,
            @RequestBody UserListLogOutputReqVO userListLogOutputReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<UserListLogOutputReqVO> resEntityBody = new ResponseEntityVO<UserListLogOutputReqVO>();

        try {
            // 選択したデータを削除する
            userListLogOutputService.execute(userListLogOutputReqVO);
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
        return new ResponseEntity<ResponseEntityVO<UserListLogOutputReqVO>>(
                resEntityBody, HttpStatus.OK);
    }
    
    
    /**
     * セッションタイムアウトメソッド。
     * 
     * @param @RequestBody UserListLogOutputReqVO 一覧データ
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/user/sessionTimeOut", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<UserListLogOutputReqVO>> sessionTimeOut(
            HttpServletRequest req) {
        ResponseEntityVO<UserListLogOutputReqVO> resEntityBody = new ResponseEntityVO<UserListLogOutputReqVO>();
        return new ResponseEntity<ResponseEntityVO<UserListLogOutputReqVO>>(
                resEntityBody, HttpStatus.OK);
    }
}
