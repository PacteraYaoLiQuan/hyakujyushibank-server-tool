package com.scsk.config;

import java.util.ArrayList;

public class CloudantForBluemixConfig {
	private ArrayList<BluemixNoSqlInfoBean> cloudantNoSQLDB;

	public ArrayList<BluemixNoSqlInfoBean> getCloudantNoSQLDB() {
		return cloudantNoSQLDB;
	}

	public void setCloudantNoSQLDB(ArrayList<BluemixNoSqlInfoBean> cloudantNoSQLDB) {
		this.cloudantNoSQLDB = cloudantNoSQLDB;
	}
}