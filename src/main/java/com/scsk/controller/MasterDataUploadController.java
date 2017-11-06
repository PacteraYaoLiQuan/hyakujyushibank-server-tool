package com.scsk.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.report.ReportUtil;
import com.scsk.request.vo.StoreATMDataReqVO;
import com.scsk.response.vo.StoreATMDataResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.MasterDataDeleteService;
import com.scsk.service.MasterDataDownloadService;
import com.scsk.service.MasterDataListService;
import com.scsk.service.MasterDataUploadService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

@Controller
@RequestMapping("/masterData")
public class MasterDataUploadController {
    @Autowired
    private MasterDataUploadService storeATMDataUploadService;
    @Autowired
    private MasterDataListService masterDataListService;
    @Autowired
    private MasterDataDeleteService masterDataDeleteService;
    @Autowired
    private MasterDataDownloadService masterDataDownloadService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<StoreATMDataResVO>> upload(@RequestBody StoreATMDataReqVO input) {
        ResponseEntityVO<StoreATMDataResVO> entityBody = new ResponseEntityVO<StoreATMDataResVO>();
        try {
            StoreATMDataResVO output = storeATMDataUploadService.execute(input);
            // ヘッダ設定
            entityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            entityBody.setResultData(output);
        } catch (BusinessException e) {
            // ヘッダ設定
            entityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            entityBody.setMessages(e.getResultMessages());
        } catch (Exception e) {
            LogInfoUtil.LogError(e.getMessage());
            entityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            entityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<StoreATMDataResVO>>(entityBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/masterDataList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<StoreATMDataResVO>> list(@RequestBody StoreATMDataReqVO input) {
        ResponseEntityVO<StoreATMDataResVO> entityBody = new ResponseEntityVO<StoreATMDataResVO>();
        try {
            StoreATMDataResVO output = masterDataListService.execute(input);
            // ヘッダ設定
            if (output.getFileFindListReqVo() == null || output.getFileFindListReqVo().size() == 0) {
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_FILE001_1002);
                throw new BusinessException(messages);
            }
            entityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            entityBody.setResultData(output);
        } catch (BusinessException e) {
            // ヘッダ設定
            entityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            entityBody.setMessages(e.getResultMessages());
        } catch (Exception e) {
            LogInfoUtil.LogError(e.getMessage());
            entityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            entityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<StoreATMDataResVO>>(entityBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/masterDataDelete", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<StoreATMDataResVO>> delete(HttpServletRequest req,
            @RequestBody StoreATMDataResVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<StoreATMDataResVO> entityBody = new ResponseEntityVO<StoreATMDataResVO>();
        try {
            masterDataDeleteService.execute(input);
            // ヘッダ設定
            entityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            entityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定
            entityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            entityBody.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            LogInfoUtil.LogError(e.getMessage());
            entityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            entityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<StoreATMDataResVO>>(entityBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/masterDataDownLoad", method = RequestMethod.GET)
    public void masterDataDownLoad(HttpServletRequest req, HttpServletResponse res, @RequestParam("name") String name1,@RequestParam("date") String date) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<StoreATMDataResVO> resEntityBody = new ResponseEntityVO<StoreATMDataResVO>();
        StoreATMDataResVO storeATMDataResVO = new StoreATMDataResVO();
        StoreATMDataResVO input = new StoreATMDataResVO();
        try {
            String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
 //           byte bb[];
//            bb = name.getBytes("ISO-8859-1");
//            name = new String(bb, "UTF-8");
            input.set_id(name1);
            storeATMDataResVO = masterDataDownloadService.execute(input);
            String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH+date+"/";
            ReportUtil.downFile(res, tempPath,storeATMDataResVO.getFileName());
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<StoreATMDataResVO>> download(HttpServletRequest req, HttpServletResponse res,
            @RequestBody StoreATMDataResVO input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        StoreATMDataResVO storeATMDataResVO = new StoreATMDataResVO();
        ResponseEntityVO<StoreATMDataResVO> resEntityBody = new ResponseEntityVO<StoreATMDataResVO>();

        try {
            storeATMDataResVO = masterDataDownloadService.execute(input);
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_ASS);
            String date = sdf.format(new Date());
            base64StringToTXT(storeATMDataResVO, req,date);
            // ヘッダ設定（処理成功の場合）
            String fileName = "";
            fileName = storeATMDataResVO.getFileName();
            storeATMDataResVO.setName(fileName);

            storeATMDataResVO.setDate(date);
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定
            resEntityBody.setResultData(storeATMDataResVO);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            if (e.getResultMessages().getList().get(0).getCode().equals(MessageKeys.E_ACCOUNT001_1008)) {
                resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1008));
            } else {
                resEntityBody.setMessages(e.getResultMessages());
            }
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<StoreATMDataResVO>>(resEntityBody, HttpStatus.OK);
    }

    private void base64StringToTXT(StoreATMDataResVO storeATMDataResVO, HttpServletRequest req,String date) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            byte[] bytes = Base64.decodeBase64(storeATMDataResVO.getFileStream());
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            bin = new BufferedInputStream(bais);
            String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
            String fileName=storeATMDataResVO.getFileName();
            String datePath = outputPath + ApplicationKeys.REPORT_TEMP_PATH +date+"/";
            File file = new File(datePath);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            String newPath =datePath +fileName;
            File newFile=new File(newPath);
            if (!newFile.exists()) {
                try {
                    newFile.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fout = new FileOutputStream(newFile);
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            bout.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
