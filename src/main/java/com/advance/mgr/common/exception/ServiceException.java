package com.advance.mgr.common.exception;

import com.advance.mgr.common.ErrorCode;

/**
 * @author huangj
 * @Description:    service 层统一异常处理
 * @date 2018/6/15
 */
public class ServiceException extends CommonException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
