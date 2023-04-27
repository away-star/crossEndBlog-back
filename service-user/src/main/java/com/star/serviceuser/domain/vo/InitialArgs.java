package com.star.serviceuser.domain.vo;

import com.star.serviceuser.domain.dto.UserInfoDto;
import com.star.serviceuser.domain.entity.Label;
import com.star.serviceuser.domain.entity.LoginInformation;
import com.star.serviceuser.domain.entity.Power;
import com.star.serviceuser.domain.entity.Proverbs;
import lombok.Data;

import java.util.List;

/**
 * @author star
 * @date 2023/2/2 18:03
 */

@Data
public class InitialArgs {
    private LoginInformation loginInformation;
    private List<Power> power;
    private List<Label> labels;
    private List<Proverbs> proverbs;
    private UserInfoDto userInfoDto;
}
