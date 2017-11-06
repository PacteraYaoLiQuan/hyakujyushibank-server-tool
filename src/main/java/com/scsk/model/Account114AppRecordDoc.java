package com.scsk.model;

import com.scsk.constants.Constants;

public class Account114AppRecordDoc extends UtilDoc {

    // ドキュメントタイプ
    private String docType = Constants.PUSH_RECORD;
    // ユーザーID
    private String userId = "";
    // 端末ID
    private String deviceTokenId = "";
    // 配信日付
    private String pushDate = "";
    // 配信日時並び用
    private long pushDateForSort = 0l;
    // Push内容
    private String pushTitle = "";
    // Push開封状況
    private String pushStatus = "";
    // プッシュ詳細OID
    private String pushDetailOid = "";

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
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

    public String getDeviceTokenId() {
        return deviceTokenId;
    }

    public void setDeviceTokenId(String deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }

    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    public long getPushDateForSort() {
        return pushDateForSort;
    }

    public void setPushDateForSort(long pushDateForSort) {
        this.pushDateForSort = pushDateForSort;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getPushDetailOid() {
        return pushDetailOid;
    }

    public void setPushDetailOid(String pushDetailOid) {
        this.pushDetailOid = pushDetailOid;
    }

}
