package com.klein.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
// @ConfigurationProperties(prefix = "spring")
public class AppConfig {
    /**
     * 在变量中注入spring默认配置文件中的配置
     * 如果类上打了前缀注解 @ConfigurationProperties(prefix = "spring")  ，则字段注入可直接： @Value("${application.name}")
     * */
    @Value("${spring.application.name}")
    public String appName;
    /**
     * 当配置位空时给定默认值，${配置key:默认值}
     * */
    @Value("${spring.info.git.encoding:utf-8}")
    public String encoding;

}


