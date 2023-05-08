package com.star.serviceuser.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.star.servicecommon.domain.MailDto;
import com.star.servicecommon.domain.Result;
import com.star.servicecommon.exception.BusinessException;
import com.star.serviceuser.domain.dto.LoginInformationDto;
import com.star.serviceuser.domain.dto.RegisterInfo;
import com.star.serviceuser.domain.entity.LoginInformation;
import com.star.serviceuser.domain.vo.UserDetail;
import com.star.serviceuser.service.LoginInformationService;
import com.star.serviceuser.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import java.util.concurrent.TimeUnit;

import static com.star.serviceuser.constant.Authority.*;
import static com.star.serviceuser.web.msg.UAACodeMsg.LOGIN_ERROR_EMAIL;
import static com.star.serviceuser.web.msg.UAACodeMsg.REGISTER_ERROR_EMAIL;

/**
 * @author star
 * @date 2023/1/30 19:32
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {
    @Resource
    private LoginInformationService loginService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 验证码发送登录
     *
     * @param email 用户电子邮件
     * @return {@link R
     * }<{@link String}>
     */
    @GetMapping("/mail/login/captcha")
    public Result<Object> codeSendForLogin(@RequestParam("email") @Email String email) {
        LoginInformation one = getLoginInformationByEmail(email);
        String captcha = ValidateCodeUtils.generateValidateCode4String(4);
        if (one == null) {
            throw new BusinessException(LOGIN_ERROR_EMAIL);
        }
        log.error(captcha);

        MailDto mailDto = new MailDto(email, captcha, "欢迎登录 cross-end Blog");
        rabbitTemplate.convertAndSend("exchange_user", "loginCaptcha", mailDto, message -> {
            //五分钟未被执行则删除
            message.getMessageProperties().setExpiration("300000");
            return message;
        });
        //boolean flag = mailClientUtil.sendMail(email, captcha, "欢迎光临 cross-end Blog");
        stringRedisTemplate.opsForValue().set(loginCaptchaPrefix + email, captcha, 5, TimeUnit.MINUTES);
//        if (flag) {
//            return Result.success();
//        }
//        throw new BusinessException(ERROR_SERVER);
        return Result.success();
    }

    @GetMapping("/mail/register/captcha")
    public Result<Object> codeSendForRegister(@RequestParam("email") @Email String email) {
        log.error(email);
        LoginInformation one = getLoginInformationByEmail(email);
        if (one != null) {
            throw new BusinessException(REGISTER_ERROR_EMAIL);
        }

        String captcha = ValidateCodeUtils.generateValidateCode4String(4);
        MailDto mailDto = new MailDto(email, captcha, "欢迎注册 cross-end Blog");
        rabbitTemplate.convertAndSend("exchange_user", "registerCaptcha", mailDto, message -> {
            //五分钟未被执行则删除
            message.getMessageProperties().setExpiration("300000");
            return message;
        });

//        boolean flag = mailClientUtil.sendMail(email, captcha, "欢迎注册cross-end Blog");
        stringRedisTemplate.opsForValue().set(registerCaptchaPrefix + email, captcha, 5, TimeUnit.MINUTES);
//        if (flag) {
//            return Result.success();
//        }
//        throw new BusinessException(ERROR_SERVER);
        return Result.success();
    }

    @GetMapping("/mail/recover/captcha")
    public Result<Object> codeSendForRecover(@RequestParam("email") @Email String email) {
        LoginInformation one = getLoginInformationByEmail(email);
        if (one == null) {
            throw new BusinessException(LOGIN_ERROR_EMAIL);
        }
        log.error(email);
        String captcha = ValidateCodeUtils.generateValidateCode4String(4);
        MailDto mailDto = new MailDto(email, captcha, "您正在修改账户密码，若非本人操作，请尽快联系管理员");
        rabbitTemplate.convertAndSend("exchange_user", "recoverCaptcha", mailDto, message -> {
            //五分钟未被执行则删除
            message.getMessageProperties().setExpiration("300000");
            return message;
        });
//        boolean flag = mailClientUtil.sendMail(email, captcha, "您正在修改账户密码，若非本人操作，请尽快联系管理员");
        stringRedisTemplate.opsForValue().set(recoveryCaptchaPrefix + email, captcha, 5, TimeUnit.MINUTES);
//        if (flag) {
//            return Result.success();
//        }
//        throw new BusinessException(ERROR_SERVER);
        return Result.success();
    }


    private LoginInformation getLoginInformationByEmail(String email) {
        log.error(email);
        LambdaQueryWrapper<LoginInformation> userWrapper = new LambdaQueryWrapper<>();
        log.error(userWrapper.toString());
        userWrapper.eq(LoginInformation::getEmail, email);
        return loginService.getOne(userWrapper);
    }


    @PostMapping("/register")
    public Result<UserDetail> register(@RequestBody @Validated RegisterInfo registerInfo) {
        // todo 从请求头中获得registerIp

        log.error(registerInfo.toString());

        String registerIp = "123456";
        UserDetail userDetail = loginService.register(registerInfo, registerIp);
        if (userDetail != null) {
            return Result.success(userDetail);
        }
        return Result.defaultError();
    }

    @PutMapping("/recover")
    public Result<String> recover(@RequestBody @Validated LoginInformationDto loginInformationDto) {
        // todo 从请求头中获得registerIp
        log.error(loginInformationDto.toString());

        if (loginService.recover(loginInformationDto.getEmail(),
                loginInformationDto.getCaptcha(),
                loginInformationDto.getPassword())) {
            return Result.success();
        }
        return Result.defaultError();
    }
}
