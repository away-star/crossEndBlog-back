package com.star.serviceuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.serviceuser.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * <p>
 * 用户基本信息 Mapper 接口
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {


    // todo 狗屎sql有问题，需要改
    @Select("SELECT li.*, p.*, pr.*, ui.* \n" +
            "FROM login_information li \n" +
            "LEFT JOIN user_info ui ON ui.login_information_id = li.id \n" +
            "LEFT JOIN power p ON p.id IN (\n" +
            "    SELECT power_id \n" +
            "    FROM user2power \n" +
            "    WHERE user_id = li.id\n" +
            ")\n" +
            "LEFT JOIN proverbs pr ON pr.login_information_id = li.id \n" +
            "WHERE li.id = #{loginInformationId}\n" +
            "GROUP BY li.id")/**/
    Map<String,Object> getInitial(@Param("loginInformationId") Long loginInformationId);
}
