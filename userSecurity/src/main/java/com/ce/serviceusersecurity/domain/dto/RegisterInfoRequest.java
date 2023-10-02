package com.ce.serviceusersecurity.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author xingxing
 * @date 2023/2/3 0:50
 */
@Data
public class RegisterInfoRequest {


    private String phone;

    private String email;

    @NotBlank
    private String captcha;

    @NotBlank
    private String registerType;
}
