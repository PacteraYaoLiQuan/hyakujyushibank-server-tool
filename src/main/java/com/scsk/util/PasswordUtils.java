package com.scsk.util;

import org.springframework.security.crypto.password.StandardPasswordEncoder;
/**
 * パスワードを暗号化
 * @author ws
 *
 */
public class PasswordUtils {

    static StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();

    /**
     * パスワードを暗号化して。
     * @param rawPasswordは暗号化前のパスワード
     * @return 暗号化のパスワード
     */
    public static String passwordEncode(String rawPassword) {
        if (rawPassword != null) {
            return standardPasswordEncoder.encode(rawPassword);
        } else {
            return "";
        }
    }
    
    /**
     * パスワードマッチング
     * @param rawPasswordは暗号化前のパスワード
     * @param encodedPasswordは暗号化後のパスワード
     * @return boolean 「true」は「rawPassword == encodedPassword」　「false」は「rawPassword != encodedPassword」
     */
    public static boolean passwordMatch(String rawPassword, String encodedPassword) {
        if (rawPassword != null && encodedPassword != null) {
            return standardPasswordEncoder.matches(rawPassword, encodedPassword);
        } else {
            return false;
        }
    }
}
