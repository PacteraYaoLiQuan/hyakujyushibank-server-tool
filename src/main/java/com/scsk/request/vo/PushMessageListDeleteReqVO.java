package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.PushMessageInitVO;

public class PushMessageListDeleteReqVO {
	// 一括削除用一覧
	private List<PushMessageInitVO> deleteList;

	public List<PushMessageInitVO> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<PushMessageInitVO> deleteList) {
		this.deleteList = deleteList;
	}
}
