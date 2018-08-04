package com.advance.mgr.rabbitmq.messageLog;

import java.util.Date;
import com.advance.mgr.rabbitmq.IRabbitmqListener;
import com.advance.mgr.rabbitmq.RabbitmqListenerBaseAspect;
import com.advance.mgr.rabbitmq.RabbitmqMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: RabbitmqListenerLog  Aop
 * @date Date : 2018/8/4 11:48
 * @since JDK 1.8
 */
@Aspect
@Component
@Order(100)
@Slf4j
public class RabbitmqListenerLogAspect extends RabbitmqListenerBaseAspect {


    @Autowired
    private MessageLogProducers messageLogProducers;

    /**
     * 描述 : 前置通知
     *
     * @param joinPoint joinPoint
     */
    @Before("listenerAspect()")
    public void before(JoinPoint joinPoint) {
        if (joinPoint.getTarget() instanceof IRabbitmqListener) {
            RabbitmqMessage<?> message = (RabbitmqMessage<?>) joinPoint.getArgs()[0];
            String queues = this.getQueues(joinPoint);
            messageLogProducers.receive(message.getId(), queues, new Date());
        }
    }

    /**
     * 描述 : 后置通知
     *
     * @param joinPoint joinPoint
     */
    @After("listenerAspect()")
    public void after(JoinPoint joinPoint) {
        if (joinPoint.getTarget() instanceof IRabbitmqListener) {
            RabbitmqMessage<?> message = (RabbitmqMessage<?>) joinPoint.getArgs()[0];
            String queues = this.getQueues(joinPoint);
            messageLogProducers.complete(message.getId(), queues, new Date());
        }
    }

    /**
     * 异常通知
     *
     * @param joinPoint joinPoint
     * @param ex        ex
     */
    @AfterThrowing(pointcut = "listenerAspect()", throwing = "ex")
    public void fterThrowing(JoinPoint joinPoint, Exception ex) {
        if (joinPoint.getTarget() instanceof IRabbitmqListener) {
            RabbitmqMessage<?> message = (RabbitmqMessage<?>) joinPoint.getArgs()[0];
            String queues = this.getQueues(joinPoint);
            messageLogProducers.error(message.getId(), queues, new Date(), ex);
        }
    }


}
