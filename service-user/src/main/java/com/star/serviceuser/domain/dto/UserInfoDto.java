package com.star.serviceuser.domain.dto;

import com.star.serviceuser.domain.entity.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author star
 * @date 2023/4/10 13:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoDto extends UserInfo {

    private String[] slideVenue;
}
