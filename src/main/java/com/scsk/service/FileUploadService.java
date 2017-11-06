package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.UpLoadFileDoc;
import com.scsk.request.vo.FileUploadReqVo;
import com.scsk.response.vo.FileUploadResVo;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

@Service
public class FileUploadService extends AbstractBLogic<FileUploadReqVo, FileUploadResVo> {

    @Autowired
    private ActionLog actionLog;

    @Override
    protected void preExecute(FileUploadReqVo input) throws Exception {

    }

    @Override
    protected FileUploadResVo doExecute(CloudantClient client, FileUploadReqVo input) throws Exception {
        Database db = client.database(Constants.DB_NAME, false);
        UpLoadFileDoc upLoadFileDoc = new UpLoadFileDoc();
        upLoadFileDoc.setDocType("HTMLPDFFILE");
        upLoadFileDoc.setFileStream(input.getFileCode());
        upLoadFileDoc.setFileName(input.getFileName());
        upLoadFileDoc.setUseFlag("0");
        upLoadFileDoc.setHopingUseDate(input.getHopingUseDate());
        upLoadFileDoc.setIosORandroid(input.getIosORandroid());
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        upLoadFileDoc.setUploadDatetime(date);
        if ("0".equals(input.getFileNameJP())) {
            upLoadFileDoc.setFileNameJP("お知らせHTML");
            upLoadFileDoc.setFileNameEN("Information.html");
            if ("0".equals(input.getIosORandroid())) {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\ios\\1.01\\");
            } else {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\android\\1.01\\");
            }
        }
        if ("1".equals(input.getFileNameJP())) {
            upLoadFileDoc.setFileNameJP("ライセンス情報HTML");
            upLoadFileDoc.setFileNameEN("License.html");
            if ("0".equals(input.getIosORandroid())) {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\ios\\1.01\\");
            } else {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\android\\1.01\\");
            }
        }
        if ("2".equals(input.getFileNameJP())) {
            upLoadFileDoc.setFileNameJP("反社会的勢力HTML");
            upLoadFileDoc.setFileNameEN("AccountAntisocialForces.html");
            if ("0".equals(input.getIosORandroid())) {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\ios\\AccountApplication\\");
            } else {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\android\\AccountApplication\\");
            }
        }
        if ("3".equals(input.getFileNameJP())) {
            upLoadFileDoc.setFileNameJP("その他口座に関する同意事項HTML");
            upLoadFileDoc.setFileNameEN("AccountOtherAgreement.html");
            if ("0".equals(input.getIosORandroid())) {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\ios\\AccountApplication\\");
            } else {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\android\\AccountApplication\\");
            }
        }
        if ("4".equals(input.getFileNameJP())) {
            upLoadFileDoc.setFileNameJP("ひろぎんネット支店ご利用規定集PDF");
            upLoadFileDoc.setFileNameEN("kitei.pdf");
            if ("0".equals(input.getIosORandroid())) {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\ios\\AccountApplication\\");
            } else {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\android\\AccountApplication\\");
            }
        }
        if ("5".equals(input.getFileNameJP())) {
            upLoadFileDoc.setFileNameJP("総合口座等取引規定集PDF");
            upLoadFileDoc.setFileNameEN("yokin_003.pdf");
            if ("0".equals(input.getIosORandroid())) {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\ios\\AccountApplication\\");
            } else {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\android\\AccountApplication\\");
            }
        }
        if ("6".equals(input.getFileNameJP())) {
            upLoadFileDoc.setFileNameJP("（仮）いよぎんアプリ利用規定HTML");
            upLoadFileDoc.setFileNameEN("TermsOfUse.html");
            if ("0".equals(input.getIosORandroid())) {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\ios\\");
            } else {
                upLoadFileDoc.setPath("\\webapp\\resources\\html\\android\\");
            }
        }
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String datetime = simpleDateFormat.format(today);
        if (input.getHopingUseDate().compareTo(datetime.substring(0,16)) >= 0) {
            try {
                repositoryUtil.save(db, upLoadFileDoc);
                actionLog.saveActionLog(Constants.ACTIONLOG_HTML_PDF_FILE_3, db);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_FILE001_1001);
                throw new BusinessException(messages);
            }
            return null;
        }else{
            FileUploadResVo fileUploadResVo=new FileUploadResVo();
            return fileUploadResVo;
        }
    }

}
