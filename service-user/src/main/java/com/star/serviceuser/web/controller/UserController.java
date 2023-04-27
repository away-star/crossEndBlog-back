package com.star.serviceuser.web.controller;
import com.star.servicecommon.domain.Result;
import com.star.servicecommon.exception.BusinessException;
import com.star.servicecommon.util.SecurityUtil;
import com.star.serviceuser.domain.dto.BlogSetting;
import com.star.serviceuser.domain.dto.UserInfoDto;
import com.star.serviceuser.domain.entity.UserInfo;
import com.star.serviceuser.domain.vo.InitialArgs;
import com.star.serviceuser.domain.vo.UserDetail;
import com.star.serviceuser.service.LoginInformationService;
import com.star.serviceuser.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.star.servicecommon.msg.CommonCodeMsg.ILLEGAL_OPERATION;

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

    @ApiOperation(response = InitialArgs.class, value = "获取用户初始信息")
    @GetMapping("/initial")
    public Result<InitialArgs> getInitialArgs(@RequestParam(required = false) Long loginInformationId) {
        log.error("loginInformationId:{}", loginInformationId);
        //判断是否请求自己的数据
        if (Objects.isNull(loginInformationId)) {
            log.error("loginInformationId is null");
            loginInformationId = Objects.requireNonNull(SecurityUtil.getUser()).getId();
            //对当前线程设置优先级
            Thread.currentThread().setPriority(10);
        }
        InitialArgs initialArgs = userInfoService.getInitial(loginInformationId);
        log.error("initialArgs:{}", initialArgs);
        return Result.success(initialArgs);
    }


    @GetMapping("/userDetail")
    public Result<UserDetail> getUserDetail() {
        UserDetail userDetail = loginInformationService.getDetail();
        return Result.success(userDetail);
    }


    @PutMapping("/updateInfo")
    public Result<String> updateInfo(@RequestBody UserInfo userInfo) {
        log.error(userInfo.toString());
        Long id = Objects.requireNonNull(SecurityUtil.getUser()).getId();
        if (Objects.isNull(id)) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        userInfo.setLoginInformationId(id);
        if (userInfoService.updateInfo(userInfo)) {
            return Result.success();
        } else {
            return Result.defaultError();
        }
    }


    @PutMapping("/updateBlogSetting")
    public Result<String> updateBlogSetting(@RequestBody BlogSetting blogSetting) {
        log.error(blogSetting.toString());

        Long id = Objects.requireNonNull(SecurityUtil.getUser()).getId();
        if (Objects.isNull(id)) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }

        if (userInfoService.updateBlogSetting(blogSetting)) {
            return Result.success();
        } else {
            return Result.defaultError();
        }
    }

    @GetMapping("/users")
    public Result<List<UserInfoDto>> getAllUsers() {
        List<UserInfoDto> userInfos = userInfoService.getAllUsers();
        return Result.success(userInfos);
    }
}



