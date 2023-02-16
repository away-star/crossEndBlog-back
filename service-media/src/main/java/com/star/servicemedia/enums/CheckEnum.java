package com.star.servicemedia.enums;


import lombok.Getter;

/**
 * @author star
 * 检查用户是否做过某事
 */

@Getter
public enum CheckEnum {
    DOWN(1, "已经做过"),

    NOTDOWN(2, "未做过");

    private final int code;
    private final String description;

    CheckEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
