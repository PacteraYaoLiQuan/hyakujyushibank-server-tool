package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.UserListLogCsvInitVO;

public class UserListLogCsvOutputResVO {
 // ログ出力用一覧
    private List<UserListLogCsvInitVO> userListLogCsvOutput;
 // 帳票用日付
    private String date;
    public List<UserListLogCsvInitVO> getUserListLogCsvOutput() {
        return userListLogCsvOutput;
    }
    public void setUserListLogCsvOutput(List<UserListLogCsvInitVO> userListLogCsvOutput) {
        this.userListLogCsvOutput = userListLogCsvOutput;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
