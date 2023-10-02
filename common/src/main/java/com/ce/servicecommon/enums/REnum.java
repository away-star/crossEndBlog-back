package com.ce.servicecommon.enums;

import lombok.Getter;

/**
 *
 * @author xingxing
 * @date 2022/12/22
 */
@Getter
public enum REnum {

    SUCCESS(200, "成功"),

    ERROR(400, "处理失败");

    private final int code;
    private final String description;

    REnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}


