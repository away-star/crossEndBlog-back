package com.ce.servicecommon.annotations;

import java.lang.annotation.*;

/**
 * @author: xingxing
 * @create: 2023-10-02 09:26
 * @Description: 权限认证
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreAuth {
    int auth();
}
