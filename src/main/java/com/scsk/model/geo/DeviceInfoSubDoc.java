package com.scsk.model.geo;

public class DeviceInfoSubDoc {
	// ユーザーID
	private String userId = "";
    // 配信用端末ID
    private String deviceTokenId = null;
    // デバイスモデル名
    private String deviceModelName = null;
    // OSバージョン
    private String deviceOSVer = null;
    // 伊予ｱﾌﾟﾘバージョン
    private String appVer = null;
    
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    public String getDeviceTokenId() {
        return deviceTokenId;
    }

    public void setDeviceTokenId(String deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }

    public String getDeviceModelName() {
        return deviceModelName;
    }

    public void setDeviceModelName(String deviceModelName) {
        this.deviceModelName = deviceModelName;
    }

    public String getDeviceOSVer() {
        return deviceOSVer;
    }

    public void setDeviceOSVer(String deviceOSVer) {
        this.deviceOSVer = deviceOSVer;
	}

	public String getAppVer() {
		return appVer;
	}

	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}
}
