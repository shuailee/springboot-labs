package com.shuailee.springboot.study.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// @EnableScheduling  //启动调度器
// 指定扫描的包的根路径 ，如果不指定只扫描当前包下的bean，如果依赖包名不一样可能会导致依赖包的bean加载不到
@ComponentScan(basePackages = "com.shuailee")
public class SpringbootStudyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStudyApiApplication.class, args);
    }

}
