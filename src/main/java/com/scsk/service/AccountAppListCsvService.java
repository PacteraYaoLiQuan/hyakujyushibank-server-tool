package com.scsk.service;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.scsk.request.vo.AccountAppListCsvButtonReqVO;
import com.scsk.response.vo.Account114AppListCsvButtonResVO;
import com.scsk.response.vo.AccountAppListCsvButtonResVO;
import com.scsk.response.vo.AccountYamaGataAppListCsvButtonResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.PhoneNumberUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.AccountApp114InitVO;
import com.scsk.vo.AccountAppInitVO;
import com.scsk.vo.AccountAppYamaGataInitVO;

/**
 * CSV一覧検索サービス。<br>
 * <br>
 * CSV一覧検索を実装するロジック。<br>
 */
@Service
public class AccountAppListCsvService extends AbstractBLogic<BaseResVO, BaseResVO> {

	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private ActionLog actionLog;
	@Value("${bank_cd}")
	private String bank_cd;

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param accountAppListCsvButtonReqVO
	 *            申込一覧情報
	 */
	@Override
	protected void preExecute(BaseResVO accountAppListCsvButtonReqVO) throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param accountAppListCsvButtonReqVO
	 *            申込一覧情報
	 * @return accountAppListCsvButtonResVO CSV出力用情報
	 */
	@Override
	protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);

		if ("0169".equals(bank_cd)) {
			AccountAppListCsvButtonResVO accountAppListCsvButtonResVO = hirosima(db, baseResVO);
			return accountAppListCsvButtonResVO;
		} else if ("0122".equals(bank_cd)) {
			AccountYamaGataAppListCsvButtonResVO accountAppListCsvButtonResVO = yamagata(db, baseResVO);
			return accountAppListCsvButtonResVO;
		}else if("0173".equals(bank_cd)){
		    Account114AppListCsvButtonResVO accountAppListCsvButtonResVO=hyakujyushi(db,baseResVO);
		    return accountAppListCsvButtonResVO;
		}
		return null;
	}

	private Account114AppListCsvButtonResVO hyakujyushi(Database db, BaseResVO baseResVO) throws Exception {
        String accoutAppListCsvLog = "(受付番号：";
        // 受付進捗管理表出力用データを取得
        Account114AppListCsvButtonResVO accountAppListCsvButtonResVO = new Account114AppListCsvButtonResVO();
        AccountAppListCsvButtonReqVO accountAppListCsvButtonReqVO = new AccountAppListCsvButtonReqVO();
        accountAppListCsvButtonReqVO = (AccountAppListCsvButtonReqVO) baseResVO;
        List<AccountApp114InitVO> accountAppList = new ArrayList<>();

        for (int i = 0; i < accountAppListCsvButtonReqVO.getCsvList3().size(); i++) {
            if (accountAppListCsvButtonReqVO.getCsvList2().get(i).getSelect() == null) {
                continue;
            }
            // 一覧選択したデータ
            if (accountAppListCsvButtonReqVO.getCsvList2().get(i).getSelect() == true) {
                Account114AppDoc accountAppDoc = new Account114AppDoc();
                try {
                    // 口座開設データを取得
                    accountAppDoc = (Account114AppDoc) repositoryUtil.find(db,
                            accountAppListCsvButtonReqVO.getCsvList2().get(i).get_id(), Account114AppDoc.class);
                    accoutAppListCsvLog = accoutAppListCsvLog + accountAppDoc.getAccountAppSeq() + "/";
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
                    throw new BusinessException(messages);
                }
                if (accountAppDoc != null) {
                    AccountApp114InitVO accountAppInitVO = new AccountApp114InitVO();
                    // CSV出力用データを戻る
                    accountAppInitVO.set_id(accountAppDoc.get_id());
                    accountAppInitVO.set_rev(accountAppDoc.get_rev());

                    // 受付番号
                    accountAppInitVO.setAccountAppSeq(accountAppDoc.getAccountAppSeq());

                    // 受付日時
                    accountAppInitVO.setReceiptDate(accountAppDoc.getReceiptDate());

                    // 姓名
                    accountAppInitVO.setName(encryptorUtil.decrypt(accountAppDoc.getLastName()) + " "
                            + encryptorUtil.decrypt(accountAppDoc.getFirstName()));

                    // 姓名カナ
                    accountAppInitVO.setKanaName(encryptorUtil.decrypt(accountAppDoc.getKanaLastName()) + " "
                            + encryptorUtil.decrypt(accountAppDoc.getKanaFirstName()));

                    // 生年月日
                    if (encryptorUtil.decrypt(accountAppDoc.getBirthday()).length() == 8) {
                        accountAppInitVO.setBirthday(encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(0, 4)
                                + "/" + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(4, 6) + "/"
                                + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(6));
                    } else if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getBirthday()))) {
                        accountAppInitVO.setBirthday(encryptorUtil.decrypt(accountAppDoc.getBirthday()));
                    } else {
                        accountAppInitVO.setBirthday("");
                    }
                    
                    // 性別
                    switch (accountAppDoc.getSexKbn()) {
                    case "1":
                        accountAppInitVO.setSexKbn(Constants.SEX_1);
                        break;
                    case "2":
                        accountAppInitVO.setSexKbn(Constants.SEX_2);
                    }
                    // 郵便番号
                    accountAppInitVO.setPostCode(encryptorUtil.decrypt(accountAppDoc.getPostCode()));

                    // 都道府
                    switch (accountAppDoc.getPrefecturesCode()) {
                    case "1":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_1);
                        break;
                    case "2":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_2);
                        break;
                    case "3":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_3);
                        break;
                    case "4":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_4);
                        break;
                    case "5":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_5);
                        break;
                    case "6":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_6);
                        break;
                    case "7":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_7);
                        break;
                    case "8":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_8);
                        break;
                    case "9":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_9);
                        break;
                    case "10":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_10);
                        break;
                    case "11":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_11);
                        break;
                    case "12":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_12);
                        break;
                    case "13":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_13);
                        break;
                    case "14":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_14);
                        break;
                    case "15":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_15);
                        break;
                    case "16":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_16);
                        break;
                    case "17":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_17);
                        break;
                    case "18":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_18);
                        break;
                    case "19":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_19);
                        break;
                    case "20":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_20);
                        break;
                    case "21":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_21);
                        break;
                    case "22":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_22);
                        break;
                    case "23":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_23);
                        break;
                    case "24":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_24);
                        break;
                    case "25":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_25);
                        break;
                    case "26":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_26);
                        break;
                    case "27":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_27);
                        break;
                    case "28":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_28);
                        break;
                    case "29":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_29);
                        break;
                    case "30":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_30);
                        break;
                    case "31":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_31);
                        break;
                    case "32":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_32);
                        break;
                    case "33":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_33);
                        break;
                    case "34":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_34);
                        break;
                    case "35":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_35);
                        break;
                    case "36":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_36);
                        break;
                    case "37":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_37);
                        break;
                    case "38":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_38);
                        break;
                    case "39":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_39);
                        break;
                    case "40":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_40);
                        break;
                    case "41":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_41);
                        break;
                    case "42":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_42);
                        break;
                    case "43":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_43);
                        break;
                    case "44":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_44);
                        break;
                    case "45":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_45);
                        break;
                    case "46":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_46);
                        break;
                    case "47":
                        accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_47);
                    }

                    // 市区町村・番地・アパート・マンション名
                    accountAppInitVO.setAddress(encryptorUtil.decrypt(accountAppDoc.getAddress()));

                    // 職業
                    String[] job = accountAppDoc.getJobKbn().split(",");
                    List<String>jobKbn=Arrays.asList(job);
                    Collections.sort(jobKbn);
                    String jobKbnOther = "";
                    for (int count = 0; count < jobKbn.size(); count++) {
                        String strCount = "";
                        strCount = jobKbn.get(count);
                        if (strCount.length() == 1) {
                            strCount = "0" + strCount;
                        }
                        switch (strCount) {
                        case "101":
                            jobKbnOther = jobKbnOther + Constants.JOBKBN_101 + ",";
                            break;
                        case "102":
                            jobKbnOther = jobKbnOther + Constants.JOBKBN_102 + ",";
                            break;
                        case "103":
                            jobKbnOther = jobKbnOther + Constants.JOBKBN_103 + ",";
                            break;
                        case "104":
                            jobKbnOther = jobKbnOther + Constants.JOBKBN_104 + ",";
                            break;
                        case "105":
                            jobKbnOther = jobKbnOther + Constants.JOBKBN_105 + ",";
                            break;
                        case "106":
                            jobKbnOther = jobKbnOther + Constants.JOBKBN_106 + ",";
                            break;
                        case "107":
                            jobKbnOther = jobKbnOther + Constants.JOBKBN_107 + ",";
                            break;
                        case "108":
                            jobKbnOther = jobKbnOther + Constants.JOBKBN_108 + ",";
                            break;
                        case "199":
                            jobKbnOther = jobKbnOther + "その他（" + encryptorUtil.decrypt(accountAppDoc.getJobKbnOther())
                                    + "）,";
                        }
                    }
                    int length = jobKbnOther.length();
                    if (Utils.isNotNullAndEmpty(jobKbnOther)) {
                        jobKbnOther = jobKbnOther.substring(0, length - 1);
                    }
                    accountAppInitVO.setJobKbnOther(jobKbnOther);

                    // 自宅電話番号
                    if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getTeleNumber()))) {
                        accountAppInitVO.setTeleNumber(
                                PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getTeleNumber())));
                    } else {
                        accountAppInitVO.setTeleNumber("");
                    }

                    // 携帯電話番号
                    if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber()))) {
                        accountAppInitVO.setPhoneNumber(
                                PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber())));
                    } else {
                        accountAppInitVO.setPhoneNumber("");
                    }
                    accountAppList.add(accountAppInitVO);

                    // 勤務先名
                    accountAppInitVO.setWorkName(encryptorUtil.decrypt(accountAppDoc.getWorkName()));

                    // 勤務先電話番号
                    if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber()))) {
                        accountAppInitVO.setWorkTeleNumber(
                                PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber())));
                    } else {
                        accountAppInitVO.setWorkTeleNumber("");
                    }

                    // IPアドレス
                    accountAppInitVO.setIpAddress(encryptorUtil.decrypt(accountAppDoc.getIpAddress()));

                    // 通帳デザイン
                    switch (accountAppDoc.getBankbookType()) {
                    case "1":
                        accountAppInitVO.setBankbookType(Constants.BANKBOOKDESIGNKBN_1);
                        break;
                    case "2":
                        accountAppInitVO.setBankbookType(Constants.BANKBOOKDESIGNKBN_2);
                        break;
                    case "3":
                        accountAppInitVO.setBankbookType(Constants.BANKBOOKDESIGNKBN_3);
                        break;
                    case "4":
                        accountAppInitVO.setBankbookType(Constants.BANKBOOKDESIGNKBN_4);
                    }

                    // カードデザイン
                    switch (accountAppDoc.getCardType()) {
                    case "1":
                        accountAppInitVO.setCardType(Constants.CARDDESIGNKBN_1);
                        break;
                    case "2":
                        accountAppInitVO.setCardType(Constants.CARDDESIGNKBN_2);
                        break;
                    case "3":
                        accountAppInitVO.setCardType(Constants.CARDDESIGNKBN_3);
                        break;
                    case "4":
                        accountAppInitVO.setCardType(Constants.CARDDESIGNKBN_4);
                    }

                    // 取引目的
                    String[] purpose = accountAppDoc.getAccountPurpose().split(",");
                    List<String> accountPurpose=Arrays.asList(purpose);
                    Collections.sort(accountPurpose);
                    String accountPurposeOther = "";
                    for (int count = 0; count < accountPurpose.size(); count++) {
                        switch (accountPurpose.get(count)) {
                        case "01":
                            accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_01 + ",";
                            break;
                        case "02":
                            accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_02 + ",";
                            break;
                        case "03":
                            accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_03 + ",";
                            break;
                        case "04":
                            accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_04 + ",";
                            break;
                        case "05":
                            accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_05 + ",";
                            break;
                        case "06":
                            accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_06 + ",";
                            break;
                        case "07":
                            accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_07 + ",";
                            break;
                        case "08":
                            accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_08 + ",";
                            break;
                        case "09":
                            accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_09 + ",";
                            break;
                        case "99":
                            accountPurposeOther = accountPurposeOther + "その他（"
                                    + encryptorUtil.decrypt(accountAppDoc.getAccountPurposeOther()) + "）,";
                        }
                    }
                    int length1 = accountPurposeOther.length();
                    if (Utils.isNotNullAndEmpty(accountPurposeOther)) {
                        accountPurposeOther = accountPurposeOther.substring(0, length1 - 1);
                    }
                    accountAppInitVO.setAccountPurposeOther(accountPurposeOther);

                    // 知った経緯
                    String knowProcess = accountAppDoc.getKnowProcess();

                }
            }
        }

        accountAppListCsvButtonResVO.setAccountAppList(accountAppList);
        accoutAppListCsvLog = accoutAppListCsvLog.substring(0, accoutAppListCsvLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_3 + accoutAppListCsvLog + ")", db);
        return accountAppListCsvButtonResVO;
    }

    /**
	 * 
	 * 広島
	 * 
	 * 
	 */
	public AccountAppListCsvButtonResVO hirosima(Database db, BaseResVO baseResVO) throws Exception {
		String accoutAppListCsvLog = "(受付番号：";
		// 受付進捗管理表出力用データを取得
		AccountAppListCsvButtonResVO accountAppListCsvButtonResVO = new AccountAppListCsvButtonResVO();
		AccountAppListCsvButtonReqVO accountAppListCsvButtonReqVO = new AccountAppListCsvButtonReqVO();
		accountAppListCsvButtonReqVO = (AccountAppListCsvButtonReqVO) baseResVO;
		List<AccountAppInitVO> accountAppList = new ArrayList<>();

		for (int i = 0; i < accountAppListCsvButtonReqVO.getCsvList().size(); i++) {
			if (accountAppListCsvButtonReqVO.getCsvList().get(i).getSelect() == null) {
				continue;
			}
			// 一覧選択したデータ
			if (accountAppListCsvButtonReqVO.getCsvList().get(i).getSelect() == true) {
				AccountAppDoc accountAppDoc = new AccountAppDoc();
				try {
					// 口座開設データを取得
					accountAppDoc = (AccountAppDoc) repositoryUtil.find(db,
							accountAppListCsvButtonReqVO.getCsvList().get(i).get_id(), AccountAppDoc.class);
					accoutAppListCsvLog = accoutAppListCsvLog + accountAppDoc.getAccountAppSeq() + "/";
				} catch (BusinessException e) {
					// エラーメッセージを出力、処理終了。
					ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
					throw new BusinessException(messages);
				}
				if (accountAppDoc != null) {
					AccountAppInitVO accountAppInitVO = new AccountAppInitVO();
					// CSV出力用データを戻る
					accountAppInitVO.set_id(accountAppDoc.get_id());
					accountAppInitVO.set_rev(accountAppDoc.get_rev());

					// 受付番号
					accountAppInitVO.setAccountAppSeq(accountAppDoc.getAccountAppSeq());

					// 受付日時
					accountAppInitVO.setReceiptDate(accountAppDoc.getReceiptDate());

					// 姓名
					accountAppInitVO.setName(encryptorUtil.decrypt(accountAppDoc.getLastName()) + " "
							+ encryptorUtil.decrypt(accountAppDoc.getFirstName()));

					// 姓名カナ
					accountAppInitVO.setKanaName(encryptorUtil.decrypt(accountAppDoc.getKanaLastName()) + " "
							+ encryptorUtil.decrypt(accountAppDoc.getKanaFirstName()));

					// 生年月日
					if (encryptorUtil.decrypt(accountAppDoc.getBirthday()).length() == 8) {
						accountAppInitVO.setBirthday(encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(0, 4)
								+ "/" + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(4, 6) + "/"
								+ encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(6));
					} else if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getBirthday()))) {
						accountAppInitVO.setBirthday(encryptorUtil.decrypt(accountAppDoc.getBirthday()));
					} else {
						accountAppInitVO.setBirthday("");
					}

					// 性別
					switch (accountAppDoc.getSex()) {
					case 0:
						accountAppInitVO.setSex(Constants.SEX_1);
						break;
					case 1:
						accountAppInitVO.setSex(Constants.SEX_2);
					}
					// 郵便番号
					accountAppInitVO.setPostCode(encryptorUtil.decrypt(accountAppDoc.getPostCode()));

					// 都道府
					accountAppInitVO.setAddress(encryptorUtil.decrypt(accountAppDoc.getAddress1()) + " "
							+ encryptorUtil.decrypt(accountAppDoc.getAddress2()));
					// 住所（３、４が未利用）

					// 市区町村以下（カナ）
					accountAppInitVO.setKanaAddress(encryptorUtil.decrypt(accountAppDoc.getKanaAddress()));

					// 職業
					List<String> occupation = accountAppDoc.getOccupation();
					Collections.sort(occupation);
					String otherOccupations = "";
					for (int count = 0; count < occupation.size(); count++) {
						String strCount = "";
						strCount = occupation.get(count);
						if (strCount.length() == 1) {
							strCount = "0" + strCount;
						}
						switch (strCount) {
						case "01":
							otherOccupations = otherOccupations + Constants.OCCUPATION_1 + ",";
							break;
						case "02":
							otherOccupations = otherOccupations + Constants.OCCUPATION_2 + ",";
							break;
						case "03":
							otherOccupations = otherOccupations + Constants.OCCUPATION_3 + ",";
							break;
						case "04":
							otherOccupations = otherOccupations + Constants.OCCUPATION_4 + ",";
							break;
						case "05":
							otherOccupations = otherOccupations + Constants.OCCUPATION_5 + ",";
							break;
						case "06":
							otherOccupations = otherOccupations + Constants.OCCUPATION_6 + ",";
							break;
						case "07":
							otherOccupations = otherOccupations + Constants.OCCUPATION_7 + ",";
							break;
						case "08":
							otherOccupations = otherOccupations + Constants.OCCUPATION_8 + ",";
							break;
						case "90":
							otherOccupations = otherOccupations + "その他（"
									+ encryptorUtil.decrypt(accountAppDoc.getOtherOccupations()) + "）,";
						}
					}
					int length = otherOccupations.length();
					if (Utils.isNotNullAndEmpty(otherOccupations)) {
						otherOccupations = otherOccupations.substring(0, length - 1);
					}
					accountAppInitVO.setOtherOccupations(otherOccupations);

					// 自宅電話番号
					if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getTeleNumber()))) {
						accountAppInitVO.setTeleNumber(
								PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getTeleNumber())));
					} else {
						accountAppInitVO.setTeleNumber("");
					}

					// 携帯電話番号
					if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber()))) {
						accountAppInitVO.setPhoneNumber(
								PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber())));
					} else {
						accountAppInitVO.setPhoneNumber("");
					}
					accountAppList.add(accountAppInitVO);

					// 勤務先名
					accountAppInitVO.setWorkName(encryptorUtil.decrypt(accountAppDoc.getWorkName()));

					// 勤務先電話番号
					if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber()))) {
						accountAppInitVO.setWorkTeleNumber(
								PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber())));
					} else {
						accountAppInitVO.setWorkTeleNumber("");
					}

					// IPアドレス
					accountAppInitVO.setIpAddress(encryptorUtil.decrypt(accountAppDoc.getIpAddress()));

					// 既に口座をお持ちの方
					switch (accountAppDoc.getHoldAccount()) {
					case "0":
						accountAppInitVO.setHoldAccount(Constants.HOLDACCOUNT_0);
						break;
					case "1":
						accountAppInitVO.setHoldAccount(Constants.HOLDACCOUNT_1);
					}

					// 既に口座をお持ちの方：店名
					accountAppInitVO.setHoldAccountBank(encryptorUtil.decrypt(accountAppDoc.getHoldAccountBank()));

					// 既に口座をお持ちの方：口座番号
					accountAppInitVO.setHoldAccountNumber(encryptorUtil.decrypt(accountAppDoc.getHoldAccountNumber()));

					// 口座開設の取引目的
					List<String> tradingPurposes = accountAppDoc.getTradingPurposes();
					Collections.sort(tradingPurposes);
					String otherTradingPurposes = "";
					for (int count = 0; count < tradingPurposes.size(); count++) {
						switch (tradingPurposes.get(count)) {
						case "1":
							otherTradingPurposes = otherTradingPurposes + Constants.TRADINGPURPOSES_1 + ",";
							break;
						case "2":
							otherTradingPurposes = otherTradingPurposes + Constants.TRADINGPURPOSES_2 + ",";
							break;
						case "3":
							otherTradingPurposes = otherTradingPurposes + Constants.TRADINGPURPOSES_3 + ",";
							break;
						case "4":
							otherTradingPurposes = otherTradingPurposes + Constants.TRADINGPURPOSES_4 + ",";
							break;
						case "5":
							otherTradingPurposes = otherTradingPurposes + Constants.TRADINGPURPOSES_5 + ",";
							break;
						case "6":
							otherTradingPurposes = otherTradingPurposes + "その他（"
									+ encryptorUtil.decrypt(accountAppDoc.getOtherTradingPurposes()) + "）,";
						}
					}
					int length1 = otherTradingPurposes.length();
					if (Utils.isNotNullAndEmpty(otherTradingPurposes)) {
						otherTradingPurposes = otherTradingPurposes.substring(0, length1 - 1);
					}
					accountAppInitVO.setOtherTradingPurposes(otherTradingPurposes);

					// 口座開設の動機
					String accountAppMotive = accountAppDoc.getAccountAppMotive();
					switch (accountAppMotive) {
					case "01":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_1);
						break;
					case "02":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_2);
						break;
					case "03":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_3);
						break;
					case "04":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_4);
						break;
					case "05":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_5);
						break;
					case "06":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_6);
						break;
					case "07":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_7);
						break;
					case "08":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_8);
						break;
					case "09":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_9);
						break;
					case "10":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_10);
						break;
					case "90":
						accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_90);
						break;
					default:
						accountAppInitVO.setAccountAppMotive("");
					}

					// 知った経緯
					String knowProcess = accountAppDoc.getKnowProcess();
					switch (knowProcess) {
					case "01":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_1);
						break;
					case "02":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_2);
						break;
					case "03":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_3);
						break;
					case "04":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_4);
						break;
					case "05":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_5);
						break;
					case "06":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_6);
						break;
					case "90":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_7);
						break;
					default:
						accountAppInitVO.setKnowProcess("");
					}
				}
			}
		}

		accountAppListCsvButtonResVO.setAccountAppList(accountAppList);
		accoutAppListCsvLog = accoutAppListCsvLog.substring(0, accoutAppListCsvLog.length() - 1);
		actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_3 + accoutAppListCsvLog + ")", db);
		return accountAppListCsvButtonResVO;
	}

	/**
	 * 
	 * 山形
	 * 
	 */
	public AccountYamaGataAppListCsvButtonResVO yamagata(Database db, BaseResVO baseResVO) throws Exception {
		String accoutAppListCsvLog = "(受付番号：";
		// 受付進捗管理表出力用データを取得
		AccountYamaGataAppListCsvButtonResVO accountAppListCsvButtonResVO = new AccountYamaGataAppListCsvButtonResVO();
		AccountAppListCsvButtonReqVO accountAppListCsvButtonReqVO = new AccountAppListCsvButtonReqVO();
		accountAppListCsvButtonReqVO = (AccountAppListCsvButtonReqVO) baseResVO;
		List<AccountAppYamaGataInitVO> accountAppList = new ArrayList<>();

		for (int i = 0; i < accountAppListCsvButtonReqVO.getCsvList2().size(); i++) {
			if (accountAppListCsvButtonReqVO.getCsvList2().get(i).getSelect() == null) {
				continue;
			}
			// 一覧選択したデータ
			if (accountAppListCsvButtonReqVO.getCsvList2().get(i).getSelect() == true) {
				AccountYamaGataAppDoc accountAppDoc = new AccountYamaGataAppDoc();
				try {
					// 口座開設データを取得
					accountAppDoc = (AccountYamaGataAppDoc) repositoryUtil.find(db,
							accountAppListCsvButtonReqVO.getCsvList2().get(i).get_id(), AccountYamaGataAppDoc.class);
					accoutAppListCsvLog = accoutAppListCsvLog + accountAppDoc.getAccountAppSeq() + "/";
				} catch (BusinessException e) {
					// エラーメッセージを出力、処理終了。
					ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
					throw new BusinessException(messages);
				}
				if (accountAppDoc != null) {
					AccountAppYamaGataInitVO accountAppInitVO = new AccountAppYamaGataInitVO();
					// CSV出力用データを戻る
					accountAppInitVO.set_id(accountAppDoc.get_id());
					accountAppInitVO.set_rev(accountAppDoc.get_rev());

					// 受付番号
					accountAppInitVO.setAccountAppSeq(accountAppDoc.getAccountAppSeq());

					// 受付日時
					accountAppInitVO.setReceiptDate(accountAppDoc.getReceiptDate());

					// 姓名
					accountAppInitVO.setName(encryptorUtil.decrypt(accountAppDoc.getLastName()) + " "
							+ encryptorUtil.decrypt(accountAppDoc.getFirstName()));

					// 姓名カナ
					accountAppInitVO.setKanaName(encryptorUtil.decrypt(accountAppDoc.getKanaLastName()) + " "
							+ encryptorUtil.decrypt(accountAppDoc.getKanaFirstName()));

					// 生年月日
					if (encryptorUtil.decrypt(accountAppDoc.getBirthday()).length() == 8) {
						accountAppInitVO.setBirthday(encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(0, 4)
								+ "/" + encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(4, 6) + "/"
								+ encryptorUtil.decrypt(accountAppDoc.getBirthday()).substring(6));
					} else if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getBirthday()))) {
						accountAppInitVO.setBirthday(encryptorUtil.decrypt(accountAppDoc.getBirthday()));
					} else {
						accountAppInitVO.setBirthday("");
					}
					
					// 和暦年号
					switch (encryptorUtil.decrypt(accountAppDoc.getOrdinaryDepositEraKbn())) {
					case "1":
						accountAppInitVO.setOrdinaryDepositEraKbn(Constants.ORDINARYDEPOSITERAKBN_1);
						break;
					case "2":
						accountAppInitVO.setOrdinaryDepositEraKbn(Constants.ORDINARYDEPOSITERAKBN_2);
						break;
					case "3":
						accountAppInitVO.setOrdinaryDepositEraKbn(Constants.ORDINARYDEPOSITERAKBN_3);
						break;
					case "4":
						accountAppInitVO.setOrdinaryDepositEraKbn(Constants.ORDINARYDEPOSITERAKBN_4);
					}
					// 和暦生年月日
					String birthday = encryptorUtil.decrypt(accountAppDoc.getBirthday());
					if (Utils.isNotNullAndEmpty(birthday) && birthday.length() > 7) {
						String eraBirthday = japaneseCalendar(birthday);
						accountAppInitVO.setEraBirthday(eraBirthday);
					} else {
						accountAppInitVO.setEraBirthday("");
					}
					accountAppInitVO.setEraBirthday(encryptorUtil.decrypt(accountAppDoc.getEraBirthday()));

					// 性別
					switch (accountAppDoc.getSexKbn()) {
					case 1:
						accountAppInitVO.setSexKbn(Constants.SEX_1);
						break;
					case 2:
						accountAppInitVO.setSexKbn(Constants.SEX_2);
					}
					// 郵便番号
					accountAppInitVO.setPostCode(encryptorUtil.decrypt(accountAppDoc.getPostCode()));

					// 都道府
					switch (accountAppDoc.getPrefecturesCode()) {
					case "1":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_1);
						break;
					case "2":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_2);
						break;
					case "3":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_3);
						break;
					case "4":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_4);
						break;
					case "5":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_5);
						break;
					case "6":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_6);
						break;
					case "7":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_7);
						break;
					case "8":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_8);
						break;
					case "9":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_9);
						break;
					case "10":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_10);
						break;
					case "11":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_11);
						break;
					case "12":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_12);
						break;
					case "13":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_13);
						break;
					case "14":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_14);
						break;
					case "15":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_15);
						break;
					case "16":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_16);
						break;
					case "17":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_17);
						break;
					case "18":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_18);
						break;
					case "19":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_19);
						break;
					case "20":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_20);
						break;
					case "21":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_21);
						break;
					case "22":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_22);
						break;
					case "23":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_23);
						break;
					case "24":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_24);
						break;
					case "25":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_25);
						break;
					case "26":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_26);
						break;
					case "27":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_27);
						break;
					case "28":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_28);
						break;
					case "29":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_29);
						break;
					case "30":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_30);
						break;
					case "31":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_31);
						break;
					case "32":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_32);
						break;
					case "33":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_33);
						break;
					case "34":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_34);
						break;
					case "35":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_35);
						break;
					case "36":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_36);
						break;
					case "37":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_37);
						break;
					case "38":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_38);
						break;
					case "39":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_39);
						break;
					case "40":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_40);
						break;
					case "41":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_41);
						break;
					case "42":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_42);
						break;
					case "43":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_43);
						break;
					case "44":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_44);
						break;
					case "45":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_45);
						break;
					case "46":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_46);
						break;
					case "47":
						accountAppInitVO.setPrefecturesCode(Constants.PREFETURESCODE_47);
					}

					// 市区町村・番地・アパート・マンション名
					accountAppInitVO.setAddress(encryptorUtil.decrypt(accountAppDoc.getAddress()));

					// 職業
					List<String> jobKbn = accountAppDoc.getJobKbn();
					Collections.sort(jobKbn);
					String jobKbnOther = "";
					for (int count = 0; count < jobKbn.size(); count++) {
						String strCount = "";
						strCount = jobKbn.get(count);
						if (strCount.length() == 1) {
							strCount = "0" + strCount;
						}
						switch (strCount) {
						case "01":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_01 + ",";
							break;
						case "02":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_02 + ",";
							break;
						case "03":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_03 + ",";
							break;
						case "04":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_04 + ",";
							break;
						case "05":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_05 + ",";
							break;
						case "06":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_06 + ",";
							break;
						case "07":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_07 + ",";
							break;
						case "08":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_08 + ",";
							break;
						case "09":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_09 + ",";
							break;
						case "10":
							jobKbnOther = jobKbnOther + Constants.JOBKBN_10 + ",";
							break;
						case "49":
							jobKbnOther = jobKbnOther + "その他（" + encryptorUtil.decrypt(accountAppDoc.getJobKbnOther())
									+ "）,";
						}
					}
					int length = jobKbnOther.length();
					if (Utils.isNotNullAndEmpty(jobKbnOther)) {
						jobKbnOther = jobKbnOther.substring(0, length - 1);
					}
					accountAppInitVO.setJobKbnOther(jobKbnOther);

					// 自宅電話番号
					if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getTeleNumber()))) {
						accountAppInitVO.setTeleNumber(
								PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getTeleNumber())));
					} else {
						accountAppInitVO.setTeleNumber("");
					}

					// 携帯電話番号
					if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber()))) {
						accountAppInitVO.setPhoneNumber(
								PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getPhoneNumber())));
					} else {
						accountAppInitVO.setPhoneNumber("");
					}
					accountAppList.add(accountAppInitVO);

					// 勤務先名
					accountAppInitVO.setWorkName(encryptorUtil.decrypt(accountAppDoc.getWorkName()));

					// 勤務先電話番号
					if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber()))) {
						accountAppInitVO.setWorkTeleNumber(
								PhoneNumberUtil.format(encryptorUtil.decrypt(accountAppDoc.getWorkTeleNumber())));
					} else {
						accountAppInitVO.setWorkTeleNumber("");
					}

					// IPアドレス
					accountAppInitVO.setIpAddress(encryptorUtil.decrypt(accountAppDoc.getIpAddress()));

					// 通帳デザイン
					switch (accountAppDoc.getBankbookDesignKbn()) {
					case "1":
						accountAppInitVO.setBankbookDesignKbn(Constants.BANKBOOKDESIGNKBN_1);
						break;
					case "2":
						accountAppInitVO.setBankbookDesignKbn(Constants.BANKBOOKDESIGNKBN_2);
						break;
					case "3":
						accountAppInitVO.setBankbookDesignKbn(Constants.BANKBOOKDESIGNKBN_3);
						break;
					case "4":
						accountAppInitVO.setBankbookDesignKbn(Constants.BANKBOOKDESIGNKBN_4);
					}

					// カードデザイン
					switch (accountAppDoc.getCardDesingKbn()) {
					case "1":
						accountAppInitVO.setCardDesingKbn(Constants.CARDDESIGNKBN_1);
						break;
					case "2":
						accountAppInitVO.setCardDesingKbn(Constants.CARDDESIGNKBN_2);
						break;
					case "3":
						accountAppInitVO.setCardDesingKbn(Constants.CARDDESIGNKBN_3);
						break;
					case "4":
						accountAppInitVO.setCardDesingKbn(Constants.CARDDESIGNKBN_4);
					}

					// 取引目的
					List<String> accountPurpose = accountAppDoc.getAccountPurpose();
					Collections.sort(accountPurpose);
					String accountPurposeOther = "";
					for (int count = 0; count < accountPurpose.size(); count++) {
						switch (accountPurpose.get(count)) {
						case "01":
							accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_01 + ",";
							break;
						case "02":
							accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_02 + ",";
							break;
						case "03":
							accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_03 + ",";
							break;
						case "04":
							accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_04 + ",";
							break;
						case "05":
							accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_05 + ",";
							break;
						case "06":
							accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_06 + ",";
							break;
						case "07":
							accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_07 + ",";
							break;
						case "08":
							accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_08 + ",";
							break;
						case "09":
							accountPurposeOther = accountPurposeOther + Constants.ACCOUNTPURPOSE_09 + ",";
							break;
						case "99":
							accountPurposeOther = accountPurposeOther + "その他（"
									+ encryptorUtil.decrypt(accountAppDoc.getAccountPurposeOther()) + "）,";
						}
					}
					int length1 = accountPurposeOther.length();
					if (Utils.isNotNullAndEmpty(accountPurposeOther)) {
						accountPurposeOther = accountPurposeOther.substring(0, length1 - 1);
					}
					accountAppInitVO.setAccountPurposeOther(accountPurposeOther);

					// // 口座開設の動機
					// String accountAppMotive =
					// accountAppDoc.getAccountAppMotive();
					// switch (accountAppMotive) {
					// case "01":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_1);
					// break;
					// case "02":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_2);
					// break;
					// case "03":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_3);
					// break;
					// case "04":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_4);
					// break;
					// case "05":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_5);
					// break;
					// case "06":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_6);
					// break;
					// case "07":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_7);
					// break;
					// case "08":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_8);
					// break;
					// case "09":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_9);
					// break;
					// case "10":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_10);
					// break;
					// case "90":
					// accountAppInitVO.setAccountAppMotive(Constants.ACCOUNTAPPMOTIVE_90);
					// break;
					// default:
					// accountAppInitVO.setAccountAppMotive("");
					// }

					// 知った経緯
					String knowProcess = accountAppDoc.getKnowProcess();
					switch (knowProcess) {
					case "1":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_01);
						break;
					case "2":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_02);
						break;
					case "3":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_03);
						break;
					case "4":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_04);
						break;
					case "5":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_05);
						break;
					case "6":
						accountAppInitVO.setKnowProcess(Constants.KNOWPROCESS_06);
						break;
					default:
						accountAppInitVO.setKnowProcess("");
					}
				}
			}
		}

		accountAppListCsvButtonResVO.setAccountAppList(accountAppList);
		accoutAppListCsvLog = accoutAppListCsvLog.substring(0, accoutAppListCsvLog.length() - 1);
		actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_3 + accoutAppListCsvLog + ")", db);
		return accountAppListCsvButtonResVO;
	}
	
	/**
	 * 和暦にフォーマットするメソッド。
	 * 
	 * @param inputdDate
	 *            処理前日付
	 * @return date 処理後和歴日付
	 * @throws Exception
	 */
	public String japaneseCalendar(String inputDate) {

		String dateOutput = "";
		if (Utils.isNotNullAndEmpty(inputDate) && inputDate.length() > 7) {
			dateOutput = inputDate.substring(0, 4) + "-" + inputDate.substring(4, 6) + "-" + inputDate.substring(6, 8);
		} else {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN);
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(dateOutput, pos);
		Locale locale = new Locale("ja", "JP", "JP");

		// 和暦で表示
		DateFormat japaseseFormat = new SimpleDateFormat("GGGGy年M月d日", locale);
		String dateStr = japaseseFormat.format(date);
		dateStr = dateStr.substring(0, 1) + dateStr.substring(2);
		dateStr = dateStr.substring(0, (dateStr.indexOf("年") + 1));

		return dateStr;
	}
}
