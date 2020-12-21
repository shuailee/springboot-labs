package com.shuailee.hystrixdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
// 在启动类上添加@EnableCircuitBreaker来开启Hystrix的断路器功能
@EnableCircuitBreaker
@EnableHystrixDashboard
public class StudyGupaoHystrixdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyGupaoHystrixdemoApplication.class, args);
    }

}
