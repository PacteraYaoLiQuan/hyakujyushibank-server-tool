package com.scsk.batch.controller;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scsk.service.CloudantDBService;
import com.scsk.util.LogInfoUtil_Batch;

/**
 * バッチコントロールクラス
 */

@Service
public class BatchStart {
    @Value("${cloudantLocal.local}")
    private boolean CLOUDANTLOCAL;
    @Value("${bank_cd}")
    private String bankCode;
    // @Autowired
    // private RepositoryUtil repositoryUtil;
    private static SchedulerFactory batchSchedulerFactory = new StdSchedulerFactory();

    // JOB_GROUP
    private static String BATCH_JOB_GROUP = "BATCH_JOB_GROUP";
    // TRIGGER_GROUP
    private static String BATCH_TRIGGER_GROUP = "BATCH_TRIGGER_GROUP";

    // 申込データの暗証番号削除バッチ JOB
    private static String JOB_BAT_DEL_SECURITYPASSWORDTIME = "JOB_BAT_DEL_SECURITYPASSWORDTIME";
    // 申込データの暗証番号削除バッチ TRIGGER
    private static String TRIGGER_BAT_DEL_SECURITYPASSWORDTIME = "TRIGGER_BAT_DEL_SECURITYPASSWORDTIME";

    private static String JOB_STOREATM = "JOB_STOREATM";
    private static String TRIGGER_STOREATM = "TRIGGER_STOREATM";
    
    @Value("${batDelSecurityPassword.time}")
    private String BATDELSECURITYPASSWORDTIME; // 申込データの暗証番号削除バッチ

    @Value("${springBatch.runBatDelSecurityPassword}")
    private boolean runBatDelSecurityPassword;
    @Value("${batStoreATM.time}")
    private String BATSTOREATMTIME;
    @Value("${springBatch.runBatStoreATM}")
    private boolean runBatStoreATM;
    private CronTrigger batDelSecurityPasswordTrigger;
    // TODO 【サンプール】テスト用複数バッチ
    private CronTrigger batStoreATMTrigger;
    private Scheduler batchScheduler;

    @Autowired
    private CloudantDBService cloudantDBService;

    @PostConstruct
    public void autowork() {

        try {
            // スケジュールを取得
            batchScheduler = batchSchedulerFactory.getScheduler();
            if (batchScheduler.getJobNames(BATCH_JOB_GROUP).length == 0) {
                if (runBatDelSecurityPassword) {
                    /* バッチJOB定義 */
                    // 申込データの暗証番号削除バッチ JOB
                    JobDetail delSecurityPasswordDetail = new JobDetail(JOB_BAT_DEL_SECURITYPASSWORDTIME,
                            BATCH_JOB_GROUP, BatDelSecurityPasswordController.class);
                    // TODO 【サンプール】テスト用複数バッチ
                    JobDataMap cloudantDBMap = new JobDataMap();
                    cloudantDBMap.put("CLOUDANTLOCAL", CLOUDANTLOCAL);
                    cloudantDBMap.put("bankCode", bankCode);
                    cloudantDBMap.put("cloudantDBService", cloudantDBService);
                    delSecurityPasswordDetail.setJobDataMap(cloudantDBMap);
                    // batBakAccountAppDetail.setJobDataMap(cloudantDBMap);

                    // 申込データの暗証番号削除バッチ
                    batDelSecurityPasswordTrigger = new CronTrigger(TRIGGER_BAT_DEL_SECURITYPASSWORDTIME,
                            BATCH_TRIGGER_GROUP);
                    batDelSecurityPasswordTrigger.setCronExpression(BATDELSECURITYPASSWORDTIME);
                    batchScheduler.scheduleJob(delSecurityPasswordDetail, batDelSecurityPasswordTrigger);
                    LogInfoUtil_Batch.LogInfo("**********  申込データの暗証番号削除バッチ　正常起動  **********");

                }
                if (runBatStoreATM) {
                    JobDetail batStoreATMDetail = new JobDetail(JOB_STOREATM, BATCH_JOB_GROUP,
                            BatStoreATMStartController.class);
                    JobDataMap cloudantDBMap = new JobDataMap();
                    cloudantDBMap.put("CLOUDANTLOCAL", CLOUDANTLOCAL);
                    cloudantDBMap.put("bankCode", bankCode);
                    cloudantDBMap.put("cloudantDBService", cloudantDBService);
                    batStoreATMDetail.setJobDataMap(cloudantDBMap);
                    batStoreATMTrigger = new CronTrigger(TRIGGER_STOREATM, BATCH_TRIGGER_GROUP);
                    batStoreATMTrigger.setCronExpression(BATSTOREATMTIME);
                    batchScheduler.scheduleJob(batStoreATMDetail, batStoreATMTrigger);
                    LogInfoUtil_Batch.LogInfo("**********  店舗ATMマスタデータ自動更新バッチ　正常起動  **********");
                }
                batchScheduler.start();
                LogInfoUtil_Batch.LogInfo("**********  バッチグループ　正常起動成功  **********");
            }
        } catch (Exception e) {
            LogInfoUtil_Batch.LogInfo("**********  バッチグループ　起動失敗  **********");
            LogInfoUtil_Batch.LogError(e.getMessage());
        }
    }
}
