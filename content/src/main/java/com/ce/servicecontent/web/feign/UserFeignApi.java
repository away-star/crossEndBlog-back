package com.ce.servicecontent.web.feign;

import com.ce.servicecommon.domain.vo.Result;
import com.ce.sercivecommon.domain.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xingxing
 * @date 2023/4/21 19:15
 */
@FeignClient(name = "service-user")
public interface UserFeignApi {

    @GetMapping("/feign/userInfos")
    Result<List<UserInfo>> getUserInfos(@RequestParam(required = true,value ="ids") List<Long> ids);

}
