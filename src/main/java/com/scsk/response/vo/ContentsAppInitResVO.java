package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.BaseResVO;
import com.scsk.vo.ContentsAppInitVO;

public class ContentsAppInitResVO extends BaseResVO{
	private List<ContentsAppInitVO> contentsAppInitList;

	public List<ContentsAppInitVO> getContentsAppInitList() {
		return contentsAppInitList;
	}

	public void setContentsAppInitList(List<ContentsAppInitVO> contentsAppInitList) {
		this.contentsAppInitList = contentsAppInitList;
	}
	
}
