package com.ce.serviceusersecurity.domain.dto;

import com.ce.serviceusersecurity.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xingxing
 * @date 2023/4/10 13:46
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private Userinfo userinfo;
    private SecurityInfo securityInfo;
    private List<User2power> user2powers;
    private List<Label> labels;
    private List<Proverb> proverbs;
}
