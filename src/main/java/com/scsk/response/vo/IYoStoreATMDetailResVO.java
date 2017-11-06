package com.scsk.response.vo;

public class IYoStoreATMDetailResVO extends BaseResVO {
    // id
    private String _id;
    // 編集/新規mode
    private String modeType;
    // 店舗区分
    private int typeKbn;
    // 店舗コード_母店番号
    private String storeNumber;
    // 店舗コード_出張所枝番
    private String subStoreNumber;
    // 店舗コード_ATM枝番
    private String atmStoreNumber;
    // 店舗名_（漢字）
    private String storeATMName;
    // 店舗名_（カナ）
    private String kanaStoreATMName;
    // 所在地_郵便番号
    private String postCode;
    // 所在地_住所
    private String address;
    // 所在地_ランドマーク
    private String landMark;
    // 電話番号
    private String teleNumber;
    // 窓口営業時間_平日始業
    private String windowStartHour;
    private String windowStartMinute;
    // 窓口営業時間_平日終業
    private String windowEndHour;
    private String windowEndMinute;
    // 窓口営業時間_土曜日始業
    private String windowStartHour_SAT;
    private String windowStartMinute_SAT;
    // 窓口営業時間_土曜日終業
    private String windowEndHour_SAT;
    private String windowEndMinute_SAT;
    // 窓口営業時間_日曜日始業
    private String windowStartHour_SUN;
    private String windowStartMinute_SUN;
    // 窓口営業時間_日曜日終業
    private String windowEndHour_SUN;
    private String windowEndMinute_SUN;
    // ATM営業時間_平日始業
    private String atmStartHour;
    private String atmStartMinute;
    // ATM営業時間_平日終業
    private String atmEndHour;
    private String atmEndMinute;
    // ATM営業時間_土曜日始業
    private String atmStartHour_SAT;
    private String atmStartMinute_SAT;
    // ATM営業時間_土曜日終業
    private String atmEndHour_SAT;
    private String atmEndMinute_SAT;
    // ATM営業時間_日曜日始業
    private String atmStartHour_SUN;
    private String atmStartMinute_SUN;
    // ATM営業時間_日曜日終業
    private String atmEndHour_SUN;
    private String atmEndMinute_SUN;
    // AＴＭ設置台数
    private String atmCount;
    // 駐車場_有無
    private int park;
    // 駐車場_障害者対応
    private int parkServiceForDisabled;
    // 駐車場_備考
    private String parkComment;
    // ひろぎんウツミ屋
    private int hirginUtsumiya;
    // 商品サービス_外貨両替（ﾄﾞﾙ、ﾕｰﾛ）
    private int serviceDollarEuro;
    // 商品サービス_外貨両替（ｱｼﾞｱ通貨）
    private int serviceAsia;
    // 商品サービス_外貨両替（その他）
    private int serviceOther;
    // 商品サービス_外貨買取
    private int serviceForeignExchange;
    // 商品サービス_投資信託
    private int serviceInvestmentTrust;
    // 商品サービス_年金保険
    private int servicePensionInsurance;
    // 商品サービス_金融商品仲介（みずほ証券）
    private int serviceMizuho;
    // 商品サービス_金融商品仲介（ひろぎんウツミ屋）
    private int serviceHirginUtsumiya;
    // 商品サービス_全自動貸金庫
    private int serviceAutoSafeDepositBox;
    // 商品サービス_一般貸金庫
    private int serviceSafeDepositBox;
    // 商品サービス_ｾｰﾌﾃｨｹｰｽ
    private int serviceSafeBox;
    // 店舗設備_IB専用PC
    private int internationalTradePC;
    // 店舗設備_ｷｯｽﾞｽﾍﾟｰｽ
    private int childrenSpace;
    // バリアフリー_視覚障害対応ATM
    private int barrierFreeVisualImpairment;
    // バリアフリー_点字ﾌﾞﾛｯｸ
    private int barrierFreeBrailleBlock;
    // バリアフリー_音声ｶﾞｲﾄﾞ
    private int barrierFreeVoiceGuide;
    // バリアフリー_AED
    private int barrierFreeAED;
    // ATM機能_振込
    private int atmFunctionTransfer;
    // ATM機能_硬貨入出金
    private int atmFunctionCoinAccess;
    // ATM機能_宝くじｻｰﾋﾞｽ
    private int atmFunctionLotteryService;
    // ATM機能_手のひら認証
    private int atmFunctionPalmAuthentication;
    // ATM機能_IC対応
    private int atmFunctionIC;
    // ATM機能_PASPYチャージ
    private int atmFunctionPASPY;
    // ATM機能_他行幹事
    private int atmFunctionOtherBankingAffairs;
    // 座標_経度
    private String longitude;
    // 座標_緯度
    private String latitude;
    // 備考①
    private String comment1;
    // 備考②
    private String comment2;
    // 備考③
    private String comment3;
    // 備考④
    private String comment4;
    // 備考⑤
    private String comment5;
    // 開始日時
    private String startDateTime;
    // 終了日時
    private String endDateTime;

