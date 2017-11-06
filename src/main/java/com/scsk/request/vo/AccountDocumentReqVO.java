package com.scsk.request.vo;

import java.util.ArrayList;
import java.util.List;

import com.scsk.constants.Constants;
import com.scsk.model.CardNoDoc;

public class AccountDocumentReqVO {

    private String docType = Constants.ACCOUNT_DOCUMENT;
    private String agreeTime = "";
    private String agreeCheck = "";
    private String purposeFlg = "";
    private String typeFlg = "";
    private String driverLicenseFront = "";
    private String driverLicenseBack = "";
    private String incomeCertificateFront = "";
    private String incomeCertificateBack = "";
    private List<CardNoDoc> cardNoList = new ArrayList<CardNoDoc>();
    private String reportTypeFlg = "";
    private String name = "";
    private String nameKana = "";
    private String driverLicenseNo = "";
    private String birthDay = "";
    private String telephoneNo = "";
    private String bankNo = "";
    private String storeNo = "";
    private String accountNo = "";
    private String other = "";
    private String oid = "";
    private String acomSendStatus = "";
    private String dnpSendStatus = "";
    private String sendStatus = "";
    private String sendStep = "";
    private String acomSendDate = "";
    private String dnpSendDate = "";
    private String errorMsg = "";
    private String compDate = "";
    private String status = "";

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
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

    public String getPurposeFlg() {
        return purposeFlg;
    }

    public void setPurposeFlg(String purposeFlg) {
        this.purposeFlg = purposeFlg;
    }

    public String getTypeFlg() {
        return typeFlg;
    }

    public void setTypeFlg(String typeFlg) {
        this.typeFlg = typeFlg;
    }

    public String getDriverLicenseFront() {
        return driverLicenseFront;
    }

    public void setDriverLicenseFront(String driverLicenseFront) {
        this.driverLicenseFront = driverLicenseFront;
    }

    public String getDriverLicenseBack() {
        return driverLicenseBack;
    }

    public void setDriverLicenseBack(String driverLicenseBack) {
        this.driverLicenseBack = driverLicenseBack;
    }

    public String getIncomeCertificateFront() {
        return incomeCertificateFront;
    }

    public void setIncomeCertificateFront(String incomeCertificateFront) {
        this.incomeCertificateFront = incomeCertificateFront;
    }

    public String getIncomeCertificateBack() {
        return incomeCertificateBack;
    }

    public void setIncomeCertificateBack(String incomeCertificateBack) {
        this.incomeCertificateBack = incomeCertificateBack;
    }

    public List<CardNoDoc> getCardNoList() {
        return cardNoList;
    }

    public void setCardNoList(List<CardNoDoc> cardNoList) {
        this.cardNoList = cardNoList;
    }

    public String getReportTypeFlg() {
        return reportTypeFlg;
    }

    public void setReportTypeFlg(String reportTypeFlg) {
        this.reportTypeFlg = reportTypeFlg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKana() {
        return nameKana;
    }

    public void setNameKana(String nameKana) {
        this.nameKana = nameKana;
    }

    public String getDriverLicenseNo() {
        return driverLicenseNo;
    }

    public void setDriverLicenseNo(String driverLicenseNo) {
        this.driverLicenseNo = driverLicenseNo;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAcomSendStatus() {
        return acomSendStatus;
    }

    public void setAcomSendStatus(String acomSendStatus) {
        this.acomSendStatus = acomSendStatus;
    }

    public String getDnpSendStatus() {
        return dnpSendStatus;
    }

    public void setDnpSendStatus(String dnpSendStatus) {
        this.dnpSendStatus = dnpSendStatus;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendStep() {
        return sendStep;
    }

    public void setSendStep(String sendStep) {
        this.sendStep = sendStep;
    }

    public String getAcomSendDate() {
        return acomSendDate;
    }

    public void setAcomSendDate(String acomSendDate) {
        this.acomSendDate = acomSendDate;
    }

    public String getDnpSendDate() {
        return dnpSendDate;
    }

    public void setDnpSendDate(String dnpSendDate) {
        this.dnpSendDate = dnpSendDate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCompDate() {
        return compDate;
    }

    public void setCompDate(String compDate) {
        this.compDate = compDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
