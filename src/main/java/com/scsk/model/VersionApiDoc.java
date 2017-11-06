package com.scsk.model;

import java.util.ArrayList;
import java.util.List;

import com.scsk.constants.Constants;

public class VersionApiDoc extends UtilDoc{

    // ドキュメントタイプ
    private String docType = Constants.VERSIONDOC_DOCTYPE;
    // バージョン
    private List<String> versionList = new ArrayList<>() ;
    // メッセージ
    private String message = "";
    // 設備区分　　　
    private String deviceDistinguish = "";
    // バージョン数
    private String newVersion = ""; 
    
    public List<String> getVersionList() {
        return versionList;
    }
    public void setVersionList(List<String> versionList) {
        this.versionList = versionList;
    }
    public String getNewVersion() {
        return newVersion;
    }
    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getDeviceDistinguish() {
        return deviceDistinguish;
    }
    public void setDeviceDistinguish(String deviceDistinguish) {
        this.deviceDistinguish = deviceDistinguish;
    }
}
