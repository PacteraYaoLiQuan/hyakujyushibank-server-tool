package com.scsk.response.vo;

import java.util.List;

import com.scsk.constants.Constants;
import com.scsk.vo.AccountAppPushListVO;
import com.scsk.vo.StatusModifyListVO;
import com.scsk.vo.TacsflagVO;

public class AccountApp114DetailResVO extends BaseResVO {
    private List<StatusModifyListVO> statusModifyList;
    private Boolean select;
    private String docType = Constants.ACCOUNT_APP;
    private String userId;
    private String userType;
    private String deviceTokenId;
    private String applicationEndFlg;
    private String applicationFlg;
    private String licenseIdR;
    private String lastNameR;
    private String firstNameR;
    private String birthdayR;
    private String licenseId;
    private String name;
    private String accountAppSeq;
    private String kanaName;
    // IPアドレス
    private String ipAddress;
    private String birthday;
    private String sexKbn;
    private String postCodeR;
    private String prefecturesCodeR;
    private String addressR;
    private String postCode;
    private String prefecturesCode;
    private String address;
    private String kanaAddress;
    private String teleNumber;
    private String phoneNumber;
    private String jobKbn;
    private String jobKbnOther;
    private String workName;
    private String kanaWorkName;
    private String workPostCode;
    private String workPrefecturesCode;
    private String workAddress;
    private String workTeleNumber;
    private String workNumberKbn;
    private String accountType;
    private String bankbookType;
    private String cardType;
    private String salesOfficeOption;
    private String accountPurpose;
    private String accountPurposeOther;
    private String securityPassword;
    private String securityPasswordConfirm;
    private String creditlimit;
    private String onlinePassword;
    private String onlinePasswordConfirm;
    private String knowProcess;
    private String applicationReason;
    private String applicationReasonOther;
    private String status;
    // 国名コード
    private String IC_CountryCode;
    // 国名
    private String IC_CountryName;
    // 国脅威度
    private String IC_CountryThreat;
    // PS-IP
    private String IC_PSIP;
    // Proxy利用
    private String IC_Proxy;
    // 携帯電話（日本）
    private String IC_isMobile;
    // 登録企業
    private String IC_CompanyName;
    // 登録企業ドメイン
    private String IC_CompanyDomain;
    // IP登録地
    private String IC_CompanyCity;
    // IP登録地と郵便番号間の直線距離
    private String IC_Distance;
    // IP脅威度
    private String IC_IpThreat;
    // 検索履歴
    private String IC_SearchHistory;
    // 自宅電話番号認証結果
    private String TC_Access1;
    // 自宅電話番号検索結果
    private String TC_Result1;
    // 自宅電話番号加入期間
    private String TC_Month1;
    // 自宅電話番号移転番号
    private String TC_Movetel1;
    // 自宅電話番号キャリア名
    private String TC_Carrier1;
    // 自宅電話番号検索数
    private String TC_Count1;
    // 自宅電話番号アテンション
    private String TC_Attention1;
    // 自宅電話番号TACSフラグ
    private String TC_Tacsflag1;
    // 自宅電話番号最新年
    private String TC_Latestyear1;
    // 自宅電話番号最新月
    private String TC_Latestmonth1;
    // push通知一覧表示用
    private List<AccountAppPushListVO> accountAppDetailPushList;
    // 携帯電話番号認証結果
    private String TC_Access2;
    // 携帯電話番号検索結果
    private String TC_Result2;
    // 携帯電話番号加入期間
    private String TC_Month2;
    // 携帯電話番号移転番号
    private String TC_Movetel2;
    // 携帯電話番号キャリア名
    private String TC_Carrier2;
    // 携帯電話番号検索数
    private String TC_Count2;
    // 携帯電話番号アテンション
    private String TC_Attention2;
    // 携帯電話番号TACSフラグ
    private String TC_Tacsflag2;
    // 携帯電話番号最新年
    private String TC_Latestyear2;
    // 携帯電話番号最新月
    private String TC_Latestmonth2;
    private String applicationDate;
    private String receiptDate;
    private String identificationType;
    private String identificationImage;
    private String identificationImageBack;
    private String livingConditions;
    private String livingConditionsImage;
    // 自宅電話番号利用ステータス履歴
    private List<TacsflagVO> TacsflagList1;
    // 携帯電話番号利用ステータス履歴
    private List<TacsflagVO> TacsflagList2;
    // 2週間以内の検索回数
    private String IC_TwoWeeksCount;
    // 自宅電話番号照会データ最新年月
    private String TC_LatestDate1;
    // 携帯電話番号照会データ最新年月
    private String TC_LatestDate2;
    private String appraisalTelResult;
    private String appraisalIPResult;

