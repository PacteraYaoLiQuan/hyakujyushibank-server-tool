package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.AccountLoanDoc;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.request.vo.YamagataStatusModifyReqVO;
import com.scsk.response.vo.AccountLoan114DetailResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.response.vo.YamagataStatusModifyResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;

/**
 * ローン申込詳細検索サービス。<br>
 * <br>
 * ローン申込詳細検索を実装するロジック。<br>
 * ローン申込詳細を更新するロジック。<br>
 */
@Service
public class AccountLoanDetailSelService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;
    @Autowired
    private AccountLoanStatusModifyService accountLoanStatusModifyService;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(BaseResVO detailReqVO) throws Exception {

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
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {

        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        AccountLoan114DetailResVO applicationDetailResVO = hyakujyushi(db, baseResVO);
        return applicationDetailResVO;

    }

    // 114
    private AccountLoan114DetailResVO hyakujyushi(Database db, BaseResVO baseResVO) throws Exception {

        AccountLoan114DetailResVO applicationDetailResVO = new AccountLoan114DetailResVO();
        AccountAppDetailReqVO reqVO = new AccountAppDetailReqVO();
        reqVO = (AccountAppDetailReqVO) baseResVO;
        String accountAppDetailLog = "(受付番号：";
        // 申込詳細情報取得
        AccountLoanDoc accountLoanDoc = new AccountLoanDoc();
        try {
            accountLoanDoc = (AccountLoanDoc) repositoryUtil.find(db, reqVO.get_id(), AccountLoanDoc.class);
            accountAppDetailLog = accountAppDetailLog + accountLoanDoc.getLoanAppSeq();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
            throw new BusinessException(messages);
        }

        // ユーザーID
        applicationDetailResVO.setUserId(accountLoanDoc.getUserId());
        applicationDetailResVO.setDocType(accountLoanDoc.getDocType());
        // ユーザータイプ
        applicationDetailResVO.setUserType(accountLoanDoc.getUserType());
        // 受付番号
//        applicationDetailResVO.setAccountAppSeq();
        applicationDetailResVO.setLoanAppSeq(accountLoanDoc.getLoanAppSeq());
        applicationDetailResVO.setLoanAppTime(accountLoanDoc.getLoanAppTime());
        applicationDetailResVO.setStatus(accountLoanDoc.getStatus());
        applicationDetailResVO.setLoanType(accountLoanDoc.getLoanType());
        applicationDetailResVO.setReLoadInfoFlg(accountLoanDoc.getReLoadInfoFlg());
        applicationDetailResVO.setAgreeTime(accountLoanDoc.getAgreeTime());
        applicationDetailResVO.setAgreeCheck(accountLoanDoc.getAgreeCheck());
        applicationDetailResVO.setLimitMoney(accountLoanDoc.getLimitMoney());
        applicationDetailResVO.setDairekutoMail(accountLoanDoc.getDairekutoMail());
        applicationDetailResVO.setDriverLicenseSeq(accountLoanDoc.getDriverLicenseSeq());
        applicationDetailResVO.setReadDriverLicenseNo(accountLoanDoc.getDriverLicenseNo());
        applicationDetailResVO.setReadFirstName(accountLoanDoc.getReadFirstName());
        applicationDetailResVO.setReadLastName(accountLoanDoc.getReadLastName());
        applicationDetailResVO.setReadBirthDay(accountLoanDoc.getReadBirthDay());
        applicationDetailResVO.setDriverLicenseSeq(accountLoanDoc.getDriverLicenseSeq());
        applicationDetailResVO.setLoanType(accountLoanDoc.getLoanType());
        applicationDetailResVO.setLastName(encryptorUtil.decrypt(accountLoanDoc.getLastName()));
        applicationDetailResVO.setFirstName(encryptorUtil.decrypt(accountLoanDoc.getFirstName()));
        applicationDetailResVO.setLastKanaName(encryptorUtil.decrypt(accountLoanDoc.getLastKanaName()));
        applicationDetailResVO.setFirstKanaName(encryptorUtil.decrypt(accountLoanDoc.getFirstKanaName()));
        applicationDetailResVO.setBirthday(encryptorUtil.decrypt(accountLoanDoc.getBirthday()));
        // 和暦年号
        applicationDetailResVO.setYearType(accountLoanDoc.getYearType());
        // 和暦生年月日
        applicationDetailResVO.setEraBirthday(encryptorUtil.decrypt(accountLoanDoc.getEraBirthday()));
        // 生年月日
        applicationDetailResVO.setBirthday(encryptorUtil.decrypt(accountLoanDoc.getBirthday()));
        applicationDetailResVO.setSexKbn(accountLoanDoc.getSexKbn());
        // 年齢
        applicationDetailResVO.setAge(accountLoanDoc.getAge());
        // 国籍
        applicationDetailResVO.setCountry(accountLoanDoc.getCountry());
        applicationDetailResVO.setCountry(accountLoanDoc.getCountry());
        // 運転免許番号フラグ
        applicationDetailResVO.setDriverLicenseFlg(accountLoanDoc.getDriverLicenseFlg());
        // 運転免許番号
        applicationDetailResVO.setDriverLicenseNo(encryptorUtil.decrypt(accountLoanDoc.getDriverLicenseNo()));
        applicationDetailResVO.setReadPostCode(accountLoanDoc.getReadPostCode());
        applicationDetailResVO.setReadPrefecturesCode(accountLoanDoc.getReadPrefecturesCode());
        applicationDetailResVO.setReadAddress(accountLoanDoc.getReadAddress());
        applicationDetailResVO.setReadOtherAddress(accountLoanDoc.getReadOtherAddress());
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
        applicationDetailResVO.setLiveType(accountLoanDoc.getLiveType());
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
        applicationDetailResVO.setWorkPrefecturesCode(encryptorUtil.decrypt(accountLoanDoc.getWorkPrefecturesCode()));
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
//        applicationDetailResVO.setJobContent(accountLoanDoc.getJobContent());
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
        applicationDetailResVO.setGraduateYearMonth(encryptorUtil.decrypt(accountLoanDoc.getGraduateYearMonth()));
        // 最終学歴卒業年月_和暦
        applicationDetailResVO.setGraduateYearType(accountLoanDoc.getGraduateYearType());
        // 出向先会社名_漢字
        applicationDetailResVO.setVisitingName(encryptorUtil.decrypt(accountLoanDoc.getVisitingName()));
        // 出向先会社名_フリガナ
        applicationDetailResVO.setVisitingKanaName(encryptorUtil.decrypt(accountLoanDoc.getVisitingKanaName()));
        // 出向先所属部課名
        applicationDetailResVO.setVisitingDepartment(encryptorUtil.decrypt(accountLoanDoc.getVisitingDepartment()));
        // 役職
        applicationDetailResVO.setVisitingPosition(accountLoanDoc.getVisitingPosition());
        // 出向先郵便番号
        applicationDetailResVO.setVisitingPostCode(encryptorUtil.decrypt(accountLoanDoc.getVisitingPostCode()));
        // 出向先所在地_都道府県
        applicationDetailResVO.setVisitPreCode(encryptorUtil.decrypt(accountLoanDoc.getVisitPreCode()));
        // 出向先所在地_住所
        applicationDetailResVO.setVisitAddress(encryptorUtil.decrypt(accountLoanDoc.getVisitAddress()));
        // 出向先所在地_以降住所
        applicationDetailResVO.setVisitOtherAddress(encryptorUtil.decrypt(accountLoanDoc.getVisitOtherAddress()));
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
        applicationDetailResVO.setMoney(accountLoanDoc.getMoney());
        // 住宅ローン契約（当行）
        applicationDetailResVO.setRentLoanContract(accountLoanDoc.getRentLoanContract());
        // 本社所在地
        applicationDetailResVO.setComPreCode(encryptorUtil.decrypt(accountLoanDoc.getComPreCode()));
        // 健康保険証の種類
        applicationDetailResVO.setHealthType(accountLoanDoc.getHealthType());
        // お申込金額_万円
        applicationDetailResVO.setApplicationMoney(encryptorUtil.decrypt(accountLoanDoc.getApplicationMoney()));
        applicationDetailResVO.setGetHopeDate(encryptorUtil.decrypt(accountLoanDoc.getGetHopeDate()));
        // 毎月返済希望額_円
        applicationDetailResVO.setReturnHopeMonth(encryptorUtil.decrypt(accountLoanDoc.getReturnHopeMonth()));
        // 返済希望回数_回
        applicationDetailResVO.setReturnHopeCount(encryptorUtil.decrypt(accountLoanDoc.getReturnHopeCount()));
        // 内据置回数_回
        applicationDetailResVO.setInCount(encryptorUtil.decrypt(accountLoanDoc.getInCount()));
        // ご利用目的フラグ
        applicationDetailResVO.setPurposeFlg(accountLoanDoc.getPurposeFlg());
        applicationDetailResVO.setOtherPurpose(accountLoanDoc.getOtherPurpose());
        applicationDetailResVO.setOtherPurpose(encryptorUtil.decrypt(accountLoanDoc.getPurpose()));
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
        applicationDetailResVO.setIncreaseFlg(accountLoanDoc.getIncreaseFlg());
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
        applicationDetailResVO.setNoLoanReturnMoney(encryptorUtil.decrypt(accountLoanDoc.getNoLoanReturnMoney()));
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
        applicationDetailResVO.setOtherComReturnMoney(encryptorUtil.decrypt(accountLoanDoc.getOtherComReturnMoney()));
        // [信販会社等その他] 残高（万円）
        applicationDetailResVO.setOtherComRest2(encryptorUtil.decrypt(accountLoanDoc.getOtherComRest2()));
        // [信販会社等その他] 年間返済額（万円）
        applicationDetailResVO.setOtherComReturnMoney2(encryptorUtil.decrypt(accountLoanDoc.getOtherComReturnMoney2()));
        // [信販会社等その他] 残高（万円）
        applicationDetailResVO.setOtherComRest3(encryptorUtil.decrypt(accountLoanDoc.getOtherComRest3()));
        // [信販会社等その他] 年間返済額（万円）
        applicationDetailResVO.setOtherComReturnMoney3(encryptorUtil.decrypt(accountLoanDoc.getOtherComReturnMoney3()));
        // [信販会社等その他] 残高（万円）
        applicationDetailResVO.setOtherComRest4(encryptorUtil.decrypt(accountLoanDoc.getOtherComRest4()));
        // [信販会社等その他] 年間返済額（万円）
        applicationDetailResVO.setOtherComReturnMoney4(encryptorUtil.decrypt(accountLoanDoc.getOtherComReturnMoney4()));
        // [信販会社等その他] 残高（万円）
        applicationDetailResVO.setOtherComRest5(encryptorUtil.decrypt(accountLoanDoc.getOtherComRest5()));
        // [信販会社等その他] 年間返済額（万円）
        applicationDetailResVO.setOtherComReturnMoney5(encryptorUtil.decrypt(accountLoanDoc.getOtherComReturnMoney5()));
        // [信販会社等その他] 残高（万円）
        applicationDetailResVO.setOtherComRest6(encryptorUtil.decrypt(accountLoanDoc.getOtherComRest6()));
        // [信販会社等その他] 年間返済額（万円）
        applicationDetailResVO.setOtherComReturnMoney6(encryptorUtil.decrypt(accountLoanDoc.getOtherComReturnMoney6()));
        // お取引希望店
        applicationDetailResVO.setHopeStoreNmae(encryptorUtil.decrypt(accountLoanDoc.getHopeStoreNmae()));
        // お取引希望店フラグ
        applicationDetailResVO.setHopeStoreFlg(accountLoanDoc.getHopeStoreFlg());
        // 当行とのお取引
        applicationDetailResVO.setBankAccount(accountLoanDoc.getBankAccount());
        // push通知履歴一覧を取得
        YamagataStatusModifyReqVO yamagataStatusModifyReqVO = new YamagataStatusModifyReqVO();
        yamagataStatusModifyReqVO.setAccountAppSeq(accountLoanDoc.getLoanAppSeq());
        yamagataStatusModifyReqVO.setUserId(accountLoanDoc.getUserId());
        YamagataStatusModifyResVO yamagataStatusModifyResVO = accountLoanStatusModifyService
                .execute(yamagataStatusModifyReqVO);

        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_5 + accountAppDetailLog + ")", db);
        applicationDetailResVO.setYamagataStatusModifyList(yamagataStatusModifyResVO.getYamagataStatusModifyListVO());
        return applicationDetailResVO;

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
}
