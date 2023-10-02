package com.ce.servicecontent.web.msg;


import com.ce.servicecommon.msg.CodeMsg;


public class ContentCodeMsg extends CodeMsg {
    private ContentCodeMsg(Integer code, String msg){
        super(code,msg);
    }

    public static final ContentCodeMsg CONTENT_NOTFOUND = new ContentCodeMsg(400100,"未查找到当前post，请联系管理员");

    public static final ContentCodeMsg CONTENT_PRIVATE = new ContentCodeMsg(400100,"当前post私密状态，非法操作！");


}
