package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountLoanInitVO;

public class AccountLoanInitResVO extends BaseResVO {

    // ローン申込み一覧初期化表示用
    private List<AccountLoanInitVO> accountLoanList;

    public List<AccountLoanInitVO> getAccountLoanList() {
        return accountLoanList;
    }

    public void setAccountLoanList(List<AccountLoanInitVO> accountLoanList) {
        this.accountLoanList = accountLoanList;
    }

}
