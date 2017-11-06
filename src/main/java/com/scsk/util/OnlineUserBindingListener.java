package com.scsk.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.scsk.exception.BusinessException;
import com.scsk.request.vo.LoginStatusUpdReqVO;
import com.scsk.service.LoginStatusUpdService;

public class OnlineUserBindingListener implements HttpSessionBindingListener {
    
	private String userID;
	private String loginID;
    public OnlineUserBindingListener() {
    
    }
	public  OnlineUserBindingListener(String userID,String loginID) {
		 this.userID = userID;
		 this.loginID = loginID;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();

		// ユーザーをリストに保存
		List<String> onlineUserList = (List<String>) application.getAttribute("onlineUserList");
		
		// 初期化
		if (onlineUserList == null) {
			onlineUserList = new ArrayList<String>();
			application.setAttribute("onlineUserList", onlineUserList);
		}
		onlineUserList.add(this.userID);
		System.out.println(this.userID + "　ログイン");
		
		// ログ作成Sample
//		LogInfoUtil_Action.LogInfo(this.userID + "　ログイン LogInfoUtil_Action");
//		LogInfoUtil_Manager.LogInfo(this.userID + "　ログイン LogInfoUtil_Manager");
//		
//		LogInfoUtil_Batch.LogInfo("バッチログテスト");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
        
		// ユーザーをリストに保存削除
		List<String> onlineUserList = (List<String>) application.getAttribute("onlineUserList");
		onlineUserList.remove(this.userID);
        LoginStatusUpdReqVO input = new LoginStatusUpdReqVO();
        input.setUserID(this.loginID);
        try {
              WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
              WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(webApplicationContext.getServletContext());
              LoginStatusUpdService loginStatusUpdService = (LoginStatusUpdService) BeanFactoryUtils.beanOfTypeIncludingAncestors(webAppContext, LoginStatusUpdService.class);
              loginStatusUpdService.execute(input);
//              ActionLog actionLog =  (ActionLog) BeanFactoryUtils.beanOfTypeIncludingAncestors(webAppContext, ActionLog.class);
//              actionLog.saveActionLog(Constants.ACTIONLOG_LOGOUT_1,this.loginID); 
        } catch (BusinessException e) {
              LogInfoUtil.LogInfo(e.getErrorCode());
         } catch (Exception e) {
                 // TODO Auto-generated catch block
              e.printStackTrace();
         }
         //TODO ユーザー登録状態更新箇所　↓
         System.out.println(this.userID + "　ログアウト");
        // ログ作成Sample
//		LogInfoUtil_Action.LogInfo(this.userID + "　ログアウト LogInfoUtil_Action");
//		LogInfoUtil_Manager.LogInfo(this.userID + "　ログアウト LogInfoUtil_Manager");
	}
}
