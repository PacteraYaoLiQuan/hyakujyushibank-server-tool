package com.scsk.model;

import java.util.ArrayList;
import java.util.List;

import com.scsk.constants.Constants;
import com.scsk.model.geo.DeviceInfoDoc;

/**
 * PUSH明細連携DOC
 * 
 */
public class PushRirekiTorihikidDataDoc extends UtilDoc {
    // ドキュメントタイプ
    private final String docType = Constants.PUSH_RIREKI_TORIHIKIDATA;
    // 店舗番号
    private String tennpoBango = "";
    // 科目
    private String kamokuCode = "";
    // 口座番号
    private String kouzaBango = "";
    // Pushメッセージのタイトル（管理用）
    private String pushTitle = "";
    // Push通知表示メッセージ
    private String pushMessage = "";
    // 送信開始日時（未指定の場合は現在日時）
    private String pushDatetime = "";
    // 取引発生日
    private String torihikiDate = "";
    // 取引発生時刻
    private String torihikiTime = "";
    // 取引区分（入金│出金）
    private String torihikiKbn = "";
    // 取引詳細（現金│振替│振込）
    private String torihikiSyosai = "";
    // 取引金額
    private String torihikiKingaku = "";
    // ユーザーID
    private String ueserID = "";
    // 端末ID
    private List<DeviceInfoDoc> deviceTokenId = new ArrayList<>();
    // バッチPUSH配信日時
    private String batchPushDatetime = "";
    // Push
    private String pushBn = "";

    public String getPushBn() {
        return pushBn;
    }

    public void setPushBn(String pushBn) {
        this.pushBn = pushBn;
    }

    public String getBatchPushDatetime() {
        return batchPushDatetime;
    }

    public void setBatchPushDatetime(String batchPushDatetime) {
        this.batchPushDatetime = batchPushDatetime;
    }

    public String getTennpoBango() {
        return tennpoBango;
    }

    public String getDocType() {
        return docType;
    }

    public void setTennpoBango(String tennpoBango) {
        this.tennpoBango = tennpoBango;
    }

    public String getKamokuCode() {
        return kamokuCode;
    }

    public void setKamokuCode(String kamokuCode) {
        this.kamokuCode = kamokuCode;
    }

    public String getKouzaBango() {
        return kouzaBango;
    }

    public void setKouzaBango(String kouzaBango) {
        this.kouzaBango = kouzaBango;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public String getPushMessage() {
        return pushMessage;
    }

    public void setPushMessage(String pushMessage) {
        this.pushMessage = pushMessage;
    }

    public String getPushDatetime() {
        return pushDatetime;
    }

    public void setPushDatetime(String pushDatetime) {
        this.pushDatetime = pushDatetime;
    }

    public String getTorihikiDate() {
        return torihikiDate;
    }

    public void setTorihikiDate(String torihikiDate) {
        this.torihikiDate = torihikiDate;
    }

    public String getTorihikiTime() {
        return torihikiTime;
    }

    public void setTorihikiTime(String torihikiTime) {
        this.torihikiTime = torihikiTime;
    }

    public String getTorihikiKbn() {
        return torihikiKbn;
    }

    public void setTorihikiKbn(String torihikiKbn) {
        this.torihikiKbn = torihikiKbn;
    }

    public String getTorihikiSyosai() {
        return torihikiSyosai;
    }

    public void setTorihikiSyosai(String torihikiSyosai) {
        this.torihikiSyosai = torihikiSyosai;
    }

    public String getTorihikiKingaku() {
        return torihikiKingaku;
    }

    public void setTorihikiKingaku(String torihikiKingaku) {
        this.torihikiKingaku = torihikiKingaku;
    }

    public String getUeserID() {
        return ueserID;
    }

    public void setUeserID(String ueserID) {
        this.ueserID = ueserID;
    }

    public List<DeviceInfoDoc> getDeviceTokenId() {
        return deviceTokenId;
    }

    public void setDeviceTokenId(List<DeviceInfoDoc> deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }
}