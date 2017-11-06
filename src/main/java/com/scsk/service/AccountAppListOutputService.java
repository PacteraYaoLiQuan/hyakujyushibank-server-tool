package com.scsk.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.scsk.model.AccountAppAgreedOperationDateDoc;
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.model.HyakujyushiUserInfoDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.model.StatusModifyDoc;
import com.scsk.model.UserInfoDoc;
import com.scsk.model.YamagataPushDetailDoc;
import com.scsk.model.YamagataPushrecordDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.model.YamagataUserInfoDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppListOutputButtonReqVO;
import com.scsk.response.vo.Account114AppListOutputButtonResVO;
import com.scsk.response.vo.AccountAppListOutputButtonResVO;
import com.scsk.response.vo.AccountYamaGataAppListOutputButtonResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PhoneNumberUtil;
import com.scsk.util.PushNotifications;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.AccountApp114InitVO;
import com.scsk.vo.AccountAppInitVO;
import com.scsk.vo.AccountAppYamaGataInitVO;

/**
 * 帳票出力検索サービス。<br>
 * <br>
 * 帳票出力検索を実装するロジック。<br>
 */
@Service
public class AccountAppListOutputService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
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
     * @param accountAppListOutputButtonReqVO
     *            申込一覧情報
     */
    @Override
    protected void preExecute(BaseResVO accountAppListOutputButtonReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param accountAppListOutputButtonReqVO
     *            申込一覧情報
     * @return accountAppListOutputButtonResVO 帳票用情報
     */
    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);

        if ("0169".equals(bank_cd)) {
            AccountAppListOutputButtonResVO accountAppListOutputButtonResVO = hirosima(client, db, baseResVO);
            return accountAppListOutputButtonResVO;
        } else if ("0122".equals(bank_cd)) {
            AccountYamaGataAppListOutputButtonResVO accountAppListOutputButtonResVO = yamagata(client, db, baseResVO);
            return accountAppListOutputButtonResVO;
        } else if ("0173".equals(bank_cd)) {

            Account114AppListOutputButtonResVO accountAppListOutputButtonResVO = hyakujyushi(client, db, baseResVO);
            return accountAppListOutputButtonResVO;

        }
        return null;
    }

    private Account114AppListOutputButtonResVO hyakujyushi(CloudantClient client, Database db, BaseResVO baseResVO) throws Exception {
        Account114AppListOutputButtonResVO accountAppListOutputButtonResVO = new Account114AppListOutputButtonResVO();
        AccountAppListOutputButtonReqVO accountAppListOutputButtonReqVO = new AccountAppListOutputButtonReqVO();
        accountAppListOutputButtonReqVO = (AccountAppListOutputButtonReqVO) baseResVO;
        List<AccountApp114InitVO> accountAppList = new ArrayList<>();
        String accoutAppListCsvLog = "(受付番号：";
        /*
         * List<UserInfoDoc> userInfoList = new ArrayList<>(); userInfoList =
         * repositoryUtil.getView(db,ApplicationKeys.
         * INSIGHTVIEW_USERINFOLIST_USERINFOLIST,UserInfoDoc.class);
         */

        List<AccountAppAgreedOperationDateDoc> agreeDateList = new ArrayList<>();
        for (int i = 0; i < accountAppListOutputButtonReqVO.getOutputList3().size(); i++) {
            if (accountAppListOutputButtonReqVO.getOutputList3().get(i).getSelect() == null) {
                continue;
            }
            HyakujyushiUserInfoDoc userInfoDoc = new HyakujyushiUserInfoDoc();
            // 一覧選択したデータ
            if (accountAppListOutputButtonReqVO.getOutputList3().get(i).getSelect() == true) {
                Account114AppDoc accountAppDoc = new Account114AppDoc();
                // PushrecordDoc pushrecordDoc = new PushrecordDoc();
                AccountApp114InitVO accountAppInitVO = new AccountApp114InitVO();

                try {
                    // 帳票出力用データを取得
                    accountAppDoc = (Account114AppDoc) repositoryUtil.find(db,
                            accountAppListOutputButtonReqVO.getOutputList3().get(i).get_id(),
                            Account114AppDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
                    throw new BusinessException(messages);
                }
                accoutAppListCsvLog = accoutAppListCsvLog + accountAppDoc.getAccountAppSeq() + "/";
                // 更新前に、「受付」の場合、ステータスを更新、配信履歴を追加、PUSH通知を送信する
                if ("1".equals(accountAppDoc.getStatus())) {

                    HyakujyushiPushAdmit(client, accountAppDoc, "2");

                    accountAppDoc.setStatus("2");
                }

                // 口座開設DBに更新
                // // 帳票出力回数を更新
                // accountAppDoc.setBillOutputCount(accountAppDoc.getBillOutputCount()
                // + 1);

                try {
                    repositoryUtil.update(db, accountAppDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                    throw new BusinessException(messages);
                }

                // 帳票出力用データを戻る
                // 帳票出力用データ初期値をセット
                accountAppInitVO.setIdentificationType("");
                accountAppInitVO.setLivingConditions("");
                accountAppInitVO.setLastName("");
                accountAppInitVO.setFirstName("");
                accountAppInitVO.setKanaLastName("");
                accountAppInitVO.setKanaFirstName("");
                accountAppInitVO.setBirthday("");

                accountAppInitVO.setAccountType("");
                accountAppInitVO.setSexKbn("");
                accountAppInitVO.setPostCode("");
                accountAppInitVO.setPrefecturesCode("");
                accountAppInitVO.setAddress("");
                accountAppInitVO.setTeleNumber("");
                accountAppInitVO.setPhoneNumber("");
                accountAppInitVO.setWorkTeleNumber("");
                accountAppInitVO.setWorkName("");
                accountAppInitVO.setAccountPurposeOther("");
                accountAppInitVO.setJobKbnOther("");
                accountAppInitVO.setSecurityPassword("");
                accountAppInitVO.setBankbookType("");
                accountAppInitVO.setCardType("");
                accountAppInitVO.setSalesOfficeOption("");
                accountAppInitVO.setCreditlimit("0");
                accountAppInitVO.setOnlinePassword("");
                accountAppInitVO.setApplicationDate("");
                accountAppInitVO.setIdentificationImage("");

                // 帳票出力用データをセット
                accountAppInitVO.set_id(accountAppDoc.get_id());
                accountAppInitVO.set_rev(accountAppDoc.get_rev());

                // 受付番号
                accountAppInitVO.setAccountAppSeq(accountAppDoc.getAccountAppSeq());

                // 本人確認書類
                String idType = accountAppDoc.getIdentificationType();
                if (idType.equals("A1")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_1);
                }
                if (idType.equals("A2")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_2);
                }
                if (idType.equals("A3")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_3);
                }
                if (idType.equals("A4")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_4);
                }

                // 生活状況確認書類
                String livinType = accountAppDoc.getLivingConditions();
                if (livinType.equals("B1")) {
                    accountAppInitVO.setLivingConditions(Constants.LIVINGCONDITIONS_1);
                }
                if (livinType.equals("B2")) {
                    accountAppInitVO.setLivingConditions(Constants.LIVINGCONDITIONS_2);
                }

                // 姓
                accountAppInitVO.setLastName(encryptorUtil.decrypt(accountAppDoc.getLastName()));
                // 名
                accountAppInitVO.setFirstName(encryptorUtil.decrypt(accountAppDoc.getFirstName()));
                // 姓カナ
                accountAppInitVO.setKanaLastName(encryptorUtil.decrypt(accountAppDoc.getKanaLastName()));
                // 名カナ
                accountAppInitVO.setKanaFirstName(encryptorUtil.decrypt(accountAppDoc.getKanaFirstName()));
                // 生年月日
                accountAppInitVO.setBirthday(encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(0, 4)
                        + Constants.MARK4 + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(4, 6)
                        + Constants.MARK4 + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(6));
                // 普通預金の種類
                accountAppInitVO.setAccountType(accountAppDoc.getAccountType());
                // 性別
                switch (accountAppDoc.getSexKbn()) {
                case "1":
                    accountAppInitVO.setSexKbn("1");
                    break;
                case "2":
                    accountAppInitVO.setSexKbn("2");
                }

                // 郵便番号
                accountAppInitVO.setPostCode(encryptorUtil.decrypt(accountAppDoc.getPostCode()).substring(0, 3)
                        + Constants.MARK3 + encryptorUtil.decrypt(accountAppDoc.getPostCode()).substring(3));
                // 都道府
                accountAppInitVO.setPrefecturesCode(accountAppDoc.getPrefecturesCode());
                // 市区町村・番地・アパート・マンション名
                accountAppInitVO.setAddress(encryptorUtil.decrypt(accountAppDoc.getAddress()));
                // 自宅電話番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getTeleNumber()))) {
                    accountAppInitVO.setTeleNumber(
                            PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getTeleNumber())));
                }

                // 携帯電話番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber()))) {
                    accountAppInitVO.setPhoneNumber(
                            PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber())));
                }

                // 勤務先電話番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber()))) {
                    accountAppInitVO.setWorkTeleNumber(
                            PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber())));
                }

                // 勤務先名
                if (encryptorUtil.decrypt(accountAppDoc.getWorkName()).length() > 80) {
                    accountAppInitVO.setWorkName(encryptorUtil.decrypt(accountAppDoc.getWorkName()).substring(0, 80));
                } else {
                    accountAppInitVO.setWorkName(encryptorUtil.decrypt(accountAppDoc.getWorkName()));
                }

                // 取引目的
                String[] purpose = accountAppDoc.getAccountPurpose().split(",");
                List<String> accountPurpose=Arrays.asList(purpose);
                Collections.sort(accountPurpose);
                accountAppInitVO.setAccountPurpose(accountPurpose);
                // その他取引目的
                accountAppInitVO.setAccountPurposeOther(encryptorUtil.decrypt(accountAppDoc.getAccountPurposeOther()));

                // 職業
                String[] job = accountAppDoc.getJobKbn().split(",");
                List<String> jobKbn=Arrays.asList(job);
                Collections.sort(jobKbn);
                accountAppInitVO.setJobKbn(jobKbn);
                accountAppInitVO.setJobKbnOther(encryptorUtil.decrypt(accountAppDoc.getJobKbnOther()));

                // 暗証番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getSecurityPassword()))
                        && encryptorUtil.decrypt(accountAppDoc.getSecurityPassword()).length() == 4) {
                    String securityPassword1 = encryptorUtil.decrypt(accountAppDoc.getSecurityPassword());
                    String securityPassword2 = String
                            .valueOf((Integer.parseInt(securityPassword1.substring(3)) + 2) % 10)
                            + String.valueOf((Integer.parseInt(securityPassword1.substring(0, 1)) + 3) % 10)
                            + String.valueOf((Integer.parseInt(securityPassword1.substring(1, 2)) + 5) % 10)
                            + String.valueOf((Integer.parseInt(securityPassword1.substring(2, 3)) + 7) % 10);
                    accountAppInitVO.setSecurityPassword(securityPassword2);
                }

                // 通帳デザイン
                accountAppInitVO.setBankbookType(accountAppDoc.getBankbookType());
                // カードデザイン
                accountAppInitVO.setCardType(accountAppDoc.getCardType());
                // １日あたりの振込・払込限度額
                accountAppInitVO.setCreditlimit(accountAppDoc.getCreditlimit());
                // オンライン暗証番号
                accountAppInitVO.setOnlinePassword(encryptorUtil.decrypt(accountAppDoc.getOnlinePassword()));
                // 口座開設する店舗
                accountAppInitVO.setSalesOfficeOption(accountAppDoc.getSalesOfficeOption());
                // 申込日
                accountAppInitVO.setApplicationDate(accountAppDoc.getCreatedDate());

                // 本人確認書類画像Id
                accountAppInitVO.setIdentificationImage(accountAppDoc.getIdentificationImage());

                // 端末機種 OSバージョン アプリバージョン
                // 検索キーを整理する
                try {
                    userInfoDoc = repositoryUtil.find(db, accountAppDoc.getUserId(), HyakujyushiUserInfoDoc.class);

                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                }
                // 口座開設申込同意日時
                try {
                    String queryKey = "accountAppSeq:\"" + accountAppDoc.getAccountAppSeq() + "\"";

                    agreeDateList = repositoryUtil.getIndex(db,
                            ApplicationKeys.INSIGHTINDEX_ACCOUNT_SEARCHBYACCOUNTAPPSEQ_AGREEDATE, queryKey,
                            AccountAppAgreedOperationDateDoc.class);
                    if (agreeDateList != null && agreeDateList.size() != 0) {
                        accountAppInitVO.setReceiptDate(agreeDateList.get(0).getAgreedOperationDate());
                    }
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                }

                accountAppList.add(accountAppInitVO);
            }
        }

        accountAppListOutputButtonResVO.setAccountAppList(accountAppList);
        accoutAppListCsvLog = accoutAppListCsvLog.substring(0, accoutAppListCsvLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_7 + accoutAppListCsvLog + ")", db);
        return accountAppListOutputButtonResVO;
    }

    private void HyakujyushiPushAdmit(CloudantClient client, Account114AppDoc doc, String status) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);
        String deviceId = "";
        String userId = "";
        HyakujyushiUserInfoDoc userInfoDoc = new HyakujyushiUserInfoDoc();
        try {
            userInfoDoc = repositoryUtil.find(db, doc.getUserId(), HyakujyushiUserInfoDoc.class);
        } catch (Exception e) {

        }
        if (userInfoDoc != null) {
            userId = doc.getUserId();
            // deviceId =
            // encryptorUtil.decrypt(userInfoDoc.getDeviceId());
            deviceId = userInfoDoc.getDeviceInfoList().get(0).getDeviceTokenId();
        }
        String pushTile = "";
        String message = "";
        String receiptDate = dateFormatJP(doc.getReceiptDate());
        String applyInformation = Constants.YAMAGATA_RECEIPT_DATE + receiptDate + Constants.YAMAGATA_ACCOUNT_SEQ
                + doc.getAccountAppSeq() + Constants.YAMAGATA_APPLY_KIND;

        message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_2 + applyInformation + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
        pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_2;

        Date date = new Date();
        SimpleDateFormat dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String pushDate = dateFormart.format(date);
        List<String> list = new ArrayList<String>();
        String messageCode = "";
        String pushrecorDetaldId = "";
        String pushrecordId = "";
        int sum = 0;
        list.add(deviceId);
        String messageHtml = Constants.YAMAGATA_PUSH_MESSAGE_HTML_START + message
                + Constants.YAMAGATA_PUSH_MESSAGE_HTML_END;
        String queryKey = "accountAppSeq:\"" + doc.getAccountAppSeq() + "\"";
        List<StatusModifyDoc> yamagataStatusModifyList = repositoryUtil.getIndex(db,
                ApplicationKeys.INSIGHTINDEX_STATUSMODIFY_SEARCHINFO, queryKey, StatusModifyDoc.class);
        for (StatusModifyDoc yamagataStatusModifyDoc : yamagataStatusModifyList) {
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

        StatusModifyDoc yamagataStatusModifyDoc = new StatusModifyDoc();
        yamagataStatusModifyDoc.setAccountAppSeq(doc.getAccountAppSeq());
        yamagataStatusModifyDoc.setStatus(status);
        yamagataStatusModifyDoc.setStatusModifyDate(pushDate);
        yamagataStatusModifyDoc.setStatusModifyDateForSort(date.getTime());
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
            repositoryUtil.save(db, yamagataStatusModifyDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
            throw new BusinessException(messages);
        }
    }

    /**
     * 広島
     *
     */

    public AccountAppListOutputButtonResVO hirosima(CloudantClient client, Database db, BaseResVO baseResVO)
            throws Exception {
        AccountAppListOutputButtonResVO accountAppListOutputButtonResVO = new AccountAppListOutputButtonResVO();
        AccountAppListOutputButtonReqVO accountAppListOutputButtonReqVO = new AccountAppListOutputButtonReqVO();
        accountAppListOutputButtonReqVO = (AccountAppListOutputButtonReqVO) baseResVO;
        List<AccountAppInitVO> accountAppList = new ArrayList<>();
        String accoutAppListCsvLog = "(受付番号：";
        /*
         * List<UserInfoDoc> userInfoList = new ArrayList<>(); userInfoList =
         * repositoryUtil.getView(db,ApplicationKeys.
         * INSIGHTVIEW_USERINFOLIST_USERINFOLIST,UserInfoDoc.class);
         */

        for (int i = 0; i < accountAppListOutputButtonReqVO.getOutputList().size(); i++) {
            if (accountAppListOutputButtonReqVO.getOutputList().get(i).getSelect() == null) {
                continue;
            }
            // 一覧選択したデータ
            if (accountAppListOutputButtonReqVO.getOutputList().get(i).getSelect() == true) {
                AccountAppDoc accountAppDoc = new AccountAppDoc();
                // PushrecordDoc pushrecordDoc = new PushrecordDoc();
                AccountAppInitVO accountAppInitVO = new AccountAppInitVO();

                try {
                    // 帳票出力用データを取得
                    accountAppDoc = (AccountAppDoc) repositoryUtil.find(db,
                            accountAppListOutputButtonReqVO.getOutputList().get(i).get_id(), AccountAppDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
                    throw new BusinessException(messages);
                }
                accoutAppListCsvLog = accoutAppListCsvLog + accountAppDoc.getAccountAppSeq() + "/";
                // 更新前に、「受付」の場合、ステータスを更新、配信履歴を追加、PUSH通知を送信する
                if ("1".equals(accountAppDoc.getStatus())) {

                    // システム日時を取得
                    // SimpleDateFormat sdf = new
                    // SimpleDateFormat(Constants.DATE_FORMAT);
                    // String date = sdf.format(new Date());
                    // 配信日付 yyyyMMddHHmmssSSS
                    // SimpleDateFormat sdfPushSSS = new
                    // SimpleDateFormat(Constants.DATE_FORMAT_ASS);
                    // String datePushSSS = sdfPushSSS.format(new Date());

                    // List<String> deviceTokenIdList = new ArrayList<String>();

                    // int sum = 1;
                    // 配信履歴Doc件数検索
                    // List<PushrecordDoc> pushrecordDocSUM = new ArrayList<>();
                    // MapReduce viewSUM = new MapReduce();
                    // viewSUM.setMap("function (doc) {if(doc.docType &&
                    // doc.docType === \"PUSHRECORD\" && doc.delFlg &&
                    // doc.delFlg===\"0\" && doc.pushStatus ===\"1\" &&
                    // doc.userId===\""
                    // + accountAppDoc.getUserId()
                    // + "\") {emit(doc._id, 1);}}");
                    // pushrecordDocSUM =
                    // repositoryUtil.queryByDynamicView(db,viewSUM,
                    // PushrecordDoc.class);
                    // if (pushrecordDocSUM != null&& pushrecordDocSUM.size() !=
                    // 0) {
                    // sum = pushrecordDocSUM.size() + 1;
                    // }

                    // PUSH通知文言を設定
                    // String receiptDate=accountAppDoc.getReceiptDate();
                    // String receiptDateFormart= receiptDate.substring(0, 4) +
                    // Constants.YEAR
                    // + receiptDate.substring(5, 7) + Constants.MONTH_JP
                    // + receiptDate.substring(8, 10) + Constants.DAY + " "
                    // + receiptDate.substring(11,16);
                    // String applyInformation = Constants.RECEIPT_DATE +
                    // receiptDateFormart
                    // + Constants.ACCOUNT_SEQ +
                    // accountAppDoc.getAccountAppSeq()
                    // + Constants.APPLY_KIND;
                    pushNotifications(client, accountAppDoc, "2");

                    // ステータスを更新
                    accountAppDoc.setStatus("2");
                }

                // 口座開設DBに更新
                // 帳票出力回数を更新
                accountAppDoc.setBillOutputCount(accountAppDoc.getBillOutputCount() + 1);

                try {
                    repositoryUtil.update(db, accountAppDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                    throw new BusinessException(messages);
                }

                // 帳票出力用データを戻る
                // 帳票出力用データ初期値をセット
                accountAppInitVO.setIdentificationType("");
                accountAppInitVO.setLivingConditions("");
                accountAppInitVO.setLastName("");
                accountAppInitVO.setFirstName("");
                accountAppInitVO.setKanaLastName("");
                accountAppInitVO.setKanaFirstName("");
                accountAppInitVO.setBirthday("");
                accountAppInitVO.setSex("");
                accountAppInitVO.setPostCode("");
                accountAppInitVO.setAddress1("");
                accountAppInitVO.setAddress2("");
                accountAppInitVO.setAddress3("");
                accountAppInitVO.setAddress4("");
                accountAppInitVO.setKanaAddress("");
                accountAppInitVO.setTeleNumber("");
                accountAppInitVO.setPhoneNumber("");
                accountAppInitVO.setWorkTeleNumber("");
                accountAppInitVO.setWorkName("");
                accountAppInitVO.setCardType("");
                accountAppInitVO.setOtherTradingPurposes("");
                accountAppInitVO.setOtherOccupations("");
                accountAppInitVO.setHoldAccount("");
                accountAppInitVO.setHoldAccountBank("");
                accountAppInitVO.setHoldAccountNumber("");
                accountAppInitVO.setAccountDeposit(1);
                accountAppInitVO.setDirectServicesCardPassword("");
                accountAppInitVO.setDirectServicesContract("");
                accountAppInitVO.setSecurityPassword("");
                accountAppInitVO.setApplicationDate("");
                accountAppInitVO.setChannel("S");
                accountAppInitVO.setEmail("");
                accountAppInitVO.setHoldAccountBankNumber("");
                accountAppInitVO.setDirectServicesContractBankNumber("");
                accountAppInitVO.setDirectServicesContractAccountNumber("");
                accountAppInitVO.setDirectServicesContractBank("");
                accountAppInitVO.setAccountAppMotive("");
                accountAppInitVO.setKnowProcess("");
                accountAppInitVO.setStoreCode("");
                accountAppInitVO.setIdentificationImage("");

                // 帳票出力用データをセット
                accountAppInitVO.set_id(accountAppDoc.get_id());
                accountAppInitVO.set_rev(accountAppDoc.get_rev());

                // 受付番号
                accountAppInitVO.setAccountAppSeq(accountAppDoc.getAccountAppSeq());

                // 本人確認書類
                String idType = accountAppDoc.getIdentificationType();
                if (idType.equals("A1")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_1);
                }
                if (idType.equals("A2")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_2);
                }
                if (idType.equals("A3")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_3);
                }
                if (idType.equals("A4")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_4);
                }

                // 生活状況確認書類
                String livinType = accountAppDoc.getLivingConditions();
                if (livinType.equals("B1")) {
                    accountAppInitVO.setLivingConditions(Constants.LIVINGCONDITIONS_1);
                }
                if (livinType.equals("B2")) {
                    accountAppInitVO.setLivingConditions(Constants.LIVINGCONDITIONS_2);
                }

                // 姓
                accountAppInitVO.setLastName(encryptorUtil.decrypt(accountAppDoc.getLastName()));
                // 名
                accountAppInitVO.setFirstName(encryptorUtil.decrypt(accountAppDoc.getFirstName()));
                // 姓カナ
                accountAppInitVO.setKanaLastName(encryptorUtil.decrypt(accountAppDoc.getKanaLastName()));
                // 名カナ
                accountAppInitVO.setKanaFirstName(encryptorUtil.decrypt(accountAppDoc.getKanaFirstName()));
                // 生年月日
                accountAppInitVO.setBirthday(encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(0, 4)
                        + Constants.MARK4 + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(4, 6)
                        + Constants.MARK4 + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(6));
                accountAppInitVO.setAge(encryptorUtil.decrypt(accountAppDoc.getAge()));

                // 性別
                switch (accountAppDoc.getSex()) {
                case 0:
                    accountAppInitVO.setSex("1");
                    break;
                case 1:
                    accountAppInitVO.setSex("2");
                }

                // 郵便番号
                accountAppInitVO.setPostCode(encryptorUtil.decrypt(accountAppDoc.getPostCode()).substring(0, 3)
                        + Constants.MARK3 + encryptorUtil.decrypt(accountAppDoc.getPostCode()).substring(3));
                // 都
                accountAppInitVO.setAddress1(encryptorUtil.decrypt(accountAppDoc.getAddress1()));
                // 道府
                accountAppInitVO.setAddress2(encryptorUtil.decrypt(accountAppDoc.getAddress2()));
                // 県
                accountAppInitVO.setAddress3(encryptorUtil.decrypt(accountAppDoc.getAddress3()));
                // 市区町村以下
                accountAppInitVO.setAddress4(encryptorUtil.decrypt(accountAppDoc.getAddress4()));
                // 市区町村以下（カナ）
                accountAppInitVO.setKanaAddress(encryptorUtil.decrypt(accountAppDoc.getKanaAddress()));

                // 自宅電話番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getTeleNumber()))) {
                    accountAppInitVO.setTeleNumber(
                            PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getTeleNumber())));
                }

                // 携帯電話番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber()))) {
                    accountAppInitVO.setPhoneNumber(
                            PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber())));
                }

                // 勤務先電話番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber()))) {
                    accountAppInitVO.setWorkTeleNumber(
                            PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber())));
                }

                // 勤務先名
                if (encryptorUtil.decrypt(accountAppDoc.getWorkName()).length() > 80) {
                    accountAppInitVO.setWorkName(encryptorUtil.decrypt(accountAppDoc.getWorkName()).substring(0, 80));
                } else {
                    accountAppInitVO.setWorkName(encryptorUtil.decrypt(accountAppDoc.getWorkName()));
                }

                // 発行するカード種類
                accountAppInitVO.setCardType(accountAppDoc.getCardType());
                // 利用しないサービス
                accountAppInitVO.setNoApplicationService(accountAppDoc.getNoApplicationService());
                // 取引目的
                accountAppInitVO.setTradingPurposes(accountAppDoc.getTradingPurposes());
                // その他取引目的
                accountAppInitVO
                        .setOtherTradingPurposes(encryptorUtil.decrypt(accountAppDoc.getOtherTradingPurposes()));

                // 職業
                List<String> occupation = accountAppDoc.getOccupation();
                Collections.sort(occupation);
                accountAppInitVO.setOccupation(occupation);
                accountAppInitVO.setOtherOccupations(encryptorUtil.decrypt(accountAppDoc.getOtherOccupations()));
                accountAppInitVO.setHoldAccount(accountAppDoc.getHoldAccount());
                accountAppInitVO.setHoldAccountBank(encryptorUtil.decrypt(accountAppDoc.getHoldAccountBank()));

                // 既に口座をお持ちの方：口座番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getHoldAccountNumber()))) {
                    accountAppInitVO.setHoldAccountNumber(encryptorUtil.decrypt(accountAppDoc.getHoldAccountNumber()));
                }

                // ダイレクトバンキングカード暗証番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getDirectServicesCardPassword()))
                        && encryptorUtil.decrypt(accountAppDoc.getDirectServicesCardPassword()).length() == 4) {
                    String directServicesCardPassword1 = encryptorUtil
                            .decrypt(accountAppDoc.getDirectServicesCardPassword());
                    String directServicesCardPassword2 = String
                            .valueOf((Integer.parseInt(directServicesCardPassword1.substring(1, 2)) + 1) % 10)
                            + String.valueOf((Integer.parseInt(directServicesCardPassword1.substring(2, 3)) + 4) % 10)
                            + String.valueOf((Integer.parseInt(directServicesCardPassword1.substring(3)) + 6) % 10)
                            + String.valueOf((Integer.parseInt(directServicesCardPassword1.substring(0, 1)) + 8) % 10);
                    accountAppInitVO.setDirectServicesCardPassword(directServicesCardPassword2);
                }

                // ダイレクトバンキングサービスのご契約
                accountAppInitVO.setDirectServicesContract(accountAppDoc.getDirectServicesContract());

                // ダイレクトバンキングサービスのご契約：口座番号
                if (Utils.isNotNullAndEmpty(
                        encryptorUtil.decrypt(accountAppDoc.getDirectServicesContractAccountNumber()))) {
                    accountAppInitVO.setDirectServicesContractAccountNumber(
                            encryptorUtil.decrypt(accountAppDoc.getDirectServicesContractAccountNumber()));
                }

                // ダイレクトバンキングサービスのご契約：店名
                accountAppInitVO.setDirectServicesContractBank(
                        encryptorUtil.decrypt(accountAppDoc.getDirectServicesContractBank()));
                // ひろぎんネット支店の口座開設の動機
                accountAppInitVO.setAccountAppMotive(accountAppDoc.getAccountAppMotive());
                // ひろぎんネット支店を知った経緯
                accountAppInitVO.setKnowProcess(accountAppDoc.getKnowProcess());
                // テレホンバンキングサービス_都度登録振込（１日あたり）
                accountAppInitVO.setTelOncePerDay(accountAppDoc.getTelOncePerDay());
                // テレホンバンキングサービス_都度登録振込（１回あたり）
                accountAppInitVO.setTelOncePerTrans(accountAppDoc.getTelOncePerTrans());
                // テレホンバンキングサービス_事前登録振込（１日あたり）
                accountAppInitVO.setTelRegisterPerDay(accountAppDoc.getTelRegisterPerDay());
                // テレホンバンキングサービス_事前登録振込（１回あたり）
                accountAppInitVO.setTelRegisterPerTrans(accountAppDoc.getTelRegisterPerTrans());
                // インターネットバンキングサービス_都度登録振込（１日あたり）
                accountAppInitVO.setInternetOncePerDay(accountAppDoc.getInternetOncePerDay());
                // インターネットバンキングサービス_都度登録振込（１回あたり）
                accountAppInitVO.setInternetOncePerTrans(accountAppDoc.getInternetOncePerTrans());
                // インターネットバンキングサービス_事前登録振込（１日あたり）
                accountAppInitVO.setInternetRegisterPerDay(accountAppDoc.getInternetRegisterPerDay());
                // インターネットバンキングサービス_事前登録振込（１回あたり）
                accountAppInitVO.setInternetRegisterPerTrans(accountAppDoc.getInternetRegisterPerTrans());
                // 暗証番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getSecurityPassword()))
                        && encryptorUtil.decrypt(accountAppDoc.getSecurityPassword()).length() == 4) {
                    String securityPassword1 = encryptorUtil.decrypt(accountAppDoc.getSecurityPassword());
                    String securityPassword2 = String
                            .valueOf((Integer.parseInt(securityPassword1.substring(3)) + 2) % 10)
                            + String.valueOf((Integer.parseInt(securityPassword1.substring(0, 1)) + 3) % 10)
                            + String.valueOf((Integer.parseInt(securityPassword1.substring(1, 2)) + 5) % 10)
                            + String.valueOf((Integer.parseInt(securityPassword1.substring(2, 3)) + 7) % 10);
                    accountAppInitVO.setSecurityPassword(securityPassword2);
                }
                // 申込日
                accountAppInitVO.setApplicationDate(accountAppDoc.getCreatedDate());

                /*
                 * // メールアドレス if(accountAppDoc.getUserType() != 0){ if
                 * (userInfoList != null && userInfoList.size() != 0) { for
                 * (UserInfoDoc doc : userInfoList) { if
                 * (doc.getUserId().equals(accountAppDoc.getUserId())) {
                 * accountAppInitVO.setEmail(encryptorUtil.decrypt(doc.getEmail(
                 * ))); break; } } } }
                 */

                // ストアコード
                if (accountAppDoc.getStoreCode().length() > 3) {
                    accountAppInitVO.setStoreCode(accountAppDoc.getStoreCode().substring(0, 3));
                } else {
                    accountAppInitVO.setStoreCode(accountAppDoc.getStoreCode());
                }

                // 本人確認書類画像Id
                accountAppInitVO.setIdentificationImage(accountAppDoc.getIdentificationImage());

                accountAppList.add(accountAppInitVO);
            }
        }

        accountAppListOutputButtonResVO.setAccountAppList(accountAppList);
        accoutAppListCsvLog = accoutAppListCsvLog.substring(0, accoutAppListCsvLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_7 + accoutAppListCsvLog + ")", db);
        return accountAppListOutputButtonResVO;
    }

    /**
     * 山形
     *
     */

    public AccountYamaGataAppListOutputButtonResVO yamagata(CloudantClient client, Database db, BaseResVO baseResVO)
            throws Exception {
        AccountYamaGataAppListOutputButtonResVO accountAppListOutputButtonResVO = new AccountYamaGataAppListOutputButtonResVO();
        AccountAppListOutputButtonReqVO accountAppListOutputButtonReqVO = new AccountAppListOutputButtonReqVO();
        accountAppListOutputButtonReqVO = (AccountAppListOutputButtonReqVO) baseResVO;
        List<AccountAppYamaGataInitVO> accountAppList = new ArrayList<>();
        String accoutAppListCsvLog = "(受付番号：";
        /*
         * List<UserInfoDoc> userInfoList = new ArrayList<>(); userInfoList =
         * repositoryUtil.getView(db,ApplicationKeys.
         * INSIGHTVIEW_USERINFOLIST_USERINFOLIST,UserInfoDoc.class);
         */

        List<AccountAppAgreedOperationDateDoc> agreeDateList = new ArrayList<>();
        for (int i = 0; i < accountAppListOutputButtonReqVO.getOutputList2().size(); i++) {
            if (accountAppListOutputButtonReqVO.getOutputList2().get(i).getSelect() == null) {
                continue;
            }
            YamagataUserInfoDoc userInfoDoc = new YamagataUserInfoDoc();
            // 一覧選択したデータ
            if (accountAppListOutputButtonReqVO.getOutputList2().get(i).getSelect() == true) {
                AccountYamaGataAppDoc accountAppDoc = new AccountYamaGataAppDoc();
                // PushrecordDoc pushrecordDoc = new PushrecordDoc();
                AccountAppYamaGataInitVO accountAppInitVO = new AccountAppYamaGataInitVO();

                try {
                    // 帳票出力用データを取得
                    accountAppDoc = (AccountYamaGataAppDoc) repositoryUtil.find(db,
                            accountAppListOutputButtonReqVO.getOutputList2().get(i).get_id(),
                            AccountYamaGataAppDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
                    throw new BusinessException(messages);
                }
                accoutAppListCsvLog = accoutAppListCsvLog + accountAppDoc.getAccountAppSeq() + "/";
                // 更新前に、「受付」の場合、ステータスを更新、配信履歴を追加、PUSH通知を送信する
                if ("1".equals(accountAppDoc.getStatus())) {

                    YamagataPushAdmit(client, accountAppDoc, "2");

                    accountAppDoc.setStatus("2");
                }

                // 口座開設DBに更新
                // // 帳票出力回数を更新
                // accountAppDoc.setBillOutputCount(accountAppDoc.getBillOutputCount()
                // + 1);

                try {
                    repositoryUtil.update(db, accountAppDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                    throw new BusinessException(messages);
                }

                // 帳票出力用データを戻る
                // 帳票出力用データ初期値をセット
                accountAppInitVO.setIdentificationType("");
                accountAppInitVO.setLivingConditions("");
                accountAppInitVO.setLastName("");
                accountAppInitVO.setFirstName("");
                accountAppInitVO.setKanaLastName("");
                accountAppInitVO.setKanaFirstName("");
                accountAppInitVO.setBirthday("");
                accountAppInitVO.setOrdinaryDepositEraKbn("");
                accountAppInitVO.setEraBirthday("");
                accountAppInitVO.setAge("");
                accountAppInitVO.setAccountType("");
                accountAppInitVO.setSexKbn("");
                accountAppInitVO.setPostCode("");
                accountAppInitVO.setPrefecturesCode("");
                accountAppInitVO.setAddress("");
                accountAppInitVO.setTeleNumber("");
                accountAppInitVO.setPhoneNumber("");
                accountAppInitVO.setWorkTeleNumber("");
                accountAppInitVO.setWorkName("");
                // accountAppInitVO.setAccountPurpose("");
                accountAppInitVO.setAccountPurposeOther("");
                accountAppInitVO.setJobKbnOther("");
                // accountAppInitVO.setHoldAccount("");
                // accountAppInitVO.setHoldAccountBank("");
                // accountAppInitVO.setHoldAccountNumber("");
                // accountAppInitVO.setAccountDeposit(1);
                // accountAppInitVO.setDirectServicesCardPassword("");
                // accountAppInitVO.setDirectServicesContract("");
                accountAppInitVO.setSecurityPassword("");
                accountAppInitVO.setBankbookDesignKbn("");
                accountAppInitVO.setCardDesingKbn("");
                accountAppInitVO.setSalesOfficeOption("");
                accountAppInitVO.setCreditlimit(0);
                accountAppInitVO.setOnlinePassword("");
                accountAppInitVO.setApplicationDate("");
                accountAppInitVO.setChannel("S");
                // accountAppInitVO.setEmail("");
                // accountAppInitVO.setHoldAccountBankNumber("");
                // accountAppInitVO.setDirectServicesContractBankNumber("");
                // accountAppInitVO.setDirectServicesContractAccountNumber("");
                // accountAppInitVO.setDirectServicesContractBank("");
                // accountAppInitVO.setAccountAppMotive("");
                accountAppInitVO.setKnowProcess("");
                accountAppInitVO.setKnowProcessOther("");
                // accountAppInitVO.setStoreCode("");
                accountAppInitVO.setIdentificationImage("");

                // 帳票出力用データをセット
                accountAppInitVO.set_id(accountAppDoc.get_id());
                accountAppInitVO.set_rev(accountAppDoc.get_rev());

                // 受付番号
                accountAppInitVO.setAccountAppSeq(accountAppDoc.getAccountAppSeq());

                // 本人確認書類
                String idType = accountAppDoc.getIdentificationType();
                if (idType.equals("A1")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_1);
                }
                if (idType.equals("A2")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_2);
                }
                if (idType.equals("A3")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_3);
                }
                if (idType.equals("A4")) {
                    accountAppInitVO.setIdentificationType(Constants.IDENTIFICATIONTYPE_4);
                }

                // 生活状況確認書類
                String livinType = accountAppDoc.getLivingConditions();
                if (livinType.equals("B1")) {
                    accountAppInitVO.setLivingConditions(Constants.LIVINGCONDITIONS_1);
                }
                if (livinType.equals("B2")) {
                    accountAppInitVO.setLivingConditions(Constants.LIVINGCONDITIONS_2);
                }

                // 姓
                accountAppInitVO.setLastName(encryptorUtil.decrypt(accountAppDoc.getLastName()));
                // 名
                accountAppInitVO.setFirstName(encryptorUtil.decrypt(accountAppDoc.getFirstName()));
                // 姓カナ
                accountAppInitVO.setKanaLastName(encryptorUtil.decrypt(accountAppDoc.getKanaLastName()));
                // 名カナ
                accountAppInitVO.setKanaFirstName(encryptorUtil.decrypt(accountAppDoc.getKanaFirstName()));
                // 生年月日
                accountAppInitVO.setBirthday(encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(0, 4)
                        + Constants.MARK4 + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(4, 6)
                        + Constants.MARK4 + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(6));
                // 和暦年号
                accountAppInitVO
                        .setOrdinaryDepositEraKbn(encryptorUtil.decrypt(accountAppDoc.getOrdinaryDepositEraKbn()));
                // 和暦生年月日
                String eraDate = "";
                String day = encryptorUtil.decrypt(accountAppDoc.getEraBirthday());
                eraDate = day.substring(0, 2) + "年" + day.substring(2, 4) + "月" + day.substring(4, 6) + "日";
                accountAppInitVO.setEraBirthday(eraDate);
                // 年齢
                accountAppInitVO.setAge(encryptorUtil.decrypt(accountAppDoc.getAge()));
                // 普通預金の種類
                accountAppInitVO.setAccountType(accountAppDoc.getAccountType());
                // 性別
                switch (accountAppDoc.getSexKbn()) {
                case 1:
                    accountAppInitVO.setSexKbn("1");
                    break;
                case 2:
                    accountAppInitVO.setSexKbn("2");
                }

                // 郵便番号
                accountAppInitVO.setPostCode(encryptorUtil.decrypt(accountAppDoc.getPostCode()).substring(0, 3)
                        + Constants.MARK3 + encryptorUtil.decrypt(accountAppDoc.getPostCode()).substring(3));
                // 都道府
                accountAppInitVO.setPrefecturesCode(accountAppDoc.getPrefecturesCode());
                // 市区町村・番地・アパート・マンション名
                accountAppInitVO.setAddress(encryptorUtil.decrypt(accountAppDoc.getAddress()));
                // 自宅電話番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getTeleNumber()))) {
                    accountAppInitVO.setTeleNumber(
                            PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getTeleNumber())));
                }

                // 携帯電話番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber()))) {
                    accountAppInitVO.setPhoneNumber(
                            PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber())));
                }

                // 勤務先電話番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber()))) {
                    accountAppInitVO.setWorkTeleNumber(
                            PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber())));
                }

                // 勤務先名
                if (encryptorUtil.decrypt(accountAppDoc.getWorkName()).length() > 80) {
                    accountAppInitVO.setWorkName(encryptorUtil.decrypt(accountAppDoc.getWorkName()).substring(0, 80));
                } else {
                    accountAppInitVO.setWorkName(encryptorUtil.decrypt(accountAppDoc.getWorkName()));
                }

                // 取引目的
                List<String> accountPurpose = accountAppDoc.getAccountPurpose();
                Collections.sort(accountPurpose);
                accountAppInitVO.setAccountPurpose(accountPurpose);
                // その他取引目的
                accountAppInitVO.setAccountPurposeOther(encryptorUtil.decrypt(accountAppDoc.getAccountPurposeOther()));

                // 職業
                List<String> jobKbn = accountAppDoc.getJobKbn();
                Collections.sort(jobKbn);
                accountAppInitVO.setJobKbn(jobKbn);
                accountAppInitVO.setJobKbnOther(encryptorUtil.decrypt(accountAppDoc.getJobKbnOther()));

                // ひろぎんネット支店を知った経緯
                accountAppInitVO.setKnowProcess(accountAppDoc.getKnowProcess());
                accountAppInitVO.setKnowProcessOther(encryptorUtil.decrypt(accountAppDoc.getKnowProcessOther()));
                // 暗証番号
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getSecurityPassword()))
                        && encryptorUtil.decrypt(accountAppDoc.getSecurityPassword()).length() == 4) {
                    String securityPassword1 = encryptorUtil.decrypt(accountAppDoc.getSecurityPassword());
                    String securityPassword2 = String
                            .valueOf((Integer.parseInt(securityPassword1.substring(3)) + 2) % 10)
                            + String.valueOf((Integer.parseInt(securityPassword1.substring(0, 1)) + 3) % 10)
                            + String.valueOf((Integer.parseInt(securityPassword1.substring(1, 2)) + 5) % 10)
                            + String.valueOf((Integer.parseInt(securityPassword1.substring(2, 3)) + 7) % 10);
                    accountAppInitVO.setSecurityPassword(securityPassword2);
                }

                // 通帳デザイン
                accountAppInitVO.setBankbookDesignKbn(accountAppDoc.getBankbookDesignKbn());
                // カードデザイン
                accountAppInitVO.setCardDesingKbn(accountAppDoc.getCardDesingKbn());
                // １日あたりの振込・払込限度額
                accountAppInitVO.setCreditlimit(accountAppDoc.getCreditlimit());
                // オンライン暗証番号
                accountAppInitVO.setOnlinePassword(encryptorUtil.decrypt(accountAppDoc.getOnlinePassword()));
                // 口座開設する店舗
                accountAppInitVO.setSalesOfficeOption(accountAppDoc.getSalesOfficeOption());
                // 申込日
                accountAppInitVO.setApplicationDate(accountAppDoc.getCreatedDate());

                // 本人確認書類画像Id
                accountAppInitVO.setIdentificationImage(accountAppDoc.getIdentificationImage());

                // 端末機種 OSバージョン アプリバージョン
                // 検索キーを整理する
                try {
                    userInfoDoc = repositoryUtil.find(db, accountAppDoc.getUserId(), YamagataUserInfoDoc.class);

                    accountAppInitVO.setModelName(userInfoDoc.getModelName());
                    accountAppInitVO.setOsVersion(userInfoDoc.getModelName());
                    accountAppInitVO.setAppVersion(userInfoDoc.getAppVersion());

                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                }
                // 口座開設申込同意日時
                try {
                    String queryKey = "accountAppSeq:\"" + accountAppDoc.getAccountAppSeq() + "\"";

                    agreeDateList = repositoryUtil.getIndex(db,
                            ApplicationKeys.INSIGHTINDEX_ACCOUNT_SEARCHBYACCOUNTAPPSEQ_AGREEDATE, queryKey,
                            AccountAppAgreedOperationDateDoc.class);
                    if (agreeDateList != null && agreeDateList.size() != 0) {
                        accountAppInitVO.setAgreedOperationDate(agreeDateList.get(0).getAgreedOperationDate());
                    }
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                }

                accountAppList.add(accountAppInitVO);
            }
        }

        accountAppListOutputButtonResVO.setAccountAppList(accountAppList);
        accoutAppListCsvLog = accoutAppListCsvLog.substring(0, accoutAppListCsvLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_7 + accoutAppListCsvLog + ")", db);
        return accountAppListOutputButtonResVO;
    }

    /**
     * YAMAGATA
     * 
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
    public void YamagataPushAdmit(CloudantClient client, AccountYamaGataAppDoc doc, String status) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);
        String deviceId = "";
        String userId = "";
        YamagataUserInfoDoc userInfoDoc = new YamagataUserInfoDoc();
        try {
            userInfoDoc = repositoryUtil.find(db, doc.getUserId(), YamagataUserInfoDoc.class);
        } catch (Exception e) {

        }
        if (userInfoDoc != null) {
            userId = doc.getUserId();
            // deviceId =
            // encryptorUtil.decrypt(userInfoDoc.getDeviceId());
            deviceId = userInfoDoc.getDeviceId();
        }
        String pushTile = "";
        String message = "";
        String receiptDate = dateFormatJP(doc.getReceiptDate());
        String applyInformation = Constants.YAMAGATA_RECEIPT_DATE + receiptDate + Constants.YAMAGATA_ACCOUNT_SEQ
                + doc.getAccountAppSeq() + Constants.YAMAGATA_APPLY_KIND;

        message = Constants.YAMAGATA_PUSH_MESSAGE_STATUS_2 + applyInformation + Constants.YAMAGATA_PUSH_MESSAGE_ABOUT;
        pushTile = Constants.YAMAGATA_PUSH_MESSAGE_TITLE_2;

        Date date = new Date();
        SimpleDateFormat dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String pushDate = dateFormart.format(date);
        List<String> list = new ArrayList<String>();
        String messageCode = "";
        String pushrecorDetaldId = "";
        String pushrecordId = "";
        int sum = 0;
        list.add(deviceId);
        String messageHtml = Constants.YAMAGATA_PUSH_MESSAGE_HTML_START + message
                + Constants.YAMAGATA_PUSH_MESSAGE_HTML_END;
        String queryKey = "accountAppSeq:\"" + doc.getAccountAppSeq() + "\"";
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
        yamagataStatusModifyDoc.setAccountAppSeq(doc.getAccountAppSeq());
        yamagataStatusModifyDoc.setStatus(status);
        yamagataStatusModifyDoc.setStatusModifyDate(pushDate);
        yamagataStatusModifyDoc.setStatusModifyDateForSort(date.getTime());
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
            repositoryUtil.save(db, yamagataStatusModifyDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
            throw new BusinessException(messages);
        }
    }

    /**
     * TacsflagValueメソッド。
     * 
     * @param tacsflag
     *            TACSフラグ
     * @return date TACSフラグ値
     * @throws Exception
     */
    public String TacsflagValue(String tacsflag) {
        String tacsflagValue = "";
        switch (tacsflag) {
        case "0":
            tacsflagValue = Constants.TACSFLAGVALUE_1;
            break;
        case "1":
            tacsflagValue = Constants.TACSFLAGVALUE_2;
            break;
        case "2":
            tacsflagValue = Constants.TACSFLAGVALUE_3;
            break;
        case "3":
            tacsflagValue = Constants.TACSFLAGVALUE_4;
            break;
        case "4":
            tacsflagValue = Constants.TACSFLAGVALUE_5;
            break;
        case "6":
            tacsflagValue = Constants.TACSFLAGVALUE_10;
            break;
        case "7":
            tacsflagValue = Constants.TACSFLAGVALUE_6;
            break;
        case "8":
            tacsflagValue = Constants.TACSFLAGVALUE_7;
            break;
        case "9":
            tacsflagValue = Constants.TACSFLAGVALUE_8;
            break;
        case "S":
            tacsflagValue = Constants.TACSFLAGVALUE_9;
        }

        return tacsflagValue;
    }

    /**
     * 広島
     * 
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
    public PushrecordDoc pushNotifications(CloudantClient client, AccountAppDoc doc, String status) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);

        PushrecordDoc pushrecordDoc = new PushrecordDoc();
        // 配信履歴DBに追加
        // システム日時を取得
        Date dateTime = new Date();
        // 配信日付変数定義
        String pushDate = "";
        // 作成日付変数定義
        String createDate = "";

        // 配信日付設定
        SimpleDateFormat dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT_ASS);
        pushDate = dateFormart.format(dateTime);
        // 作成日付設定
        dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        createDate = dateFormart.format(dateTime);
        dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT);
        String saveDate = dateFormart.format(dateTime);
        String sortTime = Utils.timeFormatConvert(Constants.DATE_FORMAT_ASS, Constants.DATE_FORMAT_SSS, pushDate);
        pushrecordDoc.setDocType(Constants.DOCTYPE_1);

        List<String> deviceTokenIdList = new ArrayList<String>();

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
        message = Constants.PUSH_MESSAGE_STATUS_2 + applyInformation + Constants.PUSH_MESSAGE_ABOUT;

        // 臨時ユーザの場合、単一アプリを送信する
        if (doc.getUserType() == 0) {
            // PUSH通知を送信する
            List<String> list = new ArrayList<String>();

            list.add(encryptorUtil.decrypt(doc.getDeviceTokenId()));

            List<String> tokenId = new ArrayList<String>();
            tokenId.add(doc.getDeviceTokenId());
            if (list != null && list.size() > 0) {
                if (list.size() == 1 && "".equals(list.get(0))) {
                    list = new ArrayList<String>();
                }
            }
            if (list.isEmpty() || list.size() == 0) {
                pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
                pushrecordDoc.setUserId(doc.getUserId());
                pushrecordDoc.setSaveDate(saveDate);
                pushrecordDoc.setPushContent("");
                pushrecordDoc.setPushStatus("4");
                pushrecordDoc.setStatus(status);
                pushrecordDoc.setPushDate("");
                pushrecordDoc.setSortTime("");
                pushrecordDoc.setDeviceTokenId(tokenId);
                pushrecordDoc.setCreatedDate(doc.getCreatedDate());
                try {
                    repositoryUtil.save(db, pushrecordDoc);
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
                pushrecordDoc.setOpenDate("");
                pushrecordDoc.setSaveDate(saveDate);
                pushrecordDoc.setPushContent(message);
                pushrecordDoc.setStatus(status);
                if (Constants.RETURN_OK.equals(messageCode)) {
                    // 未開封
                    pushrecordDoc.setPushStatus("1");
                } else {
                    // 配信失敗
                    pushrecordDoc.setPushStatus("3");
                    pushrecordDoc.setPushDate("");
                    pushrecordDoc.setSortTime("");

                }
                try {
                    pushrecordDoc.setCreatedDate(createDate);
                    pushrecordDoc.setUpdatedDate(createDate);
                    repositoryUtil.savePushrecord(db, pushrecordDoc);
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
            // 検索キーを整理する
            String queryKey = "userId:\"" + doc.getUserId() + "\"";

            userInfoDoc = repositoryUtil.getIndex(db, ApplicationKeys.INSIGHTINDEX_ACCOUNTMNT_SEARCHBYUSERID_USERINFO,
                    queryKey, UserInfoDoc.class);
            if (userInfoDoc != null && userInfoDoc.size() != 0) {
                deviceTokenIdList = userInfoDoc.get(0).getDeviceTokenIdList();
            }

            if (deviceTokenIdList.size() == 0 || deviceTokenIdList.isEmpty()) {
                pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
                pushrecordDoc.setUserId(doc.getUserId());
                pushrecordDoc.setSaveDate(saveDate);
                pushrecordDoc.setPushContent("");
                pushrecordDoc.setPushStatus("4");
                pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                pushrecordDoc.setStatus(status);
                repositoryUtil.save(db, pushrecordDoc);
            } else {

                // PUSH通知を送信する
                List<String> list = new ArrayList<String>();
                for (int count = 0; count < deviceTokenIdList.size(); count++) {
                    list.add(encryptorUtil.decrypt(deviceTokenIdList.get(count)));
                }

                String messageCode = pushNotifications.sendMessage(message, sum, list);

                pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                pushrecordDoc.setAccountAppSeq(doc.getAccountAppSeq());
                pushrecordDoc.setUserId(doc.getUserId());
                pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                pushrecordDoc.setPushDate(pushDate);
                pushrecordDoc.setSortTime(sortTime);
                pushrecordDoc.setOpenDate("");
                pushrecordDoc.setSaveDate(saveDate);
                pushrecordDoc.setPushContent(message);
                pushrecordDoc.setStatus(status);
                if (messageCode.equals(Constants.RETURN_OK)) {
                    // 未開封
                    pushrecordDoc.setPushStatus("1");
                } else {
                    // 配信失敗
                    pushrecordDoc.setPushStatus("3");
                }
                pushrecordDoc.setCreatedDate(createDate);
                pushrecordDoc.setUpdatedDate(createDate);
                repositoryUtil.savePushrecord(db, pushrecordDoc);

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

    /**
     * 和暦にフォーマットするメソッド。
     * 
     * @param inputdDate
     *            処理前日付
     * @return date 処理後和歴日付
     * @throws ParseException
     * @throws Exception
     */
    public String japaneseCalendar(String inputDate) throws ParseException {

        String dateOutput = "";
        if (Utils.isNotNullAndEmpty(inputDate) && inputDate.length() > 7) {
            dateOutput = inputDate.substring(0, 4) + "-" + inputDate.substring(4, 6) + "-" + inputDate.substring(6, 8);
        } else {
            return "";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Locale locale = new Locale("ja", "JP", "JP");
        DateFormat japaneseFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);

        return japaneseFormat.format(formatter.parse(dateOutput));
    }

}