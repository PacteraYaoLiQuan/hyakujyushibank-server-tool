package com.scsk.request.vo;

import com.scsk.vo.BaseResVO;

public class ContentsTypeUpdateReqVO extends BaseResVO{
	private String _id;
	private String modeType;
	private String appCD = "";
	private String typeCD = "";
	private String typeName = "";

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
	public String getAppCD() {
		return appCD;
	}
	public void setAppCD(String appCD) {
		this.appCD = appCD;
	}
	public String getTypeCD() {
		return typeCD;
	}
	public void setTypeCD(String typeCD) {
		this.typeCD = typeCD;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	

}
