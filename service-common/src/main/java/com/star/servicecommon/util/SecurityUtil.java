package com.star.servicecommon.util;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;


@Slf4j
public class SecurityUtil {

    public static UserInfo getUser() {
        log.error("666");
        //log.error(SecurityContextHolder.getContext().toString());
        //拿jwt中的用户身份
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error(principal.toString());
        if (principal instanceof String){
            String jsonString = (String) principal;
            UserInfo UserInfo = null;
            try {
                UserInfo = JSONUtil.toBean(jsonString, UserInfo.class);
            } catch (Exception e) {
                log.error("解析jwt中的用户身份无法转成XcUser对象:{}",jsonString);
                log.error("5555555555555");
                return null;
            }
            return UserInfo;
        }
        return null;
    }



    @Data
    public static class UserInfo implements Serializable {

        private static final long serialVersionUID = 1L;


        private Long id;


        private String phone;


        private String Email;


        private String account;
    }

}
