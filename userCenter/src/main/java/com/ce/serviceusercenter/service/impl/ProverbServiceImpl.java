package com.ce.serviceusercenter.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.serviceusercenter.domain.entity.Proverb;
import com.ce.serviceusercenter.mapper.ProverbMapper;
import com.ce.serviceusercenter.service.ProverbService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 箴言篇 服务实现类
 * </p>
 *
 * @author xingxing
 * @since 2023-09-11
 */
@Service
public class ProverbServiceImpl extends ServiceImpl<ProverbMapper, Proverb> implements ProverbService {

}
