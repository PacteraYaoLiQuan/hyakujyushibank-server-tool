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
import com.scsk.request.vo.AccountLoanListCompleteButtonReqVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * 完了消込ステータス更新サービス。<br>
 * <br>
 * 完了消込ステータス更新を実装するロジック。<br>
 */
@Service
public class AccountLoanListCompleteService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param accountAppListCompleteButtonReqVO
     *            申込一覧情報
     */
    @Override
    protected void preExecute(BaseResVO accountLoanListCompleteButtonReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param accountAppListCompleteButtonReqVO
     *            申込一覧情報
     * @return accountAppListCompleteButtonReqVO 完了消込情報
     */
    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        AccountLoanListCompleteButtonReqVO output = hyakujyushi(db, baseResVO);
        return output;

    }

    /**
     * 114
     */
    public AccountLoanListCompleteButtonReqVO hyakujyushi(Database db, BaseResVO baseResVO) throws Exception {
        AccountLoanListCompleteButtonReqVO accountAppListCompleteButtonReqVO = new AccountLoanListCompleteButtonReqVO();
        accountAppListCompleteButtonReqVO = (AccountLoanListCompleteButtonReqVO) baseResVO;
        String accountAppListCompleteLog = "(受付番号：";
        Date date = new Date();
        for (int i = 0; i < accountAppListCompleteButtonReqVO.getCompleteList().size(); i++) {
            if (accountAppListCompleteButtonReqVO.getCompleteList().get(i).getSelect() == null) {
                continue;
            }
            // 一覧選択したデータ
            if (accountAppListCompleteButtonReqVO.getCompleteList().get(i).getSelect() == true) {
                accountAppListCompleteLog = accountAppListCompleteLog
                        + accountAppListCompleteButtonReqVO.getCompleteList().get(i).getAccountAppSeq() + "/";
                AccountLoanDoc accountLoanDoc = new AccountLoanDoc();
                // 申込詳細情報取得
                try {
                    accountLoanDoc = (AccountLoanDoc) repositoryUtil.find(db,
                            accountAppListCompleteButtonReqVO.getCompleteList().get(i).get_id(),
                            AccountLoanDoc.class);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                    throw new BusinessException(messages);
                }
                // DBに更新
                try {
                    accountLoanDoc.setStatus("3");
                    repositoryUtil.update(db, accountLoanDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPDETAIL_1002);
                    throw new BusinessException(messages);
                }
                SimpleDateFormat sdf = null;
                sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
                String statusModifyDate = sdf.format(date);
                StatusModifyLoanDoc statusModifyLoanDoc = new StatusModifyLoanDoc();
                statusModifyLoanDoc.setAccountAppSeq(accountLoanDoc.getLoanAppSeq());
                statusModifyLoanDoc.setStatus("3");
                statusModifyLoanDoc.setStatusModifyDate(statusModifyDate);
                statusModifyLoanDoc.setStatusModifyDateForSort(date.getTime());
                statusModifyLoanDoc.setSendStatus("1");
                statusModifyLoanDoc.setPushRecordOid("");
                try {
                    repositoryUtil.save(db, statusModifyLoanDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                    throw new BusinessException(messages);
                }
            }
        }
        accountAppListCompleteLog = accountAppListCompleteLog.substring(0, accountAppListCompleteLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNTLOAN_4 + accountAppListCompleteLog + ")", db);
        return accountAppListCompleteButtonReqVO;
    }
}
