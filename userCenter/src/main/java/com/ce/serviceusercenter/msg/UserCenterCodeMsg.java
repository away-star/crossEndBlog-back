package com.ce.serviceusercenter.msg;


import com.ce.servicecommon.msg.CodeMsg;


public class UserCenterCodeMsg extends CodeMsg {
    private UserCenterCodeMsg(Integer code, String msg) {
        super(code, msg);
    }

    public static final UserCenterCodeMsg LOGIN_ERROR = new UserCenterCodeMsg(500100, "未知逻辑错误");
    public static final UserCenterCodeMsg LOGIN_ERROR_PASSWORD = new UserCenterCodeMsg(500101, "账号密码有误");

    public static final UserCenterCodeMsg LOGIN_ERROR_ACCOUNT = new UserCenterCodeMsg(500102, "未查询到账号信息，请先注册");

    public static final UserCenterCodeMsg LOGIN_ERROR_EMAIL = new UserCenterCodeMsg(500103, "未查询到邮箱信息，请先注册");

    public static final UserCenterCodeMsg LOGIN_ERROR_PHONE = new UserCenterCodeMsg(500104, "未查询到手机号信息，请先注册");

    public static final UserCenterCodeMsg REGISTER_ERROR_EMAIL = new UserCenterCodeMsg(500105, "该邮箱已注册，请前往登录");

    public static final UserCenterCodeMsg ERROR_CAPTCHA = new UserCenterCodeMsg(500106, "验证码有误，请重新输入");
    public static final UserCenterCodeMsg ERROR_SERVER = new UserCenterCodeMsg(500107, "服务器出问题啦！");
}
