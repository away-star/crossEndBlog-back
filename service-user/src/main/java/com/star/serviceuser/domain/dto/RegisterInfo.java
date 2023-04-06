package com.star.serviceuser.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author star
 * @date 2023/2/3 0:50
 */
@Data
public class RegisterInfo {


    private String phone;

    private String email;

    private String account;

    private String password;

    private String captcha;

    @NotBlank
    private String registerType;
}
