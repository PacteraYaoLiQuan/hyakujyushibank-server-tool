package com.scsk.model;

/**
 * 対話履歴DOC
 * 
 * @author 9003474
 *
 */
public class AssistantChatApiDoc extends UtilDoc {

    // ドキュメントタイプ
    private String docType = "";
    // ユーザーID
    private String userId = "";
    // keyドキュメントタID
    private String keyDocId = "";
    // 質問
    private String ask = "";
    // 質問時間
    private String askTime = "";
    // 回答
    private String answer = "";
    // 回答時間
    private String answerTime = "";
    // 情報の種類
    private String msgType = "0";
    // 配信Msg
    private String pushMsg = "";
    // 配信日付
    private String pushTime = "";
    // ソート日付
    private String sortTime = "";
    // IDデータを変更します
    private String changeIdData = "";

    public String getChangeIdData() {
        return changeIdData;
    }

    public void setChangeIdData(String changeIdData) {
        this.changeIdData = changeIdData;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(String pushMsg) {
        this.pushMsg = pushMsg;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getAskTime() {
        return askTime;
    }

    public void setAskTime(String askTime) {
        this.askTime = askTime;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
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

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getKeyDocId() {
        return keyDocId;
    }

    public void setKeyDocId(String keyDocId) {
        this.keyDocId = keyDocId;
    }

    public String getSortTime() {
        return sortTime;
    }

    public void setSortTime(String sortTime) {
        this.sortTime = sortTime;
    }

}
