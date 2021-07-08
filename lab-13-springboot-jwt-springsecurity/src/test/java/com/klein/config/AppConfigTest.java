package com.klein.config;

import com.klein.SpringbootApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @package: com.klein.config
 * @description:
 * @author: klein
 * @date: 2021-06-09 17:06
 **/
public class AppConfigTest extends SpringbootApplicationTests {

    @Autowired
    AppConfig appConfig;


    @Test
    public void name() {
        System.out.println(appConfig.getAppName());
        System.out.println(appConfig.getEncoding());
    }
}