package com.scsk.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * IPアドレス
 * 
 * @author WS
 *
 */
public class IPaddress {

    /**
     * IPアドレスを取得します
     * 
     * @return IPアドレス
     */
    public static String getIp() {
        HttpServletRequest request = GlobalRequest.request();
        String ip = request.getHeader("X-Forwarded-For-Netscaler");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
