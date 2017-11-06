package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.AccountAppDoc;
import com.scsk.model.rev.GeologicalAppraisalIPDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppDetailStatusReqVO;
import com.scsk.response.vo.AccountAppDetailStatusResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.GeologicalAppraisalIPUtli;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 *  IP鑑定APIメソッド。
 * 
 * @return ResponseEntity　戻るデータオブジェクト
 */
@Service
public class AccountAppIpAddressFindFindService
        extends
        AbstractBLogic<AccountAppDetailStatusReqVO, AccountAppDetailStatusResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
//  @Autowired
//  private PushNotifications pushNotifications;

    @Override
    protected void preExecute(
            AccountAppDetailStatusReqVO applicationDetailStatusReqVO)
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
    protected AccountAppDetailStatusResVO doExecute(CloudantClient client,
            AccountAppDetailStatusReqVO applicationDetailStatusReqVO)
            throws Exception {

        AccountAppDetailStatusResVO output = new AccountAppDetailStatusResVO();
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        String accountAppUpdLog="(受付番号：";
        AccountAppDoc applicationDoc = new AccountAppDoc();
        // 申込詳細情報取得
        try {
            applicationDoc = (AccountAppDoc) repositoryUtil.find(db,
                    applicationDetailStatusReqVO.get_id(), AccountAppDoc.class);
            accountAppUpdLog=accountAppUpdLog+applicationDoc.getAccountAppSeq();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(
                    MessageKeys.E_ACCOUNTAPPDETAIL_1001);
            throw new BusinessException(messages);
        }
        // ＩＰアドレス鑑定
        try {

            GeologicalAppraisalIPDoc geologicalAppraisalIPDoc = GeologicalAppraisalIPUtli.GeologicalAppraisalIP(encryptorUtil.decrypt(applicationDoc.getIpAddress()), encryptorUtil.decrypt(applicationDoc.getPostCode()));
            applicationDoc.setIC_CountryCode(geologicalAppraisalIPDoc.getCountryCode());
            applicationDoc.setIC_CountryName(geologicalAppraisalIPDoc.getCountryName());
            applicationDoc.setIC_CountryThreat(geologicalAppraisalIPDoc.getCountryThreat());
            applicationDoc.setIC_PSIP(geologicalAppraisalIPDoc.getPs_ip());
            applicationDoc.setIC_Proxy(geologicalAppraisalIPDoc.getProxy());
            applicationDoc.setIC_isMobile(geologicalAppraisalIPDoc.getIsMobile());
            applicationDoc.setIC_Carrier(geologicalAppraisalIPDoc.getCarrier());
            applicationDoc.setIC_CompanyName(geologicalAppraisalIPDoc.getCompanyName());
            applicationDoc.setIC_CompanyDomain(geologicalAppraisalIPDoc.getCompanyDomain());
            applicationDoc.setIC_CompanyCity(geologicalAppraisalIPDoc.getCompanyCity());
            applicationDoc.setIC_Distance(geologicalAppraisalIPDoc.getDistance());
            applicationDoc.setIC_IpThreat(geologicalAppraisalIPDoc.getThreat());
            applicationDoc.setIC_SearchHistory(geologicalAppraisalIPDoc.getSearchHistory());

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
