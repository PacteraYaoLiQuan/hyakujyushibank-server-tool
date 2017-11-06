package com.scsk.response.vo;

import java.util.List;

import com.scsk.request.vo.FileUploadReqVo;

public class FileUploadResVo {

    //初期化表示用
    private List<FileUploadReqVo> fileFindListReqVo;

    public List<FileUploadReqVo> getFileFindListReqVo() {
        return fileFindListReqVo;
    }

    public void setFileFindListReqVo(List<FileUploadReqVo> fileFindListReqVo) {
        this.fileFindListReqVo = fileFindListReqVo;
    }
    }
