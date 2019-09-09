package com.demo.global;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisCacheManager {

    @Autowired
    private RedisConnectionFactory connectionFactory;

/*    public CacheManager setTimeout(int seconds) {
        return getCacheManager(seconds);
    }

    @Cacheable(value = "test",cacheManager = cacheManager,sync=true,key="#key")
    public Object test(String key,Object value,CacheManager cacheManager) {

        return value;
    }

    private CacheManager getCacheManager(int seconds) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisSerializationContext.SerializationPair<?> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(jackson2JsonRedisSerializer);
        RedisCacheConfiguration cacheCfg = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair)
                .entryTtl(Duration.ofSeconds(seconds));
        // return RedisCacheManager.create(connectionFactory);
        return new org.springframework.data.redis.cache.RedisCacheManager(redisCacheWriter, cacheCfg);
    }*/
}
