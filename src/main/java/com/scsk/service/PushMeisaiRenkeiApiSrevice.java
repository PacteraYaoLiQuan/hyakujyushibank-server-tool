package com.scsk.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.IYoUserInfoDoc;
import com.scsk.model.PushRirekiTorihikidDataDoc;
import com.scsk.model.PushRirekiTorihikidDataRecordDoc;
import com.scsk.model.geo.DeviceInfoDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.PushMeisaiRenkeiApiReqVO;
import com.scsk.response.vo.PushMeisaiRenkeiApiResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.PushNotifications;
import com.scsk.vo.TorihikiMeisaiDataVO;

/**
 * プッシュデータ同期
 * 
 * @author ylq
 *
 */
@Service
public class PushMeisaiRenkeiApiSrevice extends AbstractBLogic<PushMeisaiRenkeiApiReqVO, PushMeisaiRenkeiApiResVO> {

    @Autowired
    public RepositoryUtil repositoryUtil;
    @Autowired
    private PushNotifications pushNotifications;
    @Autowired
    private EncryptorUtil encryptorUtil;

    @Override
    protected void preExecute(PushMeisaiRenkeiApiReqVO input) throws Exception {
    }

    @Override
    protected PushMeisaiRenkeiApiResVO doExecute(CloudantClient client, PushMeisaiRenkeiApiReqVO input)
            throws Exception {
        //
        Database db = client.database(Constants.DB_NAME, false);
        PushMeisaiRenkeiApiResVO output = new PushMeisaiRenkeiApiResVO();
     // システム日時を取得
        Date dateSys = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        SimpleDateFormat sdfPush = new SimpleDateFormat(Constants.DATE_FORMAT_YMD);
        String date = sdf.format(dateSys);
        String datePushFrom = sdfPush.format(dateSys) + " 07:00:00";
        String datePushTo = sdfPush.format(dateSys) + " 23:59:59";
        for (TorihikiMeisaiDataVO torihikiMeisaiDataVO : input.getTorihikiDataList()) {
            List<IYoUserInfoDoc> userInfoList = repositoryUtil.getView(db,
                    ApplicationKeys.INSIGHTVIEW_ACCOUNT_ACCOUNTNUMBER, IYoUserInfoDoc.class,
                    encryptorUtil.encrypt(torihikiMeisaiDataVO.getKouzaBango()));
            for (IYoUserInfoDoc userInfoDoc : userInfoList) {
                PushRirekiTorihikidDataDoc pushRirekiTorihikidDataDoc = new PushRirekiTorihikidDataDoc();
                if (userInfoDoc.getDeviceInfoList() != null) {
                    List<String> deviceIdList = new ArrayList<>();
                    for (DeviceInfoDoc deviceInfoDoc : userInfoDoc.getDeviceInfoList()) {
                        if (!deviceInfoDoc.getDeviceTokenId().isEmpty() && !encryptorUtil.decrypt(deviceInfoDoc.getDeviceTokenId()).isEmpty()  ) {
                            deviceIdList.add(encryptorUtil.decrypt(deviceInfoDoc.getDeviceTokenId()));
                        }
                    }
                    if (datePushFrom.compareTo(date) < 0 && datePushTo.compareTo(date) > 0) {
                        // 通知設定ON
                        String pushBn = "2";
                        if ("1".equals(userInfoDoc.getNoticeFlag())) {
                            // 取引区分入金
                            if ("1".equals(torihikiMeisaiDataVO.getTorihikiKbn())) {
                                if ("1".equals(userInfoDoc.getAmountFlg()) || "3".equals(userInfoDoc.getAmountFlg())) {
                                    if (compare(torihikiMeisaiDataVO.getTorihikiKingaku(),
                                            userInfoDoc.getNoticeAmt())) {
                                        if (deviceIdList != null && deviceIdList.size() > 0) {
                                            pushNotifications.sendMessage(torihikiMeisaiDataVO.getPushTitle(), null,
                                                    deviceIdList);
                                            pushBn = "1";
                                        } else {
                                            pushBn = "0";
                                        }
                                    }
                                }
                            } else if ("2".equals(torihikiMeisaiDataVO.getTorihikiKbn())) {
                                if ("2".equals(userInfoDoc.getAmountFlg()) || "3".equals(userInfoDoc.getAmountFlg())) {
                                    if (compare(torihikiMeisaiDataVO.getTorihikiKingaku(),
                                            userInfoDoc.getNoticeAmt())) {
                                        if (deviceIdList != null && deviceIdList.size() > 0) {
                                            pushNotifications.sendMessage(torihikiMeisaiDataVO.getPushTitle(), null,
                                                    deviceIdList);
                                            pushBn = "1";
                                        } else {
                                            pushBn = "0";
                                        }
                                    }
                                }
                            }
                        }
                        PushRirekiTorihikidDataRecordDoc pushRirekiTorihikidDataRecordDoc = new PushRirekiTorihikidDataRecordDoc();
                        // 店舗番号
                        pushRirekiTorihikidDataRecordDoc.setTennpoBango(torihikiMeisaiDataVO.getTennpoBango());
                        pushRirekiTorihikidDataRecordDoc.setUeserID(userInfoDoc.get_id());
                        pushRirekiTorihikidDataRecordDoc.setDeviceTokenId(userInfoDoc.getDeviceInfoList());
                        // 口座番号
                        pushRirekiTorihikidDataRecordDoc.setKouzaBango(torihikiMeisaiDataVO.getKouzaBango());
                        // 科目
                        pushRirekiTorihikidDataRecordDoc.setKamokuCode(torihikiMeisaiDataVO.getKamokuCode());
                        // Pushメッセージのタイトル（管理用）
                        pushRirekiTorihikidDataRecordDoc.setPushTitle(torihikiMeisaiDataVO.getPushTitle());
                        // Push通知表示メッセージ
                        pushRirekiTorihikidDataRecordDoc.setPushMessage(torihikiMeisaiDataVO.getPushMessage());
                        // 送信開始日時（未指定の場合は現在日時）
                        if (torihikiMeisaiDataVO.getPushDatetime().isEmpty()) {
                            pushRirekiTorihikidDataRecordDoc.setPushDatetime(date);
                        } else {
                            pushRirekiTorihikidDataRecordDoc.setPushDatetime(torihikiMeisaiDataVO.getPushDatetime());
                        }
                        // 取引発生日
                        pushRirekiTorihikidDataRecordDoc.setTorihikiDate(torihikiMeisaiDataVO.getTorihikiDate());
                        // 取引発生時刻
                        pushRirekiTorihikidDataRecordDoc.setTorihikiTime(torihikiMeisaiDataVO.getTorihikiTime());
                        // 取引区分（入金│出金）
                        pushRirekiTorihikidDataRecordDoc.setTorihikiKbn(torihikiMeisaiDataVO.getTorihikiKbn());
                        // 取引詳細（現金│振替│振込）
                        pushRirekiTorihikidDataRecordDoc.setTorihikiSyosai(torihikiMeisaiDataVO.getTorihikiSyosai());
                        pushRirekiTorihikidDataRecordDoc.setPushBn(pushBn);
                        // 取引金額
                        pushRirekiTorihikidDataRecordDoc.setTorihikiKingaku(torihikiMeisaiDataVO.getTorihikiKingaku());
                        pushRirekiTorihikidDataRecordDoc.setBatchPushDatetime(date);
                        repositoryUtil.save(db, pushRirekiTorihikidDataRecordDoc);
                    } else {
                        // 店舗番号
                        pushRirekiTorihikidDataDoc.setTennpoBango(torihikiMeisaiDataVO.getTennpoBango());
                        pushRirekiTorihikidDataDoc.setUeserID(userInfoDoc.get_id());
                        pushRirekiTorihikidDataDoc.setDeviceTokenId(userInfoDoc.getDeviceInfoList());
                        // 口座番号
                        pushRirekiTorihikidDataDoc.setKouzaBango(torihikiMeisaiDataVO.getKouzaBango());
                        // 科目
                        pushRirekiTorihikidDataDoc.setKamokuCode(torihikiMeisaiDataVO.getKamokuCode());
                        // Pushメッセージのタイトル（管理用）
                        pushRirekiTorihikidDataDoc.setPushTitle(torihikiMeisaiDataVO.getPushTitle());
                        // Push通知表示メッセージ
                        pushRirekiTorihikidDataDoc.setPushMessage(torihikiMeisaiDataVO.getPushMessage());
                        // 送信開始日時（未指定の場合は現在日時）
                        if (torihikiMeisaiDataVO.getPushDatetime().isEmpty()) {
                            pushRirekiTorihikidDataDoc.setPushDatetime(date);
                        } else {
                            pushRirekiTorihikidDataDoc.setPushDatetime(torihikiMeisaiDataVO.getPushDatetime());
                        }
                        // 取引発生日
                        pushRirekiTorihikidDataDoc.setTorihikiDate(torihikiMeisaiDataVO.getTorihikiDate());
                        // 取引発生時刻
                        pushRirekiTorihikidDataDoc.setTorihikiTime(torihikiMeisaiDataVO.getTorihikiTime());
                        // 取引区分（入金│出金）
                        pushRirekiTorihikidDataDoc.setTorihikiKbn(torihikiMeisaiDataVO.getTorihikiKbn());
                        // 取引詳細（現金│振替│振込）
                        pushRirekiTorihikidDataDoc.setTorihikiSyosai(torihikiMeisaiDataVO.getTorihikiSyosai());
                        pushRirekiTorihikidDataDoc.setPushBn("0");
                        // 取引金額
                        pushRirekiTorihikidDataDoc.setTorihikiKingaku(torihikiMeisaiDataVO.getTorihikiKingaku());
                        pushRirekiTorihikidDataDoc.setBatchPushDatetime(date);
                        repositoryUtil.save(db, pushRirekiTorihikidDataDoc);
                    }
                }
            }
        }
        output.set_id("");
        return output;
    }

    public boolean compare(String noticeAmt, String userNoticeAmt) {
        BigDecimal aBigDecimal = new BigDecimal(0);
        BigDecimal bBigDecimal = new BigDecimal(0);
        if (noticeAmt != null && !noticeAmt.isEmpty()) {
            aBigDecimal = new BigDecimal(noticeAmt);
        }
        if (userNoticeAmt != null && !userNoticeAmt.isEmpty()) {
            bBigDecimal = new BigDecimal(userNoticeAmt);
        }
        if (aBigDecimal.compareTo(bBigDecimal) >= 0) {
            return true;
        }
        return false;
    }
}