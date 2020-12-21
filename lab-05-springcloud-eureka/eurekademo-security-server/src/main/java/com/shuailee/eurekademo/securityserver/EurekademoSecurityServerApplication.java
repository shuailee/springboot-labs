package com.shuailee.eurekademo.securityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //启用Euerka注册中心功能
public class EurekademoSecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekademoSecurityServerApplication.class, args);
    }

}
