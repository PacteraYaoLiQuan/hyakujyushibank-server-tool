package com.scsk.model;

import java.util.ArrayList;
import java.util.List;

import com.scsk.model.geo.CardNoDoc;
import com.scsk.model.geo.DeviceInfoDoc;
import com.scsk.model.geo.ThemeDoc;

/**
 * ユーザー情報DOC
 */
public class IYoUserInfoDoc extends UtilDoc {

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
    // 満年齢
    private String age = "";
    // 職業
    private List<String> occupation = new ArrayList<String>();
    // その他職業
    private String otherOccupations = "";
    // パスワード
    private String password = "";
    // 携帯電話番号
    private String phoneNumber = "";
    // 自宅電話番号
    private String teleNumber = "";
    // 勤務先名
    private String workName = "";
    // 勤務先電話番号
    private String workTeleNumber = "";
    // 生年月日
    private String birthday = "";
    // 性别
    private int sex = 0;
    // 郵便番号
    private String postCode = "";
    // 都
    private String address1 = "";
    // 道府
    private String address2 = "";
    // 県
    private String address3 = "";
    // 市区町村以下
    private String address4 = "";
    // 市区町村以下（カナ）
    private String kanaAddress = "";
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
    private String changPasswordFlg = "0";
    // 入金、出金(1:入金,2:出金,3:入金と出金)
    private String amountFlg;

    public String getAmountFlg() {
        return amountFlg;
    }

    public void setAmountFlg(String amountFlg) {
        this.amountFlg = amountFlg;
    }

    public String getChangPasswordFlg() {
        return changPasswordFlg;
    }

    public void setChangPasswordFlg(String changPasswordFlg) {
        this.changPasswordFlg = changPasswordFlg;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getKanaAddress() {
        return kanaAddress;
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

    public String getDocType() {
        return docType;
    }
}