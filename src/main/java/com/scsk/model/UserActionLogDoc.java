package com.scsk.model;

import com.scsk.constants.Constants;

public class UserActionLogDoc {
	// ドキュメントタイプ
	private String docType = Constants.USERACTIONLOG_DOCTYPE;
	// 作成日時
	private String createdDate = "";
	// ユーザーID
	private String userID = "";
	// ユーザー名
	private String userName = "";
	// アクセス画面名
	private String accessScreen = "";
	// ボタン名
	private String buttonName = "";

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccessScreen() {
		return accessScreen;
	}

	public void setAccessScreen(String accessScreen) {
		this.accessScreen = accessScreen;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

}
