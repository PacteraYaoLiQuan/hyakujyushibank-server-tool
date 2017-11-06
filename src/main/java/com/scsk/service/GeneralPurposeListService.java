package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.ContentsDoc;
import com.scsk.request.vo.GeneralPurposeReqVO;
import com.scsk.response.vo.GeneralPurposeResVO;
import com.scsk.util.ActionLog;
import com.scsk.vo.BaseResVO;
import com.scsk.vo.GeneralPurposeInitVO;

@Service
public class GeneralPurposeListService extends AbstractBLogic<BaseResVO, BaseResVO> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(BaseResVO input) throws Exception {

    }

    @Override
    protected BaseResVO doExecute(CloudantClient client, BaseResVO input) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        SimpleDateFormat simpleDateFormatCom = new SimpleDateFormat(Constants.DATE_FORMAT_YMD);
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        GeneralPurposeResVO generalPurposeResVO = new GeneralPurposeResVO();
        List<ContentsDoc> contentsList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_CONTENTSLIST_CONTENTSLIST, ContentsDoc.class);
        List<GeneralPurposeInitVO> generalPurposeInitVOList = new ArrayList<>();
        GeneralPurposeReqVO generalPurposeReqVO = (GeneralPurposeReqVO) input;
        if (contentsList != null && contentsList.size() != 0) {
            for (ContentsDoc contentsDoc : contentsList) {
                if (Constants.CONTENTS_APP.equals(contentsDoc.getAppCD())
                        && Constants.CONTENTS_TYPE.equals(contentsDoc.getTypeCD())) {
                    GeneralPurposeInitVO generalPurposeInitVO = new GeneralPurposeInitVO();
                    java.util.Date dateFrom = sdf.parse(contentsDoc.getDateFrom());
                    java.util.Date dateTo = sdf.parse(contentsDoc.getDateTo());
                    if (!generalPurposeReqVO.getDateFrom().isEmpty() && !generalPurposeReqVO.getDateTo().isEmpty()) {
                        if ((simpleDateFormatCom.format(dateFrom).compareTo(generalPurposeReqVO.getDateFrom()) >= 0
                                && simpleDateFormatCom.format(dateFrom).compareTo(generalPurposeReqVO.getDateTo()) <= 0)
                               || (simpleDateFormatCom.format(dateTo).compareTo(generalPurposeReqVO.getDateFrom()) >= 0
                                       && simpleDateFormatCom.format(dateTo).compareTo(generalPurposeReqVO.getDateTo()) <= 0)
                               || (simpleDateFormatCom.format(dateTo).compareTo(generalPurposeReqVO.getDateFrom()) >= 0
                               && simpleDateFormatCom.format(dateTo).compareTo(generalPurposeReqVO.getDateTo()) <= 0)
                               || (simpleDateFormatCom.format(dateFrom).compareTo(generalPurposeReqVO.getDateFrom()) >= 0
                               && simpleDateFormatCom.format(dateTo).compareTo(generalPurposeReqVO.getDateTo()) <= 0)) {
                            generalPurposeInitVO.set_id(contentsDoc.get_id());
                            generalPurposeInitVO.setDateFrom(simpleDateFormat.format(dateFrom));
                            generalPurposeInitVO.setDateTo(simpleDateFormat.format(dateTo));
                            generalPurposeInitVO.setComment1(contentsDoc.getComment1());
                            generalPurposeInitVO.setContentsFile1(contentsDoc.getContentsFilePath1());
                            generalPurposeInitVO.setContentsID(contentsDoc.getContentsID());
                            generalPurposeInitVO.setContentsTitle(contentsDoc.getContentsTitle());
                            generalPurposeInitVOList.add(generalPurposeInitVO);
                        }
                    } else if (!generalPurposeReqVO.getDateFrom().isEmpty()
                            && generalPurposeReqVO.getDateTo().isEmpty()) {
                        if (simpleDateFormatCom.format(dateTo).compareTo(generalPurposeReqVO.getDateFrom()) >= 0) {
                            generalPurposeInitVO.set_id(contentsDoc.get_id());
                            generalPurposeInitVO.setDateFrom(simpleDateFormat.format(dateFrom));
                            generalPurposeInitVO.setDateTo(simpleDateFormat.format(dateTo));
                            generalPurposeInitVO.setComment1(contentsDoc.getComment1());
                            generalPurposeInitVO.setContentsFile1(contentsDoc.getContentsFilePath1());
                            generalPurposeInitVO.setContentsID(contentsDoc.getContentsID());
                            generalPurposeInitVO.setContentsTitle(contentsDoc.getContentsTitle());
                            generalPurposeInitVOList.add(generalPurposeInitVO);
                        }
                    } else if (generalPurposeReqVO.getDateFrom().isEmpty()
                            && !generalPurposeReqVO.getDateTo().isEmpty()) {
                        if (simpleDateFormatCom.format(dateFrom).compareTo(generalPurposeReqVO.getDateTo()) <= 0) {
                            generalPurposeInitVO.set_id(contentsDoc.get_id());
                            generalPurposeInitVO.setDateFrom(simpleDateFormat.format(dateFrom));
                            generalPurposeInitVO.setDateTo(simpleDateFormat.format(dateTo));
                            generalPurposeInitVO.setComment1(contentsDoc.getComment1());
                            generalPurposeInitVO.setContentsFile1(contentsDoc.getContentsFile1());
                            generalPurposeInitVO.setContentsID(contentsDoc.getContentsID());
                            generalPurposeInitVO.setContentsTitle(contentsDoc.getContentsTitle());
                            generalPurposeInitVOList.add(generalPurposeInitVO);
                        }
                    } else {
                        generalPurposeInitVO.set_id(contentsDoc.get_id());
                        generalPurposeInitVO.setDateFrom(simpleDateFormat.format(dateFrom));
                        generalPurposeInitVO.setDateTo(simpleDateFormat.format(dateTo));
                        generalPurposeInitVO.setComment1(contentsDoc.getComment1());
                        generalPurposeInitVO.setContentsFile1(contentsDoc.getContentsFilePath1());
                        generalPurposeInitVO.setContentsID(contentsDoc.getContentsID());
                        generalPurposeInitVO.setContentsTitle(contentsDoc.getContentsTitle());
                        generalPurposeInitVOList.add(generalPurposeInitVO);
                    }
                }
            }
        }
        generalPurposeResVO.setGeneralPurposeInitList(generalPurposeInitVOList);
        actionLog.saveActionLog(Constants.ACTIONLOG_GENERAL_PURPOSE_1, db);
        return generalPurposeResVO;
    }

}
