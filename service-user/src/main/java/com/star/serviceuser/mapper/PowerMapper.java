package com.star.serviceuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.serviceuser.domain.entity.Power;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author star
 * @since 2023-01-31
 */
@Mapper
public interface PowerMapper extends BaseMapper<Power> {

    @Select("SELECT	* FROM power WHERE id IN ( SELECT power_id FROM user2power WHERE user_id = #{userId})")
    List<Power> selectPermissionByUserId(@Param("userId") Long userId);
}
