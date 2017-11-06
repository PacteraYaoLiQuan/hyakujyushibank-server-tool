package com.scsk.constants;

/**
 * 定数クラス
 */
public final class Constants {

    // データベース名前
    public static final String DB_NAME = "insight";
    // データベースのView名
    public static final String DB_VIEW = "insightView";
    // メール拡張名
    public static final String EMAILEND = "@scsk.jp";
    // admin権限
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    // user権限
    public static final String ROLE_USER = "ROLE_USER";
    // 日付フォーマット
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";
    // 日付フォーマット
    public static final String DATE_SENDEMAIL = "yyyy年MM月dd日HH時mm分";
    // 日付フォーマット
    public static final String DATE_SENDEMAIL_1 = "yyyy年MM月dd日 HH:mm";
    // 日付フォーマット
    public static final String DATE_FORMAT_ASS = "yyyyMMddHHmmssSSS";
    // 日付フォーマット
    public static final String DATE_FORMAT_APP = "yyyyMM";
    // 日付フォーマット
    public static final String DATE_FORMAT_APP_DATE = "yyyyMMdd";
    // 登録・更新日時フォーマット
    public static final String DATE_FORMAT_DB = "yyyy/MM/dd HH:mm:ss";
    // 日時フォーマット
    public static final String DATE_FORMAT_YMD = "yyyy/MM/dd";
    // 日付フォーマット
    public static final String DATE_FORMAT_APP_DATE2 = "yyyy-MM-dd";
    // 日付フォーマット
    public static final String DATE_FORMAT_SSS = "yyyy/MM/dd HH:mm:ss.SSS";

    public static final String DATE_FORMAT_CONTENTS = "yyyyMMdd HH:mm";
    // アプリケーションコード
    public static final String CONTENTS_APP = "YAMAGATA";
    // コンテンツ種別コード
    public static final String CONTENTS_TYPE = "CAMPAIGN";

    // PDF拡張名
    public static final String PDF = ".pdf";
    // CSV拡張名
    public static final String CSV = ".csv";
    // 圧縮ファイルの拡張名
    public static final String ZIP = ".zip";
    // TXT
    public static final String TXT = ".txt";
    // XLSX
    public static final String XLSX = ".xlsx";

    public static final String FEATURE = "Feature";

    public static final String SESSIONUSERINFO = "sessionUserInfo";
    public static final String LONGINID = "loginID";
    public static final String USERID = "userID";
    public static final String HIDUSERID = "hidUserID";
    public static final String PASSWORD = "password";
    public static final String COUNT = "count";
    public static final String SELECTFLG = "selectFlg";
    public static final String MESSAGEID = "errMessageID";
    public static final String FLG = "flg";
    public static final String LOCKSTATUS = "lockStatus";
    public static final String LONGINNAME = "loginNAME";

    // 戻るステータスコード
    public static final String RESULT_STATUS_OK = "OK";
    public static final String RESULT_STATUS_NG = "NG";
    public static final String SYSTEMERROR_CODE = "500";
    public static final String RESULT_SUCCESS_CODE = "I0000001";

    // ステータス
    public static final String APPLY_STATUS_1 = "受付";
    public static final String APPLY_STATUS_2 = "処理中";
    public static final String APPLY_STATUS_3 = "完了";

    // LogInfo
    public static final String IMPLEMENT_START = "実行開始";
    public static final String IMPLEMENT_END = "実行完了";
    public static final String SENNI = "画面へ遷移";

    public static final String INDEX = "条件検索";
    public static final String FIND = "単一検索処理";
    public static final String VIEW = "無条件検索";
    public static final String DYNAMICVIEW = "ビュー検索";
    public static final String SAVE = "登録処理";
    public static final String UPDATE = "更新処理";
    public static final String DELETE = "(論理)削除処理";
    public static final String REMOVE = "(物理)削除処理";
    public static final String LOGMESSAGE001 = "ビュー({})削除失敗";
    public static final String LOGMESSAGE002 = "ビュー({})削除異常";

