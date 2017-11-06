package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountAppYamaGataInitVO;

public class AccountYamaGataAppInitResVO extends BaseResVO {

	// 申込一覧初期化表示用
	private List<AccountAppYamaGataInitVO> AccountAppList;

	public List<AccountAppYamaGataInitVO> getAccountAppList() {
		return AccountAppList;
	}

	public void setAccountAppList(List<AccountAppYamaGataInitVO> accountAppList) {
		AccountAppList = accountAppList;
	}
	
}