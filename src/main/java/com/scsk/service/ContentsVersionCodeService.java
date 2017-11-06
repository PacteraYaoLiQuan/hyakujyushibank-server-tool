package com.scsk.service;

import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.response.vo.ContentsVersionCodeResVO;
import com.scsk.vo.BaseResVO;
@Service
public class ContentsVersionCodeService extends AbstractBLogic<BaseResVO, BaseResVO> {

	@Override
	protected void preExecute(BaseResVO input) throws Exception {
		
	}

	@Override
	protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
		ContentsVersionCodeResVO contentsVersionCodeResVO=new ContentsVersionCodeResVO();
		contentsVersionCodeResVO=(ContentsVersionCodeResVO) baseResVO;
		int[]versionCode=new int[100];
		for(int i=0;i<100;i++){
			versionCode[i]=i;
		}
		contentsVersionCodeResVO.setVersion(versionCode);
		return contentsVersionCodeResVO;
	}

}
