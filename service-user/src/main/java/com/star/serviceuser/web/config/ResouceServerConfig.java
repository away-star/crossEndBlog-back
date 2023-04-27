package com.star.serviceuser.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;


@Configuration
@EnableResourceServer // 标识该应用是一个资源服务器
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // 开启方法级别的权限控制
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    //资源服务标识
    public static final String RESOURCE_ID = "cross-end";

    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID)//资源 id
                .tokenStore(tokenStore)
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf() // 禁用 csrf
                .disable()
                .authorizeRequests().anyRequest().permitAll().and()

                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.BAD_REQUEST));
        // .and()
        //.authorizeRequests()
        // .anyRequest().permitAll();
    }
}
