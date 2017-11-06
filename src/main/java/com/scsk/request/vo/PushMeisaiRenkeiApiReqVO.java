package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.TorihikiMeisaiDataVO;

public class PushMeisaiRenkeiApiReqVO {

    // 取引データ数量
    private int torihikiDataCount;
    // 取引データリスト
    private List<TorihikiMeisaiDataVO> torihikiDataList;
    private String meisaiRenkeiKey;

    public String getMeisaiRenkeiKey() {
        return meisaiRenkeiKey;
    }

    public void setMeisaiRenkeiKey(String meisaiRenkeiKey) {
        this.meisaiRenkeiKey = meisaiRenkeiKey;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    private String appID;

    public int getTorihikiDataCount() {
        return torihikiDataCount;
    }

    public void setTorihikiDataCount(int torihikiDataCount) {
        this.torihikiDataCount = torihikiDataCount;
    }

    public List<TorihikiMeisaiDataVO> getTorihikiDataList() {
        return torihikiDataList;
    }

    public void setTorihikiDataList(List<TorihikiMeisaiDataVO> torihikiDataList) {
        this.torihikiDataList = torihikiDataList;
    }
}