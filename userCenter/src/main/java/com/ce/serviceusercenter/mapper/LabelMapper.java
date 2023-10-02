package com.ce.serviceusercenter.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ce.serviceusercenter.domain.entity.Label;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户label表 Mapper 接口
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Mapper
public interface LabelMapper extends BaseMapper<Label> {

}
