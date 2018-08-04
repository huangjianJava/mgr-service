/**
 * RabbitmqListenerAspect.java
 * Created at 2016-11-17
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package com.advance.mgr.rabbitmq;

import java.lang.reflect.Method;
import java.util.Arrays;
import com.advance.mgr.exception.definedException.SystemRuntimeException;
import com.advance.mgr.rabbitmq.RabbitmqMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: RabbitmqListenerBaseAspect
 * @date Date : 2018/8/4 10:05
 * @since JDK 1.8
 */
@Slf4j
public class RabbitmqListenerBaseAspect {

    /**
     * 描述 : listenerAspect
     */
    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener) && this(com.advance.mgr.rabbitmq.IRabbitmqListener)")
    public void listenerAspect() {
        log.info("RabbitmqListenerBaseAspect.listenerAspect");
    }

    /**info
     * 描述 : 获得队列名称(利用反射)
     *
     * @param joinPoint joinPoint
     * @return 队列名称
     */
    public String getQueues(JoinPoint joinPoint) {
        try {
            Method method = joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(), RabbitmqMessage.class);
            return Arrays.toString(method.getAnnotation(RabbitListener.class).queues());
        } catch (NoSuchMethodException e) {
            throw new SystemRuntimeException(e);
        }
    }

}
