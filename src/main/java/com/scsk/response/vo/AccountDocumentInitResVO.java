package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountDocument114InitVO;

public class AccountDocumentInitResVO extends BaseResVO{


    // 申込一覧初期化表示用
    private List<AccountDocument114InitVO> AccountDocumentList;

    public List<AccountDocument114InitVO> getAccountDocumentList() {
        return AccountDocumentList;
    }

    public void setAccountDocumentList(List<AccountDocument114InitVO> accountDocumentList) {
        AccountDocumentList = accountDocumentList;
    }
    
}
