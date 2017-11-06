package com.scsk.util;

/**
 * 電子メールを送信します
 * @author yql
 *
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scsk.config.SendMailConfig;
import com.scsk.util.AbsSendMail.MailType;
import com.sendgrid.smtpapi.SMTPAPI;

/**
 * @author yql
 *
 */
@Component
public class SendMail {
    @Autowired
    private SendMailConfig sendMailConfig;

//    SimpleDateFormat sdf = new SimpleDateFormat(
//            Constants.DATE_SENDEMAIL);
//    String date = sdf.format(new Date());
    // アカウント登録
    public void sendMailForRegist(String toMail, String name) {
        RegistSendMail registSendMail = new RegistSendMail();
        try {
            Map<String, String> subParameter = new HashMap<>();
//            subParameter.put("time", Utils.currentTime(Constants.DATE_SENDEMAIL));
            subParameter.put("name", name);
            subParameter.put("email", toMail);
            registSendMail.sendMail(subParameter, toMail, MailType.REGIST);
        } catch (IOException | MessagingException e) {
            LogInfoUtil.logWarn(String.format("メールの送信に失敗しました。(%s)", toMail), e);
        }
    }

    /**
     * アカウント登録
     */
    private class RegistSendMail extends AbsSendMail {
        RegistSendMail() {
            super.setSendMailConfig(sendMailConfig);
        }

        @Override
        String createSmtpapi(Map<String, String> subParameter, String toMail, String category) {
            SMTPAPI smtpapi = new SMTPAPI();
            smtpapi.addTo(toMail);
            smtpapi.addCategory(category);
//            smtpapi.addSubstitution("###time###", subParameter.get("time"));
            smtpapi.addSubstitution("###name###", subParameter.get("name"));
            smtpapi.addSubstitution("###email###", subParameter.get("email"));
            return smtpapi.rawJsonString();
        }

    }

    // パスワード初期化
    public void sendMailForResetPassword(String toMail, String password, String name) {
        ResetPasswordSendMail resetPasswordSendMail = new ResetPasswordSendMail();
        try {
            Map<String, String> subParameter = new HashMap<>();
//            subParameter.put("time", Utils.currentTime(Constants.DATE_SENDEMAIL));
            subParameter.put("name", name);
            subParameter.put("password", password);
            resetPasswordSendMail.sendMail(subParameter, toMail, MailType.RESETPASSWORD);
        } catch (IOException | MessagingException e) {
            LogInfoUtil.logWarn(String.format("メールの送信に失敗しました。(%s)", toMail), e);
        }
    }

    /**
     * パスワード初期化
     */
    private class ResetPasswordSendMail extends AbsSendMail {
        ResetPasswordSendMail() {
            super.setSendMailConfig(sendMailConfig);
        }

        @Override
        String createSmtpapi(Map<String, String> subParameter, String toMail, String category) {
            SMTPAPI smtpapi = new SMTPAPI();
            smtpapi.addTo(toMail);
            smtpapi.addCategory(category);
            smtpapi.addSubstitution("###name###", subParameter.get("name"));
//            smtpapi.addSubstitution("###time###", subParameter.get("time"));
            smtpapi.addSubstitution("###password###", subParameter.get("password"));
            return smtpapi.rawJsonString();
        }
    }

    // メールアドレス変更
    public void sendMailForChangeMail(String toMail, String name) {
        ChangeMailSendMail changeMailSendMail = new ChangeMailSendMail();
        try {
            Map<String, String> subParameter = new HashMap<>();
//            subParameter.put("time", Utils.currentTime(Constants.DATE_SENDEMAIL));
            subParameter.put("name", name);
            subParameter.put("newEmail", toMail);
            changeMailSendMail.sendMail(subParameter, toMail, MailType.CHANGEMAIL);
        } catch (IOException | MessagingException e) {
            LogInfoUtil.logWarn(String.format("メールの送信に失敗しました。(%s)", toMail), e);
        }
    }

    /**
     * メールアドレス変更
     */
    private class ChangeMailSendMail extends AbsSendMail {
        ChangeMailSendMail() {
            super.setSendMailConfig(sendMailConfig);
        }

        @Override
        String createSmtpapi(Map<String, String> subParameter, String toMail, String category) {
            SMTPAPI smtpapi = new SMTPAPI();
            smtpapi.addTo(toMail);
            smtpapi.addCategory(category);
            smtpapi.addSubstitution("###name###", subParameter.get("name"));
//            smtpapi.addSubstitution("###time###", subParameter.get("time"));
            smtpapi.addSubstitution("###newEmail###", subParameter.get("newEmail"));
            return smtpapi.rawJsonString();
        }
    }
    
    // ユーザーを新規
    public void sendMailForNewPassword(String name,String toMail, String password, String userId) {
        NewPasswordSendMail newPasswordSendMail = new NewPasswordSendMail();
        try {
            Map<String, String> subParameter = new HashMap<>();
            subParameter.put("userId", userId);
            subParameter.put("name", name);
            subParameter.put("newPassword", password);
            newPasswordSendMail.sendMail(subParameter, toMail, MailType.NEWPASSWORD);
        } catch (IOException | MessagingException e) {
            LogInfoUtil.logWarn(String.format("メールの送信に失敗しました。(%s)", toMail), e);
        }
    }

    /**
     * ユーザーを新規
     */
    private class NewPasswordSendMail extends AbsSendMail {
        NewPasswordSendMail() {
            super.setSendMailConfig(sendMailConfig);
        }

        @Override
        String createSmtpapi(Map<String, String> subParameter, String toMail, String category) {
            SMTPAPI smtpapi = new SMTPAPI();
            smtpapi.addTo(toMail);
            smtpapi.addCategory(category);
            smtpapi.addSubstitution("###name###", subParameter.get("name"));
            smtpapi.addSubstitution("###userId###", subParameter.get("userId"));
            smtpapi.addSubstitution("###newPassword###", subParameter.get("newPassword"));
            return smtpapi.rawJsonString();
        }
    }
    
    // ユーザーパスワードのリセット
    public void sendMailUserResetPassword(String name,String toMail, String password,String userId) {
        UserResetPassword userResetPassword = new UserResetPassword();
        try {
            Map<String, String> subParameter = new HashMap<>();
            subParameter.put("userId", userId);
            subParameter.put("name", name);
            subParameter.put("password", password);
            userResetPassword.sendMail(subParameter, toMail, MailType.USERRESETPASSWORD);
        } catch (IOException | MessagingException e) {
            LogInfoUtil.logWarn(String.format("メールの送信に失敗しました。(%s)", toMail), e);
        }
    }

    /**
     * ユーザーパスワードのリセット
     */
    private class UserResetPassword extends AbsSendMail {
        UserResetPassword() {
            super.setSendMailConfig(sendMailConfig);
        }

        @Override
        String createSmtpapi(Map<String, String> subParameter, String toMail, String category) {
            SMTPAPI smtpapi = new SMTPAPI();
            smtpapi.addTo(toMail);
            smtpapi.addCategory(category);
            smtpapi.addSubstitution("###userId###", subParameter.get("userId"));
            smtpapi.addSubstitution("###name###", subParameter.get("name"));
            smtpapi.addSubstitution("###password###", subParameter.get("password"));
            return smtpapi.rawJsonString();
        }
    }
}
