package com.ce.servicecommon.domain.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: xingxing
 * @create: 2023-09-26 22:47
 * @Description: token内含信息
 */

@Data
public class TokenClaim {
    String account;
    List<Long> powerCodeList;
    Long security_info_id;


    public TokenClaim(String account, Long security_info_id,List<Long> powerCodeList) {
        this.account = account;
        this.powerCodeList = powerCodeList;
        this.security_info_id = security_info_id;
    }

    public TokenClaim() {
    }

}
