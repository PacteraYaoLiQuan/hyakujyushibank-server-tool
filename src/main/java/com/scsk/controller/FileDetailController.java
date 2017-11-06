package com.scsk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.request.vo.FileDetailUpdateReqVO;
import com.scsk.response.vo.FileDetailUpdateResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.FileDetailSelService;
import com.scsk.service.FileDetailUpdService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;

    /**
     * ユーザー新規／編集コントロール。<br>
     * <br>
     * ユーザー情報データを検索すること。<br>
     * ユーザー情報データを登録すること。<br>
     * ユーザー情報データを更新すること。<br>
     */


@Controller
@RequestMapping("/protected")
public class FileDetailController {

    @Autowired
    FileDetailSelService fileDetailSelService;

    @Autowired
    FileDetailUpdService fileDetailUpdService;

        /**
         * ファイル詳細データ検索/登録メソッド。
         * 
         * @return ResponseEntity　戻るデータオブジェクト
         */
        @RequestMapping(value = "/file/fileDetail", method = RequestMethod.GET, produces = "application/json")
        public ResponseEntity<ResponseEntityVO<FileDetailUpdateResVO>> detailShow(
                @RequestParam("_id") String id) {
            LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
            ResponseEntityVO<FileDetailUpdateResVO> entityVO = new ResponseEntityVO<FileDetailUpdateResVO>();
            FileDetailUpdateReqVO input = new FileDetailUpdateReqVO();
            input.set_id(id);

            try {
                // ユーザー詳細情報取得
                FileDetailUpdateResVO output = fileDetailSelService.execute(input);
                // ヘッダ設定（処理成功の場合）
                entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
                entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
                // ボーディ設定
                entityVO.setResultData(output);
                LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
            } catch (BusinessException e) {
                // ヘッダ設定（処理失敗の場合）
                entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
                entityVO.setMessages(e.getResultMessages());
                LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
            } catch (Exception e) {
                // 予想エラー以外の場合
                entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
                entityVO.setMessages(ResultMessages.error()
                        .add(MessageKeys.ERR_500));
                LogInfoUtil.LogError(e.getMessage(), e);
            }
            LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
            return new ResponseEntity<ResponseEntityVO<FileDetailUpdateResVO>>(entityVO,
                    HttpStatus.OK);

        }
        
        /**
         * ユーザ追加・更新メソッド。
         * 
         * @param req
         *            request
         * @return 戻る画面ID
         */
//        @RequestMapping(value = "/fileok", method = RequestMethod.POST)
//        public String saveUser(HttpServletRequest req, Model model) {
//            // 画面から入力パラメータを取得する
//            ImageFileDoc fileBean = new ImageFileDoc();
//            fileBean.setFileNameJP(req.getParameter("username"));
//            fileBean.setFileNameEN(req.getParameter("password"));
//            fileBean.setUseLocal(req.getParameter("role"));
//            try {
//                // 登録ボタンを押下する場合
//                if ("add".equals(req.getParameter("action"))) {
//                    // サービスの実行メソッドを呼び出す
//                    fileBean = fileDetailAddService.execute(fileBean);
//                    // 更新ボタンを押下する場合
////                } else if ("upd".equals(req.getParameter("action"))) {
////                    fileBean = fileDetailUpdService.execute(fileBean);
//                }
//            } catch (BusinessException be) {
//                ResultMessages messages = be.getResultMessages();
//                model.addAttribute(messages);
//                // エラーがある場合、本画面に戻る
//                return "/protected/imageFile/fileList.jsp";
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return "welcomePage";
//        }
        
        /**
         * ファイル詳細データ更新メソッド。
         * 
         * @return ResponseEntity　戻るデータオブジェクト
         */
        @RequestMapping(value = "/file/fileUpdate", method = RequestMethod.POST, produces = "application/json")
        public ResponseEntity<ResponseEntityVO<FileDetailUpdateResVO>> dataUpd(
                @RequestBody FileDetailUpdateReqVO input) {
            LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
            ResponseEntityVO<FileDetailUpdateResVO> entityVO = new ResponseEntityVO<FileDetailUpdateResVO>();

            try {
                // ステータスを更新
                FileDetailUpdateResVO output = fileDetailUpdService.execute(input);
                // ヘッダ設定（処理成功の場合）
                entityVO.setResultStatus(Constants.RESULT_STATUS_OK);
                entityVO.setResultCode(Constants.RESULT_SUCCESS_CODE);
                // ボーディ設定
                entityVO.setResultData(output);
                LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
            } catch (BusinessException e) {
                // ヘッダ設定（処理失敗の場合）
                entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
                entityVO.setMessages(e.getResultMessages());
                LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
            } catch (Exception e) {
                // 予想エラー以外の場合
                entityVO.setResultStatus(Constants.RESULT_STATUS_NG);
                entityVO.setMessages(ResultMessages.error()
                        .add(MessageKeys.ERR_500));
                LogInfoUtil.LogError(e.getMessage(), e);
            }

            LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
            return new ResponseEntity<ResponseEntityVO<FileDetailUpdateResVO>>(
                    entityVO, HttpStatus.OK);
        }
    }
