package com.scsk.model;

import com.scsk.constants.Constants;

public class ContentsAppDoc extends UtilDoc {
	private String docType = Constants.DOCTYPE_6;
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
