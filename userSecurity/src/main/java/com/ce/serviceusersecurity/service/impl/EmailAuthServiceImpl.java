package com.ce.serviceusersecurity.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ce.servicecommon.exception.BusinessException;
import com.ce.serviceusersecurity.domain.dto.LoginInfoRequest;
import com.ce.serviceusersecurity.domain.dto.UserInfoDto;
import com.ce.serviceusersecurity.domain.entity.User2power;
import com.ce.serviceusersecurity.domain.vo.LoginVo;
import com.ce.serviceusersecurity.service.AuthService;
import com.ce.serviceusersecurity.service.SecurityInfoService;
import com.ce.servicecommon.util.JwtUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ce.servicecommon.constant.RedisAbout.loginCaptchaPrefix;
import static com.ce.servicecommon.msg.CommonCodeMsg.ILLEGAL_OPERATION;
import static com.ce.serviceusersecurity.msg.UAACodeMsg.ERROR_CAPTCHA;


/**
 * @author xingxing
 */
@Slf4j
@Service("emailAuthService")
public class EmailAuthServiceImpl implements AuthService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Resource
    private SecurityInfoService securityInfoService;

    @Override
    public LoginVo execute(LoginInfoRequest loginInfoRequest) {
        log.error("进来了");
        String email = loginInfoRequest.getEmail();
        UserInfoDto initialUserInfoByEmail = securityInfoService.getInitialUserInfoByEmail(email);
        //UserInfoDto userInfoDto = securityInfoMapper.selectUserAndPowerByEmail(email);
        LoginVo loginVo = getLoginVo(loginInfoRequest, initialUserInfoByEmail);
        if (loginVo != null) return loginVo;
        throw new BusinessException(ILLEGAL_OPERATION);
    }

    private LoginVo getLoginVo(LoginInfoRequest loginInfoRequest, UserInfoDto userInfoDto) {
        String captcha = loginInfoRequest.getCaptcha();
        log.error(userInfoDto.toString());
        List<User2power> user2powers = userInfoDto.getUser2powers();
        ArrayList<Long> powerIds = new ArrayList<>();
        for (User2power user2power : user2powers) {
            powerIds.add(user2power.getPowerId());
        }
        String token = JwtUtil.sign(userInfoDto.getSecurityInfo().getAccount(), userInfoDto.getSecurityInfo().getId(),powerIds);
        //判定为邮箱+验证码登录
        if (captcha != null) {
            // todo 从redis中查询到验证码是否正确
            String s = stringRedisTemplate.opsForValue().get(loginCaptchaPrefix + loginInfoRequest.getEmail());
            if (!captcha.equals(s)) {
                throw new BusinessException(ERROR_CAPTCHA);
            }
            LoginVo loginVo = new LoginVo();
            BeanUtil.copyProperties(userInfoDto, loginVo);
            loginVo.setToken(token);
            return loginVo;
        }
        return null;
    }
}
