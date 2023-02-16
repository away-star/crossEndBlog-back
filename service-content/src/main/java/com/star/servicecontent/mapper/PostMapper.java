package com.star.servicecontent.mapper;

import com.star.servicecontent.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * post表 Mapper 接口
 * </p>
 *
 * @author star
 * @since 2023-02-06
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}
