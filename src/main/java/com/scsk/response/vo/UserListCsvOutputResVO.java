package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.UserInitVO;

public class UserListCsvOutputResVO {
 // ログ出力用一覧
    private List<UserInitVO> UserListCsvOutput;
 // 帳票用日付
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<UserInitVO> getUserListCsvOutput() {
        return UserListCsvOutput;
    }

    public void setUserListCsvOutput(List<UserInitVO> userListCsvOutput) {
        UserListCsvOutput = userListCsvOutput;
    }

}
