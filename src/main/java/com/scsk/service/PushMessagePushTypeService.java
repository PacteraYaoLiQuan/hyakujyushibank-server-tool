package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.exception.BusinessException;
import com.scsk.model.IYoUserInfoDoc;
import com.scsk.model.YamagataMsgOpenStatusDoc;
import com.scsk.request.vo.PushMessagePushTypeReqVO;
import com.scsk.response.vo.PushMessagePushTypeResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.vo.PushMessagePushTypeVO;

@Service
public class PushMessagePushTypeService extends AbstractBLogic<PushMessagePushTypeReqVO, PushMessagePushTypeResVO> {
    @Autowired
    private EncryptorUtil encryptorUtil;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(PushMessagePushTypeReqVO detailReqVO) throws Exception {

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
    protected PushMessagePushTypeResVO doExecute(CloudantClient client, PushMessagePushTypeReqVO pushTypeReqVo)
            throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        PushMessagePushTypeResVO out = new PushMessagePushTypeResVO();
        List<PushMessagePushTypeVO> pushTypeList = new ArrayList<>();
        List<YamagataMsgOpenStatusDoc> messageOpenList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_MSGOPENSTATUS_MSGOPENSTATUS_PUSHTITLELOID, YamagataMsgOpenStatusDoc.class,
                pushTypeReqVo.get_id());
        for (YamagataMsgOpenStatusDoc msgOpenStatusDoc : messageOpenList) {
            PushMessagePushTypeVO pushMessagePushTypeVO = new PushMessagePushTypeVO();
            IYoUserInfoDoc iYoUserInfoDoc = new IYoUserInfoDoc();
            if (!msgOpenStatusDoc.getUserId().isEmpty()) {
                try {
                    iYoUserInfoDoc = repositoryUtil.find(db, msgOpenStatusDoc.getUserId(), IYoUserInfoDoc.class);
                } catch (BusinessException e) {
                }
                if (iYoUserInfoDoc != null) {
                    pushMessagePushTypeVO.setUserID(iYoUserInfoDoc.get_id());
                    String name = encryptorUtil.decrypt(iYoUserInfoDoc.getLastName()) + " "
                            + encryptorUtil.decrypt(iYoUserInfoDoc.getFirstName());
                    pushMessagePushTypeVO.setUserName(name);
                    pushMessagePushTypeVO.setOccupation(iYoUserInfoDoc.getOtherOccupations());
                    pushMessagePushTypeVO.setUserType(iYoUserInfoDoc.getUserType());
                    if ("0".equals(iYoUserInfoDoc.getUserType())) {
                        pushMessagePushTypeVO.setEmail("");
                    } else if ("1".equals(iYoUserInfoDoc.getUserType())) {
                        pushMessagePushTypeVO.setEmail(encryptorUtil.decrypt(iYoUserInfoDoc.getEmail()));
                    } else if ("2".equals(iYoUserInfoDoc.getUserType())) {
                        pushMessagePushTypeVO.setEmail(encryptorUtil.decrypt(iYoUserInfoDoc.getFacebookEmail()));
                    } else if ("4".equals(iYoUserInfoDoc.getUserType())) {
                        pushMessagePushTypeVO.setEmail(encryptorUtil.decrypt(iYoUserInfoDoc.getGoogleEmail()));
                    } else if ("3".equals(iYoUserInfoDoc.getUserType())) {
                        pushMessagePushTypeVO.setEmail(encryptorUtil.decrypt(iYoUserInfoDoc.getYahooEmail()));
                    }
                    // useUserListInitVO.setOtherOccupations(otherOccupations);
                    if (iYoUserInfoDoc.getCardNoList().size() != 0 && iYoUserInfoDoc.getCardNoList() != null) {
                        String accountNumber = "";
                        String storeNo = "";
                        for (int i = 0; i < iYoUserInfoDoc.getCardNoList().size(); i++) {
                            if (iYoUserInfoDoc.getCardNoList().get(i).getStoreNo().equals("")
                                    && iYoUserInfoDoc.getCardNoList().get(i).getStoreName().equals("")
                                    && iYoUserInfoDoc.getCardNoList().get(i).getKamokuName().equals("")
                                    && iYoUserInfoDoc.getCardNoList().get(i).getAccountNumber().equals("")
                                    && iYoUserInfoDoc.getCardNoList().get(i).getAccountName().equals("")) {
                            } else {
                                accountNumber = accountNumber
                                        + encryptorUtil.decrypt(iYoUserInfoDoc.getCardNoList().get(i).getStoreNo()) + "/"
                                        + encryptorUtil.decrypt(iYoUserInfoDoc.getCardNoList().get(i).getStoreName()) + "/"
                                        + encryptorUtil.decrypt(iYoUserInfoDoc.getCardNoList().get(i).getKamokuName()) + "/"
                                        + encryptorUtil.decrypt(iYoUserInfoDoc.getCardNoList().get(i).getAccountNumber()) + "/"
                                        + encryptorUtil.decrypt(iYoUserInfoDoc.getCardNoList().get(i).getAccountName()) + "\r\n";
                             
                            }
                        }
                        pushMessagePushTypeVO.setAccountNumber(accountNumber);
                    }
                    pushTypeList.add(pushMessagePushTypeVO);
                }
            }
            out.setPushTypeList(pushTypeList);
        }
        return out;
    }
}
