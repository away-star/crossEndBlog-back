package com.ce.serviceusersecurity;

import com.ce.serviceusersecurity.domain.entity.SecurityInfo;
import com.ce.serviceusersecurity.mapper.SecurityInfoMapper;
import com.ce.serviceusersecurity.service.SecurityInfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class ServiceUserApplicationTests {

    @Resource
    private SecurityInfoService securityInfoService;


    @Test
    void contextLoads() {
        //SecurityInfo securityInfo = securityInfoMapper.selectUserAndPowerByEmail1("1");
        //System.out.println(securityInfo);
        //List<SecurityInfo> list = securityInfoService.list();
        System.out.println(securityInfoService.getInitialUserInfoByEmail("1"));
    }
}
