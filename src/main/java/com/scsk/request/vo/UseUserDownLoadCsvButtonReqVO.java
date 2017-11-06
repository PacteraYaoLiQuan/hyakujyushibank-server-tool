package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.UseUserDownLoadInitVO;

public class UseUserDownLoadCsvButtonReqVO {
	// CSVデータ出力用一覧
	private List<UseUserDownLoadInitVO> csvList;

	public List<UseUserDownLoadInitVO> getCsvList() {
		return csvList;
	}

	public void setCsvList(List<UseUserDownLoadInitVO> csvList) {
		this.csvList = csvList;
	}
}
