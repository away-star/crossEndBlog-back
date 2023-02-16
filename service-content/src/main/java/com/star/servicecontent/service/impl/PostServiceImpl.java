package com.star.servicecontent.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.servicecommon.exception.BusinessException;
import com.star.servicecontent.entity.Post;
import com.star.servicecontent.entity.PostInfo;
import com.star.servicecontent.entity.dto.PostDto;
import com.star.servicecontent.mapper.PostMapper;
import com.star.servicecontent.service.PostInfoService;
import com.star.servicecontent.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.example.servicecommon.msg.CommonCodeMsg.DATABASE_ERROR;
import static com.star.servicecontent.web.msg.ContentCodeMsg.CONTENT_NOTFOUND;
import static com.star.servicecontent.web.msg.ContentCodeMsg.CONTENT_PRIVATE;

/**
 * <p>
 * post表 服务实现类
 * </p>
 *
 * @author star
 * @since 2023-02-06
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Resource
    private PostInfoService postInfoService;

    @Override
    @Transactional
    public PostDto getDetail(Integer id) {
        Post post = this.getById(id);
        log.error(post.toString());
        if (post==null){
            throw new BusinessException(CONTENT_NOTFOUND);
        }
        if (!post.isOpen()){
            throw new BusinessException(CONTENT_PRIVATE);
        }
        PostInfo info = postInfoService.getOne(new LambdaQueryWrapper<PostInfo>().eq(PostInfo::getPostId, id));
        PostDto postDto = new PostDto();
        BeanUtil.copyProperties(post,postDto);
        postDto.setPostInfo(info);
        return postDto;
    }

    @Override
    @Transactional
    public Boolean post(Post post) {
        log.error(post.toString());
        if (!this.save(post)){
            throw new BusinessException(DATABASE_ERROR);
        }
        PostInfo postInfo = new PostInfo();
        postInfo.setPostId(post.getId());
        if (!postInfoService.save(postInfo)){
            throw new BusinessException(DATABASE_ERROR);
        }
        log.error(post.toString());
        return true;
    }
}
