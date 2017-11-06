package com.scsk.vo;

public class AccountLoanInitVO {
    // ID
    private String _id;
    // 選択
    private Boolean select;
    // ユーザーID
    private String userId;
    // 受付番号
    private String accountAppSeq;
    // 受付日時
    private String receiptDate;
    // ローン種類
    private String loanType;
    // ステイタス
    private String status;
    // 氏名
    private String name;
    // PUSH開封状況
    private String pushStatus;
    // 同意フラグ
    private String agreeFlg;
    // ダイレクトメール
    private String dairekutoMail;
    // 運転免許証裏面
    private String driverLicenseB;
    // 読取り名前の名(読取結果確認)
    private String readFirstName;
    // 読取り生年月日
    private String readBirthDay;
    // お名前_漢字の姓
    private String lastName;
    // お名前_フリガナの姓
    private String lastKanaName;
    // 生年月日_年
    private String year;
    // 生年月日_日
    private String day;
    // 性別
    private String sexKbn;
    // 運転免許番号フラグ
    private String driverLicenseFlg;
    // 読取り郵便番号
    private String readPostCode;
    // 読取り住所
    private String readAddress;
    // 郵便番号
    private String postCode;
    // ご自宅住所_住所
    private String address;
    // お電話番号_自宅
    private String teleNumber;
    // お住まい
    private String enAddress;
    // 居住年数_年
    private String liveYear;
    // 家賃（住宅ローン）_毎月（円）
    private String rentLoan;
    // ご家族_居住形態
    private String liveType;
    // 承知フラグ
    private String masterKnowFlg;
    // お勤め先名_漢字
    private String companyName;
    // お勤め先名（屋号）_漢字
    private String familyName;
    // お勤め先名（屋号）（年金種類）_漢字
    private String incomeName;
    // お勤め先名（屋号）_漢字
    private String otherName;
    // 業種
    private String jobType;
    // お勤め先所在地_都道府県
    private String workPrefecturesCode;
    // お勤め先所在地_以降住所
    private String workOtherAddress;
    // お勤め先電話番号_所属部直通
    private String workPhoneNumber;
    // 所得区分
    private String getKbn;
    // 役職
    private String position;
    // 勤続（営）年数_年
    private String workYear;
    // 所属部課名
    private String department;
    // 入社年月_年
    private String workStartYear;
    // 入社年月
    private String workYearMonth;
    // 最終学歴卒業年月
    private String graduateYearMonth;
    // 最終学歴卒業年月_年
    private String graduateYear;
    // 出向先会社名_漢字
    private String visitingName;
    // 出向先所属部課名
    private String visitingDepartment;
    // 出向先郵便番号
    private String visitingPostCode;
    // 出向先所在地_住所
    private String visitAddress;
    // 出向先電話番号
    private String visitTelNumber;
    // 年収/税込年収
    private String incomeYear;
    // 税込年収 [年金受給額]
    private String incomeYearPay;
    // 社員数
    private String workNumber;
    // お勤め先の種類
    private String workTypeCode;
    // 住宅ローン契約（当行）
    private String rentLoanContract;
    // 健康保険証の種類
    private String healthType;
    // お借入希望日_[平成] 年
    private String getHopeYearType;
    // お借入希望日_日
    private String getHopeDay;
    // 返済希望回数_回
    private String returnHopeCount;
    // ご利用目的フラグ
    private String purposeFlg;
    // ご利用目的_目的
    private String purpose;
    // ご利用目的_お支払先（ご購入先）①
    private String payMoney1;
    // ご利用目的_お支払先（ご購入先）②
    private String payMoney2;
    // 口座持ちフラグ
    private String ownAccountKbn;
    // 支店フラグ
    private String storeNameFlg;
    // 返済併用フラグ
    private String increaseFlg;
    // 返済開始日
    private String returnStartDay;
    // 返済月1
    private String increaseReturn1;
    // 返済開始日
    private String returnStartDay2;
    // 借入フラグ
    private String getFlg;
    // 万円
    private String getMoney;
    // [無担保ローン] 残高（万円）
    private String noLoanRest;
    // [住宅ローン] 残高（万円）
    private String loanRest;
    // [カードローン] 残高（万円）
    private String cardLoanRest;
    // [信販会社等その他] 残高（万円）
    private String otherComRest;
    // [他金融機関] 残高（万円）
    private String otherFinanceRest;
    // [銀行系カードローン] 残高（万円）
    private String bankRest;
    // [信販・流通系クレジット] 残高（万円）
    private String circuRest;
    // [消費者金融系ローン] 残高（万円）
    private String payFinanceRest;
    // [他] 残高（万円）
    private String otherRest;
    // お取引希望店
    private String hopeStoreNmae;
    // 当行とのお取引
    private String bankAccount;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountAppSeq() {
        return accountAppSeq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountAppSeq(String accountAppSeq) {
        this.accountAppSeq = accountAppSeq;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getAgreeFlg() {
        return agreeFlg;
    }

    public void setAgreeFlg(String agreeFlg) {
        this.agreeFlg = agreeFlg;
    }

    public String getDairekutoMail() {
        return dairekutoMail;
    }

    public void setDairekutoMail(String dairekutoMail) {
        this.dairekutoMail = dairekutoMail;
    }

    public String getDriverLicenseB() {
        return driverLicenseB;
    }

    public void setDriverLicenseB(String driverLicenseB) {
        this.driverLicenseB = driverLicenseB;
    }

    public String getReadFirstName() {
        return readFirstName;
    }

    public void setReadFirstName(String readFirstName) {
        this.readFirstName = readFirstName;
    }

    public String getReadBirthDay() {
        return readBirthDay;
    }

    public void setReadBirthDay(String readBirthDay) {
        this.readBirthDay = readBirthDay;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastKanaName() {
        return lastKanaName;
    }

    public void setLastKanaName(String lastKanaName) {
        this.lastKanaName = lastKanaName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSexKbn() {
        return sexKbn;
    }

    public void setSexKbn(String sexKbn) {
        this.sexKbn = sexKbn;
    }

    public String getDriverLicenseFlg() {
        return driverLicenseFlg;
    }

    public void setDriverLicenseFlg(String driverLicenseFlg) {
        this.driverLicenseFlg = driverLicenseFlg;
    }

    public String getReadPostCode() {
        return readPostCode;
    }

    public void setReadPostCode(String readPostCode) {
        this.readPostCode = readPostCode;
    }

    public String getReadAddress() {
        return readAddress;
    }

    public void setReadAddress(String readAddress) {
        this.readAddress = readAddress;
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

    public String getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
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

    public String getRentLoan() {
        return rentLoan;
    }

    public void setRentLoan(String rentLoan) {
        this.rentLoan = rentLoan;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getMasterKnowFlg() {
        return masterKnowFlg;
    }

    public void setMasterKnowFlg(String masterKnowFlg) {
        this.masterKnowFlg = masterKnowFlg;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getWorkPrefecturesCode() {
        return workPrefecturesCode;
    }

    public void setWorkPrefecturesCode(String workPrefecturesCode) {
        this.workPrefecturesCode = workPrefecturesCode;
    }

    public String getWorkOtherAddress() {
        return workOtherAddress;
    }

    public void setWorkOtherAddress(String workOtherAddress) {
        this.workOtherAddress = workOtherAddress;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getGetKbn() {
        return getKbn;
    }

    public void setGetKbn(String getKbn) {
        this.getKbn = getKbn;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWorkYear() {
        return workYear;
    }

    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkStartYear() {
        return workStartYear;
    }

    public void setWorkStartYear(String workStartYear) {
        this.workStartYear = workStartYear;
    }

    public String getWorkYearMonth() {
        return workYearMonth;
    }

    public void setWorkYearMonth(String workYearMonth) {
        this.workYearMonth = workYearMonth;
    }

    public String getGraduateYearMonth() {
        return graduateYearMonth;
    }

    public void setGraduateYearMonth(String graduateYearMonth) {
        this.graduateYearMonth = graduateYearMonth;
    }

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

    public String getVisitingName() {
        return visitingName;
    }

    public void setVisitingName(String visitingName) {
        this.visitingName = visitingName;
    }

    public String getVisitingDepartment() {
        return visitingDepartment;
    }

    public void setVisitingDepartment(String visitingDepartment) {
        this.visitingDepartment = visitingDepartment;
    }

    public String getVisitingPostCode() {
        return visitingPostCode;
    }

    public void setVisitingPostCode(String visitingPostCode) {
        this.visitingPostCode = visitingPostCode;
    }

    public String getVisitAddress() {
        return visitAddress;
    }

    public void setVisitAddress(String visitAddress) {
        this.visitAddress = visitAddress;
    }

    public String getVisitTelNumber() {
        return visitTelNumber;
    }

    public void setVisitTelNumber(String visitTelNumber) {
        this.visitTelNumber = visitTelNumber;
    }

    public String getIncomeYear() {
        return incomeYear;
    }

    public void setIncomeYear(String incomeYear) {
        this.incomeYear = incomeYear;
    }

    public String getIncomeYearPay() {
        return incomeYearPay;
    }

    public void setIncomeYearPay(String incomeYearPay) {
        this.incomeYearPay = incomeYearPay;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getWorkTypeCode() {
        return workTypeCode;
    }

    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode;
    }

    public String getRentLoanContract() {
        return rentLoanContract;
    }

    public void setRentLoanContract(String rentLoanContract) {
        this.rentLoanContract = rentLoanContract;
    }

    public String getHealthType() {
        return healthType;
    }

    public void setHealthType(String healthType) {
        this.healthType = healthType;
    }

    public String getGetHopeYearType() {
        return getHopeYearType;
    }

    public void setGetHopeYearType(String getHopeYearType) {
        this.getHopeYearType = getHopeYearType;
    }

    public String getGetHopeDay() {
        return getHopeDay;
    }

    public void setGetHopeDay(String getHopeDay) {
        this.getHopeDay = getHopeDay;
    }

    public String getReturnHopeCount() {
        return returnHopeCount;
    }

    public void setReturnHopeCount(String returnHopeCount) {
        this.returnHopeCount = returnHopeCount;
    }

    public String getPurposeFlg() {
        return purposeFlg;
    }

    public void setPurposeFlg(String purposeFlg) {
        this.purposeFlg = purposeFlg;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPayMoney1() {
        return payMoney1;
    }

    public void setPayMoney1(String payMoney1) {
        this.payMoney1 = payMoney1;
    }

    public String getPayMoney2() {
        return payMoney2;
    }

    public void setPayMoney2(String payMoney2) {
        this.payMoney2 = payMoney2;
    }

    public String getOwnAccountKbn() {
        return ownAccountKbn;
    }

    public void setOwnAccountKbn(String ownAccountKbn) {
        this.ownAccountKbn = ownAccountKbn;
    }

    public String getStoreNameFlg() {
        return storeNameFlg;
    }

    public void setStoreNameFlg(String storeNameFlg) {
        this.storeNameFlg = storeNameFlg;
    }

    public String getIncreaseFlg() {
        return increaseFlg;
    }

    public void setIncreaseFlg(String increaseFlg) {
        this.increaseFlg = increaseFlg;
    }

    public String getReturnStartDay() {
        return returnStartDay;
    }

    public void setReturnStartDay(String returnStartDay) {
        this.returnStartDay = returnStartDay;
    }

    public String getIncreaseReturn1() {
        return increaseReturn1;
    }

    public void setIncreaseReturn1(String increaseReturn1) {
        this.increaseReturn1 = increaseReturn1;
    }

    public String getReturnStartDay2() {
        return returnStartDay2;
    }

    public void setReturnStartDay2(String returnStartDay2) {
        this.returnStartDay2 = returnStartDay2;
    }

    public String getGetFlg() {
        return getFlg;
    }

    public void setGetFlg(String getFlg) {
        this.getFlg = getFlg;
    }

    public String getGetMoney() {
        return getMoney;
    }

    public void setGetMoney(String getMoney) {
        this.getMoney = getMoney;
    }

    public String getNoLoanRest() {
        return noLoanRest;
    }

    public void setNoLoanRest(String noLoanRest) {
        this.noLoanRest = noLoanRest;
    }

    public String getLoanRest() {
        return loanRest;
    }

    public void setLoanRest(String loanRest) {
        this.loanRest = loanRest;
    }

    public String getCardLoanRest() {
        return cardLoanRest;
    }

    public void setCardLoanRest(String cardLoanRest) {
        this.cardLoanRest = cardLoanRest;
    }

    public String getOtherComRest() {
        return otherComRest;
    }

    public void setOtherComRest(String otherComRest) {
        this.otherComRest = otherComRest;
    }

    public String getOtherFinanceRest() {
        return otherFinanceRest;
    }

    public void setOtherFinanceRest(String otherFinanceRest) {
        this.otherFinanceRest = otherFinanceRest;
    }

    public String getBankRest() {
        return bankRest;
    }

    public void setBankRest(String bankRest) {
        this.bankRest = bankRest;
    }

    public String getCircuRest() {
        return circuRest;
    }

    public void setCircuRest(String circuRest) {
        this.circuRest = circuRest;
    }

    public String getPayFinanceRest() {
        return payFinanceRest;
    }

    public void setPayFinanceRest(String payFinanceRest) {
        this.payFinanceRest = payFinanceRest;
    }

    public String getOtherRest() {
        return otherRest;
    }

    public void setOtherRest(String otherRest) {
        this.otherRest = otherRest;
    }

    public String getHopeStoreNmae() {
        return hopeStoreNmae;
    }

    public void setHopeStoreNmae(String hopeStoreNmae) {
        this.hopeStoreNmae = hopeStoreNmae;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
