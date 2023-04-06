package com.star.servicecontent.web.controller;

import com.star.servicecommon.domain.Result;
import com.star.servicecommon.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author star
 * @date 2023/2/5 13:39
 */
@Slf4j
@RestController
@RequestMapping("/Article")
@RefreshScope
public class ArticleController {
    private SecurityUtil securityUtil;

    @GetMapping()
    public Result getArticles(){
        SecurityUtil.UserInfo user = SecurityUtil.getUser();
        if (Objects.requireNonNull(user).getId()!=null){
            return null;
        }
        return null;
    }

}
