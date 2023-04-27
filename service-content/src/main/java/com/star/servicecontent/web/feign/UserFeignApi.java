package com.star.servicecontent.web.feign;

import com.star.servicecommon.domain.Result;
import com.star.servicecommon.domain.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author star
 * @date 2023/4/21 19:15
 */
@FeignClient(name = "service-user")
public interface UserFeignApi {

    @GetMapping("/feign/userInfos")
    Result<List<UserInfo>> getUserInfos(@RequestParam(required = true,value ="ids") List<Long> ids);

}
