package com.star.servicecontent.entity.dto;

import com.star.servicecontent.entity.Post;
import com.star.servicecontent.entity.PostInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author star
 * @date 2023/2/6 12:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostDto extends Post {
    private PostInfo postInfo;
}
