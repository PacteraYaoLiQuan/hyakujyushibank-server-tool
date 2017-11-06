package com.scsk.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.scsk.model.HyakujyushiUserInfoDoc;
import com.scsk.model.IYoUserInfoDoc;
import com.scsk.model.geo.CardNoDoc;
import com.scsk.request.vo.UseUserDetailReqVO;
import com.scsk.response.vo.UseUserDetailResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PhoneNumberUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.Utils;

@Service
public class UseUserDetailSelService extends AbstractBLogic<UseUserDetailReqVO, UseUserDetailResVO> {

	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param reqVo
	 *            入力情報
	 */
	@Override
	protected void preExecute(UseUserDetailReqVO detailReqVO) throws Exception {

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
	protected UseUserDetailResVO doExecute(CloudantClient client, UseUserDetailReqVO detailReqVO) throws Exception {

		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		String useUserDetailLog = "(ユーザータイプ：";
		List<CardNoDoc> cardNoDocList = new ArrayList<>();
		if("0174".equals(bank_cd)){
		    IYoUserInfoDoc applicationDoc = new IYoUserInfoDoc();
	        try {
	            applicationDoc = (IYoUserInfoDoc) repositoryUtil.find(db, detailReqVO.get_id(), IYoUserInfoDoc.class);
	            useUserDetailLog = useUserDetailLog + applicationDoc.getDocType();
	        } catch (BusinessException e) {
	            // e.printStackTrace();
	            LogInfoUtil.LogDebug(e.getMessage());
	            // エラーメッセージを出力、処理終了。
	            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
	            throw new BusinessException(messages);
	        }
	        UseUserDetailResVO useUserDetailResVO=iYoUserInfoDoc(db,detailReqVO,applicationDoc);
	        actionLog.saveActionLog(Constants.ACTIONLOG_USEUSER_3 + useUserDetailLog + ")", db);
	        return useUserDetailResVO;
		}else if("0173".equals(bank_cd)){
            HyakujyushiUserInfoDoc applicationDoc = new HyakujyushiUserInfoDoc();
            try {
                applicationDoc = (HyakujyushiUserInfoDoc) repositoryUtil.find(db, detailReqVO.get_id(), HyakujyushiUserInfoDoc.class);
                useUserDetailLog = useUserDetailLog + applicationDoc.getDocType();
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1001);
                throw new BusinessException(messages);
            }
            UseUserDetailResVO useUserDetailResVO=hyakujyushiUserInfoDoc(db,detailReqVO,applicationDoc);
            return useUserDetailResVO;
        }
        return null;
	}

