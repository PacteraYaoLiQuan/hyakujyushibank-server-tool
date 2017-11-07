package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.YamagataStatusModifyListVO;

public class AccountLoan114DetailResVO extends BaseResVO {

    private List<YamagataStatusModifyListVO> yamagataStatusModifyList;
    private String docType;
    private Boolean select;
    private String status;
    // ユーザーID
    private String userId;
    // ユーザータイプ
    private String userType;
    // 受付番号
    private String accountAppSeq;
    private String loanAppSeq;
    //
    private String agreeTime;
    private String agreeCheck;

    public String getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(String agreeTime) {
        this.agreeTime = agreeTime;
    }

    public String getAgreeCheck() {
        return agreeCheck;
    }

    public void setAgreeCheck(String agreeCheck) {
        this.agreeCheck = agreeCheck;
    }

    private String loanAppTime;

    public String getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(String loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    // ローン種類
    private String loanType;
    // ローン種類
    private String loanFlag;
    // カードローン申し込み済フラグ
    private String reLoadInfoFlg;
    // 同意フラグ
    private String agreeFlg;
    // お借入希望日
    private String getHopeDate;

    // お借入限度額
    private String limitMoney;
    // 運転免許証表面
    private String driverLicenseF;
    // 運転免許証裏面
    private String driverLicenseB;
    // ダイレクトメール
    private String dairekutoMail;
    // 運転免許証画像の受付番号
    private String driverLicenseSeq;

    // 読取り運転免許証番号
    private String readDriverLicenseNo;
    // 読取り名前の名(読取結果確認)
    private String readFirstName;
    // 読取り名前の姓(読取結果確認)
    private String readLastName;
    // 読取り生年月日
    private String readBirthDay;
    // お名前_漢字
    private String firstName;
    // お名前_フリガナ
    private String lastName;
    // 姓名
    private String name;
    // 年齢
    private String age;

    private String kanaName;

    // お名前_フリガナの名
    private String firstKanaName;
    // お名前_フリガナの姓
    private String lastKanaName;
    // 和暦年号
    private String yearType;

    public String getEraBirthday() {
        return EraBirthday;
    }

    public void setEraBirthday(String eraBirthday) {
        EraBirthday = eraBirthday;
    }

    // 生年月日_和暦
    private String EraBirthday;
    // 生年月日_年
    private String year;
    // 生年月日_月
    private String month;
    // 生年月日_日
    private String day;
    // 生年月日
    private String birthday;
    // 性別
    private String sexKbn;
    // 国籍
    private String country;
    // 運転免許番号フラグ
    private String driverLicenseFlg;
    // 運転免許番号
    private String driverLicenseNo;
    // 読取り郵便番号
    private String readPostCode;
    // 読取り都道府県
    private String readPrefecturesCode;
    // 読取り住所
    private String readAddress;
    // 読取り住所_以降ご住所
    private String readOtherAddress;
    // 郵便番号
    private String postCode;
    // ご自宅住所_都道府県
    private String prefecturesCode;
    // ご自宅住所_住所
    private String address;
    // ご自宅住所_以降ご住所
    private String otherAddress;
    // お電話番号_自宅
    private String teleNumber;
    // お電話番号_携帯・PHS
    private String phoneNumber;
    // お住まい
    private String enAddress;
    // 居住年数_年
    private String liveYear;
    // 居住年数_ヶ月
    private String liveMonth;
    // 家賃（住宅ローン）_毎月（円）
    private String rentLoan;
    // ご家族_配偶者
    private String spouse;
    // ご家族_居住形態
    private String liveType;
    // ご家族_扶養家族（人）
    private String familyNumber;
    // 承知フラグ
    private String masterKnowFlg;
    // ご職業
    private String occupation;
    // 有無フラグ
    private String sendFlg;
    // お勤め先名_漢字
    private String companyName;
    // お勤め先名_フリガナ
    private String companyKanaName;
    // お勤め先名（屋号）_漢字
    private String familyName;
    // お勤め先名（屋号）_フリガナ
    private String familyKanaName;
    // お勤め先名（屋号）（年金種類）_漢字
    private String incomeName;
    // お勤め先名（屋号）（年金種類）_フリガナ
    private String incomeKanaName;
    // お勤め先名（屋号）_漢字
    private String otherName;
    // お勤め先名（屋号）_フリガナ
    private String otherKanaName;
    // 業種
    private String jobType;
    // お勤め先郵便番号
    private String workPostCode;
    // お勤め先所在地_都道府県
    private String workPrefecturesCode;
    // お勤め先所在地_住所
    private String workAddress;
    // お勤め先所在地_以降住所
    private String workOtherAddress;
    // お勤め先電話番号_代表電話
    private String workTeleNumber;
    // お勤め先電話番号_所属部直通
    private String workPhoneNumber;
    // 所得区分フラグ
    private String getKbnFlg;
    // 所得区分
    private String getKbn;
    // 事業内容
    private String jobContent;
    // 役職
    private String position;
    // 従業員数
    private String jobNumber;
    // 勤続（営）年数_年
    private String workYear;
    // 勤続（営）年数_ヶ月
    private String workMonth;
    // 所属部課名
    private String department;
    // 入社年月_和暦
    private String workStartYearType;
    // 入社年月_年
    private String workStartYear;
    // 入社年月_月入社
    private String workStartMonth;
    // 入社年月
    private String workYearMonth;
    // 給料日_毎月（日）
    private String payment;
    // 最終学歴卒業年月
    private String graduateYearMonth;
    // 最終学歴卒業年月_和暦
    private String graduateYearType;
    // 最終学歴卒業年月_年
    private String graduateYear;
    // 最終学歴卒業年月_月入社
    private String graduateMonth;
    // 出向先会社名_漢字
    private String visitingName;
    // 出向先会社名_フリガナ
    private String visitingKanaName;
    // 出向先所属部課名
    private String visitingDepartment;
    // 役職
    private String visitingPosition;
    // 出向先郵便番号
    private String visitingPostCode;
    // 出向先所在地_都道府県
    private String visitPreCode;
    // 出向先所在地_住所
    private String visitAddress;
    // 出向先所在地_以降住所
    private String visitOtherAddress;
    // 出向先電話番号
    private String visitTelNumber;
    // 年収/税込年収フラグ
    private String incomeFlg;
    // 年収/税込年収
    private String incomeYear;
    // 税込年収 [世帯収入]
    private String incomeFamily;
    // 税込年収 [年金受給額]
    private String incomeYearPay;
    // お仕事の内容
    private String workContent;
    // 社員数
    private String workNumber;
    // 就業形態
    private String workType;
    // お勤め先の種類
    private String workTypeCode;
    // 資本金
    private String money;
    // 住宅ローン契約（当行）
    private String rentLoanContract;
    // 本社所在地
    private String comPreCode;
    // 健康保険証の種類
    private String healthType;
    // お申込金額_万円
    private String applicationMoney;
    // お借入希望日_[平成] 年
    private String getHopeYearType;
    // お借入希望日_月
    private String getHopeMonth;
    // お借入希望日_日
    private String getHopeDay;
    // 毎月返済希望額_円
    private String returnHopeMonth;
    // 返済希望回数_回
    private String returnHopeCount;
    // 内据置回数_回
    private String inCount;
    // ご利用目的フラグ
    private String purposeFlg;
    // ご利用目的_「10.その他」選択時
    private String otherPurpose;
    // ご利用目的_目的
    private String purpose;
    // ご利用目的_所要資金総額（万円）
    private String moneyTotal;
    // ご利用目的_お支払先（ご購入先）①
    private String payMoney1;
    // ご利用目的_金額①（万円）
    private String money1;
    // ご利用目的_お支払先（ご購入先）②
    private String payMoney2;
    // ご利用目的_金額②（万円）
    private String money2;
    // 口座持ちフラグ
    private String ownAccountKbn;
    // 支店
    private String storeName;
    // 支店フラグ
    private String storeNameFlg;
    // 口座番号（普通）
    private String accountNumber;
    // 返済併用フラグ
    private String increaseFlg;
    // 返済日
    private String returnDay;
    // 返済開始日
    private String returnStartDay;
    // 返済元金（万円）
    private String returnMoney;
    // 返済月1
    private String increaseReturn1;
    // 返済月2
    private String increaseReturn2;
    // 返済開始日
    private String returnStartDay2;
    // 返済元金（万円）
    private String returnMoney2;
    // 借入フラグ
    private String getFlg;
    // 件
    private String getCount;
    // 万円
    private String getMoney;
    // 借入フラグ
    private String getFromOtherFlg;
    // [無担保ローン] 残高（万円）
    private String noLoanRest;
    // [無担保ローン] 年間返済額（万円）
    private String noLoanReturnMoney;
    // [住宅ローン] 残高（万円）
    private String loanRest;
    // [住宅ローン] 年間返済額（万円）
    private String loanReturnMoney;
    // [カードローン] 残高（万円）
    private String cardLoanRest;
    // [カードローン] 年間返済額（万円）
    private String cardLoanRestRM;
    // [信販会社等その他] 残高（万円）
    private String otherComRest;
    // [信販会社等その他] 年間返済額（万円）
    private String otherComReturnMoney;
    // [信販会社等その他] 残高（万円）
    private String otherComRest2;
    // [信販会社等その他] 年間返済額（万円）
    private String otherComReturnMoney2;
    // [信販会社等その他] 残高（万円）
    private String otherComRest3;
    // [信販会社等その他] 年間返済額（万円）
    private String otherComReturnMoney3;
    // [信販会社等その他] 残高（万円）
    private String otherComRest4;
    // [信販会社等その他] 年間返済額（万円）
    private String otherComReturnMoney4;
    // [信販会社等その他] 残高（万円）
    private String otherComRest5;
    // [信販会社等その他] 年間返済額（万円）
    private String otherComReturnMoney5;
    // [信販会社等その他] 残高（万円）
    private String otherComRest6;
    // [信販会社等その他] 年間返済額（万円）
    private String otherComReturnMoney6;

    public String getOtherComRest2() {
        return otherComRest2;
    }

    public void setOtherComRest2(String otherComRest2) {
        this.otherComRest2 = otherComRest2;
    }

    public String getOtherComReturnMoney2() {
        return otherComReturnMoney2;
    }

    public void setOtherComReturnMoney2(String otherComReturnMoney2) {
        this.otherComReturnMoney2 = otherComReturnMoney2;
    }

    public String getOtherComRest3() {
        return otherComRest3;
    }

    public void setOtherComRest3(String otherComRest3) {
        this.otherComRest3 = otherComRest3;
    }

    public String getOtherComReturnMoney3() {
        return otherComReturnMoney3;
    }

    public void setOtherComReturnMoney3(String otherComReturnMoney3) {
        this.otherComReturnMoney3 = otherComReturnMoney3;
    }

    public String getOtherComRest4() {
        return otherComRest4;
    }

    public void setOtherComRest4(String otherComRest4) {
        this.otherComRest4 = otherComRest4;
    }

    public String getOtherComReturnMoney4() {
        return otherComReturnMoney4;
    }

    public void setOtherComReturnMoney4(String otherComReturnMoney4) {
        this.otherComReturnMoney4 = otherComReturnMoney4;
    }

    public String getOtherComRest5() {
        return otherComRest5;
    }

    public void setOtherComRest5(String otherComRest5) {
        this.otherComRest5 = otherComRest5;
    }

    public String getOtherComReturnMoney5() {
        return otherComReturnMoney5;
    }

    public void setOtherComReturnMoney5(String otherComReturnMoney5) {
        this.otherComReturnMoney5 = otherComReturnMoney5;
    }

    public String getOtherComRest6() {
        return otherComRest6;
    }

    public void setOtherComRest6(String otherComRest6) {
        this.otherComRest6 = otherComRest6;
    }

    public String getOtherComReturnMoney6() {
        return otherComReturnMoney6;
    }

    public void setOtherComReturnMoney6(String otherComReturnMoney6) {
        this.otherComReturnMoney6 = otherComReturnMoney6;
    }

    // [他金融機関] 残高（万円）
    private String otherFinanceRest;
    // [他金融機関] 年間返済額（万円）
    private String otherFinanceRM;
    // [銀行系カードローン] 残高（万円）
    private String bankRest;
    // [銀行系カードローン] 年間返済額（万円）
    private String bankReturnMoney;
    // [信販・流通系クレジット] 残高（万円）
    private String circuRest;
    // [信販・流通系クレジット] 年間返済額（万円）
    private String circuReturnMoney;
    // [消費者金融系ローン] 残高（万円）
    private String payFinanceRest;
    // [消費者金融系ローン] 年間返済額（万円）
    private String payFinanceRM;
    // [他] 残高（万円）
    private String otherRest;
    // [他] 年間返済額（万円）
    private String otherReturnMoney;
    // お取引希望店
    private String hopeStoreNmae;
    // お取引希望店フラグ
    private String hopeStoreFlg;
    // 当行とのお取引
    private String bankAccount;

    public List<YamagataStatusModifyListVO> getYamagataStatusModifyList() {
        return yamagataStatusModifyList;
    }

    public void setYamagataStatusModifyList(List<YamagataStatusModifyListVO> yamagataStatusModifyList) {
        this.yamagataStatusModifyList = yamagataStatusModifyList;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAccountAppSeq() {
        return accountAppSeq;
    }

    public void setAccountAppSeq(String accountAppSeq) {
        this.accountAppSeq = accountAppSeq;
    }

    public String getLoanAppTime() {
        return loanAppTime;
    }

    public void setLoanAppTime(String loanAppTime) {
        this.loanAppTime = loanAppTime;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanFlag() {
        return loanFlag;
    }

    public void setLoanFlag(String loanFlag) {
        this.loanFlag = loanFlag;
    }

    public String getReLoadInfoFlg() {
        return reLoadInfoFlg;
    }

    public void setReLoadInfoFlg(String reLoadInfoFlg) {
        this.reLoadInfoFlg = reLoadInfoFlg;
    }

    public String getAgreeFlg() {
        return agreeFlg;
    }

    public void setAgreeFlg(String agreeFlg) {
        this.agreeFlg = agreeFlg;
    }

    public String getGetHopeDate() {
        return getHopeDate;
    }

    public void setGetHopeDate(String getHopeDate) {
        this.getHopeDate = getHopeDate;
    }

    public String getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(String limitMoney) {
        this.limitMoney = limitMoney;
    }

    public String getDriverLicenseF() {
        return driverLicenseF;
    }

    public void setDriverLicenseF(String driverLicenseF) {
        this.driverLicenseF = driverLicenseF;
    }

    public String getDriverLicenseB() {
        return driverLicenseB;
    }

    public void setDriverLicenseB(String driverLicenseB) {
        this.driverLicenseB = driverLicenseB;
    }

    public String getDairekutoMail() {
        return dairekutoMail;
    }

    public void setDairekutoMail(String dairekutoMail) {
        this.dairekutoMail = dairekutoMail;
    }

    public String getDriverLicenseSeq() {
        return driverLicenseSeq;
    }

    public void setDriverLicenseSeq(String driverLicenseSeq) {
        this.driverLicenseSeq = driverLicenseSeq;
    }

    public String getReadDriverLicenseNo() {
        return readDriverLicenseNo;
    }

    public void setReadDriverLicenseNo(String readDriverLicenseNo) {
        this.readDriverLicenseNo = readDriverLicenseNo;
    }

    public String getReadFirstName() {
        return readFirstName;
    }

    public void setReadFirstName(String readFirstName) {
        this.readFirstName = readFirstName;
    }

    public String getReadLastName() {
        return readLastName;
    }

    public void setReadLastName(String readLastName) {
        this.readLastName = readLastName;
    }

    public String getReadBirthDay() {
        return readBirthDay;
    }

    public void setReadBirthDay(String readBirthDay) {
        this.readBirthDay = readBirthDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getKanaName() {
        return kanaName;
    }

    public void setKanaName(String kanaName) {
        this.kanaName = kanaName;
    }

    public String getFirstKanaName() {
        return firstKanaName;
    }

    public void setFirstKanaName(String firstKanaName) {
        this.firstKanaName = firstKanaName;
    }

    public String getLastKanaName() {
        return lastKanaName;
    }

    public void setLastKanaName(String lastKanaName) {
        this.lastKanaName = lastKanaName;
    }

    public String getYearType() {
        return yearType;
    }

    public void setYearType(String yearType) {
        this.yearType = yearType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSexKbn() {
        return sexKbn;
    }

    public void setSexKbn(String sexKbn) {
        this.sexKbn = sexKbn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDriverLicenseFlg() {
        return driverLicenseFlg;
    }

    public void setDriverLicenseFlg(String driverLicenseFlg) {
        this.driverLicenseFlg = driverLicenseFlg;
    }

    public String getDriverLicenseNo() {
        return driverLicenseNo;
    }

    public void setDriverLicenseNo(String driverLicenseNo) {
        this.driverLicenseNo = driverLicenseNo;
    }

    public String getReadPostCode() {
        return readPostCode;
    }

    public void setReadPostCode(String readPostCode) {
        this.readPostCode = readPostCode;
    }

    public String getReadPrefecturesCode() {
        return readPrefecturesCode;
    }

    public void setReadPrefecturesCode(String readPrefecturesCode) {
        this.readPrefecturesCode = readPrefecturesCode;
    }

    public String getReadAddress() {
        return readAddress;
    }

    public void setReadAddress(String readAddress) {
        this.readAddress = readAddress;
    }

    public String getReadOtherAddress() {
        return readOtherAddress;
    }

    public void setReadOtherAddress(String readOtherAddress) {
        this.readOtherAddress = readOtherAddress;
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

    public String getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress;
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

    public String getEnAddress() {
        return enAddress;
    }

    public void setEnAddress(String enAddress) {
        this.enAddress = enAddress;
    }

    public String getLiveYear() {
        return liveYear;
    }

    public void setLiveYear(String liveYear) {
        this.liveYear = liveYear;
    }

    public String getLiveMonth() {
        return liveMonth;
    }

    public void setLiveMonth(String liveMonth) {
        this.liveMonth = liveMonth;
    }

    public String getRentLoan() {
        return rentLoan;
    }

    public void setRentLoan(String rentLoan) {
        this.rentLoan = rentLoan;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getFamilyNumber() {
        return familyNumber;
    }

    public void setFamilyNumber(String familyNumber) {
        this.familyNumber = familyNumber;
    }

    public String getMasterKnowFlg() {
        return masterKnowFlg;
    }

    public void setMasterKnowFlg(String masterKnowFlg) {
        this.masterKnowFlg = masterKnowFlg;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSendFlg() {
        return sendFlg;
    }

    public void setSendFlg(String sendFlg) {
        this.sendFlg = sendFlg;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyKanaName() {
        return companyKanaName;
    }

    public void setCompanyKanaName(String companyKanaName) {
        this.companyKanaName = companyKanaName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyKanaName() {
        return familyKanaName;
    }

    public void setFamilyKanaName(String familyKanaName) {
        this.familyKanaName = familyKanaName;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public String getIncomeKanaName() {
        return incomeKanaName;
    }

    public void setIncomeKanaName(String incomeKanaName) {
        this.incomeKanaName = incomeKanaName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getOtherKanaName() {
        return otherKanaName;
    }

    public void setOtherKanaName(String otherKanaName) {
        this.otherKanaName = otherKanaName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getWorkPostCode() {
        return workPostCode;
    }

    public void setWorkPostCode(String workPostCode) {
        this.workPostCode = workPostCode;
    }

    public String getWorkPrefecturesCode() {
        return workPrefecturesCode;
    }

    public void setWorkPrefecturesCode(String workPrefecturesCode) {
        this.workPrefecturesCode = workPrefecturesCode;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getWorkOtherAddress() {
        return workOtherAddress;
    }

    public void setWorkOtherAddress(String workOtherAddress) {
        this.workOtherAddress = workOtherAddress;
    }

    public String getWorkTeleNumber() {
        return workTeleNumber;
    }

    public void setWorkTeleNumber(String workTeleNumber) {
        this.workTeleNumber = workTeleNumber;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getGetKbnFlg() {
        return getKbnFlg;
    }

    public void setGetKbnFlg(String getKbnFlg) {
        this.getKbnFlg = getKbnFlg;
    }

    public String getGetKbn() {
        return getKbn;
    }

    public void setGetKbn(String getKbn) {
        this.getKbn = getKbn;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getWorkYear() {
        return workYear;
    }

    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    public String getWorkMonth() {
        return workMonth;
    }

    public void setWorkMonth(String workMonth) {
        this.workMonth = workMonth;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkStartYearType() {
        return workStartYearType;
    }

    public void setWorkStartYearType(String workStartYearType) {
        this.workStartYearType = workStartYearType;
    }

    public String getWorkStartYear() {
        return workStartYear;
    }

    public void setWorkStartYear(String workStartYear) {
        this.workStartYear = workStartYear;
    }

    public String getWorkStartMonth() {
        return workStartMonth;
    }

    public void setWorkStartMonth(String workStartMonth) {
        this.workStartMonth = workStartMonth;
    }

    public String getWorkYearMonth() {
        return workYearMonth;
    }

    public void setWorkYearMonth(String workYearMonth) {
        this.workYearMonth = workYearMonth;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getGraduateYearMonth() {
        return graduateYearMonth;
    }

    public void setGraduateYearMonth(String graduateYearMonth) {
        this.graduateYearMonth = graduateYearMonth;
    }

    public String getGraduateYearType() {
        return graduateYearType;
    }

    public void setGraduateYearType(String graduateYearType) {
        this.graduateYearType = graduateYearType;
    }

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

    public String getGraduateMonth() {
        return graduateMonth;
    }

    public void setGraduateMonth(String graduateMonth) {
        this.graduateMonth = graduateMonth;
    }

    public String getVisitingName() {
        return visitingName;
    }

    public void setVisitingName(String visitingName) {
        this.visitingName = visitingName;
    }

    public String getVisitingKanaName() {
        return visitingKanaName;
    }

    public void setVisitingKanaName(String visitingKanaName) {
        this.visitingKanaName = visitingKanaName;
    }

    public String getVisitingDepartment() {
        return visitingDepartment;
    }

    public void setVisitingDepartment(String visitingDepartment) {
        this.visitingDepartment = visitingDepartment;
    }

    public String getVisitingPosition() {
        return visitingPosition;
    }

    public void setVisitingPosition(String visitingPosition) {
        this.visitingPosition = visitingPosition;
    }

    public String getVisitingPostCode() {
        return visitingPostCode;
    }

    public void setVisitingPostCode(String visitingPostCode) {
        this.visitingPostCode = visitingPostCode;
    }

    public String getVisitPreCode() {
        return visitPreCode;
    }

    public void setVisitPreCode(String visitPreCode) {
        this.visitPreCode = visitPreCode;
    }

    public String getVisitAddress() {
        return visitAddress;
    }

    public void setVisitAddress(String visitAddress) {
        this.visitAddress = visitAddress;
    }

    public String getVisitOtherAddress() {
        return visitOtherAddress;
    }

    public void setVisitOtherAddress(String visitOtherAddress) {
        this.visitOtherAddress = visitOtherAddress;
    }

    public String getVisitTelNumber() {
        return visitTelNumber;
    }

    public void setVisitTelNumber(String visitTelNumber) {
        this.visitTelNumber = visitTelNumber;
    }

    public String getIncomeFlg() {
        return incomeFlg;
    }

    public void setIncomeFlg(String incomeFlg) {
        this.incomeFlg = incomeFlg;
    }

    public String getIncomeYear() {
        return incomeYear;
    }

    public void setIncomeYear(String incomeYear) {
        this.incomeYear = incomeYear;
    }

    public String getIncomeFamily() {
        return incomeFamily;
    }

    public void setIncomeFamily(String incomeFamily) {
        this.incomeFamily = incomeFamily;
    }

    public String getIncomeYearPay() {
        return incomeYearPay;
    }

    public void setIncomeYearPay(String incomeYearPay) {
        this.incomeYearPay = incomeYearPay;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getWorkTypeCode() {
        return workTypeCode;
    }

    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRentLoanContract() {
        return rentLoanContract;
    }

    public void setRentLoanContract(String rentLoanContract) {
        this.rentLoanContract = rentLoanContract;
    }

    public String getComPreCode() {
        return comPreCode;
    }

    public void setComPreCode(String comPreCode) {
        this.comPreCode = comPreCode;
    }

    public String getHealthType() {
        return healthType;
    }

    public void setHealthType(String healthType) {
        this.healthType = healthType;
    }

    public String getApplicationMoney() {
        return applicationMoney;
    }

    public void setApplicationMoney(String applicationMoney) {
        this.applicationMoney = applicationMoney;
    }

    public String getGetHopeYearType() {
        return getHopeYearType;
    }

    public void setGetHopeYearType(String getHopeYearType) {
        this.getHopeYearType = getHopeYearType;
    }

    public String getGetHopeMonth() {
        return getHopeMonth;
    }

    public void setGetHopeMonth(String getHopeMonth) {
        this.getHopeMonth = getHopeMonth;
    }

    public String getGetHopeDay() {
        return getHopeDay;
    }

    public void setGetHopeDay(String getHopeDay) {
        this.getHopeDay = getHopeDay;
    }

    public String getReturnHopeMonth() {
        return returnHopeMonth;
    }

    public void setReturnHopeMonth(String returnHopeMonth) {
        this.returnHopeMonth = returnHopeMonth;
    }

    public String getReturnHopeCount() {
        return returnHopeCount;
    }

    public void setReturnHopeCount(String returnHopeCount) {
        this.returnHopeCount = returnHopeCount;
    }

    public String getInCount() {
        return inCount;
    }

    public void setInCount(String inCount) {
        this.inCount = inCount;
    }

    public String getPurposeFlg() {
        return purposeFlg;
    }

    public void setPurposeFlg(String purposeFlg) {
        this.purposeFlg = purposeFlg;
    }

    public String getOtherPurpose() {
        return otherPurpose;
    }

    public void setOtherPurpose(String otherPurpose) {
        this.otherPurpose = otherPurpose;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(String moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public String getPayMoney1() {
        return payMoney1;
    }

    public void setPayMoney1(String payMoney1) {
        this.payMoney1 = payMoney1;
    }

    public String getMoney1() {
        return money1;
    }

    public void setMoney1(String money1) {
        this.money1 = money1;
    }

    public String getPayMoney2() {
        return payMoney2;
    }

    public void setPayMoney2(String payMoney2) {
        this.payMoney2 = payMoney2;
    }

    public String getMoney2() {
        return money2;
    }

    public void setMoney2(String money2) {
        this.money2 = money2;
    }

    public String getOwnAccountKbn() {
        return ownAccountKbn;
    }

    public void setOwnAccountKbn(String ownAccountKbn) {
        this.ownAccountKbn = ownAccountKbn;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNameFlg() {
        return storeNameFlg;
    }

    public void setStoreNameFlg(String storeNameFlg) {
        this.storeNameFlg = storeNameFlg;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIncreaseFlg() {
        return increaseFlg;
    }

    public void setIncreaseFlg(String increaseFlg) {
        this.increaseFlg = increaseFlg;
    }

    public String getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(String returnDay) {
        this.returnDay = returnDay;
    }

    public String getReturnStartDay() {
        return returnStartDay;
    }

    public void setReturnStartDay(String returnStartDay) {
        this.returnStartDay = returnStartDay;
    }

    public String getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(String returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getIncreaseReturn1() {
        return increaseReturn1;
    }

    public void setIncreaseReturn1(String increaseReturn1) {
        this.increaseReturn1 = increaseReturn1;
    }

    public String getIncreaseReturn2() {
        return increaseReturn2;
    }

    public void setIncreaseReturn2(String increaseReturn2) {
        this.increaseReturn2 = increaseReturn2;
    }

    public String getReturnStartDay2() {
        return returnStartDay2;
    }

    public void setReturnStartDay2(String returnStartDay2) {
        this.returnStartDay2 = returnStartDay2;
    }

    public String getReturnMoney2() {
        return returnMoney2;
    }

    public void setReturnMoney2(String returnMoney2) {
        this.returnMoney2 = returnMoney2;
    }

    public String getGetFlg() {
        return getFlg;
    }

    public void setGetFlg(String getFlg) {
        this.getFlg = getFlg;
    }

    public String getGetCount() {
        return getCount;
    }

    public void setGetCount(String getCount) {
        this.getCount = getCount;
    }

    public String getGetMoney() {
        return getMoney;
    }

    public void setGetMoney(String getMoney) {
        this.getMoney = getMoney;
    }

    public String getGetFromOtherFlg() {
        return getFromOtherFlg;
    }

    public void setGetFromOtherFlg(String getFromOtherFlg) {
        this.getFromOtherFlg = getFromOtherFlg;
    }

    public String getNoLoanRest() {
        return noLoanRest;
    }

    public void setNoLoanRest(String noLoanRest) {
        this.noLoanRest = noLoanRest;
    }

    public String getNoLoanReturnMoney() {
        return noLoanReturnMoney;
    }

    public void setNoLoanReturnMoney(String noLoanReturnMoney) {
        this.noLoanReturnMoney = noLoanReturnMoney;
    }

    public String getLoanRest() {
        return loanRest;
    }

    public void setLoanRest(String loanRest) {
        this.loanRest = loanRest;
    }

    public String getLoanReturnMoney() {
        return loanReturnMoney;
    }

    public void setLoanReturnMoney(String loanReturnMoney) {
        this.loanReturnMoney = loanReturnMoney;
    }

    public String getCardLoanRest() {
        return cardLoanRest;
    }

    public void setCardLoanRest(String cardLoanRest) {
        this.cardLoanRest = cardLoanRest;
    }

    public String getCardLoanRestRM() {
        return cardLoanRestRM;
    }

    public void setCardLoanRestRM(String cardLoanRestRM) {
        this.cardLoanRestRM = cardLoanRestRM;
    }

    public String getOtherComRest() {
        return otherComRest;
    }

    public void setOtherComRest(String otherComRest) {
        this.otherComRest = otherComRest;
    }

    public String getOtherComReturnMoney() {
        return otherComReturnMoney;
    }

    public void setOtherComReturnMoney(String otherComReturnMoney) {
        this.otherComReturnMoney = otherComReturnMoney;
    }

    public String getOtherFinanceRest() {
        return otherFinanceRest;
    }

    public void setOtherFinanceRest(String otherFinanceRest) {
        this.otherFinanceRest = otherFinanceRest;
    }

    public String getOtherFinanceRM() {
        return otherFinanceRM;
    }

    public void setOtherFinanceRM(String otherFinanceRM) {
        this.otherFinanceRM = otherFinanceRM;
    }

    public String getBankRest() {
        return bankRest;
    }

    public void setBankRest(String bankRest) {
        this.bankRest = bankRest;
    }

    public String getBankReturnMoney() {
        return bankReturnMoney;
    }

    public void setBankReturnMoney(String bankReturnMoney) {
        this.bankReturnMoney = bankReturnMoney;
    }

    public String getCircuRest() {
        return circuRest;
    }

    public void setCircuRest(String circuRest) {
        this.circuRest = circuRest;
    }

    public String getCircuReturnMoney() {
        return circuReturnMoney;
    }

    public void setCircuReturnMoney(String circuReturnMoney) {
        this.circuReturnMoney = circuReturnMoney;
    }

    public String getPayFinanceRest() {
        return payFinanceRest;
    }

    public void setPayFinanceRest(String payFinanceRest) {
        this.payFinanceRest = payFinanceRest;
    }

    public String getPayFinanceRM() {
        return payFinanceRM;
    }

    public void setPayFinanceRM(String payFinanceRM) {
        this.payFinanceRM = payFinanceRM;
    }

    public String getOtherRest() {
        return otherRest;
    }

    public void setOtherRest(String otherRest) {
        this.otherRest = otherRest;
    }

    public String getOtherReturnMoney() {
        return otherReturnMoney;
    }

    public void setOtherReturnMoney(String otherReturnMoney) {
        this.otherReturnMoney = otherReturnMoney;
    }

    public String getHopeStoreNmae() {
        return hopeStoreNmae;
    }

    public void setHopeStoreNmae(String hopeStoreNmae) {
        this.hopeStoreNmae = hopeStoreNmae;
    }

    public String getHopeStoreFlg() {
        return hopeStoreFlg;
    }

    public void setHopeStoreFlg(String hopeStoreFlg) {
        this.hopeStoreFlg = hopeStoreFlg;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

}
