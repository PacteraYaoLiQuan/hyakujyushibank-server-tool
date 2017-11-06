package com.scsk.vo;

import java.util.List;

public class PushMessageInitVO {
    // ID
    private String _id;
    // バージョン
    private String _rev;
    // 選択
    private Boolean select;
    private Boolean pushAllFlag;
    private Boolean pushOthreFlag;

    public Boolean getPushAllFlag() {
        return pushAllFlag;
    }

    public void setPushAllFlag(Boolean pushAllFlag) {
        this.pushAllFlag = pushAllFlag;
    }

    public Boolean getPushOthreFlag() {
        return pushOthreFlag;
    }

    public void setPushOthreFlag(Boolean pushOthreFlag) {
        this.pushOthreFlag = pushOthreFlag;
    }

    // ドキュメントタイプ
    private String docType;
    // ModeType
    private String modeType;
    // accountNumber
    private List<String> arrNoList;
    // ErrMessage
    private Boolean errMessage;
    // 配信日時
    private String pushDate;
    // プッシュ内容タイトル
    private String pushTitle;
    // メッセージ
    private String pushMessage;
    // 配信日時並び用
    private String pushDateForSort;
    // プッシュ件数
    private int pushCnt;
    // プッシュ成功件数
    private int pushSuccessCnt;
    // プッシュ失敗件数
    private int pushFaileCnt;
    // PUSH配信操作者
    private String pushAccessUser;
    // iOS／Android区分
    private String osKBN;
    // PUSH区分
    private String pushKBN;
    // 送達区分
    private String arriveKBN;
    // 開封区分
    private String openKBN;
    // プッシュ詳細OID
    private String pushDetailOid;
    // 配信ステータス
    private String pushStatus;
    // 送達＆開封数（Android）
    private int andOpenCnt;
    // 送達＆未開封数（Android）
    private int andCloseCnt;
    // 送達＆開封数（iOS）
    private int iosOpenCnt;
    // 送達＆未開封数（iOS）
    private int iosCloseCnt;
    // 任意配信件名OID
    private String pushTitlelOid;
    // 作成者
    private String createdBy;
    // 作成日時
    private String createdDate;
    // 更新者
    private String updatedBy;
    // 更新日時
    private String updatedDate;
    // １：全体配信／２：選択配信／３：CSV配信
    private String pushType;

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
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

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public List<String> getArrNoList() {
        return arrNoList;
    }

    public void setArrNoList(List<String> arrNoList) {
        this.arrNoList = arrNoList;
    }

    public Boolean getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(Boolean errMessage) {
        this.errMessage = errMessage;
    }

    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
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

    public String getPushDateForSort() {
        return pushDateForSort;
    }

    public void setPushDateForSort(String pushDateForSort) {
        this.pushDateForSort = pushDateForSort;
    }

    public int getPushCnt() {
        return pushCnt;
    }

    public void setPushCnt(int pushCnt) {
        this.pushCnt = pushCnt;
    }

    public int getPushSuccessCnt() {
        return pushSuccessCnt;
    }

    public void setPushSuccessCnt(int pushSuccessCnt) {
        this.pushSuccessCnt = pushSuccessCnt;
    }

    public int getPushFaileCnt() {
        return pushFaileCnt;
    }

    public void setPushFaileCnt(int pushFaileCnt) {
        this.pushFaileCnt = pushFaileCnt;
    }

    public String getPushAccessUser() {
        return pushAccessUser;
    }

    public void setPushAccessUser(String pushAccessUser) {
        this.pushAccessUser = pushAccessUser;
    }

    public String getOsKBN() {
        return osKBN;
    }

    public void setOsKBN(String osKBN) {
        this.osKBN = osKBN;
    }

    public String getPushKBN() {
        return pushKBN;
    }

    public void setPushKBN(String pushKBN) {
        this.pushKBN = pushKBN;
    }

    public String getArriveKBN() {
        return arriveKBN;
    }

    public void setArriveKBN(String arriveKBN) {
        this.arriveKBN = arriveKBN;
    }

    public String getOpenKBN() {
        return openKBN;
    }

    public void setOpenKBN(String openKBN) {
        this.openKBN = openKBN;
    }

    public String getPushDetailOid() {
        return pushDetailOid;
    }

    public void setPushDetailOid(String pushDetailOid) {
        this.pushDetailOid = pushDetailOid;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public int getAndOpenCnt() {
        return andOpenCnt;
    }

    public void setAndOpenCnt(int andOpenCnt) {
        this.andOpenCnt = andOpenCnt;
    }

    public int getAndCloseCnt() {
        return andCloseCnt;
    }

    public void setAndCloseCnt(int andCloseCnt) {
        this.andCloseCnt = andCloseCnt;
    }

    public int getIosOpenCnt() {
        return iosOpenCnt;
    }

    public void setIosOpenCnt(int iosOpenCnt) {
        this.iosOpenCnt = iosOpenCnt;
    }

    public int getIosCloseCnt() {
        return iosCloseCnt;
    }

    public void setIosCloseCnt(int iosCloseCnt) {
        this.iosCloseCnt = iosCloseCnt;
    }

    public String getPushTitlelOid() {
        return pushTitlelOid;
    }

    public void setPushTitlelOid(String pushTitlelOid) {
        this.pushTitlelOid = pushTitlelOid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
