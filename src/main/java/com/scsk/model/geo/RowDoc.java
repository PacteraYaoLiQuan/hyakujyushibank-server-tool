package com.scsk.model.geo;

import com.scsk.model.StoreATMInfoDoc;

public class RowDoc {
    private String id = null;

    private GeometryDoc geometry = null;

    private StoreATMInfoDoc doc = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeometryDoc getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDoc geometry) {
        this.geometry = geometry;
    }

    public StoreATMInfoDoc getDoc() {
        return doc;
    }

    public void setDoc(StoreATMInfoDoc doc) {
        this.doc = doc;
    }

}
