package com.klein.config;

import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                // 不配的话，默认当前项目端口
                //.host("localhost:8081")
                .apiInfo(apiInfo())
                .pathMapping("/")
                // 选择哪些路径和api会生成document
                .select()
                // 对所有api进行监控
                .apis(RequestHandlerSelectors.any())
                // 选择监控的package
                .apis(RequestHandlerSelectors.basePackage("com.klein.controller"))
                // 只监控有ApiOperation注解的接口
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //错误路径不监控
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                // 对根下所有路径进行监控
                .paths(PathSelectors.regex("/.*"))
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("项目接口文档")
                //.contact(new Contact("Hanstrovsky", "www.hanstrovsky.com", "Hanstrovsky@gmail.com"))
                .description("这是用Swagger动态生成的接口文档")
                .termsOfServiceUrl("NO terms of service")
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0")
                .build();
    }
}
