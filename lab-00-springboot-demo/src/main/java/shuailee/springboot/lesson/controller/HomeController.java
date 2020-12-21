package shuailee.springboot.lesson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shuailee.springboot.lesson.config.*;

/**
 * @program: lesson
 * @description:
 * @create: 2019-10-08 13:44
 **/
@RestController
public class HomeController {

    @Autowired
    InfoAppConfig infoAppConfig;
    @Autowired
    InfoAppConfig2 infoAppConfig2;

    @Autowired
    MyInfoConfig myInfoConfig;

    @Autowired
    MyInfoConfig2 myInfoConfig2;



    /**
     * 所有加载出来的配置都可以通过Environment注入获取到：env.getProperty("info.address")
     * */
    @Autowired
    private Environment env;


    @RequestMapping("/hello")
    public String hello() {


        System.out.println("@Value方式讀取application配置:" + infoAppConfig.getAddress());
        System.out.println("@Value方式讀取application配置:" + infoAppConfig.getCompany());

        System.out.println("@ConfigurationProperties方式讀取application配置:"+infoAppConfig2.getAddress());
        System.out.println("@ConfigurationProperties方式讀取application配置:"+infoAppConfig2.getCompany());

        System.out.println("@Value + @PropertySource方式讀取myconfig自定義配置:"+myInfoConfig.getFlag());
        System.out.println("@Value + @PropertySource方式讀取myconfig自定義配置:"+myInfoConfig.getName());

        System.out.println("@ConfigurationProperties + @PropertySource方式讀取myconfig自定義配置:"+myInfoConfig2.getFlag());
        System.out.println("@ConfigurationProperties + @PropertySource方式讀取myconfig自定義配置:"+myInfoConfig2.getName());

        System.out.println("直接通过Environment注入获取："+env.getProperty("info.address"));


        return "hello spring";
    }


}
