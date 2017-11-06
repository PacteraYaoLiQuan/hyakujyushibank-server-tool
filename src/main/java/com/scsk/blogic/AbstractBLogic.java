package com.scsk.blogic;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudant.client.api.CloudantClient;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;
import com.scsk.util.LogInfoUtil;

/**
 * 全てserviceクラスの基礎クラス。<br>
 * <br>
 * serviceクラスの実行流れをコントロールする。<br>
 * 
 * @param <T>
 * @param I
 *            Input パラメータ
 * @param O
 *            Output パラメータ
 */
public abstract class AbstractBLogic<I, O> implements BLogic<I, O> {

    @Autowired
    private CloudantDBService cloudantDBService;

    @Autowired
    public RepositoryUtil repositoryUtil;

    /**
     * 主処理メソッド。
     * 
     * @param I
     *            Input パラメータ
     * @throws Exception
     *             システムエラー
     */
    public O execute(I input) throws Exception {
        String name = input.getClass().getSimpleName();
        try {
            LogInfoUtil.LogInfoStrat(name.substring(0, name.length() - 5));
            // 主処理の前置き共通処理
            preCommon(input);

            // 主処理の前置き処理（パラメータ設定、チェックなど）
            preExecute(input);

            // ビジネスロジックを実現メソッド
            O output = doExecute(cloudantDBService.getCloudantClient(), input);

            return output;
        } finally {
            // 主処理実行した後の共通処理
            nextCommon(input);
            LogInfoUtil.LogInfoEnd(name.substring(0, name.length() - 5));
        }

    }

    /**
     * 主処理の前置き共通処理。
     * 
     * @param I
     *            Input パラメータ
     */
    private void preCommon(I input) throws Exception {
        cloudantDBService.cloudantOpen();
    }

    /**
     * 主処理の前置き処理（パラメータ設定、チェックなど）。 各serviceクラスを実現必要
     * 
     * @param I
     *            Input パラメータ
     */
    protected abstract void preExecute(I input) throws Exception;

    /**
     * ビジネスロジックを実現メソッド。 各serviceクラスを実現必要
     * 
     * @param client
     *            DB接続オブジェクト
     * @param I
     *            Input パラメータ
     * @throws Exception
     *             システムエラー
     */
    protected abstract O doExecute(CloudantClient client, I input) throws Exception;

    /**
     * 主処理実行した後の共通処理。
     * 
     * @param I
     *            Input パラメータ
     */
    private void nextCommon(I input) {
        cloudantDBService.cloudantClose();
    }

}