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
import java.time.LocalDateTime;

/**
 * <p>
 *
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
@TableName("user2power")
//@ApiModel(value = "User2power对象", description = "")
public class User2power implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户登录信息id")
    @TableField("security_info_id")
    private Long securityInfoId;

    @Schema(description = "权限主键")
    @TableField("power_id")
    private Long powerId;

    @Schema(description = "主键")
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;

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
