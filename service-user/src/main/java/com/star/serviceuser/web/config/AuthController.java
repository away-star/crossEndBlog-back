package com.star.serviceuser.web.config;

import com.example.servicecommon.domain.Result;
import com.star.serviceuser.service.LoginInformationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author star
 * @date 2023/2/7 12:10
 */
@Api(value = "OAuth接口")
@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthController implements InitializingBean {

  /*  令牌请求的端点*/
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private LoginInformationService loginInformationService;



    /**
     * 重写/oauth/token这个默认接口，返回的数据格式统一
     */
    @PostMapping(value = "/token")
    public Result<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam
    Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return Result.success(accessToken);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}