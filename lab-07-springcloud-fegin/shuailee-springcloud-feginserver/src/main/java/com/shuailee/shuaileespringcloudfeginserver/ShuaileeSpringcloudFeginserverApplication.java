package com.shuailee.shuaileespringcloudfeginserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ShuaileeSpringcloudFeginserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShuaileeSpringcloudFeginserverApplication.class, args);
    }

}
