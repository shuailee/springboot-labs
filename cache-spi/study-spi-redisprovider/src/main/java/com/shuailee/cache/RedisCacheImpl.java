package com.shuailee.cache;


import com.shuailee.spi.IMyCache;

/**
 * @program: study-spi-redisprovider
 * @description:
 * @create: 2019-10-10 14:59
 **/
public class RedisCacheImpl implements IMyCache {
    @Override
    public Object get(String cacheName) {
        return "redis cache";
    }

    @Override
    public Boolean exeit(String cacheName) {
        return true;
    }

    @Override
    public void add(String cacheName, Object o) {
        System.out.println("redis add");
    }

    @Override
    public void delete(String cacheName) {
        System.out.println("redis del");
    }
}
