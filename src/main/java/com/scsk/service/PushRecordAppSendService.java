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
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.model.HyakujyushiUserInfoDoc;
import com.scsk.model.HyakujyushiUserInfoDoc;
import com.scsk.model.HyakujyushiUserInfoDoc;
import com.scsk.model.HyakujyushiUserInfoDoc;
import com.scsk.model.IYoUserInfoDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.model.UserInfoDoc;
import com.scsk.model.YamagataPushDetailDoc;
import com.scsk.model.YamagataPushrecordDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.model.YamagataUserInfoDoc;
import com.scsk.model.geo.DeviceInfoDoc;
import com.scsk.request.vo.PushRecordAppListSendReqVO;
import com.scsk.util.ActionLog;
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
public class PushRecordAppSendService extends AbstractBLogic<PushRecordAppListSendReqVO, PushRecordAppListSendReqVO> {

    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private PushNotifications pushNotifications;
    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;

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
        String sendStatus = "";
        String pushRecordAppSendLog = "";
        // push通知履歴一覧を取得
        int count = 0;
        int selectCount = 0;
        for (AccountAppPushListVO accountAppPushListVO : ReqVO.getSendList()) {
            if (accountAppPushListVO.getSelect() == null) {
                continue;
            }
            if ("0169".equals(bank_cd)) {
                PushrecordDoc pushrecordDoc = new PushrecordDoc();
                try {
                    // push通知履歴
                    if (accountAppPushListVO.getSelect() == true) {
                        selectCount++;
                        pushrecordDoc = (PushrecordDoc) repositoryUtil.find(db, accountAppPushListVO.get_id(),
                                PushrecordDoc.class);
                    }
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1001);
                    throw new BusinessException(messages);
                }
                if (accountAppPushListVO.getSelect() == true) {
                    int countI = 0;
                    countI = pushNotifications(client, pushrecordDoc, pushrecordDoc.getStatus());
                    if (countI != 0) {
                        sendStatus = "配信失敗";
                    } else {
                        sendStatus = "配信成功";
                    }
                    String statusStr = "";
                    if ("3".equals(pushrecordDoc.getStatus())) {
                        statusStr = "完了";
                    } else if ("4".equals(pushrecordDoc.getStatus())) {
                        statusStr = "却下（総合的判断）";
                    } else if ("5".equals(pushrecordDoc.getStatus())) {
                        statusStr = "却下（本人確認連絡不可）";
                    } else if ("6".equals(pushrecordDoc.getStatus())) {
                        statusStr = "却下（郵便受取不可）";
                    } else if ("7".equals(pushrecordDoc.getStatus())) {
                        statusStr = "却下（内容不備）";
                    } else if ("8".equals(pushrecordDoc.getStatus())) {
                        statusStr = "却下（本人申出）";
                    }
                    pushRecordAppSendLog = pushRecordAppSendLog + "【受付番号：" + pushrecordDoc.getAccountAppSeq()
                            + "/ステータス：" + statusStr + "/配信状態：" + sendStatus + "】";
                    count = count + countI;
                }
                if (count == selectCount) {
                    // 一括配信失敗しました。
                    ReqVO.setErrFlag("0");
                } else if (count == 0) {
                    // 一括配信成功しました。
                    ReqVO.setErrFlag("1");
                } else {
                    // 一括配信完了しました。（配信失敗あり）
                    ReqVO.setErrFlag("2");
                }
            } else if ("0122".equals(bank_cd)) {
                if (accountAppPushListVO.getSelect() == true) {
                    yamagateAdmit(db, accountAppPushListVO);
                }
            }else if("0173".equals(bank_cd)){
                if (accountAppPushListVO.getSelect() == true) {
                    hyakujyushiAdmit(db, accountAppPushListVO);
                }
            }
            
        }

        actionLog.saveActionLog(Constants.ACTIONLOG_PUSHPRECORD_3 + pushRecordAppSendLog, db);
        return ReqVO;
    }

    private void hyakujyushiAdmit(Database db, AccountAppPushListVO accountAppPushListVO) throws Exception {
        List<DeviceInfoDoc> deviceInfoList = new ArrayList<>();
        String deviceId = "";
        String userId = "";
        HyakujyushiUserInfoDoc userInfoDoc = new HyakujyushiUserInfoDoc();
        try {
            userInfoDoc = repositoryUtil.find(db, accountAppPushListVO.getUserId(), HyakujyushiUserInfoDoc.class);
        } catch (Exception e) {

        }
        deviceInfoList=userInfoDoc.getDeviceInfoList();
        if (userInfoDoc != null) {
            for(int i=0;i<deviceInfoList.size();i++){
                deviceId=deviceId+encryptorUtil.decrypt(deviceInfoList.get(i).getDeviceTokenId())+"/";
            }
            userId = accountAppPushListVO.getUserId();
        }
        String pushTile = "";
        String message = "";
        String receiptDate = dateFormatJP(accountAppPushListVO.getReceiptDate());
        String applyInformation = Constants.HYAKUJYUSH_RECEIPT_DATE + receiptDate + Constants.HYAKUJYUSH_ACCOUNT_SEQ
                + accountAppPushListVO.getAccountAppSeq()
                + Constants.HYAKUJYUSH_APPLY_KIND
                + Constants.HYAKUJYUSHI_APPLY_KIND_1 +accountAppPushListVO.getSalesOfficeOption() +Constants.HYAKUJYUSHI_APPLY_KIND_2;
        switch (accountAppPushListVO.getStatus()) {
        case "1":
            message = Constants.HYAKUJYUSHI_PUSH_MESSAGE_STATUS_1 + applyInformation
                    + Constants.HYAKUJYUSHI_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.HYAKUJYUSHI_PUSH_MESSAGE_TITLE_1;
            break;
        case "2":
            message = Constants.HYAKUJYUSHI_PUSH_MESSAGE_STATUS_2 + applyInformation
                    + Constants.HYAKUJYUSHI_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.HYAKUJYUSHI_PUSH_MESSAGE_TITLE_2;
            break;
        case "3":
            message = Constants.HYAKUJYUSHI_PUSH_MESSAGE_STATUS_3 + Constants.HYAKUJYUSHI_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.HYAKUJYUSHI_PUSH_MESSAGE_TITLE_3;
            break;
        case "4":
            message = Constants.HYAKUJYUSHI_PUSH_MESSAGE_STATUS_4 + applyInformation
                    + Constants.HYAKUJYUSHI_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.HYAKUJYUSHI_PUSH_MESSAGE_TITLE_4;
            break;
        case "5":
            message = Constants.HYAKUJYUSHI_PUSH_MESSAGE_STATUS_5 + applyInformation
                    + Constants.HYAKUJYUSHI_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.HYAKUJYUSHI_PUSH_MESSAGE_TITLE_5;
            break;
        case "6":
            message = Constants.HYAKUJYUSHI_PUSH_MESSAGE_STATUS_6 + applyInformation
                    + Constants.HYAKUJYUSHI_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.HYAKUJYUSHI_PUSH_MESSAGE_TITLE_6;
            break;
        case "7":
            message = Constants.HYAKUJYUSHI_PUSH_MESSAGE_STATUS_7 + applyInformation
                    + Constants.HYAKUJYUSHI_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.HYAKUJYUSHI_PUSH_MESSAGE_TITLE_7;
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
        list.add(deviceId);
        String queryKey = "accountAppSeq:\"" + accountAppPushListVO.getAccountAppSeq() + "\"";
        List<YamagataStatusModifyDoc> yamagataStatusModifyList = repositoryUtil.getIndex(db,
                ApplicationKeys.INSIGHTINDEX_STATUSMODIFY_SEARCHINFO, queryKey, YamagataStatusModifyDoc.class);
        for (YamagataStatusModifyDoc yamagataStatusModifyDoc : yamagataStatusModifyList) {
            YamagataPushrecordDoc yamagataPushrecordDoc = new YamagataPushrecordDoc();
            try {
                yamagataPushrecordDoc = repositoryUtil.find(db, yamagataStatusModifyDoc.getPushRecordOid(),
                        YamagataPushrecordDoc.class);
            } catch (Exception e) {
            }
            if (yamagataPushrecordDoc != null && !"".equals(yamagataPushrecordDoc.get_id())) {
                sum = sum + 1;
            }
        }
        if (!deviceId.isEmpty()) {
            // PUSH通知を送信する
            messageCode = pushNotifications.sendMessage(pushTile, sum, list);
        }
        // 配信詳細DOC作成
        YamagataPushDetailDoc yamagataPushDetailDoc = new YamagataPushDetailDoc();
        yamagataPushDetailDoc.setPushMessage(messageHtml);
        try {
            pushrecorDetaldId = repositoryUtil.saveToResultId(db, yamagataPushDetailDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }
        // 配信履歴DOC作成
        YamagataPushrecordDoc yamagataPushrecordDoc = new YamagataPushrecordDoc();
        // ユーザーID
        yamagataPushrecordDoc.setUserId(userId);
        // 端末ID
        yamagataPushrecordDoc.setDeviceTokenId(deviceId);
        // 配信日時
        yamagataPushrecordDoc.setPushDate(pushDate);
        // 配信日時並び用
        yamagataPushrecordDoc.setPushDateForSort(date.getTime());
        // プッシュ内容タイトル
        yamagataPushrecordDoc.setPushTitle(pushTile);
        // プッシュ開封状況
        yamagataPushrecordDoc.setPushStatus("1");
        // プッシュ詳細OID
        yamagataPushrecordDoc.setPushDetailOid(pushrecorDetaldId);
        try {
            pushrecordId = repositoryUtil.saveToResultId(db, yamagataPushrecordDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }
        YamagataStatusModifyDoc yamagataStatusModifyDoc = new YamagataStatusModifyDoc();
        try {
            yamagataStatusModifyDoc = repositoryUtil.find(db, accountAppPushListVO.get_id(),
                    YamagataStatusModifyDoc.class);

        } catch (Exception e) {

        }
        if (Constants.RETURN_OK.equals(messageCode)) {
            // ２：配信済
            yamagataStatusModifyDoc.setSendStatus("2");
        } else {
            if (!deviceId.isEmpty()) {
                // ４：承認済（配信失敗）
                yamagataStatusModifyDoc.setSendStatus("4");
            } else {
                // ３：承認済（配信不可）
                yamagataStatusModifyDoc.setSendStatus("3");
            }
        }
        yamagataStatusModifyDoc.setPushRecordOid(pushrecordId);
        try {
            repositoryUtil.update(db, yamagataStatusModifyDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }

    
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
    public void yamagateAdmit(Database db, AccountAppPushListVO accountAppPushListVO) throws Exception {
        String deviceId = "";
        String userId = "";
        YamagataUserInfoDoc userInfoDoc = new YamagataUserInfoDoc();
        try {
            userInfoDoc = repositoryUtil.find(db, accountAppPushListVO.getUserId(), YamagataUserInfoDoc.class);
        } catch (Exception e) {

        }
        if (userInfoDoc != null) {
            userId = accountAppPushListVO.getUserId();
            deviceId = encryptorUtil.decrypt(userInfoDoc.getDeviceId());
        }
        String pushTile = "";
        String message = "";
        String receiptDate = dateFormatJP(accountAppPushListVO.getReceiptDate());
        String applyInformation = Constants.YAMAGATA_RECEIPT_DATE + receiptDate + Constants.YAMAGATA_ACCOUNT_SEQ
                + accountAppPushListVO.getAccountAppSeq() + Constants.YAMAGATA_APPLY_KIND;
        switch (accountAppPushListVO.getStatus()) {
        case "1":
            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_1 + applyInformation
                    + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_1;
            break;
        case "2":
            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_2 + applyInformation
                    + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_2;
            break;
        case "3":
            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_3 + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_3;
            break;
        case "4":
            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_4 + applyInformation
                    + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_4;
            break;
        case "5":
            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_4 + applyInformation
                    + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_5;
            break;
        case "6":
            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_4 + applyInformation
                    + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_6;
            break;
        case "7":
            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_4 + applyInformation
                    + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_7;
            break;
        case "8":
            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_5 + applyInformation
                    + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_8;
            break;
        case "9":
            message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_6 + applyInformation
                    + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
            pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_9;
            break;
        default:
            break;
        }
        String messageHtml = Constants.YAMAGATA_PUSH_MESSAGE_HTML_START + message
                + Constants.YAMAGATA_PUSH_MESSAGE_HTML_END;
        Date date = new Date();
        SimpleDateFormat dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String pushDate = dateFormart.format(date);
        List<String> list = new ArrayList<String>();
        String messageCode = "";
        String pushrecorDetaldId = "";
        String pushrecordId = "";
        int sum = 0;
        list.add(deviceId);
        String queryKey = "accountAppSeq:\"" + accountAppPushListVO.getAccountAppSeq() + "\"";
        List<YamagataStatusModifyDoc> yamagataStatusModifyList = repositoryUtil.getIndex(db,
                ApplicationKeys.INSIGHTINDEX_STATUSMODIFY_SEARCHINFO, queryKey, YamagataStatusModifyDoc.class);
        for (YamagataStatusModifyDoc yamagataStatusModifyDoc : yamagataStatusModifyList) {
            YamagataPushrecordDoc yamagataPushrecordDoc = new YamagataPushrecordDoc();
            try {
                yamagataPushrecordDoc = repositoryUtil.find(db, yamagataStatusModifyDoc.getPushRecordOid(),
                        YamagataPushrecordDoc.class);
            } catch (Exception e) {
            }
            if (yamagataPushrecordDoc != null && !"".equals(yamagataPushrecordDoc.get_id())) {
                sum = sum + 1;
            }
        }
        if (!deviceId.isEmpty()) {
            // PUSH通知を送信する
            messageCode = pushNotifications.sendMessage(pushTile, sum, list);
        }
        // 配信詳細DOC作成
        YamagataPushDetailDoc yamagataPushDetailDoc = new YamagataPushDetailDoc();
        yamagataPushDetailDoc.setPushMessage(messageHtml);
        try {
            pushrecorDetaldId = repositoryUtil.saveToResultId(db, yamagataPushDetailDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }
        // 配信履歴DOC作成
        YamagataPushrecordDoc yamagataPushrecordDoc = new YamagataPushrecordDoc();
        // ユーザーID
        yamagataPushrecordDoc.setUserId(userId);
        // 端末ID
        yamagataPushrecordDoc.setDeviceTokenId(deviceId);
        // 配信日時
        yamagataPushrecordDoc.setPushDate(pushDate);
        // 配信日時並び用
        yamagataPushrecordDoc.setPushDateForSort(date.getTime());
        // プッシュ内容タイトル
        yamagataPushrecordDoc.setPushTitle(pushTile);
        // プッシュ開封状況
        yamagataPushrecordDoc.setPushStatus("1");
        // プッシュ詳細OID
        yamagataPushrecordDoc.setPushDetailOid(pushrecorDetaldId);
        try {
            pushrecordId = repositoryUtil.saveToResultId(db, yamagataPushrecordDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }
        YamagataStatusModifyDoc yamagataStatusModifyDoc = new YamagataStatusModifyDoc();
        try {
            yamagataStatusModifyDoc = repositoryUtil.find(db, accountAppPushListVO.get_id(),
                    YamagataStatusModifyDoc.class);

        } catch (Exception e) {

        }
        if (Constants.RETURN_OK.equals(messageCode)) {
            // ２：配信済
            yamagataStatusModifyDoc.setSendStatus("2");
        } else {
            if (!deviceId.isEmpty()) {
                // ４：承認済（配信失敗）
                yamagataStatusModifyDoc.setSendStatus("4");
            } else {
                // ３：承認済（配信不可）
                yamagataStatusModifyDoc.setSendStatus("3");
            }
        }
        yamagataStatusModifyDoc.setPushRecordOid(pushrecordId);
        try {
            repositoryUtil.update(db, yamagataStatusModifyDoc);
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
    public int pushNotifications(CloudantClient client, PushrecordDoc doc, String status) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);
        int errCount = 0;
        PushrecordDoc pushrecordDoc = new PushrecordDoc();
        // 配信履歴DBに追加
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String date = sdf.format(new Date());
        SimpleDateFormat dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT_ASS);
        String pushDate = dateFormart.format(new Date());
        String sortTime = Utils.timeFormatConvert(Constants.DATE_FORMAT_ASS, Constants.DATE_FORMAT_SSS, pushDate);
        pushrecordDoc.setDocType(Constants.DOCTYPE_1);

        int sum = 1;
        // 配信履歴Doc件数検索
        List<PushrecordDoc> pushrecordDocSUM = new ArrayList<>();
        MapReduce viewSUM = new MapReduce();

        viewSUM.setMap(
                "function (doc) {if(doc.docType && doc.docType === \"PUSHRECORD\" && doc.delFlg && doc.delFlg===\"0\" && doc.pushStatus ===\"1\" && doc.userId===\""
                        + doc.getUserId() + "\") {emit(doc._id, 1);}}");

        pushrecordDocSUM = repositoryUtil.queryByDynamicView(db, viewSUM, PushrecordDoc.class);

        if (pushrecordDocSUM != null && pushrecordDocSUM.size() != 0) {
            sum = pushrecordDocSUM.size() + 1;
        }
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
        int userType = 2;
        List<AccountAppDoc> accountAppDocList = new ArrayList<>();
        List<AccountYamaGataAppDoc> accountAppDocList2 = new ArrayList<>();
        accountAppDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST,
                AccountAppDoc.class);
        AccountAppDoc accountAppDocTokenId = new AccountAppDoc();
        for (AccountAppDoc accountAppDoc : accountAppDocList) {
            if (accountAppDoc.getAccountAppSeq().equals(doc.getAccountAppSeq())) {
                userType = accountAppDoc.getUserType();
                accountAppDocTokenId = accountAppDoc;
                break;
            }
        }
        // 臨時ユーザの場合、単一アプリを送信する
        if (userType == 0) {
            // PUSH通知を送信する
            List<String> list = new ArrayList<String>();
            // if (accountAppDoc1.getDeviceTokenId() != null &&
            // accountAppDoc1.getDeviceTokenId().size() > 0) {
            // if (doc.getDeviceTokenId().get(0) != null &&
            // !doc.getDeviceTokenId().get(0).isEmpty()) {
            // list.add(encryptorUtil.decrypt(doc.getDeviceTokenId().get(0)));
            // }
            // }
            list.add(encryptorUtil.decrypt(accountAppDocTokenId.getDeviceTokenId()));
            List<String> tokenId = new ArrayList<String>();
            tokenId.add(accountAppDocTokenId.getDeviceTokenId());
            if (list != null && list.size() > 0) {
                if (list.size() == 1 && "".equals(list.get(0))) {
                    list = new ArrayList<String>();
                }
            }
            if (list.isEmpty() || list.size() == 0) {
                errCount++;
                pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
                pushrecordDoc.setUserId(doc.getUserId());
                pushrecordDoc.setSaveDate(date);
                pushrecordDoc.setPushContent("");
                pushrecordDoc.setPushStatus("4");
                pushrecordDoc.set_id(doc.get_id());
                pushrecordDoc.set_rev(doc.get_rev());
                pushrecordDoc.setStatus(status);
                pushrecordDoc.setPushDate("");
                pushrecordDoc.setSortTime("");
                pushrecordDoc.setDeviceTokenId(tokenId);
                pushrecordDoc.setCreatedDate(doc.getCreatedDate());
                try {
                    repositoryUtil.update(db, pushrecordDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
                    throw new BusinessException(messages);
                }
            } else {
                String messageCode = pushNotifications.sendMessage(message, sum, list);
                pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
                pushrecordDoc.setUserId(doc.getUserId());
                pushrecordDoc.setDeviceTokenId(tokenId);
                pushrecordDoc.setPushDate(pushDate);
                pushrecordDoc.setSortTime(sortTime);
                pushrecordDoc.setOpenDate(doc.getOpenDate());
                pushrecordDoc.setSaveDate(doc.getSaveDate());
                pushrecordDoc.setPushContent(doc.getPushContent());
                pushrecordDoc.set_id(doc.get_id());
                pushrecordDoc.set_rev(doc.get_rev());
                pushrecordDoc.setStatus(status);
                pushrecordDoc.setCreatedDate(doc.getCreatedDate());
                if (Constants.RETURN_OK.equals(messageCode)) {
                    // 未開封
                    pushrecordDoc.setPushStatus("1");
                } else {
                    // 配信失敗
                    pushrecordDoc.setPushStatus("3");
                    pushrecordDoc.setPushDate("");
                    pushrecordDoc.setSortTime("");
                    errCount++;
                }
                try {
                    repositoryUtil.update(db, pushrecordDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
                    throw new BusinessException(messages);
                }
            }
        } else {
            // 正式ユーザの場合、複数アプリを送信する
            List<UserInfoDoc> userInfoDoc = new ArrayList<>();
            List<String> deviceTokenIdList = new ArrayList<String>();
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
            if (deviceTokenIdList.isEmpty() || deviceTokenIdList.size() == 0) {
                errCount++;
                pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
                pushrecordDoc.setUserId(doc.getUserId());
                pushrecordDoc.setSaveDate(date);
                pushrecordDoc.setPushContent(doc.getPushContent());
                pushrecordDoc.setPushStatus("4");
                pushrecordDoc.set_id(doc.get_id());
                pushrecordDoc.set_rev(doc.get_rev());
                pushrecordDoc.setStatus(status);
                pushrecordDoc.setPushDate("");
                pushrecordDoc.setSortTime("");
                pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                pushrecordDoc.setCreatedDate(doc.getCreatedDate());
                try {
                    repositoryUtil.update(db, pushrecordDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
                    throw new BusinessException(messages);
                }
            } else {
                List<String> list = new ArrayList<String>();
                for (int count = 0; count < deviceTokenIdList.size(); count++) {
                    list.add(encryptorUtil.decrypt(deviceTokenIdList.get(count)));
                }
                // PUSH通知を送信する
                String messageCode = pushNotifications.sendMessage(message, sum, list);
                pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
                pushrecordDoc.setUserId(doc.getUserId());
                pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                pushrecordDoc.setPushDate(pushDate);
                pushrecordDoc.setSortTime(sortTime);
                pushrecordDoc.setOpenDate("");
                pushrecordDoc.setSaveDate(doc.getSaveDate());
                pushrecordDoc.setPushContent(doc.getPushContent());
                pushrecordDoc.setStatus(status);
                pushrecordDoc.set_id(doc.get_id());
                pushrecordDoc.set_rev(doc.get_rev());
                pushrecordDoc.setCreatedDate(doc.getCreatedDate());
                if (Constants.RETURN_OK.equals(messageCode)) {
                    // 未開封
                    pushrecordDoc.setPushStatus("1");
                } else {
                    // 配信失敗
                    pushrecordDoc.setPushStatus("3");
                    pushrecordDoc.setPushDate("");
                    pushrecordDoc.setSortTime("");
                    errCount++;
                }
                try {
                    repositoryUtil.update(db, pushrecordDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
                    throw new BusinessException(messages);
                }
            }
        }
        return errCount;
    }
}
