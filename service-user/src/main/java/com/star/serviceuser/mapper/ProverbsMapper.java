package com.star.serviceuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.serviceuser.domain.entity.Proverbs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 箴言篇 Mapper 接口
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
@Mapper
public interface ProverbsMapper extends BaseMapper<Proverbs> {

    @Select("SELECT * FROM proverbs where is_using=1 and is_delete=0 and login_information_id=#{userId} ORDER BY RAND() LIMIT 1；")
    Proverbs getRandProverbs(@Param("userId") Long userId);

}
