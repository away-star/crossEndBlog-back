package com.ce.serviceusersecurity.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 箴言篇
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("proverb")
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel(value="Proverb对象", description="箴言篇")
public class Proverb implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description =  "箴言")
    @TableField("context")
    private String context;

    @Schema(description =  "创作者")
    @TableField("create_people")
    private String createPeople;

    @Schema(description =  "使用人id")
    @TableField("security_info_id")
    private Long securityInfoId;

    @Schema(description =  "主键")
    @TableId("id")
    private Long id;

    @Schema(description =  "状态")
    @TableField("is_active")
    private Integer isActive;

    @Schema(description =  "该记录创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description =  "该记录最后一次修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description =  "逻辑删除")
    @TableField("is_delete")
    private Integer deleteFlag;


}
