package com.scsk.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.Constants;
import com.scsk.model.AccountAppSeqDoc;
import com.scsk.request.vo.AccountAppSeqReqVO;
import com.scsk.response.vo.AccountAppSeqResVO;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.SessionUser;
/**
 * 受付番号のシーケンス取得
 * 
 * @author ylq
 */
@Service
public class AccountAppSeqService extends AbstractBLogic<AccountAppSeqReqVO, AccountAppSeqResVO> {

    // 検索条件
    private final String ACCOUNTAPPSEQ_id = "AccountAppSeq%s";
    // DOCタイプ
    private final String DOCTYPE = "ACCOUNTAPPSEQ";
    // 自動フィルビットの
    private final String REV = "%08d";
    // 文字列の傍受
    private final String SPLIT = "-";
    @Autowired
    private EncryptorUtil encryptorUtil;

    @Override
    protected void preExecute(AccountAppSeqReqVO input) throws Exception {
    }

    @Override
    protected AccountAppSeqResVO doExecute(CloudantClient client, AccountAppSeqReqVO input) throws Exception {
        AccountAppSeqResVO output = new AccountAppSeqResVO();
        // dbを接続して。
        Database db = client.database(Constants.DB_NAME, false);
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_APP);
        String date = sdf.format(new Date());
        // 検索条件_id
        String key = String.format(ACCOUNTAPPSEQ_id, date);

        AccountAppSeqDoc accountAppSeqDoc = null;

        try {
            // 検索
            accountAppSeqDoc = db.find(AccountAppSeqDoc.class, key);

        } catch (NoDocumentException e) {
             
        } catch (Exception e) {
            
        }

        // システム日時を取得
        SimpleDateFormat saveDatesdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String saveDate = saveDatesdf.format(new Date());

        // 検索結果が１件の場合、処理２へ
        if (accountAppSeqDoc != null) {
            
//            accountAppSeqDoc.set_rev("330-328c9da699811f2e26e207fe714b00c4");
            
            // パラメータ.userId
            accountAppSeqDoc.setUserId(encryptorUtil.encrypt(input.getUsetId()));
            // 更新者設定
            accountAppSeqDoc.setUpdatedBy(SessionUser.userId());
            // 更新日時設定
            accountAppSeqDoc.setUpdatedDate(saveDate);

            try {
                // 成功の場合：申込採番DOCの更新後_Rev返して、処理終了
                Response response = db.update(accountAppSeqDoc);
                // 更新後の_revを取って、”＿”の先頭文字を取得して返す
                String rev = response.getRev();
                // 文字列の傍受
                String rev2[] = rev.split(SPLIT);
                String rev3 = rev2[0];
                // _revの例00000001
                String rev4 = String.format(REV, Integer.parseInt(rev3));
                output.setAppSeq(rev4);
                return output;
            } catch (DocumentConflictException e) {
                throw e;
            }
            // システムエラーが起こった(検索結果が0件)場合、処理３へ
        } else {
            // 申込採番DOC生成
            accountAppSeqDoc = new AccountAppSeqDoc();
            accountAppSeqDoc.set_id(key);
            accountAppSeqDoc.setDocType(DOCTYPE);
            accountAppSeqDoc.setUserId(encryptorUtil.encrypt(input.getUsetId()));

            // 作成者設定
            accountAppSeqDoc.setCreatedBy(SessionUser.userId());
            // 作成日時設定
            accountAppSeqDoc.setCreatedDate(saveDate);
            // 更新者設定
            accountAppSeqDoc.setUpdatedBy(SessionUser.userId());
            // 更新日時設定
            accountAppSeqDoc.setUpdatedDate(saveDate);
            // 削除フラグ
            accountAppSeqDoc.setDelFlg("0");
            try {
                // 成功の場合：申込採番DOCの_Rev返して、処理終了
                Response response = db.save(accountAppSeqDoc);
                // 新規後の_revを取って、”＿”の先頭文字を取得して返す
                String rev = response.getRev();
                // 文字列の傍受
                String rev2[] = rev.split(SPLIT);
                String rev3 = rev2[0];
                // _revの例00000001
                String rev4 = String.format(REV, Integer.parseInt(rev3));
                output.setAppSeq(rev4);
                return output;
            } catch (DocumentConflictException e2) {
                throw e2;
            }
        }
    }
}
