package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.ContentsDoc;
import com.scsk.response.vo.ContentsInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Service
public class ContentsDeleteService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        ContentsInitResVO input = (ContentsInitResVO) baseResVO;
        Database db = client.database(Constants.DB_NAME, false);
        String actionLogStr = "";
        for (int i = 0; i < input.getContentsInitList().size(); i++) {
            if (input.getContentsInitList().get(i).getSelect() == null) {
                continue;
            }
            if (input.getContentsInitList().get(i).getSelect() == true) {
                actionLogStr = actionLogStr + input.getContentsInitList().get(i).getContentsTitle() + "/";
                ContentsDoc contentsDoc = new ContentsDoc();
                try {
                    // 削除前検索
                    contentsDoc = (ContentsDoc) repositoryUtil.find(db, input.getContentsInitList().get(i).get_id(),
                            ContentsDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_STOREATMLIST_1001);
                    throw new BusinessException(messages);
                }
                // 店舗情報DBを削除
                repositoryUtil.removeByDocId(db, contentsDoc.get_id());
            }
        }
        actionLogStr = actionLogStr.substring(0, actionLogStr.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_CONTENTS_2 + "（" + "コンテンツ名：" + actionLogStr + "）",
                db);
        return input;
    }

}
