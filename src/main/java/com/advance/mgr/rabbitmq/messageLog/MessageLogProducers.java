package com.advance.mgr.rabbitmq.messageLog;

import java.util.Date;
import java.util.UUID;
import com.advance.mgr.rabbitmq.RabbitmqMessage;
import com.advance.mgr.rabbitmq.messageLog.domain.CompleteLog;
import com.advance.mgr.rabbitmq.messageLog.domain.ErrorLog;
import com.advance.mgr.rabbitmq.messageLog.domain.ReceiveLog;
import com.advance.mgr.rabbitmq.messageLog.domain.SendLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: MessageLogProducers
 * @date Date : 2018/8/4 11:39
 * @since JDK 1.8
 */
@Component
@EnableAsync
@Slf4j
public class MessageLogProducers {

    /**
     * 描述 : 应用名称
     */
    @Value("${spring.application.name}")
    private String springApplicationName;

    /**
     * 描述 : amqpTemplate
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * objectMapper
     */
    @Autowired
    private ObjectMapper objectMapper;


    /**
     * @method
     * @description send日志处理
     * @date: 2018/8/4 11:41
     * @author: hongcheng.wu
     * @param <T>     泛型
     * @param message message
     * @return
     */
    @Async
    public <T> void send(RabbitmqMessage<T> message) {
        //将消息转换为json格式
        String msgJsonString = null;
        try {
            msgJsonString = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.warn("MessageLog.msgJsonString convert msg to string error : ", e);
        }
        //构造实体
        SendLog log = new SendLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setId(message.getId());
        log.setSender(message.getSender());
        log.setExchange(message.getExchange());
        log.setRoutingKey(message.getRoutingKey());
        log.setTimestamp(message.getTimestamp());
        log.setMsgCreateDate(message.getCreateDate());
        log.setSendDate(message.getSendDate());
        log.setMsgJsonString(msgJsonString);
        //构造消息
        RabbitmqMessage<SendLog> logMessage = new RabbitmqMessage<>();
        logMessage.setSender(springApplicationName);
        logMessage.setSendDate(new Date());
        logMessage.setExchange(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName());
        logMessage.setRoutingKey(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.SEND.name());
        logMessage.setTimestamp(System.currentTimeMillis());
        logMessage.setBody(log);
        //发送消息
        this.amqpTemplate.convertAndSend(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.SEND.name(), logMessage);
    }

    /**
     * receive接收消息处理
     * @param msgId
     * @param queues
     * @param receiveDate
     */
    @Async
    protected void receive(String msgId, String queues, Date receiveDate) {
        //构造实体
        ReceiveLog log = new ReceiveLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setId(msgId);
        log.setQueues(queues);
        log.setReceiveDate(receiveDate);
        //构造消息
        RabbitmqMessage<ReceiveLog> logMessage = new RabbitmqMessage<>();
        logMessage.setSender(springApplicationName);
        logMessage.setSendDate(new Date());
        logMessage.setExchange(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName());
        logMessage.setRoutingKey(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.RECEIVE.name());
        logMessage.setTimestamp(System.currentTimeMillis());
        logMessage.setBody(log);
        //发送消息
        this.amqpTemplate.convertAndSend(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.RECEIVE.name(), logMessage);
    }


    /**
     * complete
     *
     * @param msgId        msgId
     * @param queues       queues
     * @param completeDate completeDate
     */
    @Async
    protected void complete(String msgId, String queues, Date completeDate) {
        //构造实体
        CompleteLog log = new CompleteLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setId(msgId);
        log.setQueues(queues);
        log.setCompleteDate(completeDate);
        //构造消息
        RabbitmqMessage<CompleteLog> logMessage = new RabbitmqMessage<>();
        logMessage.setSender(springApplicationName);
        logMessage.setSendDate(new Date());
        logMessage.setExchange(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName());
        logMessage.setRoutingKey(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.COMPLETE.name());
        logMessage.setTimestamp(System.currentTimeMillis());
        logMessage.setBody(log);
        //发送消息
        this.amqpTemplate.convertAndSend(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.COMPLETE.name(), logMessage);
    }

    /**
     * error
     *
     * @param msgId     msgId
     * @param queues    queues
     * @param errorDate errorDate
     * @param ex        ex
     */
    @Async
    protected void error(String msgId, String queues, Date errorDate, Exception ex) {
        //构造实体
        ErrorLog log = new ErrorLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setId(msgId);
        log.setQueues(queues);
        log.setErrorDate(errorDate);
        log.setErrorMsg(ExceptionUtils.getStackTrace(ex));
        //构造消息
        RabbitmqMessage<ErrorLog> logMessage = new RabbitmqMessage<>();
        logMessage.setSender(springApplicationName);
        logMessage.setSendDate(new Date());
        logMessage.setExchange(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName());
        logMessage.setRoutingKey(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.ERROR.name());
        logMessage.setTimestamp(System.currentTimeMillis());
        logMessage.setBody(log);
        //发送消息
        this.amqpTemplate.convertAndSend(RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), RabbitmqLogConstant.EXCHANGE_MESSAGE_LOG.ERROR.name(), logMessage);
    }



}
