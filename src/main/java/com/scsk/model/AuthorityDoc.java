package com.scsk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 権限DOC
 */
public class AuthorityDoc extends UtilDoc {
	// ドキュメントタイプ
	private String docType = "AUTHORITY";
	// 権限名
	private String authorityName = "";
	// アクセス機能ID
	private List<String> functionIDList = new ArrayList<String>();
	// アクセス機能権限
	private List<String> functionValueList = new ArrayList<String>();

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

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
