package com.scsk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.FileListDeleteReqVO;
import com.scsk.request.vo.FileSelReqVO;
import com.scsk.response.vo.FileInitResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.FileListDelService;
import com.scsk.service.FileListInitService;
import com.scsk.service.FileListSelService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

/**
 * ユーザー一覧画面。<br>
 * <br>
 * メニュー画面からファイル一覧表示＆登録／詳細／削除を実装すること。<br>
 */

@Controller
@RequestMapping("/protected")
public class FileListController {

    @Autowired
    private FileListDelService fileListDelService;
    @Autowired
    private FileListInitService fileListInitService;
    @Autowired
    private FileListSelService fileListSelService;

    /**
     * ファイル一覧初期化表示メソッド。
     * 
     * @param なし
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/imageFile/fileList", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<FileInitResVO>> listInit(HttpServletRequest req) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        FileInitResVO fileInitResVO = new FileInitResVO();
        ResponseEntityVO<FileInitResVO> resEntityBody = new ResponseEntityVO<FileInitResVO>();
        try {
            // ファイル一覧データを検索する
            fileInitResVO = (FileInitResVO) fileListInitService.execute(fileInitResVO);
            if (fileInitResVO.getFileList() == null || fileInitResVO.getFileList().size() == 0) {
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_FILELIST_1001);
                throw new BusinessException(messages);
            }
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            resEntityBody.setResultData(fileInitResVO);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            resEntityBody.setResultData(fileInitResVO);
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<FileInitResVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * 一括削除メソッド。
     * 
     * @param @RequestBody
     *            FileListDeleteReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/imageFile/deleteButton", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<FileListDeleteReqVO>> deleteButton(HttpServletRequest req,
            @RequestBody FileListDeleteReqVO fileListDeleteReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<FileListDeleteReqVO> resEntityBody = new ResponseEntityVO<FileListDeleteReqVO>();

        try {
            // 選択したデータを削除する
            fileListDelService.execute(fileListDeleteReqVO);
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
        return new ResponseEntity<ResponseEntityVO<FileListDeleteReqVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * ファイル一覧検索メソッド。
     * 
     * @param なし
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/imageFile/fileSel", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<FileInitResVO>> listSel(HttpServletRequest req, HttpServletResponse res,
            @RequestBody FileSelReqVO fileSelReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        FileInitResVO fileInitResVO = new FileInitResVO();
        ResponseEntityVO<FileInitResVO> resEntityBody = new ResponseEntityVO<FileInitResVO>();
        try {
            // ファイル一覧データを検索する
            fileInitResVO = fileListSelService.execute(fileSelReqVO);
             if (fileInitResVO.getFileList() == null || fileInitResVO.getFileList().size() == 0) {
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_FILELIST_1001);
            throw new BusinessException(messages);
            }
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            resEntityBody.setResultData(fileInitResVO);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            resEntityBody.setResultData(fileInitResVO);
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<FileInitResVO>>(resEntityBody, HttpStatus.OK);
    }

}
