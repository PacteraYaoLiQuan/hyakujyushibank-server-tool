package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.IYoStoreATMInfoDoc;
import com.scsk.model.Store114ATMInfoDoc;
import com.scsk.model.StoreATMInfoDoc;
import com.scsk.request.vo.StoreATMDetailReqVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.response.vo.IYoStoreATMDetailResVO;
import com.scsk.response.vo.Store114ATMDetailUpdateResVO;
import com.scsk.response.vo.StoreATMDetailResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;

/**
 * 店舗ATM詳細検索サービス。<br>
 * <br>
 * 店舗ATM詳細検索を実装するロジック。<br>
 */
@Service
public class StoreATMDetailSelService extends AbstractBLogic<StoreATMDetailReqVO, BaseResVO> {
    @Value("${bank_cd}")
    private String bank_cd;
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(StoreATMDetailReqVO detailReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param reqVo
     *            入力情報
     * @return resVo 詳細情報
     * @throws Exception
     */
    @Override
    protected BaseResVO doExecute(CloudantClient client, StoreATMDetailReqVO detailReqVO) throws Exception {

        StoreATMDetailResVO storeATMDetailResVO = new StoreATMDetailResVO();
        IYoStoreATMDetailResVO iYoStoreATMDetailResVO = new IYoStoreATMDetailResVO();
        Store114ATMDetailUpdateResVO store114atmDetailUpdateResVO=new Store114ATMDetailUpdateResVO();
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);

        if ("0169".equals(bank_cd)) {
            storeATMDetailResVO = hiroshima(db, detailReqVO);
            return storeATMDetailResVO;
        } else if ("0174".equals(bank_cd)) {
            iYoStoreATMDetailResVO = iyo(db, detailReqVO);
            return iYoStoreATMDetailResVO;
        } else if("0173".equals(bank_cd)){
            store114atmDetailUpdateResVO=hyakujyushi(db,detailReqVO);
            return store114atmDetailUpdateResVO;
        }
        return new BaseResVO();
    }

