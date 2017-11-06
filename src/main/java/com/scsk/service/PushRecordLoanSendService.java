package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.Account114PushDetailDoc;
import com.scsk.model.HyakujyushiUserInfoDoc;
import com.scsk.model.PushrecordLoanDoc;
import com.scsk.model.StatusModifyLoanDoc;
import com.scsk.model.YamagataPushDetailDoc;
import com.scsk.model.geo.DeviceInfoDoc;
import com.scsk.request.vo.PushRecordAppListSendReqVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PushNotifications;
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
public class PushRecordLoanSendService extends AbstractBLogic<PushRecordAppListSendReqVO, PushRecordAppListSendReqVO> {

    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private PushNotifications pushNotifications;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(PushRecordAppListSendReqVO pushRecordAppListSendReqVO) throws Exception {

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
    protected PushRecordAppListSendReqVO doExecute(CloudantClient client, PushRecordAppListSendReqVO ReqVO)
            throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);

        // push通知履歴一覧を取得

        for (AccountAppPushListVO accountAppPushListVO : ReqVO.getSendList()) {
            if (accountAppPushListVO.getSelect() == null) {
                continue;
            }

            if (accountAppPushListVO.getSelect() == true) {
                admit114(db, accountAppPushListVO);
            }

        }
        return ReqVO;
    }

    /**
     * PUSH通知を送信する。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param statusList
     *            ステータス
     * @return void
     * @throws Exception
     */
    public void admit114(Database db, AccountAppPushListVO accountAppPushListVO) throws Exception {
        List<String> deviceId = new ArrayList<>();
        String userId = "";
        HyakujyushiUserInfoDoc userInfoDoc = new HyakujyushiUserInfoDoc();
        try {
            userInfoDoc = repositoryUtil.find(db, accountAppPushListVO.getUserId(), HyakujyushiUserInfoDoc.class);
        } catch (Exception e) {

        }
        if (userInfoDoc != null) {
            userId = accountAppPushListVO.getUserId();
            for (DeviceInfoDoc deviceInfoDoc : userInfoDoc.getDeviceInfoList())
                if (!deviceInfoDoc.getDeviceTokenId().isEmpty()) {
                    deviceId.add(encryptorUtil.decrypt(deviceInfoDoc.getDeviceTokenId()));
                }
        }
        String pushTile = "";
        String message = "";
        String loanName = "";
        if ("0".equals(accountAppPushListVO.getLoanType())) {
            loanName ="ニューカードローン";
        } else if ("1".equals(accountAppPushListVO.getLoanType())) {
            loanName = "マイカーローン";
        } else if ("2".equals(accountAppPushListVO.getLoanType())) {
            loanName = "教育ローン";
        } else if ("3".equals(accountAppPushListVO.getLoanType())) {
            loanName = "フリーローン";
        }
        String receiptDate = dateFormatJP(accountAppPushListVO.getReceiptDate());
        String applyInformation = Constants.HYAKUJYUSH_RECEIPT_DATE + receiptDate + Constants.HYAKUJYUSH_ACCOUNT_SEQ
                + accountAppPushListVO.getAccountAppSeq() + Constants.HYAKUJYUSH_APPLY_KIND +loanName +"<br/><br/>";
        switch (accountAppPushListVO.getStatus()) {
        case "1":
            message = Constants.HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_1 + applyInformation
                    + Constants.HYAKUJYUSH_PUSH_MESSAGE_ABOUT_LOAN;
            pushTile = Constants.HYAKUJYUSH_PUSH_MESSAGE_TITLE_LOAN_1;
            break;
        case "2":
            message = Constants.HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_2 + applyInformation
                    + Constants.HYAKUJYUSH_PUSH_MESSAGE_ABOUT_LOAN;
            pushTile = Constants.HYAKUJYUSH_PUSH_MESSAGE_TITLE_LOAN_2;
            break;
        case "3":
            message = Constants.HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_3
            +"近日中に" +accountAppPushListVO.getStoreName()  +Constants.HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_3_1
            + Constants.HYAKUJYUSH_PUSH_MESSAGE_ABOUT_LOAN;
            pushTile = Constants.HYAKUJYUSH_PUSH_MESSAGE_TITLE_LOAN_3;
            break;
        case "4":
            message = Constants.HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_4 + applyInformation
                    + Constants.HYAKUJYUSH_PUSH_MESSAGE_ABOUT_LOAN;
            pushTile = Constants.HYAKUJYUSH_PUSH_MESSAGE_TITLE_LOAN_4;
            break;
        default:
            break;
        }
        String messageHtml = Constants.HYAKUJYUSH_ALLPUSH_MESSAGE_HTML_START + message
                + Constants.HYAKUJYUSH_PUSH_MESSAGE_HTML_END;
        Date date = new Date();
        SimpleDateFormat dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String pushDate = dateFormart.format(date);
        List<String> list = new ArrayList<String>();
        String messageCode = "";
        String pushrecorDetaldId = "";
        String pushrecordId = "";
        int sum = 0;

        String queryKey = accountAppPushListVO.getAccountAppSeq();
        List<StatusModifyLoanDoc> statusModifyLoanList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_STATUSMODIFY_LOAN_STATUSMODIFY_LOAN, StatusModifyLoanDoc.class, queryKey);
        for (StatusModifyLoanDoc statusModifyLoanDoc : statusModifyLoanList) {
            PushrecordLoanDoc pushrecordLoanDoc = new PushrecordLoanDoc();
            try {
                pushrecordLoanDoc = repositoryUtil.find(db, statusModifyLoanDoc.getPushRecordOid(),
                        PushrecordLoanDoc.class);
            } catch (Exception e) {
            }
            if (pushrecordLoanDoc != null && !"".equals(pushrecordLoanDoc.get_id())) {
                sum = sum + 1;
            }
        }
        if (!deviceId.isEmpty()) {
            // PUSH通知を送信する
            messageCode = pushNotifications.sendMessage(pushTile, sum, list);
        }
        // 配信詳細DOC作成
        Account114PushDetailDoc account114PushDetailDoc = new Account114PushDetailDoc();
        account114PushDetailDoc.setDocType(Constants.PUSH_DETAIL_LOAN);
        account114PushDetailDoc.setPushMessage(messageHtml);
        try {
            pushrecorDetaldId = repositoryUtil.saveToResultId(db, account114PushDetailDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }
        // 配信履歴DOC作成
        PushrecordLoanDoc pushrecordLoanDoc = new PushrecordLoanDoc();
        // ユーザーID
        pushrecordLoanDoc.setUserId(userId);
        // 端末ID
        if (deviceId != null && deviceId.size() > 0) {
            pushrecordLoanDoc.setDeviceTokenId(deviceId.get(0));
        }
        // 配信日時
        pushrecordLoanDoc.setPushDate(pushDate);
        // 配信日時並び用
        pushrecordLoanDoc.setPushDateForSort(date.getTime());
        // プッシュ内容タイトル
        pushrecordLoanDoc.setPushTitle(pushTile);
        // プッシュ開封状況
        pushrecordLoanDoc.setPushStatus("1");
        // プッシュ詳細OID
        pushrecordLoanDoc.setPushDetailOid(pushrecorDetaldId);
        try {
            pushrecordId = repositoryUtil.saveToResultId(db, pushrecordLoanDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }
       StatusModifyLoanDoc statusModifyLoanDoc = new StatusModifyLoanDoc();
        try {
            statusModifyLoanDoc = repositoryUtil.find(db, accountAppPushListVO.get_id(),
                    StatusModifyLoanDoc.class);

        } catch (Exception e) {

        }
        if (Constants.RETURN_OK.equals(messageCode)) {
            // ２：配信済
            statusModifyLoanDoc.setSendStatus("2");
        } else {
            if (!deviceId.isEmpty()) {
                // ４：承認済（配信失敗）
                statusModifyLoanDoc.setSendStatus("4");
            } else {
                // ３：承認済（配信不可）
                statusModifyLoanDoc.setSendStatus("3");
            }
        }
        statusModifyLoanDoc.setPushRecordOid(pushrecordId);
        try {
            repositoryUtil.update(db, statusModifyLoanDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }

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
