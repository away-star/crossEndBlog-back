package com.star.serviceuser.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author star
 * @date 2023/2/7 12:21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OauthResult{

    private UserDetail userDetail;

    OAuth2AccessToken oAuth2AccessToken;
}
