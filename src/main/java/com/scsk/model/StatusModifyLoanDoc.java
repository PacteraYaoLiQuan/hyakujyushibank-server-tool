package com.scsk.model;

import com.scsk.constants.Constants;

public class StatusModifyLoanDoc extends UtilDoc {

    // ドキュメントタイプ
    private String docType = Constants.STATUS_MODIFY_LOAN;
    // 受付番号
    private String accountAppSeq = "";
    // 申込処理ステータス
    private String status = "";
    // ステータス变更日付
    private String statusModifyDate = "";
    // ソート日付
    private long statusModifyDateForSort = 0l;
    // 配信状态
    private String sendStatus = "";
    // プッシュOID
    private String pushRecordOid = "";

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getAccountAppSeq() {
        return accountAppSeq;
    }

    public void setAccountAppSeq(String accountAppSeq) {
        this.accountAppSeq = accountAppSeq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatusModifyDate() {
        return statusModifyDate;
    }

    public void setStatusModifyDate(String statusModifyDate) {
        this.statusModifyDate = statusModifyDate;
    }

    public long getStatusModifyDateForSort() {
        return statusModifyDateForSort;
    }

    public void setStatusModifyDateForSort(long statusModifyDateForSort) {
        this.statusModifyDateForSort = statusModifyDateForSort;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getPushRecordOid() {
        return pushRecordOid;
    }

    public void setPushRecordOid(String pushRecordOid) {
        this.pushRecordOid = pushRecordOid;
    }


}
