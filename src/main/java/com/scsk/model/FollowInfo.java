package com.scsk.model;

import com.scsk.constants.Constants;

/**
 * ユーザーDOC
 */
public class FollowInfo extends UtilDoc {

	// ドキュメントタイプ
	private String docType = Constants.FOLLOWINFO_DOCTYPE;
	// ユーザーID
	private String userId = "";
	// トピックID
	private String topicId = "";
	// トピック名
	private String topicTitle = "";
	// フォローID
	private String followId = "";
	// 表示順
	private int sort = 0;

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}