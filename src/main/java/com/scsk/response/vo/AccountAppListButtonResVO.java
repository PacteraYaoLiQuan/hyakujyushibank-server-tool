package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountAppInitVO;

public class AccountAppListButtonResVO {

	// 受付進捗管理表出力用List
	private List<AccountAppInitVO> AccountAppList;

	// 帳票用日付
	private String date;

	public List<AccountAppInitVO> getAccountAppList() {
		return AccountAppList;
	}

	public void setAccountAppList(List<AccountAppInitVO> accountAppList) {
		AccountAppList = accountAppList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}