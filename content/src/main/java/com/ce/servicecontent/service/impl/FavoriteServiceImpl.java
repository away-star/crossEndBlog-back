package com.ce.servicecontent.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.servicecontent.domain.entity.Favorite;
import com.ce.servicecontent.mapper.FavoriteMapper;
import com.ce.servicecontent.service.FavoriteService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 点赞信息 服务实现类
 * </p>
 *
 * @author xingxing
 * @since 2023-10-02
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

}
