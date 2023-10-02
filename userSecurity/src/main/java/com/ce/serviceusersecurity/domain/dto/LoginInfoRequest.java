package com.ce.serviceusersecurity.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author: xingxing
 * @create: 2023-09-20 09:40
 * @Description: 登录参数
 */
@Data
public class LoginInfoRequest {

    private String captcha;

    @NotBlank
    //认证的类型
    private String authType;

    private String phone;

    private String Email;

    private String account;

    private String password;
}
