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

@Service
public class PushRecordLoanDetailSelService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private EncryptorUtil decryptorUtil;
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
        AccountLoan114DetailResVO pushRecordAppDetailResVO = hyakujyushi(db, baseResVO);
        return pushRecordAppDetailResVO;
    }

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

        // push通知履歴一覧を取得
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNTLOAN_2 + accountAppDetailLog + ")", db);
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

        applicationDetailResVO.setLimitMoney(accountLoanDoc.getLimitMoney());
        // ダイレクトメール
        applicationDetailResVO.setDairekutoMail(accountLoanDoc.getDairekutoMail());
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

        applicationDetailResVO.setName(decryptorUtil.decrypt(accountLoanDoc.getFirstName())
                + decryptorUtil.decrypt(accountLoanDoc.getLastName()));
        // ガナ姓名
        applicationDetailResVO.setKanaName(decryptorUtil.decrypt(accountLoanDoc.getFirstKanaName())
                + decryptorUtil.decrypt(accountLoanDoc.getLastKanaName()));
        // // お名前_フリガナの名
        // applicationDetailResVO.setFirstKanaName(decryptorUtil.decrypt(accountLoanDoc.getFirstKanaName()));
        // // お名前_フリガナの姓
        // applicationDetailResVO.setLastKanaName(decryptorUtil.decrypt(accountLoanDoc.getLastKanaName()));
     // 和暦年号
        applicationDetailResVO.setYearType(accountLoanDoc.getYearType());
        // 和暦生年月日
        String eraDate = "";
        String day = decryptorUtil.decrypt(accountLoanDoc.getEraBirthday());
        eraDate = day.substring(0, 2) + "年" + day.substring(2, 4) + "月" + day.substring(4, 6) + "日";
        applicationDetailResVO.setEraBirthday(eraDate);

        // 生年月日
        applicationDetailResVO.setBirthday(decryptorUtil.decrypt(accountLoanDoc.getBirthday()));
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
        applicationDetailResVO.setDriverLicenseNo(decryptorUtil.decrypt(accountLoanDoc.getDriverLicenseNo()));
        // 郵便番号
        applicationDetailResVO.setPostCode(decryptorUtil.decrypt(accountLoanDoc.getPostCode()));
        // ご自宅住所_都道府県
        applicationDetailResVO.setPrefecturesCode(decryptorUtil.decrypt(accountLoanDoc.getPrefecturesCode()));
        // ご自宅住所_住所
        applicationDetailResVO.setAddress(decryptorUtil.decrypt(accountLoanDoc.getAddress()));
        // ご自宅住所_以降ご住所
        applicationDetailResVO.setOtherAddress(decryptorUtil.decrypt(accountLoanDoc.getOtherAddress()));
        // お電話番号_自宅
        applicationDetailResVO.setTeleNumber(decryptorUtil.decrypt(accountLoanDoc.getTeleNumber()));
        // お電話番号_携帯・PHS
        applicationDetailResVO.setPhoneNumber(decryptorUtil.decrypt(accountLoanDoc.getPhoneNumber()));
        // お住まい
        applicationDetailResVO.setEnAddress(accountLoanDoc.getEnAddress());
        // 居住年数_年
        applicationDetailResVO.setLiveYear(decryptorUtil.decrypt(accountLoanDoc.getLiveYear()));
        // 居住年数_ヶ月
        applicationDetailResVO.setLiveMonth(decryptorUtil.decrypt(accountLoanDoc.getLiveMonth()));
        // 家賃（住宅ローン）_毎月（円）
        applicationDetailResVO.setRentLoan(decryptorUtil.decrypt(accountLoanDoc.getRentLoan()));
        // ご家族_配偶者
        applicationDetailResVO.setSpouse(accountLoanDoc.getSpouse());
        // ご家族_居住形態

        if ("1".equals(accountLoanDoc.getLiveType())) {
            applicationDetailResVO.setLiveType("同居");
        } else if ("2".equals(accountLoanDoc.getLiveType())) {
            applicationDetailResVO.setLiveType("別居");
        }
        // お借入希望日
        applicationDetailResVO.setGetHopeDate(decryptorUtil.decrypt(accountLoanDoc.getGetHopeDay()));
        // ご家族_扶養家族（人）
        applicationDetailResVO.setFamilyNumber(decryptorUtil.decrypt(accountLoanDoc.getFamilyNumber()));
        // 承知フラグ
        applicationDetailResVO.setMasterKnowFlg(accountLoanDoc.getMasterKnowFlg());
        // ご職業
        applicationDetailResVO.setOccupation(accountLoanDoc.getOccupation());
        // 有無フラグ
        applicationDetailResVO.setSendFlg(accountLoanDoc.getSendFlg());
        // お勤め先名_漢字
        applicationDetailResVO.setCompanyName(decryptorUtil.decrypt(accountLoanDoc.getCompanyName()));
        // お勤め先名_フリガナ
        applicationDetailResVO.setCompanyKanaName(decryptorUtil.decrypt(accountLoanDoc.getCompanyKanaName()));
        // 業種
        applicationDetailResVO.setJobType(accountLoanDoc.getJobType());
        // お勤め先郵便番号
        applicationDetailResVO.setWorkPostCode(decryptorUtil.decrypt(accountLoanDoc.getWorkPostCode()));
        // お勤め先所在地_都道府県
        applicationDetailResVO.setWorkPrefecturesCode(decryptorUtil.decrypt(accountLoanDoc.getWorkPrefecturesCode()));
        // お勤め先所在地_住所
        applicationDetailResVO.setWorkAddress(decryptorUtil.decrypt(accountLoanDoc.getWorkAddress()));
        // お勤め先所在地_以降住所
        applicationDetailResVO.setWorkOtherAddress(decryptorUtil.decrypt(accountLoanDoc.getWorkOtherAddress()));
        // お勤め先電話番号_代表電話
        applicationDetailResVO.setWorkTeleNumber(decryptorUtil.decrypt(accountLoanDoc.getWorkTeleNumber()));
        // お勤め先電話番号_所属部直通
        applicationDetailResVO.setWorkPhoneNumber(decryptorUtil.decrypt(accountLoanDoc.getWorkPhoneNumber()));
        // 所得区分フラグ
        applicationDetailResVO.setGetKbnFlg(accountLoanDoc.getGetKbnFlg());
        // 所得区分
        applicationDetailResVO.setGetKbn(decryptorUtil.decrypt(accountLoanDoc.getGetKbn()));
        // 事業内容
        applicationDetailResVO.setJobContent(accountLoanDoc.getJobContent());
        // 役職
        applicationDetailResVO.setPosition(accountLoanDoc.getPosition());
        // 従業員数
        applicationDetailResVO.setJobNumber(decryptorUtil.decrypt(accountLoanDoc.getJobNumber()));
        // 勤続（営）年数_年
        applicationDetailResVO.setWorkYear(decryptorUtil.decrypt(accountLoanDoc.getWorkYear()));
        // 勤続（営）年数_ヶ月
        applicationDetailResVO.setWorkMonth(decryptorUtil.decrypt(accountLoanDoc.getWorkMonth()));
        // 所属部課名
        applicationDetailResVO.setDepartment(decryptorUtil.decrypt(accountLoanDoc.getDepartment()));
        // 入社年月_和暦
        applicationDetailResVO.setWorkStartYearType(accountLoanDoc.getWorkStartYearType());
        // 入社年月
        applicationDetailResVO.setWorkYearMonth(decryptorUtil.decrypt(accountLoanDoc.getWorkYearMonth()));
        // 給料日_毎月（日）
        applicationDetailResVO.setPayment(decryptorUtil.decrypt(accountLoanDoc.getPayment()));
        // 最終学歴卒業年月
        applicationDetailResVO.setGraduateYearMonth(decryptorUtil.decrypt(accountLoanDoc.getGraduateYearMonth()));
        // 最終学歴卒業年月_和暦
        applicationDetailResVO.setGraduateYearType(accountLoanDoc.getGraduateYearType());
        // 出向先会社名_漢字
        applicationDetailResVO.setVisitingName(decryptorUtil.decrypt(accountLoanDoc.getVisitingName()));
        // 出向先会社名_フリガナ
        applicationDetailResVO.setVisitingKanaName(decryptorUtil.decrypt(accountLoanDoc.getVisitingKanaName()));
        // 出向先所属部課名
        applicationDetailResVO.setVisitingDepartment(decryptorUtil.decrypt(accountLoanDoc.getVisitingDepartment()));
        // 役職
        applicationDetailResVO.setVisitingPosition(accountLoanDoc.getVisitingPosition());
        // 出向先郵便番号
        applicationDetailResVO.setVisitingPostCode(decryptorUtil.decrypt(accountLoanDoc.getVisitingPostCode()));
        // 出向先所在地_都道府県
        applicationDetailResVO.setVisitPreCode(decryptorUtil.decrypt(accountLoanDoc.getVisitPreCode()));
        // 出向先所在地_住所
        applicationDetailResVO.setVisitAddress(decryptorUtil.decrypt(accountLoanDoc.getVisitAddress()));
        // 出向先所在地_以降住所
        applicationDetailResVO.setVisitOtherAddress(decryptorUtil.decrypt(accountLoanDoc.getVisitOtherAddress()));
        // 出向先電話番号
        applicationDetailResVO.setVisitTelNumber(decryptorUtil.decrypt(accountLoanDoc.getVisitTelNumber()));
        // 年収/税込年収フラグ
        applicationDetailResVO.setIncomeFlg(accountLoanDoc.getIncomeFlg());
        // 年収/税込年収
        applicationDetailResVO.setIncomeYear(decryptorUtil.decrypt(accountLoanDoc.getIncomeYear()));
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
        applicationDetailResVO.setLoanAppTime(accountLoanDoc.getLoanAppTime());
        // 住宅ローン契約（当行）
        applicationDetailResVO.setRentLoanContract(accountLoanDoc.getRentLoanContract());
        // 本社所在地
        applicationDetailResVO.setComPreCode(decryptorUtil.decrypt(accountLoanDoc.getComPreCode()));
        // 健康保険証の種類
        applicationDetailResVO.setHealthType(accountLoanDoc.getHealthType());
        // お申込金額_万円
        applicationDetailResVO.setApplicationMoney(decryptorUtil.decrypt(accountLoanDoc.getApplicationMoney()));
        // お借入希望日_[平成] 年
        applicationDetailResVO.setGetHopeYearType(decryptorUtil.decrypt(accountLoanDoc.getGetHopeYearType()));
        // お借入希望日_月
        applicationDetailResVO.setGetHopeMonth(decryptorUtil.decrypt(accountLoanDoc.getGetHopeMonth()));
        // お借入希望日_日
        applicationDetailResVO.setGetHopeDay(decryptorUtil.decrypt(accountLoanDoc.getGetHopeDay()));
        // 毎月返済希望額_円
        applicationDetailResVO.setReturnHopeMonth(decryptorUtil.decrypt(accountLoanDoc.getReturnHopeMonth()));
        // 返済希望回数_回
        applicationDetailResVO.setReturnHopeCount(decryptorUtil.decrypt(accountLoanDoc.getReturnHopeCount()));
        // 内据置回数_回
        applicationDetailResVO.setInCount(decryptorUtil.decrypt(accountLoanDoc.getInCount()));
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
            applicationDetailResVO.setOtherPurpose(decryptorUtil.decrypt(accountLoanDoc.getPurpose()));
        }
       
        // ご利用目的_所要資金総額（万円）
        applicationDetailResVO.setMoneyTotal(decryptorUtil.decrypt(accountLoanDoc.getMoneyTotal()));
        // ご利用目的_お支払先（ご購入先）①
        applicationDetailResVO.setPayMoney1(decryptorUtil.decrypt(accountLoanDoc.getPayMoney1()));
        // ご利用目的_金額①（万円）
        applicationDetailResVO.setMoney1(decryptorUtil.decrypt(accountLoanDoc.getMoney1()));
        // ご利用目的_お支払先（ご購入先）②
        applicationDetailResVO.setPayMoney2(decryptorUtil.decrypt(accountLoanDoc.getPayMoney2()));
        // ご利用目的_金額②（万円）
        applicationDetailResVO.setMoney2(decryptorUtil.decrypt(accountLoanDoc.getMoney2()));
        // 口座持ちフラグ
        applicationDetailResVO.setOwnAccountKbn(accountLoanDoc.getOwnAccountKbn());
        // 支店
        applicationDetailResVO.setStoreName(decryptorUtil.decrypt(accountLoanDoc.getStoreName()));
        // 支店フラグ
        applicationDetailResVO.setStoreNameFlg(accountLoanDoc.getStoreNameFlg());
        // 口座番号（普通）
        applicationDetailResVO.setAccountNumber(decryptorUtil.decrypt(accountLoanDoc.getAccountNumber()));
        // 返済併用フラグ
        if ("1".equals(accountLoanDoc.getIncreaseFlg())) {
            applicationDetailResVO.setIncreaseFlg("はい");
        } else {
            applicationDetailResVO.setIncreaseFlg("いいえ");
        }
        // 返済日
        applicationDetailResVO.setReturnDay(decryptorUtil.decrypt(accountLoanDoc.getReturnDay()));
        // 返済開始日
        applicationDetailResVO.setReturnStartDay(decryptorUtil.decrypt(accountLoanDoc.getReturnStartDay()));
        // 返済元金（万円）
        applicationDetailResVO.setReturnMoney(decryptorUtil.decrypt(accountLoanDoc.getReturnMoney()));
        // 返済月1
        applicationDetailResVO.setIncreaseReturn1(decryptorUtil.decrypt(accountLoanDoc.getIncreaseReturn1()));
        // 返済月2
        applicationDetailResVO.setIncreaseReturn2(decryptorUtil.decrypt(accountLoanDoc.getIncreaseReturn2()));
        // 返済開始日
        applicationDetailResVO.setReturnStartDay2(decryptorUtil.decrypt(accountLoanDoc.getReturnStartDay2()));
        // 返済元金（万円）
        applicationDetailResVO.setReturnMoney2(decryptorUtil.decrypt(accountLoanDoc.getReturnMoney2()));
        // 借入フラグ
        applicationDetailResVO.setGetFlg(accountLoanDoc.getGetFlg());
        // 件
        applicationDetailResVO.setGetCount(decryptorUtil.decrypt(accountLoanDoc.getGetCount()));
        // 万円
        applicationDetailResVO.setGetMoney(decryptorUtil.decrypt(accountLoanDoc.getGetMoney()));
        // 借入フラグ
        applicationDetailResVO.setGetFromOtherFlg(accountLoanDoc.getGetFromOtherFlg());
        // [無担保ローン] 残高（万円）
        applicationDetailResVO.setNoLoanRest(decryptorUtil.decrypt(accountLoanDoc.getNoLoanRest()));
        // [無担保ローン] 年間返済額（万円）
        applicationDetailResVO.setNoLoanReturnMoney(decryptorUtil.decrypt(accountLoanDoc.getNoLoanReturnMoney()));
        // [住宅ローン] 残高（万円）
        applicationDetailResVO.setLoanRest(decryptorUtil.decrypt(accountLoanDoc.getLoanRest()));
        // [住宅ローン] 年間返済額（万円）
        applicationDetailResVO.setLoanReturnMoney(decryptorUtil.decrypt(accountLoanDoc.getLoanReturnMoney()));
        // [カードローン] 残高（万円）
        applicationDetailResVO.setCardLoanRest(decryptorUtil.decrypt(accountLoanDoc.getCardLoanRest()));
        // [カードローン] 年間返済額（万円）
        applicationDetailResVO.setCardLoanRestRM(decryptorUtil.decrypt(accountLoanDoc.getCardLoanRestRM()));
        // [信販会社等その他] 残高（万円）
        applicationDetailResVO.setOtherComRest(decryptorUtil.decrypt(accountLoanDoc.getOtherComRest()));
        // [信販会社等その他] 年間返済額（万円）
        applicationDetailResVO.setOtherComReturnMoney(decryptorUtil.decrypt(accountLoanDoc.getOtherComReturnMoney()));
        // お取引希望店
        applicationDetailResVO.setHopeStoreNmae(decryptorUtil.decrypt(accountLoanDoc.getHopeStoreNmae()));
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
        applicationDetailResVO.setStatus(accountLoanDoc.getStatus());
        // push通知履歴一覧を取得
        YamagataStatusModifyReqVO yamagataStatusModifyReqVO = new YamagataStatusModifyReqVO();
        yamagataStatusModifyReqVO.setAccountAppSeq(accountLoanDoc.getLoanAppSeq());
        yamagataStatusModifyReqVO.setUserId(accountLoanDoc.getUserId());
        YamagataStatusModifyResVO yamagataStatusModifyResVO = accountLoanStatusModifyService
                .execute(yamagataStatusModifyReqVO);
        applicationDetailResVO.setYamagataStatusModifyList(yamagataStatusModifyResVO.getYamagataStatusModifyListVO());
        applicationDetailResVO.setYamagataStatusModifyList(yamagataStatusModifyResVO.getYamagataStatusModifyListVO());
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_5 + accountAppDetailLog + ")", db);

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
