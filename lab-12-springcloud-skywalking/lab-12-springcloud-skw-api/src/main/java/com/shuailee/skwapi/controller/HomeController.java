package com.shuailee.skwapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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


    @RequestMapping("/createOrder")
    public String hello() {

        RestTemplate restTemplate=new RestTemplate();
        log.info("Welcome To Acme Financial. Calling Acme Financial's Back Office Microservice");
        String response = restTemplate.getForObject("http://127.0.0.1:8089/order", String.class);
        Random random = new Random();
        int randomInt = random.nextInt(1000);
        try {

            Thread.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("你好 Klein - " + randomInt);
        log.error("你好 Klein  error - " + randomInt);
        log.info("Got response from Acme Financial's Back Office Microservice [{}]", response);
        return response;

    }
}
