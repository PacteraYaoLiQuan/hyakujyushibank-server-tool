package com.scsk.model;

import java.util.ArrayList;
import java.util.List;

import com.scsk.model.geo.CardNoDoc;
import com.scsk.model.geo.DeviceInfoDoc;
import com.scsk.model.geo.ThemeDoc;

public class HyakujyushiUserInfoDoc extends UtilDoc{

    // ドキュメントタイプ
    private final String docType = DocTypeEnum.USERINFO.name();
    // ユーザータイプ
    private String userType = "";
    // ユーザキー
    private String userKey = "";
    // E-mail
    private String email = "";
    // 姓
    private String lastName = "";
    // 名
    private String firstName = "";
    // 姓カナ
    private String kanaLastName = "";
    // 名カナ
    private String kanaFirstName = "";
    // 生年月日
    private String birthday = "";
    // 性别
    private String sexKbn = "";
    // 満年齢
    private String age = "";
    // 郵便番号
    private String postCode = "";
    // 都道府県
    private String prefecturesCode = "";
    // 市区町村以下
    private String address = "";
    // 市区町村以下（カナ）
    private String kanaAddress = "";
    // 自宅電話番号
    private String teleNumber = "";
    // 携帯電話番号
    private String phoneNumber = "";
    // 職業
    private String jobKbn = "";
    // その他職業
    private String jobKbnOther = "";
    // 勤務先名
    private String workName = "";
    // 勤務先名（カナ）
    private String kanaWorkName = "";
    // 勤務先郵便番号
    private String workPostCode = "";
    // 勤務先都道府県
    private String workPrefecturesCode = "";
    // 勤務先市区町村以下
    private String workAddress = "";
    // 勤務先電話番号
    private String workTeleNumber = "";
    // 勤務先の電話番号区分
    private String workNumberKbn = "";
    // パスワード
    private String password = "";
    // 口座情報
    private List<CardNoDoc> cardNoList = new ArrayList<>();
    // 興味あるテーマ
    private List<ThemeDoc> themeList = new ArrayList<>();
    // FacebookID
    private String facebookId = "";
    // Facebookユーザ名
    private String facebookName = "";
    // Facebookメールアドレス
    private String facebookEmail = "";
    // Facebook性別
    private int facebookSex = 0;
    // Facebook生年月日
    private String facebookBirthday = "";
    // Facebook電話番号
    private String facebookPhoneNumber = "";
    // Facebook住所
    private String facebookAddress = "";
    // yahoo start
    // YahooID
    private String yahooId = "";
    // Yahooメールアドレス
    private String yahooEmail = "";
    // googleID
    private String googleId = "";
    // googleメールアドレス
    private String googleEmail = "";
    // Google end
    // デバイス情報リスト
    private List<DeviceInfoDoc> deviceInfoList = new ArrayList<>();
    // ユーザ生成日時
    private String userCreatDate = "";
    // 利用規約同意日時
    private String agreeDate = "";
    // 最終リクエスト日時
    private String lastReqTime = "";
    // タブ設定
    private String tabSetting = "";
    // メニュー設定
    private List<String> menuSetting = new ArrayList<String>();
    // 通知設定
    private String localSetting = "";
    // 通知設定（区分）
    private String noticeFlag = "";
    // 通知設定（金額）
    private String noticeAmt = "";
    // 位置情報サービス設定
    private String gpsSetting = "";
    // 入金、出金
    private String amountFlg;
    //
    private String changPasswordFlg = "0";

    public String getSexKbn() {
        return sexKbn;
    }

