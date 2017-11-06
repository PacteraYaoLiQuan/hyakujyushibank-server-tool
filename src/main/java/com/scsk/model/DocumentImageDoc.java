package com.scsk.model;

import com.scsk.constants.Constants;

public class DocumentImageDoc extends UtilDoc {

    private String docType = Constants.DOCUMENT_IMAGE;
    private String documentAppSeq = "";
    private String imageSeq = "";
    private String cardImageFront = "";
    private String cardImageBack = "";

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocumentAppSeq() {
        return documentAppSeq;
    }

    public void setDocumentAppSeq(String documentAppSeq) {
        this.documentAppSeq = documentAppSeq;
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
