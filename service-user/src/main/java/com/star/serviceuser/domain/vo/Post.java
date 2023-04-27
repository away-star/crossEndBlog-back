package com.star.serviceuser.domain.vo;

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
 * post表
 * </p>
 *
 * @author star
 * @since 2023-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("post")
@ApiModel(value="Post对象", description="post表")
public class Post implements Serializable {

    private static final long serialVersionUID=1L;


    @ApiModelProperty(value = "帖子id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "帖子标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "帖子内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "发帖时间，时间戳")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "post作者id")
    @TableField("author_id")
    private Long authorId;

    @ApiModelProperty(value = "post最后一次修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "归属领域")
    @TableField("category")
    private String category;

    @ApiModelProperty(value = "是否公开")
    @TableField("is_public")
    private boolean open;

    @ApiModelProperty(value = "封面图片")
    @TableField("cover_url")
    private String coverUrl;

    @ApiModelProperty(value = "简介")
    @TableField("description")
    private String description;

}
