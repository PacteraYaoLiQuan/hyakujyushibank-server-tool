package com.scsk.service;

import java.util.ArrayList;
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
import com.scsk.model.Account114DocumentDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.response.vo.PushRecordAppListInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.AccountAppPushListVO;
@Service
public class DocumentPushRecordAppListService extends AbstractBLogic<PushRecordAppListInitResVO, PushRecordAppListInitResVO>{


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
         if("0173".equals(bank_cd)){
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
            yamagataStatusModifyDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_DOCUMENTSTATUSMODIFY_DOCUMENTSTATUSMODIFY,
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
        List<Account114DocumentDoc> accountAppDocList = new ArrayList<>();
        accountAppDocList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_ACCOUNTDOCUMENTLIST_ACCOUNTDOCUMENTLIST, Account114DocumentDoc.class);
        for (YamagataStatusModifyDoc doc : yamagataStatusModifyDocListSave) {
            // ステータスは受付または処理中の場合
            if ("1".equals(doc.getSendStatus())) {
                for (Account114DocumentDoc accountAppDoc : accountAppDocList) {
                    AccountAppPushListVO accountAppPushListVO = new AccountAppPushListVO();
                    if (doc.getAccountAppSeq().equals(accountAppDoc.getDocumentAppSeq())) {
                        accountAppPushListVO.setAccountAppSeq(doc.getAccountAppSeq());
                        // 姓名
                        accountAppPushListVO.setName(encryptorUtil.decrypt(accountAppDoc.getLastName()) + " "
                                + encryptorUtil.decrypt(accountAppDoc.getFirstName()));
                        accountAppPushListVO.setReceiptDate(accountAppDoc.getDocumentAppTime());
                        accountAppPushListVO.setStatus(doc.getStatus());
                        accountAppPushListVO.set_id(doc.get_id());
                        accountAppPushListVO.setUserId(accountAppDoc.getUserId());
                        accountAppPushListVO.setAccountApp_id(accountAppDoc.get_id());
                        accountAppPushListVO.setDriverLicenseNo(encryptorUtil.decrypt(accountAppDoc.getDriverLicenseNo()));
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
