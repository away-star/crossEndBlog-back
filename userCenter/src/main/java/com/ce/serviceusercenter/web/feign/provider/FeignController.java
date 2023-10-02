package com.ce.serviceusercenter.web.feign.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ce.serviceusercenter.domain.entity.Userinfo;
import com.ce.serviceusercenter.service.UserinfoService;
import com.ce.servicecommon.domain.vo.Result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @author xingxing
 * @date 2023/4/21 19:11
 */

@RestController
@RequestMapping("/feign")
@Slf4j
public class FeignController {
    @Resource
    private UserinfoService userInfoService;

    @GetMapping("/userInfos")
    public Result<List<Userinfo>> getUserInfos(@RequestParam(required = true) List<Long> ids) {
        log.error("securityInfoId:{}", ids);
        LambdaQueryWrapper<Userinfo> userInfoWrapper = new LambdaQueryWrapper<>();

        List<Userinfo> userInfos = userInfoService.list(userInfoWrapper);
        log.error("userInfos:{}", userInfos);
        return Result.success(userInfos);
    }
}
