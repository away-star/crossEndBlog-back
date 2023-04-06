package com.star.serviceOpenAi.config;

import com.star.serviceOpenAi.utils.OpenAiUtils;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author star
 * @date 2023/3/7 20:19
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties implements InitializingBean {
    // 秘钥
    String token;
    // 超时时间
    Integer timeout;

    // 设置属性时同时设置给OpenAiUtils
    @Override
    public void afterPropertiesSet() throws Exception {
        OpenAiUtils.OPENAPI_TOKEN = token;
        OpenAiUtils.TIMEOUT = timeout;
    }
}

