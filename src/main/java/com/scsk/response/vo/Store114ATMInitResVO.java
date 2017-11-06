package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.Store114ATMInitVO;

public class Store114ATMInitResVO  extends BaseResVO{
    // 店舗情報一覧初期化表示用
    private List<Store114ATMInitVO> StoreATMList;

    public List<Store114ATMInitVO> getStoreATMList() {
        return StoreATMList;
    }

    public void setStoreATMList(List<Store114ATMInitVO> storeATMList) {
        StoreATMList = storeATMList;
    }

}
