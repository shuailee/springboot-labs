package com.shuailee.springbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @package: com.shuailee.springbootdemo.controller
 * @description:
 * @author: shuai.li
 * @date: 2020-09-15 11:47
 **/
@RestController
public class HomeController {

    @RequestMapping("/hello")
    public String hello() {
        Random random = new Random();
        int randomInt = random.nextInt(1000);
        try {

            Thread.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(1 + randomInt);

        System.getProperties();
        return "你好 SHUAI - "+ randomInt;
    }


    public static void main(String[] args) {
        List<String> skulist=new ArrayList<>();
        skulist.add("a");
        skulist.add("b");
        skulist.add("c");
        skulist.add("d");
        for (int i = 0; i < 3; i++) {
            Collections.shuffle(skulist);
            System.out.println(skulist);
        }

    }
}