    private Store114ATMDetailUpdateResVO hyakujyushi(Database db, StoreATMDetailReqVO detailReqVO) {
        String storeDetailLog = "(店舗ATM名：";
        Store114ATMDetailUpdateResVO store114atmDetailUpdateResVO=new Store114ATMDetailUpdateResVO();
        // 店舗ATM詳細情報取得

        Store114ATMInfoDoc storeATMInfoDoc = new Store114ATMInfoDoc();
        try {
            storeATMInfoDoc = (Store114ATMInfoDoc) repositoryUtil.find(db, detailReqVO.get_id(),
                    Store114ATMInfoDoc.class);
            storeDetailLog = storeDetailLog + storeATMInfoDoc.getStoreName();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMDETAIL_1001);
            throw new BusinessException(messages);
        }
        store114atmDetailUpdateResVO.setAccountMachine(storeATMInfoDoc.getAccountMachine());
        store114atmDetailUpdateResVO.setAddress(storeATMInfoDoc.getAddress());
        store114atmDetailUpdateResVO.setArea(storeATMInfoDoc.getArea());
        store114atmDetailUpdateResVO.setAtmAddress(storeATMInfoDoc.getAtmAddress());
     // ATM営業時間_平日始業
        String atmOpenStartTime = storeATMInfoDoc.getAtmOpenStartTime();
        if (Utils.isNotNullAndEmpty(atmOpenStartTime)) {
            atmOpenStartTime = cover(atmOpenStartTime);
            store114atmDetailUpdateResVO.setAtmOpenStartHour(atmOpenStartTime.substring(0, 2));
            store114atmDetailUpdateResVO.setAtmOpenStartMinute(atmOpenStartTime.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setAtmOpenStartHour("");
            store114atmDetailUpdateResVO.setAtmOpenStartMinute("");
        }
        // ATM営業時間_平日終業
        String atmOpenEndTime = storeATMInfoDoc.getAtmOpenEndTime();
        if (Utils.isNotNullAndEmpty(atmOpenEndTime)) {
            atmOpenEndTime = cover(atmOpenEndTime);
            store114atmDetailUpdateResVO.setAtmOpenEndHour(atmOpenEndTime.substring(0, 2));
            store114atmDetailUpdateResVO.setAtmOpenEndMinute(atmOpenEndTime.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setAtmOpenEndHour("");
            store114atmDetailUpdateResVO.setAtmOpenEndMinute("");
        }
        // ATM営業時間_土曜日始業
        String atmOpenStartTime_SAT = storeATMInfoDoc.getAtmOpenStartTime_SAT();
        if (Utils.isNotNullAndEmpty(atmOpenStartTime_SAT)) {
            atmOpenStartTime_SAT = cover(atmOpenStartTime_SAT);
            store114atmDetailUpdateResVO.setAtmOpenStartHour_SAT(atmOpenStartTime_SAT.substring(0, 2));
            store114atmDetailUpdateResVO.setAtmOpenStartMinute_SAT(atmOpenStartTime_SAT.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setAtmOpenStartHour_SAT("");
            store114atmDetailUpdateResVO.setAtmOpenStartMinute_SAT("");
        }
        // ATM営業時間_土曜日終業
        String atmOpenEndTime_SAT = storeATMInfoDoc.getAtmOpenEndTime_SAT();
        if (Utils.isNotNullAndEmpty(atmOpenEndTime_SAT)) {
            atmOpenEndTime_SAT = cover(atmOpenEndTime_SAT);
            store114atmDetailUpdateResVO.setAtmOpenEndHour_SAT(atmOpenEndTime_SAT.substring(0, 2));
            store114atmDetailUpdateResVO.setAtmOpenEndMinute_SAT(atmOpenEndTime_SAT.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setAtmOpenEndHour_SAT("");
            store114atmDetailUpdateResVO.setAtmOpenEndMinute_SAT("");
        }
        // ATM営業時間_日曜日始業
        String atmOpenStartTime_SUN = storeATMInfoDoc.getAtmOpenStartTime_SUN();
        if (Utils.isNotNullAndEmpty(atmOpenStartTime_SUN)) {
            atmOpenStartTime_SUN = cover(atmOpenStartTime_SUN);
            store114atmDetailUpdateResVO.setAtmOpenStartHour_SUN(atmOpenStartTime_SUN.substring(0, 2));
            store114atmDetailUpdateResVO.setAtmOpenStartMinute_SUN(atmOpenStartTime_SUN.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setAtmOpenStartHour_SUN("");
            store114atmDetailUpdateResVO.setAtmOpenStartMinute_SUN("");
        }
        // ATM営業時間_日曜日終業
        String atmOpenEndTime_SUN = storeATMInfoDoc.getAtmOpenEndTime_SUN();
        if (Utils.isNotNullAndEmpty(atmOpenEndTime_SUN)) {
            atmOpenEndTime_SUN = cover(atmOpenEndTime_SUN);
            store114atmDetailUpdateResVO.setAtmOpenEndHour_SUN(atmOpenEndTime_SUN.substring(0, 2));
            store114atmDetailUpdateResVO.setAtmOpenEndMinute_SUN(atmOpenEndTime_SUN.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setAtmOpenEndHour_SUN("");
            store114atmDetailUpdateResVO.setAtmOpenEndMinute_SUN("");
        }
        store114atmDetailUpdateResVO.setConversionMachine(storeATMInfoDoc.getConversionMachine());
        store114atmDetailUpdateResVO.setDelFlg(storeATMInfoDoc.getDelFlg());
        store114atmDetailUpdateResVO.setLatitude(storeATMInfoDoc.getLatitude());
        store114atmDetailUpdateResVO.setLongitude(storeATMInfoDoc.getLongitude());
        store114atmDetailUpdateResVO.setLoanMachine(storeATMInfoDoc.getLoanMachine());
        store114atmDetailUpdateResVO.setManageStore(storeATMInfoDoc.getManageStore());
        String policeCompany=storeATMInfoDoc.getPoliceCompany();
        if(policeCompany.equals("四国警備")){
            policeCompany="1";
        }else if(policeCompany.equals("綜合警備")){
            policeCompany="2";
        }else if(policeCompany.equals("綜合")){
            policeCompany="3";
        }else if (policeCompany.equals("セコム")) {
            policeCompany="4";
        }else if (policeCompany.equals("セノン")) {
            policeCompany="5";
        }
        store114atmDetailUpdateResVO.setPoliceCompany(policeCompany);
        store114atmDetailUpdateResVO.setPostCode(storeATMInfoDoc.getPostCode());
        store114atmDetailUpdateResVO.setServiceConversionIn(storeATMInfoDoc.getServiceConversionIn());
        store114atmDetailUpdateResVO.setServiceConversionOut(storeATMInfoDoc.getServiceConversionOut());
        store114atmDetailUpdateResVO.setStoreName(storeATMInfoDoc.getStoreName());
        store114atmDetailUpdateResVO.setStoreNumber(storeATMInfoDoc.getStoreNumber());
     // Store営業時間_平日始業
        String StoreOpenStartTime = storeATMInfoDoc.getStoreOpenStartTime();
        if (Utils.isNotNullAndEmpty(StoreOpenStartTime)) {
            StoreOpenStartTime = cover(StoreOpenStartTime);
            store114atmDetailUpdateResVO.setStoreOpenStartHour(StoreOpenStartTime.substring(0, 2));
            store114atmDetailUpdateResVO.setStoreOpenStartMinute(StoreOpenStartTime.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setStoreOpenStartHour("");
            store114atmDetailUpdateResVO.setStoreOpenStartMinute("");
        }
        // Store営業時間_平日終業
        String StoreOpenEndTime = storeATMInfoDoc.getStoreOpenEndTime();
        if (Utils.isNotNullAndEmpty(StoreOpenEndTime)) {
            StoreOpenEndTime = cover(StoreOpenEndTime);
            store114atmDetailUpdateResVO.setStoreOpenEndHour(StoreOpenEndTime.substring(0, 2));
            store114atmDetailUpdateResVO.setStoreOpenEndMinute(StoreOpenEndTime.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setStoreOpenEndHour("");
            store114atmDetailUpdateResVO.setStoreOpenEndMinute("");
        }
        // Store営業時間_土曜日始業
        String StoreOpenStartTime_SAT = storeATMInfoDoc.getStoreOpenStartTime_SAT();
        if (Utils.isNotNullAndEmpty(StoreOpenStartTime_SAT)) {
            StoreOpenStartTime_SAT = cover(StoreOpenStartTime_SAT);
            store114atmDetailUpdateResVO.setStoreOpenStartHour_SAT(StoreOpenStartTime_SAT.substring(0, 2));
            store114atmDetailUpdateResVO.setStoreOpenStartMinute_SAT(StoreOpenStartTime_SAT.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setStoreOpenStartHour_SAT("");
            store114atmDetailUpdateResVO.setStoreOpenStartMinute_SAT("");
        }
        // Store営業時間_土曜日終業
        String StoreOpenEndTime_SAT = storeATMInfoDoc.getStoreOpenEndTime_SAT();
        if (Utils.isNotNullAndEmpty(StoreOpenEndTime_SAT)) {
            StoreOpenEndTime_SAT = cover(StoreOpenEndTime_SAT);
            store114atmDetailUpdateResVO.setStoreOpenEndHour_SAT(StoreOpenEndTime_SAT.substring(0, 2));
            store114atmDetailUpdateResVO.setStoreOpenEndMinute_SAT(StoreOpenEndTime_SAT.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setStoreOpenEndHour_SAT("");
            store114atmDetailUpdateResVO.setStoreOpenEndMinute_SAT("");
        }
        // Store営業時間_日曜日始業
        String StoreOpenStartTime_SUN = storeATMInfoDoc.getStoreOpenStartTime_SUN();
        if (Utils.isNotNullAndEmpty(StoreOpenStartTime_SUN)) {
            StoreOpenStartTime_SUN = cover(StoreOpenStartTime_SUN);
            store114atmDetailUpdateResVO.setStoreOpenStartHour_SUN(StoreOpenStartTime_SUN.substring(0, 2));
            store114atmDetailUpdateResVO.setStoreOpenStartMinute_SUN(StoreOpenStartTime_SUN.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setStoreOpenStartHour_SUN("");
            store114atmDetailUpdateResVO.setStoreOpenStartMinute_SUN("");
        }
        // Store営業時間_日曜日終業
        String StoreOpenEndTime_SUN = storeATMInfoDoc.getStoreOpenEndTime_SUN();
        if (Utils.isNotNullAndEmpty(StoreOpenEndTime_SUN)) {
            StoreOpenEndTime_SUN = cover(StoreOpenEndTime_SUN);
            store114atmDetailUpdateResVO.setStoreOpenEndHour_SUN(StoreOpenEndTime_SUN.substring(0, 2));
            store114atmDetailUpdateResVO.setStoreOpenEndMinute_SUN(StoreOpenEndTime_SUN.substring(3, 5));
        } else {
            store114atmDetailUpdateResVO.setStoreOpenEndHour_SUN("");
            store114atmDetailUpdateResVO.setStoreOpenEndMinute_SUN("");
        }
        store114atmDetailUpdateResVO.setTeleNumber(storeATMInfoDoc.getTeleNumber());
        store114atmDetailUpdateResVO.setTrustAgent(storeATMInfoDoc.getTrustAgent());
        store114atmDetailUpdateResVO.setTypeKbn(storeATMInfoDoc.getTypeKbn());
        return store114atmDetailUpdateResVO;
    }

    private IYoStoreATMDetailResVO iyo(Database db, StoreATMDetailReqVO detailReqVO) {
        String storeDetailLog = "(店舗ATM名：";
        IYoStoreATMDetailResVO iYoStoreATMDetailResVO = new IYoStoreATMDetailResVO();
        // 店舗ATM詳細情報取得

        IYoStoreATMInfoDoc iYoStoreATMInfoDoc = new IYoStoreATMInfoDoc();
        try {
            iYoStoreATMInfoDoc = (IYoStoreATMInfoDoc) repositoryUtil.find(db, detailReqVO.get_id(),
                    IYoStoreATMInfoDoc.class);
            storeDetailLog = storeDetailLog + iYoStoreATMInfoDoc.getStoreATMName();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMDETAIL_1001);
            throw new BusinessException(messages);
        }
        // 店舗コード_母店番号
        iYoStoreATMDetailResVO.setStoreNumber(iYoStoreATMInfoDoc.getStoreNumber());
        // 店舗名_（漢字）
        iYoStoreATMDetailResVO.setStoreATMName(iYoStoreATMInfoDoc.getStoreATMName());
        iYoStoreATMDetailResVO.setPoiStatus(iYoStoreATMInfoDoc.getPoiStatus());
        // 座標_経度

        iYoStoreATMDetailResVO.setLongitude(iYoStoreATMInfoDoc.getLongitude_t());

        // 座標_緯度

        iYoStoreATMDetailResVO.setLatitude(iYoStoreATMInfoDoc.getLatitude_t());

        // 所在地_住所

        iYoStoreATMDetailResVO.setAddress(iYoStoreATMInfoDoc.getAddress());

        iYoStoreATMDetailResVO.setDelStoreNumber(iYoStoreATMInfoDoc.getDelStoreNumber());

        // 店舗名_（カナ）
        iYoStoreATMDetailResVO.setKanaStoreATMName(iYoStoreATMInfoDoc.getKanaStoreATMName());
        iYoStoreATMDetailResVO.setTypeKbn(iYoStoreATMInfoDoc.getTypeKbn());
        // 電話番号

        iYoStoreATMDetailResVO.setTeleNumber(iYoStoreATMInfoDoc.getTeleNumber());

        // 所在地_郵便番号
        iYoStoreATMDetailResVO.setPostCode(iYoStoreATMInfoDoc.getPostCode());

        String windowOpenStartTime = iYoStoreATMInfoDoc.getWindowOpenStartTime();
        if (Utils.isNotNullAndEmpty(windowOpenStartTime)) {
            windowOpenStartTime = cover(windowOpenStartTime);
            iYoStoreATMDetailResVO.setWindowOpenStartHour(windowOpenStartTime.substring(0, 2));
            iYoStoreATMDetailResVO.setWindowOpenStartMinute(windowOpenStartTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setWindowOpenStartHour("");
            iYoStoreATMDetailResVO.setWindowOpenStartMinute("");
        }

        String windowOpenEndTime = iYoStoreATMInfoDoc.getWindowOpenEndTime();
        
        if (Utils.isNotNullAndEmpty(windowOpenEndTime)) {
            windowOpenEndTime = cover(windowOpenEndTime);
            iYoStoreATMDetailResVO.setWindowOpenEndHour(windowOpenEndTime.substring(0, 2));
            iYoStoreATMDetailResVO.setWindowOpenEndMinute(windowOpenEndTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setWindowOpenEndHour("");
            iYoStoreATMDetailResVO.setWindowOpenEndMinute("");
        }
        // 窓口営業時間_土曜日始業
        String windowOpenStartTime_SAT = iYoStoreATMInfoDoc.getWindowOpenStartTime_SAT();
        if (Utils.isNotNullAndEmpty(windowOpenStartTime_SAT)) {
            windowOpenStartTime_SAT = cover(windowOpenStartTime_SAT);
            iYoStoreATMDetailResVO.setWindowOpenStartHour_SAT(windowOpenStartTime_SAT.substring(0, 2));
            iYoStoreATMDetailResVO.setWindowOpenStartMinute_SAT(windowOpenStartTime_SAT.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setWindowOpenStartHour_SAT("");
            iYoStoreATMDetailResVO.setWindowOpenStartMinute_SAT("");
        }
        // 窓口営業時間_土曜日終業
        String windowOpenEndTime_SAT = iYoStoreATMInfoDoc.getWindowOpenEndTime_SAT();
        
        if (Utils.isNotNullAndEmpty(windowOpenEndTime_SAT)) {
            windowOpenEndTime_SAT = cover(windowOpenEndTime_SAT);
            iYoStoreATMDetailResVO.setWindowOpenEndHour_SAT(windowOpenEndTime_SAT.substring(0, 2));
            iYoStoreATMDetailResVO.setWindowOpenEndMinute_SAT(windowOpenEndTime_SAT.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setWindowOpenEndHour_SAT("");
            iYoStoreATMDetailResVO.setWindowOpenEndMinute_SAT("");
        }
        // 窓口営業時間_日曜日始業
        String windowOpenStartTime_SUN = iYoStoreATMInfoDoc.getWindowOpenStartTime_SUN();
        if (Utils.isNotNullAndEmpty(windowOpenStartTime_SUN)) {
            windowOpenStartTime_SUN = cover(windowOpenStartTime_SUN);
            iYoStoreATMDetailResVO.setWindowOpenStartHour_SUN(windowOpenStartTime_SUN.substring(0, 2));
            iYoStoreATMDetailResVO.setWindowOpenStartMinute_SUN(windowOpenStartTime_SUN.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setWindowOpenStartHour_SUN("");
            iYoStoreATMDetailResVO.setWindowOpenStartMinute_SUN("");
        }
        // 窓口営業時間_日曜日終業
        String windowOpenEndTime_SUN = iYoStoreATMInfoDoc.getWindowOpenEndTime_SUN();
       
        if (Utils.isNotNullAndEmpty(windowOpenEndTime_SUN)) {
            windowOpenEndTime_SUN = cover(windowOpenEndTime_SUN);
            iYoStoreATMDetailResVO.setWindowOpenEndHour_SUN(windowOpenEndTime_SUN.substring(0, 2));
            iYoStoreATMDetailResVO.setWindowOpenEndMinute_SUN(windowOpenEndTime_SUN.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setWindowOpenEndHour_SUN("");
            iYoStoreATMDetailResVO.setWindowOpenEndMinute_SUN("");
        }
        // ATM営業時間_平日始業
        String atmOpenStartTime = iYoStoreATMInfoDoc.getAtmOpenStartTime();
        if (Utils.isNotNullAndEmpty(atmOpenStartTime)) {
            atmOpenStartTime = cover(atmOpenStartTime);
            iYoStoreATMDetailResVO.setAtmOpenStartHour(atmOpenStartTime.substring(0, 2));
            iYoStoreATMDetailResVO.setAtmOpenStartMinute(atmOpenStartTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAtmOpenStartHour("");
            iYoStoreATMDetailResVO.setAtmOpenStartMinute("");
        }
        // ATM営業時間_平日終業
        String atmOpenEndTime = iYoStoreATMInfoDoc.getAtmOpenEndTime();
        if (Utils.isNotNullAndEmpty(atmOpenEndTime)) {
            atmOpenEndTime = cover(atmOpenEndTime);
            iYoStoreATMDetailResVO.setAtmOpenEndHour(atmOpenEndTime.substring(0, 2));
            iYoStoreATMDetailResVO.setAtmOpenEndMinute(atmOpenEndTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAtmOpenEndHour("");
            iYoStoreATMDetailResVO.setAtmOpenEndMinute("");
        }
        // ATM営業時間_土曜日始業
        String atmOpenStartTime_SAT = iYoStoreATMInfoDoc.getAtmOpenStartTime_SAT();
        if (Utils.isNotNullAndEmpty(atmOpenStartTime_SAT)) {
            atmOpenStartTime_SAT = cover(atmOpenStartTime_SAT);
            iYoStoreATMDetailResVO.setAtmOpenStartHour_SAT(atmOpenStartTime_SAT.substring(0, 2));
            iYoStoreATMDetailResVO.setAtmOpenStartMinute_SAT(atmOpenStartTime_SAT.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAtmOpenStartHour_SAT("");
            iYoStoreATMDetailResVO.setAtmOpenStartMinute_SAT("");
        }
        // ATM営業時間_土曜日終業
        String atmOpenEndTime_SAT = iYoStoreATMInfoDoc.getAtmOpenEndTime_SAT();
        if (Utils.isNotNullAndEmpty(atmOpenEndTime_SAT)) {
            atmOpenEndTime_SAT = cover(atmOpenEndTime_SAT);
            iYoStoreATMDetailResVO.setAtmOpenEndHour_SAT(atmOpenEndTime_SAT.substring(0, 2));
            iYoStoreATMDetailResVO.setAtmOpenEndMinute_SAT(atmOpenEndTime_SAT.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAtmOpenEndHour_SAT("");
            iYoStoreATMDetailResVO.setAtmOpenEndMinute_SAT("");
        }
        // ATM営業時間_日曜日始業
        String atmOpenStartTime_SUN = iYoStoreATMInfoDoc.getAtmOpenStartTime_SUN();
        if (Utils.isNotNullAndEmpty(atmOpenStartTime_SUN)) {
            atmOpenStartTime_SUN = cover(atmOpenStartTime_SUN);
            iYoStoreATMDetailResVO.setAtmOpenStartHour_SUN(atmOpenStartTime_SUN.substring(0, 2));
            iYoStoreATMDetailResVO.setAtmOpenStartMinute_SUN(atmOpenStartTime_SUN.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAtmOpenStartHour_SUN("");
            iYoStoreATMDetailResVO.setAtmOpenStartMinute_SUN("");
        }
        // ATM営業時間_日曜日終業
        String atmOpenEndTime_SUN = iYoStoreATMInfoDoc.getAtmOpenEndTime_SUN();
        if (Utils.isNotNullAndEmpty(atmOpenEndTime_SUN)) {
            atmOpenEndTime_SUN = cover(atmOpenEndTime_SUN);
            iYoStoreATMDetailResVO.setAtmOpenEndHour_SUN(atmOpenEndTime_SUN.substring(0, 2));
            iYoStoreATMDetailResVO.setAtmOpenEndMinute_SUN(atmOpenEndTime_SUN.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAtmOpenEndHour_SUN("");
            iYoStoreATMDetailResVO.setAtmOpenEndMinute_SUN("");
        }
        iYoStoreATMDetailResVO.setAtmComment1(iYoStoreATMInfoDoc.getAtmComment1());
        iYoStoreATMDetailResVO.setAtmComment2(iYoStoreATMInfoDoc.getAtmComment2());
        String conversionMachineStartTime = iYoStoreATMInfoDoc.getConversionMachineStartTime();
        if (Utils.isNotNullAndEmpty(conversionMachineStartTime)) {
            conversionMachineStartTime = cover(conversionMachineStartTime);
            iYoStoreATMDetailResVO.setConversionMachineStartHour(conversionMachineStartTime.substring(0, 2));
            iYoStoreATMDetailResVO.setConversionMachineStartMinute(conversionMachineStartTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setConversionMachineStartHour("");
            iYoStoreATMDetailResVO.setConversionMachineStartMinute("");
        }
        String conversionMachineEndTime = iYoStoreATMInfoDoc.getConversionMachineEndTime();
        if (Utils.isNotNullAndEmpty(conversionMachineEndTime)) {
            conversionMachineEndTime = cover(conversionMachineEndTime);
            iYoStoreATMDetailResVO.setConversionMachineEndHour(conversionMachineEndTime.substring(0, 2));
            iYoStoreATMDetailResVO.setConversionMachineEndMinute(conversionMachineEndTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setConversionMachineEndHour("");
            iYoStoreATMDetailResVO.setConversionMachineEndMinute("");
        }
        String conversionMachineStartTime_holiday = iYoStoreATMInfoDoc.getConversionMachineStartTime_holiday();
        if (Utils.isNotNullAndEmpty(conversionMachineStartTime_holiday)) {
            conversionMachineStartTime_holiday = cover(conversionMachineStartTime_holiday);
            iYoStoreATMDetailResVO.setConversionMachineStartHour_holiday(conversionMachineStartTime_holiday.substring(0, 2));
            iYoStoreATMDetailResVO.setConversionMachineStartMinute_holiday(conversionMachineStartTime_holiday.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setConversionMachineStartHour_holiday("");
            iYoStoreATMDetailResVO.setConversionMachineStartMinute_holiday("");
        }
        String conversionMachineEndTime_holiday = iYoStoreATMInfoDoc.getConversionMachineStartTime_holiday();
        if (Utils.isNotNullAndEmpty(conversionMachineEndTime_holiday)) {
            conversionMachineEndTime_holiday = cover(conversionMachineEndTime_holiday);
            iYoStoreATMDetailResVO.setConversionMachineEndHour_holiday(conversionMachineEndTime_holiday.substring(0, 2));
            iYoStoreATMDetailResVO.setConversionMachineEndMinute_holiday(conversionMachineEndTime_holiday.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setConversionMachineEndHour_holiday("");
            iYoStoreATMDetailResVO.setConversionMachineEndMinute_holiday("");
        }
        
        iYoStoreATMDetailResVO.setAccountMachineStartTime(iYoStoreATMInfoDoc.getAccountMachineStartTime());
        iYoStoreATMDetailResVO.setAccountMachineStartTime_SAT(iYoStoreATMInfoDoc.getAccountMachineStartTime_SAT());
        iYoStoreATMDetailResVO.setAccountMachineStartTime_SUN(iYoStoreATMInfoDoc.getAccountMachineStartTime_SUN());
        String autoLoanMachineStartTime = iYoStoreATMInfoDoc.getAutoLoanMachineStartTime();
        if (Utils.isNotNullAndEmpty(autoLoanMachineStartTime)) {
            autoLoanMachineStartTime = cover(autoLoanMachineStartTime);
            iYoStoreATMDetailResVO.setAutoLoanMachineStartHour(autoLoanMachineStartTime.substring(0, 2));
            iYoStoreATMDetailResVO.setAutoLoanMachineStartMinute(autoLoanMachineStartTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAutoLoanMachineStartHour("");
            iYoStoreATMDetailResVO.setAutoLoanMachineStartMinute("");
        }
        String autoLoanMachineEndTime = iYoStoreATMInfoDoc.getAutoLoanMachineEndTime();
        if (Utils.isNotNullAndEmpty(autoLoanMachineEndTime)) {
            autoLoanMachineEndTime = cover(autoLoanMachineEndTime);
            iYoStoreATMDetailResVO.setAutoLoanMachineEndHour(autoLoanMachineEndTime.substring(0, 2));
            iYoStoreATMDetailResVO.setAutoLoanMachineEndMinute(autoLoanMachineEndTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAutoLoanMachineEndHour("");
            iYoStoreATMDetailResVO.setAutoLoanMachineEndMinute("");
        }
        String autoLoanMachineStartTime_holiday = iYoStoreATMInfoDoc.getAutoLoanMachineStartTime_holiday();
        if (Utils.isNotNullAndEmpty(autoLoanMachineStartTime_holiday)) {
            autoLoanMachineStartTime_holiday = cover(autoLoanMachineStartTime_holiday);
            iYoStoreATMDetailResVO.setAutoLoanMachineStartHour_holiday(autoLoanMachineStartTime_holiday.substring(0, 2));
            iYoStoreATMDetailResVO.setAutoLoanMachineStartMinute_holiday(autoLoanMachineStartTime_holiday.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAutoLoanMachineStartHour_holiday("");
            iYoStoreATMDetailResVO.setAutoLoanMachineStartMinute_holiday("");
        }
        String autoLoanMachineEndTime_holiday = iYoStoreATMInfoDoc.getAutoLoanMachineEndTime_holiday();
        if (Utils.isNotNullAndEmpty(autoLoanMachineEndTime_holiday)) {
            autoLoanMachineEndTime_holiday = cover(autoLoanMachineEndTime_holiday);
            iYoStoreATMDetailResVO.setAutoLoanMachineEndHour_holiday(autoLoanMachineEndTime_holiday.substring(0, 2));
            iYoStoreATMDetailResVO.setAutoLoanMachineEndMinute_holiday(autoLoanMachineEndTime_holiday.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setAutoLoanMachineEndHour_holiday("");
            iYoStoreATMDetailResVO.setAutoLoanMachineEndMinute_holiday("");
        }
        iYoStoreATMDetailResVO.setAutoLoanMachineFlag(iYoStoreATMInfoDoc.getAutoLoanMachineFlag());
        
        String loanMachineStartTime = iYoStoreATMInfoDoc.getLoanMachineStartTime();
        if (Utils.isNotNullAndEmpty(loanMachineStartTime)) {
            loanMachineStartTime = cover(loanMachineStartTime);
            iYoStoreATMDetailResVO.setLoanMachineStartHour(loanMachineStartTime.substring(0, 2));
            iYoStoreATMDetailResVO.setLoanMachineStartMinute(loanMachineStartTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setLoanMachineStartHour("");
            iYoStoreATMDetailResVO.setLoanMachineStartMinute("");
        }
        String loanMachineEndTime = iYoStoreATMInfoDoc.getLoanMachineEndTime();
        if (Utils.isNotNullAndEmpty(loanMachineEndTime)) {
            loanMachineEndTime = cover(loanMachineEndTime);
            iYoStoreATMDetailResVO.setLoanMachineEndHour(loanMachineEndTime.substring(0, 2));
            iYoStoreATMDetailResVO.setLoanMachineEndMinute(loanMachineEndTime.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setLoanMachineEndHour("");
            iYoStoreATMDetailResVO.setLoanMachineEndMinute("");
        }
        String loanMachineStartTime_holiday = iYoStoreATMInfoDoc.getLoanMachineStartTime_holiday();
        if (Utils.isNotNullAndEmpty(loanMachineStartTime_holiday)) {
            loanMachineStartTime_holiday = cover(loanMachineStartTime_holiday);
            iYoStoreATMDetailResVO.setLoanMachineStartHour_holiday(loanMachineStartTime_holiday.substring(0, 2));
            iYoStoreATMDetailResVO.setLoanMachineStartMinute_holiday(loanMachineStartTime_holiday.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setLoanMachineStartHour_holiday("");
            iYoStoreATMDetailResVO.setLoanMachineStartMinute_holiday("");
        }
        String loanMachineEndTime_holiday = iYoStoreATMInfoDoc.getLoanMachineEndTime_holiday();
        if (Utils.isNotNullAndEmpty(loanMachineEndTime_holiday)) {
            loanMachineEndTime_holiday = cover(loanMachineEndTime_holiday);
            iYoStoreATMDetailResVO.setLoanMachineEndHour_holiday(loanMachineEndTime_holiday.substring(0, 2));
            iYoStoreATMDetailResVO.setLoanMachineEndMinute_holiday(loanMachineEndTime_holiday.substring(3, 5));
        } else {
            iYoStoreATMDetailResVO.setLoanMachineEndHour_holiday("");
            iYoStoreATMDetailResVO.setLoanMachineEndMinute_holiday("");
        }
        iYoStoreATMDetailResVO.setLoanMachineFlag(iYoStoreATMInfoDoc.getLoanMachineFlag());
        
        iYoStoreATMDetailResVO.setAed(iYoStoreATMInfoDoc.getAed());
        iYoStoreATMDetailResVO.setInternationalStore(iYoStoreATMInfoDoc.getInternationalStore());
        // 備考①
        if (iYoStoreATMInfoDoc.getComment1() != null) {
            iYoStoreATMDetailResVO.setComment1(iYoStoreATMInfoDoc.getComment1());
        }
        // 備考②
        if (iYoStoreATMInfoDoc.getComment2() != null) {
            iYoStoreATMDetailResVO.setComment2(iYoStoreATMInfoDoc.getComment2());
        }
        // ATM設置台数
        iYoStoreATMDetailResVO.setAtmCount(iYoStoreATMInfoDoc.getAtmCount());
        iYoStoreATMDetailResVO.setParkCount(iYoStoreATMInfoDoc.getParkCount());
        iYoStoreATMDetailResVO.setToilet(iYoStoreATMInfoDoc.getToilet());
        // 駐車場_備考
        if (iYoStoreATMInfoDoc.getParkComment() != null) {
            iYoStoreATMDetailResVO.setParkComment(iYoStoreATMInfoDoc.getParkComment());
        }
        iYoStoreATMDetailResVO.setServiceConversionStore(iYoStoreATMInfoDoc.getServiceConversionStore());
        iYoStoreATMDetailResVO.setWheelChair(iYoStoreATMInfoDoc.getWheelChair());
        iYoStoreATMDetailResVO.setWheelChairStore(iYoStoreATMInfoDoc.getWheelChairStore());
        iYoStoreATMDetailResVO.setCurrentStation1(iYoStoreATMInfoDoc.getCurrentStation1());
        iYoStoreATMDetailResVO.setCurrentStation2(iYoStoreATMInfoDoc.getCurrentStation2());
        iYoStoreATMDetailResVO.setCurrentStation3(iYoStoreATMInfoDoc.getCurrentStation3());
        iYoStoreATMDetailResVO.setCurrentStationDistance1(iYoStoreATMInfoDoc.getCurrentStationDistance1());
        iYoStoreATMDetailResVO.setCurrentStationDistance2(iYoStoreATMInfoDoc.getCurrentStationDistance2());
        iYoStoreATMDetailResVO.setCurrentStationDistance3(iYoStoreATMInfoDoc.getCurrentStationDistance3());
        iYoStoreATMDetailResVO.setCurrentStationTime1(iYoStoreATMInfoDoc.getCurrentStationTime1());
        iYoStoreATMDetailResVO.setCurrentStationTime2(iYoStoreATMInfoDoc.getCurrentStationTime2());
        iYoStoreATMDetailResVO.setCurrentStationTime3(iYoStoreATMInfoDoc.getCurrentStationTime3());
        iYoStoreATMDetailResVO.setMessage(iYoStoreATMInfoDoc.getMessage());
        iYoStoreATMDetailResVO.setImage(iYoStoreATMInfoDoc.getImage());
        iYoStoreATMDetailResVO.setImageUrl(iYoStoreATMInfoDoc.getImageUrl());
        iYoStoreATMDetailResVO.setIcon(iYoStoreATMInfoDoc.getIcon());
        iYoStoreATMDetailResVO.setFinalUpdateTime(iYoStoreATMInfoDoc.getFinalUpdateTime());
        iYoStoreATMDetailResVO.setDelFlg(iYoStoreATMInfoDoc.getDelFlg());
        iYoStoreATMDetailResVO.setDelFlg(iYoStoreATMInfoDoc.getDelFlg()); 
        return iYoStoreATMDetailResVO;
    }

    private StoreATMDetailResVO hiroshima(Database db, StoreATMDetailReqVO detailReqVO) {
        String storeDetailLog = "(店舗ATM名：";
        StoreATMDetailResVO storeATMDetailResVO = new StoreATMDetailResVO();
        // 店舗ATM詳細情報取得
        StoreATMInfoDoc storeATMInfoDoc = new StoreATMInfoDoc();
        try {
            storeATMInfoDoc = (StoreATMInfoDoc) repositoryUtil.find(db, detailReqVO.get_id(), StoreATMInfoDoc.class);
            storeDetailLog = storeDetailLog + storeATMInfoDoc.getStoreATMName();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMDETAIL_1001);
            throw new BusinessException(messages);
        }

        // 戻り値を設定
        // 姓名
        String storeATMName = storeATMInfoDoc.getStoreATMName();
        storeATMDetailResVO.setStoreATMName(storeATMName);
        // 姓名カナ
        String kanaStoreATMName = storeATMInfoDoc.getKanaStoreATMName();
        storeATMDetailResVO.setKanaStoreATMName(kanaStoreATMName);

        // 店舗区分
        storeATMDetailResVO.setTypeKbn(storeATMInfoDoc.getTypeKbn());
        // 店舗コード_母店番号
        storeATMDetailResVO.setStoreNumber(storeATMInfoDoc.getStoreNumber());
        // 店舗コード_出張所枝番
        storeATMDetailResVO.setSubStoreNumber(storeATMInfoDoc.getSubStoreNumber());
        // 店舗コード_ATM枝番
        storeATMDetailResVO.setAtmStoreNumber(storeATMInfoDoc.getAtmStoreNumber());
        // 店舗名_（漢字）
        storeATMDetailResVO.setStoreATMName(storeATMInfoDoc.getStoreATMName());
        // 店舗名_（カナ）
        storeATMDetailResVO.setKanaStoreATMName(storeATMInfoDoc.getKanaStoreATMName());
        // 所在地_郵便番号
        storeATMDetailResVO.setPostCode(storeATMInfoDoc.getPostCode());
        // 所在地_住所
        storeATMDetailResVO.setAddress(storeATMInfoDoc.getAddress());
        // 所在地_ランドマーク
        storeATMDetailResVO.setLandMark(storeATMInfoDoc.getLandMark());
        // 電話番号
        storeATMDetailResVO.setTeleNumber(storeATMInfoDoc.getTeleNumber());
        // 窓口営業時間_平日始業
        String windowOpenStartTime = storeATMInfoDoc.getWindowOpenStartTime();
        if (Utils.isNotNullAndEmpty(windowOpenStartTime)) {
            windowOpenStartTime = cover(windowOpenStartTime);
            storeATMDetailResVO.setWindowStartHour(windowOpenStartTime.substring(0, 2));
            storeATMDetailResVO.setWindowStartMinute(windowOpenStartTime.substring(3, 5));
        } else {
            storeATMDetailResVO.setWindowStartHour("");
            storeATMDetailResVO.setWindowStartMinute("");
        }

        // 窓口営業時間_平日終業
        String windowOpenEndTime = storeATMInfoDoc.getWindowOpenEndTime();
        if (Utils.isNotNullAndEmpty(windowOpenEndTime) && "※"
                .equals(windowOpenEndTime.substring(windowOpenEndTime.length() - 1, windowOpenEndTime.length()))) {
            windowOpenEndTime = windowOpenEndTime.substring(0, windowOpenEndTime.length() - 1);
            storeATMDetailResVO.setWorkDayFlag("1");
        } else {
            storeATMDetailResVO.setWorkDayFlag("0");
        }
        if (Utils.isNotNullAndEmpty(windowOpenEndTime)) {
            windowOpenEndTime = cover(windowOpenEndTime);
            storeATMDetailResVO.setWindowEndHour(windowOpenEndTime.substring(0, 2));
            storeATMDetailResVO.setWindowEndMinute(windowOpenEndTime.substring(3, 5));
        } else {
            storeATMDetailResVO.setWindowEndHour("");
            storeATMDetailResVO.setWindowEndMinute("");
        }
        // 窓口営業時間_土曜日始業
        String windowOpenStartTime_SAT = storeATMInfoDoc.getWindowOpenStartTime_SAT();
        if (Utils.isNotNullAndEmpty(windowOpenStartTime_SAT)) {
            windowOpenStartTime_SAT = cover(windowOpenStartTime_SAT);
            storeATMDetailResVO.setWindowStartHour_SAT(windowOpenStartTime_SAT.substring(0, 2));
            storeATMDetailResVO.setWindowStartMinute_SAT(windowOpenStartTime_SAT.substring(3, 5));
        } else {
            storeATMDetailResVO.setWindowStartHour_SAT("");
            storeATMDetailResVO.setWindowStartMinute_SAT("");
        }
        // 窓口営業時間_土曜日終業
        String windowOpenEndTime_SAT = storeATMInfoDoc.getWindowOpenEndTime_SAT();
        if (Utils.isNotNullAndEmpty(windowOpenEndTime_SAT) && "※".equals(
                windowOpenEndTime_SAT.substring(windowOpenEndTime_SAT.length() - 1, windowOpenEndTime_SAT.length()))) {
            windowOpenEndTime_SAT = windowOpenEndTime_SAT.substring(0, windowOpenEndTime_SAT.length() - 1);
            storeATMDetailResVO.setSaturdayFlag("1");
        } else {
            storeATMDetailResVO.setSaturdayFlag("0");
        }
        if (Utils.isNotNullAndEmpty(windowOpenEndTime_SAT)) {
            windowOpenEndTime_SAT = cover(windowOpenEndTime_SAT);
            storeATMDetailResVO.setWindowEndHour_SAT(windowOpenEndTime_SAT.substring(0, 2));
            storeATMDetailResVO.setWindowEndMinute_SAT(windowOpenEndTime_SAT.substring(3, 5));
        } else {
            storeATMDetailResVO.setWindowEndHour_SAT("");
            storeATMDetailResVO.setWindowEndMinute_SAT("");
        }
        // 窓口営業時間_日曜日始業
        String windowOpenStartTime_SUN = storeATMInfoDoc.getWindowOpenStartTime_SUN();
        if (Utils.isNotNullAndEmpty(windowOpenStartTime_SUN)) {
            windowOpenStartTime_SUN = cover(windowOpenStartTime_SUN);
            storeATMDetailResVO.setWindowStartHour_SUN(windowOpenStartTime_SUN.substring(0, 2));
            storeATMDetailResVO.setWindowStartMinute_SUN(windowOpenStartTime_SUN.substring(3, 5));
        } else {
            storeATMDetailResVO.setWindowStartHour_SUN("");
            storeATMDetailResVO.setWindowStartMinute_SUN("");
        }
        // 窓口営業時間_日曜日終業
        String windowOpenEndTime_SUN = storeATMInfoDoc.getWindowOpenEndTime_SUN();
        if (Utils.isNotNullAndEmpty(windowOpenEndTime_SUN) && "※".equals(
                windowOpenEndTime_SUN.substring(windowOpenEndTime_SUN.length() - 1, windowOpenEndTime_SUN.length()))) {
            windowOpenEndTime_SUN = windowOpenEndTime_SUN.substring(0, windowOpenEndTime_SUN.length() - 1);
            storeATMDetailResVO.setSundayFlag("1");
        } else {
            storeATMDetailResVO.setSundayFlag("0");
        }
        if (Utils.isNotNullAndEmpty(windowOpenEndTime_SUN)) {
            windowOpenEndTime_SUN = cover(windowOpenEndTime_SUN);
            storeATMDetailResVO.setWindowEndHour_SUN(windowOpenEndTime_SUN.substring(0, 2));
            storeATMDetailResVO.setWindowEndMinute_SUN(windowOpenEndTime_SUN.substring(3, 5));
        } else {
            storeATMDetailResVO.setWindowEndHour_SUN("");
            storeATMDetailResVO.setWindowEndMinute_SUN("");
        }
        // ATM営業時間_平日始業
        String atmOpenStartTime = storeATMInfoDoc.getAtmOpenStartTime();
        if (Utils.isNotNullAndEmpty(atmOpenStartTime)) {
            atmOpenStartTime = cover(atmOpenStartTime);
            storeATMDetailResVO.setAtmStartHour(atmOpenStartTime.substring(0, 2));
            storeATMDetailResVO.setAtmStartMinute(atmOpenStartTime.substring(3, 5));
        } else {
            storeATMDetailResVO.setAtmStartHour("");
            storeATMDetailResVO.setAtmStartMinute("");
        }
        // ATM営業時間_平日終業
        String atmOpenEndTime = storeATMInfoDoc.getAtmOpenEndTime();
        if (Utils.isNotNullAndEmpty(atmOpenEndTime)) {
            atmOpenEndTime = cover(atmOpenEndTime);
            storeATMDetailResVO.setAtmEndHour(atmOpenEndTime.substring(0, 2));
            storeATMDetailResVO.setAtmEndMinute(atmOpenEndTime.substring(3, 5));
        } else {
            storeATMDetailResVO.setAtmEndHour("");
            storeATMDetailResVO.setAtmEndMinute("");
        }
        // ATM営業時間_土曜日始業
        String atmOpenStartTime_SAT = storeATMInfoDoc.getAtmOpenStartTime_SAT();
        if (Utils.isNotNullAndEmpty(atmOpenStartTime_SAT)) {
            atmOpenStartTime_SAT = cover(atmOpenStartTime_SAT);
            storeATMDetailResVO.setAtmStartHour_SAT(atmOpenStartTime_SAT.substring(0, 2));
            storeATMDetailResVO.setAtmStartMinute_SAT(atmOpenStartTime_SAT.substring(3, 5));
        } else {
            storeATMDetailResVO.setAtmStartHour_SAT("");
            storeATMDetailResVO.setAtmStartMinute_SAT("");
        }
        // ATM営業時間_土曜日終業
        String atmOpenEndTime_SAT = storeATMInfoDoc.getAtmOpenEndTime_SAT();
        if (Utils.isNotNullAndEmpty(atmOpenEndTime_SAT)) {
            atmOpenEndTime_SAT = cover(atmOpenEndTime_SAT);
            storeATMDetailResVO.setAtmEndHour_SAT(atmOpenEndTime_SAT.substring(0, 2));
            storeATMDetailResVO.setAtmEndMinute_SAT(atmOpenEndTime_SAT.substring(3, 5));
        } else {
            storeATMDetailResVO.setAtmEndHour_SAT("");
            storeATMDetailResVO.setAtmEndMinute_SAT("");
        }
        // ATM営業時間_日曜日始業
        String atmOpenStartTime_SUN = storeATMInfoDoc.getAtmOpenStartTime_SUN();
        if (Utils.isNotNullAndEmpty(atmOpenStartTime_SUN)) {
            atmOpenStartTime_SUN = cover(atmOpenStartTime_SUN);
            storeATMDetailResVO.setAtmStartHour_SUN(atmOpenStartTime_SUN.substring(0, 2));
            storeATMDetailResVO.setAtmStartMinute_SUN(atmOpenStartTime_SUN.substring(3, 5));
        } else {
            storeATMDetailResVO.setAtmStartHour_SUN("");
            storeATMDetailResVO.setAtmStartMinute_SUN("");
        }
        // ATM営業時間_日曜日終業
        String atmOpenEndTime_SUN = storeATMInfoDoc.getAtmOpenEndTime_SUN();
        if (Utils.isNotNullAndEmpty(atmOpenEndTime_SUN)) {
            atmOpenEndTime_SUN = cover(atmOpenEndTime_SUN);
            storeATMDetailResVO.setAtmEndHour_SUN(atmOpenEndTime_SUN.substring(0, 2));
            storeATMDetailResVO.setAtmEndMinute_SUN(atmOpenEndTime_SUN.substring(3, 5));
        } else {
            storeATMDetailResVO.setAtmEndHour_SUN("");
            storeATMDetailResVO.setAtmEndMinute_SUN("");
        }
        // AＴＭ設置台数
        storeATMDetailResVO.setAtmCount(storeATMInfoDoc.getAtmCount());
        // 駐車場_有無
        storeATMDetailResVO.setPark(storeATMInfoDoc.getPark());
        // 駐車場_障害者対応
        storeATMDetailResVO.setParkServiceForDisabled(storeATMInfoDoc.getParkServiceForDisabled());
        // 駐車場_備考
        storeATMDetailResVO.setParkComment(storeATMInfoDoc.getParkComment());
        // ひろぎんウツミ屋
        storeATMDetailResVO.setHirginUtsumiya(storeATMInfoDoc.getHirginUtsumiya());
        // 商品サービス_外貨両替（ﾄﾞﾙ、ﾕｰﾛ）
        storeATMDetailResVO.setServiceDollarEuro(storeATMInfoDoc.getServiceDollarEuro());
        // 商品サービス_外貨両替（ｱｼﾞｱ通貨）
        storeATMDetailResVO.setServiceAsia(storeATMInfoDoc.getServiceAsia());
        // 商品サービス_外貨両替（その他）
        storeATMDetailResVO.setServiceOther(storeATMInfoDoc.getServiceOther());
        // 商品サービス_外貨買取
        storeATMDetailResVO.setServiceForeignExchange(storeATMInfoDoc.getServiceForeignExchange());
        // 商品サービス_投資信託
        storeATMDetailResVO.setServiceInvestmentTrust(storeATMInfoDoc.getServiceInvestmentTrust());
        // 商品サービス_年金保険
        storeATMDetailResVO.setServicePensionInsurance(storeATMInfoDoc.getServicePensionInsurance());
        // 商品サービス_金融商品仲介（みずほ証券）
        storeATMDetailResVO.setServiceMizuho(storeATMInfoDoc.getServiceMizuho());
        // 商品サービス_金融商品仲介（ひろぎんウツミ屋）
        storeATMDetailResVO.setServiceHirginUtsumiya(storeATMInfoDoc.getServiceHirginUtsumiya());
        // 商品サービス_全自動貸金庫
        storeATMDetailResVO.setServiceAutoSafeDepositBox(storeATMInfoDoc.getServiceAutoSafeDepositBox());
        // 商品サービス_一般貸金庫
        storeATMDetailResVO.setServiceSafeDepositBox(storeATMInfoDoc.getServiceSafeDepositBox());
        // 商品サービス_ｾｰﾌﾃｨｹｰｽ
        storeATMDetailResVO.setServiceSafeBox(storeATMInfoDoc.getServiceSafeBox());
        // 店舗設備_IB専用PC
        storeATMDetailResVO.setInternationalTradePC(storeATMInfoDoc.getInternationalTradePC());
        // 店舗設備_ｷｯｽﾞｽﾍﾟｰｽ
        storeATMDetailResVO.setChildrenSpace(storeATMInfoDoc.getChildrenSpace());
        // バリアフリー_視覚障害対応ATM
        storeATMDetailResVO.setBarrierFreeVisualImpairment(storeATMInfoDoc.getBarrierFreeVisualImpairment());
        // バリアフリー_点字ﾌﾞﾛｯｸ
        storeATMDetailResVO.setBarrierFreeBrailleBlock(storeATMInfoDoc.getBarrierFreeBrailleBlock());
        // バリアフリー_音声ｶﾞｲﾄﾞ
        storeATMDetailResVO.setBarrierFreeVoiceGuide(storeATMInfoDoc.getBarrierFreeVoiceGuide());
        // バリアフリー_AED
        storeATMDetailResVO.setBarrierFreeAED(storeATMInfoDoc.getBarrierFreeAED());
        // ATM機能_振込
        storeATMDetailResVO.setAtmFunctionTransfer(storeATMInfoDoc.getAtmFunctionTransfer());
        // ATM機能_硬貨入出金
        storeATMDetailResVO.setAtmFunctionCoinAccess(storeATMInfoDoc.getAtmFunctionCoinAccess());
        // ATM機能_宝くじｻｰﾋﾞｽ
        storeATMDetailResVO.setAtmFunctionLotteryService(storeATMInfoDoc.getAtmFunctionLotteryService());
        // ATM機能_手のひら認証
        storeATMDetailResVO.setAtmFunctionPalmAuthentication(storeATMInfoDoc.getAtmFunctionPalmAuthentication());
        // ATM機能_IC対応
        storeATMDetailResVO.setAtmFunctionIC(storeATMInfoDoc.getAtmFunctionIC());
        // ATM機能_PASPYチャージ
        storeATMDetailResVO.setAtmFunctionPASPY(storeATMInfoDoc.getAtmFunctionPASPY());
        // ATM機能_他行幹事
        storeATMDetailResVO.setAtmFunctionOtherBankingAffairs(storeATMInfoDoc.getAtmFunctionOtherBankingAffairs());
        // 座標_経度
        storeATMDetailResVO.setLongitude(storeATMInfoDoc.getLongitude());
        // 座標_緯度
        storeATMDetailResVO.setLatitude(storeATMInfoDoc.getLatitude());
        // 備考①
        storeATMDetailResVO.setComment1(storeATMInfoDoc.getComment1());
        // 備考②
        storeATMDetailResVO.setComment2(storeATMInfoDoc.getComment2());
        // 備考③
        storeATMDetailResVO.setComment3(storeATMInfoDoc.getComment3());
        // 備考④
        storeATMDetailResVO.setComment4(storeATMInfoDoc.getComment4());
        // 備考⑤
        storeATMDetailResVO.setComment5(storeATMInfoDoc.getComment5());
        // 開始日時
        storeATMDetailResVO.setStartDateTime(storeATMInfoDoc.getStartDateTime());
        // 終了日時

        storeATMDetailResVO.setDelFlg(storeATMInfoDoc.getDelFlg());
        storeATMDetailResVO.setEndDateTime(storeATMInfoDoc.getEndDateTime());
        actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_5 + storeDetailLog + ")", db);
        return storeATMDetailResVO;
    }

    /**
     * String formartメソッド。
     * 
     * @param 処理前
     *            value
     * @return 処理後 str
     */
    private String cover(String value) {
        String str = "";
        if (value.length() < 5) {
            str = "0" + value;
        } else {
            str = value;
        }
        return str;
    }
}
