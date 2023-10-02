package com.ce.serviceusermq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ce.serviceusermq.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户基本信息 Mapper 接口
 * </p>
 *
 * @author ce
 * @since 2023-01-30
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

}
