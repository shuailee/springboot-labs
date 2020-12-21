package com.shuailee.springbootdubboclient.controller;

import com.shuailee.dubbo.api.IPayService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    /**
     * dubbo提供的注解，自动将以来的dubbo接口注入
     * 在客户端配置的负载均衡策略loadbalance要高于服务端的配置（如果服务端也配置了的话）
     * */
    @Reference(loadbalance = "roundrobin")
    IPayService payService;

    @GetMapping("/pay")
    public String pay(){
        return payService.pay("钢铁侠");
    }
}
