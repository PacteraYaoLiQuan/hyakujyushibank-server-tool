package com.scsk.model;

import com.scsk.constants.Constants;

/**
 * パスワード変更履歴DOC
 */
public class PwdHistoryDoc extends UtilDoc {
	// ドキュメントタイプ
	private String docType = Constants.PWDHISTORY_DOCTYPE;
	// ユーザーId
	private String userID = "";
	// パスワード
	private String password = "";
	// パスワード変更日時
	private String passwordChangeDateTime = "";

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordChangeDateTime() {
		return passwordChangeDateTime;
	}

	public void setPasswordChangeDateTime(String passwordChangeDateTime) {
		this.passwordChangeDateTime = passwordChangeDateTime;
	}

}
