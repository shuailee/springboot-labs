package com.shuailee.service;

import mvcframework.annotation.ShuaiService;

@ShuaiService
public class HomeService implements IDataProcess {

    public String getName(String name){
        return "homeservice: hello word name- "+name;
    }

    public String getAge(Integer age){
        return "homeservice: hello word age- "+age;
    }
}
