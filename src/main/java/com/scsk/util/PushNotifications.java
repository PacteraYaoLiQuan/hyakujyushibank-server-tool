package com.scsk.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scsk.config.PushNotificationsConfig;

/**
 * Push推送
 * 
 * @author ws
 *
 */
@Component
public class PushNotifications {

    @Autowired
    private PushNotificationsConfig config;

    /**
     * 
     * @param messageContent
     * @param deviceIds
     * @throws IOException
     */
    public String sendMessage(String messageContent, Integer badge, List<String> deviceIds) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        ObjectMapper objectMapper = new ObjectMapper();

        if (deviceIds == null || deviceIds.size() <= 0) {
            return null;
        }
        Map<String, Object> sendMessageBody = new HashMap<String, Object>();
        Map<String, String> message = new HashMap<String, String>();
        Map<String, List<String>> target = new HashMap<String, List<String>>();
        Map<String, Object> settings = new HashMap<String, Object>();
        Map<String, Object> apns = new HashMap<String, Object>();
        Map<String, Object> gcm = new HashMap<String, Object>();
        Map<String, Object> style = new HashMap<String, Object>();
        message.put("alert", messageContent);
        target.put("deviceIds", deviceIds);

        if (badge != null) {
            apns.put("badge", badge);
//            settings.put("apns", apns);
        }
        apns.put("sound", "default");
        settings.put("apns", apns);
        
        style.put("type", "bigtext_notification");
        style.put("text", "");
        style.put("title", config.getGcmStyleTitle());
        gcm.put("style", style);
        gcm.put("icon", "app_push_icon");
        settings.put("gcm", gcm);

        sendMessageBody.put("message", message);
        sendMessageBody.put("settings", settings);
        sendMessageBody.put("target", target);

        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(sendMessageBody);
        } catch (IOException e) {
            throw e;
        }

        try {
            HttpPost httppost = new HttpPost(config.getPushServiceURL() + config.getApplicationId() + "/messages");

            httppost.setHeader("appSecret", config.getAppSecret());
            httppost.setHeader("Accept-Language", config.getAcceptLanguage());

            StringEntity stringEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            httppost.setEntity(stringEntity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                LogInfoUtil.LogDebug("----------------------------------------");
                LogInfoUtil.LogDebug("Send Message Body : " + jsonString);
                LogInfoUtil.LogDebug("Status Code       : " + String.valueOf(response.getStatusLine().getStatusCode()));
                LogInfoUtil.LogDebug("Response Entity   : " + EntityUtils.toString(response.getEntity(), "UTF-8"));
                LogInfoUtil.LogDebug("----------------------------------------");
                return String.valueOf(response.getStatusLine().getStatusCode());
            } finally {
                response.close();
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LogInfoUtil.logWarn(e.getMessage(), e);
            }
        }
    }

    /**
     * 
     * @param messageContent
     * @param deviceIds
     * @throws IOException
     */
    public String sendMessage(String messageContent, Integer badge) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> sendMessageBody = new HashMap<String, Object>();
        Map<String, String> message = new HashMap<String, String>();
        Map<String, Object> settings = new HashMap<String, Object>();
        Map<String, Object> apns = new HashMap<String, Object>();
        Map<String, Object> gcm = new HashMap<String, Object>();
        Map<String, Object> style = new HashMap<String, Object>();
        message.put("alert", messageContent);

        if (badge != null) {
            apns.put("badge", badge);
            settings.put("apns", apns);
        }

        style.put("type", "bigtext_notification");
        style.put("text", "");
        style.put("title", config.getGcmStyleTitle());
        gcm.put("style", style);
        gcm.put("icon", "app_push_icon");
        settings.put("gcm", gcm);

        sendMessageBody.put("message", message);
        sendMessageBody.put("settings", settings);
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(sendMessageBody);
        } catch (IOException e) {
            throw e;
        }

        try {
            HttpPost httppost = new HttpPost(config.getPushServiceURL() + config.getApplicationId() + "/messages");

            httppost.setHeader("appSecret", config.getAppSecret());
            httppost.setHeader("Accept-Language", config.getAcceptLanguage());

            StringEntity stringEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            httppost.setEntity(stringEntity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                LogInfoUtil.LogDebug("----------------------------------------");
                LogInfoUtil.LogDebug("Send Message Body : " + jsonString);
                LogInfoUtil.LogDebug("Status Code       : " + String.valueOf(response.getStatusLine().getStatusCode()));
                LogInfoUtil.LogDebug("Response Entity   : " + EntityUtils.toString(response.getEntity(), "UTF-8"));
                LogInfoUtil.LogDebug("----------------------------------------");
                return String.valueOf(response.getStatusLine().getStatusCode());
            } finally {
                response.close();
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LogInfoUtil.logWarn(e.getMessage(), e);
            }
        }
    }
}
