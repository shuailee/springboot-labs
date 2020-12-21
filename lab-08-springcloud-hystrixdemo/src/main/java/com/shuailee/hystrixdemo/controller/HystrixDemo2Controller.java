package com.shuailee.hystrixdemo.controller;

import com.netflix.hystrix.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HystrixDemo2Controller {

    @GetMapping("hello2")
    public String helloword2(){
        return new CommandHelloWorld("hello world").execute();
    }


    Random random=new Random();
    /**
     * 使用编码方式 实现熔断
     * http://c.biancheng.net/view/5374.html
     * */
    class CommandHelloWorld extends com.netflix.hystrix.HystrixCommand<String> {

        private final String name;

        public CommandHelloWorld(String name) {

            super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyGroup"))
                    //设置Spring Cloud Hystrix资源隔离策略（线程、信号量）
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE
                            ))
                    //设置用于确定 Hystrix 使用信号量策略时最大的并发请求数。 execution.isolation.semaphore.maxConcurrentRequests
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withExecutionIsolationSemaphoreMaxConcurrentRequests(2
                            ))
            );
            // run请求时长超过100就执行Fallback
            // 底层使用的是 withExecutionTimeoutInMilliseconds
            /*super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"),
                    100);*/
            this.name = name;
        }

        @Override
        protected String run() {
            int value = random.nextInt(8000);
            try {
                System.out.println("helloword2 costs " + value + " ms.");
                Thread.sleep(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // a real example would do work like a network call here
            return "Hello " + name + "!";
        }

        /**
         * 容错执行
         * */
        @Override
        protected String getFallback() {
            System.out.println("接口熔断");
            return "接口熔断";
        }
    }

}
