package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.model.ContentsDoc;
import com.scsk.response.vo.GeneralPurposeOutCsvResVO;
import com.scsk.vo.BaseResVO;
import com.scsk.vo.GeneralPurposeInitVO;

@Service
public class GeneralPurposeDownService extends AbstractBLogic<BaseResVO, List<GeneralPurposeOutCsvResVO>> {


    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected List<GeneralPurposeOutCsvResVO> doExecute(CloudantClient client, BaseResVO input) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        List<GeneralPurposeOutCsvResVO> generalPurposeOutCsvResList = new ArrayList<>();
        GeneralPurposeInitVO generalPurposeInitVO = (GeneralPurposeInitVO) input;
        GeneralPurposeOutCsvResVO generalPurposeOutCsvResVO = new GeneralPurposeOutCsvResVO();
        ContentsDoc contentsDoc = repositoryUtil.find(db, generalPurposeInitVO.get_id(),ContentsDoc.class);
        if (contentsDoc != null) {
            generalPurposeOutCsvResVO.setOutPutFileName(contentsDoc.getComment1());
            generalPurposeOutCsvResList.add(generalPurposeOutCsvResVO);
        }
        return generalPurposeOutCsvResList;
    }

}
