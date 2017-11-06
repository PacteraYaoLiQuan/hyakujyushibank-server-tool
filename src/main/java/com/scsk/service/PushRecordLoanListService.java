package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.DesignDocument.MapReduce;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.Account114AppDoc;
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountLoanDoc;
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.model.StatusModifyLoanDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.response.vo.PushRecordAppListInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.AccountAppPushListVO;

/**
 * 申込詳細検索サービス。<br>
 * <br>
 * 申込詳細検索を実装するロジック。<br>
 * 申込詳細を更新するロジック。<br>
 */
@Service
public class PushRecordLoanListService extends AbstractBLogic<PushRecordAppListInitResVO, PushRecordAppListInitResVO> {

    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(PushRecordAppListInitResVO pushRecordAppListInitResVO) throws Exception {

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
    protected PushRecordAppListInitResVO doExecute(CloudantClient client, PushRecordAppListInitResVO resVO)
            throws Exception {

        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);

        PushRecordAppListInitResVO pushRecordLoanListInitResVO = hyakujyushi(db, resVO);
        return pushRecordLoanListInitResVO;
    }

    /**
     * 
     * 114
     * 
     */
    public PushRecordAppListInitResVO hyakujyushi(Database db, PushRecordAppListInitResVO resVO) throws Exception {
        PushRecordAppListInitResVO pushRecordAppListInitResVO = new PushRecordAppListInitResVO();
        // push通知履歴一覧を取得
        List<StatusModifyLoanDoc> statusModifyLoanDocList = new ArrayList<>();
        try {
            // push通知履歴
            statusModifyLoanDocList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_STATUSMODIFY_LOAN_STATUSMODIFY_LOAN, StatusModifyLoanDoc.class);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSH001_1001);
            throw new BusinessException(messages);
        }
        List<StatusModifyLoanDoc> statusModifyLoanDocListSave = new ArrayList<>();
        removeMinStatusModifyDate(statusModifyLoanDocList, statusModifyLoanDocListSave);
        List<AccountAppPushListVO> accountAppPushList = new ArrayList<AccountAppPushListVO>();
        // 戻り値を設定
        List<AccountLoanDoc> accountLoanDocList = new ArrayList<>();
        accountLoanDocList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_ACCOUNTLOANLIST_ACCOUNTLOANLIST, AccountLoanDoc.class);
        for (StatusModifyLoanDoc doc : statusModifyLoanDocListSave) {
            // ステータスは受付または処理中の場合
            if ("1".equals(doc.getSendStatus())) {
                for (AccountLoanDoc accountLoanDoc : accountLoanDocList) {
                    AccountAppPushListVO accountAppPushListVO = new AccountAppPushListVO();
                    if (doc.getAccountAppSeq().equals(accountLoanDoc.getLoanAppSeq())) {
                        accountAppPushListVO.setAccountAppSeq(doc.getAccountAppSeq());
                        // 姓名
                        accountAppPushListVO.setName(encryptorUtil.decrypt(accountLoanDoc.getLastName()) + " "
                                + encryptorUtil.decrypt(accountLoanDoc.getFirstName()));
                        // 申込受付日付
                        accountAppPushListVO.setReceiptDate(accountLoanDoc.getLoanAppTime());
                        accountAppPushListVO.setStatus(doc.getStatus());
                        accountAppPushListVO.set_id(doc.get_id());
                        accountAppPushListVO.setUserId(accountLoanDoc.getUserId());
                        accountAppPushListVO.setAccountApp_id(accountLoanDoc.get_id());
                        accountAppPushListVO.setLoanType(accountLoanDoc.getLoanType());
                        accountAppPushListVO.setStoreName(encryptorUtil.decrypt(accountLoanDoc.getStoreName()));
                        accountAppPushList.add(accountAppPushListVO);
                    }
                }
            }
        }
        pushRecordAppListInitResVO.setAccountAppPushList(accountAppPushList);
        actionLog.saveActionLog(Constants.ACTIONLOG_PUSHPRECORD_1, db);
        return pushRecordAppListInitResVO;

    }

    /**
     * date formartメソッド。
     * 
     * @param date
     *            処理前日付
     * @return date 処理後日付
     * @throws Exception
     */
    public String dateFormat(String dateInput) {
        String dateOutput = "";
        if (Utils.isNotNullAndEmpty(dateInput) && dateInput.length() > 7) {
            dateOutput = dateInput.substring(0, 4) + "/" + dateInput.substring(4, 6) + "/" + dateInput.substring(6, 8);
        }

        return dateOutput;
    }

    /**
     * 申込処理ステータス变更リスト作成。
     * 
     * @param date
     *            申込処理ステータス变更リスト
     * @return date 申込処理ステータス变更リスト
     * @throws Exception
     */
    public List<StatusModifyLoanDoc> removeMinStatusModifyDate(
            List<StatusModifyLoanDoc> yamagataStatusModifyDocList,
            List<StatusModifyLoanDoc> yamagataStatusModifyDocListSave) {

        for (int i = 0; i < yamagataStatusModifyDocList.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < yamagataStatusModifyDocList.size(); j++) {
                if (yamagataStatusModifyDocList.get(j).getAccountAppSeq()
                        .equals(yamagataStatusModifyDocList.get(i).getAccountAppSeq())) {
                    if (yamagataStatusModifyDocList.get(j).getStatusModifyDate()
                            .compareTo(yamagataStatusModifyDocList.get(i).getStatusModifyDate()) > 0) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                yamagataStatusModifyDocListSave.add(yamagataStatusModifyDocList.get(i));
            }
        }
        return yamagataStatusModifyDocListSave;
    }

    /**
     * Push通知履歴リスト作成。
     * 
     * @param date
     *            Push通知履歴リスト
     * @return date Push通知履歴リスト
     * @throws Exception
     */
    public List<PushrecordDoc> remove(List<PushrecordDoc> pushrecordDocList, List<PushrecordDoc> pushrecordDocList1) {

        for (int i = 0; i < pushrecordDocList.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < pushrecordDocList.size(); j++) {
                if (pushrecordDocList.get(j).getAccountAppSeq().equals(pushrecordDocList.get(i).getAccountAppSeq())) {
                    if (pushrecordDocList.get(j).getCreatedDate()
                            .compareTo(pushrecordDocList.get(i).getCreatedDate()) > 0) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                pushrecordDocList1.add(pushrecordDocList.get(i));
            }
        }
        return pushrecordDocList1;
    }

}
