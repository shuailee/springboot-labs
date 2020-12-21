package shuailee.springboot.lesson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import shuailee.springboot.lesson.entity.Foo;
import shuailee.springboot.lesson.service.FooService;

import java.util.List;

/**
 * @program: lesson
 * @description:
 * @create: 2019-10-09 20:28
 **/
@RestController
public class JdbcTemplateController {

    @Autowired
    FooService fooService;

    /**
     * JdbcTemplate
     * */
    @GetMapping("/list")
    public List<Foo> queryUsers() {
        List<Foo> list = fooService.findAll();
        return list;
    }


    @PostMapping("/add")
    public void add(@RequestBody Foo foo) {
        /**
        * 如果@RequestBody 接收不到foo参数，很有可能是因为FOO实体的大小写与传入的json大小写不匹配造成的
        * 字段上可以通过@JsonProperty(value = "ID") 来矫正
        * */
        fooService.addFoo(foo);
    }

}
