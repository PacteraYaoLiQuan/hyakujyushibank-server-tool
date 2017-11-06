package com.scsk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 *
 */
@Component
public class SendMailConfig {
    @Value("${sendMail.SMTP.host}")
    private String smtpHost;
    @Value("${sendMail.SMTP.port}")
    private String smtpPort;
    @Value("${sendMail.SMTP.userName}")
    private String smtpUserName;
    @Value("${sendMail.SMTP.password}")
    private String smtppassword;
    @Value("${sendMail.mail.from}")
    private String mailFrom;

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public String getSmtpUserName() {
        return smtpUserName;
    }

    public String getSmtppassword() {
        return smtppassword;
    }

    public String getMailFrom() {
        return mailFrom;
    }
}
