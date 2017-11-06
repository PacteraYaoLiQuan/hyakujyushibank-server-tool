package com.scsk.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.AccountLoanDoc;
import com.scsk.model.YamagataPushrecordDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.response.vo.AccountLoanInitResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.vo.AccountAppYamaGataInitVO;
import com.scsk.vo.AccountLoanInitVO;
import com.scsk.vo.YamagataStatusModifyListVO;

/**
 * ローン申込み一覧初期化検索サービス。<br>
 * 
 * <br>
 * ローン申込み一覧初期化検索サービス。<br>
 */
@Service
public class AccountLoanListInitService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param なし
     *            検索条件
     */
    @Override
    protected void preExecute(BaseResVO ResVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param なし
     *            検索条件
     * @return accountAppInitResVO 申込一覧情報
     */
    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO ResVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        AccountLoanInitResVO accountLoanInitResVO = new AccountLoanInitResVO();
        // 申込一覧初期データを取得
        List<AccountLoanDoc> accountLoanDocList = new ArrayList<>();
        List<AccountLoanInitVO> accountLoanList = new ArrayList<>();

        accountLoanDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_ACCOUNTLOANLIST_ACCOUNTLOANLIST,
                AccountLoanDoc.class);
        String queryKey = "";
        for (AccountLoanDoc accountLoanDoc : accountLoanDocList) {
            AccountLoanInitVO accountLoanInitVO = new AccountLoanInitVO();
            // 受付番号
            accountLoanInitVO.setAccountAppSeq(accountLoanDoc.getLoanAppSeq());
            // 受付日時
            accountLoanInitVO.setReceiptDate(accountLoanDoc.getLoanAppTime());
            // ステイタス
            accountLoanInitVO.setStatus(accountLoanDoc.getStatus());
            List<YamagataStatusModifyListVO> yamagataStatusModifyListVOList = new ArrayList<YamagataStatusModifyListVO>();
            // 申込一覧初期化用データを戻る
            // Push開封状況
            queryKey = "loanAppSeq:\"" + accountLoanDoc.getLoanAppSeq() + "\"";
            List<YamagataStatusModifyDoc> yamagataStatusModifyList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_STATUSMODIFY_LOAN_STATUSMODIFY_LOAN, YamagataStatusModifyDoc.class,
                    queryKey);

            for (YamagataStatusModifyDoc yamagataStatusModifyDoc : yamagataStatusModifyList) {
                YamagataPushrecordDoc yamagataPushrecordDoc = null;
                if (!yamagataStatusModifyDoc.getPushRecordOid().isEmpty()) {
                    try {
                        yamagataPushrecordDoc = (YamagataPushrecordDoc) repositoryUtil.find(db,
                                yamagataStatusModifyDoc.getPushRecordOid(), YamagataPushrecordDoc.class);
                    } catch (Exception e) {
                    }
                }
                YamagataStatusModifyListVO yamagataStatusModifyListVO = new YamagataStatusModifyListVO();
                yamagataStatusModifyListVO.setStatusModifyDate(yamagataStatusModifyDoc.getStatusModifyDate());
                yamagataStatusModifyListVO.setStatusModifyDateForSort(
                        String.valueOf(yamagataStatusModifyDoc.getStatusModifyDateForSort()));
                if (yamagataPushrecordDoc != null) {
                    yamagataStatusModifyListVO.setPushStatus(yamagataPushrecordDoc.getPushStatus());
                }
                yamagataStatusModifyListVOList.add(yamagataStatusModifyListVO);
            }
            // 配信履歴データをソート
            Comparator<YamagataStatusModifyListVO> comparator = new Comparator<YamagataStatusModifyListVO>() {
                public int compare(YamagataStatusModifyListVO s1, YamagataStatusModifyListVO s2) {
                    // 最新変更日付
                    return s1.getStatusModifyDateForSort().compareTo(s2.getStatusModifyDateForSort()) * -1;
                }
            };
            Collections.sort(yamagataStatusModifyListVOList, comparator);
            if (yamagataStatusModifyListVOList != null && yamagataStatusModifyListVOList.size() > 0) {
                if (yamagataStatusModifyListVOList.get(0).getPushStatus() != null) {
                    accountLoanInitVO.setPushStatus(yamagataStatusModifyListVOList.get(0).getPushStatus());
                } else {
                    accountLoanInitVO.setPushStatus("5");
                }
            } else {
                accountLoanInitVO.setPushStatus("5");
            }
            // 姓名
            accountLoanInitVO.setName(
                    encryptorUtil.decrypt(accountLoanDoc.getLastName()) + " " + encryptorUtil.decrypt(accountLoanDoc.getFirstName()));
            // 商品名
            accountLoanInitVO.setLoanType(accountLoanDoc.getLoanType());
            // _id
            accountLoanInitVO.set_id(accountLoanDoc.get_id());
            accountLoanList.add(accountLoanInitVO);
        }
        accountLoanInitResVO.setAccountLoanList(accountLoanList);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNTLOAN_1, db);
        return accountLoanInitResVO;
    }
}
