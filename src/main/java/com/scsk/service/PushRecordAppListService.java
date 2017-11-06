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
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.Account114AppDoc;
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.response.vo.PushRecordAppListInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.AccountAppPushListVO;

/**
 * 申込詳細検索サービス。<br>
 * <br>
 * 申込詳細検索を実装するロジック。<br>
 * 申込詳細を更新するロジック。<br>
 */
@Service
public class PushRecordAppListService extends AbstractBLogic<PushRecordAppListInitResVO, PushRecordAppListInitResVO> {

    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(PushRecordAppListInitResVO pushRecordAppListInitResVO) throws Exception {

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
    protected PushRecordAppListInitResVO doExecute(CloudantClient client, PushRecordAppListInitResVO resVO)
            throws Exception {

        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        if ("0169".equals(bank_cd)) {
            PushRecordAppListInitResVO pushRecordAppListInitResVO = hirosima(db, resVO);
            return pushRecordAppListInitResVO;
        } else if ("0122".equals(bank_cd)) {
            PushRecordAppListInitResVO pushRecordAppListInitResVO = yamagata(db, resVO);
            return pushRecordAppListInitResVO;
        }else if("0173".equals(bank_cd)){
            PushRecordAppListInitResVO pushRecordAppListInitResVO = hyakujyushi(db, resVO);
            return pushRecordAppListInitResVO;
        }
        return null;
    }

    private PushRecordAppListInitResVO hyakujyushi(Database db, PushRecordAppListInitResVO resVO) throws Exception {
        PushRecordAppListInitResVO pushRecordAppListInitResVO = new PushRecordAppListInitResVO();
        // push通知履歴一覧を取得
        List<YamagataStatusModifyDoc> yamagataStatusModifyDocList = new ArrayList<>();
        try {
            // push通知履歴
            yamagataStatusModifyDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_STATUSMODIFY_STATUSMODIFY,
                    YamagataStatusModifyDoc.class);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSH001_1001);
            throw new BusinessException(messages);
        }
        List<YamagataStatusModifyDoc> yamagataStatusModifyDocListSave = new ArrayList<>();
        removeMinStatusModifyDate(yamagataStatusModifyDocList, yamagataStatusModifyDocListSave);
        List<AccountAppPushListVO> accountAppPushList = new ArrayList<AccountAppPushListVO>();
        // 戻り値を設定
        List<Account114AppDoc> accountAppDocList = new ArrayList<>();
        accountAppDocList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST, Account114AppDoc.class);
        for (YamagataStatusModifyDoc doc : yamagataStatusModifyDocListSave) {
            // ステータスは受付または処理中の場合
            if ("1".equals(doc.getSendStatus())) {
                for (Account114AppDoc accountAppDoc : accountAppDocList) {
                    AccountAppPushListVO accountAppPushListVO = new AccountAppPushListVO();
                    if (doc.getAccountAppSeq().equals(accountAppDoc.getAccountAppSeq())) {
                        accountAppPushListVO.setAccountAppSeq(doc.getAccountAppSeq());
                        // 姓名
                        accountAppPushListVO.setName(encryptorUtil.decrypt(accountAppDoc.getLastName()) + " "
                                + encryptorUtil.decrypt(accountAppDoc.getFirstName()));
                        accountAppPushListVO.setReceiptDate(accountAppDoc.getReceiptDate());
                        accountAppPushListVO.setStatus(doc.getStatus());
                        accountAppPushListVO.set_id(doc.get_id());
                        accountAppPushListVO.setUserId(accountAppDoc.getUserId());
                        accountAppPushListVO.setAccountApp_id(accountAppDoc.get_id());
                        accountAppPushListVO.setSalesOfficeOption(encryptorUtil.decrypt(accountAppDoc.getSalesOfficeOption()));
                        accountAppPushList.add(accountAppPushListVO);
                    }
                }
            }
        }
        pushRecordAppListInitResVO.setAccountAppPushList(accountAppPushList);
        actionLog.saveActionLog(Constants.ACTIONLOG_PUSHPRECORD_1, db);
        return pushRecordAppListInitResVO;

    }

    /**
     * 
     * 広島
     * 
     */
    public PushRecordAppListInitResVO hirosima(Database db, PushRecordAppListInitResVO resVO) throws Exception {
        PushRecordAppListInitResVO pushRecordAppListInitResVO = new PushRecordAppListInitResVO();
        // push通知履歴一覧を取得
        List<PushrecordDoc> pushrecordDocList = new ArrayList<>();
        try {
            // push通知履歴
            MapReduce view = new MapReduce();
            view.setMap(
                    "function (doc) {if(doc.docType && doc.docType === \"PUSHRECORD\" && doc.delFlg && doc.delFlg===\"0\" "
                            + ") {emit([doc.saveDate,doc.openDate], 1);}}");
            pushrecordDocList = repositoryUtil.queryByDynamicView(db, view, PushrecordDoc.class);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSH001_1001);
            throw new BusinessException(messages);
        }
        List<PushrecordDoc> pushrecordDocList1 = new ArrayList<>();
        remove(pushrecordDocList, pushrecordDocList1);
        List<AccountAppPushListVO> accountAppPushList = new ArrayList<AccountAppPushListVO>();
        // 戻り値を設定
        List<AccountAppDoc> accountAppDocList = new ArrayList<>();
        for (PushrecordDoc doc : pushrecordDocList1) {
            // ステータスは受付または処理中の場合
            if (!doc.getStatus().equals("1") && !doc.getStatus().equals("2")) {
                if ((doc.getPushDate() == null || doc.getPushDate().isEmpty()) && !("5".equals(doc.getPushStatus())
                        || "6".equals(doc.getPushStatus()) || "7".equals(doc.getPushStatus()))) {
                    AccountAppPushListVO pushVo = new AccountAppPushListVO();
                    // 受付番号
                    pushVo.setAccountAppSeq(doc.getAccountAppSeq());

                    accountAppDocList = repositoryUtil.getView(db,
                            ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST, AccountAppDoc.class);
                    for (AccountAppDoc accountAppDoc : accountAppDocList) {
                        if (doc.getAccountAppSeq().equals(accountAppDoc.getAccountAppSeq())) {
                            // 姓名
                            pushVo.setName(encryptorUtil.decrypt(accountAppDoc.getLastName()) + " "
                                    + encryptorUtil.decrypt(accountAppDoc.getFirstName()));
                            // 申込受付日付
                            pushVo.setReceiptDate(accountAppDoc.getReceiptDate());
                            // 受付または処理中配信状況しない
                            // 配信状況
                            if ("1".equals(doc.getStatus()) || "2".equals(doc.getStatus())) {
                                pushVo.setSendStatus("1");
                            } else {
                                if (doc.getPushDate() != null && !doc.getPushDate().isEmpty()) {
                                    pushVo.setSendStatus("3");
                                } else {
                                    pushVo.setSendStatus("2");
                                }
                            }
                            pushVo.set_id(doc.get_id());
                            // Push開封状況
                            if (doc.getPushStatus() != null && !doc.getPushStatus().isEmpty()) {
                                pushVo.setPushStatus(doc.getPushStatus());
                            } else {
                                pushVo.setPushStatus("5");
                            }
                            // 配信日時
                            if (doc.getPushDate() != null && !doc.getPushDate().isEmpty()) {
                                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_ASS);
                                Date date = sdf.parse(doc.getPushDate());
                                SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
                                String date1 = sdf1.format(date);
                                pushVo.setPushDate(date1);
                            } else {
                                pushVo.setPushDate("―");
                            }
                            // 開封日付
                            pushVo.setOpenDate(dateFormat(doc.getOpenDate()));
                            // 申込処理ステータス
                            pushVo.setStatus(doc.getStatus());
                            if ("3".equals(doc.getPushStatus())) {
                                pushVo.setPushErr("【配信失敗】システムエラーによりPushメッセージを配信できませんでした。時間を置いてから再度実行してください。");
                            } else if ("4".equals(doc.getPushStatus())) {
                                pushVo.setPushErr(
                                        "【配信不可】端末IDが取得できずPushメッセージを配信できませんでした。申込ユーザがログアウトまたはアプリを削除した可能性があります。");
                            } else {
                                pushVo.setPushErr("");
                            }
                            accountAppPushList.add(pushVo);
                            break;
                        }
                    }
                }

            }

        }
        pushRecordAppListInitResVO.setAccountAppPushList(accountAppPushList);
        actionLog.saveActionLog(Constants.ACTIONLOG_PUSHPRECORD_1, db);
        return pushRecordAppListInitResVO;

    }

    /**
     * 
     * 山形
     * 
     */
    public PushRecordAppListInitResVO yamagata(Database db, PushRecordAppListInitResVO resVO) throws Exception {
        PushRecordAppListInitResVO pushRecordAppListInitResVO = new PushRecordAppListInitResVO();
        // push通知履歴一覧を取得
        List<YamagataStatusModifyDoc> yamagataStatusModifyDocList = new ArrayList<>();
        try {
            // push通知履歴
            yamagataStatusModifyDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_STATUSMODIFY_STATUSMODIFY,
                    YamagataStatusModifyDoc.class);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSH001_1001);
            throw new BusinessException(messages);
        }
        List<YamagataStatusModifyDoc> yamagataStatusModifyDocListSave = new ArrayList<>();
        removeMinStatusModifyDate(yamagataStatusModifyDocList, yamagataStatusModifyDocListSave);
        List<AccountAppPushListVO> accountAppPushList = new ArrayList<AccountAppPushListVO>();
        // 戻り値を設定
        List<AccountYamaGataAppDoc> accountYamaGataAppDocList = new ArrayList<>();
        accountYamaGataAppDocList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST, AccountYamaGataAppDoc.class);
        for (YamagataStatusModifyDoc doc : yamagataStatusModifyDocListSave) {
            // ステータスは受付または処理中の場合
            if ("1".equals(doc.getSendStatus())) {
                for (AccountYamaGataAppDoc accountYamaGataAppDoc : accountYamaGataAppDocList) {
                    AccountAppPushListVO accountAppPushListVO = new AccountAppPushListVO();
                    if (doc.getAccountAppSeq().equals(accountYamaGataAppDoc.getAccountAppSeq())) {
                        accountAppPushListVO.setAccountAppSeq(doc.getAccountAppSeq());
                        // 姓名
                        accountAppPushListVO.setName(encryptorUtil.decrypt(accountYamaGataAppDoc.getLastName()) + " "
                                + encryptorUtil.decrypt(accountYamaGataAppDoc.getFirstName()));
                        // 申込受付日付
                        accountAppPushListVO.setReceiptDate(accountYamaGataAppDoc.getReceiptDate());
                        accountAppPushListVO.setStatus(doc.getStatus());
                        accountAppPushListVO.set_id(doc.get_id());
                        accountAppPushListVO.setUserId(accountYamaGataAppDoc.getUserId());
                        accountAppPushListVO.setAccountApp_id(accountYamaGataAppDoc.get_id());
                        accountAppPushList.add(accountAppPushListVO);
                    }
                }
            }
        }
        pushRecordAppListInitResVO.setAccountAppPushList(accountAppPushList);
        actionLog.saveActionLog(Constants.ACTIONLOG_PUSHPRECORD_1, db);
        return pushRecordAppListInitResVO;

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

    /**
     * 申込処理ステータス变更リスト作成。
     * 
     * @param date
     *            申込処理ステータス变更リスト
     * @return date 申込処理ステータス变更リスト
     * @throws Exception
     */
    public List<YamagataStatusModifyDoc> removeMinStatusModifyDate(
            List<YamagataStatusModifyDoc> yamagataStatusModifyDocList,
            List<YamagataStatusModifyDoc> yamagataStatusModifyDocListSave) {

        for (int i = 0; i < yamagataStatusModifyDocList.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < yamagataStatusModifyDocList.size(); j++) {
                if (yamagataStatusModifyDocList.get(j).getAccountAppSeq()
                        .equals(yamagataStatusModifyDocList.get(i).getAccountAppSeq())) {
                    if (yamagataStatusModifyDocList.get(j).getStatusModifyDate()
                            .compareTo(yamagataStatusModifyDocList.get(i).getStatusModifyDate()) > 0) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                yamagataStatusModifyDocListSave.add(yamagataStatusModifyDocList.get(i));
            }
        }
        return yamagataStatusModifyDocListSave;
    }

    /**
     * Push通知履歴リスト作成。
     * 
     * @param date
     *            Push通知履歴リスト
     * @return date Push通知履歴リスト
     * @throws Exception
     */
    public List<PushrecordDoc> remove(List<PushrecordDoc> pushrecordDocList, List<PushrecordDoc> pushrecordDocList1) {

        for (int i = 0; i < pushrecordDocList.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < pushrecordDocList.size(); j++) {
                if (pushrecordDocList.get(j).getAccountAppSeq().equals(pushrecordDocList.get(i).getAccountAppSeq())) {
                    if (pushrecordDocList.get(j).getCreatedDate()
                            .compareTo(pushrecordDocList.get(i).getCreatedDate()) > 0) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                pushrecordDocList1.add(pushrecordDocList.get(i));
            }
        }
        return pushrecordDocList1;
    }

}
