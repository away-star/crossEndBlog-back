package com.star.serviceuser.service.impl;

import com.star.serviceuser.domain.entity.Proverbs;
import com.star.serviceuser.mapper.ProverbsMapper;
import com.star.serviceuser.service.ProverbsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 箴言篇 服务实现类
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
@Service
public class ProverbsServiceImpl extends ServiceImpl<ProverbsMapper, Proverbs> implements ProverbsService {

}
