package com.scsk.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.model.UserDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.Utils;

public class CustomAuthenticationFailure implements
		AuthenticationFailureHandler {

	@Autowired
	private CloudantDBService cloudantDBService;
	@Autowired
	private RepositoryUtil repositoryUtil;
	@Autowired
	private EncryptorUtil encryptorUtil;
    @Autowired
    private ActionLog actionLog;
	
	private String defaultFailureUrl;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
        session.setAttribute(Constants.USERID, request.getParameter("j_username"));
        session.setAttribute(Constants.HIDUSERID, request.getParameter("hidUserID"));
        session.setAttribute(Constants.PASSWORD, request.getParameter("j_password"));
        session.setAttribute(Constants.COUNT, request.getParameter("count"));
        session.setAttribute(Constants.SELECTFLG,request.getParameter("selectFlg"));
        session.setAttribute(Constants.MESSAGEID,request.getParameter("errMessageID"));
		String userID = request.getParameter("j_username");
		String hidUserID = request.getParameter("hidUserID");
		String count = request.getParameter("count");
		int count2 = 0;
		boolean  lockStatus = false;
		String checkFlg = "";
		List<UserDoc> userList = new ArrayList<>();
		
		// パスワードを入力しない
		if (!Utils.isNotNullAndEmpty(request.getParameter("j_username"))) {
            session.setAttribute(Constants.MESSAGEID,MessageKeys.E_LOGIN001_1018);
            redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
        } else if (!Utils.isNotNullAndEmpty(request.getParameter("j_password"))) {
            session.setAttribute(Constants.MESSAGEID,MessageKeys.E_LOGIN001_1005);
            redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
        } else {
    			try {
    				cloudantDBService.cloudantOpen();
    				// データベースを取得
    				Database db = cloudantDBService.getCloudantClient().database(Constants.DB_NAME, false);
    				// 検索キーを整理する
    				String queryKey = "userID:\"" + encryptorUtil.encrypt(userID) + "\"";
    				// UserDocを取得
    				userList = repositoryUtil.getIndex(db,ApplicationKeys.INSIGHTINDEX_LOGIN_LOGIN_GETUSERINFO,queryKey, UserDoc.class);
    				if (userList != null && userList.size() > 0) {
    					lockStatus = userList.get(0).isLockStatus();
    					// ユーザーIDがロックされる
    					if (lockStatus == true) {
    						checkFlg = "1";
    						session.setAttribute(Constants.MESSAGEID,MessageKeys.E_LOGIN001_1002);
    						actionLog.saveActionLog(Constants.ACTIONLOG_LOGIN_2, userID);
    						redirectStrategy.sendRedirect(request, response,defaultFailureUrl);
    					}
    				} else {
    					// ユーザーIDが間違い
    					checkFlg = "1";
    					session.setAttribute(Constants.MESSAGEID,MessageKeys.E_LOGIN001_1001);
    					actionLog.saveActionLog(Constants.ACTIONLOG_LOGIN_1, userID);
    					redirectStrategy.sendRedirect(request, response,defaultFailureUrl);
    				}
    			} catch (Exception e) {
    				LogInfoUtil.LogError(e.getMessage(), e);
    			} finally {
    				cloudantDBService.cloudantClose();
    			}

			// 連続入力間違い回数加算
			if (!Utils.isNotNullAndEmpty(checkFlg)) {
				if (Utils.isNotNullAndEmpty(count)) {
					if (userID.equals(hidUserID)) {
						count2 = Integer.parseInt(count);
						count2 = count2 + 1;
						count = String.valueOf(count2);
					} else {
						count = "1";
					}
				} else {
					count = "1";
				}
				session.setAttribute(Constants.COUNT, count);

				// 一定回数ログインに失敗したので、ロックされました（ロック状態を更新）
				if (Integer.parseInt(count) >= 5) {
					UserDoc userDoc = new UserDoc();
					try {
						cloudantDBService.cloudantOpen();
						// データベースを取得
						Database db = cloudantDBService.getCloudantClient().database(Constants.DB_NAME, false);
						userDoc = (UserDoc) repositoryUtil.find(db, userList.get(0).get_id(), UserDoc.class);
						// ロック状態を更新する
						userDoc.setLockStatus(true);
						repositoryUtil.update(db, userDoc);
						session.setAttribute(Constants.MESSAGEID,MessageKeys.E_LOGIN001_1004);
						redirectStrategy.sendRedirect(request, response,defaultFailureUrl);
						actionLog.saveActionLog(Constants.ACTIONLOG_PASSWORD_2, db);
					} catch (Exception e) {
						LogInfoUtil.LogError(e.getMessage(), e);
					} finally {
						cloudantDBService.cloudantClose();
					}
				} else {
					session.setAttribute(Constants.MESSAGEID,MessageKeys.E_LOGIN001_1001);
					redirectStrategy.sendRedirect(request, response,defaultFailureUrl);
				}
			}
		}

		session.setAttribute(Constants.HIDUSERID,request.getParameter("j_username"));
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), "'" + defaultFailureUrl + "' is not a valid redirect URL");
		this.defaultFailureUrl = defaultFailureUrl;
	}
}
