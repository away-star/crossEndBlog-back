package com.star.servicecontent.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer // 标识该应用是一个资源服务器
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // 开启方法级别的权限控制
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {

    //资源服务标识
    public static final String RESOURCE_ID = "cross-end";

    @Autowired
    TokenStore tokenStore; // 注入 TokenStore

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID) // 资源 id
                .tokenStore(tokenStore) // 采用 TokenStore 进行管理
                .stateless(true); // 所有请求都无状态
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf() // 禁用 csrf
                .disable()
                .authorizeRequests() // 权限配置
                .antMatchers("/r/**").authenticated() // 所有 /r/** 的请求必须认证通过
                .anyRequest().permitAll(); // 其他所有请求都允许访问
    }
}
