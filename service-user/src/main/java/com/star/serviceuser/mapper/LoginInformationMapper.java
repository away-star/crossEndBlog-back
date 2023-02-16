package com.star.serviceuser.mapper;

import com.star.serviceuser.domain.entity.LoginInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 登录安全信息 Mapper 接口
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */

@Mapper
public interface LoginInformationMapper extends BaseMapper<LoginInformation> {

}
