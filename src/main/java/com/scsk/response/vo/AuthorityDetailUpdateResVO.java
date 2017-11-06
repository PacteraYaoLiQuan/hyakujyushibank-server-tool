package com.scsk.response.vo;

import java.util.List;

public class AuthorityDetailUpdateResVO {

	// 権限名
	private String authorityName;
	// アクセス機能ID
	private List<String> functionIDList;
	// アクセス機能権限
	private List<String> functionValueList;

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public List<String> getFunctionIDList() {
		return functionIDList;
	}

	public void setFunctionIDList(List<String> functionIDList) {
		this.functionIDList = functionIDList;
	}

	public List<String> getFunctionValueList() {
		return functionValueList;
	}

	public void setFunctionValueList(List<String> functionValueList) {
		this.functionValueList = functionValueList;
	}
}
