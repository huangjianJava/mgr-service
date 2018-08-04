package com.advance.mgr.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 消息发送者公共接口定义
 * @date Date : 2018/8/4 10:08
 * @since JDK 1.8
 */
public interface IRabbitmqListener<T> {

    /**
     * 描述 : 消费者队列(Bean)
     *
     * @return Queue
     */
    Queue queue();

    /**
     * 描述 : 绑定(Bean)
     *
     * @return Binding
     */
    Binding binding();

    /**
     * 描述 : 处理消息
     *
     * @param message 消息
     */
    void process(RabbitmqMessage<T> message);


}
