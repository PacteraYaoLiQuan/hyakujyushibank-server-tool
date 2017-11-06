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
import com.cloudant.client.api.model.DesignDocument.MapReduce;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.Account114AppDoc;
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.model.UserInfoDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppListCompleteButtonReqVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * 完了消込ステータス更新サービス。<br>
 * <br>
 * 完了消込ステータス更新を実装するロジック。<br>
 */
@Service
public class AccountAppListCompleteService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param accountAppListCompleteButtonReqVO
     *            申込一覧情報
     */
    @Override
    protected void preExecute(BaseResVO accountAppListCompleteButtonReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param accountAppListCompleteButtonReqVO
     *            申込一覧情報
     * @return accountAppListCompleteButtonReqVO 完了消込情報
     */
    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);

        if ("0169".equals(bank_cd)) {
            AccountAppListCompleteButtonReqVO output = hirosima(db, baseResVO);
            return output;
        } else if ("0122".equals(bank_cd)) {
            AccountAppListCompleteButtonReqVO output = yamagata(db, baseResVO);
            return output;
        }else if("0173".equals(bank_cd)){
            AccountAppListCompleteButtonReqVO output = hyakujyushi(db, baseResVO);
            return output;
        }
        return null;
    }

    private AccountAppListCompleteButtonReqVO hyakujyushi(Database db, BaseResVO baseResVO) {
        AccountAppListCompleteButtonReqVO accountAppListCompleteButtonReqVO = new AccountAppListCompleteButtonReqVO();
        accountAppListCompleteButtonReqVO = (AccountAppListCompleteButtonReqVO) baseResVO;
        String accountAppListCompleteLog = "(受付番号：";
        for (int i = 0; i < accountAppListCompleteButtonReqVO.getCompleteList().size(); i++) {
            if (accountAppListCompleteButtonReqVO.getCompleteList().get(i).getSelect() == null) {
                continue;
            }
            // 一覧選択したデータ
            if (accountAppListCompleteButtonReqVO.getCompleteList().get(i).getSelect() == true) {
                accountAppListCompleteLog = accountAppListCompleteLog
                        + accountAppListCompleteButtonReqVO.getCompleteList().get(i).getAccountAppSeq() + "/";
                Account114AppDoc accountAppDoc = new Account114AppDoc();
                PushrecordDoc pushrecordDoc = new PushrecordDoc();
                try {
                    // 更新前検索
                    accountAppDoc = (Account114AppDoc) repositoryUtil.find(db,
                            accountAppListCompleteButtonReqVO.getCompleteList().get(i).get_id(), Account114AppDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
                    throw new BusinessException(messages);
                }
                // 更新された内容を設定
                // ステータス
                accountAppDoc.setStatus("3");
                // 口座開設DBに更新
                try {
                    repositoryUtil.update(db, accountAppDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                    throw new BusinessException(messages);
                }

                // システム日時を取得
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
                String date = sdf.format(new Date());
                // 配信日付 yyyyMMddHHmmssSSS
                List<String> deviceTokenIdList = new ArrayList<String>();

                // int sum = 1;
                // 配信履歴Doc件数検索
                // List<PushrecordDoc> pushrecordDocSUM = new ArrayList<>();
                // MapReduce viewSUM = new MapReduce();
                // viewSUM.setMap("function (doc) {if(doc.docType && doc.docType
                // === \"PUSHRECORD\" && doc.delFlg && doc.delFlg===\"0\" &&
                // doc.pushStatus ===\"1\" && doc.userId===\""
                // + accountAppDoc.getUserId()
                // + "\") {emit(doc._id, 1);}}");
                //
                // pushrecordDocSUM =
                // repositoryUtil.queryByDynamicView(db,viewSUM,
                // PushrecordDoc.class);

                // if (pushrecordDocSUM != null && pushrecordDocSUM.size() != 0)
                // {
                // sum = pushrecordDocSUM.size() + 1;
                // }

                // 臨時ユーザの場合、単一アプリを送信する
                if (accountAppDoc.getUserType().equals("0")) {
                    // PUSH通知を送信する
                    // List<String> list = new ArrayList<String>();
                    String message = Constants.PUSH_MESSAGE_STATUS_3 + Constants.PUSH_MESSAGE_ABOUT;
                    // list.add(encryptorUtil.decrypt(accountAppDoc.getDeviceTokenId()));
                    // String messageCode =
                    // pushNotifications.sendMessage(message,sum, list);

                    // 配信履歴DBに追加
                    pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                    pushrecordDoc.setAccountAppSeq(accountAppDoc.getAccountAppSeq());
                    pushrecordDoc.setUserId(accountAppDoc.getUserId());
                    List<String> tokenId = new ArrayList<String>();
                    tokenId.add(accountAppDoc.getDeviceTokenId());
                    pushrecordDoc.setDeviceTokenId(tokenId);
                    // pushrecordDoc.setPushDate(datePushSSS);
                    pushrecordDoc.setOpenDate("");
                    pushrecordDoc.setSaveDate(date);
                    pushrecordDoc.setPushContent(message);
                    // if (messageCode.equals(Constants.RETURN_OK)) {
                    // // 未開封
                    // pushrecordDoc.setPushStatus("1");
                    // } else {
                    // // 配信失敗
                    // pushrecordDoc.setPushStatus("3");
                    // }
                    pushrecordDoc.setStatus("3");

                    try {
                        repositoryUtil.save(db, pushrecordDoc);
                    } catch (BusinessException e) {
                        // e.printStackTrace();
                        LogInfoUtil.LogDebug(e.getMessage());
                        // エラーメッセージを出力、処理終了。
                        ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                        throw new BusinessException(messages);
                    }

                } else {
                    // 正式ユーザの場合、複数アプリを送信する
                    List<UserInfoDoc> userInfoDoc = new ArrayList<>();

                    // UserInfoDoc検索
                    MapReduce view = new MapReduce();
                    view.setMap(
                            "function (doc) {if(doc.docType && doc.docType === \"USERINFO\" && doc.delFlg && doc.delFlg===\"0\" && doc.userId===\""
                                    + accountAppDoc.getUserId() + "\") {emit(doc._id, 1);}}");
                    userInfoDoc = repositoryUtil.queryByDynamicView(db, view, UserInfoDoc.class);

                    if (userInfoDoc != null && userInfoDoc.size() != 0) {
                        deviceTokenIdList = userInfoDoc.get(0).getDeviceTokenIdList();
                    }
                    // PUSH通知を送信する
                    // List<String> list = new ArrayList<String>();
                    // for (int count = 0; count < deviceTokenIdList.size();
                    // count++) {
                    // list.add(encryptorUtil.decrypt(deviceTokenIdList.get(count)));
                    // }
                    String message = Constants.PUSH_MESSAGE_STATUS_3 + Constants.PUSH_MESSAGE_ABOUT;
                    // String messageCode =
                    // pushNotifications.sendMessage(message,sum, list);

                    // 正式ユーザの場合、ログ‐アウトので、送信しない
                    if (deviceTokenIdList.size() == 0 || deviceTokenIdList.isEmpty()
                            || deviceTokenIdList.equals(null)) {
                        pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                        pushrecordDoc.setAccountAppSeq(accountAppDoc.getAccountAppSeq());
                        pushrecordDoc.setUserId(accountAppDoc.getUserId());
                        pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                        // pushrecordDoc.setPushDate("");
                        pushrecordDoc.setOpenDate("");
                        pushrecordDoc.setSaveDate(date);
                        pushrecordDoc.setPushContent(message);
                        // 未配信
                        // pushrecordDoc.setPushStatus("4");
                        pushrecordDoc.setStatus("3");

                        try {
                            repositoryUtil.save(db, pushrecordDoc);
                        } catch (BusinessException e) {
                            // e.printStackTrace();
                            LogInfoUtil.LogDebug(e.getMessage());
                            // エラーメッセージを出力、処理終了。
                            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                            throw new BusinessException(messages);
                        }
                    } else {
                        // 正式ユーザの場合、ログ‐インので、送信する
                        pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                        pushrecordDoc.setAccountAppSeq(accountAppDoc.getAccountAppSeq());
                        pushrecordDoc.setUserId(accountAppDoc.getUserId());
                        pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                        // pushrecordDoc.setPushDate(datePushSSS);
                        pushrecordDoc.setOpenDate("");
                        pushrecordDoc.setSaveDate(date);
                        pushrecordDoc.setPushContent(message);
                        // if (messageCode.equals(Constants.RETURN_OK)) {
                        // // 未開封
                        // pushrecordDoc.setPushStatus("1");
                        // } else {
                        // // 配信失敗
                        // pushrecordDoc.setPushStatus("3");
                        // }
                        pushrecordDoc.setStatus("3");

                        try {
                            repositoryUtil.save(db, pushrecordDoc);
                        } catch (BusinessException e) {
                            // e.printStackTrace();
                            LogInfoUtil.LogDebug(e.getMessage());
                            // エラーメッセージを出力、処理終了。
                            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                            throw new BusinessException(messages);
                        }
                    }
                }
            }
        }
        accountAppListCompleteLog = accountAppListCompleteLog.substring(0, accountAppListCompleteLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_4 + accountAppListCompleteLog + ")", db);
        return accountAppListCompleteButtonReqVO;
    }

    /**
     * 広島
     */
    public AccountAppListCompleteButtonReqVO hirosima(Database db, BaseResVO baseResVO) throws Exception {
        AccountAppListCompleteButtonReqVO accountAppListCompleteButtonReqVO = new AccountAppListCompleteButtonReqVO();
        accountAppListCompleteButtonReqVO = (AccountAppListCompleteButtonReqVO) baseResVO;
        String accountAppListCompleteLog = "(受付番号：";
        for (int i = 0; i < accountAppListCompleteButtonReqVO.getCompleteList().size(); i++) {
            if (accountAppListCompleteButtonReqVO.getCompleteList().get(i).getSelect() == null) {
                continue;
            }
            // 一覧選択したデータ
            if (accountAppListCompleteButtonReqVO.getCompleteList().get(i).getSelect() == true) {
                accountAppListCompleteLog = accountAppListCompleteLog
                        + accountAppListCompleteButtonReqVO.getCompleteList().get(i).getAccountAppSeq() + "/";
                AccountAppDoc accountAppDoc = new AccountAppDoc();
                PushrecordDoc pushrecordDoc = new PushrecordDoc();
                try {
                    // 更新前検索
                    accountAppDoc = (AccountAppDoc) repositoryUtil.find(db,
                            accountAppListCompleteButtonReqVO.getCompleteList().get(i).get_id(), AccountAppDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
                    throw new BusinessException(messages);
                }
                // 更新された内容を設定
                // ステータス
                accountAppDoc.setStatus("3");
                // 口座開設DBに更新
                try {
                    repositoryUtil.update(db, accountAppDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                    throw new BusinessException(messages);
                }

                // システム日時を取得
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
                String date = sdf.format(new Date());
                // 配信日付 yyyyMMddHHmmssSSS
                List<String> deviceTokenIdList = new ArrayList<String>();

                // int sum = 1;
                // 配信履歴Doc件数検索
                // List<PushrecordDoc> pushrecordDocSUM = new ArrayList<>();
                // MapReduce viewSUM = new MapReduce();
                // viewSUM.setMap("function (doc) {if(doc.docType && doc.docType
                // === \"PUSHRECORD\" && doc.delFlg && doc.delFlg===\"0\" &&
                // doc.pushStatus ===\"1\" && doc.userId===\""
                // + accountAppDoc.getUserId()
                // + "\") {emit(doc._id, 1);}}");
                //
                // pushrecordDocSUM =
                // repositoryUtil.queryByDynamicView(db,viewSUM,
                // PushrecordDoc.class);

                // if (pushrecordDocSUM != null && pushrecordDocSUM.size() != 0)
                // {
                // sum = pushrecordDocSUM.size() + 1;
                // }

                // 臨時ユーザの場合、単一アプリを送信する
                if (accountAppDoc.getUserType() == 0) {
                    // PUSH通知を送信する
                    // List<String> list = new ArrayList<String>();
                    String message = Constants.PUSH_MESSAGE_STATUS_3 + Constants.PUSH_MESSAGE_ABOUT;
                    // list.add(encryptorUtil.decrypt(accountAppDoc.getDeviceTokenId()));
                    // String messageCode =
                    // pushNotifications.sendMessage(message,sum, list);

                    // 配信履歴DBに追加
                    pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                    pushrecordDoc.setAccountAppSeq(accountAppDoc.getAccountAppSeq());
                    pushrecordDoc.setUserId(accountAppDoc.getUserId());
                    List<String> tokenId = new ArrayList<String>();
                    tokenId.add(accountAppDoc.getDeviceTokenId());
                    pushrecordDoc.setDeviceTokenId(tokenId);
                    // pushrecordDoc.setPushDate(datePushSSS);
                    pushrecordDoc.setOpenDate("");
                    pushrecordDoc.setSaveDate(date);
                    pushrecordDoc.setPushContent(message);
                    // if (messageCode.equals(Constants.RETURN_OK)) {
                    // // 未開封
                    // pushrecordDoc.setPushStatus("1");
                    // } else {
                    // // 配信失敗
                    // pushrecordDoc.setPushStatus("3");
                    // }
                    pushrecordDoc.setStatus("3");

                    try {
                        repositoryUtil.save(db, pushrecordDoc);
                    } catch (BusinessException e) {
                        // e.printStackTrace();
                        LogInfoUtil.LogDebug(e.getMessage());
                        // エラーメッセージを出力、処理終了。
                        ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                        throw new BusinessException(messages);
                    }

                } else {
                    // 正式ユーザの場合、複数アプリを送信する
                    List<UserInfoDoc> userInfoDoc = new ArrayList<>();

                    // UserInfoDoc検索
                    MapReduce view = new MapReduce();
                    view.setMap(
                            "function (doc) {if(doc.docType && doc.docType === \"USERINFO\" && doc.delFlg && doc.delFlg===\"0\" && doc.userId===\""
                                    + accountAppDoc.getUserId() + "\") {emit(doc._id, 1);}}");
                    userInfoDoc = repositoryUtil.queryByDynamicView(db, view, UserInfoDoc.class);

                    if (userInfoDoc != null && userInfoDoc.size() != 0) {
                        deviceTokenIdList = userInfoDoc.get(0).getDeviceTokenIdList();
                    }
                    // PUSH通知を送信する
                    // List<String> list = new ArrayList<String>();
                    // for (int count = 0; count < deviceTokenIdList.size();
                    // count++) {
                    // list.add(encryptorUtil.decrypt(deviceTokenIdList.get(count)));
                    // }
                    String message = Constants.PUSH_MESSAGE_STATUS_3 + Constants.PUSH_MESSAGE_ABOUT;
                    // String messageCode =
                    // pushNotifications.sendMessage(message,sum, list);

                    // 正式ユーザの場合、ログ‐アウトので、送信しない
                    if (deviceTokenIdList.size() == 0 || deviceTokenIdList.isEmpty()
                            || deviceTokenIdList.equals(null)) {
                        pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                        pushrecordDoc.setAccountAppSeq(accountAppDoc.getAccountAppSeq());
                        pushrecordDoc.setUserId(accountAppDoc.getUserId());
                        pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                        // pushrecordDoc.setPushDate("");
                        pushrecordDoc.setOpenDate("");
                        pushrecordDoc.setSaveDate(date);
                        pushrecordDoc.setPushContent(message);
                        // 未配信
                        // pushrecordDoc.setPushStatus("4");
                        pushrecordDoc.setStatus("3");

                        try {
                            repositoryUtil.save(db, pushrecordDoc);
                        } catch (BusinessException e) {
                            // e.printStackTrace();
                            LogInfoUtil.LogDebug(e.getMessage());
                            // エラーメッセージを出力、処理終了。
                            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                            throw new BusinessException(messages);
                        }
                    } else {
                        // 正式ユーザの場合、ログ‐インので、送信する
                        pushrecordDoc.setDocType(Constants.DOCTYPE_1);
                        pushrecordDoc.setAccountAppSeq(accountAppDoc.getAccountAppSeq());
                        pushrecordDoc.setUserId(accountAppDoc.getUserId());
                        pushrecordDoc.setDeviceTokenId(deviceTokenIdList);
                        // pushrecordDoc.setPushDate(datePushSSS);
                        pushrecordDoc.setOpenDate("");
                        pushrecordDoc.setSaveDate(date);
                        pushrecordDoc.setPushContent(message);
                        // if (messageCode.equals(Constants.RETURN_OK)) {
                        // // 未開封
                        // pushrecordDoc.setPushStatus("1");
                        // } else {
                        // // 配信失敗
                        // pushrecordDoc.setPushStatus("3");
                        // }
                        pushrecordDoc.setStatus("3");

                        try {
                            repositoryUtil.save(db, pushrecordDoc);
                        } catch (BusinessException e) {
                            // e.printStackTrace();
                            LogInfoUtil.LogDebug(e.getMessage());
                            // エラーメッセージを出力、処理終了。
                            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                            throw new BusinessException(messages);
                        }
                    }
                }
            }
        }
        accountAppListCompleteLog = accountAppListCompleteLog.substring(0, accountAppListCompleteLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_4 + accountAppListCompleteLog + ")", db);
        return accountAppListCompleteButtonReqVO;
    }

    /**
     * 山形
     */
    public AccountAppListCompleteButtonReqVO yamagata(Database db, BaseResVO baseResVO) throws Exception {
        AccountAppListCompleteButtonReqVO accountAppListCompleteButtonReqVO = new AccountAppListCompleteButtonReqVO();
        accountAppListCompleteButtonReqVO = (AccountAppListCompleteButtonReqVO) baseResVO;
        String accountAppListCompleteLog = "(受付番号：";
        Date date = new Date();
        for (int i = 0; i < accountAppListCompleteButtonReqVO.getCompleteList2().size(); i++) {
            if (accountAppListCompleteButtonReqVO.getCompleteList2().get(i).getSelect() == null) {
                continue;
            }
            // 一覧選択したデータ
            if (accountAppListCompleteButtonReqVO.getCompleteList2().get(i).getSelect() == true) {
                accountAppListCompleteLog = accountAppListCompleteLog
                        + accountAppListCompleteButtonReqVO.getCompleteList2().get(i).getAccountAppSeq() + "/";
                AccountYamaGataAppDoc applicationDoc = new AccountYamaGataAppDoc();
                // 申込詳細情報取得
                try {
                    applicationDoc = (AccountYamaGataAppDoc) repositoryUtil.find(db,
                            accountAppListCompleteButtonReqVO.getCompleteList2().get(i).get_id(),
                            AccountYamaGataAppDoc.class);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                    throw new BusinessException(messages);
                }
                // DBに更新
                try {
                    applicationDoc.setStatus("3");
                    repositoryUtil.update(db, applicationDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPDETAIL_1002);
                    throw new BusinessException(messages);
                }
                SimpleDateFormat sdf = null;
                sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
                String statusModifyDate = sdf.format(date);
                YamagataStatusModifyDoc yamagataStatusModifyDoc = new YamagataStatusModifyDoc();
                yamagataStatusModifyDoc.setAccountAppSeq(applicationDoc.getAccountAppSeq());
                yamagataStatusModifyDoc.setStatus("3");
                yamagataStatusModifyDoc.setStatusModifyDate(statusModifyDate);
                yamagataStatusModifyDoc.setStatusModifyDateForSort(date.getTime());
                yamagataStatusModifyDoc.setSendStatus("1");
                yamagataStatusModifyDoc.setPushRecordOid("");
                try {
                    repositoryUtil.save(db, yamagataStatusModifyDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
                    throw new BusinessException(messages);
                }
            }
        }
        accountAppListCompleteLog = accountAppListCompleteLog.substring(0, accountAppListCompleteLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_4 + accountAppListCompleteLog + ")", db);
        return accountAppListCompleteButtonReqVO;
    }
}
