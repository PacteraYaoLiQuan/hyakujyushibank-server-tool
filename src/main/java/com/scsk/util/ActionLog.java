package com.scsk.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.ActionLogDoc;
import com.scsk.model.UserDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;

@Service
public class ActionLog {
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private CloudantDBService cloudantDBService;

    public void saveActionLog(String actionLog, Database db) {
        try {
            ActionLogDoc actionLogDoc = new ActionLogDoc();
            SimpleDateFormat sdf=new SimpleDateFormat(Constants.DATE_FORMAT_DB); 
            Date date = new Date();
            String accessDateTime=sdf.format(date);
            String userID = encryptorUtil.encrypt(SessionUser.userId());
            String userName = encryptorUtil.encrypt(SessionUser.userName());
            actionLogDoc.setDocType(Constants.DOCTYPE_4);
            actionLogDoc.setAccessDatetime(accessDateTime);
            actionLogDoc.setUserID(userID);
            actionLogDoc.setUserName(userName);
            actionLogDoc.setActionLog(actionLog);
            repositoryUtil.save(db, actionLogDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveActionLog(String actionLog) {
        try {
            cloudantDBService.cloudantOpen();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        CloudantClient cloudantClient = cloudantDBService.getCloudantClient();
        Database db = cloudantClient.database(Constants.DB_NAME, false);
        try {
            ActionLogDoc actionLogDoc = new ActionLogDoc();
            SimpleDateFormat sdf=new SimpleDateFormat(Constants.DATE_FORMAT_DB); 
            Date date = new Date();
            String accessDateTime=sdf.format(date);
            String userID = encryptorUtil.encrypt(SessionUser.userId());
            String userName = encryptorUtil.encrypt(SessionUser.userName());
            actionLogDoc.setDocType(Constants.DOCTYPE_4);
            actionLogDoc.setAccessDatetime(accessDateTime);
            actionLogDoc.setUserID(userID);
            actionLogDoc.setUserName(userName);
            actionLogDoc.setActionLog(actionLog);
            repositoryUtil.save(db, actionLogDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveActionLog(String actionLog,String userId) {
        try {
            cloudantDBService.cloudantOpen();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        CloudantClient cloudantClient = cloudantDBService.getCloudantClient();
        Database db = cloudantClient.database(Constants.DB_NAME, false);
        String userName = null;
        String queryKey;
        // UserDocを取得
        try {
            queryKey = "userID:\"" + encryptorUtil.encrypt(userId) + "\"";
            List<UserDoc>userList = repositoryUtil.getIndex(db,ApplicationKeys.INSIGHTINDEX_LOGIN_LOGIN_GETUSERINFO,queryKey, UserDoc.class);
            if(userList!=null&& userList.size()!=0){
                userName=userList.get(0).getUserName();
            }
            ActionLogDoc actionLogDoc = new ActionLogDoc();
            SimpleDateFormat sdf=new SimpleDateFormat(Constants.DATE_FORMAT_DB); 
            Date date = new Date();
            String accessDateTime=sdf.format(date);
            actionLogDoc.setDocType(Constants.DOCTYPE_4);
            actionLogDoc.setAccessDatetime(accessDateTime);
            actionLogDoc.setUserID(encryptorUtil.encrypt(userId));
            actionLogDoc.setUserName(userName);
            actionLogDoc.setActionLog(actionLog);
            actionLogDoc.setCreatedBy(userId);
            actionLogDoc.setUpdatedBy(userId);
            repositoryUtil.saveActionLog(db, actionLogDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
