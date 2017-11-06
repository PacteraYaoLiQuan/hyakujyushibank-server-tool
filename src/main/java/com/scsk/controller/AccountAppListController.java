package com.scsk.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
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
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.request.vo.AccountAppListButtonReqVO;
import com.scsk.request.vo.AccountAppListCompleteButtonReqVO;
import com.scsk.request.vo.AccountAppListCsvButtonReqVO;
import com.scsk.request.vo.AccountAppListOutputButtonReqVO;
import com.scsk.response.vo.Account114AppInitResVO;
import com.scsk.response.vo.Account114AppListCsvButtonResVO;
import com.scsk.response.vo.Account114AppListOutputButtonResVO;
import com.scsk.response.vo.AccountApp114DetailResVO;
import com.scsk.response.vo.AccountAppDetailResVO;
import com.scsk.response.vo.AccountAppInitResVO;
import com.scsk.response.vo.AccountAppListButtonResVO;
import com.scsk.response.vo.AccountAppListCsvButtonResVO;
import com.scsk.response.vo.AccountAppListOutputButtonResVO;
import com.scsk.response.vo.AccountAppListOutputCsvResVO;
import com.scsk.response.vo.AccountAppYamaGataDetailResVO;
import com.scsk.response.vo.AccountYamaGataAppInitResVO;
import com.scsk.response.vo.AccountYamaGataAppListCsvButtonResVO;
import com.scsk.response.vo.AccountYamaGataAppListOutputButtonResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.AccountAppAppraisalDetailService;
import com.scsk.service.AccountAppDetailImageSelService;
import com.scsk.service.AccountAppListCompleteService;
import com.scsk.service.AccountAppListCsvService;
import com.scsk.service.AccountAppListInitService;
import com.scsk.service.AccountAppListOutputService;
import com.scsk.service.AccountAppListService;
import com.scsk.service.AccountAppYamaGataExcelOutPutService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.AccountApp114InitVO;
import com.scsk.vo.AccountAppInitVO;
import com.scsk.vo.AccountAppYamaGataInitVO;
import com.scsk.vo.ErrVO;
import com.scsk.vo.ImageVO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 申込一覧画面。<br>
 * <br>
 * メニュー画面から申込一覧表示＆ステータス変更＆帳票出力＆CSV出力を実装すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class AccountAppListController {

    @Autowired
    private AccountAppListCompleteService accountAppListCompleteService;
    @Autowired
    private AccountAppListCsvService accountAppListCsvService;
    @Autowired
    private AccountAppListInitService accountAppListInitService;
    @Autowired
    private AccountAppListOutputService accountAppListOutputService;
    @Autowired
    private AccountAppListService accountAppListService;
    @Autowired
    AccountAppDetailImageSelService accountAppDetailImageSelService;
    @Autowired
    AccountAppAppraisalDetailService accountAppAppraisalDetailService;
    @Autowired
    AccountAppYamaGataExcelOutPutService accountAppYamaGataExcelOutPutService;
    @Autowired
    private ReportBLogic reportBLogic;
    @Resource
    private MessageSource messageSource;
    @Value("${bank_cd}")
    private String bank_cd;

    /**
     * 申込一覧初期化表示メソッド。
     * 
     * @param なし
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/accountAppList", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> accountAppListInit(HttpServletRequest req) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        AccountAppInitResVO accountAppInitResVO = new AccountAppInitResVO();
        AccountYamaGataAppInitResVO accountAppInitResVO2 = new AccountYamaGataAppInitResVO();
        Account114AppInitResVO account114AppInitResVO = new Account114AppInitResVO();
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();

        try {
            // 申込一覧データを検索する
            if ("0169".equals(bank_cd)) {
                BaseResVO ba = (AccountAppInitResVO) accountAppListInitService.execute(baseResVO);
                accountAppInitResVO = (AccountAppInitResVO) ba;
            } else if ("0122".equals(bank_cd)) {
                BaseResVO ba = (AccountYamaGataAppInitResVO) accountAppListInitService.execute(baseResVO);
                accountAppInitResVO2 = (AccountYamaGataAppInitResVO) ba;
            } else if("0173".equals(bank_cd)){
                BaseResVO ba = (Account114AppInitResVO) accountAppListInitService.execute(baseResVO);
                account114AppInitResVO = (Account114AppInitResVO) ba;
            }

            // 初期化データない場合、Messageをセットする
            if ("0169".equals(bank_cd)) {
                if (accountAppInitResVO.getAccountAppList().isEmpty()) {
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1002);
                    throw new BusinessException(messages);
                }
            } else if ("0122".equals(bank_cd)) {
                if (accountAppInitResVO2.getAccountAppList().isEmpty()) {
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1002);
                    throw new BusinessException(messages);
                }
            }else if("0173".equals(bank_cd)){
                if (account114AppInitResVO.getAccountAppList().isEmpty()) {
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1002);
                    throw new BusinessException(messages);
                }
            }
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            if ("0169".equals(bank_cd)) {
                resEntityBody.setResultData(accountAppInitResVO);
            } else if ("0122".equals(bank_cd)) {
                resEntityBody.setResultData(accountAppInitResVO2);
            }else if ("0173".equals(bank_cd)) {
                resEntityBody.setResultData(account114AppInitResVO);
            }
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            if ("0169".equals(bank_cd)) {
                resEntityBody.setResultData(accountAppInitResVO);
            } else if ("0122".equals(bank_cd)) {
                resEntityBody.setResultData(accountAppInitResVO2);
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
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * (一覧出力)受付進捗管理表出力メソッド。
     * 
     * @param @RequestParam receiptDate 申込受付日付 status ステータス
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/accountAppListReport", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<AccountAppListButtonResVO>> accountAppListReport(HttpServletRequest req,
            HttpServletResponse res, @RequestParam("receiptDate") String receiptDate,
            @RequestParam("status") String[] status) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppListButtonResVO> resEntityBody = new ResponseEntityVO<AccountAppListButtonResVO>();
        // 帳票出力用の パラメータを準備
        AccountAppListButtonReqVO accountAppListButtonReqVO = new AccountAppListButtonReqVO();
        AccountAppListButtonResVO accountAppListButtonResVO = new AccountAppListButtonResVO();
        accountAppListButtonReqVO.setReceiptDate(receiptDate);
        accountAppListButtonReqVO.setStatus(status);

        try {
            // 帳票出力用データを取得
            accountAppListButtonResVO = accountAppListService.execute(accountAppListButtonReqVO);
            // 初期化データない場合、Messageをセットする
            if (accountAppListButtonResVO.getAccountAppList().isEmpty()) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1002);
                throw new BusinessException(messages);
            }
            // 帳票出力用の datasourceを準備
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(accountAppListButtonResVO.getAccountAppList());

            // 当日日付を取得する（日付フォーマット）
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
            String date = sdf.format(new Date());
            // 出力する日付フォルダーを作成
            reportBLogic.datePathCreate(req, date);

            // 帳票出力用のparameterを準備
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("year", date.substring(0, 4));
            parameters.put("month", date.substring(4, 6));
            parameters.put("day", date.substring(6, 8));
            // プロジェクトのパスを取得
            this.getClass().getClassLoader();
            String jasperName = ApplicationKeys.ACCOUNTAPPLIST001_REPORT;
            String outputFileName = ApplicationKeys.ACCOUNTAPPLIST101_REPORT;
            // 帳票/CSV区分設定
            String flg = "1";

            // 帳票出力
            reportBLogic.reportCreate(date, jasperName, (Map<String, Object>) parameters, jrDataSource, flg,
                    outputFileName, req, res);

            accountAppListButtonResVO.setDate(date);
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定
            resEntityBody.setResultData(accountAppListButtonResVO);
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
        return new ResponseEntity<ResponseEntityVO<AccountAppListButtonResVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * 完了消込メソッド。
     * 
     * @param @RequestBody accountAppListCompleteButtonReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/completeButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> completeButton(HttpServletRequest req,
            @RequestBody AccountAppListCompleteButtonReqVO accountAppListCompleteButtonReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();
        baseResVO = accountAppListCompleteButtonReqVO;

        try {
            // 「処理中」－＞「完了」に変更する
            accountAppListCompleteService.execute(baseResVO);
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
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * 帳票出力メソッド。
     * 
     * @param @RequestBody accountAppListOutputButtonReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/outputButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> outputButton(HttpServletRequest req, HttpServletResponse res,
            @RequestBody AccountAppListOutputButtonReqVO accountAppListOutputButtonReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        AccountAppListOutputButtonResVO accountAppListOutputButtonResVO = new AccountAppListOutputButtonResVO();
        AccountYamaGataAppListOutputButtonResVO accountAppListOutputButtonResVO2 = new AccountYamaGataAppListOutputButtonResVO();
        Account114AppListOutputButtonResVO accountAppListOutputButtonResVO3=new Account114AppListOutputButtonResVO();
        AccountAppListOutputCsvResVO accountAppListOutputCsvResVO = new AccountAppListOutputCsvResVO();
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();
        baseResVO = accountAppListOutputButtonReqVO;

        try {
            // 「受付」－＞「処理中」に変更する,帳票出力用データを抽出
            if ("0169".equals(bank_cd)) {
                BaseResVO ba = accountAppListOutputService.execute(baseResVO);
                accountAppListOutputButtonResVO = (AccountAppListOutputButtonResVO) ba;

                // 画面に選択したデータをレコードずつPDF＆CSV出力する
                List<AccountAppInitVO> checkList = accountAppListOutputButtonResVO.getAccountAppList();
                if (checkList != null && checkList.size() != 0) {
                    // 当日日付を取得する（日付フォーマット）
                    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
                    String date = sdf.format(new Date());
                    // 出力する日付フォルダーを作成
                    reportBLogic.datePathCreate(req, date);
                    List<AccountAppInitVO> dateSourceList = new ArrayList<AccountAppInitVO>();
                    List<AccountAppInitVO> dateSourceList2 = new ArrayList<AccountAppInitVO>();
                    List<ImageVO> imageList = new ArrayList<ImageVO>();

                    // データ出力順序を設定（申込受付番号（主キー）の昇順）
                    Comparator<AccountAppInitVO> comparator = new Comparator<AccountAppInitVO>() {
                        public int compare(AccountAppInitVO s1, AccountAppInitVO s2) {
                            if (s1.getAccountAppSeq().equals(s2.getAccountAppSeq())) {
                                return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                            } else if (s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq()) > 1) {
                                return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                            } else {
                                return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                            }
                        }
                    };

                    Collections.sort(checkList, comparator);

                    List<ErrVO> ErrList = new ArrayList<>();
                    for (AccountAppInitVO vo : checkList) {
                        // 帳票出力用の datasourceを準備
                        if (Utils.isNotNullAndEmpty(vo.getIdentificationImage())) {
                            // 画像出力用データを取得
                            AccountAppDetailReqVO input = new AccountAppDetailReqVO();
                            input.set_id(vo.getIdentificationImage());
                            AccountAppDetailResVO resVo = (AccountAppDetailResVO) accountAppDetailImageSelService
                                    .execute(baseResVO);
                            resVo.setAccountAppSeq(vo.getAccountAppSeq());
                            resVo.setIdentificationType(vo.getIdentificationType());
                            resVo.setLivingConditions(vo.getLivingConditions());
                            // 本人確認書類保存＆ImageVOListセット（本人確認書類）
                            imageList.add(reportBLogic.ImageOutputSave(date, resVo, req));
                        }

                        // CSV出力項目をチェック
                        // List<ErrVO> ErrList1 = new ArrayList<>();
                        // ErrList1 = accountAppReportCheck(vo);
                        String errMessageText = "";
                        // if (ErrList1 != null && ErrList1.size() != 0) {
                        // for (int i = 0; i < ErrList1.size(); i++) {
                        // // エラー内容をセット
                        // errMessageText = errMessageText + "[" +
                        // ErrList1.get(i).getMessage() + "]";
                        // ErrList.add(ErrList1.get(i));
                        // }
                        // AccountAppInitVO accountAppInitVO = new
                        // AccountAppInitVO();
                        // // ErrCSVを出力設定
                        // accountAppInitVO = accountAppReport(vo);
                        // accountAppInitVO.setErrMessage(errMessageText);
                        // // AccountAppInitVOListセット
                        // dateSourceList2.add(accountAppInitVO);
                        // } else {
                        AccountAppInitVO accountAppInitVO = new AccountAppInitVO();
                        // CSVを出力設定
                        accountAppInitVO = accountAppReport(vo);
                        // AccountAppInitVOListセット
                        dateSourceList.add(accountAppInitVO);
                        // }
                        accountAppListOutputCsvResVO.setImageList(imageList);
                    }

                    // 本人確認書類PDF生成
                    JRDataSource jrDataSource = new JRBeanCollectionDataSource(
                            accountAppListOutputCsvResVO.getImageList());
                    reportBLogic.ImageOutputCreate(date, jrDataSource, ApplicationKeys.ACCOUNTAPPLIST002_REPORT,
                            ApplicationKeys.ACCOUNTAPPLIST102_REPORT, req);

                    // プロジェクトのパスを取得
                    this.getClass().getClassLoader();
                    String outputFileName = "";

                    outputFileName = ApplicationKeys.ACCOUNTAPPLIST104_REPORT;

                    // CSVを作成
                    csvCreate("1", date, dateSourceList, outputFileName, req);

                    accountAppListOutputButtonResVO.setDate(date);

                    // 電話番号鑑定結果PDF出力
                    List<AccountAppDetailResVO> detailList = new ArrayList<AccountAppDetailResVO>();
                    for (int i = 0; i < accountAppListOutputButtonReqVO.getOutputList().size(); i++) {
                        if (accountAppListOutputButtonReqVO.getOutputList().get(i).getSelect() == null) {
                            continue;
                        }
                        // 一覧選択したデータ
                        AccountAppDetailReqVO detailReqVO = new AccountAppDetailReqVO();
                        if (accountAppListOutputButtonReqVO.getOutputList().get(i).getSelect() == true) {
                            detailReqVO.set_id(accountAppListOutputButtonReqVO.getOutputList().get(i).get_id());
                            baseResVO = detailReqVO;
                            AccountAppDetailResVO output = new AccountAppDetailResVO();
                            output = (AccountAppDetailResVO) accountAppAppraisalDetailService.execute(baseResVO);
                            if (output != null) {
                                if (output.getTC_Month1() == null) {
                                    output.setTC_Month1("");
                                }
                                if (output.getTC_Month2() == null) {
                                    output.setTC_Month2("");
                                }
                                if ("high".equals(output.getIC_IpThreat())) {
                                    output.setIC_IpThreat("要注意");
                                } else if ("low".equals(output.getIC_IpThreat())) {
                                    output.setIC_IpThreat("低い");
                                }
                                if ("high".equals(output.getIC_CountryThreat())) {
                                    output.setIC_CountryThreat("要注意");
                                } else if ("low".equals(output.getIC_CountryThreat())) {
                                    output.setIC_CountryThreat("低い");
                                }
                                if ("true".equals(output.getIC_PSIP())) {
                                    output.setIC_PSIP("要注意");
                                } else if ("false".equals(output.getIC_PSIP())) {
                                    output.setIC_PSIP("-");
                                }
                                if ("true".equals(output.getIC_Proxy())) {
                                    output.setIC_Proxy("要注意（利用している可能性あり）");
                                } else if ("false".equals(output.getIC_Proxy())) {
                                    output.setIC_Proxy("-");
                                }
                                if ("0".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("検索成功");
                                } else if ("1".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("データ無し");
                                } else if ("2".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("電話番号の桁数不正");
                                } else if ("4".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("特殊電話番号等調査対象外、予期しないエラー");
                                }
                                if ("0".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("検索成功");
                                } else if ("1".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("データ無し");
                                } else if ("2".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("電話番号の桁数不正");
                                } else if ("4".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("特殊電話番号等調査対象外、予期しないエラー");
                                }
                                if ("0".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("無効：電話番号が使われていない");
                                } else if ("1".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("有効：電話番号が使われている");
                                } else if ("2".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("移転：移転ｱﾅｳﾝｽが流れている電話番号");
                                } else if ("3".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("都合停止：お客様都合で利用ができない電話番号");
                                } else if ("4".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("ｴﾗｰ：判定できない電話番号もしくは050、0120局番等の電話番号");
                                } else if ("7".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("局預け：電話局に電話番号を預けている");
                                } else if ("8".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("再調査：判定できない電話番号");
                                } else if ("9".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("INS回線有効：電話番号が使われている（ISDN回線）");
                                } else if ("S".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("NTT以外：NTT以外で一部判定ができない電話番号");
                                }
                                if ("0".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("無効：電話番号が使われていない");
                                } else if ("1".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("有効：電話番号が使われている");
                                } else if ("2".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("移転：移転ｱﾅｳﾝｽが流れている電話番号");
                                } else if ("3".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("都合停止：お客様都合で利用ができない電話番号");
                                } else if ("4".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("ｴﾗｰ：判定できない電話番号もしくは050、0120局番等の電話番号");
                                } else if ("7".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("局預け：電話局に電話番号を預けている");
                                } else if ("8".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("再調査：判定できない電話番号");
                                } else if ("9".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("INS回線有効：電話番号が使われている（ISDN回線）");
                                } else if ("S".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("NTT以外：NTT以外で一部判定ができない電話番号");
                                }
                                if ("-1".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("MSG該当なし：｢直近加入」「都合停止」「変更過多」｢長期有効」「エラー」いずれにも該当しない場合");
                                } else if ("1".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("直近加入：三カ月以内に有効になった番号");
                                } else if ("2".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("都合停止：半年以内に都合停止がある番号");
                                } else if ("3".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("変更過多：有効無効を3回以上繰り返している番号");
                                } else if ("4".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("長期有効：24ヵ月以上有効である番号");
                                } else if ("E".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("ｴﾗｰ：特殊番号等で調査不可");
                                } else if ("U".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("未調査：対象ﾃﾞｰﾀが無い番号");
                                }
                                if ("-1".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("MSG該当なし：｢直近加入」「都合停止」「変更過多」｢長期有効」「エラー」いずれにも該当しない場合");
                                } else if ("1".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("直近加入：三カ月以内に有効になった番号");
                                } else if ("2".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("都合停止：半年以内に都合停止がある番号");
                                } else if ("3".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("変更過多：有効無効を3回以上繰り返している番号");
                                } else if ("4".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("長期有効：24ヵ月以上有効である番号");
                                } else if ("E".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("ｴﾗｰ：特殊番号等で調査不可");
                                } else if ("U".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("未調査：対象ﾃﾞｰﾀが無い番号");
                                }
                            }
                            detailList.add(output);
                        }
                    }

                    // 帳票出力用の datasourceを準備
                    JRDataSource jrDataSource1 = new JRBeanCollectionDataSource(detailList);

                    // 帳票出力用のparameterを準備
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("year", date.substring(0, 4));
                    parameters.put("month", date.substring(4, 6));
                    parameters.put("day", date.substring(6, 8));
                    // プロジェクトのパスを取得
                    this.getClass().getClassLoader();
                    String jasperName = ApplicationKeys.ACCOUNTAPPLIST07_REPORT;
                    String outputFileName1 = ApplicationKeys.ACCOUNTAPPLIST107_REPORT;
                    // 帳票/CSV区分設定
                    String flg = "1";

                    // 帳票出力
                    reportBLogic.reportCreate(date, jasperName, (Map<String, Object>) parameters, jrDataSource1, flg,
                            outputFileName1, req, res);

                    accountAppListOutputButtonResVO.setDate(date);

                    // IPアドレス鑑定結果PDF出力
                    // 帳票出力用の datasourceを準備
                    JRDataSource jrDataSource2 = new JRBeanCollectionDataSource(detailList);
                    // プロジェクトのパスを取得
                    this.getClass().getClassLoader();

                    String jasperName1 = ApplicationKeys.ACCOUNTAPPLIST06_REPORT;
                    String outputFileName2 = ApplicationKeys.ACCOUNTAPPLIST106_REPORT;
                    // 帳票/CSV区分設定
                    String flg1 = "1";

                    // 帳票出力
                    reportBLogic.reportCreate(date, jasperName1, (Map<String, Object>) parameters, jrDataSource2, flg1,
                            outputFileName2, req, res);
                    accountAppListOutputButtonResVO.setDate(date);
                }

                // ヘッダ設定（処理成功の場合）
                resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
                // ボーディ設定
                resEntityBody.setResultData(accountAppListOutputButtonResVO);
                LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);

            } else if ("0122".equals(bank_cd)) {
                BaseResVO ba = accountAppListOutputService.execute(baseResVO);
                accountAppListOutputButtonResVO2 = (AccountYamaGataAppListOutputButtonResVO) ba;

                // 画面に選択したデータをレコードずつPDF＆CSV出力する
                List<AccountAppYamaGataInitVO> checkList = accountAppListOutputButtonResVO2.getAccountAppList();
                if (checkList != null && checkList.size() != 0) {
                    // 当日日付を取得する（日付フォーマット）
                    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
                    String date = sdf.format(new Date());
                    // SimpleDateFormat sdfZipFormat = new
                    // SimpleDateFormat(Constants.DATE_FORMAT_ASS);
                    // String dateZipName = sdfZipFormat.format(new Date());
                    // 出力する日付フォルダーを作成
                    reportBLogic.datePathCreate(req, date);
                    List<AccountAppYamaGataInitVO> dateSourceList = new ArrayList<AccountAppYamaGataInitVO>();
                    List<AccountAppYamaGataInitVO> dateSourceList2 = new ArrayList<AccountAppYamaGataInitVO>();
                    List<ImageVO> imageList = new ArrayList<ImageVO>();

                    // データ出力順序を設定（申込受付番号（主キー）の昇順）
                    Comparator<AccountAppYamaGataInitVO> comparator = new Comparator<AccountAppYamaGataInitVO>() {
                        public int compare(AccountAppYamaGataInitVO s1, AccountAppYamaGataInitVO s2) {
                            if (s1.getAccountAppSeq().equals(s2.getAccountAppSeq())) {
                                return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                            } else if (s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq()) > 1) {
                                return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                            } else {
                                return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                            }
                        }
                    };

                    Collections.sort(checkList, comparator);

                    List<ErrVO> ErrList = new ArrayList<>();
                    for (AccountAppYamaGataInitVO vo : checkList) {
                        // 帳票出力用の datasourceを準備
                        if (Utils.isNotNullAndEmpty(vo.getIdentificationImage())) {
                            // 画像出力用データを取得
                            AccountAppDetailReqVO input = new AccountAppDetailReqVO();
                            input.set_id(vo.getIdentificationImage());
                            AccountAppYamaGataDetailResVO resVo = (AccountAppYamaGataDetailResVO) accountAppDetailImageSelService
                                    .execute(input);
                            resVo.setAccountAppSeq(vo.getAccountAppSeq());
                            resVo.setIdentificationType(vo.getIdentificationType());
                            resVo.setLivingConditions(vo.getLivingConditions());
                            // 本人確認書類保存＆ImageVOListセット（本人確認書類）
                            imageList.add(reportBLogic.ImageOutputSave2(date, resVo, req));
                        }

                        AccountAppYamaGataInitVO accountAppInitVO = new AccountAppYamaGataInitVO();
                        // CSVを出力設定
                        accountAppInitVO = accountAppReport2(vo);
                        // AccountAppInitVOListセット
                        dateSourceList.add(accountAppInitVO);
                        // }
                        accountAppListOutputCsvResVO.setImageList(imageList);
                    }

                    // 本人確認書類PDF生成
                    JRDataSource jrDataSource = new JRBeanCollectionDataSource(
                            accountAppListOutputCsvResVO.getImageList());
                    reportBLogic.ImageOutputCreate(date, jrDataSource, ApplicationKeys.ACCOUNTAPPLIST002_REPORT,
                            ApplicationKeys.ACCOUNTAPPLIST102_REPORT, req);

                    // プロジェクトのパスを取得
                    this.getClass().getClassLoader();
                    String outputFileName = "";
                    reportBLogic.dateZipCreate(req, res, date);
                    String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
                    //帳票出力用のZIP作成
                    String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;
                    String zipFileName = tempPath + date + Constants.ZIP;
                    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
                    outputFileName = ApplicationKeys.ACCOUNTAPPLIST104_REPORT;
                    // CSVを作成
                    // csvCreate2("1", date, dateSourceList, outputFileName, req
                    // ,zipOutputStream);
                    accountAppListOutputButtonResVO2.setDate(date);
                    for (AccountAppYamaGataInitVO accountAppYamaGataInitVO : accountAppListOutputButtonResVO2
                            .getAccountAppList()) {
                    	accountAppYamaGataExcelOutPutService.ExcelOutput(req, accountAppYamaGataInitVO, zipOutputStream);
                    }
                    zipOutputStream.close();


                    // 電話番号鑑定結果PDF出力
                    List<AccountAppYamaGataDetailResVO> detailList = new ArrayList<AccountAppYamaGataDetailResVO>();
                    for (int i = 0; i < accountAppListOutputButtonReqVO.getOutputList2().size(); i++) {
                        if (accountAppListOutputButtonReqVO.getOutputList2().get(i).getSelect() == null) {
                            continue;
                        }
                        // 一覧選択したデータ
                        AccountAppDetailReqVO detailReqVO = new AccountAppDetailReqVO();
                        if (accountAppListOutputButtonReqVO.getOutputList2().get(i).getSelect() == true) {
                            detailReqVO.set_id(accountAppListOutputButtonReqVO.getOutputList2().get(i).get_id());
                            baseResVO = detailReqVO;
                            AccountAppYamaGataDetailResVO output = new AccountAppYamaGataDetailResVO();
                            output = (AccountAppYamaGataDetailResVO) accountAppAppraisalDetailService
                                    .execute(baseResVO);
                            if (output != null) {
                                if (output.getTC_Month1() == null) {
                                    output.setTC_Month1("");
                                }
                                if (output.getTC_Month2() == null) {
                                    output.setTC_Month2("");
                                }
                                if ("high".equals(output.getIC_IpThreat())) {
                                    output.setIC_IpThreat("要注意");
                                } else if ("low".equals(output.getIC_IpThreat())) {
                                    output.setIC_IpThreat("低い");
                                }
                                if ("high".equals(output.getIC_CountryThreat())) {
                                    output.setIC_CountryThreat("要注意");
                                } else if ("low".equals(output.getIC_CountryThreat())) {
                                    output.setIC_CountryThreat("低い");
                                }
                                if ("true".equals(output.getIC_PSIP())) {
                                    output.setIC_PSIP("要注意");
                                } else if ("false".equals(output.getIC_PSIP())) {
                                    output.setIC_PSIP("-");
                                }
                                if ("true".equals(output.getIC_Proxy())) {
                                    output.setIC_Proxy("要注意（利用している可能性あり）");
                                } else if ("false".equals(output.getIC_Proxy())) {
                                    output.setIC_Proxy("-");
                                }
                                if ("0".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("検索成功");
                                } else if ("1".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("データ無し");
                                } else if ("2".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("電話番号の桁数不正");
                                } else if ("4".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("特殊電話番号等調査対象外、予期しないエラー");
                                }
                                if ("0".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("検索成功");
                                } else if ("1".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("データ無し");
                                } else if ("2".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("電話番号の桁数不正");
                                } else if ("4".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("特殊電話番号等調査対象外、予期しないエラー");
                                }
                                if ("0".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("無効：電話番号が使われていない");
                                } else if ("1".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("有効：電話番号が使われている");
                                } else if ("2".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("移転：移転ｱﾅｳﾝｽが流れている電話番号");
                                } else if ("3".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("都合停止：お客様都合で利用ができない電話番号");
                                } else if ("4".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("ｴﾗｰ：判定できない電話番号もしくは050、0120局番等の電話番号");
                                } else if ("6".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("ﾃﾞｰﾀ通信専用端末：モバイルWiFi等のデータ通信専用端末の電話番号");
                                } else if ("7".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("局預け：電話局に電話番号を預けている");
                                } else if ("8".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("再調査：判定できない電話番号");
                                } else if ("9".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("INS回線有効：電話番号が使われている（ISDN回線）");
                                } else if ("S".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("NTT以外：NTT以外で一部判定ができない電話番号");
                                }
                                if ("0".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("無効：電話番号が使われていない");
                                } else if ("1".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("有効：電話番号が使われている");
                                } else if ("2".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("移転：移転ｱﾅｳﾝｽが流れている電話番号");
                                } else if ("3".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("都合停止：お客様都合で利用ができない電話番号");
                                } else if ("4".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("ｴﾗｰ：判定できない電話番号もしくは050、0120局番等の電話番号");
                                } else if ("6".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("ﾃﾞｰﾀ通信専用端末：モバイルWiFi等のデータ通信専用端末の電話番号");
                                } else if ("7".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("局預け：電話局に電話番号を預けている");
                                } else if ("8".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("再調査：判定できない電話番号");
                                } else if ("9".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("INS回線有効：電話番号が使われている（ISDN回線）");
                                } else if ("S".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("NTT以外：NTT以外で一部判定ができない電話番号");
                                }
                                if ("-1".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("MSG該当なし：｢直近加入」「都合停止」「変更過多」｢長期有効」「エラー」いずれにも該当しない場合");
                                } else if ("1".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("直近加入：三カ月以内に有効になった番号");
                                } else if ("2".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("都合停止：半年以内に都合停止がある番号");
                                } else if ("3".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("変更過多：有効無効を3回以上繰り返している番号");
                                } else if ("4".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("長期有効：24ヵ月以上有効である番号");
                                } else if ("E".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("ｴﾗｰ：特殊番号等で調査不可");
                                } else if ("U".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("未調査：対象ﾃﾞｰﾀが無い番号");
                                }
                                if ("-1".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("MSG該当なし：｢直近加入」「都合停止」「変更過多」｢長期有効」「エラー」いずれにも該当しない場合");
                                } else if ("1".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("直近加入：三カ月以内に有効になった番号");
                                } else if ("2".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("都合停止：半年以内に都合停止がある番号");
                                } else if ("3".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("変更過多：有効無効を3回以上繰り返している番号");
                                } else if ("4".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("長期有効：24ヵ月以上有効である番号");
                                } else if ("E".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("ｴﾗｰ：特殊番号等で調査不可");
                                } else if ("U".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("未調査：対象ﾃﾞｰﾀが無い番号");
                                }
                            }
                            detailList.add(output);
                        }
                    }

                    // 帳票出力用の datasourceを準備
                    JRDataSource jrDataSource1 = new JRBeanCollectionDataSource(detailList);

                    // 帳票出力用のparameterを準備
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("year", date.substring(0, 4));
                    parameters.put("month", date.substring(4, 6));
                    parameters.put("day", date.substring(6, 8));
                    // プロジェクトのパスを取得
                    this.getClass().getClassLoader();
                    String jasperName = ApplicationKeys.YAMAGATAACCOUNTAPPLIST01_REPORT;
                    String outputFileName1 = ApplicationKeys.ACCOUNTAPPLIST107_REPORT;
                    // 帳票/CSV区分設定
                    String flg = "1";

                    // 帳票出力
                    reportBLogic.reportCreate(date, jasperName, (Map<String, Object>) parameters, jrDataSource1, flg,
                            outputFileName1, req, res);

                    accountAppListOutputButtonResVO2.setDate(date);

                    // 帳票出力用の datasourceを準備
                    JRDataSource jrDataSource2 = new JRBeanCollectionDataSource(detailList);
                    // プロジェクトのパスを取得
                    this.getClass().getClassLoader();

                    String jasperName1 = ApplicationKeys.ACCOUNTAPPLIST06_REPORT;
                    String outputFileName2 = ApplicationKeys.ACCOUNTAPPLIST106_REPORT;
                    // 帳票/CSV区分設定
                    String flg1 = "1";

                    // 帳票出力
                    reportBLogic.reportCreate(date, jasperName1, (Map<String, Object>) parameters, jrDataSource2, flg1,
                            outputFileName2, req, res);
                    accountAppListOutputButtonResVO2.setDate(date);
                }

                // ヘッダ設定（処理成功の場合）
                resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
                // ボーディ設定
                resEntityBody.setResultData(accountAppListOutputButtonResVO2);
                LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);

            }else if("0173".equals(bank_cd)){
                BaseResVO ba = accountAppListOutputService.execute(baseResVO);
                accountAppListOutputButtonResVO3 = (Account114AppListOutputButtonResVO) ba;

                // 画面に選択したデータをレコードずつPDF＆CSV出力する
                List<AccountApp114InitVO> checkList = accountAppListOutputButtonResVO3.getAccountAppList();
                if (checkList != null && checkList.size() != 0) {
                    // 当日日付を取得する（日付フォーマット）
                    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
                    String date = sdf.format(new Date());
                    // SimpleDateFormat sdfZipFormat = new
                    // SimpleDateFormat(Constants.DATE_FORMAT_ASS);
                    // String dateZipName = sdfZipFormat.format(new Date());
                    // 出力する日付フォルダーを作成
                    reportBLogic.datePathCreate(req, date);
                    List<AccountApp114InitVO> dateSourceList = new ArrayList<AccountApp114InitVO>();
                    List<AccountApp114InitVO> dateSourceList2 = new ArrayList<AccountApp114InitVO>();
                    List<ImageVO> imageList = new ArrayList<ImageVO>();

                    // データ出力順序を設定（申込受付番号（主キー）の昇順）
                    Comparator<AccountApp114InitVO> comparator = new Comparator<AccountApp114InitVO>() {
                        public int compare(AccountApp114InitVO s1, AccountApp114InitVO s2) {
                            if (s1.getAccountAppSeq().equals(s2.getAccountAppSeq())) {
                                return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                            } else if (s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq()) > 1) {
                                return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                            } else {
                                return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                            }
                        }
                    };

                    Collections.sort(checkList, comparator);

                    List<ErrVO> ErrList = new ArrayList<>();
                    for (AccountApp114InitVO vo : checkList) {
                        // 帳票出力用の datasourceを準備
                        if (Utils.isNotNullAndEmpty(vo.getIdentificationImage())) {
                            // 画像出力用データを取得
                            AccountAppDetailReqVO input = new AccountAppDetailReqVO();
                            input.set_id(vo.getIdentificationImage());
                            AccountApp114DetailResVO resVo = (AccountApp114DetailResVO) accountAppDetailImageSelService
                                    .execute(input);
                            resVo.setAccountAppSeq(vo.getAccountAppSeq());
                            resVo.setIdentificationType(vo.getIdentificationType());
                            resVo.setLivingConditions(vo.getLivingConditions());
                            // 本人確認書類保存＆ImageVOListセット（本人確認書類）
                            imageList.add(reportBLogic.ImageOutputSave3(date, resVo, req));
                        }

                        AccountApp114InitVO accountAppInitVO = new AccountApp114InitVO();
                        // CSVを出力設定
                        accountAppInitVO = accountAppReport3(vo);
                        // AccountAppInitVOListセット
                        dateSourceList.add(accountAppInitVO);
                        // }
                        accountAppListOutputCsvResVO.setImageList(imageList);
                    }

                    // 本人確認書類PDF生成
                    JRDataSource jrDataSource = new JRBeanCollectionDataSource(
                            accountAppListOutputCsvResVO.getImageList());
                    reportBLogic.ImageOutputCreate(date, jrDataSource, ApplicationKeys.ACCOUNTAPPLIST002_REPORT,
                            ApplicationKeys.ACCOUNTAPPLIST102_REPORT, req);

                    // プロジェクトのパスを取得
                    this.getClass().getClassLoader();
                    String outputFileName = "";
                    reportBLogic.dateZipCreate(req, res, date);
                    String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
                    //帳票出力用のZIP作成
                    String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH;
                    String zipFileName = tempPath + date + Constants.ZIP;
                    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
                    outputFileName = ApplicationKeys.ACCOUNTAPPLIST104_REPORT;
                    // CSVを作成
                    // csvCreate2("1", date, dateSourceList, outputFileName, req
                    // ,zipOutputStream);
                    accountAppListOutputButtonResVO3.setDate(date);
                    for (AccountApp114InitVO accountAppYamaGataInitVO : accountAppListOutputButtonResVO3
                            .getAccountAppList()) {
                        accountAppYamaGataExcelOutPutService.HyakujyushiExcelOutput(req, accountAppYamaGataInitVO, zipOutputStream);
                    }
                    zipOutputStream.close();


                    // 電話番号鑑定結果PDF出力
                    List<AccountAppYamaGataDetailResVO> detailList = new ArrayList<AccountAppYamaGataDetailResVO>();
                    for (int i = 0; i < accountAppListOutputButtonReqVO.getOutputList3().size(); i++) {
                        if (accountAppListOutputButtonReqVO.getOutputList3().get(i).getSelect() == null) {
                            continue;
                        }
                        // 一覧選択したデータ
                        AccountAppDetailReqVO detailReqVO = new AccountAppDetailReqVO();
                        if (accountAppListOutputButtonReqVO.getOutputList3().get(i).getSelect() == true) {
                            detailReqVO.set_id(accountAppListOutputButtonReqVO.getOutputList3().get(i).get_id());
                            baseResVO = detailReqVO;
                            AccountAppYamaGataDetailResVO output = new AccountAppYamaGataDetailResVO();
                            output = (AccountAppYamaGataDetailResVO) accountAppAppraisalDetailService
                                    .execute(baseResVO);
                            if (output != null) {
                                if (output.getTC_Month1() == null) {
                                    output.setTC_Month1("");
                                }
                                if (output.getTC_Month2() == null) {
                                    output.setTC_Month2("");
                                }
                                if ("high".equals(output.getIC_IpThreat())) {
                                    output.setIC_IpThreat("要注意");
                                } else if ("low".equals(output.getIC_IpThreat())) {
                                    output.setIC_IpThreat("低い");
                                }
                                if ("high".equals(output.getIC_CountryThreat())) {
                                    output.setIC_CountryThreat("要注意");
                                } else if ("low".equals(output.getIC_CountryThreat())) {
                                    output.setIC_CountryThreat("低い");
                                }
                                if ("true".equals(output.getIC_PSIP())) {
                                    output.setIC_PSIP("要注意");
                                } else if ("false".equals(output.getIC_PSIP())) {
                                    output.setIC_PSIP("-");
                                }
                                if ("true".equals(output.getIC_Proxy())) {
                                    output.setIC_Proxy("要注意（利用している可能性あり）");
                                } else if ("false".equals(output.getIC_Proxy())) {
                                    output.setIC_Proxy("-");
                                }
                                if ("0".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("検索成功");
                                } else if ("1".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("データ無し");
                                } else if ("2".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("電話番号の桁数不正");
                                } else if ("4".equals(output.getTC_Result1())) {
                                    output.setTC_Result1("特殊電話番号等調査対象外、予期しないエラー");
                                }
                                if ("0".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("検索成功");
                                } else if ("1".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("データ無し");
                                } else if ("2".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("電話番号の桁数不正");
                                } else if ("4".equals(output.getTC_Result2())) {
                                    output.setTC_Result2("特殊電話番号等調査対象外、予期しないエラー");
                                }
                                if ("0".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("無効：電話番号が使われていない");
                                } else if ("1".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("有効：電話番号が使われている");
                                } else if ("2".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("移転：移転ｱﾅｳﾝｽが流れている電話番号");
                                } else if ("3".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("都合停止：お客様都合で利用ができない電話番号");
                                } else if ("4".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("ｴﾗｰ：判定できない電話番号もしくは050、0120局番等の電話番号");
                                } else if ("6".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("ﾃﾞｰﾀ通信専用端末：モバイルWiFi等のデータ通信専用端末の電話番号");
                                } else if ("7".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("局預け：電話局に電話番号を預けている");
                                } else if ("8".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("再調査：判定できない電話番号");
                                } else if ("9".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("INS回線有効：電話番号が使われている（ISDN回線）");
                                } else if ("S".equals(output.getTC_Tacsflag1())) {
                                    output.setTC_Tacsflag1("NTT以外：NTT以外で一部判定ができない電話番号");
                                }
                                if ("0".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("無効：電話番号が使われていない");
                                } else if ("1".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("有効：電話番号が使われている");
                                } else if ("2".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("移転：移転ｱﾅｳﾝｽが流れている電話番号");
                                } else if ("3".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("都合停止：お客様都合で利用ができない電話番号");
                                } else if ("4".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("ｴﾗｰ：判定できない電話番号もしくは050、0120局番等の電話番号");
                                } else if ("6".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("ﾃﾞｰﾀ通信専用端末：モバイルWiFi等のデータ通信専用端末の電話番号");
                                } else if ("7".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("局預け：電話局に電話番号を預けている");
                                } else if ("8".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("再調査：判定できない電話番号");
                                } else if ("9".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("INS回線有効：電話番号が使われている（ISDN回線）");
                                } else if ("S".equals(output.getTC_Tacsflag2())) {
                                    output.setTC_Tacsflag2("NTT以外：NTT以外で一部判定ができない電話番号");
                                }
                                if ("-1".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("MSG該当なし：｢直近加入」「都合停止」「変更過多」｢長期有効」「エラー」いずれにも該当しない場合");
                                } else if ("1".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("直近加入：三カ月以内に有効になった番号");
                                } else if ("2".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("都合停止：半年以内に都合停止がある番号");
                                } else if ("3".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("変更過多：有効無効を3回以上繰り返している番号");
                                } else if ("4".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("長期有効：24ヵ月以上有効である番号");
                                } else if ("E".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("ｴﾗｰ：特殊番号等で調査不可");
                                } else if ("U".equals(output.getTC_Attention1())) {
                                    output.setTC_Attention1("未調査：対象ﾃﾞｰﾀが無い番号");
                                }
                                if ("-1".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("MSG該当なし：｢直近加入」「都合停止」「変更過多」｢長期有効」「エラー」いずれにも該当しない場合");
                                } else if ("1".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("直近加入：三カ月以内に有効になった番号");
                                } else if ("2".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("都合停止：半年以内に都合停止がある番号");
                                } else if ("3".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("変更過多：有効無効を3回以上繰り返している番号");
                                } else if ("4".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("長期有効：24ヵ月以上有効である番号");
                                } else if ("E".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("ｴﾗｰ：特殊番号等で調査不可");
                                } else if ("U".equals(output.getTC_Attention2())) {
                                    output.setTC_Attention2("未調査：対象ﾃﾞｰﾀが無い番号");
                                }
                            }
                            detailList.add(output);
                        }
                    }

                    // 帳票出力用の datasourceを準備
                    JRDataSource jrDataSource1 = new JRBeanCollectionDataSource(detailList);

                    // 帳票出力用のparameterを準備
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("year", date.substring(0, 4));
                    parameters.put("month", date.substring(4, 6));
                    parameters.put("day", date.substring(6, 8));
                    // プロジェクトのパスを取得
                    this.getClass().getClassLoader();
                    String jasperName = ApplicationKeys.YAMAGATAACCOUNTAPPLIST01_REPORT;
                    String outputFileName1 = ApplicationKeys.ACCOUNTAPPLIST107_REPORT;
                    // 帳票/CSV区分設定
                    String flg = "1";

                    // 帳票出力
                    reportBLogic.reportCreate(date, jasperName, (Map<String, Object>) parameters, jrDataSource1, flg,
                            outputFileName1, req, res);

                    accountAppListOutputButtonResVO3.setDate(date);

                    // 帳票出力用の datasourceを準備
                    JRDataSource jrDataSource2 = new JRBeanCollectionDataSource(detailList);
                    // プロジェクトのパスを取得
                    this.getClass().getClassLoader();

                    String jasperName1 = ApplicationKeys.ACCOUNTAPPLIST06_REPORT;
                    String outputFileName2 = ApplicationKeys.ACCOUNTAPPLIST106_REPORT;
                    // 帳票/CSV区分設定
                    String flg1 = "1";

                    // 帳票出力
                    reportBLogic.reportCreate(date, jasperName1, (Map<String, Object>) parameters, jrDataSource2, flg1,
                            outputFileName2, req, res);
                    accountAppListOutputButtonResVO3.setDate(date);
                }

                // ヘッダ設定（処理成功の場合）
                resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
                // ボーディ設定
                resEntityBody.setResultData(accountAppListOutputButtonResVO3);
                LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);

            }
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
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * CSV出力メソッド。
     * 
     * @param @RequestBody accountAppListCsvButtonReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/csvButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> csvButton(HttpServletRequest req, HttpServletResponse res,
            @RequestBody AccountAppListCsvButtonReqVO accountAppListCsvButtonReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        AccountAppListCsvButtonResVO accountAppListCsvButtonResVO = new AccountAppListCsvButtonResVO();
        AccountYamaGataAppListCsvButtonResVO accountAppListCsvButtonResVO2 = new AccountYamaGataAppListCsvButtonResVO();
        Account114AppListCsvButtonResVO accountAppListCsvButtonResVO3=new Account114AppListCsvButtonResVO();
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();
        baseResVO = accountAppListCsvButtonReqVO;

        try {
            if ("0169".equals(bank_cd)) {
                // CSV出力用データを抽出
                BaseResVO ba = accountAppListCsvService.execute(baseResVO);
                accountAppListCsvButtonResVO = (AccountAppListCsvButtonResVO) ba;
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
                String outputFileName = ApplicationKeys.ACCOUNTAPPLIST103_REPORT;
                // 帳票/CSV区分設定
                csvCreate1("1", date, accountAppListCsvButtonResVO.getAccountAppList(), outputFileName, req);
                // // CSV出力
                // reportBLogic.reportCreate(date, jasperName,
                // (Map<String, Object>) parameters, jrDataSource, flg,
                // outputFileName, req, res);

                // ヘッダ設定（処理成功の場合）
                accountAppListCsvButtonResVO.setDate(date);
                resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
                resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
                // ボーディ設定
                resEntityBody.setResultData(accountAppListCsvButtonResVO);
                LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
            } else if ("0122".equals(bank_cd)) {
                // CSV出力用データを抽出
                BaseResVO ba = accountAppListCsvService.execute(baseResVO);
                accountAppListCsvButtonResVO2 = (AccountYamaGataAppListCsvButtonResVO) ba;
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
                String outputFileName = ApplicationKeys.ACCOUNTAPPLIST103_REPORT;
                // 帳票/CSV区分設定
                csvCreate3("1", date, accountAppListCsvButtonResVO2.getAccountAppList(), outputFileName, req);
                // // CSV出力
                // reportBLogic.reportCreate(date, jasperName,
                // (Map<String, Object>) parameters, jrDataSource, flg,
                // outputFileName, req, res);

                // ヘッダ設定（処理成功の場合）
                accountAppListCsvButtonResVO2.setDate(date);
                resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
                resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
                // ボーディ設定
                resEntityBody.setResultData(accountAppListCsvButtonResVO2);
                LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
            }else if ("0173".equals(bank_cd)) {

                // CSV出力用データを抽出
                BaseResVO ba = accountAppListCsvService.execute(baseResVO);
                accountAppListCsvButtonResVO3 = (Account114AppListCsvButtonResVO) ba;
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
                String outputFileName = ApplicationKeys.ACCOUNTAPPLIST103_REPORT;
                // 帳票/CSV区分設定
                csvCreate4("1", date, accountAppListCsvButtonResVO3.getAccountAppList(), outputFileName, req);
                // // CSV出力
                // reportBLogic.reportCreate(date, jasperName,
                // (Map<String, Object>) parameters, jrDataSource, flg,
                // outputFileName, req, res);

                // ヘッダ設定（処理成功の場合）
                accountAppListCsvButtonResVO2.setDate(date);
                resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
                resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
                // ボーディ設定
                resEntityBody.setResultData(accountAppListCsvButtonResVO2);
                LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
            
            }
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
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }


    /**
     * 帳票出力項目をチェック。
     * 
     * @param AccountAppInitVO
     *            vo 帳票用データ
     * @return List<ErrVO>
     */
    public List<ErrVO> accountAppReportCheck(AccountAppInitVO vo) {

        SimpleDateFormat format1 = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        SimpleDateFormat format2 = new SimpleDateFormat(Constants.DATE_FORMAT_YMD);
        List<ErrVO> errList = new ArrayList<>();
        if (Utils.isNotNullAndEmpty(vo.getApplicationDate())) {
            try {
                format1.setLenient(false);
                format1.parse(vo.getApplicationDate());
            } catch (ParseException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1001, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getLastName())) {
            try {
                if (vo.getLastName().getBytes("SHIFT-JIS").length > 30) {
                    ErrVO errVO = new ErrVO();
                    errVO.setAccountAppSeq(vo.getAccountAppSeq());
                    errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1002, new Object[] {},
                            LocaleContextHolder.getLocale()));
                    errList.add(errVO);
                }
            } catch (UnsupportedEncodingException | NoSuchMessageException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1002, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getFirstName())) {
            try {
                if (vo.getFirstName().getBytes("SHIFT-JIS").length > 30) {
                    ErrVO errVO = new ErrVO();
                    errVO.setAccountAppSeq(vo.getAccountAppSeq());
                    errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1003, new Object[] {},
                            LocaleContextHolder.getLocale()));
                    errList.add(errVO);
                }
            } catch (UnsupportedEncodingException | NoSuchMessageException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1003, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getKanaLastName())) {
            try {
                if (vo.getKanaLastName().getBytes("SHIFT-JIS").length > 30) {
                    ErrVO errVO = new ErrVO();
                    errVO.setAccountAppSeq(vo.getAccountAppSeq());
                    errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1004, new Object[] {},
                            LocaleContextHolder.getLocale()));
                    errList.add(errVO);
                }
            } catch (UnsupportedEncodingException | NoSuchMessageException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1004, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getKanaFirstName())) {
            try {
                if (vo.getKanaFirstName().getBytes("SHIFT-JIS").length > 30) {
                    ErrVO errVO = new ErrVO();
                    errVO.setAccountAppSeq(vo.getAccountAppSeq());
                    errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1005, new Object[] {},
                            LocaleContextHolder.getLocale()));
                    errList.add(errVO);
                }
            } catch (UnsupportedEncodingException | NoSuchMessageException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1005, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getBirthday())) {
            try {
                format2.setLenient(false);
                format2.parse(vo.getBirthday());
            } catch (ParseException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1006, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getAge())) {
            try {
                Integer.parseInt(vo.getAge());
            } catch (NumberFormatException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1007, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
            if (Integer.parseInt(vo.getAge()) < 18) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1008, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getPostCode())) {
            if (vo.getPostCode().length() < 8) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1009, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getAddress1())) {
            try {
                if (vo.getAddress1().getBytes("SHIFT-JIS").length > 8) {
                    ErrVO errVO = new ErrVO();
                    errVO.setAccountAppSeq(vo.getAccountAppSeq());
                    errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1010, new Object[] {},
                            LocaleContextHolder.getLocale()));
                    errList.add(errVO);
                }
            } catch (UnsupportedEncodingException | NoSuchMessageException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1010, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getAddress2())) {
            try {
                if (vo.getAddress2().getBytes("SHIFT-JIS").length > 90) {
                    ErrVO errVO = new ErrVO();
                    errVO.setAccountAppSeq(vo.getAccountAppSeq());
                    errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1011, new Object[] {},
                            LocaleContextHolder.getLocale()));
                    errList.add(errVO);
                }
            } catch (UnsupportedEncodingException | NoSuchMessageException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1011, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getKanaAddress())) {
            try {
                if (vo.getKanaAddress().getBytes("SHIFT-JIS").length > 120) {
                    ErrVO errVO = new ErrVO();
                    errVO.setAccountAppSeq(vo.getAccountAppSeq());
                    errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1012, new Object[] {},
                            LocaleContextHolder.getLocale()));
                    errList.add(errVO);
                }
            } catch (UnsupportedEncodingException | NoSuchMessageException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1012, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getTeleNumber())) {
            if (vo.getTeleNumber().length() > 20) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1013, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getPhoneNumber())) {
            if (vo.getPhoneNumber().length() > 20) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1014, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getWorkTeleNumber())) {
            if (vo.getWorkTeleNumber().length() > 20) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1015, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getTradingPurposes10())) {
            try {
                if (vo.getTradingPurposes10().getBytes("SHIFT-JIS").length > 40) {
                    ErrVO errVO = new ErrVO();
                    errVO.setAccountAppSeq(vo.getAccountAppSeq());
                    errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1016, new Object[] {},
                            LocaleContextHolder.getLocale()));
                    errList.add(errVO);
                }
            } catch (UnsupportedEncodingException | NoSuchMessageException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1016, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getOccupation13())) {
            try {
                if (vo.getOccupation13().getBytes("SHIFT-JIS").length > 20) {
                    ErrVO errVO = new ErrVO();
                    errVO.setAccountAppSeq(vo.getAccountAppSeq());
                    errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1017, new Object[] {},
                            LocaleContextHolder.getLocale()));
                    errList.add(errVO);
                }
            } catch (UnsupportedEncodingException | NoSuchMessageException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1017, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getHoldAccountBankNumber())) {
            try {
                Integer.parseInt(vo.getHoldAccountBankNumber());
            } catch (NumberFormatException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1018, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
            if (vo.getHoldAccountBankNumber().length() > 3) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1019, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getHoldAccountNumber())) {
            try {
                Integer.parseInt(vo.getHoldAccountNumber());
            } catch (NumberFormatException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1020, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
            if (vo.getHoldAccountNumber().length() > 7) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1021, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getDirectServicesContractBankNumber())) {
            try {
                Integer.parseInt(vo.getDirectServicesContractBankNumber());
            } catch (NumberFormatException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1022, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
            if (vo.getDirectServicesContractBankNumber().length() > 3) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1023, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getDirectServicesContractAccountNumber())) {
            try {
                Integer.parseInt(vo.getDirectServicesContractAccountNumber());
            } catch (NumberFormatException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1024, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
            if (vo.getDirectServicesContractAccountNumber().length() > 7) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1025, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (vo.getTelRegisterPerTrans() > 500) {
            ErrVO errVO = new ErrVO();
            errVO.setAccountAppSeq(vo.getAccountAppSeq());
            errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1026, new Object[] {},
                    LocaleContextHolder.getLocale()));
            errList.add(errVO);
        }
        if (vo.getTelRegisterPerDay() > 500) {
            ErrVO errVO = new ErrVO();
            errVO.setAccountAppSeq(vo.getAccountAppSeq());
            errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1027, new Object[] {},
                    LocaleContextHolder.getLocale()));
            errList.add(errVO);
        }
        if (vo.getTelOncePerTrans() > 50) {
            ErrVO errVO = new ErrVO();
            errVO.setAccountAppSeq(vo.getAccountAppSeq());
            errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1028, new Object[] {},
                    LocaleContextHolder.getLocale()));
            errList.add(errVO);
        }
        if (vo.getTelOncePerDay() > 50) {
            ErrVO errVO = new ErrVO();
            errVO.setAccountAppSeq(vo.getAccountAppSeq());
            errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1029, new Object[] {},
                    LocaleContextHolder.getLocale()));
            errList.add(errVO);
        }
        if (vo.getInternetRegisterPerTrans() > 500) {
            ErrVO errVO = new ErrVO();
            errVO.setAccountAppSeq(vo.getAccountAppSeq());
            errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1030, new Object[] {},
                    LocaleContextHolder.getLocale()));
            errList.add(errVO);
        }
        if (vo.getInternetRegisterPerDay() > 500) {
            ErrVO errVO = new ErrVO();
            errVO.setAccountAppSeq(vo.getAccountAppSeq());
            errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1031, new Object[] {},
                    LocaleContextHolder.getLocale()));
            errList.add(errVO);
        }
        if (vo.getInternetOncePerTrans() > 100) {
            ErrVO errVO = new ErrVO();
            errVO.setAccountAppSeq(vo.getAccountAppSeq());
            errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1032, new Object[] {},
                    LocaleContextHolder.getLocale()));
            errList.add(errVO);
        }
        if (vo.getInternetOncePerDay() > 100) {
            ErrVO errVO = new ErrVO();
            errVO.setAccountAppSeq(vo.getAccountAppSeq());
            errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1033, new Object[] {},
                    LocaleContextHolder.getLocale()));
            errList.add(errVO);
        }
        if (Utils.isNotNullAndEmpty(vo.getSecurityPassword())) {
            try {
                Integer.parseInt(vo.getSecurityPassword());
            } catch (NumberFormatException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1034, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        if (Utils.isNotNullAndEmpty(vo.getDirectServicesCardPassword())) {
            try {
                Integer.parseInt(vo.getDirectServicesCardPassword());
            } catch (NumberFormatException e) {
                ErrVO errVO = new ErrVO();
                errVO.setAccountAppSeq(vo.getAccountAppSeq());
                errVO.setMessage(messageSource.getMessage(MessageKeys.E_ACCOUNTAPPLISTERR_1035, new Object[] {},
                        LocaleContextHolder.getLocale()));
                errList.add(errVO);
            }
        }
        return errList;
    }

    /**
     * 帳票を出力。
     * 
     * @param AccountAppYamaGataInitVO
     *            vo 帳票用データ
     * @return AccountAppYamaGataInitVO accountAppInitVO
     */
    public AccountAppYamaGataInitVO accountAppReport2(AccountAppYamaGataInitVO vo) {

        AccountAppYamaGataInitVO accountAppInitVO = new AccountAppYamaGataInitVO();
        accountAppInitVO = vo;

        List<String> accountPurpose = new ArrayList<String>();
        accountPurpose = vo.getAccountPurpose();
        accountAppInitVO.setAccountPurpose1("0");
        accountAppInitVO.setAccountPurpose2("0");
        accountAppInitVO.setAccountPurpose3("0");
        accountAppInitVO.setAccountPurpose4("0");
        accountAppInitVO.setAccountPurpose5("0");
        accountAppInitVO.setAccountPurpose6("0");
        accountAppInitVO.setAccountPurpose7("0");
        accountAppInitVO.setAccountPurpose8("0");
        accountAppInitVO.setAccountPurpose9("0");
        accountAppInitVO.setAccountPurpose99("0");
        for (int count = 0; count < accountPurpose.size(); count++) {
            switch (accountPurpose.get(count)) {
            case "01":
                accountAppInitVO.setAccountPurpose1("1");
                break;
            case "02":
                accountAppInitVO.setAccountPurpose2("1");
                break;
            case "03":
                accountAppInitVO.setAccountPurpose3("1");
                break;
            case "04":
                accountAppInitVO.setAccountPurpose4("1");
                break;
            case "05":
                accountAppInitVO.setAccountPurpose5("1");
                break;
            case "06":
                accountAppInitVO.setAccountPurpose6("1");
                break;
            case "07":
                accountAppInitVO.setAccountPurpose7("1");
                break;
            case "08":
                accountAppInitVO.setAccountPurpose8("1");
                break;
            case "09":
                accountAppInitVO.setAccountPurpose9("1");
                break;
            case "99":
                accountAppInitVO.setAccountPurpose99("1");
                accountAppInitVO.setAccountPurpose99(vo.getAccountPurposeOther());
            }
        }

        List<String> jobKbn = new ArrayList<String>();
        jobKbn = vo.getJobKbn();
        accountAppInitVO.setJobKbn1("0");
        accountAppInitVO.setJobKbn2("0");
        accountAppInitVO.setJobKbn3("0");
        accountAppInitVO.setJobKbn4("0");
        accountAppInitVO.setJobKbn5("0");
        accountAppInitVO.setJobKbn6("0");
        accountAppInitVO.setJobKbn7("0");
        accountAppInitVO.setJobKbn8("0");
        accountAppInitVO.setJobKbn9("0");
        accountAppInitVO.setJobKbn10("0");
        accountAppInitVO.setJobKbn49("0");
        for (int count = 0; count < jobKbn.size(); count++) {
            switch (jobKbn.get(count)) {
            case "01":
                accountAppInitVO.setJobKbn1("1");
                break;
            case "02":
                accountAppInitVO.setJobKbn2("1");
                break;
            case "03":
                accountAppInitVO.setJobKbn3("1");
                break;
            case "04":
                accountAppInitVO.setJobKbn4("1");
                break;
            case "05":
                accountAppInitVO.setJobKbn5("1");
                break;
            case "06":
                accountAppInitVO.setJobKbn6("1");
                break;
            case "07":
                accountAppInitVO.setJobKbn7("1");
                break;
            case "08":
                accountAppInitVO.setJobKbn8("1");
                break;
            case "09":
                accountAppInitVO.setJobKbn9("1");
                break;
            case "10":
                accountAppInitVO.setJobKbn10("1");
                break;
            case "49":
                accountAppInitVO.setJobKbn49("1");
                accountAppInitVO.setJobKbn49(vo.getJobKbnOther());
            }
        }

        accountAppInitVO.setLastName(vo.getLastName());
        accountAppInitVO.setFirstName(vo.getFirstName());
        accountAppInitVO.setKanaLastName(vo.getKanaLastName());
        accountAppInitVO.setKanaFirstName(vo.getKanaFirstName());
        accountAppInitVO.setBirthday(vo.getBirthday());
        accountAppInitVO.setSexKbn(vo.getSexKbn());
        accountAppInitVO.setPostCode(vo.getPostCode());
        accountAppInitVO.setTeleNumber(vo.getTeleNumber());
        accountAppInitVO.setPhoneNumber(vo.getPhoneNumber());
        accountAppInitVO.setKnowProcess(vo.getKnowProcess());
        accountAppInitVO.setApplicationDate(vo.getApplicationDate());
        accountAppInitVO.setPrefecturesCode(vo.getPrefecturesCode());

        return accountAppInitVO;
    }

    /**
     * 帳票を出力。
     * 
     * @param AccountAppYamaGataInitVO
     *            vo 帳票用データ
     * @return AccountAppYamaGataInitVO accountAppInitVO
     */
    public AccountApp114InitVO accountAppReport3(AccountApp114InitVO vo) {

        AccountApp114InitVO accountAppInitVO = new AccountApp114InitVO();
        accountAppInitVO = vo;

        List<String> accountPurpose = new ArrayList<String>();
        accountPurpose = vo.getAccountPurpose();
        accountAppInitVO.setAccountPurpose1("0");
        accountAppInitVO.setAccountPurpose2("0");
        accountAppInitVO.setAccountPurpose3("0");
        accountAppInitVO.setAccountPurpose4("0");
        accountAppInitVO.setAccountPurpose5("0");
        accountAppInitVO.setAccountPurpose6("0");
        accountAppInitVO.setAccountPurpose7("0");
        accountAppInitVO.setAccountPurpose99("0");
        for (int count = 0; count < accountPurpose.size(); count++) {
            switch (accountPurpose.get(count)) {
            case "01":
                accountAppInitVO.setAccountPurpose1("1");
                break;
            case "02":
                accountAppInitVO.setAccountPurpose2("1");
                break;
            case "03":
                accountAppInitVO.setAccountPurpose3("1");
                break;
            case "04":
                accountAppInitVO.setAccountPurpose4("1");
                break;
            case "05":
                accountAppInitVO.setAccountPurpose5("1");
                break;
            case "06":
                accountAppInitVO.setAccountPurpose6("1");
                break;
            case "07":
                accountAppInitVO.setAccountPurpose7("1");
                break;
            case "99":
                accountAppInitVO.setAccountPurpose99("1");
                accountAppInitVO.setAccountPurpose99(vo.getAccountPurposeOther());
            }
        }

        List<String> jobKbn = new ArrayList<String>();
        jobKbn = vo.getJobKbn();
        accountAppInitVO.setJobKbn101("0");
        accountAppInitVO.setJobKbn102("0");
        accountAppInitVO.setJobKbn103("0");
        accountAppInitVO.setJobKbn104("0");
        accountAppInitVO.setJobKbn105("0");
        accountAppInitVO.setJobKbn106("0");
        accountAppInitVO.setJobKbn107("0");
        accountAppInitVO.setJobKbn108("0");
        accountAppInitVO.setJobKbn199("0");

        for (int count = 0; count < jobKbn.size(); count++) {
            switch (jobKbn.get(count)) {
            case "101":
                accountAppInitVO.setJobKbn101("1");
                break;
            case "102":
                accountAppInitVO.setJobKbn102("1");
                break;
            case "103":
                accountAppInitVO.setJobKbn103("1");
                break;
            case "104":
                accountAppInitVO.setJobKbn104("1");
                break;
            case "105":
                accountAppInitVO.setJobKbn105("1");
                break;
            case "106":
                accountAppInitVO.setJobKbn106("1");
                break;
            case "107":
                accountAppInitVO.setJobKbn107("1");
                break;
            case "108":
                accountAppInitVO.setJobKbn108("1");
                break;
            case "199":
                accountAppInitVO.setJobKbn199("1");
                accountAppInitVO.setJobKbn199(vo.getJobKbnOther());
            }
        }

        accountAppInitVO.setLastName(vo.getLastName());
        accountAppInitVO.setFirstName(vo.getFirstName());
        accountAppInitVO.setKanaLastName(vo.getKanaLastName());
        accountAppInitVO.setKanaFirstName(vo.getKanaFirstName());
        accountAppInitVO.setBirthday(vo.getBirthday());
        accountAppInitVO.setSexKbn(vo.getSexKbn());
        accountAppInitVO.setPostCode(vo.getPostCode());
        accountAppInitVO.setTeleNumber(vo.getTeleNumber());
        accountAppInitVO.setPhoneNumber(vo.getPhoneNumber());
        accountAppInitVO.setKnowProcess(vo.getKnowProcess());
        accountAppInitVO.setApplicationDate(vo.getApplicationDate());
        accountAppInitVO.setPrefecturesCode(vo.getPrefecturesCode());

        return accountAppInitVO;
    }
    
    /**
     * 帳票を出力。
     * 
     * @param AccountAppInitVO
     *            vo 帳票用データ
     * @return AccountAppInitVO accountAppInitVO
     */
    public AccountAppInitVO accountAppReport(AccountAppInitVO vo) {

        AccountAppInitVO accountAppInitVO = new AccountAppInitVO();
        accountAppInitVO = vo;

        List<String> noApplicationService = new ArrayList<String>();
        noApplicationService = vo.getNoApplicationService();
        accountAppInitVO.setNoApplicationService1("0");
        accountAppInitVO.setNoApplicationService2("0");
        for (int count = 0; count < noApplicationService.size(); count++) {
            switch (noApplicationService.get(count)) {
            case "3":
                accountAppInitVO.setNoApplicationService1("1");
                break;
            case "2":
                accountAppInitVO.setNoApplicationService2("1");
            }
        }

        List<String> tradingPurposes = new ArrayList<String>();
        tradingPurposes = vo.getTradingPurposes();
        accountAppInitVO.setTradingPurposes1("0");
        accountAppInitVO.setTradingPurposes2("0");
        accountAppInitVO.setTradingPurposes3("0");
        accountAppInitVO.setTradingPurposes4("0");
        accountAppInitVO.setTradingPurposes5("0");
        accountAppInitVO.setTradingPurposes6("");
        accountAppInitVO.setTradingPurposes7("");
        accountAppInitVO.setTradingPurposes8("");
        accountAppInitVO.setTradingPurposes9("0");
        accountAppInitVO.setTradingPurposes10("");
        for (int count = 0; count < tradingPurposes.size(); count++) {
            switch (tradingPurposes.get(count)) {
            case "1":
                accountAppInitVO.setTradingPurposes1("1");
                break;
            case "2":
                accountAppInitVO.setTradingPurposes4("1");
                break;
            case "3":
                accountAppInitVO.setTradingPurposes2("1");
                break;
            case "4":
                accountAppInitVO.setTradingPurposes5("1");
                break;
            case "5":
                accountAppInitVO.setTradingPurposes3("1");
                break;
            case "6":
                accountAppInitVO.setTradingPurposes9("1");
                accountAppInitVO.setTradingPurposes10(vo.getOtherTradingPurposes());
            }
        }

        List<String> occupation = new ArrayList<String>();
        occupation = vo.getOccupation();
        accountAppInitVO.setOccupation1("0");
        accountAppInitVO.setOccupation2("0");
        accountAppInitVO.setOccupation3("0");
        accountAppInitVO.setOccupation4("0");
        accountAppInitVO.setOccupation5("0");
        accountAppInitVO.setOccupation6("0");
        accountAppInitVO.setOccupation7("0");
        accountAppInitVO.setOccupation8("0");
        accountAppInitVO.setOccupation9("");
        accountAppInitVO.setOccupation10("");
        accountAppInitVO.setOccupation11("");
        accountAppInitVO.setOccupation12("0");
        accountAppInitVO.setOccupation13("");
        for (int count = 0; count < occupation.size(); count++) {
            switch (occupation.get(count)) {
            case "01":
                accountAppInitVO.setOccupation1("1");
                break;
            case "02":
                accountAppInitVO.setOccupation2("1");
                break;
            case "03":
                accountAppInitVO.setOccupation3("1");
                break;
            case "04":
                accountAppInitVO.setOccupation4("1");
                break;
            case "05":
                accountAppInitVO.setOccupation5("1");
                break;
            case "06":
                accountAppInitVO.setOccupation6("1");
                break;
            case "07":
                accountAppInitVO.setOccupation7("1");
                break;
            case "08":
                accountAppInitVO.setOccupation8("1");
                break;
            case "90":
                accountAppInitVO.setOccupation12("1");
                accountAppInitVO.setOccupation13(vo.getOtherOccupations());
            }
        }

        accountAppInitVO.setLastName(vo.getLastName());
        accountAppInitVO.setFirstName(vo.getFirstName());
        accountAppInitVO.setKanaLastName(vo.getKanaLastName());
        accountAppInitVO.setKanaFirstName(vo.getKanaFirstName());
        accountAppInitVO.setBirthday(vo.getBirthday());
        accountAppInitVO.setSex(vo.getSex());
        accountAppInitVO.setPostCode(vo.getPostCode());
        accountAppInitVO.setTeleNumber(vo.getTeleNumber());
        accountAppInitVO.setPhoneNumber(vo.getPhoneNumber());
        accountAppInitVO.setCardType(vo.getCardType());
        accountAppInitVO.setDirectServicesContract(vo.getDirectServicesContract());
        accountAppInitVO.setAccountAppMotive(vo.getAccountAppMotive());
        accountAppInitVO.setKnowProcess(vo.getKnowProcess());
        accountAppInitVO.setApplicationDate(vo.getApplicationDate());
        accountAppInitVO.setChannel(vo.getChannel());
        accountAppInitVO.setEmail(vo.getEmail());
        accountAppInitVO.setPrefectures(vo.getAddress1());

        if (vo.getAddress2().length() <= 20) {
            accountAppInitVO.setAddress01(vo.getAddress2());
            accountAppInitVO.setAddress02("");
        } else {
            accountAppInitVO.setAddress01(vo.getAddress2().substring(0, 20));
            accountAppInitVO.setAddress02(vo.getAddress2().substring(20));
        }
        if (vo.getKanaAddress().length() <= 30) {
            accountAppInitVO.setKanaAddress1(vo.getKanaAddress());
            accountAppInitVO.setKanaAddress2("");
        } else {
            accountAppInitVO.setKanaAddress1(vo.getKanaAddress().substring(0, 30));
            accountAppInitVO.setKanaAddress2(vo.getKanaAddress().substring(30));
        }

        return accountAppInitVO;
    }

    /**
     * 一覧出力ボタン受付進捗管理表出力PDFを開けるメソッド。
     * 
     * @param @RequestParam date 当日日付
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/downLoad", method = RequestMethod.GET)
    public void downLoad(HttpServletRequest req, HttpServletResponse res, @RequestParam("date") String date) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppListCompleteButtonReqVO> resEntityBody = new ResponseEntityVO<AccountAppListCompleteButtonReqVO>();

        try {
            // PDFを開ける
            String outputFileName = ApplicationKeys.ACCOUNTAPPLIST101_REPORT;
            reportBLogic.pdfOpen(req, res, date, outputFileName);
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
        // return new
        // ResponseEntity<ResponseEntityVO<AccountAppListCompleteButtonReqVO>>(resEntityBody,
        // HttpStatus.OK);
    }

    /**
     * 帳票出力ボタン本人確認書類PDFを開けるメソッド。
     * 
     * @param @RequestParam date 当日日付
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/imageOpen", method = RequestMethod.GET)
    public void pdfOpen(HttpServletRequest req, HttpServletResponse res, @RequestParam("date") String date) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppListCompleteButtonReqVO> resEntityBody = new ResponseEntityVO<AccountAppListCompleteButtonReqVO>();

        try {
            // PDFを開ける
            String outputFileName = ApplicationKeys.ACCOUNTAPPLIST102_REPORT;
            reportBLogic.pdfOpen2(req, res, date, outputFileName);
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
        // return new
        // ResponseEntity<ResponseEntityVO<AccountAppListCompleteButtonReqVO>>(resEntityBody,
        // HttpStatus.OK);
    }

    /**
     * 帳票出力ボタン電話番号鑑定PDFを開けるメソッド。
     * 
     * @param @RequestParam date 当日日付
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/phoneOpen", method = RequestMethod.GET)
    public void pdfOpen1(HttpServletRequest req, HttpServletResponse res, @RequestParam("date") String date) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppListCompleteButtonReqVO> resEntityBody = new ResponseEntityVO<AccountAppListCompleteButtonReqVO>();

        try {
            // PDFを開ける
            String outputFileName = ApplicationKeys.ACCOUNTAPPLIST107_REPORT;
            reportBLogic.pdfOpen4(req, res, date, outputFileName);
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
        // return new
        // ResponseEntity<ResponseEntityVO<AccountAppListCompleteButtonReqVO>>(resEntityBody,
        // HttpStatus.OK);
    }

    /**
     * 帳票出力ボタンＩＰアドレス鑑定PDFを開けるメソッド。
     * 
     * @param @RequestParam date 当日日付
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/ipOpen", method = RequestMethod.GET)
    public void pdfOpen2(HttpServletRequest req, HttpServletResponse res, @RequestParam("date") String date) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppListCompleteButtonReqVO> resEntityBody = new ResponseEntityVO<AccountAppListCompleteButtonReqVO>();

        try {
            // PDFを開ける
            String outputFileName = ApplicationKeys.ACCOUNTAPPLIST106_REPORT;
            reportBLogic.pdfOpen5(req, res, date, outputFileName);
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
        // return new
        // ResponseEntity<ResponseEntityVO<AccountAppListCompleteButtonReqVO>>(resEntityBody,
        // HttpStatus.OK);
    }

    /**
     * 帳票出力ボタンCSVファイルダウンロードメソッド。
     * 
     * @param @RequestParam date 当日日付
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/csvDownLoad", method = RequestMethod.GET)
    public void csvDownLoad(HttpServletRequest req, HttpServletResponse res, @RequestParam("date") String date) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppListCompleteButtonReqVO> resEntityBody = new ResponseEntityVO<AccountAppListCompleteButtonReqVO>();

        try {
            // String outputFileName = ApplicationKeys.ACCOUNTAPPLIST104_REPORT;
            // ZIPファイルダウンロード
            reportBLogic.zipDownLoad(req, res, date);

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
        // return new
        // ResponseEntity<ResponseEntityVO<AccountAppListCompleteButtonReqVO>>(resEntityBody,
        // HttpStatus.OK);
    }

    /**
     * CSVボタンダウン‐ロードメソッド。
     * 
     * @param @RequestParam date 当日日付
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/csvButtonDownLoad", method = RequestMethod.GET)
    public void csvButtonDownLoad(HttpServletRequest req, HttpServletResponse res, @RequestParam("date") String date) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppListCompleteButtonReqVO> resEntityBody = new ResponseEntityVO<AccountAppListCompleteButtonReqVO>();

        try {
            // CSV出力用のフォルダー
            String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
            // 帳票出力用の詳細フォルダーを取得
            String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";

            // ZIPファイルダウンロード
            String zipName = ApplicationKeys.ACCOUNTAPPLIST103_REPORT + Constants.CSV;
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
        // return new
        // ResponseEntity<ResponseEntityVO<AccountAppListCompleteButtonReqVO>>(resEntityBody,
        // HttpStatus.OK);
    }

    /**
     * 帳票出力ボタンCSVファイル生成メソッド。
     * 
     * @param tacsflag
     *            TACSフラグ
     * @return date TACSフラグ値
     * @throws Exception
     */
    public void csvCreate(String flg, String date, List<AccountAppInitVO> dateSourceList, String outputFileName,
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

            for (int i = 0; i < dateSourceList.size(); i++) {
                // 住所１
                String address1 = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getAddress01())) {
                    address1 = dateSourceList.get(i).getAddress01();
                    address1 = address1.replace("\"", "\"\"");
                }
                // 住所２
                String address2 = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getAddress02())) {
                    address2 = dateSourceList.get(i).getAddress02();
                    address2 = address2.replace("\"", "\"\"");
                }
                // 職業
                String otherOccupations = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getOccupation13())) {
                    otherOccupations = dateSourceList.get(i).getOccupation13();
                    otherOccupations = otherOccupations.replace("\"", "\"\"");
                }
                // 勤務先名
                String workName = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getWorkName())) {
                    workName = dateSourceList.get(i).getWorkName();
                    workName = workName.replace("\"", "\"\"");
                }
                // 口座開設の取引目的
                String otherTradingPurposes = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getTradingPurposes10())) {
                    otherTradingPurposes = dateSourceList.get(i).getTradingPurposes10();
                    otherTradingPurposes = otherTradingPurposes.replace("\"", "\"\"");
                }

                String s2 = "";
                String s1 = "\"" + dateSourceList.get(i).getAccountAppSeq() + "\"" + "," + "\""
                        + dateSourceList.get(i).getChannel() + "\"" + "," + "\""
                        + dateSourceList.get(i).getApplicationDate() + "\"" + "," + "\""
                        + dateSourceList.get(i).getLastName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getFirstName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getKanaLastName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getKanaFirstName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getBirthday() + "\"" + "," + dateSourceList.get(i).getAge() + ","
                        + "\"" + dateSourceList.get(i).getSex() + "\"" + "," + "\""
                        + dateSourceList.get(i).getPostCode() + "\"" + "," + "\""
                        + dateSourceList.get(i).getPrefectures() + "\"" + "," + "\"" + address1 + "\"" + "," + "\""
                        + address2 + "\"" + "," + "\"" + dateSourceList.get(i).getKanaAddress1() + "\"" + "," + "\""
                        + dateSourceList.get(i).getKanaAddress2() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTeleNumber() + "\"" + "," + "\""
                        + dateSourceList.get(i).getPhoneNumber() + "\"" + "," + "\"" + dateSourceList.get(i).getEmail()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getCardType() + "\"" + "," + "\""
                        + dateSourceList.get(i).getNoApplicationService1() + "\"" + "," + "\""
                        + dateSourceList.get(i).getNoApplicationService2() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTradingPurposes1() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTradingPurposes2() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTradingPurposes3() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTradingPurposes4() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTradingPurposes5() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTradingPurposes6() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTradingPurposes7() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTradingPurposes8() + "\"" + "," + "\""
                        + dateSourceList.get(i).getTradingPurposes9() + "\"" + "," + "\"" + otherTradingPurposes + "\""
                        + "," + "\"" + dateSourceList.get(i).getOccupation1() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation2() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation3() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation4() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation5() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation6() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation7() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation8() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation9() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation10() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation11() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOccupation12() + "\"" + "," + "\"" + otherOccupations + "\"" + ","
                        + "\"" + workName + "\"" + "," + "\"" + dateSourceList.get(i).getWorkTeleNumber() + "\"" + ","
                        + dateSourceList.get(i).getStoreCode() + "," + dateSourceList.get(i).getAccountDeposit() + ","
                        + dateSourceList.get(i).getHoldAccountNumber() + "," + "\""
                        + dateSourceList.get(i).getDirectServicesContract() + "\"" + ","
                        + dateSourceList.get(i).getDirectServicesContractBankNumber() + ","
                        + dateSourceList.get(i).getDirectServicesContractAccountNumber() + "," + "\""
                        + dateSourceList.get(i).getAccountAppMotive() + "\"" + "," + "\""
                        + dateSourceList.get(i).getKnowProcess() + "\"" + ","
                        + dateSourceList.get(i).getTelRegisterPerTrans() + ","
                        + dateSourceList.get(i).getTelRegisterPerDay() + ","
                        + dateSourceList.get(i).getTelOncePerTrans() + "," + dateSourceList.get(i).getTelOncePerDay()
                        + "," + dateSourceList.get(i).getInternetRegisterPerTrans() + ","
                        + dateSourceList.get(i).getInternetRegisterPerDay() + ","
                        + dateSourceList.get(i).getInternetOncePerTrans() + ","
                        + dateSourceList.get(i).getInternetOncePerDay() + ","
                        + dateSourceList.get(i).getSecurityPassword() + ","
                        + dateSourceList.get(i).getDirectServicesCardPassword();
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
     * 帳票出力ボタンCSVファイル生成メソッド。
     * 
     * @param tacsflag
     *            TACSフラグ
     * @return date TACSフラグ値
     * @throws Exception
     */
    public void csvCreate2(String flg, String date, List<AccountAppYamaGataInitVO> dateSourceList,
            String outputFileName, HttpServletRequest req, ZipOutputStream zipOutputStream) {
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

            for (int i = 0; i < dateSourceList.size(); i++) {
                // 都道府県
                String prefecturesCode = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getPrefecturesCode())) {
                    prefecturesCode = dateSourceList.get(i).getPrefecturesCode();
                    prefecturesCode = prefecturesCode.replace("\"", "\"\"");
                }
                // 市区町村・番地・アパート・マンション名
                String address = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getAddress())) {
                    address = dateSourceList.get(i).getAddress();
                    address = address.replace("\"", "\"\"");
                }
                // 職業
                String jobKbnOther = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getJobKbn49())) {
                    jobKbnOther = dateSourceList.get(i).getJobKbn49();
                    jobKbnOther = jobKbnOther.replace("\"", "\"\"");
                }
                // 勤務先名
                String workName = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getWorkName())) {
                    workName = dateSourceList.get(i).getWorkName();
                    workName = workName.replace("\"", "\"\"");
                }
                // 口座開設の取引目的
                String accountPurposeOther = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getAccountPurpose99())) {
                    accountPurposeOther = dateSourceList.get(i).getAccountPurpose99();
                    accountPurposeOther = accountPurposeOther.replace("\"", "\"\"");
                }

                String s2 = "";
                String s1 = "\"" + dateSourceList.get(i).getAccountAppSeq() + "\"" + "," + "\""
                        + dateSourceList.get(i).getChannel() + "\"" + "," + "\""
                        + dateSourceList.get(i).getApplicationDate() + "\"" + "," + "\""
                        + dateSourceList.get(i).getLastName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getFirstName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getKanaLastName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getKanaFirstName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getBirthday() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOrdinaryDepositEraKbn() + "\"" + "," + "\""
                        + dateSourceList.get(i).getEraBirthday() + "\"" + "," + dateSourceList.get(i).getAge() + ","
                        + "\"" + dateSourceList.get(i).getAccountType() + "\"" + "," + "\""
                        + dateSourceList.get(i).getSexKbn() + "\"" + "," + "\"" + dateSourceList.get(i).getPostCode()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getPrefecturesCode() + "\"" + "," + "\"" + address
                        + "\"" + "," + "\"" + dateSourceList.get(i).getTeleNumber() + "\"" + "," + "\""
                        + dateSourceList.get(i).getPhoneNumber() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose1() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose2() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose3() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose4() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose5() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose6() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose7() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose8() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose9() + "\"" + "," + "\""
                        + dateSourceList.get(i).getAccountPurpose99() + "\"" + "," + "\"" + accountPurposeOther + "\""
                        + "," + "\"" + dateSourceList.get(i).getJobKbn1() + "\"" + "," + "\""
                        + dateSourceList.get(i).getJobKbn2() + "\"" + "," + "\"" + dateSourceList.get(i).getJobKbn3()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getJobKbn4() + "\"" + "," + "\""
                        + dateSourceList.get(i).getJobKbn5() + "\"" + "," + "\"" + dateSourceList.get(i).getJobKbn6()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getJobKbn7() + "\"" + "," + "\""
                        + dateSourceList.get(i).getJobKbn8() + "\"" + "," + "\"" + dateSourceList.get(i).getJobKbn9()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getJobKbn10() + "\"" + "," + "\""
                        + dateSourceList.get(i).getJobKbn49() + "\"" + "," + "\"" + jobKbnOther + "\"" + "," + "\""
                        + workName + "\"" + "," + "\"" + dateSourceList.get(i).getWorkTeleNumber() + "\"" + "," + "\""
                        + dateSourceList.get(i).getKnowProcess() + "\"" + ","
                        + dateSourceList.get(i).getSecurityPassword() + "," + dateSourceList.get(i).getOnlinePassword()
                        + "," + dateSourceList.get(i).getCreditlimit() + "," + "\""
                        + dateSourceList.get(i).getBankbookDesignKbn() + "\"" + "," + "\""
                        + dateSourceList.get(i).getCardDesingKbn() + "\"" + "," + "\""
                        + dateSourceList.get(i).getSalesOfficeOption();
                if (flg.equals("1")) {
                    s2 = s1 + "\n";
                } else {
                    s2 = s1 + "," + "\"" + dateSourceList.get(i).getErrMessage() + "\"" + "\n";
                }

                Buff.write(s2.getBytes("MS932"));
            }
            Buff.flush();
            Buff.close();

            zipOutputStream.putNextEntry(new ZipEntry(csvName));
            File file1 = new File(csvFileName);
            FileInputStream fis1 = new FileInputStream(file1);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis1.read(buffer)) > 0) {

                zipOutputStream.write(buffer, 0, len);

            }
            zipOutputStream.closeEntry();

            fis1.close();

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
     * 顧客出力ボタンCSVファイル生成メソッド。
     * 
     * @param tacsflag
     *            TACSフラグ
     * @return date TACSフラグ値
     * @throws Exception
     */
    public void csvCreate1(String flg, String date, List<AccountAppInitVO> dateSourceList, String outputFileName,
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
            s = "\"個人情報管理表\"" + "\n";
            Buff.write(s.getBytes("SHIFT-JIS"));
            s = "\"" + "受付番号" + "\"" + "," + "\"" + "受付日時" + "\"" + "," + "\"" + "申込者氏名" + "\"" + "," + "\""
                    + "申込者氏名（カナ）" + "\"" + "," + "\"" + "生年月日" + "\"" + "," + "\"" + "性別" + "\"" + "," + "\"" + "郵便番号"
                    + "\"" + "," + "\"" + "住所" + "\"" + "," + "\"" + "市区町村以下（カナ）" + "\"" + "," + "\"" + "職業" + "\""
                    + "," + "\"" + "自宅電話番号" + "\"" + "," + "\"" + "携帯電話番号" + "\"" + "," + "\"" + "勤務先名" + "\"" + ","
                    + "\"" + "勤務先電話番号" + "\"" + "," + "\"" + "IPアドレス" + "\"" + "," + "\"" + "既に口座をお持ちの方" + "\"" + ","
                    + "\"" + "既に口座をお持ちの方：店名" + "\"" + "," + "\"" + "既に口座をお持ちの方：口座番号" + "\"" + "," + "\"" + "口座開設の取引目的"
                    + "\"" + "," + "\"" + "口座開設の動機" + "\"" + "," + "\"" + "知った経緯" + "\"" + "\n";
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
                // 口座開設の取引目的
                String otherTradingPurposes = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getOtherTradingPurposes())) {
                    otherTradingPurposes = dateSourceList.get(i).getOtherTradingPurposes();
                    otherTradingPurposes = otherTradingPurposes.replace("\"", "\"\"");
                }
                String s2 = "";
                String s1 = "\"" + dateSourceList.get(i).getAccountAppSeq() + "\"" + "," + "\""
                        + dateSourceList.get(i).getReceiptDate() + "\"" + "," + "\"" + dateSourceList.get(i).getName()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getKanaName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getBirthday() + "\"" + "," + "\"" + dateSourceList.get(i).getSex()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getPostCode() + "\"" + "," + "\"" + address + "\""
                        + "," + "\"" + dateSourceList.get(i).getKanaAddress() + "\"" + "," + "\"" + otherOccupations
                        + "\"" + "," + "\"" + dateSourceList.get(i).getTeleNumber() + "\"" + "," + "\""
                        + dateSourceList.get(i).getPhoneNumber() + "\"" + "," + "\"" + workName + "\"" + "," + "\""
                        + dateSourceList.get(i).getWorkTeleNumber() + "\"" + "," + "\""
                        + dateSourceList.get(i).getIpAddress() + "\"" + "," + "\""
                        + dateSourceList.get(i).getHoldAccount() + "\"" + "," + "\""
                        + dateSourceList.get(i).getHoldAccountBank() + "\"" + "," + "\""
                        + dateSourceList.get(i).getHoldAccountNumber() + "\"" + "," + "\"" + otherTradingPurposes
                        + "\"" + "," + "\"" + dateSourceList.get(i).getAccountAppMotive() + "\"" + "," + "\""
                        + dateSourceList.get(i).getKnowProcess() + "\"";
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
     * 山形 顧客出力ボタンCSVファイル生成メソッド。
     * 
     * @param tacsflag
     *            TACSフラグ
     * @return date TACSフラグ値
     * @throws Exception
     */
    public void csvCreate3(String flg, String date, List<AccountAppYamaGataInitVO> dateSourceList,
            String outputFileName, HttpServletRequest req) {
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
            s = "\"個人情報管理表\"" + "\n";
            Buff.write(s.getBytes("SHIFT-JIS"));
            s = "\"" + "受付番号" + "\"" + "," + "\"" + "受付日時" + "\"" + "," + "\"" + "申込者氏名" + "\"" + "," + "\""
                    + "申込者氏名（カナ）" + "\"" + "," + "\"" + "生年月日" + "\"" + "," + "\"" + "和暦年号" + "\"" + "," + "\""
                    + "和暦生年月日" + "\"" + "," + "\"" + "性別" + "\"" + "," + "\"" + "郵便番号" + "\"" + "," + "\"" + "都道府"
                    + "\"" + "," + "\"" + "市区町村・番地・アパート・マンション名" + "\"" + "," + "\"" + "職業" + "\"" + "," + "\""
                    + "自宅電話番号" + "\"" + "," + "\"" + "携帯電話番号" + "\"" + "," + "\"" + "勤務先名" + "\"" + "," + "\""
                    + "勤務先電話番号" + "\"" + "," + "\"" + "IPアドレス" + "\"" + "," + "\"" + "通帳デザイン" + "\"" + "," + "\""
                    + "カードデザイン" + "\"" + "," + "\"" + "取引目的" + "\"" + "," + "\"" + "知った経緯" + "\"" + "\n";
            Buff.write(s.getBytes("MS932"));

            for (int i = 0; i < dateSourceList.size(); i++) {
                // 市区町村・番地・アパート・マンション名
                String address = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getAddress())) {
                    address = dateSourceList.get(i).getAddress();
                    address = address.replace("\"", "\"\"");
                }
                // 職業
                String jobKbnOther = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getJobKbnOther())) {
                    jobKbnOther = dateSourceList.get(i).getJobKbnOther();
                    jobKbnOther = jobKbnOther.replace("\"", "\"\"");
                }
                // 勤務先名
                String workName = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getWorkName())) {
                    workName = dateSourceList.get(i).getWorkName();
                    workName = workName.replace("\"", "\"\"");
                }
                // 口座開設の取引目的
                String accountPurposeOther = "";
                if (Utils.isNotNullAndEmpty(dateSourceList.get(i).getAccountPurposeOther())) {
                    accountPurposeOther = dateSourceList.get(i).getAccountPurposeOther();
                    accountPurposeOther = accountPurposeOther.replace("\"", "\"\"");
                }
                String s2 = "";
                String s1 = "\"" + dateSourceList.get(i).getAccountAppSeq() + "\"" + "," + "\""
                        + dateSourceList.get(i).getReceiptDate() + "\"" + "," + "\"" + dateSourceList.get(i).getName()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getKanaName() + "\"" + "," + "\""
                        + dateSourceList.get(i).getBirthday() + "\"" + "," + "\""
                        + dateSourceList.get(i).getOrdinaryDepositEraKbn() + "\"" + "," + "\""
                        + dateSourceList.get(i).getEraBirthday() + "\"" + "," + "\""
                        + dateSourceList.get(i).getSexKbn() + "\"" + "," + "\"" + dateSourceList.get(i).getPostCode()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getPrefecturesCode() + "\"" + "," + "\"" + address
                        + "\"" + "," + "\"" + jobKbnOther + "\"" + "," + "\"" + dateSourceList.get(i).getTeleNumber()
                        + "\"" + "," + "\"" + dateSourceList.get(i).getPhoneNumber() + "\"" + "," + "\"" + workName
                        + "\"" + "," + "\"" + dateSourceList.get(i).getWorkTeleNumber() + "\"" + "," + "\""
                        + dateSourceList.get(i).getIpAddress() + "\"" + "," + "\""
                        + dateSourceList.get(i).getBankbookDesignKbn() + "\"" + "," + "\""
                        + dateSourceList.get(i).getCardDesingKbn() + "\"" + "," + "\"" + accountPurposeOther + "\""
                        + "," + "\"" + dateSourceList.get(i).getKnowProcess() + "\"";
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
    private void csvCreate4(String string, String date, List<AccountApp114InitVO> accountAppList, String outputFileName,
            HttpServletRequest req) {
        
    }
}
