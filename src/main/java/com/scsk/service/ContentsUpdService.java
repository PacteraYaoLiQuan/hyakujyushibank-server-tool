package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.ContentsAppDoc;
import com.scsk.model.ContentsDoc;
import com.scsk.model.ContentsIdDoc;
import com.scsk.model.ContentsTypeDoc;
import com.scsk.request.vo.ContentsUpdateReqVO;
import com.scsk.response.vo.ContentsUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.CreateContents;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Service
public class ContentsUpdService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Value("${cloudantLocal.local}")
    private boolean CLOUDANTLOCAL;
    @Autowired
    private CreateContents createContents;

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_CONTENTS);
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        ContentsUpdateResVO contentsUpdateResVO = new ContentsUpdateResVO();
        Database db = client.database(Constants.DB_NAME, false);
        ContentsDoc contentsDoc = new ContentsDoc();

        ContentsUpdateReqVO input = (ContentsUpdateReqVO) baseResVO;
        String actionLogStr = "（コンテンツ名：";
        String contentsInfoKey = "";
        if (input.getModeType().equals("2")) {
            try {
                contentsDoc = (ContentsDoc) repositoryUtil.find(db, input.get_id(), ContentsDoc.class);
            } catch (BusinessException e) {
                LogInfoUtil.LogDebug(e.getMessage());
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTSAPP_1003);
                throw new BusinessException(messages);
            }
        }
        List<ContentsDoc> contentsList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_CONTENTSLIST_CONTENTSLIST, ContentsDoc.class);

        for (ContentsDoc contentsDoc1 : contentsList) {
            if (contentsDoc1.getContentsID().equals(input.getContentsID())) {
                if (!contentsDoc1.getTypeCD().equals(input.getTypeCD())) {
                    contentsDoc.setOrderID(0);
                }
            }
        }
        contentsDoc.setAppCD(input.getAppCD());
        contentsDoc.setTypeCD(input.getTypeCD());
        contentsDoc.setContentsID(input.getContentsID());
        contentsDoc.setContentsTitle(input.getContentsTitle());
        contentsDoc.setComment1(input.getComment1());
        contentsDoc.setComment2(input.getComment2());
        contentsDoc.setComment3(input.getComment3());
        contentsDoc.setComment4(input.getComment4());
        contentsDoc.setComment5(input.getComment5());
        contentsDoc.setComment6(input.getComment6());
        contentsDoc.setComment7(input.getComment7());
        contentsDoc.setComment8(input.getComment8());
        contentsDoc.setComment9(input.getComment9());
        contentsDoc.setComment10(input.getComment10());
        contentsDoc.setContentsFile1(input.getContentsFile1());
        contentsDoc.setContentsFile2(input.getContentsFile2());
        contentsDoc.setContentsFile3(input.getContentsFile3());
        contentsDoc.setContentsFile4(input.getContentsFile4());
        contentsDoc.setContentsFile5(input.getContentsFile5());
        if (input.getContentsFileName1().equals("")) {
            contentsDoc.setContentsFileName1("");
        } else {
            contentsDoc.setContentsFileName1(
                    input.getAppCD() + "_" + input.getContentsID() + "_" + input.getContentsFileName1().split("_")[2]);
        }
        if (input.getContentsFileName2().equals("")) {
            contentsDoc.setContentsFileName2("");
        } else {
            contentsDoc.setContentsFileName2(
                    input.getAppCD() + "_" + input.getContentsID() + "_" + input.getContentsFileName2().split("_")[2]);
        }
        if (input.getContentsFileName3().equals("")) {
            contentsDoc.setContentsFileName3("");
        } else {
            contentsDoc.setContentsFileName3(
                    input.getAppCD() + "_" + input.getContentsID() + "_" + input.getContentsFileName3().split("_")[2]);
        }
        if (input.getContentsFileName4().equals("")) {
            contentsDoc.setContentsFileName4("");
        } else {
            contentsDoc.setContentsFileName4(
                    input.getAppCD() + "_" + input.getContentsID() + "_" + input.getContentsFileName4().split("_")[2]);
        }
        if (input.getContentsFileName5().equals("")) {
            contentsDoc.setContentsFileName5("");
        } else {
            contentsDoc.setContentsFileName5(
                    input.getAppCD() + "_" + input.getContentsID() + "_" + input.getContentsFileName5().split("_")[2]);
        }
        contentsDoc.setCreateFlag1(input.getCreateFlag1());
        contentsDoc.setCreateFlag2(input.getCreateFlag2());
        contentsDoc.setCreateFlag3(input.getCreateFlag3());
        contentsDoc.setCreateFlag4(input.getCreateFlag4());
        contentsDoc.setCreateFlag5(input.getCreateFlag5());
        if (!input.getContentsFileName1().equals("") && input.getContentsFile1().equals("")) {
            contentsDoc.setCreateFlag1(2);
        }
        if (!input.getContentsFileName2().equals("") && input.getContentsFile2().equals("")) {
            contentsDoc.setCreateFlag2(2);
        }
        if (!input.getContentsFileName3().equals("") && input.getContentsFile3().equals("")) {
            contentsDoc.setCreateFlag3(2);
        }
        if (!input.getContentsFileName4().equals("") && input.getContentsFile4().equals("")) {
            contentsDoc.setCreateFlag4(2);
        }
        if (!input.getContentsFileName5().equals("") && input.getContentsFile5().equals("")) {
            contentsDoc.setCreateFlag5(2);
        }
        java.util.Date dateFrom = simpleDateFormat.parse(input.getDateFrom());
        java.util.Date dateTo = simpleDateFormat.parse(input.getDateTo());
        contentsDoc.setDateFrom(sdf.format(dateFrom));
        contentsDoc.setDateTo(sdf.format(dateTo));
        actionLogStr = actionLogStr + input.getContentsTitle();
        if (input.getModeType().equals("2")) {
            try {
                repositoryUtil.update(db, contentsDoc);

                actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_CONTENTS_6 + actionLogStr + ")", db);
            } catch (BusinessException e) {
                LogInfoUtil.LogDebug(e.getMessage());
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1006);
                throw new BusinessException(messages);
            }
        } else {
            String queryKey = "appCD:\"" + input.getAppCD() + "\"";
            List<ContentsAppDoc> contentsAppList = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_CONTENTSAPP_SEARCHINFO, queryKey, ContentsAppDoc.class);
            String key = "appCD:\"" + input.getAppCD() + "\" AND typeCD:\"" + input.getTypeCD() + "\"";
            List<ContentsTypeDoc> contentsTypeList = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_CONTENTSTYPE_SEARCHINFO, key, ContentsTypeDoc.class);
            String idKey = "appCD:\"" + input.getAppCD() + "\"";
            List<ContentsDoc> contentsDocs = repositoryUtil.getIndex(db,
                    ApplicationKeys.INSIGHTINDEX_CONTENTS_REMOVEBYAPPCD, idKey, ContentsDoc.class);
            boolean flag = false;

            for (ContentsDoc contentsDoc1 : contentsDocs) {
                if (contentsDoc1.getContentsID().equals(input.getContentsID())) {
                    flag = true;
                }
            }
            if (flag) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_CONTENTS_1003);
                throw new BusinessException(messages);
            }

            if (contentsTypeList == null || contentsTypeList.size() == 0) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_CONTENTS_1002);
                throw new BusinessException(messages);
            }
            if (contentsAppList == null || contentsAppList.size() == 0) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_CONTENTS_1001);
                throw new BusinessException(messages);
            } else {
                try {

                    contentsDoc.setOrderID(0);
                    repositoryUtil.save(db, contentsDoc);

                    actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_CONTENTS_4 + actionLogStr + ")", db);
                } catch (BusinessException e) {
                    LogInfoUtil.LogDebug(e.getMessage());
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
                    throw new BusinessException(messages);
                }
            }
        }
        contentsInfoKey = "appCD:\"" + input.getAppCD() + "\" AND typeCD:\"" + input.getTypeCD()
                + "\" AND contentsID:\"" + input.getContentsID() + "\"";
        List<ContentsDoc> contentsInfoList = repositoryUtil.getIndex(db,
                ApplicationKeys.INSIGHTINDEX_CONTENTS_SEARCHINFO, contentsInfoKey, ContentsDoc.class);
        String id = contentsInfoList.get(0).get_id();
        String statusCode = "";
        try {
            statusCode = createContents.sendMessage(id);
        } catch (Exception e) {
            deleteContents(db, id);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.ERR_500);
            throw new BusinessException(messages);
        }
        if (!"200".equals(statusCode)) {
            deleteContents(db, id);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.ERR_500);
            throw new BusinessException(messages);
        }

        return contentsUpdateResVO;
    }

    public void deleteContents(Database db, String id) {
        ContentsDoc contentDoc = repositoryUtil.find(db, id, ContentsDoc.class);
        if (contentDoc.getCreateFlag1() == 0) {
            contentDoc.setContentsFile1("");
            contentDoc.setContentsFileName1("");
        }
        if (contentDoc.getCreateFlag2() == 0) {
            contentDoc.setContentsFile2("");
            contentDoc.setContentsFileName2("");
        }
        if (contentDoc.getCreateFlag3() == 0) {
            contentDoc.setContentsFile3("");
            contentDoc.setContentsFileName3("");
        }
        if (contentDoc.getCreateFlag4() == 0) {
            contentDoc.setContentsFile4("");
            contentDoc.setContentsFileName4("");
        }
        if (contentDoc.getCreateFlag5() == 0) {
            contentDoc.setContentsFile5("");
            contentDoc.setContentsFileName5("");
        }
        try {
            repositoryUtil.update(db, contentDoc);
        } catch (BusinessException e) {
            LogInfoUtil.LogDebug(e.getMessage());
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1006);
            throw new BusinessException(messages);
        }
    }
}
