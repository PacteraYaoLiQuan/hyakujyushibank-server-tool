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
import com.scsk.model.PushrecordLoanDoc;
import com.scsk.model.StatusModifyLoanDoc;
import com.scsk.request.vo.YamagataStatusModifyReqVO;
import com.scsk.response.vo.YamagataStatusModifyResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.vo.YamagataStatusModifyListVO;

/**
 * 配信状態検索サービス。<br>
 * <br>
 */
@Service
public class AccountLoanStatusModifyService
        extends AbstractBLogic<YamagataStatusModifyReqVO, YamagataStatusModifyResVO> {

    @Autowired
    EncryptorUtil encryptorUtil;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param YamagataStatusModifyReqVO
     *            入力情報
     */
    @Override
    protected void preExecute(YamagataStatusModifyReqVO yamagataStatusModifyReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param reqVo
     *            入力情報
     * @return yamagataStatusModifyResVO 詳細情報
     * @throws Exception
     */
    @Override
    protected YamagataStatusModifyResVO doExecute(CloudantClient client,
            YamagataStatusModifyReqVO yamagataStatusModifyReqVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        List<YamagataStatusModifyListVO> yamagataStatusModifyListVOList = new ArrayList<YamagataStatusModifyListVO>();
        YamagataStatusModifyResVO yamagataStatusModifyResVO = new YamagataStatusModifyResVO();
        String queryKey = yamagataStatusModifyReqVO.getAccountAppSeq();
        List<StatusModifyLoanDoc> statusModifyLoanList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_STATUSMODIFY_LOAN_STATUSMODIFY_LOAN, StatusModifyLoanDoc.class, queryKey);
        for (StatusModifyLoanDoc statusModifyLoanDoc : statusModifyLoanList) {
            PushrecordLoanDoc pushrecordLoanDoc = null;
            if (!statusModifyLoanDoc.getPushRecordOid().isEmpty()) {
                try {
                    pushrecordLoanDoc = (PushrecordLoanDoc) repositoryUtil.find(db,
                            statusModifyLoanDoc.getPushRecordOid(), PushrecordLoanDoc.class);
                } catch (Exception e) {

                }
            }
            YamagataStatusModifyListVO yamagataStatusModifyListVO = new YamagataStatusModifyListVO();
            // 申込処理ステータス
            yamagataStatusModifyListVO.setStatus(statusModifyLoanDoc.getStatus());
            // 端末ID
            if (pushrecordLoanDoc != null) {

                // 配信日時
                if (pushrecordLoanDoc.getPushDate() != null && !pushrecordLoanDoc.getPushDate().isEmpty()) {
                    yamagataStatusModifyListVO.setPushDate(pushrecordLoanDoc.getPushDate());
                } else {
                    yamagataStatusModifyListVO.setPushDate("―");
                }
                yamagataStatusModifyListVO.setPushStatus(pushrecordLoanDoc.getPushStatus());
                yamagataStatusModifyListVO
                        .setDeviceTokenId(encryptorUtil.decrypt(pushrecordLoanDoc.getDeviceTokenId()));
            } else {
                yamagataStatusModifyListVO.setPushDate("―");
                yamagataStatusModifyListVO.setPushStatus("5");
                yamagataStatusModifyListVO.setDeviceTokenId("");
            }
            // 変更日時
            yamagataStatusModifyListVO.setStatusModifyDate(statusModifyLoanDoc.getStatusModifyDate());
            yamagataStatusModifyListVO
                    .setStatusModifyDateForSort(String.valueOf(statusModifyLoanDoc.getStatusModifyDateForSort()));
            // 配信状态
            yamagataStatusModifyListVO.setSendStatus(statusModifyLoanDoc.getSendStatus());
            yamagataStatusModifyListVO.setStatus(statusModifyLoanDoc.getStatus());
            yamagataStatusModifyListVOList.add(yamagataStatusModifyListVO);
        }
        // 配信履歴データをソート
        Comparator<YamagataStatusModifyListVO> comparator = new Comparator<YamagataStatusModifyListVO>() {
            public int compare(YamagataStatusModifyListVO s1, YamagataStatusModifyListVO s2) {
                return s1.getStatusModifyDateForSort().compareTo(s2.getStatusModifyDateForSort());
            }
        };
        Collections.sort(yamagataStatusModifyListVOList, comparator);
        yamagataStatusModifyResVO.setYamagataStatusModifyListVO(yamagataStatusModifyListVOList);
        return yamagataStatusModifyResVO;
    }

}
