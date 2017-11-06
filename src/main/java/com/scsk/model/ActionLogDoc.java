package com.scsk.model;

public class ActionLogDoc extends UtilDoc {
    
    private String docType = "ACTIONLOG";
    
    private String accessDatetime = "";
    
    private String userID = "";
    
    private String userName = "";
    
    private String actionLog = "";

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getAccessDatetime() {
        return accessDatetime;
    }

    public void setAccessDatetime(String accessDatetime) {
        this.accessDatetime = accessDatetime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActionLog() {
        return actionLog;
    }

    public void setActionLog(String actionLog) {
        this.actionLog = actionLog;
    }

}
