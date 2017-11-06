package com.scsk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.ImageFileDoc;
import com.scsk.request.vo.FileSelReqVO;
import com.scsk.response.vo.FileInitResVO;
import com.scsk.util.ActionLog;
import com.scsk.vo.FileInitVO;

/**
 * ファイル一覧検索サービス。<br>
 * <br>
 * ファイル一覧検索を実装するロジック。<br>
 */
@Service
public class FileListSelService extends 
AbstractBLogic<FileSelReqVO, FileInitResVO> {
    
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param なし
     *            検索条件
     */
    @Override
    protected void preExecute(FileSelReqVO fileSelReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param なし
     *            検索条件
     */
    @Override
    protected FileInitResVO doExecute(CloudantClient client, FileSelReqVO fileSelReqVO)
            throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);

        // ファイル一覧初期データを取得
        List<ImageFileDoc> fileInfoDocList = new ArrayList<>();
        List<ImageFileDoc> fileInfoDocList1 = new ArrayList<>();
        List<FileInitVO> fileList = new ArrayList<>();
        FileInitResVO fileInitResVO = new FileInitResVO();
        fileInfoDocList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_IMAGEFILE_IMAGEFILE, ImageFileDoc.class);
        if (fileSelReqVO.getFileNameJP().isEmpty() && fileSelReqVO.getUseLocal().isEmpty() ) {
            fileInfoDocList1 = fileInfoDocList;
        } else {
//            MapReduce view = new MapReduce();
            // 画面の検索条件、ファイル名（日本語）だけ入力場合
            if (!fileSelReqVO.getFileNameJP().isEmpty() && fileSelReqVO.getUseLocal().isEmpty()) {
             
                for (ImageFileDoc imageFileDoc:fileInfoDocList) {
                    if (imageFileDoc.getFileNameJP().contains(fileSelReqVO.getFileNameJP())){
                        fileInfoDocList1.add(imageFileDoc);
                    }
                }
            } 
            //画面の検索条件、利用箇所だけ入力場合
            else if (fileSelReqVO.getFileNameJP().isEmpty() && !fileSelReqVO.getUseLocal().isEmpty()) {
                for (ImageFileDoc imageFileDoc:fileInfoDocList) {
                    if (imageFileDoc.getUseLocal().contains(fileSelReqVO.getUseLocal())){
                        fileInfoDocList1.add(imageFileDoc);
                    }
                }
            }
            //画面の検索条件、ファイル名（日本語）と利用箇所入力場合
            else if (!fileSelReqVO.getFileNameJP().isEmpty() && !fileSelReqVO.getUseLocal().isEmpty()) {
                for (ImageFileDoc imageFileDoc:fileInfoDocList) {
                    if (imageFileDoc.getFileNameJP().contains(fileSelReqVO.getFileNameJP()) 
                            && imageFileDoc.getUseLocal().contains(fileSelReqVO.getUseLocal())){
                        fileInfoDocList1.add(imageFileDoc);
                    }
                }
            }
        }
        if (fileInfoDocList1 != null && fileInfoDocList1.size() != 0) {
            for (ImageFileDoc doc : fileInfoDocList1) {
                FileInitVO fileInitVO = new FileInitVO();
                // ファイル一覧初期化用データを戻る
                fileInitVO.set_id(doc.get_id());
                fileInitVO.set_rev(doc.get_rev());
                fileInitVO.setFileNameJP(doc.getFileNameJP());
                fileInitVO.setUseLocal(doc.getUseLocal());
                fileInitVO.setReferURL(doc.getReferURL());
                fileList.add(fileInitVO);
            }
        } 
        // ファイル一覧初期データを取得
        fileInitResVO.setFileList(fileList);
        actionLog.saveActionLog(Constants.ACTIONLOG_IMAGE_FILE_2, db);
        return fileInitResVO;
    }
}
