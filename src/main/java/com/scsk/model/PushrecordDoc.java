package com.scsk.model;

import java.util.List;

/**
 * 配信履歴DOC
 * 
 */
public class PushrecordDoc extends UtilDoc {

    // ドキュメントタイプ
    private String docType = "";
    // 受付番号
    private String accountAppSeq = "";
    // ユーザーID
    private String userId = "";
    // 端末ID
    private List<String> deviceTokenId;
    // 配信日付
    private String pushDate = "";
    // ソート日付
    private String sortTime = "";
    // 開封日付
    private String openDate = "";
    // 最新開封状況取得用日付
    private String saveDate = "";
    // Push内容
    private String pushContent = "";
    // Push開封状況
    private String pushStatus = "";
    // 申込処理ステータス
    private String status = "";
    // 申込受付日付
    private String receiptDate = "";
    // 姓
    private String lastName = "";
    // 名
    private String firstName = "";

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getAccountAppSeq() {
        return accountAppSeq;
    }

    public void setAccountAppSeq(String accountAppSeq) {
        this.accountAppSeq = accountAppSeq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getDeviceTokenId() {
        return deviceTokenId;
    }

    public void setDeviceTokenId(List<String> deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }

    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
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

    public String getSortTime() {
        return sortTime;
    }

    public void setSortTime(String sortTime) {
        this.sortTime = sortTime;
    }

}
