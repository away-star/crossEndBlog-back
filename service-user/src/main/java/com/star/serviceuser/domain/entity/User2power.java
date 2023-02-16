package com.star.serviceuser.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author star
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user2power")
@ApiModel(value="User2power对象", description="")
public class User2power implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户主键")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "权限主键")
    @TableField("power_id")
    private Integer powerId;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


}
