package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.PushMessagePushTypeVO;

public class PushMessagePushTypeResVO {
    private List<PushMessagePushTypeVO> pushTypeList;

    public List<PushMessagePushTypeVO> getPushTypeList() {
        return pushTypeList;
    }

    public void setPushTypeList(List<PushMessagePushTypeVO> pushTypeList) {
        this.pushTypeList = pushTypeList;
    }
}
