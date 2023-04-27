package com.star.serviceuser.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author star
 * @date 2023/4/14 15:30
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 将自定义的Result类转换成JSON字符串并设置content-type
        response.getWriter().write("{\"code\":101,\"message\":\"token有误\"}");
        log.error("token有误");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
    }
}