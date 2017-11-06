package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.FileInitVO;

public class FileSelReqVO {
    private String fileNameJP;
    private String useLocal;
    private List<FileInitVO> fileSel;
    public String getFileNameJP() {
        return fileNameJP;
    }
    public void setFileNameJP(String fileNameJP) {
        this.fileNameJP = fileNameJP;
    }
    public String getUseLocal() {
        return useLocal;
    }
    public void setUseLocal(String useLocal) {
        this.useLocal = useLocal;
    }
    public List<FileInitVO> getFileSel() {
        return fileSel;
    }
    public void setFileSel(List<FileInitVO> fileSel) {
        this.fileSel = fileSel;
    }

}
