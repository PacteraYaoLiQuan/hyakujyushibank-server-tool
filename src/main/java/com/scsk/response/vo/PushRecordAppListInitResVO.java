package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.AccountAppPushListVO;
import com.scsk.vo.UserGet;

public class PushRecordAppListInitResVO {

    private String _id;
    // 配信コード
    private String messageCode;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    // Push通知確認一覧初期化表示用
    private List<AccountAppPushListVO> AccountAppPushList;

    public List<AccountAppPushListVO> getAccountAppPushList() {
        return AccountAppPushList;
    }

    public void setAccountAppPushList(List<AccountAppPushListVO> accountAppPushList2) {
        AccountAppPushList = accountAppPushList2;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }
}