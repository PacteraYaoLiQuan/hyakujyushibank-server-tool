package com.scsk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.request.vo.AccountAppPushNotificationsReqVO;
import com.scsk.response.vo.AccountAppDetailResVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.response.vo.AccountAppPushNotificationsResVO;
import com.scsk.response.vo.AccountAppYamaGataDetailResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.AccountAppAppraisalDetailService;
import com.scsk.service.AccountAppDetailImageSelService;
import com.scsk.service.AccountAppDetailSelService;
import com.scsk.service.AccountAppDetailUpdService;
import com.scsk.service.AccountAppIpAddressFindFindService;
import com.scsk.service.AccountAppPhoneNumberFindService;
import com.scsk.service.AccountAppPushNotifications;
import com.scsk.service.AccountAppPushRecordService;
import com.scsk.service.AccountAppTelNumberFindService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * 申込詳細コントロール。<br>
 * <br>
 * 申込詳細データを検索すること。<br>
 * 申込詳細データを更新すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class AccountAppDetailController {

	@Autowired
	AccountAppDetailSelService accountAppDetailSelService;

	@Autowired
	AccountAppDetailUpdService accountAppDetailUpdService;

	@Autowired
	AccountAppDetailImageSelService accountAppDetailImageSelService;

	@Autowired
	AccountAppAppraisalDetailService accountAppAppraisalDetailService;

	@Autowired
	AccountAppPushRecordService accountAppPushRecordService;

	@Autowired
	AccountAppPushNotifications accountAppPushNotifications;
	
    @Autowired
    AccountAppPhoneNumberFindService accountAppPhoneNumberFindService;
    
    @Autowired
    AccountAppTelNumberFindService accountAppTelNumberFindService;
    
    @Autowired
    AccountAppIpAddressFindFindService accountAppIpAddressFindFindService;
    @Value("${bank_cd}")
    private String bank_cd;
	/**
	 * 申込詳細データ検索メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/account/accountAppDetail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<BaseResVO>> detailShow(
			@RequestParam("_id") String id) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
		AccountAppDetailReqVO input = new AccountAppDetailReqVO();
		input.set_id(id);
		BaseResVO baseResVO = new BaseResVO();
		baseResVO = input;

		try {
			// 申込詳細情報取得
			BaseResVO ba = accountAppDetailSelService
					.execute(baseResVO);
			// ヘッダ設定（処理成功の場合）
			entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
			entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
			// ボーディ設定
			entityVO.setResultData(ba);
			LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
		} catch (BusinessException e) {
			// ヘッダ設定（処理失敗の場合）
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(e.getResultMessages());
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
					.getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<BaseResVO>>(
				entityVO, HttpStatus.OK);

	}

	/**
	 * 申込詳細データ更新メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/account/accountAppStatusUpd", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>> statusUpd(
			@RequestBody AccountAppDetailStatusReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<AccountAppDetailStatusResVO> entityVO = new ResponseEntityVO<AccountAppDetailStatusResVO>();

		try {
			// ステータスを更新
			AccountAppDetailStatusResVO output = accountAppDetailUpdService
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
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
					.getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}

		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>>(
				entityVO, HttpStatus.OK);

	}
	   /**
     * 自宅電話番号鑑定APIメソッド。
     * 
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/account/phoneNumberFind", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> phoneNumberFind(
            @RequestBody AccountAppDetailStatusReqVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();
        baseResVO = input;

        try {
            // ステータスを更新
            AccountAppDetailStatusResVO output = (AccountAppDetailStatusResVO) accountAppPhoneNumberFindService
                    .execute(baseResVO);
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
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
                    .getCode());
        } catch (Exception e) {
            // 予想エラー以外の場合
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(ResultMessages.error()
                    .add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(
                entityVO, HttpStatus.OK);
    }
	
    /**
  * 自宅電話番号鑑定APIメソッド。
  * 
  * @return ResponseEntity　戻るデータオブジェクト
  */
 @RequestMapping(value = "/account/TelNumberFind", method = RequestMethod.POST, produces = "application/json")
 public ResponseEntity<ResponseEntityVO<BaseResVO>> telNumberFind(
         @RequestBody AccountAppDetailStatusReqVO input) {
     LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
     ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
     BaseResVO baseResVO = new BaseResVO();
     baseResVO = input;

     try {
         // ステータスを更新
         AccountAppDetailStatusResVO output = (AccountAppDetailStatusResVO) accountAppTelNumberFindService
                 .execute(baseResVO);
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
         LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
                 .getCode());
     } catch (Exception e) {
         // 予想エラー以外の場合
         entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
         entityVO.setMessages(ResultMessages.error()
                 .add(MessageKeys.ERR_500));
         LogInfoUtil.LogError(e.getMessage(), e);
     }
     LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
     return new ResponseEntity<ResponseEntityVO<BaseResVO>>(
             entityVO, HttpStatus.OK);
 }
 
 /**
* ＩＰアドレス鑑定結果APIメソッド。
* 
* @return ResponseEntity　戻るデータオブジェクト
*/
@RequestMapping(value = "/account/IpAddressFind", method = RequestMethod.POST, produces = "application/json")
public ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>> IpAddressFind(
      @RequestBody AccountAppDetailStatusReqVO input) {
  LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
  ResponseEntityVO<AccountAppDetailStatusResVO> entityVO = new ResponseEntityVO<AccountAppDetailStatusResVO>();

  try {
      // ステータスを更新
      AccountAppDetailStatusResVO output = accountAppIpAddressFindFindService
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
      LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
              .getCode());
  } catch (Exception e) {
      // 予想エラー以外の場合
      entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
      entityVO.setMessages(ResultMessages.error()
              .add(MessageKeys.ERR_500));
      LogInfoUtil.LogError(e.getMessage(), e);
  }
  LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
  return new ResponseEntity<ResponseEntityVO<AccountAppDetailStatusResVO>>(
          entityVO, HttpStatus.OK);
}
 
	/**
	 * 画像検索メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/account/accountAppDetailImage", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<BaseResVO>> detailImage(
			@RequestParam("_id") String id) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
		AccountAppDetailReqVO input = new AccountAppDetailReqVO();
		input.set_id(id);
		BaseResVO baseResVO = new BaseResVO();
		baseResVO = input;

		try {
			// 画像情報取得
			BaseResVO output = accountAppDetailImageSelService
					.execute(baseResVO);
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
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
					.getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<BaseResVO>>(
				entityVO, HttpStatus.OK);

	}

	/**
	 * ＩＰアドレス鑑定/電話番号鑑定検索メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/account/accountAppAppraisal", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<BaseResVO>> AppraisalShow(
			@RequestParam("_id") String id) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
		AccountAppDetailReqVO input = new AccountAppDetailReqVO();
		input.set_id(id);
		BaseResVO baseResVO = new BaseResVO();
		baseResVO = input;

		try {
			// 鑑定情報取得
			BaseResVO output = accountAppAppraisalDetailService
					.execute(baseResVO);
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
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
					.getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<BaseResVO>>(
				entityVO, HttpStatus.OK);

	}

	/**
	 * Push通知確認画面検索メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/account/pushRecord", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<AccountAppDetailResVO>> pushRecord(
			HttpServletRequest req) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<AccountAppDetailResVO> entityVO = new ResponseEntityVO<AccountAppDetailResVO>();
		AccountAppDetailReqVO input = new AccountAppDetailReqVO();

		try {
			// Push通知確認画面情報取得
			AccountAppDetailResVO output = accountAppPushRecordService
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
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
					.getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<AccountAppDetailResVO>>(
				entityVO, HttpStatus.OK);

	}

	/**
	 * Push通知メソッド。
	 * 
	 * @return ResponseEntity　戻るデータオブジェクト
	 */
	@RequestMapping(value = "/account/pushNotifications", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseEntityVO<AccountAppPushNotificationsResVO>> pushNotifications(
			@RequestBody AccountAppPushNotificationsReqVO input) {
		LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
		ResponseEntityVO<AccountAppPushNotificationsResVO> entityVO = new ResponseEntityVO<AccountAppPushNotificationsResVO>();

		try {
			// Push通知確認画面情報取得
			AccountAppPushNotificationsResVO output = accountAppPushNotifications
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
			LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0)
					.getCode());
		} catch (Exception e) {
			// 予想エラー以外の場合
			entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
			entityVO.setMessages(ResultMessages.error()
					.add(MessageKeys.ERR_500));
			LogInfoUtil.LogError(e.getMessage(), e);
		}
		LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
		return new ResponseEntity<ResponseEntityVO<AccountAppPushNotificationsResVO>>(
				entityVO, HttpStatus.OK);

	}
}
