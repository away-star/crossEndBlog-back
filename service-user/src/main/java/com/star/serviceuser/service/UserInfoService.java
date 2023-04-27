package com.star.serviceuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.serviceuser.domain.dto.BlogSetting;
import com.star.serviceuser.domain.dto.UserInfoDto;
import com.star.serviceuser.domain.vo.InitialArgs;
import com.star.serviceuser.domain.entity.UserInfo;

import java.util.List;


/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
public interface UserInfoService extends IService<UserInfo> {


    InitialArgs getInitial(Long loginInformationId);

    UserInfo initialInfo(Long userId, String registerIp);

    boolean updateInfo(UserInfo userInfo);

    boolean updateBlogSetting(BlogSetting blogSetting);

    List<UserInfoDto> getAllUsers();
}
