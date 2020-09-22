package com.zyj.p2p.base.util;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author onlyzyj
 * @date 2020/9/22-20:49
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequireLogin rl = handlerMethod.getMethodAnnotation(RequireLogin.class);
            if (rl != null){
                if(UserContext.getCurrent() == null){
                    response.sendRedirect("/login.html");
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }
}
