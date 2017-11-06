package com.scsk.model;

import com.scsk.constants.Constants;

public class YamagataMsgDetailDoc extends UtilDoc {
	// ドキュメントタイプ
	private String docType = Constants.MSGDETAIL_DOCTYPE;
	// メッセージ
	private String pushMessage = "";
	// メッセージ（HTML）
	private String pushMessageHTML = "";

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

	public String getPushMessageHTML() {
		return pushMessageHTML;
	}

	public void setPushMessageHTML(String pushMessageHTML) {
		this.pushMessageHTML = pushMessageHTML;
	}
}
