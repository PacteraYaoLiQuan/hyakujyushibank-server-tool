package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.UseUserListInitVO;

public class UseUserListCsvButtonResVO {
	// 帳票出力用一覧
	private List<UseUserListInitVO> UseUserList;
	// 帳票用日付
	private String date;

	public List<UseUserListInitVO> getUseUserList() {
		return UseUserList;
	}

	public void setUseUserList(List<UseUserListInitVO> useUserList) {
		UseUserList = useUserList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
