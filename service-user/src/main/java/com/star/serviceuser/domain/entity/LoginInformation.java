package com.star.serviceuser.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 登录安全信息
 * @EqualsAndHashCode(callSuper = false)的作用：callSuper = true，根据子类自身的字段值和从父类继承的字段值 来生成hashcode，当两个子类对象比较时，只有子类对象的本身的字段值和继承父类的字段值都相同，equals方法的返回值是true。
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("login_information")
@ApiModel(value="LoginInformation对象", description="登录安全信息")
public class LoginInformation implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableField(value = "id",fill = FieldFill.INSERT)
    private Long id;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("Email")
    private String email;

    @ApiModelProperty(value = "账号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "注册的ip地址")
    @TableField("register_ip")
    private String registerIp;
}
