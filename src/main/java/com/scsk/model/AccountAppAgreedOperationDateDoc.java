package com.scsk.model;
/**
 * 
 * 同意操作データDOC
 *
 */
public class AccountAppAgreedOperationDateDoc extends UtilDoc{

    // ドキュメントタイプ
    private String docType = "";
    // ユーザーID
    private String userId = "";
    // ユーザータイプ
    private int userType = 0;
    // 端末ID
    private String deviceTokenId = "";
    // 同意操作日時
    private String agreedOperationDate = "";
    // 受付番号
    private String accountAppSeq = "";
    
    public void setDeviceTokenId(String deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }
    public String getDeviceTokenId() {
        return deviceTokenId;
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
    public void setAgreedOperationDate(String agreedOperationDate) {
        this.agreedOperationDate = agreedOperationDate;
    }
    public String getAgreedOperationDate() {
        return agreedOperationDate;
    }
	public String getAccountAppSeq() {
		return accountAppSeq;
	}
	public void setAccountAppSeq(String accountAppSeq) {
		this.accountAppSeq = accountAppSeq;
	}
}
