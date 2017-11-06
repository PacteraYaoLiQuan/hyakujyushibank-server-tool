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
import com.scsk.model.Account114DocumentDoc;
import com.scsk.model.DocumentImageDoc;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.request.vo.StatusModifyReqVO;
import com.scsk.response.vo.Account114DocumentDetailResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.response.vo.StatusModifyResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
@Service
public class DocumentRecordAppDetailSelService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;
    @Autowired
    private DocumentStatusModifyService documentStatusModifyService;
    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(BaseResVO detailReqVO) throws Exception {

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
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {

        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        if("0173".equals(bank_cd)){

            Account114DocumentDetailResVO pushRecordAppDetailResVO = hyakujyushi(db, baseResVO);
            return pushRecordAppDetailResVO;
        
        }
        return null;
    } 

    private Account114DocumentDetailResVO hyakujyushi(Database db, BaseResVO baseResVO) throws Exception {
        Account114DocumentDetailResVO account114DocumentDetailResVO = new Account114DocumentDetailResVO();
        AccountAppDetailReqVO input = (AccountAppDetailReqVO) baseResVO;
        String accountAppDetailLog = "(受付番号：";
        Account114DocumentDoc account114DocumentDoc = new Account114DocumentDoc();
        try {
            account114DocumentDoc = (Account114DocumentDoc) repositoryUtil.find(db, input.get_id(),
                    Account114DocumentDoc.class);
            accountAppDetailLog = accountAppDetailLog + account114DocumentDoc.getDocumentAppSeq();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTS_1006);
            throw new BusinessException(messages);
        }
        account114DocumentDetailResVO.setAgreeCheck(account114DocumentDoc.getAgreeCheck());
        account114DocumentDetailResVO.setAgreeTime(account114DocumentDoc.getAgreeTime());
        account114DocumentDetailResVO.setBankNo(encryptorUtil.decrypt(account114DocumentDoc.getBankNo()));
        account114DocumentDetailResVO.setKanaName(encryptorUtil.decrypt(account114DocumentDoc.getKanaFirstName())
                + encryptorUtil.decrypt(account114DocumentDoc.getKanaLastName()));
        account114DocumentDetailResVO.setName(encryptorUtil.decrypt(account114DocumentDoc.getFirstName())
                + encryptorUtil.decrypt(account114DocumentDoc.getLastName()));
        account114DocumentDetailResVO.setOther(encryptorUtil.decrypt(account114DocumentDoc.getOther()));
        String purpose = "";
        if ("0".equals(account114DocumentDoc.getPurpose())) {
            purpose = "ATMカードローンのお申込み";
        } else if ("1".equals(account114DocumentDoc.getPurpose())) {
            purpose = "その他のローンのお申込み";
        } else if ("2".equals(account114DocumentDoc.getPurpose())) {
            purpose = "その他のお取り引き";
        }
        account114DocumentDetailResVO.setPurpose(purpose);
        account114DocumentDetailResVO.setReadBirthDay(encryptorUtil.decrypt(account114DocumentDoc.getReadBirthDay()));
        account114DocumentDetailResVO
                .setReadDriverLicenseNo(encryptorUtil.decrypt(account114DocumentDoc.getReadDriverLicenseNo()));

        account114DocumentDetailResVO.setSelfConfirmFlg(account114DocumentDoc.getSelfConfirmFlg());
        account114DocumentDetailResVO.setStoreName(encryptorUtil.decrypt(account114DocumentDoc.getStoreName()));
        account114DocumentDetailResVO.setStoreNo(encryptorUtil.decrypt(account114DocumentDoc.getStoreNo()));
        account114DocumentDetailResVO.setTelephoneNo(encryptorUtil.decrypt(account114DocumentDoc.getTelephoneNo()));
        DocumentImageDoc documentImageDoc = new DocumentImageDoc();
        if (account114DocumentDoc.getType().contains("1")) {
            try {
                documentImageDoc = (DocumentImageDoc) repositoryUtil.find(db, account114DocumentDoc.getCard1Seq(),
                        DocumentImageDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTS_1006);
                throw new BusinessException(messages);
            }
            account114DocumentDetailResVO.setCard6Seq(documentImageDoc.getCardImageBack());
        }
        if (!account114DocumentDoc.getCard1Seq().equals("")) {
            try {
                documentImageDoc = (DocumentImageDoc) repositoryUtil.find(db, account114DocumentDoc.getCard1Seq(),
                        DocumentImageDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTS_1006);
                throw new BusinessException(messages);
            }
            account114DocumentDetailResVO.setCard1Seq(documentImageDoc.getCardImageFront());
        } else {
            account114DocumentDetailResVO.setCard1Seq("");
        }
        if (!account114DocumentDoc.getCard2Seq().equals("")) {
            try {
                documentImageDoc = (DocumentImageDoc) repositoryUtil.find(db, account114DocumentDoc.getCard2Seq(),
                        DocumentImageDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTS_1006);
                throw new BusinessException(messages);
            }
            account114DocumentDetailResVO.setCard2Seq(documentImageDoc.getCardImageFront());
        } else {
            account114DocumentDetailResVO.setCard2Seq("");
        }
        if (!account114DocumentDoc.getCard3Seq().equals("")) {
            try {
                documentImageDoc = (DocumentImageDoc) repositoryUtil.find(db, account114DocumentDoc.getCard3Seq(),
                        DocumentImageDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTS_1006);
                throw new BusinessException(messages);
            }
            account114DocumentDetailResVO.setCard3Seq(documentImageDoc.getCardImageFront());
        } else {
            account114DocumentDetailResVO.setCard3Seq("");
        }
        if (!account114DocumentDoc.getCard4Seq().equals("")) {
            try {
                documentImageDoc = (DocumentImageDoc) repositoryUtil.find(db, account114DocumentDoc.getCard4Seq(),
                        DocumentImageDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTS_1006);
                throw new BusinessException(messages);
            }
            account114DocumentDetailResVO.setCard4Seq(documentImageDoc.getCardImageFront());
        } else {
            account114DocumentDetailResVO.setCard4Seq("");
        }
        if (!account114DocumentDoc.getCard5Seq().equals("")) {
            try {
                documentImageDoc = (DocumentImageDoc) repositoryUtil.find(db, account114DocumentDoc.getCard5Seq(),
                        DocumentImageDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTS_1006);
                throw new BusinessException(messages);
            }
            account114DocumentDetailResVO.setCard5Seq(documentImageDoc.getCardImageFront());
        } else {
            account114DocumentDetailResVO.setCard5Seq("");
        }

        String typeFlg = "";
        if ("0".equals(account114DocumentDoc.getTypeFlg())) {
            typeFlg = "いいえ";
        } else {
            typeFlg = "はい";
        }
        account114DocumentDetailResVO.setTypeFlg(typeFlg);
        account114DocumentDetailResVO.setUserId(encryptorUtil.decrypt(account114DocumentDoc.getUserId()));
        account114DocumentDetailResVO.setUserType(account114DocumentDoc.getUserType());
        account114DocumentDetailResVO.setDocumentAppSeq(account114DocumentDoc.getDocumentAppSeq());
        account114DocumentDetailResVO.setReadFirstName(encryptorUtil.decrypt(account114DocumentDoc.getReadFirstName()));
        account114DocumentDetailResVO.setReadLastName(encryptorUtil.decrypt(account114DocumentDoc.getReadLastName()));
        account114DocumentDetailResVO.setDocumentAppTime(account114DocumentDoc.getDocumentAppTime());
        account114DocumentDetailResVO.setSendItem(account114DocumentDoc.getSendItem());
        account114DocumentDetailResVO.setStatus(account114DocumentDoc.getStatus());
        String userType = "";
        if ("0".equals(account114DocumentDoc.getUserType())) {
            userType = "匿名";
        } else {
            userType = "正式";
        }
        account114DocumentDetailResVO.setBankNo(encryptorUtil.decrypt(account114DocumentDoc.getBankNo()));
        account114DocumentDetailResVO.setStoreNo(encryptorUtil.decrypt(account114DocumentDoc.getStoreNo()));
        account114DocumentDetailResVO.setStoreName(encryptorUtil.decrypt(account114DocumentDoc.getStoreName()));
        account114DocumentDetailResVO.setUserType(userType);
        // push通知履歴一覧を取得
        StatusModifyReqVO yamagataStatusModifyReqVO = new StatusModifyReqVO();
        yamagataStatusModifyReqVO.setAccountAppSeq(account114DocumentDoc.getDocumentAppSeq());
        yamagataStatusModifyReqVO.setUserId(account114DocumentDoc.getUserId());
        StatusModifyResVO statusModifyResVO = documentStatusModifyService.execute(yamagataStatusModifyReqVO);
        account114DocumentDetailResVO.setStatusModifyList(statusModifyResVO.getStatusModifyListVO());

        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_5 + accountAppDetailLog + ")", db);
        return account114DocumentDetailResVO;
    }


    /**
     * date formartメソッド。
     * 
     * @param date
     *            処理前日付
     * @return date 処理後日付
     * @throws Exception
     */
    public String dateFormat(String dateInput) {
        String dateOutput = "";
        if (Utils.isNotNullAndEmpty(dateInput) && dateInput.length() > 7) {
            dateOutput = dateInput.substring(0, 4) + "/" + dateInput.substring(4, 6) + "/" + dateInput.substring(6, 8);
        }

        return dateOutput;
    }

}
