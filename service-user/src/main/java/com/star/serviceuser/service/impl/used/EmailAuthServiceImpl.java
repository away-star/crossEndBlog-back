package com.star.serviceuser.service.impl.used;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.servicecommon.exception.BusinessException;
import com.star.serviceuser.domain.dto.LoginInformationDto;
import com.star.serviceuser.domain.entity.LoginInformation;
import com.star.serviceuser.service.AuthService;
import com.star.serviceuser.service.LoginInformationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.star.serviceuser.web.msg.UAACodeMsg.*;


/**
 * @author star
 */
@Slf4j
@Service("email_authservice")
public class EmailAuthServiceImpl implements AuthService {

    @Autowired
    private LoginInformationService service;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginInformation execute(LoginInformationDto authParamsDto) {
        log.error("进来了" );
        String email = authParamsDto.getEmail();

        LoginInformation user = service.getOne(new LambdaQueryWrapper<LoginInformation>().eq(email!=  null, LoginInformation::getEmail, email));

        if (user == null) {
            //账号不存在
            throw new BusinessException(LOGIN_ERROR_EMAIL);
        }

        String captcha = authParamsDto.getCaptcha();
        log.error(user.toString());
        // todo 从redis中查询到验证码是否正确
        String s = stringRedisTemplate.opsForValue().get(authParamsDto.getEmail());
        if (StringUtils.equals(captcha,s)){
            return user;
        }
        throw new BusinessException(ERROR_CAPTCHA);
    }
}
