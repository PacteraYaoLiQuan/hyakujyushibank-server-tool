package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountAppPushListVO;
import com.scsk.vo.TacsflagVO;
import com.scsk.vo.YamagataStatusModifyListVO;

public class AccountAppYamaGataDetailResVO extends BaseResVO {
	
    // push通知一覧表示用
	private List<AccountAppPushListVO> accountAppDetailPushList;
	private List<YamagataStatusModifyListVO> yamagataStatusModifyList;
	// 姓名
	private String name;
	// 姓名カナ
	private String kanaName;
	// 生年月日
	private String birthday;
	// 和暦年号
    private String ordinaryDepositEraKbn;
	// 和暦生年月日
	private String eraBirthday;
	// 普通預金の種類
	private String accountType;
	// 性別
	private int sexKbn;
	// 年齢
	private String age;
	// お勤め先（学校名）
	private String workName;
	// 郵便番号
	private String postCode;
	// 都道府県
	private String prefecturesCode;
	// 市区町村・番地・アパート・マンション名
	private String address;
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
	// 取引目的
	private List<String> accountPurpose;
	// その他取引目的
	private String accountPurposeOther;
	// 職業
	private List<String> jobKbn;
	// その他職業
	private String jobKbnOther;
	// 通帳デザイン
	private String bankbookDesignKbn;
	// カードデザイン
	private String cardDesingKbn;
	// キャッシュカード暗証番号
	private String securityPassword;
	// 口座開設する店舗
	private String salesOfficeOption;
	// １日あたりの振込・払込限度額
	private String creditlimit;
	// オンライン暗証番号
	private String onlinePassword;
	// きっかけ
	private String knowProcess;
	// きっかけ(その他)
	private String knowProcessOther;
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
	private String ipAddress;
	// ステータス
	private String status;
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
	public String getOrdinaryDepositEraKbn() {
		return ordinaryDepositEraKbn;
	}
	public void setOrdinaryDepositEraKbn(String ordinaryDepositEraKbn) {
		this.ordinaryDepositEraKbn = ordinaryDepositEraKbn;
	}
	public String getEraBirthday() {
		return eraBirthday;
	}
	public void setEraBirthday(String eraBirthday) {
		this.eraBirthday = eraBirthday;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public int getSexKbn() {
		return sexKbn;
	}
	public void setSexKbn(int sexKbn) {
		this.sexKbn = sexKbn;
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
	public String getPrefecturesCode() {
		return prefecturesCode;
	}
	public void setPrefecturesCode(String prefecturesCode) {
		this.prefecturesCode = prefecturesCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public List<String> getAccountPurpose() {
		return accountPurpose;
	}
	public void setAccountPurpose(List<String> accountPurpose) {
		this.accountPurpose = accountPurpose;
	}
	public String getAccountPurposeOther() {
		return accountPurposeOther;
	}
	public void setAccountPurposeOther(String accountPurposeOther) {
		this.accountPurposeOther = accountPurposeOther;
	}
	public List<String> getJobKbn() {
		return jobKbn;
	}
	public void setJobKbn(List<String> jobKbn) {
		this.jobKbn = jobKbn;
	}
	public String getJobKbnOther() {
		return jobKbnOther;
	}
	public void setJobKbnOther(String jobKbnOther) {
		this.jobKbnOther = jobKbnOther;
	}
	public String getBankbookDesignKbn() {
		return bankbookDesignKbn;
	}
	public void setBankbookDesignKbn(String bankbookDesignKbn) {
		this.bankbookDesignKbn = bankbookDesignKbn;
	}
	public String getCardDesingKbn() {
		return cardDesingKbn;
	}
	public void setCardDesingKbn(String cardDesingKbn) {
		this.cardDesingKbn = cardDesingKbn;
	}
	public String getSecurityPassword() {
		return securityPassword;
	}
	public void setSecurityPassword(String securityPassword) {
		this.securityPassword = securityPassword;
	}
	public String getCreditlimit() {
		return creditlimit;
	}
	public void setCreditlimit(String creditlimit) {
		this.creditlimit = creditlimit;
	}
	public String getOnlinePassword() {
		return onlinePassword;
	}
	public void setOnlinePassword(String onlinePassword) {
		this.onlinePassword = onlinePassword;
	}
	public String getKnowProcess() {
		return knowProcess;
	}
	public void setKnowProcess(String knowProcess) {
		this.knowProcess = knowProcess;
	}
	public String getKnowProcessOther() {
		return knowProcessOther;
	}
	public void setKnowProcessOther(String knowProcessOther) {
		this.knowProcessOther = knowProcessOther;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getAppraisalTelResult() {
		return appraisalTelResult;
	}
	public void setAppraisalTelResult(String appraisalTelResult) {
		this.appraisalTelResult = appraisalTelResult;
	}
	public String getAppraisalIPResult() {
		return appraisalIPResult;
	}
	public void setAppraisalIPResult(String appraisalIPResult) {
		this.appraisalIPResult = appraisalIPResult;
	}
	public String getSalesOfficeOption() {
		return salesOfficeOption;
	}
	public void setSalesOfficeOption(String salesOfficeOption) {
		this.salesOfficeOption = salesOfficeOption;
	}
	public List<AccountAppPushListVO> getAccountAppDetailPushList() {
		return accountAppDetailPushList;
	}
	public void setAccountAppDetailPushList(List<AccountAppPushListVO> accountAppDetailPushList) {
		this.accountAppDetailPushList = accountAppDetailPushList;
	}
   public List<YamagataStatusModifyListVO> getYamagataStatusModifyList() {
        return yamagataStatusModifyList;
    }
    public void setYamagataStatusModifyList(List<YamagataStatusModifyListVO> yamagataStatusModifyList) {
        this.yamagataStatusModifyList = yamagataStatusModifyList;
    }
}
