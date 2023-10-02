package com.ce.serviceuser;

import com.ce.serviceusercenter.service.PowerService;
import com.ce.serviceusercenter.service.User2powerService;
import com.ce.serviceusercenter.service.UserinfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ServiceUserApplicationTests {
    @Value("${mytest}")
    private String my;

    @Resource
    private LoginInformationService loginInformationService;

    @Resource
    private PowerService powerService;

    @Resource
    private User2powerService user2power;

    @Resource
    private UserinfoService userInfoService;

    @Test
    void contextLoads() {

       /* Userinfo Userinfo = new Userinfo();
        Userinfo.setLoginInformationId(1313L);

        userInfoService.save(Userinfo);
        System.out.println(Userinfo);*/


    }

}
