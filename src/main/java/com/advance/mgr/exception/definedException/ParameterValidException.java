package com.advance.mgr.exception.definedException;

import java.util.List;
import org.springframework.validation.ObjectError;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 参数校验异常
 * @date Date : 2018/7/31 17:03
 * @since JDK 1.8
 */
public class ParameterValidException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    /**
     * 描述 : 参数静态校验错误信息
     */
    private final List<ObjectError> allErrors;

    /**
     * 描述 : 构造函数
     *
     * @param message   异常信息
     * @param allErrors 参数静态校验错误信息
     */
    public ParameterValidException(String message, List<ObjectError> allErrors) {
        super(message);
        this.allErrors = allErrors;
    }

    /**
     * 描述 : 构造函数
     *
     * @param cause     堆栈
     * @param allErrors 参数静态校验错误信息
     */
    public ParameterValidException(Throwable cause, List<ObjectError> allErrors) {
        super(cause);
        this.allErrors = allErrors;
    }

    /**
     * 描述 : 构造函数
     *
     * @param message   异常信息
     * @param cause     堆栈
     * @param allErrors 参数静态校验错误信息
     */
    public ParameterValidException(String message, Throwable cause, List<ObjectError> allErrors) {
        super(message, cause);
        this.allErrors = allErrors;
    }

    /**
     * 描述 : 获取allErrors
     *
     * @return the allErrors
     */
    public List<ObjectError> getAllErrors() {
        return allErrors;
    }

}
