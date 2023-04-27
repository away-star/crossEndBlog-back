package com.star.servicecontent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.servicecontent.entity.Message;
import com.star.servicecontent.mapper.MessageMapper;
import com.star.servicecontent.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 留言表 服务实现类
 * </p>
 *
 * @author star
 * @since 2023-04-20
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
