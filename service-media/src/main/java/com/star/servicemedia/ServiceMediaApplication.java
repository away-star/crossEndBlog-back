package com.star.servicemedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author 86136
 */
@SpringBootApplication(scanBasePackages = "com.star")
public class ServiceMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMediaApplication.class, args);
    }

}