	private UseUserDetailResVO hyakujyushiUserInfoDoc(Database db, UseUserDetailReqVO detailReqVO,
            HyakujyushiUserInfoDoc applicationDoc) throws Exception {
        UseUserDetailResVO useUserDetailResVO = new UseUserDetailResVO();
        // 戻り値を設定
        // ユーザーID
        useUserDetailResVO.setUserId(detailReqVO.get_id());
        // ユーザータイプ
        useUserDetailResVO.setUserType(applicationDoc.getUserType());
        // 姓名
        String name = encryptorUtil.decrypt(applicationDoc.getLastName()) + " "
                + encryptorUtil.decrypt(applicationDoc.getFirstName());
        useUserDetailResVO.setName(name);
        // 姓名カナ
        String kanaName = encryptorUtil.decrypt(applicationDoc.getKanaLastName()) + " "
                + encryptorUtil.decrypt(applicationDoc.getKanaFirstName());
        useUserDetailResVO.setKanaName(kanaName);
        // 年齢
        useUserDetailResVO.setAge(encryptorUtil.decrypt(applicationDoc.getAge()));
        // 生年月日
        useUserDetailResVO.setBirthday(dateFormat(encryptorUtil.decrypt(applicationDoc.getBirthday())));

        // 性別
        useUserDetailResVO.setSexKbn(applicationDoc.getSexKbn());
        // 職業
        String[]job=applicationDoc.getJobKbn().split(",");
        List<String> occupation = Arrays.asList(job);
        Collections.sort(occupation);
        useUserDetailResVO.setOccupation(occupation);
        // その他職業
        useUserDetailResVO.setOtherOccupations(encryptorUtil.decrypt(applicationDoc.getJobKbnOther()));
        // 携帯電話番号
        String phoneNumber = encryptorUtil.decrypt(applicationDoc.getPhoneNumber());
        if (Utils.isNotNullAndEmpty(phoneNumber)) {
            useUserDetailResVO.setPhoneNumber(PhoneNumberUtil.format(phoneNumber));
        }
        // 自宅電話番号
        String teleNumber = encryptorUtil.decrypt(applicationDoc.getTeleNumber());
        if (Utils.isNotNullAndEmpty(teleNumber)) {
            useUserDetailResVO.setTeleNumber(PhoneNumberUtil.format(teleNumber));
        }

        // 勤務先名
        useUserDetailResVO.setWorkName(encryptorUtil.decrypt(applicationDoc.getWorkName()));
        // 勤務先電話番号
        String workTeleNumber = encryptorUtil.decrypt(applicationDoc.getWorkTeleNumber());
        if (Utils.isNotNullAndEmpty(workTeleNumber)) {
            useUserDetailResVO.setWorkTeleNumber(PhoneNumberUtil.format(workTeleNumber));
        }
        // 郵便番号
        String postCode = encryptorUtil.decrypt(applicationDoc.getPostCode());
        if (Utils.isNotNullAndEmpty(postCode)) {
            String postCodeFormat = postCode.substring(0, 3) + "-" + postCode.substring(3);
            useUserDetailResVO.setPostCode(postCodeFormat);
        }
        // 都道府県
        useUserDetailResVO.setAddress1(encryptorUtil.decrypt(applicationDoc.getAddress()));

        // 市区町村以下（カナ）
        useUserDetailResVO.setKanaAddress(encryptorUtil.decrypt(applicationDoc.getKanaAddress()));
        // E-mail
        useUserDetailResVO.setEmail(encryptorUtil.decrypt(applicationDoc.getEmail()));

        if (applicationDoc.getCardNoList().size() != 0 && applicationDoc.getCardNoList() != null) {
            // 口座名称
            String accountName = "";
            for (int i = 0; i < applicationDoc.getCardNoList().size(); i++) {
                accountName = accountName + encryptorUtil.decrypt(applicationDoc.getCardNoList().get(i).getAccountName()) + "\r\n";
            }
            useUserDetailResVO.setAccountName(accountName);
            // 店番
            String storeName = "";
            for (int i = 0; i < applicationDoc.getCardNoList().size(); i++) {
                storeName = storeName + encryptorUtil.decrypt(applicationDoc.getCardNoList().get(i).getStoreName()) + "\r\n";
            }
            useUserDetailResVO.setStoreName(storeName);
            // 科目名
            String kamokuName = "";
            for (int i = 0; i < applicationDoc.getCardNoList().size(); i++) {
                kamokuName = kamokuName + encryptorUtil.decrypt(applicationDoc.getCardNoList().get(i).getKamokuName()) + "\r\n";
            }
            useUserDetailResVO.setKamokuName(kamokuName);
            // 口座番号
            String accountNumber = "";
            for (int i = 0; i < applicationDoc.getCardNoList().size(); i++) {
                accountNumber = accountNumber
                        + encryptorUtil.decrypt(applicationDoc.getCardNoList().get(i).getAccountNumber()) + "\r\n";
            }
            useUserDetailResVO.setAccountNumber(accountNumber);
        }
        // 利用規約同意日時
        useUserDetailResVO.setAgreeDate(applicationDoc.getAgreeDate());
        // 最終リクエスト日時
        useUserDetailResVO.setLastReqTime(applicationDoc.getLastReqTime());

        
        return useUserDetailResVO;}

