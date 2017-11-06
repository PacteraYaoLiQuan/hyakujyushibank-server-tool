package com.scsk.request.vo;

import java.util.List;

import com.scsk.response.vo.BaseResVO;
import com.scsk.vo.AccountAppInitVO;
import com.scsk.vo.AccountAppYamaGataInitVO;

public class AccountAppListCsvButtonReqVO extends BaseResVO {

	// CSVデータ出力用一覧
	private List<AccountAppInitVO> csvList;
	
	private List<AccountAppYamaGataInitVO> csvList2;

	private List<AccountAppYamaGataInitVO> csvList3;
	
	public List<AccountAppYamaGataInitVO> getCsvList3() {
        return csvList3;
    }

    public void setCsvList3(List<AccountAppYamaGataInitVO> csvList3) {
        this.csvList3 = csvList3;
    }

    public List<AccountAppInitVO> getCsvList() {
		return csvList;
	}

	public void setCsvList(List<AccountAppInitVO> csvList) {
		this.csvList = csvList;
	}

	public List<AccountAppYamaGataInitVO> getCsvList2() {
		return csvList2;
	}

	public void setCsvList2(List<AccountAppYamaGataInitVO> csvList2) {
		this.csvList2 = csvList2;
	}

}