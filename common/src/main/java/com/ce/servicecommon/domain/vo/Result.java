package com.ce.servicecommon.domain.vo;

import com.ce.servicecommon.msg.CodeMsg;
import lombok.Data;

import java.io.Serializable;

import static com.ce.servicecommon.constant.DefaultResult.*;


@Data
public class Result<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> defaultSuccess() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(SUCCESS_CODE, msg, data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<>(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    public static <T> Result<T> defaultError() {
        return new Result<>(ERROR_CODE, ERROR_MESSAGE, null);
    }

    public boolean hasError() {
        //状态码！=200 说明有错误.
        return this.code != SUCCESS_CODE;
    }
}
