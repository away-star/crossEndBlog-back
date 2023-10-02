package com.ce.serviceusersecurity.service;

import com.ce.serviceusersecurity.domain.dto.LoginInfoRequest;
import com.ce.serviceusersecurity.domain.vo.LoginVo;

/**
 * @author xingxing
 * @version 1.0
 * @description 认证接口
 * @date 2022/10/20 14:48
 */
public interface AuthService {

    /**
     * @param loginInfoRequest 认证参数
     * @return com.ce.serviceusersecurity.domain.entity.SecurityInfo
     * @description 认证方法
     * @author xingxing
     * @date 2022/9/29 12:11
     */
    LoginVo execute(LoginInfoRequest loginInfoRequest);

}
