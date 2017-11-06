package com.scsk.model;

import com.scsk.constants.Constants;

public class IyoMsgOpenStatusDoc extends UtilDoc {
	// ドキュメントタイプ
	private String docType = Constants.MSGOPENSTATUS_DOCTYPE;
	// 端末のユーザーID
	private String userId = "";
	// 任意配信件名OID
	private String pushTitlelOid = "";
	// PUSH区分
	private String pushKBN = "";
	// 送達区分
	private String arriveKBN = "";
	// 開封区分
	private String openKBN = "";
	// 開封日時
	private String openDateTime = "";

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

	public String getPushTitlelOid() {
		return pushTitlelOid;
	}

	public void setPushTitlelOid(String pushTitlelOid) {
		this.pushTitlelOid = pushTitlelOid;
	}

	public String getPushKBN() {
		return pushKBN;
	}

	public void setPushKBN(String pushKBN) {
		this.pushKBN = pushKBN;
	}

	public String getArriveKBN() {
		return arriveKBN;
	}

	public void setArriveKBN(String arriveKBN) {
		this.arriveKBN = arriveKBN;
	}

	public String getOpenKBN() {
		return openKBN;
	}

	public void setOpenKBN(String openKBN) {
		this.openKBN = openKBN;
	}

	public String getOpenDateTime() {
		return openDateTime;
	}

	public void setOpenDateTime(String openDateTime) {
		this.openDateTime = openDateTime;
	}
}
