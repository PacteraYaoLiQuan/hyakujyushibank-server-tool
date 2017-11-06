package com.scsk.request.vo;

public class PasswordUpdReqVO {

	// 旧パスワード
	private String password;
	// 新パスワード
	private String newPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}