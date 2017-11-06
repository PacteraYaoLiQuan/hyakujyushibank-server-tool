package com.scsk.model;

/**
 * 匿名ユーザー口座集計DOC
 */
public class AccountSumDoc extends UtilDoc {

	// ドキュメントタイプ
	private String docType = "";
	// 口座申込数
	private int accountAppCount = 0;

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public int getAccountAppCount() {
		return accountAppCount;
	}

	public void setAccountAppCount(int accountAppCount) {
		this.accountAppCount = accountAppCount;
	}

}
