package com.shuailee.dubbo.provider;

import com.shuailee.dubbo.api.IPayService;
import org.apache.dubbo.config.annotation.Service;

// dubbo的注解
@Service(loadbalance = "random")
public class PayService implements IPayService {

    @Override
    public String pay(String uid) {

        System.out.println("服务端被调用...");
        return "发起支付了" + uid;
    }
}