    // ステータスMessage
    public static final String YAMAGATA_ALLPUSH_MESSAGE_HTML_START = "<!DOCTYPE html><html>" + "<head>"
            + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
            + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">"
            + "<meta name=\"format-detection\" content=\"telephone=yes\"/>" + "<title>プレビュー</title>" + "<script>"
            + "var u = navigator.userAgent;" + "var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1;"
            + "var isPc = u.indexOf('Windows') > -1 || u.indexOf('Win') > -1;" + "function openMoneyTree(){"
            + "if(isAndroid) {" + "android.openMoneyTree();}" + "else if (isPc) {" + "}"
            + "else { window.webkit.messageHandlers.callLinkOpenMoneyTree.postMessage({body:''}); }" + "}"
            + "function openHtmlView(url){" + "if(isAndroid) {" + "android.openURLWithChromeView(url);}"
            + "else if (isPc) {"
            + "window.open(url,'linkWin','height=768,innerHeight=768,width=1024,innerWidth=1024,top=50,left=80,toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');"
            + "}" + "else { window.webkit.messageHandlers.callLinkOpenSafari.postMessage({body:url}); }" + "}"
            + "function openTelView(phoneNumber){if(isAndroid) { " + "var telNumber = \"tel:\" +phoneNumber;"
            + "android.openTelView(telNumber);"
            + "} else if (isPc) {}else { var phone = \"'\" +phoneNumber + \"'\";window.webkit.messageHandlers.callLinkOpenTel.postMessage({body:phone}); } }"
            + "</script>" + "<style>" + " body {margin:0 auto;}"
            + "* {font-family: \"ヒラギノ角ゴ Pro W3\", \"Hiragino Kaku Gothic Pro\", メイリオ, Meiryo, \"ＭＳ Ｐゴシック\", \"MS PGothic\", sans-serif;color:#000000;}"
            // +".box{background-image:url(https://yamagatabank-api.scsk-api.minefocus.jp/images/contents/YAMAGATA_AM0001003_A.png);"
            // +"background-size:100% 100%;-moz-background-size:100% 100%;"
            // +"background-repeat:no-repeat;"
            // +"margin:0
            // auto;padding-left:20px;padding-right:20px;height:100%;}"
            // +"@media screen and (min-width:360px) and (min-height:640px) {
            // .box{height:640px;}}"
            + " .box{position:relative; z-index:10;padding-left:20px;padding-right:20px;height:100%;}"
            + " .imgstyle{position:fixed; bottom:0px; z-index:1;width:100%; height:0 auto;}" + "</style>"
            + "</head><body><div class=\"box\">";
    public static final String YAMAGATA_PUSH_MESSAGE_HTML_START = "<!DOCTYPE html><html class=\"login-content\"><head><meta content=\"text/html; charset=UTF-8\" http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><title>プレビュー</title><script>var u = navigator.userAgent;var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1;var isiOS = !!u.match(/\\(i[^;]+;( U;)? CPU.+Mac OS X/);function openTelView(){if(isAndroid) { android.openTelView('tel:0120-166-410');} else if (isiOS) {window.webkit.messageHandlers.callLinkOpenTel.postMessage({body:0120-166-410}); } }</script></head><body class=\"login-content\">";
    public static final String YAMAGATA_PUSH_MESSAGE_HTML_END = "</div><img src=\"https://yamagatabank-api.scsk-api.minefocus.jp/images/contents/YAMAGATA_AM0001003_A.png\"></body></html>";
    public static final String PUSH_MESSAGE_HTML_END = "</div></body></html>";
    public static final String ACCOUNT_SEQ = "\n受付番号：\n";
    public static final String RECEIPT_DATE = "受付日時：\n";
    public static final String APPLY_KIND = "\n申込種別：\nひろぎんネット支店口座開設\n\n";
    public static final String YAMAGATA_RECEIPT_DATE = "受付日時：<br/>";
    public static final String YAMAGATA_ACCOUNT_SEQ = "<br/>受付番号：<br/>";
    public static final String YAMAGATA_APPLY_KIND = "<br/>申込種別：<br/>口座開設<br/><br/>";
    public static final String YAMAGATA_PUSH_MESSAGE_STATUS_1 = "[山形銀行より]"
            + "<br/>このたびは口座開設のお申し込みをいただきまして、誠にありがとうございます。" + "<br/><br/>お客さまの申し込みを受付しました。"
            + "<br/>口座開設完了まで今しばらくお待ちください。" + "<br/><br/>";
    public static final String YAMAGATA_PUSH_MESSAGE_STATUS_2 = "[山形銀行より]" + "<br/>お客さまの口座開設手続きを開始いたしました。"
            + "<br/><br/>近日中（平日の9：00～17：00）にお届けいただいた電話番号にご本人さま確認のお電話をさせていただきますのでよろしくお願いいたします。"
            + "<br/><br/>※ご連絡がつかない場合は、お申し込みをお断りさせていただきますので予めご了承ください。 " + "<br/><br/>";
    public static final String YAMAGATA_PUSH_MESSAGE_STATUS_3 = "<br/>[山形銀行より]" + "<br/>お客さまの口座開設手続きが完了しました。"
            + "<br/><br/>近日中にキャッシュカードおよび＜やまぎん＞ネットバンクご利用カードを「本人限定受取郵便（特定事項伝達型）」にて送付いたします。"
            + "<br/>お受け取り時には本人確認書類として公的書類（運転免許証、パスポート、健康保険証等）をご提示ください。"
            + "<br/><br/>郵便物をお受け取りいただけなかった場合、お申し込みを無効とさせていただくことがございます。"
            + "<br/><br/>また、キャッシュカードとともにお送りする印鑑票はご記入、ご捺印のうえ同封の返信用封筒にて速やかにご返送いただきますようお願いいたします。"
            + "<br/><br/>引き続き山形銀行をご愛顧いただきますようお願い申し上げます。" + "<br/><br/>";
    public static final String YAMAGATA_PUSH_MESSAGE_STATUS_4 = "[山形銀行より]"
            + "<br/>このたびは口座開設のお申し込みをいただきまして、誠にありがとうございました。" + "<br/><br/>誠に申し訳ございませんが、今回のお申し込みはお受付できませんでした。"
            + "<br/><br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。" + "<br/><br/>";
    public static final String YAMAGATA_PUSH_MESSAGE_STATUS_5 = "[山形銀行より]"
            + "<br/>このたびは口座開設のお申し込みをいただきまして、誠にありがとうございました。" + "<br/><br/>今回のお申し込みはご本人さまのお申し出により、取り下げとさせていただきました。"
            + "<br/><br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。" + "<br/><br/>";
    public static final String YAMAGATA_PUSH_MESSAGE_STATUS_6 = "[山形銀行より]"
            + "<br/>このたびは口座開設のお申し込みをいただきまして、誠にありがとうございます。" + "<br/><br/>口座開設のお手続きに際しまして、ご連絡事項がございます。"
            + "<br/><br/>お手数ですが、ご都合のよい時間に下記電話番号までご連絡いただきますようよろしくお願いいたします。"
            + "<br/><a href='javascript:void(0);' onclick='javascript:openTelView();';>0120-166-410</a>（通話料は無料です）<br/><br/>";
    public static final String YAMAGATA_PUSH_MESSAGE_ABOUT = "【お問い合わせ】<br/>山形銀行　インターネット支店<br/>TEL：0120-166-410<br/>受付時間：9:00 ～ 17:00<br/>（銀行休業日を除きます。）<br/>";
    public static final String YAMAGATA_PUSH_MESSAGE_TITLE_1 = "申し込みを受付しました。口座開設完了まで今しばらくお待ちください。";
    public static final String YAMAGATA_PUSH_MESSAGE_TITLE_2 = "口座開設手続きを開始いたしました。";
    public static final String YAMAGATA_PUSH_MESSAGE_TITLE_9 = "口座開設のお手続きに際しまして、ご連絡事項がございます。";
    public static final String YAMAGATA_PUSH_MESSAGE_TITLE_3 = "口座開設手続きが完了しました。";
    public static final String YAMAGATA_PUSH_MESSAGE_TITLE_4 = "誠に申し訳ございませんが、今回のお申し込みはお受付できませんでした。";
    public static final String YAMAGATA_PUSH_MESSAGE_TITLE_5 = "誠に申し訳ございませんが、今回のお申し込みはお受付できませんでした。";
    public static final String YAMAGATA_PUSH_MESSAGE_TITLE_6 = "誠に申し訳ございませんが、今回のお申し込みはお受付できませんでした。";
    public static final String YAMAGATA_PUSH_MESSAGE_TITLE_7 = "誠に申し訳ございませんが、今回のお申し込みはお受付できませんでした。";
    public static final String YAMAGATA_PUSH_MESSAGE_TITLE_8 = "今回のお申込みはご本人さまのお申出により、取り下げとさせていただきました。";

    public static final String PUSH_MESSAGE_STATUS_1 = "[ひろぎんネット支店より]" + "\nこのたびは口座開設のお申込みをいただきまして、誠にありがとうございます。"
            + "\n\nお客さまの申込を受付しました。" + "\n口座開設完了まで今しばらくお待ちください。" + "\n\n";

    public static final String PUSH_MESSAGE_STATUS_2 = "[ひろぎんネット支店より]" + "\nお客さまの口座開設手続きを開始いたしました。"
            + "\n\n近日中（平日の9：00～17：00）にお届けいただいた電話番号にご本人さま確認のお電話をさせていただきますのでよろしくお願いいたします。" + "\n\n";

    public static final String PUSH_MESSAGE_STATUS_3 = "[ひろぎんネット支店より]" + "\nお客さまの口座開設手続きが完了しました。"
            + "\n\n近日中にキャッシュカードおよびダイレクトバンキングサービスご利用カードをそれぞれ「本人限定受取郵便（特定事項伝達型）」にて送付いたします。" + "\nお受け取り時には本人確認書類をご提示ください。"
            + "\n\n郵便物をお受け取りいただけなかった場合、お申込みを無効とさせていただくことがございます。"
            + "\n\nまた、キャッシュカードとともにお送りする印鑑票はご記入、ご捺印のうえ同封の返信用封筒にて速やかにご返送いただきますようお願いいたします。"
            + "\n\n引き続きひろぎんネット支店をご愛顧いただきますようお願い申し上げます。" + "\n\n";

    public static final String PUSH_MESSAGE_STATUS_4 = "[ひろぎんネット支店より]" + "\nこのたびは口座開設のお申込みをいただきまして、誠にありがとうございました。"
            + "\n\n誠に申し訳ございませんが、今回のお申込みはお受付できませんでした。" + "\n詳細については、お申込みいただいた住所宛に郵送でお送りさせていただきますので、そちらでご確認ください。"
            + "\n\nなお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。" + "\n\n";

    public static final String PUSH_MESSAGE_STATUS_5 = "[ひろぎんネット支店より]" + "\nこのたびは口座開設のお申込みをいただきまして、誠にありがとうございました。"
            + "\n\n誠に申し訳ございませんが、今回のお申込みはお受付できませんでした。" + "\n詳細については、お申込みいただいた住所宛に郵送でお送りさせていただきますので、そちらでご確認ください。"
            + "\n\nなお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。" + "\n\n";

