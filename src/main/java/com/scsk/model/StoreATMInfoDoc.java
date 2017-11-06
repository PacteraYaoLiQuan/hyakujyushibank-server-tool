package com.scsk.model;

import com.scsk.constants.Constants;
import com.scsk.model.geo.GeometryDoc;

/**
 * 店舗情報DOC
 */
public class StoreATMInfoDoc extends UtilDoc {
    
    private String type=Constants.FEATURE;
    
    private GeometryDoc geometry;
	// ドキュメントタイプ
	private String docType = Constants.STOREATM_DOCTYPE;
	// 店舗区分
	private int typeKbn = 0;
	// 店舗コード_母店番号
	private String storeNumber = "";
	// 店舗コード_出張所枝番
	private String subStoreNumber = "";
	// 店舗コード_ATM枝番
	private String atmStoreNumber = "";
	// 店舗名_（漢字）
	private String storeATMName = "";
	// 店舗名_（カナ）
	private String kanaStoreATMName = "";
	// 所在地_郵便番号
	private String postCode = "";
	// 所在地_住所
	private String address = "";
	// 所在地_ランドマーク
	private String landMark = "";
	// 電話番号
	private String teleNumber = "";
	// 窓口営業時間_平日始業
	private String windowOpenStartTime = "";
	// 窓口営業時間_平日終業
	private String windowOpenEndTime = "";
	// 窓口営業時間_土曜日始業
	private String windowOpenStartTime_SAT = "";
	// 窓口営業時間_土曜日終業
	private String windowOpenEndTime_SAT = "";
	// 窓口営業時間_日曜日始業
	private String windowOpenStartTime_SUN = "";
	// 窓口営業時間_日曜日終業
	private String windowOpenEndTime_SUN = "";
	// ATM営業時間_平日始業
	private String atmOpenStartTime = "";
	// ATM営業時間_平日終業
	private String atmOpenEndTime = "";
	// ATM営業時間_土曜日始業
	private String atmOpenStartTime_SAT = "";
	// ATM営業時間_土曜日終業
	private String atmOpenEndTime_SAT = "";
	// ATM営業時間_日曜日始業
	private String atmOpenStartTime_SUN = "";
	// ATM営業時間_日曜日終業
	private String atmOpenEndTime_SUN = "";
	// ATM設置台数
	private String atmCount = "";
	// 駐車場_有無
	private int park = 0;
	// 駐車場_障害者対応
	private int parkServiceForDisabled = 0;
	// 駐車場_備考
	private String parkComment = "";
	// ひろぎんウツミ屋
	private int hirginUtsumiya = 0;
	// 商品サービス_外貨両替（ﾄﾞﾙ、ﾕｰﾛ）
	private int serviceDollarEuro = 0;
	// 商品サービス_外貨両替（ｱｼﾞｱ通貨）
	private int serviceAsia = 0;
	// 商品サービス_外貨両替（その他）
	private int serviceOther = 0;
	// 商品サービス_外貨買取
	private int serviceForeignExchange = 0;
	// 商品サービス_投資信託
	private int serviceInvestmentTrust = 0;
	// 商品サービス_年金保険
	private int servicePensionInsurance = 0;
	// 商品サービス_金融商品仲介（みずほ証券）
	private int serviceMizuho = 0;
	// 商品サービス_金融商品仲介（ひろぎんウツミ屋）
	private int serviceHirginUtsumiya = 0;
	// 商品サービス_全自動貸金庫
	private int serviceAutoSafeDepositBox = 0;
	// 商品サービス_一般貸金庫
	private int serviceSafeDepositBox = 0;
	// 商品サービス_ｾｰﾌﾃｨｹｰｽ
	private int serviceSafeBox = 0;
	// 店舗設備_IB専用PC
	private int internationalTradePC = 0;
	// 店舗設備_ｷｯｽﾞｽﾍﾟｰｽ
	private int childrenSpace = 0;
	// バリアフリー_視覚障害対応ATM
	private int barrierFreeVisualImpairment = 0;
	// バリアフリー_点字ﾌﾞﾛｯｸ
	private int barrierFreeBrailleBlock = 0;
	// バリアフリー_音声ｶﾞｲﾄﾞ
	private int barrierFreeVoiceGuide = 0;
	// バリアフリー_AED
	private int barrierFreeAED = 0;
	// ATM機能_振込
	private int atmFunctionTransfer = 0;
	// ATM機能_硬貨入出金
	private int atmFunctionCoinAccess = 0;
	// ATM機能_宝くじｻｰﾋﾞｽ
	private int atmFunctionLotteryService = 0;
	// ATM機能_手のひら認証
	private int atmFunctionPalmAuthentication = 0;
	// ATM機能_IC対応
	private int atmFunctionIC = 0;
	// ATM機能_PASPYチャージ
	private int atmFunctionPASPY = 0;
	// ATM機能_他行幹事
	private int atmFunctionOtherBankingAffairs = 0;
	// 座標_経度
	private String longitude = "";
	// 座標_緯度
	private String latitude = "";
	// 備考①
	private String comment1 = "";
	// 備考②
	private String comment2 = "";
	// 備考③
	private String comment3 = "";
	// 備考④
	private String comment4 = "";
	// 備考⑤
	private String comment5 = "";
	// 開始日時
	private String startDateTime = "";
	// 終了日時
	private String endDateTime = "";

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeometryDoc getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDoc geometry) {
        this.geometry = geometry;
    }

    public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
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

	public String getWindowOpenStartTime() {
		return windowOpenStartTime;
	}

	public void setWindowOpenStartTime(String windowOpenStartTime) {
		this.windowOpenStartTime = windowOpenStartTime;
	}

	public String getWindowOpenEndTime() {
		return windowOpenEndTime;
	}

	public void setWindowOpenEndTime(String windowOpenEndTime) {
		this.windowOpenEndTime = windowOpenEndTime;
	}

	public String getWindowOpenStartTime_SAT() {
		return windowOpenStartTime_SAT;
	}

	public void setWindowOpenStartTime_SAT(String windowOpenStartTime_SAT) {
		this.windowOpenStartTime_SAT = windowOpenStartTime_SAT;
	}

	public String getWindowOpenEndTime_SAT() {
		return windowOpenEndTime_SAT;
	}

	public void setWindowOpenEndTime_SAT(String windowOpenEndTime_SAT) {
		this.windowOpenEndTime_SAT = windowOpenEndTime_SAT;
	}

	public String getWindowOpenStartTime_SUN() {
		return windowOpenStartTime_SUN;
	}

	public void setWindowOpenStartTime_SUN(String windowOpenStartTime_SUN) {
		this.windowOpenStartTime_SUN = windowOpenStartTime_SUN;
	}

	public String getWindowOpenEndTime_SUN() {
		return windowOpenEndTime_SUN;
	}

	public void setWindowOpenEndTime_SUN(String windowOpenEndTime_SUN) {
		this.windowOpenEndTime_SUN = windowOpenEndTime_SUN;
	}

	public String getAtmOpenStartTime() {
		return atmOpenStartTime;
	}

	public void setAtmOpenStartTime(String atmOpenStartTime) {
		this.atmOpenStartTime = atmOpenStartTime;
	}

	public String getAtmOpenEndTime() {
		return atmOpenEndTime;
	}

	public void setAtmOpenEndTime(String atmOpenEndTime) {
		this.atmOpenEndTime = atmOpenEndTime;
	}

	public String getAtmOpenStartTime_SAT() {
		return atmOpenStartTime_SAT;
	}

	public void setAtmOpenStartTime_SAT(String atmOpenStartTime_SAT) {
		this.atmOpenStartTime_SAT = atmOpenStartTime_SAT;
	}

	public String getAtmOpenEndTime_SAT() {
		return atmOpenEndTime_SAT;
	}

	public void setAtmOpenEndTime_SAT(String atmOpenEndTime_SAT) {
		this.atmOpenEndTime_SAT = atmOpenEndTime_SAT;
	}

	public String getAtmOpenStartTime_SUN() {
		return atmOpenStartTime_SUN;
	}

	public void setAtmOpenStartTime_SUN(String atmOpenStartTime_SUN) {
		this.atmOpenStartTime_SUN = atmOpenStartTime_SUN;
	}

	public String getAtmOpenEndTime_SUN() {
		return atmOpenEndTime_SUN;
	}

	public void setAtmOpenEndTime_SUN(String atmOpenEndTime_SUN) {
		this.atmOpenEndTime_SUN = atmOpenEndTime_SUN;
	}


	public String getAtmCount() {
        return atmCount;
    }

    public void setAtmCount(String atmCount) {
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
}
