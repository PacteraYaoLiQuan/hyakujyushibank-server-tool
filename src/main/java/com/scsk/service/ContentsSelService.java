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
import com.scsk.model.ContentsTypeDoc;
import com.scsk.request.vo.ContentsUpdateReqVO;
import com.scsk.response.vo.ContentsUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;

@Service
public class ContentsSelService extends AbstractBLogic<BaseResVO, BaseResVO> {
    @Value("${cloudantLocal.local}")
    private boolean CLOUDANTLOCAL;
    @Value("${createContents.contentsServiceURL}")
    private String contentsServiceURL;

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO baseResVO) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_CONTENTS);
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
//        String filePath = "";
//        filePath = contentsServiceURL.split("/")[0] + "//" + contentsServiceURL.split("/")[2] + "/images/contents/";
        ContentsUpdateResVO contentsUpdateResVO = new ContentsUpdateResVO();
        Database db = client.database(Constants.DB_NAME, false);
        ContentsDoc contentsDoc = new ContentsDoc();
        ContentsUpdateReqVO input = (ContentsUpdateReqVO) baseResVO;
        String actionLogStr="（コンテンツ名：";
        try {
            contentsDoc = (ContentsDoc) repositoryUtil.find(db, input.get_id(), ContentsDoc.class);
            actionLogStr=actionLogStr+contentsDoc.getContentsTitle();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_CONTENTS_1006);
            throw new BusinessException(messages);
        }
        String queryKey = "appCD:\"" + contentsDoc.getAppCD() + "\"";
        List<ContentsAppDoc> contentsAppList = repositoryUtil.getIndex(db,
                ApplicationKeys.INSIGHTINDEX_CONTENTSAPP_SEARCHINFO, queryKey, ContentsAppDoc.class);
        String key = "appCD:\"" + contentsDoc.getAppCD() + "\" AND typeCD:\"" + contentsDoc.getTypeCD() + "\"";
        List<ContentsTypeDoc> contentsTypeList = repositoryUtil.getIndex(db,
                ApplicationKeys.INSIGHTINDEX_CONTENTSTYPE_SEARCHINFO, key, ContentsTypeDoc.class);
        contentsUpdateResVO.setAppCD(contentsAppList.get(0).getAppCD() + "：" + contentsAppList.get(0).getAppName());
        contentsUpdateResVO
                .setTypeCD(contentsTypeList.get(0).getTypeCD() + "：" + contentsTypeList.get(0).getTypeName());
        contentsUpdateResVO.setContentsID(contentsDoc.getContentsID());
        contentsUpdateResVO.setContentsTitle(contentsDoc.getContentsTitle());
        contentsUpdateResVO.setComment1(contentsDoc.getComment1());
        contentsUpdateResVO.setComment2(contentsDoc.getComment2());
        contentsUpdateResVO.setComment3(contentsDoc.getComment3());
        contentsUpdateResVO.setComment4(contentsDoc.getComment4());
        contentsUpdateResVO.setComment5(contentsDoc.getComment5());
        contentsUpdateResVO.setComment6(contentsDoc.getComment6());
        contentsUpdateResVO.setComment7(contentsDoc.getComment7());
        contentsUpdateResVO.setComment8(contentsDoc.getComment8());
        contentsUpdateResVO.setComment9(contentsDoc.getComment9());
        contentsUpdateResVO.setComment10(contentsDoc.getComment10());
        contentsUpdateResVO.setContentsFile1(contentsDoc.getContentsFile1());
        contentsUpdateResVO.setContentsFile2(contentsDoc.getContentsFile2());
        contentsUpdateResVO.setContentsFile3(contentsDoc.getContentsFile3());
        contentsUpdateResVO.setContentsFile4(contentsDoc.getContentsFile4());
        contentsUpdateResVO.setContentsFile5(contentsDoc.getContentsFile5());
        contentsUpdateResVO.setContentsFileName1(contentsDoc.getContentsFileName1());
        contentsUpdateResVO.setContentsFileName2(contentsDoc.getContentsFileName2());
        contentsUpdateResVO.setContentsFileName3(contentsDoc.getContentsFileName3());
        contentsUpdateResVO.setContentsFileName4(contentsDoc.getContentsFileName4());
        contentsUpdateResVO.setContentsFileName5(contentsDoc.getContentsFileName5());
        contentsUpdateResVO.setCreateFlag1(contentsDoc.getCreateFlag1());
        contentsUpdateResVO.setCreateFlag2(contentsDoc.getCreateFlag2());
        contentsUpdateResVO.setCreateFlag3(contentsDoc.getCreateFlag3());
        contentsUpdateResVO.setCreateFlag4(contentsDoc.getCreateFlag4());
        contentsUpdateResVO.setCreateFlag5(contentsDoc.getCreateFlag5());
        java.util.Date dateFrom = sdf.parse(contentsDoc.getDateFrom());
        java.util.Date dateTo = sdf.parse(contentsDoc.getDateTo());
        contentsUpdateResVO.setDateFrom(simpleDateFormat.format(dateFrom));
        contentsUpdateResVO.setDateTo(simpleDateFormat.format(dateTo));
        contentsUpdateResVO.setOrderID(contentsDoc.getOrderID());
        contentsUpdateResVO.setContentsFilePath1(contentsDoc.getContentsFilePath1());
        contentsUpdateResVO.setContentsFilePath2(contentsDoc.getContentsFilePath2());
        contentsUpdateResVO.setContentsFilePath3(contentsDoc.getContentsFilePath3());
        contentsUpdateResVO.setContentsFilePath4(contentsDoc.getContentsFilePath4());
        contentsUpdateResVO.setContentsFilePath5(contentsDoc.getContentsFilePath5());
        actionLog.saveActionLog(Constants.ACTIONLOG_CONTENTS_CONTENTS_5+actionLogStr+")", db);
        return contentsUpdateResVO;

    }

}