    public static final String PUSH_MESSAGE_STATUS_6 = "[ひろぎんネット支店より]" + "\nこのたびは口座開設のお申込みをいただきまして、誠にありがとうございました。"
            + "\n\n誠に申し訳ございませんが、今回のお申込みはお受付できませんでした。" + "\n詳細については、お申込みいただいた住所宛に郵送でお送りさせていただきますので、そちらでご確認ください。"
            + "\n\nなお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。" + "\n\n";

    public static final String PUSH_MESSAGE_STATUS_7 = "[ひろぎんネット支店より]" + "\nこのたびは口座開設のお申込みをいただきまして、誠にありがとうございました。"
            + "\n\n誠に申し訳ございませんが、今回のお申込みはお受付できませんでした。" + "\n詳細については、お申込みいただいた住所宛に郵送でお送りさせていただきますので、そちらでご確認ください。"
            + "\n\nなお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。" + "\n\n";

    public static final String PUSH_MESSAGE_STATUS_8 = "[ひろぎんネット支店より]" + "\nこのたびは口座開設のお申込みをいただきまして、誠にありがとうございました。"
            + "\n\n今回のお申込みはご本人さまのお申出により、取り下げとさせていただきました。" + "\n\nなお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。" + "\n\n";

    public static final String PUSH_MESSAGE_ABOUT = "【お問い合わせ】\nひろぎんネット支店\nTEL：0120-93-1645\n平日／9:00 ～ 17:00\n（土・日・祝休日、および大晦日・正月3が日は除く）\n";
    // ステータスMessage
    public static final String HYAKUJYUSH_ALLPUSH_MESSAGE_HTML_START = "<!DOCTYPE html><html>" + "<head>"
            + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
            + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">"
            + "<meta name=\"format-detection\" content=\"telephone=yes\"/>" + "<title>プレビュー</title>" + "<script>"
            + "var u = navigator.userAgent;" + "var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1;"
            + "var isPc = u.indexOf('Windows') > -1 || u.indexOf('Win') > -1;" + "function openMoneyTree(){"
            + "if(isAndroid) {" + "android.openMoneyTree();}" + "else if (isPc) {" + "}"
            + "else { window.webkit.messageHandlers.callLinkOpenMoneyTree.postMessage({body:''}); }" + "}"
            + "function openHtmlView(url){" + "if(isAndroid) {" + "android.openURLWithChromeView(url);}"
            + "else if (isPc) {"
            + "window.open(url,'linkWin','height=768,innerHeight=768,width=1024,innerWidth=1024,top=50,left=80,toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');"
            + "}" + "else { window.webkit.messageHandlers.callLinkOpenSafari.postMessage({body:url}); }" + "}"
            + "function openTelView(phoneNumber){if(isAndroid) { " + "var telNumber = \"tel:\" +phoneNumber;"
            + "android.openTelView(telNumber);"
            + "} else if (isPc) {}else { var phone = \"'\" +phoneNumber + \"'\";window.webkit.messageHandlers.callLinkOpenTel.postMessage({body:phone}); } }"
            + "</script>" + "<style>" + " body {margin:0 auto;}"
            + "* {font-family: \"ヒラギノ角ゴ Pro W3\", \"Hiragino Kaku Gothic Pro\", メイリオ, Meiryo, \"ＭＳ Ｐゴシック\", \"MS PGothic\", sans-serif;color:#000000;}"
            // +".box{background-image:url(https://HYAKUJYUSHbank-api.scsk-api.minefocus.jp/images/contents/HYAKUJYUSH_AM0001003_A.png);"
            // +"background-size:100% 100%;-moz-background-size:100% 100%;"
            // +"background-repeat:no-repeat;"
            // +"margin:0
            // auto;padding-left:20px;padding-right:20px;height:100%;}"
            // +"@media screen and (min-width:360px) and (min-height:640px) {
            // .box{height:640px;}}"
            + " .box{position:relative; z-index:10;padding-left:20px;padding-right:20px;height:100%;}"
            + " .imgstyle{position:fixed; bottom:0px; z-index:1;width:100%; height:0 auto;}" + "</style>"
            + "</head><body><div class=\"box\">";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_HTML_END = "</div></body></html>";
    public static final String HYAKUJYUSH_RECEIPT_DATE = "受付日時：<br/>";
    public static final String HYAKUJYUSH_ACCOUNT_SEQ = "<br/>受付番号：<br/>";
    public static final String HYAKUJYUSH_APPLY_KIND = "<br/>申込種別：<br/>";
    public static final String HYAKUJYUSHI_APPLY_KIND_1= "百十四銀行：";
    public static final String HYAKUJYUSHI_APPLY_KIND_2= "口座開設";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_1 = "[百十四銀行より]"
            + "<br/>このたびはローンのお申込みをいただきまして、誠にありがとうございます。" + "<br/>お客さまの申込を受付しました。" + "<br/>ローン申込完了まで今しばらくお待ちください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_2 = "[百十四銀行より]" + "<br/>お客さまのローン開設手続きを開始いたしました。"
            + "<br/><br/>近日中（平日の9：00～17：00）にお届けいただいた電話番号にご本人さま確認のお電話をさせていただきますのでよろしくお願いいたします。"
            + "<br/><br/>※ご連絡がつかない場合は、お申し込みをお断りさせていただきますので予めご了承ください。 " + "<br/><br/>";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_3 = "[百十四銀行より]" + "<br/>お客さまのローン申込手続きが完了しました。<br/><br/>";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_3_1 = "の窓口へ来店いただくようお願いします。<br/>来店の際には本人確認書類をご提示ください。<br/>引き続き百十四銀行をご愛顧いただきますようお願い申し上げます。"
            + "<br/>近日中に○○支店の窓口へ来店いただくようお願いします。" + "<br/>お受け取り時には本人確認書類として公的書類（運転免許証、パスポート、健康保険証等）をご提示ください。"
            + "<br/><br/>郵便物をお受け取りいただけなかった場合、お申し込みを無効とさせていただくことがございます。"
            + "<br/><br/>また、キャッシュカードとともにお送りする印鑑票はご記入、ご捺印のうえ同封の返信用封筒にて速やかにご返送いただきますようお願いいたします。"
            + "<br/><br/>引き続き百十四銀行をご愛顧いただきますようお願い申し上げます。" + "<br/><br/>";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_4 = "[百十四銀行より]"
            + "<br/>このたびはローンのお申込みをいただきまして、誠にありがとうございました。" + "<br/>誠に申し訳ございませんが、今回のローンのお申込みはお受付できませんでした。"
            + "<br/>詳細については、お申込みいただいた住所宛に郵送でお送りさせていただきますので、そちらでご確認ください。<br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_ABOUT_LOAN = "【お問い合わせ】<br/>ダイレクトローン受付センター<br/>TEL：0120-091296<br/>24時間365日受付<br/>";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_TITLE_LOAN_1 = "スマホアプリから送信された申込データをサーバが受付した際に自動送信する。";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_TITLE_LOAN_2 = "管理サイト（DBセンターPC）で帳票出力した際に自動送信する。";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_TITLE_LOAN_3 = "管理サイト（DBセンターPC）で完了消込した際に自動送信する。";
    public static final String HYAKUJYUSH_PUSH_MESSAGE_TITLE_LOAN_4 = "管理サイト（DBセンターPC）でステータスを「却下（総合的判断）」に変更した際に自動送信する。";
   
