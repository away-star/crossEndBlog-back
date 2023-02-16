package com.star.servicemedia.Vo;


import lombok.Data;

import static com.star.servicemedia.enums.REnum.ERROR;
import static com.star.servicemedia.enums.REnum.SUCCESS;


@Data
public class R<T> {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static <T> R<T> success() {
        return restR(null, SUCCESS.getCode(), null);
    }

    public static <T> R<T> success(T data) {
        return restR(data, SUCCESS.getCode(), null);
    }

    public static <T> R<T> success(T data, String message) {
        return restR(data, SUCCESS.getCode(), message);
    }

    public static <T> R<T> success(String message) {
        return restR(null, SUCCESS.getCode(), message);
    }

    public static <T> R<T> error() {
        return restR(null, ERROR.getCode(), null);
    }


    public static <T> R<T> error(String message) {
        return restR(null, ERROR.getCode(), message);
    }


    private static <T> R<T> restR(T data, Integer code, String message) {
        R<T> R = new R<>();
        R.setData(data);
        R.setCode(code);
        R.setMessage(message);
        return R;
    }
}
