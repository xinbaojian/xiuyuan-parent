package xin.xiuyuan.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.Duration;

/**
 * 项目全局配置
 *
 * @author xinbaojian
 * @create 2025-12-16 11:09
 **/
@Slf4j
@EnableCaching
@EnableAsync
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"xin.xiuyuan"})
@EnableMongoRepositories(basePackages = {
        "xin.xiuyuan.admin.repository",
        "xin.xiuyuan.domain.repository",
        "xin.xiuyuan.file.storage.repository"
})
public class AppConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        // 默认配置：不启用类型信息（用于简单类型缓存，如 List<String>）
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.registerModule(new JavaTimeModule());
        defaultObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(defaultObjectMapper)))
                .disableCachingNullValues();

        // 对象缓存配置：启用类型信息（用于复杂对象缓存，如 SysAnnex）
        ObjectMapper objectMapperMapper = new ObjectMapper();
        objectMapperMapper.registerModule(new JavaTimeModule());
        objectMapperMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 只对对象启用类型信息，使用 NON_FINAL 策略
        objectMapperMapper.activateDefaultTyping(
                objectMapperMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
        );

        RedisCacheConfiguration objectCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapperMapper)))
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                // 预定义需要对象类型信息的缓存
                .withCacheConfiguration("annex", objectCacheConfig)
                .withCacheConfiguration("user", objectCacheConfig)
                .build();
    }
}