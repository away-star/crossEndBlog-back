package com.ce.serviceusercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.serviceusercenter.domain.entity.Userinfo;
import com.ce.serviceusercenter.mapper.UserInfoMapper;
import com.ce.serviceusercenter.service.UserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author xingxing
 * @since 2023-01-30
 */
@Service
@Slf4j
public class UserinfoServiceImpl extends ServiceImpl<UserInfoMapper, Userinfo> implements UserinfoService {


}
