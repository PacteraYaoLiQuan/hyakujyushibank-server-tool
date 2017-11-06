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
import com.scsk.model.GlobalDoc;
import com.scsk.response.vo.GeneralPurposeOutCsvResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.vo.BaseResVO;
import com.scsk.vo.GeneralPurposeInitVO;

@Service
public class GeneralPurposeOutCsvService extends AbstractBLogic<BaseResVO, List<GeneralPurposeOutCsvResVO>> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected List<GeneralPurposeOutCsvResVO> doExecute(CloudantClient client, BaseResVO input) throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        List<GlobalDoc> globalList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_GLOBALLIST_GLOBALLIST,
                GlobalDoc.class);
        List<GeneralPurposeOutCsvResVO> generalPurposeOutCsvResList = new ArrayList<>();
        GeneralPurposeInitVO generalPurposeInitVO = (GeneralPurposeInitVO) input;
        String strActionlog = "（キャンペン名：" + generalPurposeInitVO.getComment1() + "）";
        for (GlobalDoc globalDoc : globalList) {
            GeneralPurposeOutCsvResVO generalPurposeOutCsvResVO = new GeneralPurposeOutCsvResVO();
            if (Constants.CONTENTS_APP.equals(globalDoc.getAppCD())
                    && Constants.CONTENTS_TYPE.equals(globalDoc.getTypeCD())
                    && globalDoc.getContentsID().equals(generalPurposeInitVO.getContentsID())) {
                generalPurposeOutCsvResVO.setMap(globalDoc.getMap());
                generalPurposeOutCsvResVO.setCreateData(globalDoc.getCreatedDate());
                generalPurposeOutCsvResVO.setOutPutFileName(generalPurposeInitVO.getComment1());
                generalPurposeOutCsvResVO.set_id(generalPurposeInitVO.get_id());
                generalPurposeOutCsvResList.add(generalPurposeOutCsvResVO);
            }
        }
        actionLog.saveActionLog(Constants.ACTIONLOG_GENERAL_PURPOSE_2 + strActionlog, db);
        return generalPurposeOutCsvResList;
    }

}
