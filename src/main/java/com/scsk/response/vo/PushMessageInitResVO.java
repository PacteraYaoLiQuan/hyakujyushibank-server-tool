package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.PushMessageInitVO;

public class PushMessageInitResVO {
	// Pushメッセージ一覧初期化表示用
	private List<PushMessageInitVO> messageList;

	public List<PushMessageInitVO> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<PushMessageInitVO> messageList) {
		this.messageList = messageList;
	}

}
