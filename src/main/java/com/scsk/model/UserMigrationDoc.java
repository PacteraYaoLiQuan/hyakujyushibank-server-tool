package com.scsk.model;
/**
 * 
 * ユーザー移行DOC
 *
 */
public class UserMigrationDoc extends UtilDoc{

    // 
    private String docType = "";
    // 
    private String oldUser = "";
    // 
    private String newUser = "";
    // 
    private String flag = "";
    
    
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }
    public String getOldUser() {
        return oldUser;
    }
    public void setOldUser(String oldUser) {
        this.oldUser = oldUser;
    }
    public String getNewUser() {
        return newUser;
    }
    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
}
