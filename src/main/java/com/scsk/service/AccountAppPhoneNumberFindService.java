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
import com.scsk.model.rev.PhoneNumberDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PhoneNumberUtil;
import com.scsk.util.ResultMessages;

/**
 * 携帯電話番号APIメソッド。
 * 
 * @return ResponseEntity　戻るデータオブジェクト
 */
@Service
public class AccountAppPhoneNumberFindService
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
     *    広島
     *            
     */
        public AccountAppDetailStatusResVO hirosima(Database db,BaseResVO baseResVO) throws Exception{
        AccountAppDetailStatusResVO output = new AccountAppDetailStatusResVO();
        AccountAppDetailStatusReqVO accountAppDetailStatusReqVO = new AccountAppDetailStatusReqVO();
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
            PhoneNumberDoc phoneNumberDoc = PhoneNumberUtil.phoneNumber(encryptorUtil.decrypt(applicationDoc.getPhoneNumber()));
            applicationDoc.setTC_Access2(phoneNumberDoc.getAccess2());
            applicationDoc.setTC_Result2(phoneNumberDoc.getResult2());
            applicationDoc.setTC_Month2(phoneNumberDoc.getMonth2());
            applicationDoc.setTC_Movetel2(phoneNumberDoc.getMovetel2());
            applicationDoc.setTC_Carrier2(phoneNumberDoc.getCarrier2());
            applicationDoc.setTC_Count2(phoneNumberDoc.getCount2());
            applicationDoc.setTC_Attention2(phoneNumberDoc.getAttention2());
            applicationDoc.setTC_Tacsflag2(phoneNumberDoc.getTacsflag2());
            applicationDoc.setTC_Latestyear2(phoneNumberDoc.getLatestyear2());
            applicationDoc.setTC_Latestmonth2(phoneNumberDoc.getLatestmonth2());
            applicationDoc.setTC_F01_2(phoneNumberDoc.getF01_2());
            applicationDoc.setTC_F02_2(phoneNumberDoc.getF02_2());
            applicationDoc.setTC_F03_2(phoneNumberDoc.getF03_2());
            applicationDoc.setTC_F04_2(phoneNumberDoc.getF04_2());
            applicationDoc.setTC_F05_2(phoneNumberDoc.getF05_2());
            applicationDoc.setTC_F06_2(phoneNumberDoc.getF06_2());
            applicationDoc.setTC_F07_2(phoneNumberDoc.getF07_2());
            applicationDoc.setTC_F08_2(phoneNumberDoc.getF08_2());
            applicationDoc.setTC_F09_2(phoneNumberDoc.getF09_2());
            applicationDoc.setTC_F10_2(phoneNumberDoc.getF10_2());
            applicationDoc.setTC_F11_2(phoneNumberDoc.getF11_2());
            applicationDoc.setTC_F12_2(phoneNumberDoc.getF12_2());
            applicationDoc.setTC_F13_2(phoneNumberDoc.getF13_2());
            applicationDoc.setTC_F14_2(phoneNumberDoc.getF14_2());
            applicationDoc.setTC_F15_2(phoneNumberDoc.getF15_2());
            applicationDoc.setTC_F16_2(phoneNumberDoc.getF16_2());
            applicationDoc.setTC_F17_2(phoneNumberDoc.getF17_2());
            applicationDoc.setTC_F18_2(phoneNumberDoc.getF18_2());
            applicationDoc.setTC_F19_2(phoneNumberDoc.getF19_2());
            applicationDoc.setTC_F20_2(phoneNumberDoc.getF20_2());
            applicationDoc.setTC_F21_2(phoneNumberDoc.getF21_2());
            applicationDoc.setTC_F22_2(phoneNumberDoc.getF22_2());
            applicationDoc.setTC_F23_2(phoneNumberDoc.getF23_2());
            applicationDoc.setTC_F24_2(phoneNumberDoc.getF24_2());

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
        return output;
    }
        
        /**
         * 
         *    山形
         *            
         */
            public AccountAppDetailStatusResVO yamagata(Database db,BaseResVO baseResVO) throws Exception{
            AccountAppDetailStatusResVO output = new AccountAppDetailStatusResVO();
            AccountAppDetailStatusReqVO accountAppDetailStatusReqVO = new AccountAppDetailStatusReqVO();
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
                PhoneNumberDoc phoneNumberDoc = PhoneNumberUtil.phoneNumber(encryptorUtil.decrypt(applicationDoc.getPhoneNumber()));
                applicationDoc.setTC_Access2(phoneNumberDoc.getAccess2());
                applicationDoc.setTC_Result2(phoneNumberDoc.getResult2());
                applicationDoc.setTC_Month2(phoneNumberDoc.getMonth2());
                applicationDoc.setTC_Movetel2(phoneNumberDoc.getMovetel2());
                applicationDoc.setTC_Carrier2(phoneNumberDoc.getCarrier2());
                applicationDoc.setTC_Count2(phoneNumberDoc.getCount2());
                applicationDoc.setTC_Attention2(phoneNumberDoc.getAttention2());
                applicationDoc.setTC_Tacsflag2(phoneNumberDoc.getTacsflag2());
                applicationDoc.setTC_Latestyear2(phoneNumberDoc.getLatestyear2());
                applicationDoc.setTC_Latestmonth2(phoneNumberDoc.getLatestmonth2());
                applicationDoc.setF01_2(phoneNumberDoc.getF01_2());
                applicationDoc.setF02_2(phoneNumberDoc.getF02_2());
                applicationDoc.setF03_2(phoneNumberDoc.getF03_2());
                applicationDoc.setF04_2(phoneNumberDoc.getF04_2());
                applicationDoc.setF05_2(phoneNumberDoc.getF05_2());
                applicationDoc.setF06_2(phoneNumberDoc.getF06_2());
                applicationDoc.setF07_2(phoneNumberDoc.getF07_2());
                applicationDoc.setF08_2(phoneNumberDoc.getF08_2());
                applicationDoc.setF09_2(phoneNumberDoc.getF09_2());
                applicationDoc.setF10_2(phoneNumberDoc.getF10_2());
                applicationDoc.setF11_2(phoneNumberDoc.getF11_2());
                applicationDoc.setF12_2(phoneNumberDoc.getF12_2());
                applicationDoc.setF13_2(phoneNumberDoc.getF13_2());
                applicationDoc.setF14_2(phoneNumberDoc.getF14_2());
                applicationDoc.setF15_2(phoneNumberDoc.getF15_2());
                applicationDoc.setF16_2(phoneNumberDoc.getF16_2());
                applicationDoc.setF17_2(phoneNumberDoc.getF17_2());
                applicationDoc.setF18_2(phoneNumberDoc.getF18_2());
                applicationDoc.setF19_2(phoneNumberDoc.getF19_2());
                applicationDoc.setF20_2(phoneNumberDoc.getF20_2());
                applicationDoc.setF21_2(phoneNumberDoc.getF21_2());
                applicationDoc.setF22_2(phoneNumberDoc.getF22_2());
                applicationDoc.setF23_2(phoneNumberDoc.getF23_2());
                applicationDoc.setF24_2(phoneNumberDoc.getF24_2());

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
            return output;
        }

}
