package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.IYoStoreATMInfoDoc;
import com.scsk.model.Store114ATMInfoDoc;
import com.scsk.model.StoreATMInfoDoc;
import com.scsk.response.vo.BaseResVO;
import com.scsk.response.vo.IYoStoreATMInitResVO;
import com.scsk.response.vo.Store114ATMInitResVO;
import com.scsk.response.vo.StoreATMInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.Utils;
import com.scsk.vo.IYoStoreATMInitVO;
import com.scsk.vo.Store114ATMInitVO;
import com.scsk.vo.StoreATMInitVO;

/**
 * 店舗ATM一覧初期化検索サービス。<br>
 * <br>
 * 店舗ATM一覧初期化検索を実装するロジック。<br>
 */
@Service
public class StoreATMListInitService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Value("${bank_cd}")
    private String bank_cd;
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param なし
     *            検索条件
     */
    @Override
    protected void preExecute(BaseResVO ResVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param なし
     *            検索条件
     * @return storeATMInitResVO 店舗ATM一覧情報
     */
    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO ResVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        StoreATMInitResVO storeATMInitResVO = new StoreATMInitResVO();
        IYoStoreATMInitResVO iYoStoreATMInitResVO=new IYoStoreATMInitResVO();
        Store114ATMInitResVO store114atmInitResVO=new Store114ATMInitResVO();
        if ("0169".equals(bank_cd)) {
            List<StoreATMInitVO> storeATMList = hirosima(db);
            storeATMInitResVO.setStoreATMList(storeATMList);
            actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_1, db);
            return storeATMInitResVO;
        } else if ("0174".equals(bank_cd)) {
            List<IYoStoreATMInitVO> storeATMList = iyo(db);
            iYoStoreATMInitResVO.setStoreATMList(storeATMList);
            actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_1, db);
            return iYoStoreATMInitResVO;
        }else if("0173".equals(bank_cd)){
            List<Store114ATMInitVO> storeATMList = hyakujyushi(db);
            store114atmInitResVO.setStoreATMList(storeATMList);
            actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_1, db);
            return store114atmInitResVO;
        }
        return new BaseResVO();
       
    }
    
    private List<Store114ATMInitVO> hyakujyushi(Database db) {
        // 店舗ATM一覧初期データを取得
        List<Store114ATMInfoDoc> storeATMInfoDocList = new ArrayList<>();
        List<Store114ATMInitVO> storeATMList = new ArrayList<>();

        storeATMInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_STOREATMDATALIST_STOREATMDATALIST,
                Store114ATMInfoDoc.class);

        if (storeATMInfoDocList != null && storeATMInfoDocList.size() != 0) {
            for (Store114ATMInfoDoc doc : storeATMInfoDocList) {
                Store114ATMInitVO storeATMInitVO = new Store114ATMInitVO();
                // 店舗ATM一覧初期化用データを戻る
                storeATMInitVO.set_id(doc.get_id());
                storeATMInitVO.set_rev(doc.get_rev());
                storeATMInitVO.setTypeKbn(doc.getTypeKbn());
                storeATMInitVO.setStoreNumber(doc.getStoreNumber());
                storeATMInitVO.setStoreName(doc.getStoreName());
                storeATMInitVO.setAddress(doc.getAddress());
                storeATMInitVO.setTeleNumber(doc.getTeleNumber());

                if (Utils.isNotNullAndEmpty(doc.getStoreOpenStartTime())) {
                    storeATMInitVO.setStoreOpenStartTime(
                            doc.getStoreOpenStartTime() + Constants.MARK2 + doc.getStoreOpenEndTime());
                } else {
                    storeATMInitVO.setStoreOpenStartTime(
                            doc.getAtmOpenStartTime() + Constants.MARK2 + doc.getAtmOpenEndTime());
                }
                storeATMInitVO.setLatitude(doc.getLatitude());
                storeATMInitVO.setLongitude(doc.getLongitude());
                storeATMInitVO.setDelFlg(doc.getDelFlg());
                storeATMList.add(storeATMInitVO);
            }
        }
        actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_1, db);
        return storeATMList;
    }

    public List<StoreATMInitVO> hirosima(Database db) throws Exception{
        // 店舗ATM一覧初期データを取得
        List<StoreATMInfoDoc> storeATMInfoDocList = new ArrayList<>();
        List<StoreATMInitVO> storeATMList = new ArrayList<>();

        storeATMInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_STOREATMDATALIST_STOREATMDATALIST,
                StoreATMInfoDoc.class);

        if (storeATMInfoDocList != null && storeATMInfoDocList.size() != 0) {
            for (StoreATMInfoDoc doc : storeATMInfoDocList) {
                StoreATMInitVO storeATMInitVO = new StoreATMInitVO();
                // 店舗ATM一覧初期化用データを戻る
                storeATMInitVO.set_id(doc.get_id());
                storeATMInitVO.set_rev(doc.get_rev());
                storeATMInitVO.setTypeKbn(doc.getTypeKbn());
                storeATMInitVO.setStoreNumber(doc.getStoreNumber());
                storeATMInitVO.setSubStoreNumber(doc.getSubStoreNumber());
                storeATMInitVO.setAtmStoreNumber(doc.getAtmStoreNumber());
                storeATMInitVO.setStoreATMCode(doc.getStoreNumber()+doc.getSubStoreNumber()+doc.getAtmStoreNumber());
                storeATMInitVO.setStoreATMName(doc.getStoreATMName());
                storeATMInitVO.setKanaStoreATMName(doc.getKanaStoreATMName());
                storeATMInitVO.setAddress(doc.getAddress());
                storeATMInitVO.setTeleNumber(doc.getTeleNumber());

                if (Utils.isNotNullAndEmpty(doc.getWindowOpenStartTime())) {
                    storeATMInitVO.setWindowOpenStartTime(
                            doc.getWindowOpenStartTime() + Constants.MARK2 + doc.getWindowOpenEndTime());
                } else {
                    storeATMInitVO.setWindowOpenStartTime(
                            doc.getAtmOpenStartTime() + Constants.MARK2 + doc.getAtmOpenEndTime());
                }
                storeATMInitVO.setLatitude(doc.getLatitude());
                storeATMInitVO.setLongitude(doc.getLongitude());
                storeATMInitVO.setDelFlg(doc.getDelFlg());
                storeATMList.add(storeATMInitVO);
            }
        }
        actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_1, db);
        return storeATMList;
    }
    public List<IYoStoreATMInitVO> iyo(Database db) throws Exception{
        List<IYoStoreATMInfoDoc> storeATMInfoDocList = new ArrayList<>();
        List<IYoStoreATMInitVO> storeATMList = new ArrayList<>();

        storeATMInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_STOREATMDATALIST_STOREATMDATALIST,
                IYoStoreATMInfoDoc.class);

        if (storeATMInfoDocList != null && storeATMInfoDocList.size() != 0) {
            for (IYoStoreATMInfoDoc doc : storeATMInfoDocList) {
                IYoStoreATMInitVO storeATMInitVO = new IYoStoreATMInitVO();
                // 店舗ATM一覧初期化用データを戻る
                storeATMInitVO.set_id(doc.get_id());
                storeATMInitVO.set_rev(doc.get_rev());
                storeATMInitVO.setTypeKbn(doc.getTypeKbn());
                storeATMInitVO.setStoreNumber(doc.getStoreNumber());
                storeATMInitVO.setStoreATMName(doc.getStoreATMName());
                storeATMInitVO.setKanaStoreATMName(doc.getKanaStoreATMName());
                storeATMInitVO.setAddress(doc.getAddress());
                storeATMInitVO.setTeleNumber(doc.getTeleNumber());
                if (Utils.isNotNullAndEmpty(doc.getWindowOpenStartTime())) {
                    storeATMInitVO.setWindowOpenStartTime(
                            doc.getWindowOpenStartTime() + Constants.MARK2 + doc.getWindowOpenEndTime());
                } else {
                    storeATMInitVO.setWindowOpenStartTime(
                            doc.getAtmOpenStartTime() + Constants.MARK2 + doc.getAtmOpenEndTime());
                }
                storeATMInitVO.setLatitude(doc.getLatitude());
                storeATMInitVO.setLongitude(doc.getLongitude());
                storeATMInitVO.setPoiStatus(doc.getPoiStatus());
                storeATMList.add(storeATMInitVO);
            }
        }
        actionLog.saveActionLog(Constants.ACTIONLOG_STOREATM_1, db);
        return storeATMList;
        
    }

}
