package com.ce.servicecommon.constant;

/**
 * @author xingxing
 * @date 2023/2/6 13:06
 */
public class Security {
    public static String realIpKey = "realIp";

    public static final long expireTime = 240 * 60 * 1000;
    //token秘钥
    public static final String tokenSecret = "cross";
    public static final int alive = 0;
    public static final int deleted = 1;
    public static final int shareAuthPowerCode = 10001;

}
