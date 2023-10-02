package com.ce.serviceusersecurity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ce.serviceusersecurity.domain.dto.UserInfoDto;
import com.ce.serviceusersecurity.domain.vo.InitialArgs;
import com.ce.serviceusersecurity.domain.entity.Userinfo;


/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author xingxing
 * @since 2023-01-30
 */
public interface UserinfoService extends IService<Userinfo> {

    /*
     * @param securityInfoId
     * @param size
     * Return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.ce.serviceusersecurity.domain.dto.UserInfoDto>
     * Author xingxing
     * Date 2023/10/2
     */
    Page<UserInfoDto> getUsersList(Long securityInfoId,int size);
}
