package com.advance.mgr.annotations;

import java.lang.reflect.Method;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import com.advance.mgr.controller.HelloController;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 自定义注解aop
 * @date Date : 2018/8/2 11:12
 * @since JDK 1.8
 */
@Aspect
@Component
public class InOutLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(InOutLogAspect.class);

    /**
     * @method
     * @description 切入点
     * @date: 2018/8/2 11:13
     * @author: hongcheng.wu
     * @param:
     * @return
     */
    @Pointcut("@annotation(com.advance.mgr.annotations.InOutLog)")
    public void aspect() { }

    /**
     * @method
     * @description 前置通知
     * @date: 2018/8/2 11:14
     * @author: hongcheng.wu
     * @param:
     * @return
     */
    @Before("aspect()")
    public void doBefore(final JoinPoint joinPoint) throws Exception {
        final JSONObject jsonObject = doJsonObject(joinPoint);
        logger.info(jsonObject.toJSONString());
    }

    /**
     * @method
     * @description 后置通知
     * @date: 2018/8/2 11:21
     * @author: hongcheng.wu
     * @param:
     * @return
     */
    @AfterReturning(returning = "result", pointcut = "aspect()")
    public void doAfterReturning(final JoinPoint joinPoint, final Object result) {

        final JSONObject jsonObject = doJsonObject(joinPoint);
        jsonObject.put("outParams", toJSONString(result));

        logger.info(jsonObject.toJSONString());
    }


    /**
     * @method
     * @description 重写json转换方法 ：针对层级很多的json转换异常问题
     * @date: 2018/8/2 11:19
     * @author: hongcheng.wu
     * @param:
     * @return
     */
    private String toJSONString(Object object) {
        return com.alibaba.fastjson.JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }


    private JSONObject doJsonObject(final JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = attributes.getRequest();

        final String description = getMethodDescription(joinPoint);

        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("url", request.getRequestURL());
        json.put("method", (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
        json.put("inParams", joinPoint.getArgs());

        return json;
    }

    /**
     * @method
     * @description 获取注解中对方法的描述信息 用于Controller层注解
     * @date: 2018/8/2 11:16
     * @author: hongcheng.wu
     * @param: 采用反射机制
     * @return
     */
    public String getMethodDescription(final JoinPoint joinPoint) {
        //类名
        final String targetName = joinPoint.getTarget().getClass().getName();
        //方法名
        final String methodName = joinPoint.getSignature().getName();
        //方法参数
        final Object[] arguments = joinPoint.getArgs();
        Class targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            logger.error("类型转换异常", e);
        }
        String description = "";
        if (targetClass == null) {
            return description;
        }
        //获取类中所有的方法
        final Method[] methods = targetClass.getMethods();

        //方法配对
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                final Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    //配对成功或者方法描述
                    description = method.getAnnotation(InOutLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

}
