package com.scsk.config;

import java.util.ArrayList;

public class BluemixNoSqlInfoBean {
	private String name;
	private String label;
	private ArrayList<String> tags;
	private String plan;
	private BluemixCredentialsBean credentials;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public BluemixCredentialsBean getCredentials() {
		return credentials;
	}

	public void setCredentials(BluemixCredentialsBean credentials) {
		this.credentials = credentials;
	}
}
