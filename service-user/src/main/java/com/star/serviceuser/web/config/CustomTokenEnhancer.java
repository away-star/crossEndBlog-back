package com.star.serviceuser.web.config;

import cn.hutool.json.JSONUtil;
import com.star.servicecommon.exception.BusinessException;
import com.star.servicecommon.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

import static com.star.servicecommon.msg.CommonCodeMsg.ILLEGAL_OPERATION;

/**
 * @author star
 * @date 2023/4/13 17:08
 */

@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        String user = ((UserDetails) authentication.getPrincipal()).getUsername();
        SecurityUtil.UserInfo UserInfo = null;
        try {
            UserInfo = JSONUtil.toBean(user, SecurityUtil.UserInfo.class);
        } catch (Exception e) {
            log.debug("解析jwt中的用户身份无法转成XcUser对象:{}", UserInfo);
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        log.error("UserInfo:{}", UserInfo.getId());
        String loginInformationId = String.valueOf(UserInfo.getId());
        additionalInfo.put("loginInformationId", loginInformationId);
        log.error(((UserDetails) authentication.getPrincipal()).getUsername());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}