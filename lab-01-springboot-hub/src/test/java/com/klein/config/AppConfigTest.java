package com.klein.config;

import com.klein.Lab01SpringbootHubApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @package: com.klein.config
 * @description:
 * @author: klein
 * @date: 2021-06-09 17:06
 **/
@Slf4j
public class AppConfigTest extends Lab01SpringbootHubApplicationTests{

    @Autowired
    AppConfig appConfig;


    @Test
    public void name() {
        System.out.println(appConfig.getAppName());
        System.out.println(appConfig.getEncoding());
    }
}