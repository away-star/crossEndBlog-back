package com.ce.serviceusersecurity.service;

import com.ce.serviceusersecurity.domain.entity.User2power;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xingxing
 * @since 2023-01-31
 */
public interface User2powerService extends IService<User2power> {
    void initialPower(Long security_info_id);
}
