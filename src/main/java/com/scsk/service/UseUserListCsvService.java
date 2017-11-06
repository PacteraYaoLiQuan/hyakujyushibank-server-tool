package com.scsk.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.IYoUserInfoDoc;
import com.scsk.request.vo.UseUserListCsvButtonReqVO;
import com.scsk.response.vo.UseUserListCsvButtonResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.PhoneNumberUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;
import com.scsk.vo.UseUserListInitVO;

/**
 * CSV一覧検索サービス。<br>
 * <br>
 * CSV一覧検索を実装するロジック。<br>
 */
@Service
public class UseUserListCsvService extends AbstractBLogic<UseUserListCsvButtonReqVO, UseUserListCsvButtonResVO> {

	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private ActionLog actionLog;

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param accountAppListCsvButtonReqVO
	 *            申込一覧情報
	 */
	@Override
	protected void preExecute(UseUserListCsvButtonReqVO useUserListCsvButtonReqVO) throws Exception {

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
	protected UseUserListCsvButtonResVO doExecute(CloudantClient client,
			UseUserListCsvButtonReqVO useUserListCsvButtonReqVO) throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		// String useUserListCsvLog = "(ユーザータイプ：";
		// 受付進捗管理表出力用データを取得
		UseUserListCsvButtonResVO useUserListCsvButtonResVO = new UseUserListCsvButtonResVO();
		List<UseUserListInitVO> useUserList = new ArrayList<>();

		for (int i = 0; i < useUserListCsvButtonReqVO.getCsvList().size(); i++) {
			if (useUserListCsvButtonReqVO.getCsvList().get(i).getSelect() == null) {
				continue;
			}
			// 一覧選択したデータ
			if (useUserListCsvButtonReqVO.getCsvList().get(i).getSelect() == true) {
				IYoUserInfoDoc userInfoDoc = new IYoUserInfoDoc();
				try {
					// 口座開設データを取得
					userInfoDoc = (IYoUserInfoDoc) repositoryUtil.find(db,
							useUserListCsvButtonReqVO.getCsvList().get(i).get_id(), IYoUserInfoDoc.class);
					// useUserListCsvLog = useUserListCsvLog +
					// userInfoDoc.getUserType() + "/";
				} catch (BusinessException e) {
					// エラーメッセージを出力、処理終了。
					ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
					throw new BusinessException(messages);
				}
				if (userInfoDoc != null) {
					UseUserListInitVO useUserListInitVO = new UseUserListInitVO();
					// CSV出力用データを戻る
					useUserListInitVO.set_id(userInfoDoc.get_id());
					useUserListInitVO.set_rev(userInfoDoc.get_rev());

					// ユーザータイプ
					switch (userInfoDoc.getUserType()) {
					case "0":
						useUserListInitVO.setUserType(Constants.USERTYPE_0);
						break;
					case "1":
						useUserListInitVO.setUserType(Constants.USERTYPE_1);
						break;
					case "2":
						useUserListInitVO.setUserType(Constants.USERTYPE_2);
						break;
					case "3":
						useUserListInitVO.setUserType(Constants.USERTYPE_3);
						break;
					case "4":
						useUserListInitVO.setUserType(Constants.USERTYPE_4);
						break;
					case "5":
						useUserListInitVO.setUserType(Constants.USERTYPE_5);
					}
					// 姓名
					useUserListInitVO.setName(encryptorUtil.decrypt(userInfoDoc.getLastName()) + " "
							+ encryptorUtil.decrypt(userInfoDoc.getFirstName()));

					// 姓名カナ
					useUserListInitVO.setKanaName(encryptorUtil.decrypt(userInfoDoc.getKanaLastName()) + " "
							+ encryptorUtil.decrypt(userInfoDoc.getKanaFirstName()));
					// 年齢
					useUserListInitVO.setAge(encryptorUtil.decrypt(userInfoDoc.getAge()));
					// 生年月日
					if (encryptorUtil.decrypt(userInfoDoc.getBirthday()).length() == 8) {
						useUserListInitVO.setBirthday(encryptorUtil.decrypt(userInfoDoc.getBirthday()).substring(0, 4)
								+ "/" + encryptorUtil.decrypt(userInfoDoc.getBirthday()).substring(4, 6) + "/"
								+ encryptorUtil.decrypt(userInfoDoc.getBirthday()).substring(6));
					} else if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(userInfoDoc.getBirthday()))) {
						useUserListInitVO.setBirthday(encryptorUtil.decrypt(userInfoDoc.getBirthday()));
					} else {
						useUserListInitVO.setBirthday("");
					}
					// 性別
					switch (userInfoDoc.getSex()) {
					case 0:
						useUserListInitVO.setSex(Constants.SEX_1);
						break;
					case 1:
						useUserListInitVO.setSex(Constants.SEX_2);
					}
					// 職業
					List<String> occupation = userInfoDoc.getOccupation();
					Collections.sort(occupation);
					String otherOccupations = "";
					for (int count = 0; count < occupation.size(); count++) {
						String strCount = "";
						strCount = occupation.get(count);
						switch (strCount) {
						case "1":
							otherOccupations = otherOccupations + Constants.iyoOCCUPATION_1 + ",";
							break;
						case "2":
							otherOccupations = otherOccupations + Constants.iyoOCCUPATION_2 + ",";
							break;
						case "3":
							otherOccupations = otherOccupations + Constants.iyoOCCUPATION_3 + ",";
							break;
						case "4":
							otherOccupations = otherOccupations + Constants.iyoOCCUPATION_4 + ",";
							break;
						case "5":
							otherOccupations = otherOccupations + Constants.iyoOCCUPATION_5 + ",";
							break;
						case "6":
							otherOccupations = otherOccupations + Constants.iyoOCCUPATION_6 + ",";
							break;
						case "7":
							otherOccupations = otherOccupations + Constants.iyoOCCUPATION_7 + ",";
							break;
						case "8":
							otherOccupations = otherOccupations + Constants.iyoOCCUPATION_8 + ",";
							break;
						case "9":
							otherOccupations = otherOccupations + "その他（"
									+ encryptorUtil.decrypt(userInfoDoc.getOtherOccupations()) + "）,";
						}
					}
					int length = otherOccupations.length();
					if (Utils.isNotNullAndEmpty(otherOccupations)) {
						otherOccupations = otherOccupations.substring(0, length - 1);
					}
					useUserListInitVO.setOtherOccupations(otherOccupations);

					// 携帯電話番号
					if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(userInfoDoc.getPhoneNumber()))) {
						useUserListInitVO.setPhoneNumber(
								PhoneNumberUtil.format(encryptorUtil.decrypt(userInfoDoc.getPhoneNumber())));
					} else {
						useUserListInitVO.setPhoneNumber("");
					}

