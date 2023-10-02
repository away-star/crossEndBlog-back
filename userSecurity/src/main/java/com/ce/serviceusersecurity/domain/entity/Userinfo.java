package com.ce.serviceusersecurity.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户基本信息
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("Userinfo")
//@ApiModel(value = "Userinfo对象", description = "用户基本信息")
public class Userinfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;

    @Schema(description = "用户登录信息id")
    @TableField("security_info_id")
    private Long securityInfoId;

    @Schema(description = "网名")
    @TableField("nickname")
    private String nickname;

    @Schema(description = "生日")
    @TableField("birthday")
    private LocalDate birthday;

    @Schema(description = "头像")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "性别")
    @TableField("gender")
    private String gender;

    @Schema(description = "用户地址")
    @TableField("address")
    private String address;

    @Schema(description = "用户等级")
    @TableField("user_lever")
    private Boolean userLever;

    @Schema(description = "QQ号")
    @TableField("qq")
    private Long qq;

    @Schema(description = "首页展示图（不超过五张）")
    @TableField("slide_show")
    private String slideShow;

    @Schema(description = "个人签名")
    @TableField("idiograph")
    private String idiograph;

    @Schema(description = "欢迎语")
    @TableField("welcome_text")
    private String welcomeText;

    @Schema(description = "github地址")
    @TableField("github_addr")
    private String githubAddr;

    @Schema(description = "掘金地址")
    @TableField("juejin_addr")
    private String juejinAddr;

    @Schema(description = "CSDN地址")
    @TableField("CSDN_addr")
    private String csdnAddr;

    @Schema(description = "副昵称（或自拟称号）")
    @TableField("subname")
    private String subname;

    @Schema(description = "该记录创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "该记录最后一次修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    @TableField("is_delete")
    private Integer deleteFlag;

    @Schema(description = "在线状态")
    @TableField("is_online")
    private Integer isOnline;
}
