package com.ce.servicecommon.interception;

import com.ce.servicecommon.exception.BusinessException;
import com.ce.servicecommon.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.ce.servicecommon.msg.CommonCodeMsg.INSUFFICIENT_PERMISSIONS;

/**
 * @author: xingxing
 * @create: 2023-10-02 14:11
 * @Description: 日志拦截器
 */

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {


    /*
     * @param request
     * @param response
     * @param handler
     * Return boolean
     * Author xingxing
     * Date 2023/10/2
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器------------------prehandle");
        String token = request.getHeader("Authorization");
        if (JwtUtil.verify(token)) {
            throw new BusinessException(INSUFFICIENT_PERMISSIONS);
        }
        String newToken = JwtUtil.extendExpiration(token, 30);
        if (newToken != null) {
            response.setHeader("Authorization", newToken);
        }
        //业务处理判断拦截
        //true放行
        //false不放行
        return true;
    }

    /*
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * Return void
     * Author xingxing
     * Date 2023/10/2
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    /*
     * @param request
     * @param response
     * @param handler
     * @param ex
     * Return void
     * Author xingxing
     * Date 2023/10/2
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
    }
}