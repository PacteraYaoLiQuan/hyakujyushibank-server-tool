package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountAppInitVO;

public class AccountAppInitResVO extends BaseResVO {

	// 申込一覧初期化表示用
	private List<AccountAppInitVO> AccountAppList;

	public List<AccountAppInitVO> getAccountAppList() {
		return AccountAppList;
	}

	public void setAccountAppList(List<AccountAppInitVO> accountAppList) {
		AccountAppList = accountAppList;
	}

}