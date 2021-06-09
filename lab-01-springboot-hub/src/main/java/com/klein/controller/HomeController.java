package com.klein.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package: com.klein.controller
 * @description:
 * @author: klein
 * @date: 2021-06-09 16:33
 **/
@RestController
@RequestMapping("/api")
public class HomeController {

    @RequestMapping("/hello")
    public String Hello() {
        return "hello";
    }
}
