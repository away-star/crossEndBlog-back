package com.star.serviceuser.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author star
 * @date 2023/1/28 12:03
 */

@FeignClient(name = "service-content")
public interface contentFeignApi {

    @GetMapping("/content/777")
    public String post();
}
