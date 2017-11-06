package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.BaseResVO;
import com.scsk.vo.ContentsTypeInitVO;

public class ContentsTypeInitResVO extends BaseResVO {

	private List<ContentsTypeInitVO> contentsTypeInitList;

	public List<ContentsTypeInitVO> getContentsTypeInitList() {
		return contentsTypeInitList;
	}

	public void setContentsTypeInitList(List<ContentsTypeInitVO> contentsTypeInitList) {
		this.contentsTypeInitList = contentsTypeInitList;
	}
	

}
