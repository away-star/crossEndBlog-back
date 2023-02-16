package com.star.serviceuser.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.servicecommon.domain.Result;
import com.star.serviceuser.domain.entity.LoginInformation;
import com.star.serviceuser.service.LoginInformationService;
import com.star.serviceuser.util.MailClientUtil;
import com.star.serviceuser.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import java.util.concurrent.TimeUnit;

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
    private MailClientUtil mailClientUtil;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 验证码发送登录
     *
     * @param email 用户电子邮件
     * @return {@link R
     * }<{@link String}>
     */
    @GetMapping("/mail/captcha")
    public Result<Object> codeSendForLogin(@RequestParam("email") @Email String email) {
        LambdaQueryWrapper<LoginInformation> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(LoginInformation::getEmail, email);
        log.error(email);
        LoginInformation one = loginService.getOne(userWrapper);
        String captcha = ValidateCodeUtils.generateValidateCode4String(4);


        if (one== null) {
            throw new RuntimeException("未查找到相关账号信息，请先注册哦");
        }
        log.error(captcha);
        boolean flag = mailClientUtil.sendMail(email, captcha, "欢迎光临starBlog");
        stringRedisTemplate.opsForValue().set(email,captcha,5, TimeUnit.MINUTES);
        if (flag) {
            return Result.success();
        }
        throw new RuntimeException("系统错误，请联系小星");
    }

}
