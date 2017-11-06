package com.scsk.request.vo;

public class StoreATMDataReqVO {
    
    private String _id="";

    private String docType ="";
    
    private String fileName="";
    
    private String path="";
    
    private String uploadDatetime="";
    
    private String hopingUseDate="";
    
    private String useFlag="";
    
    private String batExecuteDatetime="";
    
    private String fileStream="";
    
    private Boolean select;
    
    private Boolean checkDisable;
    
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getCheckDisable() {
        return checkDisable;
    }

    public void setCheckDisable(Boolean checkDisable) {
        this.checkDisable = checkDisable;
    }
    
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
    
    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
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
}
