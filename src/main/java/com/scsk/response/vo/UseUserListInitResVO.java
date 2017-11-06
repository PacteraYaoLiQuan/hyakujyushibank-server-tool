package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.UseUserListInitVO;

public class UseUserListInitResVO {
	// Pushメッセージ一覧初期化表示用
	private List<UseUserListInitVO> useUserList;

	public List<UseUserListInitVO> getUseUserList() {
		return useUserList;
	}

	public void setUseUserList(List<UseUserListInitVO> useUserList) {
		this.useUserList = useUserList;
	}

}
