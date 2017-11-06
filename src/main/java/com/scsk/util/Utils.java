package com.scsk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具クラス
 */
public class Utils {

    /**
     * 
     * @param value
     *            チェック値
     * @return trueの場合、非nullと非空、その以外false
     */
    public static boolean isNotNullAndEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    /**
     * 
     * @param dateFormat
     * @return
     */
    public static String currentTime(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date());
    }

    /**
     * 時間のフォーマット変換
     * 
     * @param bFormat
     *            変換前のフォーマット
     * @param aFormat
     *            変換後のフォーマット
     * @param time
     *            時間
     * @return 変換後の時間
     */
    public static String timeFormatConvert(String bFormat, String aFormat, String time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(bFormat);
        SimpleDateFormat sdf2 = new SimpleDateFormat(aFormat);
        String r = "";
        try {
            r = sdf2.format(sdf1.parse(time));
        } catch (Exception e) {
            LogInfoUtil.LogError("時間変換エラー", e);
        }
        return r;
    }

}
