package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.ImageVO;

public class AccountAppListOutputCsvResVO {

	// 帳票出力用一覧
	private List<ImageVO> ImageList;

	public List<ImageVO> getImageList() {
		return ImageList;
	}

	public void setImageList(List<ImageVO> imageList) {
		ImageList = imageList;
	}
}