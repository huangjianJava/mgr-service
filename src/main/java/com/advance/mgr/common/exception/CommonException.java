package com.advance.mgr.common.exception;

import com.advance.mgr.common.ErrorCode;

/**
 * @author huangj
 * @Description:    异常父类，用于处理自己的业务异常，
 * 其他自定义异常需继承该类，在异常统一处理时，更加方便（状态码200）
 * @date 2018/6/1
 */
public class CommonException extends RuntimeException{

    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(ErrorCode errorCode){
        super(errorCode.getDesc());
    }

    public CommonException(ErrorCode errorCode, Throwable cause){
        super(errorCode.getDesc(),cause);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    protected CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
