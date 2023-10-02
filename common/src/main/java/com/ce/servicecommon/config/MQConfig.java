package com.ce.servicecommon.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xingxing
 * @date 2023/5/7 17:01
 */

@Configuration
public class MQConfig {

    /**
     * 用于将消息转换为json格式
     * @return MessageConverter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
