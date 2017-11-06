package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.UseUserDownLoadInitVO;

public class UseUserDownLoadCsvButtonResVO {
	// 帳票出力用一覧
	private List<UseUserDownLoadInitVO> useUserDownLoadList;
	// 帳票用日付
	private String date;

	public List<UseUserDownLoadInitVO> getUseUserDownLoadList() {
		return useUserDownLoadList;
	}

	public void setUseUserDownLoadList(List<UseUserDownLoadInitVO> useUserDownLoadList) {
		this.useUserDownLoadList = useUserDownLoadList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
