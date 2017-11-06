package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.UserInitVO;

public class UserListDeleteReqVO {

	// 一括削除用一覧
	private List<UserInitVO> deleteList;

	public List<UserInitVO> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<UserInitVO> deleteList) {
		this.deleteList = deleteList;
	}
}