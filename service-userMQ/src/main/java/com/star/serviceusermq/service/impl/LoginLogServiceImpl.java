package com.star.serviceusermq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.serviceusermq.entity.LoginLog;
import com.star.serviceusermq.mapper.LoginLogMapper;
import com.star.serviceusermq.service.LoginLogService;
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
