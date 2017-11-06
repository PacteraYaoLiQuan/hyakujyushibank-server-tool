package com.scsk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CloudantForLocalConfig {
    @Value("${cloudantLocal.local}")
    private boolean local;
    @Value("${cloudantLocal.type}")
    private String type;
    @Value("${cloudantLocal.url}")
    private String url;
    @Value("${cloudantLocal.username}")
    private String username;
    @Value("${cloudantLocal.password}")
    private String password;
    
    public boolean isLocal() {
        return local;
    }
    public String getType() {
        return type;
    }
    public String getUrl() {
        return url;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
