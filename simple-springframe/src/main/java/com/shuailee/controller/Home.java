package com.shuailee.controller;

import com.shuailee.service.HomeService;
import mvcframework.annotation.ShuaiAutowired;
import mvcframework.annotation.ShuaiController;
import mvcframework.annotation.ShuaiRequestMapping;
import mvcframework.annotation.ShuaiRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ShuaiController
public class Home {

    @ShuaiAutowired
    HomeService homeService;

    @ShuaiRequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp,
                      @ShuaiRequestParam("name") String name)
    {
        String result = homeService.getName(name);
		//String result = "My name is " + name;
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @ShuaiRequestMapping("/query/age")
    public void queryAge(HttpServletRequest req, HttpServletResponse resp, @ShuaiRequestParam() Integer age)
    {
        String result = homeService.getAge(age);
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
