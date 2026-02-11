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
        // 配置类型提示，确保反序列化时保留类型信息
        ObjectMapper redisObjectMapper = new ObjectMapper();
        redisObjectMapper.registerModule(new JavaTimeModule());
        redisObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 启用默认类型信息，确保反序列化时能正确还原类型
        redisObjectMapper.activateDefaultTyping(
                redisObjectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
        );

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 设置默认缓存过期时间
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper)))
                // 不缓存空值
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                // 预定义 annex 缓存的配置
                .withCacheConfiguration("annex", config)
                .build();
    }
}