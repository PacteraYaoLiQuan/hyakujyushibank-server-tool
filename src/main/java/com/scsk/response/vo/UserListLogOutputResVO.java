package com.scsk.response.vo;

import java.util.List;

import com.scsk.model.UserActionLogDoc;

public class UserListLogOutputResVO {
	// ログ出力List
	private List<UserActionLogDoc> userActionList;

	public List<UserActionLogDoc> getUserActionList() {
		return userActionList;
	}

	public void setUserActionList(List<UserActionLogDoc> userActionList) {
		this.userActionList = userActionList;
	}

}
