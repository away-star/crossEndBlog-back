package com.star.serviceuser.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.servicecommon.exception.BusinessException;
import com.example.servicecommon.util.SecurityUtil;
import com.star.serviceuser.domain.dto.RegisterInfo;
import com.star.serviceuser.domain.entity.LoginInformation;
import com.star.serviceuser.domain.entity.UserInfo;
import com.star.serviceuser.domain.vo.UserDetail;
import com.star.serviceuser.mapper.LoginInformationMapper;
import com.star.serviceuser.service.LoginInformationService;
import com.star.serviceuser.service.User2powerService;
import com.star.serviceuser.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.example.servicecommon.msg.CommonCodeMsg.DATABASE_ERROR;
import static com.star.serviceuser.web.msg.UAACodeMsg.ERROR_CAPTCHA;
import static com.star.serviceuser.web.msg.UAACodeMsg.REGISTER_ERROR_EMAIL;

/**
 * <p>
 * 登录安全信息 服务实现类
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
@Service
public class LoginInformationServiceImpl extends ServiceImpl<LoginInformationMapper, LoginInformation> implements LoginInformationService {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private User2powerService user2powerService;

    @Resource
    private UserInfoService userInfoService;

    @Override
    //@Transactional
    public UserDetail register(RegisterInfo registerInfo, String registerIp) {


        LoginInformation loginInformation = null;
        if (StringUtils.equals(registerInfo.getRegisterType(), "password")) {
            loginInformation = registerByPassword(registerInfo.getAccount(), registerInfo.getPassword());
        }
        if (StringUtils.equals(registerInfo.getRegisterType(), "email")) {
            loginInformation = registerByEmail(registerInfo.getEmail(), registerInfo.getCaptcha());
        }
        user2powerService.initialAuthorization(loginInformation.getId());
        UserInfo userInfo = userInfoService.initialInfo(loginInformation.getId(), registerIp);

        UserDetail userDetail = new UserDetail();
        BeanUtil.copyProperties(registerInfo,userDetail,"password","captcha");
        userDetail.setUserInfo(userInfo);
        return userDetail;
    }

    @Override
    public UserDetail getDetail() {
        SecurityUtil.UserInfo user = SecurityUtil.getUser();
        UserInfo byId = userInfoService.getById(user.getId());
        UserDetail userDetail = new UserDetail();
        BeanUtil.copyProperties(user,userDetail);
        userDetail.setUserInfo(byId);
        return userDetail;
    }

    private LoginInformation registerByPassword(String account, String password) {
        LoginInformation user = new LoginInformation();
        user.setAccount(account);
        user.setPassword(password);
        log.error(user.toString());
        System.out.println("======================================");
        if (!this.save(user)){
            throw new BusinessException(DATABASE_ERROR);
        };
        //这里设置了LoginInformation的id字段的自动填充，那么后面在调用user对象有id了吗
        return user;
    }

    private LoginInformation registerByEmail(String email, String captcha) {
        LoginInformation one = this.getOne(new LambdaQueryWrapper<LoginInformation>().eq(LoginInformation::getEmail, email));
        if (one==null){
            throw new BusinessException(REGISTER_ERROR_EMAIL);
        }

        LoginInformation user = new LoginInformation();
        user.setEmail(email);


        // todo 结合redis判断用户验证码是否正确
        String s = stringRedisTemplate.opsForValue().get(email);
        if (!StringUtils.equals(captcha, s)) {
            throw new BusinessException(ERROR_CAPTCHA);
        }
        if (this.save(user)){
            throw new BusinessException(DATABASE_ERROR);
        };
        return user;
    }
}
