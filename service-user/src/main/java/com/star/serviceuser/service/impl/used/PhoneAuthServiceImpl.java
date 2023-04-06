package com.star.serviceuser.service.impl.used;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.star.servicecommon.exception.BusinessException;
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
@Service("phone_authservice")
public class PhoneAuthServiceImpl implements AuthService {

    @Autowired
    private LoginInformationService service;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginInformation execute(LoginInformationDto authParamsDto) {
        log.error("进来了" );
        String phone = authParamsDto.getPhone();

        LoginInformation user = service.getOne(new LambdaQueryWrapper<LoginInformation>().eq(phone!=  null, LoginInformation::getPhone, phone));

        if (user == null) {
            //账号不存在
            throw new BusinessException(LOGIN_ERROR_PHONE);
        }

        String captcha = authParamsDto.getCaptcha();

        // todo 从redis中查询到验证码是否正确
        String s = stringRedisTemplate.opsForValue().get(authParamsDto.getPhone());
        if (StringUtils.equals(captcha,s)){
            return user;
        }
        throw new BusinessException(ERROR_CAPTCHA);
    }
}
