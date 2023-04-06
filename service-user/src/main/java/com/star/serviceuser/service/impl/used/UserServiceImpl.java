package com.star.serviceuser.service.impl.used;

import cn.hutool.json.JSONUtil;
import com.star.servicecommon.exception.BusinessException;
import com.star.serviceuser.domain.dto.LoginInformationDto;
import com.star.serviceuser.domain.entity.LoginInformation;
import com.star.serviceuser.domain.entity.Power;
import com.star.serviceuser.mapper.PowerMapper;
import com.star.serviceuser.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.star.servicecommon.msg.CommonCodeMsg.DATABASE_ERROR;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2022/10/20 10:54
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private PowerMapper powerMapper;

    //传入的是AuthParamsDto的json串
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("认证请求参数:{}", s);
        LoginInformationDto loginInformationDto = null;
        try {
            //将认证参数转为AuthParamsDto类型
            loginInformationDto = JSONUtil.toBean(s, LoginInformationDto.class);
        } catch (Exception e) {
            log.info("认证请求不符合项目要求:{}", s);
            throw new RuntimeException("认证请求数据格式不对");
        }

        //认证方式,
        String authType = loginInformationDto.getAuthType();
        log.error(loginInformationDto.toString());
        //从spring容器中拿具体的认证bean实例
        AuthService authService = applicationContext.getBean(authType + "_authservice", AuthService.class);
        //开始认证,认证成功拿到用户信息
        LoginInformation loginInformation = authService.execute(loginInformationDto);
        log.error(loginInformation.toString());
        return getUserPrincipal(loginInformation);
    }


    //根据XcUserExt对象构造一个UserDetails对象

    /**
     * @param user 用户id，主键
     * @return com.xuecheng.ucenter.model.po.XcUser 用户信息
     * @description 查询用户信息
     * @author Mr.M
     * @date 2022/9/29 12:19
     */
    private UserDetails getUserPrincipal(LoginInformation user) {

        //权限列表，存放的用户权限
        List<String> permissionList = new ArrayList<>();

        //根据用户id查询数据库中他的权限
        //此处如果用mp涉及到数据库的两次调用，采用sql查询提升效率
        List<Power> powerList = powerMapper.selectPermissionByUserId(user.getId());
        powerList.forEach(menu -> {
            permissionList.add(menu.getDescription());
        });

        if (permissionList.size() == 0) {
            //用户权限,如果不加报Cannot pass a null GrantedAuthority collection
            throw new BusinessException(DATABASE_ERROR);
        }

        String[] authorities = permissionList.toArray(new String[0]);

        log.error(Arrays.toString(authorities));
        //原来存的是账号，现在扩展为用户的全部信息(密码不要放)
        user.setPassword(null);

        String jsonString = JSONUtil.toJsonStr(user);
        UserDetails userDetails = User.withUsername(jsonString).password("").authorities(authorities).build();
        log.error('[' + userDetails.getUsername() + "]登录成功");
        return userDetails;
    }

}
