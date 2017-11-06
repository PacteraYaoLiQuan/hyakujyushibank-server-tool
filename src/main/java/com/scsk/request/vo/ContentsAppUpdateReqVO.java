package com.scsk.request.vo;

import com.scsk.vo.BaseResVO;

public class ContentsAppUpdateReqVO extends BaseResVO{
	private String _id;
	private String modeType;
	private String docType = "";
	private String appCD = "";
	private String appName = "";
	private int userFlag = 0;

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
