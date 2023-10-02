package com.ce.servicecommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ce.servicecommon.domain.dto.TokenClaim;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.ce.servicecommon.constant.Security.expireTime;
import static com.ce.servicecommon.constant.Security.tokenSecret;


@Schema(name = "JwtUtil", description = "Jwt工具类")
@Slf4j
public class JwtUtil {


    /*
     * @param account
     * @param powerCodeList
     * Return java.lang.String
     * Author xingxing
     * Date 2023/9/30
     */
    public static String sign(String account, Long security_info_id,

                              List<Long> powerCodeList

    ) {
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + expireTime);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            //设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("type", "JWT");
            header.put("alg", "HS256");
            //携带username,password信息，生成签名
            return JWT.create()
                    .withHeader(header)
                    .withClaim("account", account)
                    .withClaim("powerCodeList", powerCodeList)
                    .withClaim("security_info_id", security_info_id)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("签名失败");
            return null;
        }

    }

    /*
     * @param token
     * Return boolean
     * Author xingxing
     * Date 2023/9/30
     */
    public static boolean verify(String token) {
        /**
         * @desc 验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("token校验失败");
            return false;
        }
    }

    /*
     * @param token
     * Return com.ce.serviceusersecurity.domain.dto.TokenClaim
     * Author xingxing
     * Date 2023/9/30
     */
    public static TokenClaim parseJWT(String token) {
        DecodedJWT decodeToken = JWT.decode(token);
        String email = decodeToken.getClaim("email").asString();
        Long security_info_id = Long.valueOf(decodeToken.getClaim("email").asString());
        List<Long> powerCodeList = decodeToken.getClaim("powerCodeList").asList(Long.class);
        return new TokenClaim(email, security_info_id, powerCodeList);
    }

    /*
     * @param token
     * Return boolean
     * Author xingxing
     * Date 2023/9/30
     */
    public static boolean isJwtExpired(String token) {
        try {
            DecodedJWT decodeToken = JWT.decode(token);
            return decodeToken.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }


    public static String extendExpiration(String token, int extendTimeDays) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            DecodedJWT decodedToken = JWT.decode(token);
            Date currentExpiration = decodedToken.getExpiresAt();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentExpiration);
            calendar.add(Calendar.DAY_OF_MONTH, -5);
            Date newExpiration = calendar.getTime();
            // 如果新的时间小于当前时间加上5天，那么就更新token
            if (newExpiration.before(new Date())) {
                calendar.add(Calendar.DAY_OF_MONTH, extendTimeDays + 5);// 加extendTimeDays天
                newExpiration = calendar.getTime();
                return JWT.create()
                        .withHeader(decodedToken.getHeader())
                        .withClaim("security_info_id", decodedToken.getClaim("security_info_id").asString())
                        .withClaim("account", decodedToken.getClaim("account").asString())
                        .withClaim("powerCodeList", decodedToken.getClaim("powerCodeList").asList(Long.class))
                        .withExpiresAt(newExpiration)
                        .sign(algorithm);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("延长token有效期失败", e);
            return null;
        }
    }
}

