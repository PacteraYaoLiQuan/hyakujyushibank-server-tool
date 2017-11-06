package com.scsk.authentication;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.AuthorityDoc;
import com.scsk.model.FunctionDoc;
import com.scsk.model.UserDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.OnlineUserBindingListener;
import com.scsk.util.ResultMessages;

public class CustomAuthenticationSuccess extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private CloudantDBService cloudantDBService;
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        LoginUser user = (LoginUser) authentication.getPrincipal();
        HttpSession session = request.getSession();
        session.setAttribute(Constants.SESSIONUSERINFO, user);
        session.setAttribute(Constants.MESSAGEID, "");

        List<UserDoc> userList = new ArrayList<>();
        UserDoc userDoc = new UserDoc();
        List<UserDoc> userInfoDocList = new ArrayList<>();
        String userId = "";
        // if ("admin".equalsIgnoreCase(request.getParameter("j_username"))) {
        // session.setAttribute(Constants.LONGINID, "admin");
        // session.setAttribute(Constants.LONGINNAME,"Admin");
        // session.setAttribute("Authority", "2");
        // session.setAttribute("Account", "2");
        // session.setAttribute("Store", "2");
        // session.setAttribute("User", "2");
        // session.setAttribute("Push", "2");
        // session.setAttribute("ImageFile", "2");
        // session.setAttribute("StoreAtmDataFile", "2");
        // session.setAttribute("HtmlPdfFile", "2");
        // userId= "admin";
        // try {
        // cloudantDBService.cloudantOpen();
        // Database db =
        // cloudantDBService.getCloudantClient().database(Constants.DB_NAME,
        // false);
        // actionLog.saveActionLog(Constants.ACTIONLOG_LOGIN_3, db);
        // } catch (Exception e) {
        // LogInfoUtil.LogError(e.getMessage());
        // // エラーメッセージを出力、処理終了。
        // ResultMessages messages = ResultMessages.warning().add(
        // MessageKeys.E_USER001_1001);
        // throw new BusinessException(messages);
        // }
        // // データベースを取得
        //
        // } else {
        try {
            cloudantDBService.cloudantOpen();
            // データベースを取得
            Database db = cloudantDBService.getCloudantClient().database(Constants.DB_NAME, false);
            // 検索キーを整理する
            userInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_USERLIST_USERLIST, UserDoc.class);
            for (UserDoc userDoc1 : userInfoDocList) {
                if (request.getParameter("j_username").equalsIgnoreCase(encryptorUtil.decrypt(userDoc1.getUserID()))) {
                    userList.add(userDoc1);
                    break;
                }
            }

            //
            // String queryKey = "userID:\"" +
            // encryptorUtil.encrypt(request.getParameter("j_username")) + "\"";
            // // UserDocを取得
            // userList =
            // repositoryUtil.getIndex(db,ApplicationKeys.INSIGHTINDEX_LOGIN_LOGIN_GETUSERINFO,queryKey,
            // UserDoc.class);

            userDoc = (UserDoc) repositoryUtil.find(db, userList.get(0).get_id(), UserDoc.class);
            // ログイン状態を更新する
            userDoc.setLoginStatus(1);
            userId = encryptorUtil.decrypt(userDoc.getUserID());
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
            String date = sdf.format(new Date());
            // 最終ログイン日時を更新する
            userDoc.setEndLoginDateTime(date);
            // UserDocを更新する
            repositoryUtil.update(db, userDoc);

            // 権限一覧初期データを取得
            List<AuthorityDoc> authorityDocList = new ArrayList<>();
            // 権限詳細情報取得
            AuthorityDoc authorityDoc = new AuthorityDoc();
            authorityDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_AUTHORITYLIST_AUTHORITYLIST,
                    AuthorityDoc.class);
            String authorityId = "";
            List<FunctionDoc> functionDocList = new ArrayList<FunctionDoc>();
            functionDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_FUNCTIONLIST_FUNCTIONLIST,
                    FunctionDoc.class);
            if (authorityDocList != null && authorityDocList.size() != 0 && functionDocList != null && functionDocList.size() != 0) {
                for (AuthorityDoc doc : authorityDocList) {
                    if (doc.getAuthorityName().equals(userDoc.getAuthority())) {
                        authorityId = doc.get_id();
                        break;
                    }
                }
                authorityDoc = (AuthorityDoc) repositoryUtil.find(db, authorityId, AuthorityDoc.class);
                List<String> functionIDList = new ArrayList<String>();
                List<String> functionValueList = new ArrayList<String>();
                functionIDList = authorityDoc.getFunctionIDList();
                functionValueList = authorityDoc.getFunctionValueList();
                for (int i = 0; i < functionDocList.size(); i++) {
                    boolean flag = true;
                    for (int j = 0; j < authorityDoc.getFunctionIDList().size(); j++) {
                        String functionID = functionDocList.get(i).getFunctionID();
                        if (functionID.equals(authorityDoc.getFunctionIDList().get(j))) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        functionIDList.add(functionDocList.get(i).getFunctionID());
                        functionValueList.add("0");
                        // アクセス機能ID
                        authorityDoc.setFunctionIDList(functionIDList);
                        // アクセス機能権限
                        authorityDoc.setFunctionValueList(functionValueList);
                    }
                }
                try {
                    repositoryUtil.update(db, authorityDoc);
                } catch (BusinessException e) {
                    // e.printStackTrace();
                    LogInfoUtil.LogDebug(e.getMessage());
                    // エラーメッセージを出力、処理終了。
                    ResultMessages messages = ResultMessages.error().add(MessageKeys.E_AUTHORITYLIST_1005);
                    throw new BusinessException(messages);
                }
                if (!authorityId.isEmpty()) {
                    authorityDoc = (AuthorityDoc) repositoryUtil.find(db, authorityId, AuthorityDoc.class);
                    if (authorityDoc.getFunctionIDList() != null && authorityDoc.getFunctionIDList().size() > 0) {
                        for (int i = 0; i < authorityDoc.getFunctionIDList().size(); i++) {
                            session.setAttribute(authorityDoc.getFunctionIDList().get(i),
                                    authorityDoc.getFunctionValueList().get(i));
                        }
                    } else {
                        for (int i = 0; i < authorityDoc.getFunctionIDList().size(); i++) {
                            session.setAttribute(functionDocList.get(i).getFunctionID(), "0");
                        }
                    }
                } else {
                    for (int i = 0; i < authorityDoc.getFunctionIDList().size(); i++) {
                        session.setAttribute(functionDocList.get(i).getFunctionID(), "0");
                    }
                }
            }

            actionLog.saveActionLog(Constants.ACTIONLOG_LOGIN_3, db);
        } catch (BusinessException e) {

            LogInfoUtil.LogError(e.getMessage());
            // エラーメッセージを出力、処理終了。
            ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_USER001_1001);
            throw new BusinessException(messages);
        } catch (Exception e) {
            LogInfoUtil.LogError(e.getMessage(), e);
        } finally {
            cloudantDBService.cloudantClose();
        }
        
        session.setAttribute("bank_cd", bank_cd);
        // }

        /** add by mulin start **/
        String loingid = (String) session.getAttribute(Constants.LONGINID);
        session.setAttribute("onlineUserBindingListener", new OnlineUserBindingListener(loingid, userId));
        /** add by mulin end **/
    }

}
