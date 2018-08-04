package com.advance.mgr.rabbitmq;

import com.advance.mgr.dto.sys.SysRoleResDto;
import com.advance.mgr.rabbitmq.messageLog.RabbitmqLogConstant;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 实现IRabbitmqListener处理自己的队列逻辑
 * @date Date : 2018/8/4 10:15
 * @since JDK 1.8
 */
@Configuration
@EnableRabbit
@Slf4j
public class RabbitMqCustomer implements IRabbitmqListener<SysRoleResDto> {

    @Value("${spring.rabbitmq.mgr-exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @Value("${spring.rabbitmq.rount-key}")
    private String rountKey;

    /**
     * 定义对列名称
     * @return
     */
    @Override
    @Bean
    public Queue queue() {
       return new Queue(queue);
    }

    /**
     * 绑定交换机和路由key（需放在配置文件里面在实际企业级应用中）
     * @return
     */
    @Override
    @Bean
    public Binding binding() {
        return new Binding(queue, Binding.DestinationType.QUEUE, exchange, rountKey, null);
    }

    /**
     * @method
     * @description 监听方法
     * @date: 2018/8/4 10:38
     * @author: hongcheng.wu
     * @param:
     * @return
     */
    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void process(RabbitmqMessage<SysRoleResDto> message) {
        log.info(JSON.toJSONString(message));
    }



}
