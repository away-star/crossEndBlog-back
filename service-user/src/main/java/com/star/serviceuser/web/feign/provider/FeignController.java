package com.star.serviceuser.web.feign.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.star.servicecommon.domain.Result;
import com.star.serviceuser.domain.entity.UserInfo;
import com.star.serviceuser.domain.vo.InitialArgs;
import com.star.serviceuser.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author star
 * @date 2023/4/21 19:11
 */

@RestController
@RequestMapping("/feign")
@Slf4j
public class FeignController {
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation(response = InitialArgs.class, value = "feign内部接口获取用户信息")
    @GetMapping("/userInfos")
    public Result<List<UserInfo>> getUserInfos(@RequestParam(required = true) List<Long> ids) {
        log.error("loginInformationId:{}", ids);
        LambdaQueryWrapper<UserInfo> userInfoWrapper = new LambdaQueryWrapper<>();
        userInfoWrapper.in(UserInfo::getLoginInformationId, ids);
        List<UserInfo> userInfos = userInfoService.list(userInfoWrapper);
        log.error("userInfos:{}", userInfos);
        return Result.success(userInfos);
    }
}
