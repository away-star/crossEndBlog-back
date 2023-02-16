package com.star.serviceuser.domain.dto;

import com.star.serviceuser.domain.entity.LoginInformation;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author star
 * @date 2023/1/31 0:04
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginInformationDto extends LoginInformation {
    private String captcha;

    //认证的类型
    private String authType;
}
