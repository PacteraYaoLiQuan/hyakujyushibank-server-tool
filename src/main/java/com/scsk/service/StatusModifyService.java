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
import com.scsk.model.HyakujyushiUserInfoDoc;
import com.scsk.model.StatusModifyDoc;
import com.scsk.model.YamagataPushrecordDoc;
import com.scsk.request.vo.StatusModifyReqVO;
import com.scsk.response.vo.StatusModifyResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.vo.StatusModifyListVO;
@Service
public class StatusModifyService extends AbstractBLogic<StatusModifyReqVO, StatusModifyResVO>{

    @Autowired  EncryptorUtil encryptorUtil;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param YamagataStatusModifyReqVO
     *            入力情報
     */
    @Override
    protected void preExecute(StatusModifyReqVO yamagataStatusModifyReqVO) throws Exception {

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
    protected StatusModifyResVO doExecute(CloudantClient client,
            StatusModifyReqVO yamagataStatusModifyReqVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        List<StatusModifyListVO> yamagataStatusModifyListVOList = new ArrayList<StatusModifyListVO>();
        StatusModifyResVO yamagataStatusModifyResVO = new StatusModifyResVO();
        String queryKey =yamagataStatusModifyReqVO.getAccountAppSeq();
        List<StatusModifyDoc> yamagataStatusModifyList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_STATUSMODIFY_STATUSMODIFY,StatusModifyDoc.class,queryKey);
        HyakujyushiUserInfoDoc userInfoDoc = new HyakujyushiUserInfoDoc();

        try {
            userInfoDoc = repositoryUtil.find(db, yamagataStatusModifyReqVO.getUserId(),
                    HyakujyushiUserInfoDoc.class);
        } catch (Exception e) {

        }
        for (StatusModifyDoc yamagataStatusModifyDoc : yamagataStatusModifyList) {
            YamagataPushrecordDoc yamagataPushrecordDoc = null;
            if (!yamagataStatusModifyDoc.getPushRecordOid().isEmpty()) {
                try {
                    yamagataPushrecordDoc = (YamagataPushrecordDoc) repositoryUtil.find(db,
                            yamagataStatusModifyDoc.getPushRecordOid(), YamagataPushrecordDoc.class);
                } catch (Exception e) {

                }
            }
            StatusModifyListVO yamagataStatusModifyListVO = new StatusModifyListVO();
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
            if (userInfoDoc != null && userInfoDoc.getDeviceInfoList().size()>0) {
                yamagataStatusModifyListVO.setDeviceTokenId(encryptorUtil.decrypt(userInfoDoc.getDeviceInfoList().get(0).getDeviceTokenId()));
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
        Comparator<StatusModifyListVO> comparator = new Comparator<StatusModifyListVO>() {
            public int compare(StatusModifyListVO s1, StatusModifyListVO s2) {
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
        yamagataStatusModifyResVO.setStatusModifyListVO(yamagataStatusModifyListVOList);
        return yamagataStatusModifyResVO;
    }

}
