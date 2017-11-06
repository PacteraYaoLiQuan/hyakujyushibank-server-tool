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
import com.scsk.model.YamagataPushrecordDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.model.YamagataUserInfoDoc;
import com.scsk.request.vo.YamagataStatusModifyReqVO;
import com.scsk.response.vo.YamagataStatusModifyResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.vo.YamagataStatusModifyListVO;

/**
 * 配信状態検索サービス。<br>
 * <br>
 */
@Service
public class YamagataStatusModifyService extends AbstractBLogic<YamagataStatusModifyReqVO, YamagataStatusModifyResVO> {

    @Autowired  EncryptorUtil encryptorUtil;

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
        String queryKey = "accountAppSeq:\"" + yamagataStatusModifyReqVO.getAccountAppSeq() + "\"";
        List<YamagataStatusModifyDoc> yamagataStatusModifyList = repositoryUtil.getIndex(db,
                ApplicationKeys.INSIGHTINDEX_STATUSMODIFY_SEARCHINFO, queryKey, null,200,YamagataStatusModifyDoc.class);
        YamagataUserInfoDoc userInfoDoc = new YamagataUserInfoDoc();

        try {
            userInfoDoc = repositoryUtil.find(db, yamagataStatusModifyReqVO.getUserId(),
                    YamagataUserInfoDoc.class);
        } catch (Exception e) {

        }
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
            // 申込処理ステータス
            yamagataStatusModifyListVO.setStatus(yamagataStatusModifyDoc.getStatus());
            // 端末ID
            if (yamagataPushrecordDoc != null) {
               
                // 配信日時
                if (yamagataPushrecordDoc.getPushDate() != null && !yamagataPushrecordDoc.getPushDate().isEmpty()) {
                    yamagataStatusModifyListVO.setPushDate(yamagataPushrecordDoc.getPushDate());
                } else {
                    yamagataStatusModifyListVO.setPushDate("―");
                }
                yamagataStatusModifyListVO.setPushStatus(yamagataPushrecordDoc.getPushStatus());
            } else {
                yamagataStatusModifyListVO.setPushDate("―");
                yamagataStatusModifyListVO.setPushStatus("5");
            }
            if (userInfoDoc != null) {
                yamagataStatusModifyListVO.setDeviceTokenId(encryptorUtil.decrypt(userInfoDoc.getDeviceId()));
            }
            // 変更日時
            yamagataStatusModifyListVO.setStatusModifyDate(yamagataStatusModifyDoc.getStatusModifyDate());
            yamagataStatusModifyListVO.setStatusModifyDateForSort(String.valueOf(yamagataStatusModifyDoc.getStatusModifyDateForSort()));
            // 配信状态
            yamagataStatusModifyListVO.setSendStatus(yamagataStatusModifyDoc.getSendStatus());
            yamagataStatusModifyListVO.setStatus(yamagataStatusModifyDoc.getStatus());
            yamagataStatusModifyListVOList.add(yamagataStatusModifyListVO);
        }
        // 配信履歴データをソート
        Comparator<YamagataStatusModifyListVO> comparator = new Comparator<YamagataStatusModifyListVO>() {
            public int compare(YamagataStatusModifyListVO s1, YamagataStatusModifyListVO s2) {
                // 最新変更日付
//                if (s1.getStatusModifyDate().compareTo(s2.getStatusModifyDate()) != 0) {
                    return s1.getStatusModifyDateForSort().compareTo(s2.getStatusModifyDateForSort());
//                } else {
//                    // Push開封状況
//                    return s1.getStatusModifyDate().compareTo(s2.getStatusModifyDate());
//                }
            }
        };
        Collections.sort(yamagataStatusModifyListVOList, comparator);
        yamagataStatusModifyResVO.setYamagataStatusModifyListVO(yamagataStatusModifyListVOList);
        return yamagataStatusModifyResVO;
    }

}
