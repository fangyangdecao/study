package com.yhy.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class AuthenticationInterceptorAdapter extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //LocalDateTime now = LocalDateTime.now();
        if (3>1){
            System.out.println("时间拦截器start，通过");
            return true;
        }else {
            System.out.println("时间拦截器start，没通过");
            return false;
        }
    }

}
