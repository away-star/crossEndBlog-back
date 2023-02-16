package com.star.servicecontent.service;

import com.star.servicecontent.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.servicecontent.entity.dto.PostDto;

/**
 * <p>
 * post表 服务类
 * </p>
 *
 * @author star
 * @since 2023-02-06
 */
public interface PostService extends IService<Post> {

    PostDto getDetail(Integer id);

    Boolean post(Post post);
}
