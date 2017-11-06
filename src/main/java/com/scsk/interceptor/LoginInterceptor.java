package com.scsk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CloudantDBService cloudantDBService;
    @Autowired
    public RepositoryUtil repositoryUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 全てのControllerを実行する前の処理
        // TODO
        System.out.println("preHandle");
        return super.preHandle(request, response, handler);
    }

}
