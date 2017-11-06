package com.scsk.model;

import com.scsk.constants.Constants;

/**
 * 配信履歴詳細DOC
 * 
 */
public class YamagataPushDetailDoc extends UtilDoc {
    // ドキュメントタイプ
    private String docType = Constants.PUSH_DETAIL;
    // メッセージ
    private String pushMessage = "";

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPushMessage() {
        return pushMessage;
    }

    public void setPushMessage(String pushMessage) {
        this.pushMessage = pushMessage;
    }
}
