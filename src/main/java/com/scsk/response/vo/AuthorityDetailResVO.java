package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.FunctionVO;

public class AuthorityDetailResVO {
	// 権限名
	private String authorityName = "";
	// 権限詳細画面機能一覧表示用
	private List<FunctionVO> functionList;

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public List<FunctionVO> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<FunctionVO> functionList) {
		this.functionList = functionList;
	}
}
