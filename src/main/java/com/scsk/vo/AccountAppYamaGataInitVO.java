package com.scsk.vo;

import java.util.List;

public class AccountAppYamaGataInitVO {

	// 選択
	private Boolean select;
	// ドキュメントタイプ
	private String docType;
	// ユーザーID
	private String userId;
	// ユーザータイプ
	private int userType;
	// 端末ID
	private String deviceTokenId;
	// 免許証号
	private String licenseId;
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
	// birthdayJP
	private String birthdayJP;
	// 性別
	private String sexKbn;
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
	// 生計費決済
	private String accountPurpose1;
	// 事業費決済
	private String accountPurpose2;
	// 給与受取
	private String accountPurpose3;
	// 年金受取
	private String accountPurpose4;
	// 仕送り
	private String accountPurpose5;
	// 貯蓄
	private String accountPurpose6;
	// 資産運用
	private String accountPurpose7;
	// 融資返済用口座
	private String accountPurpose8;
	// 外国為替取引
	private String accountPurpose9;
	// その他記述
	private String accountPurpose99;
	// 職業
	private List<String> jobKbn;
	// その他職業
	private String jobKbnOther;
	// 会社役員・団体役員
	private String jobKbn1;
	// 会社員・団体職員
	private String jobKbn2;
	// 公務員 
	private String jobKbn3;
	// 個人事業主・自営業
	private String jobKbn4;
	// パート・アルバイト
	private String jobKbn5;
	// 派遣・嘱託・契約社員
	private String jobKbn6;
	// 主婦・主夫
	private String jobKbn7;
	// 年金受給者
	private String jobKbn8;
	// 学生
	private String jobKbn9;
	// 無職
	private String jobKbn10;
	// その他
	private String jobKbn49;
	// 通帳デザイン
	private String bankbookDesignKbn;
	// カードデザイン
	private String cardDesingKbn;
	// キャッシュカード暗証番号
	private String securityPassword;
	// 口座開設する店舗
	private String salesOfficeOption;
	// １日あたりの振込・払込限度額
	private int creditlimit;
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
	// Channel
	private String channel;
	// ErrMessage
	private String errMessage;
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	// IPアドレス
	private String ipAddress;
	// ステータス
	private String status;
	// 帳票出力回数
	private int billOutputCount;
	// Push開封状況
	private String pushStatus;
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
	// TACSフラグ０１
	private String f01_1;
	// TACSフラグ０２
	private String f02_1;
	// TACSフラグ０３
	private String f03_1;
	// TACSフラグ０４
	private String f04_1;
	// TACSフラグ０５
	private String f05_1;
	// TACSフラグ０６
	private String f06_1;
	// TACSフラグ０７
	private String f07_1;
	// TACSフラグ０８
	private String f08_1;
	// TACSフラグ０９
	private String f09_1;
	// TACSフラグ１０
	private String f10_1;
	// TACSフラグ１１
	private String f11_1;
	// TACSフラグ１２
	private String f12_1;
	// TACSフラグ１３
	private String f13_1;
	// TACSフラグ１４
	private String f14_1;
	// TACSフラグ１５
	private String f15_1;
	// TACSフラグ１６
	private String f16_1;
	// TACSフラグ１７
	private String f17_1;
	// TACSフラグ１８
	private String f18_1;
	// TACSフラグ１９
	private String f19_1;
	// TACSフラグ２０
	private String f20_1;
	// TACSフラグ２１
	private String f21_1;
	// TACSフラグ２２
	private String f22_1;
	// TACSフラグ２３
	private String f23_1;
	// TACSフラグ２４
	private String f24_1;

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
	// 携帯電話番号認証結果
	private String TC_Access2;
	// TACSフラグ０１
	private String f01_2;
	// TACSフラグ０２
	private String f02_2;
	// TACSフラグ０３
	private String f03_2;
	// TACSフラグ０４
	private String f04_2;
	// TACSフラグ０５
	private String f05_2;
	// TACSフラグ０６
	private String f06_2;
	// TACSフラグ０７
	private String f07_2;
	// TACSフラグ０８
	private String f08_2;
	// TACSフラグ０９
	private String f09_2;
	// TACSフラグ１０
	private String f10_2;
	// TACSフラグ１１
	private String f11_2;
	// TACSフラグ１２
	private String f12_2;
	// TACSフラグ１３
	private String f13_2;
	// TACSフラグ１４
	private String f14_2;
	// TACSフラグ１５
	private String f15_2;
	// TACSフラグ１６
	private String f16_2;
	// TACSフラグ１７
	private String f17_2;
	// TACSフラグ１８
	private String f18_2;
	// TACSフラグ１９
	private String f19_2;
	// TACSフラグ２０
	private String f20_2;
	// TACSフラグ２１
	private String f21_2;
	// TACSフラグ２２
	private String f22_2;
	// TACSフラグ２３
	private String f23_2;
	// TACSフラグ２４
	private String f24_2;
	// ID
	private String _id;
	// バージョン
	private String _rev;
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
	// 姓
	private String lastName;
	// 名
	private String firstName;
	// 姓カナ
	private String kanaLastName;
	// 名カナ
	private String kanaFirstName;
    // モデル名
    private String modelName = "";
    // OSバージョン
    private String osVersion = "";
    // APPバージョン
    private String appVersion = "";
    // 同意日時
    private String agreedOperationDate = "";
    
