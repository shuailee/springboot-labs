package com.shuailee.hystrixdemo.controller;


import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HystrixDemoController {

    Random random=new Random();
    /**
     * 使用注解方式：
     * 当服务时长超过100ms的时候或方法内异常的时候 执行熔断，调用默认方法
     * */
    @HystrixCommand(
            fallbackMethod = "defaultFail",
            commandProperties = {

                    // 设置并发数，如果达到最大并发数时，后续请求会被拒绝。需要在配置文件中设置HystrixCommand.run()的隔离策略为：SEMAPHORE
                    // https://blog.csdn.net/zhu_19930414/article/details/84636142
                    @HystrixProperty(name="execution.isolation.semaphore.maxConcurrentRequests",value = "2")
                    // 当服务时长超过100ms的时候或方法内异常的时候 执行熔断，调用fallbackMethod中的方法
                    //@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "100")
            }

    )
    @RequestMapping("/hello")
    public String hello(){

        // 当服务出现异常时，也会执行容错方法
        // random = null;
        try {
            int i = random.nextInt(500);
            System.out.println(i);
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello";
    }
    public String defaultFail(){
        return "fail 短路";
    }




}
