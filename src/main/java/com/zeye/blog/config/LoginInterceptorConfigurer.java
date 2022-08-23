package com.zeye.blog.config;

import com.zeye.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


/** 处理器拦截器功能的注册 如果不注册 功能无法实现*/
@Configuration //加载当前的拦截器并进行注册 相当于配置类 自己定义
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    /** 配置拦截器,将拦截器注册到过滤器里面*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建自定义拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();

        // 配置白名单，存放再一个List集合

       registry.addInterceptor(interceptor)
               .addPathPatterns("/admin/**")// 表示拦截的url是什么，/**拦截所有请求
               .excludePathPatterns("/admin")
               .excludePathPatterns("/admin/login");
    }
}
