package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import com.scsk.model.Account114AppDoc;
import com.scsk.model.Account114AppRecordDoc;
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.model.PushrecordDoc;
import com.scsk.model.StatusModifyDoc;
import com.scsk.model.YamagataPushrecordDoc;
import com.scsk.model.YamagataStatusModifyDoc;
import com.scsk.response.vo.Account114AppInitResVO;
import com.scsk.response.vo.AccountAppInitResVO;
import com.scsk.response.vo.AccountYamaGataAppInitResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.Utils;
import com.scsk.vo.AccountApp114InitVO;
import com.scsk.vo.AccountAppInitVO;
import com.scsk.vo.AccountAppYamaGataInitVO;
import com.scsk.vo.YamagataStatusModifyListVO;

/**
 * 申込一覧初期化検索サービス。<br>
 * <br>
 * 申込一覧初期化検索を実装するロジック。<br>
 */
@Service
public class AccountAppListInitService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Value("${bank_cd}")
    private String bank_cd;
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

        AccountAppInitResVO accountAppInitResVO = new AccountAppInitResVO();
        AccountYamaGataAppInitResVO accountAppInitResVO2 = new AccountYamaGataAppInitResVO();
        Account114AppInitResVO account114AppInitResVO = new Account114AppInitResVO();
        if ("0169".equals(bank_cd)) {
            List<AccountAppInitVO> accountAppList = hirosima(db);
            accountAppInitResVO.setAccountAppList(accountAppList);
            actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_1, db);
            return accountAppInitResVO;
        } else if ("0122".equals(bank_cd)) {
            List<AccountAppYamaGataInitVO> accountAppList = yamagata(db);
            accountAppInitResVO2.setAccountAppList(accountAppList);
            actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_1, db);
            return accountAppInitResVO2;
        } else if ("0173".equals(bank_cd)) {
            List<AccountApp114InitVO> accountAppList = hyakujyushi(db);
            account114AppInitResVO.setAccountAppList(accountAppList);
            actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_1, db);
            return account114AppInitResVO;
        }
        return new BaseResVO();
    }

    // 114
    private List<AccountApp114InitVO> hyakujyushi(Database db) throws Exception {

        // 申込一覧初期データを取得
        List<Account114AppDoc> accountAppDocList = new ArrayList<>();
        List<AccountApp114InitVO> accountAppList = new ArrayList<>();

        accountAppDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST,
                Account114AppDoc.class);
        String queryKey = "";

        for (Account114AppDoc doc : accountAppDocList) {
            AccountApp114InitVO accountAppInitVO = new AccountApp114InitVO();
            List<YamagataStatusModifyListVO> yamagataStatusModifyListVOList = new ArrayList<YamagataStatusModifyListVO>();
            // 申込一覧初期化用データを戻る
            // Push開封状況
            queryKey = "accountAppSeq:\"" + doc.getAccountAppSeq() + "\"";
            List<StatusModifyDoc> statusModifyList = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_STATUSMODIFY_SEARCHINFO, queryKey, StatusModifyDoc.class);
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
                    accountAppInitVO.setPushStatus(yamagataStatusModifyListVOList.get(0).getPushStatus());
                } else {
                    accountAppInitVO.setPushStatus("5");
                }

            } else {
                accountAppInitVO.setPushStatus("5");
            }

            // 2週間以内の検索回数を取得
            int twoWeeksCount = 0;
            // 取引目的{
            String[] purpose = doc.getAccountPurpose().split(",");
            List<String> accountPurpose = Arrays.asList(purpose);
            Collections.sort(accountPurpose);
            accountAppInitVO.setAccountPurpose(accountPurpose);
            // その他取引目的
            accountAppInitVO.setAccountPurposeOther(encryptorUtil.decrypt(doc.getAccountPurposeOther()));
            // 職業
            String[] job = doc.getJobKbn().split(",");
            List<String> jobKbn = Arrays.asList(job);
            Collections.sort(jobKbn);
            accountAppInitVO.setJobKbn(jobKbn);
            // 受付番号
            accountAppInitVO.setAccountAppSeq(doc.getAccountAppSeq());
            // その他職業
            accountAppInitVO.setJobKbnOther(encryptorUtil.decrypt(doc.getJobKbnOther()));
            accountAppInitVO.setAccountType(doc.getAccountType());
            accountAppInitVO.setAddressR(encryptorUtil.decrypt(doc.getAddressR()));
            accountAppInitVO.setAddress(encryptorUtil.decrypt(doc.getAddress()));
            accountAppInitVO.setApplicationEndFlg(encryptorUtil.decrypt(doc.getApplicationEndFlg()));
            accountAppInitVO.setApplicationFlg(encryptorUtil.decrypt(doc.getApplicationFlg()));
            String[] reason = doc.getApplicationReason().split(",");
            List<String> applicationReason = Arrays.asList(reason);
            accountAppInitVO.setApplicationReason(applicationReason);
            accountAppInitVO.setBankbookType(doc.getBankbookType());
            accountAppInitVO.setBirthday(encryptorUtil.decrypt(doc.getBirthday()));
            accountAppInitVO.setBirthdayR(encryptorUtil.decrypt(doc.getBirthdayR()));
            accountAppInitVO.setCardType(doc.getCardType());
            accountAppInitVO.setCreditlimit(encryptorUtil.decrypt(doc.getCreditlimit()));
            accountAppInitVO.setDeviceTokenId(encryptorUtil.decrypt(doc.getDeviceTokenId()));
            accountAppInitVO.setFirstNameR(encryptorUtil.decrypt(doc.getFirstNameR()));
            accountAppInitVO
                    .setName(encryptorUtil.decrypt(doc.getFirstName()) + encryptorUtil.decrypt(doc.getLastName()));

            accountAppInitVO.setKanaAddress(encryptorUtil.decrypt(doc.getKanaAddress()));
            accountAppInitVO.setKanaName(
                    encryptorUtil.decrypt(doc.getKanaFirstName()) + encryptorUtil.decrypt(doc.getKanaLastName()));
            accountAppInitVO.setKanaWorkName(encryptorUtil.decrypt(doc.getKanaWorkName()));
            String[] process = doc.getKnowProcess().split(",");
            List<String> knowProcess = Arrays.asList(process);
            accountAppInitVO.setKnowProcess(knowProcess);
            accountAppInitVO.setLastNameR(encryptorUtil.decrypt(doc.getLastNameR()));
            accountAppInitVO.setLicenseId(encryptorUtil.decrypt(doc.getLicenseId()));
            accountAppInitVO.setLicenseIdR(encryptorUtil.decrypt(doc.getLicenseIdR()));
            accountAppInitVO.setOnlinePassword(encryptorUtil.decrypt(doc.getOnlinePassword()));
            accountAppInitVO.setOnlinePasswordConfirm(encryptorUtil.decrypt(doc.getOnlinePasswordConfirm()));
            accountAppInitVO.setPhoneNumber(encryptorUtil.decrypt(doc.getPhoneNumber()));
            accountAppInitVO.setPostCode(encryptorUtil.decrypt(doc.getPostCode()));
            accountAppInitVO.setPostCodeR(encryptorUtil.decrypt(doc.getPostCodeR()));
            accountAppInitVO.setPrefecturesCode(encryptorUtil.decrypt(doc.getPrefecturesCode()));
            accountAppInitVO.setPrefecturesCodeR(encryptorUtil.decrypt(doc.getPrefecturesCodeR()));
            accountAppInitVO.setSalesOfficeOption(encryptorUtil.decrypt(doc.getSalesOfficeOption()));
            accountAppInitVO.setSecurityPassword(encryptorUtil.decrypt(doc.getSecurityPassword()));
            accountAppInitVO.setSecurityPasswordConfirm(encryptorUtil.decrypt(doc.getSecurityPasswordConfirm()));
            accountAppInitVO.setSexKbn(doc.getSexKbn());
            accountAppInitVO.setTeleNumber(encryptorUtil.decrypt(doc.getTeleNumber()));
            accountAppInitVO.setUserId(encryptorUtil.decrypt(doc.getUserId()));
            accountAppInitVO.setUserType(encryptorUtil.decrypt(doc.getUserType()));
            accountAppInitVO.setWorkAddress(encryptorUtil.decrypt(doc.getWorkAddress()));
            accountAppInitVO.setWorkName(encryptorUtil.decrypt(doc.getWorkName()));
            accountAppInitVO.setWorkNumberKbn(doc.getWorkNumberKbn());
            accountAppInitVO.setWorkPostCode(encryptorUtil.decrypt(doc.getWorkPostCode()));
            accountAppInitVO.setWorkPrefecturesCode(encryptorUtil.decrypt(doc.getWorkPrefecturesCode()));
            accountAppInitVO.setWorkTeleNumber(encryptorUtil.decrypt(doc.getWorkTeleNumber()));
            accountAppInitVO.setKanaWorkName(encryptorUtil.decrypt(doc.getKanaWorkName()));
            accountAppInitVO.setStatus(doc.getStatus());
            // 電話番号鑑定結果
            int TC_Count1 = 0;
            int TC_Count2 = 0;
            if (Utils.isNotNullAndEmpty(doc.getTC_Count2())) {
                TC_Count2 = Integer.parseInt(doc.getTC_Count2());
            }
            if (Utils.isNotNullAndEmpty(doc.getTC_Count1())) {
                TC_Count1 = Integer.parseInt(doc.getTC_Count1());
            }
            boolean flag = false;
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getPhoneNumber()))
                    && Utils.isNotNullAndEmpty(doc.getTC_Result2())) {
                // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
                // attention＝1or2or3orEorU or 検索結果 result=0以外
                if (TC_Count2 >= 2 || doc.getTC_Tacsflag2().equals("0") || doc.getTC_Tacsflag2().equals("2")
                        || doc.getTC_Tacsflag2().equals("3") || doc.getTC_Tacsflag2().equals("4")
                        || doc.getTC_Tacsflag2().equals("7") || doc.getTC_Attention2().equals("1")
                        || doc.getTC_Attention2().equals("2") || doc.getTC_Attention2().equals("3")
                        || doc.getTC_Attention2().equals("E") || doc.getTC_Attention2().equals("U")
                        || !doc.getTC_Result2().equals("0")) {
                    // 要注意
                    // accountAppInitVO.setAppraisalTelResult("1");
                    flag = true;
                }
                // } else{
                // // 問題なし
                // accountAppInitVO.setAppraisalTelResult("2");
                // }
            }
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getTeleNumber()))
                    && Utils.isNotNullAndEmpty(doc.getTC_Result1())) {
                // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
                // attention＝1or2or3orEorU or 検索結果 result=0以外
                if (TC_Count1 >= 2 || doc.getTC_Tacsflag1().equals("0") || doc.getTC_Tacsflag1().equals("2")
                        || doc.getTC_Tacsflag1().equals("3") || doc.getTC_Tacsflag1().equals("4")
                        || doc.getTC_Tacsflag1().equals("7") || doc.getTC_Attention1().equals("1")
                        || doc.getTC_Attention1().equals("2") || doc.getTC_Attention1().equals("3")
                        || doc.getTC_Attention1().equals("E") || doc.getTC_Attention1().equals("U")
                        || !doc.getTC_Result1().equals("0")) {
                    // 要注意
                    // accountAppInitVO.setAppraisalTelResult("1");
                    flag = true;
                }
                // } else {
                // // 問題なし
                // accountAppInitVO.setAppraisalTelResult("2");
                // }
            }
            if (flag) {
                // 要注意
                accountAppInitVO.setAppraisalTelResult("1");
            } else {
                // 問題なし
                accountAppInitVO.setAppraisalTelResult("2");
            }
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getTeleNumber()))) {
                if (!Utils.isNotNullAndEmpty(doc.getTC_Result1())) {
                    accountAppInitVO.setAppraisalTelResult("3");
                }
            }
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getPhoneNumber()))) {
                if (!Utils.isNotNullAndEmpty(doc.getTC_Result2())) {
                    accountAppInitVO.setAppraisalTelResult("3");
                }
            }
            if (Utils.isNotNullAndEmpty(doc.getIC_SearchHistory())) {
                String[] searchHistory = doc.getIC_SearchHistory().split(",");
                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_APP_DATE2);

                Calendar nowdate = Calendar.getInstance();
                nowdate.add(Calendar.DAY_OF_WEEK, -14);

                for (int i = 0; i < searchHistory.length; i++) {
                    String sDate = searchHistory[i].split(":")[0];
                    Date date;
                    int count1 = 0;
                    int count2 = 0;
                    try {
                        date = format.parse(sDate);
                        if (date.compareTo(nowdate.getTime()) == 0 || date.compareTo(nowdate.getTime()) == 1) {
                            count1 = searchHistory[i].indexOf(":");
                            count1 = count1 + 1;
                            count2 = Integer.parseInt(searchHistory[i].substring(count1));
                            twoWeeksCount = twoWeeksCount + count2;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // ＩＰアドレス鑑定結果
            // 国脅威度＝high or PS-IP＝true or Proxy利用＝true or 2週間以内の検索回数＞＝2
            if (doc.getIC_CountryThreat().equals(Constants.THREAT_1) || doc.getIC_PSIP().equals(Constants.PSIP_1)
                    || doc.getIC_Proxy().equals(Constants.PSIP_1) || twoWeeksCount >= 2) {
                // 要注意
                accountAppInitVO.setAppraisalIPResult("1");
            } else {
                // 問題なし
                accountAppInitVO.setAppraisalIPResult("2");
            }
            if (!Utils.isNotNullAndEmpty(doc.getIC_CountryCode())) {
                // 鑑定失敗
                accountAppInitVO.setAppraisalIPResult("3");
            }
            accountAppInitVO.set_id(doc.get_id());
            accountAppInitVO.set_rev(doc.get_rev());
            accountAppInitVO.setApplicationDate(doc.getApplicationDate());
            accountAppInitVO.setReceiptDate(doc.getReceiptDate());
            accountAppInitVO.setIdentificationImage(doc.getIdentificationImage());
            accountAppInitVO.setIdentificationType(doc.getIdentificationType());
            accountAppInitVO.setLivingConditions(doc.getLivingConditions());
            accountAppInitVO.setLivingConditionsImage(doc.getLivingConditionsImage());
            accountAppList.add(accountAppInitVO);
        }

        return accountAppList;

    }

    // 広島
    public List<AccountAppInitVO> hirosima(Database db) throws Exception {
        // 申込一覧初期データを取得
        List<AccountAppDoc> accountAppDocList = new ArrayList<>();
        List<AccountAppInitVO> accountAppList = new ArrayList<>();

        accountAppDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST,
                AccountAppDoc.class);

        // 配信履歴初期データを取得
        List<PushrecordDoc> pushrecordDoc = new ArrayList<>();
        String flg = "";
        String queryKey = "";
        MapReduce mapReduce = new MapReduce();
        if (accountAppDocList != null && accountAppDocList.size() != 0) {
            for (AccountAppDoc doc : accountAppDocList) {
                if (flg.isEmpty() || flg.equals("")) {
                    queryKey = "doc.accountAppSeq === \"" + doc.getAccountAppSeq() + "\"";
                    flg = "1";
                } else {
                    queryKey = queryKey + " || doc.accountAppSeq === \"" + doc.getAccountAppSeq() + "\"";
                }
            }

            mapReduce
                    .setMap("function (doc) {if(doc.docType && doc.docType === \"PUSHRECORD\" && doc.delFlg && doc.delFlg===\"0\" && ("
                            + queryKey + ")) {emit(doc.accountAppSeq, 1);}}");
            pushrecordDoc = repositoryUtil.queryByDynamicView(db, mapReduce, PushrecordDoc.class);

            // 配信履歴データをソート
            Comparator<PushrecordDoc> comparator = new Comparator<PushrecordDoc>() {
                public int compare(PushrecordDoc s1, PushrecordDoc s2) {
                    if (s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq()) != 0) {
                        return s1.getAccountAppSeq().compareTo(s2.getAccountAppSeq());
                    }
                    // 最新開封状況取得用日付
                    else if (s1.getSaveDate().compareTo(s2.getSaveDate()) != 0) {
                        return s1.getSaveDate().compareTo(s2.getSaveDate()) * -1;
                    } else if (s1.getOpenDate().compareTo(s2.getOpenDate()) != 0) {
                        // 開封日付
                        return s1.getOpenDate().compareTo(s2.getOpenDate()) * -1;
                    } else {
                        // Push開封状況
                        return s1.getPushStatus().compareTo(s2.getPushStatus());
                    }
                }
            };
            Collections.sort(pushrecordDoc, comparator);

            // if (accountAppDocList != null && accountAppDocList.size() != 0) {
            for (AccountAppDoc doc : accountAppDocList) {
                AccountAppInitVO accountAppInitVO = new AccountAppInitVO();
                // 申込一覧初期化用データを戻る
                // Push開封状況
                for (PushrecordDoc doc2 : pushrecordDoc) {
                    if (doc2.getAccountAppSeq().equals(doc.getAccountAppSeq())) {
                        // Push開封状況
                        if (doc2.getPushStatus() != null && !doc2.getPushStatus().isEmpty()) {
                            if ("3".equals(doc2.getPushStatus())) {
                                accountAppInitVO.setPushStatus("5");
                            } else if ("4".equals(doc2.getPushStatus())) {
                                accountAppInitVO.setPushStatus("5");
                            } else if ("5".equals(doc2.getPushStatus())) {
                                accountAppInitVO.setPushStatus("5");
                            } else if ("6".equals(doc2.getPushStatus())) {
                                accountAppInitVO.setPushStatus("5");
                            } else if ("7".equals(doc2.getPushStatus())) {
                                accountAppInitVO.setPushStatus("5");
                            } else {
                                accountAppInitVO.setPushStatus(doc2.getPushStatus());
                            }
                        } else {
                            accountAppInitVO.setPushStatus("5");
                        }
                        break;
                    }
                }
                if (!Utils.isNotNullAndEmpty(accountAppInitVO.getPushStatus())) {
                    accountAppInitVO.setPushStatus("5");
                }
                accountAppInitVO.set_id(doc.get_id());
                accountAppInitVO.set_rev(doc.get_rev());
                // 帳票出力回数
                accountAppInitVO.setBillOutputCount(doc.getBillOutputCount());
                // 姓名
                accountAppInitVO.setName(
                        encryptorUtil.decrypt(doc.getLastName()) + " " + encryptorUtil.decrypt(doc.getFirstName()));
                // 申込受付日付
                accountAppInitVO.setReceiptDate(doc.getReceiptDate());
                // 受付番号
                accountAppInitVO.setAccountAppSeq(doc.getAccountAppSeq());
                // ステータス
                accountAppInitVO.setStatus(doc.getStatus());
                // IP脅威度
                accountAppInitVO.setIC_IpThreat(doc.getIC_IpThreat());

                // 電話番号鑑定結果
                int TC_Count1 = 0;
                int TC_Count2 = 0;
                if (Utils.isNotNullAndEmpty(doc.getTC_Count2())) {
                    TC_Count2 = Integer.parseInt(doc.getTC_Count2());
                }
                if (Utils.isNotNullAndEmpty(doc.getTC_Count1())) {
                    TC_Count1 = Integer.parseInt(doc.getTC_Count1());
                }
                boolean flag = false;
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getPhoneNumber()))
                        && Utils.isNotNullAndEmpty(doc.getTC_Result2())) {
                    // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
                    // attention＝1or2or3orEorU or 検索結果 result=0以外
                    if (TC_Count2 >= 2 || doc.getTC_Tacsflag2().equals("0") || doc.getTC_Tacsflag2().equals("2")
                            || doc.getTC_Tacsflag2().equals("3") || doc.getTC_Tacsflag2().equals("4")
                            || doc.getTC_Tacsflag2().equals("7") || doc.getTC_Attention2().equals("1")
                            || doc.getTC_Attention2().equals("2") || doc.getTC_Attention2().equals("3")
                            || doc.getTC_Attention2().equals("E") || doc.getTC_Attention2().equals("U")
                            || !doc.getTC_Result2().equals("0")) {
                        // 要注意
                        // accountAppInitVO.setAppraisalTelResult("1");
                        flag = true;
                    }
                    // } else{
                    // // 問題なし
                    // accountAppInitVO.setAppraisalTelResult("2");
                    // }
                }
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getTeleNumber()))
                        && Utils.isNotNullAndEmpty(doc.getTC_Result1())) {
                    // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
                    // attention＝1or2or3orEorU or 検索結果 result=0以外
                    if (TC_Count1 >= 2 || doc.getTC_Tacsflag1().equals("0") || doc.getTC_Tacsflag1().equals("2")
                            || doc.getTC_Tacsflag1().equals("3") || doc.getTC_Tacsflag1().equals("4")
                            || doc.getTC_Tacsflag1().equals("7") || doc.getTC_Attention1().equals("1")
                            || doc.getTC_Attention1().equals("2") || doc.getTC_Attention1().equals("3")
                            || doc.getTC_Attention1().equals("E") || doc.getTC_Attention1().equals("U")
                            || !doc.getTC_Result1().equals("0")) {
                        // 要注意
                        // accountAppInitVO.setAppraisalTelResult("1");
                        flag = true;
                    }
                    // } else {
                    // // 問題なし
                    // accountAppInitVO.setAppraisalTelResult("2");
                    // }
                }
                if (flag) {
                    // 要注意
                    accountAppInitVO.setAppraisalTelResult("1");
                } else {
                    // 問題なし
                    accountAppInitVO.setAppraisalTelResult("2");
                }
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getTeleNumber()))) {
                    if (!Utils.isNotNullAndEmpty(doc.getTC_Result1())) {
                        accountAppInitVO.setAppraisalTelResult("3");
                    }
                }
                if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getPhoneNumber()))) {
                    if (!Utils.isNotNullAndEmpty(doc.getTC_Result2())) {
                        accountAppInitVO.setAppraisalTelResult("3");
                    }
                }
                // 2週間以内の検索回数を取得
                int twoWeeksCount = 0;
                if (Utils.isNotNullAndEmpty(doc.getIC_SearchHistory())) {
                    String[] searchHistory = doc.getIC_SearchHistory().split(",");
                    SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_APP_DATE2);

                    Calendar nowdate = Calendar.getInstance();
                    nowdate.add(Calendar.DAY_OF_WEEK, -14);

                    for (int i = 0; i < searchHistory.length; i++) {
                        String sDate = searchHistory[i].split(":")[0];
                        Date date;
                        int count1 = 0;
                        int count2 = 0;
                        try {
                            date = format.parse(sDate);
                            if (date.compareTo(nowdate.getTime()) == 0 || date.compareTo(nowdate.getTime()) == 1) {
                                count1 = searchHistory[i].indexOf(":");
                                count1 = count1 + 1;
                                count2 = Integer.parseInt(searchHistory[i].substring(count1));
                                twoWeeksCount = twoWeeksCount + count2;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                // ＩＰアドレス鑑定結果
                // 国脅威度＝high or PS-IP＝true or Proxy利用＝true or 2週間以内の検索回数＞＝2
                if (doc.getIC_CountryThreat().equals(Constants.THREAT_1) || doc.getIC_PSIP().equals(Constants.PSIP_1)
                        || doc.getIC_Proxy().equals(Constants.PSIP_1) || twoWeeksCount >= 2) {
                    // 要注意
                    accountAppInitVO.setAppraisalIPResult("1");
                } else {
                    // 問題なし
                    accountAppInitVO.setAppraisalIPResult("2");
                }
                if (!Utils.isNotNullAndEmpty(doc.getIC_CountryCode())) {
                    // 鑑定失敗
                    accountAppInitVO.setAppraisalIPResult("3");
                }
                accountAppList.add(accountAppInitVO);
            }
        }
        return accountAppList;
    }

    // 山形
    public List<AccountAppYamaGataInitVO> yamagata(Database db) throws Exception {
        // 申込一覧初期データを取得
        List<AccountYamaGataAppDoc> accountAppDocList = new ArrayList<>();
        List<AccountAppYamaGataInitVO> accountAppList = new ArrayList<>();

        accountAppDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST,
                AccountYamaGataAppDoc.class);
        String queryKey = "";

        for (AccountYamaGataAppDoc doc : accountAppDocList) {
            AccountAppYamaGataInitVO accountAppInitVO = new AccountAppYamaGataInitVO();
            List<YamagataStatusModifyListVO> yamagataStatusModifyListVOList = new ArrayList<YamagataStatusModifyListVO>();
            // 申込一覧初期化用データを戻る
            // Push開封状況
            queryKey = "accountAppSeq:\"" + doc.getAccountAppSeq() + "\"";
            List<YamagataStatusModifyDoc> yamagataStatusModifyList = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_STATUSMODIFY_SEARCHINFO, queryKey, YamagataStatusModifyDoc.class);
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
                    accountAppInitVO.setPushStatus(yamagataStatusModifyListVOList.get(0).getPushStatus());
                } else {
                    accountAppInitVO.setPushStatus("5");
                }

            } else {
                accountAppInitVO.setPushStatus("5");
            }
            accountAppInitVO.set_id(doc.get_id());
            accountAppInitVO.set_rev(doc.get_rev());
            // // 帳票出力回数
            // accountAppInitVO.setBillOutputCount(doc.getBillOutputCount());
            // 姓名
            accountAppInitVO.setName(
                    encryptorUtil.decrypt(doc.getLastName()) + " " + encryptorUtil.decrypt(doc.getFirstName()));
            // 申込受付日付
            accountAppInitVO.setReceiptDate(doc.getReceiptDate());
            // 受付番号
            accountAppInitVO.setAccountAppSeq(doc.getAccountAppSeq());
            // ステータス
            accountAppInitVO.setStatus(doc.getStatus());
            // IP脅威度
            accountAppInitVO.setIC_IpThreat(doc.getIC_IpThreat());

            // 電話番号鑑定結果
            int TC_Count1 = 0;
            int TC_Count2 = 0;
            if (Utils.isNotNullAndEmpty(doc.getTC_Count2())) {
                TC_Count2 = Integer.parseInt(doc.getTC_Count2());
            }
            if (Utils.isNotNullAndEmpty(doc.getTC_Count1())) {
                TC_Count1 = Integer.parseInt(doc.getTC_Count1());
            }
            boolean flag = false;
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getPhoneNumber()))
                    && Utils.isNotNullAndEmpty(doc.getTC_Result2())) {
                // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
                // attention＝1or2or3orEorU or 検索結果 result=0以外
                if (TC_Count2 >= 2 || doc.getTC_Tacsflag2().equals("0") || doc.getTC_Tacsflag2().equals("2")
                        || doc.getTC_Tacsflag2().equals("3") || doc.getTC_Tacsflag2().equals("4")
                        || doc.getTC_Tacsflag2().equals("7") || doc.getTC_Attention2().equals("1")
                        || doc.getTC_Attention2().equals("2") || doc.getTC_Attention2().equals("3")
                        || doc.getTC_Attention2().equals("E") || doc.getTC_Attention2().equals("U")
                        || !doc.getTC_Result2().equals("0")) {
                    // 要注意
                    // accountAppInitVO.setAppraisalTelResult("1");
                    flag = true;
                }
                // } else{
                // // 問題なし
                // accountAppInitVO.setAppraisalTelResult("2");
                // }
            }
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getTeleNumber()))
                    && Utils.isNotNullAndEmpty(doc.getTC_Result1())) {
                // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
                // attention＝1or2or3orEorU or 検索結果 result=0以外
                if (TC_Count1 >= 2 || doc.getTC_Tacsflag1().equals("0") || doc.getTC_Tacsflag1().equals("2")
                        || doc.getTC_Tacsflag1().equals("3") || doc.getTC_Tacsflag1().equals("4")
                        || doc.getTC_Tacsflag1().equals("7") || doc.getTC_Attention1().equals("1")
                        || doc.getTC_Attention1().equals("2") || doc.getTC_Attention1().equals("3")
                        || doc.getTC_Attention1().equals("E") || doc.getTC_Attention1().equals("U")
                        || !doc.getTC_Result1().equals("0")) {
                    // 要注意
                    // accountAppInitVO.setAppraisalTelResult("1");
                    flag = true;
                }
                // } else {
                // // 問題なし
                // accountAppInitVO.setAppraisalTelResult("2");
                // }
            }
            if (flag) {
                // 要注意
                accountAppInitVO.setAppraisalTelResult("1");
            } else {
                // 問題なし
                accountAppInitVO.setAppraisalTelResult("2");
            }
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getTeleNumber()))) {
                if (!Utils.isNotNullAndEmpty(doc.getTC_Result1())) {
                    accountAppInitVO.setAppraisalTelResult("3");
                }
            }
            if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getPhoneNumber()))) {
                if (!Utils.isNotNullAndEmpty(doc.getTC_Result2())) {
                    accountAppInitVO.setAppraisalTelResult("3");
                }
            }
            // 2週間以内の検索回数を取得
            int twoWeeksCount = 0;
            if (Utils.isNotNullAndEmpty(doc.getIC_SearchHistory())) {
                String[] searchHistory = doc.getIC_SearchHistory().split(",");
                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_APP_DATE2);

                Calendar nowdate = Calendar.getInstance();
                nowdate.add(Calendar.DAY_OF_WEEK, -14);

                for (int i = 0; i < searchHistory.length; i++) {
                    String sDate = searchHistory[i].split(":")[0];
                    Date date;
                    int count1 = 0;
                    int count2 = 0;
                    try {
                        date = format.parse(sDate);
                        if (date.compareTo(nowdate.getTime()) == 0 || date.compareTo(nowdate.getTime()) == 1) {
                            count1 = searchHistory[i].indexOf(":");
                            count1 = count1 + 1;
                            count2 = Integer.parseInt(searchHistory[i].substring(count1));
                            twoWeeksCount = twoWeeksCount + count2;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            accountAppList.add(accountAppInitVO);
        }

        return accountAppList;
    }
}
