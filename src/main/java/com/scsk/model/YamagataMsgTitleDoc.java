package com.scsk.model;

import com.scsk.constants.Constants;

public class YamagataMsgTitleDoc extends UtilDoc {
	// ドキュメントタイプ
	private String docType = Constants.MSGTITLE_DOCTYPE;
	// 配信日時
	private String pushDate = "";
	// 配信日時並び用
	private Long pushDateForSort = 0L;
	// プッシュ件数
	private int pushCnt = 0;
	// プッシュ成功件数
	private int pushSuccessCnt = 0;
	// プッシュ失敗件数
	private int pushFaileCnt = 0;
	// プッシュ内容タイトル
	private String pushTitle = "";
	// プッシュ詳細OID
	private String pushDetailOid = "";
	// PUSH配信操作者
	private String pushAccessUser = "";
	//１：全体配信／２：選択配信／３：CSV配信
    private String pushType = "";
	public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getPushDate() {
		return pushDate;
	}

	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}

	public Long getPushDateForSort() {
		return pushDateForSort;
	}

	public void setPushDateForSort(Long pushDateForSort) {
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

	public String getPushTitle() {
		return pushTitle;
	}

	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}

	public String getPushDetailOid() {
		return pushDetailOid;
	}

	public void setPushDetailOid(String pushDetailOid) {
		this.pushDetailOid = pushDetailOid;
	}

	public String getPushAccessUser() {
		return pushAccessUser;
	}

	public void setPushAccessUser(String pushAccessUser) {
		this.pushAccessUser = pushAccessUser;
	}
}
