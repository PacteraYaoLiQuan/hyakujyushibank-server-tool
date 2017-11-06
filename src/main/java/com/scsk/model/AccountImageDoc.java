package com.scsk.model;

import com.scsk.constants.Constants;

public class AccountImageDoc extends UtilDoc {
    private String docType = Constants.ACCOUNT_IMAGE;
    private String accountAppSeq = "";
    private String imageSeq = "";
    private String cardImageFront = "";
    private String cardImageBack = "";

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

    public String getImageSeq() {
        return imageSeq;
    }

    public void setImageSeq(String imageSeq) {
        this.imageSeq = imageSeq;
    }

    public String getCardImageFront() {
        return cardImageFront;
    }

    public void setCardImageFront(String cardImageFront) {
        this.cardImageFront = cardImageFront;
    }

    public String getCardImageBack() {
        return cardImageBack;
    }

    public void setCardImageBack(String cardImageBack) {
        this.cardImageBack = cardImageBack;
    }

}
