package com.scsk.response.vo;

import com.scsk.vo.BaseResVO;

public class ContentsVersionCodeResVO extends BaseResVO{
	private int[] version=new int[1000];

	public int[] getVersion() {
		return version;
	}

	public void setVersion(int[] version) {
		this.version = version;
	}
}
