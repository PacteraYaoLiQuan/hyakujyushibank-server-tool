package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

@Service
public class AccountDocumentSelService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        Account114DocumentDetailResVO account114DocumentDetailResVO = new Account114DocumentDetailResVO();
        Database db = client.database(Constants.DB_NAME, false);
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
        account114DocumentDetailResVO.setType(account114DocumentDoc.getType());
        account114DocumentDetailResVO.setBankNo(encryptorUtil.decrypt(account114DocumentDoc.getBankNo()));
        account114DocumentDetailResVO.setKanaLastName(encryptorUtil.decrypt(account114DocumentDoc.getKanaLastName()));
        account114DocumentDetailResVO.setKanaFirstName(encryptorUtil.decrypt(account114DocumentDoc.getKanaFirstName()));
        account114DocumentDetailResVO.setLastName(encryptorUtil.decrypt(account114DocumentDoc.getLastName()));
        account114DocumentDetailResVO.setFirstName(encryptorUtil.decrypt(account114DocumentDoc.getFirstName()));
        account114DocumentDetailResVO.setOther(encryptorUtil.decrypt(account114DocumentDoc.getOther()));
//        String purpose = "";
//        if ("0".equals(account114DocumentDoc.getPurpose())) {
//            purpose = "ATMカードローンのお申込み";
//        } else if ("1".equals(account114DocumentDoc.getPurpose())) {
//            purpose = "その他のローンのお申込み";
//        } else if ("2".equals(account114DocumentDoc.getPurpose())) {
//            purpose = "その他のお取り引き";
//        }
        account114DocumentDetailResVO.setPurpose(account114DocumentDoc.getPurpose());
        account114DocumentDetailResVO.setReadBirthDay(encryptorUtil.decrypt(account114DocumentDoc.getReadBirthDay()));
        account114DocumentDetailResVO
                .setReadDriverLicenseNo(encryptorUtil.decrypt(account114DocumentDoc.getReadDriverLicenseNo()));

        account114DocumentDetailResVO.setSelfConfirmFlg(account114DocumentDoc.getSelfConfirmFlg());
        account114DocumentDetailResVO.setStoreName(encryptorUtil.decrypt(account114DocumentDoc.getStoreName()));
        account114DocumentDetailResVO.setStoreNo(encryptorUtil.decrypt(account114DocumentDoc.getStoreNo()));
        account114DocumentDetailResVO.setTelephoneNo(encryptorUtil.decrypt(account114DocumentDoc.getTelephoneNo()));
        DocumentImageDoc documentImageDoc = new DocumentImageDoc();
        if (account114DocumentDoc.getType().contains("0")) {
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
            account114DocumentDetailResVO.setCard6Seq(encryptorUtil.decrypt(documentImageDoc.getCardImageBack()));
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
            account114DocumentDetailResVO.setCard1Seq(encryptorUtil.decrypt(documentImageDoc.getCardImageFront()));
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
            account114DocumentDetailResVO.setCard2Seq(encryptorUtil.decrypt(documentImageDoc.getCardImageFront()));
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
            account114DocumentDetailResVO.setCard3Seq(encryptorUtil.decrypt(documentImageDoc.getCardImageFront()));
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
            account114DocumentDetailResVO.setCard4Seq(encryptorUtil.decrypt(documentImageDoc.getCardImageFront()));
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
            account114DocumentDetailResVO.setCard5Seq(encryptorUtil.decrypt(documentImageDoc.getCardImageFront()));
        } else {
            account114DocumentDetailResVO.setCard5Seq("");
        }

        account114DocumentDetailResVO.setUserId(account114DocumentDoc.getUserId());
        account114DocumentDetailResVO.setUserType(account114DocumentDoc.getUserType());
        account114DocumentDetailResVO.setDocumentAppSeq(account114DocumentDoc.getDocumentAppSeq());
        account114DocumentDetailResVO.setReadFirstName(encryptorUtil.decrypt(account114DocumentDoc.getReadFirstName()));
        account114DocumentDetailResVO.setReadLastName(encryptorUtil.decrypt(account114DocumentDoc.getReadLastName()));
        account114DocumentDetailResVO.setDocumentAppTime(account114DocumentDoc.getDocumentAppTime());
        account114DocumentDetailResVO.setSendItem(account114DocumentDoc.getSendItem());
        account114DocumentDetailResVO.setStatus(account114DocumentDoc.getStatus());
        account114DocumentDetailResVO.setOtherTypeContent(account114DocumentDoc.getOtherTypeContent());
//        String userType = "";
//        if ("0".equals(account114DocumentDoc.getUserType())) {
//            userType = "匿名";
//        } else {
//            userType = "正式";
//        }
        account114DocumentDetailResVO.setBankNo(encryptorUtil.decrypt(account114DocumentDoc.getBankNo()));
        account114DocumentDetailResVO.setStoreNo(encryptorUtil.decrypt(account114DocumentDoc.getStoreNo()));
        account114DocumentDetailResVO.setStoreName(encryptorUtil.decrypt(account114DocumentDoc.getStoreName()));
        account114DocumentDetailResVO.setUserType(account114DocumentDoc.getUserType());
        // push通知履歴一覧を取得
        StatusModifyReqVO yamagataStatusModifyReqVO = new StatusModifyReqVO();
        yamagataStatusModifyReqVO.setAccountAppSeq(account114DocumentDoc.getDocumentAppSeq());
        yamagataStatusModifyReqVO.setUserId(account114DocumentDoc.getUserId());
//        StatusModifyResVO statusModifyResVO = documentStatusModifyService.execute(yamagataStatusModifyReqVO);
//        account114DocumentDetailResVO.setStatusModifyList(statusModifyResVO.getStatusModifyListVO());

        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_5 + accountAppDetailLog + ")", db);
        return account114DocumentDetailResVO;
    }

}
