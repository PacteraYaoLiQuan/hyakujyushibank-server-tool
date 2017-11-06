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
import com.scsk.model.Account114AppDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.response.vo.PushRecordAppListInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.AccountAppPushListVO;

@Service
public class DocumentRecordListService extends AbstractBLogic<PushRecordAppListInitResVO, PushRecordAppListInitResVO> {
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;
    @Override
    protected void preExecute(PushRecordAppListInitResVO input) throws Exception {

    }

    @Override
    protected PushRecordAppListInitResVO doExecute(CloudantClient client, PushRecordAppListInitResVO input)
            throws Exception {
        Database db = client.database(Constants.DB_NAME, false);
        if("0173".equals(bank_cd)){
            PushRecordAppListInitResVO pushRecordAppListInitResVO = hyakujyushi(db, input);
            return pushRecordAppListInitResVO;
        }
        return null;
    }

    private PushRecordAppListInitResVO hyakujyushi(Database db, PushRecordAppListInitResVO input) throws Exception {
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

                        accountAppPushListVO.setStatus(doc.getStatus());
                        accountAppPushListVO.set_id(doc.get_id());
                        accountAppPushListVO.setUserId(accountAppDoc.getUserId());
                        accountAppPushListVO.setAccountApp_id(accountAppDoc.get_id());
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
}
