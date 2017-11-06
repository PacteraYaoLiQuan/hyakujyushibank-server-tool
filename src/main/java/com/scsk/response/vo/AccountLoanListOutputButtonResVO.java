package com.scsk.response.vo;

import java.util.List;

import com.scsk.vo.ImageVO;

public class AccountLoanListOutputButtonResVO extends BaseResVO {

	// 帳票出力用一覧
	private List<AccountLoan114DetailResVO> accountLoanList;
	// 帳票出力用一覧
    private List<ImageVO> imageList;
	public List<ImageVO> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageVO> imageList) {
        this.imageList = imageList;
    }

    //帳票用日付
	private String date;

    public List<AccountLoan114DetailResVO> getAccountLoanList() {
        return accountLoanList;
    }

    public void setAccountLoanList(List<AccountLoan114DetailResVO> accountLoanList) {
        this.accountLoanList = accountLoanList;
    }

    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}