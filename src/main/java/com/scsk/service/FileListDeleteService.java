package com.scsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.UpLoadFileDoc;
import com.scsk.response.vo.FileUploadResVo;
import com.scsk.util.ActionLog;
import com.scsk.util.ResultMessages;

@Service
public class FileListDeleteService extends AbstractBLogic<FileUploadResVo, FileUploadResVo> {
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param UserListDeleteReqVO
     *            ユーザー一覧情報
     */
    @Override
    protected void preExecute(FileUploadResVo FileUploadResVo) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param FileUploadResVo
     * @return FileUploadResVo 一括削除情報
     */
    @Override
    protected FileUploadResVo doExecute(CloudantClient client, FileUploadResVo fileUploadResVo)
            throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        for (int i = 0; i < fileUploadResVo.getFileFindListReqVo().size(); i++) {
            if (fileUploadResVo.getFileFindListReqVo().get(i).getSelect() == null) {
                continue;
            }
            if (fileUploadResVo.getFileFindListReqVo().get(i).getSelect() == true) {
                UpLoadFileDoc upLoadFileDoc = new UpLoadFileDoc();
                try {
                    // 削除前検索
                    upLoadFileDoc = (UpLoadFileDoc) repositoryUtil.find(db, fileUploadResVo.getFileFindListReqVo().get(i).get_id(),
                            UpLoadFileDoc.class);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_FILE001_1003);
                    throw new BusinessException(messages);
                }
                try {
                    // ユーザー情報DBを削除
                    repositoryUtil.removeByDocId(db, upLoadFileDoc.get_id());
                    actionLog.saveActionLog(Constants.ACTIONLOG_HTML_PDF_FILE_5 , db);
                } catch (BusinessException e) {
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_FILE001_1004);
                    throw new BusinessException(messages);
                }

            }
        }
        return fileUploadResVo;
    }

}
