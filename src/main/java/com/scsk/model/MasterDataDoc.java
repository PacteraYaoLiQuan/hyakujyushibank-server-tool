package com.scsk.model;

import com.scsk.constants.Constants;

public class MasterDataDoc extends UtilDoc{
    
    private String docType =Constants.DOCTYPE_5;
    
    private String fileName="";
    
    private String path="";
    
    private String uploadDatetime="";
    
    private String hopingUseDate="";
    
    private String useFlag="";
    
    private String batExecuteDatetime="";
    
    private String fileStream="";

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