	public String getAgreedOperationDate() {
		return agreedOperationDate;
	}
	public void setAgreedOperationDate(String agreedOperationDate) {
		this.agreedOperationDate = agreedOperationDate;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getDeviceTokenId() {
		return deviceTokenId;
	}
	public void setDeviceTokenId(String deviceTokenId) {
		this.deviceTokenId = deviceTokenId;
	}
	public String getLicenseId() {
		return licenseId;
	}
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
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
	public String getSexKbn() {
		return sexKbn;
	}
	public void setSexKbn(String sexKbn) {
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
	public String getSalesOfficeOption() {
		return salesOfficeOption;
	}
	public void setSalesOfficeOption(String salesOfficeOption) {
		this.salesOfficeOption = salesOfficeOption;
	}
	public int getCreditlimit() {
		return creditlimit;
	}
	public void setCreditlimit(int creditlimit) {
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
	
	public int getBillOutputCount() {
		return billOutputCount;
	}
	public void setBillOutputCount(int billOutputCount) {
		this.billOutputCount = billOutputCount;
	}
	public String getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
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
	public String getF01_1() {
		return f01_1;
	}
	public void setF01_1(String f01_1) {
		this.f01_1 = f01_1;
	}
	public String getF02_1() {
		return f02_1;
	}
	public void setF02_1(String f02_1) {
		this.f02_1 = f02_1;
	}
	public String getF03_1() {
		return f03_1;
	}
	public void setF03_1(String f03_1) {
		this.f03_1 = f03_1;
	}
	public String getF04_1() {
		return f04_1;
	}
	public void setF04_1(String f04_1) {
		this.f04_1 = f04_1;
	}
	public String getF05_1() {
		return f05_1;
	}
	public void setF05_1(String f05_1) {
		this.f05_1 = f05_1;
	}
	public String getF06_1() {
		return f06_1;
	}
	public void setF06_1(String f06_1) {
		this.f06_1 = f06_1;
	}
	public String getF07_1() {
		return f07_1;
	}
	public void setF07_1(String f07_1) {
		this.f07_1 = f07_1;
	}
	public String getF08_1() {
		return f08_1;
	}
	public void setF08_1(String f08_1) {
		this.f08_1 = f08_1;
	}
	public String getF09_1() {
		return f09_1;
	}
	public void setF09_1(String f09_1) {
		this.f09_1 = f09_1;
	}
	public String getF10_1() {
		return f10_1;
	}
	public void setF10_1(String f10_1) {
		this.f10_1 = f10_1;
	}
	public String getF11_1() {
		return f11_1;
	}
	public void setF11_1(String f11_1) {
		this.f11_1 = f11_1;
	}
	public String getF12_1() {
		return f12_1;
	}
	public void setF12_1(String f12_1) {
		this.f12_1 = f12_1;
	}
	public String getF13_1() {
		return f13_1;
	}
	public void setF13_1(String f13_1) {
		this.f13_1 = f13_1;
	}
	public String getF14_1() {
		return f14_1;
	}
	public void setF14_1(String f14_1) {
		this.f14_1 = f14_1;
	}
	public String getF15_1() {
		return f15_1;
	}
	public void setF15_1(String f15_1) {
		this.f15_1 = f15_1;
	}
	public String getF16_1() {
		return f16_1;
	}
	public void setF16_1(String f16_1) {
		this.f16_1 = f16_1;
	}
	public String getF17_1() {
		return f17_1;
	}
	public void setF17_1(String f17_1) {
		this.f17_1 = f17_1;
	}
	public String getF18_1() {
		return f18_1;
	}
	public void setF18_1(String f18_1) {
		this.f18_1 = f18_1;
	}
	public String getF19_1() {
		return f19_1;
	}
	public void setF19_1(String f19_1) {
		this.f19_1 = f19_1;
	}
	public String getF20_1() {
		return f20_1;
	}
	public void setF20_1(String f20_1) {
		this.f20_1 = f20_1;
	}
	public String getF21_1() {
		return f21_1;
	}
	public void setF21_1(String f21_1) {
		this.f21_1 = f21_1;
	}
	public String getF22_1() {
		return f22_1;
	}
	public void setF22_1(String f22_1) {
		this.f22_1 = f22_1;
	}
	public String getF23_1() {
		return f23_1;
	}
	public void setF23_1(String f23_1) {
		this.f23_1 = f23_1;
	}
	public String getF24_1() {
		return f24_1;
	}
	public void setF24_1(String f24_1) {
		this.f24_1 = f24_1;
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
	public String getF01_2() {
		return f01_2;
	}
	public void setF01_2(String f01_2) {
		this.f01_2 = f01_2;
	}
	public String getF02_2() {
		return f02_2;
	}
	public void setF02_2(String f02_2) {
		this.f02_2 = f02_2;
	}
	public String getF03_2() {
		return f03_2;
	}
	public void setF03_2(String f03_2) {
		this.f03_2 = f03_2;
	}
	public String getF04_2() {
		return f04_2;
	}
	public void setF04_2(String f04_2) {
		this.f04_2 = f04_2;
	}
	public String getF05_2() {
		return f05_2;
	}
	public void setF05_2(String f05_2) {
		this.f05_2 = f05_2;
	}
	public String getF06_2() {
		return f06_2;
	}
	public void setF06_2(String f06_2) {
		this.f06_2 = f06_2;
	}
	public String getF07_2() {
		return f07_2;
	}
	public void setF07_2(String f07_2) {
		this.f07_2 = f07_2;
	}
	public String getF08_2() {
		return f08_2;
	}
	public void setF08_2(String f08_2) {
		this.f08_2 = f08_2;
	}
	public String getF09_2() {
		return f09_2;
	}
	public void setF09_2(String f09_2) {
		this.f09_2 = f09_2;
	}
	public String getF10_2() {
		return f10_2;
	}
	public void setF10_2(String f10_2) {
		this.f10_2 = f10_2;
	}
	public String getF11_2() {
		return f11_2;
	}
	public void setF11_2(String f11_2) {
		this.f11_2 = f11_2;
	}
	public String getF12_2() {
		return f12_2;
	}
	public void setF12_2(String f12_2) {
		this.f12_2 = f12_2;
	}
	public String getF13_2() {
		return f13_2;
	}
	public void setF13_2(String f13_2) {
		this.f13_2 = f13_2;
	}
	public String getF14_2() {
		return f14_2;
	}
	public void setF14_2(String f14_2) {
		this.f14_2 = f14_2;
	}
	public String getF15_2() {
		return f15_2;
	}
	public void setF15_2(String f15_2) {
		this.f15_2 = f15_2;
	}
	public String getF16_2() {
		return f16_2;
	}
	public void setF16_2(String f16_2) {
		this.f16_2 = f16_2;
	}
	public String getF17_2() {
		return f17_2;
	}
	public void setF17_2(String f17_2) {
		this.f17_2 = f17_2;
	}
	public String getF18_2() {
		return f18_2;
	}
	public void setF18_2(String f18_2) {
		this.f18_2 = f18_2;
	}
	public String getF19_2() {
		return f19_2;
	}
	public void setF19_2(String f19_2) {
		this.f19_2 = f19_2;
	}
	public String getF20_2() {
		return f20_2;
	}
	public void setF20_2(String f20_2) {
		this.f20_2 = f20_2;
	}
	public String getF21_2() {
		return f21_2;
	}
	public void setF21_2(String f21_2) {
		this.f21_2 = f21_2;
	}
	public String getF22_2() {
		return f22_2;
	}
	public void setF22_2(String f22_2) {
		this.f22_2 = f22_2;
	}
	public String getF23_2() {
		return f23_2;
	}
	public void setF23_2(String f23_2) {
		this.f23_2 = f23_2;
	}
	public String getF24_2() {
		return f24_2;
	}
	public void setF24_2(String f24_2) {
		this.f24_2 = f24_2;
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getKanaLastName() {
		return kanaLastName;
	}
	public void setKanaLastName(String kanaLastName) {
		this.kanaLastName = kanaLastName;
	}
	public String getKanaFirstName() {
		return kanaFirstName;
	}
	public void setKanaFirstName(String kanaFirstName) {
		this.kanaFirstName = kanaFirstName;
	}
	public String getAccountPurpose1() {
		return accountPurpose1;
	}
	public void setAccountPurpose1(String accountPurpose1) {
		this.accountPurpose1 = accountPurpose1;
	}
	public String getAccountPurpose2() {
		return accountPurpose2;
	}
	public void setAccountPurpose2(String accountPurpose2) {
		this.accountPurpose2 = accountPurpose2;
	}
	public String getAccountPurpose3() {
		return accountPurpose3;
	}
	public void setAccountPurpose3(String accountPurpose3) {
		this.accountPurpose3 = accountPurpose3;
	}
	public String getAccountPurpose4() {
		return accountPurpose4;
	}
	public void setAccountPurpose4(String accountPurpose4) {
		this.accountPurpose4 = accountPurpose4;
	}
	public String getAccountPurpose5() {
		return accountPurpose5;
	}
	public void setAccountPurpose5(String accountPurpose5) {
		this.accountPurpose5 = accountPurpose5;
	}
	public String getAccountPurpose6() {
		return accountPurpose6;
	}
	public void setAccountPurpose6(String accountPurpose6) {
		this.accountPurpose6 = accountPurpose6;
	}
	public String getAccountPurpose7() {
		return accountPurpose7;
	}
	public void setAccountPurpose7(String accountPurpose7) {
		this.accountPurpose7 = accountPurpose7;
	}
	public String getAccountPurpose8() {
		return accountPurpose8;
	}
	public void setAccountPurpose8(String accountPurpose8) {
		this.accountPurpose8 = accountPurpose8;
	}
	public String getAccountPurpose9() {
		return accountPurpose9;
	}
	public void setAccountPurpose9(String accountPurpose9) {
		this.accountPurpose9 = accountPurpose9;
	}
    public String getAccountPurpose99() {
		return accountPurpose99;
	}
    public void setAccountPurpose99(String accountPurpose99) {
	this.accountPurpose99 = accountPurpose99;
    }
	public String getJobKbn1() {
		return jobKbn1;
	}
	public void setJobKbn1(String jobKbn1) {
		this.jobKbn1 = jobKbn1;
	}
	public String getJobKbn2() {
		return jobKbn2;
	}
	public void setJobKbn2(String jobKbn2) {
		this.jobKbn2 = jobKbn2;
	}
	public String getJobKbn3() {
		return jobKbn3;
	}
	public void setJobKbn3(String jobKbn3) {
		this.jobKbn3 = jobKbn3;
	}
	public String getJobKbn4() {
		return jobKbn4;
	}
	public void setJobKbn4(String jobKbn4) {
		this.jobKbn4 = jobKbn4;
	}
	public String getJobKbn5() {
		return jobKbn5;
	}
	public void setJobKbn5(String jobKbn5) {
		this.jobKbn5 = jobKbn5;
	}
	public String getJobKbn6() {
		return jobKbn6;
	}
	public void setJobKbn6(String jobKbn6) {
		this.jobKbn6 = jobKbn6;
	}
	public String getJobKbn7() {
		return jobKbn7;
	}
	public void setJobKbn7(String jobKbn7) {
		this.jobKbn7 = jobKbn7;
	}
	public String getJobKbn8() {
		return jobKbn8;
	}
	public void setJobKbn8(String jobKbn8) {
		this.jobKbn8 = jobKbn8;
	}
	public String getJobKbn9() {
		return jobKbn9;
	}
	public void setJobKbn9(String jobKbn9) {
		this.jobKbn9 = jobKbn9;
	}
	public String getJobKbn10() {
		return jobKbn10;
	}
	public void setJobKbn10(String jobKbn10) {
		this.jobKbn10 = jobKbn10;
	}
	public String getJobKbn49() {
		return jobKbn49;
	}
	public void setJobKbn49(String jobKbn49) {
		this.jobKbn49 = jobKbn49;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBirthdayJP() {
		return birthdayJP;
	}
	public void setBirthdayJP(String birthdayJP) {
		this.birthdayJP = birthdayJP;
	}
    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    public String getOsVersion() {
        return osVersion;
    }
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
    public String getAppVersion() {
        return appVersion;
    }
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}