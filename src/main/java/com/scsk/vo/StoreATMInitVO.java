package com.scsk.vo;

public class StoreATMInitVO {

	// 選択
	private Boolean select;
	// ドキュメントタイプ
	private String docType;
	// 店舗区分
	private int typeKbn;
	// 店舗コード_母店番号
	private String storeNumber;
	// 店舗コード_出張所枝番
	private String subStoreNumber;
	// 店舗コード_ATM枝番
	private String atmStoreNumber;
	// 店舗コード
	private String storeATMCode;
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
	private String windowOpenStartTime;
	// 窓口営業時間_平日終業
	private String windowOpenEndTime;
	// 窓口営業時間_土曜日始業
	private String windowOpenStartTime_SAT;
	// 窓口営業時間_土曜日終業
	private String windowOpenEndTime_SAT;
	// 窓口営業時間_日曜日始業
	private String windowOpenStartTime_SUN;
	// 窓口営業時間_日曜日終業
	private String windowOpenEndTime_SUN;
	// ATM営業時間_平日始業
	private String atmOpenStartTime;
	// ATM営業時間_平日終業
	private String atmOpenEndTime;
	// ATM営業時間_土曜日始業
	private String atmOpenStartTime_SAT;
	// ATM営業時間_土曜日終業
	private String atmOpenEndTime_SAT;
	// ATM営業時間_日曜日始業
	private String atmOpenStartTime_SUN;
	// ATM営業時間_日曜日終業
	private String atmOpenEndTime_SUN;
	// ATM設置台数
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
	private String serviceDollarEuro;
	// 商品サービス_外貨両替（ｱｼﾞｱ通貨）
	private String serviceAsia;
	// 商品サービス_外貨両替（その他）
	private String serviceOther;
	// 商品サービス_外貨買取
	private String serviceForeignExchange;
	// 商品サービス_投資信託
	private String serviceInvestmentTrust;
	// 商品サービス_年金保険
	private String servicePensionInsurance;
	// 商品サービス_金融商品仲介（みずほ証券）
	private String serviceMizuho;
	// 商品サービス_金融商品仲介（ひろぎんウツミ屋）
	private String serviceHirginUtsumiya;
	// 商品サービス_全自動貸金庫
	private String serviceAutoSafeDepositBox;
	// 商品サービス_一般貸金庫
	private String serviceSafeDepositBox;
	// 商品サービス_ｾｰﾌﾃｨｹｰｽ
	private String serviceSafeBox;
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
	private String atmFunctionTransfer;
	// ATM機能_硬貨入出金
	private String atmFunctionCoinAccess;
	// ATM機能_宝くじｻｰﾋﾞｽ
	private String atmFunctionLotteryService;
	// ATM機能_手のひら認証
	private String atmFunctionPalmAuthentication;
	// ATM機能_IC対応
	private String atmFunctionIC;
	// ATM機能_PASPYチャージ
	private String atmFunctionPASPY;
	// ATM機能_他行幹事
	private String atmFunctionOtherBankingAffairs;
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
	// ID
	private String _id;
	// バージョン
	private String _rev;	
	//利用可否フラグ 
	private Boolean canUseFlag;
	
	private String delFlg;

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getStoreATMCode() {
        return storeATMCode;
    }

    public void setStoreATMCode(String storeATMCode) {
        this.storeATMCode = storeATMCode;
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

	public String getServiceDollarEuro() {
		return serviceDollarEuro;
	}

	public void setServiceDollarEuro(String serviceDollarEuro) {
		this.serviceDollarEuro = serviceDollarEuro;
	}

	public String getServiceAsia() {
		return serviceAsia;
	}

	public void setServiceAsia(String serviceAsia) {
		this.serviceAsia = serviceAsia;
	}

	public String getServiceOther() {
		return serviceOther;
	}

	public void setServiceOther(String serviceOther) {
		this.serviceOther = serviceOther;
	}

	public String getServiceForeignExchange() {
		return serviceForeignExchange;
	}

	public void setServiceForeignExchange(String serviceForeignExchange) {
		this.serviceForeignExchange = serviceForeignExchange;
	}

	public String getServiceInvestmentTrust() {
		return serviceInvestmentTrust;
	}

	public void setServiceInvestmentTrust(String serviceInvestmentTrust) {
		this.serviceInvestmentTrust = serviceInvestmentTrust;
	}

	public String getServicePensionInsurance() {
		return servicePensionInsurance;
	}

	public void setServicePensionInsurance(String servicePensionInsurance) {
		this.servicePensionInsurance = servicePensionInsurance;
	}

	public String getServiceMizuho() {
		return serviceMizuho;
	}

	public void setServiceMizuho(String serviceMizuho) {
		this.serviceMizuho = serviceMizuho;
	}

	public String getServiceHirginUtsumiya() {
		return serviceHirginUtsumiya;
	}

	public void setServiceHirginUtsumiya(String serviceHirginUtsumiya) {
		this.serviceHirginUtsumiya = serviceHirginUtsumiya;
	}

	public String getServiceAutoSafeDepositBox() {
		return serviceAutoSafeDepositBox;
	}

	public void setServiceAutoSafeDepositBox(String serviceAutoSafeDepositBox) {
		this.serviceAutoSafeDepositBox = serviceAutoSafeDepositBox;
	}

	public String getServiceSafeDepositBox() {
		return serviceSafeDepositBox;
	}

	public void setServiceSafeDepositBox(String serviceSafeDepositBox) {
		this.serviceSafeDepositBox = serviceSafeDepositBox;
	}

	public String getServiceSafeBox() {
		return serviceSafeBox;
	}

	public void setServiceSafeBox(String serviceSafeBox) {
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

	public String getAtmFunctionTransfer() {
		return atmFunctionTransfer;
	}

	public void setAtmFunctionTransfer(String atmFunctionTransfer) {
		this.atmFunctionTransfer = atmFunctionTransfer;
	}

	public String getAtmFunctionCoinAccess() {
		return atmFunctionCoinAccess;
	}

	public void setAtmFunctionCoinAccess(String atmFunctionCoinAccess) {
		this.atmFunctionCoinAccess = atmFunctionCoinAccess;
	}

	public String getAtmFunctionLotteryService() {
		return atmFunctionLotteryService;
	}

	public void setAtmFunctionLotteryService(String atmFunctionLotteryService) {
		this.atmFunctionLotteryService = atmFunctionLotteryService;
	}

	public String getAtmFunctionPalmAuthentication() {
		return atmFunctionPalmAuthentication;
	}

	public void setAtmFunctionPalmAuthentication(
			String atmFunctionPalmAuthentication) {
		this.atmFunctionPalmAuthentication = atmFunctionPalmAuthentication;
	}

	public String getAtmFunctionIC() {
		return atmFunctionIC;
	}

	public void setAtmFunctionIC(String atmFunctionIC) {
		this.atmFunctionIC = atmFunctionIC;
	}

	public String getAtmFunctionPASPY() {
		return atmFunctionPASPY;
	}

	public void setAtmFunctionPASPY(String atmFunctionPASPY) {
		this.atmFunctionPASPY = atmFunctionPASPY;
	}

	public String getAtmFunctionOtherBankingAffairs() {
		return atmFunctionOtherBankingAffairs;
	}

	public void setAtmFunctionOtherBankingAffairs(
			String atmFunctionOtherBankingAffairs) {
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

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}
	

	public Boolean getCanUseFlag() {
		return canUseFlag;
	}

	public void setCanUseFlag(Boolean canUseFlag) {
		this.canUseFlag = canUseFlag;
	}
}