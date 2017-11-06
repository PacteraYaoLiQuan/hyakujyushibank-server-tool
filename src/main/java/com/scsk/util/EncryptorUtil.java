package com.scsk.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scsk.config.EncryptorConfig;
/**
 * 暗号化
 * @author ws
 *
 */
@Component
public class EncryptorUtil {
    private final String ALGORITHM = "AES";
    private final String CIPHER_ALGORITHM_BCB = "AES/CBC/PKCS5Padding";
    private final String DEFAULT_CHARSET = "UTF-8";
    @Autowired
    private EncryptorConfig encryptorConfig;

    /**
     * 変換キー<br>
     * 
     * @param key
     * @return
     * @throws Exception
     */
    private Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return secretKey;
    }

    /**
     * 暗号解読
     * 
     * @param base64String
     * @return
     * @throws Exception
     */
    public String decrypt(String base64String) throws Exception {
        try {
            Key k = toKey(encryptorConfig.getKey().getBytes(DEFAULT_CHARSET));

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_BCB);
            IvParameterSpec iv = new IvParameterSpec(encryptorConfig.getIv().getBytes());
            cipher.init(Cipher.DECRYPT_MODE, k, iv);

            return new String(cipher.doFinal(hexToByte(base64String)), DEFAULT_CHARSET);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 暗号化
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public String encrypt(String data) throws Exception {
        if (data != null) {
            try {
                Key k = toKey(encryptorConfig.getKey().getBytes(DEFAULT_CHARSET));

                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_BCB);
                IvParameterSpec iv = new IvParameterSpec(encryptorConfig.getIv().getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, k, iv);

                return byteToHex(cipher.doFinal(data.getBytes(DEFAULT_CHARSET)));
            } catch (Exception e) {
                throw e;
            }
        }
        return null;
    }

    /**
     * バイナリデータをBASE64文字列としてエンコードされます
     * 
     * @param binaryData
     * @return
     */
    private String encode(byte[] binaryData) {
        try {
            return new String(Base64.encodeBase64(binaryData), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            LogInfoUtil.LogError(e.getMessage());
            return null;
        }
    }

    /**
     * バイナリデータ復旧のBASE64文字列
     * 
     * @param base64String
     * @return
     */
    private byte[] decode(String base64String) {
        return Base64.decodeBase64(base64String);
    }

    /**
     * 
     * @param byteRresult
     * @return
     */
    private String byteToHex(byte[] byteRresult) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteRresult.length; i++) {
            String hex = Integer.toHexString(byteRresult[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 
     * @param content
     * @return
     */
    private byte[] hexToByte(String content) {
        byte[] byteRresult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        return byteRresult;
    }
}
