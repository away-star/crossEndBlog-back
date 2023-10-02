package com.ce.servicemedia.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author xingxing
 * @date 2023/4/14 13:23
 */
@Configuration
public class SwaggerConfig {
    // 创建Docket存入容器，Docket代表一个接口文档
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("cross-end blog service-media")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ce.servicemedia.web.controller")) // 扫描指定包下的controller
                // 指定@ApiOperation标注的接口被加入文档
                .build();
    }

    // 创建接口文档的具体信息，会显示在接口文档页面中
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                // 文档标题
                .title("标题：媒体资源系统接口文档")
                // 文档描述
                .description("描述：本文档描述了媒体资源系统的接口定义")
                // 版本
                .version("1.0")
                // 联系人信息
                .contact(new Contact("ce", "http://github.com", "2064989403@qq.com"))
                // 版权
                .license("ce")
                // 版权地址
                .licenseUrl("http://github.com")
                .build();
    }
}
