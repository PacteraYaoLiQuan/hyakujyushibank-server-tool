package com.scsk.vo;

import java.util.List;

public class UseUserDownLoadInitVO {
	// id
	private String _id;
	// 選択
	private Boolean select;
	// ErrMessage
	private Boolean errMessage;
	// ドキュメントタイプ
	private String docType;
	// ユーザーID
	private String userId;
	// トピックID
	private String topicId;
	// トピック名
	private String topicTitle;
	// フォローID
	private String followId;
	// 表示順
	private int sort;
	// 記事ID
	private String clipId;
	// 記事タイトル
	private String title;
	// 記事公開日時
	private String publishedAt;
	// 記事に含まれる画像URLの配列
	private List<String> imageUrls;
	// ソースID
	private int sourceId;
	// ソース名
	private String sourceName;
	// 記事の見出し／概要
	private String description;
	// 記事のURL
	private String url;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}

	public Boolean getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(Boolean errMessage) {
		this.errMessage = errMessage;
	}

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

	public String getClipId() {
		return clipId;
	}

	public void setClipId(String clipId) {
		this.clipId = clipId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
