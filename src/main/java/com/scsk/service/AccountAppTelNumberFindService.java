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
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.model.rev.TeleNumberDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.TeleNumberUtil;
import com.scsk.util.Utils;

/**
 *  自宅電話番号鑑定APIメソッド。
 * 
 * @return ResponseEntity　戻るデータオブジェクト
 */
@Service
public class AccountAppTelNumberFindService
        extends
        AbstractBLogic<BaseResVO, BaseResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Value("${bank_cd}")
    private String bank_cd;
//  @Autowired
//  private PushNotifications pushNotifications;

    @Override
    protected void preExecute(
    		BaseResVO applicationDetailStatusReqVO)
            throws Exception {

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
    protected BaseResVO doExecute(CloudantClient client,
    		BaseResVO baseResVO)
            throws Exception {
        
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        if("0169".equals(bank_cd)){
        	AccountAppDetailStatusResVO output = hirosima(db,baseResVO);
        	return output;
        } else if("0122".equals(bank_cd)){
        	AccountAppDetailStatusResVO output = yamagata(db,baseResVO);
        	return output;
        }
        return null;
    }
    
    /**
     * 
     * 広島
     * 
     */
        public AccountAppDetailStatusResVO hirosima (Database db,BaseResVO baseResVO) throws Exception{
        AccountAppDetailStatusResVO output = new AccountAppDetailStatusResVO();
        AccountAppDetailStatusReqVO accountAppDetailStatusReqVO	= new AccountAppDetailStatusReqVO();
        accountAppDetailStatusReqVO = (AccountAppDetailStatusReqVO) baseResVO;
        String accountAppUpdLog="(受付番号：";
        AccountAppDoc applicationDoc = new AccountAppDoc();
        // 申込詳細情報取得
        try {
            applicationDoc = (AccountAppDoc) repositoryUtil.find(db,
            		accountAppDetailStatusReqVO.get_id(), AccountAppDoc.class);
            accountAppUpdLog=accountAppUpdLog+applicationDoc.getAccountAppSeq();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(
                    MessageKeys.E_ACCOUNTAPPDETAIL_1001);
            throw new BusinessException(messages);
        }
        // 自宅電話番号鑑定
        try {
            TeleNumberDoc teleNumberDoc = TeleNumberUtil.teleNumber(encryptorUtil.decrypt(applicationDoc.getTeleNumber()));

            applicationDoc.setTC_Access1(teleNumberDoc.getAccess1());
            applicationDoc.setTC_Result1(teleNumberDoc.getResult1());
            applicationDoc.setTC_Month1(teleNumberDoc.getMonth1());
            applicationDoc.setTC_Movetel1(teleNumberDoc.getMovetel1());
            applicationDoc.setTC_Carrier1(teleNumberDoc.getCarrier1());
            applicationDoc.setTC_Count1(teleNumberDoc.getCount1());
            applicationDoc.setTC_Attention1(teleNumberDoc.getAttention1());
            applicationDoc.setTC_Tacsflag1(teleNumberDoc.getTacsflag1());
            applicationDoc.setTC_Latestyear1(teleNumberDoc.getLatestyear1());
            applicationDoc.setTC_Latestmonth1(teleNumberDoc.getLatestmonth1());
            applicationDoc.setTC_F01_1(teleNumberDoc.getF01_1());
            applicationDoc.setTC_F02_1(teleNumberDoc.getF02_1());
            applicationDoc.setTC_F03_1(teleNumberDoc.getF03_1());
            applicationDoc.setTC_F04_1(teleNumberDoc.getF04_1());
            applicationDoc.setTC_F05_1(teleNumberDoc.getF05_1());
            applicationDoc.setTC_F06_1(teleNumberDoc.getF06_1());
            applicationDoc.setTC_F07_1(teleNumberDoc.getF07_1());
            applicationDoc.setTC_F08_1(teleNumberDoc.getF08_1());
            applicationDoc.setTC_F09_1(teleNumberDoc.getF09_1());
            applicationDoc.setTC_F10_1(teleNumberDoc.getF10_1());
            applicationDoc.setTC_F11_1(teleNumberDoc.getF11_1());
            applicationDoc.setTC_F12_1(teleNumberDoc.getF12_1());
            applicationDoc.setTC_F13_1(teleNumberDoc.getF13_1());
            applicationDoc.setTC_F14_1(teleNumberDoc.getF14_1());
            applicationDoc.setTC_F15_1(teleNumberDoc.getF15_1());
            applicationDoc.setTC_F16_1(teleNumberDoc.getF16_1());
            applicationDoc.setTC_F17_1(teleNumberDoc.getF17_1());
            applicationDoc.setTC_F18_1(teleNumberDoc.getF18_1());
            applicationDoc.setTC_F19_1(teleNumberDoc.getF19_1());
            applicationDoc.setTC_F20_1(teleNumberDoc.getF20_1());
            applicationDoc.setTC_F21_1(teleNumberDoc.getF21_1());
            applicationDoc.setTC_F22_1(teleNumberDoc.getF22_1());
            applicationDoc.setTC_F23_1(teleNumberDoc.getF23_1());
            applicationDoc.setTC_F24_1(teleNumberDoc.getF24_1());
            } catch (Exception e) {
            } // 完了
        try {
            repositoryUtil.update(db, applicationDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(
                    MessageKeys.E_ACCOUNTAPPDETAIL_1002);
            throw new BusinessException(messages);
        }
        // 電話番号鑑定結果
        int TC_Count1 = 0;
        int TC_Count2 = 0;
        if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count2())) {
            TC_Count2 = Integer.parseInt(applicationDoc.getTC_Count2());
        }
        if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count1())) {
            TC_Count1 = Integer.parseInt(applicationDoc.getTC_Count1());
        }
        boolean flag = false;
        if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getPhoneNumber())) && Utils.isNotNullAndEmpty(applicationDoc.getTC_Result2())) {
            // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG　attention＝1or2or3orEorU or 検索結果　result=0以外
            if (TC_Count2 >= 2 || applicationDoc.getTC_Tacsflag2().equals("0")
                    || applicationDoc.getTC_Tacsflag2().equals("2")
                    || applicationDoc.getTC_Tacsflag2().equals("3")
                    || applicationDoc.getTC_Tacsflag2().equals("4")
                    || applicationDoc.getTC_Tacsflag2().equals("7")
                    || applicationDoc.getTC_Attention2().equals("1")
                    || applicationDoc.getTC_Attention2().equals("2")
                    || applicationDoc.getTC_Attention2().equals("3")
                    || applicationDoc.getTC_Attention2().equals("E")
                    || applicationDoc.getTC_Attention2().equals("U")
                    || !applicationDoc.getTC_Result2().equals("0")) {
                // 要注意
//              accountAppInitVO.setAppraisalTelResult("1");
                flag = true;
            }
//          } else{
//              // 問題なし
//              accountAppInitVO.setAppraisalTelResult("2");
//          }
        } 
        if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getTeleNumber())) && Utils.isNotNullAndEmpty(applicationDoc.getTC_Result1())) {
            // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG　attention＝1or2or3orEorU or 検索結果　result=0以外
            if (TC_Count1 >= 2 || applicationDoc.getTC_Tacsflag1().equals("0")
                    || applicationDoc.getTC_Tacsflag1().equals("2")
                    || applicationDoc.getTC_Tacsflag1().equals("3")
                    || applicationDoc.getTC_Tacsflag1().equals("4")
                    || applicationDoc.getTC_Tacsflag1().equals("7")
                    || applicationDoc.getTC_Attention1().equals("1")
                    || applicationDoc.getTC_Attention1().equals("2")
                    || applicationDoc.getTC_Attention1().equals("3")
                    || applicationDoc.getTC_Attention1().equals("E")
                    || applicationDoc.getTC_Attention1().equals("U")
                    || !applicationDoc.getTC_Result1().equals("0")) {
                flag = true;
            }
        }
        if (flag) {
         // 要注意
            output.setStatus("1");
        } else {
         // 問題なし
            output.setStatus("2");
        }
        return output;
    }
        
        
        /**
         * 
         *    山形        
         * 
         */
        public AccountAppDetailStatusResVO yamagata (Database db,BaseResVO baseResVO) throws Exception{
            AccountAppDetailStatusResVO output = new AccountAppDetailStatusResVO();
            AccountAppDetailStatusReqVO accountAppDetailStatusReqVO	= new AccountAppDetailStatusReqVO();
            accountAppDetailStatusReqVO = (AccountAppDetailStatusReqVO) baseResVO;
            String accountAppUpdLog="(受付番号：";
            AccountYamaGataAppDoc applicationDoc = new AccountYamaGataAppDoc();
            // 申込詳細情報取得
            try {
                applicationDoc = (AccountYamaGataAppDoc) repositoryUtil.find(db,
                		accountAppDetailStatusReqVO.get_id(), AccountYamaGataAppDoc.class);
                accountAppUpdLog=accountAppUpdLog+applicationDoc.getAccountAppSeq();
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(
                        MessageKeys.E_ACCOUNTAPPDETAIL_1001);
                throw new BusinessException(messages);
            }
            // 自宅電話番号鑑定
            try {
                TeleNumberDoc teleNumberDoc = TeleNumberUtil.teleNumber(encryptorUtil.decrypt(applicationDoc.getTeleNumber()));

                applicationDoc.setTC_Access1(teleNumberDoc.getAccess1());
                applicationDoc.setTC_Result1(teleNumberDoc.getResult1());
                applicationDoc.setTC_Month1(teleNumberDoc.getMonth1());
                applicationDoc.setTC_Movetel1(teleNumberDoc.getMovetel1());
                applicationDoc.setTC_Carrier1(teleNumberDoc.getCarrier1());
                applicationDoc.setTC_Count1(teleNumberDoc.getCount1());
                applicationDoc.setTC_Attention1(teleNumberDoc.getAttention1());
                applicationDoc.setTC_Tacsflag1(teleNumberDoc.getTacsflag1());
                applicationDoc.setTC_Latestyear1(teleNumberDoc.getLatestyear1());
                applicationDoc.setTC_Latestmonth1(teleNumberDoc.getLatestmonth1());
                applicationDoc.setF01_1(teleNumberDoc.getF01_1());
                applicationDoc.setF02_1(teleNumberDoc.getF02_1());
                applicationDoc.setF03_1(teleNumberDoc.getF03_1());
                applicationDoc.setF04_1(teleNumberDoc.getF04_1());
                applicationDoc.setF05_1(teleNumberDoc.getF05_1());
                applicationDoc.setF06_1(teleNumberDoc.getF06_1());
                applicationDoc.setF07_1(teleNumberDoc.getF07_1());
                applicationDoc.setF08_1(teleNumberDoc.getF08_1());
                applicationDoc.setF09_1(teleNumberDoc.getF09_1());
                applicationDoc.setF10_1(teleNumberDoc.getF10_1());
                applicationDoc.setF11_1(teleNumberDoc.getF11_1());
                applicationDoc.setF12_1(teleNumberDoc.getF12_1());
                applicationDoc.setF13_1(teleNumberDoc.getF13_1());
                applicationDoc.setF14_1(teleNumberDoc.getF14_1());
                applicationDoc.setF15_1(teleNumberDoc.getF15_1());
                applicationDoc.setF16_1(teleNumberDoc.getF16_1());
                applicationDoc.setF17_1(teleNumberDoc.getF17_1());
                applicationDoc.setF18_1(teleNumberDoc.getF18_1());
                applicationDoc.setF19_1(teleNumberDoc.getF19_1());
                applicationDoc.setF20_1(teleNumberDoc.getF20_1());
                applicationDoc.setF21_1(teleNumberDoc.getF21_1());
                applicationDoc.setF22_1(teleNumberDoc.getF22_1());
                applicationDoc.setF23_1(teleNumberDoc.getF23_1());
                applicationDoc.setF24_1(teleNumberDoc.getF24_1());
                } catch (Exception e) {
                } // 完了
            try {
                repositoryUtil.update(db, applicationDoc);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(
                        MessageKeys.E_ACCOUNTAPPDETAIL_1002);
                throw new BusinessException(messages);
            }
            // 電話番号鑑定結果
            int TC_Count1 = 0;
            int TC_Count2 = 0;
            if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count2())) {
                TC_Count2 = Integer.parseInt(applicationDoc.getTC_Count2());
            }
            if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count1())) {
                TC_Count1 = Integer.parseInt(applicationDoc.getTC_Count1());
            }
            boolean flag = false;
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getPhoneNumber())) && Utils.isNotNullAndEmpty(applicationDoc.getTC_Result2())) {
                // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG　attention＝1or2or3orEorU or 検索結果　result=0以外
                if (TC_Count2 >= 2 || applicationDoc.getTC_Tacsflag2().equals("0")
                        || applicationDoc.getTC_Tacsflag2().equals("2")
                        || applicationDoc.getTC_Tacsflag2().equals("3")
                        || applicationDoc.getTC_Tacsflag2().equals("4")
                        || applicationDoc.getTC_Tacsflag2().equals("6")
                        || applicationDoc.getTC_Tacsflag2().equals("7")
                        || applicationDoc.getTC_Attention2().equals("1")
                        || applicationDoc.getTC_Attention2().equals("2")
                        || applicationDoc.getTC_Attention2().equals("3")
                        || applicationDoc.getTC_Attention2().equals("E")
                        || applicationDoc.getTC_Attention2().equals("U")
                        || !applicationDoc.getTC_Result2().equals("0")) {
                    // 要注意
//                  accountAppInitVO.setAppraisalTelResult("1");
                    flag = true;
                }
//              } else{
//                  // 問題なし
//                  accountAppInitVO.setAppraisalTelResult("2");
//              }
            } 
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getTeleNumber())) && Utils.isNotNullAndEmpty(applicationDoc.getTC_Result1())) {
                // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG　attention＝1or2or3orEorU or 検索結果　result=0以外
                if (TC_Count1 >= 2 || applicationDoc.getTC_Tacsflag1().equals("0")
                        || applicationDoc.getTC_Tacsflag1().equals("2")
                        || applicationDoc.getTC_Tacsflag1().equals("3")
                        || applicationDoc.getTC_Tacsflag1().equals("4")
                        || applicationDoc.getTC_Tacsflag1().equals("6")
                        || applicationDoc.getTC_Tacsflag1().equals("7")
                        || applicationDoc.getTC_Attention1().equals("1")
                        || applicationDoc.getTC_Attention1().equals("2")
                        || applicationDoc.getTC_Attention1().equals("3")
                        || applicationDoc.getTC_Attention1().equals("E")
                        || applicationDoc.getTC_Attention1().equals("U")
                        || !applicationDoc.getTC_Result1().equals("0")) {
                    flag = true;
                }
            }
            if (flag) {
             // 要注意
                output.setStatus("1");
            } else {
             // 問題なし
                output.setStatus("2");
            }
            return output;
        }

}
