package com.ce.serviceusercenter.domain.dto;

import com.ce.serviceusercenter.domain.entity.Userinfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xingxing
 * @date 2023/4/10 13:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoDto extends Userinfo {

    private String[] slideVenue;
}
