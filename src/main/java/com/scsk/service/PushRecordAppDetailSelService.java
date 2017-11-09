package com.scsk.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.request.vo.StatusModifyReqVO;
import com.scsk.request.vo.YamagataStatusModifyReqVO;
import com.scsk.response.vo.AccountApp114DetailResVO;
import com.scsk.response.vo.AccountAppDetailResVO;
import com.scsk.response.vo.AccountAppYamaGataDetailResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.response.vo.StatusModifyResVO;
import com.scsk.response.vo.YamagataStatusModifyResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PhoneNumberUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.AccountAppPushListVO;

@Service
public class PushRecordAppDetailSelService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;
    @Autowired
    private YamagataStatusModifyService yamagataStatusModifyService;
    @Autowired
    private StatusModifyService statusModifyService;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(BaseResVO detailReqVO) throws Exception {

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
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {

        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        if ("0169".equals(bank_cd)) {
            AccountAppDetailResVO pushRecordAppDetailResVO = hirosima(db, baseResVO);
            return pushRecordAppDetailResVO;
        } else if ("0122".equals(bank_cd)) {
            AccountAppYamaGataDetailResVO pushRecordAppDetailResVO = yamagata(db, baseResVO);
            return pushRecordAppDetailResVO;
        } else if ("0173".equals(bank_cd)) {

            AccountApp114DetailResVO pushRecordAppDetailResVO = hyakujyushi(db, baseResVO);
            return pushRecordAppDetailResVO;

        }
        return null;
    }

    private AccountApp114DetailResVO hyakujyushi(Database db, BaseResVO baseResVO) throws Exception {

        AccountApp114DetailResVO applicationDetailResVO = new AccountApp114DetailResVO();
        AccountAppDetailReqVO reqVO = new AccountAppDetailReqVO();
        reqVO = (AccountAppDetailReqVO) baseResVO;
        String accountAppDetailLog = "(受付番号：";
        // 申込詳細情報取得
        Account114AppDoc applicationDoc = new Account114AppDoc();
        try {
            applicationDoc = (Account114AppDoc) repositoryUtil.find(db, reqVO.get_id(), Account114AppDoc.class);
            accountAppDetailLog = accountAppDetailLog + applicationDoc.getAccountAppSeq();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
            throw new BusinessException(messages);
        }

        // push通知履歴一覧を取得
        List<PushrecordDoc> pushrecordDocList = new ArrayList<>();

        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_5 + accountAppDetailLog + ")", db);
        // 取引目的{
        applicationDetailResVO.setAccountPurpose(applicationDoc.getAccountPurpose());
        // その他取引目的
        applicationDetailResVO.setAccountPurposeOther(encryptorUtil.decrypt(applicationDoc.getAccountPurposeOther()));
        // 職業
        applicationDetailResVO.setJobKbn(applicationDoc.getJobKbn());
        // 受付番号
        applicationDetailResVO.setAccountAppSeq(applicationDoc.getAccountAppSeq());
        // その他職業
        applicationDetailResVO.setJobKbnOther(encryptorUtil.decrypt(applicationDoc.getJobKbnOther()));
        String accountType = "";
        if ("20".equals(applicationDoc.getAccountType())) {
            accountType = "総合口座";
        } else if ("10".equals(applicationDoc.getAccountType())) {
            accountType = "普通口座";
        }
        applicationDetailResVO.setIpAddress(encryptorUtil.decrypt(applicationDoc.getIpAddress()));
        applicationDetailResVO.setAccountType(accountType);
        applicationDetailResVO.setAddress(encryptorUtil.decrypt(applicationDoc.getAddress()));
        applicationDetailResVO.setApplicationEndFlg(encryptorUtil.decrypt(applicationDoc.getApplicationEndFlg()));
        String applicationFlg = "";
        if ("0".equals(applicationDoc.getApplicationFlg())) {
            applicationFlg = "「仮申込み」をする";
        } else if ("1".equals(applicationDoc.getApplicationFlg())) {
            applicationFlg = "「仮申込み」をしない";
        }
        applicationDetailResVO.setApplicationFlg(applicationFlg);
        applicationDetailResVO.setApplicationReason(applicationDoc.getApplicationReason());
        String bankbookType = "";
        if ("1".equals(applicationDoc.getBankbookType())) {
            bankbookType = "PACK一般";
        } else if ("2".equals(applicationDoc.getBankbookType())) {
            bankbookType = "PACKキャラ";
        } else if ("3".equals(applicationDoc.getBankbookType())) {
            bankbookType = "普通預金";
        }
        applicationDetailResVO.setBankbookType(bankbookType);
        applicationDetailResVO.setBirthday(encryptorUtil.decrypt(applicationDoc.getBirthday()));
        String cardType = "";
        if ("1".equals(applicationDoc.getCardType())) {
            cardType = "一般";
        } else if ("2".equals(applicationDoc.getCardType())) {
            cardType = "キャラクター";
        }
        applicationDetailResVO.setCardType(cardType);
        applicationDetailResVO.setCreditlimit(encryptorUtil.decrypt(applicationDoc.getCreditlimit()));
        applicationDetailResVO.setDeviceTokenId(encryptorUtil.decrypt(applicationDoc.getDeviceTokenId()));
        applicationDetailResVO.setName(encryptorUtil.decrypt(applicationDoc.getFirstName())
                + encryptorUtil.decrypt(applicationDoc.getLastName()));

        applicationDetailResVO.setKanaAddress(encryptorUtil.decrypt(applicationDoc.getKanaAddress()));
        applicationDetailResVO.setKanaName(encryptorUtil.decrypt(applicationDoc.getKanaFirstName())
                + encryptorUtil.decrypt(applicationDoc.getKanaLastName()));
        applicationDetailResVO.setKanaWorkName(encryptorUtil.decrypt(applicationDoc.getKanaWorkName()));
        applicationDetailResVO.setKnowProcess(applicationDoc.getKnowProcess());
        applicationDetailResVO.setLicenseId(encryptorUtil.decrypt(applicationDoc.getLicenseId()));
        applicationDetailResVO.setOnlinePassword(encryptorUtil.decrypt(applicationDoc.getOnlinePassword()));
        applicationDetailResVO
                .setOnlinePasswordConfirm(encryptorUtil.decrypt(applicationDoc.getOnlinePasswordConfirm()));
        applicationDetailResVO.setPhoneNumber(encryptorUtil.decrypt(applicationDoc.getPhoneNumber()));
        applicationDetailResVO.setPostCode(encryptorUtil.decrypt(applicationDoc.getPostCode()));
        applicationDetailResVO.setPrefecturesCode(encryptorUtil.decrypt(applicationDoc.getPrefecturesCode()));
        applicationDetailResVO.setSalesOfficeOption(encryptorUtil.decrypt(applicationDoc.getSalesOfficeOption()));
        applicationDetailResVO.setSecurityPassword(encryptorUtil.decrypt(applicationDoc.getSecurityPassword()));
        applicationDetailResVO
                .setSecurityPasswordConfirm(encryptorUtil.decrypt(applicationDoc.getSecurityPasswordConfirm()));
        String sexKbn = "";
        if ("1".equals(applicationDoc.getSexKbn())) {
            sexKbn = "男";
        } else {
            sexKbn = "女";
        }
        applicationDetailResVO.setSexKbn(sexKbn);
        applicationDetailResVO.setTeleNumber(encryptorUtil.decrypt(applicationDoc.getTeleNumber()));
        applicationDetailResVO.setUserId(encryptorUtil.decrypt(applicationDoc.getUserId()));
        applicationDetailResVO.setUserType(encryptorUtil.decrypt(applicationDoc.getUserType()));
        applicationDetailResVO.setWorkAddress(encryptorUtil.decrypt(applicationDoc.getWorkAddress()));
        applicationDetailResVO.setWorkName(encryptorUtil.decrypt(applicationDoc.getWorkName()));
        String workNumberKbn = "";
        if ("10".equals(applicationDoc.getWorkNumberKbn())) {
            workNumberKbn = "連絡先";
        } else if ("20".equals(applicationDoc.getWorkNumberKbn())) {
            workNumberKbn = "勤務先";
        } else if ("30".equals(applicationDoc.getWorkNumberKbn())) {
            workNumberKbn = "本社";
        } else if ("40".equals(applicationDoc.getWorkNumberKbn())) {
            workNumberKbn = "携帯";
        }
        applicationDetailResVO.setWorkNumberKbn(workNumberKbn);
        applicationDetailResVO.setWorkPostCode(encryptorUtil.decrypt(applicationDoc.getWorkPostCode()));
        applicationDetailResVO.setWorkPrefecturesCode(encryptorUtil.decrypt(applicationDoc.getWorkPrefecturesCode()));
        applicationDetailResVO.setWorkTeleNumber(encryptorUtil.decrypt(applicationDoc.getWorkTeleNumber()));
        applicationDetailResVO.setKanaWorkName(encryptorUtil.decrypt(applicationDoc.getKanaWorkName()));
        applicationDetailResVO.setApplicationDate(applicationDoc.getApplicationDate());
        applicationDetailResVO.setReceiptDate(applicationDoc.getReceiptDate());
        applicationDetailResVO.setIdentificationImage(applicationDoc.getIdentificationImage());
        applicationDetailResVO.setIdentificationType(applicationDoc.getIdentificationType());
        applicationDetailResVO.setLivingConditions(applicationDoc.getLivingConditions());
        applicationDetailResVO.setLivingConditionsImage(applicationDoc.getLivingConditionsImage());
        applicationDetailResVO.setApplicationReasonOther(encryptorUtil.decrypt(applicationDoc.getApplicationReasonOther()));
        String userType = "";
        if ("0".equals(applicationDoc.getUserType())) {
            userType = "匿名";
        } else if ("1".equals(applicationDoc.getUserType())) {
            userType = "正式";
        }
        applicationDetailResVO.setUserType(userType);

        applicationDetailResVO.setStatus(applicationDoc.getStatus());
        // push通知履歴一覧を取得
        StatusModifyReqVO yamagataStatusModifyReqVO = new StatusModifyReqVO();
        yamagataStatusModifyReqVO.setAccountAppSeq(applicationDoc.getAccountAppSeq());
        yamagataStatusModifyReqVO.setUserId(applicationDoc.getUserId());
        StatusModifyResVO yamagataStatusModifyResVO = statusModifyService.execute(yamagataStatusModifyReqVO);
        applicationDetailResVO.setStatusModifyList(yamagataStatusModifyResVO.getStatusModifyListVO());

        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_5 + accountAppDetailLog + ")", db);

        return applicationDetailResVO;

    }

    /**
     * 
     * 広島
     * 
     */
    public AccountAppDetailResVO hirosima(Database db, BaseResVO baseResVO) throws Exception {
        AccountAppDetailResVO pushRecordAppDetailResVO = new AccountAppDetailResVO();
        AccountAppDetailReqVO accountAppDetailReqVO = new AccountAppDetailReqVO();
        accountAppDetailReqVO = (AccountAppDetailReqVO) baseResVO;
        String pushRecordApp = "";
        String pushRecordAppDetailLog = "(受付番号：";
        // 申込詳細情報取得
        AccountAppDoc applicationDoc = new AccountAppDoc();
        try {
            List<PushrecordDoc> list = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_PUSHRECORDLIST_PUSHRECORDLIST, PushrecordDoc.class);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get_id().equals(accountAppDetailReqVO.get_id())) {
                    pushRecordApp = list.get(i).getAccountAppSeq();
                    break;
                }
            }
            List<AccountAppDoc> accountAppList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST, AccountAppDoc.class);
            for (AccountAppDoc accountAppDoc : accountAppList) {
                if (accountAppDoc.getAccountAppSeq().equals(pushRecordApp)) {
                    applicationDoc = accountAppDoc;
                }
            }
            pushRecordAppDetailLog = pushRecordAppDetailLog + applicationDoc.getAccountAppSeq();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
            throw new BusinessException(messages);
        }

        // 戻り値を設定
        // 姓名
        String name = encryptorUtil.decrypt(applicationDoc.getLastName()) + " "
                + encryptorUtil.decrypt(applicationDoc.getFirstName());
        pushRecordAppDetailResVO.setName(name);
        // 姓名カナ
        String kanaName = encryptorUtil.decrypt(applicationDoc.getKanaLastName()) + " "
                + encryptorUtil.decrypt(applicationDoc.getKanaFirstName());
        pushRecordAppDetailResVO.setKanaName(kanaName);
        // 生年月日
        pushRecordAppDetailResVO.setBirthday(dateFormat(encryptorUtil.decrypt(applicationDoc.getBirthday())));
        // 満年齢
        pushRecordAppDetailResVO.setAge(encryptorUtil.decrypt(applicationDoc.getAge()));
        // 勤務先名
        pushRecordAppDetailResVO.setWorkName(encryptorUtil.decrypt(applicationDoc.getWorkName()));
        // 性別
        pushRecordAppDetailResVO.setSex(applicationDoc.getSex());
        // 郵便番号
        String postCode = encryptorUtil.decrypt(applicationDoc.getPostCode());
        if (Utils.isNotNullAndEmpty(postCode)) {
            String postCodeFormat = postCode.substring(0, 3) + "-" + postCode.substring(3);
            pushRecordAppDetailResVO.setPostCode(postCodeFormat);
        }

        // 都道府県
        pushRecordAppDetailResVO.setAddress1(encryptorUtil.decrypt(applicationDoc.getAddress1()));
        // 市区町村以下
        pushRecordAppDetailResVO.setAddress2(encryptorUtil.decrypt(applicationDoc.getAddress2()));
        // 市区町村以下（カナ）
        pushRecordAppDetailResVO.setKanaAddress(encryptorUtil.decrypt(applicationDoc.getKanaAddress()));
        // 自宅電話番号
        String tel = encryptorUtil.decrypt(applicationDoc.getTeleNumber());
        if (Utils.isNotNullAndEmpty(tel)) {
            pushRecordAppDetailResVO.setTeleNumber(PhoneNumberUtil.format(tel));
        }

        // 携帯電話番号
        String phoneNumber = encryptorUtil.decrypt(applicationDoc.getPhoneNumber());
        if (Utils.isNotNullAndEmpty(phoneNumber)) {
            pushRecordAppDetailResVO.setPhoneNumber(PhoneNumberUtil.format(phoneNumber));
        }

        // 勤務先電話番号
        String workTeleNumber = encryptorUtil.decrypt(applicationDoc.getWorkTeleNumber());
        if (Utils.isNotNullAndEmpty(workTeleNumber)) {
            pushRecordAppDetailResVO.setWorkTeleNumber(PhoneNumberUtil.format(workTeleNumber));
        }

        // 受付番号
        pushRecordAppDetailResVO.setAccountAppSeq(applicationDoc.getAccountAppSeq());
        // 申込日
        String appDate = applicationDoc.getApplicationDate();
        pushRecordAppDetailResVO.setApplicationDate(appDate);
        // 申込受付日付
        pushRecordAppDetailResVO.setReceiptDate(applicationDoc.getReceiptDate());
        // 発行するカード種類
        pushRecordAppDetailResVO.setCardType(applicationDoc.getCardType());
        // 利用しないサービス
        List<String> noApplicationService = applicationDoc.getNoApplicationService();
        Collections.sort(noApplicationService, Collections.reverseOrder());
        if (noApplicationService.isEmpty()) {
            List<String> noAppService = new ArrayList<String>();
            noAppService.add("4");
            pushRecordAppDetailResVO.setNoApplicationService(noAppService);
        } else {
            pushRecordAppDetailResVO.setNoApplicationService(noApplicationService);
        }

        // 商品指定
        List<String> goodsAppointed = new ArrayList<String>();
        if (applicationDoc.getGoodsAppointed().equals("1")) {
            goodsAppointed.add("1");
        }
        if (applicationDoc.getSpecificAccount().equals("1")) {
            goodsAppointed.add("2");
        }
        if (applicationDoc.getCardLoans().equals("1")) {
            goodsAppointed.add("3");
        } else if (applicationDoc.getCardLoans().equals("2")) {
            goodsAppointed.add("4");
        }
        pushRecordAppDetailResVO.setGoodsAppointed(goodsAppointed);

        // 取引目的{
        List<String> tradingPurposes = applicationDoc.getTradingPurposes();
        Collections.sort(tradingPurposes);
        pushRecordAppDetailResVO.setTradingPurposes(tradingPurposes);
        // その他取引目的
        pushRecordAppDetailResVO
                .setOtherTradingPurposes(encryptorUtil.decrypt(applicationDoc.getOtherTradingPurposes()));
        // 職業
        List<String> occupation = applicationDoc.getOccupation();
        Collections.sort(occupation);
        pushRecordAppDetailResVO.setOccupation(occupation);
        // その他職業
        pushRecordAppDetailResVO.setOtherOccupations(encryptorUtil.decrypt(applicationDoc.getOtherOccupations()));
        // 暗証番号
        pushRecordAppDetailResVO.setSecurityPassword(encryptorUtil.decrypt(applicationDoc.getSecurityPassword()));
        // テレホンバンキングサービス_事前登録振込（１回あたり）
        pushRecordAppDetailResVO.setTelRegisterPerTrans(applicationDoc.getTelRegisterPerTrans());
        // テレホンバンキングサービス_事前登録振込（１日あたり）
        pushRecordAppDetailResVO.setTelRegisterPerDay(applicationDoc.getTelRegisterPerDay());
        // テレホンバンキングサービス_都度登録振込（１回あたり）
        pushRecordAppDetailResVO.setTelOncePerTrans(applicationDoc.getTelOncePerTrans());
        // テレホンバンキングサービス_都度登録振込（１日あたり）
        pushRecordAppDetailResVO.setTelOncePerDay(applicationDoc.getTelOncePerDay());
        // インターネットバンキングサービス_事前登録振込（１回あたり）
        pushRecordAppDetailResVO.setInternetRegisterPerTrans(applicationDoc.getInternetRegisterPerTrans());
        // インターネットバンキングサービス_事前登録振込（１日あたり）
        pushRecordAppDetailResVO.setInternetRegisterPerDay(applicationDoc.getInternetRegisterPerDay());
        // インターネットバンキングサービス_都度登録振込（１回あたり）
        pushRecordAppDetailResVO.setInternetOncePerTrans(applicationDoc.getInternetOncePerTrans());
        // インターネットバンキングサービス_都度登録振込（１日あたり）
        pushRecordAppDetailResVO.setInternetOncePerDay(applicationDoc.getInternetOncePerDay());
        // モバイルバンキングサービス_事前登録振込（１回あたり）
        pushRecordAppDetailResVO.setMobileRegisterPerTrans(applicationDoc.getMobileRegisterPerTrans());
        // モバイルバンキングサービス_事前登録振込（１日あたり）
        pushRecordAppDetailResVO.setMobileRegisterPerDay(applicationDoc.getMobileRegisterPerDay());
        // モバイルバンキングサービス_都度登録振込（１回あたり）
        pushRecordAppDetailResVO.setMobileOncePerTrans(applicationDoc.getMobileOncePerTrans());
        // モバイルバンキングサービス_都度登録振込（１日あたり）
        pushRecordAppDetailResVO.setMobileOncePerDay(applicationDoc.getMobileOncePerDay());
        // 本人確認書類
        pushRecordAppDetailResVO.setIdentificationType(applicationDoc.getIdentificationType());
        // 本人確認書類画像
        pushRecordAppDetailResVO.setIdentificationImage(applicationDoc.getIdentificationImage());
        // 生活状況確認書類
        // applicationDetailResVO.setLivingConditions(applicationDoc
        // .getLivingConditions());
        // IPアドレス
        pushRecordAppDetailResVO.setIpAddress(encryptorUtil.decrypt(applicationDoc.getIpAddress()));
        // 取引種類
        pushRecordAppDetailResVO.setTransactionType(applicationDoc.getTransactionType());
        // 申込サービス
        pushRecordAppDetailResVO.setApplyService(applicationDoc.getApplyService());
        // 既に口座をお持ちの方
        pushRecordAppDetailResVO.setHoldAccount(applicationDoc.getHoldAccount());
        // 既に口座をお持ちの方：店名
        pushRecordAppDetailResVO.setHoldAccountBank(encryptorUtil.decrypt(applicationDoc.getHoldAccountBank()));
        // 既に口座をお持ちの方：口座番号
        pushRecordAppDetailResVO.setHoldAccountNumber(encryptorUtil.decrypt(applicationDoc.getHoldAccountNumber()));
        // ダイレクトバンキングサービスのご契約
        if (Utils.isNotNullAndEmpty(applicationDoc.getDirectServicesContract())) {
            pushRecordAppDetailResVO.setDirectServicesContract(applicationDoc.getDirectServicesContract());
        } else {
            pushRecordAppDetailResVO.setDirectServicesContract("0");
        }

        // ダイレクトバンキングサービスのご契約：店名
        pushRecordAppDetailResVO
                .setDirectServicesContractBank(encryptorUtil.decrypt(applicationDoc.getDirectServicesContractBank()));
        // ダイレクトバンキングサービスのご契約：口座番号
        pushRecordAppDetailResVO.setDirectServicesContractAccountNumber(
                encryptorUtil.decrypt(applicationDoc.getDirectServicesContractAccountNumber()));
        // ダイレクトバンキングカード暗証番号
        pushRecordAppDetailResVO
                .setDirectServicesCardPassword(encryptorUtil.decrypt(applicationDoc.getDirectServicesCardPassword()));
        // ひろぎんネット支店の口座開設の動機
        pushRecordAppDetailResVO.setAccountAppMotive(applicationDoc.getAccountAppMotive());
        // ひろぎんネット支店の口座開設のその他動機
        pushRecordAppDetailResVO
                .setAccountAppOtherMotive(encryptorUtil.decrypt(applicationDoc.getAccountAppOtherMotive()));
        // ひろぎんネット支店を知った経緯
        pushRecordAppDetailResVO.setKnowProcess(applicationDoc.getKnowProcess());
        // ひろぎんネット支店を知ったその他経緯
        pushRecordAppDetailResVO.setKnowOtherProcess(encryptorUtil.decrypt(applicationDoc.getKnowOtherProcess()));
        // ステータス
        pushRecordAppDetailResVO.setStatus(applicationDoc.getStatus());
        // push通知履歴一覧を取得
        List<PushrecordDoc> pushrecordDocList = new ArrayList<>();

        // push通知履歴
        MapReduce view = new MapReduce();
        view.setMap(
                "function (doc) {if(doc.docType && doc.docType === \"PUSHRECORD\" && doc.delFlg && doc.delFlg===\"0\" && doc.accountAppSeq===\""
                        + applicationDoc.getAccountAppSeq() + "\") {emit([doc.saveDate,doc.openDate], 1);}}");
        pushrecordDocList = repositoryUtil.queryByDynamicView(db, view, PushrecordDoc.class);

        List<AccountAppPushListVO> accountAppDetailPushList = new ArrayList<AccountAppPushListVO>();
        for (PushrecordDoc doc : pushrecordDocList) {
            for (String deviceTokenId : doc.getDeviceTokenId()) {
                AccountAppPushListVO pushVo = new AccountAppPushListVO();
                // Push開封状況
                if (doc.getPushStatus() != null && !doc.getPushStatus().isEmpty()) {
                    if ("3".equals(doc.getPushStatus())) {
                        pushVo.setPushStatus("5");
                    } else if ("4".equals(doc.getPushStatus())) {
                        pushVo.setPushStatus("5");
                    } else if ("5".equals(doc.getPushStatus())) {
                        pushVo.setPushStatus("5");
                    } else if ("6".equals(doc.getPushStatus())) {
                        pushVo.setPushStatus("5");
                    } else if ("7".equals(doc.getPushStatus())) {
                        pushVo.setPushStatus("5");
                    } else {
                        pushVo.setPushStatus(doc.getPushStatus());
                    }
                } else {
                    pushVo.setPushStatus("5");
                }
                pushVo.setDeviceTokenId(encryptorUtil.decrypt(deviceTokenId));
                // 配信日時
                if (doc.getPushDate() != null && !doc.getPushDate().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_ASS);
                    Date date = sdf.parse(doc.getPushDate());
                    SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
                    String date1 = sdf1.format(date);
                    pushVo.setPushDate(date1);
                } else {
                    pushVo.setPushDate("―");
                }
                // 開封日付
                pushVo.setOpenDate(dateFormat(doc.getOpenDate()));
                // 変更日時
                if (doc.getCreatedDate() != null && !doc.getCreatedDate().isEmpty()) {
                    pushVo.setCreateDate(doc.getCreatedDate());
                }
                // 配信状況
                if ("1".equals(doc.getStatus()) || "2".equals(doc.getStatus())) {
                    if (doc.getPushDate() == null || doc.getPushDate().isEmpty()) {
                        pushVo.setSendStatus("2");
                    }
                }
                if (!"1".equals(doc.getStatus()) && !"2".equals(doc.getStatus())) {
                    if ((doc.getPushDate() == null || doc.getPushDate().isEmpty()) && !"3".equals(doc.getPushStatus())
                            && !"4".equals(doc.getPushStatus()) && !"5".equals(doc.getPushStatus())
                            && !"6".equals(doc.getPushStatus()) && !"7".equals(doc.getPushStatus())) {
                        pushVo.setSendStatus("1");
                    }
                }
                if (doc.getPushDate() != null && !doc.getPushDate().isEmpty()) {
                    pushVo.setSendStatus("3");
                }
                if (!"1".equals(doc.getStatus()) && !"2".equals(doc.getStatus())) {
                    if (doc.getPushDate() == null || doc.getPushDate().isEmpty()) {
                        if ("3".equals(doc.getPushStatus())) {
                            pushVo.setSendStatus("4");
                        }
                        if ("4".equals(doc.getPushStatus())) {
                            pushVo.setSendStatus("5");
                        }
                        if ("5".equals(doc.getPushStatus())) {
                            pushVo.setSendStatus("6");
                        }
                        if ("6".equals(doc.getPushStatus())) {
                            pushVo.setSendStatus("7");
                        }
                        if ("7".equals(doc.getPushStatus())) {
                            pushVo.setSendStatus("8");
                        }
                    }
                }
                if ("2".equals(doc.getStatus())) {
                    if (doc.getPushDate() == null || doc.getPushDate().isEmpty()) {
                        if ("3".equals(doc.getPushStatus())) {
                            pushVo.setSendStatus("4");
                        }
                        if ("4".equals(doc.getPushStatus())) {
                            pushVo.setSendStatus("5");
                        }
                    }
                }

                // 申込処理ステータス
                pushVo.setStatus(doc.getStatus());
                accountAppDetailPushList.add(pushVo);
            }
        }

        actionLog.saveActionLog(Constants.ACTIONLOG_PUSHPRECORD_4 + pushRecordAppDetailLog + ")", db);
        pushRecordAppDetailResVO.setAccountAppDetailPushList(accountAppDetailPushList);
        return pushRecordAppDetailResVO;

    }

    /**
     * 
     * 山形
     * 
     */
    public AccountAppYamaGataDetailResVO yamagata(Database db, BaseResVO baseResVO) throws Exception {
        AccountAppYamaGataDetailResVO applicationDetailResVO = new AccountAppYamaGataDetailResVO();
        AccountAppDetailReqVO reqVO = new AccountAppDetailReqVO();
        reqVO = (AccountAppDetailReqVO) baseResVO;
        String accountAppDetailLog = "(受付番号：";
        // 申込詳細情報取得
        AccountYamaGataAppDoc applicationDoc = new AccountYamaGataAppDoc();
        try {
            applicationDoc = (AccountYamaGataAppDoc) repositoryUtil.find(db, reqVO.get_id(),
                    AccountYamaGataAppDoc.class);
            accountAppDetailLog = accountAppDetailLog + applicationDoc.getAccountAppSeq();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
            throw new BusinessException(messages);
        }

        // 戻り値を設定
        // 姓名
        String name = encryptorUtil.decrypt(applicationDoc.getLastName()) + " "
                + encryptorUtil.decrypt(applicationDoc.getFirstName());
        applicationDetailResVO.setName(name);
        // 姓名カナ
        String kanaName = encryptorUtil.decrypt(applicationDoc.getKanaLastName()) + " "
                + encryptorUtil.decrypt(applicationDoc.getKanaFirstName());
        applicationDetailResVO.setKanaName(kanaName);
        // 生年月日
        applicationDetailResVO.setBirthday(dateFormat(encryptorUtil.decrypt(applicationDoc.getBirthday())));
        // 和暦年号
        applicationDetailResVO
                .setOrdinaryDepositEraKbn(encryptorUtil.decrypt(applicationDoc.getOrdinaryDepositEraKbn()));
        // 和暦生年月日
        String eraDate = "";
        String day = encryptorUtil.decrypt(applicationDoc.getEraBirthday());
        eraDate = day.substring(0, 2) + "年" + day.substring(2, 4) + "月" + day.substring(4, 6) + "日";
        applicationDetailResVO.setEraBirthday(eraDate);
        // 年齢
        applicationDetailResVO.setAge(encryptorUtil.decrypt(applicationDoc.getAge()));
        // 普通預金の種類
        applicationDetailResVO.setAccountType(applicationDoc.getAccountType());
        // 勤務先名
        applicationDetailResVO.setWorkName(encryptorUtil.decrypt(applicationDoc.getWorkName()));
        // 性別
        applicationDetailResVO.setSexKbn(applicationDoc.getSexKbn());
        // 郵便番号
        String postCode = encryptorUtil.decrypt(applicationDoc.getPostCode());
        if (Utils.isNotNullAndEmpty(postCode)) {
            String postCodeFormat = postCode.substring(0, 3) + "-" + postCode.substring(3);
            applicationDetailResVO.setPostCode(postCodeFormat);
        }

        // 都道府県
        applicationDetailResVO.setPrefecturesCode(applicationDoc.getPrefecturesCode());
        // 市区町村以下
        applicationDetailResVO.setAddress(encryptorUtil.decrypt(applicationDoc.getAddress()));
        // 自宅電話番号
        String tel = encryptorUtil.decrypt(applicationDoc.getTeleNumber());
        if (Utils.isNotNullAndEmpty(tel)) {
            applicationDetailResVO.setTeleNumber(PhoneNumberUtil.format(tel));
        }

        // 携帯電話番号
        String phoneNumber = encryptorUtil.decrypt(applicationDoc.getPhoneNumber());
        if (Utils.isNotNullAndEmpty(phoneNumber)) {
            applicationDetailResVO.setPhoneNumber(PhoneNumberUtil.format(phoneNumber));
        }

        // 勤務先電話番号
        String workTeleNumber = encryptorUtil.decrypt(applicationDoc.getWorkTeleNumber());
        if (Utils.isNotNullAndEmpty(workTeleNumber)) {
            applicationDetailResVO.setWorkTeleNumber(PhoneNumberUtil.format(workTeleNumber));
        }

        // 受付番号
        applicationDetailResVO.setAccountAppSeq(applicationDoc.getAccountAppSeq());
        // 申込日
        String appDate = applicationDoc.getApplicationDate();
        applicationDetailResVO.setApplicationDate(appDate);
        // 申込受付日付
        applicationDetailResVO.setReceiptDate(applicationDoc.getReceiptDate());

        // 取引目的{
        List<String> accountPurpose = applicationDoc.getAccountPurpose();
        Collections.sort(accountPurpose);
        applicationDetailResVO.setAccountPurpose(accountPurpose);
        // その他取引目的
        applicationDetailResVO.setAccountPurposeOther(encryptorUtil.decrypt(applicationDoc.getAccountPurposeOther()));
        // 職業
        List<String> jobKbn = applicationDoc.getJobKbn();
        Collections.sort(jobKbn);
        applicationDetailResVO.setJobKbn(jobKbn);
        // その他職業
        applicationDetailResVO.setJobKbnOther(encryptorUtil.decrypt(applicationDoc.getJobKbnOther()));
        // 通帳デザイン
        applicationDetailResVO.setBankbookDesignKbn(applicationDoc.getBankbookDesignKbn());
        // カードデザイン
        applicationDetailResVO.setCardDesingKbn(applicationDoc.getCardDesingKbn());
        // キャッシュカード暗証番号
        applicationDetailResVO.setSecurityPassword(encryptorUtil.decrypt(applicationDoc.getSecurityPassword()));
        // 口座開設する店舗
        applicationDetailResVO.setSalesOfficeOption(applicationDoc.getSalesOfficeOption());
        // １日あたりの振込・払込限度額
        DecimalFormat df = new DecimalFormat();
        df.applyPattern(",###");
        String creditlimit = df.format(applicationDoc.getCreditlimit()).toString() + "円";
        applicationDetailResVO.setCreditlimit(creditlimit);
        // オンライン暗証番号
        applicationDetailResVO.setOnlinePassword(applicationDoc.getOnlinePassword());
        // 本人確認書類
        applicationDetailResVO.setIdentificationType(applicationDoc.getIdentificationType());
        // 本人確認書類画像
        applicationDetailResVO.setIdentificationImage(applicationDoc.getIdentificationImage());
        // 生活状況確認書類
        // IPアドレス
        applicationDetailResVO.setIpAddress(encryptorUtil.decrypt(applicationDoc.getIpAddress()));
        // ひろぎんネット支店を知った経緯
        applicationDetailResVO.setKnowProcess(applicationDoc.getKnowProcess());
        // ひろぎんネット支店を知ったその他経緯
        applicationDetailResVO.setKnowProcessOther(encryptorUtil.decrypt(applicationDoc.getKnowProcessOther()));
        // ステータス
        applicationDetailResVO.setStatus(applicationDoc.getStatus());

        // push通知履歴一覧を取得
        YamagataStatusModifyReqVO yamagataStatusModifyReqVO = new YamagataStatusModifyReqVO();
        yamagataStatusModifyReqVO.setAccountAppSeq(applicationDoc.getAccountAppSeq());
        yamagataStatusModifyReqVO.setUserId(applicationDoc.getUserId());
        YamagataStatusModifyResVO yamagataStatusModifyResVO = yamagataStatusModifyService
                .execute(yamagataStatusModifyReqVO);

        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_5 + accountAppDetailLog + ")", db);
        applicationDetailResVO.setYamagataStatusModifyList(yamagataStatusModifyResVO.getYamagataStatusModifyListVO());
        return applicationDetailResVO;
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

}
