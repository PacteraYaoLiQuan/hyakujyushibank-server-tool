package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountAppYamaGataInitVO;

public class AccountYamaGataAppListCsvButtonResVO extends BaseResVO{

	// 帳票出力用一覧
	private List<AccountAppYamaGataInitVO> AccountAppList;

	// 帳票用日付
	private String date;

	public List<AccountAppYamaGataInitVO> getAccountAppList() {
		return AccountAppList;
	}

	public void setAccountAppList(List<AccountAppYamaGataInitVO> accountAppList) {
		AccountAppList = accountAppList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}