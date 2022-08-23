package com.zeye.blog.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect     // 切面
@Component  //开启组件扫描
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截 controller里面的所有方法
     */
    @Pointcut("execution(* com.zeye.blog.controller.*.*(..))")//标注为切面,里面的内容说明要拦截那一些类
    public void log(){


    }

    @Before("log()")//方法之前 这个方法会在这个切面之前执行
    public void doBefore(JoinPoint joinPoint){// 切面之前
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);

        logger.info("Request : {}",requestLog);
    }


    @After("log()")// 这个方法会在这个切面之后执行
    public void doAfter(){
        logger.info("---------------doAfter--------------");
    }

    /**
     * @AfterReturning(returning = "返回的东西")
     * pointcut = "log()"  切面就是log
     * @param result
     */
    @AfterReturning(returning = "result",pointcut = "log()")  // 这个方法会在这个切面之后执行然后返回
    public void doAfterReturn(Object result){
        logger.info("Result : {} ", result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
