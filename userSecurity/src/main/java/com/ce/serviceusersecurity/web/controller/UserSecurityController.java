package com.ce.serviceusersecurity.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ce.servicecommon.domain.MailDto;
import com.ce.servicecommon.domain.vo.Result;
import com.ce.servicecommon.exception.BusinessException;
import com.ce.serviceusersecurity.domain.dto.LoginInfoRequest;
import com.ce.serviceusersecurity.domain.dto.RecoverRequest;
import com.ce.serviceusersecurity.domain.dto.RegisterInfoRequest;
import com.ce.serviceusersecurity.domain.entity.SecurityInfo;
import com.ce.serviceusersecurity.domain.vo.LoginVo;
import com.ce.serviceusersecurity.msg.UAACodeMsg;
import com.ce.serviceusersecurity.service.AuthService;
import com.ce.serviceusersecurity.service.SecurityInfoService;
import com.ce.serviceusersecurity.util.ValidateCodeUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

import static com.ce.servicecommon.constant.RedisAbout.*;
import static com.ce.servicecommon.constant.Security.realIpKey;
import static com.ce.servicecommon.constant.Security.alive;
import static com.ce.servicecommon.constant.Welcome.*;

/**
 * @author xingxing
 * @date 2023/1/30 19:32
 */

@RestController
@RequestMapping("/UserSecurity")
@Slf4j
public class UserSecurityController {

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private SecurityInfoService securityInfoService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 验证码发送登录
     *
     * @param email 用户电子邮件
     * @return }<{@link String}>
     */
    @GetMapping("/mail/login/captcha")
    public Result<Object> codeSendForLogin(@RequestParam("email") @Email String email) {
        SecurityInfo one = getSecurityInfoByEmail(email);
        String captcha = ValidateCodeUtils.generateValidateCode4String(4);
        if (one == null) {
            throw new BusinessException(UAACodeMsg.LOGIN_ERROR_EMAIL);
        }
        log.error(captcha);

        // todo 这是一个消息队列发送验证码模板
        MailDto mailDto = new MailDto(email, captcha, loginWelcome);
        //此处判断是否到交换机
        //CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //correlationData.getFuture().addCallback(o -> {
        //    log.error("消息成功发送到交换机");
        //}, throwable -> {
        //    log.error("消息发送到交换机失败");
        //    log.error(throwable.getMessage());
        //});
        rabbitTemplate.convertAndSend("exchange_user", "loginCaptcha", mailDto, message -> {
            //五分钟未被执行则进入死信队列
            message.getMessageProperties().setExpiration("300000");
            // 设置死信队列
            message.getMessageProperties().setHeader("x-dead-letter-exchange", "exchange_dead");
            message.getMessageProperties().setHeader("x-dead-letter-routing-key", "dead");
            return message;
        });

        stringRedisTemplate.opsForValue().set(loginCaptchaPrefix + email, captcha, 5, TimeUnit.MINUTES);
        return Result.defaultSuccess();
    }
    /*
     * @param email
     * Return com.ce.servicecommon.domain.vo.Result<java.lang.Object>
     * Author xingxing
     * Date 2023/9/30
     */
    @GetMapping("/mail/register/captcha")
    public Result<Object> codeSendForRegister(@RequestParam("email") @Email String email) {
        log.error(email);
        SecurityInfo one = getSecurityInfoByEmail(email);
        if (one != null) {
            throw new BusinessException(UAACodeMsg.REGISTER_ERROR_EMAIL);
        }

        String captcha = ValidateCodeUtils.generateValidateCode4String(4);
        MailDto mailDto = new MailDto(email, captcha, registerWelcome);
        rabbitTemplate.convertAndSend("exchange_user", "registerCaptcha", mailDto, message -> {
            //五分钟未被执行则删除
            message.getMessageProperties().setExpiration("300000");
            return message;
        });

        stringRedisTemplate.opsForValue().set(registerCaptchaPrefix + email, captcha, 5, TimeUnit.MINUTES);
        return Result.defaultSuccess();
    }
    /*
     * @param email
     * Return com.ce.servicecommon.domain.vo.Result<java.lang.Object>
     * Author xingxing
     * Date 2023/9/30
     */
    @GetMapping("/mail/recover/captcha")
    public Result<Object> codeSendForRecover(@RequestParam("email") @Email String email) {
        SecurityInfo one = getSecurityInfoByEmail(email);
        if (one == null) {
            throw new BusinessException(UAACodeMsg.LOGIN_ERROR_EMAIL);
        }
        log.error(email);
        String captcha = ValidateCodeUtils.generateValidateCode4String(4);
        MailDto mailDto = new MailDto(email, captcha, recoverAlert);
        rabbitTemplate.convertAndSend("exchange_user", "recoverCaptcha", mailDto, message -> {
            //五分钟未被执行则删除
            message.getMessageProperties().setExpiration("300000");
            return message;
        });
        stringRedisTemplate.opsForValue().set(recoveryCaptchaPrefix + email, captcha, 5, TimeUnit.MINUTES);
        return Result.defaultSuccess();
    }

    private SecurityInfo getSecurityInfoByEmail(String email) {
        log.error(email);
        LambdaQueryWrapper<SecurityInfo> userWrapper = new LambdaQueryWrapper<>();
        log.error(userWrapper.toString());
        userWrapper.eq(SecurityInfo::getEmail, email).eq(SecurityInfo::getDeleteFlag, alive);
        return securityInfoService.getOne(userWrapper);
    }
    /*
     * @param registerInfoRequest
     * @param request
     * Return com.ce.servicecommon.domain.vo.Result<jakarta.validation.constraints.Null>
     * Author xingxing
     * Date 2023/9/30
     */
    @PostMapping("/register")
    public Result<Null> register(@RequestBody @Validated RegisterInfoRequest registerInfoRequest, HttpServletRequest request) {
        // todo 从请求头中获得registerIp
        log.error(registerInfoRequest.toString());
        String registerIp = request.getHeader(realIpKey);
        securityInfoService.register(registerInfoRequest, registerIp);
        return Result.defaultSuccess();
    }

    /*
     * @param recoverRequest
     * Return com.ce.servicecommon.domain.vo.Result<java.lang.String>
     * Author xingxing
     * Date 2023/9/30
     */
    @PutMapping("/recover")
    public Result<String> recover(@RequestBody @Validated RecoverRequest recoverRequest) {
        securityInfoService.recover(recoverRequest);
        return Result.defaultSuccess();
    }
    /*
     * @param loginInfoRequest
     * @param request
     * Return com.ce.servicecommon.domain.vo.Result<com.ce.serviceusersecurity.domain.vo.LoginVo>
     * Author xingxing
     * Date 2023/9/30
     */
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody @Validated LoginInfoRequest loginInfoRequest, HttpServletRequest request) {
        log.error(request.getRemoteAddr());
        // todo 从请求头中获得registerIp
        String loginIp = request.getHeader(realIpKey);
        String authType = loginInfoRequest.getAuthType();
        //从spring容器中拿具体的认证bean实例（工厂模式）
        AuthService authService = applicationContext.getBean(authType + "_authService", AuthService.class);
        //开始认证,认证成功拿到用户信息
        return Result.success(authService.execute(loginInfoRequest));
    }
}
