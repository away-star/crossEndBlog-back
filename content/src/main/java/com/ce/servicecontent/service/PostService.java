package com.ce.servicecontent.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ce.servicecontent.domain.entity.Post;
import com.ce.servicecontent.domain.vo.PostDetailVo;

/**
 * <p>
 * post表 服务类
 * </p>
 *
 * @author xingxing
 * @since 2023-02-06
 */
public interface PostService extends IService<Post> {

    PostDetailVo getPostDetailVo(Integer id);
}
