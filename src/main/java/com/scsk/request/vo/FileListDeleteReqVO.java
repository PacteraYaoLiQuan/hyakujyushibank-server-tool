package com.scsk.request.vo;

import java.util.List;

import com.scsk.vo.FileInitVO;

public class FileListDeleteReqVO {
    // 一括削除用一覧
    private List<FileInitVO> deleteList;

    public List<FileInitVO> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<FileInitVO> deleteList) {
        this.deleteList = deleteList;
    }

}
