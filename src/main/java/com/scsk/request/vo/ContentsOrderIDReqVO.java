package com.scsk.request.vo;

import com.scsk.vo.BaseResVO;

public class ContentsOrderIDReqVO extends BaseResVO{
	private String appCD="";
	private String typeCD="";
	private String contentsID="";
	private String orderID="";
	private String[]orderIDArr;

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

	public String[] getOrderIDArr() {
		return orderIDArr;
	}
	public void setOrderIDArr(String[] orderIDArr) {
		this.orderIDArr = orderIDArr;
	}
	public String getContentsID() {
		return contentsID;
	}
	public void setContentsID(String contentsID) {
		this.contentsID = contentsID;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
}
