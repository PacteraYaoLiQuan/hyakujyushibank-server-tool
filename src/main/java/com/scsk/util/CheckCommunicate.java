package com.scsk.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class CheckCommunicate {
    /**
     * 
     * @param authKey1
     * @param authKey2
     * @param authKey3
     * @param appVer
     * @param appOS
     * @return
     */
    public static boolean check(String authKey1, String authKey2, String authKey3, String appVer, String appOS) {
        String key = authKey1 + authKey2 + "hirojima";
        String sha = shaEncode(key);
        if (sha.equals(authKey3)) {
            return true;
        }
        return false;
    }

    /**
     * SHA暗号化
     * 
     * @param text
     *            テキスト
     * @return 暗号化のテキスト
     */
    private static String shaEncode(String text) {

        ShaPasswordEncoder sha = new ShaPasswordEncoder();
        sha.setEncodeHashAsBase64(false);
        String pwd = sha.encodePassword(text, null);

        return pwd;
    }
    
    public static String contentsEncode(String text){
        ShaPasswordEncoder sha = new ShaPasswordEncoder();
        sha.setEncodeHashAsBase64(false);
        String pwd = sha.encodePassword(text, null);

        return pwd;
    }
}
