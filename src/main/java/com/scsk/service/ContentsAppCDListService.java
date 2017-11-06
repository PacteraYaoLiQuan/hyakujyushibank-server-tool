package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.ContentsAppDoc;
import com.scsk.response.vo.ContentsAppCDListResVO;
import com.scsk.vo.BaseResVO;
@Service
public class ContentsAppCDListService extends AbstractBLogic<BaseResVO, BaseResVO> {

	@Override
	protected void preExecute(BaseResVO input) throws Exception {
		
	}

	@Override
	protected BaseResVO doExecute(CloudantClient client, BaseResVO input) throws Exception {
		Database db = client.database(Constants.DB_NAME, false);
        ContentsAppCDListResVO contentsAppCDListResVO=new ContentsAppCDListResVO();
        List<ContentsAppDoc>contentsAppList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_CONTENTSAPPLIST_CONTENTSAPPLIST,
        		ContentsAppDoc.class);
        List<String>appCDList=new ArrayList();
        if (contentsAppList != null && contentsAppList.size() != 0) {
        	for(int i=0;i<contentsAppList.size();i++){
        		appCDList.add(contentsAppList.get(i).getAppCD()+"："+contentsAppList.get(i).getAppName());
        	}
        }
        contentsAppCDListResVO.setAppCDList(appCDList);
		return contentsAppCDListResVO;
	}

}
