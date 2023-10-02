package com.ce.servicecontent.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.servicecontent.domain.entity.Reply;
import com.ce.servicecontent.mapper.ReplyMapper;
import com.ce.servicecontent.service.ReplyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 回复表（包含评论的回复以及回复的回复） 服务实现类
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {

}
