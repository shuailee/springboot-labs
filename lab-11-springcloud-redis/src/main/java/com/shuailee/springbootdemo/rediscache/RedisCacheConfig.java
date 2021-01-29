package com.shuailee.springbootdemo.rediscache;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义缓存读写机制  CachingConfigurerSupport
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisCacheConfig extends CachingConfigurerSupport {

    /**
     * 添加自定义缓存异常处理，当缓存读写异常时,忽略异常
     * 如果不加自定义 读写失败时将会将异常抛出，影响业务流程
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new IgnoreExceptionCacheErrorHandler();
    }

    /**
     * 通过自定义CacheResolver开发者可以实现更多的自定义功能，例如热点缓存自动升降级的场景
     * */
    @Override
    public CacheResolver cacheResolver() {
        // 通过Caffeine实现的自定义堆内存缓存管理器
        CacheManager guavaCacheManager = cacheManagerWithCaffeine(); //new CaffeineCacheManager();
        CacheManager redisCacheManager = cacheManager();

        List<CacheManager> list = new ArrayList<>();
        // 优先读取堆内存缓存
        list.add(guavaCacheManager);
        // 堆内存缓存读取不到该key时再读取redis缓存
        list.add(redisCacheManager);
        return new CustomCacheResolver(list);
    }




    /**
     * desc: (1) caffeine 作为堆缓存
     * https://juejin.cn/post/6844903670014803981
     * @param: []
     * @return:
     * @date: 2021/1/29 18:51
     */
    @Bean(name = "caffeine")
    @Primary
    public CacheManager cacheManagerWithCaffeine(){
        log.info("This is cacheManagerWithCaffeine");
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine caffeine = Caffeine.newBuilder()
                //cache的初始容量值
                .initialCapacity(100)
                //maximumSize用来控制cache的最大缓存数量，maximumSize和maximumWeight不可以同时使用，
                .maximumSize(1000);
                //控制最大权重
//                .maximumWeight(100);
//                .expireAfter();
                //使用refreshAfterWrite必须要设置cacheLoader
//                .refreshAfterWrite(5,TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
//        cacheManager.setCacheLoader(cacheLoader);
//        cacheManager.setCacheNames(getNames());
//        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }


    /**
     * (2) 选择redis作为默认缓存工具 配合@Cacheable 等注解使用
     * CacheManager的使用场景比较多，最常见的@Cacheable、@CachePut、@CacheEvict等默认都可以配合CacheManager使用
     * */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // fastjson作为序列化器
        RedisSerializationContext.SerializationPair serializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(getRedisSerializer());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 缓存时间1个小时
                .entryTtl(Duration.ofHours(1))
                // 指定序列化器
                .serializeValuesWith(serializationPair);
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * desc: fastjson作为序列化器
     * @param: []
     * @return:
     * @date: 2021/1/29 17:41
     */
    private RedisSerializer<Object> getRedisSerializer(){
        return new GenericFastJsonRedisSerializer();
    }

}
