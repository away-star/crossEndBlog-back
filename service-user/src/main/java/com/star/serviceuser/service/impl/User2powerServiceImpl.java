package com.star.serviceuser.service.impl;

import com.example.servicecommon.exception.BusinessException;
import com.star.serviceuser.domain.entity.User2power;
import com.star.serviceuser.mapper.User2powerMapper;
import com.star.serviceuser.service.User2powerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.servicecommon.msg.CommonCodeMsg.DATABASE_ERROR;
import static com.star.serviceuser.constant.Authority.initialAuthority;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author star
 * @since 2023-01-31
 */
@Service
public class User2powerServiceImpl extends ServiceImpl<User2powerMapper, User2power> implements User2powerService {


    @Override
    public boolean initialAuthorization(Long userId) {
        List<User2power> user2powers = new ArrayList<>();
        User2power user2power = new User2power();
        user2power.setUserId(userId);
        for (Integer integer : initialAuthority) {
            user2power.setPowerId(integer);
            user2powers.add(user2power);
        }
        if (this.saveBatch(user2powers)){
            return true;
        }
        throw new BusinessException(DATABASE_ERROR);
    }
}
