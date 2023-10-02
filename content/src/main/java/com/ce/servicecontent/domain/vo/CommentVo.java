package com.ce.servicecontent.domain.vo;

import com.ce.servicecontent.domain.entity.Comment;
import com.ce.servicecontent.domain.entity.Reply;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: xingxing
 * @create: 2023-10-02 21:13
 * @Description: 评论展现
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVo extends Comment {

    private List<Reply> replies;

    private int favoriteCount;
}
