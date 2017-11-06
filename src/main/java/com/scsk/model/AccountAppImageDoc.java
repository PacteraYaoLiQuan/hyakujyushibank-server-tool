package com.scsk.model;
/**
 * 申込画像DOC
 */
public class AccountAppImageDoc extends UtilDoc {

    //ドキュメントタイプ
    private String docType = "";
    //受付番号
    private String accountAppSeq = "";
    //本人確認書類画像
    private String identificationImage = "";
    //本人確認書類画像裏面
    private String identificationImageBack = "";
    //生活状況確認書類画像
    private String livingConditionsImage = "";
    
    public String getIdentificationImageBack() {
        return identificationImageBack;
    }
    public void setIdentificationImageBack(String identificationImageBack) {
        this.identificationImageBack = identificationImageBack;
    }
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
    public String getIdentificationImage() {
        return identificationImage;
    }
    public void setIdentificationImage(String identificationImage) {
        this.identificationImage = identificationImage;
    }
    public String getLivingConditionsImage() {
        return livingConditionsImage;
    }
    public void setLivingConditionsImage(String livingConditionsImage) {
        this.livingConditionsImage = livingConditionsImage;
    }
    
}
