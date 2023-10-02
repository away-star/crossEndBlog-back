package com.ce.servicecontent.domain.vo;

import com.ce.servicecontent.domain.entity.Post;
import lombok.Data;

import java.util.List;

/**
 * @author: xingxing
 * @create: 2023-10-02 20:56
 * @Description: postVo
 */

@Data
public class PostDetailVo {

    private Post post;

    private List<CommentVo> commentVos;

    private int FavoriteCount;
}
