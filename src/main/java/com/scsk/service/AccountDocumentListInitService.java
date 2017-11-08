package com.scsk.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.Account114AppRecordDoc;
import com.scsk.model.Account114DocumentDoc;
import com.scsk.model.StatusModifyDoc;
import com.scsk.response.vo.AccountDocumentInitResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.vo.AccountDocument114InitVO;
import com.scsk.vo.YamagataStatusModifyListVO;

@Service
public class AccountDocumentListInitService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Value("${bank_cd}")
    private String bank_cd;
    @Autowired
    private ActionLog actionLog;
    @Autowired
    private EncryptorUtil encryptorUtil;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO input) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);

        AccountDocumentInitResVO accountAppInitResVO = new AccountDocumentInitResVO();

        List<AccountDocument114InitVO> accountAppList = hyakujyushi(db);
        accountAppInitResVO.setAccountDocumentList(accountAppList);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_1, db);
        return accountAppInitResVO;
    }

    private List<AccountDocument114InitVO> hyakujyushi(Database db) throws Exception {

        // 申込一覧初期データを取得
        List<Account114DocumentDoc> accountDocumentDocList = new ArrayList<>();
        List<AccountDocument114InitVO> accountAppList = new ArrayList<>();

        accountDocumentDocList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_ACCOUNTDOCUMENTLIST_ACCOUNTDOCUMENTLIST, Account114DocumentDoc.class);
        String queryKey = "";
        for (Account114DocumentDoc account114DocumentDoc : accountDocumentDocList) {
            List<YamagataStatusModifyListVO> yamagataStatusModifyListVOList = new ArrayList<YamagataStatusModifyListVO>();
            AccountDocument114InitVO accountDocument114InitVO = new AccountDocument114InitVO();
            // Push開封状況
            queryKey = account114DocumentDoc.getDocumentAppSeq();
            List<StatusModifyDoc> statusModifyList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_DOCUMENTSTATUSMODIFY_DOCUMENTSTATUSMODIFY, StatusModifyDoc.class, queryKey);
            for (StatusModifyDoc statusModifyDoc : statusModifyList) {
                Account114AppRecordDoc pushrecordDoc = null;
                if (!statusModifyDoc.getPushRecordOid().isEmpty()) {
                    try {
                        pushrecordDoc = (Account114AppRecordDoc) repositoryUtil.find(db,
                                statusModifyDoc.getPushRecordOid(), Account114AppRecordDoc.class);
                    } catch (Exception e) {
                    }
                }
                YamagataStatusModifyListVO yamagataStatusModifyListVO = new YamagataStatusModifyListVO();
                yamagataStatusModifyListVO.setStatusModifyDate(statusModifyDoc.getStatusModifyDate());
                yamagataStatusModifyListVO
                        .setStatusModifyDateForSort(String.valueOf(statusModifyDoc.getStatusModifyDateForSort()));
                if (pushrecordDoc != null) {
                    yamagataStatusModifyListVO.setPushStatus(pushrecordDoc.getPushStatus());
                }
                yamagataStatusModifyListVOList.add(yamagataStatusModifyListVO);
            }
            // 配信履歴データをソート
            Comparator<YamagataStatusModifyListVO> comparator = new Comparator<YamagataStatusModifyListVO>() {
                public int compare(YamagataStatusModifyListVO s1, YamagataStatusModifyListVO s2) {
                    // 最新変更日付
                    // if
                    // (s1.getStatusModifyDate().compareTo(s2.getStatusModifyDate())
                    // != 0) {
                    return s1.getStatusModifyDateForSort().compareTo(s2.getStatusModifyDateForSort()) * -1;
                    // } else {
                    // return
                    // s1.getStatusModifyDate().compareTo(s2.getStatusModifyDate())
                    // * -1;
                    // }
                }
            };
            Collections.sort(yamagataStatusModifyListVOList, comparator);
            if (yamagataStatusModifyListVOList != null && yamagataStatusModifyListVOList.size() > 0) {
                if (yamagataStatusModifyListVOList.get(0).getPushStatus() != null) {
                    accountDocument114InitVO.setPushStatus(yamagataStatusModifyListVOList.get(0).getPushStatus());
                } else {
                    accountDocument114InitVO.setPushStatus("5");
                }

            } else {
                accountDocument114InitVO.setPushStatus("5");
            }

            accountDocument114InitVO.set_id(account114DocumentDoc.get_id());
            accountDocument114InitVO.set_rev(account114DocumentDoc.get_rev());
            accountDocument114InitVO.setAgreeCheck(account114DocumentDoc.getAgreeCheck());
            accountDocument114InitVO.setAgreeTime(account114DocumentDoc.getAgreeTime());
            accountDocument114InitVO.setUserId(account114DocumentDoc.getUserId());
            accountDocument114InitVO.setUserType(account114DocumentDoc.getUserType());
            accountDocument114InitVO.setDocumentAppSeq(account114DocumentDoc.getDocumentAppSeq());
            accountDocument114InitVO.setCard1Seq(account114DocumentDoc.getCard1Seq());
            accountDocument114InitVO.setCard2Seq(account114DocumentDoc.getCard2Seq());
            accountDocument114InitVO.setCard3Seq(account114DocumentDoc.getCard3Seq());
            accountDocument114InitVO.setCard4Seq(account114DocumentDoc.getCard4Seq());
            accountDocument114InitVO.setCard5Seq(account114DocumentDoc.getCard5Seq());

            accountDocument114InitVO.setPurpose(account114DocumentDoc.getPurpose());
            accountDocument114InitVO.setName(encryptorUtil.decrypt(account114DocumentDoc.getFirstName())
                    + encryptorUtil.decrypt(account114DocumentDoc.getLastName()));
            accountDocument114InitVO.setReadLastName(account114DocumentDoc.getReadLastName());
            accountDocument114InitVO.setDocumentAppTime(account114DocumentDoc.getDocumentAppTime());
            accountDocument114InitVO.setSendItem(account114DocumentDoc.getSendItem());
            accountDocument114InitVO.setStatus(account114DocumentDoc.getStatus());
            accountAppList.add(accountDocument114InitVO);
        }

        return accountAppList;

    }

}