    private String delFlg;
    private Boolean select;
    private String docType;

    private String poiStatus;

    private String delStoreNumber;

    private String atmOpenStartHour;
    private String atmOpenStartMinute;
    private String atmOpenEndHour;
    private String atmOpenEndMinute;
    private String atmOpenStartHour_SAT;
    private String atmOpenStartMinute_SAT;
    private String atmOpenEndHour_SAT;
    private String atmOpenEndMinute_SAT;
    private String atmOpenStartHour_SUN;
    private String atmOpenStartMinute_SUN;
    private String atmOpenEndHour_SUN;
    private String atmOpenEndMinute_SUN;
    private String atmComment1;
    private String atmComment2;
    private String windowOpenStartHour;
    private String windowOpenStartMinute;
    private String windowOpenEndHour;
    private String windowOpenEndMinute;
    private String windowOpenStartHour_SAT;
    private String windowOpenStartMinute_SAT;
    private String windowOpenEndHour_SAT;
    private String windowOpenEndMinute_SAT;
    private String windowOpenStartHour_SUN;
    private String windowOpenStartMinute_SUN;
    private String windowOpenEndHour_SUN;
    private String windowOpenEndMinute_SUN;
    private String conversionMachineStartHour;
    private String conversionMachineStartMinute;
    private String conversionMachineEndHour;
    private String conversionMachineEndMinute;
    private String conversionMachineStartHour_holiday;
    private String conversionMachineStartMinute_holiday;
    private String conversionMachineEndHour_holiday;
    private String conversionMachineEndMinute_holiday;
    private String accountMachineStartTime;
    private String accountMachineStartTime_SAT;
    private String accountMachineStartTime_SUN;
    private String autoLoanMachineStartHour;
    private String autoLoanMachineStartMinute;
    private String autoLoanMachineEndHour;
    private String autoLoanMachineEndMinute;
    private String autoLoanMachineStartHour_holiday;
    private String autoLoanMachineStartMinute_holiday;
    private String autoLoanMachineEndHour_holiday;
    private String autoLoanMachineEndMinute_holiday;
    private String autoLoanMachineFlag;
    private String loanMachineStartHour;
    private String loanMachineStartMinute;
    private String loanMachineEndHour;
    private String loanMachineEndMinute;
    private String loanMachineStartHour_holiday;
    private String loanMachineStartMinute_holiday;
    private String loanMachineEndHour_holiday;
    private String loanMachineEndMinute_holiday;
    private String loanMachineFlag;
    private String aed;
    private String internationalStore;

    private String parkCount;

    private String toilet;
    private String serviceConversionStore;
    private String wheelChair;
    private String wheelChairStore;
    private String currentStation1;
    private String currentStationDistance1;
    private String currentStationTime1;
    private String currentStation2;
    private String currentStationDistance2;
    private String currentStationTime2;
    private String currentStation3;
    private String currentStationDistance3;
    private String currentStationTime3;
    private String message;
    private String image;
    private String imageUrl;
    private String icon;
    private String finalUpdateTime;
    private String _rev;

    public String getLoanMachineStartHour() {
        return loanMachineStartHour;
    }

    public void setLoanMachineStartHour(String loanMachineStartHour) {
        this.loanMachineStartHour = loanMachineStartHour;
    }

    public String getLoanMachineStartMinute() {
        return loanMachineStartMinute;
    }

    public void setLoanMachineStartMinute(String loanMachineStartMinute) {
        this.loanMachineStartMinute = loanMachineStartMinute;
    }

    public String getLoanMachineEndHour() {
        return loanMachineEndHour;
    }

    public void setLoanMachineEndHour(String loanMachineEndHour) {
        this.loanMachineEndHour = loanMachineEndHour;
    }

    public String getLoanMachineEndMinute() {
        return loanMachineEndMinute;
    }

    public void setLoanMachineEndMinute(String loanMachineEndMinute) {
        this.loanMachineEndMinute = loanMachineEndMinute;
    }

    public String getLoanMachineStartHour_holiday() {
        return loanMachineStartHour_holiday;
    }

    public void setLoanMachineStartHour_holiday(String loanMachineStartHour_holiday) {
        this.loanMachineStartHour_holiday = loanMachineStartHour_holiday;
    }

    public String getLoanMachineStartMinute_holiday() {
        return loanMachineStartMinute_holiday;
    }

    public void setLoanMachineStartMinute_holiday(String loanMachineStartMinute_holiday) {
        this.loanMachineStartMinute_holiday = loanMachineStartMinute_holiday;
    }

    public String getLoanMachineEndHour_holiday() {
        return loanMachineEndHour_holiday;
    }

