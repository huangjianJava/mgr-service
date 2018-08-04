package com.advance.mgr.config;

import com.advance.mgr.rabbitmq.messageLog.RabbitmqLogConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: RabbitMQConfig
 * @date Date : 2018/8/4 9:47
 * @since JDK 1.8
 */
@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.mgr-exchange}")
    private String exchange;



    /**
     * @method
     * @description json消息转换器
     * @date: 2018/8/4 9:56
     * @author: hongcheng.wu
     * @param:
     * @return
     */
    @Bean
    public MessageConverter messageConverter () {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * @method
     * @description 配置日志交换机
     * @date: 2018/8/4 10:00
     * @author: hongcheng.wu
     * @param:
     * @return
     */
    @Bean
    public DirectExchange exchangeMessageLog() {
        return new DirectExchange(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), true, false);
    }

    /**
     * @method
     * @description 配置业务交换机
     * @date: 2018/8/4 11:12
     * @author: hongcheng.wu
     * @param:
     * @return
     */
    @Bean
    public DirectExchange exchangeMgrService() {
        return new DirectExchange(exchange, true, false);
    }




}