    private UseUserDetailResVO iYoUserInfoDoc(Database db, UseUserDetailReqVO detailReqVO,
            IYoUserInfoDoc applicationDoc) throws Exception {
	    UseUserDetailResVO useUserDetailResVO = new UseUserDetailResVO();
	    // 戻り値を設定
        // ユーザーID
        useUserDetailResVO.setUserId(detailReqVO.get_id());
        // ユーザータイプ
        useUserDetailResVO.setUserType(applicationDoc.getUserType());
        // 姓名
        String name = encryptorUtil.decrypt(applicationDoc.getLastName()) + " "
                + encryptorUtil.decrypt(applicationDoc.getFirstName());
        useUserDetailResVO.setName(name);
        // 姓名カナ
        String kanaName = encryptorUtil.decrypt(applicationDoc.getKanaLastName()) + " "
                + encryptorUtil.decrypt(applicationDoc.getKanaFirstName());
        useUserDetailResVO.setKanaName(kanaName);
        // 年齢
        useUserDetailResVO.setAge(encryptorUtil.decrypt(applicationDoc.getAge()));
        // 生年月日
        useUserDetailResVO.setBirthday(dateFormat(encryptorUtil.decrypt(applicationDoc.getBirthday())));

        // 性別
        useUserDetailResVO.setSex(applicationDoc.getSex());
        // 職業
        List<String> occupation = applicationDoc.getOccupation();
        Collections.sort(occupation);
        useUserDetailResVO.setOccupation(occupation);
        // その他職業
        useUserDetailResVO.setOtherOccupations(encryptorUtil.decrypt(applicationDoc.getOtherOccupations()));
        // 携帯電話番号
        String phoneNumber = encryptorUtil.decrypt(applicationDoc.getPhoneNumber());
        if (Utils.isNotNullAndEmpty(phoneNumber)) {
            useUserDetailResVO.setPhoneNumber(PhoneNumberUtil.format(phoneNumber));
        }
        // 自宅電話番号
        String teleNumber = encryptorUtil.decrypt(applicationDoc.getTeleNumber());
        if (Utils.isNotNullAndEmpty(teleNumber)) {
            useUserDetailResVO.setTeleNumber(PhoneNumberUtil.format(teleNumber));
        }

        // 勤務先名
        useUserDetailResVO.setWorkName(encryptorUtil.decrypt(applicationDoc.getWorkName()));
        // 勤務先電話番号
        String workTeleNumber = encryptorUtil.decrypt(applicationDoc.getWorkTeleNumber());
        if (Utils.isNotNullAndEmpty(workTeleNumber)) {
            useUserDetailResVO.setWorkTeleNumber(PhoneNumberUtil.format(workTeleNumber));
        }
        // 郵便番号
        String postCode = encryptorUtil.decrypt(applicationDoc.getPostCode());
        if (Utils.isNotNullAndEmpty(postCode)) {
            String postCodeFormat = postCode.substring(0, 3) + "-" + postCode.substring(3);
            useUserDetailResVO.setPostCode(postCodeFormat);
        }
        // 都道府県
        useUserDetailResVO.setAddress1(encryptorUtil.decrypt(applicationDoc.getAddress1()));
        // 市区町村以下
        useUserDetailResVO.setAddress2(encryptorUtil.decrypt(applicationDoc.getAddress2()));
        // 市区町村以下（カナ）
        useUserDetailResVO.setKanaAddress(encryptorUtil.decrypt(applicationDoc.getKanaAddress()));
        // E-mail
        useUserDetailResVO.setEmail(encryptorUtil.decrypt(applicationDoc.getEmail()));

        if (applicationDoc.getCardNoList().size() != 0 && applicationDoc.getCardNoList() != null) {
            // 口座名称
            String accountName = "";
            for (int i = 0; i < applicationDoc.getCardNoList().size(); i++) {
                accountName = accountName + encryptorUtil.decrypt(applicationDoc.getCardNoList().get(i).getAccountName()) + "\r\n";
            }
            useUserDetailResVO.setAccountName(accountName);
            // 店番
            String storeName = "";
            for (int i = 0; i < applicationDoc.getCardNoList().size(); i++) {
                storeName = storeName + encryptorUtil.decrypt(applicationDoc.getCardNoList().get(i).getStoreName()) + "\r\n";
            }
            useUserDetailResVO.setStoreName(storeName);
            // 科目名
            String kamokuName = "";
            for (int i = 0; i < applicationDoc.getCardNoList().size(); i++) {
                kamokuName = kamokuName + encryptorUtil.decrypt(applicationDoc.getCardNoList().get(i).getKamokuName()) + "\r\n";
            }
            useUserDetailResVO.setKamokuName(kamokuName);
            // 口座番号
            String accountNumber = "";
            for (int i = 0; i < applicationDoc.getCardNoList().size(); i++) {
                accountNumber = accountNumber
                        + encryptorUtil.decrypt(applicationDoc.getCardNoList().get(i).getAccountNumber()) + "\r\n";
            }
            useUserDetailResVO.setAccountNumber(accountNumber);
        }
        // 利用規約同意日時
        useUserDetailResVO.setAgreeDate(applicationDoc.getAgreeDate());
        // 最終リクエスト日時
        useUserDetailResVO.setLastReqTime(applicationDoc.getLastReqTime());

        return useUserDetailResVO;}

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
