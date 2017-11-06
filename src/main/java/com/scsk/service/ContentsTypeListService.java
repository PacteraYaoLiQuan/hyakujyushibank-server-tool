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
import com.scsk.model.ContentsAppDoc;
import com.scsk.model.ContentsTypeDoc;
import com.scsk.response.vo.ContentsTypeInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.vo.BaseResVO;
import com.scsk.vo.ContentsTypeInitVO;

@Service
public class ContentsTypeListService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO input) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        ContentsTypeInitResVO contentsTypeInitResVO = new ContentsTypeInitResVO();
        List<ContentsTypeDoc> contentsTypeList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_CONTENTSTYPELIST_CONTENTSTYPELIST, ContentsTypeDoc.class);

        List<ContentsTypeInitVO> contentsTypeInitList = new ArrayList<>();
        if (contentsTypeList != null && contentsTypeList.size() != 0) {
            for (ContentsTypeDoc contentsTypeDoc : contentsTypeList) {
                ContentsTypeInitVO contentsTypeInitVO = new ContentsTypeInitVO();
                contentsTypeInitVO.set_id(contentsTypeDoc.get_id());
                String queryKey = "appCD:\"" + contentsTypeDoc.getAppCD() + "\"";
                List<ContentsAppDoc> contentsAppList = repositoryUtil.getIndex(db,
                        ApplicationKeys.INSIGHTINDEX_CONTENTSAPP_SEARCHINFO, queryKey, ContentsAppDoc.class);
                contentsTypeInitVO
                        .setAppCD(contentsAppList.get(0).getAppCD() + "：" + contentsAppList.get(0).getAppName());
                contentsTypeInitVO.setTypeName(contentsTypeDoc.getTypeName());
                contentsTypeInitVO.setTypeCD(contentsTypeDoc.getTypeCD());
                contentsTypeInitList.add(contentsTypeInitVO);
            }
        }
        contentsTypeInitResVO.setContentsTypeInitList(contentsTypeInitList);
        actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_TYPE_1, db);
        return contentsTypeInitResVO;
    }

}
