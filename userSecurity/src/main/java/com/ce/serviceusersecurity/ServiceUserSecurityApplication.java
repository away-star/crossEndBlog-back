package com.ce.serviceusersecurity;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author xingxing
 */
@SpringBootApplication(scanBasePackages = "com.ce")
@MapperScan(basePackages = "com.ce.serviceusersecurity.mapper", annotationClass = Mapper.class)
@EnableFeignClients
@EnableAspectJAutoProxy
public class ServiceUserSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceUserSecurityApplication.class, args);
    }

}
