package com.star.serviceuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.serviceuser.domain.dto.RegisterInfo;
import com.star.serviceuser.domain.entity.LoginInformation;
import com.star.serviceuser.domain.vo.UserDetail;

/**
 * <p>
 * 登录安全信息 服务类
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
public interface LoginInformationService extends IService<LoginInformation> {

    UserDetail register(RegisterInfo registerInfo, String registerIp);

    UserDetail getDetail();

    boolean recover(String email, String captcha, String password);
}
