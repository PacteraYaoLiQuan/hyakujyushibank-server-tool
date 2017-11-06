package com.scsk.vo;

import com.scsk.constants.Constants;

public class ContentsAppInitVO {
	private Boolean select;
	private String _id="";
	private String docType = Constants.DOCTYPE_6;
	private String appCD = "";
	private String appName = "";
	private int userFlag = 0;

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

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
