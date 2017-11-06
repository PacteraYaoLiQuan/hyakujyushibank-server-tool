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
import com.scsk.model.UtilDoc;
import com.scsk.model.YamagataMsgDetailDoc;
import com.scsk.model.YamagataMsgOpenStatusDoc;
import com.scsk.model.YamagataMsgTitleDoc;
import com.scsk.model.YamagataUserInfoDoc;
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
import com.scsk.util.Utils;

@Service
public class PushMessageDetailUpdService
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
        // 山形
        if ("0122".equals(bank_cd)) {
            PushMessageDetailUpdateResVO output = yamagata(client, db, pushMessageDetailUpdateReqVO);
            return output;
        }
        // 伊予
        else if ("0174".equals(bank_cd) || "0173".equals(bank_cd)) {
            PushMessageDetailUpdateResVO output = iyo(client, db, pushMessageDetailUpdateReqVO);
            return output;
        }
        return null;

    }

    /**
     * 
     * 山形
     */

    public PushMessageDetailUpdateResVO yamagata(CloudantClient client, Database db,
            PushMessageDetailUpdateReqVO pushMessageDetailUpdateReqVO) throws Exception {

        PushMessageDetailUpdateResVO output = new PushMessageDetailUpdateResVO();
        YamagataMsgTitleDoc titleDoc = new YamagataMsgTitleDoc();
        YamagataMsgDetailDoc detailDoc = new YamagataMsgDetailDoc();
        List<YamagataUserInfoDoc> userInfoDocList = new ArrayList<>();
        List<String> subDeviceIdList = new ArrayList<>();
        List<YamagataUserInfoDoc> yamagataUserInfoDocList = new ArrayList<>();

        try {
            userInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_YAMAGATAUSERINFOLIST_USERINFOLIST,
                    YamagataUserInfoDoc.class);

            for (YamagataUserInfoDoc userDoc : userInfoDocList) {
                if (Utils.isNotNullAndEmpty(userDoc.getDeviceId())) {
                    yamagataUserInfoDocList.add(userDoc);
                }
            }
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1004);
            throw new BusinessException(messages);
        }

        String pushDetailOid = "";
        // 本文
        String pushMessage = Constants.YAMAGATA_PUSH_MESSAGE_HTML_START
                + pushMessageDetailUpdateReqVO.getPushMessage().replace("\n", "<BR>")
                + Constants.YAMAGATA_PUSH_MESSAGE_HTML_END;
        // HTML メッセージ
        detailDoc.setPushMessageHTML(pushMessage);
        // メッセージ
        detailDoc.setPushMessage(pushMessageDetailUpdateReqVO.getPushMessage());

        try {
            pushDetailOid = repositoryUtil.saveToResultId(db, detailDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1003);
            throw new BusinessException(messages);
        }

        String pushTitlelOid = "";
        // システム日時を取得
        Date dateSys = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(dateSys);
        // 配信日時
        titleDoc.setPushDate(date);
        // 配信日時並び用
        titleDoc.setPushDateForSort(dateSys.getTime());
        // プッシュ件数
        titleDoc.setPushCnt(yamagataUserInfoDocList.size());
        // プッシュ成功件数
        titleDoc.setPushSuccessCnt(0);
        // プッシュ失敗件数
        titleDoc.setPushFaileCnt(0);
        // 件名
        titleDoc.setPushTitle(pushMessageDetailUpdateReqVO.getPushTitle());
        // Oid
        titleDoc.setPushDetailOid(pushDetailOid);
        // PUSH配信操作者
        titleDoc.setPushAccessUser(SessionUser.userName());

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

        int i = 1;
        int failed = 0;
        int success = 0;

        List<YamagataMsgOpenStatusDoc> yamagataMsgOpenStatusDocList = new ArrayList<>();
        List<UtilDoc> yamagataMsgOpenStatusDocListNew = new ArrayList<>();
        for (YamagataUserInfoDoc userDoc : yamagataUserInfoDocList) {
            subDeviceIdList.add(encryptorUtil.decrypt(userDoc.getDeviceId()));
            YamagataMsgOpenStatusDoc yamagataMsgOpenStatusDoc = new YamagataMsgOpenStatusDoc();
            // 任意配信件名OID
            yamagataMsgOpenStatusDoc.setPushTitlelOid(pushTitlelOid);
            // ユーザーID
            yamagataMsgOpenStatusDoc.setUserId(userDoc.get_id());
            // 端末ID
            yamagataMsgOpenStatusDoc.setDeviceTokenId(userDoc.getDeviceId());
            // iOS／Android区分
            if (userDoc.getOsVersion().contains("Android")) {
                yamagataMsgOpenStatusDoc.setOsKBN("A");
            } else {
                yamagataMsgOpenStatusDoc.setOsKBN("I");
            }
            // 送達区分
            yamagataMsgOpenStatusDoc.setArriveKBN("0");
            // 開封区分
            yamagataMsgOpenStatusDoc.setOpenKBN("0");
            // 開封日時
            yamagataMsgOpenStatusDoc.setOpenDateTime("");
            yamagataMsgOpenStatusDocList.add(yamagataMsgOpenStatusDoc);
            if (i % 100 == 0) {
                String messageCode = pushNotifications.sendMessage(
                        pushMessageDetailUpdateReqVO.getPushMessageList().get(0).getPushTitle(), null, subDeviceIdList);
                SimpleDateFormat sdfOpen = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
                String dateOpen = sdfOpen.format(new Date());
                for (YamagataMsgOpenStatusDoc yamagataMsgOpenStatusDocOld : yamagataMsgOpenStatusDocList) {
                    // システム日時を取得
                    if (SessionUser.userId() != "") {
                        // 作成者設定
                        yamagataMsgOpenStatusDocOld.setCreatedBy(SessionUser.userId());
                        // 更新者設定
                        yamagataMsgOpenStatusDocOld.setUpdatedBy("");
                    }
                    // 作成日時設定
                    yamagataMsgOpenStatusDocOld.setCreatedDate(dateOpen);
                    // 更新日時設定
                    yamagataMsgOpenStatusDocOld.setUpdatedDate("");
                    // 削除フラグ
                    yamagataMsgOpenStatusDocOld.setDelFlg("0");
                    // PUSH区分
                    if (Constants.RETURN_OK.equals(messageCode)) {
                        yamagataMsgOpenStatusDocOld.setPushKBN("S");
                    } else {
                        yamagataMsgOpenStatusDocOld.setPushKBN("F");
                    }
                    yamagataMsgOpenStatusDocListNew.add(yamagataMsgOpenStatusDocOld);
                }

                try {
                    repositoryUtil.bulk(db, yamagataMsgOpenStatusDocListNew);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSHMESSAGE_1003);
                    throw new BusinessException(messages);
                }

                if (Constants.RETURN_OK.equals(messageCode)) {
                    success = success + 100;
                } else {
                    failed = failed + 100;
                }
                subDeviceIdList = new ArrayList<String>();
                yamagataMsgOpenStatusDocListNew = new ArrayList<>();
                yamagataMsgOpenStatusDocList = new ArrayList<>();
            }

            if (i == yamagataUserInfoDocList.size()) {
                String messageCode = pushNotifications.sendMessage(
                        pushMessageDetailUpdateReqVO.getPushMessageList().get(0).getPushTitle(), null, subDeviceIdList);
                SimpleDateFormat sdfOpen = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
                String dateOpen = sdfOpen.format(new Date());
                for (YamagataMsgOpenStatusDoc yamagataMsgOpenStatusDocOld : yamagataMsgOpenStatusDocList) {
                    // システム日時を取得
                    if (SessionUser.userId() != "") {
                        // 作成者設定
                        yamagataMsgOpenStatusDocOld.setCreatedBy(SessionUser.userId());
                        // 更新者設定
                        yamagataMsgOpenStatusDocOld.setUpdatedBy("");
                    }
                    // 作成日時設定
                    yamagataMsgOpenStatusDocOld.setCreatedDate(dateOpen);
                    // 更新日時設定
                    yamagataMsgOpenStatusDocOld.setUpdatedDate("");
                    // 削除フラグ
                    yamagataMsgOpenStatusDocOld.setDelFlg("0");
                    // PUSH区分
                    if (Constants.RETURN_OK.equals(messageCode)) {
                        yamagataMsgOpenStatusDocOld.setPushKBN("S");
                    } else {
                        yamagataMsgOpenStatusDocOld.setPushKBN("F");
                    }

                    yamagataMsgOpenStatusDocListNew.add(yamagataMsgOpenStatusDocOld);
                }

                if (Constants.RETURN_OK.equals(messageCode)) {
                    success = success + i % 100;
                } else {
                    failed = failed + i % 100;
                }

                // PUSH区分
                if (Constants.RETURN_OK.equals(messageCode)) {
                    yamagataMsgOpenStatusDoc.setPushKBN("S");
                } else {
                    yamagataMsgOpenStatusDoc.setPushKBN("F");
                }

                try {
                    repositoryUtil.bulk(db, yamagataMsgOpenStatusDocListNew);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSHMESSAGE_1003);
                    throw new BusinessException(messages);
                }
            }
            i++;
        }
        titleDoc = new YamagataMsgTitleDoc();
        titleDoc = repositoryUtil.find(db, pushTitlelOid, YamagataMsgTitleDoc.class);
        titleDoc.set_id(pushTitlelOid);
        // プッシュ成功件数
        titleDoc.setPushSuccessCnt(success);
        // プッシュ失敗件数
        titleDoc.setPushFaileCnt(failed);
        // DBに更新
        try {
            repositoryUtil.update(db, titleDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGELIST_1002);
            throw new BusinessException(messages);
        }
        return output;
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
        String pushDetailOid = "";
        // 本文
        String pushMessage = Constants.YAMAGATA_ALLPUSH_MESSAGE_HTML_START
                + pushMessageDetailUpdateReqVO.getPushMessage().replace("\n", "<BR>")
                + Constants.PUSH_MESSAGE_HTML_END;
        // HTML メッセージ
        detailDoc.setPushMessageHTML(pushMessage);
        // メッセージ
        detailDoc.setPushMessage(pushMessageDetailUpdateReqVO.getPushMessage());
        try {
            pushDetailOid = repositoryUtil.saveToResultId(db, detailDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1003);
            throw new BusinessException(messages);
        }
        String pushTitlelOid = "";
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
        titleDoc.setPushTitle(pushMessageDetailUpdateReqVO.getPushTitle());
        // Oid
        titleDoc.setPushDetailOid(pushDetailOid);
        // PUSH配信操作者
        titleDoc.setPushAccessUser(SessionUser.userName());
        // 配信タイプ:選択配信
        titleDoc.setPushType("2");
        // DBに更新
        try {
            String pushMessageDetailSaveLog = "（件名：";
            pushMessageDetailSaveLog = pushMessageDetailSaveLog + titleDoc.getPushTitle();
            pushTitlelOid = repositoryUtil.saveToResultId(db, titleDoc);
            actionLog.saveActionLog(Constants.ACTIONLOG_PUSH_MESSAGE_2 + pushMessageDetailSaveLog + ")",
                    db);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1003);
            throw new BusinessException(messages);
        }
        for (int i = 0; i < pushMessageDetailUpdateReqVO.getPushMessageList().size(); i++) {
            if (pushMessageDetailUpdateReqVO.getPushMessageList().get(i).getSelect() == null) {
                continue;
            }
            // 一覧選択したデータ
            if (pushMessageDetailUpdateReqVO.getPushMessageList().get(i).getSelect() == true) {

                int sum = 1;
                try {
                    userInfoDoc = (IYoUserInfoDoc) repositoryUtil.find(db,
                            pushMessageDetailUpdateReqVO.getPushMessageList().get(i).get_id(), IYoUserInfoDoc.class);
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
                                ApplicationKeys.INSIGHTVIEW_MSGOPENSTATUS_MSGOPENSTATUS_USERID,
                                IyoMsgOpenStatusDoc.class, queryKey);
                        for (IyoMsgOpenStatusDoc statusModifyDoc : msgOpenStatusDocList) {
                            if ("0".equals(statusModifyDoc.getOpenKBN())) {
                                sum = sum + 1;
                            }
                        }
                        String messageCode = pushNotifications.sendMessage(pushMessageDetailUpdateReqVO.getPushTitle(),
                                sum, deviceIdList);
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
        }
        return output;
    }
}
