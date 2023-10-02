package com.ce.serviceusercenter.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ce.serviceusercenter.domain.entity.Proverb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 箴言篇 Mapper 接口
 * </p>
 *
 * @author xingxing
 * @since 2023-01-30
 */
@Mapper
public interface ProverbMapper extends BaseMapper<Proverb> {

    @Select("SELECT * FROM Proverb where is_using=1 and is_delete=0 and login_information_id=#{userId} ORDER BY RAND() LIMIT 1；")
    Proverb getRandProverbs(@Param("email") String email);

}
