package com.scsk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.AuthorityDetailReqVO;
import com.scsk.request.vo.AuthorityDetailStatusReqVO;
import com.scsk.response.vo.AuthorityDetailResVO;
import com.scsk.response.vo.AuthorityDetailUpdateResVO;
import com.scsk.response.vo.AuthorityInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.AuthorityDetailSelService;
import com.scsk.service.AuthorityDetailUpdService;
import com.scsk.service.AuthorityListInitService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.AuthorityInitVO;

/**
 * 権限新規／編集コントロール。<br>
 * <br>
 * 権限情報データを検索すること。<br>
 * 権限情報データを登録すること。<br>
 * 権限情報データを更新すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class AuthorityDetailController {

	@Autowired
	AuthorityDetailSelService authorityDetailSelService;

	@Autowired
	AuthorityDetailUpdService authorityDetailUpdService;
    @Autowired
    private AuthorityListInitService authorityListInitService;
	/**
	 * 権限詳細データ検索メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/authority/authorityDetail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<AuthorityDetailResVO>> detailShow(
			@RequestParam("_id") String id) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<AuthorityDetailResVO> entityVO = new ResponseEntityVO<AuthorityDetailResVO>();
		AuthorityDetailReqVO input = new AuthorityDetailReqVO();
		input.set_id(id);

		try {
			// 権限詳細情報取得
			AuthorityDetailResVO output = authorityDetailSelService
					.execute(input);
			// ヘッダ設定（処理成功の場合）
			entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
			entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定
			entityVO.setResultData(output);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(e.getResultMessages());
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<AuthorityDetailResVO>>(
				entityVO, HttpStatus.OK);

	}

	/**
	 * 権限詳細データ更新メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/authority/authorityDetailUpd", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<AuthorityDetailUpdateResVO>> dataUpd(
			@RequestBody AuthorityDetailStatusReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<AuthorityDetailUpdateResVO> entityVO = new ResponseEntityVO<AuthorityDetailUpdateResVO>();

		try {
			// ステータスを更新
			AuthorityDetailUpdateResVO output = authorityDetailUpdService
					.execute(input);
			// ヘッダ設定（処理成功の場合）
			entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
			entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定
			entityVO.setResultData(output);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(e.getResultMessages());
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<AuthorityDetailUpdateResVO>>(
				entityVO, HttpStatus.OK);

	}
	
	/**
     * 権限名の存在チェックメソッド。
     * 
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/authority/authorityNameCheck", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<AuthorityInitResVO>> authorityNameCheck(
            @RequestBody AuthorityDetailStatusReqVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AuthorityInitResVO> resEntityBody = new ResponseEntityVO<AuthorityInitResVO>();
        AuthorityInitResVO authorityInitResVO = new AuthorityInitResVO();
        try {
            // 権限一覧データを検索する
            authorityInitResVO = (AuthorityInitResVO) authorityListInitService
                    .execute(authorityInitResVO);
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            // ボーディ設定（処理成功の場合）
            resEntityBody.setResultData(authorityInitResVO);
            // 権限名あるの場合
            for(AuthorityInitVO authorityInitVO :authorityInitResVO.getAuthorityList() ) {
                if (authorityInitVO.getAuthorityName().equals(input.getAuthorityName())) {
                    resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
                   break;
                }
            }
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            resEntityBody.setResultData(authorityInitResVO);
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(
                    MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<AuthorityInitResVO>>(
                resEntityBody, HttpStatus.OK);

    }

}
