package com.scsk.util;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import com.scsk.config.SendMailConfig;
import com.scsk.constants.ApplicationKeys;

/**
 * E-mail配送
 * @author yql
 *
 */
public abstract class AbsSendMail {
    private SendMailConfig sendMailConfig;
    
    public void setSendMailConfig(SendMailConfig sendMailConfig) {
        this.sendMailConfig = sendMailConfig;
    }

    public enum MailType {
        /** パスワード初期化 **/
        RESETPASSWORD {
//            public String getTemplateName() {
//                return "ResetPassword.html";
//            }
            public String getTemplateName() {
                return "ResetPassword.txt";
            }

            public String getSubject() {
                return "【（仮）いよぎんアプリ】パスワード初期化のお知らせ";
            }

            public String getCategory() {
                return "resetPassword";
            }
        },
        /** アカウント登録 **/
        REGIST {
//            public String getTemplateName() {
//                return "Regist.html";
//            }
            public String getTemplateName() {
                return "Regist.txt";
            }

            public String getSubject() {
                return "【（仮）いよぎんアプリ】アカウント登録のお知らせ";
            }

            public String getCategory() {
                return "regist";
            }
        },
        /** メールアドレス変更 **/
        CHANGEMAIL {
//            public String getTemplateName() {
//                return "updateEmail.html";
//            }
            public String getTemplateName() {
                return "updateEmail.txt";
            }

            public String getSubject() {
                return "【（仮）いよぎんアプリ】メールアドレス変更のお知らせ";
            }

            public String getCategory() {
                return "changeMail";
            }
        },
        /** ユーザーパスワードのリセット **/
        USERRESETPASSWORD {
            public String getTemplateName() {
                return "UserResetPassword.txt";
            }

            public String getSubject() {
                return "【MINEFOCUS】ユーザーパスワードリセットのお知らせ";
            }

            public String getCategory() {
                return "UserResetPassword";
            }
        },
        
        /** ユーザーを新規 **/
        NEWPASSWORD {
//            public String getTemplateName() {
//                return "newPssword.html";
//            }
            public String getTemplateName() {
                return "newPssword.txt";
            }

            public String getSubject() {
                return "【MINEFOCUS】ユーザーアカウント登録完了のお知らせ";
            }

            public String getCategory() {
                return "newPssword";
            }
        };

        public abstract String getTemplateName();

        public abstract String getSubject();

        public abstract String getCategory();
    }

    public void sendMail(Map<String, String> subParameter, String toMail, MailType type) throws IOException, MessagingException {
        // SMTP接続情報
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", sendMailConfig.getSmtpHost());
        props.put("mail.smtp.port", sendMailConfig.getSmtpPort());
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.auth", "true");
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        mailSession.setDebug(false); // console log for debug

        String smtpHeader = createSmtpapi(subParameter, toMail, type.getCategory());
        smtpHeader = MimeUtility.encodeText(smtpHeader);

        // メールの情報を設定するため
        MimeMessage message = new MimeMessage(mailSession);

        message.setFrom(new InternetAddress(sendMailConfig.getMailFrom()));

        message.setSubject(type.getSubject(), "UTF-8");

        message.setSentDate(new Date());

        message.setHeader("X-SMTPAPI", MimeUtility.fold(76, smtpHeader));

//        Multipart multipart = new MimeMultipart("alternative");
        String text = getTemplateTxt(type.getTemplateName());
//        MimeBodyPart htmlbody = new MimeBodyPart();
//        htmlbody.setText(text, "UTF-8", "HTML");
//        multipart.addBodyPart(htmlbody);
        message.setText(text,"UTF-8","plain");
//        message.setContent(text,"text/html;charset=UTF-8");
        message.saveChanges();
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
        Transport.send(message);
    }

    abstract String createSmtpapi(Map<String, String> subParameter, String toMail, String category);

    private class SMTPAuthenticator extends Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = sendMailConfig.getSmtpUserName();
            String password = sendMailConfig.getSmtppassword();
            return new PasswordAuthentication(username, password);
        }
    }

    public static String getTemplateTxt(String templateName){
        HttpServletRequest request = GlobalRequest.request();
        String filePath = request.getServletContext().getRealPath(ApplicationKeys.MAIL_RESOURCES_PATH);
        filePath = filePath + "/" + templateName;
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String s = null;
            while((s = br.readLine())!=null){
                result.append(System.lineSeparator()+s );
            }
            br.close();    
        }catch(Exception e){
            LogInfoUtil.logWarn("", e);
        }
        return result.toString();
    }
    
    public String getTemplateHtml(String templateName) {

        HttpServletRequest request = GlobalRequest.request();
        String filePath = request.getServletContext().getRealPath(ApplicationKeys.MAIL_RESOURCES_PATH);
        filePath = filePath + "/" + templateName;
        String templateContent = "";

        try {
            FileInputStream fileinputstream = new FileInputStream(filePath);
            int lenght = fileinputstream.available();
            byte bytes[] = new byte[lenght];

            fileinputstream.read(bytes);
            fileinputstream.close();

            templateContent = new String(bytes);
        } catch (IOException e) {
            LogInfoUtil.logWarn(String.format("テンプレートの読み取りエラー(%s)", templateName), e);
        }
        return templateContent;
    }
}
