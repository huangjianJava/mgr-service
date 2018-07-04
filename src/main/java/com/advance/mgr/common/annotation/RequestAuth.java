package com.advance.mgr.common.annotation;

import com.advance.mgr.common.enums.sys.PopedomEnum;
import java.lang.annotation.*;

/**
 * @author huangj
 * @Description: 自定义注解 -> 接口权限校验
 * @date 2018/7/4
 */
@Target(ElementType.METHOD)   
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface RequestAuth {
	boolean checkLogin() default true; //是否检测用户
    
	String logName() default "";  //日志记录的名称,如为空取
	
	String logUrl() default ""; //日志记录的url
	
	boolean saveRequest() default true;  //请求的参数
	
	boolean saveResult()  default false; //请求的结果
	
	String popedomCode()  default ""; //功能权限名称
	
	PopedomEnum popedomType() default PopedomEnum.None; //功能权限类型

}
