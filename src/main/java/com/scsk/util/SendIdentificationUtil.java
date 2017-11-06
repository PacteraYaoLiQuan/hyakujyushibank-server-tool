package com.scsk.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Component;

/**
 * ＩＰアドレス鑑定
 * @author ylq
 * input ip
 *       リクエストパラメータ.ip
 * input zip
 *       リクエストパラメータ.zip
 *       
 */
@Component
public class SendIdentificationUtil {


    public static String geologicalAppraisalIPSend(String ip, String zip) {
        String urlNameString = "http://ip-kanteidan.com/search/api.php?sid=S1paWP0t18Ex2mWYKHk/Y1GgraSRWclWsFwG0cxvX4NgkUZyrbChc5pjcx/5/fYGeLv0Zg==&k=iW68d/r2Hc0=&ip="+ip+"&zip="+zip+"";
        return sendAppraisal(urlNameString);
    }

    /**
     * 自宅電話番号鑑定
     * @author ylq
     * input tel
     *       リクエストパラメータ.tel
     *       
     */    
    public static String teleNumberSend(String tel) {
        String urlNameString = "https://www.webt2k.com/t2ktelinfo.do?tel=" + tel + "&cid=114api&id=114api&pass=114apipass";
        return sendAppraisal(urlNameString);
    }

    /**
     * 携帯電話番号鑑定
     * @author ylq
     * input phone
     *       リクエストパラメータ.phone
     *       
     */
    public static String phoneNumberSend(String phone) {
        String urlNameString = "https://www.webt2k.com/t2ktelinfo.do?tel=" + phone + "&cid=114api&id=114api&pass=114apipass";
        return sendAppraisal(urlNameString);
    }
    
    private static String sendAppraisal (String url){
        String result = "";
        BufferedReader in = null;
        try {
//            String urlNameString = url;
            URL realUrl = new URL(url);
            // オープンとURLとの間の接続
            URLConnection connection = realUrl.openConnection();
            // 実際の接続を確立します
            connection.connect();
            // 入力に応答してURLを読み取ることストリーム
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += StringEscapeUtils.unescapeHtml(line);
            }
        } catch (Exception e) {
            LogInfoUtil.logWarn("異常なGETリクエスト！", e);
        }
        // 入力ストリームを閉じます
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                LogInfoUtil.logWarn("", e2);
            }
        }
        return result;
    }
}
