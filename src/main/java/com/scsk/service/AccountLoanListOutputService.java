package com.scsk.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.Account114PushDetailDoc;
import com.scsk.model.AccountLoanDoc;
import com.scsk.model.HyakujyushiUserInfoDoc;
import com.scsk.model.PushrecordLoanDoc;
import com.scsk.model.StatusModifyLoanDoc;
import com.scsk.model.geo.DeviceInfoDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountLoanListOutputButtonReqVO;
import com.scsk.response.vo.AccountLoan114DetailResVO;
import com.scsk.response.vo.AccountLoanListOutputButtonResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PushNotifications;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;

/**
 * 帳票出力検索サービス。<br>
 * <br>
 * 帳票出力検索を実装するロジック。<br>
 */
@Service
public class AccountLoanListOutputService
        extends AbstractBLogic<AccountLoanListOutputButtonReqVO, AccountLoanListOutputButtonResVO> {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private PushNotifications pushNotifications;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param accountAppListOutputButtonReqVO
     *            申込一覧情報
     */
    @Override
    protected void preExecute(AccountLoanListOutputButtonReqVO AccountLoanListOutputButtonReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param accountLoanListOutputButtonReqVO
     *            申込一覧情報
     * @return accountLoanListOutputButtonResVO 帳票用情報
     */
    @Override
    protected AccountLoanListOutputButtonResVO doExecute(CloudantClient client,
            AccountLoanListOutputButtonReqVO accountLoanListOutputButtonReqVO) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        AccountLoanListOutputButtonResVO accountLoanListOutputButtonResVO = hyakujyushi(client, db,
                accountLoanListOutputButtonReqVO);
        return accountLoanListOutputButtonResVO;

    }

    /**
     * 114
     *
     */

    public AccountLoanListOutputButtonResVO hyakujyushi(CloudantClient client, Database db,
            AccountLoanListOutputButtonReqVO accountLoanListOutputButtonReqVO) throws Exception {
        AccountLoanListOutputButtonResVO accountLoanListOutputButtonResVO = new AccountLoanListOutputButtonResVO();

        List<AccountLoan114DetailResVO>accountLoanInitVOList = new ArrayList<>();
        for (int i = 0; i < accountLoanListOutputButtonReqVO.getOutputList().size(); i++) {
            if (accountLoanListOutputButtonReqVO.getOutputList().get(i).getSelect() == null) {
                continue;
            }
            // 一覧選択したデータ
            if (accountLoanListOutputButtonReqVO.getOutputList().get(i).getSelect() == true) {
                AccountLoan114DetailResVO applicationDetailResVO = new AccountLoan114DetailResVO();
                AccountLoanDoc accountLoanDoc = new AccountLoanDoc();
                try {
                    // 帳票出力用データを取得
                    accountLoanDoc = (AccountLoanDoc) repositoryUtil.find(db,
                            accountLoanListOutputButtonReqVO.getOutputList().get(i).get_id(), AccountLoanDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
                    throw new BusinessException(messages);
                }
                // 更新前に、「受付」の場合、ステータスを更新、配信履歴を追加、PUSH通知を送信する
                if ("1".equals(accountLoanDoc.getStatus())) {
                    hyakujyushiPush(client, accountLoanDoc, "2");
                    accountLoanDoc.setStatus("2");
                }
                try {
                    repositoryUtil.update(db, accountLoanDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1004);
                    throw new BusinessException(messages);
                }

                // ユーザーID
                applicationDetailResVO.setUserId(accountLoanDoc.getUserId());
                // ユーザータイプ
                applicationDetailResVO.setUserType(accountLoanDoc.getUserType());
                // 受付番号
                applicationDetailResVO.setAccountAppSeq(accountLoanDoc.getLoanAppSeq());
                // ローン種類
                applicationDetailResVO.setLoanType(accountLoanDoc.getLoanType());
                // カードローン申し込み済フラグ(0：OFF、1：ON)
                applicationDetailResVO.setReLoadInfoFlg(accountLoanDoc.getReLoadInfoFlg());
                // 同意フラグ(0：同意しない、1：同意する)
                applicationDetailResVO.setAgreeFlg(accountLoanDoc.getAgreeFlg());
                // お借入限度額
                // お借入限度額
                if ("0".equals(accountLoanDoc.getLimitMoney())) {
                    applicationDetailResVO.setLimitMoney("30万円");
                } else if ("1".equals(accountLoanDoc.getLimitMoney())) {
                    applicationDetailResVO.setLimitMoney("50万円");
                } else if ("2".equals(accountLoanDoc.getLimitMoney())) {
                    applicationDetailResVO.setLimitMoney("100万円");
                } else if ("3".equals(accountLoanDoc.getLimitMoney())) {
                    applicationDetailResVO.setLimitMoney("200万円");
                } else if ("4".equals(accountLoanDoc.getLimitMoney())) {
                    applicationDetailResVO.setLimitMoney("300万円");
                } else if ("5".equals(accountLoanDoc.getLimitMoney())) {
                    applicationDetailResVO.setLimitMoney("400万円");
                } else if ("6".equals(accountLoanDoc.getLimitMoney())) {
                    applicationDetailResVO.setLimitMoney("500万円");
                }
                // ダイレクトメール
                applicationDetailResVO.setDairekutoMail(accountLoanDoc.getDairekutoMail());
                applicationDetailResVO.setLoanAppTime(accountLoanDoc.getLoanAppTime());
                // 運転免許証画像の受付番号
                applicationDetailResVO.setDriverLicenseSeq(accountLoanDoc.getDriverLicenseSeq());
                if ("0".equals(accountLoanDoc.getLoanType())) {
                    applicationDetailResVO.setLoanType("ニューカードローン");
                    applicationDetailResVO.setLoanFlag("0");
                } else if ("1".equals(accountLoanDoc.getLoanType())) {
                    applicationDetailResVO.setLoanType("マイカーローン");
                    applicationDetailResVO.setLoanFlag("1");
                } else if ("2".equals(accountLoanDoc.getLoanType())) {
                    applicationDetailResVO.setLoanType("教育ローン");
                    applicationDetailResVO.setLoanFlag("1");
                } else if ("3".equals(accountLoanDoc.getLoanType())) {
                    applicationDetailResVO.setLoanType("フリーローン");
                    applicationDetailResVO.setLoanFlag("1");
                }

                applicationDetailResVO.setName(encryptorUtil.decrypt(accountLoanDoc.getFirstName())
                        + encryptorUtil.decrypt(accountLoanDoc.getLastName()));
                // ガナ姓名
                applicationDetailResVO.setKanaName(encryptorUtil.decrypt(accountLoanDoc.getFirstKanaName())
                        + encryptorUtil.decrypt(accountLoanDoc.getLastKanaName()));
                // // お名前_フリガナの名
                // applicationDetailResVO.setFirstKanaName(encryptorUtil.decrypt(accountLoanDoc.getFirstKanaName()));
                // // お名前_フリガナの姓
                // applicationDetailResVO.setLastKanaName(encryptorUtil.decrypt(accountLoanDoc.getLastKanaName()));
                // 和暦年号
                applicationDetailResVO.setYearType(accountLoanDoc.getYearType());
                // 和暦生年月日
                String eraDate = "";
                String day = encryptorUtil.decrypt(accountLoanDoc.getEraBirthday());
                eraDate = day.substring(0, 2) + "年" + day.substring(2, 4) + "月" + day.substring(4, 6) + "日";
                applicationDetailResVO.setEraBirthday(eraDate);

                // 生年月日
                applicationDetailResVO.setBirthday(encryptorUtil.decrypt(accountLoanDoc.getBirthday()));
                String sexKbn = "";
                if ("1".equals(accountLoanDoc.getSexKbn())) {
                    sexKbn = "男";
                } else {
                    sexKbn = "女";
                }
                applicationDetailResVO.setSexKbn(sexKbn);
                // 年齢
                applicationDetailResVO.setAge(accountLoanDoc.getAge());
                // 国籍
                applicationDetailResVO.setCountry(accountLoanDoc.getCountry());
                String country = "";
                if (!"1".equals(accountLoanDoc.getCountry())) {
                    country = "日本国籍以外";
                } else {
                    country = "日本";
                }
                applicationDetailResVO.setCountry(country);
                // 運転免許番号フラグ
                applicationDetailResVO.setDriverLicenseFlg(accountLoanDoc.getDriverLicenseFlg());
                // 運転免許番号
                applicationDetailResVO.setDriverLicenseNo(encryptorUtil.decrypt(accountLoanDoc.getDriverLicenseNo()));
                // 郵便番号
                applicationDetailResVO.setPostCode(encryptorUtil.decrypt(accountLoanDoc.getPostCode()));
                // ご自宅住所_都道府県
                applicationDetailResVO.setPrefecturesCode(encryptorUtil.decrypt(accountLoanDoc.getPrefecturesCode()));
                // ご自宅住所_住所
                applicationDetailResVO.setAddress(encryptorUtil.decrypt(accountLoanDoc.getAddress()));
                // ご自宅住所_以降ご住所
                applicationDetailResVO.setOtherAddress(encryptorUtil.decrypt(accountLoanDoc.getOtherAddress()));
                // お電話番号_自宅
                applicationDetailResVO.setTeleNumber(encryptorUtil.decrypt(accountLoanDoc.getTeleNumber()));
                // お電話番号_携帯・PHS
                applicationDetailResVO.setPhoneNumber(encryptorUtil.decrypt(accountLoanDoc.getPhoneNumber()));
                // お住まい
                applicationDetailResVO.setEnAddress(accountLoanDoc.getEnAddress());
                // 居住年数_年
                applicationDetailResVO.setLiveYear(encryptorUtil.decrypt(accountLoanDoc.getLiveYear()));
                // 居住年数_ヶ月
                applicationDetailResVO.setLiveMonth(encryptorUtil.decrypt(accountLoanDoc.getLiveMonth()));
                // 家賃（住宅ローン）_毎月（円）
                applicationDetailResVO.setRentLoan(encryptorUtil.decrypt(accountLoanDoc.getRentLoan()));
                // ご家族_配偶者
                applicationDetailResVO.setSpouse(accountLoanDoc.getSpouse());
                // ご家族_居住形態

                if ("1".equals(accountLoanDoc.getLiveType())) {
                    applicationDetailResVO.setLiveType("同居");
                } else if ("2".equals(accountLoanDoc.getLiveType())) {
                    applicationDetailResVO.setLiveType("別居");
                }

                // ご家族_扶養家族（人）
                applicationDetailResVO.setFamilyNumber(encryptorUtil.decrypt(accountLoanDoc.getFamilyNumber()));
                // 承知フラグ
                applicationDetailResVO.setMasterKnowFlg(accountLoanDoc.getMasterKnowFlg());
                // ご職業
                applicationDetailResVO.setOccupation(accountLoanDoc.getOccupation());
                // 有無フラグ
                applicationDetailResVO.setSendFlg(accountLoanDoc.getSendFlg());
                // お勤め先名_漢字
                applicationDetailResVO.setCompanyName(encryptorUtil.decrypt(accountLoanDoc.getCompanyName()));
                // お勤め先名_フリガナ
                applicationDetailResVO.setCompanyKanaName(encryptorUtil.decrypt(accountLoanDoc.getCompanyKanaName()));
                // 業種
                applicationDetailResVO.setJobType(accountLoanDoc.getJobType());
                // お勤め先郵便番号
                applicationDetailResVO.setWorkPostCode(encryptorUtil.decrypt(accountLoanDoc.getWorkPostCode()));
                // お勤め先所在地_都道府県
                applicationDetailResVO
                        .setWorkPrefecturesCode(encryptorUtil.decrypt(accountLoanDoc.getWorkPrefecturesCode()));
                // お勤め先所在地_住所
                applicationDetailResVO.setWorkAddress(encryptorUtil.decrypt(accountLoanDoc.getWorkAddress()));
                // お勤め先所在地_以降住所
                applicationDetailResVO.setWorkOtherAddress(encryptorUtil.decrypt(accountLoanDoc.getWorkOtherAddress()));
                // お勤め先電話番号_代表電話
                applicationDetailResVO.setWorkTeleNumber(encryptorUtil.decrypt(accountLoanDoc.getWorkTeleNumber()));
                // お勤め先電話番号_所属部直通
                applicationDetailResVO.setWorkPhoneNumber(encryptorUtil.decrypt(accountLoanDoc.getWorkPhoneNumber()));
                // 所得区分フラグ
                applicationDetailResVO.setGetKbnFlg(accountLoanDoc.getGetKbnFlg());
                // 所得区分
                applicationDetailResVO.setGetKbn(encryptorUtil.decrypt(accountLoanDoc.getGetKbn()));
                // 事業内容
                applicationDetailResVO.setJobContent(accountLoanDoc.getJobContent());
                // 役職
                applicationDetailResVO.setPosition(accountLoanDoc.getPosition());
                // 従業員数
                applicationDetailResVO.setJobNumber(encryptorUtil.decrypt(accountLoanDoc.getJobNumber()));
                // 勤続（営）年数_年
                applicationDetailResVO.setWorkYear(encryptorUtil.decrypt(accountLoanDoc.getWorkYear()));
                // 勤続（営）年数_ヶ月
                applicationDetailResVO.setWorkMonth(encryptorUtil.decrypt(accountLoanDoc.getWorkMonth()));
                // 所属部課名
                applicationDetailResVO.setDepartment(encryptorUtil.decrypt(accountLoanDoc.getDepartment()));
                // 入社年月_和暦
                applicationDetailResVO.setWorkStartYearType(accountLoanDoc.getWorkStartYearType());
                // 入社年月
                applicationDetailResVO.setWorkYearMonth(encryptorUtil.decrypt(accountLoanDoc.getWorkYearMonth()));
                // 給料日_毎月（日）
                applicationDetailResVO.setPayment(encryptorUtil.decrypt(accountLoanDoc.getPayment()));
                // 最終学歴卒業年月
                applicationDetailResVO
                        .setGraduateYearMonth(encryptorUtil.decrypt(accountLoanDoc.getGraduateYearMonth()));
                // 最終学歴卒業年月_和暦
                applicationDetailResVO.setGraduateYearType(accountLoanDoc.getGraduateYearType());
                // 出向先会社名_漢字
                applicationDetailResVO.setVisitingName(encryptorUtil.decrypt(accountLoanDoc.getVisitingName()));
                // 出向先会社名_フリガナ
                applicationDetailResVO.setVisitingKanaName(encryptorUtil.decrypt(accountLoanDoc.getVisitingKanaName()));
                // 出向先所属部課名
                applicationDetailResVO
                        .setVisitingDepartment(encryptorUtil.decrypt(accountLoanDoc.getVisitingDepartment()));
                // 役職
                applicationDetailResVO.setVisitingPosition(accountLoanDoc.getVisitingPosition());
                // 出向先郵便番号
                applicationDetailResVO.setVisitingPostCode(encryptorUtil.decrypt(accountLoanDoc.getVisitingPostCode()));
                // 出向先所在地_都道府県
                applicationDetailResVO.setVisitPreCode(encryptorUtil.decrypt(accountLoanDoc.getVisitPreCode()));
                // 出向先所在地_住所
                applicationDetailResVO.setVisitAddress(encryptorUtil.decrypt(accountLoanDoc.getVisitAddress()));
                // 出向先所在地_以降住所
                applicationDetailResVO
                        .setVisitOtherAddress(encryptorUtil.decrypt(accountLoanDoc.getVisitOtherAddress()));
                // 出向先電話番号
                applicationDetailResVO.setVisitTelNumber(encryptorUtil.decrypt(accountLoanDoc.getVisitTelNumber()));
                // 年収/税込年収フラグ
                applicationDetailResVO.setIncomeFlg(accountLoanDoc.getIncomeFlg());
                // 年収/税込年収
                applicationDetailResVO.setIncomeYear(encryptorUtil.decrypt(accountLoanDoc.getIncomeYear()));
                // お仕事の内容
                applicationDetailResVO.setWorkContent(accountLoanDoc.getWorkContent());
                // 社員数
                applicationDetailResVO.setWorkNumber(accountLoanDoc.getWorkNumber());
                // 就業形態
                applicationDetailResVO.setWorkType(accountLoanDoc.getWorkType());
                // お勤め先の種類
                applicationDetailResVO.setWorkTypeCode(accountLoanDoc.getWorkTypeCode());
                // 資本金
                if ("1".equals(accountLoanDoc.getMoney())) {
                    applicationDetailResVO.setMoney("1千万円未満");
                } else if ("2".equals(accountLoanDoc.getMoney())) {
                    applicationDetailResVO.setMoney("1千万円以上");
                } else if ("3".equals(accountLoanDoc.getMoney())) {
                    applicationDetailResVO.setMoney("3千万円以上");
                } else if ("4".equals(accountLoanDoc.getMoney())) {
                    applicationDetailResVO.setMoney("5千万円以上");
                } else if ("5".equals(accountLoanDoc.getMoney())) {
                    applicationDetailResVO.setMoney("1億円以上");
                } else if ("6".equals(accountLoanDoc.getMoney())) {
                    applicationDetailResVO.setMoney("5億円以上");
                }
                // 住宅ローン契約（当行）
                applicationDetailResVO.setRentLoanContract(accountLoanDoc.getRentLoanContract());
                // 本社所在地
                applicationDetailResVO.setComPreCode(encryptorUtil.decrypt(accountLoanDoc.getComPreCode()));
                // 健康保険証の種類
                applicationDetailResVO.setHealthType(accountLoanDoc.getHealthType());
                // お申込金額_万円
                applicationDetailResVO.setApplicationMoney(encryptorUtil.decrypt(accountLoanDoc.getApplicationMoney()));
                // お借入希望日_[平成] 年
                applicationDetailResVO.setGetHopeYearType(encryptorUtil.decrypt(accountLoanDoc.getGetHopeYearType()));
                // お借入希望日_月
                applicationDetailResVO.setGetHopeMonth(encryptorUtil.decrypt(accountLoanDoc.getGetHopeMonth()));
                // お借入希望日_日
                applicationDetailResVO.setGetHopeDay(encryptorUtil.decrypt(accountLoanDoc.getGetHopeDay()));
                // 毎月返済希望額_円
                applicationDetailResVO.setReturnHopeMonth(encryptorUtil.decrypt(accountLoanDoc.getReturnHopeMonth()));
                // 返済希望回数_回
                applicationDetailResVO.setReturnHopeCount(encryptorUtil.decrypt(accountLoanDoc.getReturnHopeCount()));
                // 内据置回数_回
                applicationDetailResVO.setInCount(encryptorUtil.decrypt(accountLoanDoc.getInCount()));
                // ご利用目的フラグ
                applicationDetailResVO.setPurposeFlg(accountLoanDoc.getPurposeFlg());

                if ("1".equals(accountLoanDoc.getPurposeFlg())) {
                    accountLoanDoc.setOtherPurpose("生活費");
                } else if ("2".equals(accountLoanDoc.getPurposeFlg())) {
                    accountLoanDoc.setOtherPurpose("飲食費・交際費");
                } else if ("4".equals(accountLoanDoc.getPurposeFlg())) {
                    accountLoanDoc.setOtherPurpose("レジャー資金");
                } else if ("5".equals(accountLoanDoc.getPurposeFlg())) {
                    accountLoanDoc.setOtherPurpose("冠婚葬祭費");
                } else if ("6".equals(accountLoanDoc.getPurposeFlg())) {
                    accountLoanDoc.setOtherPurpose("入院・治療費");
                } else if ("7".equals(accountLoanDoc.getPurposeFlg())) {
                    accountLoanDoc.setOtherPurpose("教育資金");
                } else if ("8".equals(accountLoanDoc.getPurposeFlg())) {
                    accountLoanDoc.setOtherPurpose("借入金返済資金");
                } else if ("9".equals(accountLoanDoc.getPurposeFlg())) {
                    accountLoanDoc.setOtherPurpose("車の購入");
                } else {
                    accountLoanDoc.setOtherPurpose(accountLoanDoc.getOtherPurpose());
                }
                // ご利用目的_目的
                if (!"0".equals(accountLoanDoc.getLoanType())) {
                    applicationDetailResVO.setOtherPurpose(encryptorUtil.decrypt(accountLoanDoc.getPurpose()));
                }
                applicationDetailResVO.setStatus(accountLoanDoc.getStatus());
                // ご利用目的_所要資金総額（万円）
                applicationDetailResVO.setMoneyTotal(encryptorUtil.decrypt(accountLoanDoc.getMoneyTotal()));
                // ご利用目的_お支払先（ご購入先）①
                applicationDetailResVO.setPayMoney1(encryptorUtil.decrypt(accountLoanDoc.getPayMoney1()));
                // ご利用目的_金額①（万円）
                applicationDetailResVO.setMoney1(encryptorUtil.decrypt(accountLoanDoc.getMoney1()));
                // ご利用目的_お支払先（ご購入先）②
                applicationDetailResVO.setPayMoney2(encryptorUtil.decrypt(accountLoanDoc.getPayMoney2()));
                // ご利用目的_金額②（万円）
                applicationDetailResVO.setMoney2(encryptorUtil.decrypt(accountLoanDoc.getMoney2()));
                // 口座持ちフラグ
                applicationDetailResVO.setOwnAccountKbn(accountLoanDoc.getOwnAccountKbn());
                // 支店
                applicationDetailResVO.setStoreName(encryptorUtil.decrypt(accountLoanDoc.getStoreName()));
                // 支店フラグ
                applicationDetailResVO.setStoreNameFlg(accountLoanDoc.getStoreNameFlg());
                // お借入希望日
                applicationDetailResVO.setGetHopeDate(encryptorUtil.decrypt(accountLoanDoc.getGetHopeDay()));
                // 口座番号（普通）
                applicationDetailResVO.setAccountNumber(encryptorUtil.decrypt(accountLoanDoc.getAccountNumber()));
                // 返済併用フラグ
                if ("1".equals(accountLoanDoc.getIncreaseFlg())) {
                    applicationDetailResVO.setIncreaseFlg("はい");
                } else {
                    applicationDetailResVO.setIncreaseFlg("いいえ");
                }
                // 返済日
                applicationDetailResVO.setReturnDay(encryptorUtil.decrypt(accountLoanDoc.getReturnDay()));
                // 返済開始日
                applicationDetailResVO.setReturnStartDay(encryptorUtil.decrypt(accountLoanDoc.getReturnStartDay()));
                // 返済元金（万円）
                applicationDetailResVO.setReturnMoney(encryptorUtil.decrypt(accountLoanDoc.getReturnMoney()));
                // 返済月1
                applicationDetailResVO.setIncreaseReturn1(encryptorUtil.decrypt(accountLoanDoc.getIncreaseReturn1()));
                // 返済月2
                applicationDetailResVO.setIncreaseReturn2(encryptorUtil.decrypt(accountLoanDoc.getIncreaseReturn2()));
                // 返済開始日
                applicationDetailResVO.setReturnStartDay2(encryptorUtil.decrypt(accountLoanDoc.getReturnStartDay2()));
                // 返済元金（万円）
                applicationDetailResVO.setReturnMoney2(encryptorUtil.decrypt(accountLoanDoc.getReturnMoney2()));
                // 借入フラグ
                applicationDetailResVO.setGetFlg(accountLoanDoc.getGetFlg());
                // 件
                applicationDetailResVO.setGetCount(encryptorUtil.decrypt(accountLoanDoc.getGetCount()));
                // 万円
                applicationDetailResVO.setGetMoney(encryptorUtil.decrypt(accountLoanDoc.getGetMoney()));
                // 借入フラグ
                applicationDetailResVO.setGetFromOtherFlg(accountLoanDoc.getGetFromOtherFlg());
                // [無担保ローン] 残高（万円）
                applicationDetailResVO.setNoLoanRest(encryptorUtil.decrypt(accountLoanDoc.getNoLoanRest()));
                // [無担保ローン] 年間返済額（万円）
                applicationDetailResVO
                        .setNoLoanReturnMoney(encryptorUtil.decrypt(accountLoanDoc.getNoLoanReturnMoney()));
                // [住宅ローン] 残高（万円）
                applicationDetailResVO.setLoanRest(encryptorUtil.decrypt(accountLoanDoc.getLoanRest()));
                // [住宅ローン] 年間返済額（万円）
                applicationDetailResVO.setLoanReturnMoney(encryptorUtil.decrypt(accountLoanDoc.getLoanReturnMoney()));
                // [カードローン] 残高（万円）
                applicationDetailResVO.setCardLoanRest(encryptorUtil.decrypt(accountLoanDoc.getCardLoanRest()));
                // [カードローン] 年間返済額（万円）
                applicationDetailResVO.setCardLoanRestRM(encryptorUtil.decrypt(accountLoanDoc.getCardLoanRestRM()));
                // [信販会社等その他] 残高（万円）
                applicationDetailResVO.setOtherComRest(encryptorUtil.decrypt(accountLoanDoc.getOtherComRest()));
                // [信販会社等その他] 年間返済額（万円）
                applicationDetailResVO
                        .setOtherComReturnMoney(encryptorUtil.decrypt(accountLoanDoc.getOtherComReturnMoney()));
                // お取引希望店
                applicationDetailResVO.setHopeStoreNmae(encryptorUtil.decrypt(accountLoanDoc.getHopeStoreNmae()));
                // お取引希望店フラグ
                applicationDetailResVO.setHopeStoreFlg(accountLoanDoc.getHopeStoreFlg());
                // 当行とのお取引
                if ("1".equals(accountLoanDoc.getBankAccount())) {
                    applicationDetailResVO.setBankAccount("はい");
                } else {
                    applicationDetailResVO.setBankAccount("いいえ");
                }
                if ("1".equals(accountLoanDoc.getOwnAccountKbn())) {
                    applicationDetailResVO.setOwnAccountKbn("はい");
                } else {
                    applicationDetailResVO.setOwnAccountKbn("いいえ");
                }
                // 受付番号
                applicationDetailResVO.setAccountAppSeq(accountLoanDoc.getLoanAppSeq());

                // ステータス
                applicationDetailResVO.setStatus(accountLoanDoc.getStatus());

                accountLoanInitVOList.add(applicationDetailResVO);
            }
        }
        accountLoanListOutputButtonResVO.setAccountLoanList(accountLoanInitVOList);
      
        return accountLoanListOutputButtonResVO;
    }

    /**
     * 114
     * 
     * PUSH通知を送信する。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param AccountAppDoc
     *            口座開設取得結果
     * @param status
     *            ステータス
     * 
     * @return void
     */
    public void hyakujyushiPush(CloudantClient client, AccountLoanDoc doc, String status) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        List<String> deviceId = new ArrayList<>();
        String userId = "";
        HyakujyushiUserInfoDoc userInfoDoc = new HyakujyushiUserInfoDoc();
        try {
            userInfoDoc = repositoryUtil.find(db, doc.getUserId(), HyakujyushiUserInfoDoc.class);
        } catch (Exception e) {

        }
        if (userInfoDoc != null) {
            userId = doc.getUserId();
            for (DeviceInfoDoc deviceInfoDoc : userInfoDoc.getDeviceInfoList())
                if (!deviceInfoDoc.getDeviceTokenId().isEmpty()) {
                    deviceId.add(encryptorUtil.decrypt(deviceInfoDoc.getDeviceTokenId()));
                }
        }
        String pushTile = "";
        String message = "";
        String loanName = "";
        if ("0".equals(doc.getLoanType())) {
            loanName = "ニューカードローン";
        } else if ("1".equals(doc.getLoanType())) {
            loanName = "マイカーローン";
        } else if ("2".equals(doc.getLoanType())) {
            loanName = "教育ローン";
        } else if ("3".equals(doc.getLoanType())) {
            loanName = "フリーローン";
        }
        String receiptDate = dateFormatJP(doc.getLoanAppTime());
        String applyInformation = Constants.HYAKUJYUSH_RECEIPT_DATE + receiptDate + Constants.HYAKUJYUSH_ACCOUNT_SEQ
                + doc.getLoanAppSeq() + Constants.HYAKUJYUSH_APPLY_KIND + loanName + "<br/><br/>";

        message = Constants.HYAKUJYUSH_PUSH_MESSAGE_STATUS_LOAN_2 + applyInformation
                + Constants.HYAKUJYUSH_PUSH_MESSAGE_ABOUT_LOAN;
        pushTile = Constants.HYAKUJYUSH_PUSH_MESSAGE_TITLE_LOAN_2;
        String messageHtml = Constants.HYAKUJYUSH_ALLPUSH_MESSAGE_HTML_START + message
                + Constants.HYAKUJYUSH_PUSH_MESSAGE_HTML_END;
        Date date = new Date();
        SimpleDateFormat dateFormart = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String pushDate = dateFormart.format(date);
        List<String> list = new ArrayList<String>();
        String messageCode = "";
        String pushrecorDetaldId = "";
        String pushrecordId = "";
        int sum = 0;

        String queryKey = doc.getLoanAppSeq();
        List<StatusModifyLoanDoc> statusModifyLoanList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_STATUSMODIFY_LOAN_STATUSMODIFY_LOAN, StatusModifyLoanDoc.class, queryKey);
        for (StatusModifyLoanDoc statusModifyLoanDoc : statusModifyLoanList) {
            PushrecordLoanDoc pushrecordLoanDoc = new PushrecordLoanDoc();
            try {
                pushrecordLoanDoc = repositoryUtil.find(db, statusModifyLoanDoc.getPushRecordOid(),
                        PushrecordLoanDoc.class);
            } catch (Exception e) {
            }
            if (pushrecordLoanDoc != null && !"".equals(pushrecordLoanDoc.get_id())) {
                sum = sum + 1;
            }
        }
        if (!deviceId.isEmpty()) {
            // PUSH通知を送信する
            messageCode = pushNotifications.sendMessage(pushTile, sum, list);
        }
        // 配信詳細DOC作成
        Account114PushDetailDoc account114PushDetailDoc = new Account114PushDetailDoc();
        account114PushDetailDoc.setDocType(Constants.PUSH_DETAIL_LOAN);
        account114PushDetailDoc.setPushMessage(messageHtml);
        try {
            pushrecorDetaldId = repositoryUtil.saveToResultId(db, account114PushDetailDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }
        // 配信履歴DOC作成
        PushrecordLoanDoc pushrecordLoanDoc = new PushrecordLoanDoc();
        // ユーザーID
        pushrecordLoanDoc.setUserId(userId);
        // 端末ID
        if (deviceId != null && deviceId.size() > 0) {
            pushrecordLoanDoc.setDeviceTokenId(deviceId.get(0));
        }
        // 配信日時
        pushrecordLoanDoc.setPushDate(pushDate);
        // 配信日時並び用
        pushrecordLoanDoc.setPushDateForSort(date.getTime());
        // プッシュ内容タイトル
        pushrecordLoanDoc.setPushTitle(pushTile);
        // プッシュ開封状況
        pushrecordLoanDoc.setPushStatus("1");
        // プッシュ詳細OID
        pushrecordLoanDoc.setPushDetailOid(pushrecorDetaldId);
        try {
            pushrecordId = repositoryUtil.saveToResultId(db, pushrecordLoanDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSH001_1003);
            throw new BusinessException(messages);
        }
        StatusModifyLoanDoc statusModifyLoanDoc = new StatusModifyLoanDoc();
        statusModifyLoanDoc.setAccountAppSeq(doc.getAccountAppSeq());
        statusModifyLoanDoc.setStatus(status);
        statusModifyLoanDoc.setStatusModifyDate(pushDate);
        statusModifyLoanDoc.setStatusModifyDateForSort(date.getTime());
        if (Constants.RETURN_OK.equals(messageCode)) {
            // ２：配信済
            statusModifyLoanDoc.setSendStatus("2");
        } else {
            if (!deviceId.isEmpty()) {
                // ４：承認済（配信失敗）
                statusModifyLoanDoc.setSendStatus("4");
            } else {
                // ３：承認済（配信不可）
                statusModifyLoanDoc.setSendStatus("3");
            }
        }
        statusModifyLoanDoc.setPushRecordOid(pushrecordId);
        try {
            repositoryUtil.save(db, statusModifyLoanDoc);
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1003);
            throw new BusinessException(messages);
        }
    }

    /**
     * date formartメソッド。
     * 
     * @param date
     *            処理前日付
     * @return date 処理後日付
     * @throws Exception
     */
    public String dateFormatJP(String dateInput) {
        String dateOutput = "";
        if (Utils.isNotNullAndEmpty(dateInput) && dateInput.length() > 7) {
            dateOutput = dateInput.substring(0, 4) + Constants.YEAR + dateInput.substring(5, 7) + Constants.MONTH_JP
                    + dateInput.substring(8, 10) + Constants.DAY + " " + dateInput.substring(11, 16);
        }

        return dateOutput;
    }

    /**
     * 和暦にフォーマットするメソッド。
     * 
     * @param inputdDate
     *            処理前日付
     * @return date 処理後和歴日付
     * @throws ParseException
     * @throws Exception
     */
    public String japaneseCalendar(String inputDate) throws ParseException {

        String dateOutput = "";
        if (Utils.isNotNullAndEmpty(inputDate) && inputDate.length() > 7) {
            dateOutput = inputDate.substring(0, 4) + "-" + inputDate.substring(4, 6) + "-" + inputDate.substring(6, 8);
        } else {
            return "";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Locale locale = new Locale("ja", "JP", "JP");
        DateFormat japaneseFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);

        return japaneseFormat.format(formatter.parse(dateOutput));
    }

}