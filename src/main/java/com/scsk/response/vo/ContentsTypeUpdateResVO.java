package com.scsk.response.vo;

import com.scsk.vo.BaseResVO;

public class ContentsTypeUpdateResVO extends BaseResVO{

	private String docType = "";
	private String appCD = "";
	private String typeCD = "";
	private String typeName = "";
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
