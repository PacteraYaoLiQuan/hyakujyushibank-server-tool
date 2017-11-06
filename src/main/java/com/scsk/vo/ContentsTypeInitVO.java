package com.scsk.vo;

import com.scsk.constants.Constants;

public class ContentsTypeInitVO {
	private Boolean select;
	private String _id="";
	private String docType = Constants.DOCTYPE_7;
	private String appCD = "";
	private String typeCD = "";
	private String typeName = "";
	
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
