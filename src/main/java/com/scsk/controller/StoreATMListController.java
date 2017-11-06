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

import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.StoreATMListDeleteButtonReqVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.response.vo.IYoStoreATMInitResVO;
import com.scsk.response.vo.Store114ATMInitResVO;
import com.scsk.response.vo.StoreATMInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.StoreATMListDeleteService;
import com.scsk.service.StoreATMListInitService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;


/**
 * 店舗ATM一覧画面。<br>
 * <br>
 * メニュー画面から店舗ATM一覧表示＆登録／詳細／削除を実装すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class StoreATMListController {
    @Value("${bank_cd}")
    private String bank_cd;
    @Autowired
    private StoreATMListDeleteService storeATMListDeleteService;
    @Autowired
    private StoreATMListInitService storeATMListInitService;

    /**
     * 店舗ATM一覧初期化表示メソッド。
     * 
     * @param なし
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/master/storeATMList", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> listInit(HttpServletRequest req) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        StoreATMInitResVO storeATMInitResVO = new StoreATMInitResVO();
        IYoStoreATMInitResVO iYoStoreATMInitResVO = new IYoStoreATMInitResVO();
        Store114ATMInitResVO store114ATMInitResVO = new Store114ATMInitResVO();
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = new BaseResVO();
        try {
            // 店舗ATM一覧データを検索する
            if ("0169".equals(bank_cd)) {
                BaseResVO ba = (StoreATMInitResVO) storeATMListInitService.execute(baseResVO);
                storeATMInitResVO = (StoreATMInitResVO) ba;
                if (storeATMInitResVO.getStoreATMList().isEmpty()) {
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMLIST_1002);
                    throw new BusinessException(messages);
                }
            } else if ("0174".equals(bank_cd)) {
                BaseResVO ba = (IYoStoreATMInitResVO) storeATMListInitService.execute(baseResVO);
                iYoStoreATMInitResVO = (IYoStoreATMInitResVO) ba;
                if (iYoStoreATMInitResVO.getStoreATMList().isEmpty()) {
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMLIST_1002);
                    throw new BusinessException(messages);
                }
            }else if("0173".equals(bank_cd)){
                BaseResVO ba = (Store114ATMInitResVO) storeATMListInitService.execute(baseResVO);
                store114ATMInitResVO = (Store114ATMInitResVO) ba;
                if (store114ATMInitResVO.getStoreATMList().isEmpty()) {
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMLIST_1002);
                    throw new BusinessException(messages);
                }
            }

            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            if ("0169".equals(bank_cd)) {
                resEntityBody.setResultData(storeATMInitResVO);
            } else if ("0174".equals(bank_cd)) {
                resEntityBody.setResultData(iYoStoreATMInitResVO);
            }else if ("0173".equals(bank_cd)) {
                resEntityBody.setResultData(store114ATMInitResVO);
            }
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            if ("0169".equals(bank_cd)) {
                resEntityBody.setResultData(storeATMInitResVO);
            } else if ("0174".equals(bank_cd)) {
                resEntityBody.setResultData(iYoStoreATMInitResVO);
            }else if("0173".equals(bank_cd)){
                resEntityBody.setResultData(store114ATMInitResVO);
            }
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * 一括削除メソッド。
     * 
     * @param @RequestBody
     *            storeATMListDeleteButtonReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/master/deleteButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> deleteButton(HttpServletRequest req,
            @RequestBody StoreATMListDeleteButtonReqVO storeATMListDeleteButtonReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());

        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();

        try {
            // 選択したデータを削除する
            storeATMListDeleteService.execute(storeATMListDeleteButtonReqVO);
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }
}
