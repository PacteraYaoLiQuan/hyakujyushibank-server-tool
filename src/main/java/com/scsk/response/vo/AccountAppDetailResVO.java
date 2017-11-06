package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountAppPushListVO;
import com.scsk.vo.TacsflagVO;

public class AccountAppDetailResVO extends BaseResVO{
	// 姓名
	private String name;
	// 姓名カナ
	private String kanaName;
	// 生年月日
	private String birthday;
	// 性別
	private int sex;
	// 満年齢
	private String age;
	// 勤務先名
	private String workName;
	// 郵便番号
	private String postCode;
	// 都道府県
	private String address1;
	// 市区町村以下
	private String address2;
	// 市区町村以下（カナ）
	private String kanaAddress;
	// 自宅電話番号
	private String teleNumber;
	// 携帯電話番号
	private String phoneNumber;
	// 勤務先電話番号
	private String workTeleNumber;
	// 受付番号
	private String accountAppSeq;
	// 申込日
	private String applicationDate;
	// 申込受付日付
	private String receiptDate;
	// 発行するカード種類
	private String cardType;
	// 利用しないサービス
	private List<String> noApplicationService;
	// 商品指定
	private List<String> goodsAppointed;
	// 取引目的
	private List<String> tradingPurposes;
	// その他取引目的
	private String otherTradingPurposes;
	// 職業
	private List<String> occupation;
	// その他職業
	private String otherOccupations;
	// 暗証番号
	private String securityPassword;
	// テレホンバンキングサービス_事前登録振込（１回あたり）
	private int telRegisterPerTrans = 0;
	// テレホンバンキングサービス_事前登録振込（１日あたり）
	private int telRegisterPerDay = 0;
	// テレホンバンキングサービス_都度登録振込（１回あたり）
	private int telOncePerTrans = 0;
	// テレホンバンキングサービス_都度登録振込（１日あたり）
	private int telOncePerDay = 0;
	// インターネットバンキングサービス_事前登録振込（１回あたり）
	private int internetRegisterPerTrans = 0;
	// インターネットバンキングサービス_事前登録振込（１日あたり）
	private int internetRegisterPerDay = 0;
	// インターネットバンキングサービス_都度登録振込（１回あたり）
	private int internetOncePerTrans = 0;
	// インターネットバンキングサービス_都度登録振込（１日あたり）
	private int internetOncePerDay = 0;
	// モバイルバンキングサービス_事前登録振込（１回あたり）
	private int mobileRegisterPerTrans = 0;
	// モバイルバンキングサービス_事前登録振込（１日あたり）
	private int mobileRegisterPerDay = 0;
	// モバイルバンキングサービス_都度登録振込（１回あたり）
	private int mobileOncePerTrans = 0;
	// モバイルバンキングサービス_都度登録振込（１日あたり）
	private int mobileOncePerDay = 0;
	// 本人確認書類
	private String identificationType;
	// 本人確認書類画像
	private String identificationImage;
	// 本人確認書類画像裏面
	private String identificationImageBack;
	// 生活状況確認書類
	private String livingConditions;
	// 生活状況確認書類画像
	private String livingConditionsImage;
	// IPアドレス
	private String ipAddress = "";
	// 取引種類
	private String transactionType;
	// 申込サービス
	private List<String> applyService;
	// ステータス
	private String status;
	// 既に口座をお持ちの方
	private String holdAccount;
	// 既に口座をお持ちの方：店名
	private String holdAccountBank;
	// 既に口座をお持ちの方：口座番号
	private String holdAccountNumber;
	// ダイレクトバンキングサービスのご契約
	private String directServicesContract;
	// ダイレクトバンキングサービスのご契約：店名
	private String directServicesContractBank;
	// ダイレクトバンキングサービスのご契約：口座番号
	private String directServicesContractAccountNumber;
	// ダイレクトバンキングカード暗証番号
	private String directServicesCardPassword;
	// ひろぎんネット支店の口座開設の動機
	private String accountAppMotive;
	// ひろぎんネット支店の口座開設のその他動機
	private String accountAppOtherMotive;
	// ひろぎんネット支店を知った経緯
	private String knowProcess;
	// ひろぎんネット支店を知ったその他経緯
	private String knowOtherProcess;
	// push通知一覧表示用
	private List<AccountAppPushListVO> accountAppDetailPushList;
	// 国名コード
	private String IC_CountryCode;
	// 国名
	private String IC_CountryName;
	// 国脅威度
	private String IC_CountryThreat;
	// PS-IP
	private String IC_PSIP;
	// Proxy利用
	private String IC_Proxy;
	// 携帯電話（日本）
	private String IC_isMobile;
	// 登録企業
	private String IC_CompanyName;
	// 登録企業ドメイン
	private String IC_CompanyDomain;
	// IP登録地
	private String IC_CompanyCity;
	// IP登録地と郵便番号間の直線距離
	private String IC_Distance;
	// IP脅威度
	private String IC_IpThreat;
	// 検索履歴
	private String IC_SearchHistory;
	// 自宅電話番号認証結果
	private String TC_Access1;
	// 自宅電話番号検索結果
	private String TC_Result1;
	// 自宅電話番号加入期間
	private String TC_Month1;
	// 自宅電話番号移転番号
	private String TC_Movetel1;
	// 自宅電話番号キャリア名
	private String TC_Carrier1;
	// 自宅電話番号検索数
	private String TC_Count1;
	// 自宅電話番号アテンション
	private String TC_Attention1;
	// 自宅電話番号TACSフラグ
	private String TC_Tacsflag1;
	// 自宅電話番号最新年
	private String TC_Latestyear1;
	// 自宅電話番号最新月
	private String TC_Latestmonth1;
	/*
	 * // TACSフラグ０１ private String f01_１; // TACSフラグ０２ private String f02_１; //
	 * TACSフラグ０３ private String f03_１; // TACSフラグ０４ private String f04_１; //
	 * TACSフラグ０５ private String f05_１; // TACSフラグ０６ private String f06_１; //
	 * TACSフラグ０７ private String f07_１; // TACSフラグ０８ private String f08_１; //
	 * TACSフラグ０９ private String f09_１; // TACSフラグ１０ private String f10_１; //
	 * TACSフラグ１１ private String f11_１; // TACSフラグ１２ private String f12_１; //
	 * TACSフラグ１３ private String f13_１; // TACSフラグ１４ private String f14_１; //
	 * TACSフラグ１５ private String f15_１; // TACSフラグ１６ private String f16_１; //
	 * TACSフラグ１７ private String f17_１; // TACSフラグ１８ private String f18_１; //
	 * TACSフラグ１９ private String f19_１; // TACSフラグ２０ private String f20_１; //
	 * TACSフラグ２１ private String f21_１; // TACSフラグ２２ private String f22_１; //
	 * TACSフラグ２３ private String f23_１; // TACSフラグ２４ private String f24_１;
	 */
	// 携帯電話番号認証結果
	private String TC_Access2;
	// 携帯電話番号検索結果
	private String TC_Result2;
	// 携帯電話番号加入期間
	private String TC_Month2;
	// 携帯電話番号移転番号
	private String TC_Movetel2;
	// 携帯電話番号キャリア名
	private String TC_Carrier2;
	// 携帯電話番号検索数
	private String TC_Count2;
	// 携帯電話番号アテンション
	private String TC_Attention2;
	// 携帯電話番号TACSフラグ
	private String TC_Tacsflag2;
	// 携帯電話番号最新年
	private String TC_Latestyear2;
	// 携帯電話番号最新月
	private String TC_Latestmonth2;
	/*
	 * // TACSフラグ０１ private String f01_2; // TACSフラグ０２ private String f02_2; //
	 * TACSフラグ０３ private String f03_2; // TACSフラグ０４ private String f04_2; //
	 * TACSフラグ０５ private String f05_2; // TACSフラグ０６ private String f06_2; //
	 * TACSフラグ０７ private String f07_2; // TACSフラグ０８ private String f08_2; //
	 * TACSフラグ０９ private String f09_2; // TACSフラグ１０ private String f10_2; //
	 * TACSフラグ１１ private String f11_2; // TACSフラグ１２ private String f12_2; //
	 * TACSフラグ１３ private String f13_2; // TACSフラグ１４ private String f14_2; //
	 * TACSフラグ１５ private String f15_2; // TACSフラグ１６ private String f16_2; //
	 * TACSフラグ１７ private String f17_2; // TACSフラグ１８ private String f18_2; //
	 * TACSフラグ１９ private String f19_2; // TACSフラグ２０ private String f20_2; //
	 * TACSフラグ２１ private String f21_2; // TACSフラグ２２ private String f22_2; //
	 * TACSフラグ２３ private String f23_2; // TACSフラグ２４ private String f24_2;
	 */
	// 自宅電話番号利用ステータス履歴
	private List<TacsflagVO> TacsflagList1;
	// 携帯電話番号利用ステータス履歴
	private List<TacsflagVO> TacsflagList2;
	// 2週間以内の検索回数
	private String IC_TwoWeeksCount;
	// 自宅電話番号照会データ最新年月
	private String TC_LatestDate1;
	// 携帯電話番号照会データ最新年月
	private String TC_LatestDate2;
	private String appraisalTelResult;
	private String appraisalIPResult;
	public String getAppraisalIPResult() {
        return appraisalIPResult;
    }

