package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.Account114DocumentDoc;
import com.scsk.model.StatusModifyDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PushNotifications;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
@Service
public class AccountDocumentDetailUpdService
        extends AbstractBLogic<AccountAppDetailStatusReqVO, AccountAppDetailStatusResVO> {

    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;
    @Autowired
    private PushNotifications pushNotifications;

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

        if ("0173".equals(bank_cd)) {
            AccountAppDetailStatusResVO output = hyakujyushi(client, db, applicationDetailStatusReqVO);
            return output;
        } else {

        }
        return null;

    }

    private AccountAppDetailStatusResVO hyakujyushi(CloudantClient client, Database db,
            AccountAppDetailStatusReqVO applicationDetailStatusReqVO) {

        AccountAppDetailStatusResVO output = new AccountAppDetailStatusResVO();
        String accountAppUpdLog = "(受付番号：";
        Account114DocumentDoc applicationDoc = new Account114DocumentDoc();
        // 申込詳細情報取得
        try {
            applicationDoc = (Account114DocumentDoc) repositoryUtil.find(db, applicationDetailStatusReqVO.get_id(),
                    Account114DocumentDoc.class);
            accountAppUpdLog = accountAppUpdLog + applicationDoc.getDocumentAppSeq();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
            throw new BusinessException(messages);
        }
        // DBに更新
        try {
            applicationDoc.setStatus(applicationDetailStatusReqVO.getStatus());
            repositoryUtil.update(db, applicationDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPDETAIL_1002);
            throw new BusinessException(messages);
        }
        String statusModify = "";
        SimpleDateFormat sdf = null;
        Date date = new Date();
        sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String statusModifyDate = sdf.format(date);
        StatusModifyDoc statusModifyDocSave = new StatusModifyDoc();
        statusModifyDocSave.setAccountAppSeq(applicationDoc.getDocumentAppSeq());
        statusModifyDocSave.setStatus(applicationDetailStatusReqVO.getStatus());
        statusModifyDocSave.setStatusModifyDate(statusModifyDate);
        statusModifyDocSave.setStatusModifyDateForSort(date.getTime());
        statusModifyDocSave.setDocType(Constants.DOCUMENT_STATUS_MODIFY);

        if ("1".equals(applicationDetailStatusReqVO.getStatus())
                || "2".equals(applicationDetailStatusReqVO.getStatus())) {
            statusModifyDocSave.setSendStatus("0");
        } else {
            statusModifyDocSave.setSendStatus("1");
        }
        statusModifyDocSave.setPushRecordOid("");
        try {
            statusModify = repositoryUtil.saveToResultId(db, statusModifyDocSave);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
            throw new BusinessException(messages);
        }
        output.setPushStatus("5");
        output.set_id(applicationDoc.get_id());
        output.setStatus(applicationDoc.getStatus());

        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_6 + accountAppUpdLog + ")", db);
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
