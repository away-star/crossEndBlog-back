package com.star.serviceuser.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户基本信息
 * </p>
 *
 * @author star
 * @since 2023-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_info")
@ApiModel(value="UserInfo对象", description="用户基本信息")
public class UserInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户登录信息id")
    @TableField("login_information_id")
    private Long loginInformationId;

    @ApiModelProperty(value = "网名")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "副昵称（或自拟称号）")
    @TableField("subname")
    private String subname;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    private Date birthday;

    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty(value = "用户地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "用户等级")
    @TableField("user_lever")
    private Boolean userLever;

    @ApiModelProperty(value = "用户创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "用户最后一次信息修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "QQ号")
    @TableField("qq")
    private Long qq;

    @ApiModelProperty(value = "首页展示图（不超过五张）")
    @TableField("slide_show")
    private String slideShow;

    @ApiModelProperty(value = "个人签名")
    @TableField("idiograph")
    private String idiograph;

    @ApiModelProperty(value = "欢迎语")
    @TableField("welcome_text")
    private String welcomeText;

    @ApiModelProperty(value = "github地址")
    @TableField("github_addr")
    private String githubAddr;

    @ApiModelProperty(value = "掘金地址")
    @TableField("juejin_addr")
    private String juejinAddr;

    @ApiModelProperty(value = "CSDN地址")
    @TableField("CSDN_addr")
    private String csdnAddr;
}
