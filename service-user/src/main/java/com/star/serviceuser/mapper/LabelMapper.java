package com.star.serviceuser.mapper;

import com.star.serviceuser.domain.entity.Label;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户label表 Mapper 接口
 * </p>
 *
 * @author star
 * @since 2023-04-16
 */
@Mapper
public interface LabelMapper extends BaseMapper<Label> {

}
