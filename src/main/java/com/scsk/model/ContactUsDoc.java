package com.scsk.model;

/**
 * お問い合わせDOC
 *
 */
public class ContactUsDoc extends UtilDoc {

    // ドキュメントタイプ
    String docType = "";

    // メールアドレス
    String userId = "";

    // 端末ID
    String deviceTokenId = "";

    // お問い合わせ
    String contactUs = "";

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

    public String getContactUs() {
        return contactUs;
    }

    public void setContactUs(String contactUs) {
        this.contactUs = contactUs;
    }
}
