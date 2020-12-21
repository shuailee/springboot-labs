package com.shuailee.cache;

import com.shuailee.spi.IMyCache;

/**
 * @program: study-spi-mongodbprovider
 * @description:
 * @create: 2019-10-10 15:10
 **/
public class MongodbCacheImpl implements IMyCache {
    @Override
    public Object get(String cacheName) {
        return "mongodb cache";
    }
    @Override
    public Boolean exeit(String cacheName) {
        return true;
    }
    @Override
    public void add(String cacheName, Object o) {
        System.out.println("mongodb add");
    }
    @Override
    public void delete(String cacheName) {
        System.out.println("mongodb del");
    }
}
