package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
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
public class ContentsAppSelService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        ContentsAppUpdateResVO contentsAppUpdateResVO = new ContentsAppUpdateResVO();
        Database db = client.database(Constants.DB_NAME, false);
        ContentsAppDoc contentsAppDoc = new ContentsAppDoc();
        ContentsAppUpdateReqVO input = (ContentsAppUpdateReqVO) baseResVO;
        String actionLogStr="（アプリケーション名：";
        try {
            contentsAppDoc = (ContentsAppDoc) repositoryUtil.find(db, input.get_id(), ContentsAppDoc.class);
            actionLogStr=actionLogStr+contentsAppDoc.getAppName();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTSAPP_1003);
            throw new BusinessException(messages);
        }
        contentsAppUpdateResVO.setAppCD(contentsAppDoc.getAppCD());
        contentsAppUpdateResVO.setAppName(contentsAppDoc.getAppName());
        contentsAppUpdateResVO.setUserFlag(contentsAppDoc.getUserFlag());
        actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_APPLICATION_5+actionLogStr+")", db);
        return contentsAppUpdateResVO;
    }

}
