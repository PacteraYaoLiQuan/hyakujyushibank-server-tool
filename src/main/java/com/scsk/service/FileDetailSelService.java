package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
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
public class FileDetailSelService extends 
AbstractBLogic <FileDetailUpdateReqVO, FileDetailUpdateResVO>{
    
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(FileDetailUpdateReqVO detailReqVO) throws Exception {

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
    protected FileDetailUpdateResVO doExecute(CloudantClient client,
            FileDetailUpdateReqVO detailReqVO) throws Exception {
        String fileDetailLog="（ファイル名：";
        FileDetailUpdateResVO fileDetailUpdateResVO = new FileDetailUpdateResVO();
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        // ファイル詳細情報取得
        ImageFileDoc imageFileDoc = new ImageFileDoc();
        try {
            imageFileDoc = (ImageFileDoc) repositoryUtil.find(db, detailReqVO.get_id(),
                    ImageFileDoc.class);
            fileDetailLog=fileDetailLog+imageFileDoc.getFileNameJP();
        } catch (BusinessException e) {
            // e.printStackTrace();
            LogInfoUtil.LogDebug(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(
                    MessageKeys.E_FILEDETAIL_1004);
            throw new BusinessException(messages);
        }

        // 戻り値を設定
        fileDetailUpdateResVO.setFileNameJP(imageFileDoc.getFileNameJP());
        // ファイル名（英語）
        fileDetailUpdateResVO.setFileNameEN(imageFileDoc.getFileNameEN());
        // 利用箇所
        fileDetailUpdateResVO.setUseLocal(imageFileDoc.getUseLocal());
        // 参照URL
        fileDetailUpdateResVO.setReferURL(imageFileDoc.getReferURL());
        // PATH
        fileDetailUpdateResVO.setPath(imageFileDoc.getPath());
        // fileStream
        fileDetailUpdateResVO.setFileStream(imageFileDoc.getFileStream());
        actionLog.saveActionLog(Constants.ACTIONLOG_IMAGE_FILE_6 +fileDetailLog+")", db);
        return fileDetailUpdateResVO;
    }
}
