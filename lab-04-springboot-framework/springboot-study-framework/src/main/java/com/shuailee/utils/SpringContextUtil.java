package com.shuailee.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @program: geek-calendar-framework
 * @description: SpringContextUtil
 * @author: shuai.li
 * @create: 2020-05-21 17:59
 **/
public class SpringContextUtil implements ApplicationContextAware {
    /**
     * spring应用上下文
     */
    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取Bean对象
     * @param calssz bean类
     * @return bean实例
     */
    public static <T> T getBean(Class<T> calssz){
        return applicationContext.getBean(calssz);
    }

    /**
     * 获取Bean对象
     * @param name beanName
     * @return bean对象
     */
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
}
