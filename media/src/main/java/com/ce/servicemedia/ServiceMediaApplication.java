package com.ce.servicemedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author xingxing
 */
@SpringBootApplication(scanBasePackages = "com.ce")
public class ServiceMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMediaApplication.class, args);
    }

}
