package com.shuailee.dumpdemo.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: dump-demo
 * @description:
 * 需要指定VM参数
 * -Xms20M
 * -Xmx20M
 * -Xmn10M
 * -XX:+PrintGCDetails
 * -XX:SurvivorRatio=8

 * @create: 2019-10-18 17:50
 **/
public class HeapOOM {

/**
 *
 https://www.cnblogs.com/luoxn28/p/5425425.html
 https://blog.csdn.net/lom9357bye/article/details/79686939
 * */
    //静态内部类
    static class OOMObject{

    }

    public static void main(String[] args) {

        /**
         设置最大堆和Xmx最小堆Xms参数，通过不断的创建对象，当没有足够的空间创建新的对象时产生内存溢出异常
         * */

        List<OOMObject> list=new ArrayList<OOMObject>();
        //通过不断的创建对象达到OOM
        while (true){
            System.out.println("hello");
            list.add(new OOMObject());
        }

    }
}