    public void setLoanMachineEndHour_holiday(String loanMachineEndHour_holiday) {
        this.loanMachineEndHour_holiday = loanMachineEndHour_holiday;
    }

    public String getLoanMachineEndMinute_holiday() {
        return loanMachineEndMinute_holiday;
    }

    public void setLoanMachineEndMinute_holiday(String loanMachineEndMinute_holiday) {
        this.loanMachineEndMinute_holiday = loanMachineEndMinute_holiday;
    }

    public String getAccountMachineStartTime() {
        return accountMachineStartTime;
    }

    public void setAccountMachineStartTime(String accountMachineStartTime) {
        this.accountMachineStartTime = accountMachineStartTime;
    }

    public String getAccountMachineStartTime_SAT() {
        return accountMachineStartTime_SAT;
    }

    public void setAccountMachineStartTime_SAT(String accountMachineStartTime_SAT) {
        this.accountMachineStartTime_SAT = accountMachineStartTime_SAT;
    }

    public String getAccountMachineStartTime_SUN() {
        return accountMachineStartTime_SUN;
    }

    public void setAccountMachineStartTime_SUN(String accountMachineStartTime_SUN) {
        this.accountMachineStartTime_SUN = accountMachineStartTime_SUN;
    }

    public String getAtmOpenStartHour() {
        return atmOpenStartHour;
    }

    public void setAtmOpenStartHour(String atmOpenStartHour) {
        this.atmOpenStartHour = atmOpenStartHour;
    }

    public String getAtmOpenStartMinute() {
        return atmOpenStartMinute;
    }

    public void setAtmOpenStartMinute(String atmOpenStartMinute) {
        this.atmOpenStartMinute = atmOpenStartMinute;
    }

    public String getAtmOpenEndHour() {
        return atmOpenEndHour;
    }

    public void setAtmOpenEndHour(String atmOpenEndHour) {
        this.atmOpenEndHour = atmOpenEndHour;
    }

    public String getAtmOpenEndMinute() {
        return atmOpenEndMinute;
    }

    public void setAtmOpenEndMinute(String atmOpenEndMinute) {
        this.atmOpenEndMinute = atmOpenEndMinute;
    }

    public String getAtmOpenStartHour_SAT() {
        return atmOpenStartHour_SAT;
    }

    public void setAtmOpenStartHour_SAT(String atmOpenStartHour_SAT) {
        this.atmOpenStartHour_SAT = atmOpenStartHour_SAT;
    }

    public String getAtmOpenStartMinute_SAT() {
        return atmOpenStartMinute_SAT;
    }

    public void setAtmOpenStartMinute_SAT(String atmOpenStartMinute_SAT) {
        this.atmOpenStartMinute_SAT = atmOpenStartMinute_SAT;
    }

    public String getAtmOpenEndHour_SAT() {
        return atmOpenEndHour_SAT;
    }

    public void setAtmOpenEndHour_SAT(String atmOpenEndHour_SAT) {
        this.atmOpenEndHour_SAT = atmOpenEndHour_SAT;
    }

    public String getAtmOpenEndMinute_SAT() {
        return atmOpenEndMinute_SAT;
    }

    public void setAtmOpenEndMinute_SAT(String atmOpenEndMinute_SAT) {
        this.atmOpenEndMinute_SAT = atmOpenEndMinute_SAT;
    }

    public String getAtmOpenStartHour_SUN() {
        return atmOpenStartHour_SUN;
    }

    public void setAtmOpenStartHour_SUN(String atmOpenStartHour_SUN) {
        this.atmOpenStartHour_SUN = atmOpenStartHour_SUN;
    }

    public String getAtmOpenStartMinute_SUN() {
        return atmOpenStartMinute_SUN;
    }

    public void setAtmOpenStartMinute_SUN(String atmOpenStartMinute_SUN) {
        this.atmOpenStartMinute_SUN = atmOpenStartMinute_SUN;
    }

    public String getAtmOpenEndHour_SUN() {
        return atmOpenEndHour_SUN;
    }

    public void setAtmOpenEndHour_SUN(String atmOpenEndHour_SUN) {
        this.atmOpenEndHour_SUN = atmOpenEndHour_SUN;
    }

    public String getAtmOpenEndMinute_SUN() {
        return atmOpenEndMinute_SUN;
    }

    public void setAtmOpenEndMinute_SUN(String atmOpenEndMinute_SUN) {
        this.atmOpenEndMinute_SUN = atmOpenEndMinute_SUN;
    }

    public String getWindowOpenStartHour() {
        return windowOpenStartHour;
    }

    public void setWindowOpenStartHour(String windowOpenStartHour) {
        this.windowOpenStartHour = windowOpenStartHour;
    }

    public String getWindowOpenStartMinute() {
        return windowOpenStartMinute;
    }

    public void setWindowOpenStartMinute(String windowOpenStartMinute) {
        this.windowOpenStartMinute = windowOpenStartMinute;
    }

