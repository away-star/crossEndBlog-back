package com.star.serviceuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.serviceuser.domain.entity.UserInfo;
import com.star.serviceuser.domain.vo.InitialArgs;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
public interface UserInfoService extends IService<UserInfo> {


    InitialArgs getInitial(Long userId);

    UserInfo initialInfo(Long userId, String registerIp);

}
