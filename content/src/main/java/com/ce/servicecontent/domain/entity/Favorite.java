package com.ce.servicecontent.domain.entity;

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
 * 点赞信息
 * </p>
 *
 * @author xingxing
 * @since 2023-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("favorite")
public class Favorite implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId("id")
    private Long id;

    @Schema(description = "点赞对象id")
    @TableField("target_id")
    private Long targetId;

    @Schema(description = "点赞人id")
    @TableField("user_id")
    private Long securityInfoId;

    @Schema(description = "该条记录创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "该条最后一次更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    @TableField("is_delete")
    private Integer deleteFlag;
}
