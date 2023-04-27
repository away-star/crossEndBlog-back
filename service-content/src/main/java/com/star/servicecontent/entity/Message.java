package com.star.servicecontent.entity;

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
 * 留言表
 * </p>
 *
 * @author star
 * @since 2023-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("message")
@ApiModel(value="Message对象", description="留言表")
public class Message implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "留言内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "回复内容内容")
    @TableField("response")
    private String response;

    @ApiModelProperty(value = "留言时间，时间戳")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "留言者id")
    @TableField("author_id")
    private Long authorId;

    @ApiModelProperty(value = "留言对象id")
    @TableField("host_id")
    private Long hostId;

    @ApiModelProperty(value = "该留言最后一次修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否公开")
    @TableField("is_public")
    private boolean open;


}
