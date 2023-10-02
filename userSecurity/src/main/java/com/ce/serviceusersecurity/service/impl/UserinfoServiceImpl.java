package com.ce.serviceusersecurity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.serviceusersecurity.domain.dto.UserInfoDto;
import com.ce.serviceusersecurity.domain.entity.SecurityInfo;
import com.ce.serviceusersecurity.domain.entity.Userinfo;
import com.ce.serviceusersecurity.mapper.UserinfoMapper;
import com.ce.serviceusersecurity.service.SecurityInfoService;
import com.ce.serviceusersecurity.service.UserinfoService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ce.servicecommon.constant.Security.alive;

/**
 * @author: xingxing
 * @create: 2023-09-30 16:04
 * @Description: UserInfoService实现类
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements UserinfoService {


    @Resource
    @Lazy
    private SecurityInfoService securityInfoService;


    @Override
    public Page<UserInfoDto> getUsersList(Long security_info_id,int size) {
        Page<SecurityInfo> pageTemp = new Page<>(1, size);
        LambdaQueryWrapper<SecurityInfo> wrapper = new LambdaQueryWrapper<SecurityInfo>().lt(SecurityInfo::getId, security_info_id).eq(SecurityInfo::getDeleteFlag, alive);
        List<SecurityInfo> list = securityInfoService.list(pageTemp, wrapper);
        List<Long> ids = list.stream().map(SecurityInfo::getId).toList();
        LambdaQueryWrapper<Userinfo> wrapper1 = new LambdaQueryWrapper<Userinfo>().in(Userinfo::getSecurityInfoId, ids);
        List<Userinfo> list1 = this.list(wrapper1);
        List<UserInfoDto> userInfoDtos = list1.stream().map(userinfo -> {
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setUserinfo(userinfo);
            userInfoDto.setSecurityInfo(list.stream().filter(securityInfo -> securityInfo.getId().equals(userinfo.getSecurityInfoId())).findFirst().get());
            return userInfoDto;
        }).toList();

        Page<UserInfoDto> page = new Page<>();
        BeanUtil.copyProperties(pageTemp, page);
        page.setRecords(userInfoDtos);
        return page;
    }
}
