package com.shuailee.dumpdemo.oom;

/**
 * @program: dump-demo
 * @description: 演示StackOverFlow（栈深度达到虚拟机允许的最大深度时抛出的SOF异常）
 * 需要指定VM参数
 * -Xss160k

 * @create: 2019-10-18 18:13
 **/
public class StackSOFTest {

    private int stackLength = 1;

    /**
     * 递归方法，不断的调用自己可以增加栈的深度
     */
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        StackSOFTest stackSOFTest = new StackSOFTest();
        try {
            //调用stackLeak方法，当栈深度达到虚拟机允许的最大深度时，抛出抛出StackOverFlow异常
            stackSOFTest.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + stackSOFTest.stackLength);
            throw e;
        }

    }


}
