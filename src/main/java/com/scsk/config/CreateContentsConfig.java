package com.scsk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CreateContentsConfig {
    @Value("${createContents.contentsServiceURL}")
    private String contentsServiceURL;

	public String getContentsServiceURL() {
		return contentsServiceURL;
	}
    
}
