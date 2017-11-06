package com.scsk.service;

import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.model.ContentsIdDoc;
import com.scsk.response.vo.ContentsUpdateResVO;
import com.scsk.vo.BaseResVO;

@Service
public class ContentsIDService extends AbstractBLogic<BaseResVO, BaseResVO>{

    @Override
    protected void preExecute(BaseResVO input) throws Exception {
        
    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);
        String contentsId = "";
        ContentsIdDoc contentsIdDoc = new ContentsIdDoc();
        contentsId = repositoryUtil.saveToResultId(db, contentsIdDoc);
        ContentsUpdateResVO input =new ContentsUpdateResVO();
        input.setContentsID(contentsId);
        return input;
    }

}
