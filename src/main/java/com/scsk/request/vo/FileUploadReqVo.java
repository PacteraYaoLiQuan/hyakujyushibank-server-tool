package com.scsk.request.vo;

public class FileUploadReqVo {
    // ID
    private String _id;
    
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String date="";
    
    private String name="";
    
    private String docType="";
    
    private String fileCode="";
    
    private String fileName="";
    
    private String iosORandroid;
        
    private String fileNameJP;
    
    private String fileNameEN;
    
    private String path;
    
    private String uploadDatetime;
    
    private String hopingUseDate;
    
    private String useFlag;
    
    private String batExecuteDatetime;
    private String createdBy;
    // 選択
    private Boolean select;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    private Boolean checkDisable;

    public Boolean getCheckDisable() {
        return checkDisable;
    }

    public void setCheckDisable(Boolean checkDisable) {
        this.checkDisable = checkDisable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private String fileStream="";
    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getIosORandroid() {
        return iosORandroid;
    }

    public void setIosORandroid(String iosORandroid) {
        this.iosORandroid = iosORandroid;
    }

    public String getFileNameJP() {
        return fileNameJP;
    }

    public void setFileNameJP(String fileNameJP) {
        this.fileNameJP = fileNameJP;
    }

    public String getFileNameEN() {
        return fileNameEN;
    }

    public void setFileNameEN(String fileNameEN) {
        this.fileNameEN = fileNameEN;
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
