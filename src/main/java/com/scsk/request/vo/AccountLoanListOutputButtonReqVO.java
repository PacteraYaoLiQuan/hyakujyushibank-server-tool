package com.scsk.request.vo;

import java.util.List;

import com.scsk.response.vo.BaseResVO;
import com.scsk.vo.AccountAppInitVO;

public class AccountLoanListOutputButtonReqVO extends BaseResVO {

    // 帳票出力用一覧
    private List<AccountAppInitVO> outputList;

    public List<AccountAppInitVO> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<AccountAppInitVO> outputList) {
        this.outputList = outputList;
    }

}