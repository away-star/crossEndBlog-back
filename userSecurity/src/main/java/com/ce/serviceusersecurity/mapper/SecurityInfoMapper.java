package com.ce.serviceusersecurity.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ce.serviceusersecurity.domain.dto.UserInfoDto;
import com.ce.serviceusersecurity.domain.entity.SecurityInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户安全信息 Mapper 接口
 * </p>
 *
 * @author xingxing
 * @since 2023-09-19
 */
@Mapper
public interface SecurityInfoMapper extends BaseMapper<SecurityInfo> {
    @Select("SELECT label.*, proverb.*, security_info.*, user2power.*, userinfo.*\n" +
            "FROM security_info\n" +
            "LEFT JOIN ce_user.label ON security_info.id = label.security_info_id\n" +
            "LEFT JOIN ce_user.proverb ON security_info.id = proverb.security_info_id\n" +
            "LEFT JOIN ce_user.user2power ON security_info.id = user2power.security_info_id\n" +
            "LEFT JOIN ce_user.userinfo ON security_info.id = userinfo.security_info_id\n" +
            "WHERE security_info.Email = #{email} And security_info.is_delete=0;")
    UserInfoDto selectUserAndPowerByEmail(@Param("email") String email);

    @Select("SELECT label.*, proverb.*, security_info.*, user2power.*, userinfo.*\n" +
            "FROM security_info\n" +
            "LEFT JOIN ce_user.label ON security_info.id = label.security_info_id\n" +
            "LEFT JOIN ce_user.proverb ON security_info.id = proverb.security_info_id\n" +
            "LEFT JOIN ce_user.user2power ON security_info.id = user2power.security_info_id\n" +
            "LEFT JOIN ce_user.userinfo ON security_info.id = userinfo.security_info_id\n" +
            "WHERE security_info.phone = #{phone} And security_info.is_delete=0;")
    UserInfoDto selectUserAndPowerByPhone(@Param("phone") String phone);

    @Select("SELECT label.*, proverb.*, security_info.*, user2power.*, userinfo.*\n" +
            "FROM security_info\n" +
            "LEFT JOIN ce_user.label ON security_info.id = label.security_info_id\n" +
            "LEFT JOIN ce_user.proverb ON security_info.id = proverb.security_info_id\n" +
            "LEFT JOIN ce_user.user2power ON security_info.id = user2power.security_info_id\n" +
            "LEFT JOIN ce_user.userinfo ON security_info.id = userinfo.security_info_id\n" +
            "WHERE security_info.account = #{account} And security_info.is_delete=0;")
    UserInfoDto selectUserAndPowerByAccount(@Param("account") String account);


    @Select("SELECT label.*\n" +
            "FROM security_info\n" +
            "LEFT JOIN ce_user.label ON security_info.id = label.security_info_id\n" +
            "LEFT JOIN ce_user.proverb ON security_info.id = proverb.security_info_id\n" +
            "LEFT JOIN ce_user.user2power ON security_info.id = user2power.security_info_id\n" +
            "LEFT JOIN ce_user.userinfo ON security_info.id = userinfo.security_info_id\n" +
            "WHERE security_info.Email = #{email} And security_info.is_delete=0;")
    SecurityInfo selectUserAndPowerByEmail1(@Param("email") String email);
}
