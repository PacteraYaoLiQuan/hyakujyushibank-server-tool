package com.scsk.vo;

public class AccountAppPushListVO {
    // 選択
    private Boolean select;
    // 端末ID
    private String deviceTokenId;
    // 受付番号
    private String accountAppSeq;
    // 配信日付
    private String pushDate;
    public String getSalesOfficeOption() {
        return salesOfficeOption;
    }

    public void setSalesOfficeOption(String salesOfficeOption) {
        this.salesOfficeOption = salesOfficeOption;
    }

    private String salesOfficeOption;
    // ID
    private String accountApp_id;
    private String driverLicenseNo;
    public String getDriverLicenseNo() {
        return driverLicenseNo;
    }

    public void setDriverLicenseNo(String driverLicenseNo) {
        this.driverLicenseNo = driverLicenseNo;
    }

    private String loanType;

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    private String storeName;
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    // 開封日付
    private String openDate;
    // Push開封状況
    private String pushStatus;
    // 申込処理ステータス
    private String status;
    // Push開封状況DB
    private String pushStatusDB;
    // 申込受付日付
    private String receiptDate;
    // 氏名
    private String name;
    // Push通知確認チェック
    private boolean checkDisable;
    // ID
    private String _id;
    // 配信状況
    private String sendStatus;
    // 変更日付
    private String createDate;
    // 変更日付
    private String pushErr;
    // ユーザーID
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPushStatusDB() {
        return pushStatusDB;
    }

    public String getAccountApp_id() {
        return accountApp_id;
    }

    public void setAccountApp_id(String accountApp_id) {
        this.accountApp_id = accountApp_id;
    }

    public void setPushStatusDB(String pushStatusDB) {
        this.pushStatusDB = pushStatusDB;
    }

    private boolean visible;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getPushErr() {
        return pushErr;
    }

    public void setPushErr(String pushErr) {
        this.pushErr = pushErr;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isCheckDisable() {
        return checkDisable;
    }

    public void setCheckDisable(boolean checkDisable) {
        this.checkDisable = checkDisable;
    }

    public String getDeviceTokenId() {
        return deviceTokenId;
    }

    public void setDeviceTokenId(String deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }

    public String getAccountAppSeq() {
        return accountAppSeq;
    }

    public void setAccountAppSeq(String accountAppSeq) {
        this.accountAppSeq = accountAppSeq;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }
}
