package com.advance.mgr.exception.definedException;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 权限异常
 * @date Date : 2018/7/31 17:01
 * @since JDK 1.8
 */
public class PermissionException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param message 异常信息
     */
    public PermissionException(String message) {
        super(message);
    }

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param cause 堆栈
     */
    public PermissionException(Throwable cause) {
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
    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
