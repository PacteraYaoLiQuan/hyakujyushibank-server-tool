package com.scsk.service;

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
import com.scsk.model.ContentsAppDoc;
import com.scsk.model.ContentsTypeDoc;
import com.scsk.request.vo.ContentsTypeUpdateReqVO;
import com.scsk.response.vo.ContentsTypeUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Service
public class ContentsTypeUpdService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        ContentsTypeUpdateResVO contentsTypeUpdateResVO = new ContentsTypeUpdateResVO();
        Database db = client.database(Constants.DB_NAME, false);
        ContentsTypeDoc contentsTypeDoc = new ContentsTypeDoc();
        ContentsTypeUpdateReqVO input = (ContentsTypeUpdateReqVO) baseResVO;
        String actionLogStr = "（コンテンツ種別名：";
        if (input.getModeType().equals("2")) {
            try {
                contentsTypeDoc = (ContentsTypeDoc) repositoryUtil.find(db, input.get_id(), ContentsTypeDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTSAPP_1003);
                throw new BusinessException(messages);
            }
        }
        contentsTypeDoc.setAppCD(input.getAppCD());
        contentsTypeDoc.setTypeName(input.getTypeName());
        contentsTypeDoc.setTypeCD(input.getTypeCD());
        actionLogStr = actionLogStr + input.getTypeName();
        if (input.getModeType().equals("2")) {
            try {
                repositoryUtil.update(db, contentsTypeDoc);
                actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_TYPE_6 + actionLogStr + ")", db);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1006);
                throw new BusinessException(messages);
            }
        } else {
            String queryKey = "appCD:\"" + input.getAppCD() + "\"";
            List<ContentsAppDoc> contentsAppList = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_CONTENTSAPP_SEARCHINFO, queryKey, ContentsAppDoc.class);
            String key = "appCD:\"" + input.getAppCD() + "\" AND typeCD:\"" + input.getTypeCD() + "\"";
            List<ContentsTypeDoc> contentsTypeList = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_CONTENTSTYPE_SEARCHINFO, key, ContentsTypeDoc.class);
            if (contentsTypeList != null && contentsTypeList.size() != 0) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_CONTENTS_1004);
                throw new BusinessException(messages);
            }
            if (contentsAppList == null || contentsAppList.size() == 0) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_CONTENTS_1001);
                throw new BusinessException(messages);
            } else {
                try {
                    repositoryUtil.save(db, contentsTypeDoc);
                    actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_TYPE_4 + actionLogStr + ")", db);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
                    throw new BusinessException(messages);
                }
            }
        }

        return contentsTypeUpdateResVO;
    }

}
