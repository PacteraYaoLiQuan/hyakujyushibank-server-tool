package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.StoreATMInitVO;

public class StoreATMInitResVO extends BaseResVO{

	// 店舗情報一覧初期化表示用
	private List<StoreATMInitVO> StoreATMList;

	public List<StoreATMInitVO> getStoreATMList() {
		return StoreATMList;
	}

	public void setStoreATMList(List<StoreATMInitVO> storeATMList) {
		StoreATMList = storeATMList;
	}

}