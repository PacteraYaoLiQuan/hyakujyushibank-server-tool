package com.scsk.response.vo;

public class StoreATMDetailUpdateResVO extends BaseResVO{

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
	private int atmCount;
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
	// バリアフリー_振込
	private int barrierFreeTransfer;
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
	// 窓口営業時間_平日始業
	private String windowOpenStartTime;

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

	public int getAtmCount() {
		return atmCount;
	}

	public void setAtmCount(int atmCount) {
		this.atmCount = atmCount;
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

	public int getBarrierFreeTransfer() {
		return barrierFreeTransfer;
	}

	public void setBarrierFreeTransfer(int barrierFreeTransfer) {
		this.barrierFreeTransfer = barrierFreeTransfer;
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

	public void setAtmFunctionPalmAuthentication(
			int atmFunctionPalmAuthentication) {
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

	public void setAtmFunctionOtherBankingAffairs(
			int atmFunctionOtherBankingAffairs) {
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

	public String getWindowOpenStartTime() {
		return windowOpenStartTime;
	}

	public void setWindowOpenStartTime(String windowOpenStartTime) {
		this.windowOpenStartTime = windowOpenStartTime;
	}
}
