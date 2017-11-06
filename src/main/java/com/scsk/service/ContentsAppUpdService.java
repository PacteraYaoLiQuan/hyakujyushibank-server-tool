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
import com.scsk.request.vo.ContentsAppUpdateReqVO;
import com.scsk.response.vo.ContentsAppUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Service
public class ContentsAppUpdService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        ContentsAppUpdateResVO output = new ContentsAppUpdateResVO();
        Database db = client.database(Constants.DB_NAME, false);
        ContentsAppDoc contentsAppDoc = new ContentsAppDoc();
        ContentsAppUpdateReqVO input = (ContentsAppUpdateReqVO) baseResVO;
        String actionLogStr = "（アプリケーション名：";
        if (input.getModeType().equals("2")) {
            try {
                contentsAppDoc = (ContentsAppDoc) repositoryUtil.find(db, input.get_id(), ContentsAppDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTSAPP_1003);
                throw new BusinessException(messages);
            }
        }
        contentsAppDoc.setAppCD(input.getAppCD());
        contentsAppDoc.setAppName(input.getAppName());
        contentsAppDoc.setUserFlag(input.getUserFlag());
        
        actionLogStr = actionLogStr + input.getAppName();
        if (input.getModeType().equals("2")) {
            try {
                repositoryUtil.update(db, contentsAppDoc);
                actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_APPLICATION_6 + actionLogStr + ")", db);
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
            if (contentsAppList != null && contentsAppList.size() != 0) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_CONTENTSAPP_1001);
                throw new BusinessException(messages);
            } else {
                try {
                    repositoryUtil.save(db, contentsAppDoc);
                    actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_APPLICATION_4 + actionLogStr + ")", db);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
                    throw new BusinessException(messages);
                }
            }
        }

        return output;
    }

}
