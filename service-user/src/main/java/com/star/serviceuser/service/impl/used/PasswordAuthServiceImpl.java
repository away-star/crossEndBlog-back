package com.star.serviceuser.service.impl.used;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.servicecommon.exception.BusinessException;
import com.star.serviceuser.domain.dto.LoginInformationDto;
import com.star.serviceuser.domain.entity.LoginInformation;
import com.star.serviceuser.service.AuthService;
import com.star.serviceuser.service.LoginInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.star.serviceuser.web.msg.UAACodeMsg.LOGIN_ERROR_ACCOUNT;
import static com.star.serviceuser.web.msg.UAACodeMsg.LOGIN_ERROR_PASSWORD;


/**
 * @author star
 */
@Slf4j
@Service("password_authservice")
public class PasswordAuthServiceImpl implements AuthService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private LoginInformationService service;

    @Override
    public LoginInformation execute(LoginInformationDto authParamsDto) {
        log.error("进来了" );
        String account = authParamsDto.getAccount();
        LoginInformation user = service.getOne(new LambdaQueryWrapper<LoginInformation>().eq(account != null, LoginInformation::getAccount, account));
        if (user == null) {
            //账号不存在
            throw new BusinessException(LOGIN_ERROR_ACCOUNT);
        }
        String passwordDB = user.getPassword();

        //输入的密码
        String passwordInput = authParamsDto.getPassword();
        log.error(passwordInput);
        log.error(passwordDB+"5555");
        boolean matches = passwordEncoder.matches(passwordInput, passwordDB);

        if(!matches){
            throw new BusinessException(LOGIN_ERROR_PASSWORD);
        }
        return user;
    }
}
