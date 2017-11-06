package com.scsk.model;

/**
 * 配信履歴詳細DOC
 * 
 */
public class Account114PushDetailDoc extends UtilDoc {
    // ドキュメントタイプ
    private  String docType = "";
    public void setDocType(String docType) {
        this.docType = docType;
    }

    // メッセージ
    private String pushMessage = "";

    public String getDocType() {
        return docType;
    }

    public String getPushMessage() {
        return pushMessage;
    }

    public void setPushMessage(String pushMessage) {
        this.pushMessage = pushMessage;
    }
}