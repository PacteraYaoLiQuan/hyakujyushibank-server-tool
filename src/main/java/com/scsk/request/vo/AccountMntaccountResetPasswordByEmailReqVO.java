package com.scsk.request.vo;

/**
 * パスワード忘れリクエストデータ
 * @author ylq
 *
 */
public class AccountMntaccountResetPasswordByEmailReqVO {

    // E-mail
    private String email;
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
