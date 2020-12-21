package com.shuailee.configcenter.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${word}")
    private String word;

    @RequestMapping("hello")
    public String hello(){
        return word;
    }
}
