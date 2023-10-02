package com.ce.serviceusersecurity.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ce.serviceusersecurity.domain.entity.Userinfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户基本信息 Mapper 接口
 * </p>
 *
 * @author xingxing
 * @since 2023-09-19
 */
@Mapper
public interface UserinfoMapper extends BaseMapper<Userinfo> {

}
