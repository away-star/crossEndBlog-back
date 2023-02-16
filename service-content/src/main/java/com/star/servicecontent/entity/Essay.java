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
 * essay表
 * </p>
 *
 * @author star
 * @since 2023-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("essay")
@ApiModel(value="Essay对象", description="essay表")
public class Essay implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "随笔id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "随笔内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "写随笔时候的心情")
    @TableField("mood")
    private String mood;

    @ApiModelProperty(value = "创作时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "作者id")
    @TableField("author_id")
    private Long authorId;

    @ApiModelProperty(value = "最后一次修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否公开")
    @TableField("is_public")
    private boolean open;


    @ApiModelProperty(value = "封面图片")
    @TableField("cover_url")
    private String coverUrl;
}
