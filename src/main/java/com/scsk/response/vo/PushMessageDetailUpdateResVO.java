package com.scsk.response.vo;

public class PushMessageDetailUpdateResVO {
	// id
	private String _id;
	// modeType
	private String modeType;
	// プッシュ内容タイトル
	private String pushTitle;
	// pushMessage
	private String pushMessage;
	// メッセージ（HTML）
	private String pushMessageHTML;
	// 配信日時
	private String pushDate;
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
	// 作成者
	private String createdBy;
	// 作成日時
	private String createdDate;
	// 更新者
	private String updatedBy;
	// 更新日時
	private String updatedDate;
	// 削除フラグ
	private String delFlg;
	private String outPath;
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
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

	public String getPushMessageHTML() {
		return pushMessageHTML;
	}

	public void setPushMessageHTML(String pushMessageHTML) {
		this.pushMessageHTML = pushMessageHTML;
	}

	public String getPushDate() {
		return pushDate;
	}

	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
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

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
}
