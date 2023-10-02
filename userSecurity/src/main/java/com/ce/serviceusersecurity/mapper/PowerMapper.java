package com.ce.serviceusersecurity.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ce.serviceusersecurity.domain.entity.Power;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xingxing
 * @since 2023-09-19
 */
@Mapper
public interface PowerMapper extends BaseMapper<Power> {

    @Select("SELECT	* FROM power WHERE id IN ( SELECT power_id FROM user2power WHERE user_id = #{userId})")
    List<Power> selectPermissionByUserId(@Param("userId") Long userId);

}
