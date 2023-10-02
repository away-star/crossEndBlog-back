package com.ce.serviceusercenter.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户安全信息
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_info")
//@ApiModel(value="SecurityInfo对象", description="用户安全信息")
public class SecurityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId("id")
    private Long id;

    @Schema(description = "手机号")
    @TableField("phone")
    private String phone;

    @Schema(description = "邮箱")
    @TableField("Email")
    private String Email;

    @Schema(description = "账号")
    @TableField("account")
    private String account;

    @Schema(description = "密码")
    @TableField("password")
    private String password;

    @Schema(description = "注册ip地址")
    @TableField("register_ip")
    private String registerIp;

    @Schema(description = "该记录创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "该记录最后一次修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    @TableField("is_delete")
    private Integer deleteFlag;


}
