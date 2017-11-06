package com.scsk.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.scsk.report.ReportBLogic;
import com.scsk.request.vo.AccountAppListCompleteButtonReqVO;
import com.scsk.request.vo.GeneralPurposeReqVO;
import com.scsk.response.vo.GeneralPurposeOutCsvResVO;
import com.scsk.response.vo.GeneralPurposeResVO;
import com.scsk.responseentity.vo.ResponseEntityVO;
import com.scsk.service.GeneralPurposeDownService;
import com.scsk.service.GeneralPurposeListService;
import com.scsk.service.GeneralPurposeOutCsvService;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.vo.BaseResVO;
import com.scsk.vo.GeneralPurposeInitVO;

@Controller
@RequestMapping("/protected")
public class GeneralPurposeListController {
    @Autowired
    private GeneralPurposeListService generalPurposeListService;
    @Autowired
    private GeneralPurposeOutCsvService generalPurposeOutCsvService;
    @Autowired
    private ReportBLogic reportBLogic;
    @Autowired
    private GeneralPurposeDownService generalPurposeDownService;
    @RequestMapping(value = "/generalPurpose/generalPurposeList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> listInit(HttpServletRequest req,
            @RequestBody GeneralPurposeReqVO generalPurposeReqVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        GeneralPurposeResVO generalPurposeResVO = new GeneralPurposeResVO();
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = generalPurposeReqVO;
        baseResVO = generalPurposeReqVO;
        try {
            // 汎用DB＆データを検索する
            generalPurposeResVO = (GeneralPurposeResVO) generalPurposeListService.execute(baseResVO);
            // 初期化データない場合、Messageをセットする
            if (generalPurposeResVO.getGeneralPurposeInitList().isEmpty()) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_GENERALPURPOSE_1001);
                throw new BusinessException(messages);
            }
            // ヘッダ設定（処理成功の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定（処理成功の場合）
            resEntityBody.setResultData(generalPurposeResVO);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(e.getResultMessages());
            resEntityBody.setResultData(generalPurposeResVO);
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * 汎用DBCSV出力メソッド。
     * 
     * @param @RequestBody
     *            accountAppListCsvButtonReqVO 一覧データ
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/generalPurpose/generalPurposeOutCsv", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseEntityVO<BaseResVO>> generalPurposeOutCsv(HttpServletRequest req,
            HttpServletResponse res, @RequestBody GeneralPurposeInitVO generalPurposeInitVO) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<BaseResVO> resEntityBody = new ResponseEntityVO<BaseResVO>();
        BaseResVO baseResVO = generalPurposeInitVO;
        baseResVO = generalPurposeInitVO;
        GeneralPurposeOutCsvResVO generalPurposeOutCsvResVO = new GeneralPurposeOutCsvResVO();
        try {
            // CSV出力用データを抽出
            List<GeneralPurposeOutCsvResVO> generalPurposeOutCsvResVOList = generalPurposeOutCsvService.execute(baseResVO);
          
            if (generalPurposeOutCsvResVOList.isEmpty()) {
                ResultMessages messages = ResultMessages.error().add(
                        MessageKeys.E_GENERALPURPOSE_1002);
                throw new BusinessException(messages);
            }
            // 当日日付を取得する（日付フォーマット）
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
            String date = sdf.format(new Date());
            // 出力する日付フォルダーを作成
            reportBLogic.datePathCreate(req, date);
            // CSV出力用のparameterを準備
            // プロジェクトのパスを取得
            this.getClass().getClassLoader();
            String outputFileName = generalPurposeInitVO.getComment1() +"_"+ date;
            // CSV区分設定
            csvCreate("1", date, generalPurposeOutCsvResVOList, outputFileName, req);
            // ヘッダ設定（処理成功の場合）
            generalPurposeOutCsvResVO.setDate(date);
            generalPurposeOutCsvResVO.setOutPutFileName(generalPurposeInitVO.getComment1());
            generalPurposeOutCsvResVO.set_id(generalPurposeInitVO.get_id());
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_OK);
            resEntityBody.setResultCode(Constants.RESULT_SUCCESS_CODE);
            // ボーディ設定
            resEntityBody.setResultData(generalPurposeOutCsvResVO);
            LogInfoUtil.LogInfo(Constants.RESULT_SUCCESS_CODE);
        } catch (BusinessException e) {
            // ヘッダ設定（処理失敗の場合）
            resEntityBody.setMessages(e.getResultMessages());
            resEntityBody.setResultData(generalPurposeOutCsvResVO);
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            LogInfoUtil.LogInfo(e.getResultMessages().getList().get(0).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 予想エラー以外の場合
            resEntityBody.setResultStatus(Constants.RESULT_STATUS_NG);
            resEntityBody.setMessages(ResultMessages.error().add(MessageKeys.ERR_500));
            LogInfoUtil.LogError(e.getMessage(), e);
        }
        LogInfoUtil.LogInfoEnd(this.getClass().getSimpleName());
        return new ResponseEntity<ResponseEntityVO<BaseResVO>>(resEntityBody, HttpStatus.OK);
    }

    /**
     * 帳票出力ボタンCSVファイルダウンロードメソッド。
     * 
     * @param @RequestParam
     *            date 当日日付
     * @return ResponseEntity 戻るデータオブジェクト
     */
    @RequestMapping(value = "/generalPurpose/csvDownLoad", method = RequestMethod.GET)
    public void csvDownLoad(HttpServletRequest req, HttpServletResponse res, @RequestParam("date") String date,@RequestParam("_id") String _id) {
        LogInfoUtil.LogInfoStrat(this.getClass().getSimpleName());
        ResponseEntityVO<AccountAppListCompleteButtonReqVO> resEntityBody = new ResponseEntityVO<AccountAppListCompleteButtonReqVO>();

        try {
            GeneralPurposeInitVO generalPurposeInitVO = new GeneralPurposeInitVO();
            generalPurposeInitVO.set_id(_id);
            BaseResVO baseResVO = generalPurposeInitVO;
            baseResVO = generalPurposeInitVO;
            List<GeneralPurposeOutCsvResVO> generalPurposeOutCsvResVOList = generalPurposeDownService.execute(baseResVO);
            String outFileName = "";
            if (generalPurposeOutCsvResVOList !=null && generalPurposeOutCsvResVOList.size() >0) {
                outFileName = generalPurposeOutCsvResVOList.get(0).getOutPutFileName();
            }
         
            String outputFileName = outFileName + "_" + date;
            // CSVファイルダウンロード
            reportBLogic.csvDownLoad(req, res, date, outputFileName);
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

    /**
     * 帳票出力ボタンCSVファイル生成メソッド。
     * 
     * @param tacsflag
     *            TACSフラグ
     * @return date TACSフラグ値
     * @throws Exception
     */
    public void csvCreate(String flg, String date, List<GeneralPurposeOutCsvResVO> generalPurposeOutCsvResVOList,
            String outputFileName, HttpServletRequest req) {
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;
        try {
            // CSV出力用のフォルダー
            String outputPath = req.getServletContext().getRealPath(ApplicationKeys.REPORT_PROTECTED_PATH);
            // CSV出力用の日付フォルダーを取得
            String datePath = outputPath + ApplicationKeys.REPORT_TEMP_PATH + date + "/";
            // Keyフォルダーを作成
            String printPath = datePath;
            File file = new File(printPath);
            // フォルダー存在しない場合
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            // PDF拡張名を設定
            String[] fileName = outputFileName.split("\\.");
            // CSV拡張名を設定
            String csvName = fileName[0] + Constants.CSV;
            // 出力するCSVファイル名を設定
            String csvFileName = printPath + csvName;
            outSTr = new FileOutputStream(new File(csvFileName));
            Buff = new BufferedOutputStream(outSTr);
            String s = "";
            Buff.write(s.getBytes("SHIFT-JIS"));
            if  (generalPurposeOutCsvResVOList != null && generalPurposeOutCsvResVOList.size() > 0) {
                Map<String, String> map = generalPurposeOutCsvResVOList.get(0).getMap();
                int i = 1;
                for (String key :map.keySet()) {
                    s = s +"\""+"項目名" + i + "（"+ key+ ")" + "\""  +",";
                    i ++;
                }
            }
            s =s  + "\"" + "登録日時"+ "\""+"\n";
            Buff.write(s.getBytes("MS932"));
            
            for (GeneralPurposeOutCsvResVO generalPurposeOutCsvResVO : generalPurposeOutCsvResVOList) {
                Map<String, String> map = generalPurposeOutCsvResVO.getMap();
                String s2 = "";
                String s1 = "";
                int j = 1;
                for (String key :map.keySet()) {
                    s1 = s1 +"\"" + "項目値" + j + "（"+  map.get(key)+ ")"+ "\""+ ",";
                    j++;
                }
                s1 = s1 +"\"" + generalPurposeOutCsvResVO.getCreateData()+ "\"";
                s2 = s1 + "\n";
                Buff.write(s2.getBytes("MS932"));
            }
            Buff.flush();
            Buff.close();
        } catch (Exception e) {
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1002);
            throw new BusinessException(messages);
        } finally {
            try {
                Buff.close();
            } catch (Exception e) {
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1002);
                throw new BusinessException(messages);
            }
        }
    }

}
