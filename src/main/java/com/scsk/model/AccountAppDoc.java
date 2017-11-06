package com.scsk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 口座開設DOC
 */
public class AccountAppDoc extends UtilDoc {

    private String name;
    // ドキュメントタイプ
    private String docType = "";
    // ユーザーID
    private String userId = "";
    // ユーザータイプ
    private int userType = 0;
    // 端末ID
    private String deviceTokenId = "";
    // 免許証号
    private String licenseId = "";
    // 姓
    private String lastName = "";
    // 名
    private String firstName = "";
    // 姓名カナ
    private String kanaLastName = "";
    // 名カナ
    private String kanaFirstName = "";
    // 満年齢
    private String age = "";
    // 生年月日
    private String birthday = "";
    // 性别
    private int sex = 0;
    // 郵便番号
    private String postCode = "";
    // 都
    private String address1 = "";
    // 道府
    private String address2 = "";
    // 県
    private String address3 = "";
    // 市区町村以下
    private String address4 = "";
    // 市区町村以下（カナ）
    private String kanaAddress = "";
    // 自宅電話番号
    private String teleNumber = "";
    // 携帯電話番号
    private String phoneNumber = "";
    // 勤務先名
    private String workName = "";
    // 勤務先電話番号
    private String workTeleNumber = "";
    // 申込銀行
    private String applicationBank = "";
    // 受付番号
    private String accountAppSeq = "";
    // 申込日
    private String applicationDate = "";
    // 申込受付日付
    private String receiptDate = "";
    // 発行するカード種類
    private String cardType = "";
    // 利用しないサービス
    private List<String> noApplicationService = new ArrayList<String>();
    // 口座開設と同時にお申込みいただける商品指定
    private String goodsAppointed = "";
    // 特定口座について
    private String specificAccount = "";
    // カードローン
    private String cardLoans = "";
    // 取引目的
    private List<String> tradingPurposes = new ArrayList<String>();
    // その他取引目的
    private String otherTradingPurposes = "";
    // 職業
    private List<String> occupation = new ArrayList<String>();
    // その他職業
    private String otherOccupations = "";
    // 暗証番号
    private String securityPassword = "";
    // テレホンバンキングサービス_事前登録振込（１回あたり）
    private int telRegisterPerTrans = 0;
    // テレホンバンキングサービス_事前登録振込（１日あたり）
    private int telRegisterPerDay = 0;
    // テレホンバンキングサービス_都度登録振込（１回あたり）
    private int telOncePerTrans = 0;
    // テレホンバンキングサービス_都度登録振込（１日あたり）
    private int telOncePerDay = 0;
    // インターネットバンキングサービス_事前登録振込（１回あたり）
    private int internetRegisterPerTrans = 0;
    // インターネットバンキングサービス_事前登録振込（１日あたり）
    private int internetRegisterPerDay = 0;
    // インターネットバンキングサービス_都度登録振込（１回あたり）
    private int internetOncePerTrans = 0;
    // インターネットバンキングサービス_都度登録振込（１日あたり）
    private int internetOncePerDay = 0;
    // モバイルバンキングサービス_事前登録振込（１回あたり）
    private int mobileRegisterPerTrans = 0;
    // モバイルバンキングサービス_事前登録振込（１日あたり）
    private int mobileRegisterPerDay = 0;
    // モバイルバンキングサービス_都度登録振込（１回あたり）
    private int mobileOncePerTrans = 0;
    // モバイルバンキングサービス_都度登録振込（１日あたり）
    private int mobileOncePerDay = 0;
    // 本人確認書類
    private String identificationType = "";
    // 本人確認書類画像
    private String identificationImage = "";
    // 生活状況確認書類
    private String livingConditions = "";
    // 生活状況確認書類画像
    private String livingConditionsImage = "";
    // IPアドレス
    private String ipAddress = "";
    // 取引種類
    private String transactionType = "";
    // 申込サービス
    private List<String> applyService = new ArrayList<String>();
    // 既に口座をお持ちの方
    private String holdAccount = "";
    // 既に口座をお持ちの方：店名
    private String holdAccountBank = "";
    // 既に口座をお持ちの方：口座番号
    private String holdAccountNumber = "";
    // ダイレクトバンキングサービスのご契約
    private String directServicesContract = "";
    // ダイレクトバンキングサービスのご契約：店名
    private String directServicesContractBank = "";
    // ダイレクトバンキングサービスのご契約：口座番号
    private String directServicesContractAccountNumber = "";
    // ダイレクトバンキングカード暗証番号
    private String directServicesCardPassword = "";
    // ひろぎんネット支店の口座開設の動機
    private String accountAppMotive = "";
    // ひろぎんネット支店の口座開設のその他動機
    private String accountAppOtherMotive = "";
    // ひろぎんネット支店を知った経緯
    private String knowProcess = "";
    // ひろぎんネット支店を知ったその他経緯
    private String knowOtherProcess = "";
    // ステータス
    private String status = "";
    // ストアコード
    private String storeCode = "";
    // 帳票出力回数
    private int billOutputCount = 0;
    //
    private String agreedOperationDate = "";
    // 国名コード
    private String IC_CountryCode = "";
    // 国名
    private String IC_CountryName = "";
    // 国脅威度
    private String IC_CountryThreat = "";
    // PS-IP
    private String IC_PSIP = "";
    // Proxy利用
    private String IC_Proxy = "";
    // 携帯電話（日本）
    private String IC_isMobile = "";
    // キャリア
    private String IC_Carrier = "";
    // 登録企業
    private String IC_CompanyName = "";
    // 登録企業ドメイン
    private String IC_CompanyDomain = "";
    // IP登録地
    private String IC_CompanyCity = "";
    // IP登録地と郵便番号間の直線距離
    private String IC_Distance = "";
    // IP脅威度
    private String IC_IpThreat = "";
    // 検索履歴
    private String IC_SearchHistory = "";
    // 自宅電話番号認証結果
    private String TC_Access1 = "";
    // 自宅電話番号検索結果
    private String TC_Result1 = "";
    // 自宅電話番号加入期間
    private String TC_Month1 = "";
    // 自宅電話番号移転番号
    private String TC_Movetel1 = "";
    // 自宅電話番号キャリア名
    private String TC_Carrier1 = "";
    // 自宅電話番号検索数
    private String TC_Count1 = "";
    // 自宅電話番号アテンション
    private String TC_Attention1 = "";
    // 自宅電話番号TACSフラグ
    private String TC_Tacsflag1 = "";
    // 自宅電話番号最新年
    private String TC_Latestyear1 = "";
    // 自宅電話番号最新月
    private String TC_Latestmonth1 = "";
    // TACSフラグ０１
    private String TC_F01_1 = "";
    // TACSフラグ０２
    private String TC_F02_1 = "";
    // TACSフラグ０３
    private String TC_F03_1 = "";
    // TACSフラグ０４
    private String TC_F04_1 = "";
    // TACSフラグ０５
    private String TC_F05_1 = "";
    // TACSフラグ０６
    private String TC_F06_1 = "";
    // TACSフラグ０７
    private String TC_F07_1 = "";
    // TACSフラグ０８
    private String TC_F08_1 = "";
    // TACSフラグ０９
    private String TC_F09_1 = "";
    // TACSフラグ１０
    private String TC_F10_1 = "";
    // TACSフラグ１１
    private String TC_F11_1 = "";
    // TACSフラグ１２
    private String TC_F12_1 = "";
    // TACSフラグ１３
    private String TC_F13_1 = "";
    // TACSフラグ１４
    private String TC_F14_1 = "";
    // TACSフラグ１５
    private String TC_F15_1 = "";
    // TACSフラグ１６
    private String TC_F16_1 = "";
    // TACSフラグ１７
    private String TC_F17_1 = "";
    // TACSフラグ１８
    private String TC_F18_1 = "";
    // TACSフラグ１９
    private String TC_F19_1 = "";
    // TACSフラグ２０
    private String TC_F20_1 = "";
    // TACSフラグ２１
    private String TC_F21_1 = "";
    // TACSフラグ２２
    private String TC_F22_1 = "";
    // TACSフラグ２３
    private String TC_F23_1 = "";
    // TACSフラグ２４
    private String TC_F24_1 = "";
    // 携帯電話番号認証結果
    private String TC_Access2 = "";
    // 携帯電話番号検索結果
    private String TC_Result2 = "";
    // 携帯電話番号加入期間
    private String TC_Month2 = "";
    // 携帯電話番号移転番号
    private String TC_Movetel2 = "";
    // 携帯電話番号キャリア名
    private String TC_Carrier2 = "";
    // 携帯電話番号検索数
    private String TC_Count2 = "";
    // 携帯電話番号アテンション
    private String TC_Attention2 = "";
    // 携帯電話番号TACSフラグ
    private String TC_Tacsflag2 = "";
    // 携帯電話番号最新年
    private String TC_Latestyear2 = "";
    // 携帯電話番号最新月
    private String TC_Latestmonth2 = "";
    // TACSフラグ０１
    private String TC_F01_2 = "";
    // TACSフラグ０２
    private String TC_F02_2 = "";
    // TACSフラグ０３
    private String TC_F03_2 = "";
    // TACSフラグ０４
    private String TC_F04_2 = "";
    // TACSフラグ０５
    private String TC_F05_2 = "";
    // TACSフラグ０６
    private String TC_F06_2 = "";
    // TACSフラグ０７
    private String TC_F07_2 = "";
    // TACSフラグ０８
    private String TC_F08_2 = "";
    // TACSフラグ０９
    private String TC_F09_2 = "";
    // TACSフラグ１０
    private String TC_F10_2 = "";
    // TACSフラグ１１
    private String TC_F11_2 = "";
    // TACSフラグ１２
    private String TC_F12_2 = "";
    // TACSフラグ１３
    private String TC_F13_2 = "";
    // TACSフラグ１４
    private String TC_F14_2 = "";
    // TACSフラグ１５
    private String TC_F15_2 = "";
    // TACSフラグ１６
    private String TC_F16_2 = "";
    // TACSフラグ１７
    private String TC_F17_2 = "";
    // TACSフラグ１８
    private String TC_F18_2 = "";
    // TACSフラグ１９
    private String TC_F19_2 = "";
    // TACSフラグ２０
    private String TC_F20_2 = "";
    // TACSフラグ２１
    private String TC_F21_2 = "";
    // TACSフラグ２２
    private String TC_F22_2 = "";
    // TACSフラグ２３
    private String TC_F23_2 = "";
    // TACSフラグ２４
    private String TC_F24_2 = "";

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getDeviceTokenId() {
        return deviceTokenId;
    }

