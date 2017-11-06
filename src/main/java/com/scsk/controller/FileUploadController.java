package com.scsk.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.scsk.request.vo.FileUploadReqVo;
import com.scsk.response.vo.FileUploadResVo;
import com.scsk.response.vo.StoreATMDataResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.FileDownloadService;
import com.scsk.service.FileFindListService;
import com.scsk.service.FileListDeleteService;
import com.scsk.service.FileUploadService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

@Controller
@RequestMapping("/file")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;
    
    @Autowired
    private FileFindListService fileFindListService;
    
    @Autowired
    private FileListDeleteService fileListDeleteService;
    
    @Autowired
    private FileDownloadService fileDownloadService;
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<FileUploadResVo>> upload(@RequestBody FileUploadReqVo input) {
        ResponseEntityVO<FileUploadResVo> entityBody = new ResponseEntityVO<FileUploadResVo>();
        try {
            FileUploadResVo output = fileUploadService.execute(input);
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
        return new ResponseEntity<ResponseEntityVO<FileUploadResVo>>(entityBody, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/findFileList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<FileUploadResVo>> findFileList(@RequestBody FileUploadReqVo input) {
        ResponseEntityVO<FileUploadResVo> entityBody = new ResponseEntityVO<FileUploadResVo>();
        try {
            FileUploadResVo output = fileFindListService.execute(input);
            // ヘッダ設定
            if (output.getFileFindListReqVo()== null || output.getFileFindListReqVo() .size() == 0) {
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(
                        MessageKeys.E_FILE001_1002);
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
        return new ResponseEntity<ResponseEntityVO<FileUploadResVo>>(entityBody, HttpStatus.OK);
    }
    
    /**
     * 一括削除メソッド。
     * 
     * @param @RequestBody UserListDeleteReqVO 一覧データ
     * @return ResponseEntity　戻るデータオブジェクト
     */
    @RequestMapping(value = "/fileDeleteButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<FileUploadResVo>> deleteButton(
            HttpServletRequest req,
            @RequestBody FileUploadResVo fileUploadResVo) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<FileUploadResVo> resEntityBody = new ResponseEntityVO<FileUploadResVo>();
        try {
            // 選択したデータを削除する
            fileListDeleteService.execute(fileUploadResVo);
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
            resEntityBody.setMessages(ResultMessages.error().add(
                    MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<FileUploadResVo>>(
                resEntityBody, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/HtmlPdfFileDownLoad", method = RequestMethod.GET)
    public void masterDataDownLoad(HttpServletRequest req, HttpServletResponse res, @RequestParam("name") String name1,@RequestParam("date") String date) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<StoreATMDataResVO> resEntityBody = new ResponseEntityVO<StoreATMDataResVO>();
       
        FileUploadReqVo fileUploadReqVo = new FileUploadReqVo();
        
        try {
            FileUploadReqVo input = new FileUploadReqVo();
            input.set_id(name1);
            fileUploadReqVo = fileDownloadService.execute(input);
            String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
//           byte bb[];
//            bb = name.getBytes("ISO-8859-1");
//            name = new String(bb, "UTF-8");
            String tempPath = outputPath + ApplicationKeys.REPORT_TEMP_PATH+date+"/";
            ReportUtil.downFile(res, tempPath,fileUploadReqVo.getFileName());
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
    public ResponseEntity<ResponseEntityVO<FileUploadReqVo>> download(HttpServletRequest req, HttpServletResponse res,
            @RequestBody FileUploadReqVo input) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        FileUploadReqVo fileUploadReqVo = new FileUploadReqVo();
        ResponseEntityVO<FileUploadReqVo> resEntityBody = new ResponseEntityVO<FileUploadReqVo>();

        try {
            fileUploadReqVo = fileDownloadService.execute(input);
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_ASS);
            String date = sdf.format(new Date());
            base64StringToTXT(fileUploadReqVo, req,date);
            // ヘッダ設定（処理成功の場合）
            String fileName = "";
            fileName = fileUploadReqVo.getFileName();
            fileUploadReqVo.setName(fileName);

            fileUploadReqVo.setDate(date);
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定
            resEntityBody.setResultData(fileUploadReqVo);
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
        return new ResponseEntity<ResponseEntityVO<FileUploadReqVo>>(resEntityBody, HttpStatus.OK);
    }

    private void base64StringToTXT(FileUploadReqVo fileUploadReqVo, HttpServletRequest req,String date) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            byte[] bytes = Base64.decodeBase64(fileUploadReqVo.getFileStream());
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            bin = new BufferedInputStream(bais);
            String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
            String fileName=fileUploadReqVo.getFileName();
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
