package com.star.serviceuser.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration
public class TokenConfig {

    private String SIGNING_KEY ="cross-end";//和其他服务一致来解析token

    @Autowired
    TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    @Bean(name="authorizationServerTokenServicesCustom")
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices(); // 创建默认Token管理服务
        service.setSupportRefreshToken(true); // 设置支持刷新Token
        service.setTokenStore(tokenStore); // 设置Token存储策略
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain(); // 创建Token增强器链
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter, new CustomTokenEnhancer())); // 将自定义Token增强器添加到增强器链中
        service.setTokenEnhancer(tokenEnhancerChain); // 将Token增强器链设置到Token管理服务中
        service.setAccessTokenValiditySeconds(7200); // 设置accessToken过期时间为2小时（默认值）
        service.setRefreshTokenValiditySeconds(259200); // 设置refreshToken过期时间为3天（默认值）
        return service; // 返回Token管理服务
    }

//    @Bean
//    public TokenEnhancer tokenEnhancer() {
//        return new CustomTokenEnhancer();
//    }

//    @Autowired
//    TokenStore tokenStore;
//
//    @Bean
//    public TokenStore tokenStore() {
//        //使用内存存储令牌（普通令牌）
//        return new InMemoryTokenStore();
//    }
//
//    @Bean(name="authorizationServerTokenServicesCustom")
//    public AuthorizationServerTokenServices tokenService() {
//        DefaultTokenServices service=new DefaultTokenServices();
//        service.setSupportRefreshToken(true);//支持刷新令牌
//        service.setTokenStore(tokenStore);//令牌存储策略
//        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
//        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
//        return service;
//    }



}
