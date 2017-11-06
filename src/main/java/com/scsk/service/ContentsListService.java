package com.scsk.service;

import java.text.SimpleDateFormat;
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
import com.scsk.model.ContentsDoc;
import com.scsk.model.ContentsTypeDoc;
import com.scsk.response.vo.ContentsInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.vo.BaseResVO;
import com.scsk.vo.ContentsInitVO;

@Service
public class ContentsListService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO input) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        ContentsInitResVO contentsInitResVO = new ContentsInitResVO();
        List<ContentsDoc> contentsList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_CONTENTSLIST_CONTENTSLIST, ContentsDoc.class);
        List<ContentsInitVO> contentsInitList = new ArrayList<>();

        if (contentsList != null && contentsList.size() != 0) {
            for (ContentsDoc contentsDoc : contentsList) {
                String queryKey = "appCD:\"" + contentsDoc.getAppCD() + "\"";
                List<ContentsAppDoc> contentsAppList = repositoryUtil.getIndex(db,
                        ApplicationKeys.INSIGHTINDEX_CONTENTSAPP_SEARCHINFO, queryKey, ContentsAppDoc.class);
                String key = "appCD:\"" + contentsDoc.getAppCD() + "\" AND typeCD:\"" + contentsDoc.getTypeCD() + "\"";
                List<ContentsTypeDoc> contentsTypeList = repositoryUtil.getIndex(db,
                        ApplicationKeys.INSIGHTINDEX_CONTENTSTYPE_SEARCHINFO, key, ContentsTypeDoc.class);
                ContentsInitVO contentsInitVO = new ContentsInitVO();
                contentsInitVO.set_id(contentsDoc.get_id());
                contentsInitVO.setAppCD(contentsAppList.get(0).getAppCD() + "：" + contentsAppList.get(0).getAppName());
                contentsInitVO
                        .setTypeCD(contentsTypeList.get(0).getTypeCD() + "：" + contentsTypeList.get(0).getTypeName());
                contentsInitVO.setContentsID(contentsDoc.getContentsID());
                contentsInitVO.setContentsTitle(contentsDoc.getContentsTitle());
                java.util.Date dateFrom = sdf.parse(contentsDoc.getDateFrom());
                java.util.Date dateTo = sdf.parse(contentsDoc.getDateTo());
                contentsInitVO.setDateFrom(simpleDateFormat.format(dateFrom));
                contentsInitVO.setDateTo(simpleDateFormat.format(dateTo));
                contentsInitList.add(contentsInitVO);
            }
        }
        contentsInitResVO.setContentsInitList(contentsInitList);
        actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_CONTENTS_1, db);
        return contentsInitResVO;
    }

}
