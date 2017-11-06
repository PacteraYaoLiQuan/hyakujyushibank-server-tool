package com.scsk.vo;

public class GeneralPurposeInitVO extends BaseResVO{
    private String _id;
    private String dateFrom ;
    private String dateTo;
    private String contentsFile1;
    private String comment1;
    private String contentsID;
    private String contentsTitle;

    public String getContentsTitle() {
        return contentsTitle;
    }

    public void setContentsTitle(String contentsTitle) {
        this.contentsTitle = contentsTitle;
    }

    public String getContentsID() {
        return contentsID;
    }

    public void setContentsID(String contentsID) {
        this.contentsID = contentsID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getContentsFile1() {
        return contentsFile1;
    }

    public void setContentsFile1(String contentsFile1) {
        this.contentsFile1 = contentsFile1;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

}