    public static final String HYAKUJYUSHI_APPLY_KIND_DOCUMENT_1= "各種書類届出　運転免許証　";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_DOCUMENT_1 = "[百十四銀行より]"
            + "<br/>このたびは書類の届出をいただきまして、誠にありがとうございます。" + "<br/>お客さまの書類届出を受付しました。<br/>書類届出の手続き完了まで今しばらくお待ちください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_DOCUMENT_2 = "[百十四銀行より]"
            + "<br/>お客さまの書類届出の手続きを開始いたしました。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_DOCUMENT_3 = "[百十四銀行より]"
            + "<br/>お客さまの書類届出の手続きが完了しました。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_DOCUMENT_4 = "[百十四銀行より]"
            + "<br/>このたびは書類の届出をいただきまして、誠にありがとうございました。" 
            + "<br/>誠に申し訳ございませんが、送付いただいた書類が不鮮明のため届出はお受付できませんでした。"
            + "<br/>再度、ご送付いただくようお願いいたします。"
            + "<br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_DOCUMENT_5 = "[百十四銀行より]"
            + "<br/>このたびは書類の届出をいただきまして、誠にありがとうございました。"
            + "<br/>誠に申し訳ございませんが、送付いただいた書類が相違していたため届出はお受付できませんでした。"
            + "<br/>再度、必要な書類をご送付いただくようお願いいたします。"
            + "<br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_DOCUMENT_6 = "[百十四銀行より]"
            + "<br/>このたびは書類の届出をいただきまして、誠にありがとうございました。" 
            + "<br/>誠に申し訳ございませんが、送付いただいた書類に不備があるため届出はお受付できませんでした。"
            + "<br/>再度、ご確認の上ご送付いただくようお願いいたします。"
            + "<br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_DOCUMENT_1 = "スマホアプリから送信された申込データをサーバが受付した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_DOCUMENT_2 = "管理サイト（DBセンターPC）で帳票出力した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_DOCUMENT_3 = "管理サイト（DBセンターPC）で完了消込した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_DOCUMENT_4 = "ステータスを「再送付依頼（不鮮明）」に変更した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_DOCUMENT_5 = "ステータスを「再送付依頼（資料相違）」に変更した際に自動送信する。"; 
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_DOCUMENT_6 = "ステータスを「再送付依頼（資料不備）」に変更した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_ABOUT_DOCUMENT = "【お問い合わせ】<br/>ダイレクトローン受付センター<br/>TEL：0120-091296<br/>平日／9:00 ～ 20：00<br/>土・日・祝休日／9：00～17：00<br/>（5月3日から5月5日、および12月31日から1月3日は除く）";
    
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_1 = "[百十四銀行より]"
            + "<br/>このたびは口座開設のお申込みをいただきまして、誠にありがとうございます。" + "<br/>お客さまの申込を受付しました。<br/>口座開設完了まで今しばらくお待ちください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_3 = "[百十四銀行より]"
            + "<br/>お客さまの口座開設手続きが完了しました。" 
            + "<br/>近日中にキャッシュカードと通帳を「本人限定受取郵便（特定事項伝達型）」にて送付いたします。お受け取り時には運転免許証をご提示ください。"
            + "<br/>またダイレクトバンキングサービスご利用カードもご契約された方に別途送付いたします。"
            + "<br/>郵便物をお受け取りいただけなかった場合、お申込みを無効とさせていただくことがございます。"
            + "<br/>引き続き百十四銀行をご愛顧いただきますようお願い申し上げます。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_2 = "[百十四銀行より]"
            + "<br/>お客さまの口座開設手続きを開始いたしました。" 
            + "<br/>近日中（平日の9：00～17：00）にお届けいただいた電話番号にご本人さま確認のお電話をさせていただきますのでよろしくお願いいたします。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_4 = "[百十四銀行より]"
            + "<br/>このたびは口座開設のお申込みをいただきまして、誠にありがとうございました。" 
            + "<br/>誠に申し訳ございませんが、今回のお申込みはお受付できませんでした。"
            + "<br/>詳細については、お申込みいただいた住所宛に郵送でお送りさせていただきますので、そちらでご確認ください。"
            + "<br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_5 = "[百十四銀行より]"
            + "<br/>このたびは口座開設のお申込みをいただきまして、誠にありがとうございました。"
            + "<br/>誠に申し訳ございませんが、今回のお申込みはお受付できませんでした。"
            + "<br/>詳細については、お申込みいただいた住所宛に郵送でお送りさせていただきますので、そちらでご確認ください。"
            + "<br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_6 = "[百十四銀行より]"
            + "<br/>このたびは口座開設のお申込みをいただきまして、誠にありがとうございました。" 
            + "<br/>誠に申し訳ございませんが、今回のお申込みはお受付できませんでした。"
            + "<br/>詳細については、お申込みいただいた住所宛に郵送でお送りさせていただきますので、そちらでご確認ください。"
            + "<br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_STATUS_7 = "[百十四銀行より]"
            + "<br/>このたびは口座開設のお申込みをいただきまして、誠にありがとうございました。" 
            + "<br/>誠に申し訳ございませんが、今回のお申込みはお受付できませんでした。"
            + "<br/>詳細については、お申込みいただいた住所宛に郵送でお送りさせていただきますので、そちらでご確認ください。"
            + "<br/>なお、お申込データは、こちらで破棄させていただきますので、何卒ご了承ください。"
            + "<br/><br/>";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_1 = "スマホアプリから送信された申込データをサーバが受付した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_2 = "管理サイト（DBセンターPC）で帳票出力した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_3 = "管理サイト（DBセンターPC）で完了消込した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_4 = "管理サイト（DBセンターPC）でステータスを「却下（総合的判断）」に変更した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_5 = "管理サイト（DBセンターPC）でステータスを「却下（本人確認連絡不可）」に変更した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_6 = "管理サイト（DBセンターPC）でステータスを「却下（郵便受取不可）」に変更した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_TITLE_7= "管理サイト（DBセンターPC）でステータスを「却下（内容不備）」に変更した際に自動送信する。";
    public static final String HYAKUJYUSHI_PUSH_MESSAGE_ABOUT = "【お問い合わせ】<br/>１１４サリュダイヤル<br/>TEL：0120-114001<br/>平日／9:00 ～ 20：00<br/>土・日・祝休日／9：00～17：00<br/>（5月3日から5月5日、および12月31日から1月3日は除く）";
    
    
    public static final String RETURN_OK = "202";

    // ドキュメントタイプ
    public static final String DOCTYPE_1 = "PUSHRECORD";
    public static final String DOCTYPE_2 = "ACCOUNTSUM";
    public static final String DOCTYPE_3 = "AUTHORITY";
    public static final String DOCTYPE_4 = "ACTIONLOG";
    public static final String DOCTYPE_5 = "STOREATMDATAFILE";
    public static final String DOCTYPE_6 = "CONTENTS_APP";
    public static final String DOCTYPE_7 = "CONTENTS_TYPE";
    public static final String DOCTYPE_8 = "CONTENTS";
    public static final String YAMAGATA_USERINFO = "USERINFO";
    public static final String ACCOUNT_IMAGE = "ACCOUNT_IMAGE";
    public static final String DOCUMENT_IMAGE = "DOCUMENT_IMAGE";

    // 帳票/CSV表示用文字
    public static final String OCCUPATION_1 = "会社役員/ 団体役員";
    public static final String OCCUPATION_2 = "会社員/ 団体職員";
    public static final String OCCUPATION_3 = "公務員";
    public static final String OCCUPATION_4 = "個人事業主/ 自営業";
    public static final String OCCUPATION_5 = "パート／アルバイト／派遣社員／契約社員";
    public static final String OCCUPATION_6 = "主婦";
    public static final String OCCUPATION_7 = "学生";
    public static final String OCCUPATION_8 = "退職された方／無職の方";

