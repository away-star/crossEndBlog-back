package com.star.servicecontent.web.controller;

import com.star.servicecommon.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RefreshScope--------在nacos的配置文件可以动态刷新
 * @author star
 * @date 2023/1/28 12:05
 */
@Slf4j
@RestController
@RequestMapping("/content")
@RefreshScope
public class testController {


    @GetMapping("/777")
    //@PreAuthorize("hasAuthority('chat')")
    public String post(){
        log.error("jinlaile ");
        SecurityUtil.UserInfo user = SecurityUtil.getUser();
        log.error(user.toString());
        return "777";
    }
}
