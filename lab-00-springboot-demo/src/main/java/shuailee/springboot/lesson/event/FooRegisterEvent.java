package shuailee.springboot.lesson.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import shuailee.springboot.lesson.entity.Foo;

/**
 * @program: lesson
 * @description: 自定义事件监听
 * @create: 2019-10-17 20:53
 **/
public class FooRegisterEvent extends ApplicationEvent {

    /**
     * 传递给监听事件的订阅的信息
     * */
    public Foo foo;

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    /**
     @param source 发生事件的对象
     @     * */
    public FooRegisterEvent(Object source, Foo foo) {
        super(source);
        this.foo = foo;
    }
}
