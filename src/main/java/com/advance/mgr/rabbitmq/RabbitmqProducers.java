package com.advance.mgr.rabbitmq;

import java.util.Date;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 消息生产者
 * @date Date : 2018/8/4 10:05
 * @since JDK 1.8
 */
@Component
public class RabbitmqProducers {

    /**
     * 描述 : 应用名称
     */
    @Value("${spring.application.name}")
    private String springApplicationName;

    @Autowired
    private AmqpTemplate amqpTemplate;


    /**
     * 描述 : Convert a Java object to an Amqp Message and send it to a specific exchange with a
     * specific routing key.
     *
     * @param exchange   exchange
     * @param routingKey routingKey
     * @param message    a message to send
     * @param <T>        任意类型
     */
    public <T> void convertAndSend(String exchange, String routingKey, RabbitmqMessage<T> message) {
        message.setSender(springApplicationName);
        message.setSendDate(new Date());
        message.setExchange(exchange);
        message.setRoutingKey(routingKey);
        message.setTimestamp(System.currentTimeMillis());
        this.amqpTemplate.convertAndSend(exchange, routingKey, message);

    }

}
