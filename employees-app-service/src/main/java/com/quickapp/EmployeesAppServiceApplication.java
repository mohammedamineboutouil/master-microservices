package com.quickapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmployeesAppServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeesAppServiceApplication.class, args);
    }

}
