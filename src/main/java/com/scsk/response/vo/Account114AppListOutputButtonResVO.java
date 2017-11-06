package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountApp114InitVO;

public class Account114AppListOutputButtonResVO extends BaseResVO{
    // 帳票出力用一覧
    private List<AccountApp114InitVO> AccountAppList;
    
    //帳票用日付
    private String date;

    public List<AccountApp114InitVO> getAccountAppList() {
        return AccountAppList;
    }

    public void setAccountAppList(List<AccountApp114InitVO> accountAppList) {
        AccountAppList = accountAppList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
