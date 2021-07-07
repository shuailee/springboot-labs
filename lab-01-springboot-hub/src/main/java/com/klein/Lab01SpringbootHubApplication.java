package com.klein;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.klein.dao.mapper")
public class Lab01SpringbootHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab01SpringbootHubApplication.class, args);
    }

}
