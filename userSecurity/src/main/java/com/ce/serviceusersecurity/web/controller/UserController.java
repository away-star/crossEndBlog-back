package com.ce.serviceusersecurity.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ce.servicecommon.annotations.PreAuth;
import com.ce.servicecommon.domain.dto.TokenClaim;
import com.ce.servicecommon.domain.vo.Result;
import com.ce.servicecommon.util.JwtUtil;
import com.ce.serviceusersecurity.domain.dto.UserInfoDto;
import com.ce.serviceusersecurity.domain.dto.UserinfoRequest;
import com.ce.serviceusersecurity.domain.entity.Userinfo;
import com.ce.serviceusersecurity.service.SecurityInfoService;
import com.ce.serviceusersecurity.service.UserinfoService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.ce.servicecommon.constant.Security.alive;


/**
 * @author xingxing
 * @date 2023/2/1 21:27
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserinfoService userInfoService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SecurityInfoService securityInfoService;


    @Parameter(name = "securityInfoId", description = "登录信息id")
    @GetMapping("/initial")
    public Result<UserInfoDto> getUserinfo(@RequestParam(required = false) Long security_info_id, HttpServerRequest request) {
        log.error("securityInfoId:{}", security_info_id);
        //判断是否请求自己的数据
        if (Objects.isNull(security_info_id)) {
            log.error("securityInfoId is null");
            TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
            security_info_id = tokenClaim.getSecurity_info_id();
            //对当前线程设置优先级（10最大，1最小）
            Thread.currentThread().setPriority(10);
        }
        UserInfoDto initialUserInfoById = securityInfoService.getInitialUserInfoById(security_info_id);
        log.error("initialArgs:{}", initialUserInfoById);
        return Result.success(initialUserInfoById);
    }


    @PutMapping("/updateInfo")
    public Result<String> updateInfo(@RequestBody UserinfoRequest UserinfoRequest, HttpServerRequest request) {
        log.error(UserinfoRequest.toString());
        Userinfo userinfo = BeanUtil.copyProperties(UserinfoRequest, Userinfo.class);
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long security_info_id = tokenClaim.getSecurity_info_id();
        userinfo.setSecurityInfoId(security_info_id);
        LambdaQueryWrapper<Userinfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Userinfo::getSecurityInfoId, security_info_id).eq(Userinfo::getDeleteFlag, alive);
        if (userInfoService.updateById(userinfo)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }

    @GetMapping("/users")
    public Result<Page<UserInfoDto>> getUsersList(@RequestParam(required = false) Long security_info_id,@RequestParam(required = true) int size, HttpServerRequest request) {
        Page<UserInfoDto> usersList = userInfoService.getUsersList(security_info_id, size);
        return Result.success(usersList);
    }

    @GetMapping("/test")
    @PreAuth(auth = 1)
    public Result<String> test(HttpServerRequest request) {
        log.error("test");
        return Result.success("测试成功");
    }
}