					// 自宅電話番号
					if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(userInfoDoc.getTeleNumber()))) {
						useUserListInitVO.setTeleNumber(
								PhoneNumberUtil.format(encryptorUtil.decrypt(userInfoDoc.getTeleNumber())));
					} else {
						useUserListInitVO.setTeleNumber("");
					}

					// 勤務先名
					useUserListInitVO.setWorkName(encryptorUtil.decrypt(userInfoDoc.getWorkName()));

					// 勤務先電話番号
					if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(userInfoDoc.getWorkTeleNumber()))) {
						useUserListInitVO.setWorkTeleNumber(
								PhoneNumberUtil.format(encryptorUtil.decrypt(userInfoDoc.getWorkTeleNumber())));
					} else {
						useUserListInitVO.setWorkTeleNumber("");
					}

					// 郵便番号
					useUserListInitVO.setPostCode(encryptorUtil.decrypt(userInfoDoc.getPostCode()));

					// 都道府
					useUserListInitVO.setAddress(encryptorUtil.decrypt(userInfoDoc.getAddress1()) + " "
							+ encryptorUtil.decrypt(userInfoDoc.getAddress2()));

					// 市区町村以下（カナ）
					useUserListInitVO.setKanaAddress(encryptorUtil.decrypt(userInfoDoc.getKanaAddress()));

					// E-mail
					useUserListInitVO.setEmail(encryptorUtil.decrypt(userInfoDoc.getEmail()));

					if (userInfoDoc.getCardNoList().size() != 0 && userInfoDoc.getCardNoList() != null) {
						// 口座名称
						String accountName = "";
						for (int n = 0; n < userInfoDoc.getCardNoList().size(); n++) {
							if (n == userInfoDoc.getCardNoList().size() - 1) {
								accountName = accountName
										+ encryptorUtil.decrypt(userInfoDoc.getCardNoList().get(n).getAccountName());
							} else {
								accountName = accountName
										+ encryptorUtil.decrypt(userInfoDoc.getCardNoList().get(n).getAccountName())
										+ "/";
							}
						}
						useUserListInitVO.setAccountName(accountName);
						// 店番
						String storeName = "";
						for (int n = 0; n < userInfoDoc.getCardNoList().size(); n++) {
							if (n == userInfoDoc.getCardNoList().size() - 1) {
								storeName = storeName
										+ encryptorUtil.decrypt(userInfoDoc.getCardNoList().get(n).getStoreName());
							} else {
								storeName = storeName
										+ encryptorUtil.decrypt(userInfoDoc.getCardNoList().get(n).getStoreName())
										+ "/";
							}
						}
						useUserListInitVO.setStoreName(storeName);
						// 科目
						String kamokuName = "";
						for (int n = 0; n < userInfoDoc.getCardNoList().size(); n++) {
							if (n == userInfoDoc.getCardNoList().size() - 1) {
								kamokuName = kamokuName
										+ encryptorUtil.decrypt(userInfoDoc.getCardNoList().get(n).getKamokuName());
							} else {
								kamokuName = kamokuName
										+ encryptorUtil.decrypt(userInfoDoc.getCardNoList().get(n).getKamokuName())
										+ "/";
							}
						}
						useUserListInitVO.setKamokuName(kamokuName);
						// 口座番号
						String accountNumber = "";
						for (int n = 0; n < userInfoDoc.getCardNoList().size(); n++) {
							if (n == userInfoDoc.getCardNoList().size() - 1) {
								accountNumber = accountNumber
										+ encryptorUtil.decrypt(userInfoDoc.getCardNoList().get(n).getAccountNumber());
							} else {
								accountNumber = accountNumber
										+ encryptorUtil.decrypt(userInfoDoc.getCardNoList().get(n).getAccountNumber())
										+ "/";
							}
						}
						useUserListInitVO.setAccountNumber(accountNumber);

					} else {
						// 口座名称
						useUserListInitVO.setAccountName("");
						// 店番
						useUserListInitVO.setStoreName("");
						// 科目
						useUserListInitVO.setKamokuName("");
						// 口座番号
						useUserListInitVO.setAccountNumber("");
					}

					// 利用規約同意日時
					useUserListInitVO.setAgreeDate(userInfoDoc.getAgreeDate());

					// 最終リクエスト日時
					useUserListInitVO.setLastReqTime(userInfoDoc.getLastReqTime());

					useUserList.add(useUserListInitVO);
				}
			}
		}

		useUserListCsvButtonResVO.setUseUserList(useUserList);
		// useUserListCsvLog = useUserListCsvLog.substring(0,
		// useUserListCsvLog.length() - 1);
		actionLog.saveActionLog(Constants.ACTIONLOG_USEUSER_2);
		return useUserListCsvButtonResVO;
	}
}
