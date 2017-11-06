package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.scsk.model.IYoUserInfoDoc;
import com.scsk.model.IyoMsgOpenStatusDoc;
import com.scsk.model.YamagataMsgDetailDoc;
import com.scsk.model.YamagataMsgTitleDoc;
import com.scsk.model.geo.DeviceInfoDoc;
import com.scsk.model.geo.DeviceInfoSubDoc;
import com.scsk.request.vo.PushMessageDetailUpdateReqVO;
import com.scsk.response.vo.PushMessageDetailUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PushNotifications;
import com.scsk.util.ResultMessages;
import com.scsk.util.SessionUser;

@Service
public class PushMessageCsvDetailUpdService
        extends AbstractBLogic<PushMessageDetailUpdateReqVO, PushMessageDetailUpdateResVO> {
    @Autowired
    private ActionLog actionLog;
    @Autowired
    private PushNotifications pushNotifications;
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Value("${bank_cd}")
    private String bank_cd;

    protected void preExecute(PushMessageDetailUpdateReqVO pushMessageDetailUpdateReqVO) throws Exception {

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
    protected PushMessageDetailUpdateResVO doExecute(CloudantClient client,
            PushMessageDetailUpdateReqVO pushMessageDetailUpdateReqVO) throws Exception {

        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);

        // 伊予
        if ("0174".equals(bank_cd) || "0173".equals(bank_cd)) {
            PushMessageDetailUpdateResVO output = iyo(client, db, pushMessageDetailUpdateReqVO);
            return output;
        }
        return null;

    }

    /**
     * 
     * 伊予
     */

    public PushMessageDetailUpdateResVO iyo(CloudantClient client, Database db,
            PushMessageDetailUpdateReqVO pushMessageDetailUpdateReqVO) throws Exception {

        PushMessageDetailUpdateResVO output = new PushMessageDetailUpdateResVO();
        YamagataMsgTitleDoc titleDoc = new YamagataMsgTitleDoc();
        YamagataMsgDetailDoc detailDoc = new YamagataMsgDetailDoc();

        IYoUserInfoDoc userInfoDoc = new IYoUserInfoDoc();
        List<DeviceInfoSubDoc> deviceInfoSubDocList = new ArrayList<>();

        // for(String ss :pushMessageDetailUpdateReqVO.getCsvFileList()){
        //
        //
        // }
        List<String> pushKey = new ArrayList<>();
        String pushDetailOid = "";
        String pushTitlelOid = "";
        for (String messageCsv : pushMessageDetailUpdateReqVO.getPushMessageCsv()) {
            String messageCsvUse[] = messageCsv.split(",");
            String pushTitle = messageCsvUse[1] + messageCsvUse[2] + messageCsvUse[3];
            if (!pushKey.contains(pushTitle)) {
                // 本文
                String pushMessage = Constants.YAMAGATA_ALLPUSH_MESSAGE_HTML_START
                        + messageCsvUse[2].replace("\n", "<BR>") + Constants.PUSH_MESSAGE_HTML_END;
                // HTML メッセージ
                detailDoc.setPushMessageHTML(pushMessage);
                // メッセージ
                detailDoc.setPushMessage(messageCsvUse[2]);
                try {
                    pushDetailOid = repositoryUtil.saveToResultId(db, detailDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1003);
                    throw new BusinessException(messages);
                }

                // システム日時を取得
                Date dateSys = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
                String date = sdf.format(dateSys);
                // 配信日時
                titleDoc.setPushDate(date);
                // 配信日時並び用
                titleDoc.setPushDateForSort(dateSys.getTime());
                // プッシュ件数
                titleDoc.setPushCnt(deviceInfoSubDocList.size());
                // プッシュ成功件数
                titleDoc.setPushSuccessCnt(0);
                // プッシュ失敗件数
                titleDoc.setPushFaileCnt(0);
                // 件名
                titleDoc.setPushTitle(messageCsvUse[1]);
                // Oid
                titleDoc.setPushDetailOid(pushDetailOid);
                // PUSH配信操作者
                titleDoc.setPushAccessUser(SessionUser.userName());
                // 配信タイプ:CSV配信
                titleDoc.setPushType("3");
                // DBに更新
                try {
                    String pushMessageDetailSaveLog = "（件名：";
                    pushMessageDetailSaveLog = pushMessageDetailSaveLog + titleDoc.getPushTitle();
                    pushTitlelOid = repositoryUtil.saveToResultId(db, titleDoc);
                    actionLog.saveActionLog(Constants.ACTIONLOG_PUSH_MESSAGE_2 + pushMessageDetailSaveLog + ")", db);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1003);
                    throw new BusinessException(messages);
                }
            }
            pushKey.add(pushTitle);
            int sum = 1;
            try {
                userInfoDoc = (IYoUserInfoDoc) repositoryUtil.find(db, messageCsvUse[0], IYoUserInfoDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1004);
                throw new BusinessException(messages);
            }
            if (userInfoDoc != null) {
                if (userInfoDoc.getDeviceInfoList() != null) {
                    List<String> deviceIdList = new ArrayList<>();
                    for (DeviceInfoDoc deviceInfoDoc : userInfoDoc.getDeviceInfoList()) {
                        if (!encryptorUtil.decrypt(deviceInfoDoc.getDeviceTokenId()).isEmpty()) {
                            deviceIdList.add(encryptorUtil.decrypt(deviceInfoDoc.getDeviceTokenId()));
                        }
                    }
                    IyoMsgOpenStatusDoc iyoMsgOpenStatusDoc = new IyoMsgOpenStatusDoc();
                    // 任意配信件名OID
                    iyoMsgOpenStatusDoc.setPushTitlelOid(pushTitlelOid);
                    // ユーザーID
                    iyoMsgOpenStatusDoc.setUserId(userInfoDoc.get_id());
                    // 送達区分
                    iyoMsgOpenStatusDoc.setArriveKBN("0");
                    // 開封区分
                    iyoMsgOpenStatusDoc.setOpenKBN("0");
                    // 開封日時
                    iyoMsgOpenStatusDoc.setOpenDateTime("");
                    String queryKey = userInfoDoc.get_id();
                    List<IyoMsgOpenStatusDoc> msgOpenStatusDocList = repositoryUtil.getView(db,
                            ApplicationKeys.INSIGHTVIEW_MSGOPENSTATUS_MSGOPENSTATUS_USERID, IyoMsgOpenStatusDoc.class,
                            queryKey);
                    for (IyoMsgOpenStatusDoc statusModifyDoc : msgOpenStatusDocList) {
                        if ("0".equals(statusModifyDoc.getOpenKBN())) {
                            sum = sum + 1;
                        }
                    }
                    String messageCode = pushNotifications.sendMessage(messageCsvUse[1], sum,
                            deviceIdList);
                    // PUSH区分
                    if (Constants.RETURN_OK.equals(messageCode)) {
                        iyoMsgOpenStatusDoc.setPushKBN("S");
                    } else {
                        iyoMsgOpenStatusDoc.setPushKBN("F");
                    }
                    try {
                        repositoryUtil.save(db, iyoMsgOpenStatusDoc);
                    } catch (BusinessException e) {
                        // e.printStackTrace();
                        LogInfoUtil.LogDebug(e.getMessage());
                        // エラーメッセージを出力、処理終了。
                        ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSHMESSAGE_1003);
                        throw new BusinessException(messages);
                    }
                }
            }
        }
        return output;
    }
}
