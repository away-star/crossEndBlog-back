package com.ce.serviceusercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xingxing
 */
@SpringBootApplication(scanBasePackages = "com.ce")
@EnableFeignClients
public class ServiceUserCenterApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServiceUserCenterApplication.class, args);
    }

}