    public void setSexKbn(String sexKbn) {
        this.sexKbn = sexKbn;
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


    public String getJobKbn() {
        return jobKbn;
    }

    public void setJobKbn(String jobKbn) {
        this.jobKbn = jobKbn;
    }

    public String getJobKbnOther() {
        return jobKbnOther;
    }

    public void setJobKbnOther(String jobKbnOther) {
        this.jobKbnOther = jobKbnOther;
    }

    public String getKanaWorkName() {
        return kanaWorkName;
    }

    public void setKanaWorkName(String kanaWorkName) {
        this.kanaWorkName = kanaWorkName;
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

    public String getWorkNumberKbn() {
        return workNumberKbn;
    }

    public void setWorkNumberKbn(String workNumberKbn) {
        this.workNumberKbn = workNumberKbn;
    }

    public String getKanaAddress() {
        return kanaAddress;
    }

    public String getAmountFlg() {
        return amountFlg;
    }

    public void setAmountFlg(String amountFlg) {
        this.amountFlg = amountFlg;
    }

    public String getYahooEmail() {
        return yahooEmail;
    }

    public void setYahooEmail(String yahooEmail) {
        this.yahooEmail = yahooEmail;
    }

    public String getGoogleEmail() {
        return googleEmail;
    }

    public void setGoogleEmail(String googleEmail) {
        this.googleEmail = googleEmail;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkTeleNumber() {
        return workTeleNumber;
    }

    public void setWorkTeleNumber(String workTeleNumber) {
        this.workTeleNumber = workTeleNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setKanaAddress(String kanaAddress) {
        this.kanaAddress = kanaAddress;
    }

    public List<CardNoDoc> getCardNoList() {
        return cardNoList;
    }

    public void setCardNoList(List<CardNoDoc> cardNoList) {
        this.cardNoList = cardNoList;
    }

    public List<ThemeDoc> getThemeList() {
        return themeList;
    }

    public void setThemeList(List<ThemeDoc> themeList) {
        this.themeList = themeList;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFacebookName() {
        return facebookName;
    }

    public void setFacebookName(String facebookName) {
        this.facebookName = facebookName;
    }

    public String getFacebookEmail() {
        return facebookEmail;
    }

    public void setFacebookEmail(String facebookEmail) {
        this.facebookEmail = facebookEmail;
    }

    public int getFacebookSex() {
        return facebookSex;
    }

    public void setFacebookSex(int facebookSex) {
        this.facebookSex = facebookSex;
    }

    public String getFacebookBirthday() {
        return facebookBirthday;
    }

    public void setFacebookBirthday(String facebookBirthday) {
        this.facebookBirthday = facebookBirthday;
    }

    public String getFacebookPhoneNumber() {
        return facebookPhoneNumber;
    }

    public void setFacebookPhoneNumber(String facebookPhoneNumber) {
        this.facebookPhoneNumber = facebookPhoneNumber;
    }

    public String getFacebookAddress() {
        return facebookAddress;
    }

    public void setFacebookAddress(String facebookAddress) {
        this.facebookAddress = facebookAddress;
    }

    public String getYahooId() {
        return yahooId;
    }

    public void setYahooId(String yahooId) {
        this.yahooId = yahooId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public List<DeviceInfoDoc> getDeviceInfoList() {
        return deviceInfoList;
    }

    public void setDeviceInfoList(List<DeviceInfoDoc> deviceInfoList) {
        this.deviceInfoList = deviceInfoList;
    }

    public String getUserCreatDate() {
        return userCreatDate;
    }

    public void setUserCreatDate(String userCreatDate) {
        this.userCreatDate = userCreatDate;
    }

    public String getAgreeDate() {
        return agreeDate;
    }

    public void setAgreeDate(String agreeDate) {
        this.agreeDate = agreeDate;
    }

    public String getLastReqTime() {
        return lastReqTime;
    }

    public void setLastReqTime(String lastReqTime) {
        this.lastReqTime = lastReqTime;
    }

    public String getTabSetting() {
        return tabSetting;
    }

    public void setTabSetting(String tabSetting) {
        this.tabSetting = tabSetting;
    }

    public List<String> getMenuSetting() {
        return menuSetting;
    }

    public void setMenuSetting(List<String> menuSetting) {
        this.menuSetting = menuSetting;
    }

    public String getLocalSetting() {
        return localSetting;
    }

    public void setLocalSetting(String localSetting) {
        this.localSetting = localSetting;
    }

    public String getNoticeFlag() {
        return noticeFlag;
    }

    public void setNoticeFlag(String noticeFlag) {
        this.noticeFlag = noticeFlag;
    }

    public String getNoticeAmt() {
        return noticeAmt;
    }

    public void setNoticeAmt(String noticeAmt) {
        this.noticeAmt = noticeAmt;
    }

    public String getGpsSetting() {
        return gpsSetting;
    }

    public void setGpsSetting(String gpsSetting) {
        this.gpsSetting = gpsSetting;
    }

    public String getChangPasswordFlg() {
        return changPasswordFlg;
    }

    public void setChangPasswordFlg(String changPasswordFlg) {
        this.changPasswordFlg = changPasswordFlg;
    }

    public String getDocType() {
        return docType;
    }

}
