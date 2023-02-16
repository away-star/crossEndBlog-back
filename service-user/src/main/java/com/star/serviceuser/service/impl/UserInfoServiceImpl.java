package com.star.serviceuser.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.serviceuser.domain.entity.Proverbs;
import com.star.serviceuser.domain.entity.UserInfo;
import com.star.serviceuser.domain.vo.InitialArgs;
import com.star.serviceuser.mapper.ProverbsMapper;
import com.star.serviceuser.mapper.UserInfoMapper;
import com.star.serviceuser.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

import static com.star.serviceuser.constant.Authority.initialAvatar;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private ProverbsMapper proverbsMapper;
    @Override
    public InitialArgs getInitial(Long userId) {
        Proverbs randProverb = proverbsMapper.getRandProverbs(userId);
        UserInfo one = this.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getLoginInformationId, userId));
        InitialArgs userDetail = new InitialArgs();
        BeanUtil.copyProperties(one,userDetail);
        userDetail.setProverb(randProverb);
        return userDetail;
    }

    @Override
    public UserInfo initialInfo(Long userId, String registerIp) {
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginInformationId(userId);
        userInfo.setRegisterIp(registerIp);
        userInfo.setAvatar(initialAvatar);
        userInfo.setNickname("用户"+ UUID.randomUUID().toString().substring(1,5));
        this.save(userInfo);
        return userInfo;
    }
}
