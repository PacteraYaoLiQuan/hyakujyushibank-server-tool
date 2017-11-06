package com.scsk.request.vo;

import java.util.List;

import com.scsk.response.vo.BaseResVO;
import com.scsk.vo.AccountAppInitVO;
import com.scsk.vo.AccountAppYamaGataInitVO;

public class AccountAppListCompleteButtonReqVO extends BaseResVO{

	// 完了消込用一覧
	private List<AccountAppInitVO> completeList;
	
	private List<AccountAppYamaGataInitVO> completeList2;

	public List<AccountAppInitVO> getCompleteList() {
		return completeList;
	}

	public void setCompleteList(List<AccountAppInitVO> completeList) {
		this.completeList = completeList;
	}

	public List<AccountAppYamaGataInitVO> getCompleteList2() {
		return completeList2;
	}

	public void setCompleteList2(List<AccountAppYamaGataInitVO> completeList2) {
		this.completeList2 = completeList2;
	}
	
}