package com.scsk.request.vo;

import java.util.List;

import com.scsk.response.vo.BaseResVO;
import com.scsk.vo.IYoStoreATMInitVO;
import com.scsk.vo.StoreATMInitVO;

public class StoreATMListDeleteButtonReqVO extends BaseResVO {

	// 一括削除用一覧
	private List<StoreATMInitVO> deleteList;
	
	private List<IYoStoreATMInitVO> iYoDeleteList;
	
	public List<IYoStoreATMInitVO> getiYoDeleteList() {
        return iYoDeleteList;
    }

    public void setiYoDeleteList(List<IYoStoreATMInitVO> iYoDeleteList) {
        this.iYoDeleteList = iYoDeleteList;
    }

    public List<StoreATMInitVO> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<StoreATMInitVO> deleteList) {
		this.deleteList = deleteList;
	}
}