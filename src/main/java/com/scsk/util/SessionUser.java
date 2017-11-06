package com.scsk.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.scsk.authentication.LoginUser;
import com.scsk.constants.Constants;

/**
 * 
 * @author ws
 *
 */
public class SessionUser {

    /**
     * 
     * @return
     */
    public static String userId() {
        String loginUserId = "";
        HttpServletRequest request = GlobalRequest.request();

        if (request == null) {
        	loginUserId = "Batch";
        } else if (request.getHeader("authKey1") != null) {
        	loginUserId = request.getHeader("authKey1");
        } else {
            HttpSession session = request.getSession();
            LoginUser user = (LoginUser) session.getAttribute(Constants.SESSIONUSERINFO);

            if (user != null) {
            	loginUserId = user.getUsername();
            } else {
                String loingid = (String) session.getAttribute(Constants.LONGINID);
                loginUserId = loingid == null ? "" : loingid;
            }
        }
        return loginUserId;
    }
    public static String userName() {
        String loginUserName = "";
        HttpServletRequest request = GlobalRequest.request();
        HttpSession session = request.getSession();
        loginUserName = (String) session.getAttribute(Constants.LONGINNAME);
        return loginUserName;
    }
}
