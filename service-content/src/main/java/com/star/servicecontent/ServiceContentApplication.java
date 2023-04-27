package com.star.servicecontent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author 86136
 */
@SpringBootApplication(scanBasePackages = "com.star")
@EnableFeignClients
public class ServiceContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceContentApplication.class, args);
    }

}
