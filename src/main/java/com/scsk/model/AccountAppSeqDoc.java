package com.scsk.model;
/**
 * 申込採番DOC
 */
public class AccountAppSeqDoc extends UtilDoc {

    //ドキュメントタイプ
    private String docType = "";
    //ユーザーID
    private String userId = "";
   

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
   
}
