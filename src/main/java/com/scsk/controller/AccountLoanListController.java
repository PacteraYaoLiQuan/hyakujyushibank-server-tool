package com.scsk.controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.report.ReportBLogic;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.request.vo.AccountAppListOutputButtonReqVO;
import com.scsk.request.vo.AccountLoanListCompleteButtonReqVO;
import com.scsk.request.vo.AccountLoanListOutputButtonReqVO;
import com.scsk.response.vo.AccountApp114DetailResVO;
import com.scsk.response.vo.AccountAppDetailResVO;
import com.scsk.response.vo.AccountAppListOutputButtonResVO;
import com.scsk.response.vo.AccountAppListOutputCsvResVO;
import com.scsk.response.vo.AccountAppYamaGataDetailResVO;
import com.scsk.response.vo.AccountLoan114DetailResVO;
import com.scsk.response.vo.AccountLoanInitResVO;
import com.scsk.response.vo.AccountLoanListOutputButtonResVO;
import com.scsk.response.vo.AccountYamaGataAppListOutputButtonResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.AccountAppDetailImageSelService;
import com.scsk.service.AccountLoanListCompleteService;
import com.scsk.service.AccountLoanListInitService;
import com.scsk.service.AccountLoanListOutputService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.AccountAppInitVO;
import com.scsk.vo.AccountAppYamaGataInitVO;
import com.scsk.vo.ErrVO;
import com.scsk.vo.ImageVO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * ローン申込み申込一覧画面。<br>
 * <br>
 * メニュー画面からローン申込み申込一覧表示＆ステータス変更＆帳票出力＆CSV出力を実装すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class AccountLoanListController {

    @Autowired
    private AccountLoanListInitService accountLoanListInitService;
    @Autowired
    private AccountLoanListCompleteService accountLoanListCompleteService;
    @Autowired
    private AccountLoanListOutputService accountLoanListOutputService;
    @Autowired
    private ReportBLogic reportBLogic;
    @Autowired
    AccountAppDetailImageSelService accountAppDetailImageSelService;

    /**
     * ローン申込み申込一覧初期化表示メソッド。
     * 
     * @param なし
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/loanAppList", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> accountAppListInit(HttpServletRequest req) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());

        AccountLoanInitResVO accountLoanInitResVO = new AccountLoanInitResVO();
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();
        try {
            // 申込一覧データを検索する
            BaseResVO ba = (AccountLoanInitResVO) accountLoanListInitService.execute(baseResVO);
            accountLoanInitResVO = (AccountLoanInitResVO) ba;

            // 初期化データない場合、Messageをセットする
            if (accountLoanInitResVO.getAccountLoanList() == null
                    || accountLoanInitResVO.getAccountLoanList().isEmpty()) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1002);
                throw new BusinessException(messages);
            }
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            resEntityBody.setResultData(accountLoanInitResVO);
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
     * 完了消込メソッド。
     * 
     * @param @RequestBody
     *            accountAppListCompleteButtonReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/accountLoan/completeButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> completeButton(HttpServletRequest req,
            @RequestBody AccountLoanListCompleteButtonReqVO accountLoanListCompleteButtonReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();
        baseResVO = accountLoanListCompleteButtonReqVO;

        try {
            // 「処理中」－＞「完了」に変更する
            accountLoanListCompleteService.execute(baseResVO);
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
     * @param @RequestBody
     *            accountAppListOutputButtonReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/loan/outputButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<AccountLoanListOutputButtonResVO>> outputButton(HttpServletRequest req,
            HttpServletResponse res, @RequestBody AccountLoanListOutputButtonReqVO accountLoanListOutputButtonReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountLoanListOutputButtonResVO> resEntityBody = new ResponseEntityVO<AccountLoanListOutputButtonResVO>();

        try {
            // 「受付」－＞「処理中」に変更する,帳票出力用データを抽出
            AccountLoanListOutputButtonResVO accountLoanListOutputButtonResVO = accountLoanListOutputService
                    .execute(accountLoanListOutputButtonReqVO);
            // 画面に選択したデータをレコードずつPDF＆CSV出力する
            List<AccountLoan114DetailResVO> checkList = accountLoanListOutputButtonResVO.getAccountLoanList();
            if (checkList != null && checkList.size() != 0) {
                // 当日日付を取得する（日付フォーマット）
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
                String date = sdf.format(new Date());
                reportBLogic.datePathCreate(req, date);
                List<ImageVO> imageList = new ArrayList<ImageVO>();

                // データ出力順序を設定（申込受付番号（主キー）の昇順）
                Comparator<AccountLoan114DetailResVO> comparator = new Comparator<AccountLoan114DetailResVO>() {
                    public int compare(AccountLoan114DetailResVO s1, AccountLoan114DetailResVO s2) {
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

                for (AccountLoan114DetailResVO vo : checkList) {
                    // 帳票出力用の datasourceを準備
                    if (Utils.isNotNullAndEmpty(vo.getDriverLicenseSeq())) {
                        // 画像出力用データを取得
                        AccountAppDetailReqVO input = new AccountAppDetailReqVO();
                        input.set_id(vo.getDriverLicenseSeq());
                        BaseResVO baseResVO = new BaseResVO();
                        baseResVO = input;
                        
                        AccountApp114DetailResVO resVo = (AccountApp114DetailResVO) accountAppDetailImageSelService
                                .execute(input);
                        resVo.setAccountAppSeq(vo.getAccountAppSeq());
 
                        // 本人確認書類保存＆ImageVOListセット（本人確認書類）
                        imageList.add(reportBLogic.ImageOutputSave3(date, resVo, req));
                    }
                    accountLoanListOutputButtonResVO.setImageList(imageList);
                }
                // 本人確認書類PDF生成
                JRDataSource jrDataSource = new JRBeanCollectionDataSource(accountLoanListOutputButtonResVO.getImageList());
                reportBLogic.ImageOutputCreate(date, jrDataSource, ApplicationKeys.ACCOUNTAPPLIST002_REPORT,
                        ApplicationKeys.ACCOUNTAPPLIST102_REPORT, req);
                accountLoanListOutputButtonResVO.setDate(date);
            }

            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            // ボーディ設定
            resEntityBody.setResultData(accountLoanListOutputButtonResVO);
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
        return new ResponseEntity<ResponseEntityVO<AccountLoanListOutputButtonResVO>>(resEntityBody, HttpStatus.OK);
    }

}
