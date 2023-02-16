package com.star.serviceuser.domain.vo;

import com.star.serviceuser.domain.entity.LoginInformation;
import com.star.serviceuser.domain.entity.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author star
 * @date 2023/2/6 15:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetail extends LoginInformation {

    private UserInfo userInfo;

}
