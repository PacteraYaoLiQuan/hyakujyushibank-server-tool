package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.AccountAppImageDoc;
import com.scsk.request.vo.AccountAppDetailReqVO;
import com.scsk.response.vo.AccountApp114DetailResVO;
import com.scsk.response.vo.AccountAppDetailResVO;
import com.scsk.response.vo.AccountAppYamaGataDetailResVO;
import com.scsk.response.vo.BaseResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * 申込詳細画像検索サービス。<br>
 * <br>
 * 申込詳細画像検索を実装するロジック。<br>
 * 申込詳細画像を表示するロジック。<br>
 */
@Service
public class AccountAppDetailImageSelService extends AbstractBLogic<BaseResVO, BaseResVO> {

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
			AccountAppDetailResVO applicationDetailResVO = hirosima(db, baseResVO);
			return applicationDetailResVO;
		} else if ("0122".equals(bank_cd)) {
			AccountAppYamaGataDetailResVO applicationDetailResVO = yamagata(db, baseResVO);
			return applicationDetailResVO;
		}else if("0173".equals(bank_cd)){
		    AccountApp114DetailResVO applicationDetailResVO=hyakujyushi(db,baseResVO);
		    return applicationDetailResVO;
		}
		return null;
	}
//114
	private AccountApp114DetailResVO hyakujyushi(Database db, BaseResVO baseResVO) throws Exception {
	    AccountApp114DetailResVO applicationDetailResVO = new AccountApp114DetailResVO();
        AccountAppDetailReqVO accountAppDetailReqVO = new AccountAppDetailReqVO();
        accountAppDetailReqVO = (AccountAppDetailReqVO) baseResVO;

        // 画像情報取得
        AccountAppImageDoc accountAppImageDoc = new AccountAppImageDoc();
        try {
            accountAppImageDoc = (AccountAppImageDoc) repositoryUtil.find(db, accountAppDetailReqVO.get_id(),
                    AccountAppImageDoc.class);
            // 本人確認書類画像
            applicationDetailResVO
                    .setIdentificationImage(encryptorUtil.decrypt(accountAppImageDoc.getIdentificationImage()));
            // 本人確認書類画像
            applicationDetailResVO
                    .setIdentificationImageBack(encryptorUtil.decrypt(accountAppImageDoc.getIdentificationImageBack()));
            // 生活状況確認書類画像
            // applicationDetailResVO.setLivingConditionsImage(encryptorUtil
            // .decrypt(accountAppImageDoc.getLivingConditionsImage()));
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1003);
            throw new BusinessException(messages);
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

		// 画像情報取得
		AccountAppImageDoc accountAppImageDoc = new AccountAppImageDoc();
		try {
			accountAppImageDoc = (AccountAppImageDoc) repositoryUtil.find(db, accountAppDetailReqVO.get_id(),
					AccountAppImageDoc.class);
			// 本人確認書類画像
			applicationDetailResVO
					.setIdentificationImage(encryptorUtil.decrypt(accountAppImageDoc.getIdentificationImage()));
			// 本人確認書類画像
			applicationDetailResVO
					.setIdentificationImageBack(encryptorUtil.decrypt(accountAppImageDoc.getIdentificationImageBack()));
			// 生活状況確認書類画像
			// applicationDetailResVO.setLivingConditionsImage(encryptorUtil
			// .decrypt(accountAppImageDoc.getLivingConditionsImage()));
		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1003);
			throw new BusinessException(messages);
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

		// 画像情報取得
		AccountAppImageDoc accountAppImageDoc = new AccountAppImageDoc();
		try {
			accountAppImageDoc = (AccountAppImageDoc) repositoryUtil.find(db, accountAppDetailReqVO.get_id(),
					AccountAppImageDoc.class);
			// 本人確認書類画像
			applicationDetailResVO
					.setIdentificationImage(encryptorUtil.decrypt(accountAppImageDoc.getIdentificationImage()));
			// 本人確認書類画像
			applicationDetailResVO
					.setIdentificationImageBack(encryptorUtil.decrypt(accountAppImageDoc.getIdentificationImageBack()));
			// 生活状況確認書類画像
			// applicationDetailResVO.setLivingConditionsImage(encryptorUtil
			// .decrypt(accountAppImageDoc.getLivingConditionsImage()));
		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPDETAIL_1003);
			throw new BusinessException(messages);
		}
		return applicationDetailResVO;
	}
}
