package com.shuailee.springboot.study.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuailee.springboot.study.api.mapper.CatMapper;
import com.shuailee.springboot.study.api.model.Cat;
import com.shuailee.springboot.study.api.model.CatDto;
import com.shuailee.model.result.DataResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-study
 * @description: HomeController
 * @author: shuai.li
 * @create: 2020-05-18 16:29
 **/
@RestController
public class HomeController {

    @RequestMapping("/getdata")
    public DataResult getData(@RequestParam String code, @RequestParam String name) {
        Cat cat = new Cat();
        cat.setGoodId(code);
        cat.setGoodName(name);
        CatDto catDto = CatMapper.INSTANCE.carToCarDto(cat);
        System.out.println(catDto.toString());
        return DataResult.ok();
    }


    @RequestMapping("/getdata2")
    public DataResult getData() {
        Cat cat = new Cat();
        cat.setGoodId("aaa");
        cat.setGoodName("bbb");
        CatDto catDto = CatMapper.INSTANCE.carToCarDto(cat);
        System.out.println(catDto.toString());
        return DataResult.ok();
    }


    @RequestMapping("/getdata3")
    public DataResult getData(Cat cat) {
        CatDto catDto = CatMapper.INSTANCE.carToCarDto(cat);
        System.out.println(catDto.toString());
        return DataResult.ok();
    }


    public static void main(String[] args) {
        String json="{\"goodId\":\"111\",\"goodName\":\"aaa\",\"goodPrice\":\"2.3\",\"typeName\":\"AAA\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Cat cat = objectMapper.readValue(json, Cat.class);
            System.out.println(cat.getGoodId());
            System.out.println(cat.getGoodName());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
