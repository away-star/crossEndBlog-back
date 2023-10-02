package com.ce.serviceusersecurity.domain.vo;

import com.ce.serviceusersecurity.domain.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: xingxing
 * @create: 2023-09-26 20:28
 * @Description: 注册返回值
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginVo extends UserInfoDto {
    String token;
}
