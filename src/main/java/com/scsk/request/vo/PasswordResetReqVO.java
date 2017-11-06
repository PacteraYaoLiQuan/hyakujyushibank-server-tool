package com.scsk.request.vo;

public class PasswordResetReqVO {
    // id
    private String _id;
    // 編集/新規mode
    private String modeType;
    // ユーザーID
    private String userID;
    // ユーザー名
    private String userName;
    // パスワード
    private String password;
    // パスワード種別
    private int passwordType;
    // メールアドレス
    private String email;
    
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String getModeType() {
        return modeType;
    }
    public void setModeType(String modeType) {
        this.modeType = modeType;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getPasswordType() {
        return passwordType;
    }
    public void setPasswordType(int passwordType) {
        this.passwordType = passwordType;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
