package com.scsk.response.vo;

import java.util.HashMap;
import java.util.Map;

import com.scsk.vo.BaseResVO;

public class GeneralPurposeOutCsvResVO extends BaseResVO {
    private Map<String, String> map = new HashMap<>();
    private String Date;
    private String createData;
    private String outPutFileName;
    private String _id;
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOutPutFileName() {
        return outPutFileName;
    }

    public void setOutPutFileName(String outPutFileName) {
        this.outPutFileName = outPutFileName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    public String getCreateData() {
        return createData;
    }
    public void setCreateData(String createData) {
        this.createData = createData;
    }
}
