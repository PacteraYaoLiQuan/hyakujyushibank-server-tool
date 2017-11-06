package com.scsk.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

import com.scsk.config.CreateContentsConfig;

@Component
public class CreateContents {
    @Autowired
    private CreateContentsConfig createContentsConfig;

    public String sendMessage(String id) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Map<String, String> message = new HashMap<String, String>();
        ObjectMapper objectMapper = new ObjectMapper();
        Random random = new Random();
        int s = random.nextInt(9000) + 1000;
        message.put("_id", id);
        String jsonString = null;
        String authKey1 = SessionUser.userId().toString();
        String authKey2 = String.valueOf(s);
        String authKey3 = authKey1 + authKey2 + "hirojima";
        authKey3 = CheckCommunicate.contentsEncode(authKey3);
        try {
            jsonString = objectMapper.writeValueAsString(message);
        } catch (IOException e) {
            throw e;
        }
        try {
            HttpPost httppost = new HttpPost(createContentsConfig.getContentsServiceURL());
            httppost.setHeader("Accept","application/json");
            httppost.setHeader("authKey1", "Bearer "+authKey1);
            httppost.setHeader("authKey2", authKey2);
            httppost.setHeader("authKey3", authKey3);
            httppost.setHeader("Content-Type","application/json");
            StringEntity stringEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            httppost.setEntity(stringEntity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                LogInfoUtil.LogDebug("----------------------------------------");
                LogInfoUtil.LogDebug("Send Message Body : " + id);
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
