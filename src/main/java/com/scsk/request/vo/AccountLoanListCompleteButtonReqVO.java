package com.scsk.request.vo;

import java.util.List;

import com.scsk.response.vo.BaseResVO;
import com.scsk.vo.AccountLoanInitVO;

public class AccountLoanListCompleteButtonReqVO extends BaseResVO{

	// 完了消込用一覧
	private List<AccountLoanInitVO> completeList;

    public List<AccountLoanInitVO> getCompleteList() {
        return completeList;
    }

    public void setCompleteList(List<AccountLoanInitVO> completeList) {
        this.completeList = completeList;
    }
	
}