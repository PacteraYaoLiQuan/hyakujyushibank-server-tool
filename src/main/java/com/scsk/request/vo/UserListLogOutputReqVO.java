package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.UserInitVO;

public class UserListLogOutputReqVO {

	// ログ出力用一覧
    private String startTime;
    private String endTime;
    private List<UserInitVO> logOutputList;
	public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
	public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public List<UserInitVO> getLogOutputList() {
		return logOutputList;
	}

	public void setLogOutputList(List<UserInitVO> logOutputList) {
		this.logOutputList = logOutputList;
	}

}