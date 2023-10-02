package com.ce.serviceusersecurity.domain.vo;

import com.ce.serviceusersecurity.domain.dto.UserInfoDto;
import com.ce.serviceusersecurity.domain.entity.Power;
import lombok.Data;

import java.util.List;

/**
 * @author xingxing
 * @date 2023/2/2 18:03
 */

@Data
public class InitialArgs {
    private com.ce.serviceusersecurity.domain.entity.SecurityInfo SecurityInfo;
    private List<Power> power;
    private List<Label> labels;
    private List<Proverb> Proverb;
    private UserInfoDto userInfoDto;
}
