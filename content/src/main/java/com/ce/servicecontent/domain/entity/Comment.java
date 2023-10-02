package com.ce.servicecontent.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
//@ApiModel(value="Comment对象", description="评论表")
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "评论id主键")
    @TableId("id")
    private Long id;

    @Schema(description = "评论内容")
    @TableField("content")
    private String content;

    @Schema(description = "评论人id")
    @TableField("author_id")
    private Long authorId;

    @Schema(description = "文章id")
    @TableField("post_id")
    private Long postId;

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
