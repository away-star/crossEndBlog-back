package com.star.serviceuser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@Slf4j
@EnableFeignClients
@EnableAuthorizationServer
public class ServiceUserApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }

}
