package com.ce.serviceusersecurity.web.config;

import com.ce.servicecommon.interception.TokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xingxing
 * @create: 2023-10-02 14:20
 * @Description: 拦截器配置
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor;

    /*
     * @param registry
     * Return void
     * Author xingxing
     * Date 2023/10/2
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> patterns = new ArrayList<>();

        patterns.add("/**");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns(patterns)
                .excludePathPatterns("/user/updateInfo")
                .excludePathPatterns("/Proverb/all");
    }

    /**
     * //下面代码意思是:配置一个拦截器，如果访问路径时addResourceHandler中的这个路径（我这里是/**表示所有的路径），
     *  那么就映射到访问本地的addResourceLocations这个路径上，这样就可以看到该路径上的资源了
     * @param registry
     */
    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //
    //    registry.addResourceHandler("/**") //配置需要添加静态资源请求的url
    //            .addResourceLocations("classpath:/pic/"); //配置静态资源路径
    //}
}