    private String loanAppSeq = "";
    // ユーザーID

    private String agreeTime = "";
    private String agreeCheck = "";

    // お名前（漢字）：姓(入力エリア)
    private String lastName = "";
    // お名前（漢字）：名(入力エリア)
    private String firstName = "";
    // お名前（カナ）：セイ
    private String kanaLastName = "";
    // お名前（カナ）：メイ
    private String kanaFirstName = "";

    // キャリア
    private String IC_Carrier = "";
    // 登録企業

    // TACSフラグ０１
    private String f01_1 = "";
    // TACSフラグ０２
    private String f02_1 = "";
    // TACSフラグ０３
    private String f03_1 = "";
    // TACSフラグ０４
    private String f04_1 = "";
    // TACSフラグ０５
    private String f05_1 = "";
    // TACSフラグ０６
    private String f06_1 = "";
    // TACSフラグ０７
    private String f07_1 = "";
    // TACSフラグ０８
    private String f08_1 = "";
    // TACSフラグ０９
    private String f09_1 = "";
    // TACSフラグ１０
    private String f10_1 = "";
    // TACSフラグ１１
    private String f11_1 = "";
    // TACSフラグ１２
    private String f12_1 = "";
    // TACSフラグ１３
    private String f13_1 = "";
    // TACSフラグ１４
    private String f14_1 = "";
    // TACSフラグ１５
    private String f15_1 = "";
    // TACSフラグ１６
    private String f16_1 = "";
    // TACSフラグ１７
    private String f17_1 = "";
    // TACSフラグ１８
    private String f18_1 = "";
    // TACSフラグ１９
    private String f19_1 = "";
    // TACSフラグ２０
    private String f20_1 = "";
    // TACSフラグ２１
    private String f21_1 = "";
    // TACSフラグ２２
    private String f22_1 = "";
    // TACSフラグ２３
    private String f23_1 = "";
    // TACSフラグ２４
    private String f24_1 = "";
    // 携帯電話番号認証結果

    // TACSフラグ０１
    private String f01_2 = "";
    // TACSフラグ０２
    private String f02_2 = "";
    // TACSフラグ０３
    private String f03_2 = "";
    // TACSフラグ０４
    private String f04_2 = "";
    // TACSフラグ０５
    private String f05_2 = "";
    // TACSフラグ０６
    private String f06_2 = "";
    // TACSフラグ０７
    private String f07_2 = "";
    // TACSフラグ０８
    private String f08_2 = "";
    // TACSフラグ０９
    private String f09_2 = "";
    // TACSフラグ１０
    private String f10_2 = "";
    // TACSフラグ１１
    private String f11_2 = "";
    // TACSフラグ１２
    private String f12_2 = "";
    // TACSフラグ１３
    private String f13_2 = "";
    // TACSフラグ１４
    private String f14_2 = "";
    // TACSフラグ１５
    private String f15_2 = "";
    // TACSフラグ１６
    private String f16_2 = "";
    // TACSフラグ１７
    private String f17_2 = "";
    // TACSフラグ１８
    private String f18_2 = "";
    // TACSフラグ１９
    private String f19_2 = "";
    // TACSフラグ２０
    private String f20_2 = "";
    // TACSフラグ２１
    private String f21_2 = "";
    // TACSフラグ２２
    private String f22_2 = "";
    // TACSフラグ２３
    private String f23_2 = "";
    // TACSフラグ２４
    private String f24_2 = "";

