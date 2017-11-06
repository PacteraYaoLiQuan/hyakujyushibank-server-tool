package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.BaseResVO;

public class ContentsTypeCDListResVO extends BaseResVO{

	private List<String>typeCDList;

	public List<String> getTypeCDList() {
		return typeCDList;
	}

	public void setTypeCDList(List<String> typeCDList) {
		this.typeCDList = typeCDList;
	}

}
