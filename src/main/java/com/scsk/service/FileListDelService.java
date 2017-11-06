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
import com.scsk.request.vo.FileListDeleteReqVO;
import com.scsk.util.ActionLog;
import com.scsk.util.ResultMessages;

@Service
public class FileListDelService extends AbstractBLogic<FileListDeleteReqVO, FileListDeleteReqVO> {
    
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param FileListDeleteReqVO
     *            ファイル一覧情報
     */
    @Override
    protected void preExecute(FileListDeleteReqVO FileListDeleteReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param FileListDeleteReqVO
     *            ファイル一覧情報
     * @return FileListDeleteReqVO 一括削除情報
     */
    @Override
    protected FileListDeleteReqVO doExecute(CloudantClient client, FileListDeleteReqVO fileListDeleteReqVO)
            throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        String fileListDelLog = "（ファイル名：";
        for (int i = 0; i < fileListDeleteReqVO.getDeleteList().size(); i++) {
            if (fileListDeleteReqVO.getDeleteList().get(i).getSelect() == null) {
                continue;
            }
            if (fileListDeleteReqVO.getDeleteList().get(i).getSelect() == true) {
                fileListDelLog = fileListDelLog + fileListDeleteReqVO.getDeleteList().get(i).getFileNameJP() + "/";
                ImageFileDoc fileDoc = new ImageFileDoc();
                try {
                    // 削除前検索
                    fileDoc = (ImageFileDoc) repositoryUtil.find(db, fileListDeleteReqVO.getDeleteList().get(i).get_id(),
                            ImageFileDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_FILEDETAIL_1004);
                    throw new BusinessException(messages);
                }
                try {
                    // ユーザーファイル情報DBを削除
                    
                    repositoryUtil.removeByDocId(db, fileDoc.get_id());
                    
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_FILEDETAIL_1007);
                    throw new BusinessException(messages);
                }
            }
        }
        fileListDelLog = fileListDelLog.substring(0, fileListDelLog.length()-1);
        actionLog.saveActionLog(Constants.ACTIONLOG_IMAGE_FILE_3 + fileListDelLog + ")", db);
        return fileListDeleteReqVO;
    }

}
