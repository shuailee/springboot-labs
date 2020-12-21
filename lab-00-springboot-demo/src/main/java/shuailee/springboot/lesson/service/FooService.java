package shuailee.springboot.lesson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import shuailee.springboot.lesson.entity.Foo;
import shuailee.springboot.lesson.event.FooRegisterEvent;
import shuailee.springboot.lesson.repository.FooRepository;

import java.util.List;

/**
 * @program: lesson
 * @description:
 * @create: 2019-10-17 21:18
 **/
@Service
public class FooService {
    /**
     * 用于事件发布
     * */
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FooRepository fooRepository;


    public void addFoo(Foo foo) {
        //业务逻辑处理.........

        //发布用户注册的事件
        applicationContext.publishEvent(new FooRegisterEvent(this, foo));
    }

    public List<Foo> findAll() {
        return fooRepository.findAll();
    }
}

/**
 * https://www.jianshu.com/p/ef2cee8c5dd1
 * spring自定义事件监听
 * Spring的事件需要遵循如下流程：
     自定义事件，集成ApplicationEvent。
     定义事件监听器，实现ApplicationListener。
     使用容器发布事件。
 *
 * */