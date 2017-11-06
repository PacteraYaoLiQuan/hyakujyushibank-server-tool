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
import com.scsk.model.ContentsDoc;
import com.scsk.model.ContentsTypeDoc;
import com.scsk.response.vo.ContentsAppInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Service
public class ContentsAppDeleteService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        ContentsAppInitResVO input = (ContentsAppInitResVO) baseResVO;
        Database db = client.database(Constants.DB_NAME, false);
        String actionLogStr = "";
        for (int i = 0; i < input.getContentsAppInitList().size(); i++) {
            if (input.getContentsAppInitList().get(i).getSelect() == null) {
                continue;
            }
            if (input.getContentsAppInitList().get(i).getSelect() == true) {
                actionLogStr = actionLogStr + input.getContentsAppInitList().get(i).getAppName() + "/";
                ContentsAppDoc contentsAppDoc = new ContentsAppDoc();
                try {
                    // 削除前検索
                    contentsAppDoc = (ContentsAppDoc) repositoryUtil.find(db,
                            input.getContentsAppInitList().get(i).get_id(), ContentsAppDoc.class);
                    String queryKey = "appCD:\"" + contentsAppDoc.getAppCD() + "\"";
                    List<ContentsTypeDoc> contentsTypeList = repositoryUtil.getIndex(db,
                            ApplicationKeys.INSIGHTINDEX_CONTENTSTYPE_REMOVEBYAPPCD, queryKey, ContentsTypeDoc.class);
                    for (int j = 0; j < contentsTypeList.size(); j++) {
                        String docId = contentsTypeList.get(j).get_id();
                        try {
                            repositoryUtil.removeByDocId(db, docId);
                        } catch (Exception e) {
                            LogInfoUtil.LogError(e.getMessage());
                        }
                    }
                    String key = "appCD:\"" + contentsAppDoc.getAppCD() + "\"";
                    List<ContentsDoc> contentsList = repositoryUtil.getIndex(db,
                            ApplicationKeys.INSIGHTINDEX_CONTENTS_REMOVEBYAPPCD, key, ContentsDoc.class);
                    for (int j = 0; j < contentsList.size(); j++) {
                        String docId = contentsList.get(j).get_id();
                        try {
                            repositoryUtil.removeByDocId(db, docId);
                        } catch (Exception e) {
                            LogInfoUtil.LogError(e.getMessage());
                        }
                    }
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMLIST_1001);
                    throw new BusinessException(messages);
                }

                repositoryUtil.removeByDocId(db, contentsAppDoc.get_id());

            }
        }
        actionLogStr = actionLogStr.substring(0, actionLogStr.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_APPLICATION_2 + "（" + "アプリケーション名：" + actionLogStr + "）",
                db);
        return input;
    }

}
