package com.star.servicecontent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * post信息表
 * </p>
 *
 * @author star
 * @since 2023-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("post_info")
@ApiModel(value="PostInfo对象", description="post信息表")
public class PostInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "帖子信息id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "帖子id")
    @TableField("postId")
    private Integer postId;

    @ApiModelProperty(value = "评论数")
    @TableField("comment_num")
    private Integer commentNum;

    @ApiModelProperty(value = "浏览数")
    @TableField("scan_num")
    private Integer scanNum;

    @ApiModelProperty(value = "点赞数")
    @TableField("star_num")
    private Integer starNum;


}
