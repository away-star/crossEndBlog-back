package com.star.serviceuser.web.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Mr.M
 * @version 1.0
 * @description 安全管理配置
 * @date 2022/9/26 20:53
 */
@EnableWebSecurity //开启security服务
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //允许在方法上加的注解来配置权限
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



 /*   @Autowired
    DaoAuthenticationProviderCustom daoAuthenticationProviderCustom;

    //使用自己定义DaoAuthenticationProviderCustom来代替框架的DaoAuthenticationProvider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProviderCustom);
    }
*/

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    //配置用户信息服务
//    @Bean
//    public UserDetailsService userDetailsService() {
//        //这里配置用户信息,这里暂时使用这种方式将用户存储在内存中
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        //在内存创建zhangsan,密码123,分配权限p1
//        manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
//        //在内存创建lisi,密码456,分配权限p2
//        manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
//        return manager;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //密码为明文方式
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }



    //配置安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .authorizeRequests()
               // .antMatchers(HttpMethod.POST,"/information/test").authenticated()//访问/r开始的请求需要认证通过
                .anyRequest().permitAll()
                .and()
                .csrf().disable();//其它请求全部放行

        //处理登录逻辑
/*        http.addFilterAfter(loginFilter(), UsernamePasswordAuthenticationFilter.class);*/
    }

   /* @Bean
    public LoginFilter loginFilter() throws Exception{
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationManager(authenticationManager());
        return loginFilter;
    }*/
}
