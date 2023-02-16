package com.star.serviceuser.service.impl;

import com.star.serviceuser.domain.entity.LoginLog;
import com.star.serviceuser.mapper.LoginLogMapper;
import com.star.serviceuser.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
