package com.scsk.model;

import com.scsk.constants.Constants;

/**
 * ユーザー情報DOC
 */
public class YamagataUserInfoDoc extends UtilDoc {

    //
    private final String docType = Constants.YAMAGATA_USERINFO;
    // デバイスID
    private String deviceId = "";
    // モデル名
    private String modelName = "";
    // OSバージョン
    private String osVersion = "";
    // APPバージョン
    private String appVersion = "";
    // ユーザ生成日時
    private String userNewDate = "";
    // 利用規約同意日時
    private String launchAgreeDate = "";
    // 最終リクエスト日時
    private String finalRequestDate = "";

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDocType() {
        return docType;
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

    public String getUserNewDate() {
        return userNewDate;
    }

    public void setUserNewDate(String userNewDate) {
        this.userNewDate = userNewDate;
    }

    public String getLaunchAgreeDate() {
        return launchAgreeDate;
    }

    public void setLaunchAgreeDate(String launchAgreeDate) {
        this.launchAgreeDate = launchAgreeDate;
    }

    public String getFinalRequestDate() {
        return finalRequestDate;
    }

    public void setFinalRequestDate(String finalRequestDate) {
        this.finalRequestDate = finalRequestDate;
    }
}