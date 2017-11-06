package com.scsk.model;

import java.util.ArrayList;
import java.util.List;

import com.scsk.constants.Constants;

/**
 * ユーザーDOC
 */
public class ClipInfo extends UtilDoc {

	// ドキュメントタイプ
	private String docType = Constants.CLIPINFO_DOCTYPE;
	// ユーザーID
	private String userId = "";
	// 記事ID
	private String clipId = "";
	// 記事タイトル
	private String title = "";
	// 記事公開日時
	private String publishedAt = "";
	// 記事に含まれる画像URLの配列
	private List<String> imageUrls = new ArrayList<String>();
	// ソースID
	private int sourceId = 0;
	// ソース名
	private String sourceName = "";
	// 記事の見出し／概要
	private String description = "";
	// 記事のURL
	private String url = "";

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