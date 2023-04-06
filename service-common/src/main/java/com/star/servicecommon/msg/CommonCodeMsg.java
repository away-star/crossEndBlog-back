package com.star.servicecommon.msg;

/**
 * Created by lanxw
 */
public class CommonCodeMsg extends CodeMsg {
    private CommonCodeMsg(Integer code, String msg){
        super(code,msg);
    }
    public static final CommonCodeMsg ILLEGAL_OPERATION = new CommonCodeMsg(-1,"非法操作");
    public static final CommonCodeMsg TOKEN_INVALID = new CommonCodeMsg(-2,"登录超时,请重新登录");
    public static final CommonCodeMsg METHOD_ARGUMENT_IN_VALID = new CommonCodeMsg(-2,"参数不合法，请检查");
    public static final CommonCodeMsg DATABASE_ERROR = new CommonCodeMsg(-2,"数据库错误");


}
