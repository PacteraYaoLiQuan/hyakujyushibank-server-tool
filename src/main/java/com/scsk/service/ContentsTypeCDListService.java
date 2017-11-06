package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.ContentsTypeDoc;
import com.scsk.request.vo.ContentsTypeUpdateReqVO;
import com.scsk.response.vo.ContentsTypeCDListResVO;
import com.scsk.util.SortList;
import com.scsk.vo.BaseResVO;
@Service
public class ContentsTypeCDListService extends AbstractBLogic<BaseResVO, BaseResVO> {

	@Override
	protected void preExecute(BaseResVO input) throws Exception {
		
	}

	@Override
	protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
		ContentsTypeCDListResVO contentsTypeCDListResVO=new ContentsTypeCDListResVO();
		Database db = client.database(Constants.DB_NAME, false);
		List<String>typeCDList=new ArrayList();
		ContentsTypeUpdateReqVO input=(ContentsTypeUpdateReqVO)baseResVO;
		String queryKey="appCD:\""+input.getAppCD()+ "\"";
		List<ContentsTypeDoc> contentsTypeList = repositoryUtil.getIndex(db,
                ApplicationKeys.INSIGHTINDEX_CONTENTSTYPE_REMOVEBYAPPCD, queryKey, ContentsTypeDoc.class);
		SortList<ContentsTypeDoc> sortList=new SortList<ContentsTypeDoc>();
		sortList.Sort(contentsTypeList, "getTypeCD", null);
		for(int i=0;i<contentsTypeList.size();i++){
			typeCDList.add(contentsTypeList.get(i).getTypeCD()+"："+contentsTypeList.get(i).getTypeName());
		}
		contentsTypeCDListResVO.setTypeCDList(typeCDList);
		return contentsTypeCDListResVO;
	}

}
