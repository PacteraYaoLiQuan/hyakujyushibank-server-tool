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
public class ContentsTypeSelService extends AbstractBLogic<BaseResVO, BaseResVO> {

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
        try {
            contentsTypeDoc = (ContentsTypeDoc) repositoryUtil.find(db, input.get_id(), ContentsTypeDoc.class);
            actionLogStr = actionLogStr + contentsTypeDoc.getTypeName();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTSTYPE_1002);
            throw new BusinessException(messages);
        }
        String queryKey = "appCD:\"" + contentsTypeDoc.getAppCD() + "\"";
        List<ContentsAppDoc> contentsAppList = repositoryUtil.getIndex(db,
                ApplicationKeys.INSIGHTINDEX_CONTENTSAPP_SEARCHINFO, queryKey, ContentsAppDoc.class);
        contentsTypeUpdateResVO.setAppCD(contentsAppList.get(0).getAppCD() + "：" + contentsAppList.get(0).getAppName());
        contentsTypeUpdateResVO.setTypeName(contentsTypeDoc.getTypeName());
        contentsTypeUpdateResVO.setTypeCD(contentsTypeDoc.getTypeCD());
        actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_TYPE_5 + actionLogStr + ")", db);
        return contentsTypeUpdateResVO;

    }

}
