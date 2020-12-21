package com.shuailee.hystrixdemo.common;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: study-gupao-hystrixdemo
 * @description: HystrixConfiguration
 * @author: shuai.li
 * @create: 2020-06-09 12:00
 **/
@Configuration
public class HystrixConfiguration {

    /**
     * 注册一个servlet
     * */
    @Bean(name = "hystrixRegistrationBean")
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                new HystrixMetricsStreamServlet(), "/hystrix.stream");
        registration.setName("hystrixServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }
}
