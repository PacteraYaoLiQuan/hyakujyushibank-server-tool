package com.scsk.model.geo;

public class ResponseDoc {
    private String bookmark = null;

    private RowDoc[] rows = null;

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public RowDoc[] getRows() {
        return rows;
    }

    public void setRows(RowDoc[] rows) {
        this.rows = rows;
    }

}
