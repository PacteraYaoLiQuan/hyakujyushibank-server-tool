package com.scsk.model;
/**
 * 利用規約DOC
 * @author 9003474
 *
 */
public class LaunchAgreeApiDoc extends UtilDoc{

    // ドキュメントタイプ
    private String docType = "";
    // 端末ID
    private String deviceTokenId = "";
    // ユーザーID
    private String userId = "";
    
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }
    public String getDeviceTokenId() {
        return deviceTokenId;
    }
    public void setDeviceTokenId(String deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
