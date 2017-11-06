package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountApp114InitVO;

public class Account114AppInitResVO extends BaseResVO {


    // 申込一覧初期化表示用
    private List<AccountApp114InitVO> AccountAppList;

    public List<AccountApp114InitVO> getAccountAppList() {
        return AccountAppList;
    }

    public void setAccountAppList(List<AccountApp114InitVO> accountAppList) {
        AccountAppList = accountAppList;
    }
    

}
