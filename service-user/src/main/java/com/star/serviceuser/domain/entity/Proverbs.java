package com.star.serviceuser.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 箴言篇
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("proverbs")
@ApiModel(value="Proverbs对象", description="箴言篇")
public class Proverbs implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "箴言")
    @TableField("context")
    private String context;

    @ApiModelProperty(value = "创作者")
    @TableField("create_people")
    private String createPeople;

    @ApiModelProperty(value = "使用人id")
    @TableField("login_information_id")
    private Long loginInformationId;

    @ApiModelProperty(value = "创作时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "主键")
   @TableId(value = "id", type = IdType.AUTO)
  // @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Long id;

    @ApiModelProperty(value = "状态 1表示启用 0表示不启用 ")
    @TableField("is_using")
    private Integer isUsing;

    @ApiModelProperty(value = "是否删除  1表示删除了 2表示未删除")
    @TableField("is_delete")
    private Integer isDelete;


}
