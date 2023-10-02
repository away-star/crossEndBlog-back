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
 * 用户label表
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("label")
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel(value="Label对象", description="用户label表")
public class Label implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description =  "主键")
    @TableId("id")
    private Long id;

    @Schema(description =  "用户登录信息id")
    @TableField("security_info_id")
    private Long securityInfoId;

    @Schema(description =  "标签背景")
    @TableField("background_img")
    private String backgroundImg;

    @Schema(description =  "标题")
    @TableField("title")
    private String title;

    @Schema(description =  "说明")
    @TableField("sub_title")
    private String subTitle;

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