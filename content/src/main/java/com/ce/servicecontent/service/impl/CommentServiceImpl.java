package com.ce.servicecontent.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.servicecontent.domain.entity.Comment;
import com.ce.servicecontent.mapper.CommentMapper;
import com.ce.servicecontent.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
