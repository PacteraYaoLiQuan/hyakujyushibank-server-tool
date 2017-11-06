package com.scsk.model.geo;

public class CardNoDoc {
	// 口座名称
	private String accountName = null;
	// 店番
	private String storeNo = null;
	// 店舗名称
	private String storeName = null;
	// 店舗名(カナ)
	private String storeNameKana = null;
	// 科目
	private String kamokuCode = null;
	// 科目名
	private String kamokuName = null;
	// 口座番号
	private String accountNumber = null;
	// アクセストークン
	private String accessToken = null;
	// トークンタイプ
	private String tokenType = null;
	// アクセストークン有効期間
	private String expiresIn = null;
	// アクセストークンを再取得用
	private String refreshToken = null;
	// スコープ
	private String scope = null;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreNameKana() {
		return storeNameKana;
	}

	public void setStoreNameKana(String storeNameKana) {
		this.storeNameKana = storeNameKana;
	}

	public String getKamokuCode() {
		return kamokuCode;
	}

	public void setKamokuCode(String kamokuCode) {
		this.kamokuCode = kamokuCode;
	}

	public String getKamokuName() {
		return kamokuName;
	}

	public void setKamokuName(String kamokuName) {
		this.kamokuName = kamokuName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}