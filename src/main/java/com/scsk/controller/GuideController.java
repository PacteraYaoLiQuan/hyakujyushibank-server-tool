package com.scsk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scsk.authentication.LoginUser;
import com.scsk.constants.Constants;
import com.scsk.util.ActionLog;

/**
 * ログインブーツコントロール。<br>
 * <br>
 * ユーザーを導くために、ログイン時にパスワードの変更を強制する必要があります。<br>
 */
@Controller
@RequestMapping("/guide")
public class GuideController {
    @Autowired
    private ActionLog actionLog;
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
    public String guide(HttpSession session,HttpServletResponse response,HttpServletRequest request) {

        // セッション情報取得
        LoginUser user = (LoginUser) session.getAttribute(Constants.SESSIONUSERINFO);

        // パスワード変更フラグ＝＝0場合
        if (user.getChangePasswordFlg() == 0) {
            // パスワード変更画面を修正
        	session.setAttribute(Constants.FLG, "1");
        	actionLog.saveActionLog(Constants.ACTIONLOG_PASSWORD_1);
            return "redirect:/view/passwordUpd";
        }else if(user.getChange90DayFlg() == 1){
        	// パスワード変更画面を修正
            session.setAttribute(Constants.FLG, "1");
            return "redirect:/view/passwordUpd";
        } else {
            // ウェルカム画面
        	session.setAttribute(Constants.FLG, "0");
            return "redirect:/view/master";
        }
    }
}