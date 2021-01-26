package com.shuailee.skworder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @package: com.shuailee.springbootdemo.controller
 * @description:
 * @author: shuai.li
 * @date: 2020-09-15 11:47
 **/
@Slf4j
@RestController
public class HomeController {

    @RequestMapping("/order")
    public String order() {
        Random random = new Random();
        int randomInt = random.nextInt(1000);
        try {

            Thread.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(1 + randomInt);

        System.getProperties();
        log.info("你好 Order - " + randomInt);
        return "你好 Order - " + randomInt;
    }
}
