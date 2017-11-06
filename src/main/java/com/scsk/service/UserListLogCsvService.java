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
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.ActionLogDoc;
import com.scsk.request.vo.UserListLogOutputReqVO;
import com.scsk.response.vo.UserListLogCsvOutputResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.SortList;
import com.scsk.vo.UserInitVO;
import com.scsk.vo.UserListLogCsvInitVO;

/**
 * CSV一覧検索サービス。<br>
 * <br>
 * CSV一覧検索を実装するロジック。<br>
 */
@Service
public class UserListLogCsvService extends AbstractBLogic<UserListLogOutputReqVO, UserListLogCsvOutputResVO> {

    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param userListLogOutputReqVO
     * 
     */
    @Override
    protected void preExecute(UserListLogOutputReqVO userListLogCsvOutputReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param userListLogOutputReqVO
     * 
     * @return userListLogOutputReqVO CSV出力用情報
     */
    @Override
    protected UserListLogCsvOutputResVO doExecute(CloudantClient client, UserListLogOutputReqVO userListLogOutputReqVO)
            throws Exception {
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        String userListLogCsvOutputLog = "（ユーザーID：";
        // 出力用データを取得
        UserListLogCsvOutputResVO userListLogCsvOutputResVO = new UserListLogCsvOutputResVO();
        List<UserListLogCsvInitVO> userListLogCsvOutput = new ArrayList<UserListLogCsvInitVO>();
        // 検索キーを整理する
        List<ActionLogDoc> actionLogList = repositoryUtil.getView(db,
                ApplicationKeys.INSIGHTVIEW_ACTIONLOGLIST_ACTIONLOGLIST, ActionLogDoc.class);

        List<UserInitVO> userInitVoList = new ArrayList<UserInitVO>();
        for (int i = 0; i < userListLogOutputReqVO.getLogOutputList().size(); i++) {
            // if (userListLogOutputReqVO.getLogOutputList().get(i).getSelect()
            // == null) {
            // continue;
//            }
            if (userListLogOutputReqVO.getLogOutputList().get(i).getSelect() != null
                    && userListLogOutputReqVO.getLogOutputList().get(i).getSelect() == true) {
                userInitVoList.add(userListLogOutputReqVO.getLogOutputList().get(i));
                userListLogCsvOutputLog=userListLogCsvOutputLog+userListLogOutputReqVO.getLogOutputList().get(i).getUserID()+"/";
            }
        }
        try {
            if (actionLogList != null && actionLogList.size() > 0) {
                for (ActionLogDoc actionLogDoc : actionLogList) {
                    for (UserInitVO userInitVO : userInitVoList) {
                        UserListLogCsvInitVO userListLogCsvInitVO = new UserListLogCsvInitVO();
                        if ((encryptorUtil.decrypt(actionLogDoc.getUserID())).equals(userInitVO.getUserID())
                                && userListLogOutputReqVO.getEndTime()
                                        .compareTo(actionLogDoc.getAccessDatetime().substring(0, 10)) >= 0
                                && userListLogOutputReqVO.getStartTime()
                                        .compareTo(actionLogDoc.getAccessDatetime().substring(0, 10)) <= 0) {
                            // CSV出力用データを戻る
                            userListLogCsvInitVO.set_id(actionLogDoc.get_id());
                            userListLogCsvInitVO.set_rev(actionLogDoc.get_rev());
                            // 操作日時
                            userListLogCsvInitVO.setAccessDatetime(actionLogDoc.getAccessDatetime());
                            // userID
                            userListLogCsvInitVO.setUserID(encryptorUtil.decrypt(actionLogDoc.getUserID()));
                            // ユーザー名
                            userListLogCsvInitVO.setUserName(encryptorUtil.decrypt(actionLogDoc.getUserName()));
                            // 操作／行動ログ
                            userListLogCsvInitVO.setActionLog(actionLogDoc.getActionLog());
                            userListLogCsvOutput.add(userListLogCsvInitVO);
                        }
                    }
                }
            }
        } catch (BusinessException e) {
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1008);
            throw new BusinessException(messages);
        }
        // ソート順
        SortList<UserListLogCsvInitVO> sortList = new SortList<UserListLogCsvInitVO>();
        sortList.Sort(userListLogCsvOutput, "getAccessDatetime", null);

        userListLogCsvOutputResVO.setUserListLogCsvOutput(userListLogCsvOutput);
        userListLogCsvOutputLog = userListLogCsvOutputLog.substring(0, userListLogCsvOutputLog.length() - 1);
        actionLog.saveActionLog(Constants.ACTIONLOG_USERLIST_4 + userListLogCsvOutputLog + ")", db);
        return userListLogCsvOutputResVO;
    }
}
