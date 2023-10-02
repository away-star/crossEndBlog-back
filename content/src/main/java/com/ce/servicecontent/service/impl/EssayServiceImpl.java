package com.ce.servicecontent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.servicecontent.domain.entity.Essay;
import com.ce.servicecontent.mapper.EssayMapper;
import com.ce.servicecontent.service.EssayService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 随笔表 服务实现类
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Service
public class EssayServiceImpl extends ServiceImpl<EssayMapper, Essay> implements EssayService {

}
