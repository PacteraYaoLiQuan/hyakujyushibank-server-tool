package com.scsk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsk.constants.Constants;

/**
 * Logを追加クラス。<br>
 * 
 * @param String
 *            controllerName
 */

public abstract class LogInfoUtil {
    public static final Logger logger = LoggerFactory.getLogger("COMPONENT");

    /**
     * システム日時を取得。
     * 
     */
    public static String getInformation() {
        String n = "[%s]  ";
        
        return String.format(n, SessionUser.userId());
    }

    /**
     * LogInfo実行開始。
     * 
     * @param String
     *            controllerName
     */
    public static void LogInfoStrat(String name) {
        logger.info(getInformation() + name + Constants.IMPLEMENT_START);
    }

    /**
     * LogInfo実行完了。
     * 
     * @param String
     *            controllerName
     */
    public static void LogInfoEnd(String name) {

        logger.info(getInformation() + name + Constants.IMPLEMENT_END);
    }

    /**
     * LogDebug実行開始。
     * 
     * @param String
     *            controllerName
     */
    public static void LogDebugStrat(String name) {
        logger.debug(getInformation() + name + Constants.IMPLEMENT_START);
    }

    /**
     * LogDebug実行完了。
     * 
     * @param String
     *            controllerName
     */
    public static void LogDebugEnd(String name) {
        logger.debug(getInformation() + name + Constants.IMPLEMENT_END);
    }

    /**
     * LogError実行開始。
     * 
     * @param String
     *            controllerName
     */
    public static void LogErrorStrat(String name) {
        logger.error(getInformation() + name);
    }

    /**
     * LogError実行完了。
     * 
     * @param String
     *            controllerName
     */
    public static void LogErrorEnd(String name) {
        logger.error(getInformation() + name);
    }

    /**
     * 画面遷移Log。
     * 
     * @param String
     *            controllerName
     */
    public static void LogInfoPage(String name) {
        logger.info(getInformation() + name + Constants.SENNI);
    }

    /**
     * LogInfo。
     * 
     * @param String
     *            controllerName
     */
    public static void LogInfo(String input) {
        logger.info(getInformation() + input);
    }

    /**
     * LogDebug。
     * 
     * @param String
     *            controllerName
     */
    public static void LogDebug(String input) {
        logger.debug(getInformation() + input);
    }
    
    /**
     * LogDebug。
     * 
     * @param String
     *            controllerName
     */
    public static void LogDebug(String input, Throwable t) {
        logger.debug(getInformation() + input, t);
    }

    /**
     * LogError。
     * 
     * @param String
     *            controllerName
     */
    public static void LogError(String input) {
        logger.error(getInformation() + input);
    }

    /**
     * LogError。
     * 
     * @param String
     *            controllerName
     */
    public static void LogError(String smg, Throwable t) {
        logger.error(getInformation() + smg, t);
    }

    /**
     * logWarn
     * 
     * @param msg
     *            メッセージ
     */
    public static void logWarn(String msg) {
        logger.warn(getInformation() + msg);
    }

    /**
     * 
     * @param format
     *            メッセージ
     * @param arg
     *            置換語
     */
    public static void logWarn(String format, Object... arg) {
        logger.warn(getInformation() + format, arg);
    }

    /**
     * logWarn
     * 
     * @param msg
     *            メッセージ
     * @param t
     *            エラー
     */
    public static void logWarn(String msg, Throwable t) {
        logger.warn(getInformation() + msg, t);
    }
}
