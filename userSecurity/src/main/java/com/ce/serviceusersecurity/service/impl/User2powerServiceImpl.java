package com.ce.serviceusersecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.servicecommon.exception.BusinessException;
import com.ce.serviceusersecurity.constant.AuthorityInitial;
import com.ce.serviceusersecurity.domain.entity.User2power;
import com.ce.serviceusersecurity.mapper.User2powerMapper;
import com.ce.serviceusersecurity.service.User2powerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ce.servicecommon.msg.CommonCodeMsg.DATABASE_ERROR;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xingxing
 * @since 2023-01-31
 */
@Service
public class User2powerServiceImpl extends ServiceImpl<User2powerMapper, User2power> implements User2powerService {


    @Override
    @Transactional
    public void initialPower(Long security_info_id) {
        List<User2power> user2powers = new ArrayList<>();
        for (Long power_id : AuthorityInitial.initialPower) {
            User2power user2power = new User2power();
            user2power.setSecurityInfoId(security_info_id);
            user2power.setPowerId(power_id);
            user2powers.add(user2power);
        }
        if (!this.saveBatch(user2powers)) {
            throw new BusinessException(DATABASE_ERROR);
        }
    }
}