    // iyo帳票/CSV表示用文字
    public static final String iyoOCCUPATION_1 = "会社役員/ 団体役員";
    public static final String iyoOCCUPATION_2 = "会社員/ 団体職員";
    public static final String iyoOCCUPATION_3 = "公務員";
    public static final String iyoOCCUPATION_4 = "個人事業主/ 自営業";
    public static final String iyoOCCUPATION_5 = "主婦";
    public static final String iyoOCCUPATION_6 = "学生";
    public static final String iyoOCCUPATION_7 = "退職された方/ 無職の方";
    public static final String iyoOCCUPATION_8 = "パート/ アルバイト/ 派遣社員/ 契約社員";
    public static final String iyoOCCUPATION_9 = "その他";

    public static final String JOBKBN_01 = "会社役員/ 団体役員";
    public static final String JOBKBN_02 = "会社員・団体職員";
    public static final String JOBKBN_03 = "公務員";
    public static final String JOBKBN_04 = "個人事業主・自営業";
    public static final String JOBKBN_05 = "パート・アルバイト";
    public static final String JOBKBN_06 = "派遣・嘱託・契約社員";
    public static final String JOBKBN_07 = "主婦・主夫";
    public static final String JOBKBN_08 = "年金受給者";
    public static final String JOBKBN_09 = "学生";
    public static final String JOBKBN_10 = "無職";
    public static final String JOBKBN_49 = "その他";
    public static final String JOBKBN_101 = "会社役員/団体役員";
    public static final String JOBKBN_102 = "会社員/団体職員";
    public static final String JOBKBN_103 = "公務員";
    public static final String JOBKBN_104 = "派遣社員等";
    public static final String JOBKBN_105 = "パート/アルバイト";
    public static final String JOBKBN_106 = "主婦";
    public static final String JOBKBN_107 = "学生";
    public static final String JOBKBN_108 = "退職された方/無職の方";
    public static final String JOBKBN_199 = "その他";

    public static final String PREFETURESCODE_1 = "北海道";
    public static final String PREFETURESCODE_2 = "青森県";
    public static final String PREFETURESCODE_3 = "岩手県";
    public static final String PREFETURESCODE_4 = "宮城県";
    public static final String PREFETURESCODE_5 = "秋田県";
    public static final String PREFETURESCODE_6 = "山形県";
    public static final String PREFETURESCODE_7 = "福島県";
    public static final String PREFETURESCODE_8 = "茨城県";
    public static final String PREFETURESCODE_9 = "栃木県";
    public static final String PREFETURESCODE_10 = "群馬県";
    public static final String PREFETURESCODE_11 = "埼玉県";
    public static final String PREFETURESCODE_12 = "千葉県";
    public static final String PREFETURESCODE_13 = "東京都";
    public static final String PREFETURESCODE_14 = "神奈川県";
    public static final String PREFETURESCODE_15 = "新潟県";
    public static final String PREFETURESCODE_16 = "富山県";
    public static final String PREFETURESCODE_17 = "石川県";
    public static final String PREFETURESCODE_18 = "福井県";
    public static final String PREFETURESCODE_19 = "山梨県";
    public static final String PREFETURESCODE_20 = "長野県";
    public static final String PREFETURESCODE_21 = "岐阜県";
    public static final String PREFETURESCODE_22 = "静岡県";
    public static final String PREFETURESCODE_23 = "愛知県";
    public static final String PREFETURESCODE_24 = "三重県";
    public static final String PREFETURESCODE_25 = "滋賀県";
    public static final String PREFETURESCODE_26 = "京都府";
    public static final String PREFETURESCODE_27 = "大阪府";
    public static final String PREFETURESCODE_28 = "兵庫県";
    public static final String PREFETURESCODE_29 = "奈良県";
    public static final String PREFETURESCODE_30 = "和歌山県";
    public static final String PREFETURESCODE_31 = "鳥取県";
    public static final String PREFETURESCODE_32 = "島根県";
    public static final String PREFETURESCODE_33 = "岡山県";
    public static final String PREFETURESCODE_34 = "広島県";
    public static final String PREFETURESCODE_35 = "山口県";
    public static final String PREFETURESCODE_36 = "徳島県";
    public static final String PREFETURESCODE_37 = "香川県";
    public static final String PREFETURESCODE_38 = "愛媛県";
    public static final String PREFETURESCODE_39 = "高知県";
    public static final String PREFETURESCODE_40 = "福岡県";
    public static final String PREFETURESCODE_41 = "佐賀県";
    public static final String PREFETURESCODE_42 = "長崎県";
    public static final String PREFETURESCODE_43 = "熊本県";
    public static final String PREFETURESCODE_44 = "大分県";
    public static final String PREFETURESCODE_45 = "宮崎県";
    public static final String PREFETURESCODE_46 = "鹿児島県";
    public static final String PREFETURESCODE_47 = "沖縄県";

    public static final String ORDINARYDEPOSITERAKBN_1 = "明治";
    public static final String ORDINARYDEPOSITERAKBN_2 = "大正";
    public static final String ORDINARYDEPOSITERAKBN_3 = "昭和";
    public static final String ORDINARYDEPOSITERAKBN_4 = "平成";

    public static final String USERTYPE_0 = "仮ユーザ";
    public static final String USERTYPE_1 = "Emailユーザ";
    public static final String USERTYPE_2 = "facebook連携ユーザ";
    public static final String USERTYPE_3 = "Yahoo連携ユーザ";
    public static final String USERTYPE_4 = "Google連携ユーザ";
    public static final String USERTYPE_5 = "MoneyTree ID";

    public static final String SEX_1 = "男";
    public static final String SEX_2 = "女";
    public static final String DAY_1 = "月曜日";
    public static final String DAY_2 = "火曜日";
    public static final String DAY_3 = "水曜日";
    public static final String DAY_4 = "木曜日";
    public static final String DAY_5 = "金曜日";
    public static final String DAY_6 = "土曜日";
    public static final String DAY_7 = "日曜日";

    public static final String YEAR = "年";
    public static final String MONTH_JP = "月";
    public static final String DAY = "日";

    public static final String TRANSACTIONTYPE = "普　通";

    public static final String MARK1 = "〇";
    public static final String MARK2 = "~";
    public static final String MARK3 = "-";
    public static final String MARK4 = "/";

    public static final String SHOPNO_1 = "2";
    public static final String SHOPNO_2 = "9";
    public static final String SHOPNO_3 = "1";
    public static final String SHOPNAME = "ひろぎんネット支店";

    public static final String IDENTIFICATIONTYPE_1 = "運転免許証";
    public static final String IDENTIFICATIONTYPE_2 = "パスポート";
    public static final String IDENTIFICATIONTYPE_3 = "住基カード";
    public static final String IDENTIFICATIONTYPE_4 = "マイナンバーカード";
    public static final String LIVINGCONDITIONS_1 = "住民票";
    public static final String LIVINGCONDITIONS_2 = "公共料金領収証";