    public String getWindowOpenEndHour() {
        return windowOpenEndHour;
    }

    public void setWindowOpenEndHour(String windowOpenEndHour) {
        this.windowOpenEndHour = windowOpenEndHour;
    }

    public String getWindowOpenEndMinute() {
        return windowOpenEndMinute;
    }

    public void setWindowOpenEndMinute(String windowOpenEndMinute) {
        this.windowOpenEndMinute = windowOpenEndMinute;
    }

    public String getWindowOpenStartHour_SAT() {
        return windowOpenStartHour_SAT;
    }

    public void setWindowOpenStartHour_SAT(String windowOpenStartHour_SAT) {
        this.windowOpenStartHour_SAT = windowOpenStartHour_SAT;
    }

    public String getWindowOpenStartMinute_SAT() {
        return windowOpenStartMinute_SAT;
    }

    public void setWindowOpenStartMinute_SAT(String windowOpenStartMinute_SAT) {
        this.windowOpenStartMinute_SAT = windowOpenStartMinute_SAT;
    }

    public String getWindowOpenEndHour_SAT() {
        return windowOpenEndHour_SAT;
    }

    public void setWindowOpenEndHour_SAT(String windowOpenEndHour_SAT) {
        this.windowOpenEndHour_SAT = windowOpenEndHour_SAT;
    }

    public String getWindowOpenEndMinute_SAT() {
        return windowOpenEndMinute_SAT;
    }

    public void setWindowOpenEndMinute_SAT(String windowOpenEndMinute_SAT) {
        this.windowOpenEndMinute_SAT = windowOpenEndMinute_SAT;
    }

    public String getWindowOpenStartHour_SUN() {
        return windowOpenStartHour_SUN;
    }

    public void setWindowOpenStartHour_SUN(String windowOpenStartHour_SUN) {
        this.windowOpenStartHour_SUN = windowOpenStartHour_SUN;
    }

    public String getWindowOpenStartMinute_SUN() {
        return windowOpenStartMinute_SUN;
    }

    public void setWindowOpenStartMinute_SUN(String windowOpenStartMinute_SUN) {
        this.windowOpenStartMinute_SUN = windowOpenStartMinute_SUN;
    }

    public String getWindowOpenEndHour_SUN() {
        return windowOpenEndHour_SUN;
    }

    public void setWindowOpenEndHour_SUN(String windowOpenEndHour_SUN) {
        this.windowOpenEndHour_SUN = windowOpenEndHour_SUN;
    }

    public String getWindowOpenEndMinute_SUN() {
        return windowOpenEndMinute_SUN;
    }

    public void setWindowOpenEndMinute_SUN(String windowOpenEndMinute_SUN) {
        this.windowOpenEndMinute_SUN = windowOpenEndMinute_SUN;
    }

    public String getConversionMachineStartHour() {
        return conversionMachineStartHour;
    }

    public void setConversionMachineStartHour(String conversionMachineStartHour) {
        this.conversionMachineStartHour = conversionMachineStartHour;
    }

    public String getConversionMachineStartMinute() {
        return conversionMachineStartMinute;
    }

    public void setConversionMachineStartMinute(String conversionMachineStartMinute) {
        this.conversionMachineStartMinute = conversionMachineStartMinute;
    }

    public String getConversionMachineEndHour() {
        return conversionMachineEndHour;
    }

    public void setConversionMachineEndHour(String conversionMachineEndHour) {
        this.conversionMachineEndHour = conversionMachineEndHour;
    }

    public String getConversionMachineEndMinute() {
        return conversionMachineEndMinute;
    }

    public void setConversionMachineEndMinute(String conversionMachineEndMinute) {
        this.conversionMachineEndMinute = conversionMachineEndMinute;
    }

    public String getConversionMachineStartHour_holiday() {
        return conversionMachineStartHour_holiday;
    }

    public void setConversionMachineStartHour_holiday(String conversionMachineStartHour_holiday) {
        this.conversionMachineStartHour_holiday = conversionMachineStartHour_holiday;
    }

    public String getConversionMachineStartMinute_holiday() {
        return conversionMachineStartMinute_holiday;
    }

    public void setConversionMachineStartMinute_holiday(String conversionMachineStartMinute_holiday) {
        this.conversionMachineStartMinute_holiday = conversionMachineStartMinute_holiday;
    }

    public String getConversionMachineEndHour_holiday() {
        return conversionMachineEndHour_holiday;
    }

    public void setConversionMachineEndHour_holiday(String conversionMachineEndHour_holiday) {
        this.conversionMachineEndHour_holiday = conversionMachineEndHour_holiday;
    }

    public String getConversionMachineEndMinute_holiday() {
        return conversionMachineEndMinute_holiday;
    }

    public void setConversionMachineEndMinute_holiday(String conversionMachineEndMinute_holiday) {
        this.conversionMachineEndMinute_holiday = conversionMachineEndMinute_holiday;
    }

