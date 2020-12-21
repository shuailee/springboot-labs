package shuailee.springboot.lesson.common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
  * 使用Spring Boot Runner启动器，可以通过实现接口ApplicationRunner或者CommandLineRunner的方式在启动时运行一些特定的代码
 *  CommandLineRunner会在ApplicationRunner之后被回调
  * */
@Component
@Order(1) //@Order 当有多个自定义启动器的时候需要使用Order注解来控制执行顺序
public class MyCommandLineRunerDemo implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("MyCommandLineRunerDemo启动啦");
    }
}
