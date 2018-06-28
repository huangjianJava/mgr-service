package com.advance.mgr.common;

/**
 * @author huangj
 * @Description:  自定义 ErrorCode
 * @date 2018/6/1
 */
public enum ErrorCode {

    SERVER_ERROR(500,"服务器内部错误"),
    DATA_INTERGRALITY_ERROR(5000,"数据一致性错误"),

    BAD_REQUEST(400,"参数错误"),

    INVALID_INVITER_ID(4084,"邀请码不存在");


    int code;

    String desc;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
