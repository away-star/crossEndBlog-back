package com.star.serviceuser.domain.vo;

import com.star.serviceuser.domain.entity.Proverbs;
import com.star.serviceuser.domain.entity.UserInfo;
import lombok.Data;

/**
 * @author star
 * @date 2023/2/1 21:28
 */
@Data
public class InitialArgs extends UserInfo {
    private Proverbs proverb;
}
