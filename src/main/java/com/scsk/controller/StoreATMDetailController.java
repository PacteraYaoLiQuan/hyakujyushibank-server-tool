package com.scsk.controller;

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
import com.scsk.request.vo.StoreATMDetailReqVO;
import com.scsk.request.vo.StoreATMDetailUpdateReqVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.response.vo.IYoStoreATMDetailResVO;
import com.scsk.response.vo.IYoStoreATMDetailUpdateResVO;
import com.scsk.response.vo.Store114ATMDetailUpdateResVO;
import com.scsk.response.vo.StoreATMDetailResVO;
import com.scsk.response.vo.StoreATMDetailUpdateResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.StoreATMDetailSelService;
import com.scsk.service.StoreATMDetailUpdService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * 店舗ATM新規／編集コントロール。<br>
 * <br>
 * 店舗ATM情報データを検索すること。<br>
 * 店舗ATM情報データを登録すること。<br>
 * 店舗ATM情報データを更新すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class StoreATMDetailController {

    @Value("${bank_cd}")
    private String bank_cd;

    @Autowired
    StoreATMDetailSelService storeATMDetailSelService;

    @Autowired
    StoreATMDetailUpdService storeATMDetailUpdService;

    /**
     * 店舗ATM詳細データ検索メソッド。
     * 
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/master/storeATMDetail", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> detailShow(@RequestParam("_id") String id) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
        StoreATMDetailReqVO input = new StoreATMDetailReqVO();
        StoreATMDetailResVO storeATMDetailResVO = new StoreATMDetailResVO();
        IYoStoreATMDetailResVO iYoStoreATMDetailResVO = new IYoStoreATMDetailResVO();
        Store114ATMDetailUpdateResVO store114atmDetailUpdateResVO=new Store114ATMDetailUpdateResVO();
        input.set_id(id);

        try {
            // 店舗ATM詳細情報取得
            if ("0169".equals(bank_cd)) {
                BaseResVO output = storeATMDetailSelService.execute(input);
                storeATMDetailResVO = (StoreATMDetailResVO) output;
                entityVO.setResultData(storeATMDetailResVO);
            } else if ("0174".equals(bank_cd)) {
                BaseResVO output = storeATMDetailSelService.execute(input);
                iYoStoreATMDetailResVO = (IYoStoreATMDetailResVO) output;
                entityVO.setResultData(iYoStoreATMDetailResVO);
            }else if("0173".equals(bank_cd)){
                BaseResVO output = storeATMDetailSelService.execute(input);
                store114atmDetailUpdateResVO = (Store114ATMDetailUpdateResVO) output;
                entityVO.setResultData(store114atmDetailUpdateResVO);
            }
            // ヘッダ設定（処理成功の場合）
            entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
            entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            // 予想エラー以外の場合
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(entityVO, HttpStatus.OK);

    }

    /**
     * 店舗ATM詳細データ更新メソッド。
     * 
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/master/storeATMDetailUpd", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> dataUpd(
            @RequestBody StoreATMDetailUpdateReqVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<BaseResVO> entityVO = new ResponseEntityVO<BaseResVO>();
        StoreATMDetailUpdateResVO storeATMDetailUpdateResVO=new StoreATMDetailUpdateResVO();
        IYoStoreATMDetailUpdateResVO iYoStoreATMDetailUpdateResVO=new IYoStoreATMDetailUpdateResVO();
        Store114ATMDetailUpdateResVO store114atmDetailUpdateResVO=new Store114ATMDetailUpdateResVO();
        BaseResVO baseResVO = new BaseResVO();
        baseResVO = input;
        try {
            BaseResVO  output =storeATMDetailUpdService.execute(baseResVO);
            if ("0169".equals(bank_cd)) {
                storeATMDetailUpdateResVO=(StoreATMDetailUpdateResVO)output;
                entityVO.setResultData(storeATMDetailUpdateResVO);
            } else if ("0174".equals(bank_cd)) {
                iYoStoreATMDetailUpdateResVO = (IYoStoreATMDetailUpdateResVO) output;
                entityVO.setResultData(iYoStoreATMDetailUpdateResVO);
            }else if("0173".equals(bank_cd)){
                store114atmDetailUpdateResVO = (Store114ATMDetailUpdateResVO) output;
                entityVO.setResultData(store114atmDetailUpdateResVO);
            }
            // ステータスを更新
            
            // ヘッダ設定（処理成功の場合）
            entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
            entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);

            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            // 予想エラー以外の場合
            entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
            entityVO.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }

        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(entityVO, HttpStatus.OK);

    }

}
