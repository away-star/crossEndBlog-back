package com.ce.serviceusersecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ce.serviceusersecurity.domain.dto.RecoverRequest;
import com.ce.serviceusersecurity.domain.dto.RegisterInfoRequest;
import com.ce.serviceusersecurity.domain.dto.UserInfoDto;
import com.ce.serviceusersecurity.domain.entity.SecurityInfo;

/**
 * <p>
 * 登录安全信息 服务类
 * </p>
 *
 * @author xingxing
 * @since 2023-01-30
 */
public interface SecurityInfoService extends IService<SecurityInfo> {

    void register(RegisterInfoRequest registerInfoRequest, String registerIp);

    UserInfoDto getInitialUserInfoByAccount(String account);

    UserInfoDto getInitialUserInfoByPhone(String phone);

    UserInfoDto getInitialUserInfoByEmail(String email);
    UserInfoDto getInitialUserInfoById(Long security_info_id);

    void recover(RecoverRequest recoverRequest);
}
