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
import com.scsk.response.vo.ContentsAppInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.vo.BaseResVO;
import com.scsk.vo.ContentsAppInitVO;

@Service
public class ContentsAppListService extends AbstractBLogic<BaseResVO, BaseResVO>{
    
    @Autowired
    private ActionLog actionLog;
    
	@Override
	protected void preExecute(BaseResVO input) throws Exception {
		
	}

	@Override
	protected ContentsAppInitResVO doExecute(CloudantClient client, BaseResVO input) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        ContentsAppInitResVO contentsAppInitResVO=new ContentsAppInitResVO();
        List<ContentsAppDoc>contentsAppList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_CONTENTSAPPLIST_CONTENTSAPPLIST,
        		ContentsAppDoc.class);
        List<ContentsAppInitVO> contentsAppInitList = new ArrayList<>();
        if (contentsAppList != null && contentsAppList.size() != 0) {
        	for(ContentsAppDoc contentsAppDoc:contentsAppList){
        		ContentsAppInitVO contentsAppInitVO=new ContentsAppInitVO();
        		contentsAppInitVO.set_id(contentsAppDoc.get_id());
        		contentsAppInitVO.setAppCD(contentsAppDoc.getAppCD());
        		contentsAppInitVO.setAppName(contentsAppDoc.getAppName());
        		contentsAppInitVO.setUserFlag(contentsAppDoc.getUserFlag());
        		contentsAppInitList.add(contentsAppInitVO);
        	}
        }
        contentsAppInitResVO.setContentsAppInitList(contentsAppInitList);
        actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_APPLICATION_1, db);
		return contentsAppInitResVO;
	}

}
