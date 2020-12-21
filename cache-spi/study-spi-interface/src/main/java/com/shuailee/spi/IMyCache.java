package com.shuailee.spi;

/**
 * @program: study-javacore
 * @description:
 * @create: 2019-10-10 13:03
 **/
public interface IMyCache<T> {

    T get(String cacheName);

    Boolean exeit(String cacheName);

    void add(String cacheName, T t);

    void delete(String cacheName);
}
