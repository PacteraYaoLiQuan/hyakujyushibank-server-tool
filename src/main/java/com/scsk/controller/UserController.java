package com.scsk.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scsk.exception.BusinessException;
import com.scsk.model.UserDoc;
import com.scsk.service.UserAddService;
import com.scsk.service.UserSelService;
import com.scsk.service.UserUpdService;
import com.scsk.util.ResultMessages;

/**
 * ユーザ追加＆編集コントロール。<br>
 * <br>
 * ユーザ登録画面からユーザ追加＆編集を実装すること。<br>
 */
@Controller
@RequestMapping("/protected")
public class UserController {

    @Autowired
    private UserSelService userSelService;
    @Autowired
    private UserAddService userAddService;
    @Autowired
    private UserUpdService userUpdService;

    /**
     * ユーザ追加・更新メソッド。
     * 
     * @param req
     *            request
     * @return 戻る画面ID
     */
    @RequestMapping(value = "/userok", method = RequestMethod.POST)
    public String saveUser(HttpServletRequest req, Model model) {
        // 画面から入力パラメータを取得する
        UserDoc userBean = new UserDoc();
        userBean.setUserName(req.getParameter("username"));
        userBean.setPassword(req.getParameter("password"));
        userBean.setAuthority(req.getParameter("role"));
        try {
            // 登録ボタンを押下する場合
            if ("add".equals(req.getParameter("action"))) {
                // サービスの実行メソッドを呼び出す
                userBean = userAddService.execute(userBean);
                // 更新ボタンを押下する場合
            } else if ("upd".equals(req.getParameter("action"))) {
                userBean = userUpdService.execute(userBean);
            }
        } catch (BusinessException be) {
            ResultMessages messages = be.getResultMessages();
            model.addAttribute(messages);
            // エラーがある場合、本画面に戻る
            return "/protected/security/user.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "welcomePage";
    }

    /**
     * ユーザ一覧表示メソッド。
     * 
     * @param req
     *            request
     * @return 戻る画面ID
     */
    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    public String searchUser(HttpServletRequest req, Model model) {
        // 画面から入力パラメータを取得する
        // 画面から入力パラメータを取得する
        UserDoc userBean = new UserDoc();
        List<UserDoc> userList = new ArrayList<UserDoc>();
        try {
            userList = (List<UserDoc>) userSelService.execute(userBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("customers", userList);
        return "/protected/security/userlist.jsp";
    }

    /**
     * ユーザ追加メソッド。
     * 
     * @param req
     *            request
     * @return 戻る画面ID
     */
    @RequestMapping(value = "/useradd", method = RequestMethod.GET)
    public String printUser(HttpServletRequest req, ModelMap map) {

        return "/protected/security/user.jsp";
    }

}
