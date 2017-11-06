package com.scsk.vo;

public class YamagataStatusModifyListVO {
    
    // 申込処理ステータス
    private String status;
    // 端末ID
	private String deviceTokenId;
	// 変更日時
	private String statusModifyDate;
	// ソート日付
    private String statusModifyDateForSort;
    // 配信状態
	private String sendStatus;
	// 配信日時
	private String pushDate;
	// Push開封状況
	private String pushStatus;
	
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDeviceTokenId() {
        return deviceTokenId;
    }
    public void setDeviceTokenId(String deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }
    public String getStatusModifyDate() {
        return statusModifyDate;
    }
    public void setStatusModifyDate(String statusModifyDate) {
        this.statusModifyDate = statusModifyDate;
    }
    public String getSendStatus() {
        return sendStatus;
    }
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }
    public String getPushDate() {
        return pushDate;
    }
    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }
    public String getPushStatus() {
        return pushStatus;
    }
    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }
    public String getStatusModifyDateForSort() {
        return statusModifyDateForSort;
    }
    public void setStatusModifyDateForSort(String statusModifyDateForSort) {
        this.statusModifyDateForSort = statusModifyDateForSort;
    }
}