    public static final String STOREATM_DOCTYPE = "STOREATMINFO";
    public static final String USER_DOCTYPE = "USER";
    public static final String IMAGEFILE_DOCTYPE = "IMAGEFILE";
    public static final String USERACTIONLOG_DOCTYPE = "USERACTIONLOG";
    public static final String PWDHISTORY_DOCTYPE = "PWDHISTORY";
    public static final String COLON = ":";
    public static final String ACCOUNT_DOCTYPE = "ACCOUNTAPPLICATIONYAMAGATA";
    public static final String MSGTITLE_DOCTYPE = "MSG_TITLE";
    public static final String MSGDETAIL_DOCTYPE = "MSG_DETAIL";
    public static final String MSGOPENSTATUS_DOCTYPE = "MSG_OPENSTATUS";
    public static final String FOLLOWINFO_DOCTYPE = "FOLLOWINFO";
    public static final String CLIPINFO_DOCTYPE = "CLIPINFO";
    public static final String ACCOUNT_APP = "ACCOUNT_APP";
    public static final String ACCOUNT_DOCUMENT = "ACCOUNT_DOCUMENT";

    public static final String VERSIONDOC_DOCTYPE = "VERSIONDOC";

    public static final String STATUS_MODIFY = "STATUS_MODIFY";
    public static final String STATUS_MODIFY_LOAN = "STATUS_MODIFY_LOAN";
    public static final String PUSH_RECORD_LOAN = "PUSH_RECORD_LOAN";
    public static final String PUSH_RECORD = "PUSH_RECORD";
    public static final String PUSH_DETAIL = "PUSH_DETAIL";
    public static final String PUSH_DETAIL_LOAN = "PUSH_DETAIL_LOAN";
    public static final String GLOBAL_DOC = "GLOBAL_DOC";
    public static final String DOCUMENT_STATUS_MODIFY = "MSG_OPENSTATUS_DOCUMENT";
    public static final String DOCUMENT_PUSH_RECORD = "PUSH_RECORD_DOCUMENT";
    public static final String DOCUMENT_PUSH_DETAIL = "PUSH_DETAIL_DOCUMENT";

    public static final String HOLDACCOUNT_0 = "無";
    public static final String HOLDACCOUNT_1 = "有";

    public static final String BANKBOOKDESIGNKBN_1 = "キャラクター";
    public static final String BANKBOOKDESIGNKBN_2 = "総合口座-レア";
    public static final String BANKBOOKDESIGNKBN_3 = "普通預金-通常";
    public static final String BANKBOOKDESIGNKBN_4 = "総合口座-通常";

    public static final String CARDDESIGNKBN_1 = "キャラクター";
    public static final String CARDDESIGNKBN_2 = "総合口座-レア";
    public static final String CARDDESIGNKBN_3 = "普通預金-通常";
    public static final String CARDDESIGNKBN_4 = "総合口座-通常";

    public static final String TRADINGPURPOSES_1 = "生活費決済";
    public static final String TRADINGPURPOSES_2 = "給料受取/ 年金受取";
    public static final String TRADINGPURPOSES_3 = "貯蓄/ 資産運用";
    public static final String TRADINGPURPOSES_4 = "融資";
    public static final String TRADINGPURPOSES_5 = "外国為替取引";

    public static final String ACCOUNTPURPOSE_01 = "生計費決済";
    public static final String ACCOUNTPURPOSE_02 = "事業費決済";
    public static final String ACCOUNTPURPOSE_03 = "給与受取";
    public static final String ACCOUNTPURPOSE_04 = "年金受取";
    public static final String ACCOUNTPURPOSE_05 = "仕送り";
    public static final String ACCOUNTPURPOSE_06 = "貯蓄";
    public static final String ACCOUNTPURPOSE_07 = "資産運用";
    public static final String ACCOUNTPURPOSE_08 = "融資返済用口座";
    public static final String ACCOUNTPURPOSE_09 = "外国為替取引";
    public static final String ACCOUNTPURPOSE_99 = "その他";

    public static final String ACCOUNTAPPMOTIVE_1 = "近くに<ひろぎん>の店舗がないから";
    public static final String ACCOUNTAPPMOTIVE_2 = "日中店頭に行く時間がないから";
    public static final String ACCOUNTAPPMOTIVE_3 = "24時間申込み、取引が可能だから";
    public static final String ACCOUNTAPPMOTIVE_4 = "給与振込や公共料金の引落口座に使いたいから";
    public static final String ACCOUNTAPPMOTIVE_5 = "当行から案内されたから";
    public static final String ACCOUNTAPPMOTIVE_6 = "ネットオークションに利用したいから";
    public static final String ACCOUNTAPPMOTIVE_7 = "ペイオフ対策";
    public static final String ACCOUNTAPPMOTIVE_8 = "定期預金等の金利にを魅力感じたから";
    public static final String ACCOUNTAPPMOTIVE_9 = "広島銀行との取引を希望するから";
    public static final String ACCOUNTAPPMOTIVE_10 = "学生証・社員証の作成に必要だから";
    public static final String ACCOUNTAPPMOTIVE_90 = "その他";

    public static final String KNOWPROCESS_1 = "ひろぎんホームページ";
    public static final String KNOWPROCESS_2 = "ひろぎんホームページ以外のサイト";
    public static final String KNOWPROCESS_3 = "ウェブ広告";
    public static final String KNOWPROCESS_4 = "新聞広告";
    public static final String KNOWPROCESS_5 = "行員の案内";
    public static final String KNOWPROCESS_6 = "当行からのＤＭや電子メール";
    public static final String KNOWPROCESS_7 = "その他";

