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
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.Account114AppDoc;
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.model.UserInfoDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PushNotifications;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;

/**
 * 申込詳細データ更新メソッド。
 * 
 * @return ResponseEntity 戻るデータオブジェクト
 */
@Service
public class AccountAppDetailUpdService
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

        // 広島
        if ("0169".equals(bank_cd)) {
            AccountAppDetailStatusResVO output = hirosima(client, db, applicationDetailStatusReqVO);
            return output;
        }
        // 山形
        else if ("0122".equals(bank_cd)) {
            AccountAppDetailStatusResVO output = yamagata(client, db, applicationDetailStatusReqVO);
            return output;
        }
        //114
        else if ("0173".equals(bank_cd)) {
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
        Account114AppDoc applicationDoc = new Account114AppDoc();
        // 申込詳細情報取得
        try {
            applicationDoc = (Account114AppDoc) repositoryUtil.find(db, applicationDetailStatusReqVO.get_id(),
                    Account114AppDoc.class);
            accountAppUpdLog = accountAppUpdLog + applicationDoc.getAccountAppSeq();
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
        YamagataStatusModifyDoc yamagataStatusModifyDocSave = new YamagataStatusModifyDoc();
        yamagataStatusModifyDocSave.setAccountAppSeq(applicationDoc.getAccountAppSeq());
        yamagataStatusModifyDocSave.setStatus(applicationDetailStatusReqVO.getStatus());
        yamagataStatusModifyDocSave.setStatusModifyDate(statusModifyDate);
        yamagataStatusModifyDocSave.setStatusModifyDateForSort(date.getTime());

        if ("1".equals(applicationDetailStatusReqVO.getStatus())
                || "2".equals(applicationDetailStatusReqVO.getStatus())) {
            yamagataStatusModifyDocSave.setSendStatus("0");
        } else {
            yamagataStatusModifyDocSave.setSendStatus("1");
        }
        yamagataStatusModifyDocSave.setPushRecordOid("");
        try {
            statusModify = repositoryUtil.saveToResultId(db, yamagataStatusModifyDocSave);
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
     * 広島
     */
    public AccountAppDetailStatusResVO hirosima(CloudantClient client, Database db,
            AccountAppDetailStatusReqVO applicationDetailStatusReqVO) throws Exception {

        AccountAppDetailStatusResVO output = new AccountAppDetailStatusResVO();
        String accountAppUpdLog = "(受付番号：";
        AccountAppDoc applicationDoc = new AccountAppDoc();
        // 申込詳細情報取得
        try {
            applicationDoc = (AccountAppDoc) repositoryUtil.find(db, applicationDetailStatusReqVO.get_id(),
                    AccountAppDoc.class);
            accountAppUpdLog = accountAppUpdLog + applicationDoc.getAccountAppSeq();
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
        PushrecordDoc pushrecordDoc = new PushrecordDoc();
        pushrecordDoc = pushNotifications(client, applicationDoc, applicationDetailStatusReqVO.getStatus());

        // 戻る値を設定
        output.set_id(applicationDoc.get_id());
        output.setStatus(applicationDoc.getStatus());
        output.setPushStatus("5");
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_6 + accountAppUpdLog + ")", db);
        return output;
    }

    /**
     * 山形
     */
    public AccountAppDetailStatusResVO yamagata(CloudantClient client, Database db,
            AccountAppDetailStatusReqVO applicationDetailStatusReqVO) throws Exception {
        AccountAppDetailStatusResVO output = new AccountAppDetailStatusResVO();
        String accountAppUpdLog = "(受付番号：";
        AccountYamaGataAppDoc applicationDoc = new AccountYamaGataAppDoc();
        // 申込詳細情報取得
        try {
            applicationDoc = (AccountYamaGataAppDoc) repositoryUtil.find(db, applicationDetailStatusReqVO.get_id(),
                    AccountYamaGataAppDoc.class);
            accountAppUpdLog = accountAppUpdLog + applicationDoc.getAccountAppSeq();
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
        YamagataStatusModifyDoc yamagataStatusModifyDocSave = new YamagataStatusModifyDoc();
        yamagataStatusModifyDocSave.setAccountAppSeq(applicationDoc.getAccountAppSeq());
        yamagataStatusModifyDocSave.setStatus(applicationDetailStatusReqVO.getStatus());
        yamagataStatusModifyDocSave.setStatusModifyDate(statusModifyDate);
        yamagataStatusModifyDocSave.setStatusModifyDateForSort(date.getTime());

        if ("1".equals(applicationDetailStatusReqVO.getStatus())
                || "2".equals(applicationDetailStatusReqVO.getStatus())) {
            yamagataStatusModifyDocSave.setSendStatus("0");
        } else {
            yamagataStatusModifyDocSave.setSendStatus("1");
        }
        yamagataStatusModifyDocSave.setPushRecordOid("");
        try {
            statusModify = repositoryUtil.saveToResultId(db, yamagataStatusModifyDocSave);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
            throw new BusinessException(messages);
        }
        output.setPushStatus("5");
//        if ("9".equals(applicationDetailStatusReqVO.getStatus())) {
//            String deviceId = "";
//            String userId = "";
//            YamagataUserInfoDoc userInfoDoc = new YamagataUserInfoDoc();
//            try {
//                userInfoDoc = repositoryUtil.find(db,applicationDoc.getUserId(),
//                        YamagataUserInfoDoc.class);
//            } catch (Exception e) {
//                // e.printStackTrace();
//                LogInfoUtil.LogDebug(e.getMessage());
//                // エラーメッセージを出力、処理終了。
//                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
//                throw new BusinessException(messages);
//            }
//            if (userInfoDoc != null) {
//                userId = applicationDoc.getUserId();
//                deviceId = encryptorUtil.decrypt(userInfoDoc.getDeviceId());
//            }
//            String pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_6;
//            String message = "";
//            String receiptDate = dateFormatJP(applicationDoc.getReceiptDate());
//            String applyInformation = Constants.YAMAGATA_RECEIPT_DATE + receiptDate + Constants.YAMAGATA_ACCOUNT_SEQ
//                    + applicationDoc.getAccountAppSeq() + Constants.YAMAGATA_APPLY_KIND;
//            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_6 + applyInformation
//                    + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
//            String messageHtml = Constants.YAMAGATA_PUSH_MESSAGE_HTML_START + message
//                    + Constants.YAMAGATA_PUSH_MESSAGE_HTML_END;
//            date = new Date();
//            SimpleDateFormat dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
//            String pushDate = dateFormart.format(date);
//            List<String> list = new ArrayList<String>();
//            String messageCode = "";
//            String pushrecorDetaldId = "";
//            String pushrecordId = "";
//            int sum = 0;
//            list.add(deviceId);
//            String queryKey = "accountAppSeq:\"" + applicationDoc.getAccountAppSeq() + "\"";
//            List<YamagataStatusModifyDoc> yamagataStatusModifyList = repositoryUtil.getIndex(db,
//                    ApplicationKeys.INSIGHTINDEX_STATUSMODIFY_SEARCHINFO, queryKey, YamagataStatusModifyDoc.class);
//            for (YamagataStatusModifyDoc yamagataStatusModifyDoc : yamagataStatusModifyList) {
//                YamagataPushrecordDoc yamagataPushrecordDoc = new YamagataPushrecordDoc();
//                try {
//                    yamagataPushrecordDoc = repositoryUtil.find(db, yamagataStatusModifyDoc.getPushRecordOid(),
//                            YamagataPushrecordDoc.class);
//                } catch (Exception e) {
//                }
//                if (yamagataPushrecordDoc != null && !"".equals(yamagataPushrecordDoc.get_id())) {
//                    sum = sum + 1;
//                }
//            }
//            if (!deviceId.isEmpty()) {
//                // PUSH通知を送信する
//                messageCode = pushNotifications.sendMessage(pushTile, sum, list);
//            }
//            // 配信詳細DOC作成
//            YamagataPushDetailDoc yamagataPushDetailDoc = new YamagataPushDetailDoc();
//            yamagataPushDetailDoc.setPushMessage(messageHtml);
//            try {
//                pushrecorDetaldId = repositoryUtil.saveToResultId(db, yamagataPushDetailDoc);
//            } catch (BusinessException e) {
//                // e.printStackTrace();
//                LogInfoUtil.LogDebug(e.getMessage());
//                // エラーメッセージを出力、処理終了。
//                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
//                throw new BusinessException(messages);
//            }
//            // 配信履歴DOC作成
//            YamagataPushrecordDoc yamagataPushrecordDoc = new YamagataPushrecordDoc();
//            // ユーザーID
//            yamagataPushrecordDoc.setUserId(userId);
//            // 端末ID
//            yamagataPushrecordDoc.setDeviceTokenId(deviceId);
//            // 配信日時
//            yamagataPushrecordDoc.setPushDate(pushDate);
//            // 配信日時並び用
//            yamagataPushrecordDoc.setPushDateForSort(date.getTime());
//            // プッシュ内容タイトル
//            yamagataPushrecordDoc.setPushTitle(pushTile);
//            // プッシュ開封状況
//            yamagataPushrecordDoc.setPushStatus("1");
//            // プッシュ詳細OID
//            yamagataPushrecordDoc.setPushDetailOid(pushrecorDetaldId);
//            try {
//                pushrecordId = repositoryUtil.saveToResultId(db, yamagataPushrecordDoc);
//            } catch (BusinessException e) {
//                // e.printStackTrace();
//                LogInfoUtil.LogDebug(e.getMessage());
//                // エラーメッセージを出力、処理終了。
//                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
//                throw new BusinessException(messages);
//            }
//            YamagataStatusModifyDoc yamagataStatusModifyDoc = new YamagataStatusModifyDoc();
//            try {
//                yamagataStatusModifyDoc = repositoryUtil.find(db, statusModify, YamagataStatusModifyDoc.class);
//            } catch (Exception e) {
//                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
//                throw new BusinessException(messages);
//            }
//            if (Constants.RETURN_OK.equals(messageCode)) {
//                // ２：配信済
//                yamagataStatusModifyDoc.setSendStatus("2");
//            } else {
//                if (!deviceId.isEmpty()) {
//                    // ４：承認済（配信失敗）
//                    yamagataStatusModifyDoc.setSendStatus("4");
//                } else {
//                    // ３：承認済（配信不可）
//                    yamagataStatusModifyDoc.setSendStatus("3");
//                }
//            }
//            yamagataStatusModifyDoc.setPushRecordOid(pushrecordId);
//            try {
//                repositoryUtil.update(db, yamagataStatusModifyDoc);
//            } catch (BusinessException e) {
//                // e.printStackTrace();
//                LogInfoUtil.LogDebug(e.getMessage());
//                // エラーメッセージを出力、処理終了。
//                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
//                throw new BusinessException(messages);
//            }
//            output.setPushStatus("1");
//        }
        // 戻る値を設定
        output.set_id(applicationDoc.get_id());
        output.setStatus(applicationDoc.getStatus());

        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_6 + accountAppUpdLog + ")", db);
        return output;
    }

    /**
     * PUSH通知を送信する。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param AccountAppDoc
     *            口座開設取得結果
     * @param status
     *            ステータス
     * 
     * @return void
     */

    // 広島
    public PushrecordDoc pushNotifications(CloudantClient client, AccountAppDoc doc, String status) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);
        PushrecordDoc pushrecordDoc = new PushrecordDoc();
        // 配信履歴DBに追加
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String date = sdf.format(new Date());
        pushrecordDoc.setDocType(Constants.DOCTYPE_1);
        List<String> deviceTokenIdList = new ArrayList<String>();
        String receiptDate = dateFormatJP(doc.getReceiptDate());
        String message = "";
        String applyInformation = Constants.RECEIPT_DATE + receiptDate + Constants.ACCOUNT_SEQ + doc.getAccountAppSeq()
                + Constants.APPLY_KIND;
        switch (status) {
        case "1":
            message = Constants.PUSH_MESSAGE_STATUS_1 + applyInformation + Constants.PUSH_MESSAGE_ABOUT;
            break;
        case "2":
            message = Constants.PUSH_MESSAGE_STATUS_2 + applyInformation + Constants.PUSH_MESSAGE_ABOUT;
            break;
        case "3":
            message = Constants.PUSH_MESSAGE_STATUS_3 + Constants.PUSH_MESSAGE_ABOUT;
            break;
        case "4":
            message = Constants.PUSH_MESSAGE_STATUS_4 + applyInformation + Constants.PUSH_MESSAGE_ABOUT;
            break;
        case "5":
            message = Constants.PUSH_MESSAGE_STATUS_5 + applyInformation + Constants.PUSH_MESSAGE_ABOUT;
            break;
        case "6":
            message = Constants.PUSH_MESSAGE_STATUS_6 + applyInformation + Constants.PUSH_MESSAGE_ABOUT;
            break;
        case "7":
            message = Constants.PUSH_MESSAGE_STATUS_7 + applyInformation + Constants.PUSH_MESSAGE_ABOUT;
            break;
        case "8":
            message = Constants.PUSH_MESSAGE_STATUS_8 + applyInformation + Constants.PUSH_MESSAGE_ABOUT;
            break;
        default:
            break;
        }

        // 臨時ユーザの場合、単一アプリを送信する
        if (doc.getUserType() == 0) {
            // PUSH通知を送信する
            List<String> list = new ArrayList<String>();

            list.add(encryptorUtil.decrypt(doc.getDeviceTokenId()));
            // String messageCode = pushNotifications.sendMessage(message, sum,
            // list);
            pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
            pushrecordDoc.setUserId(doc.getUserId());
            List<String> tokenId = new ArrayList<String>();
            tokenId.add(doc.getDeviceTokenId());
            pushrecordDoc.setDeviceTokenId(tokenId);
            // pushrecordDoc.setPushDate(pushDate);
            pushrecordDoc.setOpenDate("");
            pushrecordDoc.setSaveDate(date);
            pushrecordDoc.setPushContent(message);
            pushrecordDoc.setStatus(status);
            pushrecordDoc.setReceiptDate(doc.getReceiptDate());
            try {
                repositoryUtil.save(db, pushrecordDoc);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                throw new BusinessException(messages);
            }

        } else {
            // 正式ユーザの場合、複数アプリを送信する
            List<UserInfoDoc> userInfoDoc = new ArrayList<>();
            // 検索キーを整理する
            String queryKey = "userId:\"" + doc.getUserId() + "\"";

            userInfoDoc = repositoryUtil.getIndex(db, ApplicationKeys.INSIGHTINDEX_ACCOUNTMNT_SEARCHBYUSERID_USERINFO,
                    queryKey, UserInfoDoc.class);
            if (userInfoDoc != null && userInfoDoc.size() != 0) {
                deviceTokenIdList = userInfoDoc.get(0).getDeviceTokenIdList();
            }
            if (deviceTokenIdList != null && deviceTokenIdList.size() > 0) {
                if (deviceTokenIdList.size() == 1 && "".equals(encryptorUtil.decrypt(deviceTokenIdList.get(0)))) {
                    deviceTokenIdList = new ArrayList<String>();
                }
            }
            if (deviceTokenIdList.size() == 0 || deviceTokenIdList.isEmpty()) {
                pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
                pushrecordDoc.setUserId(doc.getUserId());
                pushrecordDoc.setSaveDate(date);
                pushrecordDoc.setPushContent(message);
                // pushrecordDoc.setPushStatus("4");
                if (userInfoDoc != null && userInfoDoc.size() != 0) {
                    pushrecordDoc.setDeviceTokenId(userInfoDoc.get(0).getDeviceTokenIdList());
                } else {
                    pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                }
                pushrecordDoc.setReceiptDate(doc.getReceiptDate());
                pushrecordDoc.setStatus(status);
                try {
                    repositoryUtil.save(db, pushrecordDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                    throw new BusinessException(messages);
                }
            } else {

                // PUSH通知を送信する
                List<String> list = new ArrayList<String>();
                for (int count = 0; count < deviceTokenIdList.size(); count++) {
                    list.add(encryptorUtil.decrypt(deviceTokenIdList.get(count)));
                }

                // String messageCode = pushNotifications.sendMessage(message,
                // sum, list);

                pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
                pushrecordDoc.setUserId(doc.getUserId());
                pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                // pushrecordDoc.setPushDate(pushDate);
                pushrecordDoc.setOpenDate("");
                pushrecordDoc.setSaveDate(date);
                pushrecordDoc.setPushContent(message);
                pushrecordDoc.setStatus(status);
                pushrecordDoc.setReceiptDate(doc.getReceiptDate());
                // if (messageCode.equals(Constants.RETURN_OK)) {
                // // 未開封
                // pushrecordDoc.setPushStatus("1");
                // } else {
                // // 配信失敗
                // pushrecordDoc.setPushStatus("3");
                // }
                try {
                    repositoryUtil.save(db, pushrecordDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                    throw new BusinessException(messages);
                }

            }
        }
        return pushrecordDoc;
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
