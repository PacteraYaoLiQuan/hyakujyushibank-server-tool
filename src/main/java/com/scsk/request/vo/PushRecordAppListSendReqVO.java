package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.AccountAppPushListVO;

public class PushRecordAppListSendReqVO {
    // 配信失敗count
    private String  errFlag;
    // 通知確認一覧
	private List<AccountAppPushListVO> sendList;

    public List<AccountAppPushListVO> getSendList() {
        return sendList;
    }
    public void setSendList(List<AccountAppPushListVO> sendList) {
        this.sendList = sendList;
    }
    public String getErrFlag() {
        return errFlag;
    }
    public void setErrFlag(String errFlag) {
        this.errFlag = errFlag;
    }
}