package com.star.serviceuser.domain.dto;

import com.star.serviceuser.domain.entity.LoginInformation;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author star
 * @date 2023/1/31 0:04
 */


@Data
public class LoginInformationDto extends LoginInformation {

    @NotBlank(message = "验证码不能为空")
    private String captcha;

    //认证的类型
    private String authType;

    @Override
    public String toString() {
        return super.toString() + ", captcha=" + captcha + ", authType=" + authType;
    }
}
