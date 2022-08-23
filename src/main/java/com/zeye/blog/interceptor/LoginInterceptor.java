package com.zeye.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/** 定义一个拦截器*/
public class LoginInterceptor implements HandlerInterceptor {



    /**
     * 检测全局session对象中是否含有user数据，如果有则放行，如果没有则重定向到登陆页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器(url+Controller：映射)
     * @return  如果返回值为true表示放行当前的请求，如果返回值为false则表示拦截当前的请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 通过HttpServletRequest对象来获取session对象
        Object obj = request.getSession().getAttribute("user");
        if (obj == null){
            //说明用户没有登录过系统，则重定向到login.html页面
            response.sendRedirect("/admin");
            //结束后续调用
            return  false;
        }
        return true;
    }
}
