package com.star.serviceuser.service;

import com.star.serviceuser.domain.entity.User2power;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author star
 * @since 2023-01-31
 */
public interface User2powerService extends IService<User2power> {
    boolean initialAuthorization(Long userId);
}
