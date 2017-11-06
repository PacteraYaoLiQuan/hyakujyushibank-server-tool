package com.scsk.response.vo;

public class AccountAppDetailStatusResVO extends BaseResVO {

	// id
	private String _id;

	// ステータス
	private String status;

	// Push開封状況
	private String pushStatus;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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

}
