package com.ce.serviceusercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ce.serviceusercenter.domain.entity.SecurityInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户安全信息 Mapper 接口
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Mapper
public interface SecurityInfoMapper extends BaseMapper<SecurityInfo> {

}
