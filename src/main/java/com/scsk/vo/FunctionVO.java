package com.scsk.vo;

public class FunctionVO {

	// 機能名
	private String functionName;
	// 機能ID
	private String functionID;
	// ラジオボタン名
	private String radioName;
	// 機能の権限
	private String functionValue;
	
	private String orderBy;

	public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public String getRadioName() {
		return radioName;
	}

	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}

	public String getFunctionValue() {
		return functionValue;
	}

	public void setFunctionValue(String functionValue) {
		this.functionValue = functionValue;
	}
}