package com.advance.mgr.exception;

import com.advance.mgr.common.exception.ServiceException;

/**
 * @author huangj
 * @Description:    自定义 service 层异常（不同的业务需要定义各自符合的异常信息）
 * @date 2018/6/15
 */
public class SelfDefineException extends ServiceException {

    public SelfDefineException(String message) {
        super(message);
    }

}
