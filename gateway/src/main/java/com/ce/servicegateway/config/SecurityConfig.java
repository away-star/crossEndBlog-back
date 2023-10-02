//package com.ce.servicegateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
///**
// * @author xingxing
// * @version 1.0
// * @description 安全配置类
// * @date 2022/9/27 12:07
// */
//@EnableWebFluxSecurity
//@Configuration
//@SuppressWarnings("all")
//public class SecurityConfig {
//
//
//    /**
//     * 安全拦截配置
//     * @param http
//     */
//
//    @Bean
//    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
//
//        return http.authorizeExchange()
//                .pathMatchers("/**").permitAll()
//                .anyExchange().authenticated()
//                .and().csrf().disable().build();
//    }
//}
