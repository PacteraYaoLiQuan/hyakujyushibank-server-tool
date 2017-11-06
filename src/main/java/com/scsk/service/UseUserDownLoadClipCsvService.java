package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.ClipInfo;
import com.scsk.model.FollowInfo;
import com.scsk.model.IYoUserInfoDoc;
import com.scsk.request.vo.UseUserDownLoadCsvButtonReqVO;
import com.scsk.response.vo.UseUserDownLoadCsvButtonResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.UseUserDownLoadInitVO;

/**
 * CSV一覧検索サービス。<br>
 * <br>
 * CSV一覧検索を実装するロジック。<br>
 */
@Service
public class UseUserDownLoadClipCsvService
		extends AbstractBLogic<UseUserDownLoadCsvButtonReqVO, UseUserDownLoadCsvButtonResVO> {

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
	protected void preExecute(UseUserDownLoadCsvButtonReqVO useUserDownLoadCsvButtonReqVO) throws Exception {

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
	protected UseUserDownLoadCsvButtonResVO doExecute(CloudantClient client,
			UseUserDownLoadCsvButtonReqVO useUserDownLoadCsvButtonReqVO) throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		// String useUserListCsvLog = "(ユーザータイプ：";
		// 受付進捗管理表出力用データを取得
		UseUserDownLoadCsvButtonResVO useUserDownLoadCsvButtonResVO = new UseUserDownLoadCsvButtonResVO();
		List<UseUserDownLoadInitVO> downLoadList = new ArrayList<>();

		for (int i = 0; i < useUserDownLoadCsvButtonReqVO.getCsvList().size(); i++) {
			if (useUserDownLoadCsvButtonReqVO.getCsvList().get(i).getSelect() == null) {
				continue;
			}
			// 一覧選択したデータ
			if (useUserDownLoadCsvButtonReqVO.getCsvList().get(i).getSelect() == true) {
				List<ClipInfo> clipInfoList = new ArrayList<>();
				IYoUserInfoDoc userInfoDoc = new IYoUserInfoDoc();
				try {
					// データを取得
					String queryKey = useUserDownLoadCsvButtonReqVO.getCsvList().get(i).get_id();
					clipInfoList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_CLIPINFOLIST_CLIPINFO_USERID,
							ClipInfo.class, queryKey);
					// useUserListCsvLog = useUserListCsvLog +
					// userInfoDoc.getUserType() + "/";
				} catch (BusinessException e) {
					// エラーメッセージを出力、処理終了。
					ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
					throw new BusinessException(messages);
				}
				try {
					// データを取得
					userInfoDoc = repositoryUtil.find(db, useUserDownLoadCsvButtonReqVO.getCsvList().get(i).get_id(),
							IYoUserInfoDoc.class);
					// useUserListCsvLog = useUserListCsvLog +
					// userInfoDoc.getUserType() + "/";
				} catch (BusinessException e) {
					// エラーメッセージを出力、処理終了。
					ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNTAPPLIST_1001);
					throw new BusinessException(messages);
				}
				if (clipInfoList != null && clipInfoList.size() != 0) {
					for (ClipInfo clipInfo : clipInfoList) {
						UseUserDownLoadInitVO useUserDownLoadInitVO = new UseUserDownLoadInitVO();
						// ユーザーID
						useUserDownLoadInitVO.setUserId(encryptorUtil.decrypt(userInfoDoc.getLastName()) + " "
								+ encryptorUtil.decrypt(userInfoDoc.getFirstName()));
						// 記事タイトル
						useUserDownLoadInitVO.setTitle(encryptorUtil.decrypt(clipInfo.getTitle()));
						// 記事公開日時
						useUserDownLoadInitVO.setPublishedAt(clipInfo.getPublishedAt());
						// ソース名
						useUserDownLoadInitVO.setSourceName(encryptorUtil.decrypt(clipInfo.getSourceName()));
						downLoadList.add(useUserDownLoadInitVO);
					}
				} else {
					UseUserDownLoadInitVO useUserDownLoadInitVO = new UseUserDownLoadInitVO();
					useUserDownLoadInitVO.setUserId("");
					// トピック名
					useUserDownLoadInitVO.setTitle("");
					// 記事公開日時
					useUserDownLoadInitVO.setPublishedAt("");
					// ソース名
					useUserDownLoadInitVO.setSourceName("");
					downLoadList.add(useUserDownLoadInitVO);
				}
			}
		}

		useUserDownLoadCsvButtonResVO.setUseUserDownLoadList(downLoadList);
		// useUserListCsvLog = useUserListCsvLog.substring(0,
		// useUserListCsvLog.length() - 1);
		actionLog.saveActionLog(Constants.ACTIONLOG_DOWNLOAD_2);
		return useUserDownLoadCsvButtonResVO;
	}
}