    public void setAppraisalIPResult(String appraisalIPResult) {
        this.appraisalIPResult = appraisalIPResult;
    }

    public String getAppraisalTelResult() {
        return appraisalTelResult;
    }

    public void setAppraisalTelResult(String appraisalTelResult) {
        this.appraisalTelResult = appraisalTelResult;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKanaName() {
		return kanaName;
	}

	public void setKanaName(String kanaName) {
		this.kanaName = kanaName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getKanaAddress() {
		return kanaAddress;
	}

	public void setKanaAddress(String kanaAddress) {
		this.kanaAddress = kanaAddress;
	}

	public String getTeleNumber() {
		return teleNumber;
	}

	public void setTeleNumber(String teleNumber) {
		this.teleNumber = teleNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWorkTeleNumber() {
		return workTeleNumber;
	}

	public void setWorkTeleNumber(String workTeleNumber) {
		this.workTeleNumber = workTeleNumber;
	}

	public String getAccountAppSeq() {
		return accountAppSeq;
	}

	public void setAccountAppSeq(String accountAppSeq) {
		this.accountAppSeq = accountAppSeq;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public List<String> getNoApplicationService() {
		return noApplicationService;
	}

	public void setNoApplicationService(List<String> noApplicationService) {
		this.noApplicationService = noApplicationService;
	}

	public List<String> getGoodsAppointed() {
		return goodsAppointed;
	}

	public void setGoodsAppointed(List<String> goodsAppointed) {
		this.goodsAppointed = goodsAppointed;
	}

	public List<String> getTradingPurposes() {
		return tradingPurposes;
	}

	public void setTradingPurposes(List<String> tradingPurposes) {
		this.tradingPurposes = tradingPurposes;
	}

	public String getOtherTradingPurposes() {
		return otherTradingPurposes;
	}

	public void setOtherTradingPurposes(String otherTradingPurposes) {
		this.otherTradingPurposes = otherTradingPurposes;
	}

	public List<String> getOccupation() {
		return occupation;
	}

	public void setOccupation(List<String> occupation) {
		this.occupation = occupation;
	}

	public String getOtherOccupations() {
		return otherOccupations;
	}

	public void setOtherOccupations(String otherOccupations) {
		this.otherOccupations = otherOccupations;
	}

	public String getSecurityPassword() {
		return securityPassword;
	}

	public void setSecurityPassword(String securityPassword) {
		this.securityPassword = securityPassword;
	}

	public int getTelRegisterPerTrans() {
		return telRegisterPerTrans;
	}

	public void setTelRegisterPerTrans(int telRegisterPerTrans) {
		this.telRegisterPerTrans = telRegisterPerTrans;
	}

	public int getTelRegisterPerDay() {
		return telRegisterPerDay;
	}

	public void setTelRegisterPerDay(int telRegisterPerDay) {
		this.telRegisterPerDay = telRegisterPerDay;
	}

	public int getTelOncePerTrans() {
		return telOncePerTrans;
	}

	public void setTelOncePerTrans(int telOncePerTrans) {
		this.telOncePerTrans = telOncePerTrans;
	}

	public int getTelOncePerDay() {
		return telOncePerDay;
	}

	public void setTelOncePerDay(int telOncePerDay) {
		this.telOncePerDay = telOncePerDay;
	}

	public int getInternetRegisterPerTrans() {
		return internetRegisterPerTrans;
	}

	public void setInternetRegisterPerTrans(int internetRegisterPerTrans) {
		this.internetRegisterPerTrans = internetRegisterPerTrans;
	}

	public int getInternetRegisterPerDay() {
		return internetRegisterPerDay;
	}

	public void setInternetRegisterPerDay(int internetRegisterPerDay) {
		this.internetRegisterPerDay = internetRegisterPerDay;
	}

	public int getInternetOncePerTrans() {
		return internetOncePerTrans;
	}

	public void setInternetOncePerTrans(int internetOncePerTrans) {
		this.internetOncePerTrans = internetOncePerTrans;
	}

	public int getInternetOncePerDay() {
		return internetOncePerDay;
	}

	public void setInternetOncePerDay(int internetOncePerDay) {
		this.internetOncePerDay = internetOncePerDay;
	}

	public int getMobileRegisterPerTrans() {
		return mobileRegisterPerTrans;
	}

	public void setMobileRegisterPerTrans(int mobileRegisterPerTrans) {
		this.mobileRegisterPerTrans = mobileRegisterPerTrans;
	}

	public int getMobileRegisterPerDay() {
		return mobileRegisterPerDay;
	}

	public void setMobileRegisterPerDay(int mobileRegisterPerDay) {
		this.mobileRegisterPerDay = mobileRegisterPerDay;
	}

	public int getMobileOncePerTrans() {
		return mobileOncePerTrans;
	}

	public void setMobileOncePerTrans(int mobileOncePerTrans) {
		this.mobileOncePerTrans = mobileOncePerTrans;
	}

	public int getMobileOncePerDay() {
		return mobileOncePerDay;
	}

	public void setMobileOncePerDay(int mobileOncePerDay) {
		this.mobileOncePerDay = mobileOncePerDay;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationImage() {
		return identificationImage;
	}

	public void setIdentificationImage(String identificationImage) {
		this.identificationImage = identificationImage;
	}

	public String getIdentificationImageBack() {
		return identificationImageBack;
	}

	public void setIdentificationImageBack(String identificationImageBack) {
		this.identificationImageBack = identificationImageBack;
	}

	public String getLivingConditions() {
		return livingConditions;
	}

	public void setLivingConditions(String livingConditions) {
		this.livingConditions = livingConditions;
	}

	public String getLivingConditionsImage() {
		return livingConditionsImage;
	}

	public void setLivingConditionsImage(String livingConditionsImage) {
		this.livingConditionsImage = livingConditionsImage;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public List<String> getApplyService() {
		return applyService;
	}

	public void setApplyService(List<String> applyService) {
		this.applyService = applyService;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHoldAccount() {
		return holdAccount;
	}

	public void setHoldAccount(String holdAccount) {
		this.holdAccount = holdAccount;
	}

	public String getHoldAccountBank() {
		return holdAccountBank;
	}

	public void setHoldAccountBank(String holdAccountBank) {
		this.holdAccountBank = holdAccountBank;
	}

	public String getHoldAccountNumber() {
		return holdAccountNumber;
	}

	public void setHoldAccountNumber(String holdAccountNumber) {
		this.holdAccountNumber = holdAccountNumber;
	}

	public String getDirectServicesContract() {
		return directServicesContract;
	}

	public void setDirectServicesContract(String directServicesContract) {
		this.directServicesContract = directServicesContract;
	}

	public String getDirectServicesContractBank() {
		return directServicesContractBank;
	}

	public void setDirectServicesContractBank(String directServicesContractBank) {
		this.directServicesContractBank = directServicesContractBank;
	}

	public String getDirectServicesContractAccountNumber() {
		return directServicesContractAccountNumber;
	}

	public void setDirectServicesContractAccountNumber(
			String directServicesContractAccountNumber) {
		this.directServicesContractAccountNumber = directServicesContractAccountNumber;
	}

	public String getDirectServicesCardPassword() {
		return directServicesCardPassword;
	}

	public void setDirectServicesCardPassword(String directServicesCardPassword) {
		this.directServicesCardPassword = directServicesCardPassword;
	}

	public String getAccountAppMotive() {
		return accountAppMotive;
	}

	public void setAccountAppMotive(String accountAppMotive) {
		this.accountAppMotive = accountAppMotive;
	}

	public String getAccountAppOtherMotive() {
		return accountAppOtherMotive;
	}

	public void setAccountAppOtherMotive(String accountAppOtherMotive) {
		this.accountAppOtherMotive = accountAppOtherMotive;
	}

	public String getKnowProcess() {
		return knowProcess;
	}

	public void setKnowProcess(String knowProcess) {
		this.knowProcess = knowProcess;
	}

	public String getKnowOtherProcess() {
		return knowOtherProcess;
	}

	public void setKnowOtherProcess(String knowOtherProcess) {
		this.knowOtherProcess = knowOtherProcess;
	}

	public List<AccountAppPushListVO> getAccountAppDetailPushList() {
		return accountAppDetailPushList;
	}

	public void setAccountAppDetailPushList(
			List<AccountAppPushListVO> accountAppDetailPushList) {
		this.accountAppDetailPushList = accountAppDetailPushList;
	}

	public String getIC_CountryCode() {
		return IC_CountryCode;
	}

	public void setIC_CountryCode(String iC_CountryCode) {
		IC_CountryCode = iC_CountryCode;
	}

	public String getIC_CountryName() {
		return IC_CountryName;
	}

	public void setIC_CountryName(String iC_CountryName) {
		IC_CountryName = iC_CountryName;
	}

	public String getIC_CountryThreat() {
		return IC_CountryThreat;
	}

	public void setIC_CountryThreat(String iC_CountryThreat) {
		IC_CountryThreat = iC_CountryThreat;
	}

	public String getIC_PSIP() {
		return IC_PSIP;
	}

	public void setIC_PSIP(String iC_PSIP) {
		IC_PSIP = iC_PSIP;
	}

	public String getIC_Proxy() {
		return IC_Proxy;
	}

	public void setIC_Proxy(String iC_Proxy) {
		IC_Proxy = iC_Proxy;
	}

	public String getIC_isMobile() {
		return IC_isMobile;
	}

	public void setIC_isMobile(String iC_isMobile) {
		IC_isMobile = iC_isMobile;
	}

	public String getIC_CompanyName() {
		return IC_CompanyName;
	}

	public void setIC_CompanyName(String iC_CompanyName) {
		IC_CompanyName = iC_CompanyName;
	}

	public String getIC_CompanyDomain() {
		return IC_CompanyDomain;
	}

	public void setIC_CompanyDomain(String iC_CompanyDomain) {
		IC_CompanyDomain = iC_CompanyDomain;
	}

	public String getIC_CompanyCity() {
		return IC_CompanyCity;
	}

	public void setIC_CompanyCity(String iC_CompanyCity) {
		IC_CompanyCity = iC_CompanyCity;
	}

	public String getIC_Distance() {
		return IC_Distance;
	}

	public void setIC_Distance(String iC_Distance) {
		IC_Distance = iC_Distance;
	}

	public String getIC_IpThreat() {
		return IC_IpThreat;
	}

	public void setIC_IpThreat(String iC_IpThreat) {
		IC_IpThreat = iC_IpThreat;
	}

	public String getIC_SearchHistory() {
		return IC_SearchHistory;
	}

	public void setIC_SearchHistory(String iC_SearchHistory) {
		IC_SearchHistory = iC_SearchHistory;
	}

	public String getTC_Access1() {
		return TC_Access1;
	}

	public void setTC_Access1(String tC_Access1) {
		TC_Access1 = tC_Access1;
	}

	public String getTC_Result1() {
		return TC_Result1;
	}

	public void setTC_Result1(String tC_Result1) {
		TC_Result1 = tC_Result1;
	}

	public String getTC_Month1() {
		return TC_Month1;
	}

	public void setTC_Month1(String tC_Month1) {
		TC_Month1 = tC_Month1;
	}

	public String getTC_Movetel1() {
		return TC_Movetel1;
	}

	public void setTC_Movetel1(String tC_Movetel1) {
		TC_Movetel1 = tC_Movetel1;
	}

	public String getTC_Carrier1() {
		return TC_Carrier1;
	}

	public void setTC_Carrier1(String tC_Carrier1) {
		TC_Carrier1 = tC_Carrier1;
	}

	public String getTC_Count1() {
		return TC_Count1;
	}

	public void setTC_Count1(String tC_Count1) {
		TC_Count1 = tC_Count1;
	}

	public String getTC_Attention1() {
		return TC_Attention1;
	}

	public void setTC_Attention1(String tC_Attention1) {
		TC_Attention1 = tC_Attention1;
	}

	public String getTC_Tacsflag1() {
		return TC_Tacsflag1;
	}

	public void setTC_Tacsflag1(String tC_Tacsflag1) {
		TC_Tacsflag1 = tC_Tacsflag1;
	}

	public String getTC_Latestyear1() {
		return TC_Latestyear1;
	}

	public void setTC_Latestyear1(String tC_Latestyear1) {
		TC_Latestyear1 = tC_Latestyear1;
	}

	public String getTC_Latestmonth1() {
		return TC_Latestmonth1;
	}

	public void setTC_Latestmonth1(String tC_Latestmonth1) {
		TC_Latestmonth1 = tC_Latestmonth1;
	}

	public String getTC_Access2() {
		return TC_Access2;
	}

	public void setTC_Access2(String tC_Access2) {
		TC_Access2 = tC_Access2;
	}

	public String getTC_Result2() {
		return TC_Result2;
	}

	public void setTC_Result2(String tC_Result2) {
		TC_Result2 = tC_Result2;
	}

	public String getTC_Month2() {
		return TC_Month2;
	}

	public void setTC_Month2(String tC_Month2) {
		TC_Month2 = tC_Month2;
	}

	public String getTC_Movetel2() {
		return TC_Movetel2;
	}

	public void setTC_Movetel2(String tC_Movetel2) {
		TC_Movetel2 = tC_Movetel2;
	}

	public String getTC_Carrier2() {
		return TC_Carrier2;
	}

	public void setTC_Carrier2(String tC_Carrier2) {
		TC_Carrier2 = tC_Carrier2;
	}

	public String getTC_Count2() {
		return TC_Count2;
	}

	public void setTC_Count2(String tC_Count2) {
		TC_Count2 = tC_Count2;
	}

	public String getTC_Attention2() {
		return TC_Attention2;
	}

	public void setTC_Attention2(String tC_Attention2) {
		TC_Attention2 = tC_Attention2;
	}

	public String getTC_Tacsflag2() {
		return TC_Tacsflag2;
	}

	public void setTC_Tacsflag2(String tC_Tacsflag2) {
		TC_Tacsflag2 = tC_Tacsflag2;
	}

	public String getTC_Latestyear2() {
		return TC_Latestyear2;
	}

	public void setTC_Latestyear2(String tC_Latestyear2) {
		TC_Latestyear2 = tC_Latestyear2;
	}

	public String getTC_Latestmonth2() {
		return TC_Latestmonth2;
	}

	public void setTC_Latestmonth2(String tC_Latestmonth2) {
		TC_Latestmonth2 = tC_Latestmonth2;
	}

	public List<TacsflagVO> getTacsflagList1() {
		return TacsflagList1;
	}

	public void setTacsflagList1(List<TacsflagVO> tacsflagList1) {
		TacsflagList1 = tacsflagList1;
	}

	public List<TacsflagVO> getTacsflagList2() {
		return TacsflagList2;
	}

	public void setTacsflagList2(List<TacsflagVO> tacsflagList2) {
		TacsflagList2 = tacsflagList2;
	}

	public String getIC_TwoWeeksCount() {
		return IC_TwoWeeksCount;
	}

	public void setIC_TwoWeeksCount(String iC_TwoWeeksCount) {
		IC_TwoWeeksCount = iC_TwoWeeksCount;
	}

	public String getTC_LatestDate1() {
		return TC_LatestDate1;
	}

	public void setTC_LatestDate1(String tC_LatestDate1) {
		TC_LatestDate1 = tC_LatestDate1;
	}

	public String getTC_LatestDate2() {
		return TC_LatestDate2;
	}

	public void setTC_LatestDate2(String tC_LatestDate2) {
		TC_LatestDate2 = tC_LatestDate2;
	}
}
