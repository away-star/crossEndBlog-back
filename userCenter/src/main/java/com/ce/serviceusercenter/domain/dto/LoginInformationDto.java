package com.ce.serviceusercenter.domain.dto;

import com.ce.serviceusercenter.domain.entity.SecurityInfo;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;

/**
 * @author xingxing
 * @date 2023/1/31 0:04
 */


@EqualsAndHashCode(callSuper = true)
@Data
public class LoginInformationDto extends SecurityInfo {

    @NotBlank(message = "验证码不能为空")
    private String captcha;

    //认证的类型
    private String authType;

    @Override
    public String toString() {
        return super.toString() + ", captcha=" + captcha + ", authType=" + authType;
    }
}
