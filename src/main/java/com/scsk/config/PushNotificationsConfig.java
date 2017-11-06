package com.scsk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationsConfig {
    @Value("${pushNotifications.appSecret}")
    private String appSecret;
    @Value("${pushNotifications.acceptLanguage}")
    private String acceptLanguage;
    @Value("${pushNotifications.pushServiceURL}")
    private String pushServiceURL;
    @Value("${pushNotifications.applicationId}")
    private String applicationId;
    @Value("${pushNotifications.gcm.style.title}")
    private String gcmStyleTitle;

    public String getAppSecret() {
        return appSecret;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public String getPushServiceURL() {
        return pushServiceURL;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getGcmStyleTitle() {
        return gcmStyleTitle;
    }
}
