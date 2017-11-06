package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.BaseResVO;
import com.scsk.vo.ContentsInitVO;

public class ContentsInitResVO extends BaseResVO {
	private List<ContentsInitVO> contentsInitList;

	public List<ContentsInitVO> getContentsInitList() {
		return contentsInitList;
	}

	public void setContentsInitList(List<ContentsInitVO> contentsInitList) {
		this.contentsInitList = contentsInitList;
	}
}
