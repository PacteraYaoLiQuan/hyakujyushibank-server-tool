package com.scsk.model;

/**
 * 機能DOC
 */
public class FunctionDoc extends UtilDoc {
	// ドキュメントタイプ
	private String docType = "FUNCTION";
	// 機能ID
	private String functionID = "";
	// 機能名
	private String functionName = "";
	// ソート順
	private String orderBy = "";

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
