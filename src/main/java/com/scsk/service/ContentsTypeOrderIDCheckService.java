package com.scsk.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.ContentsDoc;
import com.scsk.request.vo.ContentsOrderIDReqVO;
import com.scsk.response.vo.ContentsInitResVO;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Service
public class ContentsTypeOrderIDCheckService extends AbstractBLogic<BaseResVO, BaseResVO> {

	@Override
	protected void preExecute(BaseResVO input) throws Exception {

	}

	@Override
	protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
		ContentsInitResVO contentsInitResVO = new ContentsInitResVO();
		ContentsOrderIDReqVO input = (ContentsOrderIDReqVO) baseResVO;
		Database db = client.database(Constants.DB_NAME, false);
		String[] orderIDArr = input.getOrderIDArr();

		String key = "appCD:\"" + input.getAppCD() + "\" AND typeCD:\"" + input.getTypeCD() + "\"";
		List<ContentsDoc> contentsList = repositoryUtil.getIndex(db,
				ApplicationKeys.INSIGHTINDEX_CONTENTS_REMOVEBYAPPCDANDTYPECD, key, ContentsDoc.class);
		for (int i = 0; i < orderIDArr.length; i++) {
			String contentsKey = "appCD:\"" + input.getAppCD() + "\" AND typeCD:\"" + input.getTypeCD()
					+ "\" AND contentsID:\"" + orderIDArr[i].split(":")[0] + "\"";
			List<ContentsDoc> orderID = repositoryUtil.getIndex(db, ApplicationKeys.INSIGHTINDEX_CONTENTS_SEARCHINFO,
					contentsKey, ContentsDoc.class);
			try {
			    orderID.get(0).setOrderID(Integer.parseInt(orderIDArr[i].split(":")[1]));
				repositoryUtil.update(db, orderID.get(0));
			} catch (BusinessException e) {
				// e.printStackTrace();
				LogInfoUtil.LogDebug(e.getMessage());
				// エラーメッセージを出力、処理終了。
				ResultMessages messages = ResultMessages.error().add("");
				throw new BusinessException(messages);
			} catch(IndexOutOfBoundsException exception){
			    LogInfoUtil.LogDebug(exception.getMessage());
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTSAPP_1003);
                throw new BusinessException(messages);
			}
		}
		List<ContentsDoc> newContentsList = repositoryUtil.getIndex(db,
				ApplicationKeys.INSIGHTINDEX_CONTENTS_REMOVEBYAPPCDANDTYPECD, key, ContentsDoc.class);
		String[] newOrderIDArr = new String[newContentsList.size()];
		for (int i = 0; i < newContentsList.size(); i++) {
			newOrderIDArr[i] = String.valueOf(newContentsList.get(i).getOrderID());
		}
		Boolean newFlag = checkRepeat(newOrderIDArr);
		if (newFlag) {
			return contentsInitResVO;
		} else {
			for (int i = 0; i < newContentsList.size(); i++) {
				String docID = newContentsList.get(i).get_id();
				try {
					repositoryUtil.removeByDocId(db, docID);
				} catch (BusinessException e) {
					// e.printStackTrace();
					LogInfoUtil.LogDebug(e.getMessage());
					// エラーメッセージを出力、処理終了。
					ResultMessages messages = ResultMessages.error().add("");
					throw new BusinessException(messages);
				}
			}
			for (int i = 0; i < contentsList.size(); i++) {
				contentsList.get(i).set_id(null);
				contentsList.get(i).set_rev(null);
				try {
					repositoryUtil.save(db, contentsList.get(i));
				} catch (BusinessException e) {
					// e.printStackTrace();
					LogInfoUtil.LogDebug(e.getMessage());
					// エラーメッセージを出力、処理終了。
					ResultMessages messages = ResultMessages.error().add("");
					throw new BusinessException(messages);
				}
			}
			ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ORDERID_1001);
			throw new BusinessException(messages);
		}
	}

	public static boolean checkRepeat(String[] array) {
		Set<String> set = new HashSet<String>();
		for (String str : array) {
			set.add(str);
		}
		int l = 0;
		for (String string : array) {
			if (string.equals("0")) {
				l++;
			}
		}
		if (set.size() == array.length) {
			return true;
		} else {
			if (set.size() == array.length-l+1) {
				return true;
			} else {
				return false;
			}
		}
	}
}
