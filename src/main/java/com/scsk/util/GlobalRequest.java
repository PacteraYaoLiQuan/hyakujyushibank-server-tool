package com.scsk.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 *
 */
public class GlobalRequest {

    /**
     * 
     * @return
     */
    public static HttpServletRequest request() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (sra == null) {
            return null;
        }
        HttpServletRequest request = sra.getRequest();

        return request;
    }
}