    public static final String KNOWPROCESS_01 = "当行ホームページ";
    public static final String KNOWPROCESS_02 = "当行ホームページ以外のサイト";
    public static final String KNOWPROCESS_03 = "ウェブ広告";
    public static final String KNOWPROCESS_04 = "当行からの案内";
    public static final String KNOWPROCESS_05 = "友人・知人からの紹介";
    public static final String KNOWPROCESS_06 = "その他";
    public static final String PUSH_RIREKI_TORIHIKIDATA = "PUSH_RIREKI_TORIHIKIDATA";
    public static final String PUSH_RIREKI_TORIHIKIDATA_RECORD = "PUSH_RIREKI_TORIHIKIDATA_RECORD";
    // ＩＰアドレス/電話番号鑑定
    public static final String MONTH = "ヵ月";
    public static final String PROXYVALUE_1 = "要注意（利用している可能性あり）";
    public static final String PROXYVALUE_2 = "-";
    public static final String THREAT_1 = "high";
    public static final String THREAT_2 = "low";
    public static final String THREATVALUE_1 = "要注意";
    public static final String THREATVALUE_2 = "低い";
    public static final String PSIP_1 = "true";
    public static final String PSIP_2 = "false";
    public static final String PSIPVALUE_1 = "要注意";
    public static final String PSIPVALUE_2 = "-";
    public static final String ISMOBILEVALUE = "携帯電話ＩＰアドレス以外";
    public static final String RESULTVALUE_1 = "検索成功";
    public static final String RESULTVALUE_2 = "データ無し";
    public static final String RESULTVALUE_3 = "電話番号の桁数不正";
    public static final String RESULTVALUE_4 = "特殊電話番号等調査対象外、予期しないエラー";
    public static final String ATTENTIONVALUE_1 = "MSG該当なし：｢直近加入」「都合停止」「変更過多」｢長期有効」「エラー」いずれにも該当しない場合";
    public static final String ATTENTIONVALUE_2 = "直近加入：三カ月以内に有効になった番号";
    public static final String ATTENTIONVALUE_3 = "都合停止：半年以内に都合停止がある番号";
    public static final String ATTENTIONVALUE_4 = "変更過多：有効無効を3回以上繰り返している番号";
    public static final String ATTENTIONVALUE_5 = "長期有効：24ヵ月以上有効である番号";
    public static final String ATTENTIONVALUE_6 = "ｴﾗｰ：特殊番号等で調査不可";
    public static final String ATTENTIONVALUE_7 = "未調査：対象ﾃﾞｰﾀが無い番号";
    public static final String TACSFLAGVALUE_1 = "無効：電話番号が使われていない";
    public static final String TACSFLAGVALUE_2 = "有効：電話番号が使われている";;
    public static final String TACSFLAGVALUE_3 = "移転：移転ｱﾅｳﾝｽが流れている電話番号";
    public static final String TACSFLAGVALUE_4 = "都合停止：お客様都合で利用ができない電話番号";
    public static final String TACSFLAGVALUE_5 = "ｴﾗｰ：判定できない電話番号もしくは050、0120局番等の電話番号";
    public static final String TACSFLAGVALUE_6 = "局預け：電話局に電話番号を預けている";
    public static final String TACSFLAGVALUE_7 = "再調査：判定できない電話番号";
    public static final String TACSFLAGVALUE_8 = "INS回線有効：電話番号が使われている（ISDN回線）";
    public static final String TACSFLAGVALUE_9 = "NTT以外：NTT以外で一部判定ができない電話番号";
    public static final String TACSFLAGVALUE_10 = "ﾃﾞｰﾀ通信専用端末：モバイルWiFi等のデータ通信専用端末の電話番号";
    public static final String ACTIONLOG_LOGIN_1 = "【ログイン画面】ユーザーIDかパスワードが間違っています";
    public static final String ACTIONLOG_LOGIN_2 = "【ログイン画面】ユーザーIDがロックされています";
    public static final String ACTIONLOG_LOGIN_3 = "【ログイン画面】ユーザー登録";
    public static final String ACTIONLOG_PASSWORD_1 = "【パスワード変更画面】初期化パスワードですが、パスワード変更が必要";
    public static final String ACTIONLOG_PASSWORD_2 = "【ログイン画面】パスワードを連続５回以上間違えた、アカウントがロックアウト";
    public static final String ACTIONLOG_PASSWORD_3 = "【パスワード変更画面】最終パスワード変更日時により、90日以上ですが、パスワード変更が必要";
    public static final String ACTIONLOG_PASSWORD_4 = "【パスワードリセット画面】ログイン画面でパスワード忘れボタン押下";
    public static final String ACTIONLOG_PASSWORD_5 = "【パスワードリセット画面】パスワードリセット";
    public static final String ACTIONLOG_PASSWORD_6 = "【パスワード変更画面】パスワード変更";
    public static final String ACTIONLOG_PASSWORD_7 = "【パスワード変更画面】本日は既にパスワードを変更済のため、再度変更できない。";
    public static final String ACTIONLOG_PASSWORD_8 = "【パスワード変更画面】入力されたパスワードは直近利用されています（過去５世代）";
    public static final String ACTIONLOG_AUTHORITY_1 = "【権限一覧画面】権限一覧表示";
    public static final String ACTIONLOG_AUTHORITY_2 = "【権限一覧画面】権限一括削除";
    public static final String ACTIONLOG_AUTHORITY_3 = "【権限新規登録画面】権限一覧画面で新規登録ボタン押下";
    public static final String ACTIONLOG_AUTHORITY_4 = "【権限新規登録画面】権限新規登録";
    public static final String ACTIONLOG_AUTHORITY_5 = "【権限詳細／編集画面】権限一覧画面で詳細／編集ボタン押下";
    public static final String ACTIONLOG_AUTHORITY_6 = "【権限詳細／編集画面】権限情報更新";
    public static final String ACTIONLOG_STOREATM_1 = "【店舗ATM一覧画面】店舗ATM一覧表示";
    public static final String ACTIONLOG_STOREATM_2 = "【店舗ATM一覧画面】店舗ATM一括削除";
    public static final String ACTIONLOG_STOREATM_3 = "【店舗ATM新規登録画面】店舗ATM一覧画面で新規登録ボタン押下";
    public static final String ACTIONLOG_STOREATM_4 = "【店舗ATM新規登録画面】店舗ATM新規登録";
    public static final String ACTIONLOG_STOREATM_5 = "【店舗ATM詳細／編集画面】店舗ATM一覧画面で詳細／編集ボタン押下";
    public static final String ACTIONLOG_STOREATM_6 = "【店舗ATM詳細／編集画面】店舗ATM情報更新";
    public static final String ACTIONLOG_ACCOUNT_1 = "【申込一覧画面】申込一覧表示";
    public static final String ACTIONLOG_ACCOUNT_2 = "【申込一覧画面】一覧出力";
    public static final String ACTIONLOG_ACCOUNT_3 = "【申込一覧画面】顧客CSV出力";
    public static final String ACTIONLOG_ACCOUNT_4 = "【申込一覧画面】完了消込";
    public static final String ACTIONLOG_ACCOUNT_5 = "【申込詳細／編集画面】申込一覧画面で詳細／編集ボタン押下";
    public static final String ACTIONLOG_ACCOUNT_6 = "【申込詳細／編集画面】ステータス変更";
    public static final String ACTIONLOG_ACCOUNT_7 = "【申込一覧画面】帳票出力　本人確認資料印刷";
    public static final String ACTIONLOG_PUSHPRECORD_1 = "【PUSH通知承認】PUSHメッセージ一覧表示";
    public static final String ACTIONLOG_PUSHPRECORD_2 = "【PUSH通知承認】承認";
    public static final String ACTIONLOG_PUSHPRECORD_3 = "【PUSH通知承認】一括承認";
    public static final String ACTIONLOG_PUSHPRECORD_4 = "【PUSH通知承認_詳細画面】PUSH通知承認一覧画面で詳細ボタン押下";
    public static final String ACTIONLOG_PUSHPRECORD_5 = "【PUSH通知承認】承認取り下げ";
    public static final String ACTIONLOG_LOGOUT_1 = "【共通】ログアウト";
    public static final String ACTIONLOG_LOGOUT_2 = "【共通】セッション切れ、強制ログアウト";
    public static final String ACTIONLOG_USERLIST_1 = "【ユーザー一覧画面】ユーザー一覧表示";
    public static final String ACTIONLOG_USERLIST_2 = "【ユーザー一覧画面】ユーザー一括削除";
    public static final String ACTIONLOG_USERLIST_3 = "【ユーザー一覧画面】ユーザー一覧CSV出力";
    public static final String ACTIONLOG_USERLIST_4 = "【ユーザー一覧画面】ユーザー操作／行動ログCSV出力";
    public static final String ACTIONLOG_USERLIST_5 = "【ユーザー新規登録画面】ユーザー一覧画面で新規登録ボタン押下";
    public static final String ACTIONLOG_USERLIST_6 = "【ユーザー新規登録画面】ユーザー新規登録";
    public static final String ACTIONLOG_USERLIST_7 = "【ユーザー詳細／編集画面】ユーザー一覧画面で詳細／編集ボタン押下";
    public static final String ACTIONLOG_USERLIST_8 = "【ユーザー詳細／編集画面】ユーザー情報更新";
    public static final String ACTIONLOG_USERLIST_9 = "【ユーザー詳細／編集画面】ユーザーのパスワードリセット";
    public static final String ACTIONLOG_HTML_PDF_FILE_1 = "【更新用HTMLとPDFファイル管理画面】HTMLとPDFファイル管理画面表示";
    public static final String ACTIONLOG_HTML_PDF_FILE_2 = "【更新用HTMLとPDFファイル管理画面】更新履歴検索";
    public static final String ACTIONLOG_HTML_PDF_FILE_3 = "【更新用HTMLとPDFファイル管理画面】ファイルアップロード";
    public static final String ACTIONLOG_HTML_PDF_FILE_4 = "【更新用HTMLとPDFファイル管理画面】ファイルダウンロード";
    public static final String ACTIONLOG_HTML_PDF_FILE_5 = "【更新用HTMLとPDFファイル管理画面】一括削除";
    public static final String ACTIONLOG_STORE_ATM_DATA_1 = "【更新用店舗ATMマスタデータ管理画面】店舗ATMマスタデータファイル管理画面表示";
    public static final String ACTIONLOG_STORE_ATM_DATA_2 = "【更新用店舗ATMマスタデータ管理画面】更新履歴検索";
    public static final String ACTIONLOG_STORE_ATM_DATA_3 = "【更新用店舗ATMマスタデータ管理画面】ファイルアップロード";
    public static final String ACTIONLOG_STORE_ATM_DATA_4 = "【更新用店舗ATMマスタデータ管理画面】ファイルダウンロード";
    public static final String ACTIONLOG_STORE_ATM_DATA_5 = "【更新用店舗ATMマスタデータ管理画面】一括削除";
    public static final String ACTIONLOG_IMAGE_FILE_1 = "【画面表示用画像＆URL管理画面】画像＆URL管理画面表示";
    public static final String ACTIONLOG_IMAGE_FILE_2 = "【画面表示用画像＆URL管理画面】検索処理";
    public static final String ACTIONLOG_IMAGE_FILE_3 = "【画面表示用画像＆URL管理画面】画像ファイル一括削除";
    public static final String ACTIONLOG_IMAGE_FILE_4 = "【画像＆URL新規登録画面】画像＆URL管理画面画面で新規登録ボタン押下";
    public static final String ACTIONLOG_IMAGE_FILE_5 = "【画像＆URL新規登録画面】画像ファイル新規登録";
    public static final String ACTIONLOG_IMAGE_FILE_6 = "【画像＆URL詳細／編集画面】画像＆URL管理画面で詳細／編集ボタン押下";
    public static final String ACTIONLOG_IMAGE_FILE_7 = "【画像＆URL詳細／編集画面】画像ファイル情報更新";
    public static final String ACTIONLOG_CONTENTS_APPLICATION_1 = "【アプリケーション一覧画面】アプリケーション一覧表示";
    public static final String ACTIONLOG_CONTENTS_APPLICATION_2 = "【アプリケーション一覧画面】アプリケーション一括削除";
    public static final String ACTIONLOG_CONTENTS_APPLICATION_3 = "【アプリケーション新規登録画面】アプリケーション一覧画面で新規登録ボタン押下";
    public static final String ACTIONLOG_CONTENTS_APPLICATION_4 = "【アプリケーション新規登録画面】アプリケーション新規登録";
    public static final String ACTIONLOG_CONTENTS_APPLICATION_5 = "【アプリケーション詳細／編集画面】アプリケーション一覧画面で詳細／編集ボタン押下";
    public static final String ACTIONLOG_CONTENTS_APPLICATION_6 = "【アプリケーション詳細／編集画面】アプリケーション情報更新";
    public static final String ACTIONLOG_CONTENTS_TYPE_1 = "【コンテンツ種別一覧画面】コンテンツ種別一覧表示";
    public static final String ACTIONLOG_CONTENTS_TYPE_2 = "【コンテンツ種別一覧画面】コンテンツ種別一括削除";
    public static final String ACTIONLOG_CONTENTS_TYPE_3 = "【コンテンツ種別新規登録画面】コンテンツ種別一覧画面で新規登録ボタン押下";
    public static final String ACTIONLOG_CONTENTS_TYPE_4 = "【コンテンツ種別新規登録画面】コンテンツ種別新規登録";
    public static final String ACTIONLOG_CONTENTS_TYPE_5 = "【コンテンツ種別詳細／編集画面】コンテンツ種別一覧画面で詳細／編集ボタン押下";
    public static final String ACTIONLOG_CONTENTS_TYPE_6 = "【コンテンツ種別詳細／編集画面】コンテンツ種別情報更新";
    public static final String ACTIONLOG_CONTENTS_CONTENTS_1 = "【コンテンツ一覧画面】コンテンツ一覧表示";
    public static final String ACTIONLOG_CONTENTS_CONTENTS_2 = "【コンテンツ一覧画面】コンテンツ一括削除";
    public static final String ACTIONLOG_CONTENTS_CONTENTS_3 = "【コンテンツ新規登録画面】コンテンツ一覧画面で新規登録ボタン押下";
    public static final String ACTIONLOG_CONTENTS_CONTENTS_4 = "【コンテンツ新規登録画面】コンテンツ新規登録";
    public static final String ACTIONLOG_CONTENTS_CONTENTS_5 = "【コンテンツ詳細／編集画面】コンテンツ一覧画面で詳細／編集ボタン押下";
    public static final String ACTIONLOG_CONTENTS_CONTENTS_6 = "【コンテンツ詳細／編集画面】コンテンツ情報更新";
    public static final String ACTIONLOG_PUSH_MESSAGE_1 = "【任意メッセージPUSH配信一覧】任意メッセージPUSH配信一覧表示";
    public static final String ACTIONLOG_PUSH_MESSAGE_2 = "【任意メッセージPUSH配信一覧】任意メッセージPUSH新規配信";
    public static final String ACTIONLOG_PUSH_MESSAGE_3 = "【任意メッセージPUSH配信一覧】任意メッセージPUSH配信一覧で詳細ボタン押下";
    public static final String ACTIONLOG_PUSH_MESSAGE_4 = "【任意メッセージPUSH配信一覧】任意配信履歴一括削除";
    public static final String ACTIONLOG_GENERAL_PURPOSE_1 = "【汎用DB＆データダウンロード】汎用DB＆データダウンロード画面でキャンペン検索ボタン押下";
    public static final String ACTIONLOG_GENERAL_PURPOSE_2 = "【汎用DB＆データダウンロード】汎用DB＆データダウンロード画面でダウンロードボタン押下";
    public static final String ACTIONLOG_USEUSER_1 = "【利用ユーザー一覧画面】利用ユーザー一覧表示";
    public static final String ACTIONLOG_USEUSER_2 = "【利用ユーザー一覧画面】利用ユーザーCSV出力";
    public static final String ACTIONLOG_USEUSER_3 = "【利用ユーザー詳細画面】利用ユーザー一覧画面で詳細ボタン押下";
    public static final String ACTIONLOG_DOWNLOAD_1 = "【マイテーマ利用データダウンロード画面】マイテーマ利用データダウンロード一覧表示";
    public static final String ACTIONLOG_DOWNLOAD_2 = "【マイテーマ利用データダウンロード画面】テーマダウンロードCSV出力";
    public static final String ACTIONLOG_DOWNLOAD_3 = "【マイテーマ利用データダウンロード画面】クリップダウンロードCSV出力";
    public static final String ACTIONLOG_ACCOUNTLOAN_1 = "【ローン申込み】ローン申込み一覧表示";
    public static final String ACTIONLOG_ACCOUNTLOAN_2 = "【ローン申込詳細／編集画面】ローン申込一覧画面で詳細／編集ボタン押下";
    public static final String ACTIONLOG_ACCOUNTLOAN_6 = "【ローン申込詳細／編集画面】ステータス変更";
    public static final String ACTIONLOG_ACCOUNTLOAN_4 = "【ローン申込一覧画面】完了消込";
    public static final String GETLIST = "function(doc) { if(doc.docType && doc.docType === \"%s\" && doc.delFlg && doc.delFlg === \"0\"){emit(doc._id, 1);} }";
}
