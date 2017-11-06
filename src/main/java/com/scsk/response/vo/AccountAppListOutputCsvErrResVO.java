package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.ErrVO;

public class AccountAppListOutputCsvErrResVO {

	// エラーメッセージ用一覧
	private List<ErrVO> ErrList;

	public List<ErrVO> getErrList() {
		return ErrList;
	}

	public void setErrList(List<ErrVO> errList) {
		ErrList = errList;
	}
}