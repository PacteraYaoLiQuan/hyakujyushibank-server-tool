package com.scsk.response.vo;

import com.scsk.vo.BaseResVO;

public class ContentsAppUpdateResVO extends BaseResVO{

	private String docType = "";
	private String appCD = "";
	private String appName = "";
	private int userFlag = 0;

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getAppCD() {
		return appCD;
	}

	public void setAppCD(String appCD) {
		this.appCD = appCD;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(int userFlag) {
		this.userFlag = userFlag;
	}



}
