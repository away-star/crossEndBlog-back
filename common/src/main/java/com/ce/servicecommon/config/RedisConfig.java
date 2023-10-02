package com.ce.servicecommon.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration // 表示这是一个配置类
public class RedisConfig {


    @Bean // 定义一个Bean
    @ConditionalOnMissingBean(name = "redisTemplate") // 当不存在名为"redisTemplate"的Bean时，才创建这个Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>(); // 创建一个RedisTemplate实例
        template.setConnectionFactory(redisConnectionFactory); // 设置连接工厂
        template.setDefaultSerializer(jackson2JsonRedisSerializer()); // 设置默认的序列化器
        template.setHashKeySerializer(jackson2JsonRedisSerializer()); // 设置哈希键的序列化器
        template.setHashValueSerializer(jackson2JsonRedisSerializer()); // 设置哈希值的序列化器
        return template; // 返回创建好的RedisTemplate实例
    }

    @Bean // 定义一个Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class); // 创建一个Jackson2JsonRedisSerializer实例
        ObjectMapper objectMapper = new ObjectMapper(); // 创建一个ObjectMapper实例
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY); // 设置ObjectMapper的可见性
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL); // 激活默认的类型检查
        serializer.serialize(objectMapper); // 使用ObjectMapper进行序列化
        return serializer; // 返回创建好的Jackson2JsonRedisSerializer实例
    }

    @Bean // 定义一个Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class) // 当不存在StringRedisTemplate类型的Bean时，才创建这个Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory); // 创建一个StringRedisTemplate实例
    }

    @Bean // 定义一个Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash(); // 获取RedisTemplate的哈希操作实例
    }
}