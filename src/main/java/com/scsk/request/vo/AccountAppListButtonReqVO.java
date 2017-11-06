package com.scsk.request.vo;

public class AccountAppListButtonReqVO {

	// ステータス
	private String[] status;
	// 申込受付日付
	private String receiptDate;

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
}