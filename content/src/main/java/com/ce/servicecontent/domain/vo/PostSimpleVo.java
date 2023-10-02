package com.ce.servicecontent.domain.vo;

import com.ce.servicecontent.domain.entity.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: xingxing
 * @create: 2023-10-02 20:56
 * @Description: postVo
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PostSimpleVo extends Post {

    private int FavoriteCount;
}
