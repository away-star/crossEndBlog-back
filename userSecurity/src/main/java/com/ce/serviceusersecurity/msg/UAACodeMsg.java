package com.ce.serviceusersecurity.msg;

import com.ce.servicecommon.msg.CodeMsg;

/**
 * @ClassName UAACodeMsg
 * @Description TODO
 * @Author xingxing
 * @Date 2023/9/19 16:27
 * @Version 1.0
 */
public class UAACodeMsg extends CodeMsg {
    private UAACodeMsg(Integer code, String msg) {
        super(code, msg);
    }

    public static final UAACodeMsg LOGIN_ERROR = new UAACodeMsg(500100, "未知逻辑错误");
    public static final UAACodeMsg LOGIN_ERROR_PASSWORD = new UAACodeMsg(500101, "账号密码有误");

    public static final UAACodeMsg LOGIN_ERROR_ACCOUNT = new UAACodeMsg(500102, "未查询到账户信息，请先注册");

    public static final UAACodeMsg LOGIN_ERROR_EMAIL = new UAACodeMsg(500103, "未查询到邮箱信息，请先注册");

    public static final UAACodeMsg LOGIN_ERROR_PHONE = new UAACodeMsg(500104, "未查询到手机号信息，请先注册");

    public static final UAACodeMsg REGISTER_ERROR_EMAIL = new UAACodeMsg(500105, "该邮箱已注册，请前往登录");

    public static final UAACodeMsg ERROR_CAPTCHA = new UAACodeMsg(500106, "验证码有误，请重新输入");
    public static final UAACodeMsg ERROR_SERVER = new UAACodeMsg(500107, "服务器出问题啦！");
}
