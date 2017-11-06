package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.FileInitVO;

public class FileInitResVO {
    // ファイル一覧初期化表示用
    private List<FileInitVO> fileList;

    public List<FileInitVO> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileInitVO> fileList) {
        this.fileList = fileList;
    }

   
}
