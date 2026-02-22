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
        // 默认配置：启用类型信息（用于复杂对象缓存）
        // 这样可以自动处理所有自定义对象，无需手动配置
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.registerModule(new JavaTimeModule());
        defaultObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 启用默认类型信息，使用 NON_FINAL 策略
        defaultObjectMapper.activateDefaultTyping(
                defaultObjectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
        );

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(defaultObjectMapper)))
                .disableCachingNullValues();

        // 简单类型配置：不启用类型信息（仅用于简单类型，如 List<String>）
        ObjectMapper simpleTypeMapper = new ObjectMapper();
        simpleTypeMapper.registerModule(new JavaTimeModule());
        simpleTypeMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 不启用类型信息

        RedisCacheConfiguration simpleTypeConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(simpleTypeMapper)))
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                // 仅对确定是简单类型的缓存使用不含类型的序列化
                .withCacheConfiguration("user:permissions", simpleTypeConfig)
                .build();
    }
}