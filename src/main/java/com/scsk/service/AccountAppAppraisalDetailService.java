package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.Account114AppDoc;
import com.scsk.model.AccountAppDoc;
import com.scsk.model.AccountYamaGataAppDoc;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.response.vo.AccountApp114DetailResVO;
import com.scsk.response.vo.AccountAppDetailResVO;
import com.scsk.response.vo.AccountAppYamaGataDetailResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PhoneNumberUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.TacsflagVO;

/**
 * ＩＰアドレス鑑定/電話番号鑑定検索サービス。<br>
 * <br>
 * ＩＰアドレス鑑定/電話番号鑑定検索を実装するロジック。<br>
 */
@Service
public class AccountAppAppraisalDetailService extends AbstractBLogic<BaseResVO, BaseResVO> {

	@Autowired
	private EncryptorUtil encryptorUtil;
	@Value("${bank_cd}")
	private String bank_cd;

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
		if ("0169".equals(bank_cd)) {
			AccountAppDetailResVO accountAppDetailResVO = hirosima(db, baseResVO);
			return accountAppDetailResVO;
		} else if ("0122".equals(bank_cd)) {
			AccountAppYamaGataDetailResVO accountAppDetailResVO = yamagata(db, baseResVO);
			return accountAppDetailResVO;
		}else if ("0173".equals(bank_cd)) {
		    AccountApp114DetailResVO accountApp114DetailResVO = hyakuiyushi(db, baseResVO);
            return accountApp114DetailResVO;
        }
		return null;
	}

	private AccountApp114DetailResVO hyakuiyushi(Database db, BaseResVO baseResVO) throws Exception {
        AccountApp114DetailResVO applicationDetailResVO = new AccountApp114DetailResVO();
        AccountAppDetailReqVO accountAppDetailReqVO = new AccountAppDetailReqVO();
        accountAppDetailReqVO = (AccountAppDetailReqVO) baseResVO;
        // 申込詳細情報取得
        Account114AppDoc applicationDoc = new Account114AppDoc();
        try {
            applicationDoc = (Account114AppDoc) repositoryUtil.find(db, accountAppDetailReqVO.get_id(),
                    Account114AppDoc.class);

        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
            throw new BusinessException(messages);
        }
        // 戻り値を設定
        // 自宅電話番号
        if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getTeleNumber()))) {
            applicationDetailResVO
                    .setTeleNumber(PhoneNumberUtil.format(encryptorUtil.decrypt(applicationDoc.getTeleNumber())));
        } else {
            applicationDetailResVO.setTeleNumber("");
        }
        // 携帯電話番号
        if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getPhoneNumber()))) {
            applicationDetailResVO
                    .setPhoneNumber(PhoneNumberUtil.format(encryptorUtil.decrypt(applicationDoc.getPhoneNumber())));
        } else {
            applicationDetailResVO.setPhoneNumber("");
        }
        applicationDetailResVO.setAccountAppSeq(applicationDoc.getAccountAppSeq());
        applicationDetailResVO.setIpAddress(encryptorUtil.decrypt(applicationDoc.getIpAddress()));
        applicationDetailResVO.setIC_CompanyCity(applicationDoc.getIC_CompanyCity());
        applicationDetailResVO.setIC_CompanyDomain(applicationDoc.getIC_CompanyDomain());
        applicationDetailResVO.setIC_CompanyName(applicationDoc.getIC_CompanyName());
        applicationDetailResVO.setIC_CountryCode(applicationDoc.getIC_CountryCode());
        applicationDetailResVO.setIC_CountryName(applicationDoc.getIC_CountryName());
        applicationDetailResVO.setIC_CountryThreat(applicationDoc.getIC_CountryThreat());
        applicationDetailResVO.setIC_Distance(applicationDoc.getIC_Distance());
        applicationDetailResVO.setIC_IpThreat(applicationDoc.getIC_IpThreat());
        if (applicationDoc.getIC_isMobile().equals(Constants.PSIP_1)) {
            applicationDetailResVO.setIC_isMobile(applicationDoc.getIC_Carrier());
        } else if (applicationDoc.getIC_isMobile().equals(Constants.PSIP_2)) {
            applicationDetailResVO.setIC_isMobile(Constants.ISMOBILEVALUE);
        } else {
            applicationDetailResVO.setIC_isMobile("");
        }
        applicationDetailResVO.setIC_Proxy(applicationDoc.getIC_Proxy());
        applicationDetailResVO.setIC_PSIP(applicationDoc.getIC_PSIP());
        applicationDetailResVO.setTC_Access1(applicationDoc.getTC_Access1());
        applicationDetailResVO.setTC_Access2(applicationDoc.getTC_Access2());
        applicationDetailResVO.setTC_Attention1(applicationDoc.getTC_Attention1());
        applicationDetailResVO.setTC_Attention2(applicationDoc.getTC_Attention2());
        applicationDetailResVO.setTC_Carrier1(applicationDoc.getTC_Carrier1());
        applicationDetailResVO.setTC_Carrier2(applicationDoc.getTC_Carrier2());
        applicationDetailResVO.setTC_Count1(applicationDoc.getTC_Count1());
        applicationDetailResVO.setTC_Count2(applicationDoc.getTC_Count2());
        if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestyear1())
                && Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestmonth1())) {
            applicationDetailResVO
                    .setTC_LatestDate1(applicationDoc.getTC_Latestyear1() + "/" + applicationDoc.getTC_Latestmonth1());
        } else {
            applicationDetailResVO
                    .setTC_LatestDate1(applicationDoc.getTC_Latestyear1() + applicationDoc.getTC_Latestmonth1());
        }
        if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestyear2())
                && Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestmonth2())) {
            applicationDetailResVO
                    .setTC_LatestDate2(applicationDoc.getTC_Latestyear2() + "/" + applicationDoc.getTC_Latestmonth2());
        } else {
            applicationDetailResVO
                    .setTC_LatestDate2(applicationDoc.getTC_Latestyear2() + applicationDoc.getTC_Latestmonth2());
        }
        if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Month1())) {
            applicationDetailResVO.setTC_Month1(applicationDoc.getTC_Month1() + Constants.MONTH);
        }
        if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Month2())) {
            applicationDetailResVO.setTC_Month2(applicationDoc.getTC_Month2() + Constants.MONTH);
        }
        applicationDetailResVO.setTC_Movetel1(applicationDoc.getTC_Movetel1());
        applicationDetailResVO.setTC_Movetel2(applicationDoc.getTC_Movetel2());
        applicationDetailResVO.setTC_Result1(applicationDoc.getTC_Result1());
        applicationDetailResVO.setTC_Result2(applicationDoc.getTC_Result2());
        applicationDetailResVO.setTC_Tacsflag1(applicationDoc.getTC_Tacsflag1());
        applicationDetailResVO.setTC_Tacsflag2(applicationDoc.getTC_Tacsflag2());

        int twoWeeksCount = 0;
        if (Utils.isNotNullAndEmpty(applicationDoc.getIC_SearchHistory())) {
            String[] searchHistory = applicationDoc.getIC_SearchHistory().split(",");
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
        applicationDetailResVO.setIC_TwoWeeksCount(Integer.toString(twoWeeksCount));

        List<TacsflagVO> TacsflagList1 = new ArrayList<>();
        List<TacsflagVO> TacsflagList2 = new ArrayList<>();
        // 自宅電話番号利用ステータス履歴
        if (Utils.isNotNullAndEmpty(applicationDetailResVO.getTC_LatestDate1())) {
            TacsflagVO TacsflagData1 = new TacsflagVO();
            TacsflagData1.setDate(applicationDetailResVO.getTC_LatestDate1());
            TacsflagData1.setTacsflag(applicationDoc.getF01_1());
            TacsflagList1.add(TacsflagData1);
            TacsflagVO TacsflagData2 = new TacsflagVO();
            TacsflagData2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 1));
            TacsflagData2.setTacsflag(applicationDoc.getF02_1());
            TacsflagList1.add(TacsflagData2);
            TacsflagVO TacsflagData3 = new TacsflagVO();
            TacsflagData3.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 2));
            TacsflagData3.setTacsflag(applicationDoc.getF03_1());
            TacsflagList1.add(TacsflagData3);
            TacsflagVO TacsflagData4 = new TacsflagVO();
            TacsflagData4.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 3));
            TacsflagData4.setTacsflag(applicationDoc.getF04_1());
            TacsflagList1.add(TacsflagData4);
            TacsflagVO TacsflagData5 = new TacsflagVO();
            TacsflagData5.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 4));
            TacsflagData5.setTacsflag(applicationDoc.getF05_1());
            TacsflagList1.add(TacsflagData5);
            TacsflagVO TacsflagData6 = new TacsflagVO();
            TacsflagData6.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 5));
            TacsflagData6.setTacsflag(applicationDoc.getF06_1());
            TacsflagList1.add(TacsflagData6);
            TacsflagVO TacsflagData7 = new TacsflagVO();
            TacsflagData7.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 6));
            TacsflagData7.setTacsflag(applicationDoc.getF07_1());
            TacsflagList1.add(TacsflagData7);
            TacsflagVO TacsflagData8 = new TacsflagVO();
            TacsflagData8.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 7));
            TacsflagData8.setTacsflag(applicationDoc.getF08_1());
            TacsflagList1.add(TacsflagData8);
            TacsflagVO TacsflagData9 = new TacsflagVO();
            TacsflagData9.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 8));
            TacsflagData9.setTacsflag(applicationDoc.getF09_1());
            TacsflagList1.add(TacsflagData9);
            TacsflagVO TacsflagData10 = new TacsflagVO();
            TacsflagData10.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 9));
            TacsflagData10.setTacsflag(applicationDoc.getF10_1());
            TacsflagList1.add(TacsflagData10);
            TacsflagVO TacsflagData11 = new TacsflagVO();
            TacsflagData11.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 10));
            TacsflagData11.setTacsflag(applicationDoc.getF11_1());
            TacsflagList1.add(TacsflagData11);
            TacsflagVO TacsflagData12 = new TacsflagVO();
            TacsflagData12.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 11));
            TacsflagData12.setTacsflag(applicationDoc.getF12_1());
            TacsflagList1.add(TacsflagData12);
            TacsflagVO TacsflagData13 = new TacsflagVO();
            TacsflagData13.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 12));
            TacsflagData13.setTacsflag(applicationDoc.getF13_1());
            TacsflagList1.add(TacsflagData13);
            TacsflagVO TacsflagData14 = new TacsflagVO();
            TacsflagData14.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 13));
            TacsflagData14.setTacsflag(applicationDoc.getF14_1());
            TacsflagList1.add(TacsflagData14);
            TacsflagVO TacsflagData15 = new TacsflagVO();
            TacsflagData15.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 14));
            TacsflagData15.setTacsflag(applicationDoc.getF15_1());
            TacsflagList1.add(TacsflagData15);
            TacsflagVO TacsflagData16 = new TacsflagVO();
            TacsflagData16.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 15));
            TacsflagData16.setTacsflag(applicationDoc.getF16_1());
            TacsflagList1.add(TacsflagData16);
            TacsflagVO TacsflagData17 = new TacsflagVO();
            TacsflagData17.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 16));
            TacsflagData17.setTacsflag(applicationDoc.getF17_1());
            TacsflagList1.add(TacsflagData17);
            TacsflagVO TacsflagData18 = new TacsflagVO();
            TacsflagData18.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 17));
            TacsflagData18.setTacsflag(applicationDoc.getF18_1());
            TacsflagList1.add(TacsflagData18);
            TacsflagVO TacsflagData19 = new TacsflagVO();
            TacsflagData19.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 18));
            TacsflagData19.setTacsflag(applicationDoc.getF19_1());
            TacsflagList1.add(TacsflagData19);
            TacsflagVO TacsflagData20 = new TacsflagVO();
            TacsflagData20.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 19));
            TacsflagData20.setTacsflag(applicationDoc.getF20_1());
            TacsflagList1.add(TacsflagData20);
            TacsflagVO TacsflagData21 = new TacsflagVO();
            TacsflagData21.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 20));
            TacsflagData21.setTacsflag(applicationDoc.getF21_1());
            TacsflagList1.add(TacsflagData21);
            TacsflagVO TacsflagData22 = new TacsflagVO();
            TacsflagData22.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 21));
            TacsflagData22.setTacsflag(applicationDoc.getF22_1());
            TacsflagList1.add(TacsflagData22);
            TacsflagVO TacsflagData23 = new TacsflagVO();
            TacsflagData23.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 22));
            TacsflagData23.setTacsflag(applicationDoc.getF23_1());
            TacsflagList1.add(TacsflagData23);
            TacsflagVO TacsflagData24 = new TacsflagVO();
            TacsflagData24.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 23));
            TacsflagData24.setTacsflag(applicationDoc.getF24_1());
            TacsflagList1.add(TacsflagData24);
        }

        if (Utils.isNotNullAndEmpty(applicationDetailResVO.getTC_LatestDate2())) {
            // 携帯電話番号利用ステータス履歴
            TacsflagVO TacsflagData1_2 = new TacsflagVO();
            TacsflagData1_2.setDate(applicationDetailResVO.getTC_LatestDate2());
            TacsflagData1_2.setTacsflag(applicationDoc.getF01_2());
            TacsflagList2.add(TacsflagData1_2);
            TacsflagVO TacsflagData2_2 = new TacsflagVO();
            TacsflagData2_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 1));
            TacsflagData2_2.setTacsflag(applicationDoc.getF02_2());
            TacsflagList2.add(TacsflagData2_2);
            TacsflagVO TacsflagData3_2 = new TacsflagVO();
            TacsflagData3_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 2));
            TacsflagData3_2.setTacsflag(applicationDoc.getF03_2());
            TacsflagList2.add(TacsflagData3_2);
            TacsflagVO TacsflagData4_2 = new TacsflagVO();
            TacsflagData4_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 3));
            TacsflagData4_2.setTacsflag(applicationDoc.getF04_2());
            TacsflagList2.add(TacsflagData4_2);
            TacsflagVO TacsflagData5_2 = new TacsflagVO();
            TacsflagData5_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 4));
            TacsflagData5_2.setTacsflag(applicationDoc.getF05_2());
            TacsflagList2.add(TacsflagData5_2);
            TacsflagVO TacsflagData6_2 = new TacsflagVO();
            TacsflagData6_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 5));
            TacsflagData6_2.setTacsflag(applicationDoc.getF06_2());
            TacsflagList2.add(TacsflagData6_2);
            TacsflagVO TacsflagData7_2 = new TacsflagVO();
            TacsflagData7_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 6));
            TacsflagData7_2.setTacsflag(applicationDoc.getF07_2());
            TacsflagList2.add(TacsflagData7_2);
            TacsflagVO TacsflagData8_2 = new TacsflagVO();
            TacsflagData8_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 7));
            TacsflagData8_2.setTacsflag(applicationDoc.getF08_2());
            TacsflagList2.add(TacsflagData8_2);
            TacsflagVO TacsflagData9_2 = new TacsflagVO();
            TacsflagData9_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 8));
            TacsflagData9_2.setTacsflag(applicationDoc.getF09_2());
            TacsflagList2.add(TacsflagData9_2);
            TacsflagVO TacsflagData10_2 = new TacsflagVO();
            TacsflagData10_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 9));
            TacsflagData10_2.setTacsflag(applicationDoc.getF10_2());
            TacsflagList2.add(TacsflagData10_2);
            TacsflagVO TacsflagData11_2 = new TacsflagVO();
            TacsflagData11_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 10));
            TacsflagData11_2.setTacsflag(applicationDoc.getF11_2());
            TacsflagList2.add(TacsflagData11_2);
            TacsflagVO TacsflagData12_2 = new TacsflagVO();
            TacsflagData12_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 11));
            TacsflagData12_2.setTacsflag(applicationDoc.getF12_2());
            TacsflagList2.add(TacsflagData12_2);
            TacsflagVO TacsflagData13_2 = new TacsflagVO();
            TacsflagData13_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 12));
            TacsflagData13_2.setTacsflag(applicationDoc.getF13_2());
            TacsflagList2.add(TacsflagData13_2);
            TacsflagVO TacsflagData14_2 = new TacsflagVO();
            TacsflagData14_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 13));
            TacsflagData14_2.setTacsflag(applicationDoc.getF14_2());
            TacsflagList2.add(TacsflagData14_2);
            TacsflagVO TacsflagData15_2 = new TacsflagVO();
            TacsflagData15_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 14));
            TacsflagData15_2.setTacsflag(applicationDoc.getF15_2());
            TacsflagList2.add(TacsflagData15_2);
            TacsflagVO TacsflagData16_2 = new TacsflagVO();
            TacsflagData16_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 15));
            TacsflagData16_2.setTacsflag(applicationDoc.getF16_2());
            TacsflagList2.add(TacsflagData16_2);
            TacsflagVO TacsflagData17_2 = new TacsflagVO();
            TacsflagData17_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 16));
            TacsflagData17_2.setTacsflag(applicationDoc.getF17_2());
            TacsflagList2.add(TacsflagData17_2);
            TacsflagVO TacsflagData18_2 = new TacsflagVO();
            TacsflagData18_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 17));
            TacsflagData18_2.setTacsflag(applicationDoc.getF18_2());
            TacsflagList2.add(TacsflagData18_2);
            TacsflagVO TacsflagData19_2 = new TacsflagVO();
            TacsflagData19_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 18));
            TacsflagData19_2.setTacsflag(applicationDoc.getF19_2());
            TacsflagList2.add(TacsflagData19_2);
            TacsflagVO TacsflagData20_2 = new TacsflagVO();
            TacsflagData20_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 19));
            TacsflagData20_2.setTacsflag(applicationDoc.getF20_2());
            TacsflagList2.add(TacsflagData20_2);
            TacsflagVO TacsflagData21_2 = new TacsflagVO();
            TacsflagData21_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 20));
            TacsflagData21_2.setTacsflag(applicationDoc.getF21_2());
            TacsflagList2.add(TacsflagData21_2);
            TacsflagVO TacsflagData22_2 = new TacsflagVO();
            TacsflagData22_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 21));
            TacsflagData22_2.setTacsflag(applicationDoc.getF22_2());
            TacsflagList2.add(TacsflagData22_2);
            TacsflagVO TacsflagData23_2 = new TacsflagVO();
            TacsflagData23_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 22));
            TacsflagData23_2.setTacsflag(applicationDoc.getF23_2());
            TacsflagList2.add(TacsflagData23_2);
            TacsflagVO TacsflagData24_2 = new TacsflagVO();
            TacsflagData24_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 23));
            TacsflagData24_2.setTacsflag(applicationDoc.getF24_2());
            TacsflagList2.add(TacsflagData24_2);
        }

        applicationDetailResVO.setTacsflagList1(TacsflagList1);
        applicationDetailResVO.setTacsflagList2(TacsflagList2);

        // 電話番号鑑定結果
        int TC_Count1 = 0;
        int TC_Count2 = 0;
        if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count2())) {
            TC_Count2 = Integer.parseInt(applicationDoc.getTC_Count2());
        }
        if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count1())) {
            TC_Count1 = Integer.parseInt(applicationDoc.getTC_Count1());
        }
        boolean flag = false;
        if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getPhoneNumber()))
                && Utils.isNotNullAndEmpty(applicationDoc.getTC_Result2())) {
            // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
            // attention＝1or2or3orEorU or 検索結果 result=0以外
            if (TC_Count2 >= 2 || applicationDoc.getTC_Tacsflag2().equals("0")
                    || applicationDoc.getTC_Tacsflag2().equals("2") || applicationDoc.getTC_Tacsflag2().equals("3")
                    || applicationDoc.getTC_Tacsflag2().equals("4") || applicationDoc.getTC_Tacsflag2().equals("6")
                    || applicationDoc.getTC_Tacsflag2().equals("7") || applicationDoc.getTC_Attention2().equals("1")
                    || applicationDoc.getTC_Attention2().equals("2") || applicationDoc.getTC_Attention2().equals("3")
                    || applicationDoc.getTC_Attention2().equals("E") || applicationDoc.getTC_Attention2().equals("U")
                    || !applicationDoc.getTC_Result2().equals("0")) {
                // 要注意
                // accountAppInitVO.setAppraisalTelResult("1");
                flag = true;
            }
            // } else{
            // // 問題なし
            // accountAppInitVO.setAppraisalTelResult("2");
            // }
        }
        if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getTeleNumber()))
                && Utils.isNotNullAndEmpty(applicationDoc.getTC_Result1())) {
            // 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
            // attention＝1or2or3orEorU or 検索結果 result=0以外
            if (TC_Count1 >= 2 || applicationDoc.getTC_Tacsflag1().equals("0")
                    || applicationDoc.getTC_Tacsflag1().equals("2") || applicationDoc.getTC_Tacsflag1().equals("3")
                    || applicationDoc.getTC_Tacsflag1().equals("4") || applicationDoc.getTC_Tacsflag1().equals("6")
                    || applicationDoc.getTC_Tacsflag1().equals("7") || applicationDoc.getTC_Attention1().equals("1")
                    || applicationDoc.getTC_Attention1().equals("2") || applicationDoc.getTC_Attention1().equals("3")
                    || applicationDoc.getTC_Attention1().equals("E") || applicationDoc.getTC_Attention1().equals("U")
                    || !applicationDoc.getTC_Result1().equals("0")) {
                flag = true;
            }
        }
        if (flag) {
            // 要注意
            applicationDetailResVO.setAppraisalTelResult("1");
        } else {
            // 問題なし
            applicationDetailResVO.setAppraisalTelResult("2");
        }
        // ＩＰアドレス鑑定結果
        // 国脅威度＝high or PS-IP＝true or Proxy利用＝true or 2週間以内の検索回数＞＝2
        if (applicationDoc.getIC_CountryThreat().equals(Constants.THREAT_1)
                || applicationDoc.getIC_PSIP().equals(Constants.PSIP_1)
                || applicationDoc.getIC_Proxy().equals(Constants.PSIP_1) || twoWeeksCount >= 2) {
            // 要注意
            applicationDetailResVO.setAppraisalIPResult("1");
        } else {
            // 問題なし
            applicationDetailResVO.setAppraisalIPResult("2");
        }
        return applicationDetailResVO;
    }

    /**
	 * 
	 * 広島
	 * 
	 */
	public AccountAppDetailResVO hirosima(Database db, BaseResVO baseResVO) throws Exception {
		AccountAppDetailResVO applicationDetailResVO = new AccountAppDetailResVO();
		AccountAppDetailReqVO accountAppDetailReqVO = new AccountAppDetailReqVO();
		accountAppDetailReqVO = (AccountAppDetailReqVO) baseResVO;
		// 申込詳細情報取得
		AccountAppDoc applicationDoc = new AccountAppDoc();
		try {
			applicationDoc = (AccountAppDoc) repositoryUtil.find(db, accountAppDetailReqVO.get_id(),
					AccountAppDoc.class);

		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
			throw new BusinessException(messages);
		}
		// 戻り値を設定
		// 自宅電話番号
		if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getTeleNumber()))) {
			applicationDetailResVO
					.setTeleNumber(PhoneNumberUtil.format(encryptorUtil.decrypt(applicationDoc.getTeleNumber())));
		} else {
			applicationDetailResVO.setTeleNumber("");
		}
		// 携帯電話番号
		if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getPhoneNumber()))) {
			applicationDetailResVO
					.setPhoneNumber(PhoneNumberUtil.format(encryptorUtil.decrypt(applicationDoc.getPhoneNumber())));
		} else {
			applicationDetailResVO.setPhoneNumber("");
		}
		applicationDetailResVO.setAccountAppSeq(applicationDoc.getAccountAppSeq());
		applicationDetailResVO.setIpAddress(encryptorUtil.decrypt(applicationDoc.getIpAddress()));
		applicationDetailResVO.setIC_CompanyCity(applicationDoc.getIC_CompanyCity());
		applicationDetailResVO.setIC_CompanyDomain(applicationDoc.getIC_CompanyDomain());
		applicationDetailResVO.setIC_CompanyName(applicationDoc.getIC_CompanyName());
		applicationDetailResVO.setIC_CountryCode(applicationDoc.getIC_CountryCode());
		applicationDetailResVO.setIC_CountryName(applicationDoc.getIC_CountryName());
		applicationDetailResVO.setIC_CountryThreat(applicationDoc.getIC_CountryThreat());
		applicationDetailResVO.setIC_Distance(applicationDoc.getIC_Distance());
		applicationDetailResVO.setIC_IpThreat(applicationDoc.getIC_IpThreat());
		if (applicationDoc.getIC_isMobile().equals(Constants.PSIP_1)) {
			applicationDetailResVO.setIC_isMobile(applicationDoc.getIC_Carrier());
		} else if (applicationDoc.getIC_isMobile().equals(Constants.PSIP_2)) {
			applicationDetailResVO.setIC_isMobile(Constants.ISMOBILEVALUE);
		} else {
			applicationDetailResVO.setIC_isMobile("");
		}
		applicationDetailResVO.setIC_Proxy(applicationDoc.getIC_Proxy());
		applicationDetailResVO.setIC_PSIP(applicationDoc.getIC_PSIP());
		applicationDetailResVO.setTC_Access1(applicationDoc.getTC_Access1());
		applicationDetailResVO.setTC_Access2(applicationDoc.getTC_Access2());
		applicationDetailResVO.setTC_Attention1(applicationDoc.getTC_Attention1());
		applicationDetailResVO.setTC_Attention2(applicationDoc.getTC_Attention2());
		applicationDetailResVO.setTC_Carrier1(applicationDoc.getTC_Carrier1());
		applicationDetailResVO.setTC_Carrier2(applicationDoc.getTC_Carrier2());
		applicationDetailResVO.setTC_Count1(applicationDoc.getTC_Count1());
		applicationDetailResVO.setTC_Count2(applicationDoc.getTC_Count2());
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestyear1())
				&& Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestmonth1())) {
			applicationDetailResVO
					.setTC_LatestDate1(applicationDoc.getTC_Latestyear1() + "/" + applicationDoc.getTC_Latestmonth1());
		} else {
			applicationDetailResVO
					.setTC_LatestDate1(applicationDoc.getTC_Latestyear1() + applicationDoc.getTC_Latestmonth1());
		}
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestyear2())
				&& Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestmonth2())) {
			applicationDetailResVO
					.setTC_LatestDate2(applicationDoc.getTC_Latestyear2() + "/" + applicationDoc.getTC_Latestmonth2());
		} else {
			applicationDetailResVO
					.setTC_LatestDate2(applicationDoc.getTC_Latestyear2() + applicationDoc.getTC_Latestmonth2());
		}
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Month1())) {
			applicationDetailResVO.setTC_Month1(applicationDoc.getTC_Month1() + Constants.MONTH);
		}
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Month2())) {
			applicationDetailResVO.setTC_Month2(applicationDoc.getTC_Month2() + Constants.MONTH);
		}
		applicationDetailResVO.setTC_Movetel1(applicationDoc.getTC_Movetel1());
		applicationDetailResVO.setTC_Movetel2(applicationDoc.getTC_Movetel2());
		applicationDetailResVO.setTC_Result1(applicationDoc.getTC_Result1());
		applicationDetailResVO.setTC_Result2(applicationDoc.getTC_Result2());
		applicationDetailResVO.setTC_Tacsflag1(applicationDoc.getTC_Tacsflag1());
		applicationDetailResVO.setTC_Tacsflag2(applicationDoc.getTC_Tacsflag2());

		int twoWeeksCount = 0;
		if (Utils.isNotNullAndEmpty(applicationDoc.getIC_SearchHistory())) {
			String[] searchHistory = applicationDoc.getIC_SearchHistory().split(",");
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
		applicationDetailResVO.setIC_TwoWeeksCount(Integer.toString(twoWeeksCount));

		List<TacsflagVO> TacsflagList1 = new ArrayList<>();
		List<TacsflagVO> TacsflagList2 = new ArrayList<>();
		// 自宅電話番号利用ステータス履歴
		if (Utils.isNotNullAndEmpty(applicationDetailResVO.getTC_LatestDate1())) {
			TacsflagVO TacsflagData1 = new TacsflagVO();
			TacsflagData1.setDate(applicationDetailResVO.getTC_LatestDate1());
			TacsflagData1.setTacsflag(applicationDoc.getTC_F01_1());
			TacsflagList1.add(TacsflagData1);
			TacsflagVO TacsflagData2 = new TacsflagVO();
			TacsflagData2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 1));
			TacsflagData2.setTacsflag(applicationDoc.getTC_F02_1());
			TacsflagList1.add(TacsflagData2);
			TacsflagVO TacsflagData3 = new TacsflagVO();
			TacsflagData3.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 2));
			TacsflagData3.setTacsflag(applicationDoc.getTC_F03_1());
			TacsflagList1.add(TacsflagData3);
			TacsflagVO TacsflagData4 = new TacsflagVO();
			TacsflagData4.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 3));
			TacsflagData4.setTacsflag(applicationDoc.getTC_F04_1());
			TacsflagList1.add(TacsflagData4);
			TacsflagVO TacsflagData5 = new TacsflagVO();
			TacsflagData5.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 4));
			TacsflagData5.setTacsflag(applicationDoc.getTC_F05_1());
			TacsflagList1.add(TacsflagData5);
			TacsflagVO TacsflagData6 = new TacsflagVO();
			TacsflagData6.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 5));
			TacsflagData6.setTacsflag(applicationDoc.getTC_F06_1());
			TacsflagList1.add(TacsflagData6);
			TacsflagVO TacsflagData7 = new TacsflagVO();
			TacsflagData7.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 6));
			TacsflagData7.setTacsflag(applicationDoc.getTC_F07_1());
			TacsflagList1.add(TacsflagData7);
			TacsflagVO TacsflagData8 = new TacsflagVO();
			TacsflagData8.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 7));
			TacsflagData8.setTacsflag(applicationDoc.getTC_F08_1());
			TacsflagList1.add(TacsflagData8);
			TacsflagVO TacsflagData9 = new TacsflagVO();
			TacsflagData9.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 8));
			TacsflagData9.setTacsflag(applicationDoc.getTC_F09_1());
			TacsflagList1.add(TacsflagData9);
			TacsflagVO TacsflagData10 = new TacsflagVO();
			TacsflagData10.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 9));
			TacsflagData10.setTacsflag(applicationDoc.getTC_F10_1());
			TacsflagList1.add(TacsflagData10);
			TacsflagVO TacsflagData11 = new TacsflagVO();
			TacsflagData11.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 10));
			TacsflagData11.setTacsflag(applicationDoc.getTC_F11_1());
			TacsflagList1.add(TacsflagData11);
			TacsflagVO TacsflagData12 = new TacsflagVO();
			TacsflagData12.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 11));
			TacsflagData12.setTacsflag(applicationDoc.getTC_F12_1());
			TacsflagList1.add(TacsflagData12);
			TacsflagVO TacsflagData13 = new TacsflagVO();
			TacsflagData13.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 12));
			TacsflagData13.setTacsflag(applicationDoc.getTC_F13_1());
			TacsflagList1.add(TacsflagData13);
			TacsflagVO TacsflagData14 = new TacsflagVO();
			TacsflagData14.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 13));
			TacsflagData14.setTacsflag(applicationDoc.getTC_F14_1());
			TacsflagList1.add(TacsflagData14);
			TacsflagVO TacsflagData15 = new TacsflagVO();
			TacsflagData15.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 14));
			TacsflagData15.setTacsflag(applicationDoc.getTC_F15_1());
			TacsflagList1.add(TacsflagData15);
			TacsflagVO TacsflagData16 = new TacsflagVO();
			TacsflagData16.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 15));
			TacsflagData16.setTacsflag(applicationDoc.getTC_F16_1());
			TacsflagList1.add(TacsflagData16);
			TacsflagVO TacsflagData17 = new TacsflagVO();
			TacsflagData17.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 16));
			TacsflagData17.setTacsflag(applicationDoc.getTC_F17_1());
			TacsflagList1.add(TacsflagData17);
			TacsflagVO TacsflagData18 = new TacsflagVO();
			TacsflagData18.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 17));
			TacsflagData18.setTacsflag(applicationDoc.getTC_F18_1());
			TacsflagList1.add(TacsflagData18);
			TacsflagVO TacsflagData19 = new TacsflagVO();
			TacsflagData19.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 18));
			TacsflagData19.setTacsflag(applicationDoc.getTC_F19_1());
			TacsflagList1.add(TacsflagData19);
			TacsflagVO TacsflagData20 = new TacsflagVO();
			TacsflagData20.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 19));
			TacsflagData20.setTacsflag(applicationDoc.getTC_F20_1());
			TacsflagList1.add(TacsflagData20);
			TacsflagVO TacsflagData21 = new TacsflagVO();
			TacsflagData21.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 20));
			TacsflagData21.setTacsflag(applicationDoc.getTC_F21_1());
			TacsflagList1.add(TacsflagData21);
			TacsflagVO TacsflagData22 = new TacsflagVO();
			TacsflagData22.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 21));
			TacsflagData22.setTacsflag(applicationDoc.getTC_F22_1());
			TacsflagList1.add(TacsflagData22);
			TacsflagVO TacsflagData23 = new TacsflagVO();
			TacsflagData23.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 22));
			TacsflagData23.setTacsflag(applicationDoc.getTC_F23_1());
			TacsflagList1.add(TacsflagData23);
			TacsflagVO TacsflagData24 = new TacsflagVO();
			TacsflagData24.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 23));
			TacsflagData24.setTacsflag(applicationDoc.getTC_F24_1());
			TacsflagList1.add(TacsflagData24);
		}

		if (Utils.isNotNullAndEmpty(applicationDetailResVO.getTC_LatestDate2())) {
			// 携帯電話番号利用ステータス履歴
			TacsflagVO TacsflagData1_2 = new TacsflagVO();
			TacsflagData1_2.setDate(applicationDetailResVO.getTC_LatestDate2());
			TacsflagData1_2.setTacsflag(applicationDoc.getTC_F01_2());
			TacsflagList2.add(TacsflagData1_2);
			TacsflagVO TacsflagData2_2 = new TacsflagVO();
			TacsflagData2_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 1));
			TacsflagData2_2.setTacsflag(applicationDoc.getTC_F02_2());
			TacsflagList2.add(TacsflagData2_2);
			TacsflagVO TacsflagData3_2 = new TacsflagVO();
			TacsflagData3_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 2));
			TacsflagData3_2.setTacsflag(applicationDoc.getTC_F03_2());
			TacsflagList2.add(TacsflagData3_2);
			TacsflagVO TacsflagData4_2 = new TacsflagVO();
			TacsflagData4_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 3));
			TacsflagData4_2.setTacsflag(applicationDoc.getTC_F04_2());
			TacsflagList2.add(TacsflagData4_2);
			TacsflagVO TacsflagData5_2 = new TacsflagVO();
			TacsflagData5_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 4));
			TacsflagData5_2.setTacsflag(applicationDoc.getTC_F05_2());
			TacsflagList2.add(TacsflagData5_2);
			TacsflagVO TacsflagData6_2 = new TacsflagVO();
			TacsflagData6_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 5));
			TacsflagData6_2.setTacsflag(applicationDoc.getTC_F06_2());
			TacsflagList2.add(TacsflagData6_2);
			TacsflagVO TacsflagData7_2 = new TacsflagVO();
			TacsflagData7_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 6));
			TacsflagData7_2.setTacsflag(applicationDoc.getTC_F07_2());
			TacsflagList2.add(TacsflagData7_2);
			TacsflagVO TacsflagData8_2 = new TacsflagVO();
			TacsflagData8_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 7));
			TacsflagData8_2.setTacsflag(applicationDoc.getTC_F08_2());
			TacsflagList2.add(TacsflagData8_2);
			TacsflagVO TacsflagData9_2 = new TacsflagVO();
			TacsflagData9_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 8));
			TacsflagData9_2.setTacsflag(applicationDoc.getTC_F09_2());
			TacsflagList2.add(TacsflagData9_2);
			TacsflagVO TacsflagData10_2 = new TacsflagVO();
			TacsflagData10_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 9));
			TacsflagData10_2.setTacsflag(applicationDoc.getTC_F10_2());
			TacsflagList2.add(TacsflagData10_2);
			TacsflagVO TacsflagData11_2 = new TacsflagVO();
			TacsflagData11_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 10));
			TacsflagData11_2.setTacsflag(applicationDoc.getTC_F11_2());
			TacsflagList2.add(TacsflagData11_2);
			TacsflagVO TacsflagData12_2 = new TacsflagVO();
			TacsflagData12_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 11));
			TacsflagData12_2.setTacsflag(applicationDoc.getTC_F12_2());
			TacsflagList2.add(TacsflagData12_2);
			TacsflagVO TacsflagData13_2 = new TacsflagVO();
			TacsflagData13_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 12));
			TacsflagData13_2.setTacsflag(applicationDoc.getTC_F13_2());
			TacsflagList2.add(TacsflagData13_2);
			TacsflagVO TacsflagData14_2 = new TacsflagVO();
			TacsflagData14_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 13));
			TacsflagData14_2.setTacsflag(applicationDoc.getTC_F14_2());
			TacsflagList2.add(TacsflagData14_2);
			TacsflagVO TacsflagData15_2 = new TacsflagVO();
			TacsflagData15_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 14));
			TacsflagData15_2.setTacsflag(applicationDoc.getTC_F15_2());
			TacsflagList2.add(TacsflagData15_2);
			TacsflagVO TacsflagData16_2 = new TacsflagVO();
			TacsflagData16_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 15));
			TacsflagData16_2.setTacsflag(applicationDoc.getTC_F16_2());
			TacsflagList2.add(TacsflagData16_2);
			TacsflagVO TacsflagData17_2 = new TacsflagVO();
			TacsflagData17_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 16));
			TacsflagData17_2.setTacsflag(applicationDoc.getTC_F17_2());
			TacsflagList2.add(TacsflagData17_2);
			TacsflagVO TacsflagData18_2 = new TacsflagVO();
			TacsflagData18_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 17));
			TacsflagData18_2.setTacsflag(applicationDoc.getTC_F18_2());
			TacsflagList2.add(TacsflagData18_2);
			TacsflagVO TacsflagData19_2 = new TacsflagVO();
			TacsflagData19_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 18));
			TacsflagData19_2.setTacsflag(applicationDoc.getTC_F19_2());
			TacsflagList2.add(TacsflagData19_2);
			TacsflagVO TacsflagData20_2 = new TacsflagVO();
			TacsflagData20_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 19));
			TacsflagData20_2.setTacsflag(applicationDoc.getTC_F20_2());
			TacsflagList2.add(TacsflagData20_2);
			TacsflagVO TacsflagData21_2 = new TacsflagVO();
			TacsflagData21_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 20));
			TacsflagData21_2.setTacsflag(applicationDoc.getTC_F21_2());
			TacsflagList2.add(TacsflagData21_2);
			TacsflagVO TacsflagData22_2 = new TacsflagVO();
			TacsflagData22_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 21));
			TacsflagData22_2.setTacsflag(applicationDoc.getTC_F22_2());
			TacsflagList2.add(TacsflagData22_2);
			TacsflagVO TacsflagData23_2 = new TacsflagVO();
			TacsflagData23_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 22));
			TacsflagData23_2.setTacsflag(applicationDoc.getTC_F23_2());
			TacsflagList2.add(TacsflagData23_2);
			TacsflagVO TacsflagData24_2 = new TacsflagVO();
			TacsflagData24_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 23));
			TacsflagData24_2.setTacsflag(applicationDoc.getTC_F24_2());
			TacsflagList2.add(TacsflagData24_2);
		}

		applicationDetailResVO.setTacsflagList1(TacsflagList1);
		applicationDetailResVO.setTacsflagList2(TacsflagList2);

		// 電話番号鑑定結果
		int TC_Count1 = 0;
		int TC_Count2 = 0;
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count2())) {
			TC_Count2 = Integer.parseInt(applicationDoc.getTC_Count2());
		}
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count1())) {
			TC_Count1 = Integer.parseInt(applicationDoc.getTC_Count1());
		}
		boolean flag = false;
		if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getPhoneNumber()))
				&& Utils.isNotNullAndEmpty(applicationDoc.getTC_Result2())) {
			// 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
			// attention＝1or2or3orEorU or 検索結果 result=0以外
			if (TC_Count2 >= 2 || applicationDoc.getTC_Tacsflag2().equals("0")
					|| applicationDoc.getTC_Tacsflag2().equals("2") || applicationDoc.getTC_Tacsflag2().equals("3")
					|| applicationDoc.getTC_Tacsflag2().equals("4") || applicationDoc.getTC_Tacsflag2().equals("7")
					|| applicationDoc.getTC_Attention2().equals("1") || applicationDoc.getTC_Attention2().equals("2")
					|| applicationDoc.getTC_Attention2().equals("3") || applicationDoc.getTC_Attention2().equals("E")
					|| applicationDoc.getTC_Attention2().equals("U") || !applicationDoc.getTC_Result2().equals("0")) {
				// 要注意
				// accountAppInitVO.setAppraisalTelResult("1");
				flag = true;
			}
			// } else{
			// // 問題なし
			// accountAppInitVO.setAppraisalTelResult("2");
			// }
		}
		if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getTeleNumber()))
				&& Utils.isNotNullAndEmpty(applicationDoc.getTC_Result1())) {
			// 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
			// attention＝1or2or3orEorU or 検索結果 result=0以外
			if (TC_Count1 >= 2 || applicationDoc.getTC_Tacsflag1().equals("0")
					|| applicationDoc.getTC_Tacsflag1().equals("2") || applicationDoc.getTC_Tacsflag1().equals("3")
					|| applicationDoc.getTC_Tacsflag1().equals("4") || applicationDoc.getTC_Tacsflag1().equals("7")
					|| applicationDoc.getTC_Attention1().equals("1") || applicationDoc.getTC_Attention1().equals("2")
					|| applicationDoc.getTC_Attention1().equals("3") || applicationDoc.getTC_Attention1().equals("E")
					|| applicationDoc.getTC_Attention1().equals("U") || !applicationDoc.getTC_Result1().equals("0")) {
				flag = true;
			}
		}
		if (flag) {
			// 要注意
			applicationDetailResVO.setAppraisalTelResult("1");
		} else {
			// 問題なし
			applicationDetailResVO.setAppraisalTelResult("2");
		}
		// ＩＰアドレス鑑定結果
		// 国脅威度＝high or PS-IP＝true or Proxy利用＝true or 2週間以内の検索回数＞＝2
		if (applicationDoc.getIC_CountryThreat().equals(Constants.THREAT_1)
				|| applicationDoc.getIC_PSIP().equals(Constants.PSIP_1)
				|| applicationDoc.getIC_Proxy().equals(Constants.PSIP_1) || twoWeeksCount >= 2) {
			// 要注意
			applicationDetailResVO.setAppraisalIPResult("1");
		} else {
			// 問題なし
			applicationDetailResVO.setAppraisalIPResult("2");
		}
		return applicationDetailResVO;
	}

	/**
	 * 
	 * 山形
	 * 
	 */

	public AccountAppYamaGataDetailResVO yamagata(Database db, BaseResVO baseResVO) throws Exception {
		AccountAppYamaGataDetailResVO applicationDetailResVO = new AccountAppYamaGataDetailResVO();
		AccountAppDetailReqVO accountAppDetailReqVO = new AccountAppDetailReqVO();
		accountAppDetailReqVO = (AccountAppDetailReqVO) baseResVO;
		// 申込詳細情報取得
		AccountYamaGataAppDoc applicationDoc = new AccountYamaGataAppDoc();
		try {
			applicationDoc = (AccountYamaGataAppDoc) repositoryUtil.find(db, accountAppDetailReqVO.get_id(),
					AccountYamaGataAppDoc.class);

		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
			throw new BusinessException(messages);
		}
		// 戻り値を設定
		// 自宅電話番号
		if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getTeleNumber()))) {
			applicationDetailResVO
					.setTeleNumber(PhoneNumberUtil.format(encryptorUtil.decrypt(applicationDoc.getTeleNumber())));
		} else {
			applicationDetailResVO.setTeleNumber("");
		}
		// 携帯電話番号
		if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getPhoneNumber()))) {
			applicationDetailResVO
					.setPhoneNumber(PhoneNumberUtil.format(encryptorUtil.decrypt(applicationDoc.getPhoneNumber())));
		} else {
			applicationDetailResVO.setPhoneNumber("");
		}
		applicationDetailResVO.setAccountAppSeq(applicationDoc.getAccountAppSeq());
		applicationDetailResVO.setIpAddress(encryptorUtil.decrypt(applicationDoc.getIpAddress()));
		applicationDetailResVO.setIC_CompanyCity(applicationDoc.getIC_CompanyCity());
		applicationDetailResVO.setIC_CompanyDomain(applicationDoc.getIC_CompanyDomain());
		applicationDetailResVO.setIC_CompanyName(applicationDoc.getIC_CompanyName());
		applicationDetailResVO.setIC_CountryCode(applicationDoc.getIC_CountryCode());
		applicationDetailResVO.setIC_CountryName(applicationDoc.getIC_CountryName());
		applicationDetailResVO.setIC_CountryThreat(applicationDoc.getIC_CountryThreat());
		applicationDetailResVO.setIC_Distance(applicationDoc.getIC_Distance());
		applicationDetailResVO.setIC_IpThreat(applicationDoc.getIC_IpThreat());
		if (applicationDoc.getIC_isMobile().equals(Constants.PSIP_1)) {
			applicationDetailResVO.setIC_isMobile(applicationDoc.getIC_Carrier());
		} else if (applicationDoc.getIC_isMobile().equals(Constants.PSIP_2)) {
			applicationDetailResVO.setIC_isMobile(Constants.ISMOBILEVALUE);
		} else {
			applicationDetailResVO.setIC_isMobile("");
		}
		applicationDetailResVO.setIC_Proxy(applicationDoc.getIC_Proxy());
		applicationDetailResVO.setIC_PSIP(applicationDoc.getIC_PSIP());
		applicationDetailResVO.setTC_Access1(applicationDoc.getTC_Access1());
		applicationDetailResVO.setTC_Access2(applicationDoc.getTC_Access2());
		applicationDetailResVO.setTC_Attention1(applicationDoc.getTC_Attention1());
		applicationDetailResVO.setTC_Attention2(applicationDoc.getTC_Attention2());
		applicationDetailResVO.setTC_Carrier1(applicationDoc.getTC_Carrier1());
		applicationDetailResVO.setTC_Carrier2(applicationDoc.getTC_Carrier2());
		applicationDetailResVO.setTC_Count1(applicationDoc.getTC_Count1());
		applicationDetailResVO.setTC_Count2(applicationDoc.getTC_Count2());
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestyear1())
				&& Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestmonth1())) {
			applicationDetailResVO
					.setTC_LatestDate1(applicationDoc.getTC_Latestyear1() + "/" + applicationDoc.getTC_Latestmonth1());
		} else {
			applicationDetailResVO
					.setTC_LatestDate1(applicationDoc.getTC_Latestyear1() + applicationDoc.getTC_Latestmonth1());
		}
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestyear2())
				&& Utils.isNotNullAndEmpty(applicationDoc.getTC_Latestmonth2())) {
			applicationDetailResVO
					.setTC_LatestDate2(applicationDoc.getTC_Latestyear2() + "/" + applicationDoc.getTC_Latestmonth2());
		} else {
			applicationDetailResVO
					.setTC_LatestDate2(applicationDoc.getTC_Latestyear2() + applicationDoc.getTC_Latestmonth2());
		}
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Month1())) {
			applicationDetailResVO.setTC_Month1(applicationDoc.getTC_Month1() + Constants.MONTH);
		}
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Month2())) {
			applicationDetailResVO.setTC_Month2(applicationDoc.getTC_Month2() + Constants.MONTH);
		}
		applicationDetailResVO.setTC_Movetel1(applicationDoc.getTC_Movetel1());
		applicationDetailResVO.setTC_Movetel2(applicationDoc.getTC_Movetel2());
		applicationDetailResVO.setTC_Result1(applicationDoc.getTC_Result1());
		applicationDetailResVO.setTC_Result2(applicationDoc.getTC_Result2());
		applicationDetailResVO.setTC_Tacsflag1(applicationDoc.getTC_Tacsflag1());
		applicationDetailResVO.setTC_Tacsflag2(applicationDoc.getTC_Tacsflag2());

		int twoWeeksCount = 0;
		if (Utils.isNotNullAndEmpty(applicationDoc.getIC_SearchHistory())) {
			String[] searchHistory = applicationDoc.getIC_SearchHistory().split(",");
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
		applicationDetailResVO.setIC_TwoWeeksCount(Integer.toString(twoWeeksCount));

		List<TacsflagVO> TacsflagList1 = new ArrayList<>();
		List<TacsflagVO> TacsflagList2 = new ArrayList<>();
		// 自宅電話番号利用ステータス履歴
		if (Utils.isNotNullAndEmpty(applicationDetailResVO.getTC_LatestDate1())) {
			TacsflagVO TacsflagData1 = new TacsflagVO();
			TacsflagData1.setDate(applicationDetailResVO.getTC_LatestDate1());
			TacsflagData1.setTacsflag(applicationDoc.getF01_1());
			TacsflagList1.add(TacsflagData1);
			TacsflagVO TacsflagData2 = new TacsflagVO();
			TacsflagData2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 1));
			TacsflagData2.setTacsflag(applicationDoc.getF02_1());
			TacsflagList1.add(TacsflagData2);
			TacsflagVO TacsflagData3 = new TacsflagVO();
			TacsflagData3.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 2));
			TacsflagData3.setTacsflag(applicationDoc.getF03_1());
			TacsflagList1.add(TacsflagData3);
			TacsflagVO TacsflagData4 = new TacsflagVO();
			TacsflagData4.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 3));
			TacsflagData4.setTacsflag(applicationDoc.getF04_1());
			TacsflagList1.add(TacsflagData4);
			TacsflagVO TacsflagData5 = new TacsflagVO();
			TacsflagData5.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 4));
			TacsflagData5.setTacsflag(applicationDoc.getF05_1());
			TacsflagList1.add(TacsflagData5);
			TacsflagVO TacsflagData6 = new TacsflagVO();
			TacsflagData6.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 5));
			TacsflagData6.setTacsflag(applicationDoc.getF06_1());
			TacsflagList1.add(TacsflagData6);
			TacsflagVO TacsflagData7 = new TacsflagVO();
			TacsflagData7.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 6));
			TacsflagData7.setTacsflag(applicationDoc.getF07_1());
			TacsflagList1.add(TacsflagData7);
			TacsflagVO TacsflagData8 = new TacsflagVO();
			TacsflagData8.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 7));
			TacsflagData8.setTacsflag(applicationDoc.getF08_1());
			TacsflagList1.add(TacsflagData8);
			TacsflagVO TacsflagData9 = new TacsflagVO();
			TacsflagData9.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 8));
			TacsflagData9.setTacsflag(applicationDoc.getF09_1());
			TacsflagList1.add(TacsflagData9);
			TacsflagVO TacsflagData10 = new TacsflagVO();
			TacsflagData10.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 9));
			TacsflagData10.setTacsflag(applicationDoc.getF10_1());
			TacsflagList1.add(TacsflagData10);
			TacsflagVO TacsflagData11 = new TacsflagVO();
			TacsflagData11.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 10));
			TacsflagData11.setTacsflag(applicationDoc.getF11_1());
			TacsflagList1.add(TacsflagData11);
			TacsflagVO TacsflagData12 = new TacsflagVO();
			TacsflagData12.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 11));
			TacsflagData12.setTacsflag(applicationDoc.getF12_1());
			TacsflagList1.add(TacsflagData12);
			TacsflagVO TacsflagData13 = new TacsflagVO();
			TacsflagData13.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 12));
			TacsflagData13.setTacsflag(applicationDoc.getF13_1());
			TacsflagList1.add(TacsflagData13);
			TacsflagVO TacsflagData14 = new TacsflagVO();
			TacsflagData14.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 13));
			TacsflagData14.setTacsflag(applicationDoc.getF14_1());
			TacsflagList1.add(TacsflagData14);
			TacsflagVO TacsflagData15 = new TacsflagVO();
			TacsflagData15.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 14));
			TacsflagData15.setTacsflag(applicationDoc.getF15_1());
			TacsflagList1.add(TacsflagData15);
			TacsflagVO TacsflagData16 = new TacsflagVO();
			TacsflagData16.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 15));
			TacsflagData16.setTacsflag(applicationDoc.getF16_1());
			TacsflagList1.add(TacsflagData16);
			TacsflagVO TacsflagData17 = new TacsflagVO();
			TacsflagData17.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 16));
			TacsflagData17.setTacsflag(applicationDoc.getF17_1());
			TacsflagList1.add(TacsflagData17);
			TacsflagVO TacsflagData18 = new TacsflagVO();
			TacsflagData18.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 17));
			TacsflagData18.setTacsflag(applicationDoc.getF18_1());
			TacsflagList1.add(TacsflagData18);
			TacsflagVO TacsflagData19 = new TacsflagVO();
			TacsflagData19.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 18));
			TacsflagData19.setTacsflag(applicationDoc.getF19_1());
			TacsflagList1.add(TacsflagData19);
			TacsflagVO TacsflagData20 = new TacsflagVO();
			TacsflagData20.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 19));
			TacsflagData20.setTacsflag(applicationDoc.getF20_1());
			TacsflagList1.add(TacsflagData20);
			TacsflagVO TacsflagData21 = new TacsflagVO();
			TacsflagData21.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 20));
			TacsflagData21.setTacsflag(applicationDoc.getF21_1());
			TacsflagList1.add(TacsflagData21);
			TacsflagVO TacsflagData22 = new TacsflagVO();
			TacsflagData22.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 21));
			TacsflagData22.setTacsflag(applicationDoc.getF22_1());
			TacsflagList1.add(TacsflagData22);
			TacsflagVO TacsflagData23 = new TacsflagVO();
			TacsflagData23.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 22));
			TacsflagData23.setTacsflag(applicationDoc.getF23_1());
			TacsflagList1.add(TacsflagData23);
			TacsflagVO TacsflagData24 = new TacsflagVO();
			TacsflagData24.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate1(), 23));
			TacsflagData24.setTacsflag(applicationDoc.getF24_1());
			TacsflagList1.add(TacsflagData24);
		}

		if (Utils.isNotNullAndEmpty(applicationDetailResVO.getTC_LatestDate2())) {
			// 携帯電話番号利用ステータス履歴
			TacsflagVO TacsflagData1_2 = new TacsflagVO();
			TacsflagData1_2.setDate(applicationDetailResVO.getTC_LatestDate2());
			TacsflagData1_2.setTacsflag(applicationDoc.getF01_2());
			TacsflagList2.add(TacsflagData1_2);
			TacsflagVO TacsflagData2_2 = new TacsflagVO();
			TacsflagData2_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 1));
			TacsflagData2_2.setTacsflag(applicationDoc.getF02_2());
			TacsflagList2.add(TacsflagData2_2);
			TacsflagVO TacsflagData3_2 = new TacsflagVO();
			TacsflagData3_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 2));
			TacsflagData3_2.setTacsflag(applicationDoc.getF03_2());
			TacsflagList2.add(TacsflagData3_2);
			TacsflagVO TacsflagData4_2 = new TacsflagVO();
			TacsflagData4_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 3));
			TacsflagData4_2.setTacsflag(applicationDoc.getF04_2());
			TacsflagList2.add(TacsflagData4_2);
			TacsflagVO TacsflagData5_2 = new TacsflagVO();
			TacsflagData5_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 4));
			TacsflagData5_2.setTacsflag(applicationDoc.getF05_2());
			TacsflagList2.add(TacsflagData5_2);
			TacsflagVO TacsflagData6_2 = new TacsflagVO();
			TacsflagData6_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 5));
			TacsflagData6_2.setTacsflag(applicationDoc.getF06_2());
			TacsflagList2.add(TacsflagData6_2);
			TacsflagVO TacsflagData7_2 = new TacsflagVO();
			TacsflagData7_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 6));
			TacsflagData7_2.setTacsflag(applicationDoc.getF07_2());
			TacsflagList2.add(TacsflagData7_2);
			TacsflagVO TacsflagData8_2 = new TacsflagVO();
			TacsflagData8_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 7));
			TacsflagData8_2.setTacsflag(applicationDoc.getF08_2());
			TacsflagList2.add(TacsflagData8_2);
			TacsflagVO TacsflagData9_2 = new TacsflagVO();
			TacsflagData9_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 8));
			TacsflagData9_2.setTacsflag(applicationDoc.getF09_2());
			TacsflagList2.add(TacsflagData9_2);
			TacsflagVO TacsflagData10_2 = new TacsflagVO();
			TacsflagData10_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 9));
			TacsflagData10_2.setTacsflag(applicationDoc.getF10_2());
			TacsflagList2.add(TacsflagData10_2);
			TacsflagVO TacsflagData11_2 = new TacsflagVO();
			TacsflagData11_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 10));
			TacsflagData11_2.setTacsflag(applicationDoc.getF11_2());
			TacsflagList2.add(TacsflagData11_2);
			TacsflagVO TacsflagData12_2 = new TacsflagVO();
			TacsflagData12_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 11));
			TacsflagData12_2.setTacsflag(applicationDoc.getF12_2());
			TacsflagList2.add(TacsflagData12_2);
			TacsflagVO TacsflagData13_2 = new TacsflagVO();
			TacsflagData13_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 12));
			TacsflagData13_2.setTacsflag(applicationDoc.getF13_2());
			TacsflagList2.add(TacsflagData13_2);
			TacsflagVO TacsflagData14_2 = new TacsflagVO();
			TacsflagData14_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 13));
			TacsflagData14_2.setTacsflag(applicationDoc.getF14_2());
			TacsflagList2.add(TacsflagData14_2);
			TacsflagVO TacsflagData15_2 = new TacsflagVO();
			TacsflagData15_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 14));
			TacsflagData15_2.setTacsflag(applicationDoc.getF15_2());
			TacsflagList2.add(TacsflagData15_2);
			TacsflagVO TacsflagData16_2 = new TacsflagVO();
			TacsflagData16_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 15));
			TacsflagData16_2.setTacsflag(applicationDoc.getF16_2());
			TacsflagList2.add(TacsflagData16_2);
			TacsflagVO TacsflagData17_2 = new TacsflagVO();
			TacsflagData17_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 16));
			TacsflagData17_2.setTacsflag(applicationDoc.getF17_2());
			TacsflagList2.add(TacsflagData17_2);
			TacsflagVO TacsflagData18_2 = new TacsflagVO();
			TacsflagData18_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 17));
			TacsflagData18_2.setTacsflag(applicationDoc.getF18_2());
			TacsflagList2.add(TacsflagData18_2);
			TacsflagVO TacsflagData19_2 = new TacsflagVO();
			TacsflagData19_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 18));
			TacsflagData19_2.setTacsflag(applicationDoc.getF19_2());
			TacsflagList2.add(TacsflagData19_2);
			TacsflagVO TacsflagData20_2 = new TacsflagVO();
			TacsflagData20_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 19));
			TacsflagData20_2.setTacsflag(applicationDoc.getF20_2());
			TacsflagList2.add(TacsflagData20_2);
			TacsflagVO TacsflagData21_2 = new TacsflagVO();
			TacsflagData21_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 20));
			TacsflagData21_2.setTacsflag(applicationDoc.getF21_2());
			TacsflagList2.add(TacsflagData21_2);
			TacsflagVO TacsflagData22_2 = new TacsflagVO();
			TacsflagData22_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 21));
			TacsflagData22_2.setTacsflag(applicationDoc.getF22_2());
			TacsflagList2.add(TacsflagData22_2);
			TacsflagVO TacsflagData23_2 = new TacsflagVO();
			TacsflagData23_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 22));
			TacsflagData23_2.setTacsflag(applicationDoc.getF23_2());
			TacsflagList2.add(TacsflagData23_2);
			TacsflagVO TacsflagData24_2 = new TacsflagVO();
			TacsflagData24_2.setDate(dateCalculate(applicationDetailResVO.getTC_LatestDate2(), 23));
			TacsflagData24_2.setTacsflag(applicationDoc.getF24_2());
			TacsflagList2.add(TacsflagData24_2);
		}

		applicationDetailResVO.setTacsflagList1(TacsflagList1);
		applicationDetailResVO.setTacsflagList2(TacsflagList2);

		// 電話番号鑑定結果
		int TC_Count1 = 0;
		int TC_Count2 = 0;
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count2())) {
			TC_Count2 = Integer.parseInt(applicationDoc.getTC_Count2());
		}
		if (Utils.isNotNullAndEmpty(applicationDoc.getTC_Count1())) {
			TC_Count1 = Integer.parseInt(applicationDoc.getTC_Count1());
		}
		boolean flag = false;
		if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getPhoneNumber()))
				&& Utils.isNotNullAndEmpty(applicationDoc.getTC_Result2())) {
			// 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
			// attention＝1or2or3orEorU or 検索結果 result=0以外
			if (TC_Count2 >= 2 || applicationDoc.getTC_Tacsflag2().equals("0")
					|| applicationDoc.getTC_Tacsflag2().equals("2") || applicationDoc.getTC_Tacsflag2().equals("3")
					|| applicationDoc.getTC_Tacsflag2().equals("4") || applicationDoc.getTC_Tacsflag2().equals("6")
					|| applicationDoc.getTC_Tacsflag2().equals("7") || applicationDoc.getTC_Attention2().equals("1")
					|| applicationDoc.getTC_Attention2().equals("2") || applicationDoc.getTC_Attention2().equals("3")
					|| applicationDoc.getTC_Attention2().equals("E") || applicationDoc.getTC_Attention2().equals("U")
					|| !applicationDoc.getTC_Result2().equals("0")) {
				// 要注意
				// accountAppInitVO.setAppraisalTelResult("1");
				flag = true;
			}
			// } else{
			// // 問題なし
			// accountAppInitVO.setAppraisalTelResult("2");
			// }
		}
		if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(applicationDoc.getTeleNumber()))
				&& Utils.isNotNullAndEmpty(applicationDoc.getTC_Result1())) {
			// 検索数＞＝２or TACSフラグ tacsflag＝0or2or3or4or7 orアテンションMSG
			// attention＝1or2or3orEorU or 検索結果 result=0以外
			if (TC_Count1 >= 2 || applicationDoc.getTC_Tacsflag1().equals("0")
					|| applicationDoc.getTC_Tacsflag1().equals("2") || applicationDoc.getTC_Tacsflag1().equals("3")
					|| applicationDoc.getTC_Tacsflag1().equals("4") || applicationDoc.getTC_Tacsflag1().equals("6")
					|| applicationDoc.getTC_Tacsflag1().equals("7") || applicationDoc.getTC_Attention1().equals("1")
					|| applicationDoc.getTC_Attention1().equals("2") || applicationDoc.getTC_Attention1().equals("3")
					|| applicationDoc.getTC_Attention1().equals("E") || applicationDoc.getTC_Attention1().equals("U")
					|| !applicationDoc.getTC_Result1().equals("0")) {
				flag = true;
			}
		}
		if (flag) {
			// 要注意
			applicationDetailResVO.setAppraisalTelResult("1");
		} else {
			// 問題なし
			applicationDetailResVO.setAppraisalTelResult("2");
		}
		// ＩＰアドレス鑑定結果
		// 国脅威度＝high or PS-IP＝true or Proxy利用＝true or 2週間以内の検索回数＞＝2
		if (applicationDoc.getIC_CountryThreat().equals(Constants.THREAT_1)
				|| applicationDoc.getIC_PSIP().equals(Constants.PSIP_1)
				|| applicationDoc.getIC_Proxy().equals(Constants.PSIP_1) || twoWeeksCount >= 2) {
			// 要注意
			applicationDetailResVO.setAppraisalIPResult("1");
		} else {
			// 問題なし
			applicationDetailResVO.setAppraisalIPResult("2");
		}
		return applicationDetailResVO;
	}

	/**
	 * dateCalculateメソッド。
	 * 
	 * @param date
	 *            処理前日付
	 * @return date 計算後日付
	 * @throws Exception
	 */
	public String dateCalculate(String dateInput, int count) {
		String dateOutput = "";
		if (count <= 12) {
			int month = Integer.parseInt(dateInput.substring(5, 7));
			if (month == count) {
				int year = Integer.parseInt(dateInput.substring(0, 4)) - 1;
				dateOutput = Integer.toString(year) + "/" + "12";
			} else if (month > count) {
				int month2 = Integer.parseInt(dateInput.substring(5, 7)) - count;
				dateOutput = dateInput.substring(0, 4) + "/" + monthFormat(Integer.toString(month2));
			} else {
				int year = Integer.parseInt(dateInput.substring(0, 4)) - 1;
				int month2 = 12 + Integer.parseInt(dateInput.substring(5, 7)) - count;
				dateOutput = Integer.toString(year) + "/" + monthFormat(Integer.toString(month2));
			}
		} else {
			int year = Integer.parseInt(dateInput.substring(0, 4)) - 1;
			int month = Integer.parseInt(dateInput.substring(5, 7));
			int count2 = count - 12;
			if (month == count2) {
				year = year - 1;
				dateOutput = Integer.toString(year) + "/" + "12";
			} else if (month > count2) {
				int month2 = Integer.parseInt(dateInput.substring(5, 7)) - count2;
				dateOutput = Integer.toString(year) + "/" + monthFormat(Integer.toString(month2));
			} else {
				year = year - 1;
				int month2 = 12 + Integer.parseInt(dateInput.substring(5, 7)) - count2;
				dateOutput = Integer.toString(year) + "/" + monthFormat(Integer.toString(month2));
			}
		}

		return dateOutput;
	}

	/**
	 * month formartメソッド。
	 * 
	 * @param date
	 *            処理前日付
	 * @return date 処理後日付
	 * @throws Exception
	 */
	public String monthFormat(String month) {
		String monthOutput = "";
		int length = month.length();
		if (length == 1) {
			monthOutput = "0" + month;
		} else {
			monthOutput = month;
		}

		return monthOutput;
	}
}
