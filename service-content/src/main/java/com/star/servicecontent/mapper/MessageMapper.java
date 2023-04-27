package com.star.servicecontent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.servicecontent.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 留言表 Mapper 接口
 * </p>
 *
 * @author star
 * @since 2023-04-20
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
