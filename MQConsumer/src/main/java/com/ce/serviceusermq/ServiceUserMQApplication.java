package com.ce.serviceusermq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Author xingxing
 * Date 2023/10/2
 */
@SpringBootApplication(scanBasePackages = "com.ce")
public class ServiceUserMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserMQApplication.class, args);
    }

}
