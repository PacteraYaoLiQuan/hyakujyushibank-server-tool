package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.UserInitVO;

public class UserInitResVO {

	// ユーザ一覧初期化表示用
	private List<UserInitVO> UserList;
	// 所属権限名一覧
	private List<String> authorityList;

	// SessionユーザーID
	private String sessionUserID;

	public List<UserInitVO> getUserList() {
		return UserList;
	}

	public void setUserList(List<UserInitVO> userList) {
		UserList = userList;
	}

	public List<String> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<String> authorityList) {
		this.authorityList = authorityList;
	}

	public String getSessionUserID() {
		return sessionUserID;
	}

	public void setSessionUserID(String sessionUserID) {
		this.sessionUserID = sessionUserID;
	}

}