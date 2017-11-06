package com.scsk.extension;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * リダイレクト処理用レスポンスラッパー
 *
 * @param SCSK Ikeya Haruki
 */
public class RedirectHttpServletResponseWrapper extends HttpServletResponseWrapper {

    /** オリジナルプロトコル情報が格納されているヘッダー名 */
    public static final String HEADER_X_FORWARDED_PROTO = "X-Forwarded-Proto-Netscaler";

    /** リダイレクト情報が格納されたヘッダー名 */
    public static final String HEADER_LOCATION = "Location";

    /** オリジナルプロトコル情報に格納されている値判定用 */
    public static final String PROTOCOL_HTTPS = "https";

    /** URIのスキーマ HTTP識別情報 */
    public static final String URI_HTTP_PARTS = "http:/";

    /** URIのスキーマ HTTP識別情報の文字数 */
    public static final int URI_HTTP_PARTS_LENGTH = URI_HTTP_PARTS.length();

    /** URIのスキーマ HTTPS識別情報 */
    public static final String URI_HTTPS_PARTS = "https:/";

    /** HTTPサーブレットリクエスト */
    private HttpServletRequest request = null;

    /**
     * コンストラクタ
     *
     * @param request リクエスト
     * @param response レスポンス
     */
    public RedirectHttpServletResponseWrapper(HttpServletRequest request, HttpServletResponse response) {

        super(response);
        this.request = request;
    }

    /**
     * リダイレクト変更処理。
     * 内部的には即座にリダイレクトせずに一時的にリダイレクト先を保存する。
     *
     * @param location 遷移先
     */
    @Override
    public void sendRedirect(String location) throws IOException {

        // log("RHF-Process: Matching redirect http state");
        // log("RHF-Process: redirect url = %s", location);

        String forwarded = request.getHeader(HEADER_X_FORWARDED_PROTO);
        // log("RHF-Process: %s request header value = %s", HEADER_X_FORWARDED_PROTO, forwarded);

        /*
         * SSL暗号解除装置から送られてきたリクエストヘッダー値が設定されており
         * かつ、"https"文字列であった場合は、リダイレクト先を強制的にhttpsプロトコルを利用した
         * 絶対アドレス形式に変換し、リダイレクトを行う
         */
        if (forwarded != null && PROTOCOL_HTTPS.equals(forwarded)) {
            // httpsプロトコルを利用した絶対アドレス形式への変換を行う。

            // 新しい遷移先のアドレス
            String newLoocation;

            // リダイレクト先がすでに絶対パスで記載されている場合は、そのアドレスを新しい遷移先のアドレスのアドレスとして利用する
            if (location != null
                    && (location.startsWith(URI_HTTP_PARTS) || location.startsWith(URI_HTTPS_PARTS))) {
                newLoocation = location;
            } else {
                // 相対パスで記載されている場合は、リクエスト時のURL、URI情報を元に絶対パスを生成する
                StringBuffer requestUrl = request.getRequestURL();
                String uri = request.getRequestURI();

                String baseUrl = requestUrl.substring(0, requestUrl.length() - uri.length());
                newLoocation = baseUrl;

                if (location != null) {
                    newLoocation = baseUrl + location;
                }
            }

            if (newLoocation.startsWith(URI_HTTP_PARTS)) {
                newLoocation = URI_HTTPS_PARTS + newLoocation.substring(URI_HTTP_PARTS_LENGTH);
            }

            // log("RHF-Process: new %s header value = %s", HEADER_LOCATION, newLoocation);
            super.sendRedirect(newLoocation);
            // log("RHF-Process: replaced %s response header value = %s", HEADER_LOCATION, response.getHeader(HEADER_LOCATION));
        } else {
            // リダイレクト先の変換は不要のため、リダイレクト先はラッパーに設定されている値をそのまま利用
            super.sendRedirect(location);
        }
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
}
