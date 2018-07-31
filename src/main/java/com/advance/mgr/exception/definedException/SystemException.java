package com.advance.mgr.exception.definedException;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 系统异常
 * @date Date : 2018/7/31 16:59
 * @since JDK 1.8
 */
public class SystemException  extends Exception{

    private static final long serialVersionUID = 1L;
    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param message 异常信息
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param cause 堆栈
     */
    public SystemException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param message 异常信息
     * @param cause   堆栈
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }



}
