package com.scsk.vo;

public class UserListLogCsvInitVO {
    //ID
    private String _id;
    // バージョン
    private String _rev;
    // 選択
    private Boolean select;
    // ドキュメントタイプ
    private String docType;
    // 操作日時
    private String accessDatetime;
    // ユーザーID
    private String userID;
    // ユーザー名
    private String userName;
    // 操作／行動ログ
    private String actionLog;
    // 作成者
    private String createdBy;
    // 作成日時
    private String createdDate;
    // 更新者
    private String updatedBy;
    // 更新日時
    private String updatedDate;
    // 削除フラグ
    private String delFlg;
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String get_rev() {
        return _rev;
    }
    public void set_rev(String _rev) {
        this._rev = _rev;
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
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public String getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
    public String getDelFlg() {
        return delFlg;
    }
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

}
