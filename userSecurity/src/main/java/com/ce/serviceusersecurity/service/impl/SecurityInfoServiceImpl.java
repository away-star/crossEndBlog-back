package com.ce.serviceusersecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.servicecommon.exception.BusinessException;
import com.ce.serviceusersecurity.domain.dto.RecoverRequest;
import com.ce.serviceusersecurity.domain.dto.RegisterInfoRequest;
import com.ce.serviceusersecurity.domain.dto.UserInfoDto;
import com.ce.serviceusersecurity.domain.entity.*;
import com.ce.serviceusersecurity.mapper.SecurityInfoMapper;
import com.ce.serviceusersecurity.service.*;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ce.servicecommon.constant.RedisAbout.recoveryCaptchaPrefix;
import static com.ce.servicecommon.constant.Security.alive;
import static com.ce.servicecommon.msg.CommonCodeMsg.DATABASE_ERROR;
import static com.ce.servicecommon.msg.CommonCodeMsg.METHOD_ARGUMENT_IN_VALID;
import static com.ce.serviceusersecurity.msg.UAACodeMsg.ERROR_CAPTCHA;
import static com.ce.serviceusersecurity.msg.UAACodeMsg.LOGIN_ERROR_ACCOUNT;

/**
 * <p>
 * 登录安全信息 服务实现类
 * </p>
 *
 * @author xingxing
 * @since 2023-01-30
 */
@Service
public class SecurityInfoServiceImpl extends ServiceImpl<SecurityInfoMapper, SecurityInfo> implements SecurityInfoService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private User2powerService user2powerService;

    @Resource
    private UserinfoService userInfoService;

    @Resource
    private ProverbService proverbService;

    @Resource
    private LabelService labelService;


    @Override
    @Transactional
    public void register(RegisterInfoRequest registerInfoRequest, String registerIp) {
        SecurityInfo securityInfo = new SecurityInfo();
        if (registerInfoRequest.getRegisterType().equals("email")) {
            securityInfo.setEmail(registerInfoRequest.getEmail());
            securityInfo.setAccount(registerInfoRequest.getEmail());
        } else if (registerInfoRequest.getRegisterType().equals("phone")) {
            securityInfo.setPhone(registerInfoRequest.getPhone());
            securityInfo.setAccount(registerInfoRequest.getPhone());
        } else {
            throw new BusinessException(METHOD_ARGUMENT_IN_VALID);
        }
        this.save(securityInfo);
        user2powerService.initialPower(securityInfo.getId());
        Userinfo userinfo = new Userinfo();
        userinfo.setSecurityInfoId(securityInfo.getId());
        if (!userInfoService.save(userinfo)) {
            throw new BusinessException(DATABASE_ERROR);
        }
    }

    @Override
    public UserInfoDto getInitialUserInfoByAccount(String account) {
        SecurityInfo one = this.getOne(new LambdaQueryWrapper<SecurityInfo>().eq(SecurityInfo::getAccount, account).eq(SecurityInfo::getDeleteFlag, alive));
        if (one == null) {
            throw new BusinessException(LOGIN_ERROR_ACCOUNT);
        }
        UserInfoDto initial = getInitial(one.getId());
        initial.setSecurityInfo(one);
        return initial;
    }

    @Override
    public UserInfoDto getInitialUserInfoByPhone(String phone) {
        SecurityInfo one = this.getOne(new LambdaQueryWrapper<SecurityInfo>().eq(SecurityInfo::getPhone, phone).eq(SecurityInfo::getDeleteFlag, alive));
        if (one == null) {
            throw new BusinessException(LOGIN_ERROR_ACCOUNT);
        }
        UserInfoDto initial = getInitial(one.getId());
        initial.setSecurityInfo(one);
        return initial;
    }

    @Override
    public UserInfoDto getInitialUserInfoByEmail(String email) {
        SecurityInfo one = this.getOne(new LambdaQueryWrapper<SecurityInfo>().eq(SecurityInfo::getEmail, email).eq(SecurityInfo::getDeleteFlag, alive));
        if (one == null) {
            throw new BusinessException(LOGIN_ERROR_ACCOUNT);
        }
        UserInfoDto initial = getInitial(one.getId());
        initial.setSecurityInfo(one);
        return initial;
    }

    @Override
    public UserInfoDto getInitialUserInfoById(Long security_info_id) {
        SecurityInfo one = this.getOne(new LambdaQueryWrapper<SecurityInfo>().eq(SecurityInfo::getId, security_info_id).eq(SecurityInfo::getDeleteFlag, alive));
        if (one == null) {
            throw new BusinessException(LOGIN_ERROR_ACCOUNT);
        }
        UserInfoDto initial = getInitial(one.getId());
        initial.setSecurityInfo(one);
        return initial;
    }


    // todo 根据用户id博客初始数据
    /*
     * @param securityInfoId
     * Return com.ce.serviceusersecurity.domain.vo.LoginVo
     * Author xingxing
     * Date 2023/10/1
     */
    private UserInfoDto getInitial(Long securityInfoId) {
        Userinfo userinfo = userInfoService.getOne(new LambdaQueryWrapper<Userinfo>().eq(Userinfo::getSecurityInfoId, securityInfoId).eq(Userinfo::getDeleteFlag, alive));
        List<Label> labelList = labelService.list(new LambdaQueryWrapper<Label>().eq(Label::getDeleteFlag, alive).eq(Label::getSecurityInfoId, securityInfoId));
        List<Proverb> proverbList = proverbService.list(new LambdaQueryWrapper<Proverb>().eq(Proverb::getDeleteFlag, alive).eq(Proverb::getSecurityInfoId, securityInfoId));
        List<User2power> user2powerList = user2powerService.list(new LambdaQueryWrapper<User2power>().eq(User2power::getDeleteFlag, alive).eq(User2power::getSecurityInfoId, securityInfoId));
        if (userinfo == null || user2powerList == null) {
            throw new BusinessException(DATABASE_ERROR);
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserinfo(userinfo);
        userInfoDto.setLabels(labelList);
        userInfoDto.setProverbs(proverbList);
        userInfoDto.setUser2powers(user2powerList);
        return userInfoDto;
    }


    @Override
    public void recover(RecoverRequest recoverRequest) {
        String c = stringRedisTemplate.opsForValue().get(recoveryCaptchaPrefix + recoverRequest.getEmail());
        if (recoverRequest.getCaptcha().equals(c)) {
            throw new BusinessException(ERROR_CAPTCHA);
        }
        LambdaQueryWrapper<SecurityInfo> wrapper = new LambdaQueryWrapper<>();
        if ("email".equals(recoverRequest.getAuthType())) {
            wrapper.eq(SecurityInfo::getEmail, recoverRequest.getEmail()).eq(SecurityInfo::getDeleteFlag, alive);
        } else if ("phone".equals(recoverRequest.getAuthType())) {
            wrapper.eq(SecurityInfo::getPhone, recoverRequest.getPhone()).eq(SecurityInfo::getDeleteFlag, alive);
        }
        SecurityInfo userinfo = new SecurityInfo();
        userinfo.setPassword(recoverRequest.getPassword());
        if (!this.update(userinfo, wrapper)) {
            throw new BusinessException(DATABASE_ERROR);
        }
    }
}
