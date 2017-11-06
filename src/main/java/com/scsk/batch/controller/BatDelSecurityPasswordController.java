package com.scsk.batch.controller;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.exception.BusinessException;
import com.scsk.model.AccountAppDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;
import com.scsk.util.LogInfoUtil_Batch;

@Component
public class BatDelSecurityPasswordController implements Job {
    private RepositoryUtil repositoryUtil = new RepositoryUtil();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        CloudantDBService cloudantDBService = (CloudantDBService) context.getJobDetail().getJobDataMap()
                .get("cloudantDBService");

        try {
            cloudantDBService.cloudantOpen();
            CloudantClient cloudantClient = cloudantDBService.getCloudantClient();

            Database db = cloudantClient.database(Constants.DB_NAME, false);
            updateDoc(db);

        } catch (Exception e) {
            LogInfoUtil_Batch.logWarn(e.getMessage(), e);
        } finally {
            cloudantDBService.cloudantClose();
        }

    }

    public void updateDoc(Database db) {

        LogInfoUtil_Batch.LogInfo("==========  申込データの暗証番号削除バッチ　実行開始  ==========");

        List<AccountAppDoc> accountAppList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPBATCH, AccountAppDoc.class);

        int dataCount = accountAppList.size();
        int successCount = 0;
        int faileCount = 0;

        for (int i = 0; i < accountAppList.size(); i++) {

            try {
                // 更新前検索
                accountAppList.get(i).setSecurityPassword("");
                accountAppList.get(i).setDirectServicesCardPassword("");
                
                // タグ情報DBを更新
                repositoryUtil.update(db, accountAppList.get(i));
                successCount = successCount + 1;
            } catch (BusinessException e) {
                // エラーメッセージを出力、処理終了。
                LogInfoUtil_Batch.LogError(e.getMessage());
                faileCount = faileCount + 1;
            }
        }

        LogInfoUtil_Batch.LogInfo("処理件数：　" + String.valueOf(dataCount) + "件");
        LogInfoUtil_Batch.LogInfo("処理成功件数：　" + String.valueOf(successCount) + "件");
        LogInfoUtil_Batch.LogInfo("処理失敗件数：　" + String.valueOf(faileCount) + "件");
        LogInfoUtil_Batch.LogInfo("==========  申込データの暗証番号削除バッチ　実行完了  ==========");
    }

}
