package com.star.serviceuser;

import com.star.serviceuser.domain.entity.UserInfo;
import com.star.serviceuser.service.LoginInformationService;
import com.star.serviceuser.service.PowerService;
import com.star.serviceuser.service.User2powerService;
import com.star.serviceuser.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ServiceUserApplicationTests {
    @Value("${mytest}")
    private String my;

    @Autowired
    private LoginInformationService loginInformationService;

    @Autowired
    private PowerService powerService;

    @Autowired
    private User2powerService user2power;

    @Autowired
    private UserInfoService userInfoService;

    @Test
    void contextLoads() {

        UserInfo userInfo = new UserInfo();
        userInfo.setLoginInformationId(1313L);

        userInfoService.save(userInfo);
        System.out.println(userInfo);

    }

}
