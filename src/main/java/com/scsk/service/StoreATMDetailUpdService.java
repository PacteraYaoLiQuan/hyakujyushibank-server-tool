package com.scsk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.IYoStoreATMInfoDoc;
import com.scsk.model.Store114ATMInfoDoc;
import com.scsk.model.StoreATMInfoDoc;
import com.scsk.model.geo.GeometryDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.StoreATMDetailUpdateReqVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.response.vo.IYoStoreATMDetailUpdateResVO;
import com.scsk.response.vo.Store114ATMDetailUpdateResVO;
import com.scsk.response.vo.StoreATMDetailUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PhoneNumberUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;

/**
 * 店舗ATMデータ更新メソッド。
 * 
 * @return ResponseEntity 戻るデータオブジェクト
 */
@Service
public class StoreATMDetailUpdService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Value("${bank_cd}")
    private String bank_cd;
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO storeATMDetailStatusReqVO) throws Exception {

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
    protected BaseResVO doExecute(CloudantClient client, BaseResVO storeATMDetailReqVO) throws Exception {

        StoreATMDetailUpdateResVO output = new StoreATMDetailUpdateResVO();
        IYoStoreATMDetailUpdateResVO iYoStoreATMDetailUpdateResVO = new IYoStoreATMDetailUpdateResVO();
        Store114ATMDetailUpdateResVO store114atmDetailUpdateResVO=new Store114ATMDetailUpdateResVO();
        StoreATMDetailUpdateReqVO storeATMDetailStatusReqVO = new StoreATMDetailUpdateReqVO();
        storeATMDetailStatusReqVO = (StoreATMDetailUpdateReqVO) storeATMDetailReqVO;
        IYoStoreATMInfoDoc iYoStoreATMInfoDoc = new IYoStoreATMInfoDoc();
        Store114ATMInfoDoc store114atmInfoDoc=new Store114ATMInfoDoc();
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        String storeUpdLog = "(店舗ATM名：";
        StoreATMInfoDoc storeATMInfoDoc = new StoreATMInfoDoc();
        if (storeATMDetailStatusReqVO.getModeType().equals("2")) {
            if ("0169".equals(bank_cd)) {
                // 店舗ATM詳細情報取得
                try {
                    storeATMInfoDoc = (StoreATMInfoDoc) repositoryUtil.find(db, storeATMDetailStatusReqVO.get_id(),
                            StoreATMInfoDoc.class);
                    storeUpdLog = storeUpdLog + storeATMDetailStatusReqVO.getStoreATMName();
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMDETAIL_1001);
                    throw new BusinessException(messages);
                }
            } else if ("0174".equals(bank_cd)) {
                // 店舗ATM詳細情報取得
                try {
                    iYoStoreATMInfoDoc = (IYoStoreATMInfoDoc) repositoryUtil.find(db,
                            storeATMDetailStatusReqVO.get_id(), IYoStoreATMInfoDoc.class);
                    storeUpdLog = storeUpdLog + storeATMDetailStatusReqVO.getStoreATMName();
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMDETAIL_1001);
                    throw new BusinessException(messages);
                }
            }else if("0173".equals(bank_cd)){
             // 店舗ATM詳細情報取得
                try {
                    store114atmInfoDoc = (Store114ATMInfoDoc) repositoryUtil.find(db,
                            storeATMDetailStatusReqVO.get_id(), Store114ATMInfoDoc.class);
                    storeUpdLog = storeUpdLog + storeATMDetailStatusReqVO.getStoreName();
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMDETAIL_1001);
                    throw new BusinessException(messages);
                }
            }
        }
        // 店舗ATM詳細情報取得
        if ("0169".equals(bank_cd)) {
            output = hiroshima(db, storeATMDetailStatusReqVO, storeATMInfoDoc, storeUpdLog);
            return output;
        } else if ("0174".equals(bank_cd)) {
            iYoStoreATMDetailUpdateResVO = iyo(db, storeATMDetailStatusReqVO, iYoStoreATMInfoDoc, storeUpdLog);
            return iYoStoreATMDetailUpdateResVO;
        }else if("0173".equals(bank_cd)){
            store114atmDetailUpdateResVO=hyakujyushi(db, storeATMDetailStatusReqVO, store114atmInfoDoc, storeUpdLog);
            return store114atmDetailUpdateResVO;
        }

        return new BaseResVO();
    }

    private Store114ATMDetailUpdateResVO hyakujyushi(Database db, BaseResVO storeATMDetailReqVO,
            Store114ATMInfoDoc store114atmInfoDoc, String storeUpdLog) {
        Store114ATMDetailUpdateResVO iYoStoreATMDetailUpdateResVO = new Store114ATMDetailUpdateResVO();
        StoreATMDetailUpdateReqVO storeATMDetailStatusReqVO = new StoreATMDetailUpdateReqVO();
        storeATMDetailStatusReqVO = (StoreATMDetailUpdateReqVO) storeATMDetailReqVO;
        store114atmInfoDoc.setAccountMachine(storeATMDetailStatusReqVO.getAccountMachine());
        store114atmInfoDoc.setAddress(storeATMDetailStatusReqVO.getAddress());
        store114atmInfoDoc.setArea(storeATMDetailStatusReqVO.getArea());
        store114atmInfoDoc.setAtmAddress(storeATMDetailStatusReqVO.getAtmAddress());
        String atmOpenEndTime=timeFormart(storeATMDetailStatusReqVO.getAtmEndHour(),storeATMDetailStatusReqVO.getAtmEndMinute());
        store114atmInfoDoc.setAtmOpenEndTime(atmOpenEndTime);
        String atmOpenEndTime_SAT=timeFormart(storeATMDetailStatusReqVO.getAtmEndHour_SAT(), storeATMDetailStatusReqVO.getAtmEndMinute_SAT());
        store114atmInfoDoc.setAtmOpenEndTime_SAT(atmOpenEndTime_SAT);
        String atmOpenEndTime_SUN=timeFormart(storeATMDetailStatusReqVO.getAtmEndHour_SUN(), storeATMDetailStatusReqVO.getAtmEndMinute_SUN());
        store114atmInfoDoc.setAtmOpenEndTime_SUN(atmOpenEndTime_SUN);
        String atmOpenStartTime=timeFormart(storeATMDetailStatusReqVO.getAtmStartHour(), storeATMDetailStatusReqVO.getAtmStartMinute());
        store114atmInfoDoc.setAtmOpenStartTime(atmOpenStartTime);
        String atmOpenStartTime_SAT=timeFormart(storeATMDetailStatusReqVO.getAtmStartHour_SAT(), storeATMDetailStatusReqVO.getAtmStartMinute_SAT());
        store114atmInfoDoc.setAtmOpenStartTime_SAT(atmOpenStartTime_SAT);
        String atmOpenStartTime_SUN=timeFormart(storeATMDetailStatusReqVO.getAtmOpenStartHour_SUN(), storeATMDetailStatusReqVO.getAtmOpenStartMinute_SUN());
        store114atmInfoDoc.setAtmOpenStartTime_SUN(atmOpenStartTime_SUN);
        store114atmInfoDoc.setConversionMachine(storeATMDetailStatusReqVO.getConversionMachine());
        store114atmInfoDoc.setLoanMachine(storeATMDetailStatusReqVO.getLoanMachine());
        store114atmInfoDoc.setLatitude(storeATMDetailStatusReqVO.getLatitude());
        store114atmInfoDoc.setLongitude(storeATMDetailStatusReqVO.getLongitude());
        store114atmInfoDoc.setManageStore(storeATMDetailStatusReqVO.getManageStore());
        store114atmInfoDoc.setPoliceCompany(storeATMDetailStatusReqVO.getPoliceCompany());
        store114atmInfoDoc.setPostCode(storeATMDetailStatusReqVO.getPostCode());
        store114atmInfoDoc.setServiceConversionIn(storeATMDetailStatusReqVO.getServiceConversionIn());
        store114atmInfoDoc.setServiceConversionOut(storeATMDetailStatusReqVO.getServiceConversionOut());
        store114atmInfoDoc.setStoreName(storeATMDetailStatusReqVO.getStoreName());
        store114atmInfoDoc.setStoreNumber(storeATMDetailStatusReqVO.getStoreNumber());
        String storeOpenEndTime=timeFormart(storeATMDetailStatusReqVO.getStoreOpenEndHour(), storeATMDetailStatusReqVO.getStoreOpenEndMinute());
        store114atmInfoDoc.setStoreOpenEndTime(storeOpenEndTime);
        String storeOpenEndTime_SAT=timeFormart(storeATMDetailStatusReqVO.getStoreOpenEndHour_SAT(), storeATMDetailStatusReqVO.getStoreOpenEndMinute_SAT());
        store114atmInfoDoc.setStoreOpenEndTime_SAT(storeOpenEndTime_SAT);
        String storeOpenEndTime_SUN=timeFormart(storeATMDetailStatusReqVO.getStoreOpenEndHour_SUN(),storeATMDetailStatusReqVO.getStoreOpenEndMinute_SUN());
        store114atmInfoDoc.setStoreOpenEndTime_SUN(storeOpenEndTime_SUN);
        String storeOpenStartTime=timeFormart(storeATMDetailStatusReqVO.getStoreOpenStartHour(), storeATMDetailStatusReqVO.getStoreOpenStartMinute());
        store114atmInfoDoc.setStoreOpenStartTime(storeOpenStartTime);
        String storeOpenStartTime_SAT=timeFormart(storeATMDetailStatusReqVO.getStoreOpenStartHour_SAT(), storeATMDetailStatusReqVO.getStoreOpenStartMinute_SAT());
        store114atmInfoDoc.setStoreOpenStartTime_SAT(storeOpenStartTime_SAT);
        String storeOpenStartTime_SUN=timeFormart(storeATMDetailStatusReqVO.getStoreOpenStartHour_SUN(), storeATMDetailStatusReqVO.getStoreOpenStartMinute_SUN());
        store114atmInfoDoc.setStoreOpenStartTime_SUN(storeOpenStartTime_SUN);
        store114atmInfoDoc.setTeleNumber(storeATMDetailStatusReqVO.getTeleNumber());
        store114atmInfoDoc.setTrustAgent(storeATMDetailStatusReqVO.getTrustAgent());
        store114atmInfoDoc.setTypeKbn(storeATMDetailStatusReqVO.getTypeKbn());
        String delFlg = storeATMDetailStatusReqVO.getDelFlg();
        store114atmInfoDoc.setDelFlg(delFlg);
        GeometryDoc geometryDoc = new GeometryDoc();
        double lon = Double.parseDouble(storeATMDetailStatusReqVO.getLongitude());
        double lat = Double.parseDouble(storeATMDetailStatusReqVO.getLatitude());
        double[] coordinate = { lon, lat };
        geometryDoc.setType("Point");
        geometryDoc.setCoordinates(coordinate);
        store114atmInfoDoc.setGeometry(geometryDoc);
        // DBに更新
        if (storeATMDetailStatusReqVO.getModeType().equals("2")) {
            try {
                // 店舗を更新
                repositoryUtil.update(db, store114atmInfoDoc);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMDETAIL_1002);
                throw new BusinessException(messages);
            }
            actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_6 + storeUpdLog + ")", db);
        } else {
            storeUpdLog = storeUpdLog + storeATMDetailStatusReqVO.getStoreName();
            String queryKey = "storeNumber:\"" + storeATMDetailStatusReqVO.getStoreNumber() + "\" ";
            List<Store114ATMInfoDoc> storeATMList = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_STORE_STOREATM_SEARCHINFO, queryKey, Store114ATMInfoDoc.class);
            if (storeATMList != null && storeATMList.size() != 0) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_IYOSTOREATMDETAIL_1001);
                throw new BusinessException(messages);
            } else {
                try {
                    // 店舗を登録
                    repositoryUtil.save(db, store114atmInfoDoc, delFlg);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMDETAIL_1003);
                    throw new BusinessException(messages);
                }
            }
            actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_4 + storeUpdLog + ")", db);
        }
        iYoStoreATMDetailUpdateResVO.setTypeKbn(store114atmInfoDoc.getTypeKbn());
        iYoStoreATMDetailUpdateResVO.setAddress(store114atmInfoDoc.getAddress());
        iYoStoreATMDetailUpdateResVO.setTeleNumber(store114atmInfoDoc.getTeleNumber());
        iYoStoreATMDetailUpdateResVO.setStoreOpenStartTime(store114atmInfoDoc.getStoreOpenStartTime());

        return iYoStoreATMDetailUpdateResVO;
    }

    private IYoStoreATMDetailUpdateResVO iyo(Database db, BaseResVO storeATMDetailReqVO,
            IYoStoreATMInfoDoc storeATMInfoDoc, String storeUpdLog) {
        IYoStoreATMDetailUpdateResVO iYoStoreATMDetailUpdateResVO = new IYoStoreATMDetailUpdateResVO();
        StoreATMDetailUpdateReqVO storeATMDetailStatusReqVO = new StoreATMDetailUpdateReqVO();
        storeATMDetailStatusReqVO = (StoreATMDetailUpdateReqVO) storeATMDetailReqVO;

        // 店舗コード_母店番号
        storeATMInfoDoc.setStoreNumber(storeATMDetailStatusReqVO.getStoreNumber());
        // 店舗名_（漢字）
        storeATMInfoDoc.setStoreATMName(storeATMDetailStatusReqVO.getStoreATMName());
        storeATMInfoDoc.setPoiStatus(storeATMDetailStatusReqVO.getPoiStatus());
        
        // 所在地_住所
        if (storeATMDetailStatusReqVO.getAddress() != null) {
            storeATMInfoDoc.setAddress(storeATMDetailStatusReqVO.getAddress());
        }
        storeATMInfoDoc.setDelStoreNumber(storeATMDetailStatusReqVO.getDelStoreNumber());

        // 店舗名_（カナ）
        storeATMInfoDoc.setKanaStoreATMName(storeATMDetailStatusReqVO.getKanaStoreATMName());
        storeATMInfoDoc.setTypeKbn(storeATMDetailStatusReqVO.getTypeKbn());
        // 電話番号
        if (storeATMDetailStatusReqVO.getTeleNumber() != null && storeATMDetailStatusReqVO.getTeleNumber() != "") {
            if (storeATMDetailStatusReqVO.getTeleNumber().indexOf("-") < 0) {
                String telenumber = "";
                telenumber = PhoneNumberUtil.format(storeATMDetailStatusReqVO.getTeleNumber());
                storeATMInfoDoc.setTeleNumber(telenumber);
            } else {
                storeATMInfoDoc.setTeleNumber(storeATMDetailStatusReqVO.getTeleNumber());
            }
        }
        // 所在地_郵便番号
        if (storeATMDetailStatusReqVO.getPostCode() != null) {
            storeATMInfoDoc.setPostCode(storeATMDetailStatusReqVO.getPostCode());
        }
        // ATM営業時間_平日始業
        String atmOpenStartTime = timeFormart(storeATMDetailStatusReqVO.getAtmOpenStartHour(),
                storeATMDetailStatusReqVO.getAtmOpenStartMinute());
        storeATMInfoDoc.setAtmOpenStartTime(atmOpenStartTime);
        // ATM営業時間_平日終業
        String atmOpenEndTime = timeFormart(storeATMDetailStatusReqVO.getAtmOpenEndHour(),
                storeATMDetailStatusReqVO.getAtmOpenEndMinute());
        storeATMInfoDoc.setAtmOpenEndTime(atmOpenEndTime);
        // ATM営業時間_土曜日始業
        String atmOpenStartTime_SAT = timeFormart(storeATMDetailStatusReqVO.getAtmOpenStartHour_SAT(),
                storeATMDetailStatusReqVO.getAtmOpenStartMinute_SAT());
        storeATMInfoDoc.setAtmOpenStartTime_SAT(atmOpenStartTime_SAT);
        // ATM営業時間_土曜日終業
        String atmOpenEndTime_SAT = timeFormart(storeATMDetailStatusReqVO.getAtmOpenEndHour_SAT(),
                storeATMDetailStatusReqVO.getAtmOpenEndMinute_SAT());
        storeATMInfoDoc.setAtmOpenEndTime_SAT(atmOpenEndTime_SAT);
        // ATM営業時間_日曜日始業
        String atmOpenStartTime_SUN = timeFormart(storeATMDetailStatusReqVO.getAtmOpenStartHour_SUN(),
                storeATMDetailStatusReqVO.getAtmOpenStartMinute_SUN());
        storeATMInfoDoc.setAtmOpenStartTime_SUN(atmOpenStartTime_SUN);
        // ATM営業時間_日曜日終業
        String atmOpenEndTime_SUN = timeFormart(storeATMDetailStatusReqVO.getAtmOpenEndHour_SUN(),
                storeATMDetailStatusReqVO.getAtmOpenEndMinute_SUN());
        storeATMInfoDoc.setAtmOpenEndTime_SUN(atmOpenEndTime_SUN);
        storeATMInfoDoc.setAtmComment1(storeATMDetailStatusReqVO.getAtmComment1());
        storeATMInfoDoc.setAtmComment2(storeATMDetailStatusReqVO.getAtmComment2());
        // 窓口営業時間_平日始業
        String windowOpenStartTime = timeFormart(storeATMDetailStatusReqVO.getWindowOpenStartHour(),
                storeATMDetailStatusReqVO.getWindowOpenStartMinute());
        storeATMInfoDoc.setWindowOpenStartTime(windowOpenStartTime);
        // 窓口営業時間_平日終業
        String windowOpenEndTime = timeFormart(storeATMDetailStatusReqVO.getWindowOpenEndHour(),
                storeATMDetailStatusReqVO.getWindowOpenEndMinute());
        storeATMInfoDoc.setWindowOpenEndTime(windowOpenEndTime);
        // 窓口営業時間_土曜日始業
        String windowOpenStartTime_SAT = timeFormart(storeATMDetailStatusReqVO.getWindowOpenStartHour_SAT(),
                storeATMDetailStatusReqVO.getWindowOpenStartMinute_SAT());
        storeATMInfoDoc.setWindowOpenStartTime_SAT(windowOpenStartTime_SAT);
        // 窓口営業時間_土曜日終業
        String windowOpenEndTime_SAT = timeFormart(storeATMDetailStatusReqVO.getWindowOpenEndHour_SAT(),
                storeATMDetailStatusReqVO.getWindowOpenEndMinute_SAT());
        storeATMInfoDoc.setWindowOpenEndTime_SAT(windowOpenEndTime_SAT);
        // 窓口営業時間_日曜日始業
        String windowOpenStartTime_SUN = timeFormart(storeATMDetailStatusReqVO.getWindowOpenStartHour_SUN(),
                storeATMDetailStatusReqVO.getWindowOpenStartMinute_SUN());
        storeATMInfoDoc.setWindowOpenStartTime_SUN(windowOpenStartTime_SUN);
        // 窓口営業時間_日曜日終業
        String windowOpenEndTime_SUN = timeFormart(storeATMDetailStatusReqVO.getWindowOpenEndHour_SUN(),
                storeATMDetailStatusReqVO.getWindowOpenEndMinute_SUN());
        storeATMInfoDoc.setWindowOpenEndTime_SUN(windowOpenEndTime_SUN);
        
        String conversionMachineStartTime = timeFormart(storeATMDetailStatusReqVO.getConversionMachineStartHour(),
                storeATMDetailStatusReqVO.getConversionMachineStartMinute());
        storeATMInfoDoc.setConversionMachineStartTime(conversionMachineStartTime);
        String conversionMachineEndTime = timeFormart(storeATMDetailStatusReqVO.getConversionMachineEndHour(),
                storeATMDetailStatusReqVO.getConversionMachineEndMinute());
        storeATMInfoDoc.setConversionMachineEndTime(conversionMachineEndTime);
        String conversionMachineStartTime_holiday = timeFormart(
                storeATMDetailStatusReqVO.getConversionMachineStartHour_holiday(),
                storeATMDetailStatusReqVO.getConversionMachineStartMinute_holiday());
        storeATMInfoDoc.setConversionMachineStartTime_holiday(conversionMachineStartTime_holiday);
        String conversionMachineEndTime_holiday = timeFormart(
                storeATMDetailStatusReqVO.getConversionMachineEndHour_holiday(),
                storeATMDetailStatusReqVO.getConversionMachineEndMinute_holiday());
        storeATMInfoDoc.setConversionMachineEndTime_holiday(conversionMachineEndTime_holiday);
        storeATMInfoDoc.setAccountMachineStartTime(storeATMDetailStatusReqVO.getAccountMachineStartTime());
        storeATMInfoDoc.setAccountMachineStartTime_SAT(storeATMDetailStatusReqVO.getAccountMachineStartTime_SAT());
        storeATMInfoDoc.setAccountMachineStartTime_SUN(storeATMDetailStatusReqVO.getAccountMachineStartTime_SUN());
        String autoLoanMachineStartTime = timeFormart(storeATMDetailStatusReqVO.getAutoLoanMachineStartHour(),
                storeATMDetailStatusReqVO.getAutoLoanMachineStartMinute());
        storeATMInfoDoc.setAutoLoanMachineStartTime(autoLoanMachineStartTime);
        String autoLoanMachineEndTime = timeFormart(storeATMDetailStatusReqVO.getAutoLoanMachineEndHour(),
                storeATMDetailStatusReqVO.getAutoLoanMachineEndMinute());
        storeATMInfoDoc.setAutoLoanMachineEndTime(autoLoanMachineEndTime);
        String autoLoanMachineStartTime_holiday = timeFormart(
                storeATMDetailStatusReqVO.getAutoLoanMachineStartHour_holiday(),
                storeATMDetailStatusReqVO.getAutoLoanMachineStartMinute_holiday());
        storeATMInfoDoc.setAutoLoanMachineStartTime_holiday(autoLoanMachineStartTime_holiday);
        String autoLoanMachineEndTime_holiday = timeFormart(
                storeATMDetailStatusReqVO.getAutoLoanMachineEndHour_holiday(),
                storeATMDetailStatusReqVO.getAutoLoanMachineEndMinute_holiday());
        storeATMInfoDoc.setAutoLoanMachineEndTime_holiday(autoLoanMachineEndTime_holiday);
        storeATMInfoDoc.setAutoLoanMachineFlag(storeATMDetailStatusReqVO.getAutoLoanMachineFlag());
        String loanMachineStartTime = timeFormart(storeATMDetailStatusReqVO.getLoanMachineStartHour(),
                storeATMDetailStatusReqVO.getLoanMachineStartMinute());
        storeATMInfoDoc.setLoanMachineStartTime(loanMachineStartTime);
        String loanMachineEndTime = timeFormart(storeATMDetailStatusReqVO.getLoanMachineEndHour(),
                storeATMDetailStatusReqVO.getLoanMachineEndMinute());
        storeATMInfoDoc.setLoanMachineEndTime(loanMachineEndTime);
        String loanMachineStartTime_holiday = timeFormart(storeATMDetailStatusReqVO.getLoanMachineStartHour_holiday(),
                storeATMDetailStatusReqVO.getLoanMachineStartMinute_holiday());
        storeATMInfoDoc.setLoanMachineStartTime_holiday(loanMachineStartTime_holiday);
        String loanMachineEndTime_holiday = timeFormart(storeATMDetailStatusReqVO.getLoanMachineEndHour_holiday(),
                storeATMDetailStatusReqVO.getLoanMachineEndMinute_holiday());
        storeATMInfoDoc.setLoanMachineEndTime_holiday(loanMachineEndTime_holiday);
        storeATMInfoDoc.setLoanMachineFlag(storeATMDetailStatusReqVO.getLoanMachineFlag());
        storeATMInfoDoc.setAed(storeATMDetailStatusReqVO.getAed());
        storeATMInfoDoc.setInternationalStore(storeATMDetailStatusReqVO.getInternationalStore());
        // 備考①
        if (storeATMDetailStatusReqVO.getComment1() != null) {
            storeATMInfoDoc.setComment1(storeATMDetailStatusReqVO.getComment1());
        }
        // 備考②
        if (storeATMDetailStatusReqVO.getComment2() != null) {
            storeATMInfoDoc.setComment2(storeATMDetailStatusReqVO.getComment2());
        }
        // ATM設置台数
        storeATMInfoDoc.setAtmCount(storeATMDetailStatusReqVO.getAtmCount());
        storeATMInfoDoc.setParkCount(storeATMDetailStatusReqVO.getParkCount());
        storeATMInfoDoc.setToilet(storeATMDetailStatusReqVO.getToilet());
        // 駐車場_備考
        if (storeATMDetailStatusReqVO.getParkComment() != null) {
            storeATMInfoDoc.setParkComment(storeATMDetailStatusReqVO.getParkComment());
        }
        storeATMInfoDoc.setServiceConversionStore(storeATMDetailStatusReqVO.getServiceConversionStore());
        storeATMInfoDoc.setWheelChair(storeATMDetailStatusReqVO.getWheelChair());
        storeATMInfoDoc.setWheelChairStore(storeATMDetailStatusReqVO.getWheelChairStore());
        storeATMInfoDoc.setCurrentStation1(storeATMDetailStatusReqVO.getCurrentStation1());
        storeATMInfoDoc.setCurrentStation2(storeATMDetailStatusReqVO.getCurrentStation2());
        storeATMInfoDoc.setCurrentStation3(storeATMDetailStatusReqVO.getCurrentStation3());
        storeATMInfoDoc.setCurrentStationDistance1(storeATMDetailStatusReqVO.getCurrentStationDistance1());
        storeATMInfoDoc.setCurrentStationDistance2(storeATMDetailStatusReqVO.getCurrentStationDistance2());
        storeATMInfoDoc.setCurrentStationDistance3(storeATMDetailStatusReqVO.getCurrentStationDistance3());
        storeATMInfoDoc.setCurrentStationTime1(storeATMDetailStatusReqVO.getCurrentStationTime1());
        storeATMInfoDoc.setCurrentStationTime2(storeATMDetailStatusReqVO.getCurrentStationTime2());
        storeATMInfoDoc.setCurrentStationTime3(storeATMDetailStatusReqVO.getCurrentStationTime3());
        storeATMInfoDoc.setMessage(storeATMDetailStatusReqVO.getMessage());
        storeATMInfoDoc.setImage(storeATMDetailStatusReqVO.getImage());
        storeATMInfoDoc.setImageUrl(storeATMDetailStatusReqVO.getImageUrl());
        storeATMInfoDoc.setIcon(storeATMDetailStatusReqVO.getIcon());
        storeATMInfoDoc.setFinalUpdateTime(storeATMDetailStatusReqVO.getFinalUpdateTime());
        storeATMInfoDoc.setDelFlg(storeATMDetailStatusReqVO.getDelFlg());
        String delFlg = storeATMDetailStatusReqVO.getDelFlg();
        GeometryDoc geometryDoc = new GeometryDoc();
        double lon_t = Double.parseDouble(storeATMDetailStatusReqVO.getLongitude());
        double lat_t = Double.parseDouble(storeATMDetailStatusReqVO.getLatitude());
        double lon_w=lon_t -(lat_t*0.000046038) -(lon_t*0.000083043)+0.010040;
        double lat_w = lat_t -(lat_t*0.00010695) +(lon_t*0.000017464) +0.0046017;
        double[] coordinate = { lon_w, lat_w };
        geometryDoc.setType("Point");
        geometryDoc.setCoordinates(coordinate);
        storeATMInfoDoc.setGeometry(geometryDoc);
        // 座標_経度
        if (storeATMDetailStatusReqVO.getLongitude() != null) {
            storeATMInfoDoc.setLongitude(String.valueOf(lon_w));
            storeATMInfoDoc.setLongitude_t(storeATMDetailStatusReqVO.getLongitude());
        }
        // 座標_緯度
        if (storeATMDetailStatusReqVO.getLatitude() != null) {
            storeATMInfoDoc.setLatitude(String.valueOf(lat_w));
            storeATMInfoDoc.setLatitude_t(storeATMDetailStatusReqVO.getLatitude());
        }
        // DBに更新
        if (storeATMDetailStatusReqVO.getModeType().equals("2")) {
            try {
                // 店舗を更新
                repositoryUtil.update(db, storeATMInfoDoc);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMDETAIL_1002);
                throw new BusinessException(messages);
            }
            actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_6 + storeUpdLog + ")", db);
        } else {
            storeUpdLog = storeUpdLog + storeATMDetailStatusReqVO.getStoreATMName();
            String queryKey = "storeNumber:\"" + storeATMDetailStatusReqVO.getStoreNumber() + "\" ";
            List<IYoStoreATMInfoDoc> storeATMList = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_STORE_STOREATM_SEARCHINFO, queryKey, IYoStoreATMInfoDoc.class);
            if (storeATMList != null && storeATMList.size() != 0) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_IYOSTOREATMDETAIL_1001);
                throw new BusinessException(messages);
            } else {
                try {
                    // 店舗を登録
                    repositoryUtil.save(db, storeATMInfoDoc, delFlg);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMDETAIL_1003);
                    throw new BusinessException(messages);
                }
            }
            actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_4 + storeUpdLog + ")", db);
        }
        iYoStoreATMDetailUpdateResVO.setTypeKbn(storeATMInfoDoc.getTypeKbn());
        iYoStoreATMDetailUpdateResVO.setAddress(storeATMInfoDoc.getAddress());
        iYoStoreATMDetailUpdateResVO.setTeleNumber(storeATMInfoDoc.getTeleNumber());
        iYoStoreATMDetailUpdateResVO.setWindowOpenStartTime(storeATMInfoDoc.getWindowOpenStartTime());

        return iYoStoreATMDetailUpdateResVO;
    }

    private StoreATMDetailUpdateResVO hiroshima(Database db, StoreATMDetailUpdateReqVO storeATMDetailStatusReqVO,
            StoreATMInfoDoc storeATMInfoDoc, String storeUpdLog) {
        StoreATMDetailUpdateResVO output = new StoreATMDetailUpdateResVO();
        // 店舗区分
        storeATMInfoDoc.setTypeKbn(storeATMDetailStatusReqVO.getTypeKbn());
        // 店舗コード_母店番号
        storeATMInfoDoc.setStoreNumber(storeATMDetailStatusReqVO.getStoreNumber());
        // 店舗コード_出張所枝番
        storeATMInfoDoc.setSubStoreNumber(storeATMDetailStatusReqVO.getSubStoreNumber());
        // 店舗コード_ATM枝番
        storeATMInfoDoc.setAtmStoreNumber(storeATMDetailStatusReqVO.getAtmStoreNumber());
        // 店舗名_（漢字）
        storeATMInfoDoc.setStoreATMName(storeATMDetailStatusReqVO.getStoreATMName());
        // 店舗名_（カナ）
        storeATMInfoDoc.setKanaStoreATMName(storeATMDetailStatusReqVO.getKanaStoreATMName());
        // 所在地_郵便番号
        if (storeATMDetailStatusReqVO.getPostCode() != null) {
            storeATMInfoDoc.setPostCode(storeATMDetailStatusReqVO.getPostCode());
        }
        // 所在地_住所
        if (storeATMDetailStatusReqVO.getAddress() != null) {
            storeATMInfoDoc.setAddress(storeATMDetailStatusReqVO.getAddress());
        }
        // 所在地_ランドマーク
        if (storeATMDetailStatusReqVO.getLandMark() != null) {
            storeATMInfoDoc.setLandMark(storeATMDetailStatusReqVO.getLandMark());
        }
        // 電話番号

        if (storeATMDetailStatusReqVO.getTeleNumber() != null && storeATMDetailStatusReqVO.getTeleNumber() != "") {
            if (storeATMDetailStatusReqVO.getTeleNumber().indexOf("-") < 0) {
                String telenumber = "";
                telenumber = PhoneNumberUtil.format(storeATMDetailStatusReqVO.getTeleNumber());
                storeATMInfoDoc.setTeleNumber(telenumber);
            } else {
                storeATMInfoDoc.setTeleNumber(storeATMDetailStatusReqVO.getTeleNumber());
            }
        }
        // 窓口営業時間_平日始業
        String windowOpenStartTime = timeFormart(storeATMDetailStatusReqVO.getWindowStartHour(),
                storeATMDetailStatusReqVO.getWindowStartMinute());
        storeATMInfoDoc.setWindowOpenStartTime(windowOpenStartTime);
        // 窓口営業時間_平日終業
        String windowOpenEndTime = timeFormart(storeATMDetailStatusReqVO.getWindowEndHour(),
                storeATMDetailStatusReqVO.getWindowEndMinute());
        storeATMInfoDoc.setWindowOpenEndTime(windowOpenEndTime);
        // 窓口営業時間_土曜日始業
        String windowOpenStartTime_SAT = timeFormart(storeATMDetailStatusReqVO.getWindowStartHour_SAT(),
                storeATMDetailStatusReqVO.getWindowStartMinute_SAT());
        storeATMInfoDoc.setWindowOpenStartTime_SAT(windowOpenStartTime_SAT);
        // 窓口営業時間_土曜日終業
        String windowOpenEndTime_SAT = timeFormart(storeATMDetailStatusReqVO.getWindowEndHour_SAT(),
                storeATMDetailStatusReqVO.getWindowEndMinute_SAT());
        storeATMInfoDoc.setWindowOpenEndTime_SAT(windowOpenEndTime_SAT);
        // 窓口営業時間_日曜日始業
        String windowOpenStartTime_SUN = timeFormart(storeATMDetailStatusReqVO.getWindowStartHour_SUN(),
                storeATMDetailStatusReqVO.getWindowStartMinute_SUN());
        storeATMInfoDoc.setWindowOpenStartTime_SUN(windowOpenStartTime_SUN);
        // 窓口営業時間_日曜日終業
        String windowOpenEndTime_SUN = timeFormart(storeATMDetailStatusReqVO.getWindowEndHour_SUN(),
                storeATMDetailStatusReqVO.getWindowEndMinute_SUN());
        storeATMInfoDoc.setWindowOpenEndTime_SUN(windowOpenEndTime_SUN);
        // ATM営業時間_平日始業
        String atmOpenStartTime = timeFormart(storeATMDetailStatusReqVO.getAtmStartHour(),
                storeATMDetailStatusReqVO.getAtmStartMinute());
        storeATMInfoDoc.setAtmOpenStartTime(atmOpenStartTime);
        // ATM営業時間_平日終業
        String atmOpenEndTime = timeFormart(storeATMDetailStatusReqVO.getAtmEndHour(),
                storeATMDetailStatusReqVO.getAtmEndMinute());
        storeATMInfoDoc.setAtmOpenEndTime(atmOpenEndTime);
        // ATM営業時間_土曜日始業
        String atmOpenStartTime_SAT = timeFormart(storeATMDetailStatusReqVO.getAtmStartHour_SAT(),
                storeATMDetailStatusReqVO.getAtmStartMinute_SAT());
        storeATMInfoDoc.setAtmOpenStartTime_SAT(atmOpenStartTime_SAT);
        // ATM営業時間_土曜日終業
        String atmOpenEndTime_SAT = timeFormart(storeATMDetailStatusReqVO.getAtmEndHour_SAT(),
                storeATMDetailStatusReqVO.getAtmEndMinute_SAT());
        storeATMInfoDoc.setAtmOpenEndTime_SAT(atmOpenEndTime_SAT);
        // ATM営業時間_日曜日始業
        String atmOpenStartTime_SUN = timeFormart(storeATMDetailStatusReqVO.getAtmStartHour_SUN(),
                storeATMDetailStatusReqVO.getAtmStartMinute_SUN());
        storeATMInfoDoc.setAtmOpenStartTime_SUN(atmOpenStartTime_SUN);
        // ATM営業時間_日曜日終業
        String atmOpenEndTime_SUN = timeFormart(storeATMDetailStatusReqVO.getAtmEndHour_SUN(),
                storeATMDetailStatusReqVO.getAtmEndMinute_SUN());
        storeATMInfoDoc.setAtmOpenEndTime_SUN(atmOpenEndTime_SUN);
        // ATM設置台数
        storeATMInfoDoc.setAtmCount(storeATMDetailStatusReqVO.getAtmCount());
        // 駐車場_有無
        storeATMInfoDoc.setPark(storeATMDetailStatusReqVO.getPark());
        // 駐車場_障害者対応
        storeATMInfoDoc.setParkServiceForDisabled(storeATMDetailStatusReqVO.getParkServiceForDisabled());
        // 駐車場_備考
        if (storeATMDetailStatusReqVO.getParkComment() != null) {
            storeATMInfoDoc.setParkComment(storeATMDetailStatusReqVO.getParkComment());
        }
        // ひろぎんウツミ屋
        storeATMInfoDoc.setHirginUtsumiya(storeATMDetailStatusReqVO.getHirginUtsumiya());
        // 商品サービス_外貨両替（ﾄﾞﾙ、ﾕｰﾛ）
        storeATMInfoDoc.setServiceDollarEuro(storeATMDetailStatusReqVO.getServiceDollarEuro());
        // 商品サービス_外貨両替（ｱｼﾞｱ通貨）
        storeATMInfoDoc.setServiceAsia(storeATMDetailStatusReqVO.getServiceAsia());
        // 商品サービス_外貨両替（その他）
        storeATMInfoDoc.setServiceOther(storeATMDetailStatusReqVO.getServiceOther());
        // 商品サービス_外貨買取
        storeATMInfoDoc.setServiceForeignExchange(storeATMDetailStatusReqVO.getServiceForeignExchange());
        // 商品サービス_投資信託
        storeATMInfoDoc.setServiceInvestmentTrust(storeATMDetailStatusReqVO.getServiceInvestmentTrust());
        // 商品サービス_年金保険
        storeATMInfoDoc.setServicePensionInsurance(storeATMDetailStatusReqVO.getServicePensionInsurance());
        // 商品サービス_金融商品仲介（みずほ証券）
        storeATMInfoDoc.setServiceMizuho(storeATMDetailStatusReqVO.getServiceMizuho());
        // 商品サービス_金融商品仲介（ひろぎんウツミ屋）
        storeATMInfoDoc.setServiceHirginUtsumiya(storeATMDetailStatusReqVO.getServiceHirginUtsumiya());
        // 商品サービス_全自動貸金庫
        storeATMInfoDoc.setServiceAutoSafeDepositBox(storeATMDetailStatusReqVO.getServiceAutoSafeDepositBox());
        // 商品サービス_一般貸金庫
        storeATMInfoDoc.setServiceSafeDepositBox(storeATMDetailStatusReqVO.getServiceSafeDepositBox());
        // 商品サービス_ｾｰﾌﾃｨｹｰｽ
        storeATMInfoDoc.setServiceSafeBox(storeATMDetailStatusReqVO.getServiceSafeBox());
        // 店舗設備_IB専用PC
        storeATMInfoDoc.setInternationalTradePC(storeATMDetailStatusReqVO.getInternationalTradePC());
        // 店舗設備_ｷｯｽﾞｽﾍﾟｰｽ
        storeATMInfoDoc.setChildrenSpace(storeATMDetailStatusReqVO.getChildrenSpace());
        // バリアフリー_視覚障害対応ATM
        storeATMInfoDoc.setBarrierFreeVisualImpairment(storeATMDetailStatusReqVO.getBarrierFreeVisualImpairment());
        // バリアフリー_点字ﾌﾞﾛｯｸ
        storeATMInfoDoc.setBarrierFreeBrailleBlock(storeATMDetailStatusReqVO.getBarrierFreeBrailleBlock());
        // バリアフリー_音声ｶﾞｲﾄﾞ
        storeATMInfoDoc.setBarrierFreeVoiceGuide(storeATMDetailStatusReqVO.getBarrierFreeVoiceGuide());
        // バリアフリー_AED
        storeATMInfoDoc.setBarrierFreeAED(storeATMDetailStatusReqVO.getBarrierFreeAED());
        // ATM機能_振込
        storeATMInfoDoc.setAtmFunctionTransfer(storeATMDetailStatusReqVO.getAtmFunctionTransfer());
        // ATM機能_硬貨入出金
        storeATMInfoDoc.setAtmFunctionCoinAccess(storeATMDetailStatusReqVO.getAtmFunctionCoinAccess());
        // ATM機能_宝くじｻｰﾋﾞｽ
        storeATMInfoDoc.setAtmFunctionLotteryService(storeATMDetailStatusReqVO.getAtmFunctionLotteryService());
        // ATM機能_手のひら認証
        storeATMInfoDoc.setAtmFunctionPalmAuthentication(storeATMDetailStatusReqVO.getAtmFunctionPalmAuthentication());
        // ATM機能_IC対応
        storeATMInfoDoc.setAtmFunctionIC(storeATMDetailStatusReqVO.getAtmFunctionIC());
        // ATM機能_PASPYチャージ
        storeATMInfoDoc.setAtmFunctionPASPY(storeATMDetailStatusReqVO.getAtmFunctionPASPY());
        // ATM機能_他行幹事
        storeATMInfoDoc
                .setAtmFunctionOtherBankingAffairs(storeATMDetailStatusReqVO.getAtmFunctionOtherBankingAffairs());
        // 座標_経度
        if (storeATMDetailStatusReqVO.getLongitude() != null) {
            storeATMInfoDoc.setLongitude(storeATMDetailStatusReqVO.getLongitude());
        }
        // 座標_緯度
        if (storeATMDetailStatusReqVO.getLatitude() != null) {
            storeATMInfoDoc.setLatitude(storeATMDetailStatusReqVO.getLatitude());
        }
        // 備考①
        if (storeATMDetailStatusReqVO.getComment1() != null) {
            storeATMInfoDoc.setComment1(storeATMDetailStatusReqVO.getComment1());
        }
        // 備考②
        if (storeATMDetailStatusReqVO.getComment2() != null) {
            storeATMInfoDoc.setComment2(storeATMDetailStatusReqVO.getComment2());
        }
        // 備考③
        if (storeATMDetailStatusReqVO.getComment3() != null) {
            storeATMInfoDoc.setComment3(storeATMDetailStatusReqVO.getComment3());
        }
        // 備考④
        if (storeATMDetailStatusReqVO.getComment4() != null) {
            storeATMInfoDoc.setComment4(storeATMDetailStatusReqVO.getComment4());
        }
        // 備考⑤
        if (storeATMDetailStatusReqVO.getComment5() != null) {
            storeATMInfoDoc.setComment5(storeATMDetailStatusReqVO.getComment5());
        }
        // 開始日時
        if (storeATMDetailStatusReqVO.getStartDateTime() != null) {
            storeATMInfoDoc.setStartDateTime(storeATMDetailStatusReqVO.getStartDateTime());
        }
        // 終了日時
        if (storeATMDetailStatusReqVO.getEndDateTime() != null) {
            storeATMInfoDoc.setEndDateTime(storeATMDetailStatusReqVO.getEndDateTime());
        }
        storeATMInfoDoc.setDelFlg(storeATMDetailStatusReqVO.getDelFlg());
        String delFlg = storeATMDetailStatusReqVO.getDelFlg();
        GeometryDoc geometryDoc = new GeometryDoc();
        double lon = Double.parseDouble(storeATMDetailStatusReqVO.getLongitude());
        double lat = Double.parseDouble(storeATMDetailStatusReqVO.getLatitude());
        double[] coordinate = { lon, lat };
        geometryDoc.setType("Point");
        geometryDoc.setCoordinates(coordinate);
        storeATMInfoDoc.setGeometry(geometryDoc);
        // DBに更新
        if (storeATMDetailStatusReqVO.getModeType().equals("2")) {
            try {
                // 店舗を更新
                repositoryUtil.update(db, storeATMInfoDoc);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMDETAIL_1002);
                throw new BusinessException(messages);
            }
            actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_6 + storeUpdLog + ")", db);
        } else {
            storeUpdLog = storeUpdLog + storeATMDetailStatusReqVO.getStoreATMName();
            String queryKey = "storeNumber:\"" + storeATMDetailStatusReqVO.getStoreNumber() + "\" AND subStoreNumber:\""
                    + storeATMDetailStatusReqVO.getSubStoreNumber() + "\" AND atmStoreNumber:\""
                    + storeATMDetailStatusReqVO.getAtmStoreNumber() + "\" AND typeKbn:0 ";
            List<StoreATMInfoDoc> storeATMList = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_STORE_STOREATM_SEARCHINFO, queryKey, StoreATMInfoDoc.class);
            if (storeATMList != null && storeATMList.size() != 0) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMLIST_1005);
                throw new BusinessException(messages);
            } else {
                try {
                    // 店舗を登録
                    repositoryUtil.save(db, storeATMInfoDoc, delFlg);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_STOREATMDETAIL_1003);
                    throw new BusinessException(messages);
                }
            }
            actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_4 + storeUpdLog + ")", db);
        }
        output.setTypeKbn(storeATMInfoDoc.getTypeKbn());
        output.setAddress(storeATMInfoDoc.getAddress());
        output.setTeleNumber(storeATMInfoDoc.getTeleNumber());
        output.setWindowOpenStartTime(storeATMInfoDoc.getWindowOpenStartTime());

        return output;
    }

    /**
     * 時間がHH:mmにフォーマット 。
     * 
     * @param hour
     * 
     * @param minute
     *            入力情報
     * @return フォーマット結果HH:mm
     */
    public String timeFormart(String hour, String minute) {
        String timeHHMM = "";
        if (Utils.isNotNullAndEmpty(hour) && Utils.isNotNullAndEmpty(minute)) {
            timeHHMM = hour + Constants.COLON + minute;
        }
        return timeHHMM;
    }
}
