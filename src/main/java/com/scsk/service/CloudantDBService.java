package com.scsk.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.scsk.config.BluemixCredentialsBean;
import com.scsk.config.BluemixNoSqlInfoBean;
import com.scsk.config.CloudantForLocalConfig;
import com.scsk.util.LogInfoUtil;

@Service
public final class CloudantDBService {

    private CloudantClient cloudantClient;

    @Autowired
    private CloudantForLocalConfig cloudantForLocalConfig;

    CloudantDBService() {
    }

    /**
     * 
     */
    public void cloudantOpen() throws Exception {
        try {
            if (cloudantForLocalConfig.isLocal()) {
                LogInfoUtil.LogInfo("===========cloudantOpen==========localCloudant===============");
                localCloudant();
            } else {
                LogInfoUtil.LogInfo("===========cloudantOpen==========bluemixCloudant=============");
                bluemixCloudant();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 
     */
    public void cloudantClose() {
        if (cloudantClient != null) {
            LogInfoUtil.LogInfo("========================cloudantClose========================");
            cloudantClient.shutdown();
        }
    }

    /**
     * 
     */
    private void bluemixCloudant() throws Exception {
        CloudantClient cloudantClient = null;
        BluemixCredentialsBean bluemixCredentialsBean = getVcapServices();
        if (bluemixCredentialsBean != null) {
            cloudantClient = ClientBuilder.account(bluemixCredentialsBean.getUsername())
                    .username(bluemixCredentialsBean.getUsername()).password(bluemixCredentialsBean.getPassword())
                    .build();
            this.cloudantClient = cloudantClient;
        } else {
            Exception e = new Exception("bluemixCloudant: Connection Error!");
            throw e;
        }

    }

    /**
     * 
     */
    public void localCloudant() throws Exception {
        CloudantClient cloudantClient = null;
        try {
            cloudantClient = ClientBuilder.url(new URL(cloudantForLocalConfig.getUrl()))
                    .username(cloudantForLocalConfig.getUsername()).password(cloudantForLocalConfig.getPassword())
                    .build();
            this.cloudantClient = cloudantClient;
        } catch (MalformedURLException e) {
            throw e;
        }
    }

    /**
     * 
     * @return BluemixCredentialsBean bluemixCredentialsBean
     */
    private BluemixCredentialsBean getVcapServices() throws Exception {
        String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        BluemixCredentialsBean bluemixCredentialsBean = null;
        if (VCAP_SERVICES != null) {
            Map<?, ?> maps = objectMapper.readValue(VCAP_SERVICES, Map.class);

            @SuppressWarnings("unchecked")
            List<Object> lo = (List<Object>) maps.get(cloudantForLocalConfig.getType());

            String bluemixNoSqlInfoBeanString = objectMapper.writeValueAsString(lo.get(0));

            BluemixNoSqlInfoBean bluemixNoSqlInfoBean = objectMapper.readValue(bluemixNoSqlInfoBeanString,
                    BluemixNoSqlInfoBean.class);

            bluemixCredentialsBean = bluemixNoSqlInfoBean.getCredentials();
        }
        return bluemixCredentialsBean;
    }

    public CloudantClient getCloudantClient() {
        return cloudantClient;
    }
}
