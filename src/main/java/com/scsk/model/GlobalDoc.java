package com.scsk.model;

import java.util.Map;

import com.scsk.constants.Constants;

/**
 * 汎用DBDOC
 */
public class GlobalDoc extends UtilDoc {

    // ドキュメントタイプ
    private String docType = Constants.GLOBAL_DOC;
    // アプリケーションコード
    private Map<String, String> map;
    private String appCD = "";
    private String typeCD = "";
    private String contentsID = "";
    
    public String getAppCD() {
        return appCD;
    }
    public void setAppCD(String appCD) {
        this.appCD = appCD;
    }
    public String getTypeCD() {
        return typeCD;
    }
    public void setTypeCD(String typeCD) {
        this.typeCD = typeCD;
    }
    public String getContentsID() {
        return contentsID;
    }
    public void setContentsID(String contentsID) {
        this.contentsID = contentsID;
    }
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }
    public Map<String, String> getMap() {
        return map;
    }
    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    
}
