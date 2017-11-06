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
import com.scsk.model.YamagataMsgDetailDoc;
import com.scsk.model.YamagataMsgOpenStatusDoc;
import com.scsk.model.YamagataMsgTitleDoc;
import com.scsk.request.vo.PushMessageListDeleteReqVO;
import com.scsk.util.ActionLog;
import com.scsk.util.ResultMessages;

@Service
public class PushMessageListDelService extends AbstractBLogic<PushMessageListDeleteReqVO, PushMessageListDeleteReqVO> {

	@Autowired
	private ActionLog actionLog;

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param PushMessageListDeleteReqVO
	 *            任意配信履歴一覧情報
	 */
	@Override
	protected void preExecute(PushMessageListDeleteReqVO pushMessageListDeleteReqVO) throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param PushMessageListDeleteReqVO
	 *            任意配信履歴一覧情報
	 * @return PushMessageListDeleteReqVO 一括削除情報
	 */
	@Override
	protected PushMessageListDeleteReqVO doExecute(CloudantClient client,
			PushMessageListDeleteReqVO pushMessageListDeleteReqVO) throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);
		String pushMessageListDelLog = "（件名：";
		for (int i = 0; i < pushMessageListDeleteReqVO.getDeleteList().size(); i++) {
			if (pushMessageListDeleteReqVO.getDeleteList().get(i).getSelect() == null) {
				continue;
			}
			if (pushMessageListDeleteReqVO.getDeleteList().get(i).getSelect() == true) {
				pushMessageListDelLog = pushMessageListDelLog
						+ pushMessageListDeleteReqVO.getDeleteList().get(i).getPushTitle() + "/";
				YamagataMsgTitleDoc yamagataMsgTitleDoc = new YamagataMsgTitleDoc();
				YamagataMsgDetailDoc yamagataMsgDetailDoc = new YamagataMsgDetailDoc();
				List<YamagataMsgOpenStatusDoc> yamagataMsgOpenStatusDocList = new ArrayList<>();
                // 削除前検索
                yamagataMsgOpenStatusDocList = repositoryUtil.getView(db,
                        ApplicationKeys.INSIGHTVIEW_MSGOPENSTATUS_MSGOPENSTATUS_PUSHTITLELOID, YamagataMsgOpenStatusDoc.class,
                        pushMessageListDeleteReqVO.getDeleteList().get(i).get_id());
                // pushMessage情報DBを削除
                for (YamagataMsgOpenStatusDoc yamagataMsgOpenStatusDoc : yamagataMsgOpenStatusDocList) {
                    repositoryUtil.removeByDocId(db, yamagataMsgOpenStatusDoc.get_id());
                }
                
                try {
                    // 削除前検索
                    yamagataMsgDetailDoc = repositoryUtil.find(db,
                            pushMessageListDeleteReqVO.getDeleteList().get(i).getPushDetailOid(),
                            YamagataMsgDetailDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1004);
                    throw new BusinessException(messages);
                }

                // pushMessage情報DBを削除
                repositoryUtil.removeByDocId(db, yamagataMsgDetailDoc.get_id());
                
				try {
					// 削除前検索
					yamagataMsgTitleDoc = repositoryUtil.find(db,
							pushMessageListDeleteReqVO.getDeleteList().get(i).get_id(), YamagataMsgTitleDoc.class);
				} catch (BusinessException e) {
					// エラーメッセージを出力、処理終了。
					ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1004);
					throw new BusinessException(messages);
				}
				// pushMessage情報DBを削除
				repositoryUtil.removeByDocId(db, yamagataMsgTitleDoc.get_id());
			}
		}
		pushMessageListDelLog = pushMessageListDelLog.substring(0, pushMessageListDelLog.length() - 1);
		actionLog.saveActionLog(Constants.ACTIONLOG_PUSH_MESSAGE_4 + pushMessageListDelLog + ")", db);
		return pushMessageListDeleteReqVO;
	}

}
