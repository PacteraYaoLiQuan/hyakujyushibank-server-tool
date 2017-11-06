package com.scsk.response.vo;

public class PasswordResetResVO {
    // ユーザーID
    private String userID;
 // ユーザーID
    private String showUserID;
    // ユーザー名
    private String userName;
    // パスワード
    private String password;
    // パスワード種別
    private int passwordType;
    // メールアドレス
    private String email;
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
    public String getShowUserID() {
        return showUserID;
    }
    public void setShowUserID(String showUserID) {
        this.showUserID = showUserID;
    }

}