    public List<StatusModifyListVO> getStatusModifyList() {
        return statusModifyList;
    }

    public void setStatusModifyList(List<StatusModifyListVO> statusModifyList) {
        this.statusModifyList = statusModifyList;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDeviceTokenId() {
        return deviceTokenId;
    }

    public void setDeviceTokenId(String deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }

    public String getApplicationEndFlg() {
        return applicationEndFlg;
    }

    public void setApplicationEndFlg(String applicationEndFlg) {
        this.applicationEndFlg = applicationEndFlg;
    }

    public String getApplicationFlg() {
        return applicationFlg;
    }

    public void setApplicationFlg(String applicationFlg) {
        this.applicationFlg = applicationFlg;
    }

    public String getLicenseIdR() {
        return licenseIdR;
    }

    public void setLicenseIdR(String licenseIdR) {
        this.licenseIdR = licenseIdR;
    }

    public String getLastNameR() {
        return lastNameR;
    }

    public void setLastNameR(String lastNameR) {
        this.lastNameR = lastNameR;
    }

    public String getFirstNameR() {
        return firstNameR;
    }

    public void setFirstNameR(String firstNameR) {
        this.firstNameR = firstNameR;
    }

    public String getBirthdayR() {
        return birthdayR;
    }

    public void setBirthdayR(String birthdayR) {
        this.birthdayR = birthdayR;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountAppSeq() {
        return accountAppSeq;
    }

    public void setAccountAppSeq(String accountAppSeq) {
        this.accountAppSeq = accountAppSeq;
    }

    public String getKanaName() {
        return kanaName;
    }

    public void setKanaName(String kanaName) {
        this.kanaName = kanaName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSexKbn() {
        return sexKbn;
    }

    public void setSexKbn(String sexKbn) {
        this.sexKbn = sexKbn;
    }

    public String getPostCodeR() {
        return postCodeR;
    }

    public void setPostCodeR(String postCodeR) {
        this.postCodeR = postCodeR;
    }

    public String getPrefecturesCodeR() {
        return prefecturesCodeR;
    }

    public void setPrefecturesCodeR(String prefecturesCodeR) {
        this.prefecturesCodeR = prefecturesCodeR;
    }

    public String getAddressR() {
        return addressR;
    }

    public void setAddressR(String addressR) {
        this.addressR = addressR;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPrefecturesCode() {
        return prefecturesCode;
    }

    public void setPrefecturesCode(String prefecturesCode) {
        this.prefecturesCode = prefecturesCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getJobKbnOther() {
        return jobKbnOther;
    }

    public void setJobKbnOther(String jobKbnOther) {
        this.jobKbnOther = jobKbnOther;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getKanaWorkName() {
        return kanaWorkName;
    }

    public void setKanaWorkName(String kanaWorkName) {
        this.kanaWorkName = kanaWorkName;
    }

    public String getWorkPostCode() {
        return workPostCode;
    }

    public void setWorkPostCode(String workPostCode) {
        this.workPostCode = workPostCode;
    }

    public String getWorkPrefecturesCode() {
        return workPrefecturesCode;
    }

    public void setWorkPrefecturesCode(String workPrefecturesCode) {
        this.workPrefecturesCode = workPrefecturesCode;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getWorkTeleNumber() {
        return workTeleNumber;
    }

    public void setWorkTeleNumber(String workTeleNumber) {
        this.workTeleNumber = workTeleNumber;
    }

    public String getWorkNumberKbn() {
        return workNumberKbn;
    }

    public void setWorkNumberKbn(String workNumberKbn) {
        this.workNumberKbn = workNumberKbn;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBankbookType() {
        return bankbookType;
    }

    public void setBankbookType(String bankbookType) {
        this.bankbookType = bankbookType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getSalesOfficeOption() {
        return salesOfficeOption;
    }

    public void setSalesOfficeOption(String salesOfficeOption) {
        this.salesOfficeOption = salesOfficeOption;
    }

    public String getJobKbn() {
        return jobKbn;
    }

    public void setJobKbn(String jobKbn) {
        this.jobKbn = jobKbn;
    }

    public String getAccountPurpose() {
        return accountPurpose;
    }

    public void setAccountPurpose(String accountPurpose) {
        this.accountPurpose = accountPurpose;
    }

    public String getAccountPurposeOther() {
        return accountPurposeOther;
    }

    public void setAccountPurposeOther(String accountPurposeOther) {
        this.accountPurposeOther = accountPurposeOther;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }

    public String getSecurityPasswordConfirm() {
        return securityPasswordConfirm;
    }

    public void setSecurityPasswordConfirm(String securityPasswordConfirm) {
        this.securityPasswordConfirm = securityPasswordConfirm;
    }

    public String getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(String creditlimit) {
        this.creditlimit = creditlimit;
    }

    public String getOnlinePassword() {
        return onlinePassword;
    }

    public void setOnlinePassword(String onlinePassword) {
        this.onlinePassword = onlinePassword;
    }

    public String getOnlinePasswordConfirm() {
        return onlinePasswordConfirm;
    }

    public void setOnlinePasswordConfirm(String onlinePasswordConfirm) {
        this.onlinePasswordConfirm = onlinePasswordConfirm;
    }

    public String getKnowProcess() {
        return knowProcess;
    }

    public void setKnowProcess(String knowProcess) {
        this.knowProcess = knowProcess;
    }

    public String getApplicationReason() {
        return applicationReason;
    }

    public void setApplicationReason(String applicationReason) {
        this.applicationReason = applicationReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<AccountAppPushListVO> getAccountAppDetailPushList() {
        return accountAppDetailPushList;
    }

    public void setAccountAppDetailPushList(List<AccountAppPushListVO> accountAppDetailPushList) {
        this.accountAppDetailPushList = accountAppDetailPushList;
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

    public String getIdentificationImageBack() {
        return identificationImageBack;
    }

    public void setIdentificationImageBack(String identificationImageBack) {
        this.identificationImageBack = identificationImageBack;
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

    public List<TacsflagVO> getTacsflagList1() {
        return TacsflagList1;
    }

    public void setTacsflagList1(List<TacsflagVO> tacsflagList1) {
        TacsflagList1 = tacsflagList1;
    }

    public List<TacsflagVO> getTacsflagList2() {
        return TacsflagList2;
    }

    public void setTacsflagList2(List<TacsflagVO> tacsflagList2) {
        TacsflagList2 = tacsflagList2;
    }

    public String getIC_TwoWeeksCount() {
        return IC_TwoWeeksCount;
    }

    public void setIC_TwoWeeksCount(String iC_TwoWeeksCount) {
        IC_TwoWeeksCount = iC_TwoWeeksCount;
    }

    public String getTC_LatestDate1() {
        return TC_LatestDate1;
    }

    public void setTC_LatestDate1(String tC_LatestDate1) {
        TC_LatestDate1 = tC_LatestDate1;
    }

    public String getTC_LatestDate2() {
        return TC_LatestDate2;
    }

    public void setTC_LatestDate2(String tC_LatestDate2) {
        TC_LatestDate2 = tC_LatestDate2;
    }

    public String getAppraisalTelResult() {
        return appraisalTelResult;
    }

    public void setAppraisalTelResult(String appraisalTelResult) {
        this.appraisalTelResult = appraisalTelResult;
    }

    public String getAppraisalIPResult() {
        return appraisalIPResult;
    }

    public void setAppraisalIPResult(String appraisalIPResult) {
        this.appraisalIPResult = appraisalIPResult;
    }

    public String getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(String loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public String getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(String agreeTime) {
        this.agreeTime = agreeTime;
    }

    public String getAgreeCheck() {
        return agreeCheck;
    }

    public void setAgreeCheck(String agreeCheck) {
        this.agreeCheck = agreeCheck;
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

    public String getIC_Carrier() {
        return IC_Carrier;
    }

    public void setIC_Carrier(String iC_Carrier) {
        IC_Carrier = iC_Carrier;
    }

    public String getF01_1() {
        return f01_1;
    }

    public void setF01_1(String f01_1) {
        this.f01_1 = f01_1;
    }

    public String getF02_1() {
        return f02_1;
    }

    public void setF02_1(String f02_1) {
        this.f02_1 = f02_1;
    }

    public String getF03_1() {
        return f03_1;
    }

    public void setF03_1(String f03_1) {
        this.f03_1 = f03_1;
    }

    public String getF04_1() {
        return f04_1;
    }

    public void setF04_1(String f04_1) {
        this.f04_1 = f04_1;
    }

    public String getF05_1() {
        return f05_1;
    }

    public void setF05_1(String f05_1) {
        this.f05_1 = f05_1;
    }

    public String getF06_1() {
        return f06_1;
    }

    public void setF06_1(String f06_1) {
        this.f06_1 = f06_1;
    }

    public String getF07_1() {
        return f07_1;
    }

    public void setF07_1(String f07_1) {
        this.f07_1 = f07_1;
    }

    public String getF08_1() {
        return f08_1;
    }

    public void setF08_1(String f08_1) {
        this.f08_1 = f08_1;
    }

    public String getF09_1() {
        return f09_1;
    }

    public void setF09_1(String f09_1) {
        this.f09_1 = f09_1;
    }

    public String getF10_1() {
        return f10_1;
    }

    public void setF10_1(String f10_1) {
        this.f10_1 = f10_1;
    }

    public String getF11_1() {
        return f11_1;
    }

    public void setF11_1(String f11_1) {
        this.f11_1 = f11_1;
    }

    public String getF12_1() {
        return f12_1;
    }

    public void setF12_1(String f12_1) {
        this.f12_1 = f12_1;
    }

    public String getF13_1() {
        return f13_1;
    }

    public void setF13_1(String f13_1) {
        this.f13_1 = f13_1;
    }

    public String getF14_1() {
        return f14_1;
    }

    public void setF14_1(String f14_1) {
        this.f14_1 = f14_1;
    }

    public String getF15_1() {
        return f15_1;
    }

    public void setF15_1(String f15_1) {
        this.f15_1 = f15_1;
    }

    public String getF16_1() {
        return f16_1;
    }

    public void setF16_1(String f16_1) {
        this.f16_1 = f16_1;
    }

    public String getF17_1() {
        return f17_1;
    }

    public void setF17_1(String f17_1) {
        this.f17_1 = f17_1;
    }

    public String getF18_1() {
        return f18_1;
    }

    public void setF18_1(String f18_1) {
        this.f18_1 = f18_1;
    }

    public String getF19_1() {
        return f19_1;
    }

    public void setF19_1(String f19_1) {
        this.f19_1 = f19_1;
    }

    public String getF20_1() {
        return f20_1;
    }

    public void setF20_1(String f20_1) {
        this.f20_1 = f20_1;
    }

    public String getF21_1() {
        return f21_1;
    }

    public void setF21_1(String f21_1) {
        this.f21_1 = f21_1;
    }

    public String getF22_1() {
        return f22_1;
    }

    public void setF22_1(String f22_1) {
        this.f22_1 = f22_1;
    }

    public String getF23_1() {
        return f23_1;
    }

    public void setF23_1(String f23_1) {
        this.f23_1 = f23_1;
    }

    public String getF24_1() {
        return f24_1;
    }

    public void setF24_1(String f24_1) {
        this.f24_1 = f24_1;
    }

    public String getF01_2() {
        return f01_2;
    }

    public void setF01_2(String f01_2) {
        this.f01_2 = f01_2;
    }

    public String getF02_2() {
        return f02_2;
    }

    public void setF02_2(String f02_2) {
        this.f02_2 = f02_2;
    }

    public String getF03_2() {
        return f03_2;
    }

    public void setF03_2(String f03_2) {
        this.f03_2 = f03_2;
    }

    public String getF04_2() {
        return f04_2;
    }

    public void setF04_2(String f04_2) {
        this.f04_2 = f04_2;
    }

    public String getF05_2() {
        return f05_2;
    }

    public void setF05_2(String f05_2) {
        this.f05_2 = f05_2;
    }

    public String getF06_2() {
        return f06_2;
    }

    public void setF06_2(String f06_2) {
        this.f06_2 = f06_2;
    }

    public String getF07_2() {
        return f07_2;
    }

    public void setF07_2(String f07_2) {
        this.f07_2 = f07_2;
    }

    public String getF08_2() {
        return f08_2;
    }

    public void setF08_2(String f08_2) {
        this.f08_2 = f08_2;
    }

    public String getF09_2() {
        return f09_2;
    }

    public void setF09_2(String f09_2) {
        this.f09_2 = f09_2;
    }

    public String getF10_2() {
        return f10_2;
    }

    public void setF10_2(String f10_2) {
        this.f10_2 = f10_2;
    }

    public String getF11_2() {
        return f11_2;
    }

    public void setF11_2(String f11_2) {
        this.f11_2 = f11_2;
    }

    public String getF12_2() {
        return f12_2;
    }

    public void setF12_2(String f12_2) {
        this.f12_2 = f12_2;
    }

    public String getF13_2() {
        return f13_2;
    }

    public void setF13_2(String f13_2) {
        this.f13_2 = f13_2;
    }

    public String getF14_2() {
        return f14_2;
    }

    public void setF14_2(String f14_2) {
        this.f14_2 = f14_2;
    }

    public String getF15_2() {
        return f15_2;
    }

    public void setF15_2(String f15_2) {
        this.f15_2 = f15_2;
    }

    public String getF16_2() {
        return f16_2;
    }

    public void setF16_2(String f16_2) {
        this.f16_2 = f16_2;
    }

    public String getF17_2() {
        return f17_2;
    }

    public void setF17_2(String f17_2) {
        this.f17_2 = f17_2;
    }

    public String getF18_2() {
        return f18_2;
    }

    public void setF18_2(String f18_2) {
        this.f18_2 = f18_2;
    }

    public String getF19_2() {
        return f19_2;
    }

    public void setF19_2(String f19_2) {
        this.f19_2 = f19_2;
    }

    public String getF20_2() {
        return f20_2;
    }

    public void setF20_2(String f20_2) {
        this.f20_2 = f20_2;
    }

    public String getF21_2() {
        return f21_2;
    }

    public void setF21_2(String f21_2) {
        this.f21_2 = f21_2;
    }

    public String getF22_2() {
        return f22_2;
    }

    public void setF22_2(String f22_2) {
        this.f22_2 = f22_2;
    }

    public String getF23_2() {
        return f23_2;
    }

    public void setF23_2(String f23_2) {
        this.f23_2 = f23_2;
    }

    public String getF24_2() {
        return f24_2;
    }

    public void setF24_2(String f24_2) {
        this.f24_2 = f24_2;
    }

    public String getApplicationReasonOther() {
        return applicationReasonOther;
    }

    public void setApplicationReasonOther(String applicationReasonOther) {
        this.applicationReasonOther = applicationReasonOther;
    }

}