    public void setDeviceTokenId(String deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getKanaLastName() {
        return kanaLastName;
    }

    public void setKanaLastName(String kanaLastName) {
        this.kanaLastName = kanaLastName;
    }

    public String getKanaFirstName() {
        return kanaFirstName;
    }

    public void setKanaFirstName(String kanaFirstName) {
        this.kanaFirstName = kanaFirstName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getKanaAddress() {
        return kanaAddress;
    }

    public void setKanaAddress(String kanaAddress) {
        this.kanaAddress = kanaAddress;
    }

    public String getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkTeleNumber() {
        return workTeleNumber;
    }

    public void setWorkTeleNumber(String workTeleNumber) {
        this.workTeleNumber = workTeleNumber;
    }

    public String getApplicationBank() {
        return applicationBank;
    }

    public void setApplicationBank(String applicationBank) {
        this.applicationBank = applicationBank;
    }

    public String getAccountAppSeq() {
        return accountAppSeq;
    }

    public void setAccountAppSeq(String accountAppSeq) {
        this.accountAppSeq = accountAppSeq;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public List<String> getNoApplicationService() {
        return noApplicationService;
    }

    public void setNoApplicationService(List<String> noApplicationService) {
        this.noApplicationService = noApplicationService;
    }

    public String getGoodsAppointed() {
        return goodsAppointed;
    }

    public void setGoodsAppointed(String goodsAppointed) {
        this.goodsAppointed = goodsAppointed;
    }

    public String getSpecificAccount() {
        return specificAccount;
    }

    public void setSpecificAccount(String specificAccount) {
        this.specificAccount = specificAccount;
    }

    public String getCardLoans() {
        return cardLoans;
    }

    public void setCardLoans(String cardLoans) {
        this.cardLoans = cardLoans;
    }

    public List<String> getTradingPurposes() {
        return tradingPurposes;
    }

    public void setTradingPurposes(List<String> tradingPurposes) {
        this.tradingPurposes = tradingPurposes;
    }

    public String getOtherTradingPurposes() {
        return otherTradingPurposes;
    }

    public void setOtherTradingPurposes(String otherTradingPurposes) {
        this.otherTradingPurposes = otherTradingPurposes;
    }

    public List<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<String> occupation) {
        this.occupation = occupation;
    }

    public String getOtherOccupations() {
        return otherOccupations;
    }

    public void setOtherOccupations(String otherOccupations) {
        this.otherOccupations = otherOccupations;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }

    public int getTelRegisterPerTrans() {
        return telRegisterPerTrans;
    }

    public void setTelRegisterPerTrans(int telRegisterPerTrans) {
        this.telRegisterPerTrans = telRegisterPerTrans;
    }

    public int getTelRegisterPerDay() {
        return telRegisterPerDay;
    }

    public void setTelRegisterPerDay(int telRegisterPerDay) {
        this.telRegisterPerDay = telRegisterPerDay;
    }

    public int getTelOncePerTrans() {
        return telOncePerTrans;
    }

    public void setTelOncePerTrans(int telOncePerTrans) {
        this.telOncePerTrans = telOncePerTrans;
    }

    public int getTelOncePerDay() {
        return telOncePerDay;
    }

    public void setTelOncePerDay(int telOncePerDay) {
        this.telOncePerDay = telOncePerDay;
    }

    public int getInternetRegisterPerTrans() {
        return internetRegisterPerTrans;
    }

    public void setInternetRegisterPerTrans(int internetRegisterPerTrans) {
        this.internetRegisterPerTrans = internetRegisterPerTrans;
    }

    public int getInternetRegisterPerDay() {
        return internetRegisterPerDay;
    }

    public void setInternetRegisterPerDay(int internetRegisterPerDay) {
        this.internetRegisterPerDay = internetRegisterPerDay;
    }

    public int getInternetOncePerTrans() {
        return internetOncePerTrans;
    }

    public void setInternetOncePerTrans(int internetOncePerTrans) {
        this.internetOncePerTrans = internetOncePerTrans;
    }

    public int getInternetOncePerDay() {
        return internetOncePerDay;
    }

    public void setInternetOncePerDay(int internetOncePerDay) {
        this.internetOncePerDay = internetOncePerDay;
    }

    public int getMobileRegisterPerTrans() {
        return mobileRegisterPerTrans;
    }

    public void setMobileRegisterPerTrans(int mobileRegisterPerTrans) {
        this.mobileRegisterPerTrans = mobileRegisterPerTrans;
    }

    public int getMobileRegisterPerDay() {
        return mobileRegisterPerDay;
    }

    public void setMobileRegisterPerDay(int mobileRegisterPerDay) {
        this.mobileRegisterPerDay = mobileRegisterPerDay;
    }

    public int getMobileOncePerTrans() {
        return mobileOncePerTrans;
    }

    public void setMobileOncePerTrans(int mobileOncePerTrans) {
        this.mobileOncePerTrans = mobileOncePerTrans;
    }

    public int getMobileOncePerDay() {
        return mobileOncePerDay;
    }

    public void setMobileOncePerDay(int mobileOncePerDay) {
        this.mobileOncePerDay = mobileOncePerDay;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationImage() {
        return identificationImage;
    }

    public void setIdentificationImage(String identificationImage) {
        this.identificationImage = identificationImage;
    }

    public String getLivingConditions() {
        return livingConditions;
    }

    public void setLivingConditions(String livingConditions) {
        this.livingConditions = livingConditions;
    }

    public String getLivingConditionsImage() {
        return livingConditionsImage;
    }

    public void setLivingConditionsImage(String livingConditionsImage) {
        this.livingConditionsImage = livingConditionsImage;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public List<String> getApplyService() {
        return applyService;
    }

    public void setApplyService(List<String> applyService) {
        this.applyService = applyService;
    }

    public String getHoldAccount() {
        return holdAccount;
    }

    public void setHoldAccount(String holdAccount) {
        this.holdAccount = holdAccount;
    }

    public String getHoldAccountBank() {
        return holdAccountBank;
    }

    public void setHoldAccountBank(String holdAccountBank) {
        this.holdAccountBank = holdAccountBank;
    }

    public String getHoldAccountNumber() {
        return holdAccountNumber;
    }

    public void setHoldAccountNumber(String holdAccountNumber) {
        this.holdAccountNumber = holdAccountNumber;
    }

    public String getDirectServicesContract() {
        return directServicesContract;
    }

    public void setDirectServicesContract(String directServicesContract) {
        this.directServicesContract = directServicesContract;
    }

    public String getDirectServicesContractBank() {
        return directServicesContractBank;
    }

    public void setDirectServicesContractBank(String directServicesContractBank) {
        this.directServicesContractBank = directServicesContractBank;
    }

    public String getDirectServicesContractAccountNumber() {
        return directServicesContractAccountNumber;
    }

    public void setDirectServicesContractAccountNumber(String directServicesContractAccountNumber) {
        this.directServicesContractAccountNumber = directServicesContractAccountNumber;
    }

    public String getDirectServicesCardPassword() {
        return directServicesCardPassword;
    }

    public void setDirectServicesCardPassword(String directServicesCardPassword) {
        this.directServicesCardPassword = directServicesCardPassword;
    }

    public String getAccountAppMotive() {
        return accountAppMotive;
    }

    public void setAccountAppMotive(String accountAppMotive) {
        this.accountAppMotive = accountAppMotive;
    }

    public String getAccountAppOtherMotive() {
        return accountAppOtherMotive;
    }

    public void setAccountAppOtherMotive(String accountAppOtherMotive) {
        this.accountAppOtherMotive = accountAppOtherMotive;
    }

    public String getKnowProcess() {
        return knowProcess;
    }

    public void setKnowProcess(String knowProcess) {
        this.knowProcess = knowProcess;
    }

    public String getKnowOtherProcess() {
        return knowOtherProcess;
    }

    public void setKnowOtherProcess(String knowOtherProcess) {
        this.knowOtherProcess = knowOtherProcess;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBillOutputCount() {
        return billOutputCount;
    }

    public void setBillOutputCount(int billOutputCount) {
        this.billOutputCount = billOutputCount;
    }

    public String getAgreedOperationDate() {
        return agreedOperationDate;
    }

    public void setAgreedOperationDate(String agreedOperationDate) {
        this.agreedOperationDate = agreedOperationDate;
    }

    public String getIC_CountryCode() {
        return IC_CountryCode;
    }

    public void setIC_CountryCode(String iC_CountryCode) {
        IC_CountryCode = iC_CountryCode;
    }

    public String getIC_CountryName() {
        return IC_CountryName;
    }

    public void setIC_CountryName(String iC_CountryName) {
        IC_CountryName = iC_CountryName;
    }

    public String getIC_CountryThreat() {
        return IC_CountryThreat;
    }

    public void setIC_CountryThreat(String iC_CountryThreat) {
        IC_CountryThreat = iC_CountryThreat;
    }

    public String getIC_PSIP() {
        return IC_PSIP;
    }

    public void setIC_PSIP(String iC_PSIP) {
        IC_PSIP = iC_PSIP;
    }

    public String getIC_Proxy() {
        return IC_Proxy;
    }

    public void setIC_Proxy(String iC_Proxy) {
        IC_Proxy = iC_Proxy;
    }

    public String getIC_isMobile() {
        return IC_isMobile;
    }

    public void setIC_isMobile(String iC_isMobile) {
        IC_isMobile = iC_isMobile;
    }

    public String getIC_Carrier() {
        return IC_Carrier;
    }

    public void setIC_Carrier(String iC_Carrier) {
        IC_Carrier = iC_Carrier;
    }

    public String getIC_CompanyName() {
        return IC_CompanyName;
    }

    public void setIC_CompanyName(String iC_CompanyName) {
        IC_CompanyName = iC_CompanyName;
    }

    public String getIC_CompanyDomain() {
        return IC_CompanyDomain;
    }

    public void setIC_CompanyDomain(String iC_CompanyDomain) {
        IC_CompanyDomain = iC_CompanyDomain;
    }

    public String getIC_CompanyCity() {
        return IC_CompanyCity;
    }

    public void setIC_CompanyCity(String iC_CompanyCity) {
        IC_CompanyCity = iC_CompanyCity;
    }

    public String getIC_Distance() {
        return IC_Distance;
    }

    public void setIC_Distance(String iC_Distance) {
        IC_Distance = iC_Distance;
    }

    public String getIC_IpThreat() {
        return IC_IpThreat;
    }

    public void setIC_IpThreat(String iC_IpThreat) {
        IC_IpThreat = iC_IpThreat;
    }

    public String getIC_SearchHistory() {
        return IC_SearchHistory;
    }

    public void setIC_SearchHistory(String iC_SearchHistory) {
        IC_SearchHistory = iC_SearchHistory;
    }

    public String getTC_Access1() {
        return TC_Access1;
    }

    public void setTC_Access1(String tC_Access1) {
        TC_Access1 = tC_Access1;
    }

    public String getTC_Result1() {
        return TC_Result1;
    }

    public void setTC_Result1(String tC_Result1) {
        TC_Result1 = tC_Result1;
    }

    public String getTC_Month1() {
        return TC_Month1;
    }

    public void setTC_Month1(String tC_Month1) {
        TC_Month1 = tC_Month1;
    }

    public String getTC_Movetel1() {
        return TC_Movetel1;
    }

    public void setTC_Movetel1(String tC_Movetel1) {
        TC_Movetel1 = tC_Movetel1;
    }

    public String getTC_Carrier1() {
        return TC_Carrier1;
    }

    public void setTC_Carrier1(String tC_Carrier1) {
        TC_Carrier1 = tC_Carrier1;
    }

    public String getTC_Count1() {
        return TC_Count1;
    }

    public void setTC_Count1(String tC_Count1) {
        TC_Count1 = tC_Count1;
    }

    public String getTC_Attention1() {
        return TC_Attention1;
    }

    public void setTC_Attention1(String tC_Attention1) {
        TC_Attention1 = tC_Attention1;
    }

    public String getTC_Tacsflag1() {
        return TC_Tacsflag1;
    }

    public void setTC_Tacsflag1(String tC_Tacsflag1) {
        TC_Tacsflag1 = tC_Tacsflag1;
    }

    public String getTC_Latestyear1() {
        return TC_Latestyear1;
    }

    public void setTC_Latestyear1(String tC_Latestyear1) {
        TC_Latestyear1 = tC_Latestyear1;
    }

    public String getTC_Latestmonth1() {
        return TC_Latestmonth1;
    }

    public void setTC_Latestmonth1(String tC_Latestmonth1) {
        TC_Latestmonth1 = tC_Latestmonth1;
    }

    public String getTC_F01_1() {
        return TC_F01_1;
    }

    public void setTC_F01_1(String tC_F01_1) {
        TC_F01_1 = tC_F01_1;
    }

    public String getTC_F02_1() {
        return TC_F02_1;
    }

    public void setTC_F02_1(String tC_F02_1) {
        TC_F02_1 = tC_F02_1;
    }

    public String getTC_F03_1() {
        return TC_F03_1;
    }

    public void setTC_F03_1(String tC_F03_1) {
        TC_F03_1 = tC_F03_1;
    }

    public String getTC_F04_1() {
        return TC_F04_1;
    }

    public void setTC_F04_1(String tC_F04_1) {
        TC_F04_1 = tC_F04_1;
    }

    public String getTC_F05_1() {
        return TC_F05_1;
    }

    public void setTC_F05_1(String tC_F05_1) {
        TC_F05_1 = tC_F05_1;
    }

    public String getTC_F06_1() {
        return TC_F06_1;
    }

    public void setTC_F06_1(String tC_F06_1) {
        TC_F06_1 = tC_F06_1;
    }

    public String getTC_F07_1() {
        return TC_F07_1;
    }

    public void setTC_F07_1(String tC_F07_1) {
        TC_F07_1 = tC_F07_1;
    }

    public String getTC_F08_1() {
        return TC_F08_1;
    }

    public void setTC_F08_1(String tC_F08_1) {
        TC_F08_1 = tC_F08_1;
    }

    public String getTC_F09_1() {
        return TC_F09_1;
    }

    public void setTC_F09_1(String tC_F09_1) {
        TC_F09_1 = tC_F09_1;
    }

    public String getTC_F10_1() {
        return TC_F10_1;
    }

    public void setTC_F10_1(String tC_F10_1) {
        TC_F10_1 = tC_F10_1;
    }

    public String getTC_F11_1() {
        return TC_F11_1;
    }

    public void setTC_F11_1(String tC_F11_1) {
        TC_F11_1 = tC_F11_1;
    }

    public String getTC_F12_1() {
        return TC_F12_1;
    }

    public void setTC_F12_1(String tC_F12_1) {
        TC_F12_1 = tC_F12_1;
    }

    public String getTC_F13_1() {
        return TC_F13_1;
    }

    public void setTC_F13_1(String tC_F13_1) {
        TC_F13_1 = tC_F13_1;
    }

    public String getTC_F14_1() {
        return TC_F14_1;
    }

    public void setTC_F14_1(String tC_F14_1) {
        TC_F14_1 = tC_F14_1;
    }

    public String getTC_F15_1() {
        return TC_F15_1;
    }

    public void setTC_F15_1(String tC_F15_1) {
        TC_F15_1 = tC_F15_1;
    }

    public String getTC_F16_1() {
        return TC_F16_1;
    }

    public void setTC_F16_1(String tC_F16_1) {
        TC_F16_1 = tC_F16_1;
    }

    public String getTC_F17_1() {
        return TC_F17_1;
    }

    public void setTC_F17_1(String tC_F17_1) {
        TC_F17_1 = tC_F17_1;
    }

    public String getTC_F18_1() {
        return TC_F18_1;
    }

    public void setTC_F18_1(String tC_F18_1) {
        TC_F18_1 = tC_F18_1;
    }

    public String getTC_F19_1() {
        return TC_F19_1;
    }

    public void setTC_F19_1(String tC_F19_1) {
        TC_F19_1 = tC_F19_1;
    }

    public String getTC_F20_1() {
        return TC_F20_1;
    }

    public void setTC_F20_1(String tC_F20_1) {
        TC_F20_1 = tC_F20_1;
    }

    public String getTC_F21_1() {
        return TC_F21_1;
    }

    public void setTC_F21_1(String tC_F21_1) {
        TC_F21_1 = tC_F21_1;
    }

    public String getTC_F22_1() {
        return TC_F22_1;
    }

    public void setTC_F22_1(String tC_F22_1) {
        TC_F22_1 = tC_F22_1;
    }

    public String getTC_F23_1() {
        return TC_F23_1;
    }

    public void setTC_F23_1(String tC_F23_1) {
        TC_F23_1 = tC_F23_1;
    }

    public String getTC_F24_1() {
        return TC_F24_1;
    }

    public void setTC_F24_1(String tC_F24_1) {
        TC_F24_1 = tC_F24_1;
    }

    public String getTC_Access2() {
        return TC_Access2;
    }

    public void setTC_Access2(String tC_Access2) {
        TC_Access2 = tC_Access2;
    }

    public String getTC_Result2() {
        return TC_Result2;
    }

    public void setTC_Result2(String tC_Result2) {
        TC_Result2 = tC_Result2;
    }

    public String getTC_Month2() {
        return TC_Month2;
    }

    public void setTC_Month2(String tC_Month2) {
        TC_Month2 = tC_Month2;
    }

    public String getTC_Movetel2() {
        return TC_Movetel2;
    }

    public void setTC_Movetel2(String tC_Movetel2) {
        TC_Movetel2 = tC_Movetel2;
    }

    public String getTC_Carrier2() {
        return TC_Carrier2;
    }

    public void setTC_Carrier2(String tC_Carrier2) {
        TC_Carrier2 = tC_Carrier2;
    }

    public String getTC_Count2() {
        return TC_Count2;
    }

    public void setTC_Count2(String tC_Count2) {
        TC_Count2 = tC_Count2;
    }

    public String getTC_Attention2() {
        return TC_Attention2;
    }

    public void setTC_Attention2(String tC_Attention2) {
        TC_Attention2 = tC_Attention2;
    }

    public String getTC_Tacsflag2() {
        return TC_Tacsflag2;
    }

    public void setTC_Tacsflag2(String tC_Tacsflag2) {
        TC_Tacsflag2 = tC_Tacsflag2;
    }

    public String getTC_Latestyear2() {
        return TC_Latestyear2;
    }

    public void setTC_Latestyear2(String tC_Latestyear2) {
        TC_Latestyear2 = tC_Latestyear2;
    }

    public String getTC_Latestmonth2() {
        return TC_Latestmonth2;
    }

    public void setTC_Latestmonth2(String tC_Latestmonth2) {
        TC_Latestmonth2 = tC_Latestmonth2;
    }

    public String getTC_F01_2() {
        return TC_F01_2;
    }

    public void setTC_F01_2(String tC_F01_2) {
        TC_F01_2 = tC_F01_2;
    }

    public String getTC_F02_2() {
        return TC_F02_2;
    }

    public void setTC_F02_2(String tC_F02_2) {
        TC_F02_2 = tC_F02_2;
    }

    public String getTC_F03_2() {
        return TC_F03_2;
    }

    public void setTC_F03_2(String tC_F03_2) {
        TC_F03_2 = tC_F03_2;
    }

    public String getTC_F04_2() {
        return TC_F04_2;
    }

    public void setTC_F04_2(String tC_F04_2) {
        TC_F04_2 = tC_F04_2;
    }

    public String getTC_F05_2() {
        return TC_F05_2;
    }

    public void setTC_F05_2(String tC_F05_2) {
        TC_F05_2 = tC_F05_2;
    }

    public String getTC_F06_2() {
        return TC_F06_2;
    }

    public void setTC_F06_2(String tC_F06_2) {
        TC_F06_2 = tC_F06_2;
    }

    public String getTC_F07_2() {
        return TC_F07_2;
    }

    public void setTC_F07_2(String tC_F07_2) {
        TC_F07_2 = tC_F07_2;
    }

    public String getTC_F08_2() {
        return TC_F08_2;
    }

    public void setTC_F08_2(String tC_F08_2) {
        TC_F08_2 = tC_F08_2;
    }

    public String getTC_F09_2() {
        return TC_F09_2;
    }

    public void setTC_F09_2(String tC_F09_2) {
        TC_F09_2 = tC_F09_2;
    }

    public String getTC_F10_2() {
        return TC_F10_2;
    }

    public void setTC_F10_2(String tC_F10_2) {
        TC_F10_2 = tC_F10_2;
    }

    public String getTC_F11_2() {
        return TC_F11_2;
    }

    public void setTC_F11_2(String tC_F11_2) {
        TC_F11_2 = tC_F11_2;
    }

    public String getTC_F12_2() {
        return TC_F12_2;
    }

    public void setTC_F12_2(String tC_F12_2) {
        TC_F12_2 = tC_F12_2;
    }

    public String getTC_F13_2() {
        return TC_F13_2;
    }

    public void setTC_F13_2(String tC_F13_2) {
        TC_F13_2 = tC_F13_2;
    }

    public String getTC_F14_2() {
        return TC_F14_2;
    }

    public void setTC_F14_2(String tC_F14_2) {
        TC_F14_2 = tC_F14_2;
    }

    public String getTC_F15_2() {
        return TC_F15_2;
    }

    public void setTC_F15_2(String tC_F15_2) {
        TC_F15_2 = tC_F15_2;
    }

    public String getTC_F16_2() {
        return TC_F16_2;
    }

    public void setTC_F16_2(String tC_F16_2) {
        TC_F16_2 = tC_F16_2;
    }

    public String getTC_F17_2() {
        return TC_F17_2;
    }

    public void setTC_F17_2(String tC_F17_2) {
        TC_F17_2 = tC_F17_2;
    }

    public String getTC_F18_2() {
        return TC_F18_2;
    }

    public void setTC_F18_2(String tC_F18_2) {
        TC_F18_2 = tC_F18_2;
    }

    public String getTC_F19_2() {
        return TC_F19_2;
    }

    public void setTC_F19_2(String tC_F19_2) {
        TC_F19_2 = tC_F19_2;
    }

    public String getTC_F20_2() {
        return TC_F20_2;
    }

    public void setTC_F20_2(String tC_F20_2) {
        TC_F20_2 = tC_F20_2;
    }

    public String getTC_F21_2() {
        return TC_F21_2;
    }

    public void setTC_F21_2(String tC_F21_2) {
        TC_F21_2 = tC_F21_2;
    }

    public String getTC_F22_2() {
        return TC_F22_2;
    }

    public void setTC_F22_2(String tC_F22_2) {
        TC_F22_2 = tC_F22_2;
    }

    public String getTC_F23_2() {
        return TC_F23_2;
    }

    public void setTC_F23_2(String tC_F23_2) {
        TC_F23_2 = tC_F23_2;
    }

    public String getTC_F24_2() {
        return TC_F24_2;
    }

    public void setTC_F24_2(String tC_F24_2) {
        TC_F24_2 = tC_F24_2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
