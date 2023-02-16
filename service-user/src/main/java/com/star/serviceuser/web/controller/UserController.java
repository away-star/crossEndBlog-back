package com.star.serviceuser.web.controller;

import com.example.servicecommon.domain.Result;
import com.example.servicecommon.util.SecurityUtil;
import com.star.serviceuser.domain.dto.RegisterInfo;
import com.star.serviceuser.domain.entity.UserInfo;
import com.star.serviceuser.domain.vo.InitialArgs;
import com.star.serviceuser.domain.vo.UserDetail;
import com.star.serviceuser.service.LoginInformationService;
import com.star.serviceuser.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author star
 * @date 2023/2/1 21:27
 */

@RestController
@RequestMapping("/information")
@Slf4j
public class UserController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private LoginInformationService loginInformationService;


    @GetMapping("/initial")
    public Result<InitialArgs> getInitialArgs() {
        InitialArgs initialArgs = userInfoService.getInitial(2021120053L);
        return Result.success(initialArgs);
    }

    @GetMapping("/userDetail")
    public Result<UserDetail> getUserDetail() {
        UserDetail userDetail = loginInformationService.getDetail();
        return Result.success(userDetail);
    }


    @PostMapping("/register")
    public Result<UserDetail> register(@RequestBody @Validated RegisterInfo registerInfo) {
        // todo 从请求头中获得registerIp

        log.error(registerInfo.toString());

        String registerIp = "123456";
        UserDetail userDetail = loginInformationService.register(registerInfo, registerIp);
        if (userDetail != null) {
            return Result.success(userDetail);
        }
        return Result.defaultError();
    }


    @PutMapping("/updateInfo")
    public Result<String> updateInfo(@RequestBody UserInfo userInfo) {

        if (userInfoService.updateById(userInfo)) {
            return Result.success();
        } else {
            return Result.defaultError();
        }
    }


    @GetMapping("/test")
    public Result test() {

        return Result.success(SecurityUtil.getUser());

    }
}



