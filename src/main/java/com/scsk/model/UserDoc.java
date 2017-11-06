package com.scsk.model;

import com.scsk.constants.Constants;

/**
 * ユーザーDOC
 */
public class UserDoc extends UtilDoc {

	// ドキュメントタイプ
	private String docType = Constants.USER_DOCTYPE;
	// ユーザーID
	private String userID = "";
	// ユーザー名
	private String userName = "";
	// パスワード
	private String password = "";
	// パスワード種別
	private int changePasswordFlg = 0;
	// メールアドレス
	private String email = "";
	// 所属権限名
	private String authority = "";
	// ログイン状態
	private int loginStatus = 0;
	// 最終ログイン日時
	private String endLoginDateTime = "";
	// 最終パスワード変更日時
	private String endPasswordChangeDateTime = "";
	// ロック状態
	private boolean lockStatus = true;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getChangePasswordFlg() {
		return changePasswordFlg;
	}

	public void setChangePasswordFlg(int changePasswordFlg) {
		this.changePasswordFlg = changePasswordFlg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public int getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getEndLoginDateTime() {
		return endLoginDateTime;
	}

	public void setEndLoginDateTime(String endLoginDateTime) {
		this.endLoginDateTime = endLoginDateTime;
	}

	public String getEndPasswordChangeDateTime() {
		return endPasswordChangeDateTime;
	}

	public void setEndPasswordChangeDateTime(String endPasswordChangeDateTime) {
		this.endPasswordChangeDateTime = endPasswordChangeDateTime;
	}

	public boolean isLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(boolean lockStatus) {
		this.lockStatus = lockStatus;
	}
}