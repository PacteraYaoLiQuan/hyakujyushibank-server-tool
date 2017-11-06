package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.UseUserListInitVO;

public class UseUserListCsvButtonReqVO {
	// CSVデータ出力用一覧
	private List<UseUserListInitVO> csvList;

	public List<UseUserListInitVO> getCsvList() {
		return csvList;
	}

	public void setCsvList(List<UseUserListInitVO> csvList) {
		this.csvList = csvList;
	}

}
