package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.FunctionVO;

public class AuthorityDetailStatusReqVO {
	// id
	private String _id;
	// 編集/新規mode
	private String modeType;
	// 権限名
	private String authorityName;
	// 権限詳細画面機能一覧表示用
	private List<FunctionVO> functionList;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

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
