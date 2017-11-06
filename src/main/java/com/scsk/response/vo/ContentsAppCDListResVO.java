package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.BaseResVO;

public class ContentsAppCDListResVO extends BaseResVO {
	private List<String>appCDList;

	public List<String> getAppCDList() {
		return appCDList;
	}

	public void setAppCDList(List<String> appCDList) {
		this.appCDList = appCDList;
	}
}
