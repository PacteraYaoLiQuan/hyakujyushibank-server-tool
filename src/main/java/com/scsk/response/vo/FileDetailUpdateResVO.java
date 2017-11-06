package com.scsk.response.vo;

public class FileDetailUpdateResVO {
    // id
    private String _id;
    // 編集/新規mode
    private String modeType;
    // ファイル名（日本語）
    private String fileNameJP;
    // ファイル名（英語）
    private String fileNameEN;
    // ファイルパス
    private String path;
    // アップロード日時
    private String uploadDatetime;
    // 利用箇所
    private String useLocal;
    // 参照URL
    private String referURL;
    // ファイル
    private String fileStream;
    // 作成者
    private String createdBy;
    // 作成日時
    private String createdDate;
    // 更新者
    private String updatedBy;
    // 更新日時
    private String updatedDate;
    // 削除フラグ
    private String delFlg;
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String getModeType() {
        return modeType;
    }
    public void setModeType(String modeType) {
        this.modeType = modeType;
    }
    public String getFileNameJP() {
        return fileNameJP;
    }
    public void setFileNameJP(String fileNameJP) {
        this.fileNameJP = fileNameJP;
    }
    public String getFileNameEN() {
        return fileNameEN;
    }
    public void setFileNameEN(String fileNameEN) {
        this.fileNameEN = fileNameEN;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getUploadDatetime() {
        return uploadDatetime;
    }
    public void setUploadDatetime(String uploadDatetime) {
        this.uploadDatetime = uploadDatetime;
    }
    public String getUseLocal() {
        return useLocal;
    }
    public void setUseLocal(String useLocal) {
        this.useLocal = useLocal;
    }
    public String getReferURL() {
        return referURL;
    }
    public void setReferURL(String referURL) {
        this.referURL = referURL;
    }
    public String getFileStream() {
        return fileStream;
    }
    public void setFileStream(String fileStream) {
        this.fileStream = fileStream;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public String getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
    public String getDelFlg() {
        return delFlg;
    }
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
    
    

}
