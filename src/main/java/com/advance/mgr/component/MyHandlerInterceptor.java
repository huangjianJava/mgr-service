package com.advance.mgr.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangj
 * @Description:  自定义拦截器
 * @date 2018/7/4
 */
public class MyHandlerInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("========== MyHandlerInterceptor preHandle 方法==========");


        //        String username = (String)httpServletRequest.getSession().getAttribute("username");
//        // 未登录，转发到登录界面
//        if(StringUtils.isBlank(username)){
//            httpServletRequest.setAttribute("login_error","没有权限请先登陆");
//            httpServletRequest.getRequestDispatcher("/login.html").forward(httpServletRequest, httpServletResponse);
//        }
        // 放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
