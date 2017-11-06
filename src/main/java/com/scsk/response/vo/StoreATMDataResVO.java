package com.scsk.response.vo;

import java.util.List;

import com.scsk.constants.Constants;
import com.scsk.request.vo.StoreATMDataReqVO;

public class StoreATMDataResVO {

    private String _id="";

    private String docType =Constants.DOCTYPE_5;
    
    private String fileName="";
    
    private String path="";
    
    private String uploadDatetime="";
    
    private String hopingUseDate="";
    
    private String useFlag="";
    
    private String batExecuteDatetime="";
    
    private String fileStream="";
    
    private String name="";
    
    private String date="";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUploadDatetime() {
        return uploadDatetime;
    }

    public void setUploadDatetime(String uploadDatetime) {
        this.uploadDatetime = uploadDatetime;
    }

    public String getHopingUseDate() {
        return hopingUseDate;
    }

    public void setHopingUseDate(String hopingUseDate) {
        this.hopingUseDate = hopingUseDate;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public String getBatExecuteDatetime() {
        return batExecuteDatetime;
    }

    public void setBatExecuteDatetime(String batExecuteDatetime) {
        this.batExecuteDatetime = batExecuteDatetime;
    }

    public String getFileStream() {
        return fileStream;
    }

    public void setFileStream(String fileStream) {
        this.fileStream = fileStream;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 初期化表示用
    private List<StoreATMDataReqVO> fileFindListReqVo;

    public List<StoreATMDataReqVO> getFileFindListReqVo() {
        return fileFindListReqVo;
    }

    public void setFileFindListReqVo(List<StoreATMDataReqVO> fileFindListReqVo) {
        this.fileFindListReqVo = fileFindListReqVo;
    }

}
