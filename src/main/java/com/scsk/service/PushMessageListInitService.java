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
import com.scsk.model.YamagataMsgOpenStatusDoc;
import com.scsk.model.YamagataMsgTitleDoc;
import com.scsk.response.vo.PushMessageInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.PushMessageInitVO;

/**
 * ファイル一覧初期化検索サービス。<br>
 * <br>
 * ファイル一覧初期化検索を実装するロジック。<br>
 */
@Service
public class PushMessageListInitService extends AbstractBLogic<PushMessageInitResVO, PushMessageInitResVO> {

	@Autowired
	private ActionLog actionLog;

	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param なし
	 *            検索条件
	 */
	@Override
	protected void preExecute(PushMessageInitResVO ResVO) throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param なし
	 *            検索条件
	 */
	@Override
	protected PushMessageInitResVO doExecute(CloudantClient client, PushMessageInitResVO ResVO) throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);

		// ファイル一覧初期データを取得
		List<YamagataMsgTitleDoc> titleInfoDocList = new ArrayList<>();
		List<YamagataMsgOpenStatusDoc> messageOpenList = new ArrayList<>();
		List<PushMessageInitVO> pushMessageList = new ArrayList<>();
		PushMessageInitResVO pushMessageInitResVO = new PushMessageInitResVO();
		// 送達＆開封数（Android）取得
		try {
			messageOpenList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_MSGOPENSTATUS_MSGOPENSTATUS,
					YamagataMsgOpenStatusDoc.class);
		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSHMESSAGE_1004);
			throw new BusinessException(messages);
		}
		try {
			titleInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_PUSHMESSAGELIST_MSGTITLE,
					YamagataMsgTitleDoc.class);
			if (titleInfoDocList != null && titleInfoDocList.size() != 0) {
				for (YamagataMsgTitleDoc doc : titleInfoDocList) {
					PushMessageInitVO pushMessageInitVO = new PushMessageInitVO();
					// ファイル一覧初期化用データを戻る
					pushMessageInitVO.set_id(doc.get_id());
					pushMessageInitVO.set_rev(doc.get_rev());
					pushMessageInitVO.setPushTitle(doc.getPushTitle());
					pushMessageInitVO.setPushDate(doc.getPushDate());
					pushMessageInitVO.setPushAccessUser(doc.getPushAccessUser());
					pushMessageInitVO.setPushCnt(doc.getPushCnt());
					pushMessageInitVO.setPushSuccessCnt(doc.getPushSuccessCnt());
					pushMessageInitVO.setPushFaileCnt(doc.getPushFaileCnt());
					pushMessageInitVO.setPushType(doc.getPushType());
					if ("1".equals(doc.getPushType())) {
					    pushMessageInitVO.setPushAllFlag(true);
					    pushMessageInitVO.setPushOthreFlag(false);
					} else {
			            pushMessageInitVO.setPushAllFlag(false);
                        pushMessageInitVO.setPushOthreFlag(true);
					}
					
					// 配信ステータス
					if (doc.getPushFaileCnt() == 0) {
						pushMessageInitVO.setPushStatus("配信成功（全件）");
					} else if (doc.getPushSuccessCnt() == 0) {
						pushMessageInitVO.setPushStatus("配信失敗（全件）");
					} else {
						pushMessageInitVO.setPushStatus("配信失敗（一部）");
					}
					int andOpenCnt = 0;
					int andCloseCnt = 0;
					if (messageOpenList != null && messageOpenList.size() != 0) {
						for (YamagataMsgOpenStatusDoc yamagataMsgOpenStatusDoc : messageOpenList) {
							if (doc.get_id().equals(yamagataMsgOpenStatusDoc.getPushTitlelOid())) {
								if ("1".equals(yamagataMsgOpenStatusDoc.getOpenKBN())
										&& "1".equals(yamagataMsgOpenStatusDoc.getArriveKBN())) {
									andOpenCnt++;
								} else if ("0".equals(yamagataMsgOpenStatusDoc.getOpenKBN())
                                        && "1".equals(yamagataMsgOpenStatusDoc.getArriveKBN())) {
									andCloseCnt++;
								}
							}
						}
					}
					pushMessageInitVO.setAndOpenCnt(andOpenCnt);
					pushMessageInitVO.setAndCloseCnt(andCloseCnt);
					pushMessageInitVO.setPushDetailOid(doc.getPushDetailOid());
					pushMessageList.add(pushMessageInitVO);
				}
			}
		} catch (BusinessException e) {
			// e.printStackTrace();
			LogInfoUtil.LogDebug(e.getMessage());
			// エラーメッセージを出力、処理終了。
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_PUSHMESSAGE_1004);
			throw new BusinessException(messages);
		}

		// ファイル一覧初期データを取得
		pushMessageInitResVO.setMessageList(pushMessageList);
		actionLog.saveActionLog(Constants.ACTIONLOG_PUSH_MESSAGE_1);
		return pushMessageInitResVO;
	}
}
