package com.scsk.request.vo;

import java.util.List;

import com.scsk.response.vo.BaseResVO;
import com.scsk.vo.AccountApp114InitVO;
import com.scsk.vo.AccountAppInitVO;
import com.scsk.vo.AccountAppYamaGataInitVO;

public class AccountAppListOutputButtonReqVO extends BaseResVO {

	// 帳票出力用一覧
	private List<AccountAppInitVO> outputList;
	
	private List<AccountAppYamaGataInitVO> outputList2;

	private List<AccountApp114InitVO> outputList3;
	
	public List<AccountApp114InitVO> getOutputList3() {
        return outputList3;
    }

    public void setOutputList3(List<AccountApp114InitVO> outputList3) {
        this.outputList3 = outputList3;
    }

    public List<AccountAppInitVO> getOutputList() {
		return outputList;
	}

	public void setOutputList(List<AccountAppInitVO> outputList) {
		this.outputList = outputList;
	}

	public List<AccountAppYamaGataInitVO> getOutputList2() {
		return outputList2;
	}

	public void setOutputList2(List<AccountAppYamaGataInitVO> outputList2) {
		this.outputList2 = outputList2;
	}

}