package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.AccountLoanDoc;
import com.scsk.model.StatusModifyLoanDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;

/**
 * ローン申込詳細データ更新メソッド。
 * 
 * @return ResponseEntity 戻るデータオブジェクト
 */
@Service
public class AccountLoanDetailUpdService
        extends AbstractBLogic<AccountAppDetailStatusReqVO, AccountAppDetailStatusResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(AccountAppDetailStatusReqVO applicationDetailStatusReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param reqVo
     *            入力情報
     * @return resVo 詳細情報
     * @throws Exception
     */
    @Override
    protected AccountAppDetailStatusResVO doExecute(CloudantClient client,
            AccountAppDetailStatusReqVO applicationDetailStatusReqVO) throws Exception {

        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        // 114
        AccountAppDetailStatusResVO output = hyakujyushi(client, db, applicationDetailStatusReqVO);
        return output;
    }

    /**
     * 114
     */
    public AccountAppDetailStatusResVO hyakujyushi(CloudantClient client, Database db,
            AccountAppDetailStatusReqVO applicationDetailStatusReqVO) throws Exception {
        AccountAppDetailStatusResVO output = new AccountAppDetailStatusResVO();
        String accountAppUpdLog = "(受付番号：";
        AccountLoanDoc accountLoanDoc = new AccountLoanDoc();
        // 申込詳細情報取得
        try {
            accountLoanDoc = (AccountLoanDoc) repositoryUtil.find(db, applicationDetailStatusReqVO.get_id(),
                    AccountLoanDoc.class);
            accountAppUpdLog = accountAppUpdLog + accountLoanDoc.getLoanAppSeq();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
            throw new BusinessException(messages);
        }
        // DBに更新
        try {
            accountLoanDoc.setStatus(applicationDetailStatusReqVO.getStatus());
            repositoryUtil.update(db, accountLoanDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPDETAIL_1002);
            throw new BusinessException(messages);
        }
        SimpleDateFormat sdf = null;
        Date date = new Date();
        sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String statusModifyDate = sdf.format(date);
        StatusModifyLoanDoc statusModifyLoanDoc = new StatusModifyLoanDoc();
        statusModifyLoanDoc.setAccountAppSeq(accountLoanDoc.getLoanAppSeq());
        statusModifyLoanDoc.setStatus(applicationDetailStatusReqVO.getStatus());
        statusModifyLoanDoc.setStatusModifyDate(statusModifyDate);
        statusModifyLoanDoc.setStatusModifyDateForSort(date.getTime());

        if ("1".equals(applicationDetailStatusReqVO.getStatus())
                || "2".equals(applicationDetailStatusReqVO.getStatus())) {
            statusModifyLoanDoc.setSendStatus("0");
        } else {
            statusModifyLoanDoc.setSendStatus("1");
        }
        statusModifyLoanDoc.setPushRecordOid("");
        try {
            repositoryUtil.saveToResultId(db, statusModifyLoanDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
            throw new BusinessException(messages);
        }
        output.setPushStatus("5");
        // 戻る値を設定
        output.set_id(accountLoanDoc.get_id());
        output.setStatus(accountLoanDoc.getStatus());

        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNTLOAN_6 + accountAppUpdLog + ")", db);
        return output;
    }

    /**
     * date formartメソッド。
     * 
     * @param date
     *            処理前日付
     * @return date 処理後日付
     * @throws Exception
     */
    public String dateFormatJP(String dateInput) {
        String dateOutput = "";
        if (Utils.isNotNullAndEmpty(dateInput) && dateInput.length() > 7) {
            dateOutput = dateInput.substring(0, 4) + Constants.YEAR + dateInput.substring(5, 7) + Constants.MONTH_JP
                    + dateInput.substring(8, 10) + Constants.DAY + " " + dateInput.substring(11, 16);
        }

        return dateOutput;
    }
}
