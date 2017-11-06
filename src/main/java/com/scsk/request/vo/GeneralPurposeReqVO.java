package com.scsk.request.vo;

import com.scsk.vo.BaseResVO;

public class GeneralPurposeReqVO extends BaseResVO {
    private String dateFrom;
    private String dateTo;

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
