package com.scsk.extension;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
// import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * プロトコルに応じてリダイレクト先を補正するフィルター
 *
 * @param SCSK Ikeya Haruki
 */
public class RedirectHookFilter implements Filter {

    /**
     * フィルタ初期化処理
     *
     * @param config フィルタ設定
     */
    public void init(FilterConfig config) throws ServletException {
        // 何もしない
    }

    /**
     * フィルタ処理
     *
     * @param servletRequest サーブレットリクエスト
     * @param servletResponse サーブレットレスポンス
     * @param filterChain フィルターチェーン
     * @throws IOException IO例外
     * @throws ServletException サーブレット例外
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        // log("RHF-Info    : Start RedirectHookFilter");
        //
        // try {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // /* ロギング開始 */
        //            log("RHF-Info-REQ: Method = %s", request.getMethod());
        //            log("RHF-Info-REQ: RequestURI = %s", request.getRequestURI());
        //            log("RHF-Info-REQ: RequestedSessionId = %s", request.getRequestedSessionId());
        //            log("RHF-Info-REQ: ServletPath = %s", request.getServletPath());
        //            log("RHF-Info-REQ: ContentLength = %s", request.getContentLength());
        //            log("RHF-Info-REQ: ContentType = %s", request.getContentType());
        //            log("RHF-Info-REQ: ContextPath = %s", request.getContextPath());
        //            log("RHF-Info-REQ: LocalAddr = %s", request.getLocalAddr());
        //            log("RHF-Info-REQ: LocalName = %s", request.getLocalName());
        //            log("RHF-Info-REQ: RequestURL = %s", request.getRequestURL());
        //            log("RHF-Info-REQ: PathInfo = %s", request.getPathInfo());
        //
        //            Enumeration<String> attributeEnumeration = request.getAttributeNames();
        //            while (attributeEnumeration.hasMoreElements()) {
        //                String key = attributeEnumeration.nextElement();
        //                Object value = request.getAttribute(key);
        //
        //                log("RHF-Info-REQ: Attribute %s = %s", key, value);
        //            }
        //
        //            log("RHF-Info-REQ: ContextPath = %s", request.getContextPath());
        //
        //            Cookie[] cookies = request.getCookies();
        //            if (cookies != null) {
        //                for (Cookie cookie : cookies) {
        //                    log("RHF-Info-REQ: Cookie  %s = %s", cookie.getName(), cookie.getValue());
        //                }
        //            }
        //
        //            Enumeration<String> headerEnumeration = request.getHeaderNames();
        //            while (headerEnumeration.hasMoreElements()) {
        //                String key = headerEnumeration.nextElement();
        //                Object value = request.getHeader(key);
        //
        //                log("RHF-Info-REQ: Header %s = %s", key, value);
        //            }
        //
        //            Enumeration<String> parameterEnumeration = request.getParameterNames();
        //            while (parameterEnumeration.hasMoreElements()) {
        //                String key = parameterEnumeration.nextElement();
        //                Object value = request.getParameter(key);
        //
        //                log("RHF-Info-REQ: Parameter %s = %s", key, value);
        //            }
        // /* ロギング終了 */

        /* sendRedirectを無効にするためのレスポンスラッパーを準備 */
        RedirectHttpServletResponseWrapper responseWrapper = null;

        responseWrapper = new RedirectHttpServletResponseWrapper(request, response);
        filterChain.doFilter(servletRequest, responseWrapper);

        // /* ロギング開始 */
        //            int status = response.getStatus();
        //            log("RHF-Info-RES: HTTP Status = %d", status);
        //            log("RHF-Info-RES: ContentType = %s", response.getContentType());
        //
        //            Iterator<String> headerIterator = response.getHeaderNames().iterator();
        //            while (headerIterator.hasNext()) {
        //                String key = headerIterator.next();
        //                String value = response.getHeader(key);
        //
        //                log("RHF-Info-RES: Header %s = %s", key, value);
        //            }
        //
        // /* ロギング終了 */
        //        } catch (ServletException e) {
        //            logException("RHF-Info    : Exception occured", e);
        //        } finally {
        //            log("RHF-Info    : End RedirectHookFilter");
        //            log("-------------------------------------------------");
        //        }
    }

    //    /**
    //     * ログ出力
    //     *
    //     * @param format フォーマット
    //     * @param values 埋め込み値
    //     */
    //    public static void log(String format, Object... values) {
    //
    //        String logText = "null";
    //
    //        if (format != null) {
    //            logText = String.format(format, values);
    //        }
    //
    //        System.out.println(logText);
    //    }
    //
    //    /**
    //     * ログ出力
    //     *
    //     * @param message メッセージ
    //     * @param e 例外
    //     */
    //    public static void logException(String message, Exception e) {
    //
    //        String logText = "";
    //
    //        if (message != null) {
    //            logText = message;
    //        }
    //
    //        logText = logText + getStackTrace(e);
    //
    //        System.out.println(logText);
    //    }
    //
    //    /**
    //     * スタックトレースを返却する
    //     *
    //     * @param t スロー可能なオブジェクト
    //     * @return スタックトレース
    //     */
    //    public static String getStackTrace(Throwable t) {
    //
    //        String stackTrace = t.toString() + "\n";
    //        StackTraceElement[] elements = t.getStackTrace();
    //
    //        for (StackTraceElement element : elements) {
    //            stackTrace = stackTrace + "\tat " + element.toString() + "\n";
    //        }
    //
    //        Throwable cause = t.getCause();
    //
    //        if (cause != null) {
    //            stackTrace = stackTrace + "Caused by: " + getStackTrace(cause);
    //        }
    //
    //        return stackTrace;
    //    }

    /**
     * フィルター終了時の処理
     */
    public void destroy() {
        // 処理無し
    }
}
