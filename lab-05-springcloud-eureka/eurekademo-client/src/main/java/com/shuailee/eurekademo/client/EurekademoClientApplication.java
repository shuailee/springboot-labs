package com.shuailee.eurekademo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //注解表明是一个Eureka客户端
public class EurekademoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekademoClientApplication.class, args);
    }

}
