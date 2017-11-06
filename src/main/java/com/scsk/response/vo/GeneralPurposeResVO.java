package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.BaseResVO;
import com.scsk.vo.GeneralPurposeInitVO;

public class GeneralPurposeResVO extends BaseResVO {
    private List<GeneralPurposeInitVO> generalPurposeInitList;

    public List<GeneralPurposeInitVO> getGeneralPurposeInitList() {
        return generalPurposeInitList;
    }

    public void setGeneralPurposeInitList(List<GeneralPurposeInitVO> generalPurposeInitList) {
        this.generalPurposeInitList = generalPurposeInitList;
    }

}
