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
import com.scsk.model.ContentsDoc;
import com.scsk.model.ContentsTypeDoc;
import com.scsk.response.vo.ContentsTypeInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Service
public class ContentsTypeDeleteService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        ContentsTypeInitResVO input = (ContentsTypeInitResVO) baseResVO;
        Database db = client.database(Constants.DB_NAME, false);
        String actionLogStr = "";
        for (int i = 0; i < input.getContentsTypeInitList().size(); i++) {
            if (input.getContentsTypeInitList().get(i).getSelect() == null) {
                continue;
            }
            if (input.getContentsTypeInitList().get(i).getSelect() == true) {
                actionLogStr = actionLogStr + input.getContentsTypeInitList().get(i).getTypeName() + "/";
                ContentsTypeDoc contentsTypeDoc = new ContentsTypeDoc();
                try {
                    // 削除前検索
                    contentsTypeDoc = (ContentsTypeDoc) repositoryUtil.find(db,
                            input.getContentsTypeInitList().get(i).get_id(), ContentsTypeDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMLIST_1001);
                    throw new BusinessException(messages);
                }
                String key = "appCD:\"" + contentsTypeDoc.getAppCD() + "\" AND typeCD:\"" + contentsTypeDoc.getTypeCD()
                        + "\"";
                List<ContentsDoc> contentsList = repositoryUtil.getIndex(db,
                        ApplicationKeys.INSIGHTINDEX_CONTENTS_REMOVEBYAPPCDANDTYPECD, key, ContentsDoc.class);
                for (int j = 0; j < contentsList.size(); j++) {
                    repositoryUtil.removeByDocId(db, contentsList.get(j).get_id());
                }
                repositoryUtil.removeByDocId(db, contentsTypeDoc.get_id());
            }
        }
        actionLogStr = actionLogStr.substring(0, actionLogStr.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_TYPE_2 + "（" + "コンテンツ種別名：" + actionLogStr + "）", db);
        return input;
    }

}
