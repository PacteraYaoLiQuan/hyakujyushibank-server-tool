package com.scsk.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
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
import com.scsk.model.ImageFileDoc;
import com.scsk.request.vo.FileDetailUpdateReqVO;
import com.scsk.response.vo.FileDetailUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
@Service
public class FileDetailUpdService extends AbstractBLogic<FileDetailUpdateReqVO, FileDetailUpdateResVO> {
    
    @Autowired
    private ActionLog actionLog;
    @Value("${cloudantLocal.local}")
    private boolean CLOUDANTLOCAL;
    @Override
    protected void preExecute(FileDetailUpdateReqVO fileDetailUpdateReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param reqVo
     *            入力情報
     * @return resVo 詳細情報
     * @throws Exception
     */
    @Override
    protected FileDetailUpdateResVO doExecute(CloudantClient client, FileDetailUpdateReqVO fileDetailUpdateReqVO)
            throws Exception {

        FileDetailUpdateResVO output = new FileDetailUpdateResVO();
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        List<ImageFileDoc> fileList = new ArrayList<>();
        List<ImageFileDoc> fileInfoDocList = new ArrayList<>();
        ImageFileDoc fileDoc = new ImageFileDoc();
        // ファイル名単一チェック
        String msg= "";
        if (fileDetailUpdateReqVO.getModeType().equals("1")) {
            fileInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_IMAGEFILE_IMAGEFILE, ImageFileDoc.class);
            for (ImageFileDoc fileDoc1 : fileInfoDocList) {
                if (fileDetailUpdateReqVO.getFileNameJP().equalsIgnoreCase(fileDoc1.getFileNameJP())) {
                    fileList.add(fileDoc1);
                    msg = MessageKeys.E_FILEDETAIL_1002;
                    break;
                }
                if ( fileDetailUpdateReqVO.getFileNameEN().equalsIgnoreCase(fileDoc1.getFileNameEN())) {
                    fileList.add(fileDoc1);
                    msg = MessageKeys.E_FILEDETAIL_1003;
                    break;
                }
            }
            if (fileList != null && fileList.size() != 0) {
                // メッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(msg);
                throw new BusinessException(messages);
            }
        } else if(fileDetailUpdateReqVO.getModeType().equals("2")){
            try {
                // ユーザー詳細情報取得
                fileDoc = (ImageFileDoc) repositoryUtil.find(db, fileDetailUpdateReqVO.get_id(), ImageFileDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_FILEDETAIL_1004);
                throw new BusinessException(messages);
            }
        }
        // ファイル名（日本語）
        fileDoc.setFileNameJP(fileDetailUpdateReqVO.getFileNameJP());
        // ファイル名（英語）
        fileDoc.setFileNameEN(fileDetailUpdateReqVO.getFileNameEN());
        // 利用箇所
        fileDoc.setUseLocal(fileDetailUpdateReqVO.getUseLocal());
        // ファイル
        fileDoc.setFileStream(fileDetailUpdateReqVO.getFileStream());
        // 参照URL
        if (fileDetailUpdateReqVO.getPath() != null && fileDetailUpdateReqVO.getPath() != "") {
        String fileName= fileDetailUpdateReqVO.getPath();
        String stt[] = new String[1];
        stt = fileName.split("\\.");
        fileName=fileDetailUpdateReqVO.getFileNameEN()+ "." +stt[1];
        if (CLOUDANTLOCAL) {
            fileDoc.setReferURL("http://localhost:8080/insightManagement/img/" + fileName);
        } else {
            fileDoc.setReferURL("https://minefocus-admin.scsk-admin.minefocus.jp/img/" + fileName);
        }
        // ファイルパス
        fileDoc.setPath("\\webapp\\img\\" + fileName);
        }
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        // アップロード日時
        if (fileDetailUpdateReqVO.getPath() != null && fileDetailUpdateReqVO.getPath() !=""){
            fileDoc.setUploadDatetime(date);
        }
        // DBに更新
        if (fileDetailUpdateReqVO.getModeType().equals("2")) {
            try {
                String fileDetailUpdLog = "（ファイル名：";
                fileDetailUpdLog = fileDetailUpdLog + fileDoc.getFileNameJP();
                repositoryUtil.update(db, fileDoc);
                actionLog.saveActionLog(Constants.ACTIONLOG_IMAGE_FILE_7 + fileDetailUpdLog + ")", db);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.error().add(MessageKeys.E_FILEDETAIL_1006);
                throw new BusinessException(messages);
            }
        } else {
            try {
                String fileDetailAddLog = "（ファイル名：";
                fileDetailAddLog = fileDetailAddLog + fileDoc.getFileNameJP();
                repositoryUtil.save(db, fileDoc);
//                if(fileDoc.getFileStream() == null){
//                    // エラーメッセージを出力、処理終了。
//                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_FILEDETAIL_1005);
//                    throw new BusinessException(messages);
//            }
                actionLog.saveActionLog(Constants.ACTIONLOG_IMAGE_FILE_5 + fileDetailAddLog + ")", db);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_FILEDETAIL_1005);
                throw new BusinessException(messages);
            }
        }
        File directory = new File("");
        String path="";
        if (CLOUDANTLOCAL) {
            // ローカルテスト用
            path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/")
                    + "/src/main/webapp/img";
        } else {
            // BlueMixサーバ実施用
            path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/")
                    + "/apps/myapp.war/img";
        }
        if (fileDetailUpdateReqVO.getPath() != null && fileDetailUpdateReqVO.getPath() !=""){
        String fileName1= fileDetailUpdateReqVO.getPath();
        String stt1[] = new String[1];
        stt1 = fileName1.split("\\.");
        fileName1=fileDetailUpdateReqVO.getFileNameEN()+ "." + stt1[1];
        base64StringToIMAGE(fileDetailUpdateReqVO.getFileStream(),path,fileName1);
        }
        return output;
    }
    private void base64StringToIMAGE(String base64String, String path, String fileName1) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            byte[] bytes = Base64.decodeBase64(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            bin = new BufferedInputStream(bais);
            File file = new File(path + "/" + fileName1);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if(file.delete()) {
                    try {
                        file.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            fout = new FileOutputStream(file);
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
