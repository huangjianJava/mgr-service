package com.advance.mgr.config;

import com.advance.mgr.rabbitmq.RabbitmqConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: RabbitMQConfig
 * @date Date : 2018/8/4 9:47
 * @since JDK 1.8
 */
@Configuration
public class RabbitMQConfig {

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
     * @description 配置点对点交换机
     * @date: 2018/8/4 10:00
     * @author: hongcheng.wu
     * @param:
     * @return
     */
    @Bean
    public DirectExchange exchangeMessageLog() {
        return new DirectExchange(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), true, false);
    }

    /**
     * exchangeMessageLogDlx(死信交换机)
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange exchangeMessageLogDlx() {
        return new DirectExchange(RabbitmqConstant.EXCHANGE_MESSAGE_LOG_DLX.class.getSimpleName(), true, false);
    }


}
