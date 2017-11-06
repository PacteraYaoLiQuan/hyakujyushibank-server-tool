package com.scsk.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.ContentsDoc;
import com.scsk.request.vo.ContentsUpdateReqVO;
import com.scsk.response.vo.ContentsInitResVO;
import com.scsk.vo.BaseResVO;
import com.scsk.vo.ContentsInitVO;

@Service
public class ContentsTypeOrderIDService extends AbstractBLogic<BaseResVO, BaseResVO> {

	@Override
	protected void preExecute(BaseResVO input) throws Exception {

	}

	@Override
	protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
		ContentsInitResVO contentsInitResVO = new ContentsInitResVO();
		Database db = client.database(Constants.DB_NAME, false);
		ContentsUpdateReqVO input = (ContentsUpdateReqVO) baseResVO;
		String key = "appCD:\"" + input.getAppCD() + "\" AND typeCD:\"" + input.getTypeCD() + "\"";
		List<ContentsDoc> contentsList = repositoryUtil.getIndex(db,
				ApplicationKeys.INSIGHTINDEX_CONTENTS_REMOVEBYAPPCDANDTYPECD, key, ContentsDoc.class);
		List<ContentsInitVO> contentsInitList = new ArrayList<>();
		if (contentsList != null && contentsList.size() != 0) {
			for (ContentsDoc contentsDoc : contentsList) {
				ContentsInitVO contentsInitVO = new ContentsInitVO();
				contentsInitVO.set_id(contentsDoc.get_id());
				contentsInitVO.setAppCD(contentsDoc.getAppCD());
				contentsInitVO.setTypeCD(contentsDoc.getTypeCD());
				contentsInitVO.setContentsID(contentsDoc.getContentsID());
				contentsInitVO.setContentsTitle(contentsDoc.getContentsTitle());
				contentsInitVO.setDateFrom(contentsDoc.getDateFrom());
				contentsInitVO.setDateTo(contentsDoc.getDateTo());
				int orderID = contentsDoc.getOrderID();
				if(orderID==0){
					contentsInitVO.setOrderID("");
				}else{
					contentsInitVO.setOrderID(String.valueOf(orderID));
				}
				contentsInitList.add(contentsInitVO);
			}
		}
		Collections.sort(contentsInitList, new Comparator<ContentsInitVO>() {

			@Override
			public int compare(ContentsInitVO o1, ContentsInitVO o2) {
				int obj1 =0;
				if(o1.getOrderID().equals("")){
					obj1=1000;
				}else{
					obj1= Integer.parseInt(o1.getOrderID());
				}
				int obj2 = 0;
				if(o2.getOrderID().equals("")){
					obj2=1000;
				}else{
					obj2=Integer.parseInt(o2.getOrderID());
				}
				int i = obj1 - obj2;
				if (i == 0) {
					return o1.getContentsID().compareTo(o2.getContentsID());
				}
				return i;
			}
		});
		contentsInitResVO.setContentsInitList(contentsInitList);
		return contentsInitResVO;
	}
}
