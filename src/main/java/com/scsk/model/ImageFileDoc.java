package com.scsk.model;

import com.scsk.constants.Constants;

/**
 * ユーザーDOC
 */
public class ImageFileDoc extends UtilDoc {

	// ドキュメントタイプ
	private String docType = Constants.IMAGEFILE_DOCTYPE;

    // ファイル名（日本語）
    private String fileNameJP = "";
    // ファイル名（英語）
    private String fileNameEN = "";
    // ファイルパス
    private String path = "";
    // アップロード日時
    private String uploadDatetime = "";
    // 利用箇所
    private String useLocal = "";
    // 参照URL
    private String referURL = "";
    // ファイル
    private String fileStream = "";
 
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
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
}