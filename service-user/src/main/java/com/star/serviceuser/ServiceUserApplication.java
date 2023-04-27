package com.star.serviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author star
 */
@SpringBootApplication(scanBasePackages = "com.star")
@EnableFeignClients
@EnableAuthorizationServer
public class ServiceUserApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }

}
