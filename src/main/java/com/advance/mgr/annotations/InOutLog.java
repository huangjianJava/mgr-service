package com.advance.mgr.annotations;

import java.lang.annotation.*;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 自定义日志注解
 * 使用方法为@InOutLog(description="")
 * @date Date : 2018/8/2 11:10
 * @since JDK 1.8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InOutLog {
    String description()  default "";
}
