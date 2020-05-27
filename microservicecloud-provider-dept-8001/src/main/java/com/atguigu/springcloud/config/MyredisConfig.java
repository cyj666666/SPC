package com.atguigu.springcloud.config;

import com.atguigu.springcloud.entities.Dept;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

/**
 * @Author: caoyunji
 * @Date: 2020/5/26 1:21
 */
@Configuration
public class MyredisConfig {

    @Bean
    public RedisTemplate<Object, Dept> deptRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Dept> template = new RedisTemplate<Object, Dept>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Dept> ser = new Jackson2JsonRedisSerializer<Dept>(Dept.class);
        template.setDefaultSerializer(ser);
        return template;
    }

    @Bean
    public RedisCacheManager deptCacheManager(RedisTemplate<Object, Dept> deptRedisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(deptRedisTemplate);
        //key多了一个前缀

        //使用前缀，默认会将CacheName作为key的前缀
        cacheManager.setUsePrefix(true);

        return cacheManager;
    }
}