    public String getAutoLoanMachineStartHour() {
        return autoLoanMachineStartHour;
    }

    public void setAutoLoanMachineStartHour(String autoLoanMachineStartHour) {
        this.autoLoanMachineStartHour = autoLoanMachineStartHour;
    }

    public String getAutoLoanMachineStartMinute() {
        return autoLoanMachineStartMinute;
    }

    public void setAutoLoanMachineStartMinute(String autoLoanMachineStartMinute) {
        this.autoLoanMachineStartMinute = autoLoanMachineStartMinute;
    }

    public String getAutoLoanMachineEndHour() {
        return autoLoanMachineEndHour;
    }

    public void setAutoLoanMachineEndHour(String autoLoanMachineEndHour) {
        this.autoLoanMachineEndHour = autoLoanMachineEndHour;
    }

    public String getAutoLoanMachineEndMinute() {
        return autoLoanMachineEndMinute;
    }

    public void setAutoLoanMachineEndMinute(String autoLoanMachineEndMinute) {
        this.autoLoanMachineEndMinute = autoLoanMachineEndMinute;
    }

    public String getAutoLoanMachineStartHour_holiday() {
        return autoLoanMachineStartHour_holiday;
    }

    public void setAutoLoanMachineStartHour_holiday(String autoLoanMachineStartHour_holiday) {
        this.autoLoanMachineStartHour_holiday = autoLoanMachineStartHour_holiday;
    }

    public String getAutoLoanMachineStartMinute_holiday() {
        return autoLoanMachineStartMinute_holiday;
    }

    public void setAutoLoanMachineStartMinute_holiday(String autoLoanMachineStartMinute_holiday) {
        this.autoLoanMachineStartMinute_holiday = autoLoanMachineStartMinute_holiday;
    }

    public String getAutoLoanMachineEndHour_holiday() {
        return autoLoanMachineEndHour_holiday;
    }

    public void setAutoLoanMachineEndHour_holiday(String autoLoanMachineEndHour_holiday) {
        this.autoLoanMachineEndHour_holiday = autoLoanMachineEndHour_holiday;
    }

    public String getAutoLoanMachineEndMinute_holiday() {
        return autoLoanMachineEndMinute_holiday;
    }

