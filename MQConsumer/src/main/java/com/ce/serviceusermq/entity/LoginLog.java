package com.ce.serviceusermq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户基本信息
 * </p>
 *
 * @author ce
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("login_log")
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户登录信息id")
    @TableField("login_information_id")
    private Long securityInfoId;

    @Schema(description = "在线状态")
    @TableField("isOnline")
    private Integer isOnline;

    @Schema(description = "登录时间")
    @TableField("login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    @Schema(description = "登录的ip地址")
    @TableField("login_ip")
    private String loginIp;
}
