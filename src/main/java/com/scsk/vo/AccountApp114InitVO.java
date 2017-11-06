package com.scsk.vo;

import java.util.ArrayList;
import java.util.List;

import com.ibm.icu.impl.StringRange;
import com.scsk.constants.Constants;

public class AccountApp114InitVO {
    private String appraisalTelResult="";
    private String appraisalIPResult="";
    private String _id="";
    private String _rev="";
    private Boolean select;
    private String accountAppSeq="";
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
    private String ipAddress;
    private String kanaName;
    private String lastName;
    private String firstName;
    private String kanaLastName;
    private String kanaFirstName;
    private String pushStatus;
 // 生計費決済
    private String accountPurpose1;
    // 事業費決済
    private String accountPurpose2;
    // 給与受取
    private String accountPurpose3;
    // 年金受取
    private String accountPurpose4;
    // 仕送り
    private String accountPurpose5;
    // 貯蓄
    private String accountPurpose6;
    // 資産運用
    private String accountPurpose7;
    // その他記述
    private String accountPurpose99;
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
    private List<String> jobKbn = new ArrayList<>();
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
    private List<String> accountPurpose = new ArrayList<>();
    private String accountPurposeOther;
    private String securityPassword;
    private String securityPasswordConfirm;
    private String creditlimit;
    private String onlinePassword;
    private String onlinePasswordConfirm;
    private List<String> knowProcess = new ArrayList<>();
    private List<String> applicationReason = new ArrayList<>();
    private String status;
    private String applicationDate;
    private String receiptDate;
    private String identificationType;
    private String identificationImage;
    private String livingConditions;
    private String livingConditionsImage;
 // 会社役員・団体役員
    private String jobKbn101;
    // 会社員・団体職員
    private String jobKbn102;
    // 公務員 
    private String jobKbn103;
    // 個人事業主・自営業
    private String jobKbn104;
    // パート・アルバイト
    private String jobKbn105;
    // 派遣・嘱託・契約社員
    private String jobKbn106;
    // 主婦・主夫
    private String jobKbn107;
    // 年金受給者
    private String jobKbn108;
    // 学生
    private String jobKbn199;
    
    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
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

    public String getJobKbn101() {
        return jobKbn101;
    }

    public void setJobKbn101(String jobKbn101) {
        this.jobKbn101 = jobKbn101;
    }

    public String getJobKbn102() {
        return jobKbn102;
    }

    public void setJobKbn102(String jobKbn102) {
        this.jobKbn102 = jobKbn102;
    }

    public String getJobKbn103() {
        return jobKbn103;
    }

    public void setJobKbn103(String jobKbn103) {
        this.jobKbn103 = jobKbn103;
    }

    public String getJobKbn104() {
        return jobKbn104;
    }

    public void setJobKbn104(String jobKbn104) {
        this.jobKbn104 = jobKbn104;
    }

    public String getJobKbn105() {
        return jobKbn105;
    }

    public void setJobKbn105(String jobKbn105) {
        this.jobKbn105 = jobKbn105;
    }

    public String getJobKbn106() {
        return jobKbn106;
    }

    public void setJobKbn106(String jobKbn106) {
        this.jobKbn106 = jobKbn106;
    }

    public String getJobKbn107() {
        return jobKbn107;
    }

    public void setJobKbn107(String jobKbn107) {
        this.jobKbn107 = jobKbn107;
    }

    public String getJobKbn108() {
        return jobKbn108;
    }

    public void setJobKbn108(String jobKbn108) {
        this.jobKbn108 = jobKbn108;
    }

    public String getJobKbn199() {
        return jobKbn199;
    }

    public void setJobKbn199(String jobKbn199) {
        this.jobKbn199 = jobKbn199;
    }

    public String getAccountPurpose1() {
        return accountPurpose1;
    }

    public void setAccountPurpose1(String accountPurpose1) {
        this.accountPurpose1 = accountPurpose1;
    }

    public String getAccountPurpose2() {
        return accountPurpose2;
    }

    public void setAccountPurpose2(String accountPurpose2) {
        this.accountPurpose2 = accountPurpose2;
    }

    public String getAccountPurpose3() {
        return accountPurpose3;
    }

    public void setAccountPurpose3(String accountPurpose3) {
        this.accountPurpose3 = accountPurpose3;
    }

    public String getAccountPurpose4() {
        return accountPurpose4;
    }

    public void setAccountPurpose4(String accountPurpose4) {
        this.accountPurpose4 = accountPurpose4;
    }

    public String getAccountPurpose5() {
        return accountPurpose5;
    }

    public void setAccountPurpose5(String accountPurpose5) {
        this.accountPurpose5 = accountPurpose5;
    }

    public String getAccountPurpose6() {
        return accountPurpose6;
    }

    public void setAccountPurpose6(String accountPurpose6) {
        this.accountPurpose6 = accountPurpose6;
    }

    public String getAccountPurpose7() {
        return accountPurpose7;
    }

    public void setAccountPurpose7(String accountPurpose7) {
        this.accountPurpose7 = accountPurpose7;
    }

    public String getAccountPurpose99() {
        return accountPurpose99;
    }

    public void setAccountPurpose99(String accountPurpose99) {
        this.accountPurpose99 = accountPurpose99;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    public String getAppraisalIPResult() {
        return appraisalIPResult;
    }

    public void setAppraisalIPResult(String appraisalIPResult) {
        this.appraisalIPResult = appraisalIPResult;
    }

    public String getAccountAppSeq() {
        return accountAppSeq;
    }

    public void setAccountAppSeq(String accountAppSeq) {
        this.accountAppSeq = accountAppSeq;
    }

    public String getAppraisalTelResult() {
        return appraisalTelResult;
    }

    public void setAppraisalTelResult(String appraisalTelResult) {
        this.appraisalTelResult = appraisalTelResult;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
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

    public String getKanaName() {
        return kanaName;
    }

    public void setKanaName(String kanaName) {
        this.kanaName = kanaName;
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

    public List<String> getJobKbn() {
        return jobKbn;
    }

    public void setJobKbn(List<String> jobKbn) {
        this.jobKbn = jobKbn;
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

    public List<String> getAccountPurpose() {
        return accountPurpose;
    }

    public void setAccountPurpose(List<String> accountPurpose) {
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

    public List<String> getKnowProcess() {
        return knowProcess;
    }

    public void setKnowProcess(List<String> knowProcess) {
        this.knowProcess = knowProcess;
    }

    public List<String> getApplicationReason() {
        return applicationReason;
    }

    public void setApplicationReason(List<String> applicationReason) {
        this.applicationReason = applicationReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
