package com.shuailee.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisClusterConfiguration{

    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisConnectionfactory){
        // 配置redisTemplate
        RedisTemplate<String,String> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionfactory);
        //key序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列化器
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


//    /**
//     * desc: 显示指定了使用 LettuceConnectionFactory连接，也可以使用 RedisConnectionFactory
//     * @param: [factory]
//     * @return:
//     * @date: 2021/1/29 17:24
//     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory factory) {
//        RedisTemplate redisTemplate = new RedisTemplate<String, String>();
//        factory.setShareNativeConnection(false);
//        //factory.setValidateConnection()
//        redisTemplate.setConnectionFactory(factory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        //redisTemplate.setHashValueSerializer(new FastJson4RedisSerializer());
//
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

}
