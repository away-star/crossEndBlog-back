package com.star.serviceuser.web.config;

import com.star.servicecommon.domain.Result;
import com.star.servicecommon.exception.BusinessException;
import com.star.serviceuser.domain.entity.LoginLog;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

import static com.star.serviceuser.web.msg.UAACodeMsg.LOGIN_ERROR;

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


    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * 重写/oauth/token这个默认接口，返回的数据格式统一
     */
    @PostMapping(value = "/token")
    public Result<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam
    Map<String, String> parameters, HttpServletRequest request) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        log.error("accessToken:{}", accessToken);
        if (accessToken == null) {
            throw new BusinessException(LOGIN_ERROR);
        }
        LoginLog loginLog = new LoginLog();
        setLoginLog(request, accessToken, loginLog);
        return Result.success(accessToken);
    }

    private void setLoginLog(HttpServletRequest request, OAuth2AccessToken accessToken, LoginLog loginLog) {
        loginLog.setLoginIp(request.getHeader("X-Forwarded-For"));
        loginLog.setLoginTime(LocalDateTime.now());
        loginLog.setIsOnline(1);
        loginLog.setLoginInformationId(Long.parseLong(accessToken.getAdditionalInformation().get("loginInformationId").toString()));
        rabbitTemplate.convertAndSend("exchange_user", "login", loginLog);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}