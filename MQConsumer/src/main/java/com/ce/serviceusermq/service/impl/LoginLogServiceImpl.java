package com.ce.serviceusermq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.serviceusermq.entity.LoginLog;
import com.ce.serviceusermq.mapper.LoginLogMapper;
import com.ce.serviceusermq.service.LoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author ce
 * @since 2023-01-30
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