    public void setAutoLoanMachineEndMinute_holiday(String autoLoanMachineEndMinute_holiday) {
        this.autoLoanMachineEndMinute_holiday = autoLoanMachineEndMinute_holiday;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPoiStatus() {
        return poiStatus;
    }

    public void setPoiStatus(String poiStatus) {
        this.poiStatus = poiStatus;
    }

    public String getDelStoreNumber() {
        return delStoreNumber;
    }

    public void setDelStoreNumber(String delStoreNumber) {
        this.delStoreNumber = delStoreNumber;
    }

    public String getAtmComment1() {
        return atmComment1;
    }

    public void setAtmComment1(String atmComment1) {
        this.atmComment1 = atmComment1;
    }

    public String getAtmComment2() {
        return atmComment2;
    }

    public void setAtmComment2(String atmComment2) {
        this.atmComment2 = atmComment2;
    }

    public String getAutoLoanMachineFlag() {
        return autoLoanMachineFlag;
    }

    public void setAutoLoanMachineFlag(String autoLoanMachineFlag) {
        this.autoLoanMachineFlag = autoLoanMachineFlag;
    }

    public String getLoanMachineFlag() {
        return loanMachineFlag;
    }

    public void setLoanMachineFlag(String loanMachineFlag) {
        this.loanMachineFlag = loanMachineFlag;
    }

    public String getAed() {
        return aed;
    }

    public void setAed(String aed) {
        this.aed = aed;
    }

    public String getInternationalStore() {
        return internationalStore;
    }

    public void setInternationalStore(String internationalStore) {
        this.internationalStore = internationalStore;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getServiceConversionStore() {
        return serviceConversionStore;
    }

    public void setServiceConversionStore(String serviceConversionStore) {
        this.serviceConversionStore = serviceConversionStore;
    }

    public String getWheelChair() {
        return wheelChair;
    }

    public void setWheelChair(String wheelChair) {
        this.wheelChair = wheelChair;
    }

    public String getWheelChairStore() {
        return wheelChairStore;
    }

    public void setWheelChairStore(String wheelChairStore) {
        this.wheelChairStore = wheelChairStore;
    }

    public String getCurrentStation1() {
        return currentStation1;
    }

    public void setCurrentStation1(String currentStation1) {
        this.currentStation1 = currentStation1;
    }

    public String getCurrentStationDistance1() {
        return currentStationDistance1;
    }

    public void setCurrentStationDistance1(String currentStationDistance1) {
        this.currentStationDistance1 = currentStationDistance1;
    }

    public String getCurrentStationTime1() {
        return currentStationTime1;
    }

    public void setCurrentStationTime1(String currentStationTime1) {
        this.currentStationTime1 = currentStationTime1;
    }

    public String getCurrentStation2() {
        return currentStation2;
    }

    public void setCurrentStation2(String currentStation2) {
        this.currentStation2 = currentStation2;
    }

    public String getCurrentStationDistance2() {
        return currentStationDistance2;
    }

    public void setCurrentStationDistance2(String currentStationDistance2) {
        this.currentStationDistance2 = currentStationDistance2;
    }

    public String getCurrentStationTime2() {
        return currentStationTime2;
    }

    public void setCurrentStationTime2(String currentStationTime2) {
        this.currentStationTime2 = currentStationTime2;
    }

    public String getCurrentStation3() {
        return currentStation3;
    }

    public void setCurrentStation3(String currentStation3) {
        this.currentStation3 = currentStation3;
    }

    public String getCurrentStationDistance3() {
        return currentStationDistance3;
    }

    public void setCurrentStationDistance3(String currentStationDistance3) {
        this.currentStationDistance3 = currentStationDistance3;
    }

    public String getCurrentStationTime3() {
        return currentStationTime3;
    }

    public void setCurrentStationTime3(String currentStationTime3) {
        this.currentStationTime3 = currentStationTime3;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFinalUpdateTime() {
        return finalUpdateTime;
    }

    public void setFinalUpdateTime(String finalUpdateTime) {
        this.finalUpdateTime = finalUpdateTime;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public int getTypeKbn() {
        return typeKbn;
    }

    public void setTypeKbn(int typeKbn) {
        this.typeKbn = typeKbn;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getSubStoreNumber() {
        return subStoreNumber;
    }

    public void setSubStoreNumber(String subStoreNumber) {
        this.subStoreNumber = subStoreNumber;
    }

    public String getAtmStoreNumber() {
        return atmStoreNumber;
    }

    public void setAtmStoreNumber(String atmStoreNumber) {
        this.atmStoreNumber = atmStoreNumber;
    }

    public String getStoreATMName() {
        return storeATMName;
    }

    public void setStoreATMName(String storeATMName) {
        this.storeATMName = storeATMName;
    }

    public String getKanaStoreATMName() {
        return kanaStoreATMName;
    }

    public void setKanaStoreATMName(String kanaStoreATMName) {
        this.kanaStoreATMName = kanaStoreATMName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }

    public String getWindowStartHour() {
        return windowStartHour;
    }

    public void setWindowStartHour(String windowStartHour) {
        this.windowStartHour = windowStartHour;
    }

    public String getWindowStartMinute() {
        return windowStartMinute;
    }

    public void setWindowStartMinute(String windowStartMinute) {
        this.windowStartMinute = windowStartMinute;
    }

    public String getWindowEndHour() {
        return windowEndHour;
    }

    public void setWindowEndHour(String windowEndHour) {
        this.windowEndHour = windowEndHour;
    }

    public String getWindowEndMinute() {
        return windowEndMinute;
    }

    public void setWindowEndMinute(String windowEndMinute) {
        this.windowEndMinute = windowEndMinute;
    }

    public String getWindowStartHour_SAT() {
        return windowStartHour_SAT;
    }

    public void setWindowStartHour_SAT(String windowStartHour_SAT) {
        this.windowStartHour_SAT = windowStartHour_SAT;
    }

    public String getWindowStartMinute_SAT() {
        return windowStartMinute_SAT;
    }

    public void setWindowStartMinute_SAT(String windowStartMinute_SAT) {
        this.windowStartMinute_SAT = windowStartMinute_SAT;
    }

    public String getWindowEndHour_SAT() {
        return windowEndHour_SAT;
    }

    public void setWindowEndHour_SAT(String windowEndHour_SAT) {
        this.windowEndHour_SAT = windowEndHour_SAT;
    }

    public String getWindowEndMinute_SAT() {
        return windowEndMinute_SAT;
    }

    public void setWindowEndMinute_SAT(String windowEndMinute_SAT) {
        this.windowEndMinute_SAT = windowEndMinute_SAT;
    }

    public String getWindowStartHour_SUN() {
        return windowStartHour_SUN;
    }

    public void setWindowStartHour_SUN(String windowStartHour_SUN) {
        this.windowStartHour_SUN = windowStartHour_SUN;
    }

    public String getWindowStartMinute_SUN() {
        return windowStartMinute_SUN;
    }

    public void setWindowStartMinute_SUN(String windowStartMinute_SUN) {
        this.windowStartMinute_SUN = windowStartMinute_SUN;
    }

    public String getWindowEndHour_SUN() {
        return windowEndHour_SUN;
    }

    public void setWindowEndHour_SUN(String windowEndHour_SUN) {
        this.windowEndHour_SUN = windowEndHour_SUN;
    }

    public String getWindowEndMinute_SUN() {
        return windowEndMinute_SUN;
    }

    public void setWindowEndMinute_SUN(String windowEndMinute_SUN) {
        this.windowEndMinute_SUN = windowEndMinute_SUN;
    }

    public String getAtmStartHour() {
        return atmStartHour;
    }

    public void setAtmStartHour(String atmStartHour) {
        this.atmStartHour = atmStartHour;
    }

    public String getAtmStartMinute() {
        return atmStartMinute;
    }

    public void setAtmStartMinute(String atmStartMinute) {
        this.atmStartMinute = atmStartMinute;
    }

    public String getAtmEndHour() {
        return atmEndHour;
    }

    public void setAtmEndHour(String atmEndHour) {
        this.atmEndHour = atmEndHour;
    }

    public String getAtmEndMinute() {
        return atmEndMinute;
    }

    public void setAtmEndMinute(String atmEndMinute) {
        this.atmEndMinute = atmEndMinute;
    }

    public String getAtmStartHour_SAT() {
        return atmStartHour_SAT;
    }

    public void setAtmStartHour_SAT(String atmStartHour_SAT) {
        this.atmStartHour_SAT = atmStartHour_SAT;
    }

    public String getAtmStartMinute_SAT() {
        return atmStartMinute_SAT;
    }

    public void setAtmStartMinute_SAT(String atmStartMinute_SAT) {
        this.atmStartMinute_SAT = atmStartMinute_SAT;
    }

    public String getAtmEndHour_SAT() {
        return atmEndHour_SAT;
    }

    public void setAtmEndHour_SAT(String atmEndHour_SAT) {
        this.atmEndHour_SAT = atmEndHour_SAT;
    }

    public String getAtmEndMinute_SAT() {
        return atmEndMinute_SAT;
    }

    public void setAtmEndMinute_SAT(String atmEndMinute_SAT) {
        this.atmEndMinute_SAT = atmEndMinute_SAT;
    }

    public String getAtmStartHour_SUN() {
        return atmStartHour_SUN;
    }

    public void setAtmStartHour_SUN(String atmStartHour_SUN) {
        this.atmStartHour_SUN = atmStartHour_SUN;
    }

    public String getAtmStartMinute_SUN() {
        return atmStartMinute_SUN;
    }

    public void setAtmStartMinute_SUN(String atmStartMinute_SUN) {
        this.atmStartMinute_SUN = atmStartMinute_SUN;
    }

    public String getAtmEndHour_SUN() {
        return atmEndHour_SUN;
    }

    public void setAtmEndHour_SUN(String atmEndHour_SUN) {
        this.atmEndHour_SUN = atmEndHour_SUN;
    }

    public String getAtmEndMinute_SUN() {
        return atmEndMinute_SUN;
    }

    public void setAtmEndMinute_SUN(String atmEndMinute_SUN) {
        this.atmEndMinute_SUN = atmEndMinute_SUN;
    }

    public String getAtmCount() {
        return atmCount;
    }

    public void setAtmCount(String atmCount) {
        this.atmCount = atmCount;
    }

    public String getParkCount() {
        return parkCount;
    }

    public void setParkCount(String parkCount) {
        this.parkCount = parkCount;
    }

    public int getPark() {
        return park;
    }

    public void setPark(int park) {
        this.park = park;
    }

    public int getParkServiceForDisabled() {
        return parkServiceForDisabled;
    }

    public void setParkServiceForDisabled(int parkServiceForDisabled) {
        this.parkServiceForDisabled = parkServiceForDisabled;
    }

    public String getParkComment() {
        return parkComment;
    }

    public void setParkComment(String parkComment) {
        this.parkComment = parkComment;
    }

    public int getHirginUtsumiya() {
        return hirginUtsumiya;
    }

    public void setHirginUtsumiya(int hirginUtsumiya) {
        this.hirginUtsumiya = hirginUtsumiya;
    }

    public int getServiceDollarEuro() {
        return serviceDollarEuro;
    }

    public void setServiceDollarEuro(int serviceDollarEuro) {
        this.serviceDollarEuro = serviceDollarEuro;
    }

    public int getServiceAsia() {
        return serviceAsia;
    }

    public void setServiceAsia(int serviceAsia) {
        this.serviceAsia = serviceAsia;
    }

    public int getServiceOther() {
        return serviceOther;
    }

    public void setServiceOther(int serviceOther) {
        this.serviceOther = serviceOther;
    }

    public int getServiceForeignExchange() {
        return serviceForeignExchange;
    }

    public void setServiceForeignExchange(int serviceForeignExchange) {
        this.serviceForeignExchange = serviceForeignExchange;
    }

    public int getServiceInvestmentTrust() {
        return serviceInvestmentTrust;
    }

    public void setServiceInvestmentTrust(int serviceInvestmentTrust) {
        this.serviceInvestmentTrust = serviceInvestmentTrust;
    }

    public int getServicePensionInsurance() {
        return servicePensionInsurance;
    }

    public void setServicePensionInsurance(int servicePensionInsurance) {
        this.servicePensionInsurance = servicePensionInsurance;
    }

    public int getServiceMizuho() {
        return serviceMizuho;
    }

    public void setServiceMizuho(int serviceMizuho) {
        this.serviceMizuho = serviceMizuho;
    }

    public int getServiceHirginUtsumiya() {
        return serviceHirginUtsumiya;
    }

    public void setServiceHirginUtsumiya(int serviceHirginUtsumiya) {
        this.serviceHirginUtsumiya = serviceHirginUtsumiya;
    }

    public int getServiceAutoSafeDepositBox() {
        return serviceAutoSafeDepositBox;
    }

    public void setServiceAutoSafeDepositBox(int serviceAutoSafeDepositBox) {
        this.serviceAutoSafeDepositBox = serviceAutoSafeDepositBox;
    }

    public int getServiceSafeDepositBox() {
        return serviceSafeDepositBox;
    }

    public void setServiceSafeDepositBox(int serviceSafeDepositBox) {
        this.serviceSafeDepositBox = serviceSafeDepositBox;
    }

    public int getServiceSafeBox() {
        return serviceSafeBox;
    }

    public void setServiceSafeBox(int serviceSafeBox) {
        this.serviceSafeBox = serviceSafeBox;
    }

    public int getInternationalTradePC() {
        return internationalTradePC;
    }

    public void setInternationalTradePC(int internationalTradePC) {
        this.internationalTradePC = internationalTradePC;
    }

    public int getChildrenSpace() {
        return childrenSpace;
    }

    public void setChildrenSpace(int childrenSpace) {
        this.childrenSpace = childrenSpace;
    }

    public int getBarrierFreeVisualImpairment() {
        return barrierFreeVisualImpairment;
    }

    public void setBarrierFreeVisualImpairment(int barrierFreeVisualImpairment) {
        this.barrierFreeVisualImpairment = barrierFreeVisualImpairment;
    }

    public int getBarrierFreeBrailleBlock() {
        return barrierFreeBrailleBlock;
    }

    public void setBarrierFreeBrailleBlock(int barrierFreeBrailleBlock) {
        this.barrierFreeBrailleBlock = barrierFreeBrailleBlock;
    }

    public int getBarrierFreeVoiceGuide() {
        return barrierFreeVoiceGuide;
    }

    public void setBarrierFreeVoiceGuide(int barrierFreeVoiceGuide) {
        this.barrierFreeVoiceGuide = barrierFreeVoiceGuide;
    }

    public int getBarrierFreeAED() {
        return barrierFreeAED;
    }

    public void setBarrierFreeAED(int barrierFreeAED) {
        this.barrierFreeAED = barrierFreeAED;
    }

    public int getAtmFunctionTransfer() {
        return atmFunctionTransfer;
    }

    public void setAtmFunctionTransfer(int atmFunctionTransfer) {
        this.atmFunctionTransfer = atmFunctionTransfer;
    }

    public int getAtmFunctionCoinAccess() {
        return atmFunctionCoinAccess;
    }

    public void setAtmFunctionCoinAccess(int atmFunctionCoinAccess) {
        this.atmFunctionCoinAccess = atmFunctionCoinAccess;
    }

    public int getAtmFunctionLotteryService() {
        return atmFunctionLotteryService;
    }

    public void setAtmFunctionLotteryService(int atmFunctionLotteryService) {
        this.atmFunctionLotteryService = atmFunctionLotteryService;
    }

    public int getAtmFunctionPalmAuthentication() {
        return atmFunctionPalmAuthentication;
    }

    public void setAtmFunctionPalmAuthentication(int atmFunctionPalmAuthentication) {
        this.atmFunctionPalmAuthentication = atmFunctionPalmAuthentication;
    }

    public int getAtmFunctionIC() {
        return atmFunctionIC;
    }

    public void setAtmFunctionIC(int atmFunctionIC) {
        this.atmFunctionIC = atmFunctionIC;
    }

    public int getAtmFunctionPASPY() {
        return atmFunctionPASPY;
    }

    public void setAtmFunctionPASPY(int atmFunctionPASPY) {
        this.atmFunctionPASPY = atmFunctionPASPY;
    }

    public int getAtmFunctionOtherBankingAffairs() {
        return atmFunctionOtherBankingAffairs;
    }

    public void setAtmFunctionOtherBankingAffairs(int atmFunctionOtherBankingAffairs) {
        this.atmFunctionOtherBankingAffairs = atmFunctionOtherBankingAffairs;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    public String getComment3() {
        return comment3;
    }

    public void setComment3(String comment3) {
        this.comment3 = comment3;
    }

    public String getComment4() {
        return comment4;
    }

    public void setComment4(String comment4) {
        this.comment4 = comment4;
    }

    public String getComment5() {
        return comment5;
    }

    public void setComment5(String comment5) {
        this.comment5 = comment5;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

}
