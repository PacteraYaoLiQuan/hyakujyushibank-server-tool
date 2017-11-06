package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AuthorityInitVO;

public class AuthorityInitResVO {

	// 権限一覧初期化表示用
	private List<AuthorityInitVO> AuthorityList;

	public List<AuthorityInitVO> getAuthorityList() {
		return AuthorityList;
	}

	public void setAuthorityList(List<AuthorityInitVO> authorityList) {
		AuthorityList = authorityList;
	}
}