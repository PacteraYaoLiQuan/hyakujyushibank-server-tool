package com.scsk.responseentity.vo;

import com.scsk.util.ResultMessages;

/**
 * 
 *
 * @param <T>
 */
public class ResponseEntityVO<T> {
    private String resultStatus;
    private String errorCode;
    private ResultMessages messages;
    private String resultCode;
    private T resultData;

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ResultMessages getMessages() {
        return messages;
    }

    public void setMessages(ResultMessages messages) {
        this.messages = messages;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}