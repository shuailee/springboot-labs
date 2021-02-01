package com.shuailee.springbootdemo.rediscache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * @package: com.shuailee.springbootdemo.rediscache
 * @description: 缓存异常自定义处理类
 * redis作为缓存，查询或更新失败时的处理策略（如果不自定义异常处理，默认是抛出异常，但是这样会影响业务流程，所以现实是我们往往选择忽略缓存的异常，只记录）
 * @author: klein
 * @date: 2021-01-29 17:48
 **/
@Slf4j
public class IgnoreExceptionCacheErrorHandler implements CacheErrorHandler {
    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.error(exception.getMessage(), exception);
    }

    /**
     * 写入错误处理策略，可以选择重试
     * */
    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        log.error(exception.getMessage(), exception);
    }

    /**
     * 缓存key驱除处理策略
     * */
    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.error(exception.getMessage(), exception);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.error(exception.getMessage(), exception);
    }
}
