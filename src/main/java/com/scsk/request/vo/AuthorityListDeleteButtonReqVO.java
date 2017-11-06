package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.AuthorityInitVO;

public class AuthorityListDeleteButtonReqVO {

	// 一括削除用一覧
	private List<AuthorityInitVO> deleteList;

	public List<AuthorityInitVO> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<AuthorityInitVO> deleteList) {
		this.deleteList = deleteList;
	}
}