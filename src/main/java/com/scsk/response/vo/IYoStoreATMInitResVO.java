package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.IYoStoreATMInitVO;

public class IYoStoreATMInitResVO extends BaseResVO{
    // 店舗情報一覧初期化表示用
    private List<IYoStoreATMInitVO> StoreATMList;

    public List<IYoStoreATMInitVO> getStoreATMList() {
        return StoreATMList;
    }

    public void setStoreATMList(List<IYoStoreATMInitVO> storeATMList) {
        StoreATMList = storeATMList;
    }

}
