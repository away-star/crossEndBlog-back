package com.ce.servicecontent.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * post表
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("post")
//@ApiModel(value="Post对象", description="post表")
public class Post implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "帖子id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "帖子标题")
    @TableField("title")
    private String title;

    @Schema(description = "帖子内容")
    @TableField("content")
    private String content;

    @Schema(description = "post作者id")
    @TableField("author_id")
    private Long authorId;

    @Schema(description = "归属领域")
    @TableField("category")
    private String category;

    @Schema(description = "是否公开")
    @TableField("is_public")
    private Boolean isPublic;

    @Schema(description = "封面图片")
    @TableField("cover_url")
    private String coverUrl;

    @Schema(description = "简介")
    @TableField("description")
    private String description;

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
